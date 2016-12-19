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
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.dao.UserDao;
import com.ibm.virtualization.recharge.dao.rdbms.UserDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.UserService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for user related functionalities.
 * 
 * @author Paras
 * 
 */
public class UserServiceImpl implements UserService {
	/*
	 * Logger for this class.
	 */
	private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.UserService#createUser(com.ibm.virtualization.recharge.dto.User)
	 */
	public void createUser(User user) throws VirtualizationServiceException {
		logger.info("Starte... " + user);
		Connection connection = null;
		try {
			if (user != null) {
				try {
					/*
					 * Setting default group id if user not authorized For
					 * updating group
					 */
					if (user.getGroupId() == 0) {
						logger
								.info(" User not auth. For Updating Group .....Setting Default Id = "
										+ Constants.DEFAULT_GROUP_ID);
						user.setGroupId(Constants.DEFAULT_GROUP_ID);
					}
					/*
					 * Call the validate method of UserValidator to validate
					 * User DTO data.
					 */
					ValidatorFactory.getUserValidator().validateInsert(user);
				} catch (VirtualizationServiceException validationExp) {
					logger
							.error(" Exception occured : Validation Failed for User Data.");
					throw new VirtualizationServiceException(validationExp
							.getMessage());
				}
				try {
					/* Perform password encryption */
					String encryptedPassword = "";
					String encryptedCheckPassword = "";
					IEncryption encrypt = new Encryption();
					encryptedPassword = encrypt.generateDigest(user
							.getPassword());
					encryptedCheckPassword = encrypt.generateDigest(user.getCheckPassword());
					user.setPassword(encryptedPassword);
					user.setCheckPassword(encryptedCheckPassword);
				} catch (EncryptionException encryptExp) {
					logger
							.error(" Exception occured while encrypting the password.");
					throw new VirtualizationServiceException(
							ExceptionCode.User.ERROR_PASSWORD_ENCRYPTION);
				}

				
				/* get the database connection */
				connection = DBConnectionManager.getDBConnection();
			try{
					LoginDao loginDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getLoginDao(connection);
					// Send the DTO data to DAO for insertion into VR_LOGIN_MASTER
					long loginId = loginDao.insertUser(user);
					logger.info(" Creating user with loginId = " + loginId);
				
					UserDao userDao = DAOFactory.getDAOFactory(
							Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
									.getUserDao(connection);
					/* set the loginId returned from insertUser method of loginDao */
					user.setUserId(loginId);
					/* send the user DTO to DAO for insertion into VR_USER_DETAILS */
					userDao.insertUser(user);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
					logger.info("Executed ... user Created successfully.");
				}catch(VirtualizationServiceException virtualizationExp){
					logger.error("caught VirtualizationServiceException:" +
							"unable to get value from resource bundle"+virtualizationExp.getMessage());
					throw new VirtualizationServiceException(virtualizationExp.getMessage());
				}
			}
		} catch (DAOException daoExp) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.UserService#getAllUsers()
	 */
	public ArrayList getAllUsers(ReportInputs inputDto,int lb,int ub) throws VirtualizationServiceException {
		logger.info("Starting::::");
		ArrayList userList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			UserDao userDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getUserDao(connection);
			//userList = userDao.getAllUsers();
			userList = userDao.getAllUsers(inputDto,lb,ub);
		} catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");

		return userList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.UserService#getAllUsers()
	 */
	public ArrayList getAllUsersList(ReportInputs inputDto) throws VirtualizationServiceException {
		logger.info("Starting::::");
		ArrayList userList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			UserDao userDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getUserDao(connection);
			//userList = userDao.getAllUsers();
			userList = userDao.getAllUsers(inputDto);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");

		return userList;
	}	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.service.UserService#getUsersCount(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	public int getUsersCount(ReportInputs inputDto) throws VirtualizationServiceException{
		logger.info("Started...getUsersCountList()");
		Connection connection = null;
		int noOfRows = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			UserDao userDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getUserDao(connection);

			/* fetch total no of circles */
			noOfRows = userDao.getUsersCount(inputDto);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger
					.fatal("Exception "
							+ de.getMessage()
							+ " Occured While counting number of rows"
							+ noOfRows);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed :::: successfully count no of rows "
						+ noOfRows);
		return noOfRows;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.UserService#getUser(long)
	 */
	public User getUser(long loginId) throws VirtualizationServiceException {
		logger.info("Starting:::: loginId = " + loginId);
		User user = new User();
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			UserDao userDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getUserDao(connection);
			user = userDao.getUser(loginId);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException daoExp) {
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed:::: Found a user with loginId = " + loginId);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.UserService#updateUser(com.ibm.virtualization.recharge.dto.User)
	 */
	public void updateUser(User user) throws VirtualizationServiceException {
		logger.info("Started..." + user);
		Connection connection = null;
		try {
			/*
			 * Call the validate method of UserValidator to validate User DTO
			 * data.
			 */
			ValidatorFactory.getUserValidator().validateUpdate(user);
		} catch (VirtualizationServiceException validationExp) {
			logger
					.error(" Exception occured : Validation Failed for User Data.");
			throw new VirtualizationServiceException(validationExp.getMessage());
		}

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/* update user details in VR_LOGIN_MASTER */
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			loginDao.updateUserLogin(user);

			/* update user details in VR_USER_DETAILS */
			UserDao userDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getUserDao(connection);
			userDao.updateUser(user);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException daoExp) {
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: Update successful");
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.UserService#getUser(java.lang.String)
	 */
	public long getUser(String loginName) throws VirtualizationServiceException {
		logger.info("Started...loginName :" + loginName);
		long loginId = -1;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			UserDao userDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getUserDao(connection);
			loginId = userDao.getUser(loginName);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException daoExp) {
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed .... Found a user with login name = "
				+ loginName);
		return loginId;
	}
}