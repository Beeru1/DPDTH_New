package com.ibm.dp.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.DpDcDamageStatusBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpDcChurnStatusDto;
import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.dto.DpDcFreshStatusDto;
import com.ibm.dp.service.DpDcChurnStatusService;
import com.ibm.dp.service.DpDcDamageStatusService;
import com.ibm.dp.service.DpDcFreshStatusService;
import com.ibm.dp.service.impl.DpDcChurnStatusServiceImpl;
import com.ibm.dp.service.impl.DpDcDamageStatusServiceImpl;
import com.ibm.dp.service.impl.DpDcFreshStatusServiceImpl;
import com.ibm.rmi.util.MethodNames;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


public class DpDcDamageStatus extends DispatchAction {
private static Logger logger = Logger.getLogger(DpDcDamageStatus.class.getName());

	private static final String INIT_SUCCESS = "initSuccess";
	private static final String INIT_SUCCESS_DAMAGE = "initSuccessDamage";
	private static final String INIT_SUCCESS_FRESH = "initSuccessFresh";
	private static final String STATUS_ERROR = "error";
	/**
	 *  
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc initial information which fetch the dcs from the table and display in table list [infront end screen].
	 */
	public ActionForward initDcStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
   logger.info("************** DpDcDAMAGEStatus -> initDcStatus() method. Called****************");
		
		// Get account Id form session.
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		DpDcDamageStatusBean reverseDCStatusBean =(DpDcDamageStatusBean)form;

		String methodValue = request.getParameter("methodValue");
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		try
		{
			
			logger.info("************** distId =="+distId);
			logger.info("************** Method Value is-->>>>>>>>>>>>>>>>>>>>>> ===================================================="+methodValue);
			
			request.setAttribute("hidArrDcNos", "");
			
			
			if(methodValue != null && methodValue.equalsIgnoreCase("Fresh")){
				setDefaultValueFresh(reverseDCStatusBean, request,distId);
				System.out.println("-------------------------If--------------------");
				request.setAttribute("PageId", "Fresh");
				return mapping.findForward(INIT_SUCCESS_FRESH);
			}else if(methodValue != null && methodValue.equalsIgnoreCase("SubmitDamgDetail")){
				logger.info("In Submit the Details of Damaged DRAFT DC.....");
				String courierAgencyName = reverseDCStatusBean.getHidCourAgen();
				String docketNum = reverseDCStatusBean.getHidDocketNum();
				String dcNum =request.getParameter("dcNo");
				logger.info("dcNum == "+dcNum+" and courierAgencyName == "+courierAgencyName +" and docketNum=="+docketNum);
				DpDcDamageStatusService dcNosService = new DpDcDamageStatusServiceImpl();
				dcNosService.submitDamageDetail(dcNum, courierAgencyName, docketNum);
				setDefaultValue(reverseDCStatusBean, request,distId);
				request.setAttribute("PageId", "Damage");
				reverseDCStatusBean.setHidCourAgen("");
				reverseDCStatusBean.setHidDocketNum("");
				return mapping.findForward(INIT_SUCCESS_FRESH);
				
			}else if(methodValue != null && methodValue.equalsIgnoreCase("SubmitFrsDetail")){
				logger.info("In Submit the Details of Fresh DRAFT DC.....");
				String courierAgencyName = reverseDCStatusBean.getHidCourAgen();
				String docketNum = reverseDCStatusBean.getHidDocketNum();
				String dcNum =request.getParameter("dcNo");
				logger.info("dcNum == "+dcNum+" and courierAgencyName == "+courierAgencyName +" and docketNum=="+docketNum);
				DpDcDamageStatusService dcNosService = new DpDcDamageStatusServiceImpl();
				dcNosService.submitDamageDetail(dcNum, courierAgencyName, docketNum);
				setDefaultValue(reverseDCStatusBean, request,distId);
				request.setAttribute("PageId", "Fresh");
				reverseDCStatusBean.setHidCourAgen("");
				reverseDCStatusBean.setHidDocketNum("");
				return mapping.findForward(INIT_SUCCESS_FRESH);

				//Added by Shah
			}else if(methodValue != null && methodValue.equalsIgnoreCase("SubmitChurnDetail")){
				logger.info("In Submit the Details of Fresh DRAFT DC.....");
				String courierAgencyName = reverseDCStatusBean.getHidCourAgen();
				String docketNum = reverseDCStatusBean.getHidDocketNum();
				String dcNum =request.getParameter("dcNo");
				logger.info("dcNum == "+dcNum+" and courierAgencyName == "+courierAgencyName +" and docketNum=="+docketNum);
				DpDcChurnStatusService dcNosService = new DpDcChurnStatusServiceImpl();
				dcNosService.submitDamageDetail(dcNum, courierAgencyName, docketNum);
				setDefaultValue(reverseDCStatusBean, request,distId);
				request.setAttribute("PageId", "Fresh");
				reverseDCStatusBean.setHidCourAgen("");
				reverseDCStatusBean.setHidDocketNum("");
				return mapping.findForward(INIT_SUCCESS_FRESH);
				
			}else{
				setDefaultValue(reverseDCStatusBean, request,distId);
				System.out.println("-------------------------else--------------------");
				request.setAttribute("PageId", "Damage");
				return mapping.findForward(INIT_SUCCESS_FRESH);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		
		
		//return mapping.findForward(INIT_SUCCESS);
			
	}
	
	private void setDefaultValue(DpDcDamageStatusBean reverseDCStatusBean,
			HttpServletRequest request,Long lngCrBy) throws Exception {
			DpDcDamageStatusService dcNosService = new DpDcDamageStatusServiceImpl();
			List<DpDcDamageStatusDto> dcNosList = dcNosService.getAllDCList(lngCrBy);
			reverseDCStatusBean.setDcNosList(dcNosList);
			request.setAttribute("dcNosList", dcNosList);
		
		DpDcFreshStatusService dcNosServiceFresh = new DpDcFreshStatusServiceImpl();
		List<DpDcFreshStatusDto> dcNosListFresh = dcNosServiceFresh.getAllDCListFresh(lngCrBy);

		reverseDCStatusBean.setDcNosListFresh(dcNosListFresh);
		request.setAttribute("dcNosListFresh", dcNosListFresh);

		//Added by Shah
		DpDcChurnStatusService dcNosServiceChurn = new DpDcChurnStatusServiceImpl();
		List<DpDcChurnStatusDto> dcNosListChurn = dcNosServiceChurn.getAllDCListChurn(lngCrBy);

		reverseDCStatusBean.setDcNosListChurn(dcNosListChurn);
		request.setAttribute("dcNosListChurn", dcNosListChurn);

	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc Submit the Change Status
	 */
	public ActionForward saveCancelStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** saveCancelStatus() method. Called*****************************");
		
		HttpSession session = request.getSession();
		DpDcDamageStatusBean reverseDCStatusBean =(DpDcDamageStatusBean)form;
		String strMessage ="";
		try{
			//setDefaultValue(reverseDCStatusBean, request);
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			logger.info(" distId =="+distId+" circleId == "+circleId );
		
			//Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
			String[] arrDcNos =null;
			if(reverseDCStatusBean.getHidArrDcNos()!=null){
				arrDcNos =reverseDCStatusBean.getHidArrDcNos();
				logger.info(" arrDcNos length == "+arrDcNos.length + " arrDcNos[0] == "+arrDcNos[0]);
			}
			String[] arrDcNosNew =null;
			String strSuccessMsg ="";
			if(arrDcNos!=null){
				arrDcNosNew =arrDcNos[0].split(",");
			}
			if(arrDcNosNew!=null){
				DpDcDamageStatusService dcNosService = new DpDcDamageStatusServiceImpl();
				strSuccessMsg = dcNosService.setDCStatus(arrDcNosNew);
			}
			if(strSuccessMsg.equals("SUCCESS")){
				strMessage ="DC has been Canceled Successfully !!";
			}
			else
			{
				strMessage = strSuccessMsg;
			}
			setDefaultValue(reverseDCStatusBean, request,distId);
			request.setAttribute("hidArrDcNos", "");
			
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage =ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseDCStatusBean.setStrSuccessMsg(strMessage);
			
			
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	    
	public ActionForward viewSerialsStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			DpDcDamageStatusService dcNosService = new DpDcDamageStatusServiceImpl();
			String dc_no=request.getParameter("invoiceNo");
			System.out.println("Inside ViewSerialsStatus Action for Damage with Invoice No.== " + dc_no);
			try{
				
				List<DpDcDamageStatusDto> dcNosList = dcNosService.viewSerialsStatus(dc_no);
				DpDcDamageStatusBean dcbean = (DpDcDamageStatusBean) form;
				dcbean.setDcNosList(dcNosList);
				request.setAttribute("dcNosList", dcNosList);
				System.out.println("Size::::::::::::::--------------------------------: "+dcNosList.size());
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return mapping.findForward("initSuccessDcDamageStatus");
	}
	
	//** Methods for Fresh Stock
	
	
	private void setDefaultValueFresh(DpDcDamageStatusBean reverseDCStatusBean,
			HttpServletRequest request,Long lngCrBy) throws Exception {
			DpDcFreshStatusService dcNosService = new DpDcFreshStatusServiceImpl();
			List<DpDcFreshStatusDto> dcNosListFresh = dcNosService.getAllDCListFresh(lngCrBy);

			reverseDCStatusBean.setDcNosListFresh(dcNosListFresh);
		request.setAttribute("dcNosListFresh", dcNosListFresh);
		
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc Submit the Fresh Status
	 */
	public ActionForward saveCancelStatusFresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** saveCancelStatus() method. Called*****************************");
		
		HttpSession session = request.getSession();
		DpDcDamageStatusBean reverseDCStatusBean =(DpDcDamageStatusBean)form;
		String strMessage ="";
		try{
			//setDefaultValue(reverseDCStatusBean, request);
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			logger.info(" distId =="+distId+" circleId == "+circleId );
		
			//Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
			String[] arrDcNos =null;
			if(reverseDCStatusBean.getHidArrDcNos()!=null){
				arrDcNos =reverseDCStatusBean.getHidArrDcNos();
				logger.info(" arrDcNos length == "+arrDcNos.length + " arrDcNos[0] == "+arrDcNos[0]);
			}
			String[] arrDcNosNew =null;
			String strSuccessMsg ="";
			if(arrDcNos!=null){
				arrDcNosNew =arrDcNos[0].split(",");
			}
			if(arrDcNosNew!=null){
				DpDcFreshStatusService dcNosService = new DpDcFreshStatusServiceImpl();
				strSuccessMsg = dcNosService.setDCStatusFresh(arrDcNosNew);
			}
			if(strSuccessMsg.equals("SUCCESS")){
				strMessage ="DC has been Canceled Successfully !!";
			}
			else
			{
				strMessage = strSuccessMsg;
			}
			setDefaultValueFresh(reverseDCStatusBean, request,distId);
			request.setAttribute("hidArrDcNos", "");
			
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage =ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseDCStatusBean.setStrSuccessMsg(strMessage);
			
			
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	    
	public ActionForward viewSerialsStatusFresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			DpDcFreshStatusService dcNosService = new DpDcFreshStatusServiceImpl();
			String dc_no=request.getParameter("invoiceNo");
			System.out.println("viewSerialsStatusFresh--- Action---Fresh---Invoice Number---> " + dc_no);
			try{
				
				List<DpDcFreshStatusDto> dcNosListFresh = dcNosService.viewSerialsStatusFresh(dc_no);
				DpDcDamageStatusBean dcbean = (DpDcDamageStatusBean) form;
				dcbean.setDcNosListFresh(dcNosListFresh);
				request.setAttribute("dcNosList", dcNosListFresh);
				System.out.println("Size:::--------------------------------------->>>>>> "+dcNosListFresh.size());
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return mapping.findForward("initSuccessDcFreshStatus");
	}
	
	//Added by SHAH
	public ActionForward viewSerialsStatusChurn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			DpDcChurnStatusService dcNosService = new DpDcChurnStatusServiceImpl();
			String dc_no=request.getParameter("invoiceNo");
			System.out.println("Inside ViewSerialsStatus Action for Churn with Invoice No.== " + dc_no);
			try{
				
				List<DpDcChurnStatusDto> dcNosList = dcNosService.viewSerialsStatusChurn(dc_no);
				DpDcDamageStatusBean dcbean = (DpDcDamageStatusBean) form;
				dcbean.setDcNosListChurn(dcNosList);
				request.setAttribute("dcNosList", dcNosList);
				System.out.println("Size::::::::::::::--------------------------------: "+dcNosList.size());
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return mapping.findForward("initSuccessDcChurnStatus");
	}
}
