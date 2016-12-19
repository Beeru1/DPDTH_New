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

import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPSCMConsumptionReportDto;
import com.ibm.dp.service.DPReverseLogisticReportService;
import com.ibm.dp.service.impl.DPReverseLogisticReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPSCMConsumptionReport extends DispatchAction 
{

private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS_EXCEL_CONSUMPTION = "successExcelConsumption";
private static final String SUCCESS_EXCEL_LAST_PO = "successExcelLastPO";

/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPReverseLogisticReportAction.class.getName());

public ActionForward initConsumptionReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	// Get account Id form session.
	logger.info("In initConsumptionReport() method.");
	
	DPReverseLogisticReportFormBean revLogReportBean = (DPReverseLogisticReportFormBean) form;
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	ActionErrors errors = new ActionErrors();
	String userId = (String) session.getAttribute("userId");
	revLogReportBean.setCircleid(sessionContext.getCircleId());
	int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
	DPMasterService dpms = new DPMasterServiceImpl();
	ArrayList ciList = new ArrayList();
	ArrayList cgList = new ArrayList(); // for card group list
	
	try {
		
		//log.info("***ABOVE BUSINESS CATEGORY***");
		ciList = dpms.getCircleID(userId);
		
		revLogReportBean.setArrCIList(ciList);
		if(revLogReportBean.getCircleid() != 0){
			revLogReportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			cgList=dpms.getCardGroups(""+revLogReportBean.getCircleid());
			
			
			int roleId = sessionContext.getCircleId();
			DPReverseLogisticReportService revLogReportService = new DPReverseLogisticReportServiceImpl();
			
			List tsmList = revLogReportService.getRevLogTsmAccountDetails(roleId);
			revLogReportBean.setTsmList(tsmList);
			request.setAttribute("tsmList",tsmList);
			logger.info("tsmList list size : " + tsmList.size());
			System.out.println(request.getParameter("circleId"));
			int circleId = sessionContext.getCircleId();
			
			List distList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
			revLogReportBean.setDistList(distList);
			//request.setAttribute("distList",distList);
			logger.info("distList list size : " + distList.size());
			logger.info("loginRole : " + loginRole);
			if(loginRole == 6)
			{
				revLogReportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				
				DPReverseLogisticReportService revLogReportService1 = new DPReverseLogisticReportServiceImpl();
				tsmList = null;
				tsmList = revLogReportService1.getRevLogTsmDistLogin(Integer.parseInt(sessionContext.getId()+""));
				Iterator it = tsmList.iterator();
				String tsmIdSelected = "";
				while(it.hasNext())
				{
					tsmIdSelected = ((DPReverseLogisticReportFormBean) it.next()).getAccountId();
				}
				revLogReportBean.setAccountId(tsmIdSelected);
				revLogReportBean.setTsmList(tsmList);
				request.setAttribute("tsmList",tsmList);
				logger.info("tsmList list size : " + tsmList.size());
				DPReverseLogisticReportFormBean dpReverseLogisticReportFormBean = new DPReverseLogisticReportFormBean();
				dpReverseLogisticReportFormBean.setDistAccName(sessionContext.getAccountName());
				dpReverseLogisticReportFormBean.setDistAccId(sessionContext.getId()+"");
				distList = new ArrayList<DPReverseLogisticReportFormBean>();
				distList.add(dpReverseLogisticReportFormBean);
				revLogReportBean.setDistList(distList);
				revLogReportBean.setDistAccId(sessionContext.getId()+"");
			}
			else
			{
				revLogReportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
			}
			
			
		
		}else{
			revLogReportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
			cgList=dpms.getCardGroups("0");
			
		}
		revLogReportBean.setArrCGList(cgList);
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


public ActionForward initTsmReport(ActionMapping mapping,
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
		int tsmId = Integer.parseInt(String.valueOf(accId));
		
		DPReverseLogisticReportFormBean revLogReportBean = (DPReverseLogisticReportFormBean) form;
		String levelId = request.getParameter("levelId");
		logger.info("Fetch account name of role :" + levelId);
		int roleId = Integer.parseInt(levelId);
		DPReverseLogisticReportService revLogReportService = new DPReverseLogisticReportServiceImpl();
		
		List accountList = revLogReportService.getRevLogTsmAccountDetails(roleId);
		revLogReportBean.setTsmList(accountList);
		request.setAttribute("productStatusList",accountList);
		logger.info("Account list size : " + accountList.size());
		
					
		// Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		Iterator iter = accountList.iterator();
		while (iter.hasNext()) 
		{
			DPReverseLogisticReportFormBean statusReportBean = (DPReverseLogisticReportFormBean) iter.next();
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


public ActionForward initDistReport(ActionMapping mapping,
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
				
				DPReverseLogisticReportFormBean revLogReportBean = (DPReverseLogisticReportFormBean) form;
				String levelId = request.getParameter("parentId");
				int circleId = Integer.parseInt(request.getParameter("circleId"));
				
				logger.info("Fetch account name of role :" + levelId);
				int roleId = Integer.parseInt(levelId);
				DPReverseLogisticReportService revLogReportService = new DPReverseLogisticReportServiceImpl();
				
				List accountList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
				revLogReportBean.setDistList(accountList);
				request.setAttribute("productStatusList",accountList);
				logger.info("Account list size : " + accountList.size());
				
							
				// Ajax start
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("options");
				Element optionElement;
				
				Iterator iter = accountList.iterator();
				while (iter.hasNext()) 
				{
					DPReverseLogisticReportFormBean statusReportBean = (DPReverseLogisticReportFormBean) iter.next();
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

//Get Oracle SCM consumption report
public ActionForward getConsumptionReportExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{	ActionErrors errors = new ActionErrors();
	logger.info("In getConsumptionReportExcel()  of DPReverseLogisticReportAction");
	try {
		DPReverseLogisticReportFormBean reportBean = (DPReverseLogisticReportFormBean) form;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		int  circleId = Integer.parseInt(request.getParameter("circleId"));
		int tsmId = Integer.parseInt(request.getParameter("accountId"));
		int distId = Integer.parseInt(request.getParameter("distAccId"));
			  
		DPReverseLogisticReportService revLogReportService = new DPReverseLogisticReportServiceImpl();
		List<DPSCMConsumptionReportDto> consumptionReportList = revLogReportService.getConsumptionReportExcel(distId, tsmId, circleId, fromDate, toDate);
		logger.info("Size of Report Data Excel :" + consumptionReportList.size());
		reportBean.setDpscmReportList(consumptionReportList);
		//request.setAttribute("dpscmReportList",consumptionReportList);
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(SUCCESS_EXCEL_CONSUMPTION);
	}
	
	return mapping.findForward(SUCCESS_EXCEL_CONSUMPTION);
}

}


