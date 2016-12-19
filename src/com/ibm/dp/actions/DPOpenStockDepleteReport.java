package com.ibm.dp.actions;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

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

import com.ibm.dp.beans.DPOpenStockDepleteReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.OpenStockDepleteDTO;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.ibm.utilities.Utility;
import com.ibm.utilities.PropertyReader;

public class DPOpenStockDepleteReport extends DispatchAction{

	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS = "success";
	private static final String SUCCESS_EXCEL = "successExcel";

	/*
	 * Method for getting stored password for logged-in user group
	 */

	private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());

	public ActionForward initOpenStockDepleteReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		// Get account Id form session.
		logger.info("In initOpenStockDepleteReport() method.");
		
		DPOpenStockDepleteReportBean reportBean = (DPOpenStockDepleteReportBean) form;
		DPReportService reportService = new DPReportServiceImpl();
		return mapping.findForward(INIT_SUCCESS);
	}

	public ActionForward getOpenStockDepleteReportDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In getOpenStockDepleteReportDataExcel() ");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		long accId = sessionContext.getId();
		int distId = Integer.parseInt(String.valueOf(accId));
		
		DPOpenStockDepleteReportBean reportBean = (DPOpenStockDepleteReportBean) form;
		/*String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");*/
		
		ActionErrors errors = new ActionErrors();
		
	/*	if(fromDate == null || "".equalsIgnoreCase(fromDate.trim()))
		{
			errors.add("empty.fromDate", new ActionError("empty.fromDate"));
	        saveErrors(request, errors);
	         return mapping.findForward("initsuccess");

		}*/
		
		
		/*if(!Utility.isValidateDate(fromDate,"MM/dd/yyyy"))
		{ 
			//LOGGER.error("Form Date is not valid");
			errors.add("invalid.Fromdate", new ActionError("invalid.Fromdate"));
			saveErrors(request, errors);
			 return mapping.findForward("initsuccess");
		
		}
		
		
		if(Utility.validateDateTodayFalse(fromDate))
		{
			//LOGGER.error("To Date can not be greater than today");
			errors.add("greater.Todate.today", new ActionError("greater.Todate.today"));
			saveErrors(request, errors);
			return mapping.findForward("initsuccess");
			
		}
		
		
		if(toDate == null || "".equalsIgnoreCase(toDate.trim()))
		{
			errors.add("empty.toDate", new ActionError("empty.toDate"));
	        saveErrors(request, errors);
	         return mapping.findForward("initsuccess");

		}
		
		
		
		
		
		
		
		if(!Utility.isValidateDate(toDate,"MM/dd/yyyy"))
		{
			//LOGGER.error("To Date is not valid");
			errors.add("invalid.Todate", new ActionError("invalid.Todate"));
			saveErrors(request, errors);
			 return mapping.findForward("initsuccess");
		}
		
		
		
		
		if(Utility.validateDateTodayFalse(toDate))
		{
			//LOGGER.error("To Date can not be greater than today");
			errors.add("greater.Todate.today", new ActionError("greater.Todate.today"));
			saveErrors(request, errors);
			return mapping.findForward("initsuccess");
			
		}
		
		String days = PropertyReader.getValue("report.from.todate.days").trim();
		
		
		if(!Utility.DateDiff(fromDate,toDate,days,"MM/dd/yyyy"))
		{
			//LOGGER.error("Date range is valid for "+days+" Days only");
			errors.add("Diff.formDate.toDate", new ActionError("Diff.formDate.toDate",days));
			saveErrors(request, errors);
			return mapping.findForward("initsuccess");
		}
		
		
		java.util.Date now =  new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String today = sdf.format(now);
		
		String todayDiff = PropertyReader.getValue("report.from.today.date.days").trim();
		
		if(!Utility.DateDiff(fromDate,today,todayDiff,"MM/dd/yyyy"))
		{
			//LOGGER.error("Date range is valid for "+days+" Days only");
			errors.add("Diff.formDate.today.days", new ActionError("Diff.formDate.today.days",todayDiff));
			return mapping.findForward("initsuccess");
		}
		*/
		
		// Remove to rollback Reports
		/*try
		{
		DPReportService reportService = new DPReportServiceImpl();
		List<OpenStockDepleteDTO> openStockDepleteList = reportService.getOpenStockDepleteReportData();
		logger.info("Size of Report Data Excel :" + openStockDepleteList.size());
		reportBean.setOpenStockDepleteList(openStockDepleteList);
		request.setAttribute("exceptionReportList",openStockDepleteList);
		}catch(Exception ex)
		{
			    //an internal system error occurred
				errors.add("system.internal", new ActionError("system.internal"));
				saveErrors(request, errors);
				return mapping.findForward("initsuccess");
			
		}
		*/
		return mapping.findForward(SUCCESS_EXCEL);
	}

}
