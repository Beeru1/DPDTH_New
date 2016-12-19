/**************************************************************************
 * File     : UserMasterAction.java
 * Author   : Banita
 * Created  : Sep 6, 2008
 * Modified : Sep 6, 2008
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserMasterBean;
import com.ibm.virtualization.ussdactivationweb.dao.LocationDataDAO;
import com.ibm.virtualization.ussdactivationweb.dao.UserMasterDAO;
import com.ibm.virtualization.ussdactivationweb.dao.VUserMstrDaoImpl;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
//import com.ibm.virtualization.ussdactivationweb.dto.UserDTO;
import com.ibm.virtualization.ussdactivationweb.dto.VUserMstrDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;
import com.ibm.virtualization.ussdactivationweb.utils.email.EmailUtility;

/*******************************************************************************
 * This class helps to manage the user related methods like create user, edit
 * user and find user.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/

public class UserMasterAction extends DispatchAction {

	private static final Logger logger = Logger
			.getLogger(UserMasterAction.class);



	/**
	 * This method will be called when create user usecase will be initialize.
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return Object of ActionForward class
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("Entering into init() method of UserMasterAction.");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			UserMasterBean bean = (UserMasterBean) form;
			String emailId = (String) request.getSession().getAttribute(
					"emailId");
			if (emailId != null) {
				bean.setLoginId(emailId);
				
			}
			
		
			if ("request".equalsIgnoreCase(mapping.getScope())) {
				request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
						.getCircleList());
			} else {
				session = request.getSession();
				session.setAttribute("circleList", ViewEditCircleMasterDAOImpl
						.getCircleList());
			}
			UserDetailsBean userBean = (UserDetailsBean) (request
					.getSession()).getAttribute("USER_INFO");
			/** getting zone list for drop down* */
			LocationDataDAO locDAO = new LocationDataDAO();
			if (bean.getCircleCode() != null &&  !("-1").equalsIgnoreCase(bean
					.getCircleCode())){
				ArrayList zoneList = locDAO.getLocationList(bean
						.getCircleCode(), Constants.Zone, Constants.PAGE_FALSE,
						0, 0);
				if (!zoneList.isEmpty()) {
					bean.setZoneList(zoneList);
					session.setAttribute("zoneList", zoneList);
				} 
				else {
					errors.add("USER_NO.ZONE.LIST", new ActionError(
						"USER_NO.ZONE.LIST"));
			}
			}
//			else {
//				errors.add("User_ERROR_MSG_Circle", new ActionError(
//					"User_ERROR_MSG_Circle"));
//			}
			
			if(bean.getFlag()!= null){
				if(bean.getFlag().equalsIgnoreCase("zone")){
					bean.setLoginId(bean.getMailId());
					bean.setUserTypeName(bean.getUserType());
					bean.setCircleCode(bean.getCircleCode());
				}
			}
			logger.info(" init() method of UserMasterAction initialized "+ bean.getLoginId());
		} catch (Exception e) {
			logger.error("Exception occured in init() method of UserMasterAction.");
		
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
			if (!(errors.isEmpty())) {
				saveErrors(request, errors);
			}
			mapping.findForward("error");
		}
		if (!(errors.isEmpty())) {
			saveErrors(request, errors);
		}
		
//		bean.setCheckPassword(null);
		//	bean.setPassword(null);
		logger.debug("Exiting from init() method of UserMasterAction.");
		return mapping.findForward("initSuccess");
	}

	/**
	 * This method is used to Create a User if : LoginId is available and is not
	 * used by any other user LoginId and Password are GSD Compliant
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return Object of ActionForward class
	 * @throws Exception
	 */
	public ActionForward createUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("Entering into createUser() method of UserMasterAction.");
		ActionErrors errors = new ActionErrors();
		UserMasterBean createUserBean = (UserMasterBean) form;
		VUserMstrDaoImpl userMstrDAO = new VUserMstrDaoImpl();
		UserDetailsBean userBean = (UserDetailsBean) (request
				.getSession()).getAttribute("USER_INFO");
		
		
		try {
			
			HttpSession session = request.getSession();
			if ("request".equalsIgnoreCase(mapping.getScope())) {
				request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
						.getCircleList());
			} else {
				session = request.getSession();
				session.setAttribute("circleList", ViewEditCircleMasterDAOImpl
						.getCircleList());
			}
			
			/**Server Side validation**/
			String loginId = createUserBean.getMailId();
				if(loginId.equalsIgnoreCase(null)|| loginId.equalsIgnoreCase("")){
					errors.add("USER_LOGINID_BLANK", new ActionError(
					"USER_LOGINID_BLANK"));
					saveErrors(request, errors);
					return mapping.findForward("initSuccess");
				}
				
			String password= createUserBean.getPassword();
			if(password.equalsIgnoreCase(null)|| password.equalsIgnoreCase("")){
				errors.add("USER_PASSWORD_BLANK", new ActionError(
				"USER_PASSWORD_BLANK"));
				saveErrors(request, errors);
				return mapping.findForward("initSuccess");
			}
			
			if(password.trim().length()< 8){
				errors.add("USER_PASSWORD_LESS_MIN_CHAR", new ActionError(
				"USER_PASSWORD_LESS_MIN_CHAR"));
				saveErrors(request, errors);
				return mapping.findForward("initSuccess");
			}
			
			String reNewPassword= createUserBean.getCheckPassword();
			if(reNewPassword.equalsIgnoreCase(null)|| reNewPassword.equalsIgnoreCase("")){
				errors.add("CHANGE_PASSWORD_RE_NEW_PASSWORD_BLANK", new ActionError(
				"CHANGE_PASSWORD_RE_NEW_PASSWORD_BLANK"));
				saveErrors(request, errors);
				return mapping.findForward("initSuccess");
			}
			if(reNewPassword.trim().length()< 8){
				errors.add("CHANGE_PASSWORD_RE_NEW_PASSWORD_LESS_MIN_CHAR", new ActionError(
				"CHANGE_PASSWORD_RE_NEW_PASSWORD_LESS_MIN_CHAR"));
				saveErrors(request, errors);
				return mapping.findForward("initSuccess");
			}
			
			boolean isAvailable;
			boolean isGSDCompliant;
			String checkForMachingCharactersInPwdLogin ="";
			/**
			 * isAvailable will contain a boolean value isAvailable=true if no
			 * record exist in user master table with the loginId
			 * isAvailable=false if a record exist in user master table with the
			 * loginId
			 */
			checkForMachingCharactersInPwdLogin = Utility.checkForMachingCharactersInPwdLogin(createUserBean.getPassword(),
					createUserBean.getMailId());
			if(("true").equals(checkForMachingCharactersInPwdLogin)){
				
				errors.add(Constants.ERRORPWD, new ActionError(Constants.ERRORPWD));
				saveErrors(request, errors);
				logger.info("Password exists as a part of the Login Id "+createUserBean.getMailId());
				return mapping.findForward("UsedLoginId");
			}
			
			isAvailable = userMstrDAO.findByLoginId(createUserBean.getMailId()
					.trim());
			if (isAvailable == true) {
				errors.add(Constants.ERRORLOGID, new ActionError(Constants.ERRORLOGID));
				saveErrors(request, errors);
				createUserBean.setCheckPassword(null);
				createUserBean.setPassword(null);
				logger.info("LoginId is already in use"+createUserBean.getMailId());
				return mapping.findForward("UsedLoginId");
			}
			/**
			 * isGSDCompliant will contain a boolean value isGSDCompliant=true
			 * if loginId and Password are GSD Compliant isAvailable=false if
			 * loginId and Password are not GSD Compliant
			 */
			try {
				new GSDService().validateCredentials(createUserBean.getMailId(), createUserBean.getPassword());
				isGSDCompliant = true;
			} catch (ValidationException ve) {
				errors.add("loginId", new ActionError(ve.getMessageId()));
				saveErrors(request, errors);
				logger.error("User could not be created.", ve);
					request.getSession().setAttribute("emailId",
						createUserBean.getMailId());
					createUserBean.setCheckPassword("");
					createUserBean.setPassword("");
				return mapping.findForward("initSuccess");
			}

			// If record for loginId of user is not found and loginId and
			// password are GSDCompliant
			if (isAvailable == false) {
				if (isGSDCompliant == true) {
					// If no record found for loginId and Login Id and Password
					// are GSD Compliant
					// then add user Details to create a user
					VUserMstrDTO insertUserMstrDTO = new VUserMstrDTO();
					insertUserMstrDTO.setLoginId(createUserBean.getMailId());
					insertUserMstrDTO.setStatus(createUserBean.getStatus());
					insertUserMstrDTO.setCreatedBy(userBean.getUserId());
					/** because login ID and email ID are same* */
					insertUserMstrDTO.setMailId(createUserBean.getMailId());
					insertUserMstrDTO.setUpdatedBy(userBean.getUserId());
					insertUserMstrDTO.setCircleCode(createUserBean
							.getCircleCode());
					insertUserMstrDTO.setPassword(new Encryption()
							.generateDigest(createUserBean.getPassword()));
					insertUserMstrDTO.setUserType(createUserBean.getUserType());
					insertUserMstrDTO.setGroupId(createUserBean.getGroupId());
					insertUserMstrDTO.setLoginAttempted(Constants.LoginAttempted);
					insertUserMstrDTO.setLoginStatus(Constants.InitialState);
					insertUserMstrDTO.setZoneCode(createUserBean.getZoneCode());
					userMstrDAO.insert(insertUserMstrDTO);
					ActionMessages msg = new ActionMessages();
					ActionMessage message = new ActionMessage("Saved");
					msg.add(ActionMessages.GLOBAL_MESSAGE, message);
					saveMessages(request, msg);
					logger.info(" User created"+createUserBean.getMailId());
					//UserDTO userdto = new UserDTO();
					//try {
						if (userBean != null) {
							//userdto.setUserLoginId(createUserBean.getMailId());
							//userdto.setUserPass(createUserBean.getPassword());
							String strSub = Utility.getValueFromBundle(
									Constants.EMAIL_SUBJECT_FORGORT_PASSWORD,
									Constants.EMAIL_RESOURCE_BUNDLE);

							//userdto.setSubject(strSub);

							//String messageForSendingEmail = EmailUtility
							//		.FormatMsg(userdto.getUserLoginId(),
							//				userdto.getUserPass(), userdto
							//						.getUserLoginId());
							//userdto.setMessage(messageForSendingEmail);
							UserMasterDAO userDAO = new UserMasterDAO();
							//userDAO.userExists(userdto.getUserLoginId(), 1);
							//userDAO.sendingEmail(userdto);
						}
						//logger.info(userdto.getUserLoginId()
						//		+ " Forgot password is Initiated");

					/*} catch (DAOException e) {
						//logger.error(new StringBuffer(100).append(
								//userdto.getUserLoginId()).append(
								//"forgotPassword Inititalization Error - ")
								//.append(e.getMessage()).toString());
						if (ErrorCodes.User.USER_DOES_NOT_EXISTS.equals(e
								.getMessage())) {
							errors.add("USER_DOES_NOT_EXISTS", new ActionError(
									"USER_DOES_NOT_EXISTS"));
							saveErrors(request, errors);
							return mapping.findForward("forgotpwd");
						}
					}*/
					return mapping.findForward("showList");
				} else {// if isGSDCompliant
					errors.add("errors.nonGsdCompliant", new ActionError(
							"errors.nonGsdCompliant"));
					saveErrors(request, errors);
					return mapping.findForward("nonGsdUserIdPassword");
				} // isGSDCompliant else
			} else {// if isAvailable
				errors.add(Constants.ERRORLOGID, new ActionError(Constants.ERRORLOGID));
				saveErrors(request, errors);
				logger.error("LoginId is already in use");
				
				return mapping.findForward("UsedLoginId");
			} // isAvailable else
		} catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
						"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}

			saveErrors(request, errors);
		} catch (Exception e) {
			logger.error("Exception has occur in creating a user.", e);
		}
		logger.debug("Exiting from createUser() method of UserMasterAction.");
		return mapping.findForward("UsedLoginId");
	}

	/**
	 * This method It populates the details of the user selected fetches the
	 * information from the database, then populateds the forms with previous
	 * values
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return Object of ActionForward class
	 * @throws Exception
	 */
	public ActionForward getUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Entering into getUser() method of UserMasterAction.");
		String strTarget = null;
		UserMasterBean userForm = (UserMasterBean) form;
		ActionErrors errors = new ActionErrors();
		try {
			request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
					.getCircleList());
			/** getting zone list for drop down* */
			LocationDataDAO locDAO = new LocationDataDAO();
			if (userForm.getCircleCode() != null &&  !("-1").equalsIgnoreCase(userForm
					.getCircleCode())){
				ArrayList zoneList = locDAO.getLocationList(userForm
						.getCircleCode(), Constants.Zone, Constants.PAGE_FALSE,
						0, 0);
				if (!zoneList.isEmpty()) {
					userForm.setZoneList(zoneList);
					//session.setAttribute("zoneList", zoneList);
				} 	else {
					errors.add("USER_NO.ZONE.LIST", new ActionError(
						"USER_NO.ZONE.LIST"));
			}
				if (!(errors.isEmpty())) {
					saveErrors(request, errors);
				}return mapping.findForward("showEditJsp");
				
			}
			
		} catch (Exception e) {
			strTarget = "error";
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
			if (!(errors.isEmpty())) {
				saveErrors(request, errors);
				mapping.findForward(strTarget);
			}
		}
		
		VUserMstrDTO dto = new VUserMstrDTO();
		try {
			dto = new VUserMstrDaoImpl().findByPrimaryKey(userForm.getLoginId());
			userForm.setUserId(dto.getUserId());
			userForm.setMailId(dto.getLoginId());
			userForm.setStatus(dto.getStatus());
			userForm.setMailId(dto.getLoginId());
			userForm.setOldEmailId(dto.getMailId());
			userForm.setCircleCode(dto.getCircleCode());
			userForm.setHiddenCircleId(dto.getCircleCode());
			userForm.setUserType(dto.getUserType());
			userForm.setGroupId(dto.getGroupId());
			userForm.setLoginAttempted(dto.getLoginAttempted());
			userForm.setPasswordChangedDate(dto.getPasswordChangedDt()
					.toString());
			userForm.setCircleName(dto.getCircleName());
			userForm.setZoneCode(dto.getZoneCode());
			logger.info("getUser() method of UserMasterAction initialized"+dto.getLoginId());
		} catch (DAOException de) {
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
						"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}

			if (!(errors.isEmpty())) {
				saveErrors(request, errors);
			}
		} catch (Exception ex) {
			logger.error("Exception has occur.", ex);
			return mapping.findForward("showEditJsp");
		}
		logger.debug("Exiting from getUser() method of UserMasterAction.");
		return mapping.findForward("showEditJsp");
	}

	/**
	 * This method edits a user information, finally on submit validates the
	 * details and submits the information and if the user edits his login ig then an autogenerated
	 * password will be sent to the mail.
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return Object of ActionForward class
	 * @throws Exception
	 */

	public ActionForward editUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering into editUser() method of UserMasterAction.");
		ActionForward forward = new ActionForward();
		ActionErrors errors = new ActionErrors();
		UserMasterBean editUserBean = (UserMasterBean) form;
		VUserMstrDTO dto = new VUserMstrDTO();
		VUserMstrDaoImpl userMstrDAO = new VUserMstrDaoImpl();
		UserDetailsBean userBean = (UserDetailsBean) (request
				.getSession()).getAttribute("USER_INFO");
		boolean isAvailable = false;
		
		try {
			
			if (!(editUserBean.getOldEmailId().equalsIgnoreCase(editUserBean
					.getMailId()))) {
				isAvailable = userMstrDAO.findByLoginId(editUserBean
						.getMailId().trim());
				if ((isAvailable)) {
					errors.add(Constants.ERRORLOGID, new ActionError(Constants.ERRORLOGID));
					saveErrors(request, errors);
					logger.info("LoginId is already in use "+editUserBean
							.getMailId());
					request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
							.getCircleList());
					/**Server side validation**/
					String loginId = editUserBean.getMailId();
					if(loginId.equalsIgnoreCase(null)|| loginId.equalsIgnoreCase("")){
						errors.add("USER_LOGINID_BLANK", new ActionError(
						"USER_LOGINID_BLANK"));
						saveErrors(request, errors);
						return mapping.findForward("showEditJsp");
					}
					dto = userMstrDAO.findByPrimaryKey(editUserBean.getOldEmailId());
					editUserBean.setUserId(dto.getUserId());
					editUserBean.setMailId(dto.getLoginId());
					editUserBean.setStatus(dto.getStatus());
					editUserBean.setMailId(dto.getLoginId());
					editUserBean.setOldEmailId(dto.getMailId());
					editUserBean.setCircleCode(dto.getCircleCode());
					editUserBean.setHiddenCircleId(dto.getCircleCode());
					editUserBean.setUserType(dto.getUserType());
					editUserBean.setGroupId(dto.getGroupId());
					editUserBean.setLoginAttempted(dto.getLoginAttempted());
					editUserBean.setPasswordChangedDate(dto.getPasswordChangedDt()
							.toString());
					editUserBean.setPassword(dto.getPassword());
					editUserBean.setCircleName(dto.getCircleName());
					editUserBean.setZoneCode(dto.getZoneCode());
					return mapping.findForward("showEditJsp");
				} else {
					if((Constants.ActiveState).equals(userBean.getLoginStatus()) && (Constants.InActive).equals(editUserBean.getStatus())
							&& userBean.getUserId().equalsIgnoreCase(editUserBean.getUserId())){
						errors.add(Constants.ERRORSTATUS, new ActionError(Constants.ERRORSTATUS));
						saveErrors(request, errors);
						request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
								.getCircleList());
						dto = userMstrDAO.findByPrimaryKey(editUserBean.getOldEmailId());
						editUserBean.setUserId(dto.getUserId());
						editUserBean.setMailId(dto.getLoginId());
						editUserBean.setStatus(dto.getStatus());
						editUserBean.setMailId(dto.getLoginId());
						editUserBean.setOldEmailId(dto.getMailId());
						editUserBean.setCircleCode(dto.getCircleCode());
						editUserBean.setHiddenCircleId(dto.getCircleCode());
						editUserBean.setUserType(dto.getUserType());
						editUserBean.setGroupId(dto.getGroupId());
						editUserBean.setLoginAttempted(dto.getLoginAttempted());
						editUserBean.setPasswordChangedDate(dto.getPasswordChangedDt()
								.toString());
						editUserBean.setPassword(dto.getPassword());
						editUserBean.setCircleName(dto.getCircleName());
						return mapping.findForward("showEditJsp");
					}
						else{
							String password = "welcome2airtel";// "Air" + x + "tel";
							String checkForMachingCharactersInPwdLogin ="";
							checkForMachingCharactersInPwdLogin = Utility.checkForMachingCharactersInPwdLogin(password,
									editUserBean.getMailId());
							if(("true").equals(checkForMachingCharactersInPwdLogin)){
								errors.add(Constants.ERRORAUTOPWD, new ActionError(Constants.ERRORAUTOPWD));
								saveErrors(request, errors);
								logger.info("Login Id exists as part of autogenerated password "+editUserBean.getMailId());
								request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
										.getCircleList());
								dto = userMstrDAO.findByPrimaryKey(editUserBean.getOldEmailId());
								editUserBean.setUserId(dto.getUserId());
								editUserBean.setMailId(dto.getLoginId());
								editUserBean.setStatus(dto.getStatus());
								editUserBean.setMailId(dto.getLoginId());
								editUserBean.setOldEmailId(dto.getMailId());
								editUserBean.setCircleCode(dto.getCircleCode());
								editUserBean.setHiddenCircleId(dto.getCircleCode());
								editUserBean.setUserType(dto.getUserType());
								editUserBean.setGroupId(dto.getGroupId());
								editUserBean.setLoginAttempted(dto.getLoginAttempted());
								editUserBean.setPasswordChangedDate(dto.getPasswordChangedDt()
										.toString());
								editUserBean.setPassword(dto.getPassword());
								editUserBean.setCircleName(dto.getCircleName());
								return mapping.findForward("showEditJsp");
							}else{
							// Encrypting Password
									Encryption encrypt = new Encryption();
									String encryptedPwd = encrypt.generateDigest(password);
		
									String message = EmailUtility.FormatMsg(editUserBean.getMailId(),
											password, editUserBean.getMailId());
		
									String strSub = Utility.getValueFromBundle(
											Constants.EMAIL_SUBJECT_FORGORT_PASSWORD,
											Constants.EMAIL_RESOURCE_BUNDLE);
		
									dto.setSubject(strSub);
									dto.setMessage(message);
									dto.setPassword(encryptedPwd);
										dto.setUserId(editUserBean.getUserId());
										dto.setLoginId(editUserBean.getMailId());
										dto.setStatus(editUserBean.getStatus());
										dto.setMailId(editUserBean.getMailId());
										if (editUserBean.getCircleCode() != null) {
											dto.setCircleCode(editUserBean.getCircleCode());
										}
										dto.setUserType(editUserBean.getUserType());
										dto.setGroupId(editUserBean.getGroupId());
										dto.setLoginAttempted(editUserBean.getLoginAttempted());
										dto.setCreatedBy(editUserBean.getUserCreatedBy());
										dto.setUpdatedBy(userBean.getUserId());
										if(editUserBean.getZoneCode() != 0){
											dto.setZoneCode(editUserBean.getZoneCode());
										}
							}
						}
					}
				}
			/** updating values in dTO* */
			else {
				
				if((Constants.ActiveState).equals(userBean.getLoginStatus()) && (Constants.InActive).equals(editUserBean.getStatus())
						&& userBean.getUserId().equalsIgnoreCase(editUserBean.getUserId())){
					errors.add(Constants.ERRORSTATUS, new ActionError(Constants.ERRORSTATUS));
					saveErrors(request, errors);
					request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
							.getCircleList());
					dto = userMstrDAO.findByPrimaryKey(editUserBean.getOldEmailId());
					editUserBean.setUserId(dto.getUserId());
					editUserBean.setMailId(dto.getLoginId());
					editUserBean.setStatus(dto.getStatus());
					editUserBean.setMailId(dto.getLoginId());
					editUserBean.setOldEmailId(dto.getMailId());
					editUserBean.setCircleCode(dto.getCircleCode());
					editUserBean.setHiddenCircleId(dto.getCircleCode());
					editUserBean.setUserType(dto.getUserType());
					editUserBean.setGroupId(dto.getGroupId());
					editUserBean.setLoginAttempted(dto.getLoginAttempted());
					editUserBean.setPasswordChangedDate(dto.getPasswordChangedDt()
							.toString());
					editUserBean.setPassword(dto.getPassword());
					editUserBean.setCircleName(dto.getCircleName());
					return mapping.findForward("showEditJsp");
				}
					else{
						UserMasterDAO userDao= new UserMasterDAO();
						String password = userDao.getCurrentPassword(Integer.parseInt(editUserBean.getUserId()));    
							dto.setStatus(editUserBean.getStatus());
							dto.setUserId(editUserBean.getUserId());
							dto.setLoginId(editUserBean.getMailId());
							dto.setMailId(editUserBean.getMailId());
							if (editUserBean.getCircleCode() != null) {
								dto.setCircleCode(editUserBean.getCircleCode());
							}
							dto.setUserType(editUserBean.getUserType());
							dto.setPassword(password);
							dto.setGroupId(editUserBean.getGroupId());
							dto.setLoginAttempted(editUserBean.getLoginAttempted());
							dto.setCreatedBy(editUserBean.getUserCreatedBy());
							dto.setUpdatedBy(userBean.getUserId());
							dto.setZoneCode(editUserBean.getZoneCode());
							
					}
				}

			if (userMstrDAO.update(dto) == 1) {
				logger.info("User details has been updated for login id as - "+ editUserBean
						.getMailId());
				ActionMessages msg = new ActionMessages();
				ActionMessage message = new ActionMessage("Saved");
				msg.add(ActionMessages.GLOBAL_MESSAGE, message);
				saveMessages(request, msg);
				logger.info(" editUser() initialized with sucess "+editUserBean
						.getMailId());
				forward = mapping.findForward("ShowSucess");
			} else {
				errors.add(Constants.ERRORLOGID, new ActionError(Constants.ERRORLOGID));
				saveErrors(request, errors);
				logger.info("LoginId is already in use"+editUserBean
						.getMailId());
				request.setAttribute("circleList", ViewEditCircleMasterDAOImpl
						.getCircleList());
				dto = userMstrDAO.findByPrimaryKey(editUserBean.getOldEmailId());
				editUserBean.setUserId(dto.getUserId());
				editUserBean.setMailId(dto.getLoginId());
				editUserBean.setStatus(dto.getStatus());
				editUserBean.setMailId(dto.getLoginId());
				editUserBean.setOldEmailId(dto.getMailId());
				editUserBean.setCircleCode(dto.getCircleCode());
				editUserBean.setHiddenCircleId(dto.getCircleCode());
				editUserBean.setUserType(dto.getUserType());
				editUserBean.setGroupId(dto.getGroupId());
				editUserBean.setLoginAttempted(dto.getLoginAttempted());
				editUserBean.setPasswordChangedDate(dto.getPasswordChangedDt()
						.toString());
				editUserBean.setPassword(dto.getPassword());
				editUserBean.setCircleName(dto.getCircleName());
				return mapping.findForward("showEditJsp");
			}
		} catch (DAOException de) {
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
						"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}

			saveErrors(request, errors);
		} catch (Exception e) {
			logger.info("editUser Inititalization Error - " + e.getMessage());
			forward = mapping.findForward("showEditJsp");
		}
		logger.debug("Exiting from editUser() method of UserMasterAction.");
		forward = mapping.findForward("showList");
		return forward;
	}

	/**
	 * used to display list of available users
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return Object of ActionForward class
	 * @throws Exception
	 */

	public ActionForward showList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering into showList() method of UserMasterAction.");
		UserMasterBean listUserBean = (UserMasterBean) form;
		UserDetailsBean userDetailBean = (UserDetailsBean) (request
				.getSession()).getAttribute(Constants.USER_INFO);
		ArrayList userList = new ArrayList();
		VUserMstrDaoImpl userMstrDAO = new VUserMstrDaoImpl();
		try {
			int noofpages = 0;
			noofpages = userMstrDAO.countUser();
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);
			if (userDetailBean == null) {
				ActionErrors errors = new ActionErrors();
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(request, errors);
				logger.info(userDetailBean.getLoginId() + " session expired");

			} else {
				userList = (ArrayList) new VUserMstrDaoImpl().findAllUsers(
						pagination.getLowerBound(), pagination.getUpperBound());

				if (userDetailBean.getUserType().equalsIgnoreCase(
						Constants.LOGIN_TYPE_FOR_SUPER_ADMIN)) {
					listUserBean.setLoginFlag("T");
				}

				listUserBean.setDisplayDetails(userList);
				request.setAttribute("ArrList", userList);

				logger.info(" showList() of UserMasterAction initialized "+userDetailBean.getLoginId());

			}
		} catch (DAOException de) {
			ActionErrors errors = new ActionErrors();
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
						"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}

			saveErrors(request, errors);
		} catch (Exception e) {
			logger.error("showList Inititalization Error - " + e.getMessage());
			return mapping.findForward("showListJsp");
		}
		logger.debug("Exiting from showList() method of UserMasterAction.");
		listUserBean.setDisplayDetails(userList);
		return mapping.findForward("showListJsp");
	}

	/**
	 * This method is the called when we click submit button on ForgotPwd JSP.
	 * it generates new pwd for the user.
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a Jsp ForgotPwd
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 */
	public ActionForward forgotPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("Into forgotPassword(): ... of UserMasterAction");
		UserMasterBean userbean = (UserMasterBean) form;
		ActionErrors errors = new ActionErrors();
		//UserDTO userdto = new UserDTO();
		UserDetailsBean userSessionBean = (UserDetailsBean) request
				.getSession().getAttribute("USER_INFO");
		String userLoginId = (userSessionBean != null) ? userSessionBean
				.getLoginId()
				+ ": " : "";
		try {
			//userdto.setUserLoginId(userbean.getLoginId());
			UserMasterDAO userDAO = new UserMasterDAO();
			userDAO.userExists(userbean.getLoginId(), 1);
			String password = "welcome2airtel";// "Air" + x + "tel";

			// Encrypting Password
			Encryption encrypt = new Encryption();
			String encryptedPwd = encrypt.generateDigest(password);
			//userdto.setUserPass(encryptedPwd);

			String message = EmailUtility.FormatMsg(userbean.getLoginId(),
					password, userbean.getLoginId());

			String strSub = Utility.getValueFromBundle(
					Constants.EMAIL_SUBJECT_FORGORT_PASSWORD,
					Constants.EMAIL_RESOURCE_BUNDLE);

			//userdto.setSubject(strSub);
			//userdto.setMessage(message);
			//userDAO.sendingEmail(userdto);
			//userDAO.forgotPwd(userdto);

			logger.info( " New password has been send to user " + userLoginId );

		} catch (DAOException e) {
			logger.info(new StringBuffer(100).append(userLoginId).append(
					"forgotPassword Inititalization Error - ").append(
					e.getMessage()));
			if (ErrorCodes.User.USER_DOES_NOT_EXISTS.equals(e.getMessage())) {
				errors.add("USER_DOES_NOT_EXISTS", new ActionError(
						"USER_DOES_NOT_EXISTS"));
				saveErrors(request, errors);
				return mapping.findForward("forgotpwd");
			}
		} catch (Exception e) {
			logger.info(new StringBuffer(100).append(userLoginId).append(
					"Forgot password Inititalization Error - ").append(
					e.getMessage()).toString());
			if ("INVALID_EMAIL_ADDRESS".equals(e.getMessage())) {
				errors.add("INVALID_EMAIL_ADDRESS", new ActionError(
						"INVALID_EMAIL_ADDRESS"));
				saveErrors(request, errors);
				return mapping.findForward("forgotpwd");
			}
		} finally {
			logger.info("Exiting forgotPassword(): ... of UserMasterAction");
		}
		errors.add("FORGOT_PWD", new ActionError("FORGOT_PWD"));
		saveErrors(request, errors);
		return mapping.findForward("forgotpwd");
	}

	/**
	 * This method unlockingUser() unlocks the user when account of user is
	 * locked
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return success in case of successful updation
	 * @throws Exception
	 */
	/*
	public ActionForward unlockingUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger
				.debug("Entering into unlockingUser() method of UserMasterAction.");
	
		ActionErrors errors = new ActionErrors();
		UserMasterBean unlockingUserBean = (UserMasterBean) form;
		VUserMstrDTO dto = new VUserMstrDTO();
		VUserMstrDaoImpl userMstrDAO = new VUserMstrDaoImpl();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute("USER_INFO");
		int resultCount = -1;
		try {
				if (userBean.getUserType().equalsIgnoreCase(
					Constants.LOGIN_TYPE_FOR_SUPER_ADMIN)) {

				dto.setLoginId(unlockingUserBean.getLoginId());
				dto.setLoginAttempted(String
						.valueOf(Constants.RESET_NO_LOGIN_ATTEMPTS));
				dto.setLoginStatus(Constants.InActiveState);
				resultCount = userMstrDAO.updateUser(dto);
				}
			/** updating values in dTO* 
			if (resultCount == 1) {
				logger.info(dto.getLoginId()
				+ " This user has been unlocked by administrator ");
				//UserDTO userdto = new UserDTO();
				try {
					if (userBean != null) {
						userdto.setUserLoginId(unlockingUserBean.getLoginId());
						/**Sending Email*
						String strSub = Utility.getValueFromBundle(
								Constants.EMAIL_SUBJECT,
								Constants.EMAIL_RESOURCE_BUNDLE);
						String strMessgae = Utility.getValueFromBundle(
								Constants.EMAIL_MESSAGE,
								Constants.EMAIL_RESOURCE_BUNDLE);
						userdto.setSubject(strSub);
						userdto.setMessage(strMessgae);
						UserMasterDAO userDAO = new UserMasterDAO();
						/** check not required* 
						userDAO.sendingEmail(userdto);
						errors.add("UserMaster_Unlocking_Successful",
								new ActionError("UserMaster_Unlocking_Successful"));

						saveErrors(request, errors);
						return showList(mapping, form, request, response);
					}
					logger.info( " The mail has been send to user " + dto.getLoginId()
							+ "since adminstrator has unlocked his account");

				} catch (DAOException e) {
					logger.info(new StringBuffer(100).append(dto.getLoginId())
							.append("Unlock user Inititalization Error - ")
							.append(e.getMessage()));
					if (ErrorCodes.User.USER_DOES_NOT_EXISTS.equals(e
							.getMessage())) {
						errors.add("USER_DOES_NOT_EXISTS", new ActionError(
								"USER_DOES_NOT_EXISTS"));
						saveErrors(request, errors);
						return mapping.findForward("showListJsp");
					}
					ActionMessages msg = new ActionMessages();
					ActionMessage message = new ActionMessage("Saved");
					msg.add(ActionMessages.GLOBAL_MESSAGE, message);
					saveMessages(request, msg);
					logger.debug(" unlockUser() updated sucessfully");
					return mapping.findForward("showListJsp");
				}
			} else {
				errors.add("UserMaster_Error_Occured_Unlocking",
						new ActionError("UserMaster_Error_Occured_Unlocking"));

				saveErrors(request, errors);
				logger.debug("Some error has occured while updating user account");
				getUser(mapping, form, request, response);
				return showList(mapping, form, request, response);
			}
		} catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
						"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
			saveErrors(request, errors);
		} catch (Exception e) {
			logger.debug("unlockingUser() in UserMasterAction - "
					+ e.getMessage());
			return mapping.findForward("showEditJsp");
		}
		finally{
			logger.info("Exiting from unlockingUser() method of UserMasterAction.");
		}
		return mapping.findForward("showList");
		
	}
*/
}
