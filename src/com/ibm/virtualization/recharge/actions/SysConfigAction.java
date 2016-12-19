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
import com.ibm.virtualization.recharge.beans.SysConfigFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.SysConfigService;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.SysConfigServiceImpl;

/**
 * Controller class for System Configuration.
 * 
 * @author Navroze
 */

public class SysConfigAction extends DispatchAction

{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(SysConfigAction.class
			.getName());

	/* Variables used for mapping */
	private static final String INIT_SUCCESS = "initSuccess";

	private static final String INIT_FAILURE = "error";

	private static final String CALL_SEARCH_SUCCESS = "showSearchSysConfigJsp";

	private static final String CALL_SEARCH_FAILURE = "error";

	private static final String SEARCH_SUCCESS = "showListJsp";

	private static final String SEARCH_FAILURE = "callSearchSysConfig";

	private static final String INSERT_SUCCESS = "openSysConfig";

	private static final String INSERT_FAILURE = "openSysConfig";

	private static final String GET_SYSCONFIG_EDIT_SUCCESS = "showEditJsp";

	private static final String GET_SYSCONFIG_FAILURE = "error";

	private static final String UPDATE_SUCCESS = "showSearchJsp";

	private static final String UPDATE_FAILURE = "showEditJsp";

	private static final String UNAUTHORIZED_ACCESS = "loginFailure";
	
	private static final String CREATE_FAILURE = "createfail";

	// private static final String GET_SYSCONFIG_DATA_FOR_CIRCLE_ADMIN = "searchSysConfig";
	
	private static final String GET_SYSCONFIG_DATA_FOR_CIRCLE_ADMIN = "showSearchSysConfigJsp";
	
	
	private static final String  GET_SYSCONFIG_VIEW_SUCCESS="viewSysConfig";

	/**
	 * init method is called to populate Circle Dropdown in create jsp
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

		/* To populate Circle Dropdown in Create JSP */
		try {
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CREATE_SYS_CONFIG)) {
				logger.info(" user not auth to perform this ROLE_CREATE_SYS_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			CircleService circleService = new CircleServiceImpl();
			SysConfigFormBean sysConfigFormBean = (SysConfigFormBean) form;
			sysConfigFormBean.reset(mapping, request);
			ArrayList<Circle> activeCircleDtoList = new ArrayList<Circle>();
			

			if (null != userSessionContext) {
				
				
				
				
				if (0 < userSessionContext.getCircleId()) {
					/*
					 * Call getCircle(int circleId) of CircleService to get
					 * details of the circle with specified circleId.
					 */
					activeCircleDtoList.add(circleService
							.getCircle(userSessionContext.getCircleId()));
					sysConfigFormBean.setCircleId(String
							.valueOf(userSessionContext.getCircleId()));
					sysConfigFormBean.setIsCircleAdmin("Y");
					sysConfigFormBean.setDisabledCircle(String
							.valueOf(userSessionContext.getCircleId()));
					/* set ArrayList of circleList */
					request.setAttribute("circleList", activeCircleDtoList);
				} else {
					/* Get list of Circle ID from Service Layer */
					activeCircleDtoList = circleService.getCircles();
					request.setAttribute("circleList", activeCircleDtoList);
				}
			}
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured. redirected to  : "
					+ INIT_FAILURE, se);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_FAILURE);
		}
		logger.info("  successful  redirected to : " + INIT_SUCCESS);
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * Call to insert system configuration details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward createSysConfigData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		SysConfigFormBean sysConfigFormBean = (SysConfigFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {
			
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CREATE_SYS_CONFIG)) {
				logger.info(" user not auth to perform this ROLE_CREATE_SYS_CONFIG activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			SysConfigService sysConfigService = new SysConfigServiceImpl();
			SysConfig sysConfig = new SysConfig();
			

			/* Populate DTO object with Bean values */
			/* BeanUtil to populate Circle DTO with Form Bean data */
			try {
				if (0 < userSessionContext.getCircleId()) {
					sysConfigFormBean.setCircleId(String
							.valueOf(userSessionContext.getCircleId()));
				}
				
				BeanUtils.copyProperties(sysConfig, sysConfigFormBean);
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

			sysConfig.setCreatedBy(userSessionContext.getId());
			sysConfig.setUpdatedBy(userSessionContext.getId());

			/* Call to the method of service */
			logger
					.info(" Request to define a new system configuration by user "
							+ userSessionContext.getId());
			sysConfigService.defineSystemConfiguration(sysConfig);

		} catch (VirtualizationServiceException se) {
			CircleService circleService = new CircleServiceImpl();
			ArrayList<Circle> activeCircleDtoList = circleService.getCircles();
			request.setAttribute("circleList", activeCircleDtoList);
			
			logger.error("  caught Service Exception   redirected to  "
					+ INSERT_FAILURE, se);
			errors.add("errors", new ActionError(se.getMessage()));
			//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CREATE_FAILURE);
		}
		/*
		 * reset form bean values if success and again show create account page
		 * with message
		 */
		errors.add("message.sysconfig", new ActionError(
				"message.sysconfig.insert_success"));
		saveErrors(request, errors);
		sysConfigFormBean.reset(mapping, request);

		logger.info(" Executed... ");
		return mapping.findForward(INSERT_SUCCESS);
	}

	/**
	 * This method opens system config search page with circle drop down
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward callSearchSysConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		/* To populate Circle Dropdown in Search JSP */
		try {
			
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_SYS_CONFIG)) {
				logger.info(" user not auth to perform this ROLE_VIEW_SYS_CONFIG activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			CircleService circleService = new CircleServiceImpl();
			SysConfigFormBean sysConfigFormBean = (SysConfigFormBean) form;
			ArrayList<Circle> activeCircleDtoList = new ArrayList<Circle>();
			
			if (null != userSessionContext) {
				if (0 < userSessionContext.getCircleId()) {
					/*
					 * Call getCircle(int circleId) of CircleService to get
					 * details of the circle with specified circleId.
					 */
					activeCircleDtoList.add(circleService
							.getCircle(userSessionContext.getCircleId()));
					sysConfigFormBean.setCircleId(String
							.valueOf(userSessionContext.getCircleId()));
					sysConfigFormBean.setIsCircleAdmin("Y");
					sysConfigFormBean.setDisabledCircle(String
							.valueOf(userSessionContext.getCircleId()));
					/* set ArrayList of circleList */
					request.setAttribute("circleList", activeCircleDtoList);
					return mapping.findForward(GET_SYSCONFIG_DATA_FOR_CIRCLE_ADMIN);
				} else {
					/* Get list of Circle ID from Service Layer */
					activeCircleDtoList = circleService.getCircles();
					request.setAttribute("circleList", activeCircleDtoList);
				}
			}
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CALL_SEARCH_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(CALL_SEARCH_SUCCESS);
	}

	/**
	 * This method search searchSysConfig acording to selected circle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward searchSysConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();

		/* Search an SysConfig */
		try {
			
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_SYS_CONFIG)) {
				logger.info(" user not auth to perform this ROLE_VIEW_SYS_CONFIG activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			CircleService circleService = new CircleServiceImpl();
			SysConfigFormBean sysConfigFormBean = (SysConfigFormBean) form;
			ArrayList<Circle> activeCircleDtoList = new ArrayList<Circle>();
	
			logger.info("Auth..."+authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_SYS_CONFIG));
			
			if (authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_UPDATE_SYS_CONFIG)) {
				sysConfigFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "+sysConfigFormBean.getEditStatus());
			}else{
				sysConfigFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			

			if (null != userSessionContext) {
				if (0 < userSessionContext.getCircleId()) {
					/*
					 * Call getCircle(int circleId) of CircleService to get
					 * details of the circle with specified circleId.
					 */
					activeCircleDtoList.add(circleService
							.getCircle(userSessionContext.getCircleId()));
					sysConfigFormBean.setSelectedCircleId(String
							.valueOf(userSessionContext.getCircleId()));
					sysConfigFormBean.setIsCircleAdmin("Y");
					sysConfigFormBean.setDisabledCircle(String
							.valueOf(userSessionContext.getCircleId()));
					/* set ArrayList of circleList */
					request.setAttribute("circleList", activeCircleDtoList);
				
				} else {
					/* Get list of Circle ID from Service Layer */
					activeCircleDtoList = circleService.getCircles();
					request.setAttribute("circleList", activeCircleDtoList);
				}	
			}	
			
			SysConfigService sysConfigService = new SysConfigServiceImpl();
			
			ArrayList sysConfigDtoList = new ArrayList();
			// if logged in user is circle admin
			if (null != userSessionContext) {
				if (0 < userSessionContext.getCircleId()) {
					sysConfigFormBean.setSelectedCircleId(sysConfigFormBean
							.getDisabledCircle());
				}
			}
			/* get SysConfig Details based on Circle Id */
			sysConfigDtoList = sysConfigService
					.getSystemConfigurationList(Integer
							.parseInt(sysConfigFormBean.getSelectedCircleId()));
			if (sysConfigDtoList.size() == 0) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"errors.sys.norecord_found"));
				saveErrors(request, errors);
				return mapping.findForward(SEARCH_FAILURE);
			}

			/* set the list into form bean */
			
			sysConfigFormBean.setCircleId(sysConfigFormBean.getSelectedCircleId());
			sysConfigFormBean.setSysConfigList(sysConfigDtoList);
		} catch (VirtualizationServiceException vse) {
			logger.error("  Service Exception Occured.", vse);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.sys.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward(SEARCH_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SEARCH_SUCCESS);
	}
	
	/**
	 * Call to get System Configuration Information to Edit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getEditSysConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_SYS_CONFIG)) {
			logger.info(" user not auth to perform this ROLE_UPDATE_SYS_CONFIG activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		getSysConfigData(mapping, form, request,response);
		logger.info(" Executed... ");
		return mapping.findForward(GET_SYSCONFIG_EDIT_SUCCESS);
	}

	/**
	 * Call to get System Configuration Information to View
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getViewSysConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_SYS_CONFIG)) {
			logger.info(" user not auth to perform this ROLE_VIEW_SYS_CONFIG activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		getSysConfigData(mapping, form, request,response);
		logger.info(" Executed... ");
		return mapping.findForward(GET_SYSCONFIG_VIEW_SUCCESS);
	}


	public ActionForward getSysConfigData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SysConfigFormBean sysConfigFormBean = (SysConfigFormBean) form;
		SysConfigService sysConfigservice = new SysConfigServiceImpl();
		/* To populate Circle Dropdown in Create JSP */
		try {
			CircleService circleService = new CircleServiceImpl();
			ArrayList<Circle> activeCircleDtoList = new ArrayList<Circle>();
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			if (null != userSessionContext) {
				if (0 < userSessionContext.getCircleId()) {
					/*
					 * Call getCircle(int circleId) of CircleService to get
					 * details of the circle with specified circleId.
					 */
					activeCircleDtoList.add(circleService
							.getCircle(userSessionContext.getCircleId()));
					sysConfigFormBean.setCircleId(String
							.valueOf(userSessionContext.getCircleId()));
					sysConfigFormBean.setIsCircleAdmin("Y");
					sysConfigFormBean.setDisabledCircle(String
							.valueOf(userSessionContext.getCircleId()));
				} else {
					/* Get list of Circle ID from Service Layer */
					activeCircleDtoList = circleService.getCircles();
					//sysConfigFormBean.setIsCircleAdmin("Y");
				}
			}
			/* set ArrayList of circleList */
			request.setAttribute("circleList", activeCircleDtoList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured.", se);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(INIT_FAILURE);
		}
		try {
			/* Get the system configuration detail to edit based on SysConfigId */
			SysConfig sysConfig = sysConfigservice
					.getSystemConfigurationDetails(Long
							.parseLong(sysConfigFormBean.getSysConfigId()));
			try {
				/* BeanUtil to populate Form Bean with DTO data */
				BeanUtils.copyProperties(sysConfigFormBean, sysConfig);
				
				
				
			} catch (IllegalAccessException illegalAccessExp) {
				logger
						.error(
								"  caught Exception while using BeanUtils.copyProperties()",
								illegalAccessExp);
			} catch (InvocationTargetException invocationTargetExp) {
				logger
						.error(
								"  caught Exception while using BeanUtils.copyProperties()",
								invocationTargetExp);
			}
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ GET_SYSCONFIG_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_SYSCONFIG_FAILURE);
		}
	
		return null;
	}

	/**
	 * This method updates the edited system configuration details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward updateSystemConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		/* To populate Circle Dropdown in Create JSP */
		try {
			
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_SYS_CONFIG)) {
				logger.info(" user not auth to perform this ROLE_UPDATE_SYS_CONFIG activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			CircleService circleService = new CircleServiceImpl();

			/* Get list of Circle ID from Service Layer */
			ArrayList activeCircleDtoList = circleService.getCircles();
			request.setAttribute("circleList", activeCircleDtoList);
		} catch (VirtualizationServiceException se) {
			logger.error("  Service Exception occured. ", se);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(INIT_FAILURE);
		}
		SysConfigFormBean updateBean = (SysConfigFormBean) form;
		try {
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			SysConfig sysConfig = new SysConfig();
			SysConfigService sysConfigService = new SysConfigServiceImpl();
			
			/* Set the edited data from Form bean to DTO */
			try {
				
				if (0 < userSessionContext.getCircleId()) {
					updateBean.setCircleId(String.valueOf(userSessionContext.getCircleId()));
					
				}
				
				BeanUtils.copyProperties(sysConfig, updateBean);
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

		
		
			if (session.getAttribute(Constants.AUTHENTICATED_USER) == null) {
				logger.error("  Unauthorized Access ");
				mapping.findForward(UNAUTHORIZED_ACCESS);
			} else {
				userSessionContext = (UserSessionContext) session
						.getAttribute(Constants.AUTHENTICATED_USER);
				sysConfig.setUpdatedBy(userSessionContext.getId());
			}
			logger.info(" Request to update a system configuration by user "
					+ userSessionContext.getId());
			sysConfigService.updateSystemConfiguration(sysConfig);
		} catch (VirtualizationServiceException virtualServiceExp) {
			logger.error("  caught Service Exception. ", virtualServiceExp);

			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					virtualServiceExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(UPDATE_FAILURE);
		}
		request.setAttribute("selectedCircleId", updateBean.getSelectedCircleId());
		errors.add("message.sysconfig.update_success", new ActionError(
				"message.sysconfig.update_success"));
		saveErrors(request, errors);
		/* Update successful for System Config data redirect to Search Jsp. */
		logger.info(" Executed... ");
		return mapping.findForward(UPDATE_SUCCESS);
	}
}