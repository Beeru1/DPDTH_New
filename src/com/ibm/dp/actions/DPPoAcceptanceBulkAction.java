package com.ibm.dp.actions;

import java.io.InputStream;
import java.sql.Connection;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ibm.dp.beans.DPDeliveryChallanAcceptFormBean;
import com.ibm.dp.beans.DPPoAcceptanceBulkBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.service.DPDeliveryChallanAcceptService;
import com.ibm.dp.service.DPPoAcceptanceBulkService;
import com.ibm.dp.service.DPWrongShipmentService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.DPDeliveryChallanAcceptServiceImpl;
import com.ibm.dp.service.impl.DPPoAcceptanceBulkServiceImpl;
import com.ibm.dp.service.impl.DPWrongShipmentServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;




public class DPPoAcceptanceBulkAction extends DispatchAction {
	
	private Logger logger = Logger.getLogger(DPPoAcceptanceBulkAction.class.getName());
	
	private static final String SUCCESS = "success";
	private static final String PRINT_SUCCESS="print_success";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{	
		//logger.info("**********DPPoAcceptanceBulkAction Started*********");
		DPPoAcceptanceBulkBean poaccept = (DPPoAcceptanceBulkBean)form; 
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String dc_status=Constants.ST_ACTION_INTRANSIT;
			List<WrongShipmentDTO> shortShipmentSerialsList = new ArrayList<WrongShipmentDTO>(); 
			List<WrongShipmentDTO> assignedSerialsSerialsList = new ArrayList<WrongShipmentDTO>();	
			DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
			List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId,dc_status);
			poaccept.setHidtkprint("");
			// Set temp DTO.
			WrongShipmentDTO wrongShipmentTemp = new WrongShipmentDTO(); 
			shortShipmentSerialsList.add(wrongShipmentTemp);
			assignedSerialsSerialsList.add(wrongShipmentTemp);
			//poaccept.setShortShipmentSerialsList(shortShipmentSerialsList);
			//poaccept.setAssignedSerialsSerialsList(assignedSerialsSerialsList);
			poaccept.setDcNoList(dcNoList);
			request.setAttribute("dcNoList", dcNoList);
			poaccept.setTransMessage("");
			request.setAttribute("transMessage", "");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS);
		}
		//logger.info("End of DPPoAcceptanceBulkAction********");
		return mapping.findForward(SUCCESS);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		HttpSession session = request.getSession();
		
		DPPoAcceptanceBulkService poService = new DPPoAcceptanceBulkServiceImpl();
		//logger.info("****************uploadExcel**********************");
		DPPoAcceptanceBulkBean dpPoAcceptanceBulkBean = (DPPoAcceptanceBulkBean) form;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		String dc_status=Constants.ST_ACTION_INTRANSIT;
		DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
		List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId,dc_status);
		FormFile file = dpPoAcceptanceBulkBean.getPOfile();
		InputStream myxls = file.getInputStream();
		dpPoAcceptanceBulkBean.setDcNoList(dcNoList);
		request.setAttribute("dcNoList", dcNoList);
		String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
		if(!arr.equalsIgnoreCase("csv"))
		{
			ActionMessages messages = new ActionMessages();
			messages.add("PO_FILE_TYPE_WRONG",new ActionMessage("po.file.type"));
			saveMessages(request, messages);
		}
		else
		{
			List list = poService.uploadExcel(file,dpPoAcceptanceBulkBean.getDeliveryChallanNo() );
			session.removeAttribute("error_file");
			session.setAttribute("error_file",list);
			
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = null;
			boolean checkValidate = true;
			if(list.size() > 0)
			{
				
				Iterator itt = list.iterator();
				while(itt.hasNext())
				{
					dpPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) itt.next();
					if(dpPoAcceptanceBulkDTO.getErr_msg()!= null && !dpPoAcceptanceBulkDTO.getErr_msg().equalsIgnoreCase("SUCCESS"))
					{
						checkValidate = false;
						break;
					} 
				}
				if(checkValidate)
				{
					dpPoAcceptanceBulkBean.setError_flag("false");
					String dcNo = dpPoAcceptanceBulkBean.getDeliveryChallanNo();
					dpPoAcceptanceBulkBean.setDcId(dcNo);
					String invoiceNo = wrongShipment.getInvoiceNoOfDCNO(dcNo);
					dpPoAcceptanceBulkBean.setInvoice_no(invoiceNo);
					
					Integer intCircleID = sessionContext.getCircleId();
					DPPoAcceptanceBulkDTO serialsNoDto= poService.getWrongShortSrNo(invoiceNo, dcNo, intCircleID ,list );
					
					dpPoAcceptanceBulkBean.setTotalSrNoList(serialsNoDto.getTotalSrNoList());
					if(serialsNoDto.getShort_sr_no_list().size() > 0 || serialsNoDto.getExtra_sr_no_list().size() > 0)
					{
						String circleId = String.valueOf(sessionContext.getCircleId());
						StockSummaryReportService stbService = new StockSummaryReportServiceImpl();
						List<ProductMasterDto> productList = stbService.getProductTypeMaster(circleId,dpPoAcceptanceBulkBean.getDeliveryChallanNo());
						
						dpPoAcceptanceBulkBean.setHidtkprint("");
						dpPoAcceptanceBulkBean.setError_flag_wrong_short("true");
						dpPoAcceptanceBulkBean.setShort_sr_no_list(serialsNoDto.getShort_sr_no_list());
						dpPoAcceptanceBulkBean.setExtra_sr_no_list(serialsNoDto.getExtra_sr_no_list());
						dpPoAcceptanceBulkBean.setAvailableSerialList(serialsNoDto.getAvailable_sr_no_list());
						dpPoAcceptanceBulkBean.setProductList(productList);
						request.setAttribute("productList", productList);
					}
					else
					{
						dpPoAcceptanceBulkBean.setError_flag_wrong_short("false");
					}
					return mapping.findForward(SUCCESS);
				}
				else
				{
					dpPoAcceptanceBulkBean.setError_flag("true");
					//dpPoAcceptanceBulkBean.setError_file(list);
					return mapping.findForward(SUCCESS);	
				}
			}
		}
		return mapping.findForward(SUCCESS);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		DPPoAcceptanceBulkBean dpPoAcceptanceBulkBean = (DPPoAcceptanceBulkBean) form;
		return mapping.findForward("errorFile");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward acceptPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{

		logger.info("-----------------acceptDeliveryChallan ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPPoAcceptanceBulkBean dpDCAFormBean = (DPPoAcceptanceBulkBean) form;
		long loginUserId;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			loginUserId = sessionContext.getId();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DC_ACCEPTANCE)) 
			{
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			String invoice_no=dpDCAFormBean.getInvoice_no();
			String[] arrCheckedDC = new String[1];
			arrCheckedDC[0]=invoice_no;
			
			DPDeliveryChallanAcceptService dpDCAService = new DPDeliveryChallanAcceptServiceImpl();
			
			ArrayList<DPDeliveryChallanAcceptDTO> dpDCAList = dpDCAService.reportDeliveryChallan(arrCheckedDC, com.ibm.virtualization.recharge.common.Constants.DC_REPORT_ACCEPT, loginUserId);
			ActionMessages messages = new ActionMessages();
			messages.add("MESSAGE_SENT_SUCCESS",new ActionMessage("po.accept.success"));
			saveMessages(request, messages);
			//dpDCAFormBean.setStrmsg("");
			Long distId = sessionContext.getId();
			DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
			String dc_status="INTRANSIT";
			List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId,dc_status);
			dpDCAFormBean.setDcNoList(dcNoList);
			request.setAttribute("dcNoList", dcNoList);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS);
		}
		return mapping.findForward(SUCCESS);
	
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkErrorDC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String deliveryChallanNo = request.getParameter("deliveryChallanNo");
		DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
		String error_flag = wrongShipment.checkErrorDC(deliveryChallanNo);
		ajaxCall(request, response, error_flag,error_flag);
		
		return null;
	}
	
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,String productUnit,String text)throws Exception{
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write(productUnit);
		out.flush();				
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward acceptPOWrong(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		DPPoAcceptanceBulkBean dpDCAFormBean = (DPPoAcceptanceBulkBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long loginUserId = sessionContext.getId();
		
		String[] extraSerialsArray = request.getParameterValues("wrongSerialsNos");
		String[] arrShort_sr_no = dpDCAFormBean.getArrShort_sr_no();
		String[] arr_available_sr_no = dpDCAFormBean.getArr_available_sr_no();
		String[] arr_total_sr_no =dpDCAFormBean.getArr_total_sr_no();
		

		
		String invoice_no=dpDCAFormBean.getInvoice_no();
		String dc_id = dpDCAFormBean.getDcId();
		
		//String totalSerialNumbers=dpDCAFormBean.getTotalSerialNumbers();
		DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
		boolean flag = wrongShipment.submitWrongShipmentDetails(arr_available_sr_no, arrShort_sr_no, extraSerialsArray, invoice_no, dc_id,String.valueOf(sessionContext.getId()));
		ActionMessages messages = new ActionMessages();
		if(flag)
		{
			messages.add("MESSAGE_SENT_SUCCESS",new ActionMessage("po.accept.success"));
			
			//added  by pratap //commented by NEetika this sms code is already present in DPwrongShipmentDaoimpl assume it was written by Naveen
			
			/**
			 * ********************************ALERT MANAGEMENT********************************
			 */
			/*
			try{
			log.info("*********************Sending SMS alerts***********************");
			
				logger.info("loginUserId :"+loginUserId+"  distId :"+loginUserId);
				String mobileno=null;
				//System.out.println("2222222222 Loghin id  :"+loginUserId);
				SMSDto sMSDto = null;
				Connection connection = DBConnectionManager.getDBConnection();
				sMSDto = SMSUtility.getUserDetails(String.valueOf(loginUserId), connection);
				mobileno=SMSUtility.getMobileNoForSmsOfTSM(Long.valueOf(loginUserId), com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_WRONG_SHIPMENT, connection);
				System.out.println("mobile no  :"+mobileno);
				if(mobileno!=null)
				{
					
				String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_WRONG_SHIPMENT, sMSDto, connection);
				
				if(SMSmessage != null && !SMSmessage.equalsIgnoreCase(""))
				{
					//System.out.println("inserting in DP_SEND_SMS===");
					SMSUtility.saveSMSInDB(connection, new String[] {mobileno}, SMSmessage,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_WRONG_SHIPMENT);
				}
			}
	
				
				
				log.info("*********************Sending SMS alerts ends***********************");
				
				/**
				 * ********************************ALERT MANAGEMENT ENDS********************************
				 */
			//}
			/*catch(Exception exe) //try catch added by Neetika because if some exception in SMS sending occurs functionality should work as normal
			
			{
				exe.printStackTrace();
				logger.info("Exception in sending SMS while PO accept worng ::  "+exe);
				logger.info("Exception in sending SMS while PO accept wrong  ::  "+exe.getMessage());
			}
			//end  by pratap
			
			*/
		}
		else
		{
			messages.add("MESSAGE_SENT_FAIL",new ActionMessage("po.accept.fail"));
		}
		saveMessages(request, messages);
		
		
		Long distId = sessionContext.getId();
		String dc_status=Constants.ST_ACTION_INTRANSIT;
		List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId,dc_status);
		dpDCAFormBean.setDcNoList(dcNoList);
		request.setAttribute("dcNoList", dcNoList);
		/*request.setAttribute("AvailableSerialNum", arr_available_sr_no);
		request.setAttribute("ShortSerialNum", arrShort_sr_no);
		request.setAttribute("invoiceNum", invoice_no);
		request.setAttribute("dcId", dc_id);
		request.setAttribute("totalSerialNumbers", arr_total_sr_no);
		request.setAttribute("distId",sessionContext.getId());
		request.setAttribute("circleId",sessionContext.getCircleId());
		request.setAttribute("productId",dpDCAFormBean.getProductId());*/
		dpDCAFormBean.setHidtkprint("Yes");
		dpDCAFormBean.setProductId(dpDCAFormBean.getProductId());
		dpDCAFormBean.setProdId(dpDCAFormBean.getProductId());
		//dpDCAFormBean.setTotalRcvd(serial);
		session.setAttribute("totalSerialNumbers", arr_total_sr_no.length);
		return mapping.findForward(SUCCESS);
	}
	public ActionForward missingDC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		DPPoAcceptanceBulkBean dpDCAFormBean = (DPPoAcceptanceBulkBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		
		try 
		{
			Long distId = sessionContext.getId();
			String dc_id = dpDCAFormBean.getDeliveryChallanNo();
			DPPoAcceptanceBulkService dpDCAService = new DPPoAcceptanceBulkServiceImpl();
			
			DPPoAcceptanceBulkDTO serialsNoDto= dpDCAService.getShortSrNo(dc_id);
			
			String circleId = String.valueOf(sessionContext.getCircleId());
			StockSummaryReportService stbService = new StockSummaryReportServiceImpl();
			List<ProductMasterDto> productList = stbService.getProductTypeMaster(circleId,dc_id);
			
			dpDCAFormBean.setHidtkprint("");
			//dpDCAFormBean.setError_flag_wrong_short("true");
			dpDCAFormBean.setShort_sr_no_list(serialsNoDto.getShort_sr_no_list());
			dpDCAFormBean.setExtra_sr_no_list(serialsNoDto.getExtra_sr_no_list());
			dpDCAFormBean.setAvailableSerialList(serialsNoDto.getAvailable_sr_no_list());
			//dpDCAFormBean.setProductList(productList);
			//request.setAttribute("productList", productList);
			
			
			
			DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
			boolean flag = wrongShipment.submitWrongShipmentDetails(new String[0], serialsNoDto.getShortSrNo(), new String[0], serialsNoDto.getInv_no(), dc_id,String.valueOf(sessionContext.getId()));
			ActionMessages messages = new ActionMessages();
			System.out.println("FLAG is  "+flag);
			if(flag)
			{
				messages.add("MESSAGE_SENT_SUCCESS",new ActionMessage("po.accept.missing"));
				//added  by pratap
				/**
				 * ********************************ALERT MANAGEMENT********************************
				 */
				/*try{ //commented by neetika kitni baar SMS daaloge table me already done in daoimpl
				log.info("*********************Sending SMS alerts***********************");
				
					System.out.println("distId :"+distId);
				String mobileno=null;
					SMSDto sMSDto = null;
					Connection connection = DBConnectionManager.getDBConnection();
					sMSDto = SMSUtility.getUserDetails(String.valueOf(distId), connection);
					mobileno=SMSUtility.getMobileNoForSmsOfTSM(Long.valueOf(distId), com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_MISSING_DC, connection);
					System.out.println("mobile no  :"+mobileno);
					if(mobileno!=null)
					{
					
					String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_MISSING_DC, sMSDto, connection);
					
					if(SMSmessage != null && !SMSmessage.equalsIgnoreCase(""))
					{
						//System.out.println("inserting in DP_SEND_SMS===");
						SMSUtility.saveSMSInDB(connection, new String[] {mobileno}, SMSmessage,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_MISSING_DC);
					}
					}
		
					
					
					log.info("*********************Sending SMS alerts ends***********************");
					
					/**
					 * ********************************ALERT MANAGEMENT ENDS********************************
					 */
				
				//end  by pratap
			//	}
				/*	catch(Exception exe) //try catch added by Neetika because if some exception in SMS sending occurs functionality should work as normal
					
					{
						exe.printStackTrace();
						logger.info("Exception in sending SMS while missing dc ::  "+exe);
						logger.info("Exception in sending SMS while missing dc ::  "+exe.getMessage());
					} */
			}
			else
			{
				messages.add("MESSAGE_SENT_FAIL",new ActionMessage("po.accept.fail"));
			}
			saveMessages(request, messages);
			
			
			 distId = sessionContext.getId();
			String dc_status=Constants.ST_ACTION_INTRANSIT;
			List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId,dc_status);
			dpDCAFormBean.setDcNoList(dcNoList);
			request.setAttribute("dcNoList", dcNoList);
			
			dpDCAFormBean.setHidtkprint("");
			/*dpDCAFormBean.setProductId(dpDCAFormBean.getProductId());
			dpDCAFormBean.setProdId(dpDCAFormBean.getProductId());*/
			//dpDCAFormBean.setTotalRcvd(serial);
			//session.setAttribute("totalSerialNumbers", arr_total_sr_no.length);
			
			
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS);
		}
		
		
		return mapping.findForward(SUCCESS);
	}
	
}



