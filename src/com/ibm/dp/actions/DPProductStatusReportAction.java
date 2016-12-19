package com.ibm.dp.actions;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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

import com.ibm.dp.beans.DPInventoryStatusFormBean;
import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPProductStatusReportAction extends DispatchAction 
{

private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS = "success";
private static final String SUCCESS_EXCEL = "successExcel";

/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());

public ActionForward initInventoryStatusReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	// Get account Id form session.
	logger.info("In initInventoryStatusReport() method.");
	
	DPInventoryStatusFormBean reportBean = (DPInventoryStatusFormBean) form;
	DPReportService reportService = new DPReportServiceImpl();
	
	UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
	reportBean.setLevelId("0");
	
	List productStatusList = reportService.getLevelDetails();
	reportBean.setProductStatusList(productStatusList);
	request.setAttribute("productStatusList",productStatusList);
	return mapping.findForward(INIT_SUCCESS);
}
/*
public ActionForward getReportData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	logger.info("In getReportData() method.");
	HttpSession session = request.getSession();
	
	ActionMessages messages = new ActionMessages();
	DPReportForm reportBean = (DPReportForm) form;
	DPReportService reportService = new DPReportServiceImpl();
	//String reportId = reportBean.getReportId();
	String reportId = request.getParameter("reportId");
	if(reportId.length()==0)
		reportId="0";
	String forward = SUCCESS;
	UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	if(mapping.getPath().indexOf("Excel")!=-1){
		forward=forward+"Excel";
	}	
	ArrayList reportDataList = reportService.getReportData(reportId,userSessionContext.getLapuMobileNo());
	if(reportDataList.size()==0){
		messages.add(HBOConstants.NO_RECORD,new ActionMessage("no.record.found"));
		saveMessages(request, messages);
	}
	forward = forward+reportId;
	reportBean.setReportDataList(reportDataList);
	return mapping.findForward(forward);

}*/

public ActionForward getReportDataExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{
	logger.info("In getReportDataExcel() ");
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	long accId = sessionContext.getId();
	int distId = Integer.parseInt(String.valueOf(accId));
	
	DPInventoryStatusFormBean reportBean = (DPInventoryStatusFormBean) form;
	int levelId = Integer.parseInt(request.getParameter("levelId"));
	int accountId = Integer.parseInt((String)request.getParameter("accountId"));
	
	DPReportService reportService = new DPReportServiceImpl();
	List reportDataList = reportService.getProductsDetails(levelId, accountId, distId);
	reportBean.setReportDataList(reportDataList);
	request.setAttribute("reportDataList",reportDataList);
	
	return mapping.findForward(SUCCESS_EXCEL);
}



public ActionForward initStatusAccount(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws IOException, ServletException 
{
	logger.info("In method initStatusAccount().");
	
	try 
	{
		//	Get account Id form session.
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long accId = sessionContext.getId();
		int distId = Integer.parseInt(String.valueOf(accId));
		
		DPInventoryStatusFormBean reportBean = (DPInventoryStatusFormBean) form;
		String levelId = request.getParameter("levelId");
		logger.info("Fetch account name of role :" + levelId);
		int roleId = Integer.parseInt(levelId);
		DPReportService reportService = new DPReportServiceImpl();
		
		List accountList = reportService.getAccountDetails(roleId, distId);
		reportBean.setProductStatusList(accountList);
		request.setAttribute("productStatusList",accountList);
		logger.info("Account list size : " + accountList.size());
					
		// Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		Iterator iter = accountList.iterator();
		while (iter.hasNext()) 
		{
			InventoryStatusBean statusReportBean = (InventoryStatusBean) iter.next();
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
	}

	return null;
}

}//END


