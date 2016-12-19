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

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.UserFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.GroupService;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.service.UserService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.GroupServiceImpl;
import com.ibm.virtualization.recharge.service.impl.LoginServiceImpl;
import com.ibm.virtualization.recharge.service.impl.UserServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * Controller class for User Management.
 * 
 * @author Paras
 */
public class UserAction extends DispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(UserAction.class.getName());

	/* Local Variables */
	private static final String INIT_SUCCESS = "initSuccess";

	private static final String INIT_FAILURE = "error";

	//private static final String CREATE_USER_SUCCESS = "userNameInUse";
	private static final String CREATE_USER_SUCCESS = "circleCreated";

	private static final String CREATE_USER_FAILURE = "initSuccess";

	private static final String GET_USER_EDIT = "showEditJsp";
	
	private static final String GET_USER_VIEW = "showViewJsp";

	private static final String GET_USER_FAILURE = "showList";

	private static final String EDIT_USER_SUCCESS = "showUserList";

	private static final String EDIT_USER_FAILURE = "error";

	private static final String SHOW_LIST_SUCCESS = "showListJsp";

	private static final String SHOW_LIST_FAILURE = "showListJsp";

	private static final String SHOW_SEARCH_SUCCESS = "showListJsp";

	private static final String SHOW_DOWNLOAD_USER_LIST = "listUserExport";
	
	private static final String SHOW_UNLOCK_USER_LIST = "showUnlockUserList";

	/**
	 * init method is called to populate Group and Circle Dropdowns in
	 * CreateUser.jsp
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
		logger.info(" Started... ");
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_USER)) {
				logger.info(" user not auth to perform ROLE_ADD_USER activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			UserFormBean createUserBean = (UserFormBean) form;
			createUserBean.reset(mapping, request);
			GroupService groupService = new GroupServiceImpl();
			CircleService circleService = new CircleServiceImpl();
			
			/* Get list of circles from Service Layer */
			ArrayList circleDtoList = circleService.getCircles();
			
			request.getSession().setAttribute("circleList", circleDtoList);
			/* Create Circle Drop Down */
			
			// It checks whether the login user is admin or not
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				createUserBean.setCircleId(dto.getCircleId() + "");
				createUserBean.setCircleName(dto.getCircleName());
				createUserBean.setFlagForCircleDisplay(true);
			}
			// check user is authorized to update the group or not
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_GROUP)) {
				logger.info("User Authorized For Updating Group... User Id "
						+ sessionContext.getId());
				/* Get list of groups from Service Layer */
				ArrayList groupDtoList = groupService.getGroups(Constants.USER_TYPE_INTERNAL);
				createUserBean.setUserAuthStatus(Constants.GROUP_UPDATE_AUTH);
				/* Create Group DropDown List */
				request.getSession().setAttribute("groupList", groupDtoList);
			} else {
				createUserBean
						.setUserAuthStatus(Constants.GROUP_UPDATE_NOT_AUTH);
				logger
						.info("User Not Authorized For Updating Group... UserId "
								+ sessionContext.getId());
			}
		} catch (VirtualizationServiceException virtualizationServiceExp) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured.",
					virtualizationServiceExp);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(virtualizationServiceExp
					.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * This method is used to create a unique user only if User Name and
	 * Password are GSD Compliant.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		try {
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_USER)) {
				logger.info(" user not auth to perform ROLE_ADD_USER activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			UserFormBean createUserBean = (UserFormBean) form;
			/* Check if UserName and Password are GSD Compliant. */
			try {
				logger.info("  checking for User Name = '"
						+ createUserBean.getLoginName().trim() + "'");
				GSDService gSDService = new GSDService();
				gSDService.validateCredentials(createUserBean.getLoginName(),
						createUserBean.getPassword());
				logger.info(" User Name and Password are GSD Compliant.");
			} catch (ValidationException validationExp) {
				logger.error(" caught GSD Validation Exception  "
						+ validationExp.getMessage(), validationExp);
				ActionErrors errors = new ActionErrors();
				String application_user = ResourceReader.getWebResourceBundleValue("application.user");
				

				if ("msg.security.id03".equalsIgnoreCase(validationExp
						.getMessageId())
						|| "msg.security.id05".equalsIgnoreCase(validationExp
								.getMessageId())
						|| "msg.security.id07".equalsIgnoreCase(validationExp
								.getMessageId())
						|| "msg.security.id011".equalsIgnoreCase(validationExp
								.getMessageId())) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(validationExp
							.getMessageId(), application_user));
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(validationExp
							.getMessageId()));
				}

				saveErrors(request, errors);
				return mapping.findForward(CREATE_USER_FAILURE);
			}

			User user = new User();
			UserService userService = new UserServiceImpl();

			/* BeanUtil to populate DTO with Form Bean data */
			try {
				BeanUtils.copyProperties(user, createUserBean);
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

			
			user.setCreatedBy(userSessionContext.getId());
			user.setUpdatedBy(userSessionContext.getId());
			/*
			 * if circle Admin creates a circle then the CircleId is set the
			 * CircleId of the CircleAdmin
			 */
			
			if (userSessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				user.setCircleId(userSessionContext.getCircleId());
			}
			
			
			/* Set the User Type for Internal User */
			user.setType(Constants.USER_TYPE_INTERNAL);

			/* Call createUser of User Service (Service Layer) */
			logger.info(" Request to create a new user by user "
					+ userSessionContext.getId());
			userService.createUser(user);
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception while creating user  "
					+ " redirected to   " + CREATE_USER_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CREATE_USER_FAILURE);
		}
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
				"messages.user.create_success"));
		saveErrors(request, errors);
		
		logger.info(" Executed... ");
		return mapping.findForward(CREATE_USER_SUCCESS);
	}

	/**
	 * This method populates the details of the user selected, populates the
	 * form with previous values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward getEditUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_USER)) {
			logger.info(" user not auth to perform ROLE_UPDATE_USER activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		getUser(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_USER_EDIT);
	}
	
	/**
	 * This method populates the details of the user selected, populates the
	 * form with previous values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward getViewUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_USER)) {
			logger.info(" user not auth to perform ROLE_VIEW_USER activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		getUser(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_USER_VIEW);
	}
	
	
	
	/**
	 * This method populates the details of the user selected, populates the
	 * form with previous values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward getUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request)
			throws Exception {
		logger.info(" Started... in user");
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserFormBean createUserBean = (UserFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		try {
			GroupService groupService = new GroupServiceImpl();
			// check user is authorized to update the group or not
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_GROUP)) {
				logger.info("User Authorized For Updating Group... User Id "
						+ sessionContext.getId());
				/* Get list of groups from Service Layer */
				ArrayList groupDtoList = groupService.getGroups(Constants.USER_TYPE_INTERNAL);
				createUserBean.setUserAuthStatus(Constants.GROUP_UPDATE_AUTH);
				/* Create Group DropDown List */
				request.setAttribute("groupList", groupDtoList);
			} else {
				createUserBean
						.setUserAuthStatus(Constants.GROUP_UPDATE_NOT_AUTH);
				logger
						.info("User Not Authorized For Updating Group... UserId "
								+ sessionContext.getId());
			}

		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception while creating Group Drop Down  redirected to target  "
									+ GET_USER_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_USER_FAILURE);
		}

		try {
			/* Get list of Circle from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleDtoList = circleService.getCircles();
			/* Create a circle Group Down */
			request.setAttribute("circleList", circleDtoList);
			// It checks whether the login user is admin or not
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				createUserBean.setCircleId(dto.getCircleId() + "");
				createUserBean.setCircleName(dto.getCircleName());
				createUserBean.setFlagForCircleDisplay(true);
			}

		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception while creating Circle Drop Down  redirected to target  "
									+ GET_USER_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_USER_FAILURE);
		}

		// Populate Bean with required details retrieved from database based on
		// userName selected.
		try {
			UserFormBean userForm = (UserFormBean) form;
			UserService userService = new UserServiceImpl();
			User user = new User();
			/*
			 * Call getuser from Service Layer to get details for selected
			 * userName.
			 */
			user = userService.getUser(Long.parseLong(createUserBean
					.getLoginId()));
			user.setStartDt(userForm.getStartDt());
			user.setEndDt(userForm.getEndDt());
			String page=createUserBean.getPage();
		
		
			/*
			 * BeanUtil to populate Form Bean with DTO data Populate
			 * UserFormBean with User DTO data.
			 */
			try {
				BeanUtils.copyProperties(userForm, user);
				userForm.setPage(page);
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

			/* Save the form bean to request. */
			request.setAttribute(mapping.getAttribute(), form);
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ GET_USER_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_USER_FAILURE);
		}
		logger.info(" Executed... ");
		return null ;
		//return mapping.findForward(GET_USER_SUCCESS);
	}

	/**
	 * This method edits a user information,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward editUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... in editUser");
		UserFormBean editUserBean;
		try {
			
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_USER)) {
				logger.info(" user not auth to perform ROLE_UPDATE_USER activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			editUserBean = (UserFormBean) form;
			User user = new User();
			/* Set form bean data (edited information) to DTO */
			/* BeanUtil to populate DTO with Form Bean data */
			try {
				BeanUtils.copyProperties(user, editUserBean);
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

			
			user.setUpdatedBy(userSessionContext.getId());
			/* User Id to be Edited is same as Login Id */
			user.setUserId(user.getLoginId());
			/* Set the User Type for Internal User */
			user.setType(Constants.USER_TYPE_INTERNAL);
			/* if the Updating user is not Admin */ 
			if (userSessionContext.getCircleId()!=Constants.ADMIN_CIRCLE_ID){
				user.setCircleId(userSessionContext.getCircleId());
			}
			
			UserService userService = new UserServiceImpl();
			/*
			 * Call the updateUser() method of Service Layer to update user
			 * details.
			 */
			logger.info(" Request to update a user by user "
					+ userSessionContext.getId());
			userService.updateUser(user);
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to  "
					+ EDIT_USER_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(EDIT_USER_FAILURE);
		}
		
		request.setAttribute("selectedCircleId", editUserBean.getSelectedCircleId());
		request.setAttribute("selectedStatus", editUserBean.getSelectedStatus());
		
		editUserBean.setFlag(Constants.LIST_ERROR_FLAG);
		
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.user.update_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(EDIT_USER_SUCCESS);
	}

	/**
	 * This method displays list of users.(for Report purpose)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
			UserFormBean listUserBean = (UserFormBean) form;
		try {
			CircleService circleService = new CircleServiceImpl();
			/* Get list of circles from Service Layer */
			ArrayList circleDtoList = circleService.getCircles();
			/* Create Circle Drop Down */
			request.setAttribute("circleList", circleDtoList);
			

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			logger.info("Auth..."+authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_USER));
			
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_USER)) {
				listUserBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "+listUserBean.getEditStatus());
			}else{
				listUserBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			
			
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_RESET_I_PASSWORD)) {
				listUserBean.setResetStatus(Constants.RESET_AUTHORIZED);
				logger.info(" this is the value of reset "+listUserBean.getResetStatus());
			}else{
				listUserBean.setResetStatus(Constants.RESET_UNAUTHORIZED);
			}
			
			
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UNLOCK_USER)) {
				listUserBean.setUnlockStatus(Constants.UNLOCK_AUTHORIZED);
				logger.info(" this is the value of unlock "+listUserBean.getUnlockStatus());
			}else{
				listUserBean.setUnlockStatus(Constants.UNLOCK_UNAUTHORIZED);
			}
			
			
			
			
			logger.info("Session Id"+sessionContext.getCircleId());
			if (sessionContext.getCircleId()!=Constants.ADMIN_CIRCLE_ID)
			{
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				listUserBean.setCircleId(String.valueOf(dto.getCircleId()));
				listUserBean.setCircleName(dto.getCircleName());
				listUserBean.setFlagForCircleDisplay(true);
			}
			
			UserService userService = new UserServiceImpl();
			/* Call getAllUsers() of UserService to get list of all the users. */
			ReportInputs inputDto = new ReportInputs();
			inputDto.setStatus(listUserBean.getSelectedStatus());
			inputDto.setStartDt(listUserBean.getStartDt());
			inputDto.setEndDt(listUserBean.getEndDt());
			if (sessionContext.getCircleId()!=Constants.ADMIN_CIRCLE_ID)
			{
				inputDto.setCircleId(sessionContext.getCircleId());
				listUserBean.setSelectedCircleId(String.valueOf(sessionContext.getCircleId()));
			}
			else
			{
				inputDto.setCircleId(Integer.parseInt(listUserBean.getSelectedCircleId()));
				listUserBean.setSelectedCircleId(String.valueOf(inputDto.getCircleId()));
			}	
              
			/** pagination implementation */
			if (request.getParameter("startDt") == null
					|| request.getParameter("endDt") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("status") == null) {
				request.setAttribute("startDt", inputDto.getStartDt());
				request.setAttribute("endDt", inputDto.getEndDt());
				request.setAttribute("circleId", inputDto.getCircleId()+"");
				request.setAttribute("status", inputDto.getStatus());
			} else {
				request
						.setAttribute("startDt", request
								.getParameter("startDt"));
				request.setAttribute("endDt", request.getParameter("endDt"));
				request.setAttribute("circleId", request
						.getParameter("circleId")+"");
				request.setAttribute("status", request.getParameter("status"));
			}
			/* call service to count the no of rows */
			/*int noofpages = userService.getUsersCount(inputDto);
			logger.info("Inside  getUserList().. no of pages..."+noofpages);*/
  		    request.setAttribute("page",listUserBean.getPage());
  		    Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
			
			/* call service to find all users */
			ArrayList allUserDTOList = userService.getAllUsers(inputDto, pagination.getLowerBound(), pagination.getUpperBound());
			
			User userDto= (User)allUserDTOList.get(0);
			int totalRecords = userDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages); 
       
 			/* set ArrayList of Bean objects to Form Bean */
			listUserBean.setUserList(allUserDTOList);
			
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to  "
					+ SHOW_LIST_FAILURE, se);
			logger.info("Message is " + se.getMessage());
			ActionErrors errors = new ActionErrors();
						
			if(listUserBean.getFlag()==null){
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
			}else if(listUserBean.getFlag()!=null && (listUserBean.getFlag().equals(Constants.LIST_ERROR_FLAG))){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("messages.user.update_success"));
				saveErrors(request, errors);
			}
			
			return mapping.findForward(SHOW_LIST_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_LIST_SUCCESS);
	}

	/**
	 * this method will use to open user search page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward callSearchUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info("Started... ");
		ActionErrors errors = new ActionErrors();
		try {
			
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_USER)) {
				logger.info(" user not auth to perform ROLE_VIEW_USER activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			UserFormBean listUserBean = (UserFormBean) form;
			/* Create Circle DropDown List */
			
				ArrayList circleDtoList = circleService.getCircles();
				request.setAttribute("circleList", circleDtoList);
			
			// Implementing the logic for Showing all circle list only to administrator
			// It checks whether the login user is admin or not
			
			
			logger.info("Inside callSearch...Auth..."+authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_USER));
			
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_USER)) {
				listUserBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit " + listUserBean.getEditStatus());
			}else{
				listUserBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}			
			
			logger.info("Session Id in CallSearch="+sessionContext.getCircleId());
			if (sessionContext.getCircleId()!=Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				listUserBean.setCircleId(dto.getCircleId() + "");
				
				
				listUserBean.setSelectedCircleId(listUserBean.getCircleId());
				listUserBean.setCircleName(dto.getCircleName());
				listUserBean.setFlagForCircleDisplay(true);

			}
			// listUserBean.setAllUserList(circleDtoList);
		} catch (VirtualizationServiceException se) {

			logger.error("Error Occured while retrieving Circle list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request,errors);
			return mapping.findForward(INIT_FAILURE);	
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_SEARCH_SUCCESS);
	}

	/**
	 * This method displays list of users for downloading purpose
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadUserList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		try {
			CircleService circleService = new CircleServiceImpl();
			/* Get list of circles from Service Layer */
			UserFormBean listUserBean = (UserFormBean) form;
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			// It checks whether the login user is admin or not
			if (sessionContext.getCircleId()!=Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				listUserBean.setCircleId(dto.getCircleId() + "");
				listUserBean.setCircleName(dto.getCircleName());
				listUserBean.setFlagForCircleDisplay(true);

			}
			UserService userService = new UserServiceImpl();
			/* Call getAllUsers() of UserService to get list of all the users. */
			ReportInputs inputDto = new ReportInputs();
			inputDto.setStatus(listUserBean.getSelectedStatus());
			inputDto.setStartDt(listUserBean.getStartDt());
			inputDto.setEndDt(listUserBean.getEndDt());
			if (sessionContext.getCircleId()!=Constants.ADMIN_CIRCLE_ID) {
				inputDto.setCircleId(sessionContext.getCircleId());
			}
			else{
				inputDto.setCircleId(Integer.parseInt(listUserBean.getSelectedCircleId()));
			}
			/* set ArrayList of Bean objects to Form Bean */
			ArrayList allUserDTOList = userService.getAllUsersList(inputDto);
			listUserBean.setUserList(allUserDTOList);
			logger.info("after fucntion call");
			
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to  "
					+ SHOW_LIST_FAILURE, se);
			logger.info("Message is " + se.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(SHOW_LIST_FAILURE);
		}
		logger.info(" Executed... ");
		logger.info("mapping & find forward");
		return mapping.findForward(SHOW_DOWNLOAD_USER_LIST);
	}
	
	
	/**
	 * This method unlock user
	 * form with previous values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward UnlockUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		UserFormBean listUserBean = (UserFormBean) form;
		ActionErrors errors = new ActionErrors();
		try{
	
		String loginId=request.getParameter("loginId");
		logger.info(" login id : "+listUserBean.getLoginId());
		logger.info(" listUserBean.getCircleId() : "+listUserBean.getSelectedCircleId());
		logger.info(" listUserBean.getStartDt() : "+listUserBean.getStartDt());
		logger.info(" listUserBean.getStartDt() : "+listUserBean.getSelectedStatus());
		LoginService loginService =new LoginServiceImpl();
		loginService.unlockUser(Long.parseLong(loginId));
		
		}catch(VirtualizationServiceException vs){
			logger.error("  caught Service Exception  redirected to  "
					+ SHOW_LIST_FAILURE, vs);
			logger.info("Message is " + vs.getMessage());
			
			errors.add("errors", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(SHOW_UNLOCK_USER_LIST);	
			
		}
		errors.add("errors", new ActionError("errors.user.unlock"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_UNLOCK_USER_LIST);
	}
	
	
	/**
	 * this method for reset I-password
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward resetIPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" Started... ");
		UserFormBean userFormBean = (UserFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		try {
			  if(sessionContext!=null){
				AuthenticationService authenticationService = new AuthenticationServiceImpl();
				authenticationService.resetPassword(Long.parseLong(userFormBean
					.getLoginId()), Constants.USER_TYPE_INTERNAL);
			  }

		} catch (VirtualizationServiceException vs) {

			logger.error("  caught Service Exception  redirected to  "
					+ SHOW_LIST_FAILURE, vs);
			logger.info("Message is " + vs.getMessage());

			errors.add("errors", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(SHOW_UNLOCK_USER_LIST);

		}
		errors.add("errors", new ActionError(
				"errors.resetipasswordupdatesuccessfully"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		// redirect to the specified target
		return mapping.findForward(SHOW_UNLOCK_USER_LIST);

	}
	


}
