/**
 * 
 */
package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LogTransactionDao;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.TransactionLoggerService;
import com.ibm.virtualization.recharge.common.ResourceReader;

/**
 * @author sushilku
 * 
 */
public class TransactionLoggerServiceImpl implements TransactionLoggerService {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(TransactionLoggerServiceImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.TransactionLoggerService#log(com.ibm.virtualization.recharge.dto.TransactionDetails)
	 */
	public void log(ActivityLog activityLog)
			throws VirtualizationServiceException {
		Connection connection = null;
		try {
			logger.info("Initiating log transaction ");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LogTransactionDao logTransactionDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLogTransactionDao(connection);
			logTransactionDao.insertLog(activityLog);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info("transaction log inserted successfully ");
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(
					"error.transaction.logTransaction");
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException:"
					+ "unable to get value from resource bundle"
					+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}

	}

	public void insertTransactionMaster(TransactionMaster transactionMaster )
			throws VirtualizationServiceException {
		Connection connection = null;
		try {
			logger.info("Initiating log transaction ");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			RechargeDao rechargeDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRechargeDao(
					connection);
			rechargeDao.insertTransactionMaster(transactionMaster);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info("transaction log inserted successfully ");
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(
					"error.transaction.logTransaction");
		} 
		 finally {
			DBConnectionManager.releaseResources(connection);
		}

	}

}
