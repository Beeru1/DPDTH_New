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
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
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
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.ConnectionBean;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.beans.ChangePasswordFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.ChangePasswordDto;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.message.ChangePasswordMessageBean;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.SequenceService;
import com.ibm.virtualization.recharge.service.UserService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.SequenceServiceImpl;
import com.ibm.virtualization.recharge.service.impl.UserServiceImpl;
//import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * Controller class to change password.
 * 
 * @author Paras
 */
public class ChangePasswordAction extends DispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ChangePasswordAction.class
			.getName());

	/* Local Variables */
	private static final String INIT_SUCCESS = "initSuccess";

	private static final String NON_CONTEXT_INIT_SUCCESS = "nonContextInitSuccess";

	private static final String CHANGE_PASSWD_SUCCESS = "updateSuccess";

	private static final String CHANGE_PASSWD_FAILURE = "updateFailure";

	private static final String NON_CONTEXT_CHANGE_PASSWD_SUCCESS = "nonContextUpdateSuccess";

	private static final String NON_CONTEXT_CHANGE_PASSWD_FAILURE = "nonContextUpdateFailure";

	private static final String DATA_UNAVAILABLE = "nonContextInitSuccess";

	private static final String PASSWORD_EXPIRED = "passwordExpired";

	private static final String INIT_MPASSWORD_SUCCESS = "initChangeMPasswordSuccess";

	private static final String INIT_MPASSWORD_ASYNC = "initChangeMPasswordAsync";

	private static final String CHANGE_MPASSWD_SUCCESS = "updateMPasswordSuccess";

	private static final String CHANGE_MPASSWD_FAILURE = "updateMPasswordFailure";
	
	private static final String DISPLAY_LOGIN = "displayLogin";
	
	// Connection Properties For ChangePasswordQueue,OUT Queue
	private static ConnectionBean connBeanAcc = new ConnectionBean();
	static {

		try {
			/** * Setting the Resource Bundle Name for the PropertyReader File ** */

			connBeanAcc.setHost(ResourceReader.getValueFromBundle(
					"query.q.host", 
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
			// Setting Port Number
			connBeanAcc.setPort(new Integer(ResourceReader.getValueFromBundle(
					"query.q.port",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE)));
			// Setting Channel NAme
			connBeanAcc.setChannel(ResourceReader.getValueFromBundle(
					"query.q.channel", 
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
			// Setting Queue Manager Name
			connBeanAcc.setQMgr(ResourceReader.getValueFromBundle(
					"query.q.mgr",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
			// Setting Queue Name
			connBeanAcc.setQu(ResourceReader.getValueFromBundle(
					"query.q.name",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
		} catch (NumberFormatException e) {
			logger.fatal("Number format exception occured.");
		}
	}

	/**
	 * init method is called to redirect to Change Password Jsp
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
        //Added by Neetika
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		int forceToRecoPage=0;
		if(sessionContext.getAccountLevel().equalsIgnoreCase((Constants.DIST_ACCOUNT_LEVEL)))
		{
			forceToRecoPage= AuthorizationFactory.getAuthorizerImpl()
			.forceToRecoPage(sessionContext.getId());
			logger.info("forceToRecoPage =  "+forceToRecoPage);
			if(forceToRecoPage!=0)
			{
				request.setAttribute("disabledLink", Constants.disableLink);
			
			}
			
		}
		//end neetika
		/* No dropdowns to populate, Redirect to Change Password JSP. */
		logger.info(" successful  redirected to : initSuccess. ");
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * initNonContext method is called to redirect to Change Password Jsp for
	 * Non-Context User
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward initNonContext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");

		/*
		 * No dropdowns to populate, Redirect to Change Password JSP for
		 * Non-Context User.
		 */
		logger.info(" successful  redirected to : nonContextInitSuccess");
		logger.info(" Executed... ");
		return mapping.findForward(NON_CONTEXT_INIT_SUCCESS);
	}

	/**
	 * Call to change password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
        
		ChangePasswordFormBean changePasswordBean = (ChangePasswordFormBean) form;

		// call the change password processor method
		String target = processChangePassword(changePasswordBean, request, true);
		logger.info(" Executed... ");
		// redirect to the specified target
		return mapping.findForward(target);

	}

	/**
	 * Call to change password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward changePasswordNonContextUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started .....");

		ChangePasswordFormBean changePasswordBean = (ChangePasswordFormBean) form;
		HttpSession session = request.getSession(true);
		UserSessionContext userSessionContext = new UserSessionContext();
		request.getSession().removeAttribute("message");
		try {
			/* Retrieve Login Id for the Login Name */
			UserService userService = new UserServiceImpl();
			long loginId = userService.getUser(changePasswordBean
					.getLoginName());
			if (-1 == loginId) {
				logger.error(" Error Getting User Details for  "
						+ changePasswordBean.getLoginName());
				ActionErrors errors = new ActionErrors();
				errors.add("error", new ActionError(
						"errors.changepassword.invalid_loginname"));
				saveErrors(request, errors);
				return mapping.findForward(DATA_UNAVAILABLE);
			} else {

				userSessionContext.setId(loginId);
				/* set user login id into session */
				session.setAttribute(Constants.AUTHENTICATED_USER,
						userSessionContext);
				logger.info("  login id returned is " + loginId);
			}
		} catch (VirtualizationServiceException virtualSerExp) {
			logger.error(" Error Getting User Details for  "
					+ changePasswordBean.getLoginName());
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionError(virtualSerExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(DATA_UNAVAILABLE);
		}

		// call the change password processor method
		String target = processChangePassword(changePasswordBean, request,
				false);

		logger.info(" Executed... ");
		// redirect to the specified target
		return mapping.findForward(target);

	}

	/**
	 * This method changes the password for the user in the context
	 * 
	 * @param changePasswordFormBean
	 * @param userSessionContext
	 * @param request
	 * @param isLoggedInUser :
	 *            true if user changing the password is logged-in else false
	 * @return target
	 * @throws Exception
	 */
	private String processChangePassword(
			ChangePasswordFormBean changePasswordFormBean,
			HttpServletRequest request, boolean isLoggedInUser)
			throws Exception {
		logger.info(" Started...");
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info(" Request to change Password by user "
				+ userSessionContext.getId());
		ActionErrors errors = new ActionErrors();
		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
		try {
			
			/* call to check junk password in new or confirm new password */
			changePasswordDto.setNewPassword(changePasswordFormBean.getNewPassword());
			changePasswordDto.setOldPassword(changePasswordFormBean.getOldPassword());
			changePasswordDto.setConfirmPassword(changePasswordFormBean.getConfirmPassword());
			authenticationService.validateChangePassword(changePasswordDto);
//			set the requesting system IP Address as cell ID
			changePasswordDto.setCellId(request.getRemoteAddr());
			GSDService gSDService = new GSDService();
			/* call gsdService to check password history and gsd compliance. */
			logger.info(" Calling GSD to validate change password.");
			if (userSessionContext.getLoginName() == null) {
				userSessionContext.setLoginName(changePasswordFormBean
						.getLoginName());

			}
//			accountService.checkConsectiveChars(userSessionContext.getLoginName(), 
//					changePasswordFormBean.getNewPassword(), Constants.consecutiveChars);
//			 check for special, lower case and upper case characters.
			accountService.checkChars(changePasswordFormBean.getNewPassword());			
			gSDService.validateChangePwd(userSessionContext.getLoginName(),
					changePasswordFormBean.getNewPassword(),
					changePasswordFormBean.getOldPassword());
			
			
			logger.info(" GSD validation successful for change password.");
			/* Encrypt passwords entered by the user */
			IEncryption encrypt = new Encryption();
			String newDigest = encrypt.generateDigest(changePasswordFormBean
					.getNewPassword());
			String oldDigest = encrypt.generateDigest(changePasswordFormBean
					.getOldPassword());
	
			/* call changePassword method of ChangePasswordService */
			authenticationService.changePassword(userSessionContext.getId(),
					oldDigest, newDigest);
			
			

			/* show message for successful password change */
			errors.add("message", new ActionError(
					"message.changepassword.success"));
			saveErrors(request, errors);
			logger.info(" Change password successful.");

			if (isLoggedInUser) {
				logger.info(" Password updated successfully  redirect to :"
						+ CHANGE_PASSWD_SUCCESS);
				return CHANGE_PASSWD_SUCCESS;
			} else {

				logger.info(" Password updated successfully  redirect to :"
						+ NON_CONTEXT_CHANGE_PASSWD_SUCCESS);

				return NON_CONTEXT_CHANGE_PASSWD_SUCCESS;
			}

		} catch (ValidationException valExp) {
			valExp.printStackTrace();
			
//			if(valExp.getMessage().equalsIgnoreCase("checkConsectiveChars")){
//				errors.add("validationerror", new ActionError(
//				"checkConsectiveChars"));
//			}
			if(valExp.getMessage().equalsIgnoreCase("noUppercase")){
				errors.add("validationerror", new ActionError(
				"noUppercase"));
			}
			if(valExp.getMessage().equalsIgnoreCase("noSpecialCharacter")){
				errors.add("validationerror", new ActionError(
				"noSpecialCharacter"));
			}
			if(valExp.getMessage().equalsIgnoreCase("noLowerCase")){
				errors.add("validationerror", new ActionError(
				"noLowerCase"));
			}			
			if ("msg.security.id02".equalsIgnoreCase(valExp.getMessageId())) {
				errors.add("validationerror", new ActionError(
						"msg.security.id02"));
				return PASSWORD_EXPIRED;

			} else {
				String application_user = ResourceReader.getWebResourceBundleValue("application.user");
				errors.add("validationerror", new ActionError(valExp
						.getMessageId(), application_user));
			}
		} catch (VirtualizationServiceException virtualSerExp) {
			virtualSerExp.printStackTrace();
							logger.error(" VirtualizationException occured  for "
					+ userSessionContext.getId());
			errors.add("error", new ActionError(virtualSerExp.getMessage()));
			saveErrors(request, errors);
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		if (isLoggedInUser) {
			logger.info(" Password updation failed  redirect to :"
					+ CHANGE_PASSWD_FAILURE);
			logger.info(" Executed... ");
			return CHANGE_PASSWD_FAILURE;

		} else {

			logger.info(" Password updation failed  redirect to :"
					+ NON_CONTEXT_CHANGE_PASSWD_FAILURE);
			logger.info(" Executed... ");
			return NON_CONTEXT_CHANGE_PASSWD_FAILURE;

		}

	}

	/**
	 * initChangeMPassword method is called to redirect to Change M-Password Jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward initChangeMPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started...");
		 
		/* No dropdowns to populate, Redirect to Change M-Password JSP. */
		logger.info(" successful  redirected to : initChangeMPasswordSuccess");
		logger.info(" Executed... ");
		return mapping.findForward(INIT_MPASSWORD_SUCCESS);
	}

	/**
	 * initChangeMPassword method is called to redirect to Change M-Password Jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward initChangeMPasswordAsync(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started...");
				
		ChangePasswordFormBean changePasswordFormBean = (ChangePasswordFormBean) form;
		changePasswordFormBean.setAsync(true);
		/* No dropdowns to populate, Redirect to Change M-Password JSP. */
		logger.info(" successful  redirected to : initChangeMPasswordSuccess");
		logger.info(" Executed... ");
		return mapping.findForward(INIT_MPASSWORD_ASYNC);
	}

	/**
	 * Call to change M-Password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward changeMPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");

		ChangePasswordFormBean changePasswordFormBean = (ChangePasswordFormBean) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);

		try {
			
			
			/* Encrypt passwords entered by the user */
			IEncryption encrypt = new Encryption();
			String newDigest = encrypt.generateDigest(changePasswordFormBean
					.getNewPassword());
			String oldDigest = encrypt.generateDigest(changePasswordFormBean
					.getOldPassword());
			ChangePasswordMessageBean changePasswordMessageBean = new ChangePasswordMessageBean();
			
			changePasswordMessageBean.setRequestTime(Utility.getRequestTimeDateFormat() );
			// in case of sync change m password
			if (!changePasswordFormBean.getAsync()) {
				AuthenticationService authenticationService = new AuthenticationServiceImpl();

				/* call changeMPassword method of ChangePasswordService */
				logger.info(" Request to change M-Password by Account "
						+ userSessionContext.getId());
				SequenceService sequenceService = new SequenceServiceImpl();
				ChangePasswordDto changePasswordDto = new ChangePasswordDto();
				changePasswordDto
						.setSourceAccountId(userSessionContext.getId());
				changePasswordDto.setOldDigest(oldDigest);
				changePasswordDto.setNewDigest(newDigest);
				changePasswordDto.setTransactionId(sequenceService
						.getNextTransID());
				changePasswordDto.setRequestType(RequestType.CHANGEMPWD);
				changePasswordDto.setChannelType(ChannelType.WEB);
				changePasswordDto
						.setTransactionStatus(TransactionStatus.PENDING);
				changePasswordDto.setTransactionState(TransactionState.WEB);
//				set the requesting system IP Address as cell ID
				changePasswordDto.setCellId(request.getRemoteAddr());
				authenticationService.changeMPassword(changePasswordDto);

				/* show message for successful M-password change */
				errors.add("message", new ActionError(
						"message.changempassword.success"));
				saveErrors(request, errors);
			}
			// in case of async change m password
			else {

				AccountService accountService = new AccountServiceImpl();
				Account account = accountService.getAccount(userSessionContext
						.getId());
				// Generate Transaction ID
				SequenceService seqService = new SequenceServiceImpl();

				changePasswordMessageBean.setTransactionId(seqService
						.getNextTransID());
				changePasswordMessageBean.setChannelType(ChannelType.WEB);
				changePasswordMessageBean.setPassword(oldDigest);
				changePasswordMessageBean.setNewMPassword(newDigest);
				changePasswordMessageBean
						.setTransactionStatus(TransactionStatus.SUCCESS);
				changePasswordMessageBean.setRequesterMobileNo(account
						.getMobileNumber());
//				set the requesting system IP Address as cell ID
				changePasswordMessageBean.setCellId(request.getRemoteAddr());
				changePasswordMessageBean.setSourceAccountId(account
						.getAccountId());
				if (!Utility.putToQ(changePasswordMessageBean, connBeanAcc)) {
					// Print the response back to the servlet
					logger.info("Message processing failed.");
					errors.add("genericError", new ActionError(
							ExceptionCode.ERROR_TRANSACTION_FAILED));
				} else {
					logger
							.info("::Message put Successfully in Q with parameters::\nSourceMobileNumber="
									+ changePasswordMessageBean
											.getRequesterMobileNo()
									+ "\n Transaction Id = "
									+ changePasswordMessageBean
											.getTransactionId());

					errors.add("genericError", new ActionError(
							Constants.SUCCESS_TRANSACTION,
							changePasswordMessageBean.getTransactionId()));
				}
			}
			logger.info(" updation successful.");
		} catch (EncryptionException encrypt) {

			logger.error(
					" M-Password updation failed due to encryption error  redirect to :"
							+ CHANGE_MPASSWD_FAILURE, encrypt);

			/*
			 * show message for m-password change failure due to encryption
			 * exception in gsd
			 */
			errors.add("validationerror", new ActionError(
					"message.changempassword.failure"));
			saveErrors(request, errors);
			if (changePasswordFormBean.getAsync()) {
				return mapping.findForward(INIT_MPASSWORD_ASYNC);
			} else {
				return mapping.findForward(CHANGE_MPASSWD_FAILURE);
			}
		} catch (VirtualizationServiceException virtualServExp) {
			logger.error(" M-Password updation failed  redirect to :"
					+ CHANGE_MPASSWD_FAILURE);

			errors.add("error", new ActionError(virtualServExp.getMessage()));
			/* show message for m-password change failure */
			errors.add("message", new ActionError(
					"message.changempassword.failure"));
			saveErrors(request, errors);
			if (changePasswordFormBean.getAsync()) {
				return mapping.findForward(INIT_MPASSWORD_ASYNC);
			} else {
				return mapping.findForward(CHANGE_MPASSWD_FAILURE);
			}
		}

		logger.info(" Executed... ");
		saveErrors(request, errors);
		if (changePasswordFormBean.getAsync()) {
			return mapping.findForward(INIT_MPASSWORD_ASYNC);
		} else {
			return mapping.findForward(CHANGE_MPASSWD_SUCCESS);
		}
	}
	
	
	
	
	public ActionForward resetIPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" Started... ");
		ChangePasswordFormBean changePasswordBean = (ChangePasswordFormBean) form;
		changePasswordBean.setNewPassword(Utility.getRandomPassword());
		changePasswordBean.setOldPassword(changePasswordBean.getOldPassword()); 
		// call the change password processor method
		String target = processResetIPassword(changePasswordBean, request);
		logger.info(" Executed... ");
		// redirect to the specified target
		return mapping.findForward(target);

	}
	
	
	
	/**
	 * This method changes the password for the user in the context
	 * 
	 * @param changePasswordFormBean
	 * @param userSessionContext
	 * @param request
	 * @param isLoggedInUser :
	 *            true if user changing the password is logged-in else false
	 * @return target
	 * @throws Exception
	 */
	private String processResetIPassword(
			ChangePasswordFormBean changePasswordFormBean,
			HttpServletRequest request)
			throws Exception {
		logger.info(" Started...");
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info(" Request to reset Password by user "
				+ userSessionContext.getId());
		ActionErrors errors = new ActionErrors();
		try {
				
			GSDService gSDService = new GSDService();
			/* call gsdService to check password history and gsd compliance. */
			logger.info(" Calling GSD to validate reset password.");
		
			gSDService.validateChangePwd(changePasswordFormBean.getLoginName(),
					changePasswordFormBean.getNewPassword(),
					changePasswordFormBean.getOldPassword());
			logger.info(" GSD validation successful for reset password.");
			/* Encrypt passwords entered by the user */
			IEncryption encrypt = new Encryption();
			String newDigest = encrypt.generateDigest(changePasswordFormBean
					.getNewPassword());
			String oldDigest = encrypt.generateDigest(changePasswordFormBean
					.getOldPassword());
			AuthenticationService authenticationService = new AuthenticationServiceImpl();
			
			/* call changePassword method of ChangePasswordService */
			authenticationService.changePassword(userSessionContext.getId(),
					oldDigest, newDigest);
			
			/* show message for successful password change */
			errors.add("message", new ActionError(
					"message.changepassword.success"));
			saveErrors(request, errors);
			logger.info(" Change password successful.");

			

		} catch (ValidationException valExp) {
			if ("msg.security.id02".equalsIgnoreCase(valExp.getMessageId())) {
				errors.add("validationerror", new ActionError(
						"msg.security.id02"));
				return PASSWORD_EXPIRED;

			} else {
				String application_user = ResourceReader.getWebResourceBundleValue("application.user");
				errors.add("validationerror", new ActionError(valExp
						.getMessageId(), application_user));
			}
		} catch (VirtualizationServiceException virtualSerExp) {
			logger.error(" VirtualizationException occured  for "
					+ userSessionContext.getId());
			errors.add("error", new ActionError(virtualSerExp.getMessage()));
			saveErrors(request, errors);
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return "";

	}
	
   	
	/**
	 * displayLogin method is called to redirect to Member Login Jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward displayLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started...");
		logger.info(" successful  redirected to : displayLogin");
		logger.info(" Executed... ");
		return mapping.findForward(DISPLAY_LOGIN);
	}

	

}
