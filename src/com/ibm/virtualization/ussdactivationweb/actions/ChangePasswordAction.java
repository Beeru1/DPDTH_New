/**************************************************************************
 * File     : ChangePasswordAction.java
 * Author   : Pragati
 * Created  : oct 6, 2008
 * Modified : oct 6, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.actions;

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

import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.virtualization.ussdactivationweb.beans.ChangePasswordForm;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.UserMasterDAO;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.USSDException;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * This is the Action Class for Changing Password.
 * 
 * @version 1.0
 * @author Pragati
 */
public class ChangePasswordAction extends DispatchAction {

	private static final Logger logger = Logger
			.getLogger(ChangePasswordAction.class);

	
	/**	This method initChangePassword load the page for change password
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initChangePassword(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			/**
			 * validating If user has logged in for the first Time,redirect it
			 * to Change Password Screen
			 */
			UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
					.getAttribute(Constants.USER_INFO);
				forward = mapping.findForward("ChangePasswordLeftHeader");
			logger.info("User "+ userBean.getLoginId() +"Has entered Change Password Screen");
		} catch (Exception e) {
			// Report the error using the appropriate name and ID.
			logger.error("Exception occured in change password.", e);
		}
		return forward;
	}

	
	/**	This method processChangePassword validates the password through GSD
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward processChangePassword(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserMasterDAO userMasterdao = new UserMasterDAO();
		String checkForMachingCharactersInPwdLogin = "";
		Encryption Encrypt = null;
		String strOlddigest = "";
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		UserDetailsBean userBean = (UserDetailsBean) session
				.getAttribute("USER_INFO");
		
		/**Server Side validation**/
		String oldPassword = changePasswordForm.getOldPassword();
			if(oldPassword.equalsIgnoreCase(null)|| oldPassword.equalsIgnoreCase("")){
				errors.add("CHANGE_PASSWORD_OLD_PASSWORD_BLANK", new ActionError(
				"CHANGE_PASSWORD_OLD_PASSWORD_BLANK"));
				saveErrors(request, errors);
				return mapping.findForward("ChangePasswordLeftHeader");
			}
			
			if(oldPassword.trim().length()< 8){
				errors.add("CHANGE_PASSWORD_OLD_PASSWORD_LESS_MIN_CHAR", new ActionError(
				"CHANGE_PASSWORD_OLD_PASSWORD_LESS_MIN_CHAR"));
				saveErrors(request, errors);
				return mapping.findForward("ChangePasswordLeftHeader");
			}
			
		String newPassword= changePasswordForm.getNewPassword();
		if(newPassword.equalsIgnoreCase(null)|| newPassword.equalsIgnoreCase("")){
			errors.add("CHANGE_PASSWORD_NEW_PASSWORD_BLANK", new ActionError(
			"CHANGE_PASSWORD_NEW_PASSWORD_BLANK"));
			saveErrors(request, errors);
			return mapping.findForward("ChangePasswordLeftHeader");
		}
		
		if(newPassword.trim().length()< 8){
			errors.add("CHANGE_PASSWORD_NEW_PASSWORD_LESS_MIN_CHAR", new ActionError(
			"CHANGE_PASSWORD_NEW_PASSWORD_LESS_MIN_CHAR"));
			saveErrors(request, errors);
			return mapping.findForward("ChangePasswordLeftHeader");
		}
		
		String reNewPassword= changePasswordForm.getConfirmPassword();
		if(reNewPassword.equalsIgnoreCase(null)|| reNewPassword.equalsIgnoreCase("")){
			errors.add("CHANGE_PASSWORD_RE_NEW_PASSWORD_BLANK", new ActionError(
			"CHANGE_PASSWORD_RE_NEW_PASSWORD_BLANK"));
			saveErrors(request, errors);
			return mapping.findForward("ChangePasswordLeftHeader");
		}
		if(reNewPassword.trim().length()< 8){
			errors.add("CHANGE_PASSWORD_RE_NEW_PASSWORD_LESS_MIN_CHAR", new ActionError(
			"CHANGE_PASSWORD_RE_NEW_PASSWORD_LESS_MIN_CHAR"));
			saveErrors(request, errors);
			return mapping.findForward("ChangePasswordLeftHeader");
		}
		
		try {
			/**
			 * checking if new password has more than 3 character matches with
			 * loginId*
			 */
			checkForMachingCharactersInPwdLogin = Utility
					.checkForMachingCharactersInPwdLogin(changePasswordForm
							.getNewPassword(), userBean.getLoginId());
			if (("true").equals(checkForMachingCharactersInPwdLogin)) {

				errors.add(Constants.ERRORPWD, new ActionError(
						Constants.ERRORPWD));
				saveErrors(request, errors);
				logger.info("Password exists as a part of the Login Id :: "
						+ userBean.getLoginId());
				return mapping.findForward("ChangePasswordLeftHeader");
			}
			/** Authenticating user through GSD* */

			GSDService gSDService = new GSDService();

			gSDService.validateChangePwd(userBean.getLoginId(),
					changePasswordForm.getNewPassword(), changePasswordForm
							.getOldPassword());	
			int intpass = 0;
			/** encrpting password with GSD provided Generate Digest method* */
			Encrypt = new Encryption();
			String strNewDigest = Encrypt.generateDigest(changePasswordForm
					.getNewPassword());
			strOlddigest = Encrypt.generateDigest(changePasswordForm
					.getOldPassword());
			String password = userMasterdao.getCurrentPassword(Integer
					.parseInt(userBean.getUserId()));
			if (password.equalsIgnoreCase(strOlddigest)) {
				/** Calling Dao to change Password * */
				intpass = userMasterdao.changePassword(userBean.getLoginId(), Integer
					.parseInt(userBean.getUserId()), strNewDigest);
			} else {
				errors.add("oldPassword", new ActionError("msg.security.id015"));
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
				}
				return initChangePassword(mapping, form, request, response);
			}
			/** Maintaining history of User password**/
			if (intpass == 1) {
				logger.info("Maintaining History for user -"+ userBean
						.getUserId());
				userMasterdao.insertPasswordHistory(Integer.parseInt(userBean
						.getUserId()), strNewDigest);
				/** ** changing the status of user after changing password *** */

				if (userBean != null) {
					logger.info("Password is changed for user - "+ userBean
						.getUserId());
					userBean.setLoginStatus(Constants.InActiveState);
					if (userBean.getFirstTimeLogIn() == 0) {
						userMasterdao.updatFirstTimeStatus(userBean);
					}
					userMasterdao.updateLoginStatus(userBean);

					errors.add("msg.security.passwordchanged", new ActionError(
							"msg.security.passwordchanged"));
					if (!errors.isEmpty()) {
						saveErrors(request, errors);
					}
					return mapping.findForward("showLoginJSP");
				}
			}
		} catch (USSDException ussdException) {
			ussdException.printStackTrace();
			logger.error("Exception occur in change password."
					+ ussdException.getMessage());
			if (ussdException.getErrCode().equalsIgnoreCase(
					ErrorCodes.SQLEXCEPTION)) {
				errors.add("SYS002", new ActionError("SYS002"));
					} else if (ussdException.getErrCode().equalsIgnoreCase(
					ErrorCodes.SYSTEMEXCEPTION)) {
				errors.add("SYS001", new ActionError("SYS001"));
		
			}
		}

		catch (Exception e) {
			if (e.getMessage().equalsIgnoreCase("Password expired")) {
				errors.add("msg.security.id02", new ActionError(
						"msg.security.id02"));
			} else if (e.getMessage().equalsIgnoreCase(
					"Password provided exists in History")) {
				errors.add("msg.security.id01", new ActionError(
						"msg.security.id01"));
			} else if (e.getMessage().equalsIgnoreCase(
					"User or password length must not be leaa than 8")) {
				errors.add("msg.security.id03", new ActionError(
						"msg.security.id03"));
			} else if (e.getMessage().equalsIgnoreCase(
					"First and Last Character of Password Must Not Be Numeric")) {
				errors.add("msg.security.id04", new ActionError(
						"msg.security.id04"));
			} else if (e.getMessage().equalsIgnoreCase(
					"Empty white space not allowed in user Id or password")) {
				errors.add("msg.security.id05", new ActionError(
						"msg.security.id05"));
			} else if (e.getMessage().equalsIgnoreCase(
			"Identical characters in password exceeds permissible limit")) {
				errors.add("msg.security.id06", new ActionError(
						"msg.security.id06"));
			} else if (e.getMessage().equalsIgnoreCase(
					"UserId exists as a part of the password")) {
				errors.add("msg.security.id07", new ActionError(
						"msg.security.id07"));
			} else if (e.getMessage().equalsIgnoreCase(
					"Pasword must contain atleast one alphabetic character")) {
				errors.add("msg.security.id08", new ActionError(
						"msg.security.id08"));
			} else if (e.getMessage().equalsIgnoreCase(
			"New password exceeds permissible limit of matching characters in the previous password")) {
				errors.add("msg.security.id09", new ActionError(
						"msg.security.id09"));
			} else if (("invalid special character in password")
					.equalsIgnoreCase(e.getMessage())) {
				errors.add("msg.security.id010", new ActionError(
						"msg.security.id010"));
			} else if (e.getMessage().equalsIgnoreCase("invalid userId")) {
				errors.add("msg.security.id011", new ActionError(
						"msg.security.id011"));	
			} else if (e.getMessage().equalsIgnoreCase(
			"Password must contain at least one non-alphabetic character")) {
				errors.add("msg.security.id012", new ActionError(
						"msg.security.id012"));
			} else if (e.getMessage().equalsIgnoreCase("User Login Failed")) {
				errors.add("msg.security.id013", new ActionError(
						"msg.security.id013"));
			} else if (e.getMessage().equalsIgnoreCase("User Account Locked")) {
				errors.add("msg.security.id014", new ActionError(
						"msg.security.id014"));
			} else {
				logger.debug("some other error has occured than GSD errors");
			}
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return initChangePassword(mapping, form, request, response);
	}
}