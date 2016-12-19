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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.SysConfigDao;
import com.ibm.virtualization.recharge.dao.rdbms.SysConfigDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.SysConfigService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for inserting the data of system
 * configuration.
 * 
 * @author Navroze
 * 
 */
public class SysConfigServiceImpl implements SysConfigService {

	/* Logger for this class. */
	private Logger logger = Logger.getLogger(SysConfigServiceImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SysConfigService#defineSystemConfiguration(com.ibm.virtualization.recharge.dto.SysConfig)
	 */
	public void defineSystemConfiguration(SysConfig sysConfig)
			throws VirtualizationServiceException {

		logger.info("Started... " + sysConfig);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			SysConfigDao sysConfigDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getSysConfigDao(connection);
			/*
			 * Call the validate method of SysConfigValidator to validate
			 * SysConfig DTO data.
			 */
			ValidatorFactory.getSysConfigValidator().validateInsert(sysConfig);
			/*
			 * Check if the Circle exists for which System Configuration is
			 * defined.
			 */
			CircleDao circleDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCircleDao(connection);
			if ((circleDao.getActiveCircleName(sysConfig.getCircleId())) == null) {
				logger.error(" Circle Does not Exist or is not Active : ");
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_CIRCLEID_INVALID);
			}
			/* Calls the method of DAO */
			sysConfigDao.insertSystemConfigurationDetail(sysConfig);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... : SysConfig inserted successfully.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SysConfigService#getSystemConfigurationList(int)
	 */
	public ArrayList getSystemConfigurationList(int circleId)
			throws VirtualizationServiceException {
		logger.info("Started...circleId:" + circleId);
		ArrayList sysConfigList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			SysConfigDao sysConfigDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getSysConfigDao(connection);
			sysConfigList = sysConfigDao.getSystemConfigurationList(circleId);
		} catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed..");

		return sysConfigList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SysConfigService#getSystemConfigurationDetails(long)
	 */
	public SysConfig getSystemConfigurationDetails(long sysConfigId)
			throws VirtualizationServiceException {
		logger.info("Started...sysConfigId:" + sysConfigId);
		SysConfig sysConfig;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			SysConfigDao sysConfigDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getSysConfigDao(connection);
			sysConfig = sysConfigDao.getSystemConfigurationDetails(sysConfigId);
			if (sysConfig == null) {

				logger.error("No Record Found for VR_SYSTEM_CONFIG_ID = "
						+ sysConfigId);
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_FETCH);
			}
		} catch (DAOException de) {
			logger.fatal(": DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed...");
		return sysConfig;
	}

	/*
	 * l(non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SysConfigService#getSystemConfigurationDetails(long,
	 *      java.lang.String, java.lang.String)
	 */
	public SysConfig getSystemConfigurationDetails(int circleId,
			TransactionType transactionType, ChannelType channelName)
			throws VirtualizationServiceException {
		logger
				.info("Started...circleId:" + circleId + ":transactionType:"
						+ transactionType.name() + ":channelName:"
						+ channelName.name());
		SysConfig sysConfig = new SysConfig();
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			sysConfig = CacheFactory.getCacheImpl().getSystemConfig(circleId, transactionType, channelName);
			if (sysConfig == null) {

				logger.error("No Record Found for VR_SYSTEM_CONFIG_ID = "
						+ circleId);
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_FETCH);
			}
		} catch (DAOException de) {
			logger.fatal("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed...");
		return sysConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.SysConfigService#updateSystemConfiguration(com.ibm.virtualization.recharge.dto.SysConfig)
	 */
	public void updateSystemConfiguration(SysConfig sysConfig)
			throws VirtualizationServiceException {
		logger.info("Started..." + sysConfig);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			SysConfigDao sysConfigDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getSysConfigDao(connection);
			/*
			 * Call the validate method of SysConfigValidator to validate
			 * SysConfig DTO data.
			 */
			ValidatorFactory.getSysConfigValidator().validateUpdate(sysConfig);
			/*
			 * Check if the Circle exists for which System Configuration is
			 * defined.
			 */
			CircleDao circleDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCircleDao(connection);
			if ((circleDao.getActiveCircleName(sysConfig.getCircleId())) == null) {
				logger.error(" Circle Does not Exist or is In-active : ");
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_CIRCLEID_INVALID);
			}
			sysConfigDao.updateSystemConfiguration(sysConfig);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException daoExp) {
			logger.error(" caught DAOException." + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... SysConfig updated successfully.");
	}
}
