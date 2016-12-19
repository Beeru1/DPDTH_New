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

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.AccountFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.GroupService;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.GroupServiceImpl;
import com.ibm.virtualization.recharge.service.impl.LoginServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * Controller class for account management responsible for. 1. Create New
 * Account 2. view\Search Account 3. Edit Account
 * 
 * @author Bhanu
 */
public class AccountAction extends DispatchAction {

	/* Logger for this Action class. */

	private static Logger logger = Logger.getLogger(AccountAction.class
			.getName());

	public static final String OPEN_PARENT_ACCOUNT_LIST = "parentaccountlist";

	private static final String SHOW_DOWNLOAD_ACCOUNT_LIST = "listAccountExport";

	private static final String OPEN_BILLABLE_ACCOUNT_LIST = "billablelist";
	
	private static final String STATUS_ERROR = "error";

	/**
	 * This method initializes circle list and group list open the create
	 * account jsp page
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
		try {
			
			/* Logged in user information from session */
			// get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			AccountFormBean accountFormBean = (AccountFormBean) form;
			if (accountFormBean != null) {
				accountFormBean.reset(mapping, request);
			}
			

			accountFormBean.setUserType(sessionContext.getType());
			accountFormBean.setCircleId(String.valueOf(sessionContext
					.getCircleId()));
			if (sessionContext.getCircleId() != 0
					&& sessionContext.getType().equalsIgnoreCase(
							Constants.USER_TYPE_INTERNAL)) {

				accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				accountFormBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));

			} else {
				accountFormBean
						.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);

			}

			/*
			 * if login user external and billable then not show the billable
			 * search control else for internal and not billable user show
			 * search control
			 */

			if ((sessionContext.getBillableAccId() == 0)
					&& (sessionContext.getType()
							.equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL))) {
				accountFormBean
						.setShowBillableCode(Constants.ACCOUNT_BILLABLE_NO);
			} else {
				accountFormBean
						.setShowBillableCode(Constants.ACCOUNT_BILLABLE_YES);

			}

			// check user is authorized to update the group or not
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UPDATE_GROUP)) {
				accountFormBean.setAccountStatus(Constants.GROUP_UPDATE_AUTH);
				/* Get list of groups from Service Layer */
				GroupService groupService = new GroupServiceImpl();
				ArrayList groupDtoList = groupService
						.getGroups(Constants.GROUP_TYPE_EXTERNAL);
				/* Create Group DropDown List */
				session.setAttribute("groupList", groupDtoList);

			} else {
				accountFormBean
						.setAccountStatus(Constants.GROUP_UPDATE_NOT_AUTH);
			}

			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
			ArrayList circleDtoList = circleService.getCircles();
			session.setAttribute("circleList", circleDtoList);

			AccountService accountService = new AccountServiceImpl();
			ArrayList accountLevelList = null;
			if (sessionContext.getType().equalsIgnoreCase(
					Constants.USER_TYPE_INTERNAL)) {
				accountLevelList = accountService.getAccessLevelAssignedList(0);
			} else {
				accountLevelList = accountService
						.getAccessLevelAssignedList(sessionContext.getId());
			}
			session.setAttribute("accountLevelList", accountLevelList);

			/* Create state DropDown List */
			ArrayList stateList = accountService.getAllStates();
			session.setAttribute("stateList", stateList);
			ArrayList cityList = new ArrayList();
			session.setAttribute("cityList", cityList);

		} catch (VirtualizationServiceException se) {

			logger.error(
					"Error Occured while retrieving Circle or group list ", se);
			errors.add("errors", new ActionError(se.getMessage()));

		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_ACCOUNT);
	}

	/**
	 * This method will be use to insert account data
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("... Started  ");
		ActionErrors errors = new ActionErrors();
		try {
			
//			 get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			AccountFormBean accountFormBean = (AccountFormBean) form;

			/* Check if Account Code and Password are GSD Compliant. */
			try {
				logger.info(" Checking GSD compliance of Account  Code = '"
						+ accountFormBean.getAccountCode() + "'");
				GSDService gSDService = new GSDService();
				gSDService.validateCredentials(
						accountFormBean.getAccountCode(), accountFormBean
								.getIPassword());
				logger.info(" Account Code and Password are GSD Compliant.");
			} catch (ValidationException validationExp) {
				logger.error(" caught Exception for GSD  "
						+ validationExp.getMessage(), validationExp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");

				errors.add("errors.account", new ActionError(validationExp
						.getMessageId(), acount_code));
				saveErrors(request, errors);
				return mapping.findForward(Constants.CREATE_ACCOUNT);
			}
			// set flag false for city so it  would not get focused if any server side error comes
			accountFormBean.setFlagForCityFocus(false);
			/* Level - 1 VR_LOGIN_MASTER */
			Login login = new Login();
			/* transfer values from form bean to dto */
			login.setLoginName(accountFormBean.getAccountCode());
			login.setPassword(accountFormBean.getIPassword());
			login.setStatus(Constants.ACC_STATUS);
			// it will null in case of if user not auth. update group role
			if (!(accountFormBean.getGroupId() == null || accountFormBean
					.getGroupId().equalsIgnoreCase(""))) {
				login
						.setGroupId(Integer.parseInt(accountFormBean
								.getGroupId()));
			}
			login.setMPassword(accountFormBean.getMPassword());
			login.setType(accountFormBean.getUserType());

			/* Level - 2 VR_ACCOUNT_DETAILS and LEVEL-3 VR_ACCOUNT_BALANCE */
			Account account = new Account();
			// copy account form bean data into account dto
			BeanUtils.copyProperties(account, accountFormBean);
			
			account.setCreatedBy(sessionContext.getId());
			account.setUpdatedBy(sessionContext.getId());
			login.setLoginId(sessionContext.getId());
			// if circle is disabled then set circle id same as login user
			// circle Id
			if ((accountFormBean.getCircleId() == null)
					|| (accountFormBean.getCircleId().equals(""))) {

				account.setCircleId(sessionContext.getCircleId());
				accountFormBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));
			}

			

			account.setBillableCodeId(sessionContext.getBillableAccId());

			// if account create for mtp or distributor( or any top level account )
			if ((account.getAccountLevelId()
					.equalsIgnoreCase(Constants.ACC_LEVEL_MTP_DISTRIBUTOR))
					&& sessionContext.getType().equalsIgnoreCase(
							Constants.USER_TYPE_INTERNAL)) {
				accountFormBean.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);
				account.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);

			}

			// calling accountService method to validate and insert data
			logger.info(" Request to create a new account by user "
					+ sessionContext.getId());
			AccountService accountService = new AccountServiceImpl();
			accountService.createAccount(login, account);

			errors.add("message.account", new ActionError(
					"message.account.insert_success"));
			saveErrors(request, errors);

			if (session.getAttribute("circleList") != null) {
				session.removeAttribute("circleList");
			}
			if (session.getAttribute("groupList") != null) {
				session.removeAttribute("groupList");
			}
			if (session.getAttribute("accountLevelList") != null) {
				session.removeAttribute("accountLevelList");
			}
			if (session.getAttribute("displayDetails") != null) {
				session.removeAttribute("displayDetails");
			}
			if (session.getAttribute("cityList") != null) {
				session.removeAttribute("cityList");
			}
			if (session.getAttribute("stateList") != null) {
				session.removeAttribute("stateList");
			}

			logger.info(" SuccessFully Created new Account ");
		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing User Id", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (IllegalAccessException iaExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException se) {
			// if invalid account id then error on page
			logger.error(" Service Exception occured   " + se.getMessage(), se);
			errors.add("errors.account", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.CREATE_ACCOUNT);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.OPEN_ACCOUNT);
	}

	/**
	 * Method will be use to get Account information to Edit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountInfoEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Started... ");
		// get login ID from session
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
			logger.info(" user not auth to perform this ROLE_EDIT_ACCOUNT activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		String accountId = request.getParameter("accountId");
		getAccountDetails(mapping, form, request, errors, accountId);
		logger.info(" Executed... ");
		return mapping.findForward(Constants.EDIT_ACCOUNT);
	}

	/**
	 * Method will be use to get Account information to View
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountInfoView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Started... ");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_ACCOUNT)) {
			logger.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		String accountId = request.getParameter("accountId");
		getAccountDetails(mapping, form, request, errors, accountId);
		logger.info(" Executed... ");
		return mapping.findForward(Constants.VIEW_ACCOUNT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param errors
	 * @param accountId
	 */
	private ActionForward getAccountDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, ActionErrors errors,
			String accountId) {
		try {

			AccountService accountService = new AccountServiceImpl();
			Account account = accountService.getAccount(Long
					.parseLong(accountId));
			AccountFormBean accountFormBean = (AccountFormBean) form;
			String searchType = accountFormBean.getSearchFieldName();
			String searchValue = accountFormBean.getSearchFieldValue();
			/** Copying the DTO object data to Form Bean Objects */
			BeanUtils.copyProperties(accountFormBean, account);
			accountFormBean.setSearchFieldName(searchType);
			accountFormBean.setSearchFieldValue(searchValue);
			accountFormBean.setStatus(account.getStatus().trim());
			accountFormBean.setCircleName(account.getCircleName());

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			// check user is authorized to update the group or not
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UPDATE_GROUP)) {
				accountFormBean.setAccountStatus(Constants.GROUP_UPDATE_AUTH);

				/* Get list of groups from Service Layer */
				GroupService groupService = new GroupServiceImpl();
				ArrayList groupDtoList = groupService
						.getGroups(Constants.GROUP_TYPE_EXTERNAL);
				/* Create Group DropDown List */
				session.setAttribute("groupList", groupDtoList);
			} else {
				accountFormBean
						.setAccountStatus(Constants.GROUP_UPDATE_NOT_AUTH);
			}

			if ((sessionContext.getBillableAccId() == 0)
					&& (sessionContext.getType()
							.equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL))) {
				accountFormBean
						.setShowBillableCode(Constants.ACCOUNT_BILLABLE_NO);
			} else {
				accountFormBean
						.setShowBillableCode(Constants.ACCOUNT_BILLABLE_YES);

			}

			accountFormBean.setUserType(sessionContext.getType());
			/* Create state DropDown List */
			ArrayList stateList = accountService.getAllStates();
			session.setAttribute("stateList", stateList);
			/* Create Circle DropDown List */
			ArrayList cityList = accountService.getCites(Integer
					.parseInt(accountFormBean.getAccountStateId()));
			session.setAttribute("cityList", cityList);

		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing accountId "
					+ accountId, numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));

		} catch (IllegalAccessException iaExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
		} catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);

		} catch (VirtualizationServiceException vse) {
			logger.error("  Service Exception occured   ", vse);
			errors.add("errors.account", new ActionError(vse.getMessage()));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		return null;

	}

	/**
	 * This function will update the account Edit information
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started :: ");
		ActionErrors errors = new ActionErrors();
		AccountFormBean bean = (AccountFormBean) form;
		Account account = new Account();
		try {
			
			// get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_EDIT_ACCOUNT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			//set flag false for city so it  would not get focused if any server side error comes
			bean.setFlagForCityFocus(false);
			BeanUtils.copyProperties(account, bean);
			account.setUserType(sessionContext.getType());
			account.setBillableCodeId(sessionContext.getBillableAccId());

			account.setUpdatedBy(sessionContext.getId());
			AccountService accountService = new AccountServiceImpl();
			logger.info(" Request to update an account by user "
					+ sessionContext.getId());

			accountService.updateAccount(account);

			// calling service method to call dao update
			// service.updateAccountInformation(account);
			errors.add("message.account", new ActionError(
					"message.account.update_success"));
			saveErrors(request, errors);
		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing User Id.", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (IllegalAccessException iaExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(vse.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.EDIT_ACCOUNT);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.SEARCH_ACCOUNT);
	}

	/**
	 * this method will use to open account search page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward callSearchAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info("Started... ");
		ActionErrors errors = new ActionErrors();
		try {
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
			ArrayList circleDtoList = circleService.getCircles();
			session.setAttribute("circleList", circleDtoList);
			
			AccountFormBean bean = (AccountFormBean) form;

			
			// It checks whether the login user is admin or not
			logger.info("Session Id in CallSearch="
					+ sessionContext.getCircleId());
			logger.info("Auth..."
					+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_EDIT_ACCOUNT));

			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				bean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "
						+ bean.getEditStatus());
			} else {
				bean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}

			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				bean.setCircleId(dto.getCircleId() + "");
				bean.setCircleName(dto.getCircleName());
				bean.setFlagForCircleDisplay(true);
			}

		} catch (VirtualizationServiceException se) {

			logger.error("Error Occured while retrieving Circle list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.OPEN_SEARCH_PAGE);
	}

	/**
	 * This method will use to search the account acording to search option type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward searchAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info(" Started... ");

		ActionErrors errors = new ActionErrors();
		AccountFormBean bean = (AccountFormBean) form;

		String searchFieldName = bean.getSearchFieldName();
		String searchFieldValue = bean.getSearchFieldValue();
		String circleValue = bean.getCircleId();
		String listStatus = bean.getListStatus();
		
		String startDate = bean.getStartDt();
		String endDate = bean.getEndDt();

		// Search an account
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			/*
			 * Added by Paras on Oct 04, 2007.
			 * 
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 * Passing the sessionContext to service layer.
			 */

		
			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
			
			try{
			ArrayList circleDtoList = circleService.getCircles();
			session.setAttribute("circleList", circleDtoList);
			}
			catch (VirtualizationServiceException se) {
				logger.error("  Service Exception occured ", se);
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(STATUS_ERROR);
			}
			
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				bean.setCircleId(dto.getCircleId() + "");
				bean.setCircleName(dto.getCircleName());
				bean.setFlagForCircleDisplay(true);
			}

			logger.info("Auth..."
					+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_EDIT_ACCOUNT));

			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				bean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "
						+ bean.getEditStatus());
			} else {
				bean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}

			
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_RESET_I_PASSWORD)) {
				bean.setResetStatus(Constants.RESET_AUTHORIZED);
				logger.info(" this is the value of reset "+bean.getResetStatus());
			}else{
				bean.setResetStatus(Constants.RESET_UNAUTHORIZED);
			}
			
			
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UNLOCK_USER)) {
				bean.setUnlockStatus(Constants.UNLOCK_AUTHORIZED);
				logger.info(" this is the value of unlock "+bean.getUnlockStatus());
			}else{
				bean.setUnlockStatus(Constants.UNLOCK_UNAUTHORIZED);
			}
			
			
			
			AccountService accountService = new AccountServiceImpl();
			ReportInputs mtDTO = new ReportInputs();
			// set data from form bean
			if (request.getParameter("searchType") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("listStatus") == null
					|| request.getParameter("startDate") == null
					|| request.getParameter("endDate") == null) {
				mtDTO.setSearchFieldName(searchFieldName);
				mtDTO.setSearchFieldValue(searchFieldValue);
				mtDTO.setSessionContext(sessionContext);

				mtDTO.setCircleId(Integer.parseInt(circleValue));
				mtDTO.setStatus(listStatus);
				mtDTO.setStartDt(startDate);
				mtDTO.setEndDt(endDate);
				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				request.setAttribute("circleId", circleValue);
				request.setAttribute("listStatus", listStatus);
				request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
			} else {
				mtDTO.setSearchFieldName(request.getParameter("searchType"));
				mtDTO.setSearchFieldValue(request.getParameter("searchValue"));
				mtDTO.setCircleId(Integer.parseInt(request
						.getParameter("circleId")));
				mtDTO.setStatus(request.getParameter("listStatus"));
				mtDTO.setStartDt(request.getParameter("startDate"));
				mtDTO.setEndDt(request.getParameter("endDate"));
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("circleId", request
						.getParameter("circleId"));
				request.setAttribute("listStatus", request.getParameter("listStatus"));
				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request
						.setAttribute("endDate", request
								.getParameter("endDate"));
				mtDTO.setSessionContext(sessionContext);
				bean.setStartDt(request.getParameter("startDate"));
				bean.setEndDt(request.getParameter("endDate"));
				bean.setSearchFieldName(request.getParameter("searchType"));
				bean.setSearchFieldValue(request.getParameter("searchValue"));
				bean.setCircleId(request.getParameter("circleId"));
			}
			/* call service to count the no of rows */
			int noofpages = accountService.getAccountListCount(mtDTO);
			 logger.info("Inside searchAccount().. no of pages..." + noofpages);

			 /* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all accounts */
			ArrayList accountList = accountService.getAccountList(mtDTO,
					pagination.getLowerBound(), pagination.getUpperBound());

			
			/*Account accountDto = (Account) accountList.get(0);
			int totalRecords = accountDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);*/
			
			/* Delimit accountaddress field to 10 chars while displaying in grid*/
			Iterator iter = accountList.iterator();
			while(iter.hasNext()){
			
				Account accountDto = (Account) iter.next();
				accountDto.setAccountAddress(Utility.delemitDesctiption(accountDto.getAccountAddress()));				
			}
			
			request.setAttribute("PAGES", noofpages);

			/* set ArrayList of Bean objects to FormBean */
			bean.setDisplayDetails(accountList);

		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing..", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(Constants.OPEN_SEARCH_PAGE);
	}

	/**
	 * This method will use to search the Parent account acording to Circle And
	 * search option type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward getParentAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		try {
			AccountFormBean bean = (AccountFormBean) form;
			AccountService accountService = new AccountServiceImpl();
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			/** pagination implementation */
			if (request.getParameter("searchType") == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("accountLevel") == null) {

				request.setAttribute("searchType", bean.getSearchFieldName());
				request.setAttribute("searchValue", bean.getSearchFieldValue());
				request.setAttribute("circleId", bean.getCircleId());
				request.setAttribute("accountLevel", bean.getAccountLevel());

			} else {
				bean.setSearchFieldName(request.getParameter("searchType"));
				bean.setSearchFieldValue(request.getParameter("searchValue"));
				bean.setCircleId(request.getParameter("circleId"));
				bean.setStatus(request.getParameter("accountLevel"));

				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("circleId", request
						.getParameter("circleId"));
				request.setAttribute("accountLevel", request
						.getParameter("accountLevel"));

			}
			/* call service to count the no of rows */
			int noofpages = accountService.getParentAccountListCount(bean
			 .getSearchFieldName(), bean.getSearchFieldValue(),
			 sessionContext, Integer.parseInt(bean.getCircleId()), bean
			 .getAccountLevel());
			 logger.info("Inside getCircleList().. no of pages..." + noofpages);
			// noofpages = Utility.getPaginationSize(noofpages);
			 request.setAttribute("PAGES", String.valueOf(noofpages));
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all accounts */
			ArrayList accountList = accountService.getParentAccountList(bean
					.getSearchFieldName(), bean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(bean.getCircleId()), bean
							.getAccountLevel(), pagination.getLowerBound(),
					pagination.getUpperBound());

		//	Account accountDto = (Account) accountList.get(0);
			//int totalRecords = accountDto.getTotalRecords();
			
			logger.info("No of pages"+noofpages);

			/* set the list into form bean */
			bean.setDisplayDetails(accountList);

			request.setAttribute("displayDetails", accountList);
		} catch (VirtualizationServiceException vse) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"errors.account.parentrecord_notfound"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(OPEN_PARENT_ACCOUNT_LIST);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward openSearchParentAccountPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		if (request.getSession().getAttribute("displayDetails") != null) {
			request.getSession().removeAttribute("displayDetails");
		}

		logger.info("Executed... ");
		return mapping.findForward(OPEN_PARENT_ACCOUNT_LIST);

	}

	public ActionForward downloadAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		AccountFormBean bean = (AccountFormBean) form;

		String searchFieldName = bean.getSearchFieldName();
		String searchFieldValue = bean.getSearchFieldValue();
		String circleValue = bean.getCircleId();
		String status = bean.getListStatus();
		String startDate = bean.getStartDt();
		String endDate = bean.getEndDt();

		// Search an account
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			

			/*
			 * Added by Paras on Oct 04, 2007.
			 * 
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 * Passing the sessionContext to service layer.
			 */

			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
			ArrayList circleDtoList = circleService.getCircles();
			session.setAttribute("circleList", circleDtoList);

			AccountService accountService = new AccountServiceImpl();
			ReportInputs mtDTO = new ReportInputs();
			// set data from form bean

			mtDTO.setSearchFieldName(searchFieldName);
			mtDTO.setSearchFieldValue(searchFieldValue);
			mtDTO.setSessionContext(sessionContext);
			mtDTO.setCircleId(Integer.parseInt(circleValue));
			mtDTO.setStatus(status);
			mtDTO.setStartDt(startDate);
			mtDTO.setEndDt(endDate);

			ArrayList accountList = accountService.getAccountListAll(mtDTO);

			/** **End of pagination**** */
			// set the list into form bean
			bean.setDisplayDetails(accountList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_DOWNLOAD_ACCOUNT_LIST);
	}

	/**
	 * this method search city according to state id 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting getCity... ");
		ActionErrors errors = new ActionErrors();
		try {
			AccountFormBean accountFormBean = (AccountFormBean) form;

			AccountService accountService = new AccountServiceImpl();
			HttpSession session = request.getSession();
			//if circle is disabled then set circle id same as login user
			// circle Id
			if ((accountFormBean.getCircleId() == null)
					|| (accountFormBean.getCircleId().equals(""))) {

				UserSessionContext sessionContext = (UserSessionContext) session
						.getAttribute(Constants.AUTHENTICATED_USER);
				accountFormBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));
			}

			/* Create Circle DropDown List */
			ArrayList cityList = accountService.getCites(Integer
					.parseInt(accountFormBean.getAccountStateId()));
			session.setAttribute("cityList", cityList);
			

		} catch (VirtualizationServiceException vse) {
			
			logger.error(
					"Error Occured while retrieving Circle or group list ", vse);
			errors.add("errors", new ActionError(vse.getMessage()));

		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_ACCOUNT);
	}

	/**
	 * this method get billable account list based on search 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public ActionForward getBillableAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started..getBillableAccountList. ");
		try {
			AccountFormBean bean = (AccountFormBean) form;
			AccountService accountService = new AccountServiceImpl();
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			/** pagination implementation */
			if (request.getParameter("searchType") == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("accountLevel") == null) {

				request.setAttribute("searchType", bean.getSearchFieldName());
				request.setAttribute("searchValue", bean.getSearchFieldValue());
				request.setAttribute("circleId", bean.getCircleId());
				request.setAttribute("accountLevel", bean.getAccountLevel());

			} else {
				bean.setSearchFieldName(request.getParameter("searchType"));
				bean.setSearchFieldValue(request.getParameter("searchValue"));
				bean.setCircleId(request.getParameter("circleId"));
				bean.setStatus(request.getParameter("accountLevel"));

				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("circleId", request
						.getParameter("circleId"));
				request.setAttribute("accountLevel", request
						.getParameter("accountLevel"));

			}
			/* call service to count the no of rows */
			int noofpages = accountService.getBillableAccountListCount(bean
					.getSearchFieldName(), bean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(bean.getCircleId()), bean
							.getAccountLevel());
			logger.info(" getBillableAccountListCount().. no of pages..."
					+ noofpages);
			request.setAttribute("PAGES", String.valueOf(noofpages));
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all accounts */
			ArrayList billableAccountList = accountService
					.getBillableAccountList(bean.getSearchFieldName(), bean
							.getSearchFieldValue(), sessionContext, Integer
							.parseInt(bean.getCircleId()), bean
							.getAccountLevel(), pagination.getLowerBound(),
							pagination.getUpperBound());

			/* set the list into form bean */
			bean.setDisplayDetails(billableAccountList);
			request.setAttribute("billableAccountList", billableAccountList);
		} catch (VirtualizationServiceException vse) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"errors.account.billablerecord_notfound"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(OPEN_BILLABLE_ACCOUNT_LIST);

	}

	/**
	 * this method open billable account search pop-up window
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public ActionForward openSearchBillableAccountPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		if (request.getSession().getAttribute("displayDetails") != null) {
			request.getSession().removeAttribute("displayDetails");
		}

		logger.info("Executed... ");
		return mapping.findForward(OPEN_BILLABLE_ACCOUNT_LIST);

	}

	/**
	 * this method use for get city list for edit 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCityForEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting getCity... ");
		ActionErrors errors = new ActionErrors();
		try {
			AccountFormBean accountFormBean = (AccountFormBean) form;

			AccountService accountService = new AccountServiceImpl();
			HttpSession session = request.getSession();

			/* Create Circle DropDown List */
			ArrayList cityList = accountService.getCites(Integer
					.parseInt(accountFormBean.getAccountStateId()));
			session.setAttribute("cityList", cityList);

		} catch (VirtualizationServiceException se) {
			
			logger.error(
					"Error Occured while retrieving Circle or group list ", se);
			errors.add("errors", new ActionError(se.getMessage()));

		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.EDIT_ACCOUNT);
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
		AccountFormBean accountFormBean = (AccountFormBean) form;
		ActionErrors errors = new ActionErrors();
		try{
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_UNLOCK_USER)) {
				logger.info(" user not auth to perform this ROLE_UNLOCK_USER activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			LoginService loginService =new LoginServiceImpl();
			loginService.unlockUser(Long.parseLong(accountFormBean.getAccountId()));
		
		}catch(VirtualizationServiceException vs){
			logger.error("  caught Service Exception  redirected to  "
					 , vs);
			logger.info("Message is " + vs.getMessage());
			
			errors.add("error.account", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(Constants.SEARCH_ACCOUNT);	
			
		}
		errors.add("error.account", new ActionError("errors.user.unlock"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(Constants.SEARCH_ACCOUNT);
	}
	
	/**
	 * this is method will use to reset i-password
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
		AccountFormBean accountFormBean = (AccountFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();

		try {
			
			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_RESET_I_PASSWORD)) {
				logger.info(" user not auth to perform this ROLE_RESET_I_PASSWORD activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
            if(sessionContext!=null){
				AuthenticationService authenticationService = new AuthenticationServiceImpl();
				authenticationService.resetPassword(Long.parseLong(accountFormBean
						.getAccountId()), Constants.USER_TYPE_EXTERNAL);
            }
		} catch (VirtualizationServiceException vs) {

			logger.error("  caught Service Exception  redirected to  "
					+ Constants.OPEN_SEARCH_PAGE, vs);
			logger.info("Message is " + vs.getMessage());

			errors.add("error.account", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(Constants.SEARCH_ACCOUNT);

		}
		errors.add("error.account", new ActionError(
				"errors.resetipasswordupdatesuccessfully"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		// redirect to the specified target
		return mapping.findForward(Constants.SEARCH_ACCOUNT);

	}

}