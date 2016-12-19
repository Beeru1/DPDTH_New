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

import com.ibm.dp.beans.DPExceptionReportBean;
import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPExceptionConsumedReport extends DispatchAction 
{

private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS = "success";
private static final String SUCCESS_EXCEL = "successExcel";

/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());

public ActionForward initExceptionReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{ ActionErrors errors = new ActionErrors();
	// Get account Id form session.
	try {
		logger.info("In initExceptionReport() method.");
		
		DPExceptionReportBean reportBean = (DPExceptionReportBean) form;
		DPReportService reportService = new DPReportServiceImpl();
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(SUCCESS);
	}
	return mapping.findForward(INIT_SUCCESS);
}

public ActionForward getExceptionConsumedReportDataExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{		ActionErrors errors = new ActionErrors();
	try {
		logger.info("In getExceptionConsumedReportDataExcel() ");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		long accId = sessionContext.getId();
		int distId = Integer.parseInt(String.valueOf(accId));
		
		DPExceptionReportBean reportBean = (DPExceptionReportBean) form;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		DPReportService reportService = new DPReportServiceImpl();
		List<ExceptionReportDTO> exceptionReportList = reportService.getExceptionConsumedReportData(fromDate, toDate);
		logger.info("Size of Report Data Excel :" + exceptionReportList.size());
		reportBean.setExceptionReportList(exceptionReportList);
		request.setAttribute("exceptionReportList",exceptionReportList);
	} catch (RuntimeException e) {e.printStackTrace();
	logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
	errors.add("errors",new ActionError(e.getMessage()));
	saveErrors(request, errors);
	return mapping.findForward(SUCCESS);
	}
	
	return mapping.findForward(SUCCESS_EXCEL);
}

}//END


