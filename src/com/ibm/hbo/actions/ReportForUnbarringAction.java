package com.ibm.hbo.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.common.Constants;
import com.ibm.dp.service.ReportForUnbarringService;
import com.ibm.dp.service.impl.ReportForUnbarringServiceImpl;
import com.ibm.hbo.forms.ReportForUnbarringFormBean;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class ReportForUnbarringAction extends DispatchAction{
	
	private static final String SUCCESS = "success";
	
	public ActionForward initUnbarring(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		//if(session.)
		ReportForUnbarringFormBean reportBean = (ReportForUnbarringFormBean) form;
		ReportForUnbarringService reportService = new ReportForUnbarringServiceImpl();
		//String reportId = reportBean.getReportId();
//		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ArrayList reportDataList = reportService.getReportData();
		
		if(reportDataList.size() != 0){
			reportBean.setSerialNos(reportDataList);
			reportService.updateUnbarredStatus(reportDataList);
		}
		return mapping.findForward(SUCCESS);
	}

}
