package com.ibm.dp.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPViewHierarchyFormBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.beans.TransferPendingSTBBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.TransferPendingSTBDto;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.TransferPendingSTBService;
import com.ibm.dp.service.impl.DPViewHierarchyReportServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dp.service.impl.TransferPendingSTBServiceImpl;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;

public class TransferPendingSTBAction  extends DispatchAction {

	private static Logger logger = Logger.getLogger(TransferPendingSTBAction.class
			.getName());
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// Changes done for Authentication 27/09/2013 by Divya
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_ADD_CIRCLE))
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;
		//Setting Circle List in Form
		TransferPendingSTBService transferService = TransferPendingSTBServiceImpl.getInstance();
		List<CircleDto> circleList =transferService.getAllCircleList();
		formBean.setCircleList(circleList);
		formBean.setFromTSMList(null);
		formBean.setToTSMList(null);
		List<DPReverseCollectionDto>  reverseCollectionList = transferService.getCollectionTypeMaster();
					
		formBean.setReverseCollectionList(reverseCollectionList);
		request.setAttribute("reverseCollectionList", reverseCollectionList);
		return mapping.findForward("init");

	}
	
	public ActionForward getTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			List accountList = revLogReportService.getviewhierarchyTsmAccountDetails(roleId);
			formBean.setFromTSMList(accountList);
			formBean.setToTSMList(accountList);
			request.setAttribute("TsmList",accountList);
			logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) 
			{
				DPViewHierarchyFormBean statusReportBean = (DPViewHierarchyFormBean) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", statusReportBean.getAccountName());
				optionElement.addAttribute("value", statusReportBean.getAccountId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		

		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	
	public ActionForward getFromTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			//List accountList = revLogReportService.getviewhierarchyFromTsmAccountDetails(roleId);
			/* Added by Parnika */
			//List accountList = revLogReportService.getviewhierarchyFromTsmAccountDetails(roleId);
			List accountList = revLogReportService.getDepositTypeFromTsmAccountDetails(roleId);
			/*End of changes by Parnika */
			formBean.setFromTSMList(accountList);
			formBean.setToTSMList(accountList);
			request.setAttribute("TsmList",accountList);
			logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) 
			{
				DPViewHierarchyFormBean statusReportBean = (DPViewHierarchyFormBean) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", statusReportBean.getAccountName());
				optionElement.addAttribute("value", statusReportBean.getAccountId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		

		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	
	public ActionForward getFromDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				
				try 
				{
					//	Get account Id form session.
					TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
					
					List accountList = revLogReportService.getRevLogFromDistAccountDetails(roleId , circleId);
					formBean.setFromDistList(accountList);
					logger.info("Account list size : " + accountList.size());
					
								
					// Ajax start
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					Iterator iter = accountList.iterator();
					while (iter.hasNext()) 
					{
						StockSummaryReportBean statusReportBean = (StockSummaryReportBean) iter.next();
						optionElement = root.addElement("option");
						optionElement.addAttribute("text", statusReportBean.getDistAccName());
						optionElement.addAttribute("value", statusReportBean.getDistAccId());
					}
				
					// For ajax
					response.setHeader("Cache-Control", "No-Cache");
					response.setContentType("text/xml");
					PrintWriter out = response.getWriter();
					OutputFormat outputFormat = OutputFormat.createCompactFormat();
					XMLWriter writer = new XMLWriter(out);
					writer.write(document);
					writer.flush();
					out.flush();
					// End for ajax
				
				
				} catch (Exception ex) 
				{
					ex.printStackTrace();
				}
				
				return null;
			}

	public ActionForward getToDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				
				try 
				{
					//	Get account Id form session.
					TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
					
					List accountList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
					formBean.setFromDistList(accountList);
					logger.info("Account list size : " + accountList.size());
					
								
					// Ajax start
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					Iterator iter = accountList.iterator();
					while (iter.hasNext()) 
					{
						StockSummaryReportBean statusReportBean = (StockSummaryReportBean) iter.next();
						optionElement = root.addElement("option");
						optionElement.addAttribute("text", statusReportBean.getDistAccName());
						optionElement.addAttribute("value", statusReportBean.getDistAccId());
					}
				
					// For ajax
					response.setHeader("Cache-Control", "No-Cache");
					response.setContentType("text/xml");
					PrintWriter out = response.getWriter();
					OutputFormat outputFormat = OutputFormat.createCompactFormat();
					XMLWriter writer = new XMLWriter(out);
					writer.write(document);
					writer.flush();
					out.flush();
					// End for ajax
				
				
				} catch (Exception ex) 
				{
					ex.printStackTrace();
				}
				
				return null;
			}

	
	
	
	
	public ActionForward getCollectionData(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String accountID = String.valueOf(sessionContext.getId());
		
		TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;		
		TransferPendingSTBService transferService = TransferPendingSTBServiceImpl.getInstance();
		//String distId = formBean.getFromDistId();
		String distId = request.getParameter("distId");
		String collectionId = request.getParameter("collectionId");
		logger.info("in getCollectionData collection ID == "+ collectionId);
		List<CircleDto> circleList = new ArrayList<CircleDto>(); 
		String circleId = formBean.getFromCircleId();
		String fromTSM = formBean.getFromTsmId();
		List<TransferPendingSTBDto> collectionList =new ArrayList<TransferPendingSTBDto>();
		
		collectionList =transferService.getCollectionData(distId,collectionId);
		formBean.setCircleList(circleList);
		formBean.setFromCircleId(circleId);
		formBean.setFromTsmId(fromTSM);
		formBean.setCollectionList(collectionList);
		formBean.setSuccessMessage("");
		
		// Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		Iterator iter = collectionList.iterator();
		while (iter.hasNext()) 
		{
			TransferPendingSTBDto transferDto = (TransferPendingSTBDto) iter.next();
			optionElement = root.addElement("option");
			optionElement.addAttribute("CollectionType", transferDto.getCollectionType());
			optionElement.addAttribute("customerId", transferDto.getCustomerId());
			
			optionElement.addAttribute("SerialNo", transferDto.getSerialNo());
			optionElement.addAttribute("Product", transferDto.getProduct());
			optionElement.addAttribute("SerialNoNew", transferDto.getSerialNoNew());
			optionElement.addAttribute("ProductNew", transferDto.getProductNew());
			optionElement.addAttribute("InvenChangeDate", transferDto.getInvChangeDate());
			optionElement.addAttribute("Ageing", transferDto.getAgeing());
			optionElement.addAttribute("DefectType", transferDto.getDefectType());
			optionElement.addAttribute("CollectionDate", transferDto.getCollectionDate());
			
			optionElement.addAttribute("ProductId", transferDto.getProductId());
			optionElement.addAttribute("CollectionId", transferDto.getCollectionId());
			optionElement.addAttribute("DefectId", transferDto.getDefectId());
			optionElement.addAttribute("CreatedBy", accountID);
			
			optionElement.addAttribute("ReqId", transferDto.getReqId());
			optionElement.addAttribute("Flag", transferDto.getFlag());
			//optionElement.addAttribute("CreatedOn", transferDto.getCreatedOn());
		}
	
		// For ajax
		response.setHeader("Cache-Control", "No-Cache");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		
		return null;

	}
	
	/*public ActionForward getCollectionData(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String accountID = String.valueOf(sessionContext.getId());
		
		TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;		
		TransferPendingSTBService transferService = TransferPendingSTBServiceImpl.getInstance();
		//String distId = formBean.getFromDistId();
		String distId = request.getParameter("distId");
		String collectionId = request.getParameter("collectionId");
		logger.info("in getCollectionData collection ID == "+ collectionId);
		List<CircleDto> circleList = new ArrayList<CircleDto>(); 
		String circleId = formBean.getFromCircleId();
		String fromTSM = formBean.getFromTsmId();
		List<TransferPendingSTBDto> collectionList =new ArrayList<TransferPendingSTBDto>();
		
		collectionList =transferService.getCollectionData(distId);
		formBean.setCircleList(circleList);
		formBean.setFromCircleId(circleId);
		formBean.setFromTsmId(fromTSM);
		formBean.setCollectionList(collectionList);
		formBean.setSuccessMessage("");
		
		// Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		Iterator iter = collectionList.iterator();
		while (iter.hasNext()) 
		{
			TransferPendingSTBDto transferDto = (TransferPendingSTBDto) iter.next();
			optionElement = root.addElement("option");
			optionElement.addAttribute("CollectionType", transferDto.getCollectionType());
			optionElement.addAttribute("SerialNo", transferDto.getSerialNo());
			optionElement.addAttribute("Product", transferDto.getProduct());
			optionElement.addAttribute("InvenChangeDate", transferDto.getInvChangeDate());
			optionElement.addAttribute("Ageing", transferDto.getAgeing());
			optionElement.addAttribute("DefectType", transferDto.getDefectType());
			optionElement.addAttribute("CollectionDate", transferDto.getCollectionDate());
			
			optionElement.addAttribute("ProductId", transferDto.getProductId());
			optionElement.addAttribute("CollectionId", transferDto.getCollectionId());
			optionElement.addAttribute("DefectId", transferDto.getDefectId());
			optionElement.addAttribute("CreatedBy", accountID);
			
			optionElement.addAttribute("ReqId", transferDto.getReqId());
			optionElement.addAttribute("Flag", transferDto.getFlag());
			//optionElement.addAttribute("CreatedOn", transferDto.getCreatedOn());
		}
	
		// For ajax
		response.setHeader("Cache-Control", "No-Cache");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		
		return null;

	}*/
	
	public ActionForward transferStock(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TransferPendingSTBBean formBean =(TransferPendingSTBBean)form ;		
		TransferPendingSTBService transferService = TransferPendingSTBServiceImpl.getInstance();
		TransferPendingSTBDto transferDto = new TransferPendingSTBDto();
		AuthenticationService userService = new AuthenticationServiceImpl();
		
		//String distId = formBean.getFromDistId();
		String distId = request.getParameter("distId");
		String fromCircleId = formBean.getFromCircleId();
		String toCircleId = formBean.getToCircleId();
		String fromTsmId = formBean.getFromTsmId();
		String toTsmId = formBean.getToTsmId();
		String fromDistId = formBean.getFromDistId();
		String toDistId = formBean.getToDistId();
		
		String fromDistName = request.getParameter("fromDistName");
		String toDistName = request.getParameter("toDistName");
		
		String serialNo ="";
		String productId ="";
		String reqId ="";
		String flag ="";
		int flagInt=0;
		String invChangeDate ="";
		String collectionId ="";
		String defectId ="";
		String collectiondate ="";
		String collectionType ="";
		String createdBy ="";
		String product ="";
		String ageing ="";
		String defectType ="";
		String successMessage = "";
		Connection connection = null;
		try{
			String strMessage="FAILURE";
		String selectedDetails = request.getParameter("selectedSerialNo");
		String[] selectedDetailsArr = selectedDetails.split(";");
		connection = DBConnectionManager.getDBConnection();
		connection.setAutoCommit(false);
		
		for (String dataDetails : selectedDetailsArr) {
			String[] dataDetailsArr = dataDetails.split("#");		
				serialNo = dataDetailsArr[0];
				productId = dataDetailsArr[1];
				reqId = dataDetailsArr[2];
				flag = dataDetailsArr[3];
				invChangeDate = dataDetailsArr[4];
				collectionId = dataDetailsArr[5];
				defectId = dataDetailsArr[6];
				collectiondate = dataDetailsArr[7];   
				collectionType = dataDetailsArr[8];
				createdBy = dataDetailsArr[9];
				defectType = dataDetailsArr[10];
				ageing = dataDetailsArr[11];
				product = dataDetailsArr[12];
		transferDto.setInvChangeDate(invChangeDate);
		transferDto.setCollectionId(collectionId);
		transferDto.setDefectId(defectId);
		transferDto.setCollectionDate(collectiondate);
		transferDto.setCollectionType(collectionType);
		transferDto.setCreatedBy(createdBy);
		transferDto.setProduct(product);
		transferDto.setAgeing(ageing);
		transferDto.setDefectType(defectType);
		
		transferDto.setSerialNo(serialNo);
		transferDto.setProductId(productId);
		transferDto.setReqId(reqId);
		transferDto.setFlag(flag);
		
		transferDto.setFromCircleId(fromCircleId);
		transferDto.setToCircleId(toCircleId);
		transferDto.setFromTsmId(fromTsmId);
		transferDto.setToTsmId(toTsmId);
		transferDto.setFromDistId(fromDistId);
		transferDto.setToDistId(toDistId);
		
		flagInt = Integer.parseInt(flag);
		if(flagInt==1){
			transferService.updateRevInventory(transferDto, connection);
			successMessage ="Selected STB's transferred from "+fromDistName+" to "+toDistName+" Successfully";
			strMessage="SUCCESS";
		}
		else if(flagInt==2){
			transferService.updateRevInventory(transferDto, connection);
			transferService.updateRevStockInventory(transferDto, connection);
			successMessage ="Selected STB's transferred from "+fromDistName+" to "+toDistName+" Successfully";
			strMessage="SUCCESS";
		}
		else if(flagInt==3){
			transferService.updateRevStockInventory(transferDto, connection);
			successMessage ="Selected STB's transferred from "+fromDistName+" to "+toDistName+" Successfully";
			strMessage="SUCCESS";
		}else if(flagInt==4){
			transferService.updateRevChurnInventory(transferDto, connection);
			successMessage ="Selected STB's transferred from "+fromDistName+" to "+toDistName+" Successfully";
			strMessage="SUCCESS";
		}
		//strMessage="FAILURE";
		transferService.insertRevPendingTransfer(transferDto, connection);
		
			
		List<CircleDto> circleList =transferService.getAllCircleList();
		formBean.setCircleList(circleList);
		formBean.setFromCircleId("");
		formBean.setToCircleId("");
		List<DPReverseCollectionDto>  reverseCollectionList = transferService.getCollectionTypeMaster();
		
		formBean.setReverseCollectionList(reverseCollectionList);
		request.setAttribute("reverseCollectionList", reverseCollectionList);
		}
		String fromdistMailId= userService.getEmailId(Long.parseLong(fromDistId));
		String todistMailId=  userService.getEmailId(Long.parseLong(toDistId));
		String fromDistLoginName = fromDistName;
		String toDistLoginName = toDistName;
		logger.info("From Dist Email Id == "+ fromdistMailId+"and  fromDistLoginName == "+fromDistLoginName+ " and toDistLoginName == "+toDistLoginName+"  and To Dist Mail id == "+todistMailId);
		if(!strMessage.equals("FAILURE")){
			successMessage= "Selected STB's transferred from "+fromDistName+" to "+toDistName+" .";
			String mailMsg = "";
			try{
			mailMsg = stockTransferMailing(fromDistLoginName,toDistLoginName,fromdistMailId,todistMailId,selectedDetailsArr);
			}
			catch(Exception ex) 
			{
				logger.error("Exception while sending mail to distributors : "+ex.getMessage());
			}
			
			if(mailMsg.equals("Success")){
				successMessage +=" Notifications have been sent to both distributors!!";
			}else{
				successMessage +=" Notifications could not be sent to both distributors!!";
			}
		}else{
			successMessage= "Error in transferring stock";
		}
		connection.commit();
		
		
		}catch (Exception ex) 
		{
			connection.rollback();
			successMessage="Error in transferring stock";
			ex.printStackTrace();
			List<CircleDto> circleList =transferService.getAllCircleList();
			formBean.setCircleList(circleList);
			List<DPReverseCollectionDto>  reverseCollectionList = transferService.getCollectionTypeMaster();
			
			formBean.setReverseCollectionList(reverseCollectionList);
			request.setAttribute("reverseCollectionList", reverseCollectionList);
			return mapping.findForward("init");
		} finally {
			formBean.setSuccessMessage(successMessage);
			DBConnectionManager.releaseResources(connection);
		}
		return mapping.findForward("init");
	}
/**
 * Added by Shilpa on 24-07-12
 * @param fromDistName
 * @param toDistName
 * @param fromDistEmail
 * @param toDistEmail
 * @param arrProdName
 * @param arrSrNo
 * @return
 * @throws VirtualizationServiceException
 */
	public String stockTransferMailing(String fromDistName,String toDistName, String fromDistEmail,String toDistEmail,String[] arrTransferDetail)
	throws VirtualizationServiceException {
	StringBuffer sbMessageFrom = new StringBuffer();
	StringBuffer sbMessageTo = new StringBuffer();
	String strMessage = "Success";
	String txtMessageFrom = null;
	String txtMessageTo = null;
	String strSubject = "Stock Transfer Detail";
	//Message Send to "From Distributor"
	sbMessageFrom.append("Dear " + fromDistName +", \n\n");
	sbMessageFrom.append("Following STBs has been transferred from "+ fromDistName + " to "+ toDistName+" by Admin. In case of any queries or concern, kindly contact DP helpdesk. \n\n");
	sbMessageFrom.append("Collection Type			STB ID		Product Type \n");
	sbMessageFrom.append("---------------------------------------------------------------------------- \n");
//	Message Send To, "To Distributor"
	sbMessageTo.append("Dear " +toDistName+", \n\n");
	sbMessageTo.append("Following STBs has been transferred from "+ fromDistName + " to "+ toDistName+" by Admin. In case of any queries or concern, kindly contact DP helpdesk. \n\n");
	sbMessageTo.append("Collection Type			STB ID		Product Type \n");
	sbMessageTo.append("---------------------------------------------------------------------------- \n");
	
	String prodName="";
	String srNo ="";
	String collectionType="";
	String[] dtlArray =null;
	int i =0;
	for(int count=0;count<arrTransferDetail.length;count++){
		dtlArray =arrTransferDetail[count].split("#");
		prodName = dtlArray[12];
		srNo=dtlArray[0];
		collectionType =dtlArray[8];
		logger.info("in MAILINg Function ***** Sr no == "+srNo + " and product namae == "+prodName +" and Collection Name == "+collectionType);
		sbMessageFrom.append(collectionType +"		"+ srNo + "		"+prodName +" \n");
		sbMessageTo.append(collectionType +"		"+ srNo + "		"+prodName +" \n");
		
	}

	sbMessageFrom.append(
		"\n/** This is an Auto generated email.Please do not reply to this.**/");
	sbMessageTo.append(
	"\n/** This is an Auto generated email.Please do not reply to this.**/");
	txtMessageFrom = sbMessageFrom.toString();
	logger.info("txtMessageFrom : " + txtMessageFrom);
	
	txtMessageTo = sbMessageTo.toString();
	logger.info("txtMessageTo : " + txtMessageTo);
	
	//String strHost = PropertyReader.getValue("LOGIN.SMTP"); 
	
	String strHost = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.LOGIN_SMTP);
	logger.info("SMTP HOST : " + strHost);
	String strFromEmail = PropertyReader.getValue("LOGIN.EMAIL");
	logger.info("From EMAIL : " + strFromEmail);
	
	try {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", strHost);
		Session ses = Session.getDefaultInstance(prop, null);
		
		//****** Sending message to "From Distributor" ****
		MimeMessage msg = new MimeMessage(ses);
		msg.setFrom(new InternetAddress(strFromEmail));
		msg.addRecipient(
			Message.RecipientType.TO,
			new InternetAddress(fromDistEmail));
		
		msg.setSubject(strSubject);
		msg.setText(txtMessageFrom);
		msg.setSentDate(new Date());
		
		//***** Sending message to "To Distributor" **** 
		MimeMessage msgTo = new MimeMessage(ses);
		msgTo.setFrom(new InternetAddress(strFromEmail));
		
		msgTo.addRecipient(
				Message.RecipientType.TO,
				new InternetAddress(toDistEmail));
		msgTo.setSubject(strSubject);
		msgTo.setText(txtMessageTo);
		msgTo.setSentDate(new Date());
		try
		{
		Transport.send(msg);
		Transport.send(msgTo);
		}
		catch(Exception ex) 
		{
			logger.error("Exception while sending mail to distributors : "+ex.getMessage());
		}
		
	} catch (Exception e) {
		strMessage = "Error";
		e.printStackTrace();
		logger.error(
			"Exception occured in stockTransferMailing():" + e.getMessage());
		throw new VirtualizationServiceException(e.getMessage());

	}
	return strMessage;
}
}
