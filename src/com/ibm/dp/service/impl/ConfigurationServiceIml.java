package com.ibm.dp.service.impl;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ConfigurationMasterDao;
import com.ibm.dp.dto.ConfigurationMaster;
import com.ibm.dp.service.ConfigurationService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class ConfigurationServiceIml implements ConfigurationService{

	private Logger logger = Logger.getLogger(GeographyServiceImpl.class.getName());
	
	@Override
	public void insertConfigurationDetail(ConfigurationMaster configMaster)
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
	logger.info("Started...");
	Connection connection = null;
	try {
		/* get the database connection */
		connection = DBConnectionManager.getDBConnection();
		ConfigurationMasterDao configMasterDao = DAOFactory.getDAOFactory(
				Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
				.getConfigrationMasterDao(connection);
		if (configMaster != null) {
			configMasterDao.insertConfigDetail(configMaster);
				/* Commit the transaction */
				DBConnectionManager.commitTransaction(connection);
		}
	} catch (DAOException de) {
		logger.error(" DAOException occured : " + "Exception Message: "
				+ de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	} finally {
		DBConnectionManager.releaseResources(connection);
	}
	logger.info("Executed ...Successfullly  Created configration ");
}

	@Override
	public ConfigurationMaster getConfigDetails(String configId)
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		logger.info("Started...");
		Connection connection = null;
		ConfigurationMaster configMaster = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			ConfigurationMasterDao configMasterDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getConfigrationMasterDao(connection);
						
			     configMaster = configMasterDao.getConfigDetails(configId);
			     DBConnectionManager.commitTransaction(connection);
				
			     return configMaster;
					/* Commit the transaction */
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		
	}

	@Override
	public void updateConfigDetails(ConfigurationMaster configMaster)
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		
		logger.info("Started...");
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			ConfigurationMasterDao configMasterDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getConfigrationMasterDao(connection);
				configMasterDao.updateConfigDetails(configMaster);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Successfullly  Created configration ");
	}

}
