package com.ibm.dp.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.regex.Pattern;

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
import com.ibm.dp.beans.InterSsdTransferAdminBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ChurnDCGenerationDTO;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.InterSsdTransferAdminDto;
import com.ibm.dp.service.ChurnDCGenerationService;
import com.ibm.dp.service.DPDcCreationService;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.dp.service.InterSsdTransferAdminService;
import com.ibm.dp.service.impl.ChurnDCGenerationServiceImpl;
import com.ibm.dp.service.impl.DPDcCreationServiceImpl;
import com.ibm.dp.service.impl.DPViewHierarchyReportServiceImpl;
import com.ibm.dp.service.impl.InterSsdTransferAdminServiceImpl;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;

public class InterSsdTransferAdminAction  extends DispatchAction {

	private static Logger logger = Logger.getLogger(InterSsdTransferAdminAction.class
			.getName());
	private static final String STATUS_ERROR = "error";
	private static final String REGEX_COMMA = ",";
	public ActionForward init(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
		//Setting Circle List in Form
		InterSsdTransferAdminService interssdService = new InterSsdTransferAdminServiceImpl();
		//List<CircleDto> circleList =interssdService.getAllCircleList();
		List<List<CircleDto>> getInitData  = interssdService.getInitData();
		
		List<CircleDto> circleList = getInitData.get(0);
		List<CircleDto> busCatList = getInitData.get(1);
		
		UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		Iterator<CircleDto> catIter=busCatList.iterator();
		
		
		formBean.setCircleList(circleList);
		formBean.setBusCatList(busCatList);
		formBean.setFromTSMList(null);
		formBean.setToTSMList(null);
		return mapping.findForward("init");

	}
	
	public ActionForward getTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
			String levelId = request.getParameter("levelId");
			/* Added By Parnika for Business Category-wise TSM */
			String busCategory = request.getParameter("busCat");
			int busCat = Integer.parseInt(busCategory);
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);

			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			List accountList = revLogReportService.getCategoryWiseTsmAccountDetails(roleId,busCat);
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
			logger.error("Exception in getTsmList method of InterSsdTransferAdminAction::"+ex);
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
			InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
			String levelId = request.getParameter("levelId");
			/* Added By Parnika for Business Category-wise TSM */
			String busCategory = request.getParameter("busCat");
			
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			int busCat = Integer.parseInt(busCategory);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			//List accountList = revLogReportService.getviewhierarchyFromTsmAccountDetails(roleId);
			
			List accountList = revLogReportService.getCategoryWiseFromTsmAccountDetails(roleId,busCat);
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
			logger.error("Exception in getTsmList method of InterSsdTransferAdminAction::"+ex);
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
					InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
					int intTSMID = Integer.parseInt(request.getParameter("parentId"));
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					int intBusCat = Integer.parseInt(request.getParameter("busCategory"));
					
					logger.info("TSM ::  " + intTSMID);
					
					InterSsdTransferAdminService revLogReportService = new InterSsdTransferAdminServiceImpl();
					
					List accountList = revLogReportService.getFromDistAccountDetails(intTSMID , circleId, intBusCat);
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
					InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
					int intTSMID = Integer.parseInt(request.getParameter("parentId"));
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					int intBusCat = Integer.parseInt(request.getParameter("busCategory"));
					
					logger.info("TSM ::  " + intTSMID);
					
					InterSsdTransferAdminService revLogReportService = new InterSsdTransferAdminServiceImpl();
					
					List accountList = revLogReportService.getToDistAccountDetails(intTSMID , circleId, intBusCat);
					
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
	
	public ActionForward viewSerialNoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("inside viewSerialNoList of InterSsdTransferAdminAction");
		InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
		InterSsdTransferAdminDto ssdTrnsfrDto = new InterSsdTransferAdminDto();
		InterSsdTransferAdminService interssdService = new InterSsdTransferAdminServiceImpl();
		
		ssdTrnsfrDto.setCircleId(request.getParameter("circleId").toString());
		ssdTrnsfrDto.setProductId(request.getParameter("productId").toString());
		ssdTrnsfrDto.setFromDistId(request.getParameter("fromDist").toString());
		ssdTrnsfrDto.setStockType(request.getParameter("stockType").toString());
		logger.info("Circle id == "+request.getParameter("circleId").toString() + " and product Id == "+request.getParameter("productId").toString() +"  and from dist ID == "+request.getParameter("fromDist").toString() );
		try{
		ArrayList serialNoList = interssdService.getAvailableSerialNos(ssdTrnsfrDto);
		logger.info("Arraylist Size in  viewSerialNoList == "+serialNoList.size());
		formBean.setArrAvailableStb(serialNoList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
	
	public ActionForward getAvailableQty(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String stockValue ="10";
		InterSsdTransferAdminService interssdService = new InterSsdTransferAdminServiceImpl();
		InterSsdTransferAdminDto ssdTrnsfrDto = new InterSsdTransferAdminDto();
		ssdTrnsfrDto.setCircleId(request.getParameter("circleId").toString());
		ssdTrnsfrDto.setProductId(request.getParameter("productId").toString());
		ssdTrnsfrDto.setFromDistId(request.getParameter("fromDist").toString());
		ssdTrnsfrDto.setStockType(request.getParameter("stockType").toString());
		logger.info("Circle id == "+request.getParameter("circleId").toString() + " and product Id == "+request.getParameter("productId").toString() +"  and from dist ID == "+request.getParameter("fromDist").toString() );
		try{
			ArrayList  serialNoList = interssdService.getAvailableSerialNos(ssdTrnsfrDto);
			
			logger.info("serialNoList..."+serialNoList.size());
			stockValue =serialNoList.size()+"";
			ajaxCall(request,response,stockValue);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	
	}
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,String stockValue)throws Exception
	{
	 	Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		optionElement = root.addElement("option");
		optionElement.addAttribute("value",stockValue);
		optionElement.addAttribute("text",stockValue);
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush(); 
		out.flush();
}
	
	public ActionForward insertData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("inside insertData. function of ..InterSsdTransferAdminAction");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long loginId = sessionContext.getId();
		AuthenticationService userService = new AuthenticationServiceImpl();
		InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
		InterSsdTransferAdminService interssdService = new InterSsdTransferAdminServiceImpl();
		String strMessage ="";
		try{
		String circleId = request.getParameter("circleId").toString();
		String fromDistId =request.getParameter("fromDist").toString();
		String toDistId = request.getParameter("toDist").toString();
		logger.info("loginId === "+loginId+" and circleId == "+circleId+ " and fromDistId == "+fromDistId + " and toDistId == "+toDistId);
		Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
		String[] arrProdId = commaPattern.split(formBean.getJsArrprodId()[0]);
		String[] arrSrNo = commaPattern.split(formBean.getJsArrTransfrStbs()[0]);
		String[] arrProdQty = commaPattern.split(formBean.getJsArrTransfrProdQty()[0]);
		String[] arrProdName = commaPattern.split(formBean.getJsArrprodName()[0]);
		
		ArrayList<InterSsdTransferAdminDto> interssdDtoList = new ArrayList<InterSsdTransferAdminDto>();
		for(int count=0;count<arrProdId.length;count++){
			InterSsdTransferAdminDto interSSdDto = new InterSsdTransferAdminDto();
			logger.info(" productIdListItr.next() prod id=="+arrProdId[count] + "  sr no == "+arrSrNo[count] + "  and arrProdQty=="+arrProdQty[count] );
			interSSdDto.setProductId(arrProdId[count]);
			interSSdDto.setSerialNo(arrSrNo[count]);
			interSSdDto.setProdQty(arrProdQty[count]);
			interSSdDto.setFromDistId(fromDistId);
			interSSdDto.setToDistId(toDistId);
			interSSdDto.setCircleId(circleId);
			interSSdDto.setLoginUser(loginId.toString());
			interssdDtoList.add(interSSdDto);
		
		}

		ListIterator<InterSsdTransferAdminDto> interssdDtoListItr = interssdDtoList.listIterator();
	
		strMessage=interssdService.insertStockTransfs(interssdDtoListItr);
		String fromdistMailId= userService.getEmailId(Long.parseLong(fromDistId));
		String todistMailId=  userService.getEmailId(Long.parseLong(toDistId));
		String fromDistLoginName = userService.getAccountName(Long.parseLong(fromDistId));
		String toDistLoginName = userService.getAccountName(Long.parseLong(toDistId));
		logger.info("From Dist Email Id == "+ fromdistMailId+"and  fromDistLoginName == "+fromDistLoginName+ " and toDistLoginName == "+toDistLoginName+"  and To Dist Mail id == "+todistMailId);
//		Commneted by Shilpa On 01-6-2012 for UAT observation to send DC Nos in return
		/*if(strMessage.equals("SUCCESS")){
			strMessage= "Stock Transfered Succesfully. ";
			String mailMsg = stockTransferMailing(fromDistLoginName,toDistLoginName,fromdistMailId,todistMailId,arrProdName, arrSrNo);
			if(mailMsg.equals("Success")){
				strMessage +=" Notifications have been sent to both distributors!!";
			}else{
				strMessage +=" Notifications could not be sent to both distributors!!";
			}
		}*/
		
		if(!strMessage.equals("FAILURE")){
			strMessage= "Stock Transfered Succesfully. DC No. is :"+strMessage;
			String mailMsg = stockTransferMailing(fromDistLoginName,toDistLoginName,fromdistMailId,todistMailId,arrProdName, arrSrNo);
			if(mailMsg.equals("Success")){
				strMessage +=" Notifications have been sent to both distributors!!";
			}else{
				strMessage +=" Notifications could not be sent to both distributors!!";
			}
		}else{
			strMessage= "Internal Error occured, please try again.";
		}
		
		//Updated Against the defect Id of System Testing : MASDB00179839    
		//List<CircleDto> circleList =interssdService.getAllCircleList();
		//formBean.setCircleList(circleList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
//			Updated Against the defect Id of System Testing : MASDB00179839  
			interssdService = new InterSsdTransferAdminServiceImpl();
			//List<CircleDto> circleList =interssdService.getAllCircleList();
			List<List<CircleDto>> getInitData  = interssdService.getInitData();
			
			List<CircleDto> circleList = getInitData.get(0);
			List<CircleDto> busCatList = getInitData.get(1);
			
			formBean.setCircleList(circleList);
			formBean.setBusCatList(busCatList);
			formBean.setFromTSMList(null);
			formBean.setToTSMList(null);
		}
		request.setAttribute("strMessage", strMessage);
		formBean.setStrMessage(strMessage);
		return mapping.findForward("success");
	}
	
	public String stockTransferMailing(String fromDistName,String toDistName, String fromDistEmail,String toDistEmail,String[] arrProdName, String[] arrSrNo)
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
	sbMessageFrom.append("STB ID		Product Type \n");
	sbMessageFrom.append("------------------------------------------------------- \n");
//	Message Send To, "To Distributor"
	sbMessageTo.append("Dear " +toDistName+", \n\n");
	sbMessageTo.append("Following STBs has been transferred from "+ fromDistName + " to "+ toDistName+" by Admin. In case of any queries or concern, kindly contact DP helpdesk. \n\n");
	sbMessageTo.append("STB ID		Product Type \n");
	sbMessageTo.append("------------------------------------------------------- \n");
	
	String prodName="";
	String[] srNoArray =null;
	int i =0;
	for(int count=0;count<arrProdName.length;count++){
		srNoArray =arrSrNo[count].split("A");
		prodName = arrProdName[count];
		for(i=0;i<srNoArray.length;i++){
			sbMessageFrom.append(srNoArray[i] + "		"+prodName +" \n");
			sbMessageTo.append(srNoArray[i] + "		"+prodName +" \n");
		}
		
	}

	sbMessageFrom.append(
		"\n/** This is an Auto generated email.Please do not reply to this.**/");
	sbMessageTo.append(
	"\n/** This is an Auto generated email.Please do not reply to this.**/");
	txtMessageFrom = sbMessageFrom.toString();
	logger.info("txtMessageFrom : " + txtMessageFrom);
	
	txtMessageTo = sbMessageTo.toString();
	logger.info("txtMessageTo : " + txtMessageTo);
	
	String strHost = ResourceReader.getCoreResourceBundleValue("login.smtp"); 
	logger.info("SMTP HOST : " + strHost);
	String strFromEmail = PropertyReader.getValue("LOGIN.EMAIL");
	logger.info("From EMAIL : " + strFromEmail);
	
	try {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", strHost);
		Session ses = Session.getDefaultInstance(prop, null);
		//Sending message to "From Distributor"
		MimeMessage msg = new MimeMessage(ses);
		msg.setFrom(new InternetAddress(strFromEmail));
		msg.addRecipient(
			Message.RecipientType.TO,
			new InternetAddress(fromDistEmail));
		
		msg.setSubject(strSubject);
		msg.setText(txtMessageFrom);
		msg.setSentDate(new Date());
		
//		Sending message to "To Distributor"
		MimeMessage msgTo = new MimeMessage(ses);
		msgTo.setFrom(new InternetAddress(strFromEmail));
		
		msgTo.addRecipient(
				Message.RecipientType.TO,
				new InternetAddress(toDistEmail));
		msgTo.setSubject(strSubject);
		msgTo.setText(txtMessageTo);
		msgTo.setSentDate(new Date());
		Transport.send(msg);
		Transport.send(msgTo);

	} catch (Exception e) {
		strMessage = "Error";
		e.printStackTrace();
		logger.error(
			"Exception occured in stockTransferMailing():" + e.getMessage());
		throw new VirtualizationServiceException(e.getMessage());

	}
	return strMessage;
}
	
	public ActionForward inactiveInit(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
		//Setting Circle List in Form
		InterSsdTransferAdminService interssdService = new InterSsdTransferAdminServiceImpl();
		//List<CircleDto> circleList =interssdService.getAllCircleList();
		List<List<CircleDto>> getInitData  = interssdService.getInitData();
		
		List<CircleDto> circleList = getInitData.get(0);
		List<CircleDto> busCatList = getInitData.get(1);
		
		UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		Iterator<CircleDto> catIter=busCatList.iterator();
		/*while(catIter.hasNext()){
			CircleDto bsCat=catIter.next();
			if(null!=sessionContext.getFourG() && sessionContext.getFourG().equalsIgnoreCase("Y") && !bsCat.getCircleId().equals("5"))
				catIter.remove();
			else if(null== sessionContext.getFourG() && bsCat.getCircleId().equals("5"))
				catIter.remove();
		}*/
		
		formBean.setCircleList(circleList);
		formBean.setBusCatList(busCatList);
		formBean.setFromTSMList(null);
		formBean.setToTSMList(null);
		return mapping.findForward("inactiveInit");

	}
	
	public ActionForward getFromInactiveDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("Inside method getFromInactiveDistList");
				try 
				{
					//	Get account Id form session.
					InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
					int intTSMID = Integer.parseInt(request.getParameter("parentId"));
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					int intBusCat = Integer.parseInt(request.getParameter("busCategory"));
					
					logger.info("TSM ::  " + intTSMID);
					
					InterSsdTransferAdminService revLogReportService = new InterSsdTransferAdminServiceImpl();
					
					List accountList = revLogReportService.getFromInactivAccountDetails(intTSMID, circleId, intBusCat);
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
	public ActionForward insertDataInactive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("inside insertDataInactive. function of ..InterSsdTransferAdminAction");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long loginId = sessionContext.getId();
		AuthenticationService userService = new AuthenticationServiceImpl();
		InterSsdTransferAdminBean formBean =(InterSsdTransferAdminBean)form ;
		InterSsdTransferAdminService interssdService = new InterSsdTransferAdminServiceImpl();
		String strMessage ="";
		try{
		String circleId = request.getParameter("circleId").toString();
		String fromDistId =request.getParameter("fromDist").toString();
		long distId=Long.parseLong(fromDistId);
		String stockType=request.getParameter("stockType").toString();
	//	String toDistId = request.getParameter("toDist").toString();
		logger.info("loginId === "+loginId+" and circleId == "+circleId+ " and fromDistId == "+fromDistId +" and Stock type="+stockType) ;
		Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
		String dcRem=formBean.getDcRem();
		String courierAgency=formBean.getCourierAge();
		String docketNum=formBean.getDocketNumber();
		
		String[] arrProdId = formBean.getJsArrprodId();
		String[] arrSrNo = formBean.getJsArrTransfrStbs();
		
		
		String[] arrProdQty = formBean.getJsArrTransfrProdQty();
		String[] arrProdName = formBean.getJsArrprodName();
		int length=arrProdId.length;
		
		ArrayList SDCNO;
		ArrayList serialUpdated = new ArrayList();
	
		if (null==stockType || stockType.equals("1")) {
			DistStockDTO distDto = new DistStockDTO();
			String[] arrRemarks = new String[length];
			for (int count = 0; count < length; count++) {
				arrRemarks[count] = "Inactive Stock RFD";
				arrSrNo[count] = arrSrNo[count] + " ";
				logger.info(arrSrNo[count]);
				logger.info(arrProdId[count]);
				logger.info(arrProdId[count]);
				logger.info(arrProdQty[count]);
			}
			distDto.setDistID(Long.parseLong(fromDistId));
			distDto.setCircleId(Integer.parseInt(circleId));
			distDto.setJsSrNo(arrSrNo);
			distDto.setJsArrprodId(arrProdId);
			distDto.setJsArrqty(arrProdQty);
			distDto.setJsArrremarks(arrRemarks);
			distDto.setCourierAgency(courierAgency);
			distDto.setDocketNum(docketNum);
			distDto.setRemarks(dcRem);
			HBOStockService hboStock = new HBOStockServiceImpl();
			SDCNO = hboStock.freshRecordsNewUpdated(distDto);
		
		
		if (null!=SDCNO)
		{
			String SDCNo=SDCNO.get(0).toString();			
			request.setAttribute("SDCNo", SDCNo);
			
			 strMessage="Delivery Challan was created Successfully with DC No. "+SDCNo;
			
		}
		else {
			strMessage="Something Went Wrong";
		}
		
		}else if(null!=stockType && stockType.equals("2")){
			
			DPDcCreationService dpdcsrc=new DPDcCreationServiceImpl();
			ArrayList<DpDcReverseStockInventory> dcCreationDtoList = new ArrayList<DpDcReverseStockInventory>();
			String strDcNo = "";
			String srArray[]=arrSrNo[0].split(",");
			for(int count=0;count<srArray.length;count++){
				DpDcReverseStockInventory dcCreationDto = new DpDcReverseStockInventory();
				logger.info(" productIdListItr.next() =="+arrProdId[0] + "  sr no == "+srArray[count]);
				dcCreationDto=dpdcsrc.getStockDetails(srArray[count].trim());
				dcCreationDto.setProdId(arrProdId[0]);
				dcCreationDto.setStrSerialNo(srArray[count]);
				dcCreationDto.setStrNewRemarks(dcRem);
				dcCreationDtoList.add(dcCreationDto);
				
			}
			
			ListIterator<DpDcReverseStockInventory> itr=dcCreationDtoList.listIterator();
			strDcNo=dpdcsrc.insertStockCollection(itr, distId, circleId, dcRem, courierAgency, docketNum);
			if(strDcNo!=null){
				request.setAttribute("SDCNo", strDcNo);
				request.setAttribute("transType", Constants.DC_TYPE_REVERSE);
				
				 strMessage="Delivery Challan was created Successfully with DC No. "+strDcNo;
			}
			else 
			{
				strMessage="Something went wrong";
			}
		}
			else if(null!=stockType && stockType.equals("3")){
			
			DPDcCreationService dpdcsrc=new DPDcCreationServiceImpl();
			
			String srArray[]=arrSrNo[0].split(",");
			for(int count=0;count<srArray.length;count++){
				DpDcReverseStockInventory dcCreationDto = new DpDcReverseStockInventory();
				logger.info(" productIdListItr.next() =="+arrProdId[0] + "  sr no == "+srArray[count]);
			//	dcCreationDto=dpdcsrc.getStockDetails(srArray[count].trim());
				dcCreationDto.setProdId(arrProdId[0]);
				dcCreationDto.setStrSerialNo(srArray[count]);
		//		dcCreationDto.setStrNewRemarks(dcRem);
			//	dcCreationDtoList.add(dcCreationDto);
				
		
			
			//ListIterator<DpDcReverseStockInventory> itr=dcCreationDtoList.listIterator();
				int i=	dpdcsrc.updateInactiveSecondaryStock( arrProdId[0] ,distId,srArray[count]);
				if (i==1)
				serialUpdated.add(srArray[count]);
			
			}
			if (serialUpdated.size()>0)
				strMessage="Number of Serials Processed are  :"+ serialUpdated.size();
			else
				strMessage=" No data Processed";
		}
			else if(null!=stockType && stockType.equals("4")){
				
				String srArray[]=arrSrNo[0].split(",");
				ArrayList<String> churnSrList=new ArrayList<String>();
				ArrayList<String> churnSeqList=new ArrayList<String>();
				for(int i=0;i<srArray.length;i++){
					churnSrList.add(srArray[i]);
				}

				ChurnDCGenerationService churnDCService = new ChurnDCGenerationServiceImpl();
				ArrayList<ArrayList<ChurnDCGenerationDTO>> arrPendingSTB = 
					(ArrayList<ArrayList<ChurnDCGenerationDTO>>) churnDCService.viewChurnPendingForDCList(Long.parseLong(fromDistId));
				
				 ArrayList<ChurnDCGenerationDTO> porList = new ArrayList<ChurnDCGenerationDTO>();
				 porList = arrPendingSTB.get(0);
				
				for(ChurnDCGenerationDTO chrDto:porList){
					if(churnSrList.contains(chrDto.getSerial_Number()))
					{
						churnSeqList.add(chrDto.getReqId());
					}
				}
				 
				ChurnDCGenerationDTO churnDto=new ChurnDCGenerationDTO();
				churnDto.setAllCheckedSTB(churnSeqList);
				churnDto.setCourierAgency(courierAgency);
				churnDto.setDocketNumber(docketNum);
				churnDto.setRemarks(dcRem);
				churnDto.setLnUserID(Long.parseLong(fromDistId));
				churnDto.setIntCircleID(Integer.parseInt(circleId));
				
				
				
				arrPendingSTB = churnDCService.reportDCCreation(churnDto);
				
				porList = new ArrayList<ChurnDCGenerationDTO>();
			   // ArrayList<ChurnDCGenerationDTO> porListN = new ArrayList<ChurnDCGenerationDTO>();
			    ArrayList<ChurnDCGenerationDTO> arrMessage = new ArrayList<ChurnDCGenerationDTO>();
			    
			    if(arrPendingSTB!=null && !arrPendingSTB.isEmpty())
				{
					porList = arrPendingSTB.get(0);
					arrMessage = arrPendingSTB.get(1);
					//porListN = arrPendingSTB.get(1);
					//arrMessage = arrPendingSTB.get(2);
				}
				//logger.info(" Number of Refurbishment STB  ::  "+porList.size() + " and arrMessage == "+arrMessage.get(0).getMessage());
				//logger.info(" Number of SWAP STB  ::  "+porListN.size());
				
				String strDCNO;
				if(arrMessage.size()>0)
				{
					strMessage = arrMessage.get(0).getMessage();
					strDCNO = arrMessage.get(0).getDcNo();
				}
				else
				{
					strMessage = "Internal error occured. Please try again.";
					strDCNO = "";
				}
				
				if(strDCNO!=null){
					request.setAttribute("SDCNo", strDCNO);
					request.setAttribute("transType",Constants.DC_TYPE_CHURN);
					
					 strMessage="Delivery Challan was created Successfully with DC No. "+strDCNO;
				}
				else 
				{
					strMessage="Something went wrong";
				}
				
			}
		
		
		/*ArrayList<InterSsdTransferAdminDto> interssdDtoList = new ArrayList<InterSsdTransferAdminDto>();
		for(int count=0;count<arrProdId.length;count++){
			InterSsdTransferAdminDto interSSdDto = new InterSsdTransferAdminDto();
			logger.info(" productIdListItr.next() prod id=="+arrProdId[count] + "  sr no == "+arrSrNo[count] + "  and arrProdQty=="+arrProdQty[count] );
			interSSdDto.setProductId(arrProdId[count]);
			interSSdDto.setSerialNo(arrSrNo[count]);
			interSSdDto.setProdQty(arrProdQty[count]);
			interSSdDto.setFromDistId(fromDistId);
			interSSdDto.setToDistId(toDistId);
			interSSdDto.setCircleId(circleId);
			interSSdDto.setLoginUser(loginId.toString());
			interssdDtoList.add(interSSdDto);
		
		}

		ListIterator<InterSsdTransferAdminDto> interssdDtoListItr = interssdDtoList.listIterator();
	
		strMessage=interssdService.insertStockTransfs(interssdDtoListItr);
		String fromdistMailId= userService.getEmailId(Long.parseLong(fromDistId));
		String todistMailId=  userService.getEmailId(Long.parseLong(toDistId));
		String fromDistLoginName = userService.getAccountName(Long.parseLong(fromDistId));
		String toDistLoginName = userService.getAccountName(Long.parseLong(toDistId));
		logger.info("From Dist Email Id == "+ fromdistMailId+"and  fromDistLoginName == "+fromDistLoginName+ " and toDistLoginName == "+toDistLoginName+"  and To Dist Mail id == "+todistMailId);*/
//		Commneted by Shilpa On 01-6-2012 for UAT observation to send DC Nos in return
		/*if(strMessage.equals("SUCCESS")){
			strMessage= "Stock Transfered Succesfully. ";
			String mailMsg = stockTransferMailing(fromDistLoginName,toDistLoginName,fromdistMailId,todistMailId,arrProdName, arrSrNo);
			if(mailMsg.equals("Success")){
				strMessage +=" Notifications have been sent to both distributors!!";
			}else{
				strMessage +=" Notifications could not be sent to both distributors!!";
			}
		}*/
		
		/*if(!strMessage.equals("FAILURE")){
			strMessage= "Stock Transfered Succesfully. DC No. is :"+strMessage;
			String mailMsg = stockTransferMailing(fromDistLoginName,toDistLoginName,fromdistMailId,todistMailId,arrProdName, arrSrNo);
			if(mailMsg.equals("Success")){
				strMessage +=" Notifications have been sent to both distributors!!";
			}else{
				strMessage +=" Notifications could not be sent to both distributors!!";
			}
		}else{
			strMessage= "Internal Error occured, please try again.";
		}*/
		
		//Updated Against the defect Id of System Testing : MASDB00179839    
		//List<CircleDto> circleList =interssdService.getAllCircleList();
		//formBean.setCircleList(circleList);
		}catch(Exception e)
		{
			strMessage="Something Went Wrong.";
			e.printStackTrace();
		}finally{
//			Updated Against the defect Id of System Testing : MASDB00179839  
			interssdService = new InterSsdTransferAdminServiceImpl();
			//List<CircleDto> circleList =interssdService.getAllCircleList();
			List<List<CircleDto>> getInitData  = interssdService.getInitData();
			
			List<CircleDto> circleList = getInitData.get(0);
			List<CircleDto> busCatList = getInitData.get(1);
			
			//UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			Iterator<CircleDto> catIter=busCatList.iterator();
			/*while(catIter.hasNext()){
				CircleDto bsCat=catIter.next();
				if(null!=sessionContext.getFourG() && sessionContext.getFourG().equalsIgnoreCase("Y") && !bsCat.getCircleId().equals("5"))
					catIter.remove();
				else if(null== sessionContext.getFourG() && bsCat.getCircleId().equals("5"))
					catIter.remove();
			}*/
			formBean.setCircleList(circleList);
			formBean.setBusCatList(busCatList);
			formBean.setFromTSMList(null);
			formBean.setToTSMList(null);
			formBean.setStrMessage(strMessage);
			
		}
		
		return mapping.findForward("inactiveInit");
	}
			
}
