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
import java.util.Iterator;

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
import com.ibm.virtualization.recharge.beans.GroupFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.GroupService;
import com.ibm.virtualization.recharge.service.impl.GroupServiceImpl;

/**
 * Controller class for Group Management.
 * 
 * @author Paras
 */
public class GroupAction extends DispatchAction {

	/* Logger for this class. */

	private static Logger logger = Logger
			.getLogger(GroupAction.class.getName());

	/* Local Variables */
	private static final String INIT_SUCCESS = "initSuccess";

	private static final String CREATE_GROUP_SUCCESS = "showList";

	private static final String CREATE_GROUP_FAILURE = "groupNameInUse";

	private static final String GET_GROUP_SUCCESS = "showEditJsp";
	
	private static final String VIEW_GROUP_SUCCESS = "showViewJsp";

	private static final String GET_GROUP_FAILURE = "showList";

	// private static final String EDIT_GROUP_SUCCESS = "showList";
	
	private static final String EDIT_GROUP_SUCCESS = "editSuccess";

	private static final String EDIT_GROUP_FAILURE = "showEditJsp";

	private static final String SHOW_LIST_SUCCESS = "showListJsp";

	private static final String SHOW_LIST_FAILURE = "error";

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CREATE_GROUP)) {
			logger.info(" user not auth to perform this ROLE_CREATE_GROUP activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		/* No dropdowns to populate, Redirect to get Create Group JSP. */
		logger.info("  successful  redirected to : " + INIT_SUCCESS);
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * This method is used to Create a Group,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createGroup(ActionMapping mapping, ActionForm form,
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
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CREATE_GROUP)) {
				logger.info(" user not auth to perform this ROLE_CREATE_GROUP activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			GroupFormBean createGroupBean = (GroupFormBean) form;
			Group group = new Group();

			GroupService groupService = new GroupServiceImpl();

			/* BeanUtil to populate DTO with Form Bean data */
			try {
				BeanUtils.copyProperties(group, createGroupBean);
				logger.info(" this is the value of --------bhanu "+createGroupBean.getType());
				createGroupBean.setType(createGroupBean.getType());
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
		/**	group.setGroupName((createGroupBean.getGroupName().toUpperCase())
				.trim());
		*/
			
			group.setGroupName((createGroupBean.getGroupName())
					.trim());
			
			
			group.setCreatedBy(userSessionContext.getId());
			group.setUpdatedBy(userSessionContext.getId());

			/* Call Service method to create a group. */
			logger.info(" Request to create a new group by user "
					+ userSessionContext.getId());
			groupService.createGroup(group);
			
			createGroupBean.reset(mapping, request);
		} catch (VirtualizationServiceException virtualServiceExp) {
			logger.error("  caught Service Exception  redirected to target   "
					+ CREATE_GROUP_FAILURE, virtualServiceExp);
			ActionErrors errors = new ActionErrors();
			errors.add("errors",
					new ActionError(virtualServiceExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CREATE_GROUP_FAILURE);
		}
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("messages.group.create_success"));
        saveErrors(request, errors);
		/* Successfully created a Group, redirect to show list Jsp */
		logger.info(" Executed... ");
		return mapping.findForward(CREATE_GROUP_SUCCESS);
	}

	/**
	 * This method It populates the details of the group selected, fetches the
	 * information from the database, then populates the form with previous
	 * values,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */

	private ActionForward getGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request)
			throws Exception {
		logger.info(" Started... ");
		try {
			GroupFormBean groupFormBean = (GroupFormBean) form;
			GroupService groupService = new GroupServiceImpl();
			//Group group = groupService.getGroup(request.getParameter("groupName"));
			
			int grpId = Integer.parseInt(groupFormBean.getGroupId());
			Group group = groupService.getGroup(grpId);

			/*
			 * BeanUtil to populate Form Bean with DTO data Polulate
			 * GroupFormBean with Group DTO data.
			 */
			try {
				String type=group.getType();
				BeanUtils.copyProperties(groupFormBean, group);
				groupFormBean.setType(type);
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

			HttpSession session = request.getSession();
			session.setAttribute(mapping.getAttribute(), form);
		} catch (VirtualizationServiceException virtualServiceExp) {
			logger.error("  caught Service Exception  redirected to target  "
					+ GET_GROUP_FAILURE, virtualServiceExp);
			ActionErrors errors = new ActionErrors();
			errors.add("errors",
					new ActionError(virtualServiceExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_GROUP_FAILURE);
		}
		/*
		 * Successfully Retrieved Data for the group to edit. Redirect to show
		 * EditGroup JSP
		 */
		logger.info(" Executed... ");
	//	return mapping.findForward(GET_GROUP_SUCCESS);
		return null;
	}


	/**
	 * This method edits a Group information,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward editGroup(ActionMapping mapping, ActionForm form,
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
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_GROUP)) {
				logger.info(" user not auth to perform this ROLE_UPDATE_GROUP activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			GroupFormBean editGroupBean = (GroupFormBean) form;
			Group group = new Group();
			/* Set the edited data from Form bean to DTO */
			try {
				BeanUtils.copyProperties(group, editGroupBean);
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

			group.setGroupName(editGroupBean.getGroupName().trim());
			
			group.setUpdatedBy(userSessionContext.getId());

			GroupService groupService = new GroupServiceImpl();
			/*
			 * Call updateGroup method of Service to update the new data for
			 * group
			 */
			logger.info(" Request to update a group by user "
					+ userSessionContext.getId());
			groupService.updateGroup(group);
		} catch (VirtualizationServiceException virtualServiceExp) {
			logger.error("  caught Service Exception  redirected to target  "
					+ EDIT_GROUP_FAILURE, virtualServiceExp);
			ActionErrors errors = new ActionErrors();
			errors.add("errors",
					new ActionError(virtualServiceExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(EDIT_GROUP_FAILURE);
		}
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("messages.group.update_success"));
        saveErrors(request, errors);
		/* Update successful for Group data redirect to display list of groups. */
		logger.info(" Executed... ");
		return mapping.findForward(EDIT_GROUP_SUCCESS);
	}

	/**
	 * This method is used to display list of available Groups,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getGroupList(ActionMapping mapping, ActionForm form,
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
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_GROUP)) {
				logger.info(" user not auth to perform this ROLE_VIEW_GROUP activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			GroupFormBean listGroupBean = (GroupFormBean) form;
			GroupService groupService = new GroupServiceImpl();
						
						
			logger.info("Auth..."+authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_UPDATE_GROUP));
			
			if (authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_GROUP)) {
				listGroupBean.setEditStatus(Constants.EDIT_AUTHORIZED);
			}else{
				listGroupBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			
			/* Call getGroups() of GroupService to get list of all the groups. */
			ArrayList allGroupDTOList = groupService.getGroups();
			Iterator itr = allGroupDTOList.iterator();
			while(itr.hasNext()){
				Group grpDto = (Group)itr.next();
				grpDto.setDescription(Utility.delemitDesctiption(grpDto.getDescription()));
			}
			/* set ArrayList of Bean objects to FormBean */
			listGroupBean.setDisplayDetails(allGroupDTOList);
		} catch (VirtualizationServiceException virtualServiceExp) {
			logger.error("  caught Service Exception  redirected to target  "
					+ SHOW_LIST_FAILURE, virtualServiceExp);
			ActionErrors errors = new ActionErrors();
			errors.add("errors",
					new ActionError(virtualServiceExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_LIST_FAILURE);
		}
		/*
		 * Successfull retrieved list of Group data redirect to display jsp with
		 * list of groups.
		 */
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_LIST_SUCCESS);
	}
	
	
	/**
	 * This method retrieves the Group information for view link,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward getViewGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_GROUP)) {
			logger.info(" user not auth to perform this ROLE_VIEW_GROUP activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		getGroup(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(VIEW_GROUP_SUCCESS);
	}

	/**
	 * This method retrives the Group information for edit link,
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getEditGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UPDATE_GROUP)) {
			logger.info(" user not auth to perform this ROLE_UPDATE_GROUP activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		getGroup(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_GROUP_SUCCESS);
	}
	
	
}