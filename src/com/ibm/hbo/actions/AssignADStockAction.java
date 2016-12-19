package com.ibm.hbo.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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

import com.ibm.dp.service.DPPurchaseOrderService;
import com.ibm.dp.service.impl.DPPurchaseOrderServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.TransactionAuditDTO;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.AssignDistStockFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.TransactionAuditService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;
import com.ibm.hbo.services.impl.TransactionAuditServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class AssignADStockAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(AssignADStockAction.class
			.getName());
	private static final String STATUS_ERROR = "error";

	public ActionForward init(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		String userIdFrom =sessionContext.getId()+"";
		logger.info("userNameFrom-----"+userIdFrom);	
		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFrom=new ArrayList();
		String condition=" PARENT_ACCOUNT="+userIdFrom;
		accountFrom=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,condition);
		assignADForm.setArrAssignFrom(accountFrom);	
		
//		Setting AccountTo in Form  
		ArrayList accountTo=new ArrayList();
		String toCondition=" PARENT_ACCOUNT in (select ACCOUNT_ID from " +
							" VR_ACCOUNT_DETAILS where PARENT_ACCOUNT="+userIdFrom+")" ;
		accountTo=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,toCondition);
		assignADForm.setArrAssignTo(accountTo);		
		
		assignADForm.setProduct("");
		assignADForm.setAccountFrom("");
		assignADForm.setAccountTo("");
		assignADForm.setAssignedProdQty(0);
		assignADForm.setAvailableProdQty(0);
		assignADForm.setEndingSno("");
		assignADForm.setStartingSerial("");
		
		return mapping.findForward("initAssignADStock");

	}
	public ActionForward initFSE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		String userIdFrom =sessionContext.getId()+"";
		
		logger.info("userNameFrom---"+userIdFrom);
		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFrom=new ArrayList();
		String condition=" ACCOUNT_ID="+userIdFrom+" order by ACCOUNT_NAME";		
		accountFrom=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,condition);
		assignADForm.setArrAssignFrom(accountFrom);		
		
		
		//Setting AccountTo in Form  
		ArrayList accountTo=new ArrayList();
		String toCondition=" PARENT_ACCOUNT="+userIdFrom ;
		accountTo=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,toCondition);
		assignADForm.setArrAssignTo(accountTo);	
		
		assignADForm.setProduct("");
		assignADForm.setAssignedProdQty(0);
		assignADForm.setAvailableProdQty(0);
		assignADForm.setEndingSno("");
		assignADForm.setStartingSerial("");
		
		return mapping.findForward("initAssignToFSE");

	}
	
	public ActionForward initReturnStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		String userIdFrom =sessionContext.getId()+"";
		
		logger.info("userNameFrom---"+userIdFrom);
		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFrom=new ArrayList();
		String condition=" PARENT_ACCOUNT="+userIdFrom+" order by ACCOUNT_NAME" ;
		accountFrom=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,condition);
		assignADForm.setArrAssignFrom(accountFrom);	
		
		
		
		//Setting AccountTo in Form  
		ArrayList accountTo=new ArrayList();
		String toCondition=" ACCOUNT_ID="+userIdFrom+" order by ACCOUNT_NAME";
		accountTo=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,toCondition);
		assignADForm.setArrAssignTo(accountTo);	
		
		assignADForm.setProduct("");
		assignADForm.setAssignedProdQty(0);
		assignADForm.setAvailableProdQty(0);
		assignADForm.setEndingSno("");
		assignADForm.setStartingSerial("");
		return mapping.findForward("stockReturn");

	}
	
	//rajiv jha added for return stock retailer to fse start	
	
	//rajiv jha added for return stock retailer to fse end

	public ActionForward getProdList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj = new HBOUser(sessionContext);
		String userId = obj.getUserId();	
		String circleId=obj.getCircleId();
		String category = request.getParameter("cond1");
		if(category.length()==0)
			category="0";
		HBOMasterService masterService = new HBOMasterServiceImpl();
		ArrayList arrprodList = new ArrayList();
		if(!category.equalsIgnoreCase(null)&&!category.equalsIgnoreCase("")&& category.equalsIgnoreCase("A")){
			arrprodList = masterService.getMasterList(userId,HBOConstants.PRODUCT_LIST, "(CIRCLE_ID="+circleId+" or CIRCLE_ID=0) AND CM_STATUS = 'A' ");
		}
		else
			arrprodList = masterService.getMasterList(userId,HBOConstants.PRODUCT_LIST, "CATEGORY_CODE="+category+" and (CIRCLE_ID="+circleId+" or CIRCLE_ID=0) AND CM_STATUS = 'A' ");
		logger.info("---ProdList----"+arrprodList.size());
		ajaxCall(request,response,arrprodList);
		
		session.setAttribute("prodlist", arrprodList);
		return null;
	}
//	rajiv jha added for return stock to fse start

	public ActionForward initReturnStockToFSE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		String userIdFrom =sessionContext.getId()+"";		
		logger.info("userNameFrom---"+userIdFrom);		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFrom=new ArrayList();
		String condition = " (select account_id from VR_ACCOUNT_DETAILS where PARENT_ACCOUNT = "+userIdFrom+" ) with ur ";//" PARENT_ACCOUNT="+userIdFrom+" order by ACCOUNT_NAME" ;
		accountFrom=masterService.getMasterList(userIdFrom,HBOConstants.RETAILER_FROM_USER_LIST,condition);// jha
		assignADForm.setArrAssignFrom(accountFrom);			
		//Setting AccountTo in Form  
		/*String userIdTo =sessionContext.getId()+"";
		System.err.println("Jha check getid:"+userIdTo);
		System.err.println("Jha check id>:"+sessionContext.getId()+"");
		ArrayList accountTo=new ArrayList();
		//String toCondition=" ACCOUNT_ID="+userIdFrom+" order by ACCOUNT_NAME";
		String toCondition="(select PARENT_ACCOUNT from VR_ACCOUNT_DETAILS where ACCOUNT_ID = "+userIdFrom+" ) with ur";//" ACCOUNT_ID="+userIdFrom+" order by ACCOUNT_NAME";
		accountTo=masterService.getMasterList(userIdFrom,HBOConstants.retailerFSE,toCondition); // rajiv jha 
		assignADForm.setArrAssignTo(accountTo);	
		System.err.println("Jha check query fse:"+toCondition.toString());
		assignADForm.setProduct("");
		assignADForm.setAssignedProdQty(0);
		assignADForm.setAvailableProdQty(0);
		assignADForm.setEndingSno("");
		assignADForm.setStartingSerial("");*/
		return mapping.findForward("stockReturnToFSE");

	}
	public ActionForward getProdListToFSE(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
HttpSession session = request.getSession();
UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
HBOUser obj = new HBOUser(sessionContext);
String userId = obj.getUserId();	
String circleId=obj.getCircleId();
String category = request.getParameter("cond1");
if(category.length()==0)
	category="0";
HBOMasterService masterService = new HBOMasterServiceImpl();
ArrayList arrprodList = new ArrayList();
if(!category.equalsIgnoreCase(null)&&!category.equalsIgnoreCase("")&& category.equalsIgnoreCase("A")){
	arrprodList = masterService.getMasterList(userId,HBOConstants.PRODUCT_LIST, "(CIRCLE_ID="+circleId+" or CIRCLE_ID=0)");
}
else
	arrprodList = masterService.getMasterList(userId,HBOConstants.PRODUCT_LIST, "CATEGORY_CODE="+category+" and (CIRCLE_ID="+circleId+" or CIRCLE_ID=0)");
logger.info("---ProdList----"+arrprodList.size());
ajaxCall(request,response,arrprodList);

session.setAttribute("prodlist", arrprodList);
return null;
}	

	public ActionForward returnStockToFSE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		//Updated by Shilpa Khanna for Serail num count
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		String category=request.getParameter("cond1");
		int categoryId=Integer.parseInt(category.substring(0,category.indexOf("#")));
		int productId=Integer.parseInt(request.getParameter("cond2"));
		int accountFrom=Integer.parseInt(request.getParameter("cond3"));
		int accountTo=Integer.parseInt(request.getParameter("cond4"));
		int assignedProdQty=Integer.parseInt(request.getParameter("cond5"));
		String remarks = request.getParameter("remarks");
		String endingSno=request.getParameter("cond6");
		//Updated by Shilpa Khanna
		//String startingSerial=request.getParameter("cond7");
		String startingSerial=assignADForm.getCond7();
		//Ends here 
		int availableProdQty=Integer.parseInt(request.getParameter("cond8"));
		String productName=request.getParameter("cond9");
		//Updated by Shilpa khanna -- 
		logger.info("hi  startingSerial === "+startingSerial);
		logger.info("hi  endingSno === "+endingSno);
		//logger.info("hi  Shilpa selected Sr no. === "+startingSerial2);
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		int circleId=sessionContext.getCircleId();
		DistStockDTO distDto= new DistStockDTO();
		distDto.setAccountFrom(accountFrom);
		distDto.setAccountTo(accountTo);
		distDto.setCategoryId(categoryId);
		distDto.setRoleList(roleList);
		distDto.setCircleId(circleId);
		distDto.setProductName(productName);
		distDto.setProductId(productId);
		distDto.setAssignedProdQty(assignedProdQty);
		distDto.setAvailableProdQty(availableProdQty);
		
		distDto.setEndingSno(endingSno); //rajiv jha comented
		distDto.setStartingSerial(startingSerial);//contents array of serial nos
		distDto.setAllocation("yes");
		distDto.setRemarks(remarks);	
		distDto.setReturn_to_fse(true);
		TransactionAuditDTO transactionAuditDto=new TransactionAuditDTO();
		transactionAuditDto.setAccountFrom(accountFrom);
		transactionAuditDto.setAccountTo(accountTo);
		transactionAuditDto.setProductId(productId);
		transactionAuditDto.setSerialNo(startingSerial);//startingSerial contains total serial in array
		transactionAuditDto.setTransactionType(Constants.RETURN_TO_FSE);


		TransactionAuditService secondarySaleService=new TransactionAuditServiceImpl();
				secondarySaleService.insertintoHistory(transactionAuditDto);
		
		HBOStockService stockService= new HBOStockServiceImpl();
	
		
		//ArrayList recordUpdated=stockService.recordsUpdated(distDto);//rajiv jha comented
		ArrayList recordUpdated = stockService.recordsNewUpdated(distDto);
		
		assignADForm.setProductName(productName);
		assignADForm.setAccountFrom((accountFrom+""));
		assignADForm.setAccountTo(accountTo+"");
		assignADForm.setAssignedProdQty(assignedProdQty);
		assignADForm.setAvailableProdQty(availableProdQty);
		//assignADForm.setRemarks("Actual Assigned Quantity= "+assignedProdQty);
		
		assignADForm.setAccountFromName(request.getParameter("accountFromName"));
		assignADForm.setAccountToName(request.getParameter("accountToName"));
		assignADForm.setErr_Msg("SUCCESS");
		
		Iterator itt =recordUpdated.iterator();
		DistStockDTO objDistStockDTO=null;
		String failSrN0="";
		while(itt.hasNext())
		{
			//assignADForm.setRemarks(((DistStockDTO)itt.next()).getOptionValue());
			
			
			objDistStockDTO = (DistStockDTO)itt.next();
			if(objDistStockDTO.isValidate())
			{
				assignADForm.setRemarks((objDistStockDTO).getOptionValue());
			}
			else
			{
				System.out.println("Failiure list::"+(objDistStockDTO).getSerialNo());
				//listFail.add(objDistStockDTO.getSerialNo());
				assignADForm.setValidate_flag(true);
				failSrN0 = failSrN0 +","+objDistStockDTO.getSerialNo();
				
			}
			
		}
		
		if(assignADForm.isValidate_flag())
		{
			assignADForm.setStrfailSerialNo(failSrN0.substring(1, failSrN0.length()));
			assignADForm.setProductName("");
			assignADForm.setAccountFrom((""));
			assignADForm.setAccountTo("");
			assignADForm.setAssignedProdQty(0);
			assignADForm.setAvailableProdQty(0);
			//assignADForm.setRemarks("Actual Assigned Quantity= "+assignedProdQty);
			
			assignADForm.setAccountFromName("");
			assignADForm.setAccountToName("");
			assignADForm.setCategory("");
			assignADForm.setRemarks("");
		}
		
		
		
//		logger.info("returned value from DAO");
//		ajaxCall(request, response, recordUpdated,"text");
		
		
		return initReturnStockToFSE(mapping, form, request, response);
	}
	
//	rajiv jha added for return stock to fse end
	
	public ActionForward getAssignTo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);
	String conditionA="";
	String conditionB="";	
	String userIdFrom = request.getParameter("cond1");
	String queryCond = request.getParameter("cond2");
	logger.info("Jha check ()userIdFrom >>:"+userIdFrom);
	logger.info("Jha check queryCond >>:"+queryCond);
	if(queryCond.equalsIgnoreCase("FSEAssign")){
		conditionA=HBOConstants.distAccountLvl;
		conditionB=HBOConstants.fseAccountLvl;	
	}else if(queryCond.equalsIgnoreCase("RETAssign")){
		conditionA=HBOConstants.fseAccountLvl;
		conditionB=	HBOConstants.retAccountLvl;	
	}
	
	HBOMasterService masterService = new HBOMasterServiceImpl();
	ArrayList accountFrom=new ArrayList();
	accountFrom=masterService.getAccountList(userIdFrom,conditionA,conditionB);
	logger.info("-------"+accountFrom.size());	
	ajaxCall(request,response,accountFrom);
	return null;
	}
	
	public ActionForward getStockQtyAllocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		int accountfrom =Integer.parseInt(request.getParameter("cond1"));
		int productId= Integer.parseInt(request.getParameter("cond2"));
		String flag=request.getParameter("cond3");
		
		ArrayList stockQty=null;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOStockService stockService= new HBOStockServiceImpl();
		
		if(flag.equalsIgnoreCase("Y"))
		    stockQty=stockService.getStockQtyAllocation(accountfrom,productId,flag);
		else
			stockQty=stockService.getAvailableStock(accountfrom, productId, flag, sessionContext.getId());
		logger.info("Stock Quantity ------------------------->" + stockQty.toString());
		ajaxCall(request, response, stockQty,"text");
		return null;
	}
	
	public ActionForward getStockQtySecondorySale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		int accountfrom =Integer.parseInt(request.getParameter("cond1"));
		int productId= Integer.parseInt(request.getParameter("cond2"));
		String flag=request.getParameter("cond3");
			
		ArrayList stockQty=null;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOStockService stockService= new HBOStockServiceImpl();
		
		if(flag.equalsIgnoreCase("Y"))
			stockQty=stockService.getAvailableStockSecSale(accountfrom,productId,flag);
		else
			stockQty=stockService.getAvailableStock(accountfrom, productId, flag, sessionContext.getId());
		
				
		logger.info("Stock Quantity ------------------------->" + stockQty.toString());
		ajaxCall(request, response, stockQty,"text");
		return null;
	}
	public ActionForward getStockQtyDistributor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		int productId= Integer.parseInt(request.getParameter("cond2"));
		String flag=request.getParameter("cond3");
			
		ArrayList stockQty=null;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOStockService stockService= new HBOStockServiceImpl();
		
		if(flag.equalsIgnoreCase("Y"))
			stockQty=stockService.getAvailableStockDist(sessionContext.getId(),productId,flag);
		else
			stockQty=stockService.getAvailableStockDist(sessionContext.getId(),productId,flag);	
				
		logger.info("Stock Quantity ------------------------->" + stockQty.toString());
		ajaxCall(request, response, stockQty,"text");
		return null;
	}
		
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,ArrayList arrGetValue)throws Exception
	{
	 	Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		DistStockDTO distStockDTO = new DistStockDTO();
		for (int counter = 0; counter < arrGetValue.size(); counter++) {

				optionElement = root.addElement("option");
				distStockDTO = (DistStockDTO)arrGetValue.get(counter);
				if (distStockDTO != null) {
					optionElement.addAttribute("value",distStockDTO.getOptionValue());
					//logger.info("distStockDTO.getOptionValue()"+distStockDTO.getOptionValue());
					optionElement.addAttribute("text",distStockDTO.getOptionText());
				} else {
					optionElement.addAttribute("value", "None");
					optionElement.addAttribute("text", "None");
				}				
			}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush(); 
		out.flush();
}
 
 public void ajaxCall(HttpServletRequest request, HttpServletResponse response,ArrayList arrGetValue,String text)throws Exception{
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	Element optionElement;
	String value = "0"; 
		if(arrGetValue == null ||arrGetValue.size()== 0){ 
			value = "0";
			}
		else{	
			DistStockDTO distStockDTO = (DistStockDTO)arrGetValue.get(0);
			value =  distStockDTO.getOptionValue();
		}	
			
	response.setContentType("text/html");
	response.setHeader("Cache-Control", "No-Cache");
	PrintWriter out = response.getWriter();
	out.write(value);
	out.flush();				
	}
 
	public ActionForward insertFSEData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		HttpSession session = request.getSession();
		String category=request.getParameter("cond1");
		int categoryId=Integer.parseInt(category.substring(0,category.indexOf("#")));
		int productId=Integer.parseInt(request.getParameter("cond2"));
		int accountFrom=Integer.parseInt(request.getParameter("cond3"));
		int accountTo=Integer.parseInt(request.getParameter("cond4"));
		int assignedProdQty=Integer.parseInt(request.getParameter("cond5"));
		String endingSno="";
		String startingSerial="";
		int availableProdQty=Integer.parseInt(request.getParameter("cond6"));
		String productName=request.getParameter("cond7");
		String remarks = request.getParameter("remarks");
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		int circleId=sessionContext.getCircleId();
		DistStockDTO distDto= new DistStockDTO();
		distDto.setAccountFrom(accountFrom);
		distDto.setAccountTo(accountTo);
		distDto.setCategoryId(categoryId);
		distDto.setRoleList(roleList);
		distDto.setCircleId(circleId);
		distDto.setProductName(productName);
		distDto.setProductId(productId);
		distDto.setAssignedProdQty(assignedProdQty);
		distDto.setAvailableProdQty(availableProdQty);
		distDto.setEndingSno(endingSno);
		distDto.setStartingSerial(startingSerial);
		distDto.setRemarks(remarks);	
		HBOStockService stockService= new HBOStockServiceImpl();
		
		ArrayList recordUpdated=stockService.recordsUpdated(distDto);	
		logger.info("returned value from DAO");
		ajaxCall(request, response, recordUpdated,"text");
		
		
		return null;
	}
	//rajiv jha add new method for allocation start
	public ActionForward insertNewFSEData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("in Action Class insertNewFSEData***************************************");
		HttpSession session = request.getSession();
		String category=request.getParameter("cond1");
		logger.info("category == "+category);
		String strCategoryId = category.substring(0,category.indexOf("#"));
		logger.info("strCategoryId == "+strCategoryId);
		int categoryId=Integer.parseInt(strCategoryId.trim());
		int productId=Integer.parseInt(request.getParameter("cond2"));
		int accountFrom=Integer.parseInt(request.getParameter("cond3"));
		int accountTo=Integer.parseInt(request.getParameter("cond4"));
		int assignedProdQty=Integer.parseInt(request.getParameter("cond5"));
		//String endingSno="";
		//String startingSerial="";
		int availableProdQty=Integer.parseInt(request.getParameter("cond6"));
		String productName=request.getParameter("cond7");
		String remarks = request.getParameter("remarks");
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		int circleId=sessionContext.getCircleId();
		DistStockDTO distDto= new DistStockDTO();
		TransactionAuditDTO transactionAuditDto=new TransactionAuditDTO();
		distDto.setAccountFrom(accountFrom);
		distDto.setAccountTo(accountTo);
		distDto.setCategoryId(categoryId);
		distDto.setRoleList(roleList);
		distDto.setCircleId(circleId);
		distDto.setProductName(productName);
		distDto.setProductId(productId);
		distDto.setAssignedProdQty(assignedProdQty);
		distDto.setAvailableProdQty(availableProdQty);
		distDto.setEndingSno(request.getParameter("endingSno"));//endingSno contains total serial number
		distDto.setStartingSerial(request.getParameter("startingSerial"));//startingSerial contains total serial in array
		distDto.setRemarks(remarks);
		distDto.setAllocation("yes");
		
		
		transactionAuditDto.setAccountFrom(accountFrom);
		transactionAuditDto.setAccountTo(accountTo);
		transactionAuditDto.setProductId(productId);
		transactionAuditDto.setSerialNo(request.getParameter("startingSerial"));//startingSerial contains total serial in array
		transactionAuditDto.setTransactionType(Constants.ALLOCATION);
		//AssignDistStockFormBean formNew = (AssignDistStockFormBean) form;
		
		
		
		
		
		
		HBOStockService stockService= new HBOStockServiceImpl();
		ArrayList recordUpdated=stockService.recordsNewUpdated(distDto);
		
		TransactionAuditService secondarySaleService=new TransactionAuditServiceImpl();
		secondarySaleService.insertintoHistory(transactionAuditDto);
		
		logger.info("Exit Success == ");
		
		/*Updated by Shilpa
		 * logger.info("returned value from DAO recordsNewUpdated method");
		ajaxCall(request, response, recordUpdated,"text");*/
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		String userIdFrom =sessionContext.getId()+"";
		
		logger.info("userNameFrom---"+userIdFrom);
		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFromList=new ArrayList();
		String condition=" ACCOUNT_ID="+userIdFrom+" order by ACCOUNT_NAME";		
		accountFromList=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,condition);
		assignADForm.setArrAssignFrom(accountFromList);		
		
		
		//Setting AccountTo in Form  
		ArrayList accountToList=new ArrayList();
		String toCondition=" PARENT_ACCOUNT="+userIdFrom ;
		accountToList=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,toCondition);
		assignADForm.setArrAssignTo(accountToList);	
		
		assignADForm.setCategory("");
		assignADForm.setProduct("");
		assignADForm.setAssignedProdQty(0);
		assignADForm.setAvailableProdQty(0);
		assignADForm.setEndingSno("");
		assignADForm.setStartingSerial("");
		assignADForm.setRemarks("");

		assignADForm.setAccountFrom("");
		assignADForm.setAccountTo("");
		

		
		assignADForm.setProductName(productName);
		assignADForm.setAccountFrom((accountFrom+""));
		assignADForm.setAccountTo(accountTo+"");
		assignADForm.setAssignedProdQty(assignedProdQty);
		assignADForm.setAvailableProdQty(availableProdQty);
		//assignADForm.setRemarks("Actual Assigned Quantity= "+assignedProdQty);
		Iterator itt =recordUpdated.iterator();
		while(itt.hasNext())
		{
			assignADForm.setRemarks(((DistStockDTO)itt.next()).getOptionValue());
		}
		
		assignADForm.setAccountFromName(request.getParameter("accountFromName"));
		assignADForm.setAccountToName(request.getParameter("accountToName"));
		assignADForm.setErr_Msg("SUCCESS");
		
		

		return mapping.findForward("initAssignToFSE");
	}
	//rajiv jha add new method for allocation end
	
	//rajiv jha add new method for secondary start 
	public ActionForward insertNewRetailerData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
		logger.info("in Action Class insertNewRetailerData ");
		
		HttpSession session = request.getSession();
		String category=request.getParameter("cond1");
		int categoryId=Integer.parseInt(category.substring(0,category.indexOf("#")));
		int productId=Integer.parseInt(request.getParameter("cond2"));
		int accountFrom=Integer.parseInt(request.getParameter("cond3"));
		int accountTo=Integer.parseInt(request.getParameter("cond4"));
		int assignedProdQty=Integer.parseInt(request.getParameter("cond5"));
		//rajiv jha mod start
		String endingSno=request.getParameter("endingSno");//endingSno contains total serial number
		String startingSerial=request.getParameter("startingSerial");//startingSerial contains total serial in array
		// rajiv jha mod end
		int availableProdQty=Integer.parseInt(request.getParameter("cond8"));
		String productName=request.getParameter("cond9");
		String remarks = request.getParameter("remarks");
		String billNo=request.getParameter("billno");
		float vat=Float.parseFloat(request.getParameter("vat"));
		float rate=Float.parseFloat(request.getParameter("rate"));
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		int circleId=sessionContext.getCircleId();
		DistStockDTO distDto= new DistStockDTO();
		distDto.setAccountFrom(accountFrom);
		distDto.setAccountTo(accountTo);
		distDto.setCategoryId(categoryId);
		distDto.setRoleList(roleList);
		distDto.setCircleId(circleId);
		distDto.setProductName(productName);
		distDto.setProductId(productId);
		distDto.setAssignedProdQty(assignedProdQty);
		distDto.setAvailableProdQty(availableProdQty);
		distDto.setEndingSno(endingSno);
		distDto.setStartingSerial(startingSerial);
		distDto.setRemarks(remarks);
		distDto.setAllocation("yes");//just for testing
		distDto.setSecondary("yes");
		distDto.setBillNo(billNo);
		distDto.setVat(vat);
		distDto.setRate(rate);
		distDto.setUserId((int)sessionContext.getId());		
		

		TransactionAuditDTO transactionAuditDto=new TransactionAuditDTO();
		transactionAuditDto.setAccountFrom(accountFrom);
		transactionAuditDto.setAccountTo(accountTo);
		transactionAuditDto.setProductId(productId);
		transactionAuditDto.setSerialNo(request.getParameter("startingSerial"));//startingSerial contains total serial in array
		transactionAuditDto.setTransactionType(Constants.SECONDARY_SALES);


		TransactionAuditService secondarySaleService=new TransactionAuditServiceImpl();
				
				
		HBOStockService stockService= new HBOStockServiceImpl();
		try
		{
			secondarySaleService.insertintoHistory(transactionAuditDto);
			ArrayList recordUpdated=stockService.recordsNewUpdated(distDto);
		
		// Check control param flag
		//String flag = stockService.flagStatus();
		
		/*
		String paramFlag = (String) getResources(request).getMessage("schedular.dpcpe.intflag");
		System.out.println("paramFlag  :" + paramFlag);
		if(paramFlag.equalsIgnoreCase("Y"))
		{
			int numRows = stockService.insertDataCPEDB(distDto);
			logger.info("inserted row in temp table :"+numRows);
			logger.info("rajiv jha inserted rows :"+numRows);
		}
	
		logger.info("returned value from DAO");		
		ajaxCall(request, response, recordUpdated,"text");			*/
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		
		String userIdFrom =sessionContext.getId()+"";
		logger.info("userNameFrom-----"+userIdFrom);	
		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFromList=new ArrayList();
		String condition=" PARENT_ACCOUNT="+userIdFrom;
		accountFromList=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,condition);
		assignADForm.setArrAssignFrom(accountFromList);	
		
//		Setting AccountTo in Form  
		ArrayList accountToList=new ArrayList();
		String toCondition=" PARENT_ACCOUNT in (select ACCOUNT_ID from " +
							" VR_ACCOUNT_DETAILS where PARENT_ACCOUNT="+userIdFrom+")" ;
		accountToList=masterService.getMasterList(userIdFrom,HBOConstants.USER_LIST,toCondition);
		assignADForm.setArrAssignTo(accountToList);		
		
		assignADForm.setProduct("");
		assignADForm.setAccountFrom("");
		assignADForm.setAccountTo("");
		assignADForm.setAssignedProdQty(0);
		assignADForm.setAvailableProdQty(0);
		assignADForm.setEndingSno("");
		assignADForm.setStartingSerial("");
		assignADForm.setBillno("");
		assignADForm.setCategory("");
		assignADForm.setRate("0.0");
		assignADForm.setRemarks("");
		
		
		assignADForm.setProductName(productName);
		assignADForm.setAccountFrom((accountFrom+""));
		assignADForm.setAccountTo(accountTo+"");
		assignADForm.setAssignedProdQty(assignedProdQty);
		assignADForm.setAvailableProdQty(availableProdQty);
		//assignADForm.setRemarks("Actual Assigned Quantity= "+assignedProdQty);
		Iterator itt =recordUpdated.iterator();
		while(itt.hasNext())
		{
			assignADForm.setRemarks(((DistStockDTO)itt.next()).getOptionValue());
		}
		assignADForm.setAccountFromName(request.getParameter("accountFromName"));
		assignADForm.setAccountToName(request.getParameter("accountToName"));
		assignADForm.setErr_Msg("SUCCESS");
		
		
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return mapping.findForward("initAssignADStock");
	}
	//rajiv jha add new method for secondary end
	
	public ActionForward insertRetailerData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
		HttpSession session = request.getSession();
		String category=request.getParameter("cond1");
		int categoryId=Integer.parseInt(category.substring(0,category.indexOf("#")));
		int productId=Integer.parseInt(request.getParameter("cond2"));
		int accountFrom=Integer.parseInt(request.getParameter("cond3"));
		int accountTo=Integer.parseInt(request.getParameter("cond4"));
		int assignedProdQty=Integer.parseInt(request.getParameter("cond5"));
		String endingSno=request.getParameter("cond6");
		String startingSerial=request.getParameter("cond7");
		int availableProdQty=Integer.parseInt(request.getParameter("cond8"));
		String productName=request.getParameter("cond9");
		String remarks = request.getParameter("remarks");
		String billNo=request.getParameter("billno");
		float vat=Float.parseFloat(request.getParameter("vat"));
		float rate=Float.parseFloat(request.getParameter("rate"));			
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		int circleId=sessionContext.getCircleId();
		DistStockDTO distDto= new DistStockDTO();
		distDto.setAccountFrom(accountFrom);
		distDto.setAccountTo(accountTo);
		distDto.setCategoryId(categoryId);
		distDto.setRoleList(roleList);
		distDto.setCircleId(circleId);
		distDto.setProductName(productName);
		distDto.setProductId(productId);
		distDto.setAssignedProdQty(assignedProdQty);
		distDto.setAvailableProdQty(availableProdQty);
		distDto.setEndingSno(endingSno);
		distDto.setStartingSerial(startingSerial);
		distDto.setRemarks(remarks);
		distDto.setBillNo(billNo);
		distDto.setVat(vat);
		distDto.setRate(rate);
		distDto.setUserId((int)sessionContext.getId());
				
		HBOStockService stockService= new HBOStockServiceImpl();
		try{
		ArrayList recordUpdated=stockService.recordsUpdated(distDto);	
		logger.info("returned value from DAO");		
		ajaxCall(request, response, recordUpdated,"text");
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
	}
	
	public ActionForward returnStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
//		Updated by Shilpa Khanna for Serail num count
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		String category=request.getParameter("cond1");
		int categoryId=Integer.parseInt(category.substring(0,category.indexOf("#")));
		int productId=Integer.parseInt(request.getParameter("cond2"));
		int accountFrom=Integer.parseInt(request.getParameter("cond3"));
		int accountTo=Integer.parseInt(request.getParameter("cond4"));
		int assignedProdQty=Integer.parseInt(request.getParameter("cond5"));
		String remarks = request.getParameter("remarks");
		String endingSno=request.getParameter("cond6");
//		updated by SHilpa
		//String startingSerial=request.getParameter("cond7");
		String startingSerial= assignADForm.getCond7();
		logger.info(" Sr nos = "+startingSerial);
		int availableProdQty=Integer.parseInt(request.getParameter("cond8"));
		String productName=request.getParameter("cond9");
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		int circleId=sessionContext.getCircleId();
		DistStockDTO distDto= new DistStockDTO();
		distDto.setAccountFrom(accountFrom);
		distDto.setAccountTo(accountTo);
		distDto.setCategoryId(categoryId);
		distDto.setRoleList(roleList);
		distDto.setCircleId(circleId);
		distDto.setProductName(productName);
		distDto.setProductId(productId);
		distDto.setAssignedProdQty(assignedProdQty);
		distDto.setAvailableProdQty(availableProdQty);
		
		distDto.setEndingSno(endingSno); //rajiv jha comented
		distDto.setStartingSerial(startingSerial);//contents array of serial nos
		distDto.setAllocation("yes");
		distDto.setRemarks(remarks);	
		
		TransactionAuditDTO transactionAuditDto=new TransactionAuditDTO();
		transactionAuditDto.setAccountFrom(accountFrom);
		transactionAuditDto.setAccountTo(accountTo);
		transactionAuditDto.setProductId(productId);
		transactionAuditDto.setSerialNo(startingSerial);//startingSerial contains total serial in array
		transactionAuditDto.setTransactionType(Constants.RETURN_TO_DISTRIBUTOR);


		TransactionAuditService secondarySaleService=new TransactionAuditServiceImpl();
				secondarySaleService.insertintoHistory(transactionAuditDto);
		
		HBOStockService stockService= new HBOStockServiceImpl();
		
		ArrayList recordUpdated=stockService.recordsNewUpdated(distDto);		
		logger.info("returned value from DAO");
//		Updated by SHilpa Khanna
			ajaxCall(request, response, recordUpdated,"text"); 
		
		//return initReturnStock(mapping, form, request, response);
		return null;
	}
	
	public ActionForward exportAssignStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		String userIdFrom =sessionContext.getId()+"";
		logger.info("userNameFrom---"+userIdFrom);
		request.setAttribute("distId",userIdFrom);
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		request.setAttribute("category", arrBussList);
		assignADForm.setArrCategory(arrBussList);
		
		return mapping.findForward("exportAssignStock");
	}
	
	public ActionForward downloadAssignedStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssignDistStockFormBean stockFormBean = (AssignDistStockFormBean)form;
		HBOStockService stockService= new HBOStockServiceImpl();
		String reportType = stockFormBean.getReportType();
		String productId = stockFormBean.getProduct();
		String catCode = stockFormBean.getCategory();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		StringBuffer SQL_REPORTS = new StringBuffer();
		HttpSession session = request.getSession();
		
		int paramCount=1;
		if(reportType.equalsIgnoreCase("1")){
			SQL_REPORTS.append("select parent.account_name as FSE,details.ACCOUNT_CODE,details.ACCOUNT_NAME,stock.SERIAL_NO,stock.REMARKS, product.PRODUCT_NAME,product.SR_MRP " +
					" from VR_ACCOUNT_DETAILS parent,VR_ACCOUNT_LEVEL_MASTER levelmaster,VR_ACCOUNT_DETAILS details,DP_STOCK_INVENTORY stock,DP_PRODUCT_MASTER product where parent.ACCOUNT_LEVEL=levelmaster.LEVEL_ID and levelmaster.LEVEL_NAME='FSE' and details.PARENT_ACCOUNT = parent.ACCOUNT_ID " +
					" and product.PRODUCT_ID = stock.PRODUCT_ID and parent.PARENT_ACCOUNT=? and parent.ACCOUNT_ID = stock.FSE_ID "+
					" and stock.MARK_DAMAGED='N' and details.account_id = stock.RETAILER_ID and date(stock.RETAILER_PURCHASE_DATE) between ? and ? ");
		}
		else if(reportType.equalsIgnoreCase("2")){
			SQL_REPORTS.append("select details.account_name,product.BT_PRODUCT_CODE||' '||product.PRODUCT_NAME product_code,stock.SERIAL_NO,date(stock.FSE_PURCHASE_DATE)FSE_PURCHASE_DATE" +
					" from VR_ACCOUNT_DETAILS details, DP_PRODUCT_MASTER product,VR_ACCOUNT_LEVEL_MASTER levelmaster, " +
					" DP_STOCK_INVENTORY stock where details.ACCOUNT_ID=stock.FSE_ID and details.PARENT_ACCOUNT=? " +
					" and stock.PRODUCT_ID = product.PRODUCT_ID and date(FSE_PURCHASE_DATE) between ? " +
					" and ? and stock.MARK_DAMAGED='N' and details.account_level=levelmaster.LEVEL_ID and levelmaster.LEVEL_NAME='FSE' ");
		}
		if(!catCode.equalsIgnoreCase("A")){
			if(!productId.equalsIgnoreCase("A")){
				
				SQL_REPORTS.append(" and stock.PRODUCT_ID=? ");
			}
			SQL_REPORTS.append(" and product.CATEGORY_CODE= ? ");
		}
		SQL_REPORTS.append(" order by stock.SERIAL_NO with ur");
//		SQL_ASSIGN_STOCK.append("select st.*,pm.PRODUCT_NAME from DP_STOCK_INVENTORY st,DP_PRODUCT_MASTER pm,VR_ACCOUNT_DETAILS ad where " +
//				" pm.PRODUCT_ID = st.PRODUCT_ID and ad.ACCOUNT_ID=st.FSE_ID and ad.PARENT_ACCOUNT=? and st.PRODUCT_ID=? " +
//				" and st.MARK_DAMAGED='N' and date(FSE_PURCHASE_DATE) between ? " +
//				" and ? order by st.SERIAL_NO with ur");
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String distId =sessionContext.getId()+"";
		String distCode = sessionContext.getAccountCode();
		try{
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(SQL_REPORTS.toString());
			String startDt = stockFormBean.getStartDt();
			String endDt = stockFormBean.getEndDt();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date startDate = formatter.parse(startDt);
            Date endDate = formatter.parse(endDt);
            ps.setInt(paramCount++, Integer.parseInt(distId));
 			ps.setDate(paramCount++, new java.sql.Date(startDate.getTime()));
 			ps.setDate(paramCount++, new java.sql.Date(endDate.getTime()));
			if(!catCode.equalsIgnoreCase("A")){
				if(!productId.equalsIgnoreCase("A")){
					ps.setInt(paramCount++, Integer.parseInt(productId));
				}
					ps.setInt(paramCount++, Integer.parseInt(catCode));
			}
			
			Date dt=new Date();
			Format format = new SimpleDateFormat("MM-dd-yyyy--HH-mm-ss");
			String today = format.format(dt);
			String filePath = ResourceReader.getPwdChangeParams(Constants.EXPORT_FILE_PATH);
			//String filePath = "C:/Aditya/Files/"+distId + "_" + today+"_dp.csv";
			File newFile = new File(filePath);
			
			FileWriter fileWriter = new FileWriter(newFile);
			BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			rs = ps.executeQuery();
			if(reportType.equalsIgnoreCase("1")){
				bufWriter.write("Name of FSE,Retailer Code, Retailer Name,Bill No. & Date,Product Name,Serial No.,Amount");
				while(rs.next()){
//					double amount =0.0;
//					if(catCode.equalsIgnoreCase("2"));{
//					amount = amount+rs.getDouble("SR_MRP");
//					}
					bufWriter.write("\n");
					bufWriter.write(rs.getString("FSE")+","+"."+rs.getString("ACCOUNT_CODE")+"."+","+rs.getString("ACCOUNT_NAME")+","+rs.getString("REMARKS")+","+rs.getString("PRODUCT_NAME")+","+"."+rs.getString("SERIAL_NO")+"."+","+rs.getDouble("SR_MRP"));
				}
			}
			else if(reportType.equalsIgnoreCase("2")){
				bufWriter.write("Name of FSE,Product COde & Name,Serial No.,Date of Allocation");
				while(rs.next()){
					bufWriter.write("\n");
					bufWriter.write(rs.getString("ACCOUNT_NAME")+","+rs.getString("PRODUCT_CODE")+","+"."+rs.getString("SERIAL_NO")+"."+","+rs.getString("FSE_PURCHASE_DATE"));
				}
			}
			bufWriter.close();
		
			java.io.BufferedInputStream br = null;
			
			java.io.File f = new java.io.File(newFile.getAbsolutePath());
			try {
				br = new java.io.BufferedInputStream(
						new java.io.FileInputStream(newFile));
				byte[] content = new byte[(int) f.length()];
				br.read(content);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + f.getName() + "\"");
				response.setContentType("application/octet- stream");
				// Added for https issue
				response.setHeader("Cache-Control", "must-revalidate");
				response.setHeader( "Pragma", "public" );
				response.getOutputStream().write(content);
				
				//response.getOutputStream().println();
				response.getOutputStream().flush();
				if (br != null) {
					br.close();
				}
			}catch(Exception e){
			e.printStackTrace();
		} 
	}
	catch(Exception e){
			e.printStackTrace();
	}
	return null;
}

	
	public ActionForward viewSerialNoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		HBOStockService stockService= new HBOStockServiceImpl();
		DistStockDTO distDto = new DistStockDTO();
		
		distDto.setAccountFrom(Integer.parseInt(request.getParameter("cond1")));
		distDto.setAccountTo(Integer.parseInt(request.getParameter("cond2")));
		distDto.setProductId(Integer.parseInt(request.getParameter("cond3")));
		distDto.setCategoryId(Integer.parseInt(request.getParameter("cond4")));
//		System.out.println("*********************jha *********************");
//		System.out.println("Condition - 1 -"+request.getParameter("cond1"));
//		System.out.println("Condition - 2 -"+request.getParameter("cond2"));
//		System.out.println("Condition - 3 -"+request.getParameter("cond3"));
//		System.out.println("Con:)dition - 4 -"+request.getParameter("cond4"));
//		request.setAttribute("serialNos", request.getParameter("cond5"));
//		
		//BeanUtils.copyProperties(distDto, assignADForm);
		//System.err.println("rajivjhacheck in view method :"+request.getParameter("cond4"));
		try{
		ArrayList  serialNoList = stockService.getAvailableSerialNos(distDto);
		
		System.out.println("serialNoList..."+serialNoList.size());
		
		assignADForm.setAvailableSerialNosList(serialNoList);
		//logger.info("Hi Serial no === "+ serialNoList.getSerialNo().get(0));
		if(request.getParameter("intSRAvail")!=null && !request.getParameter("intSRAvail").equals(""))
			assignADForm.setIntSRAvail(Integer.parseInt(request.getParameter("intSRAvail")));
		
		assignADForm.setFuncFlag(request.getParameter("funcFlag"));
		request.setAttribute("funcFlag", request.getParameter("funcFlag"));
		request.setAttribute("intSRAvail", request.getParameter("intSRAvail"));
		request.setAttribute("serialNoList", serialNoList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
	
	public ActionForward viewFreshStockSerialNoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		HBOStockService stockService= new HBOStockServiceImpl();
		DistStockDTO distDto = new DistStockDTO();
		
		distDto.setProductId(Integer.parseInt(request.getParameter("cond3")));
		distDto.setDistID(sessionContext.getId());
		try{
		ArrayList  serialNoList = stockService.getAvailableFreshStockSerialNos(distDto);
		
		System.out.println("serialNoList..."+serialNoList.size());
		assignADForm.setAvailableSerialNosList(serialNoList);
		if(request.getParameter("intSRAvail")!=null && !request.getParameter("intSRAvail").equals(""))
			assignADForm.setIntSRAvail(Integer.parseInt(request.getParameter("intSRAvail")));
		
		assignADForm.setFuncFlag(request.getParameter("funcFlag"));
		request.setAttribute("funcFlag", request.getParameter("funcFlag"));
		request.setAttribute("intSRAvail", request.getParameter("intSRAvail"));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
	
	
	public ActionForward getProductUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productName = request.getParameter("cond1");
		DPPurchaseOrderService poService= new DPPurchaseOrderServiceImpl();
		String productUnit = poService.getProductUnit(productName);		
		ajaxCall(request, response, productUnit,"text");
		return null;
	}
	
	public ActionForward getProductRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		ArrayList prodRateList = (ArrayList)session.getAttribute("prodlist");
		if(Integer.parseInt((String)request.getParameter("cond1"))!=0){
			DistStockDTO stockDto =(DistStockDTO) prodRateList.get(Integer.parseInt((String)request.getParameter("cond1"))-1);
			ajaxCall(request, response, (stockDto.getMrp()*Integer.parseInt((String)request.getParameter("cond2")))+"","text");
		}	
		else
			ajaxCall(request, response, "0.0","text");
		return null;
	}
	
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,String productUnit,String text)throws Exception{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		String pro = productUnit;
		PrintWriter out = response.getWriter();
		out.write(productUnit);
		out.flush();				
		}
	
	public ActionForward getStockQty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		int accountfrom =Integer.parseInt(request.getParameter("cond1"));
		int productId= Integer.parseInt(request.getParameter("cond2"));
		String productName=request.getParameter("cond3");
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		long longAccountID = sessionContext.getId();
		
		String flag="none";
			
		if(request.getParameter("strFlag")!=null )
				flag = request.getParameter("strFlag");
		
		logger.info("productName--------"+productName);
		logger.info("productId--------"+productId);
		
		if(productName.indexOf("$")!=-1)
		{
			flag=productName.substring(productName.indexOf("$")+1, productName.length());
			logger.info("flag"+flag);
		}
		
		ArrayList stockQty=null;
		
		HBOStockService stockService= new HBOStockServiceImpl();
		stockQty=stockService.getAvailableStock(accountfrom,productId,flag, longAccountID);
		System.out.println("stockQty-----"+stockQty);
		logger.info("-------" + stockQty.toString());
		ajaxCall(request, response, stockQty,"text");
		return null;
	}
	
	// rajiv jha add return fresh stock to ware house start

	public ActionForward initReturnStockToWarehouse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		String userIdFrom =sessionContext.getId()+"";		
		logger.info("userNameFrom---"+userIdFrom);		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		assignADForm.setArrCategory(arrBussList);
		
		//Setting Assign From  List in Form
		ArrayList accountFrom=new ArrayList();
		String condition = " (select account_id from VR_ACCOUNT_DETAILS where PARENT_ACCOUNT = "+userIdFrom+" ) with ur ";//" PARENT_ACCOUNT="+userIdFrom+" order by ACCOUNT_NAME" ;
		accountFrom=masterService.getMasterList(userIdFrom,HBOConstants.RETAILER_USER_LIST,condition);// jha
		assignADForm.setArrAssignFrom(accountFrom);		
		
		return mapping.findForward("returnFreshStockToWH");

	}
	
	public ActionForward returnFreshStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		
		ActionErrors errors = new ActionErrors();
		String strMessage ="";
		try
		{
			HttpSession session = request.getSession();
			
	//		Updated by Shilpa Khanna for Serail num count
			AssignDistStockFormBean assignADForm =(AssignDistStockFormBean)form ;
					
			//String category=request.getParameter("categoryID");
			//int categoryId=Integer.parseInt(category.substring(0,category.indexOf("#")));
			//int assignedProdQty=Integer.parseInt(request.getParameter("returnQuantity"));
			//String remarks = request.getParameter("remarks");
			//String endingSno=request.getParameter("cond6");
			///String startingSerial= assignADForm.getCond7();
			//String returnSerialNo=request.getParameter("returnSerialNo");
			///int productId=Integer.parseInt(request.getParameter("productIDWH"));
			///int availableProdQty=Integer.parseInt(request.getParameter("avlSerialNO"));
			///String productName=request.getParameter("productName");
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			int circleId=sessionContext.getCircleId();
			DistStockDTO distDto= new DistStockDTO();			
			distDto.setDistID(sessionContext.getId());
			//distDto.setCategoryId(Integer.parseInt(category));
			distDto.setRoleList(roleList);
			distDto.setCircleId(circleId);
			//distDto.setProductName(productName);
			//distDto.setProductId(productId);
			//distDto.setAssignedProdQty(assignedProdQty);
			//distDto.setAvailableProdQty(availableProdQty);
			//distDto.setSerialNo(returnSerialNo);
			//distDto.setEndingSno(endingSno); //rajiv jha comented
			//distDto.setStartingSerial(returnSerialNo);//contents array of serial nos
			//distDto.setRemarks(remarks);
			
//			Add on UAT observation by harabns on 16th Sept
			distDto.setRemarks(assignADForm.getDcRemarks());
						
			distDto.setJsArrprodId(assignADForm.getJsArrprodId());
			distDto.setJsSrNo(assignADForm.getJsSrNo());
			distDto.setJsArrremarks(assignADForm.getJsArrremarks());
			distDto.setJsArrqty(assignADForm.getJsArrqty());
//			Updated by Shilpa for Critical Changes BFR 14
			logger.info("Cour Name == "+assignADForm.getCourierAgency() +" and Doc Num == "+assignADForm.getDocketNum());
			distDto.setCourierAgency(assignADForm.getCourierAgency().trim());
			distDto.setDocketNum(assignADForm.getDocketNum().trim());
//			Ends Here 			
			HBOStockService stockService= new HBOStockServiceImpl();
			ArrayList SDCNO=stockService.freshRecordsNewUpdated(distDto);		
			logger.info("returned value from DAO");
			String SDCNo=SDCNO.get(0).toString();			
			request.setAttribute("SDCNo", SDCNo);
			
			
			String userIdFrom =sessionContext.getId()+"";		
			ArrayList arrBussList = new ArrayList();
			HBOMasterService masterService = new HBOMasterServiceImpl();
			arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
			assignADForm.setArrCategory(arrBussList);
			assignADForm.setCategory("");
			
			
			//Setting Assign From  List in Form
			ArrayList accountFrom=new ArrayList();
			String condition = " (select account_id from VR_ACCOUNT_DETAILS where PARENT_ACCOUNT = "+userIdFrom+" ) with ur ";//" PARENT_ACCOUNT="+userIdFrom+" order by ACCOUNT_NAME" ;
			accountFrom=masterService.getMasterList(userIdFrom,HBOConstants.RETAILER_USER_LIST,condition);// jha
			assignADForm.setArrAssignFrom(accountFrom);
			
//			Add on UAT observation by harabns on 16th Sept
//			Updated by Shilpa for Critical Changes BFR 14 
			//strMessage = "Delivery Challan have been created successfully : "+SDCNo;
		
			if(assignADForm.getCourierAgency()==null || assignADForm.getCourierAgency().trim().equals("") || assignADForm.getDocketNum()==null || assignADForm.getDocketNum().trim().equals("")){
				strMessage = "DC "+SDCNo+" is saved as draft and will be sent to Warehouse only after Courier Agency and Docket Number is entered.";
			}else{
				strMessage = "Delivery Challan have been created successfully : "+SDCNo;
			}
			
			
			assignADForm.setMessage(strMessage);
			assignADForm.setDcRemarks("");
			assignADForm.setCourierAgency("");
			assignADForm.setDocketNum("");
			}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			strMessage =ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}
		return mapping.findForward("returnFreshStockToWH");
	}
	// rajiv jha add return fresh stock to ware houe end
		
}
