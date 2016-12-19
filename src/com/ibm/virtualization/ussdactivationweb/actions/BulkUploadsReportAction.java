/**************************************************************************
 * File     : BulkBizUserAssoService.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.actions;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.Constant;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.beans.BulkUploadBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.BulkBizUserAssoDAO;
import com.ibm.virtualization.ussdactivationweb.dao.BulkBizUserCreationDAO;
import com.ibm.virtualization.ussdactivationweb.dao.BulkSubscriberDAO;
import com.ibm.virtualization.ussdactivationweb.dao.RegistrationOfAllUsersDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 * 
 */
public class BulkUploadsReportAction extends DispatchAction {

	/** getting logger refrence * */
	private static final Logger logger = Logger
			.getLogger(BulkUploadsReportAction.class.toString());

	/**
	 * This method initiates the jsp on the page
	 * 
	 * @param mapping :
	 *            ActionMapping
	 * @param form :
	 *            ActionForm
	 * @param request :
	 *            HttpServletRequest
	 * @param response :
	 *            HttpServletResponse
	 * @return forward : ActionForward
	 * @throws Exception
	 * 
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.debug("Entering BulkUploadsReportAction : init() ");
		ActionForward forward = new ActionForward();
		BulkUploadBean bulkUploadBean = (BulkUploadBean) form;
		RegistrationOfAllUsersDAO allRegDAO = new RegistrationOfAllUsersDAO();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		String adminType = null;
		ActionErrors errors = new ActionErrors();
		try {
			if (userBean.getCircleId() == null) {
				bulkUploadBean.setUserRole(Constants.SUPER_ADMIN);
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl
						.getCircleList());
				adminType = Constants.SUPER_ADMIN;

			} if(userBean.getUserType().equalsIgnoreCase(Constants.circleLogin)){
				bulkUploadBean.setUserRole(Constants.CIRCLE_ADMIN);
				bulkUploadBean.setHubCode(userBean.getHubCode());
				bulkUploadBean.setCircleCode(userBean.getCircleId());
				adminType = Constants.CIRCLE_ADMIN;
			}
			
			if(userBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
				bulkUploadBean.setUserRole(Constants.ZoneUser);
				bulkUploadBean.setHubCode(userBean.getHubCode());
				bulkUploadBean.setCircleCode(userBean.getCircleId());
				adminType = Constants.Zone_User;
			}
			if ((Constants.BulkAssociation).equalsIgnoreCase(String
					.valueOf(bulkUploadBean.getReportType()))) {
				bulkUploadBean.setTypeOfUserList(allRegDAO
						.getUserTypeList(adminType));
			}
			logger.info(" for Bulk Upload Report JSP is initiated for user "+ userBean.getLoginId());
		} catch (Exception e) {
			logger.error(
					"Exception occured in BulkUploadsReportAction : init()", e);
			logger.info(userBean.getLoginId()+" Exception occured in initialisation of Bulk Upload Report JSP ");
			errors.add("exception", new ActionError("exception"));
			saveErrors(request, errors);
			return mapping.findForward("Failure");
		}

		logger.debug("Exiting BulkUploadsReportAction : init() ");
		forward = mapping.findForward("bizUserReport");
		return forward;
	}

	/**
	 * This method is brings the list of files for bulk business user
	 * associations
	 * 
	 * @param mapping :
	 *            ActionMapping
	 * @param form :
	 *            ActionForm
	 * @param request :
	 *            HttpServletRequest
	 * @param response :
	 *            HttpServletResponse
	 * @return forward : ActionForward
	 * @throws Exception
	 * 
	 */
	public ActionForward bizUserAssoReportList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger
				.debug("Entering BulkUploadsReportAction : bizUserAssoReportList() ");
		ActionForward forward = new ActionForward();
		BulkUploadBean bulkUploadBean = (BulkUploadBean) form;
		BulkBizUserAssoDAO bulkBizUserAssoDAO = new BulkBizUserAssoDAO();
		ActionErrors errors = new ActionErrors();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		String circleCode = "";
		try {
			int noofpages = 0;
			List fileList;
			if (userBean.getCircleId() == null) {
				circleCode = bulkUploadBean.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
			}

			noofpages = bulkBizUserAssoDAO.countFilesList(circleCode,
					bulkUploadBean.getTypeOfUserId(), bulkUploadBean
							.getStatus());
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);
			if (userBean == null) {
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(request, errors);
				logger.info(userBean.getLoginId() + " session expired");
				forward = mapping.findForward(Constants.FAILURE1);
			} else {
				fileList = bulkBizUserAssoDAO.getfilesList(circleCode,
						bulkUploadBean.getTypeOfUserId(), bulkUploadBean
								.getStatus(), pagination.getLowerBound(),
						pagination.getUpperBound(),userBean.getLoginId(),userBean.getUserType());
				logger.info("List of reports has been fetched by "+userBean.getLoginId());
				if (!fileList.isEmpty()) {
					bulkUploadBean.setReportList(fileList);
				} else {
					errors.add("bulk.no.file", new ActionError("bulk.no.file"));
					saveErrors(request, errors);
				}
			}
			init(mapping, form, request, response);
		} catch (Exception e) {
			logger.error("Exception in BulkUploadsReportAction : bizUserAssoReportList()",e);
			errors.add("exception", new ActionError(
					"exception"));
			saveErrors(request, errors);
			logger.info(userBean.getLoginId() + " there was an exception during fetching the report list.");
			forward = mapping.findForward("Failure");
		}
		logger
				.debug("Exiting BulkUploadsReportAction : bizUserAssoReportList() ");
		forward = mapping.findForward("bizUserReport");
		return forward;

	}

	/**
	 * This method is brings the list of files for bulk subscriber upload
	 * 
	 * @param mapping :
	 *            ActionMapping
	 * @param form :
	 *            ActionForm
	 * @param request :
	 *            HttpServletRequest
	 * @param response :
	 *            HttpServletResponse
	 * @return forward : ActionForward
	 * @throws Exception
	 * 
	 */
	public ActionForward subscriberReportList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger
				.debug("Entering BulkUploadsReportAction : subscriberReportList() ");
		ActionForward forward = new ActionForward();
		BulkUploadBean bulkUploadBean = (BulkUploadBean) form;
		BulkSubscriberDAO bulkSubscriberDAO = new BulkSubscriberDAO();
		ActionErrors errors = new ActionErrors();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		String circleCode = "";
		try {
			int noofpages = 0;
			List fileList;
			if (userBean.getCircleId() == null) {
				circleCode = bulkUploadBean.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
			}
			noofpages = bulkSubscriberDAO.countFilesListForSubscriber(
					circleCode, bulkUploadBean.getStatus());
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);
			if (userBean == null) {
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(request, errors);
				logger.info(userBean.getLoginId() + " session expired");
				forward = mapping.findForward(Constants.FAILURE1);
			} else {
				fileList = null;/*bulkSubscriberDAO.getfilesListForSubscriber(
						circleCode, bulkUploadBean.getStatus(), pagination
								.getLowerBound(), pagination.getUpperBound());*/
				logger.info("List of reports have been fetched by "+userBean.getLoginId());
				if (!fileList.isEmpty()) {
					bulkUploadBean.setReportList(fileList);
				} else {
					errors.add("bulk.no.file", new ActionError("bulk.no.file"));
					saveErrors(request, errors);
				}
			}
			init(mapping, form, request, response);
		} catch (Exception e) {
			logger.error("Exception in BulkUploadsReportAction : subscriberReportList()",e);
			errors.add("exception", new ActionError(
			"exception"));
			saveErrors(request, errors);
			logger.info(userBean.getLoginId() + " there was an exception during fetching the report list.");
			forward = mapping.findForward("Failure");
		}
		logger
				.debug("Exiting BulkUploadsReportAction : subscriberReportList() ");
		forward = mapping.findForward("bizUserReport");
		return forward;

	}
	
	public ActionForward initBulkSubDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Entering method-init()");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		Calendar calendar = Calendar.getInstance();
		LinkedHashMap dateWiseMap = new LinkedHashMap();
		
		try {
	
//			 0-7 -- 7 days before report
			for (int i = 0; i < Integer.parseInt(Utility.getValueFromBundle(
					Constants.DAILY_REPORT_PREVIOUS_DAYS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE)); i++) {
				if (i == 0) {
					if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
							Constants.DAILY_REPORT_INCLUDE_CURRENT_DAY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						// This line added because current day is not required.
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						}
						dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					} else {
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					}
				}
			
			request.setAttribute("dateWiseMap", dateWiseMap);
		
		} catch (DAOException e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}catch (Exception e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}
		forward = mapping.findForward("SubDelReport");
		logger.info("Exiting method- init()");
		return forward;
	}
	
	/**
	 * This method is brings the list of files for bulk business user upload
	 * 
	 * @param mapping :
	 *            ActionMapping
	 * @param form :
	 *            ActionForm
	 * @param request :
	 *            HttpServletRequest
	 * @param response :
	 *            HttpServletResponse
	 * @return forward : ActionForward
	 * @throws Exception
	 * 
	 */
	public ActionForward bulkBizUserReportList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger
				.debug("Entering BulkUploadsReportAction : bulkBizUserReportList() ");
				
		ActionForward forward = new ActionForward();
		BulkUploadBean bulkUploadBean = (BulkUploadBean) form;
		BulkBizUserCreationDAO bulkBizUserCreationDAO = new BulkBizUserCreationDAO();
		ActionErrors errors = new ActionErrors();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		String circleCode = "";
		
		StringBuffer contains = new StringBuffer("");
		
		if (userBean.getUserType().equalsIgnoreCase(Constants.AdminType))
		{
			contains.append(Constants.ED);
			/*contains.append(Constants.CEO).append(",").append(Constants.SALES_HEAD).append(",").append(Constants.ZBM)
				.append(",").append(Constants.ZSM).append(",").append(Constants.TM).append(",").append(Constants.DISTIBUTOR)
				.append(",").append(Constants.FOS).append(",").append(Constants.DEALER);*/
		}
		else if (userBean.getUserType().equalsIgnoreCase(Constants.circleLogin))
		{
			contains.append(Constants.CEO);
			/*contains.append(Constants.CEO).append(",").append(Constants.SALES_HEAD).append(",").append(Constants.ZBM)
			.append(",").append(Constants.ZSM).append(",").append(Constants.TM).append(",").append(Constants.DISTIBUTOR)
			.append(",").append(Constants.FOS).append(",").append(Constants.DEALER);*/
		}
		else 
		{
			contains.append(Constants.ZBM);
			/*contains.append(Constants.ZBM)
			.append(",").append(Constants.ZSM).append(",").append(Constants.TM).append(",").append(Constants.DISTIBUTOR)
			.append(",").append(Constants.FOS).append(",").append(Constants.DEALER);*/
		}
		
			
		try {
			int noofpages = 0;
			List fileList;
			if (userBean.getCircleId() == null) {
				circleCode = bulkUploadBean.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
			}
			noofpages = bulkBizUserCreationDAO.countFilesListForBizUserCreation(
					circleCode, bulkUploadBean.getStatus(),  contains.toString().trim());
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);
			if (userBean == null) {
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(request, errors);
				logger.info(userBean.getLoginId() + " session expired");
				forward = mapping.findForward(Constants.FAILURE1);
			} else {
				fileList = null;/*bulkBizUserCreationDAO.getfilesListForBizUser(
						circleCode, bulkUploadBean.getStatus(), pagination
								.getLowerBound(), pagination.getUpperBound(), contains.toString().trim());*/
				logger.info("List of reports have been fetched by "+userBean.getLoginId());
				if (!fileList.isEmpty()) {
					bulkUploadBean.setReportList(fileList);
				} else {
					errors.add("bulk.no.file", new ActionError("bulk.no.file"));
					saveErrors(request, errors);
				}
			}
			init(mapping, form, request, response);
		} catch (Exception e) {
			logger.error("Exception in BulkUploadsReportAction : bulkBizUserReportList()",e);
			errors.add("exception", new ActionError(
			"exception"));
			saveErrors(request, errors);
			logger.info(userBean.getLoginId() + " there was an exception during fetching the report list.");
			forward = mapping.findForward("Failure");
		}
		logger
				.debug("Exiting BulkUploadsReportAction : bulkBizUserReportList() ");
		request.setAttribute(Constants.BULK_BIZ_USER_REPORT_ROLE_ATTRIBUTE, Constants.BULK_BIZ_USER_REPORT_ROLE_YES_ATTRIBUTE);
		forward = mapping.findForward("bizUserReport");
		return forward;

	}

}
