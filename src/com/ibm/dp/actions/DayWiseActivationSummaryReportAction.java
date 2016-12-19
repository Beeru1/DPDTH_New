package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.beans.DayWiseActivationSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DayWiseActivationSummaryReportDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.service.DayWiseActivationSummaryReportService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.DayWiseActivationSummaryReportServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class DayWiseActivationSummaryReportAction extends DispatchAction {
	
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(DayWiseActivationSummaryReportAction.class.getName());

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		
		System.out.println("******************11*******************************init******************************************");
		
		logger.info("In DayWiseActivationSummaryReportAction() method..........!!!");
		HttpSession session = request.getSession();
		
		DayWiseActivationSummaryReportBean reportBean = (DayWiseActivationSummaryReportBean) form;
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		
		ArrayList ciList = new ArrayList();
		ArrayList cgList = new ArrayList(); // for card group list
		DPMasterService dpms = new DPMasterServiceImpl();
		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		try {
			
			
			List<SelectionCollection> listAllCircles = new ArrayList<SelectionCollection>();
			int groupId = sessionContext.getGroupId();
			reportBean.setAccountLevel(sessionContext.getAccountLevel());

			System.out.println("groupId:::::"+groupId);
			if (groupId == Constants.ADMIN_GROUP_ID || groupId == Constants.DTH_ADMIN_GROUP_ID) {
				listAllCircles = utilityAjaxService.getAllCircles();
				reportBean.setCircleList(listAllCircles);
			} 
			else if (groupId == Constants.TSM_GROUP_ID || groupId == Constants.GROUP_ID_ZBM  || groupId == Constants.GROUP_ID_ZSM  || groupId == Constants.GROUP_ID_CIRCLE_ADMIN  ) {
				listAllCircles = utilityAjaxService.getTsmCircles(sessionContext.getId());
				reportBean.setCircleList(listAllCircles);
			} 
			else {
				SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				listAllCircles.add(sc);
				reportBean.setCircleList(listAllCircles);

		}
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
		session.setAttribute("dayWiseActivationReport", "inprogress"); 
		try {
			DayWiseActivationSummaryReportBean reportBean = (DayWiseActivationSummaryReportBean) form;
			String fromDate = reportBean.getFromDate();
			String toDate = reportBean.getToDate();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			
			String showCircle =reportBean.getShowCircle();
			
			if(circleIds.equalsIgnoreCase("")){
				circleIds = String.valueOf(sessionContext.getCircleId());
			}			
			
			DayWiseActivationSummaryReportDTO reportDto=new DayWiseActivationSummaryReportDTO();
			reportDto.setHiddenCircleSelecIds(circleIds);
			reportDto.setFromDate(fromDate);
			reportDto.setToDate(toDate);
			
			DayWiseActivationSummaryReportService reportService =new DayWiseActivationSummaryReportServiceImpl();
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
	
	private void setDefaultValue(DayWiseActivationSummaryReportBean reverseCollectionBean,
			HttpServletRequest request,String circleId) throws Exception {
		StockSummaryReportService stbService = new StockSummaryReportServiceImpl();
			List<ProductMasterDto> productList = stbService.getProductTypeMaster(circleId);

		reverseCollectionBean.setProductList(productList);
		request.setAttribute("productList", productList);
		
	}

	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
				//System.out.println("inside getReportDownloadStatus");
				try
				{
					HttpSession session = request.getSession();
					
					String status = (String) session.getAttribute("dayWiseActivationReport");
					
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
