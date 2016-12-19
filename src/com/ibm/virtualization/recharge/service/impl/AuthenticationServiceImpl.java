/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.hbo.dao.HBOUserMstrDao;
import com.ibm.hbo.dao.impl.HBOUserMstrDaoImpl;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.exception.HBOException;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ResponseCode;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.dao.UserDao;
import com.ibm.virtualization.recharge.dao.rdbms.LoginDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.ChangePasswordDto;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.RechargeTransCircleDetail;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.MessageSendService;
/**
 * This class provides the implementation for all the method declarations in
 * AuthenticationService interface
 * 
 * @author Prakash
 * 
 */
public class AuthenticationServiceImpl implements AuthenticationService {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(AuthenticationServiceImpl.class
			.getName());
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#authenticateMobileNo(java.lang.String,
	 *      java.lang.String)
	 */
	public UserSessionContext authenticateMobileNo(String mobileNumber,
			String mPassword) throws VirtualizationServiceException {
		logger.info("Started... mobileNumber " + mobileNumber);

		UserSessionContext context = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao userDao = new LoginDaoRdbms(connection);
			// Get the encrypted password
			// IEncryption encrypt = new Encryption();
			// String newDigest = encrypt.generateDigest(mPassword);
			// Get context from AccountDao
			context = userDao.getMUserContext(mobileNumber, mPassword);
			if (context != null) {
				/* find whether the Source accout is active or not */
				logger.info("Status:" + context.getStatus());
				if (!("A".equalsIgnoreCase(context.getStatus()))) {
					logger
							.error(" Source Account Inactive .Exception occured while getting USerSessionContext for mobileNumber "
									+ mobileNumber);
					throw new VirtualizationServiceException(
							ResponseCode.INACTIVE_SOURCE_ACCOUNT,
							ExceptionCode.Authentication.ERROR_INACTIVE_SOURCEACCOUNT);

				}

			}

		} catch (DAOException de) {
			logger.fatal(
					" DAOException occured while getting USerSessionContext for mobileNumber "
							+ mobileNumber, de);
			/* check if the Source Account's Circle is InActive */
			if (de.getMessage().equals(
					ExceptionCode.Authentication.ERROR_INACTIVE_SOURCECIRCLE)) {
				logger.info("SourceAccountInactive");
				throw new VirtualizationServiceException(
						ResponseCode.INACTIVE_SOURCE_CIRCLE, de.getMessage());

			} else {
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_INVALID_DETAILS, de.getMessage());
			}
		}
		// catch (EncryptionException ee) {
		// logger.error(ee.getMessage(), ee);
		// throw new VirtualizationServiceException(ee.getMessage());
		// }
		finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("get UserSessionContext object for mobileNumber "
				+ mobileNumber);
		logger.info("Executed ....");
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#authenticateAccount(java.lang.String,
	 *      java.lang.String)
	 */
	public UserSessionContext authenticateAccount(String accountCode,
			String password) throws VirtualizationServiceException {

		logger.info("Authentiction for account user: " + accountCode);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			IEncryption encrypt = new Encryption();
			String newDigest = encrypt.generateDigest(password);
			LoginDao loginDao = new LoginDaoRdbms(connection);
			UserSessionContext userSessionContext = loginDao
					.authenticateAccount(accountCode, newDigest);
			if (userSessionContext == null) {
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_AUTENTICATION_FAILURE_CODE,
						ExceptionCode.Authentication.ERROR_AUTHENTICATION_FAILED);
			}
			logger.info("Executed ....");
			return userSessionContext;
		} catch (DAOException daoExp) {
			logger.error(daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch (EncryptionException ee) {
			logger.error(ee.getMessage(), ee);
			throw new VirtualizationServiceException(ee.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#changePassword(long,
	 *      java.lang.String, java.lang.String)
	 */
	public void changePassword(long loginId, String oldPassword,
			String newPassword) throws VirtualizationServiceException {
		logger.info("Started... loginId " + loginId);
		Connection connection = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = new LoginDaoRdbms(connection);
			logger.info("calling updatePassword()");
			// Step - 1 update the password

			//int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));
			int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_NOREPEAT_COUNT));
			// boolean flag=loginDao.isUserLocked(loginId);

			loginDao.updatePassword(loginId, oldPassword, newPassword);

			// Step - 2 create Password History for old password
			loginDao.updatePasswordHistory(loginId, oldPassword,
					numberOfPwdHistory);

			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger
					.info(" Password successfully changed and password history table updated for loginId "
							+ loginId);

		} catch (DAOException daoExp) {
			logger
					.error(" DAOException occured while updating password history "
							+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ....");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#changeMPassword(long,
	 *      java.lang.String, java.lang.String)
	 */
	public int changeMPassword(ChangePasswordDto changePasswordDto)
			throws VirtualizationServiceException {
		logger.info("Started... for loginId "
				+ changePasswordDto.getSourceAccountId());
		Connection connection = null;
		int returnTransStatus = -1;
		try {
			/* get the database connection */

			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			// changePasswordDto.setRequestType(RequestType.CHANGEMPWD);

			returnTransStatus = insertTransactionMaster(changePasswordDto);
			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {
				return TransactionStatus.DUPLICATE.getValue();
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				return TransactionStatus.BLACKOUT.getValue();
			}

			// LoginDao loginDao = new LoginDaoRdbms(connection);

			logger.info("Calling updateMPassword()");
			// Step - 1 update the password

			loginDao.updateMPassword(changePasswordDto.getSourceAccountId(),
					changePasswordDto.getNewDigest(), changePasswordDto
							.getOldDigest());

			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info(" M-Password successfully changed for loginId "
					+ changePasswordDto.getSourceAccountId());
		} catch (DAOException daoExp) {
			logger
					.error(" DAOException occured while updating M-password for loginId "
							+ changePasswordDto.getSourceAccountId());
			if (daoExp
					.getMessage()
					.equals(
							ExceptionCode.Authentication.ERROR_INVALID_CHANGE_OLD_PASSWORD)) {
				throw new VirtualizationServiceException(
						ResponseCode.CHANGE_M_PASSWORD_INVALID_OLD, daoExp
								.getMessage());
			} else {
				throw new VirtualizationServiceException(daoExp.getMessage());
			}
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ....");
		return returnTransStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#changeMPassword(long,
	 *      java.lang.String, java.lang.String)
	 */
	public int resetMPassword(ChangePasswordDto changePasswordDto)
			throws VirtualizationServiceException {
		logger.info("Started... for loginId "
				+ changePasswordDto.getSourceAccountId());
		Connection connection = null;
		int returnTransStatus = -1;
		try {
			/* get the database connection */

			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);

			returnTransStatus = insertTransactionMaster(changePasswordDto);

			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {
				return TransactionStatus.DUPLICATE.getValue();
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				return TransactionStatus.BLACKOUT.getValue();
			}

			// LoginDao loginDao = new LoginDaoRdbms(connection);

			logger.info("Calling updateMPassword()");
			// Step - 1 update the password

			loginDao.resetMPassword(changePasswordDto.getSourceAccountId(),
					changePasswordDto.getNewDigest());

			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info(" M-Password successfully reset for loginId "
					+ changePasswordDto.getSourceAccountId());
		} catch (DAOException daoExp) {
			logger
					.error(" DAOException occured while updating M-password for loginId "
							+ changePasswordDto.getSourceAccountId());
			if (daoExp
					.getMessage()
					.equals(
							ExceptionCode.Authentication.ERROR_INVALID_CHANGE_OLD_PASSWORD)) {
				throw new VirtualizationServiceException(
						ResponseCode.CHANGE_M_PASSWORD_INVALID_OLD, daoExp
								.getMessage());
			} else {
				throw new VirtualizationServiceException(daoExp.getMessage());
			}
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ....");
		return returnTransStatus;
	}

	/**
	 * This method inserts input details into transaction master table.
	 * 
	 * @param dto
	 * @return returnTransStatus
	 * @throws DAOException
	 */
	private int insertTransactionMaster(ChangePasswordDto changePasswordDto)
			throws DAOException, VirtualizationServiceException {
		/* insert into customer transaction table */

		int returnTransStatus = -1;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			RechargeDao rechargeDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRechargeDao(connection);

			// RechargeDao rechargeDao = new RechargeDaoRdbms(connection);
			TransactionMaster transactionMaster = new TransactionMaster();
			transactionMaster.setCreatedBy(changePasswordDto
					.getSourceAccountId());
			transactionMaster.setCreatedDate(new Date());
			transactionMaster.setSourceId(changePasswordDto
					.getSourceAccountId());
			transactionMaster.setTransactionChannel(changePasswordDto
					.getChannelType());
			transactionMaster.setTransactionDate(new Date());
			// TODO ashish Sept 13 2007 Need to insert message xml
			transactionMaster.setTransactionStatus(TransactionStatus.PENDING);
			transactionMaster
					.setRequestType(changePasswordDto.getRequestType());

			transactionMaster.setTransactionMessage(changePasswordDto
					.toString());
			transactionMaster.setTransactoinId(changePasswordDto
					.getTransactionId());
			returnTransStatus = rechargeDao
					.insertTransactionMasterPreProcess(transactionMaster);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} finally {
			logger
					.info("createAccount() Method : Finally release connection.");
			DBConnectionManager.releaseResources(connection);
		}
		return returnTransStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#isCircleActive(int)
	 */

	public void isCircleActive(int circleId)
			throws VirtualizationServiceException {
		logger.info("Started... for loginid " + circleId);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circleDao.getActiveCircleName(circleId);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info(" Login User Circle Is Active ");
		} catch (DAOException daoExp) {
			logger
					.error(" DAOException occured while checking circle Active or not  For Circle Id :-"
							+ circleId);
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_LOGIN_FAILURE_INACTIVE_CIRCLE);

		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ....");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#changePassword(long,
	 *      java.lang.String, java.lang.String)
	 */
	public void resetPassword(long loginId, String userType)
			throws VirtualizationServiceException {
		logger.info("Started... loginId " + loginId);
		Connection connection = null;
		PreparedStatement stmt=null;
		ResultSet rset=null;
		try {
			/* get the subject from properties file */
			ResourceBundle resourceBundle = ResourceBundle
			.getBundle(Constants.RESOURCE_FILE_PASSWORD);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			String oldDigestPassword = null;
			User user = null;
			Account account = null;
			String emailAddress = null;
			String mobileno = null;
			int msgFlag=0;
			String Success_Message=null;
			// MessageSendService messageSendService=null;
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			account = accountDao.getAccount(loginId);
			oldDigestPassword = account.getIPassword();
			if (userType.equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL)) {
				//AccountDao accountDao = new AccountDaoRdbms(connection);
				emailAddress = account.getEmail();
				mobileno = account.getMobileNumber();

			} else {
			//	UserDao userDao = new UserDaoRdbms(connection);
				UserDao userDao =DAOFactory.getDAOFactory(
						Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getUserDao(connection);
				
				user = userDao.getUser(loginId);
				oldDigestPassword = user.getPassword();
				emailAddress = user.getEmailId();
				mobileno = user.getContactNumber();
			}

			//LoginDao loginDao = new LoginDaoRdbms(connection);
			
			LoginDao loginDao =DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			
			IEncryption encrypt = new Encryption();

			String newPassword = Utility.getRandomPassword();
			String newDigestPassword = encrypt.generateDigest(newPassword);
			
			int numberExpDays =Integer.parseInt(ResourceReader.getValueFromBundle(Constants.PWD_EXPIRY_LIMIT ,Constants.GSD_RESOURCE_BUNDLE));
						
			// Step - 1 update the password
			loginDao.resetIPassword(loginId,oldDigestPassword,newDigestPassword,numberExpDays);
			
			int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));
			
			 
			//Step - 2 create Password History for old password
			loginDao.updatePasswordHistory(loginId, oldDigestPassword,
					numberOfPwdHistory);
			
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);

			try {
				// sending email
				if ((emailAddress != null)) {
					String Subject = resourceBundle.getString("email.reset.subject");
					String Message = resourceBundle.getString("email.reset.message");
					String mainMessage = Message + newPassword;
					MessageSendServiceImpl messageSendService = new MessageSendServiceImpl();
					messageSendService.sendMailToRequester(mainMessage ,
							emailAddress, Subject);
				} else {

					logger.info(" Email Address Not Found For User :-  "
							+ loginId);
				}
			} catch (Exception e) {
				logger.error("Exception occured while sending Mail", e);
			}

			// sending sms
			try
			{
			if ((mobileno != null)) {
				MessageSendService messageSendService = new MessageSendServiceImpl();
				stmt=connection.prepareStatement("SELECT ENABLE_FLAG,SUCCESS_MESSAGE FROM DP_ALERT_MASTER WHERE ALERT_ID=2");
				rset=stmt.executeQuery();
				if(rset.next())
				{
					msgFlag=rset.getInt("ENABLE_FLAG");
					Success_Message=rset.getString("SUCCESS_MESSAGE");
				}
				//if ("true".equalsIgnoreCase(ResourceReader.getValueFromBundle(Constants.SMS_SENDER_SEND,Constants.RESOURCE_FILE_PASSWORD))) {
				if(msgFlag==1)
				{
					if(Success_Message!=null){
						messageSendService.sendSMS(mobileno,Success_Message.replaceAll("account_name", account.getAccountName()).replaceAll("password", newPassword));
						logger.info(" Password successfully reset  updated for loginId "+ loginId);
					}
					else{
						logger.info("Message is not defined in database to send ::::::");
					}
				}
					else {
					logger.info(" SMS send property is set to false");
				}

			} else {

				logger.info(" Mobile No.  Not Found For User :-  " + loginId);
			}
			}
			catch(SQLException ex)
			{
				logger
				.error(" Exceptin  occured while sending i-password  "
						+ ex.getMessage());
					throw new VirtualizationServiceException(
				ExceptionCode.ERROR_RESET_IPASSWORD);
			}
		} catch (EncryptionException e) {
			logger
					.error(" EncryptionException occured while reset i-password  "
							+ e.getMessage());
			throw new VirtualizationServiceException(
					ExceptionCode.ERROR_RESET_IPASSWORD);

		} catch (DAOException daoExp) {
			logger.error(" DAOException occured while reset i-password  "
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch (MissingResourceException missingResourceExp) {
			logger.error(" MissingResourceException occured while reset i-password  "
					+ missingResourceExp.getMessage());
			throw new VirtualizationServiceException(missingResourceExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("AuthenticationServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(stmt, rset);
		}
		logger.info("Executed ....");
	}
	
	public void validateUserPassword(Login login)
		throws VirtualizationServiceException{
			ValidatorFactory.getloginValidator().validate(login);
		}

	public void validateUserPassword(String loginName, String password) throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		
	}

	public void validateChangePassword(ChangePasswordDto changepassword)
	throws VirtualizationServiceException{
		ValidatorFactory.getChangePasswordValidator().validate(changepassword);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#getRechargeCircleDetail(com.ibm.virtualization.recharge.dto.RechargeTransCircleDetail)
	 */
	public void getRechargeCircleDetail(
			RechargeTransCircleDetail rechargeTransCircleDetailObj) {

		// If Source CircleId has not been set earlier then validate Source &
		// set the Source CircleId
			// Authenticating The requester /
			try {
				
				getSourceCircleId(rechargeTransCircleDetailObj);
				// Set the Source CircleId to the return parameter
				
			} catch (Exception e) {
				logger
						.error("exception occured while authenticating Source for mobile no:"
								+ rechargeTransCircleDetailObj
										.getSourceMobileNumber());
			}
		
		// search susbcriber circle only in case subscriber no is not null 
		if (rechargeTransCircleDetailObj.getSubscriberMobileNumber() != null &&rechargeTransCircleDetailObj.getSubscriberMobileNumber().length()!=0 ) {
			try {
				INRouterServiceDTO routerServiceDTO = CacheFactory
						.getCacheImpl().getDestinationIn(
								rechargeTransCircleDetailObj
										.getSubscriberMobileNumber());
				// set Subscriber's CircleId /
				rechargeTransCircleDetailObj
						.setSubscriberCircleId(routerServiceDTO
								.getSubsCircleId());
				if (rechargeTransCircleDetailObj.getTransactionType() == TransactionType.RECHARGE) {
					rechargeTransCircleDetailObj
							.setSubscriberCircleCode(routerServiceDTO
									.getSubscriberCircleCode());
				} else if (rechargeTransCircleDetailObj.getTransactionType() == TransactionType.POSTPAID_MOBILE) {
					rechargeTransCircleDetailObj
					.setSubscriberCircleCode(routerServiceDTO
							.getWireLessCode());
				} else if (rechargeTransCircleDetailObj.getTransactionType() == TransactionType.POSTPAID_ABTS) {
					rechargeTransCircleDetailObj
					.setSubscriberCircleCode(routerServiceDTO
							.getWireLineCode());
				} else if (rechargeTransCircleDetailObj.getTransactionType() == TransactionType.POSTPAID_DTH) {
					rechargeTransCircleDetailObj
					.setSubscriberCircleCode(routerServiceDTO
							.getDthCode());
				}

			} catch (Exception e) {
				logger
						.error("Exception occured while fetching subcriber's cirlceId for mobile no:"
								+ rechargeTransCircleDetailObj
										.getSubscriberMobileNumber());
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AuthenticationService#getSourceCircleId(java.lang.String)
	 */
	private void getSourceCircleId(RechargeTransCircleDetail rechargeTransCircleDetail)
	throws VirtualizationServiceException{
		logger.info("Started... mobileNumber " + rechargeTransCircleDetail.getSourceMobileNumber());

		Connection connection = null;
		
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao userDao = new LoginDaoRdbms(connection);
			RechargeTransCircleDetail circleDetail=new RechargeTransCircleDetail();
			circleDetail=userDao.getSourceCircleId(rechargeTransCircleDetail.getSourceMobileNumber());
			rechargeTransCircleDetail.setSourceCircleId(circleDetail.getSourceCircleId());
			rechargeTransCircleDetail.setSourceAccountId(circleDetail.getSourceAccountId());
			rechargeTransCircleDetail.setSourceCircleCode(circleDetail.getSourceCircleCode());
		} catch (DAOException de) {
			throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_INVALID_DETAILS, de.getMessage());
		}
		finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("get UserSessionContext object for mobileNumber "
				+ rechargeTransCircleDetail.getSourceMobileNumber());
		logger.info("Executed ....");
		
	}

	public String pwdMailingService(String userLogingId, String userEmail, String MobileNo)
	throws VirtualizationServiceException {
		logger.info("authenticationserviceimpl > pwdMailingService  inserting in DP_SEND_SMS  ");
	StringBuffer sbMessage = new StringBuffer();
	String strMessage = null;
	String txtMessage = null;
	String strSubject = "Your Distributor Portal System Login Password";
	sbMessage.append("Dear " + userLogingId + ", \n\n");
	sbMessage.append("Your new password is : ");
	strMessage =
		userLogingId.substring(0, 1)
			+ Math.abs(new Random().nextInt())
			+ userLogingId.substring(2, 3);

	sbMessage.append(strMessage + "\n");
	sbMessage.append("\nRegards ");
	sbMessage.append("\nDistributor Portal System Administartor ");
	sbMessage.append(
		"\n\n/** This is an Auto generated email.Please do not reply to this.**/");
	txtMessage = sbMessage.toString();

	//String strHost = PropertyReader.getValue("LOGIN.SMTP"); 
	String strHost = ResourceReader.getCoreResourceBundleValue("email.server");
	logger.info("SMTP HOST : " + strHost);
	String strFromEmail = PropertyReader.getValue("LOGIN.EMAIL");
	logger.info("From EMAIL : " + strFromEmail);
	
	
	try {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", strHost);
		Session ses = Session.getDefaultInstance(prop, null);
		MimeMessage msg = new MimeMessage(ses);
		msg.setFrom(new InternetAddress(strFromEmail));
		msg.addRecipient(
			Message.RecipientType.TO,
			new InternetAddress(userEmail));
		msg.setSubject(strSubject);
		msg.setText(txtMessage);
		msg.setSentDate(new Date());
		Transport.send(msg);
		

	} catch (Exception e) {
		e.printStackTrace();
		
		logger.error(
			"Exception occured in pwdMailingService():" + e.getMessage());
		throw new VirtualizationServiceException("Mail Could Not Be Sent Due to Some Issue. Please Contact System Administrator.");

	}

	// ********************************ALERT MANAGEMENT********************************
	logger.info("*********************Sending SMS alerts***********************for password change "+userLogingId);
	SMSDto sMSDto = new SMSDto();
	sMSDto.setAccountName(userLogingId);
	sMSDto.setNewPassword(strMessage);
	sMSDto.setMobileNumber(MobileNo);
	sMSDto.setAlertType(Constants.CONFIRM_ID_CHANGE_PASSWORD);
	Connection connection = null;
	
	try {
		connection = DBConnectionManager.getDBConnection();
		connection.setAutoCommit(false);
		LoginDao loginDao = new LoginDaoRdbms(connection);
		loginDao.sendPasswordSMS(sMSDto);
		connection.commit();
	}catch (DAOException de) {
		try{
			connection.rollback();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(
					"Exception occured in rollback():" + ex.getMessage());
		}
		
		de.printStackTrace();
		logger.error("Error in creating SMS alert for Forget Password.");
		//throw new VirtualizationServiceException(de.getMessage());
	}
	catch(Exception e)
	{
		try{
			connection.rollback();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(
					"Exception occured in rollback():" + ex.getMessage());
		}
		
		e.printStackTrace();
		logger.error("Error in creating SMS alert for Forget Password.");
		
	}

	finally {
		DBConnectionManager.releaseResources(connection);
	}
	/**
	 * ********************************ALERT MANAGEMENT ENDS********************************
	*/
	
	
	return strMessage;
}
	
	public int changePasswordService(HBOUserMstr dto) throws VirtualizationServiceException {
		HBOUserMstrDao userDataDao = new HBOUserMstrDaoImpl();
		int updateCnt = 0;
		try {
			//Update password dao called
			updateCnt = userDataDao.updatePassword(dto);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in changePasswordService():"
						+ e.getMessage());
				throw new VirtualizationServiceException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in changePasswordService():"
						+ e.getMessage());
			}
		}
		return updateCnt;
	}

	public long getAccountId(String accountCode) throws VirtualizationServiceException {
		long accountId;
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		accountId = accountDao.getAccountId(accountCode);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return accountId;
	}
	
	public String getEmailId(long accountId) throws VirtualizationServiceException {
		String emailId="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		emailId = accountDao.getEmailId(accountId);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return emailId;
	}
	
	public String getMobileNo(long accountId) throws VirtualizationServiceException {
		String mobileNo="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		mobileNo = accountDao.getMobileNumber(accountId);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return mobileNo;
	}
	
	public String getMobileNoForSms(long accountId,int smsAlertType) throws VirtualizationServiceException {
		logger.info("authenticatinServicepmpl > getMobileNoForSms 222222");
		String mobileNo="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		mobileNo = accountDao.getMobileNoForSms(accountId,smsAlertType);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return mobileNo;
	}
	
	
	public void changePassword(String changePwd , long loginId,
			String newPassword) throws VirtualizationServiceException{
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = new LoginDaoRdbms(connection);
			loginDao.changePassword(changePwd,loginId, newPassword);
			
		}catch (DAOException de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	}
	
//	Created by Shilpa on 17-01-2012 for Critical changes CR
	public String getAccountName(long accountId) throws VirtualizationServiceException {
		String loginName="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		loginName = accountDao.getAccountName(accountId);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return loginName;
	}
	
	
	public String validatePasswordExpiry(Login login) throws VirtualizationServiceException {
		String loginName="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		loginName = accountDao.validatePasswordExpiry(login);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return loginName;
	}
	
	
	public String isNewUser(long login) throws VirtualizationServiceException {
		String loginName="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		loginName = accountDao.isNewUser(login);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return loginName;
	}
	
	public String getStatus(String accountCode) throws VirtualizationServiceException {
		String status="";
		try{
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		DPCreateAccountDao accountDao = DAOFactory
		.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountDao(connection);
		status = accountDao.getStatus(accountCode);
		
	}catch (DAOException de) {
		de.printStackTrace();
		logger.error("Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	return status;
	}
	
	public void changePassword(long loginId,
			String newPassword) throws VirtualizationServiceException{
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = new LoginDaoRdbms(connection);
			loginDao.changePassword(loginId, newPassword);
			
		}catch (DAOException de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	}
	
	
}
