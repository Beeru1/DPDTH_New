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
import org.apache.struts.actions.DispatchAction;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.TransactionRptA2AFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account2AccountReport;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.ReportService;
import com.ibm.virtualization.recharge.service.impl.ReportServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

/**
 * Controller class for Account to Account Transaction View
 * 
 * @author Paras
 */
public class TransactionViewA2AAction extends DispatchAction {

	/* Logger for this Action class. */

	private static Logger logger = Logger.getLogger(TransactionViewA2AAction.class
			.getName());
	
	
	
	/**
	 * This method initializes and opens the transactionViewA2A.jsp page
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
		ActionErrors errors = new ActionErrors();
		/* Logged in user information from session */
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_A2A_TRANSACTIONS)) {
			logger.info(" user not auth to perform this ROLE_VIEW_A2A_TRANSACTIONS activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		return mapping.findForward(Constants.SHOW_A2A_REPORT_CRITERIA);
	}

	

	

	/**
	 * This method will use to get the view according to parameters provided
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward searchReportData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		
		
		
		TransactionRptA2AFormBean bean = (TransactionRptA2AFormBean) form;
		String searchFieldName = bean.getSearchFieldName();
		String searchFieldValue = bean.getSearchFieldValue();
		String status = bean.getStatus();
		String startDate=bean.getStartDt();
		String endDate=bean.getEndDt();
		
		ReportInputs reportInputs = new ReportInputs();
		// set the form bean parameters to report input DTO.
		reportInputs.setSearchFieldName(searchFieldName);
		reportInputs.setSearchFieldValue(searchFieldValue);
		reportInputs.setStatus(status);
		reportInputs.setStartDt(startDate);
		reportInputs.setEndDt(endDate);
		
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_A2A_TRANSACTIONS)) {
				logger.info(" user not auth to perform this ROLE_VIEW_A2A_TRANSACTIONS activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			reportInputs.setSessionContext(sessionContext);
			// call report service to get the data to be displayed.
			
			ReportService reportService = new ReportServiceImpl();
			if(request.getParameter("searchType")==null || request.getParameter("searchValue")==null
					|| request.getParameter("status")==null || request.getParameter("startDate")==null 
					|| request.getParameter("endDate")==null){
				reportInputs.setSearchFieldName(searchFieldName);
				reportInputs.setSearchFieldValue(searchFieldValue);
				reportInputs.setSessionContext(sessionContext);
				reportInputs.setStatus(status);
				reportInputs.setStartDt(startDate);
				reportInputs.setEndDt(endDate);
				request.setAttribute("searchType",searchFieldName);
				request.setAttribute("searchValue",searchFieldValue);
				request.setAttribute("status",status);
				request.setAttribute("startDate",startDate);
				request.setAttribute("endDate",endDate);
			}else{
				reportInputs.setSearchFieldName(request.getParameter("searchType"));
				reportInputs.setSearchFieldValue(request.getParameter("searchValue"));
				reportInputs.setStatus(request.getParameter("status"));
				reportInputs.setStartDt(request.getParameter("startDate"));
				reportInputs.setEndDt(request.getParameter("endDate"));
				request.setAttribute("searchType",request.getParameter("searchType"));
				request.setAttribute("searchValue",request.getParameter("searchValue"));
				request.setAttribute("status",request.getParameter("status"));
				request.setAttribute("startDate",request.getParameter("startDate"));
				request.setAttribute("endDate",request.getParameter("endDate"));
				
				bean.setSearchFieldName(reportInputs.getSearchFieldName());
				bean.setSearchFieldValue(reportInputs.getSearchFieldValue());
				bean.setStatus(reportInputs.getStatus());
				bean.setStartDt(reportInputs.getStartDt());
				bean.setEndDt(reportInputs.getEndDt());
				reportInputs.setSessionContext(sessionContext);
			}
			/* call service to count the no of rows */
			/*int noofpages=reportService.getTransactionReportPageCount(modernTradeInputs);
			logger.info("Inside searchReportData().. no of pages..."+noofpages);*/
			
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
			
			/* call service to find all records */
			//ArrayList recordList = reportService.getTransactionRptA2AData(modernTradeInputs,pagination.getLowerBound(),pagination.getUpperBound());
			ArrayList recordList =reportService.getTransactionRptA2AData(reportInputs,pagination.getLowerBound(),pagination.getUpperBound(),false);
			
			Account2AccountReport acc2accDto = (Account2AccountReport)recordList.get(0);
			int totalRecords = acc2accDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages);
			
			/* set the list recieved from service into form bean to display on jsp. */
			bean.setDisplayDetails(recordList);
			
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.SHOW_A2A_REPORT_FAILURE);
		}
		logger.info("Executed... ");
		return mapping.findForward(Constants.SHOW_A2A_REPORT_SUCCESS);
	}
	
	
}