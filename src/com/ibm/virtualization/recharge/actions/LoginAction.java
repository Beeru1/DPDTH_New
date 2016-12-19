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
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.beans.DistRecoBean;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.beans.AuthenticationFormBean;
import com.ibm.virtualization.recharge.beans.UserBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.CacheDao;
import com.ibm.virtualization.recharge.dao.rdbms.CacheDaoRdbms;
import com.ibm.virtualization.recharge.dto.Link;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.service.UserService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.LoginServiceImpl;
import com.ibm.virtualization.recharge.service.impl.UserServiceImpl;
import com.ibm.virtualization.recharge.dto.AuthenticationKeyInCache;

/**
 * Controller class for Login with Account Code
 * 
 * @author Paras
 */
public class LoginAction extends Action {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(LoginAction.class.getName());

	/* Local Variables */
	private static String AUTHENTICATION_SUCCESS = "loginSuccess";
	
	private static String DISTRIBUTOR_LOGIN = "distributorlogin";

	private static String AUTHENTICATION_FAILURE = "loginFailure";
	
	private static String REDIRECT_TO_RECO = "redirectToReco";
	

	/* Top link lebels from DB VR_LINK_MASTER.TOP_LINK_NAME */
	private static String SYSTEM_CONFIGURATION = "SYSTEM_CONFIGURATION";

	private static String ACCOUNT_MANAGEMENT = "ACCOUNT_MANAGEMENT";

	private static String MONEY_TRANSACTION = "MONEY_TRANSACTION";

	private static String SYSTEM_ADMIN = "SYSTEM_ADMINISTRATION";

	private static String USSD_ADMIN = "USSD_ACTIVATION";
	
	//Added by Nazim Hussain for Reverse Stock Flow
	private static String REVERSE_FLOW = "REVERSE_FLOW";

	private static String QUERIES = "QUERIES";

	private static String REPORTS = "REPORTS";
	
	private static String FORWARD_REPORTS = "FORWARD_REPORTS";
	private static String REVERSE_REPORTS = "REVERSE_REPORTS";
	private static String CONSUMPTION_REPORTS = "CONSUMPTION_REPORTS";
	private static String STOCK_REPORTS = "STOCK_REPORTS";
	private static String RECO_REPORTS = "RECO_REPORTS";	

	private static String PASSWORD_EXPIRED = "PasswordExpired";
	
	private static String FORGOTPASSWORD = "forgotPassword";
	
	private static String FORGOTPASSWORD_SUCCESS = "forgotPwdSuccess";
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
		AuthenticationFormBean authenticationFormBean = (AuthenticationFormBean) form;
		DPCreateAccountDto account = new DPCreateAccountDto();
		AuthenticationService autheticationservice = new AuthenticationServiceImpl();
		Login login = new Login();
		logger.info(" Login tried with :"
				+ authenticationFormBean.getLoginName()  +" from IP Address "+request.getRemoteAddr());
		
		String distName=request.getParameter("distName"); // added by rohit for proxy login
		String distID=request.getParameter("distID"); // added by rohit for proxy login
		int distFlag=0,finFlag=0;
		
		//*********** adding by pratap*************** /
		try
		{
		ArrayList<AuthenticationKeyInCache> groupUrlMapping=new ArrayList<AuthenticationKeyInCache>();
		ArrayList total_urls=new ArrayList();
		
		CacheDao cdao=new CacheDaoRdbms();
		groupUrlMapping = cdao.getGroupAndUrlInfo();
		total_urls = cdao.getTotalUrls();
		if(groupUrlMapping==null)logger.info("GroupUrlMapping is null");
		HttpSession session = request.getSession();
		session.setAttribute("groupurlmapping", groupUrlMapping);
		session.setAttribute("totalurls", total_urls);
		
		Iterator itr1=groupUrlMapping.iterator();
		
		/*while(itr1.hasNext())
		{
			AuthenticationKeyInCache akc= (AuthenticationKeyInCache)itr1.next();
			logger.info(" in login action  group id  :"+akc.getGroupId());
			logger.info(" in login action  URL :"+akc.getUrl());
		}*/
		}
		catch(Exception ex)
		{
			logger.error("Exception while getting group id and url  :");
					ex.printStackTrace();
		}
		
		//*************** end of adding by pratap********/
			
		/**
		 * getting header set by SSF 
		 */
			//String accountCodeSSF= "123456789";		
			
			String accountCodeSSF= null;//= (String)request.getHeader(ResourceReader.getCoreResourceBundleValue(Constants.SSF_ID));//parameter name present in "RechargeCore.properties"
			
			//	logger.info("SSF Header recieved accountCodeSSF "+accountCodeSSF+"ResourceReader.."+ResourceReader.getCoreResourceBundleValue(Constants.SSF_ID)+"....");
			if ("/initForgotPassword".equalsIgnoreCase(mapping.getPath())) {
					return mapping.findForward(FORGOTPASSWORD);
			}
			else if ("/forgotPassword".equalsIgnoreCase(mapping.getPath())) {
			AuthenticationService userService = new AuthenticationServiceImpl();
				HBOUserMstr userMstrDto = new HBOUserMstr();
				
				login.setLoginName(authenticationFormBean.getLoginName());
				long accountId= userService.getAccountId(login.getLoginName());

				if (-1== accountId) {
					authenticationFormBean.setMessage("Invalid Login Id");
					return mapping.findForward(FORGOTPASSWORD);
				} 
				else if (-2== accountId) {
					authenticationFormBean.setMessage("Your account has been suspended in DP. Please contact your administrator");
					return mapping.findForward(FORGOTPASSWORD);
				}else {
									
					String emailId= "";
					String mobileNo= "";
					if(accountId != 0){
						emailId = userService.getEmailId(accountId);
						//mobileNo = userService.getMobileNo(accountId,Constants.CONFIRM_ID_CHANGE_PASSWORD);
						
						mobileNo = userService.getMobileNoForSms(accountId,Constants.CONFIRM_ID_CHANGE_PASSWORD);
					}
					
					
					//Mailing service called
					String changePwd = "";
					try
					{
						logger.info("Mobile no in loginaction  :"+mobileNo);
						if(mobileNo!=null)
						changePwd =	userService.pwdMailingService(authenticationFormBean.getLoginName(),emailId,mobileNo);
					}
					catch(Exception ex)
					{
						authenticationFormBean.setMessage("There is some problem while resetting the password.Please try after some time");
						return mapping.findForward(FORGOTPASSWORD);
					}
//					 Added unlock functionality to forgot password as well
					LoginService loginService =new LoginServiceImpl();
					if(loginService.isUserLocked(accountId))
					{
						loginService.unlockUser(accountId);
					}
					
					//userMstrDto.getUserEmailid());
					
					//Encryption Code for Password 
					IEncryption encrypt = new Encryption();
					//userMstrDto.setNewPassword(encrypt.generateDigest(changePwd));
					//userMstrDto.setUserId(login.getLoginName());
					//ChangePasswordFormBean changePasswordBean = new ChangePasswordFormBean();
					//changePasswordBean.setNewPassword(encrypt.generateDigest(changePwd));
					//Change password service called
					String newDigest = encrypt.generateDigest(changePwd);
					userService.changePassword(accountId,newDigest);
					authenticationFormBean.setMessage("Your password has been mailed to your mail id");
				}
				return mapping.findForward(FORGOTPASSWORD_SUCCESS);
			}
			
			
		else if(accountCodeSSF ==null && distName == null ){ //if headers are null	// added by rohit for proxy login
		if(authenticationFormBean != null){
			
		try {
			
			login.setLoginName(authenticationFormBean.getLoginName());//need to set header usr 
			login.setPassword(authenticationFormBean.getPassword());//need to set header pass
			logger.info(" Authenticating Login :"
					+ authenticationFormBean.getLoginName());
			autheticationservice.validateUserPassword(login);
			logger.info(" Authenticated");

			/* Authenticate login name & password */
			String loginName = authenticationFormBean.getLoginName();
			
						
			
//			Addded by Priya
			AuthenticationService userService1 = new AuthenticationServiceImpl();
			login.setLoginName(authenticationFormBean.getLoginName());
			long accountId= userService1.getAccountId(login.getLoginName());	
			
			/* GSD implementation to validate user name and password */
			logger.info(" Called GSD Service");		
			UserBean userBean =null;
			
			try{
				GSDService gsdService = new GSDService();
				//System.out.println("--------------------start------------------------"+authenticationFormBean.getLoginName()+"-------------------");
				
				
				userBean = (UserBean) gsdService.validateCredentials(
						authenticationFormBean.getLoginName(),
						authenticationFormBean.getPassword(),
						Constants.SRC_USER_BEAN);
				//System.out.println("************************************************************************");
				logger.info(" Validated");
				} catch (ValidationException exp) {
					exp.printStackTrace();
					logger.error("Error Authenticating User  "
							+ authenticationFormBean.getLoginName()
							+ " Error Message  " + exp.getMessage());
				
					ActionErrors errors = new ActionErrors();
					
					if (exp.getMessageId().equals("msg.security.id02")) {
						
						AuthenticationService userService = new AuthenticationServiceImpl();
						//long accountId= userService.getAccountId(login.getLoginName());
						
						String isNewUser = autheticationservice.isNewUser(accountId);
						
						if(isNewUser != null && isNewUser.equalsIgnoreCase("true"))
						{
							
							logger.error("Error Authenticating User  "
									+ authenticationFormBean.getLoginName()
									);
							errors = new ActionErrors();
												request.getSession().setAttribute(
										"message",
										ResourceReader.getValueFromBundle("msg.security.id02",
												"ApplicationResources"));
								logger.info("Redirecting to change password.."
										+ mapping.findForward(PASSWORD_EXPIRED));
								return mapping.findForward(PASSWORD_EXPIRED);
						}
						else{
							
						userBean = new UserBean();					
						userBean.setLoginId(String.valueOf(accountId));
						userBean.setType("E");
						}
						
						// errors.add("validationerror", new
						// ActionError("msg.security.id02"));
						// saveErrors(request, errors);

						// TODO Error message to be printed using request scope,here
						// done using session scope.
						/*request.getSession().setAttribute(
								"message",
								ResourceReader.getValueFromBundle("msg.security.id02",
										"ApplicationResources"));
						logger.info("Redirecting to change password.."
								+ mapping.findForward(PASSWORD_EXPIRED));
						return mapping.findForward(PASSWORD_EXPIRED); commented by Priya */
					} else{
						if(accountId==-1){
							String status= userService1.getStatus(login.getLoginName());
							if(status.equalsIgnoreCase("I")){
								String application_user = ResourceReader.getCoreResourceBundleValue("user.suspended");
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(exp
										.getMessageId(), application_user));
								saveErrors(request, errors);
							
								return mapping.findForward(AUTHENTICATION_FAILURE);
							}else{
							String application_user = ResourceReader.getWebResourceBundleValue("user.not.exist");
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(exp
									.getMessageId(), application_user));
							saveErrors(request, errors);
						
							return mapping.findForward(AUTHENTICATION_FAILURE);
							}
						}
						else{
						String application_user = ResourceReader.getWebResourceBundleValue("user.username");
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(exp
								.getMessageId(), application_user));
						saveErrors(request, errors);
					
						return mapping.findForward(AUTHENTICATION_FAILURE);
						}
					}
				}
			
//				Commented by Priya 
				String retFlag = autheticationservice.validatePasswordExpiry(login);
				logger.info(" retFlag from validatePasswordExpiry::"+retFlag);
				if(retFlag != null && retFlag.equalsIgnoreCase("false"))
				{
					logger.error("Error Authenticating User  "
							+ authenticationFormBean.getLoginName()
							);
					ActionErrors errors = new ActionErrors();
										request.getSession().setAttribute(
								"message",
								ResourceReader.getValueFromBundle("msg.security.id02",
										"ApplicationResources"));
						logger.info("Redirecting to change password.."
								+ mapping.findForward(PASSWORD_EXPIRED));
						return mapping.findForward(PASSWORD_EXPIRED);
				}	
			
			
			
			LoginService loginService = new LoginServiceImpl();
			int pswdExpireDays = loginService.checkPswdExpiring(String.valueOf(accountId));
			authenticationFormBean.setResult(String.valueOf(pswdExpireDays));
			request.setAttribute("pswdExpireDays", pswdExpireDays);
			
			
			
			
			
			/*
			 * Check if the Account Opening Date is less than equals to today
			 */
			if (userBean.getType().equalsIgnoreCase(
					Constants.USER_TYPE_EXTERNAL)) {
				 DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
				
			}
			if (userBean == null) {
				logger.info(" Could populate userbean for Login : "	+ loginName);
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ExceptionCode.Authentication.ERROR_APPLICATION_SEVERE));
				saveErrors(request, errors);
				return mapping.findForward(AUTHENTICATION_FAILURE);
			}

			/** ******************duplicate session validation***************** */
			ServletContext sc = request.getSession().getServletContext();
		
			HttpSession session = (HttpSession) sc.getAttribute(authenticationFormBean.getLoginName());
			
		//	session.setAttribute("loginType","FormBean");
			if (session != null) {
				logger
						.info("duplicate session.Invalidating old session and creating a new session");
				sc.removeAttribute(authenticationFormBean.getLoginName());

				try {
					session.invalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// creating new session and saving it in servlet context

			} else {

				logger.info("new request and creating a fresh session");

			} 
			session = request.getSession(true);
			sc.setAttribute(authenticationFormBean.getLoginName(), session);
			session.setAttribute("pswdExpireDays", pswdExpireDays);//added by Priya
			/** **************************************************************** */
			UserSessionContext userSessionContext = null;
			if ((userBean != null) && (userBean.getLoginId() != null)) {
				session.setAttribute("distOLMID", authenticationFormBean.getLoginName());
				logger.info(userBean.getLoginId() + " successfully Logged in.");
				userSessionContext = new UserSessionContext();
				userSessionContext.setAuthType("Formbean");//setting parameter for checking login process.
				userSessionContext.setLoginName(loginName);
				userSessionContext.setId(Long.parseLong(userBean.getLoginId()));
				userSessionContext.setType(userBean.getType());
				
				// AuthenticationService autheticationservice = new
				// AuthenticationServiceImpl();
				if (userSessionContext.getType().equalsIgnoreCase(
						Constants.USER_TYPE_EXTERNAL)) {
					DPCreateAccountService accService = new DPCreateAccountServiceImpl();
					/* Get Account Details for login Id */
					account = accService.getAccount(userSessionContext.getId());
					userSessionContext.setLoginName(account.getAccountCode());
					userSessionContext.setGroupId(account.getGroupId());
					session.setAttribute("groupid", account.getGroupId());
					userSessionContext.setCircleId(account.getCircleId());
					userSessionContext.setHeirarchyId(account.getHeirarchyId());
					//userSessionContext.setBillableAccId(account
						//	.getBillableCodeId());
					userSessionContext.setAccountAddress(account
							.getAccountAddress());
					userSessionContext.setCircleCode(account.getCircleCode());
					userSessionContext.setAccountCityId(account.getAccountCityId());
					userSessionContext.setAccountCityName(account.getAccountCityName());
					userSessionContext.setLapuMobileNo(account.getLapuMobileNo());
					userSessionContext.setTradeId(account.getTradeId());
					userSessionContext.setTradeCategoryId(account.getTradeCategoryId());
					userSessionContext.setAccountLevel(account.getAccountLevel());
					userSessionContext.setAccesslevel(account.getAccountLevelId());
					userSessionContext.setAccountLevelName(account.getAccountLevelName());
					userSessionContext.setCircleName(account.getCircleName());
					userSessionContext.setAuthType("Formbean");//setting parameter for checking login process. // added by rohit for proxy login
					// check logged in user have circle is active or not
					autheticationservice.isCircleActive(account.getCircleId());
					userSessionContext.setAccountCityId(account.getAccountCityId());
					userSessionContext.setAccountZoneId(account.getAccountZoneId());
					userSessionContext.setAccountZoneName(account.getAccountZoneName());
					userSessionContext.setContactName(account.getContactName());
					userSessionContext.setAccountName(account.getAccountName());
					userSessionContext.setOutletType(account.getOutletType());
					
					if(account.getIsDisable() != null && account.getIsDisable().equalsIgnoreCase("Y"))
					{
						//authenticationFormBean.setMessage("User is Locked");
						ActionErrors errors = new ActionErrors();
						errors
								.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError("errors.login.is_disabled_yes"));
						saveErrors(request, errors);
						return mapping.findForward(AUTHENTICATION_FAILURE);
					}
					
					
				} else {
					UserService userService = new UserServiceImpl();
					User user = new User();
					/* Get User Details for login Id */
					user = userService.getUser(userSessionContext.getId());
					userSessionContext.setLoginName(user.getLoginName());
					userSessionContext.setGroupId(user.getGroupId());
					userSessionContext.setCircleId(user.getCircleId());
					userSessionContext.setAuthType("Formbean");//setting parameter for checking login process.
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
				
				if(groupId==7){
					DPCreateAccountService accService = new DPCreateAccountServiceImpl();
					int cutOffDate=accService.getCutOffDateDist(userSessionContext.getLoginName());
					logger.info(cutOffDate);
					if(cutOffDate!=0){
						
						Calendar cal=Calendar.getInstance();
						int daytoday=cal.get(Calendar.DAY_OF_MONTH);
						logger.info(daytoday);
						if(daytoday<=cutOffDate){
							if((cutOffDate-daytoday) <=3){
								logger.info(cutOffDate);
								request.setAttribute("PayoutCutoffDist",cutOffDate-daytoday);
							}
						}
						if(cutOffDate==-1){
							logger.info("Block User-id");
							request.setAttribute("disableLinkPay", "true");
							distFlag=1;
						}
					}
					
					
				}
				
				if(groupId==15){
					DPCreateAccountService accService = new DPCreateAccountServiceImpl();
					int cutoffDate=accService.getCutoffDate();
					logger.info(cutoffDate);
					if(cutoffDate!=0){
						
						Calendar cal=Calendar.getInstance();
						int daytoday=cal.get(Calendar.DAY_OF_MONTH);
						logger.info(daytoday);
						if(daytoday<=cutoffDate){
							if((cutoffDate-daytoday) <=3){
								logger.info(cutoffDate);
								request.setAttribute("PayoutCutoff",cutoffDate-daytoday);
							}
						}
						if(cutoffDate==-1){
							
							logger.info("Block User-id");
							request.setAttribute("disableLinkPay", "true");
							
							finFlag=1;
						}
					}
				}
				// Getting roles for group
				logger.info(" **********Get User Credentials " + loginName);
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				logger.info(" groupId  :  "+groupId);
				roleList = authorizationInterface.getUserCredentials(groupId, ChannelType.WEB);
				logger.info(" Load Role Link Map :: "+roleList);
				if(roleList!=null)
				logger.info(" Load Role Link Map size  :: "+roleList.size());
				// Loading All Role - link mappings.
				// TODO Ashish C, Sep 20:Ideally roleLinkMap should be loaded
				// at application level.
				roleLinkMap = AuthorizationFactory.getAuthorizerImpl()
						.loadRoleLinkMap();
			//	logger.info(" Loaded  roleLinkMap  :  "+roleLinkMap);
				if(roleList!=null)
				{
					
				}
				//	logger.info(" Load roleLinkMap size  :: "+roleLinkMap.size());
				
				ArrayList<Link> arrSysConfigLinks = new ArrayList<Link>();
				ArrayList<Link> arrAcctMgmtLinks = new ArrayList<Link>();
				ArrayList<Link> arrMoneyTranLinks = new ArrayList<Link>();
				ArrayList<Link> arrSysAdminLinks = new ArrayList<Link>();
				//Added by Nazim Hussain fro Reverse logistics
				ArrayList<Link> arrReverseFlowLinks = new ArrayList<Link>();
				
				ArrayList<Link> arrUssdActvLinks = new ArrayList<Link>();
				ArrayList<Link> arrBalEnqLinks = new ArrayList<Link>();
				ArrayList<Link> arrReportLinks = new ArrayList<Link>();
				ArrayList<Link> arrForwardReportLinks = new ArrayList<Link>();
				ArrayList<Link> arrReverseReportLinks = new ArrayList<Link>();
				ArrayList<Link> arrStockReportLinks = new ArrayList<Link>();
				ArrayList<Link> arrRecoReportLinks = new ArrayList<Link>();
				ArrayList<Link> arrConsumptionReportLinks = new ArrayList<Link>();
				ArrayList<String> addedLinks = new ArrayList<String>();
				
				String roleName = "";
				String topLinkName = "";
				Link link = null;
				ArrayList linkArray = null;
				
//				if(roleList != null && roleList.size() > 0) {
//					for(int i = 0; i < roleList.size();i++) {
//					}
//				}
				

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
										.equalsIgnoreCase(REVERSE_FLOW)) {
									arrReverseFlowLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(USSD_ADMIN)) {
									arrUssdActvLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(QUERIES)) {
									arrBalEnqLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(REPORTS)) {
									arrReportLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(FORWARD_REPORTS)) {
									arrForwardReportLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(REVERSE_REPORTS)) {
									arrReverseReportLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(RECO_REPORTS)) {
									arrRecoReportLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(CONSUMPTION_REPORTS)) {
									arrConsumptionReportLinks.add(link);
								} else if (topLinkName
										.equalsIgnoreCase(STOCK_REPORTS)) {
									arrStockReportLinks.add(link);
								}
								
								addedLinks.add(link.getLinkId());

							}
						}
					}
				}
				logger.info(" Loaded 2nd ");
				Collections.sort(arrAcctMgmtLinks); 
				Collections.sort(arrReverseFlowLinks); 
				Collections.sort(arrReportLinks); 
				Collections.sort(arrMoneyTranLinks); 
				Collections.sort(arrSysAdminLinks); 
				Collections.sort(arrUssdActvLinks); 
				Collections.sort(arrBalEnqLinks); 
				Collections.sort(arrSysConfigLinks); 
				Collections.sort(arrForwardReportLinks); 
				Collections.sort(arrReverseReportLinks); 
				Collections.sort(arrRecoReportLinks); 
				Collections.sort(arrStockReportLinks); 
				Collections.sort(arrConsumptionReportLinks); 
				
				
				authenticationFormBean.setArrAcctMgmtLinks(arrAcctMgmtLinks);
				authenticationFormBean.setArrMoneyTranLinks(arrMoneyTranLinks);
				authenticationFormBean.setArrSysAdminLinks(arrSysAdminLinks);
				authenticationFormBean.setArrReverseFlowLinks(arrReverseFlowLinks);
				authenticationFormBean.setArrSysConfigLinks(arrSysConfigLinks);
				authenticationFormBean.setArrUssdActvLinks(arrUssdActvLinks);
				authenticationFormBean.setArrBalEnqLinks(arrBalEnqLinks);
				authenticationFormBean.setArrReportLinks(arrReportLinks);
				authenticationFormBean.setArrForwardReportLinks(arrForwardReportLinks);
				authenticationFormBean.setArrReverseReportLinks(arrReverseReportLinks);
				authenticationFormBean.setArrRecoReportLinks(arrRecoReportLinks);
				authenticationFormBean.setArrStockReportLinks(arrStockReportLinks);
				authenticationFormBean.setArrConsumptionReportLinks(arrConsumptionReportLinks);
				
				session.setAttribute("AuthenticationFormBean",authenticationFormBean);
				
				if (roleList.contains("ROLE_AD"))
				{
					UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
					int forceToRecoPage=0;
					if(sessionContext.getAccountLevel().equalsIgnoreCase((Constants.DIST_ACCOUNT_LEVEL)))
					{
						forceToRecoPage= AuthorizationFactory.getAuthorizerImpl()
						.forceToRecoPage(sessionContext.getId());
						if(forceToRecoPage!=0)
						{
							session.setAttribute("disabledLink", Constants.disableLink);
							return mapping.findForward(REDIRECT_TO_RECO);
						}
						
					}
					if(distFlag==1){
						
						return mapping.findForward("redirectToInvoices");
					}else if(finFlag==1){
						return mapping.findForward("redirectToFinPayout");
					}
					
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
		} catch (VirtualizationServiceException vse)
		// TODO paras 15 oct 2007 change messages accordingly
		{
			vse.printStackTrace();
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
			ex.printStackTrace();
			logger.error("Error Database Connection .........   "
					+ ex.getMessage());

			ActionErrors errors = new ActionErrors();

			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.db.create_connection"));

			saveErrors(request, errors);
			return mapping.findForward(AUTHENTICATION_FAILURE);
			}
		
		
		
		}
	}
	/**
	 * * SSF ENTER 
	 */
	if(accountCodeSSF !=null)// Header recieve
	{			
		try {
			DPCreateAccountService accService = new DPCreateAccountServiceImpl();
			AuthenticationFormBean authFormBean = new AuthenticationFormBean();
		
			account	= accService.getDPAccountID(Integer.parseInt(accountCodeSSF));
				
			/** **************************************************************** */
			HttpSession session = request.getSession(true);
			UserSessionContext userSessionContext = new UserSessionContext();
			userSessionContext.setId(account.getAccountId());
			userSessionContext.setType(account.getUserType());
			userSessionContext.setLoginName(account.getLoginName());
			account = accService.getAccount(account.getAccountId());
			userSessionContext.setAuthType("SSF");//setting parameter for checking login process.
			logger.info("logged in using SSF");
			userSessionContext.setGroupId(account.getGroupId());
			userSessionContext.setCircleId(account.getCircleId());
			userSessionContext.setHeirarchyId(account.getHeirarchyId());
			userSessionContext.setAccountAddress(account
					.getAccountAddress());
			userSessionContext.setCircleCode(account.getCircleCode());
			userSessionContext.setLapuMobileNo(account.getLapuMobileNo());
			userSessionContext.setTradeId(account.getTradeId());
			userSessionContext.setTradeCategoryId(account.getTradeCategoryId());
			userSessionContext.setAccountLevel(account.getAccountLevel());
			userSessionContext.setAccesslevel(account.getAccountLevelId());
			// check logged in user have circle is active or not
			autheticationservice.isCircleActive(account.getCircleId());
	
				
			if (userSessionContext != null) {

				/* set authenticated user details into session */
				session.setAttribute(Constants.AUTHENTICATED_USER,
						userSessionContext);

				/*---Get user group roles to determine navigation access---*/
				ArrayList roleList = new ArrayList();
				LinkedHashMap roleLinkMap = new LinkedHashMap();
				long groupId = userSessionContext.getGroupId();

				// Getting roles for group
			 AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
			 logger.info(" groupId ::  "+groupId);
				roleList = authorizationInterface.getUserCredentials(groupId, ChannelType.WEB);
				logger.info(" Load Role Link Map ::  "+roleList);
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
				//Added by Nazim Hussain fro Reverse logistics
				ArrayList<Link> arrReverseFlowLinks = new ArrayList<Link>();
				
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
										.equalsIgnoreCase(REVERSE_FLOW)) {
									arrReverseFlowLinks.add(link);
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
				
				Collections.sort(arrAcctMgmtLinks); 
				Collections.sort(arrReverseFlowLinks); 
				Collections.sort(arrReportLinks); 
				Collections.sort(arrMoneyTranLinks); 
				Collections.sort(arrSysAdminLinks); 
				Collections.sort(arrUssdActvLinks); 
				Collections.sort(arrBalEnqLinks); 
				Collections.sort(arrSysConfigLinks); 
				
				authFormBean.setArrAcctMgmtLinks(arrAcctMgmtLinks);
				authFormBean.setArrMoneyTranLinks(arrMoneyTranLinks);
				authFormBean.setArrSysAdminLinks(arrSysAdminLinks);
				authFormBean.setArrReverseFlowLinks(arrReverseFlowLinks);
				authFormBean.setArrSysConfigLinks(arrSysConfigLinks);
				authFormBean.setArrUssdActvLinks(arrUssdActvLinks);
				authFormBean.setArrBalEnqLinks(arrBalEnqLinks);
				authFormBean.setArrReportLinks(arrReportLinks);
				session.setAttribute("AuthenticationFormBean",authFormBean);
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				
				if (roleList.contains("ROLE_AD"))
				{
					int forceToRecoPage=0;
					if(sessionContext.getAccountLevel().equalsIgnoreCase((Constants.DIST_ACCOUNT_LEVEL)))
					{
						forceToRecoPage= AuthorizationFactory.getAuthorizerImpl()
						.forceToRecoPage(sessionContext.getId());
						if(forceToRecoPage!=0)
						{
							session.setAttribute("disabledLink", Constants.disableLink);
							return mapping.findForward(REDIRECT_TO_RECO);
						}
						if(distFlag==1){
							
							return mapping.findForward(DISTRIBUTOR_LOGIN);
						} 
						
						
					}
					return mapping.findForward(DISTRIBUTOR_LOGIN);
				}
				
				/*---END:Get user group roles to determine navigation access---*/
			} else {
				logger.error("Error Getting User Details for  "
						+ authFormBean.getLoginName());
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.login.user_invalid"));
				saveErrors(request, errors);
				return mapping.findForward(AUTHENTICATION_FAILURE);
			}
	
		} catch (VirtualizationServiceException vse)
		// TODO paras 15 oct 2007 change messages accordingly
		{
			logger.error("Error Authenticating User  "
					+ authenticationFormBean.getLoginName()
					+ " Error Message  " + vse.getMessage());
			vse.printStackTrace();
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
			ex.printStackTrace();
			logger.error("Error Database Connection .........   "
					+ ex.getMessage());
			ex.getMessage();
			ex.printStackTrace();
			ActionErrors errors = new ActionErrors();

			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.db.create_connection"));

			saveErrors(request, errors);
			return mapping.findForward(AUTHENTICATION_FAILURE);
		}
	
		
	}
	/**
	 * PROXY LOGIN ENTER  // added by rohit for proxy login
	 */
	if((distName!=null)&&(distID!=null)){
	
		logger.info("distName::"+distName+", distID ::"+distID);
		
		try {
			DPCreateAccountService accService = new DPCreateAccountServiceImpl();
			AuthenticationFormBean authFormBean = new AuthenticationFormBean();
		
			/* Get Account Details for login Id */
			
			account = accService.getAccount(Long.valueOf(distID));
											
			/** **************************************************************** */
			HttpSession session = request.getSession(true);
			UserSessionContext userSessionContext = new UserSessionContext();
			userSessionContext.setId(account.getAccountId());
			userSessionContext.setType(account.getUserType());
			
//			if(null != userSessionContext.getLoginName()) { //by sanjay verma
//				userSessionContext.setProxyName(userSessionContext.getLoginName());
//			}
			userSessionContext.setLoginName(distName);
			
			account = accService.getAccount(account.getAccountId());
			userSessionContext.setAuthType("PROXYLOGIN");//At time of integration set AuthType = "SSF" remove PROXYLOGIN .
			
			userSessionContext.setGroupId(account.getGroupId());
			userSessionContext.setCircleId(account.getCircleId());
			userSessionContext.setHeirarchyId(account.getHeirarchyId());
			userSessionContext.setAccountAddress(account
					.getAccountAddress());
			userSessionContext.setCircleCode(account.getCircleCode());
			userSessionContext.setLapuMobileNo(account.getLapuMobileNo());
			userSessionContext.setTradeId(account.getTradeId());
			userSessionContext.setTradeCategoryId(account.getTradeCategoryId());
			userSessionContext.setAccountLevel(account.getAccountLevel());
			userSessionContext.setAccesslevel(account.getAccountLevelId());
			// check logged in user have circle is active or not
			autheticationservice.isCircleActive(account.getCircleId());
				
			if (userSessionContext != null) {

				/* set authenticated user details into session */
				session.setAttribute(Constants.AUTHENTICATED_USER,
						userSessionContext);

				/*---Get user group roles to determine navigation access---*/
				ArrayList roleList = new ArrayList();
				LinkedHashMap roleLinkMap = new LinkedHashMap();
				long groupId = userSessionContext.getGroupId();

				// Getting roles for group
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
				//Added by Nazim Hussain fro Reverse logistics
				ArrayList<Link> arrReverseFlowLinks = new ArrayList<Link>();
				
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
										.equalsIgnoreCase(SYSTEM_ADMIN)) {
									arrReverseFlowLinks.add(link);
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
				
				Collections.sort(arrAcctMgmtLinks); 
				Collections.sort(arrReverseFlowLinks); 
				Collections.sort(arrReportLinks); 
				Collections.sort(arrMoneyTranLinks); 
				Collections.sort(arrSysAdminLinks); 
				Collections.sort(arrUssdActvLinks); 
				Collections.sort(arrBalEnqLinks); 
				Collections.sort(arrSysConfigLinks); 
				
				authFormBean.setArrAcctMgmtLinks(arrAcctMgmtLinks);
				authFormBean.setArrMoneyTranLinks(arrMoneyTranLinks);
				authFormBean.setArrSysAdminLinks(arrSysAdminLinks);
				authFormBean.setArrReverseFlowLinks(arrReverseFlowLinks);
				authFormBean.setArrSysConfigLinks(arrSysConfigLinks);
				authFormBean.setArrUssdActvLinks(arrUssdActvLinks);
				authFormBean.setArrBalEnqLinks(arrBalEnqLinks);
				authFormBean.setArrReportLinks(arrReportLinks);
				session.setAttribute("AuthenticationFormBean",authFormBean);
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				
				if (roleList.contains("ROLE_AD"))
				{
					int forceToRecoPage=0;
					if(sessionContext.getAccountLevel().equalsIgnoreCase((Constants.DIST_ACCOUNT_LEVEL)))
					{
						forceToRecoPage= AuthorizationFactory.getAuthorizerImpl()
						.forceToRecoPage(sessionContext.getId());
						if(forceToRecoPage!=0)
						{
							session.setAttribute("disabledLink", Constants.disableLink);
							return mapping.findForward(REDIRECT_TO_RECO);
						}
						
					}
					return mapping.findForward(DISTRIBUTOR_LOGIN);
				}
				
				/*---END:Get user group roles to determine navigation access---*/
			} else {
				logger.error("Error Getting User Details for  "
						+ authFormBean.getLoginName());
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.login.user_invalid"));
				saveErrors(request, errors);
				return mapping.findForward(AUTHENTICATION_FAILURE);
			}
	
		} catch (VirtualizationServiceException vse)
		// TODO paras 15 oct 2007 change messages accordingly
		{
			logger.error("Error Authenticating User  "
					+ authenticationFormBean.getLoginName()
					+ " Error Message  " + vse.getMessage());
			vse.printStackTrace();
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
			ex.getMessage();
			ex.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.db.create_connection"));
			saveErrors(request, errors);
			return mapping.findForward(AUTHENTICATION_FAILURE);
		}
	
	}
		logger.info(" Executed... ");		
			
		/*******************Added By Nidhi***************************/
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		int forceToRecoPage=0;
		
		if(sessionContext.getAccountLevel().equalsIgnoreCase((Constants.DIST_ACCOUNT_LEVEL)))
		{
			forceToRecoPage= AuthorizationFactory.getAuthorizerImpl()
			.forceToRecoPage(sessionContext.getId());
			if(forceToRecoPage!=0)
			{
				request.setAttribute("disabledLink", Constants.disableLink);
			
				return mapping.findForward(REDIRECT_TO_RECO);
			}
			
		}
		
		if(finFlag==1){
			return mapping.findForward("redirectToFinPayout");
		}
		/****************Added By Nidhi***********************************/			
		
		return mapping.findForward(AUTHENTICATION_SUCCESS);
	}
}