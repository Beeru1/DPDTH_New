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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.apache.struts.upload.FormFile;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.TopupSlabBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UploadTopupSlab;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.CircleTopupService;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.CircleTopupServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

/**
 * Controller class for Circle wise Topup confurations
 * 
 * @version 1.0
 * @author Kumar Saurabh
 */
public class TopupSlabAction extends DispatchAction

{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(TopupSlabAction.class
			.getName());

	/* Local Variables */
	private static final String STATUS_ERROR = "error";

	private static final String INIT_SUCCESS = "initConfig";

	private static final String CREATE_TOPUP_CONFIG_SUCCESS = "dataSaved";

	// private static final String CREATE_TOPUP_CONFIG_FAILURE = "error";
	private static final String CREATE_TOPUP_CONFIG_FAILURE = "errorCreatingSlab";

	private static final String GET_TOPUP_CONFIG_SUCCESS = "editTopupConfig";

	private static final String GET_TOPUP_CONFIG_VIEW = "viewTopupConfig";

	private static final String GET_TOPUP_CONFIG_FAILURE = "error";

	private static final String EDIT_TOPUP_CONFIG_SUCCESS = "dataUpdated";

	// private static final String EDIT_TOPUP_CONFIG_FAILURE = "error";
	private static final String EDIT_TOPUP_CONFIG_FAILURE = "errorEditingSlab";

	private static final String SHOW_TOPUP_CONFIG_SUCCESS = "showList";

	private static final String SHOW_TOPUP_CONFIG_FAILURE = "showList";

	private static final String SHOW_DOWNLOAD_CONFIG_SUCCESS = "listTopUpSlabExport";

	private static final String INIT_UPLOAD_SUCCESS = "showUploadDataJsp";

	private static final String UPLOAD_SUCCESS = "uploadSuccess";

	private static final String UPLOAD_FAILURE = "uploadFailure";
	
	//private static final String FAILED_SUCCESS = "failedSuccess";
	
	private static final String UPLOAD_FAILED_SUCCESS="uploadFailedSuccess";
	
	private static final String UPLOAD_FAILED_FAILURE="uploadFailedFailure";


	/**
	 * This method is called to initialize creation of new topup configuration
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");

		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_CREATE_SLAB_CONFIG)) {
				logger
						.info(" user not auth to perform this ROLE_CREATE_SLAB_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			TopupSlabBean topupSlabBean = (TopupSlabBean) form;
			topupSlabBean.reset(mapping, request);
			/* Populate the list of circles */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleList = circleService.getCircles();

			// check that circle list is to be shown in disabled or enabled form 
			if (sessionContext.getCircleId() != 0) {

				topupSlabBean.setIsCircleAdmin(Constants.ACCOUNT_SHOW_CIRCLE);
				topupSlabBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));

			} else {

				topupSlabBean
						.setIsCircleAdmin(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			}

			// Setting the optionsList in request for display on JSP page
			topupSlabBean.setCircleList(circleList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * This method is used to define topup config slab for a circle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward createTopupSlab(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info(" Started... ");

		try {

			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_CREATE_SLAB_CONFIG)) {
				logger
						.info(" user not auth to perform this ROLE_CREATE_SLAB_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			TopupSlabBean topupSlabBean = (TopupSlabBean) form;
			/* Populating DTO with above retrieved data */

			CircleTopupConfig circleTopupConfig = new CircleTopupConfig();

			if (userSessionContext.getCircleId() != 0) {

				topupSlabBean.setIsCircleAdmin(Constants.ACCOUNT_SHOW_CIRCLE);
				topupSlabBean.setCircleId(String.valueOf(userSessionContext
						.getCircleId()));

			} else {

				topupSlabBean
						.setIsCircleAdmin(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			}

			BeanUtils.copyProperties(circleTopupConfig, topupSlabBean);

			/* Calling Service method to save above data in db */
			CircleTopupService service = new CircleTopupServiceImpl();

			circleTopupConfig.setCreatedBy(userSessionContext.getId());
			circleTopupConfig.setUpdatedBy(userSessionContext.getId());
			service.createCircleTopup(circleTopupConfig);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			/* after error is encountered again show the Circle List */
			try {
				TopupSlabBean topupSlabBean = (TopupSlabBean) form;
				/* Populate the list of circles */
				CircleService circleService = new CircleServiceImpl();
				ArrayList circleList = circleService.getCircles();
				topupSlabBean.setCircleList(circleList);
			} catch (VirtualizationServiceException vse) {
				errors = new ActionErrors();
				logger.error("  Service Exception occured ", vse);
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(STATUS_ERROR);
			}
			return mapping.findForward(CREATE_TOPUP_CONFIG_FAILURE);
		}
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.topup.create_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(CREATE_TOPUP_CONFIG_SUCCESS);
	}

	/**
	 * Method to display top-up configurations corresponding to a circleId
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward showTopupConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		List topupList = new ArrayList();

		try {

			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_SLAB_CONFIG)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_SLAB_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			TopupSlabBean topupSlabBean = (TopupSlabBean) form;
			logger.info(" topupSlabBean.getCircleId()="
					+ topupSlabBean.getCircleId());
			String circle = topupSlabBean.getCircleId();
			// String circleName = topupSlabBean.getCircleName();
			/* Populate the list of circles */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleList = circleService.getCircles();
			topupSlabBean.setCircleList(circleList);

			/* It checks whether the login user is admin or not */
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				topupSlabBean.setCircleId(dto.getCircleId() + "");
				topupSlabBean.setCircleName(dto.getCircleName());
				topupSlabBean.setFlagForCircleDisplay(true);
			}

			logger.info("circle id in session=" + circle);

			// checks whether logged in user is authorized to edit or only view
			// details
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UPDATE_SLAB_CONFIG)) {
				topupSlabBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "
						+ topupSlabBean.getEditStatus());

			} else {
				topupSlabBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			logger.info("Auth..."
					+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_UPDATE_SLAB_CONFIG));

			// Setting the int value of circle
			CircleTopupService service = new CircleTopupServiceImpl();
			/* Calling Service method to fetch topup data from db */
			ReportInputs inputDto = new ReportInputs();
			inputDto.setCircleId(Integer.parseInt(topupSlabBean.getCircleId()));
			inputDto.setStartDt(topupSlabBean.getStartDate());
			inputDto.setEndDt(topupSlabBean.getEndDate());
			inputDto.setStatus(topupSlabBean.getListStatus());

			/** pagination implementation */
			if (request.getParameter("startDt") == null
					|| request.getParameter("endDt") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("listStatus") == null) {
				request.setAttribute("startDate", inputDto.getStartDt());
				request.setAttribute("endDate", inputDto.getEndDt());
				request.setAttribute("circleId", inputDto.getCircleId() + "");
				request.setAttribute("listStatus", inputDto.getStatus());
			} else {
				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request
						.setAttribute("endDate", request
								.getParameter("endDate"));
				request.setAttribute("circleId", request
						.getParameter("circleId")
						+ "");
				request.setAttribute("listStatus", request
						.getParameter("listStatus"));
			}
			/* call service to count the no of rows for TopUpSlabConfig */
			/*
			 * int noofpages = service.getCircleTopupListCount(inputDto);
			 * logger.info("Inside showTopupConfig().. no of pages..." +
			 * noofpages);
			 */

			/* call to set lower & upper bound for Pagination */
			//request.setAttribute("page",topupSlabBean.getPage());
			//topupSlabBean.setPage(request.getParameter("page"));
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* generate the list of TopUpSlabConfig */
			topupList = service.getCircleTopupList(inputDto, pagination
					.getLowerBound(), pagination.getUpperBound());

			logger.info("topupList=" + topupList);
			CircleTopupConfig circleTopupDto = (CircleTopupConfig) topupList
					.get(0);
			int totalRecords = circleTopupDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			
			request.setAttribute("PAGES", noofpages);

			/* set ArrayList of Bean objects to FormBean */
			topupSlabBean.setTopupList(topupList);

		} catch (NumberFormatException numFormatExp) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Number format Exception occured  ", numFormatExp);
			errors.add("errors", new ActionError(numFormatExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_TOPUP_CONFIG_FAILURE);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_TOPUP_CONFIG_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_TOPUP_CONFIG_SUCCESS);
	}

	/**
	 * Method to display top-up configurations corresponding to a circleId for
	 * download purpose
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward downloadTopupConfigList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		List topupList = new ArrayList();

		try {

			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_SLAB_CONFIG)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_SLAB_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			TopupSlabBean topupSlabBean = (TopupSlabBean) form;

			/* Populate the list of circles */
			CircleService circleService = new CircleServiceImpl();

			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				topupSlabBean.setCircleId(dto.getCircleId() + "");
			}
			String circle = topupSlabBean.getCircleId();
			logger.info("Sess=" + topupSlabBean.getCircleId());
			if (circle != null && !circle.equals("")) {
				// Setting the int value of circle
				CircleTopupService service = new CircleTopupServiceImpl();
				/* Calling Service method to fetch topup data from db */
				ReportInputs inputDto = new ReportInputs();
				inputDto.setCircleId(Integer.parseInt(topupSlabBean
						.getCircleId()));
				inputDto.setStartDt(topupSlabBean.getStartDate());
				inputDto.setEndDt(topupSlabBean.getEndDate());
				inputDto.setStatus(topupSlabBean.getListStatus());
				// topupList = service.getCircleTopup(circleId);
				logger.info("Set Circle=" + inputDto.getCircleId());
				topupList = service.getCircleTopupList(inputDto);
				// Setting the topupList in bean for display on JSP page
				topupSlabBean.setTopupList(topupList);
			}

		} catch (NumberFormatException numFormatExp) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Number format Exception occured  ", numFormatExp);
			errors.add("errors", new ActionError(numFormatExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_TOPUP_CONFIG_FAILURE);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_TOPUP_CONFIG_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_DOWNLOAD_CONFIG_SUCCESS);
	}

	/**
	 * Method to initialize and get details of Circle topup configuration to be
	 * edited
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward getEditTopupConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");

		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService
				.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,
						AuthorizationConstants.ROLE_UPDATE_SLAB_CONFIG)) {
			logger
					.info(" user not auth to perform this ROLE_UPDATE_SLAB_CONFIG activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		getTopupConfig(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_TOPUP_CONFIG_SUCCESS);
	}

	/**
	 * Method to initialize and get details of Circle topup configuration to be
	 * viewed
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward getViewTopupConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");

		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_SLAB_CONFIG)) {
			logger
					.info(" user not auth to perform this ROLE_VIEW_SLAB_CONFIG activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		getTopupConfig(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_TOPUP_CONFIG_VIEW);
	}

	/**
	 * Method to initialize and get details of Circle topup configuration
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getTopupConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) throws Exception {
		logger.info(" Started... ");

		try {
			TopupSlabBean topupSlabBean = (TopupSlabBean) form;
			/* Populate the dropdown list of circles */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleList = circleService.getAllCircles();
			topupSlabBean.setCircleList(circleList);

			int intTopupConfigId = Integer.parseInt(topupSlabBean
					.getTopupConfigId());
			CircleTopupConfig circleTopupConfig = new CircleTopupConfig();
			// call service method to get the details of configuration
			String page=topupSlabBean.getPage();
			CircleTopupService service = new CircleTopupServiceImpl();
			circleTopupConfig = service.getCircleTopupOfId(intTopupConfigId);
			BeanUtils.copyProperties(topupSlabBean, circleTopupConfig);
			topupSlabBean.setPage(page);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_TOPUP_CONFIG_FAILURE);
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
		} catch (NumberFormatException numFormatExp) {
			ActionErrors errors = new ActionErrors();
			logger
					.error(
							"  Number format Exception occured  Invalid TopUp Configuration Id ",
							numFormatExp);
			errors.add("errors", new ActionError(numFormatExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_TOPUP_CONFIG_FAILURE);
		}
		logger.info(" Executed... ");
		return null;
	}

	/**
	 * Call to Edit Circle Topup Configuration Information
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward editCircleTopup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		TopupSlabBean topupSlabBean = (TopupSlabBean) form;
		try {

			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UPDATE_SLAB_CONFIG)) {
				logger
						.info(" user not auth to perform this ROLE_UPDATE_SLAB_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			/* Populating DTO with edited data recieved */
			CircleTopupConfig circleTopupConfig = new CircleTopupConfig();
			BeanUtils.copyProperties(circleTopupConfig, topupSlabBean);
			/* Calling Service method to update above data in db */
			CircleTopupService service = new CircleTopupServiceImpl();
			/* Logged in user information from session */

			circleTopupConfig.setUpdatedBy(userSessionContext.getId());

			service.updateCircleTopup(circleTopupConfig);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			/* after error is encountered again show the Circle List */
			try {
				/* Populate the list of circles */
				CircleService circleService = new CircleServiceImpl();
				ArrayList circleList = circleService.getCircles();
				topupSlabBean.setCircleList(circleList);
			} catch (VirtualizationServiceException vse) {
				errors = new ActionErrors();
				logger.error("  Service Exception occured ", vse);
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(STATUS_ERROR);
			}

			return mapping.findForward(EDIT_TOPUP_CONFIG_FAILURE);
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
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.topup.update_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(EDIT_TOPUP_CONFIG_SUCCESS);
	}

	public ActionForward initTopupConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		List topupList = new ArrayList();

		try {

			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_SLAB_CONFIG)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_SLAB_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			TopupSlabBean topupSlabBean = (TopupSlabBean) form;
			logger.info(" topupSlabBean.getCircleId()="
					+ topupSlabBean.getCircleId());
			String circle = topupSlabBean.getCircleId();
			// String circleName = topupSlabBean.getCircleName();
			CircleService circleService = new CircleServiceImpl();
			try {
				/* Populate the list of circles */
				ArrayList circleList = circleService.getCircles();
				topupSlabBean.setCircleList(circleList);
			} catch (VirtualizationServiceException se) {
				ActionErrors errors = new ActionErrors();
				logger.error("  Service Exception occured. ", se);
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(STATUS_ERROR);
			}

			/* It checks whether the login user is admin or not */
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				topupSlabBean.setCircleId(dto.getCircleId() + "");
				topupSlabBean.setCircleName(dto.getCircleName());
				topupSlabBean.setFlagForCircleDisplay(true);
			}

			logger.info("circle id in session=" + circle);
			// checks whether logged in user is authorized to edit or only view
			// details
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UPDATE_SLAB_CONFIG)) {
				topupSlabBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "
						+ topupSlabBean.getEditStatus());

			} else {
				topupSlabBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			logger.info("Auth..."
					+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_UPDATE_SLAB_CONFIG));

			// Setting the int value of circle
			CircleTopupService service = new CircleTopupServiceImpl();
			/* Calling Service method to fetch topup data from db */
			ReportInputs inputDto = new ReportInputs();
			inputDto.setCircleId(Integer.parseInt(topupSlabBean.getCircleId()));
			inputDto.setStartDt(topupSlabBean.getStartDate());
			inputDto.setEndDt(topupSlabBean.getEndDate());
			inputDto.setStatus(topupSlabBean.getStatus());

			/** pagination implementation */
			if (request.getParameter("startDt") == null
					|| request.getParameter("endDt") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("status") == null) {
				request.setAttribute("startDate", inputDto.getStartDt());
				request.setAttribute("endDate", inputDto.getEndDt());
				request.setAttribute("circleId", inputDto.getCircleId() + "");
				request.setAttribute("status", inputDto.getStatus());
			} else {
				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request
						.setAttribute("endDate", request
								.getParameter("endDate"));
				request.setAttribute("circleId", request
						.getParameter("circleId")
						+ "");
				request.setAttribute("status", request.getParameter("status"));
			}
			/* call service to count the no of rows for TopUpSlabConfig */
			/*
			 * int noofpages = service.getCircleTopupListCount(inputDto);
			 * logger.info("Inside showTopupConfig().. no of pages..." +
			 * noofpages);
			 */

			/* call to set lower & upper bound for Pagination */
			// Pagination pagination =
			// VirtualizationUtils.setPaginationinRequest(
			// request);
			//
			// /* generate the list of TopUpSlabConfig */
			// topupList = service.getCircleTopupList(inputDto, pagination
			// .getLowerBound(), pagination.getUpperBound());
			//
			// logger.info("topupList=" + topupList);
			// CircleTopupConfig circleTopupDto=
			// (CircleTopupConfig)topupList.get(0);
			// int totalRecords = circleTopupDto.getTotalRecords();
			// int noofpages = Utility.getPaginationSize(totalRecords);
			// request.setAttribute("PAGES", noofpages);
			/* set ArrayList of Bean objects to FormBean */
			// topupSlabBean.setTopupList(topupList);
		} catch (NumberFormatException numFormatExp) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Number format Exception occured  ", numFormatExp);
			errors.add("errors", new ActionError(numFormatExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_TOPUP_CONFIG_FAILURE);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);

			return mapping.findForward(SHOW_TOPUP_CONFIG_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_TOPUP_CONFIG_SUCCESS);
	}

	/**
	 * Call to display upload jsp page 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward initUploadSlabData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		try {
			TopupSlabBean topupSlabBean = (TopupSlabBean) form;
			/* Populate the list of circles */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleList = circleService.getCircles();
			//get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			// check that circle list is to be shown in disabled or enabled form 
			if (sessionContext.getCircleId() != 0) {
				topupSlabBean.setIsCircleAdmin(Constants.ACCOUNT_SHOW_CIRCLE);
				topupSlabBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));
			} else {
				topupSlabBean
						.setIsCircleAdmin(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			}

			// Setting the optionsList in request for display on JSP page
			topupSlabBean.setCircleList(circleList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}

	/**
	 * Call to upload the file and save the data to the database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward uploadSlabData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started uploadSlabData... ");
		int[] numRows;
		int rowsUpdated;
		int rowsNotUpdated;

		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		TopupSlabBean topupSlabBean = (TopupSlabBean) form;
		UploadTopupSlab topupDto = new UploadTopupSlab();
		try {
			topupDto.setCircleId(Integer.parseInt(topupSlabBean.getCircleId()));
			topupDto.setTransactionType(topupSlabBean.getTransactionType());
			topupDto.setCreatedBy(sessionContext.getId());
			topupDto.setUpdatedBy(sessionContext.getId());
			FormFile file = topupSlabBean.getUploadFile();
			String path = request.getSession().getServletContext().getRealPath(
					file.getFileName());
			String contents = new String(file.getFileData());
			File fileRef = new File(path);
			BufferedWriter brWriter = new BufferedWriter(
					new FileWriter(fileRef));
			brWriter.write(contents);
			brWriter.close();
			logger.info("Path" + path);
			topupDto.setFilePath(path);
			CircleTopupService service = new CircleTopupServiceImpl();
			numRows = service.uploadSlabData(topupDto);
			rowsUpdated = numRows[0];
			rowsNotUpdated = numRows[1];
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured ", se);
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(UPLOAD_FAILURE);
		} catch (IOException ioExp) {
			ActionErrors errors = new ActionErrors();
			logger.error(" IOException occured while writing the file", ioExp);
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					"messages.topup.create_failure"));
			saveErrors(request, errors);
			return mapping.findForward(UPLOAD_FAILURE);
		}
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.topup.insert_success", rowsUpdated));
		if (rowsNotUpdated > 0) {
			topupSlabBean.setViewFailedRecords(true);
			errors.add("Failure", new ActionError(
					"messages.topup.insert_failure", rowsNotUpdated));
		}else{
			topupSlabBean.setViewFailedRecords(false);
		}
		saveErrors(request, errors);
		topupSlabBean.setHdnCircleId(topupSlabBean.getCircleId());
		return mapping.findForward(UPLOAD_SUCCESS);

	}

	/**
	 * Method  to get details of incorrect records
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward getTopupConfigFailData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started getTopupConfigFailData... ");
		List topupIncorrectDataList = new ArrayList();

		try {
			TopupSlabBean topupSlabBean = (TopupSlabBean) form;

			int circleId = Integer.parseInt(topupSlabBean.getHdnCircleId());
			// call service method to get the details of configuration
			
			CircleTopupService service = new CircleTopupServiceImpl();
			topupIncorrectDataList = service.getTopupConfigFailData(circleId);
			topupSlabBean.setTopupList(topupIncorrectDataList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(UPLOAD_FAILED_FAILURE);
		} catch (NumberFormatException numFormatExp) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Number format Exception occured ", numFormatExp);
			errors.add("errors", new ActionError(numFormatExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(UPLOAD_FAILED_FAILURE);
		}
		logger.info(" Executed... ");

		return mapping.findForward(UPLOAD_FAILED_SUCCESS);
	}


}