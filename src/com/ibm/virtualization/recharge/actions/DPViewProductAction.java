package com.ibm.virtualization.recharge.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.apache.struts.actions.DispatchAction;


import com.ibm.dp.dto.AccountManagementActivityReportDto;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.DPProductDto;
import com.ibm.dp.service.AccountManagementActivityReportService;
import com.ibm.dp.service.impl.AccountManagementActivityReportServiceImpl;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

public class DPViewProductAction extends DispatchAction {

	private static final Logger logger;
	private static final String SUCCESS_EXCEL = "success1";
	

	static {
		logger = Logger.getLogger(DPViewProductAction.class);
	}

	public ActionForward select(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionForward forward = new ActionForward();
		DPViewProductFormBean prodDPBean = (DPViewProductFormBean) form;
		prodDPBean.reset();
		prodDPBean.setCircleId(sessionContext.getCircleId());
		
		String userId = (String) session.getAttribute("userId");
		String condition = (String) session.getAttribute("condition");
		
		DPMasterService dpms = new DPMasterServiceImpl();
		DPViewProductDTO dpvpDTO = new DPViewProductDTO();
		
		ArrayList bcaList = new ArrayList(); // for business category
		BeanUtils.copyProperties(dpvpDTO,prodDPBean);
//		ArrayList viewProduct=new ArrayList();
		
		try {
			
			bcaList = dpms.getBusinessCategory(userId);
//			DPViewProductFormBean dppbean = (DPViewProductFormBean) form;
			
			prodDPBean.setArrBCList(bcaList);
//			viewProduct=dpms.select(dpvpDTO);
//		if(dppbean.getCategorycode() == 4){
//			dppbean.setProductList(viewProduct);
//		}else{	
//			dppbean.setList(dpms.select(dpvpDTO));
//		}
		} catch (Exception e) {
			log
			.error("ERROR in view product action");
		}
		saveToken(request);
		forward = mapping.findForward("success");
		return (forward);
	}
	public ActionForward Selectdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException {
		
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		DPViewProductFormBean prodDPBean1 = (DPViewProductFormBean) form;
		
		DPMasterService dpservice = new DPMasterServiceImpl();
		int circleId = sessionContext.getCircleId();
		if(request.getParameter("categorycode")==null || request.getParameter("circleId") == null){
			
			request.setAttribute("categorycode", prodDPBean1.getBusinessCategory());
			request.setAttribute("circleId", circleId);
		}else{
			request.setAttribute("categorycode", prodDPBean1.getBusinessCategory());
			request.setAttribute("circleId", circleId);
			prodDPBean1.setBusinessCategory(request.getParameter("categorycode"));
			prodDPBean1.setCircleId(Integer.parseInt(request.getParameter("circleId")));
		}
		if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				(prodDPBean1.getBusinessCategory().equals("1"))){
			
			request.setAttribute("suk", "suk");
			
		}if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				prodDPBean1.getBusinessCategory().equals("2")){
			request.setAttribute("rc", "rc");
			
		}if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				prodDPBean1.getBusinessCategory().equals("4")){
			
			request.setAttribute("ActivationVoucher", "ActivationVoucher");
			
		}if(prodDPBean1 !=null && !prodDPBean1.getBusinessCategory().equals("")&&
				prodDPBean1.getBusinessCategory().equals("3")){
			
			request.setAttribute("easyRecharge", "easyRecharge");
		}
		DPViewProductDTO dto = new DPViewProductDTO();
		ArrayList productList = new ArrayList();
		errors.add("message.product", new ActionError(
		"message.product.insert_success"));
		 saveErrors(request, errors);
		try {
			prodDPBean1.setUpdatedby(""+sessionContext.getId());
			BeanUtils.copyProperties(dto,prodDPBean1);
		
			
			int noofpages = dpservice.getProductListCount(dto);
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all accounts */
			productList = dpservice.select(dto,
					pagination.getLowerBound(), pagination.getUpperBound());
			/*
			 * Account accountDto = (Account) accountList.get(0); int
			 * totalRecords = accountDto.getTotalRecords(); int noofpages =
			 * Utility.getPaginationSize(totalRecords);
			 */
			/* Delimit accountaddress field to 10 chars while displaying in grid */
			Iterator iter = productList.iterator();
			while (iter.hasNext()) {
				DPViewProductDTO productDto = (DPViewProductDTO) iter
						.next();
			}
			request.setAttribute("PAGES", noofpages);
			prodDPBean1.setProductList(productList);
//			ArrayList message = dpservice.select(dto);
			forward = mapping.findForward("successfully in DPMasterServiceImpl");
		} 
		catch (Exception ex){
			ex.printStackTrace();
		}
		logger.info("Executed... ");
		return forward;
	}
	
	//aman
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of DPViewProductAction...................!!!");	
		HttpSession session = request.getSession();
		session.setAttribute("AccountManagementActivity", "inprogress"); //Added by Neetika BFR 16 Release 3 on 16 Aug
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		String accountID = String.valueOf(sessionContext.getId());
		String dpProdId="";
		
		try {
			//logger.info("asa::::::;111111111111");
			DPViewProductFormBean prodDPBean = (DPViewProductFormBean) form;
			
			//DPProductReportBean reportBean = (DPProductReportBean) form;
			DPMasterService dpMasterService =new DPMasterServiceImpl();
			DPProductDto dpProductDto =new DPProductDto();
			
			request.setAttribute("categorycode", prodDPBean.getBusinessCategory());
			dpProdId= prodDPBean.getBusinessCategory();
			logger.info("asa::::::::::::catrgry:::::::::::::"+dpProdId);

			//reportDto.setAccountId(sessionContext.getId());
		
			List<DPProductDto> reportList =dpMasterService.getProductList(dpProdId);
			prodDPBean.setProdList(reportList);
			//logger.info("asa:::::::::::::222222222222");
			logger.info("Report list size == "+reportList.size());
			request.setAttribute("reportList",reportList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS_EXCEL);
		}
		
		return mapping.findForward(SUCCESS_EXCEL);
	}
	
	//end
	
	
	
	
	
	
}