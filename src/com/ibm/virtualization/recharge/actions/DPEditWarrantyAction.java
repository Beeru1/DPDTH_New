package com.ibm.virtualization.recharge.actions;


import java.lang.reflect.InvocationTargetException;

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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.DPEditWarrantyFormBean;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.beans.DPViewWarrantyFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.DPEditWarrantyDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;


public class DPEditWarrantyAction extends DispatchAction{
	private static final Logger logger;

	static {
		logger = Logger.getLogger(DPEditWarrantyAction.class);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();		
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
 //       AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
//        if (!authorizationService.isUserAuthorized(sessionContext
//		.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
//		    logger.info(" user not auth to perform this ROLE_EDIT_PRODUCT activity  ");
//		    errors.add("errors", new ActionError(
//		    "errors.usernotautherized"));
//		     saveErrors(request, errors);
//		    return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
//        }	
		
		
		
//	  String productid = request.getParameter("productid");
		DPViewWarrantyFormBean productFormBean = (DPViewWarrantyFormBean) form;
        int productid = productFormBean.getProductid();
//        DPEditProductFormBean editProduct = new DPEditProductFormBean();
//        editProduct.setProductid(Integer.parseInt(productid));
		getProductWarrantyDetails(mapping, form, request, errors, productid);
		logger.info(" Executed edit warranty action ");
		return mapping.findForward(Constants.EDIT_WARRANTY);	
	}
	
	
	private ActionForward getProductWarrantyDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, ActionErrors errors,
			int productid) throws DAOException {		
		
		try {

			DPMasterService productService = new DPMasterServiceImpl();				
		//	DPEditProductDTO product = productService.edit(productid);
			DPEditWarrantyDTO product = productService.editproductwarranty(productid);
			DPViewWarrantyFormBean productFormBean = (DPViewWarrantyFormBean) form;
			int productId = productFormBean.getProductid();
			//String productName = productFormBean.getProductname();
			BeanUtils.copyProperties(productFormBean, product);		
		
		}			
			
          catch (VirtualizationServiceException vse) {
			logger.error("  Service Exception occured   ", vse);
			errors.add("errors.account", new ActionError(vse.getMessage()));
		} catch (IllegalAccessException iaExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.PRODUCT_ERROR);
		} 
		catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.PRODUCT_ERROR);
		} 
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.PRODUCT_ERROR);
		}
		return mapping.findForward(Constants.EDIT_WARRANTY);

	}
	
/*	public ActionForward Updatedata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException
	{
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();		
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		DPViewWarrantyFormBean productFormBean = (DPViewWarrantyFormBean) form;
	//	DPEditWarrantyFormBean prodDPBean = (DPEditWarrantyFormBean) form;
		//DPEditWarrantyDTO dpepDTO = new DPEditWarrantyDTO();
		DPMasterService dpservice = new DPMasterServiceImpl();
		 errors.add("message.product", new ActionError(
		"message.product.insert_success"));
		 saveErrors(request, errors);
		 
		try {		
			
			productFormBean.setUpdatedby(""+sessionContext.getId());			
			String message = dpservice.editwarranty(productFormBean);
			forward = mapping.findForward(message);
			
			ActionMessages messages = new ActionMessages();
			
			if(message.equalsIgnoreCase("failure")){
				messages.add("ERROR_OCCURED", new ActionMessage("error.occured"));
			}
			else{
				messages.add("MESSAGE_SENT_SUCCESS",new ActionMessage("update.success"));
			}	
			saveMessages(request, messages);

		} catch (VirtualizationServiceException e) {

			logger.error("Exception occured while reteriving edit warranty Action"
					+ "Exception Message in action part: ", e);
		}
		logger.info("Executed warranty edition action");
		return forward;

	}*/
	
	
	public ActionForward UpdateWarrantydata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException {	
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();			
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
	
		DPEditWarrantyFormBean warrantyBean = (DPEditWarrantyFormBean) form;
		
		DPMasterService dpservice = new DPMasterServiceImpl();
		 errors.add("message.product", new ActionError(
		"message.product.insert_success"));
		 saveErrors(request, errors);
		 String message="";
		try {
			warrantyBean.setUpdatedby(""+sessionContext.getId());				
			message = dpservice.updateWarranty(warrantyBean);
			forward = mapping.findForward(message);				
			ActionMessages messages = new ActionMessages();				
			if(message.equalsIgnoreCase("failure")){
				messages.add("ERROR_OCCURED", new ActionMessage("error.occured"));
			}
			else{
				messages.add("MESSAGE_SENT_SUCCESS",new ActionMessage("update.success"));
			}	
			saveMessages(request, messages);

		} catch (VirtualizationServiceException e) {

			logger.error("Exception occured while reteriving Update Product Action"
					+ "Exception Message in action part: ", e);
		}
		logger.info("Executed... ");
		return forward;

	}
	public ActionForward getReset1(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		DPEditWarrantyFormBean bean = (DPEditWarrantyFormBean) form;
		bean.reset1();
		return null;
		
		
	}


}


