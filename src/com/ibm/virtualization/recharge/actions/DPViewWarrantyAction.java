package com.ibm.virtualization.recharge.actions;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.hbo.common.HBOConstants;
import com.ibm.virtualization.recharge.beans.DPEditWarrantyFormBean;
import com.ibm.virtualization.recharge.beans.DPViewWarrantyFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.DPViewWarrantyDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;
public class DPViewWarrantyAction extends DispatchAction  {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(DPViewWarrantyAction.class);
	}

	public ActionForward select(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		DPViewWarrantyFormBean prodDPBean = (DPViewWarrantyFormBean) form;		
		String userId = (String) session.getAttribute("userId");
		String condition = (String) session.getAttribute("condition");		
		DPMasterService dpms = new DPMasterServiceImpl();
		DPViewWarrantyDTO dpvpDTO = new DPViewWarrantyDTO();		
		ArrayList bcaList = new ArrayList(); // for business category
		BeanUtils.copyProperties(dpvpDTO,prodDPBean);
		
		try {			
			bcaList = dpms.getBusinessCategory(userId);
			DPViewWarrantyFormBean dppbean = (DPViewWarrantyFormBean) form;			
			dppbean.setArrBCList(bcaList);
			//dppbean.setProductList(dpms.select1(dpvpDTO));
			//dppbean.setList(dpms.select1(dpvpDTO));		
	
		} catch (Exception e) {
			log
			.error("ERROR IN FETCHING BUSINESS CATEGORY LIST [init] function");
		}
		saveToken(request);
		forward = mapping.findForward("success");
		return (forward);
	}

/*	
	public ActionForward Selectdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException {		
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		DPViewWarrantyFormBean prodDPBean1 = (DPViewWarrantyFormBean) form;
		DPMasterService dpservice = new DPMasterServiceImpl();
		
		if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				prodDPBean1.getBusinessCategory().equals("1")){
			
			request.setAttribute("suk", "suk");
			
		}if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				prodDPBean1.getBusinessCategory().equals("2")){
			System.err.println("here in action rc");
			request.setAttribute("rc", "rc");
			
		}if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				prodDPBean1.getBusinessCategory().equals("3")){
			
			request.setAttribute("handset", "handset");
			
		}
		
		DPViewWarrantyDTO dto = new DPViewWarrantyDTO();
		ArrayList productList = new ArrayList();
		errors.add("message.product", new ActionError(
		"message.product.insert_success"));
		 saveErrors(request, errors);
		 
		try {
			
			BeanUtils.copyProperties(dto,prodDPBean1);
			prodDPBean1.setUpdatedby(""+sessionContext.getId());
			
			prodDPBean1.setProductList((ArrayList)dpservice.select1(dto));
			
			ArrayList message = dpservice.select1(dto);
			
			
			forward = mapping.findForward("successfully in DPMasterServiceImpl");
			

		} 
		catch (Exception ex){
			ex.printStackTrace();
		}
		
		logger.info("Executed... ");
		
		return forward;

	}*/
	
	
	public ActionForward Selectdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		DPEditWarrantyFormBean warrntyBean = (DPEditWarrantyFormBean) form;
		String imeino = warrntyBean.getImeino();	
		getWarrantyDetails(mapping, form, request, errors, imeino);		
		logger.info(" Executed edit product action ");
		return mapping.findForward(Constants.EDIT_WARRANTY);	
	}
	
	private ActionForward getWarrantyDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, ActionErrors errors,
			String imeino) throws DAOException {		
		try {
			ActionMessages messages = new ActionMessages();
			HttpSession session = request.getSession();
			DPMasterService warrantyService = new DPMasterServiceImpl();
			UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);
			DPViewWarrantyDTO warranty = warrantyService.editWarranty(imeino,sessionContext.getId());
			if(warranty==null){
				messages.add(HBOConstants.NO_RECORD,new ActionError("no.record.found"));
				saveMessages(request, (ActionMessages) messages);
				return mapping.findForward(Constants.EDIT_WARRANTY);
			}
			DPEditWarrantyFormBean warrantyFormBean = (DPEditWarrantyFormBean) form;
			//int productId = productFormBean.getProductid();
			BeanUtils.copyProperties(warrantyFormBean, warranty);	
		}
          catch (VirtualizationServiceException vse) {
			logger.error("  Service Exception occured   ", vse);
			errors.add("errors.account", new ActionError(vse.getMessage()));
		} catch (IllegalAccessException iaExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.WARRANTY_ERROR);
		} 
		catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.WARRANTY_ERROR);
		} 
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.WARRANTY_ERROR);
		}
		return mapping.findForward(Constants.EDIT_WARRANTY);

	}
		
	public ActionForward loadPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		
		return mapping.findForward(Constants.SUCCESS);
	}	


}
