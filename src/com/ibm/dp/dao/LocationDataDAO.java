/**
 * 
 */
package com.ibm.dp.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.validator.Constant;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.CircleMasterInterface;

import com.ibm.dp.dto.LocationDataDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.utilities.PaginationUtils;
import com.ibm.dp.common.USSDConstants;
//import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.dp.common.USSDErrorCodes;
import com.ibm.dp.common.SQLConstants;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;


/*********************************************************************************
* This class fetch the values or update the value from database
* 
* 
* @author Pragati
* @version 1.0
********************************************************************************************/
public class LocationDataDAO implements CircleMasterInterface {

	private static Logger logger = Logger.getLogger(LocationDataDAO.class);


	/**	This method is used to get Location master details @ throws DAOException
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getLocationMstrList( String locType) throws DAOException {
		logger.debug("Entering  getLocationMstrList in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList locationMstrList = new ArrayList();
		LocationDataDTO locDTO = null;
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			String query= CircleMasterInterface.RETRIEVE_LOC_MSTR_DETAILS;
				if(locType.equalsIgnoreCase(USSDConstants.zoneLogin)){
					query += " AND PARENT_ID = ? ";
				}
					
			prepareStatement = connection
					.prepareStatement(query);
			prepareStatement.setInt(1, USSDConstants.Active);
			if(locType.equalsIgnoreCase(USSDConstants.zoneLogin)){
				prepareStatement.setString(2, String.valueOf(USSDConstants.CityParentId));
			}
			
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				locDTO = new LocationDataDTO();
				locDTO.setLocMstrId(resultSet.getInt("LOC_MSTR_ID"));
				locDTO.setLocationName(resultSet.getString("LOCATION"));
				locationMstrList.add(locDTO);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger.error("Exception occured when trying to close "
						+ "database resources");
			}
			logger.info("Exiting getLocationMstrList in DAOImpl");
		}
		return locationMstrList;
	}


	/**	This method is used to get location details @ throws DAOException
	 * @param parentId
	 * @param locMstrId
	 * @param pageFlag
	 * @param intLb
	 * @param intUb
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getLocationList(String parentId, int locMstrId,
			int pageFlag, int intLb, int intUb) throws DAOException {
		logger.debug("Entering  getLocationList in locationDataDao");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList locationMstrList = new ArrayList();
		LocationDataDTO locDTO = null;
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			if (pageFlag == USSDConstants.PAGE_FALSE) {
				if(locMstrId==USSDConstants.Zone){
					prepareStatement = connection
							.prepareStatement(CircleMasterInterface.RETRIEVE_LOCATION_LIST_ACTIVE_ZONE);
					//prepareStatement.setInt(1, locMstrId);
					prepareStatement.setString(1, parentId);
					prepareStatement.setString(2, USSDConstants.ActiveFlag);
				}
				else if(locMstrId==USSDConstants.City){
					prepareStatement = connection
					.prepareStatement(CircleMasterInterface.RETRIEVE_LOCATION_LIST_ACTIVE_CITY);
			//prepareStatement.setInt(1, locMstrId);
			prepareStatement.setString(1, parentId);
			prepareStatement.setString(2, USSDConstants.ActiveFlag);
		}
			}	
			else {
				if(locMstrId==USSDConstants.Zone){
					prepareStatement = connection
					.prepareStatement(CircleMasterInterface.RETRIEVE_LOCATION_LIST_PAGE_ZONE);
					//prepareStatement.setInt(1, locMstrId);
					prepareStatement.setString(1, parentId);
					prepareStatement.setString(2, String.valueOf(intUb));
					prepareStatement.setString(3, String.valueOf(intLb + 1));

				}
				else{
					prepareStatement = connection
							.prepareStatement(CircleMasterInterface.RETRIEVE_LOCATION_LIST_PAGE_CITY);
					//prepareStatement.setInt(1, locMstrId);
					prepareStatement.setString(1, parentId);
					prepareStatement.setString(2, String.valueOf(intUb));
					prepareStatement.setString(3, String.valueOf(intLb + 1));
				}

			}

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				locDTO = new LocationDataDTO();
				locDTO.setLocDataId(resultSet.getInt("LOC_DATA_ID"));
				locDTO
						.setLocationDataName(resultSet
								.getString("LOCATION_NAME"));
				String tempStatus = ((resultSet.getString("STATUS"))).trim().equalsIgnoreCase("A") ? "Active"
						: "InActive";
				locDTO.setStatus(tempStatus);

				locDTO.setCreatedBy(resultSet.getString("CREATED_BY"));

				if (resultSet.getTimestamp("CREATED_DT") != null) {
					Date createdDt = new Date(resultSet.getTimestamp(
							"CREATED_DT").getTime());
					String createdDt1 = Utility.getDateAsString(createdDt,
							"dd-MM-yyyy");
					locDTO.setCreatedDate(createdDt1);
				}
				locDTO.setModifiedBy(resultSet.getString("MODIFIED_BY"));
				locDTO.setModifiedDate(resultSet.getTimestamp("MODIFIED_DT"));
				if (pageFlag == USSDConstants.PAGE_TRUE) {
					locDTO.setRowNumber(resultSet.getString("RNUM"));
				}
				locationMstrList.add(locDTO);

			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger
						.error("Exception occured when trying to close database resources");
			}
			logger.info("Exiting getLocationList in DAOImpl");
		}
		return locationMstrList;
	}


	/**	This method is used to count location details @ throws DAOException
	 * @param parentId
	 * @param locMstrId
	 * @return
	 * @throws DAOException
	 */
	public int countLocationList(String parentId, int locMstrId)
			throws DAOException {
		logger.debug("Entering  getLocationList in locationdataDao");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int noofRow = 0;
		int noofPages = 0;
		try {
			connection = DBConnectionManager.getDBConnection();
			if(locMstrId==USSDConstants.Zone){
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.COUNT_LOCATION_LIST_ZONE);
				//prepareStatement.setInt(1, locMstrId);
				prepareStatement.setString(1, parentId);
			}
			else{
				prepareStatement = connection
				.prepareStatement(CircleMasterInterface.COUNT_LOCATION_LIST_CITY);
				//prepareStatement.setInt(1, locMstrId);
				prepareStatement.setString(1, parentId);
			}
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				noofRow = resultSet.getInt(1);
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
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger
						.error("Exception occured when trying to close database resources");
			}
			logger.info("Exiting getLocationList in DAOImpl");
		}
		return noofPages;
	}

	/**
	 * This method is used for Creation of Location data using LocationDataDTO
	 * locDTO method.
	 * 
	 * @param locDTO
	 *            object containing data for creating location
	 * @throws DAOException
	 */

	public int createLocation(LocationDataDTO locDTO) throws DAOException {
		logger.debug("Entering  createLocation in DAOImpl");
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int locId = 0;
		int i = 1;
		try {
			connection = DBConnectionManager.getDBConnection();

			pstmt = connection
					.prepareStatement(CircleMasterInterface.GET_NEXT_LOC_DATA_ID);

			resultSet = pstmt.executeQuery();

			if (resultSet.next()) {
				locId = resultSet.getInt(1);
			}
			String createLocation = CircleMasterInterface.CREATE_LOC_DATA;

			pstmt = connection.prepareStatement(createLocation);
			pstmt.setInt(i++, locId);
			pstmt.setString(i++, locDTO.getLocationName());
			pstmt.setString(i++, locDTO.getStatus());
			pstmt.setInt(i++, locDTO.getLocMstrId());
			pstmt.setString(i++, locDTO.getParentId());
			pstmt.setString(i++, locDTO.getCreatedBy());
			pstmt.setTimestamp(i++, locDTO.getModifiedDate());
			pstmt.setString(i++, locDTO.getModifiedBy());
			pstmt.setString(i++, locDTO.getLocationCode());

			pstmt.executeUpdate();
			logger.debug("before insert locationdata query exec.........");
		}

		catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			if (sqe != null
					&& sqe.getErrorCode() == Integer
							.parseInt(USSDErrorCodes.Location.SQLDUPEXCEPTION))

			{
				throw new DAOException(String.valueOf(sqe.getErrorCode()), sqe);
			} else {
				throw new DAOException(sqe.getMessage(), sqe);
			}
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionManager.releaseResources(pstmt, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger
						.error("Exception occured when trying to close database resources");
			}
			logger.info("Exiting createLocation in DAOImpl");
		}
		return locId;
	}

	/**
	 * This method fetch location details based on locDataId
	 * 
	 * @param locDataId
	 * @return
	 * @throws DAOException
	 */
	public LocationDataDTO getLocationDetails(int locDataId,int locMstrId)
			throws DAOException {
		logger.debug("Entering  getLocationList in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		LocationDataDTO locDTO = null;
		Connection connection = null;
		try {
			if(locMstrId==USSDConstants.Zone){
			connection = DBConnectionManager.getDBConnection();
			prepareStatement = connection
					.prepareStatement(CircleMasterInterface.RETRIEVE_LOCATION_DETAILS_ZONE);
			prepareStatement.setInt(1, locDataId);
			}
			else if(locMstrId==USSDConstants.City){
				connection = DBConnectionManager.getDBConnection();
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.RETRIEVE_LOCATION_DETAILS_CITY);
				prepareStatement.setInt(1, locDataId);
			}
				
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				locDTO = new LocationDataDTO();
				locDTO.setLocationName(resultSet.getString("LOCATION_NAME"));
				locDTO.setParentId(resultSet.getString("PARENT_ID"));
				String tempStatus = (resultSet.getString("STATUS")).trim().equalsIgnoreCase("A") ? "Active"
						: "InActive";
				locDTO.setStatus(tempStatus);

				locDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
				if (resultSet.getTimestamp("CREATED_DT") != null) {
					Date createdDt = new Date(resultSet.getTimestamp(
							"CREATED_DT").getTime());
					String createdDt1 = Utility.getDateAsString(createdDt,
							"dd-MM-yyyy");
					locDTO.setCreatedDate(createdDt1);
				}
				locDTO.setModifiedBy(resultSet.getString("MODIFIED_BY"));
				locDTO.setModifiedDate(resultSet.getTimestamp("MODIFIED_DT"));
				locDTO.setLocMstrId(resultSet.getInt("LOC_MSTR_ID"));

			}
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
			logger.info("Exiting getLocationList in DAOImpl");
		}
		return locDTO;
	}

	/**
	 * This method updateLocationDetails() updates location details
	 * 
	 * @param locDto
	 * @param locDataId
	 * @throws DAOException
	 */
	public int updateLocationDetails(LocationDataDTO locDto,int locMstrId)
			throws DAOException {
		logger.debug("Entering  updateLocationDetails in DAOImpl");
		int recUpdate = 0;
		PreparedStatement pstmt = null;
		String status="";
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			if(locMstrId==USSDConstants.Zone){
			pstmt = connection
					.prepareStatement(CircleMasterInterface.UPDATE_LOCATION_DETAILS_ZONE);

			pstmt.setString(1, locDto.getLocationName());
			String tempStatus = locDto.getStatus();
			
			if (tempStatus.equalsIgnoreCase(USSDConstants.ACTIVE)) {
				status = USSDConstants.ActiveFlag;
			} else {
				status = USSDConstants.InActiveFlag;
			}
			}
			if(locMstrId==USSDConstants.City){
			pstmt = connection
					.prepareStatement(CircleMasterInterface.UPDATE_LOCATION_DETAILS_CITY);

			pstmt.setString(1, locDto.getLocationName());
			String tempStatus = locDto.getStatus();
			if (tempStatus.equalsIgnoreCase(USSDConstants.ACTIVE)) {
				status = USSDConstants.ActiveFlag;
			} else {
				status = USSDConstants.InActiveFlag;
			}
			}
			pstmt.setString(2, status);
			pstmt.setString(3, locDto.getModifiedBy());
			pstmt.setInt(4, locDto.getLocDataId());
			recUpdate = pstmt.executeUpdate();
		}

		catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			if (sqe != null
					&& sqe.getErrorCode() == Integer
							.parseInt(USSDErrorCodes.ServiceClass.SQLDUPEXCEPTION))

			{
				throw new DAOException(String.valueOf(sqe.getErrorCode()), sqe);
			} else {
				throw new DAOException(sqe.getMessage(), sqe);
			}

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				connection.commit();
				DBConnectionManager.releaseResources(pstmt, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
		}
		logger.info("Exiting updateLocationDetails in DAOImpl");
		return recUpdate;
	}

	/**
	 * This method validateLocationName() check whether location name exit for
	 * same circle
	 * 
	 * @param locDto
	 * @return
	 * @throws DAOException
	 */
	public int validateLocationName(LocationDataDTO locDto) throws DAOException {
		logger.debug("Entering  validateLocationName in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int rowCount = 0;
		try {
			connection = DBConnectionManager.getDBConnection();
			if (locDto.getLocDataId() != 0) {
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.VALIDATE_LOCATION_NAME);
			} else {
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.VALIDATE_LOCATION_NAME_PARENT_ID);
			}

			String locationName =  locDto.getLocationName().toLowerCase();
			
			prepareStatement.setString(1, locationName);
			if (locDto.getLocDataId() != 0) {
				prepareStatement.setInt(2, locDto.getLocDataId());
			} else {
				prepareStatement.setString(2, locDto.getParentId());
			}

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				rowCount++;
			}

			
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger.debug("Exception occured when trying to close database "
						+ "resources");
			}
			logger.info("Exiting validateLocationName in DAOImpl");
		}
		return rowCount;
	}

	/**	This method getHierarchyLocDataId() returns the child locdataId for given locDataId
	 * @param locDataId
	 * @param status
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getHierarchyLocDataId(int locDataId, String status,int locMstrId)
			throws DAOException {
		logger.debug("Entering  getHierarchyLocDataId(int locDataId, String status)");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		LocationDataDTO locDTO = new LocationDataDTO();
		ArrayList locDataList = new ArrayList();
		try {
			if(locMstrId==USSDConstants.Zone){
				connection = DBConnectionManager.getDBConnection();
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.GET_LOCATION_HIERARCHIAL_ZONE);
				prepareStatement.setInt(1, locDataId);
				prepareStatement.setString(2, USSDConstants.ActiveFlag);
			}
			if(locMstrId==USSDConstants.City){
				connection = DBConnectionManager.getDBConnection();
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.GET_LOCATION_HIERARCHIAL_CITY);
				prepareStatement.setInt(1, locDataId);
				prepareStatement.setString(2, USSDConstants.ActiveFlag);
			}	

			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				locDTO = new LocationDataDTO();
				locDTO.setLocDataId(resultSet.getInt("LOC_DATA_ID"));
				locDTO.setLocationDataName(resultSet.getString("LOCATION_NAME"));
				locDataList.add(locDTO);
			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger.error("Exception occured when trying to close database resources");
			}
		}
		logger.info("Returning list of child locDataId");
		logger.debug("Exiting getHierarchyLocDataId(int locDataId, String status)");
		return locDataList;
	}

	/**	This method updateStatusHierarchy() update the status of location based on
	 *  locDataID 
	 * @param locDataList
	 * @param status
	 * @return 
	 * @throws DAOException
	 */
	public int updateStatusHierarchy(ArrayList locDataList, String status)
			throws DAOException {
		logger.debug("Entering  updateStatusHierarchy(ArrayList locDataList, String status)" +
				" in LOcationDataDAO");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Iterator iterator;
		int locDataId = 0;
		int[] numRows;
		boolean connCommitStatus = false;
		try {
			connection = DBConnectionManager.getDBConnection();
			connCommitStatus=connection.getAutoCommit();
			connection.setAutoCommit(false);
			prepareStatement = connection
					.prepareStatement(CircleMasterInterface.UPDATE_LOCATION_STATUS);

			iterator = locDataList.iterator();

			while (iterator.hasNext()) {
				LocationDataDTO locDataDto = (LocationDataDTO) iterator.next();
				locDataId = locDataDto.getLocDataId();
				logger.info("Updating locDataId no :: " + locDataId );
				if (locDataId != 0) {
					if (status.equalsIgnoreCase(USSDConstants.ACTIVE)) {
						prepareStatement.setInt(1, USSDConstants.Active);
					} else {
						prepareStatement.setInt(1, USSDConstants.InAcive);
					}
					prepareStatement.setInt(2, locDataId);
					prepareStatement.addBatch();
				}
			}
			
			/** excecuting Batch**/
		 numRows = prepareStatement.executeBatch();
		 connection.commit();
		logger.debug("Execution successful: " + numRows.length
				+ " rows updated");
		} catch (SQLException sqlEx) {
			
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			byte counter = 1;
			SQLException sqlExNext = sqlEx.getNextException();

			while (sqlExNext != null && counter <= 4) {
				logger.error(sqlExNext.getMessage(), sqlExNext);
				sqlExNext = sqlEx.getNextException();
				counter++;
			}
			try{
				connection.rollback();
				connection.setAutoCommit(connCommitStatus);
				return 0;
			}catch (Exception ex) {
				logger.error("Error" + sqlEx.getMessage(), ex);
			}
			
			throw new DAOException(sqlExNext.getMessage(), sqlExNext);
				
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger.error("Exception occured when trying to close database resources");
			}
		}

		logger.debug("Exiting updateStatusHierarchy(ArrayList locDataList, String status) from locationDataDao");
		return numRows.length;
	}
	
	/**	This method updateParentOfChildInHierarchy() update the parent id of location based on
	 *  locDataID 
	 * @param locDataList
	 * @param status
	 * @return 
	 * @throws DAOException
	 */
	public int updateParentIdOfChildHierarchy(int moveToLocation, int parentid,int locMstrId)
			throws DAOException{
		logger.debug("Entering  updateParentIdOfChildHierarchy(ArrayList locDataList, int parentid))" +
				" in LOcationDataDAO");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Iterator iterator;
		int locDataId = 0;
		int numRows;
		boolean connCommitStatus = false;
		try {
			if(locMstrId==USSDConstants.Zone){
				connection = DBConnectionManager.getDBConnection();
				connCommitStatus=connection.getAutoCommit();
				connection.setAutoCommit(false);
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.UPDATE_PARENT_ID_ZONE);
				prepareStatement.setInt(1, moveToLocation );
				prepareStatement.setInt(2, parentid);
			}
			else if(locMstrId==USSDConstants.City){
				connection = DBConnectionManager.getDBConnection();
				connCommitStatus=connection.getAutoCommit();
				connection.setAutoCommit(false);
				prepareStatement = connection
						.prepareStatement(CircleMasterInterface.UPDATE_PARENT_ID_CITY);
				prepareStatement.setInt(1, parentid);
				prepareStatement.setInt(2, moveToLocation);
			}
			numRows = prepareStatement.executeUpdate();
			//iterator = locDataList.iterator();

//			while (iterator.hasNext()) {
//				LocationDataDTO locDataDto = (LocationDataDTO) iterator.next();
//				locDataId = locDataDto.getLocDataId();
//				logger.info("Updating locDataId no :: " + locDataId );
//				if (locDataId != 0) {
//					prepareStatement.setInt(1, parentid);
//					prepareStatement.setInt(2, locDataId);
//					prepareStatement.addBatch();
//				}
//			}
			
			
		// numRows = prepareStatement.executeBatch();
		 connection.commit();
		logger.debug("Execution successful: " + numRows
				+ " rows updated");
		} catch (SQLException sqlEx) {
			
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			byte counter = 1;
			SQLException sqlExNext = sqlEx.getNextException();

			while (sqlExNext != null && counter <= 4) {
				logger.error(sqlExNext.getMessage(), sqlExNext);
				sqlExNext = sqlEx.getNextException();
				counter++;
			}
			try{
				connection.rollback();
				connection.setAutoCommit(connCommitStatus);
				return 0;
			}catch (Exception ex) {
				logger.error("Error" + sqlEx.getMessage(), ex);
			}
			
			throw new DAOException(sqlExNext.getMessage(), sqlExNext);
				
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, null);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
				logger.error("Exception occured when trying to close database resources");
			}
		}

		logger.debug("Exiting updateParentIdOfChildHierarchy(ArrayList locDataList, int parentid) from locationDataDao");
		return numRows;
	}
}
