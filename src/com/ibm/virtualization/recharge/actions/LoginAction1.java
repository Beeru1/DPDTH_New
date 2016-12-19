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
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.beans.AuthenticationFormBean;
import com.ibm.virtualization.recharge.beans.UserBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.Link;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.UserService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.UserServiceImpl;

/**
 * Controller class for Login with Account Code
 * 
 * @author Paras
 */
public class LoginAction1 extends Action {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(LoginAction.class.getName());

	/* Local Variables */
	private static String AUTHENTICATION_SUCCESS = "loginSuccess";
	
	private static String DISTRIBUTOR_LOGIN = "distributorlogin";

	private static String AUTHENTICATION_FAILURE = "loginFailure";

	/* Top link lebels from DB VR_LINK_MASTER.TOP_LINK_NAME */
	private static String SYSTEM_CONFIGURATION = "SYSTEM_CONFIGURATION";

	private static String ACCOUNT_MANAGEMENT = "ACCOUNT_MANAGEMENT";

	private static String MONEY_TRANSACTION = "MONEY_TRANSACTION";

	private static String SYSTEM_ADMIN = "SYSTEM_ADMINISTRATION";

	private static String USSD_ADMIN = "USSD_ACTIVATION";

	private static String QUERIES = "QUERIES";

	private static String REPORTS = "REPORTS";

	private static String PASSWORD_EXPIRED = "PasswordExpired";

	/**
	 * This method authenticates a user
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		AuthenticationFormBean authenticationFormBean = (AuthenticationFormBean) form;
		AuthenticationService autheticationservice = new AuthenticationServiceImpl();
		Login login = new Login();
		logger.info(" Login tried with :"
				+ authenticationFormBean.getLoginName() +" from IP Address "+request.getRemoteAddr()+":"+request.getRemoteHost());

		try {

			login.setLoginName(authenticationFormBean.getLoginName());
			login.setPassword(authenticationFormBean.getPassword());
			logger.info(" Authenticating Login :"
					+ authenticationFormBean.getLoginName());
			autheticationservice.validateUserPassword(login);
			logger.info(" Authenticated");

			/* Authenticate login name & password */
			String loginName = authenticationFormBean.getLoginName();

			/* GSD implementation to validate user name and password */
			logger.info(" Called GSD Service");			
			GSDService gsdService = new GSDService();
			UserBean userBean = (UserBean) gsdService.validateCredentials(
					authenticationFormBean.getLoginName(),
					authenticationFormBean.getPassword(),
					Constants.SRC_USER_BEAN);
			logger.info(" Validated");
			/*
			 * Check if the Account Opening Date is less than equals to today
			 */
			if (userBean.getType().equalsIgnoreCase(
					Constants.USER_TYPE_EXTERNAL)) {
									/* AccountService accountService = new AccountServiceImpl();
				 String openingDate = accountService.getAccoutOpeningDate(Long
						.parseLong(userBean.getLoginId()));
				if (openingDate != null) {
					ActionErrors errors = new ActionErrors();
					errors
							.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											ExceptionCode.Authentication.ERROR_ACCOUNT_LOGIN_FAILURE,
											openingDate));
					saveErrors(request, errors);
					return mapping.findForward(AUTHENTICATION_FAILURE);
				}
				*/
			}
			if (userBean == null) {
				logger
						.info(" Could populate userbean for Login : "
								+ loginName);
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ExceptionCode.Authentication.ERROR_APPLICATION_SEVERE));
				saveErrors(request, errors);
				return mapping.findForward(AUTHENTICATION_FAILURE);
			}

			/** ******************duplicate session validation***************** */
			ServletContext sc = request.getSession().getServletContext();
			HttpSession session = (HttpSession) sc
					.getAttribute(authenticationFormBean.getLoginName());
			if (session != null) {
				logger
						.info("duplicate session.Invalidating old session and creating a new session");
				sc.removeAttribute(authenticationFormBean.getLoginName());

				try {
					session.invalidate();
				} catch (Exception e) {

				}
				// creating new session and saving it in servlet context

			} else {

				logger.info("new request and creating a fresh session");

			}
			session = request.getSession(true);
			sc.setAttribute(authenticationFormBean.getLoginName(), session);
			/** **************************************************************** */
			UserSessionContext userSessionContext = null;
			if ((userBean != null) && (userBean.getLoginId() != null)) {
				logger.info(userBean.getLoginId() + " successfully Logged in.");
				userSessionContext = new UserSessionContext();
				userSessionContext.setLoginName(loginName);
				userSessionContext.setId(Long.parseLong(userBean.getLoginId()));
				userSessionContext.setType(userBean.getType());
				
				// AuthenticationService autheticationservice = new
				// AuthenticationServiceImpl();
				if (userSessionContext.getType().equalsIgnoreCase(
						Constants.USER_TYPE_EXTERNAL)) {
					DPCreateAccountService accService = new DPCreateAccountServiceImpl();
					DPCreateAccountDto account = new DPCreateAccountDto();
					/* Get Account Details for login Id */
					account = accService.getAccount(userSessionContext.getId());
					userSessionContext.setLoginName(account.getAccountCode());
					userSessionContext.setContactName(account.getContactName());
					userSessionContext.setGroupId(account.getGroupId());
					userSessionContext.setCircleId(account.getCircleId());
					userSessionContext.setHeirarchyId(account.getHeirarchyId());
					//userSessionContext.setBillableAccId(account
						//	.getBillableCodeId());
					userSessionContext.setAccountAddress(account
							.getAccountAddress());
					userSessionContext.setCircleCode(account.getCircleCode());
					userSessionContext.setLapuMobileNo(account.getLapuMobileNo());
					userSessionContext.setTradeId(account.getTradeId());
					userSessionContext.setTradeCategoryId(account.getTradeCategoryId());
					userSessionContext.setAccountLevel(account.getAccountLevel());
					userSessionContext.setAccesslevel(account.getAccountLevelId());
					userSessionContext.setAccountCode(account.getCode());
					// check logged in user have circle is active or not
					autheticationservice.isCircleActive(account.getCircleId());

				} else {
					UserService userService = new UserServiceImpl();
					User user = new User();
					/* Get User Details for login Id */
					user = userService.getUser(userSessionContext.getId());
					userSessionContext.setLoginName(user.getLoginName());
					userSessionContext.setGroupId(user.getGroupId());
					userSessionContext.setCircleId(user.getCircleId());
					// check if logged in user admin. or circle admin
					// and check user have circle active or inactive
					if (user.getCircleId() != 0) {
						autheticationservice.isCircleActive(user.getCircleId());
					}
				}
			}

			if (userSessionContext != null) {

				logger.info(" Authenticated loginName = " + loginName);

				/* set authenticated user details into session */
				session.setAttribute(Constants.AUTHENTICATED_USER,
						userSessionContext);

				/*---Get user group roles to determine navigation access---*/
				ArrayList roleList = new ArrayList();
				LinkedHashMap roleLinkMap = new LinkedHashMap();
				long groupId = userSessionContext.getGroupId();

				// Getting roles for group
				logger.info(" *****SANJAY*****Get User Credentials " + loginName);
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				
				roleList = authorizationInterface.getUserCredentials(groupId, ChannelType.WEB);
				logger.info(" Load Role Link Map ");
				// Loading All Role - link mappings.
				// TODO Ashish C, Sep 20:Ideally roleLinkMap should be loaded
				// at application level.
				roleLinkMap = AuthorizationFactory.getAuthorizerImpl()
						.loadRoleLinkMap();
				logger.info(" Loaded");
				ArrayList<Link> arrSysConfigLinks = new ArrayList<Link>();
				ArrayList<Link> arrAcctMgmtLinks = new ArrayList<Link>();
				ArrayList<Link> arrMoneyTranLinks = new ArrayList<Link>();
				ArrayList<Link> arrSysAdminLinks = new ArrayList<Link>();
				ArrayList<Link> arrUssdActvLinks = new ArrayList<Link>();
				ArrayList<Link> arrBalEnqLinks = new ArrayList<Link>();
				ArrayList<Link> arrReportLinks = new ArrayList<Link>();
				ArrayList<String> addedLinks = new ArrayList<String>();

				String roleName = "";
				String topLinkName = "";
				Link link = null;
				ArrayList linkArray = null;
				
				logger.info(" XXXXXXXXXXXXXXXXXXXXXXXXX");
				if(roleList != null && roleList.size() > 0) {
					for(int i = 0; i < roleList.size();i++) {
						logger.info("roleList["+i+"]:"+ roleList.get(i));
					}
				}
				

				if (roleList != null && roleLinkMap != null) {

					Iterator itr = roleList.iterator();
					while (itr.hasNext()) {
						roleName = (String) itr.next();
						Object o = roleLinkMap.get(roleName);
						if (o != null) {
							linkArray = (ArrayList) o;
							Iterator linkItr = linkArray.iterator();
							while (linkItr.hasNext()) {

								link = (Link) linkItr.next();
								if (link == null) {
									continue;
								}

								// Don't re-add link which is already added from
								// different role.
								if (addedLinks.contains(link.getLinkId())) {
									continue;
								}

								topLinkName = link.getTopLinkName();
								if (topLinkName == null) {
									continue;// null coming for Change
												// password
									// link
								}
								// Categorize links based on parent link
								if (topLinkName
										.equalsIgnoreCase(SYSTEM_CONFIGURATION)) {
									arrSysConfigLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(ACCOUNT_MANAGEMENT)) {
									arrAcctMgmtLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(MONEY_TRANSACTION)) {
									arrMoneyTranLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(SYSTEM_ADMIN)) {
									arrSysAdminLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(USSD_ADMIN)) {
									arrUssdActvLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(QUERIES)) {
									arrBalEnqLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(REPORTS)) {
									arrReportLinks.add(link);
								}
								addedLinks.add(link.getLinkId());

							}
						}
					}
				}
				authenticationFormBean.setArrAcctMgmtLinks(arrAcctMgmtLinks);
				authenticationFormBean.setArrMoneyTranLinks(arrMoneyTranLinks);
				authenticationFormBean.setArrSysAdminLinks(arrSysAdminLinks);
				authenticationFormBean.setArrSysConfigLinks(arrSysConfigLinks);
				authenticationFormBean.setArrUssdActvLinks(arrUssdActvLinks);
				authenticationFormBean.setArrBalEnqLinks(arrBalEnqLinks);
				authenticationFormBean.setArrReportLinks(arrReportLinks);
				session.setAttribute("AuthenticationFormBean",authenticationFormBean);
				
				if (roleList.contains("ROLE_AD"))
				{
					return mapping.findForward(DISTRIBUTOR_LOGIN);
				}
				
				/*---END:Get user group roles to determine navigation access---*/
			} else {
				logger.error("Error Getting User Details for  "
						+ authenticationFormBean.getLoginName());
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.login.user_invalid"));
				saveErrors(request, errors);
				return mapping.findForward(AUTHENTICATION_FAILURE);
			}
		} catch (ValidationException exp) {
			logger.error("Error Authenticating User  "
					+ authenticationFormBean.getLoginName()
					+ " Error Message  " + exp.getMessage());

			ActionErrors errors = new ActionErrors();
			if (exp.getMessageId().equals("msg.security.id02")) {
				// errors.add("validationerror", new
				// ActionError("msg.security.id02"));
				// saveErrors(request, errors);

				// TODO Error message to be printed using request scope,here
				// done using session scope.
				request.getSession().setAttribute(
						"message",
						ResourceReader.getValueFromBundle("msg.security.id02",
								"ApplicationResources"));
				logger.info("Redirecting to change password.."
						+ mapping.findForward(PASSWORD_EXPIRED));
				return mapping.findForward(PASSWORD_EXPIRED);
			}
			String application_user = ResourceReader.getWebResourceBundleValue("user.username");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(exp
					.getMessageId(), application_user));
			saveErrors(request, errors);
		
			return mapping.findForward(AUTHENTICATION_FAILURE);
		} catch (VirtualizationServiceException vse)
		// TODO paras 15 oct 2007 change messages accordingly
		{
			logger.error("Error Authenticating User  "
					+ authenticationFormBean.getLoginName()
					+ " Error Message  " + vse.getMessage());
 
			ActionErrors errors = new ActionErrors();
			String application_user = ResourceReader.getWebResourceBundleValue("application.user");
			// if error thrown by GSD
			if (vse.getMessageId() != null) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(vse
						.getMessageId(), application_user));
			} else {
				// if error thrown by service while checking circle active or
				// not
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(vse
						.getMessage()));
			}
			saveErrors(request, errors);
			return mapping.findForward(AUTHENTICATION_FAILURE);
		} catch (NullPointerException ex) {

			logger.error("Error Database Connection .........   "
					+ ex.getMessage());

			ActionErrors errors = new ActionErrors();

			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.db.create_connection"));

			saveErrors(request, errors);
			return mapping.findForward(AUTHENTICATION_FAILURE);
		}

		logger.info(" Executed... ");
		return mapping.findForward(AUTHENTICATION_SUCCESS);
	}
}