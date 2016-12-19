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

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.RegionDao;
import com.ibm.virtualization.recharge.dao.rdbms.RegionDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for circle related functionalities.
 * 
 * @author Paras
 * 
 */
public class CircleServiceImpl implements CircleService {
	/*
	 * Logger for this class.
	 */
	private Logger logger = Logger.getLogger(CircleServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getRegions()
	 */
	public ArrayList getRegions() throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList regionList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			// TODO BAhuja Aug.17, 2007 To use dao factory here.
			RegionDao regionDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRegionDao(connection);
			regionList = regionDao.getActiveRegions();
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
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
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
	 * @see com.ibm.virtualization.recharge.service.CircleService#createCircle(com.ibm.virtualization.recharge.dto.Circle)
	 */
	public void createCircle(Circle circle)
			throws VirtualizationServiceException {
		logger.info("Started..." + circle);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			if (circle != null) {
				try {
					/*
					 * While creating a Circle, Available and Operating Balance
					 * equals Opening Balance
					 */
//					circle.setAvailableBalance(circle.getOpeningBalance());
//					circle.setOperatingBalance(circle.getOpeningBalance());
					/*
					 * Call the validate method of CircleValidator to validate
					 * Circle DTO data.
					 */
					ValidatorFactory.getCircleValidator()
							.validateInsert(circle);
				} catch (VirtualizationServiceException validationExp) {
					logger
							.error("Exception occured : Validation Failed for Circle Data.");
					throw new VirtualizationServiceException(validationExp
							.getMessage());
				}

				/*
				 * If no circle exist with the name specified then create a new
				 * circle.
				 */
				if (checkUniqueCircle(circle)) {
					circleDao.insertCircle(circle);
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
		logger.info("Executed ...Successfullly  Created Circle ");
	}

	/**
	 * This method returns true if the Circle with the values in dto doesnot
	 * exist in the database else returns false
	 * 
	 * @param circle
	 * @return
	 * @throws VirtualizationServiceException
	 */
	private boolean checkUniqueCircle(Circle circle)
			throws VirtualizationServiceException {
		// Check if any circle already exist with the same circle code or circle
		// name.
		ArrayList<Circle> circleList = getCircles(circle.getCircleCode(),
				circle.getCircleName());
		if (0 == circleList.size()) {
			return true;
		} else {
			for (Circle element : circleList) {
				if (element.getCircleCode().equalsIgnoreCase(
						circle.getCircleCode())) {
					throw new VirtualizationServiceException(
							ExceptionCode.Circle.ERROR_CIRCLE_CODE_ALREADY_EXIST);
				} else if (element.getCircleName().equalsIgnoreCase(
						circle.getCircleName())) {
					throw new VirtualizationServiceException(
							ExceptionCode.Circle.ERROR_CIRCLE_NAME_ALREADY_EXIST);
				} // end if
			} // end for
			return false;
		} // end else

	} // end method

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#updateCircle(com.ibm.virtualization.recharge.dto.Circle)
	 */
	public void updateCircle(Circle circle)
			throws VirtualizationServiceException {
		logger.info("Started..." + circle);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			if (circle != null) {
				try {
					/*
					 * Call the validate method of CircleValidator to validate
					 * Circle DTO data.
					 */
					ValidatorFactory.getCircleValidator()
							.validateUpdate(circle);
				} catch (VirtualizationServiceException validationExp) {
					logger
							.error("Exception occured : Validation Failed for Circle Data.");
					throw new VirtualizationServiceException(validationExp
							.getMessage());
				}
				
				Circle retrievedCircle = circleDao.getCircle(circle.getCircleName());
			
				/*
				 * If no circle exist with the name specified and the same id
				 * then update the circle.
				 */
				if (retrievedCircle == null || retrievedCircle.getCircleId() == circle.getCircleId()) {
					/* update the validated circle details */
					circleDao.updateCircle(circle);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
				} else {
					logger
							.error(" Exception occured : Circle Name already in use.");
					throw new VirtualizationServiceException(
							ExceptionCode.Circle.ERROR_CIRCLE_NAME_ALREADY_EXIST);
				}

			}
		} catch (DAOException de) {
			logger.error("DAOException occured while update : "
					+ "Exception Message: " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Successfullly  Updated Circle");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getAllCircles()
	 */
	public ArrayList getAllCircles() throws VirtualizationServiceException {
		ArrayList circleList;
		Connection connection = null;
		logger.info("Started...");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circleList = circleDao.getAllCircles();
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Circle List returned successfully.");
		return circleList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getAllCircles()
	 */
	public ArrayList getAllCircles(ReportInputs inputDto, int lb, int ub)
			throws VirtualizationServiceException {
		ArrayList circleList;
		Connection connection = null;
		logger.info("Started...");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circleList = circleDao.getAllCircles(inputDto, lb, ub);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Circle List returned successfully.");
		return circleList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getAllCircles()
	 */
	public ArrayList getAllCircles(ReportInputs inputDto)
			throws VirtualizationServiceException {
		ArrayList circleList;
		Connection connection = null;
		logger.info("Started...");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circleList = circleDao.getAllCircles(inputDto);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Circle List returned successfully.");
		return circleList;
	}

	public int getCirclesCount(ReportInputs inputDto)
			throws VirtualizationServiceException {
		logger.info("Started...getUsersCountList()");
		Connection connection = null;
		int noOfRows = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);

			/* fetch total no of circles */
			noOfRows = circleDao.getCirclesCount(inputDto);
		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfRows);
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: successfully count no of rows " + noOfRows);
		return noOfRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getCircle(java.lang.String)
	 */
	public Circle getCircle(String circleName)
			throws VirtualizationServiceException {
		logger.info("Started... circle_Name = " + circleName);
		Circle circle;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circle = circleDao.getCircle(circleName);
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return circle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getCircles(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList<Circle> getCircles(String circleCode, String circleName)
			throws VirtualizationServiceException {
		logger.info("Started... circleCode = " + circleCode
				+ " circle_Name = " + circleName);
		ArrayList<Circle> circleList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circleList = circleDao.getCircles(circleCode, circleName);

		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return circleList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getCircle(int)
	 */
	public Circle getCircle(int circleId) throws VirtualizationServiceException {
		logger.info("Started... circleId = " + circleId);
		Circle circle;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circle = circleDao.getCircle(circleId);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return circle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getCircles()
	 */
	public ArrayList getCircles() throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList circleList;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			circleList = circleDao.getActiveCircles();

			if (circleList.size() == 0) {
				logger.error("getCircles : No active circles ");
				throw new VirtualizationServiceException(
						ExceptionCode.Circle.ERROR_ACTIVE_CIRCLE_NOT_EXIST);
			}
		} catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Active Circle List returned.");
		return circleList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleService#getCircle(int)
	 */
	public String getActiveCircleName(int circleId) throws VirtualizationServiceException {
		logger.info("Started... circleId = " + circleId);
		Connection connection = null;
		String circleName;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleDao circleDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			 circleName = circleDao.getActiveCircleName(circleId);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...");
		return circleName;
	}

}