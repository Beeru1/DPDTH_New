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

import com.ibm.dp.beans.DPDetailReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPDetailReportDTO;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.service.DPDetailReportService;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPDetailReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPDetailReportAction extends DispatchAction 
{

private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS_EXCEL = "successExcel";

/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPDetailReportAction.class.getName());

public ActionForward initDetailReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	// Get account Id form session.
	logger.info("In initExceptionReport() method.");
	
	DPDetailReportFormBean detailReportBean = (DPDetailReportFormBean) form;
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	ActionErrors errors = new ActionErrors();
	String userId = (String) session.getAttribute("userId");
	detailReportBean.setCircleid(sessionContext.getCircleId());
	int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
	DPMasterService dpms = new DPMasterServiceImpl();
	ArrayList ciList = new ArrayList();
	ArrayList cgList = new ArrayList(); // for card group list
	
	try {
		
		//log.info("***ABOVE BUSINESS CATEGORY***");
		ciList = dpms.getCircleID(userId);
		
		detailReportBean.setArrCIList(ciList);
		detailReportBean.setArrCGList(cgList);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
	}
		
	return mapping.findForward(INIT_SUCCESS);
}

public ActionForward getDetailReportExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{	ActionErrors errors = new ActionErrors();
	try {
		logger.info("In getDetailReportExcel()  of DPDetailReportAction");
		DPDetailReportFormBean reportBean = (DPDetailReportFormBean) form;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		DPDetailReportService detailReportService = new DPDetailReportServiceImpl();
		List<DPDetailReportDTO> detailReportList = detailReportService.getdetailReportExcel(fromDate, toDate);
		logger.info("Size of Report Data Excel :" + detailReportList.size());
		reportBean.setDetailReportList(detailReportList);
		request.setAttribute("exceptionReportList",detailReportList);
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

}//END


