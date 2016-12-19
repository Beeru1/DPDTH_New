package com.ibm.dp.actions;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.ibm.dp.beans.DPInventoryStatusFormBean;
import com.ibm.dp.beans.DPStockDetailsReportBean;
import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPDistributorStockDetailReport extends DispatchAction 
{

private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS_EXCEL = "successExcel";

/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());

public ActionForward initDistributorStockReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	// Get account Id form session.
	logger.info("In initExceptionReport() method.");
	
	DPStockDetailsReportBean reportBean = (DPStockDetailsReportBean) form;
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	
	String userId = (String) session.getAttribute("userId");
	
	//Add by harabns to roll back MIS report
	//reportBean.setCircleid(sessionContext.getCircleId());
	ActionErrors errors = new ActionErrors();
	DPMasterService dpms = new DPMasterServiceImpl();
	ArrayList ciList = new ArrayList();
	ArrayList cgList = new ArrayList(); // for card group list
	
	try {
		/* Add by harabns to roll back MIS report
		
		//log.info("***ABOVE BUSINESS CATEGORY***");
		ciList = dpms.getCircleID(userId);
		
		reportBean.setArrCIList(ciList);
		if(reportBean.getCircleid() != 0){
			reportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			cgList=dpms.getCardGroups(""+reportBean.getCircleid());
			
			
			int roleId = sessionContext.getCircleId();
			DPReportService reportService = new DPReportServiceImpl();
			
			List accountList = reportService.getDistAccountDetails(roleId);
			reportBean.setProductStatusList(accountList);
			request.setAttribute("productStatusList",accountList);
			logger.info("Account list size : " + accountList.size());
			
			
		}else{
			reportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
			cgList=dpms.getCardGroups("0");
			
		}
		reportBean.setArrCGList(cgList);
		*/
	}
	catch(Exception e)
	{
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(INIT_SUCCESS);
	}
		
	return mapping.findForward(INIT_SUCCESS);
}

public ActionForward getDistributorStockReportExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{	ActionErrors errors = new ActionErrors();
	try {
		logger.info("In getDistributorStockReportExcel()  of DPDistributorStockDetailReport");
		DPStockDetailsReportBean reportBean = (DPStockDetailsReportBean) form;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String circleId = request.getParameter("circleId");
		int distId = Integer.parseInt(request.getParameter("accountId"));
		
		  
		/* Add by harabns to roll back MIS report
		DPReportService reportService = new DPReportServiceImpl();
		List<StockDetailReport> distributorStockReportList = reportService.getDistributorStockReportExcel(distId , fromDate, toDate ,circleId );
		logger.info("Size of Report Data Excel :" + distributorStockReportList.size());
		reportBean.setDistributorStockReportList(distributorStockReportList);
		request.setAttribute("exceptionReportList",distributorStockReportList);
		*/
	} catch(Exception e)
	{
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(SUCCESS_EXCEL);
	}
	
	return mapping.findForward(SUCCESS_EXCEL);
}

public ActionForward initStatusAccount(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws IOException, ServletException 
{
	logger.info("In method initStatusAccount().");
	ActionErrors errors = new ActionErrors();
	try 
	{
		//	Get account Id form session.
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long accId = sessionContext.getId();
		int distId = Integer.parseInt(String.valueOf(accId));
		
		DPStockDetailsReportBean reportBean = (DPStockDetailsReportBean) form;
		String levelId = request.getParameter("levelId");
		logger.info("Fetch account name of role :" + levelId);
		int roleId = Integer.parseInt(levelId);
		DPReportService reportService = new DPReportServiceImpl();
		
		/* Add by harabns to roll back MIS report
		List accountList = reportService.getDistAccountDetails(roleId);
		reportBean.setProductStatusList(accountList);
		request.setAttribute("productStatusList",accountList);
		logger.info("Account list size : " + accountList.size());
		*/
					
		// Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		// Add by harabns to roll back MIS report
		/*Iterator iter = accountList.iterator();
		while (iter.hasNext()) 
		{
			InventoryStatusBean statusReportBean = (InventoryStatusBean) iter.next();
			optionElement = root.addElement("option");
			optionElement.addAttribute("text", statusReportBean.getAccountName());
			optionElement.addAttribute("value", statusReportBean.getAccountId());
		}
		*/
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
	

	} catch (Exception e) 
	{

		e.printStackTrace();
		return null;
	}

	return mapping.findForward(INIT_SUCCESS);
}

}//END


