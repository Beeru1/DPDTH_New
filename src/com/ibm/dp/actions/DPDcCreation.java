package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DpDcCreationBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.service.DPDcCreationService;
import com.ibm.dp.service.impl.DPDcCreationServiceImpl;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class DPDcCreation extends DispatchAction{
	private static Logger logger = Logger.getLogger(DPDcCreation.class.getName());
	
	private static final String INIT_SUCCESS = "initSuccess";
	private static final String STATUS_ERROR = "error";
	private static final String REGEX_COMMA = ",";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc initial information which fetch the category from the table and display in category dropdown [infront end screen].
	 */
	public ActionForward initDcCreation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			logger.info("***************************** initDcCreation() method. Called*****************************");
			ActionErrors errors = new ActionErrors();
		// Get account Id form session.
		DpDcCreationBean reverseCollectionBean = (DpDcCreationBean)form;
		
		try
		{
			
			setDefaultValue(reverseCollectionBean, request);
			request.setAttribute("hidstrDcNo", "");
			 //Security Observation Implementation by prata on 22 july 2013
			saveToken(request);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		return mapping.findForward(INIT_SUCCESS);

			
	}

	private void setDefaultValue(DpDcCreationBean reverseCollectionBean,
			HttpServletRequest request) throws Exception {
			DPDcCreationService dcCollectionService = new DPDcCreationServiceImpl();
			List<DpDcCreationDto> dcCollectionList = dcCollectionService.getCollectionTypeMaster();

		reverseCollectionBean.setDcCollectionList(dcCollectionList);
		request.setAttribute("dcCollectionList", dcCollectionList);
		
	}
	
	public ActionForward viewStockCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** viewStockCollection() method. Called*****************************");
		HttpSession session = request.getSession();
		DpDcCreationBean reverseCollectionBean = (DpDcCreationBean)form;
		ActionErrors errors = new ActionErrors();
		String strMessage = "";
		try
		{
			//Security Observation Implementation by prata on 22 july 2013
			logger.info("Saving token in viewStockCollection method ::::::");
			saveToken(request);
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			setDefaultValue(reverseCollectionBean, request);
			logger.info("reverseCollectionBean.getCollectionId() ==="+reverseCollectionBean.getCollectionId());
			String strCollectionId = "0";   //reverseCollectionBean.getCollectionId(); 
			DPDcCreationService dcCollectionService =  new DPDcCreationServiceImpl(); 
			List<DpDcReverseStockInventory>  dcStockCollectionList = dcCollectionService.getStockCollectionList(strCollectionId,distId);
						
			reverseCollectionBean.setReverseStockInventoryList(dcStockCollectionList);
			request.setAttribute("reverseStockInventoryList", dcStockCollectionList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			strMessage = ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseCollectionBean.setMessage(strMessage);
			request.setAttribute("hidstrDcNo", "");
		}
		
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward performDcCreation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** performDcCreation() method. Called*****************************");
		
		HttpSession session = request.getSession();
		DpDcCreationBean reverseCollectionBean = (DpDcCreationBean)form;
		ActionErrors errors = new ActionErrors();
		String strMessage ="";
		String status=null;
		try{
			 //Security Observation Implementation by prata on 22 july 2013
			logger.info("validating token in performDcCreation method ::::::");
			if(!isTokenValid(request))
			 {
				logger.info("inside if block  in performDcCreation method ::::::");
				return mapping.findForward("invalidoperation");
			  }
			
			setDefaultValue(reverseCollectionBean, request);
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			logger.info(" distId =="+distId+" circleId == "+circleId );
		
			Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
			String[] arrProdId = commaPattern.split(reverseCollectionBean.getHidStrProductId()[0]);
			String[] arrSrNo = commaPattern.split(reverseCollectionBean.getHidStrSerialNo()[0]);
		
			String[] arrCustId = commaPattern.split(reverseCollectionBean.getHidStrCustId()[0]);
			String[] arrReqId = commaPattern.split(reverseCollectionBean.getHidStrReqId()[0]);
			String[] arrInvChange= commaPattern.split(reverseCollectionBean.getHidStrInvChangeDate()[0]);
			
			String strRemarks = reverseCollectionBean.getStrRemarks();
			String agencyName=reverseCollectionBean.getCourierAgency();
			String docketNumber=reverseCollectionBean.getDocketNumber();
				
			logger.info(" arrProdId length =="+arrProdId[0]+" arrSrNo length == "+arrSrNo.length + " remarks == "+strRemarks);
			logger.info("In performDcCreation of DP DC CREATION -- Agency Name == "+agencyName +"  and Docket Num == "+docketNumber);
	
			ArrayList<DpDcReverseStockInventory> dcCreationDtoList = new ArrayList<DpDcReverseStockInventory>();
			String strDcNo = "";
			for(int count=0;count<arrProdId.length;count++){
				DpDcReverseStockInventory dcCreationDto = new DpDcReverseStockInventory();
				logger.info(" productIdListItr.next() =="+arrProdId[count] + "  sr no == "+arrSrNo[count]);
				dcCreationDto.setProdId(arrProdId[count]);
				dcCreationDto.setStrSerialNo(arrSrNo[count]);
				dcCreationDto.setStrNewRemarks(strRemarks);
				dcCreationDto.setStrCustomerId(arrCustId[count]);
				dcCreationDto.setStrReqId(arrReqId[count]);
				dcCreationDto.setStrInvChangeDate(arrInvChange[count]);
				dcCreationDtoList.add(dcCreationDto);
				
				
				
			}
	
			ListIterator<DpDcReverseStockInventory> dcCreationDtoListItr = dcCreationDtoList.listIterator();
		
			DPDcCreationService dcCollectionStockService=new DPDcCreationServiceImpl();
			strDcNo=dcCollectionStockService.insertStockCollection(dcCreationDtoListItr,distId,circleId,strRemarks,agencyName,docketNumber);

			//Updated by Shilpa for Critical Changes BFR 14 
			
			//strMessage = "Delivery Challan have been created successfully : "+strDcNo;
			if(agencyName==null || agencyName.trim().equals("") || docketNumber==null || docketNumber.trim().equals("")){
				strMessage = "DC "+strDcNo+"  will not be sent to Warehouse till Courier Agency and Docket Number is provided in DC Status Screen.";
			}else{
				strMessage = "Delivery Challan have been created successfully : "+strDcNo;
			}
			//added  by pratap
			/**
			 * ********************************ALERT MANAGEMENT********************************
			 */
			String message1=null;
			String message=null;
			log.info("*********************Sending SMS alerts***********************");
			if(dcCreationDtoList!=null)
			{
				try
				{
				Connection connection = DBConnectionManager.getDBConnection();
				String mobileno=null;
				//mobileno=SMSUtility.getMobileNoForSmsOfTSM(Long.valueOf(distId), com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, connection);
				/*mobileno=SMSUtility.getMobileNoForSms(Long.valueOf(distId),com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, connection);
				String stbTypeArr[]=null;
				String stbType=null;
				DpDcReverseStockInventory dcCreationDto=null;
				ListIterator<DpDcReverseStockInventory> dcCreationDtoi = dcCreationDtoList.listIterator();
				int arr=0;
				while(dcCreationDtoi.hasNext())
				{
				dcCreationDto=dcCreationDtoi.next();
				System.out.println(" distId :"+distId);
				SMSDto sMSDto = null;
				sMSDto = SMSUtility.getUserDetails(String.valueOf(distId), connection);
				sMSDto.setDcNo(strDcNo);
				sMSDto.setPrdId(dcCreationDto.getProdId());
				SMSUtility.getStbType(sMSDto,connection);
				stbTypeArr=reverseCollectionBean.getHidStrProductId();
				String stbTypeIds[]=stbTypeArr[0].split(",");
				for(int i=0;i<stbTypeArr.length;i++)System.out.println("product id  :"+stbTypeArr[i]);
				stbType=stbTypeIds[arr];
				arr++;
				String stbName=SMSUtility.getProductName(stbType,connection);
				if(agencyName==null || agencyName.trim().equals("") 
						|| docketNumber==null || docketNumber.trim().equals(""))
				{
					status = com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_DRAFT_STATUS;
				}
				else
				{
					status =com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS;
				}
				sMSDto.setStbType(stbName);
				if(status ==com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS)
					status=com.ibm.dp.common.Constants.PUSH_DC_TO_READY_DISPATCH_STATUS;
				sMSDto.setStbStatus(status);
				if(mobileno!=null)
				{
					String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto, connection);
					if(SMSmessage != null && !SMSmessage.equalsIgnoreCase(""))
					{
						SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber(),sMSDto.getParentMobileNumber2()}, SMSmessage,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION);
					}
				}
				}*/
				
				//mobileno=SMSUtility.getMobileNoForSms(Long.valueOf(distId),com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, connection);
				String stbTypeArr[]=null;
				String stbType=null;
				DpDcReverseStockInventory dcCreationDto=null;
				ListIterator<DpDcReverseStockInventory> dcCreationDtoi = dcCreationDtoList.listIterator();
				int arr=0;
				SMSDto sMSDto = null;
				while(dcCreationDtoi.hasNext())
				{
				dcCreationDto=dcCreationDtoi.next();
				System.out.println(" distId :"+distId);
				sMSDto = null;
				//sMSDto = SMSUtility.getUserDetails(String.valueOf(distId), connection);
				sMSDto = SMSUtility.getUserDetailsasPerTransaction(Long.toString(distId), connection,com.ibm.virtualization.recharge.common.Constants.DISTRIBUTOR_TYPE_DEPOSIT);
				sMSDto.setDcNo(strDcNo);
				sMSDto.setPrdId(dcCreationDto.getProdId());
				SMSUtility.getStbType(sMSDto,connection);
				stbTypeArr=reverseCollectionBean.getHidStrProductId();
				String stbTypeIds[]=stbTypeArr[0].split(",");
				for(int i=0;i<stbTypeArr.length;i++)System.out.println("product id  :"+stbTypeArr[i]);
				stbType=stbTypeIds[arr];
				arr++;
				String stbName=SMSUtility.getProductName(stbType,connection);
				if(agencyName==null || agencyName.trim().equals("") 
						|| docketNumber==null || docketNumber.trim().equals(""))
				{
					status = com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_DRAFT_STATUS;
				}
				else
				{
					status =com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS;
				}
				sMSDto.setStbType(stbName);
				if(status ==com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS)
					status=com.ibm.dp.common.Constants.PUSH_DC_TO_READY_DISPATCH_STATUS;
				sMSDto.setStbStatus(status);
				message1="";
				message= "";
				sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION);				
				message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto, connection,sMSDto.getTsmID());
				sMSDto.setMessage(message1);
				if(message1!=null && !message1.equalsIgnoreCase(""))
				{
				message=SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto);
				}
				
				}
				if(message != null && !message.equalsIgnoreCase(""))
				{
					SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber()}, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION);
				}
				}
				catch(Exception ex)
				{
					logger.info("Exception occured : while sending SMS ========="+ex.getMessage());
					ex.printStackTrace();
				}
			}
				log.info("*********************Sending SMS alerts ends***********************");
				
				/**
				 * ********************************ALERT MANAGEMENT ENDS********************************
				 */
			//end  by pratap
			String strCollectionId = "0";   //reverseCollectionBean.getCollectionId(); 
			DPDcCreationService dcCollectionService =  new DPDcCreationServiceImpl(); 
			List<DpDcReverseStockInventory>  dcStockCollectionList = dcCollectionService.getStockCollectionList(strCollectionId,distId);
						
			reverseCollectionBean.setReverseStockInventoryList(dcStockCollectionList);
			request.setAttribute("reverseStockInventoryList", dcStockCollectionList);
			reverseCollectionBean.setHidstrDcNo(strDcNo);
			
		}catch(Exception ex){
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			strMessage =ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseCollectionBean.setMessage(strMessage);
			reverseCollectionBean.setStrRemarks("");
			reverseCollectionBean.setCourierAgency("");
			reverseCollectionBean.setDocketNumber("");
			reverseCollectionBean.setCollectionId("0");
			
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward checkERR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		DpDcCreationBean reverseCollectionBean = (DpDcCreationBean)form;
		ActionErrors errors = new ActionErrors();
		String strERR ="";
		String strSerialNo = request.getParameter("strSerialNo");
		DPDcCreationService dcCollectionService =  new DPDcCreationServiceImpl(); 
		strERR= dcCollectionService.checkERR(strSerialNo);
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		logger.info("validating token in checkERR method ::::::");
		if(!isTokenValid(request))
		 {
			logger.info("inside if block  in checkERR method ::::::");
			return mapping.findForward("invalidoperation");
		  }
			optionElement = root.addElement("option");
			optionElement.addAttribute("strERR", strERR);
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();			
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		return null;
	}
	
	
}
