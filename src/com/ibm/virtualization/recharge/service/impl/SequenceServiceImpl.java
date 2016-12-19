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

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.SequenceDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.SequenceService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * Service for fetching the next transaction id
 * 
 * @author ashish
 * 
 */
public class SequenceServiceImpl implements SequenceService {
	/*
	 * Logger for this class.
	 */
	private Logger logger = Logger.getLogger(SequenceServiceImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SequenceService#getNextTransID()
	 */
	public long getNextTransID() throws VirtualizationServiceException {
		logger.info("starting............");

		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			SequenceDao dao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getSequenceDao(connection);

			logger.info("Succefully exiting with new transaction id");
			return dao.getNextTransID();
		} catch (DAOException de) {
			logger
					.fatal("DAO Exception occured while fetching new transaction Id");
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			logger.info("Finally release connection.");
			DBConnectionManager.releaseResources(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SequenceService#getNextRechargeTransID()
	 */
	public long getNextRechargeTransID() throws VirtualizationServiceException {
		logger.info("starting............");

		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			SequenceDao dao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSequenceDao(connection);
			logger.info("Succefully exiting with new transaction id");

			return dao.getNextTransIDForRecharge();
		} catch (DAOException de) {
			logger
					.fatal("DAO Exception occured while fetching new transaction Id");
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			logger.info("Finally release connection.");
			DBConnectionManager.releaseResources(connection);
		}
	}

}
