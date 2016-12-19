package com.ibm.dp.actions;


import java.util.ArrayList;

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

import com.ibm.dp.beans.DPReportForm;
import com.ibm.dp.common.Constants;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.dp.service.impl.DPReportServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.utils.PropertyReader;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPReportAction extends DispatchAction {


private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS = "success";
private static final String SUCCESS_EXCEL = "successExcel";
/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPReportAction.class
		.getName());



public ActionForward initReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	
	logger.info("inside initReport...........");
	ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		String reportExternalPassword = "";
	    try {
			UserSessionContext userSessionContext = (UserSessionContext)session.getAttribute(Constants.AUTHENTICATED_USER);
			
			int groupID = 0;
			String groupName = "";
			
			
			if(null != userSessionContext) {
				DPCreateAccountService accService = new DPCreateAccountServiceImpl();
				groupID = userSessionContext.getGroupId();
				groupName =userSessionContext.getLoginName();
			 /*   logger.info("Group ID......."+groupID);
				reportExternalPassword = accService.getReportExternalInfo(userSessionContext.getGroupId());
			  */
			}
			StringBuffer forwardBuffer = new StringBuffer();

			PropertyReader
			.setBundleName("com.ibm.dp.resources.DPResources");
/**	
			forwardBuffer.append(PropertyReader
					.getString(Constants.REPORT_URL));
			forwardBuffer.append(PropertyReader
					.getString(Constants.DP_reportExtUSER));
			forwardBuffer.append(groupName);
			forwardBuffer.append(PropertyReader
					.getString(Constants.DP_reportExtPASSWORD));
			forwardBuffer.append(reportExternalPassword);
			*/
			response.sendRedirect(PropertyReader
					.getString(Constants.REPORT_URL));// change report url.
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	} // end initRepo

public ActionForward initReportAction(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	HttpSession session = request.getSession();
	ActionErrors errors = new ActionErrors();
	try {
		DPReportForm reportBean = (DPReportForm) form;
		DPReportService reportService = new DPReportServiceImpl();
		ArrayList reportNamesList = reportService.getReportNames();
		reportBean.setReportNamesList(reportNamesList);
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(INIT_SUCCESS);
	}
	return mapping.findForward(INIT_SUCCESS);
}

public ActionForward getReportData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	//HttpSession session = request.getSession();
	/*DPReportFormBean reportBean = (DPReportFormBean) form;
	DPReportService reportService = new DPReportServiceImpl();
	String reportId = reportBean.getReportId();
	String forward = SUCCESS;
	UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);

	HBOUser hboUser = new HBOUser(userSessionContext);
	ArrayList reportDataList = reportService.getReportData(reportBean.getReportId(),hboUser.getId());
	if(reportDataList!=null){
		dataList = reportDataList.subList(0, reportDataList.size()-1);
		noOfColumns = (Integer)reportDataList.get(reportDataList.size()-1);
	}	
	reportBean.setReportDataList(dataList);
	reportBean.setNoOfColumns(noOfColumns);
	reportBean.setReportNamesList(reportService.getReportNames());
	return mapping.findForward(SUCCESS);*/
	ActionErrors errors = new ActionErrors();
	HttpSession session = request.getSession();
	//if(session.)
	String forward = SUCCESS;
	try {
		ActionMessages messages = new ActionMessages();
		DPReportForm reportBean = (DPReportForm) form;
		DPReportService reportService = new DPReportServiceImpl();
		//String reportId = reportBean.getReportId();
		String reportId = request.getParameter("reportId");
		if(reportId.length()==0)
			reportId="0";
		
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
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(forward);
	}
	return mapping.findForward(forward);

}

public ActionForward getReportDataExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	HttpSession session = request.getSession();
	ActionErrors errors = new ActionErrors();
	try {
		DPReportForm reportBean = (DPReportForm) form;
		DPReportService reportService = new DPReportServiceImpl();
		ArrayList dataList = null;
		int noOfColumns=0;
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);

		//ArrayList reportDataList = reportService.getReportData(reportBean.getReportId(),hboUser.getId());
		//if(reportDataList!=null){
		//	dataList = reportDataList.subList(0, reportDataList.size()-1);
		//	noOfColumns = (Integer)reportDataList.get(reportDataList.size()-1);
		//}	
		//reportBean.setReportDataList(dataList);

		reportBean.setNoOfColumns(noOfColumns);
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(SUCCESS);
		}
	return mapping.findForward(SUCCESS_EXCEL);
}
}