package com.ibm.dp.webServices.local;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.virtualization.recharge.beans.UserBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.LoginServiceImpl;

public class AuthenticateUserId {

	public static Logger logger = Logger.getLogger(DistributorSTBMapping.class
			.getName());

	public String[] authenticate(String dpUser, String dpPass, String wsUser,
			String wsPass) {

		String[] serviceMsg = new String[2];
		serviceMsg[0]="SUCCESS";
		serviceMsg[1]="SUCCESS";
		IEncryption crypt = new Encryption();

		ResourceBundle webserviceResourceBundle = null;
		UserBean userBean = new UserBean();		
		AuthenticationService autheticationservice = new AuthenticationServiceImpl();

		AuthenticationService userService1 = new AuthenticationServiceImpl();
		long accountId=-1;
		try {
			accountId = userService1.getAccountId(dpUser);
		} catch (VirtualizationServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.info("Error in fetching Account ID");
			serviceMsg[0]="ERROR";
			serviceMsg[1]="Error in Web service.Account not found";
		}
		try {
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}

			if (!wsUser.equals(webserviceResourceBundle
					.getString("dpwebservice.distStbMap.userid"))) {
				logger.info("**********INVALID USER NAME**********");
				serviceMsg[0] = "ERROR";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_USER_NAME;
				logger
						.info("********** Authenticate User WebService msg returned == "
								+ serviceMsg[0] + "  ::  " + serviceMsg[1]);
				return serviceMsg;
			} else if (!wsPass.equals(webserviceResourceBundle
					.getString("dpwebservice.distStbMap.password"))) {
				logger.info("**********INVALID PASSWORD**********");
				serviceMsg[0] = "ERROR";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_PASSWORD;
				logger
						.info("********** Authenticate User WebService msg returned == "
								+ serviceMsg[0] + "  ::  " + serviceMsg[1]);
				return serviceMsg;
			}

			else {

				try {
					GSDService gsdService = new GSDService();
				
					userBean = (UserBean) gsdService
							.validateCredentials(dpUser, dpPass,
									Constants.SRC_USER_BEAN);
					logger.info(" Validated");
				} catch (ValidationException exp) {
					exp.printStackTrace();

					logger.error("Error Authenticating User  " + dpUser
							+ " Error Message  " + exp.getMessage());

					if (exp.getMessageId().equals("msg.security.id02")) {
						

						String isNewUser = autheticationservice
								.isNewUser(accountId);

						if (isNewUser != null
								&& isNewUser.equalsIgnoreCase("true")) {
							
							logger.info("Password must be changed for the first login from DP");
							serviceMsg[0]="ERROR";
							serviceMsg[1]="Password Expired.Please login from DP and change the password";
							return serviceMsg;
						}
						else{
							
										
							userBean.setLoginId(String.valueOf(accountId));
							userBean.setType("E");
							}
					}
					else{
						if(accountId==-1){
							String status= userService1.getStatus(dpUser);
							if(status.equalsIgnoreCase("I")){
								String application_user = ResourceReader.getCoreResourceBundleValue("user.suspended");
								logger.info("User ID Suspended");
								serviceMsg[0]="ERROR";
								serviceMsg[1]="User ID suspended. Please contact Administrator";
								return serviceMsg;
							}else{
								//String application_user = ResourceReader.getWebResourceBundleValue("user.not.exist");
								
								logger.info("User does not exists");
								serviceMsg[0]="ERROR";
								serviceMsg[1]="User does not Exists";
								return serviceMsg;
							}
						}
						else{
								logger.info("User name login failed");
								serviceMsg[0]="ERROR";
								serviceMsg[1]="User name login failed";
								return serviceMsg;
							}

					}
				}
				Login login=new Login();
				login.setLoginName(dpPass);
				
				String retFlag = autheticationservice.validatePasswordExpiry(login);
				if(retFlag != null && retFlag.equalsIgnoreCase("false"))
				{
					logger.info("Password Expired");
					serviceMsg[0]="ERROR";
					serviceMsg[1]="Password Expired. Please Login to Distributor Portal and change the same.";
					return serviceMsg;
				}
				
				if (userBean.getType().equalsIgnoreCase(
						Constants.USER_TYPE_EXTERNAL)) {
					 DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
					 String openingDate = accountService.getAccoutOpeningDate(Long
							.parseLong(userBean.getLoginId()));
					 if (openingDate != null) {
						logger.info("Error Account Opening Date");
						serviceMsg[0]="ERROR";
						serviceMsg[1]="Error Account Opening Date greater then today's date";
						return serviceMsg;
					}
					
				}
				
				DPCreateAccountService accService = new DPCreateAccountServiceImpl();
				LoginService loginService =new LoginServiceImpl();
				DPCreateAccountDto account = accService.getAccount(accountId);
				
				
				if(account !=null && account.getGroupId()!=7){
					logger.info("Error--not a distributor");
					serviceMsg[0]="ERROR";
					serviceMsg[1]="Account does not corresponds to a distributor.Please login with Distributor's Account";
					return serviceMsg;
				}
				
				
				if(loginService.isUserLocked(accountId)){
					logger.info("Error--account locked");
					serviceMsg[0]="ERROR";
					serviceMsg[1]="Account locked. Please contact DP administrator.";
					return serviceMsg;
					
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serviceMsg;
	}

}
