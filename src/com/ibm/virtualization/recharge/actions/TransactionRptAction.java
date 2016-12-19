/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.AccountFormBean;
import com.ibm.virtualization.recharge.beans.TopupSlabBean;
import com.ibm.virtualization.recharge.beans.TransactionRptA2AFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.TransactionReport;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.ReportService;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.ReportServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

/**
 * Controller class for Transaction Report
 * 
 * @author Paras
 */
public class TransactionRptAction extends DispatchAction {

	/* Logger for this Action class. */

	private static Logger logger = Logger.getLogger(TransactionRptAction.class
			.getName());

	/**
	 * This method initializes and opens the transactionReport.jsp page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting init... ");
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_TRANSACTION_REPORT)) {
			logger.info(" user not auth to perform this ROLE_VIEW_TRANSACTION_REPORT activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		return mapping.findForward(Constants.SHOW_TRANS_REPORT_CRITERIA);
	}

	/**
	 * This method will use to get the report data according to report
	 * parameters provided
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward searchReportData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
		String searchFieldName = bean.getSearchFieldName();
		String searchFieldValue = bean.getSearchFieldValue();
		String status = bean.getStatus();
		String requestType = bean.getTransactionType();
		String startDate = bean.getStartDt();
		String endDate = bean.getEndDt();
		
		ReportInputs reportInputs = new ReportInputs();
		// set the form bean parameters to report input DTO.
		reportInputs.setSearchFieldName(searchFieldName);
		reportInputs.setSearchFieldValue(searchFieldValue);
		reportInputs.setStatus(status);
		
		// if transactionType = -1 then get data for all types of transaction
		// else get the data for the transactionType requested.
		if ("-1".equalsIgnoreCase(requestType.trim())) {
			reportInputs.setRequestType(null);
		} else {
			reportInputs.setRequestType(RequestType.valueOf(requestType));
		}
		reportInputs.setStartDt(startDate);
		reportInputs.setEndDt(endDate);

		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_TRANSACTION_REPORT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_TRANSACTION_REPORT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			reportInputs.setSessionContext(sessionContext);
			// call report service to get the data to be displayed.

			ReportService reportService = new ReportServiceImpl();
			if (request.getParameter("searchType") == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("status") == null
					|| request.getParameter("startDate") == null
					|| request.getParameter("endDate") == null) {
				reportInputs.setSearchFieldName(searchFieldName);
				reportInputs.setSearchFieldValue(searchFieldValue);
				reportInputs.setSessionContext(sessionContext);
				reportInputs.setStatus(status);
				if ("-1".equalsIgnoreCase(requestType.trim())) {
					reportInputs.setRequestType(null);
				} else {
					reportInputs.setRequestType(RequestType
							.valueOf(requestType));
				}
				reportInputs.setStartDt(startDate);
				reportInputs.setEndDt(endDate);
				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				request.setAttribute("status", status);
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
			} else {
				reportInputs.setSearchFieldName(request
						.getParameter("searchType"));
				reportInputs.setSearchFieldValue(request
						.getParameter("searchValue"));
				reportInputs.setStatus(request.getParameter("status"));
				if ("-1".equals(request.getParameter("transactionType").trim())) {
					reportInputs.setRequestType(null);
				} else {
					reportInputs.setRequestType(RequestType
							.valueOf(request.getParameter("transactionType")));
				}
				reportInputs.setStartDt(request.getParameter("startDate"));
				reportInputs.setEndDt(request.getParameter("endDate"));
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("status", request.getParameter("status"));
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request
						.setAttribute("endDate", request
								.getParameter("endDate"));
				// to reset data in the jsp on page next previous
				bean.setSearchFieldName(reportInputs.getSearchFieldName());
				bean.setSearchFieldValue(reportInputs
						.getSearchFieldValue());
				bean.setStatus(reportInputs.getStatus());
				bean.setStartDt(reportInputs.getStartDt());
				bean.setEndDt(reportInputs.getEndDt());
				bean.setTransactionType(String.valueOf(reportInputs
						.getRequestType().getValue()));

				reportInputs.setSessionContext(sessionContext);
			}
			/* call service to count the no of rows */
			/*
			 * int noofpages =
			 * reportService.getTransactionRptPageCount(modernTradeInputs);
			 * logger.info("Inside searchReportData().. no of
			 * pages..."+noofpages);
			 */

			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all records */
			ArrayList recordList = reportService.getTransactionRptData(
					reportInputs, pagination.getLowerBound(), pagination
							.getUpperBound(),request.getAttribute("export")!=null?true:false);

			TransactionReport transactionRptDto = (TransactionReport) recordList
					.get(0);
			int totalRecords = transactionRptDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages);

			/*
			 * set the list recieved from service into form bean to display on
			 * jsp.
			 */
			bean.setDisplayDetails(recordList);

		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			if (request.getAttribute("export") == null) {
				return mapping.findForward(Constants.SHOW_TRANS_REPORT_FAILURE);
			} else {
				return null;
			}
						
		}
		logger.info("Executed... ");
		if (request.getAttribute("report") == null) {
			return mapping.findForward(Constants.SHOW_TRANS_REPORT_SUCCESS);
		} else {
			return null;
		}
	}

	
	/**
	 * This method will use to get the view report data according to report
	 * parameters (for selected transaction)provided
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward getViewTransactionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		try{
			TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
			TransactionReport transactionReport = new TransactionReport();
			ReportService reportService = new ReportServiceImpl();
			int transactionId=Integer.parseInt(bean.getTransactionId());
			String requestType = bean.getTransactionType();
			bean.setViewstatus(bean.getStatus());
			/* call service to find selected record data */
			transactionReport = reportService.getTransactionRptDataView(transactionId,requestType);
						
			/* BeanUtil to populate Form Bean  with DTO data */
			try {
					BeanUtils.copyProperties(bean, transactionReport);
					/*
					 * TODO:to change the commission to esp commission in dto(TransactionReport.java)
					 * Navroze
					 */
					bean.setEspCommission(String.valueOf(transactionReport.getCommission()));
				
			} catch (IllegalAccessException iaExp) {
				logger
						.error(
								"  caught Exception while using BeanUtils.copyProperties()",
								iaExp);
			} catch (InvocationTargetException itExp) {
				logger
						.error(
								"  caught Exception while using BeanUtils.copyProperties()",
								itExp);
			}
			
		}catch (VirtualizationServiceException se) {
			logger
			.error(
					"  caught Service Exception ",
					se);
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
			"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.SHOW_TRANS_REPORT_SUCCESS);
			
					
		}
		
			return mapping.findForward(Constants.SHOW_TRANS_REPORT_VIEW_SUCCESS);
	}
	
	
	
	/**
	 * This method will use to export the report data to excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward exportData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_TRANSACTION_REPORT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_TRANSACTION_REPORT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			request.setAttribute("export", "export");
			searchReportData(mapping, form, request, response);

		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.SHOW_TRANS_REPORT_CRITERIA);
		}
		logger.info("Executed... ");
		return mapping.findForward(Constants.EXPORT_TRANS_REPORT_SUCCESS);
	}

	
	/**
	 * This method initializes and opens the customerTransactionReport.jsp page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCustomerRechargeReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug(" Starting init... ");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
		// If the User is CircleAdmin/User else User is Admin
		
				
		// check for authrization based on role
		if (!authorizationService
				.isUserAuthorized(
						sessionContext.getGroupId(),
						ChannelType.WEB,
						AuthorizationConstants.ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT)) {
			logger
					.info(" User not auth to perform this ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		// check if user are external 
		if (sessionContext.getType().equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL)) {
			logger
			.info(" external User not auth to perform this activity    ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		} 
		
		
		CircleService circleService = new CircleServiceImpl();
		
		/* Get list of circles from Service Layer */
		ArrayList circleDtoList = circleService.getCircles();
		/* Create Circle Drop Down */
		session.setAttribute("circleList", circleDtoList);
		bean.setCircleList(circleDtoList);
		// It checks whether the login user is admin or not
		if (sessionContext.getCircleId() != 0 ) {
			bean.setFlagForCircleDisplay(true);
			bean.setCircleId(String.valueOf(sessionContext
					.getCircleId()));
		}
		
		
		return mapping.findForward(Constants.SHOW_CUSTOMER_RECHARGE_TRANSACTION_REPORT);
	}

	
	/**
	 * This method will use to get the report customerTransactionReportExport.jsp data according to report
	 * parameters provided
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward searchRechargeTransactionReportData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.debug(" Started... ");
		ActionErrors errors = new ActionErrors();
		TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
		String searchFieldName = bean.getSearchFieldName();
		String searchFieldValue = bean.getSearchFieldValue();
		String status = bean.getStatus();
		String requestType = bean.getTransactionType();
		String startDate = bean.getStartDt();
		String endDate = bean.getEndDt();
		String cirlceId =null;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		if (sessionContext.getCircleId() != 0 ) {
			 cirlceId = String.valueOf(sessionContext.getCircleId());
			 bean.setFlagForCircleDisplay(true);
		}
		else{
			cirlceId = bean.getCircleId();
		}

		ReportInputs reportInputs = new ReportInputs();
		// set the form bean parameters to report input DTO.
		reportInputs.setSearchFieldName(searchFieldName);
		reportInputs.setSearchFieldValue(searchFieldValue);
		reportInputs.setStatus(status);
		// if transactionType = -1 then get data for all types of transaction
		// else get the data for the transactionType requested.
		if ("-1".equalsIgnoreCase(requestType.trim())) {
			reportInputs.setRequestType(null);
		} else {
			reportInputs.setRequestType(RequestType.valueOf(requestType));
		}
		reportInputs.setStartDt(startDate);
		reportInputs.setEndDt(endDate);
		reportInputs.setCircleId(Integer.valueOf(cirlceId));

		try {

			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT)) {
				logger.debug(" user not auth to perform this ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			reportInputs.setSessionContext(sessionContext);
			// call report service to get the data to be displayed.

			ReportService reportService = new ReportServiceImpl();
			
			if (request.getParameter("searchType") == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("status") == null
					|| request.getParameter("startDt") == null
					|| request.getParameter("endDt") == null
					|| request.getParameter("cirlceId") == null) {
				reportInputs.setSearchFieldName(searchFieldName);
				reportInputs.setSearchFieldValue(searchFieldValue);
				reportInputs.setSessionContext(sessionContext);
				reportInputs.setStatus(status);
				if ("-1".equalsIgnoreCase(requestType.trim())) {
					reportInputs.setRequestType(null);
				} else {
					reportInputs.setRequestType(RequestType
							.valueOf(requestType));
				}
				reportInputs.setStartDt(startDate);
				reportInputs.setEndDt(endDate);
				reportInputs.setCircleId(Integer.valueOf(cirlceId));
				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				request.setAttribute("status", status);
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDt", startDate);
				request.setAttribute("endDt", endDate);
				request.setAttribute("cirlceId",cirlceId);
			} else {
				reportInputs.setSearchFieldName(request
						.getParameter("searchType"));
				reportInputs.setSearchFieldValue(request
						.getParameter("searchValue"));
				reportInputs.setStatus(request.getParameter("status"));
				if ("-1".equals(request.getParameter("transactionType").trim())) {
					reportInputs.setRequestType(null);
				} else {
					reportInputs.setRequestType(RequestType
							.valueOf(request.getParameter("transactionType")));
				}
				reportInputs.setStartDt(request.getParameter("startDt"));
				reportInputs.setEndDt(request.getParameter("endDt"));
				reportInputs.setCircleId(Integer.valueOf(request.getParameter("cirlceId")));
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("status", request.getParameter("status"));
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDt", request
						.getParameter("startDt"));
				request
						.setAttribute("endDt", request
								.getParameter("endDt"));
				request.setAttribute("cirlceId",cirlceId);
				// to reset data in the jsp on page next previous
				bean.setSearchFieldName(reportInputs.getSearchFieldName());
				bean.setSearchFieldValue(reportInputs
						.getSearchFieldValue());
				bean.setStatus(reportInputs.getStatus());
				bean.setStartDt(reportInputs.getStartDt());
				bean.setEndDt(reportInputs.getEndDt());
				bean.setTransactionType(String.valueOf(reportInputs
						.getRequestType().getValue()));
				bean.setCircleId(String.valueOf(reportInputs.getCircleId()));

				reportInputs.setSessionContext(sessionContext);
			}
			/* call service to count the no of rows */
			/*
			 * int noofpages =
			 * reportService.getTransactionRptPageCount(modernTradeInputs);
			 * logger.debug("Inside searchReportData().. no of
			 * pages..."+noofpages);
			 */

			/* call to set lower & upper bound for Pagination */
			//request.setAttribute("page",bean.getPage());
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all records */
			ArrayList recordList = null ;
			String transId=null;
			transId=request.getParameter("trasactionId");
			
			recordList = reportService.getRechargeTransactionRptData(
					reportInputs, pagination.getLowerBound(), pagination
							.getUpperBound(),false,transId);
			
			TransactionReport transactionRptDto = (TransactionReport) recordList
					.get(0);
			int totalRecords = transactionRptDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages);

			/*
			 * set the list recieved from service into form bean to display on
			 * jsp.
			 */
			bean.setDisplayDetails(recordList);
				
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.debug("Executed... ");
			if (request.getParameter("trasactionId")== null){
			return mapping.findForward(Constants.SHOW_CUSTOMER_RECHARGE_TRANS_REPORT_SUCCESS);
			}else{
				return mapping.findForward(Constants.SHOW_CUSTOMER_RECHARGE_TRANS_REPORT_SUCCESS_WITHID);
			}
			
}
	
	
	/**
	 * This method will use to export the report data to excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward exportCustomerRechargeReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.debug(" Started... ");
		ActionErrors errors = new ActionErrors();
		TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
		String searchFieldName = bean.getSearchFieldName();
		String searchFieldValue = bean.getSearchFieldValue();
		String status = bean.getStatus();
		String requestType = bean.getTransactionType();
		String startDate = bean.getStartDt();
		String endDate = bean.getEndDt();
		String cirlceId =null;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		if (sessionContext.getCircleId() != 0 ) {
			 cirlceId = String.valueOf(sessionContext.getCircleId());
			 bean.setFlagForCircleDisplay(true);
		}
		else{
			cirlceId = bean.getCircleId();
		}

		ReportInputs reportInputs = new ReportInputs();
		// set the form bean parameters to report input DTO.
		reportInputs.setSearchFieldName(searchFieldName);
		reportInputs.setSearchFieldValue(searchFieldValue);
		reportInputs.setStatus(status);
		// if transactionType = -1 then get data for all types of transaction
		// else get the data for the transactionType requested.
		if ("-1".equalsIgnoreCase(requestType.trim())) {
			reportInputs.setRequestType(null);
		} else {
			reportInputs.setRequestType(RequestType.valueOf(requestType));
		}
		reportInputs.setStartDt(startDate);
		reportInputs.setEndDt(endDate);
		reportInputs.setCircleId(Integer.valueOf(cirlceId));

		try {

			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT)) {
				logger.debug(" user not auth to perform this ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			reportInputs.setSessionContext(sessionContext);
			// call report service to get the data to be displayed.

			ReportService reportService = new ReportServiceImpl();
			
			if (request.getParameter("searchType") == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("status") == null
					|| request.getParameter("startDt") == null
					|| request.getParameter("endDt") == null
					|| request.getParameter("cirlceId") == null) {
				reportInputs.setSearchFieldName(searchFieldName);
				reportInputs.setSearchFieldValue(searchFieldValue);
				reportInputs.setSessionContext(sessionContext);
				reportInputs.setStatus(status);
				if ("-1".equalsIgnoreCase(requestType.trim())) {
					reportInputs.setRequestType(null);
				} else {
					reportInputs.setRequestType(RequestType
							.valueOf(requestType));
				}
				reportInputs.setStartDt(startDate);
				reportInputs.setEndDt(endDate);
				reportInputs.setCircleId(Integer.valueOf(cirlceId));
				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				request.setAttribute("status", status);
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDt", startDate);
				request.setAttribute("endDt", endDate);
				request.setAttribute("cirlceId",cirlceId);
			} else {
				reportInputs.setSearchFieldName(request
						.getParameter("searchType"));
				reportInputs.setSearchFieldValue(request
						.getParameter("searchValue"));
				reportInputs.setStatus(request.getParameter("status"));
				if ("-1".equals(request.getParameter("transactionType").trim())) {
					reportInputs.setRequestType(null);
				} else {
					reportInputs.setRequestType(RequestType
							.valueOf(request.getParameter("transactionType")));
				}
				reportInputs.setStartDt(request.getParameter("startDt"));
				reportInputs.setEndDt(request.getParameter("endDt"));
				reportInputs.setCircleId(Integer.valueOf(request.getParameter("cirlceId")));
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("status", request.getParameter("status"));
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDt", request
						.getParameter("startDt"));
				request
						.setAttribute("endDt", request
								.getParameter("endDt"));
				request.setAttribute("cirlceId",cirlceId);
				// to reset data in the jsp on page next previous
				bean.setSearchFieldName(reportInputs.getSearchFieldName());
				bean.setSearchFieldValue(reportInputs
						.getSearchFieldValue());
				bean.setStatus(reportInputs.getStatus());
				bean.setStartDt(reportInputs.getStartDt());
				bean.setEndDt(reportInputs.getEndDt());
				bean.setTransactionType(String.valueOf(reportInputs
						.getRequestType().getValue()));
				bean.setCircleId(String.valueOf(reportInputs.getCircleId()));

				reportInputs.setSessionContext(sessionContext);
			}
	
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all records */
			ArrayList recordList = null ;
			String transId=null;
			transId=request.getParameter("trasactionId");
			
			recordList = reportService.getRechargeTransactionRptData(
					reportInputs, pagination.getLowerBound(), pagination
							.getUpperBound(),true,transId);
			
			TransactionReport transactionRptDto = (TransactionReport) recordList
					.get(0);
			int totalRecords = transactionRptDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages);

			/*
			 * set the list recieved from service into form bean to display on
			 * jsp.
			 */
			bean.setDisplayDetails(recordList);
				
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			if (request.getAttribute("export") == null) {
				return mapping.findForward(Constants.SHOW_CUSTOMER_RECHARGE_TRANS_REPORT_FAILURE);
			} else {
				return null;
			}
		}
		return mapping.findForward(Constants.EXPORT_CUSTOMER_RECHARGE_REPORT_SUCCESS);
	}
	
	
	
	
	
		
	
}