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
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.ReportService;
import com.ibm.virtualization.recharge.service.impl.ReportServiceImpl;

/**
 * Controller class for Account to Account Transaction Report
 * 
 * @author Paras
 */
public class TransferSummaryRptA2AAction extends DispatchAction {

	/* Logger for this Action class. */

	private static Logger logger = Logger
			.getLogger(TransferSummaryRptA2AAction.class.getName());

	/**
	 * This method initializes and opens the transferSummaryRptA2A.jsp page
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
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT)) {
			logger.info(" user not auth to perform this ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		return mapping
				.findForward(Constants.SHOW_A2A_TFR_SUMMARY_REPORT_CRITERIA);
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
		String startDate = bean.getStartDt();
		String endDate = bean.getEndDt();

		ReportInputs reportInputs = new ReportInputs();
		// set the form bean parameters to report input DTO.
		reportInputs.setSearchFieldName(searchFieldName);
		reportInputs.setSearchFieldValue(searchFieldValue);
		reportInputs.setStatus(status);
		reportInputs.setStartDt(startDate);
		reportInputs.setEndDt(endDate);
		reportInputs.setRequestType(RequestType.ACCOUNT);
		//modernTradeInputs.setRequestType(RequestType.RECHARGE);

		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			reportInputs.setSessionContext(sessionContext);
			// call report service to get the data to be displayed.

			ReportService reportService = new ReportServiceImpl();
			reportInputs.setSessionContext(sessionContext);
			ArrayList recordList = reportService
					.getTransferSummaryRptA2ADataAll(reportInputs);

			// set the list recieved from service into form bean to display on
			// jsp.
			
			
			bean.setDisplayDetails(recordList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping
					.findForward(Constants.SHOW_A2A_TFR_SUMMARY_REPORT_FAILURE);
		}
		logger.info("Executed... ");
		return mapping
				.findForward(Constants.SHOW_A2A_TFR_SUMMARY_REPORT_SUCCESS);
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

		ReportInputs reportInputs = new ReportInputs();
		// set the form bean parameters to report input DTO.
		reportInputs.setSearchFieldName(bean.getSearchFieldName());
		reportInputs.setSearchFieldValue(bean.getSearchFieldValue());
		reportInputs.setStatus(bean.getStatus());
		reportInputs.setStartDt(bean.getStartDt());
		reportInputs.setEndDt(bean.getEndDt());
		reportInputs.setRequestType(RequestType.ACCOUNT);
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			reportInputs.setSessionContext(sessionContext);
			// call report service to get the data to be displayed.

			ReportService reportService = new ReportServiceImpl();
			ArrayList recordList = reportService
					.getTransferSummaryRptA2ADataAll(reportInputs);

			// set the list recieved from service into form bean to display on
			// jsp.
			bean.setDisplayDetails(recordList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping
					.findForward(Constants.SHOW_A2A_TFR_SUMMARY_REPORT_FAILURE);
		}
		logger.info("Executed... ");
		return mapping
				.findForward(Constants.EXPORT_A2A_TFR_SUMMARY_REPORT_SUCCESS);
	}

}