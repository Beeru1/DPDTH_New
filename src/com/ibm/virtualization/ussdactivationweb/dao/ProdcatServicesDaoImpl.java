/**************************************************************************
 * File     : ProdcatServicesDaoImpl.java
 * Author   : Banita
 * Created  : Oct 7, 2008
 * Modified : Oct 7, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 7, 2008 	Banita	First Cut.
 * V0.2		Oct 7, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.ProdcatInterface;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationMstDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.ServiceDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * *This class is used to fetch the details of the service class,location and
 * circle
 * 
 * @author Banita
 * @version 1.0
 * 
 ******************************************************************************/
public class ProdcatServicesDaoImpl {

	private static Logger logger = Logger
	.getLogger(ProdcatServicesDaoImpl.class.getName());

	/**
	 * This method getServiceClassById() gives the details about service class.
	 * 
	 * @param serviceClassId
	 *            one parameter on which data can be fetch.
	 * @param circleCode
	 *            another parameter to fetch the details of the service class
	 * 
	 * @return an ServiceDTO:dto that contails all the details of the service
	 *         class
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */

	public ServiceDTO getServiceClassById(int serviceClassId, String circleCode)
	throws DAOException {
		logger.debug("Entering  getServiceClassById in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ServiceDTO serviceDTO = null;
		Connection connection = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.RETRIEVE_SERVICECLASS_DETAILS);
			prepareStatement.setString(1, circleCode);
			prepareStatement.setInt(2, serviceClassId);
			prepareStatement.setInt(3, Constants.Active);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				serviceDTO = new ServiceDTO();
				serviceDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
				serviceDTO.setServiceClassName(resultSet
						.getString("SERVICECLASS_NAME"));
				serviceDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				serviceDTO.setStatus(resultSet.getInt("STATUS"));
				if ((resultSet.getString("SPECIAL_SC_CHECK")).equals("Y")) {
					serviceDTO.setServiceClassCheck(true);
				} else {
					serviceDTO.setServiceClassCheck(false);
				}
				serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));
				serviceDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
				serviceDTO.setCreatedDate(USSDCommonUtility
						.getTimestampAsString(resultSet
								.getTimestamp("CREATED_DT"),
						"dd-MM-yyyy-hh-mm-ss"));
				serviceDTO.setModifiedBy(resultSet.getString("MODIFIED_BY"));
				serviceDTO.setModifiedDate(USSDCommonUtility
						.getTimestampAsString(resultSet
								.getTimestamp("MODIFIED_DT"),
						"dd-MM-yyyy-hh-mm-ss"));
				serviceDTO.setScCategoryName(resultSet
						.getString("CATEGORY_DESC"));
				serviceDTO.setTariffMessage(resultSet
						.getString("TARIFF_MESSAGE"));
				serviceDTO.setMedCmdFta(resultSet
						.getString("MED_CMD_FTA"));
				serviceDTO.setMedRechargeCmdFta(resultSet
						.getString("MED_RCB_FTA"));
			}

		} catch (SQLException sqe) {
			serviceDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			serviceDTO.setErrorMessage(String.valueOf(sqe.getMessage()));

			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			serviceDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			serviceDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}

		logger.debug("Exiting getServiceClassById in DAOImpl");
		return serviceDTO;
	}

	/**
	 * This method getCircleByCode() gives the details about circle.
	 * 
	 * @param circleCode :
	 *            parameter to fetch the details of the circle
	 * 
	 * @return an CircleDTO that contails all the details of the circle
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public CircleDTO getCircleByCode(String circleCode) throws DAOException {
		logger.debug("Entering  getCircleByCode in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		CircleDTO circleDto = null;
		Connection connection = null;

		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.RETRIEVE_CIRCLE_DETAILS);
			prepareStatement.setString(1, circleCode);
			prepareStatement.setInt(2, Constants.Active);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				circleDto = new CircleDTO();
				circleDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				circleDto.setCircleName(resultSet.getString("CIRCLE_NAME"));
				circleDto.setHubCode(resultSet.getString("HUB_CODE"));
				circleDto.setStatus(String.valueOf(resultSet.getInt("STATUS")));
				circleDto.setCreatedDt(USSDCommonUtility.getTimestampAsString(
						resultSet.getTimestamp("CREATED_DT"),
				"dd-MM-yyyy-hh-mm-ss"));
				circleDto.setCreatedBy(resultSet.getString("CREATED_BY"));
				circleDto.setUpdatedDt(USSDCommonUtility.getTimestampAsString(
						resultSet.getTimestamp("MODIFIED_DT"),
				"dd-MM-yyyy-hh-mm-ss"));
				circleDto.setUpdatedBy(resultSet.getString("MODIFIED_BY"));
				circleDto.setWelcomeMsg(resultSet.getString("WELCOME_MSG"));
				
				if ((resultSet.getString("MINSAT_CHECK_REQ")).equals("Y")) {
					circleDto.setMinsatCheck(true);
				} else {
					circleDto.setMinsatCheck(false);
				}
				
				if ((resultSet.getString("AEPF_CHECK_REQ")).equals("Y")) {
					circleDto.setApefCheck(true);
				} else {
					circleDto.setApefCheck(false);
				}
				
				if ((resultSet.getString("SIM_REQ_CHECK")).equals("Y")) {
					circleDto.setSimReqCheck(true);
				} else {
					circleDto.setSimReqCheck(false);
				}
				
				circleDto.setHubName(resultSet.getString("HUB_NAME"));
			}

		} catch (SQLException sqe) {
			circleDto.setErrorCode(String.valueOf(sqe.getErrorCode()));
			circleDto.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			circleDto.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			circleDto.setErrorMessage(String.valueOf(ex.getMessage()));
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}

		logger.debug("Exiting getCircleByCode in DAOImpl");
		return circleDto;
	}

	/**
	 * This method getLocationStatus() gives the details whether the particular
	 * location is active or inactive
	 * 
	 * @param locDataId :
	 *            parameter to fetch the details of the location
	 * 
	 * @return String that contails all the status of location
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public String getLocationStatus(int locDataId) throws DAOException {
		logger.debug("Entering  getLocationStatus in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String locStatus = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.RETRIEVE_LOCATION_STATUS);
			prepareStatement.setInt(1, locDataId);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getInt("STATUS") == 1) {
					locStatus = "Active";
				} else {
					locStatus = "InActive";
					return locStatus;
				}

			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getLocationStatus in DAOImpl");
		return locStatus;
	}

	/**
	 * This method getLatestLocationCount() gives the count of all locations
	 * which are created or modifird in particular range
	 * 
	 * @param modifiedFromDate :
	 *            parameter to fetch the locations from this date
	 * @param modifiedtoDate
	 *            parameter to fetch the locations to this date
	 * @return int that contails the count of all locations
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public int getLatestLocationCount(String modifiedFromDate,
			String modifiedtoDate) throws DAOException {
		logger.debug("Entering method getLatestLocationCount()");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyDate = new String(modifiedFromDate);
			java.util.Date dt = sdf.parse(newModifyDate);
			Timestamp modifiedFromDateTimestamp = new Timestamp(dt.getTime());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyToDate = new String(modifiedtoDate);
			java.util.Date dt1 = sdf1.parse(newModifyToDate);
			Timestamp modifiedtoDateTimestamp = new Timestamp(dt1.getTime());

			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.SQL_LOC_COUNT);
			prepareStatement.setTimestamp(1, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(2, modifiedtoDateTimestamp);
			prepareStatement.setTimestamp(3, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(4, modifiedtoDateTimestamp);
			ResultSet countLoc = prepareStatement.executeQuery();
			if (countLoc.next()) {
				noofRow = countLoc.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting method getLatestLocationCount()");
		return noofPages;
	}

	/**
	 * This method getLatestLocationDetails() gives of all locations which are
	 * created or modified in particular range
	 * 
	 * @param modifiedFromDate :
	 *            parameter to fetch the locations from this date
	 * @param modifiedtoDate
	 *            parameter to fetch the locations to this date
	 * @return LocationMstDTO that contains all locations
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public LocationMstDTO getLatestLocationDetails(String modifiedFromDate,
			String modifiedtoDate, int intLb, int intUb) throws DAOException {
		logger.debug("Entering  getLocationDTO in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		LocationDTO locationDto = null;
		LocationMstDTO locationMstDTO = new LocationMstDTO();
		LocationDTO[] locationDTO1 = new LocationDTO[10];
		Connection connection = null;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyDate = new String(modifiedFromDate);
			java.util.Date dt = sdf.parse(newModifyDate);
			Timestamp modifiedFromDateTimestamp = new Timestamp(dt.getTime());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyToDate = new String(modifiedtoDate);
			java.util.Date dt1 = sdf1.parse(newModifyToDate);
			Timestamp modifiedtoDateTimestamp = new Timestamp(dt1.getTime());

			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.RETRIEVE_LOCATION_DATA);
			prepareStatement.setTimestamp(1, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(2, modifiedtoDateTimestamp);
			prepareStatement.setTimestamp(3, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(4, modifiedtoDateTimestamp);
			prepareStatement.setString(5, String.valueOf(intUb));
			prepareStatement.setString(6, String.valueOf(intLb));
			resultSet = prepareStatement.executeQuery();
			int position = 0;
			while (resultSet.next()) {
				locationDto = new LocationDTO();
				locationDto.setLocDataId(String.valueOf(resultSet
						.getInt("LOC_DATA_ID")));
				locationDto.setLocationDataName(resultSet
						.getString("LOCATION_NAME"));
				locationDto.setStatus(resultSet.getInt("STATUS"));
				locationDto.setLocMstrId(resultSet.getInt("LOC_MSTR_ID"));
				locationDto.setParentId(resultSet.getString("PARENT_ID"));
				locationDto.setLocationName(resultSet.getString("LOCATION"));
				locationDto.setCreatedDt(USSDCommonUtility
						.getTimestampAsString(resultSet
								.getTimestamp("CREATED_DT"),
						"dd-MM-yyyy-HH-mm-ss"));
				locationDto.setUpdatedDt(USSDCommonUtility
						.getTimestampAsString(resultSet
								.getTimestamp("MODIFIED_DT"),
						"dd-MM-yyyy-HH-mm-ss"));

				// inserting in object array

				if (locationDto != null) {
					locationDTO1[position++] = locationDto;

				}
			}
			if (locationDto == null) {
				locationMstDTO = null;
			} else {
				locationMstDTO.setLocationArray(locationDTO1);
			}

		} catch (SQLException sqe) {
			locationMstDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			locationMstDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			locationMstDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			locationMstDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getLocationDTO in DAOImpl");
		return locationMstDTO;
	}

	/**
	 * This method getAllCircles() gives of all locations which are created or
	 * modified in particular range
	 * 
	 * @param modifiedFromDate :
	 *            parameter to fetch the locations from this date
	 * @param modifiedtoDate
	 *            parameter to fetch the locations to this date
	 * @return LocationMstDTO that contains all locations
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public LocationMstDTO getAllCircles(String modifiedFromDate,
			String modifiedtoDate) throws DAOException {
		logger.debug("Entering  getAllCircles in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		LocationDTO locationDto = null;
		LocationMstDTO locationMstDTO = new LocationMstDTO();
		LocationDTO[] locationDTO1 = null;
		Connection connection = null;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyDate = new String(modifiedFromDate);
			java.util.Date dt = sdf.parse(newModifyDate);
			Timestamp modifiedFromDateTimestamp = new Timestamp(dt.getTime());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyToDate = new String(modifiedtoDate);
			java.util.Date dt1 = sdf1.parse(newModifyToDate);
			Timestamp modifiedtoDateTimestamp = new Timestamp(dt1.getTime());

			int circleCount = 0;
			try {
				connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
				prepareStatement = connection
				.prepareStatement(ProdcatInterface.RETRIEVE_CIRCLE_DATA_COUNT);
				prepareStatement.setInt(1, Constants.Circle);
				prepareStatement.setTimestamp(2, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(3, modifiedtoDateTimestamp);
				prepareStatement.setTimestamp(4, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(5, modifiedtoDateTimestamp);
				resultSet = prepareStatement.executeQuery();

				if (resultSet.next()) {
					circleCount = resultSet.getInt("CIRCLE_COUNT");
				}
			} finally {
				DBConnectionUtil.closeDBResources(null, prepareStatement,
						resultSet);
				resultSet = null;
				prepareStatement = null;
				locationMstDTO = null;
			}

			if (circleCount != 0) {
				locationMstDTO = new LocationMstDTO();
				locationDTO1 = new LocationDTO[circleCount];
				prepareStatement = connection
				.prepareStatement(ProdcatInterface.RETRIEVE_CIRCLE_DATA);
				prepareStatement.setInt(1, Constants.Circle);
				prepareStatement.setTimestamp(2, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(3, modifiedtoDateTimestamp);
				prepareStatement.setTimestamp(4, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(5, modifiedtoDateTimestamp);
				resultSet = prepareStatement.executeQuery();
				int position = 0;
				while (resultSet.next()) {
					locationDto = new LocationDTO();
					locationDto
					.setLocDataId(resultSet.getString("CIRCLE_CODE"));
					locationDto.setLocationDataName(resultSet
							.getString("CIRCLE_NAME"));
					locationDto.setCreatedDt(USSDCommonUtility
							.getTimestampAsString(resultSet
									.getTimestamp("CREATED_DT"),
							"dd-MM-yyyy-HH-mm-ss"));
					locationDto.setUpdatedDt(USSDCommonUtility
							.getTimestampAsString(resultSet
									.getTimestamp("MODIFIED_DT"),
							"dd-MM-yyyy-HH-mm-ss"));
					locationDto.setStatus(resultSet.getInt("STATUS"));
					locationDto.setLocMstrId(resultSet.getInt("LOC_MSTR_ID"));

					locationDto.setParentId(resultSet.getString("PARENT_ID"));
					locationDto
					.setLocationName(resultSet.getString("LOCATION"));

					// inserting in object array

					if (locationDto != null) {
						locationDTO1[position++] = locationDto;
					}
				}
				if (locationDto == null) {
					locationMstDTO = null;
				} else {
					locationMstDTO.setLocationArray(locationDTO1);
				}
			}
		} catch (SQLException sqe) {
			locationMstDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			locationMstDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			locationMstDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			locationMstDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getAllCircles in DAOImpl");
		return locationMstDTO;
	}

	/**
	 * This method getAllHubs() gives of all locations which are created or
	 * modified in particular range
	 * 
	 * @param modifiedFromDate :
	 *            parameter to fetch the locations from this date
	 * @param modifiedtoDate
	 *            parameter to fetch the locations to this date
	 * @return LocationMstDTO that contains all locations
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public LocationMstDTO getAllHubs(String modifiedFromDate,
			String modifiedtoDate) throws DAOException {
		logger.debug("Entering  getAllHubs in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		LocationDTO locationDto = null;
		LocationMstDTO locationMstDTO = new LocationMstDTO();
		LocationDTO[] locationDTO1 = null;
		Connection connection = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyDate = new String(modifiedFromDate);
			java.util.Date dt = sdf.parse(newModifyDate);
			Timestamp modifiedFromDateTimestamp = new Timestamp(dt.getTime());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyToDate = new String(modifiedtoDate);
			java.util.Date dt1 = sdf1.parse(newModifyToDate);
			Timestamp modifiedtoDateTimestamp = new Timestamp(dt1.getTime());
			int hubCount = 0;
			try {
				connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
				prepareStatement = connection
				.prepareStatement(ProdcatInterface.RETRIEVE_HUB_DATA_COUNT);
				prepareStatement.setInt(1, Constants.Hub);
				prepareStatement.setTimestamp(2, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(3, modifiedtoDateTimestamp);
				prepareStatement.setTimestamp(4, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(5, modifiedtoDateTimestamp);
				resultSet = prepareStatement.executeQuery();

				if (resultSet.next()) {
					hubCount = resultSet.getInt("HUB_COUNT");
				}
			} finally {
				DBConnectionUtil.closeDBResources(null, prepareStatement,
						resultSet);
				resultSet = null;
				prepareStatement = null;
				locationMstDTO = null;
			}

			if (hubCount != 0) {
				locationMstDTO = new LocationMstDTO();

				locationDTO1 = new LocationDTO[hubCount];
				connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
				prepareStatement = connection
				.prepareStatement(ProdcatInterface.RETRIEVE_HUB_DATA);
				prepareStatement.setInt(1, Constants.Hub);
				prepareStatement.setTimestamp(2, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(3, modifiedtoDateTimestamp);
				prepareStatement.setTimestamp(4, modifiedFromDateTimestamp);
				prepareStatement.setTimestamp(5, modifiedtoDateTimestamp);
				resultSet = prepareStatement.executeQuery();
				int position = 0;
				while (resultSet.next()) {
					locationDto = new LocationDTO();
					locationDto.setLocDataId(resultSet.getString("HUB_CODE"));
					locationDto.setLocationDataName(resultSet
							.getString("HUB_NAME"));
					locationDto.setCreatedDt(USSDCommonUtility
							.getTimestampAsString(resultSet
									.getTimestamp("CREATED_DT"),
							"dd-MM-yyyy-HH-mm-ss"));
					locationDto.setUpdatedDt(USSDCommonUtility
							.getTimestampAsString(resultSet
									.getTimestamp("MODIFIED_DT"),
							"dd-MM-yyyy-HH-mm-ss"));
					locationDto.setStatus(resultSet.getInt("STATUS"));
					locationDto.setLocMstrId(resultSet.getInt("LOC_MSTR_ID"));
					locationDto.setParentId(null); // No parent Id for Hub
					locationDto
					.setLocationName(resultSet.getString("LOCATION"));

					// inserting in object array
					if (locationDto != null) {
						locationDTO1[position++] = locationDto;
					}
				}
				if (locationDto == null) {
					locationMstDTO = null;
				} else {
					locationMstDTO.setLocationArray(locationDTO1);
				}
			}
		} catch (SQLException sqe) {
			locationMstDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			locationMstDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			locationMstDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			locationMstDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getAllHubs in DAOImpl");
		return locationMstDTO;
	}

	/**
	 * This method getActiveCircle() gives the details about circle.
	 * 
	 * @param circleCode :
	 *            parameter to fetch the details of the circle
	 * 
	 * @return an String that contails all the information about the circle
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public String getActiveCircle(String circleCode) throws DAOException {
		logger.debug("Entering  getActiveCircle in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String activeCircle = "";
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.RETRIEVE_CIRCLE);
			prepareStatement.setInt(1, Constants.Active);
			prepareStatement.setString(2, circleCode);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				activeCircle = resultSet.getString("CIRCLE_CODE");
			}

		} 
		
		
		
		
		catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getActiveCircle in DAOImpl");
		return activeCircle;
	}
	
	public LocationMstDTO getAllActiveCircles()throws DAOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		LocationDTO locationDto = null;
		LocationMstDTO locationMstDTO = new LocationMstDTO();
		LocationDTO[] locationDTO1 = null;
		Connection connection = null;
		try{
			int circleCount = 0;
			try {
				connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
				prepareStatement = connection
				.prepareStatement(ProdcatInterface.RETRIEVE_ACTIVE_CIRCLE_COUNT);
				prepareStatement.setInt(1, Constants.Active);
				resultSet = prepareStatement.executeQuery();
				if(resultSet.next()){
					circleCount = resultSet.getInt(1);
				}
			}finally {
					DBConnectionUtil.closeDBResources(null, prepareStatement,
							resultSet);
					resultSet = null;
					prepareStatement = null;
					locationMstDTO = null;
				}
			if (circleCount != 0) {
				locationMstDTO = new LocationMstDTO();
	
				locationDTO1 = new LocationDTO[circleCount];
				connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
				prepareStatement = connection
				.prepareStatement(ProdcatInterface.RETRIEVE_ACTIVE_CIRCLE);
				prepareStatement.setInt(1, Constants.Active);
				resultSet = prepareStatement.executeQuery();
				int position = 0;
				while (resultSet.next()) {
					locationDto = new LocationDTO();
					locationDto
					.setLocDataId(resultSet.getString("CIRCLE_CODE"));
					locationDto.setLocationDataName(resultSet
							.getString("CIRCLE_NAME"));
					locationDto.setReleaseTime(resultSet
							.getString("RELEASE_TIME_IN_HRS"));
					
					//inserting in object array

					if (locationDto != null) {
						locationDTO1[position++] = locationDto;
					}
				}
				if (locationDto == null) {
					locationMstDTO = null;
				} else {
					locationMstDTO.setLocationArray(locationDTO1);
				}
						
			}
			
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getActiveCircle in DAOImpl");
		return locationMstDTO;
		
	}
	
	
	public String getLocationName(int locationId,String baseLoc)throws DAOException {
		String locationName="";
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(ProdcatInterface.RETRIEVE_LOCATION_NAME);
			prepareStatement.setInt(1, locationId);
			prepareStatement.setString(2, baseLoc);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()){
				locationName = resultSet.getString("LOCATION_NAME");
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getActiveCircle in DAOImpl");
		return locationName;
	}

}
