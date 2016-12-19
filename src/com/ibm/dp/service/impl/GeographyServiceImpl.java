/*****************************************************************************\
 **
 ** Distributor Portal.
 **
 **
 \****************************************************************************/
package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.GeographyDao;
import com.ibm.dp.dto.Geography;
import com.ibm.dp.service.GeographyService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.dp.dao.GeographyDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.RegionDao;
import com.ibm.virtualization.recharge.dao.rdbms.RegionDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.dp.service.GeographyService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for Geography related functionalities.
 * 
 * @author Parul
 * 
 */
public class GeographyServiceImpl implements GeographyService {
	/*
	 * Logger for this class.
	 */
	private Logger logger = Logger.getLogger(GeographyServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getRegions()
	 */
	public ArrayList getRegions(Integer level) throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList regionList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GeographyDao regionDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			regionList = regionDao.getActiveGeographys(level);
			if (regionList.size() == 0) {
				// Applicable for all service level api's
				logger.error(" No Active Region ");
				throw new VirtualizationServiceException(
						ExceptionCode.ERROR_NO_ACTIVE_REGION);
			}
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Region list successfully returned.");
		return regionList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#createGeography(com.ibm.virtualization.recharge.dto.Geography)
	 */
	public void createGeography(Geography geography)
			throws VirtualizationServiceException {
		logger.info("Started...");
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			if (geography != null) {
				/*try {
					ValidatorFactory.getGeographyValidator()
							.validateInsert(geography);
				} catch (VirtualizationServiceException validationExp) {
					logger
							.error("Exception occured : Validation Failed for Geography Data.");
					throw new VirtualizationServiceException(validationExp
							.getMessage());
				}*/

				/*
				 * If no geography exist with the name specified then create a new
				 * geography.
				 */
				if (checkUniqueGeography(geography)) {
					geographyDao.insertGeography(geography);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
				}
			}
		} catch (DAOException de) {
			logger.error(" DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Successfullly  Created Geography ");
	}

	/**
	 * This method returns true if the Geography with the values in dto doesnot
	 * exist in the database else returns false
	 * 
	 * @param geography
	 * @return
	 * @throws VirtualizationServiceException
	 */
	private boolean checkUniqueGeography(Geography geography)
			throws VirtualizationServiceException {
		// Check if any geography already exist with the same geography code or geography
		// name.
		ArrayList<Geography> geographyList = getGeographys(geography.getGeographyCode(),
				geography.getGeographyName(),geography.getLevel());
		if (0 == geographyList.size()) {
			return true;
		} else {
			for (Geography element : geographyList) {
				if (element.getGeographyCode().equalsIgnoreCase(
						geography.getGeographyCode())) {
					throw new VirtualizationServiceException(
							ExceptionCode.Geography.ERROR_GEOGRAPHY_CODE_ALREADY_EXIST);
				} else if (element.getGeographyName().equalsIgnoreCase(
						geography.getGeographyName())) {
					throw new VirtualizationServiceException(
							ExceptionCode.Geography.ERROR_GEOGRAPHY_NAME_ALREADY_EXIST);
				} // end if
			} // end for
			return false;
		} // end else

	} // end method

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#updateGeography(com.ibm.virtualization.recharge.dto.Geography)
	 */
	public void updateGeography(Geography geography)
			throws VirtualizationServiceException {
		logger.info("Started..." + geography);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			if (geography != null) {
				try {
					/*
					 * Call the validate method of GeographyValidator to validate
					 * Geography DTO data.
					 */
					ValidatorFactory.getGepgraphyValidator()
							.validateUpdate(geography);
				} catch (VirtualizationServiceException validationExp) {
					logger
							.error("Exception occured : Validation Failed for Geography Data.");
					throw new VirtualizationServiceException(validationExp
							.getMessage());
				}
				
				Geography retrievedGeography = geographyDao.getGeography(geography.getGeographyName(),geography.getLevel());
			
				/*
				 * If no geography exist with the name specified and the same id
				 * then update the geography.
				 */
				if (retrievedGeography == null || retrievedGeography.getGeographyId() == geography.getGeographyId()) {
					/* update the validated geography details */
					geographyDao.updateGeography(geography);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
				} else {
					logger
							.error(" Exception occured : Geography Name already in use.");
					throw new VirtualizationServiceException(
							ExceptionCode.Geography.ERROR_GEOGRAPHY_NAME_ALREADY_EXIST);
				}

			}
		} catch (DAOException de) {
			logger.error("DAOException occured while update : "
					+ "Exception Message: " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Successfullly  Updated Geography");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getAllGeographys()
	 */
	public ArrayList getAllGeography(Integer level) throws VirtualizationServiceException {
		ArrayList geographyList;
		Connection connection = null;
		logger.info("Started...");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geographyList = geographyDao.getAllGeographys(level);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Geography List returned successfully.");
		return geographyList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getAllGeographys()
	 */
	public ArrayList getAllGeographys(ReportInputs inputDto, int lb, int ub,Integer level)
			throws VirtualizationServiceException {
		ArrayList geographyList;
		Connection connection = null;
		logger.info("Started...");
		try {
			// get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geographyList = geographyDao.getAllGeographys(inputDto, lb, ub,level);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Geography List returned successfully.");
		return geographyList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getAllGeographys()
	 */
	/*public ArrayList getAllGeographys(ReportInputs inputDto)
			throws VirtualizationServiceException {
		ArrayList geographyList;
		Connection connection = null;
		logger.info("Started...");
		try {
			 get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geographyList = geographyDao.getAllGeographys(inputDto);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Geography List returned successfully.");
		return geographyList;
	}
*/
/*	public int getGeographysCount(ReportInputs inputDto)
			throws VirtualizationServiceException {
		logger.info("Started...getUsersCountList()");
		Connection connection = null;
		int noOfRows = 0;
		try {
			 get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);

			 fetch total no of geographys 
			noOfRows = geographyDao.getGeographysCount(inputDto);
		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfRows);
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: successfully count no of rows " + noOfRows);
		return noOfRows;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getGeography(java.lang.String)
	 */
	/*public Geography getGeography(String geographyName)
			throws VirtualizationServiceException {
		logger.info("Started... geography_Name = " + geographyName);
		Geography geography;
		Connection connection = null;
		try {
			 get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geography = geographyDao.getGeography(geographyName);
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return geography;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getGeographys(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList<Geography> getGeographys(String geographyCode, String geographyName,Integer level)
			throws VirtualizationServiceException {
		logger.info("Started... geographyCode = " + geographyCode
				+ " geography_Name = " + geographyName);
		ArrayList<Geography> geographyList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geographyList = geographyDao.getGeographys(geographyCode, geographyName ,level);

		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return geographyList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getGeography(int)
	 */
	public Geography getGeography(int geographyId,Integer level) throws VirtualizationServiceException {
		logger.info("Started... geographyId = " + geographyId);
		Geography geography;
		Connection connection = null;
		try {
		//	 get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geography = geographyDao.getGeography(geographyId,level);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return geography;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getGeographys()
	 */
	/*public ArrayList getGeographys() throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList geographyList;
		Connection connection = null;
		try {
			 get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			geographyList = geographyDao.getActiveGeographys();

			if (geographyList.size() == 0) {
				logger.error("getGeographys : No active geographys ");
				throw new VirtualizationServiceException(
						ExceptionCode.Geography.ERROR_ACTIVE_GEOGRAPHY_NOT_EXIST);
			}
		} catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Active Geography List returned.");
		return geographyList;
	}
	*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GeographyService#getGeography(int)
	 */
/*	public String getActiveGeographyName(int geographyId) throws VirtualizationServiceException {
		logger.info("Started... geographyId = " + geographyId);
		Connection connection = null;
		String geographyName;
		try {
			 get the database connection 
			connection = DBConnectionManager.getDBConnection();
			GeographyDao geographyDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getGeographyDao(connection);
			 geographyName = geographyDao.getActiveGeographyName(geographyId);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("GeographyServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return geographyName;
	}
*/
}