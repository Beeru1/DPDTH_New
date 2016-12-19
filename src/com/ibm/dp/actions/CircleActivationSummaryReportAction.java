package com.ibm.dp.actions;

import java.io.PrintWriter;
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
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.CircleActivationSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CircleActivationSummaryReportDTO;
import com.ibm.dp.service.CircleActivationSummaryReportService;
import com.ibm.dp.service.impl.CircleActivationSummaryReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class CircleActivationSummaryReportAction extends DispatchAction {
	
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(CircleActivationSummaryReportAction.class.getName());

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		
		System.out.println("******************11*******************************init******************************************");
		
		logger.info("In initCircleActivationSummaryReport() method..........!!!");
		HttpSession session = request.getSession();
		
		CircleActivationSummaryReportBean reportBean = (CircleActivationSummaryReportBean) form;
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		
		reportBean.setCircleId(circleIdSr);
		
		try {
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			
			logger.info("In initReport() method.   circle == "+circleIdSr);
			
			
			List<SelectionCollection> listAllCircles =null;
			
			listAllCircles = utilityAjaxService.getAllCircles();
			reportBean.setCircleList(listAllCircles);
			request.setAttribute("circleList", listAllCircles);
				
			System.out.println("action ends");
				
			}
			
	
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			//return mapping.findForward(INIT_SUCCESS);
		}
			
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of AccountDetailReportAction...................!!!");	
		HttpSession session = request.getSession();
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		String accountID = String.valueOf(sessionContext.getId());
	
		session.setAttribute("CircleActivation", "inprogress"); 
		try {
			CircleActivationSummaryReportBean reportBean = (CircleActivationSummaryReportBean) form;
			String fromDate = reportBean.getFromDate();
			String toDate = reportBean.getToDate();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			
			String showCircle =reportBean.getShowCircle();
			
			if(circleIds.equalsIgnoreCase("")){
				circleIds = String.valueOf(sessionContext.getCircleId());
			}			
			
			CircleActivationSummaryReportDTO reportDto=new CircleActivationSummaryReportDTO();
			reportDto.setHiddenCircleSelecIds(circleIds);
			reportDto.setFromDate(fromDate);
			reportDto.setToDate(toDate);
			
			CircleActivationSummaryReportService reportService =new CircleActivationSummaryReportServiceImpl();
			GenericReportsOutputDto genericOutputDto =reportService.getAccountDetailExcel(reportDto) ;
			
			reportBean.setHeaders(genericOutputDto.getHeaders());
			reportBean.setOutput(genericOutputDto.getOutput());
			//ReportDetailDTO reportDetail = genericService.getReportDetails(reportId);
			//genericFormBean.setReportLabel(reportDetail.getReportLabel());			
			request.setAttribute("output", genericOutputDto.getOutput());
			request.setAttribute("header", genericOutputDto.getHeaders());
			
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS_EXCEL);
		}
		
		return mapping.findForward(SUCCESS_EXCEL);
	}
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("CircleActivation");
			
			String result = "";
			if(status != null)
				result = status;
			
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
		

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
	

}
