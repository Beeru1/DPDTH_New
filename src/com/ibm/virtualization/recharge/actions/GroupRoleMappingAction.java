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
import com.ibm.virtualization.recharge.beans.GroupRoleMappingFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.GroupRoleMapping;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.GroupRoleMappingService;
import com.ibm.virtualization.recharge.service.GroupService;
import com.ibm.virtualization.recharge.service.impl.GroupRoleMappingServiceImpl;
import com.ibm.virtualization.recharge.service.impl.GroupServiceImpl;

/**
 * Controller class for Group Role Mapping.
 * 
 * @author Navroze
 */

public class GroupRoleMappingAction extends DispatchAction {

	/* Logger for this class. */

	private static Logger logger = Logger
			.getLogger(GroupRoleMappingAction.class.getName());

	/* Local Variables */
	private static final String INIT_SUCCESS = "initSuccess";

	private static final String INIT_FAILURE = "error";

	private static final String ROLES_SUCCESS = "rolesSuccess";

	private static final String UPDATE_ROLES_SUCCESS = "updateSuccess";

	private static final String UPDATE_ROLES_FAILURE = "updateFailure";

	private static final String GROUP_ROLE_ERROR = "error";

	private static final String NOROLES_DEFINED = "noRolesDefined";

	/**
	 * This method initialize the group list
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
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_GRP_ROLE_MAPPING)) {
				logger.info(" user not auth to perform this ROLE_VIEW_GRP_ROLE_MAPPING activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			
			GroupRoleMappingFormBean groupRoleFormBean = (GroupRoleMappingFormBean) form;
			/* code to hide the roles table */
			groupRoleFormBean.setShowTable(false);
			GroupService groupService = new GroupServiceImpl();
			/* code to retrieve the list of groups */
			ArrayList groupList = groupService.getGroups(userSessionContext.getGroupId());
			/* set the group list into the bean */
			groupRoleFormBean.setGroupList(groupList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger
					.error(
							"  Service Exception occured While getting Group List ",
							se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(INIT_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * This method get the lists of both roles assigned and not assigned to a
	 * particular group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward listRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		GroupRoleMappingFormBean groupRoleFormBean = (GroupRoleMappingFormBean) form;
		GroupRoleMappingService groupRoleService = new GroupRoleMappingServiceImpl();
		ActionErrors errors = new ActionErrors();
		/* code to hide the roles table */
		groupRoleFormBean.setShowTable(false);
		try {
			GroupService groupService = new GroupServiceImpl();
			/* code to retrieve the group list */
			ArrayList groupList = groupService.getGroups();
			/* set the groupList into the bean */
			groupRoleFormBean.setGroupList(groupList);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  Service Exception occured While getting Group List ",
							se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(INIT_FAILURE);
		}
		try {
			/* check if the channelType is not null */
			if (groupRoleFormBean.getChannelType() != null) {
				/*
				 * get roles assigned to the group for the selected
				 * channel(including All)
				 */
				ArrayList roleAssignedList = groupRoleService
						.getRoleAssignedList(Integer.parseInt(groupRoleFormBean
								.getGroupId()), ChannelType
								.valueOf(groupRoleFormBean.getChannelType()
										.trim()));
				logger.info("check role assigned" + roleAssignedList);
				/* set assigned roles into the bean variable */
				groupRoleFormBean.setRoleAssignedList(roleAssignedList);

				/*
				 * get Roles not assigned to a particular Group Id for the
				 * selected channel(including All)
				 */
				ArrayList roleNotAssignedList = groupRoleService
						.getRoleNotAssignedList(Integer
								.parseInt(groupRoleFormBean.getGroupId()),
								ChannelType.valueOf(groupRoleFormBean
										.getChannelType().trim()));
				logger.info("check role assigned" + roleNotAssignedList);
				/* set UnAssigned roles into the bean variable */
				groupRoleFormBean.setRoleNotAssignedList(roleNotAssignedList);

				/* check if there are roles or not */
				if (roleAssignedList.size() == 0
						&& roleNotAssignedList.size() == 0) {
					logger.info("error in Action by Mohit");
					errors.add("errors.grouprole", new ActionError(
							"errors.roles.no_roles"));
				}
				/* code to Show Roles table */
				groupRoleFormBean.setShowTable(true);
			}
		} catch (NullPointerException npExp) {
			logger.error("  Exception  occured due to group id  "
					+ groupRoleFormBean.getGroupId(), npExp);
			errors.add("errors.grouprole", new ActionError(
					"errors.roles.mapping"));
		} catch (NumberFormatException e) {
			logger.error("  Exception  occured due to group id :"
					+ groupRoleFormBean.getGroupId(), e);
			errors.add("errors.grouprole", new ActionError(
					"errors.roles.mapping"));
			saveErrors(request, errors);
			mapping.findForward(GROUP_ROLE_ERROR);
		} catch (VirtualizationServiceException vsExp) {
			logger.error("  Service Exception occured ", vsExp);
			errors.add("errors.grouprole", new ActionError(vsExp.getMessage()));
		}
		if (!errors.isEmpty()) {
			/* code to hide Roles table */
			groupRoleFormBean.setShowTable(false);
			logger.info("No roles defined ");
			saveErrors(request, errors);
			return mapping.findForward(NOROLES_DEFINED);
		}
		logger.info(" Executed... ");
		return mapping.findForward(ROLES_SUCCESS);
	}

	/**
	 * This method update the roles assigned to a particular user
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		GroupRoleMappingFormBean groupRoleFormBean = (GroupRoleMappingFormBean) form;
		GroupRoleMappingService groupRoleService = new GroupRoleMappingServiceImpl();
		GroupRoleMapping groupRole = new GroupRoleMapping();
		/* get login ID from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		/* code to hide the roles table */
		groupRoleFormBean.setShowTable(false);
		try {
			BeanUtils.copyProperties(groupRole, groupRoleFormBean);
			/* set the updated by from the context */
			groupRole.setUpdatedBy(sessionContext.getId());
			/* call service to update the role list */
			groupRoleService.updateRoles(groupRole, ChannelType
					.valueOf(groupRoleFormBean.getChannelType().trim()));
			errors.add("message.role.update_success", new ActionError(
					"message.role.update_success"));
			saveErrors(request, errors);
		} catch (VirtualizationServiceException vsExp) {
			logger.error("  Service Exception occured ", vsExp);
			errors.add("errors.grouprole", new ActionError(vsExp.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(UPDATE_ROLES_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(UPDATE_ROLES_SUCCESS);

	}
}
