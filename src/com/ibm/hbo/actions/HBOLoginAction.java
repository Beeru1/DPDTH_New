package com.ibm.hbo.actions;

import java.net.InetAddress;

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

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.hbo.dto.HBOLogin;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.forms.HBOLoginFormBean;
import com.ibm.hbo.services.LoginService;
import com.ibm.hbo.services.UserService;
import com.ibm.hbo.services.impl.LoginServiceImpl;
import com.ibm.hbo.services.impl.UserServiceImpl;

/**
 * @version 	1.0
 * @author
 */
public class HBOLoginAction extends Action {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(HBOLoginAction.class.getName());
	/* Local Variables */

	private static String AUTHENTICATION_SUCCESS = "loginSuccess";
	private static String FORGOTPASSWORD = "forgotPassword";
	private static String ARCLOGIN_SUCCESS = "hboLoginSuccess";
	private static String AUTHENTICATION_FAILURE = "loginFailure";
	private static String FORGOTPASSWORD_SUCCESS = "forgotPwdSuccess";
	private static String HOME_SUCCESS = "home";
	private static String HBOHOME_SUCCESS = "hboHome";

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		HBOLoginFormBean loginformBean = (HBOLoginFormBean) form;
		HBOLogin login = new HBOLogin();
		try {
			
			//---------by sachin on 13th feb--------------------
			
			if ("/initForgotPassword".equalsIgnoreCase(mapping.getPath())) {
					forward = mapping.findForward(FORGOTPASSWORD);
			}
			if ("/forgotPassword".equalsIgnoreCase(mapping.getPath())) {
					UserService userService = new UserServiceImpl();
					HBOUserMstr userMstrDto = null;
					userMstrDto = userService.userLoginEmail(loginformBean.getForgotLoginId());
				
			if (null == userMstrDto) {
					loginformBean.setMessage("Invalid Login Id");
					forward = mapping.findForward(FORGOTPASSWORD);
			} else {
					//Mailing service called
					String changePwd =userService.pwdMailingService(loginformBean.getForgotLoginId(),userMstrDto.getUserEmailid());
					//Encryption Code for Password 
					IEncryption encrypt = new Encryption();
					userMstrDto.setNewPassword(encrypt.generateDigest(changePwd));
					userMstrDto.setUserLoginId(userMstrDto.getUserLoginId());
					//Change password service called
					int changeCount = userService.changePasswordService(userMstrDto);
					loginformBean.setMessage("Your password has mailed to your mail id");
					forward = mapping.findForward(FORGOTPASSWORD_SUCCESS);

				}
				
		}
		
		//-----end of changes------------
		
		
			if ("/home".equalsIgnoreCase(mapping.getPath())) {
				forward = mapping.findForward(HOME_SUCCESS);
			}
			if ("/login".equalsIgnoreCase(mapping.getPath())) {
			
			/* 
			 * GSD implementation to validate user name and password
			*/
				//Encryption Code for Password 
				IEncryption encrypt = new Encryption();
				
				login.setUserId(loginformBean.getUserId());
				login.setPassword(encrypt.generateDigest(loginformBean.getPassword()));
				GSDService gSDService = new GSDService();
				HBOUserMstr userInfo = null;
				// Added on 8 May,08 by parul For Security
				
				if (validateUserID(loginformBean.getUserId()) && validatePassword(loginformBean.getPassword()))
						userInfo =(HBOUserMstr) gSDService.validateCredentials(login.getUserId(),loginformBean.getPassword(),"com.ibm.hbo.dto.HBOUserMstr");
				else{				
				 logger.error("Special Characters are not allowed in User Id or Password!!!");
				// logger.info("Special Characters are not allowed in User Id or Password!!!" + loginformBean.getUserId());
				}
				//populating userBean
				if(userInfo != null) { 	
					LoginService loginService = new LoginServiceImpl();
					InetAddress thisIp = InetAddress.getLocalHost();
					//login.setUserIP(thisIp.getHostAddress());	// old 
					login.setUserIP(request.getRemoteAddr());
					HBOUserBean userBean = loginService.populateUserDetails(login);
					if(userBean == null) {
						// Already Logged-in
						loginformBean.setMessage("Already Logged-in");
						forward = mapping.findForward(AUTHENTICATION_FAILURE);
					} else {	
						HttpSession session = request.getSession(true);
						session.setAttribute("USER_INFO", userBean);
						
						forward = mapping.findForward(AUTHENTICATION_SUCCESS);
					}
				}else {
	
					forward = mapping.findForward(AUTHENTICATION_FAILURE);
				}
			}
		} catch (EncryptionException e) {
			logger.error("EncryptionException in Login by User ID: " + e.getMessage());
			saveErrors(request, errors);
			forward = mapping.findForward(AUTHENTICATION_FAILURE);
		} catch (ValidationException ve) {
			errors.add("errors.login.user_invalid", new ActionError(ve.getMessageId()));
			saveErrors(request, errors);
			forward = mapping.findForward(AUTHENTICATION_FAILURE);
		} catch (Exception ex) {
			errors.add("login.failure", new ActionError("LoginFailure"));
			logger.error("Exception in Login: " + ex.getMessage());
			forward = mapping.findForward(AUTHENTICATION_FAILURE);
		}
		saveToken(request);
		return (forward);
	}
	
	boolean validateUserID(String UserId){
		String valid;
		String temp;  
		valid =  "`~&'^#$%!?/\\|+*"; 
		for (int i=0;i< UserId.length();i++)
		{
			temp = "" + UserId.substring(i, i+1);
			if (valid.indexOf(temp) >= 0) 
				return false;				
		}
		return true;
	  }
		boolean validatePassword(String Password){
			String valid;
			String temp;  
			valid =  "$?*/\\<>^'"; 
			for (int i=0;i< Password.length();i++)
			{
				temp = "" + Password.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
					return false;			
			}
			return true;
		
	    }
	
}