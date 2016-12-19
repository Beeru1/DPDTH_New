/**************************************************************************
 * File     : LoginAction.java
 * Author   : Pragati
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
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

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.virtualization.ussdactivationweb.beans.LoginForm;
import com.ibm.virtualization.ussdactivationweb.beans.ModuleBean;

import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserFormBean;
import com.ibm.virtualization.ussdactivationweb.dao.UserMasterDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
//import com.ibm.virtualization.ussdactivationweb.dto.UserDTO;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.USSDException;

/*******************************************************************************
 * This class is action class for login
 * 
 * @author Pragati
 * @version 1.0
 ******************************************************************************/
public class LoginAction extends DispatchAction {

	protected static Logger logger = Logger.getLogger(LoginAction.class
			.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		return mapping.findForward("login");

	}

	public ActionForward inithome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return mapping.findForward("homepg");

	}

	/**	This method is responsible for places user details into session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("Entering method-login()");
		GSDService gsdService = new GSDService();
		LoginForm loginForm = (LoginForm) form;
		UserMasterDAO userMasterDAO = new UserMasterDAO();
		String encryptedPassword = null;
		HttpSession session = request.getSession(true);
		ActionErrors errors = new ActionErrors();
		
		/**Server Side validation**/
		String loginId= loginForm.getLoginId();
		if(loginId.equalsIgnoreCase(null) || loginId.equalsIgnoreCase("")){
			errors.add("Login.LOGINID_BLANK", new ActionError(
			"Login.LOGINID_BLANK"));
			saveErrors(request, errors);
			return mapping.findForward("login");
		}
		
		String password = loginForm.getPassword();
		if(password.equalsIgnoreCase(null) || password.equalsIgnoreCase("")){
			errors.add("Login.PASSWORD_BLANK", new ActionError(
			"Login.PASSWORD_BLANK"));
			saveErrors(request, errors);
			return mapping.findForward("login");
		}
		
		
		/**Creating Array odject for menu**/
		ArrayList section1 = new ArrayList();
		ArrayList section2 = new ArrayList();
		ArrayList section3 = new ArrayList();
		ArrayList section4 = new ArrayList();
		ArrayList section5 = new ArrayList();
		ArrayList section6 = new ArrayList();
		ArrayList section7 = new ArrayList();

		UserDetailsBean userBean = new UserDetailsBean();

		try {
			Encryption encryption = new Encryption();
			encryptedPassword = encryption.generateDigest(loginForm
					.getPassword());
		} catch (EncryptionException Enex) {
			logger.error("Exception occured at EncryptionException "
					+ Enex.getMessage(), Enex);
		}
		try {
			//UserDTO userDto = new UserDTO();
			String circleStatus = userMasterDAO.getCircleStatus(loginForm
					.getLoginId());
			if (circleStatus != null && ("0").equalsIgnoreCase(circleStatus)) {
				errors.add("msg.security.id0125", new ActionError(
						"msg.security.id0125"));
				saveErrors(request, errors);
				return mapping.findForward("circleStatus");
			}
			//				userBean = (com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean) gsdService
			//						.validateCredentials(loginForm.getLoginId(), loginForm
			//								.getPassword(),
			//								"com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean");
			//				
			//userDto = (com.ibm.virtualization.ussdactivationweb.dto.UserDTO) 
			gsdService.validateCredentials(loginForm.getLoginId(), loginForm
							.getPassword(),
							"com.ibm.virtualization.ussdactivationweb.dto.UserDTO");

			/*if (userDto != null) {
				logger.debug("gsd return DTO object");
			}*/

			/**Fetching Values from database**/
			userBean = userMasterDAO.retrieveUserDetails(
					loginForm.getLoginId(), encryptedPassword);

			ArrayList menuList = userMasterDAO.retrieveMenu(loginForm
					.getLoginId());
			if (menuList != null && !menuList.isEmpty()) {
				for (int i = 0; i < menuList.size(); i++) {
					ModuleBean mBean = (ModuleBean) menuList.get(i);

					if (mBean.getSection().equalsIgnoreCase("1")) {
						section1.add(menuList.get(i));
					} else if (mBean.getSection().equalsIgnoreCase("2")) {
						section2.add(menuList.get(i));
					} else if (mBean.getSection().equalsIgnoreCase("3")) {
						section3.add(menuList.get(i));
					} else if (mBean.getSection().equalsIgnoreCase("4")) {
						section4.add(menuList.get(i));
					} else if (mBean.getSection().equalsIgnoreCase("5")) {
						section5.add(menuList.get(i));
					} else if (mBean.getSection().equalsIgnoreCase("6")) {
						section6.add(menuList.get(i));
					} else {
						section7.add(menuList.get(i));
					}
				}
			}

			userBean.setCircleList(ViewEditCircleMasterDAOImpl.getCircleList());

			session.setAttribute("USER_INFO", userBean);
			session.setAttribute("SECTION1", section1);
			session.setAttribute("SECTION2", section2);
			session.setAttribute("SECTION3", section3);
			session.setAttribute("SECTION4", section4);
			session.setAttribute("SECTION5", section5);
			session.setAttribute("SECTION6", section6);
			session.setAttribute("SECTION7", section7);
			session.setAttribute("MENULIST", menuList);
			

			/** Single signon Functionality; Need to enable in future **/
			String flag = userBean.getLoginStatus();
			if (((Constants.ActiveState).equalsIgnoreCase(flag))
					|| flag == Constants.ACTIVE) {
				logger.info("\n Trying multiple login in session  "
						+ " MULTIPLE_ENTRY_IN_SESSION ");
				errors.add("Login.MULTIPLE_ENTRY_IN_SESSION", new ActionError(
						"Login.MULTIPLE_ENTRY_IN_SESSION"));
				saveErrors(request, errors);
				return mapping.findForward("login");
				
			} else {
				logger.info("\n USER " + userBean.getLoginId()
						+ " HAS LOGGED IN \n");

			}

			

			/****** Update login status and first_time_login 
			 * and User Ip Address fields  for logged in user******/

			if (userBean != null) {
				userBean.setUserIp(request.getRemoteAddr());
				userBean.setLoginStatus(Constants.ActiveState);
				userMasterDAO.updateLoginStatus(userBean);
			}
			
			/**
			 * validating If user has logged in for the first Time,redirect it to
			 * Change Password Screen
			 */

			if (userBean.getFirstTimeLogIn() == Constants.FIRST_TIME_LOGIN) {
				return mapping.findForward("ChangePasswordLeftHeader");
			}

		} catch (USSDException e) {

			logger.error("Exception has occur in login action.", e);
			if (e.getErrCode().equalsIgnoreCase(ErrorCodes.SQLEXCEPTION)) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (e.getErrCode().equalsIgnoreCase(
					ErrorCodes.SYSTEMEXCEPTION)) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION=",
						new ActionError("Common.MSG_APPLICATION_EXCEPTION="));
			}
			//session.setAttribute(Constants.ERRORS, errors);
		} catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Null Pointer Exception occured");
			} else if (e.getMessage().equalsIgnoreCase("User Login Failed")) {
				errors.add("msg.security.id0127", new ActionError(
						"msg.security.id0127"));
			} else if (e.getMessage().equalsIgnoreCase("Password expired")) {
				errors.add("msg.security.id02", new ActionError(
						"msg.security.id02"));
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					/***********************************************************
					 * ** setting values to Bean and then setting that Bean to
					 * session because when password expires session is null
					 **********************************************************/
					UserDetailsBean userBean1 = new UserDetailsBean();
					userBean1.setLoginId(userBean.getLoginId());
					userBean1.setPassword(userBean.getPassword());
					session.setAttribute(Constants.USER_INFO, userBean1);
					return mapping.findForward("ChangePasswordLeftHeader");
				}
			} else if (("User Account Locked").equalsIgnoreCase(e.getMessage())) {
				errors.add("msg.security.id014", new ActionError(
						"msg.security.id014"));
			}

			else {
				logger.debug("Exception occued at Login Action"
						+ e.getMessage());
			}

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			/* In case error occurs bean is set to null */
			loginForm.setLoginId("");
			loginForm.setPassword("");
			return mapping.findForward("showLoginJSP");
		}
		logger.debug("Exiting the login() of login Action class ");
		return mapping.findForward("homepage");

	}

	/**	This method redirect to user to login pageand making session invalidate
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("Entering method --logout() of login action class ");
		HttpSession session = request.getSession(false);
		UserDetailsBean userBean = null;
		UserMasterDAO userMasterDAO = new UserMasterDAO();
		ActionErrors errors = new ActionErrors();
		try {
			if (session == null
					|| (session != null && session
							.getAttribute(Constants.USER_INFO) == null)) {
				errors.add("Login.SESSION_HAS_EXPIRED", new ActionError(
						"Login.SESSION_HAS_EXPIRED"));
				logger.info(" \nSession not found ");
				saveErrors(request, errors);
				return mapping.findForward("login");
			} else {
				userBean = (UserDetailsBean) session
						.getAttribute(Constants.USER_INFO);

				if (userBean != null) {
					userBean.setLoginStatus(Constants.InActiveState);
					userMasterDAO.updateLoginStatus(userBean);
				}

				/**
				 * session invalidate calls
				 * SessionManagement.sessionDestroyed()*
				 */
				//	session.invalidate();
				session = null;
				errors.add("Login.SESSION_HAS_EXPIRED", new ActionError(
						"Login.SESSION_HAS_EXPIRED"));
				logger.info(" \n Session not found ");
				saveErrors(request, errors);
			}
			logger.debug("\nUSER " + userBean.getLoginId()
					+ " HAS LOGGED OUT\n ");
			logger.info("\nUSER " + userBean.getLoginId()
					+ " HAS LOGGED OUT \n ");

			logger.debug("Session inside login action logout " + session);
			return mapping.findForward("doLogout");
		} catch (Exception e) {
			logger.error(" Exception- logout() " + e.getMessage(), e);
			errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
					"Common.MSG_APPLICATION_EXCEPTION"));
		}
		if (!errors.isEmpty()) {
			getResources(request, "error");
			saveErrors(request, errors);
		}
		logger.debug("Exiting method- logout() of login action class ");
		return mapping.findForward("doLogout");
	}

	/**	This method unauthorized redirect unauthorized request to unauthorized page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward unauthorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("Entering method --unauthorized()");
		ActionErrors errors = new ActionErrors();
		errors.add("loginAction.Unauthorized_Excess", new ActionError(
				"loginAction.Unauthorized_Excess"));
		saveErrors(request, errors);
		UserDetailsBean userBean = (UserDetailsBean) request.getSession()
				.getAttribute(Constants.USER_INFO);
		String userLoginId = "";
		if (userBean != null) {
			userLoginId = userBean.getLoginId();
		}
		logger.info(userLoginId + " - UNAUTHORIZED ACCESS ");
		logger.debug("Exiting method unauthorized()");
		return mapping.findForward("errorJSP");
	}

	/**	This method calls a new JSP with error message unthorized
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward unauthorizedLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Entering method --unauthorized()");
		ActionErrors errors = new ActionErrors();
		errors.add("Login.UNAUTHORIZED", new ActionError("Login.UNAUTHORIZED"));
		UserFormBean bean = (UserFormBean) form;
		if (bean != null) {
			logger
			.info("UNAUTHORIZED LOGIN ATTEMPTED USING INVALID HTTP METHOD BY: "
							+ bean.getLoginId());
			bean.setLoginId(null);
			bean.setPassword(null);
		} else {
			logger.info("UNAUTHORIZED LOGIN ATTEMPTED");
		}

		logger.debug("Exiting method unauthorized()");
		return mapping.findForward("doLogout");
	}
}
