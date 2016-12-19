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
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.dao.rdbms.LoginDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for user login authentication. date
 * 30-aug-2007
 * 
 * @author bhanu
 * 
 */
public class LoginServiceImpl implements LoginService {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(LoginServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.LoginService#getUserInformation(com.ibm.virtualization.recharge.dto.Login)
	 */
	public Login getUserInformation(long loginId)
			throws VirtualizationServiceException {
		logger.info("Started...loginId" + loginId);

		Login login = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			// get user details
			login = loginDao.getAuthenticationDetails(loginId);
		} catch (DAOException de) {
			throw new VirtualizationServiceException(de.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... user information successfully Retrieve  ");
		return login;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.LoginService#unlockUser(com.ibm.virtualization.recharge.dto.Login)
	 */
	
	public void unlockUser(long loginId) throws VirtualizationServiceException {
		logger.info("Started..unlockUser.");
		Connection connection = null;
		
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			logger.info("Before..unlockUser.");
			loginDao.unlockUser(loginId);
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException daoExp) {
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: Update successful");
		
		
	}
	
	public void inActiveIdHelpdeskUser(long loginId) throws VirtualizationServiceException {
		logger.info("Started...");
		Connection connection = null;
		
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			
			loginDao.inActiveIdHelpdeskUser(loginId,currentTime);
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException daoExp) {
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: Update successful");
		
		
	}
	
	public void activeIdHelpdeskUser(long loginId) throws VirtualizationServiceException {
		logger.info("Started...");
		Connection connection = null;
		
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			
			loginDao.activeIdHelpdeskUser(loginId,currentTime);
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException daoExp) {
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: Update successful");
		
		
	}
	
	public boolean isUserLocked(long loginId)throws VirtualizationServiceException 
	{
		Connection connection = null;
		boolean flag = false;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = new LoginDaoRdbms(connection);
			
			flag = loginDao.isUserLocked(loginId);
		}
		catch (DAOException daoExp) 
		{
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(connection);
		}
		return flag;
	}
	
	public int checkPswdExpiring(String loginId)throws VirtualizationServiceException 
	{
		Connection connection = null;
		int flag = 0;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = new LoginDaoRdbms(connection);
			
			flag = loginDao.checkPswdExpiring(loginId);
		}
		catch (DAOException daoExp) 
		{
			logger.error(" DAOException Caught : " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(connection);
		}
		return flag;
	}
}