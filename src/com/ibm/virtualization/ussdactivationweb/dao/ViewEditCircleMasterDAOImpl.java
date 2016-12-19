/**************************************************************************
 * File     : ViewEditCircleMasterDAOImpl.java
 * Author   : Banita
 * Created  : Sep 8, 2008
 * Modified : Sep 8, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.CircleMasterInterface;
import com.ibm.virtualization.ussdactivationweb.dto.CircleMasterDTO;
import com.ibm.virtualization.ussdactivationweb.dto.LocationDataDTO;
import com.ibm.virtualization.ussdactivationweb.exception.VCircleMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class handles the creation,update and search for a circle and makes the
 * connection with database.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/

public class ViewEditCircleMasterDAOImpl implements CircleMasterInterface {
	private static final Logger logger = Logger
	.getLogger(ViewEditCircleMasterDAOImpl.class.toString());

	/**
	 * This method is called to count the circles.
	 * 
	 * @return object of int:total number of circles.
	 * 
	 * @throws VCircleMstrDaoException
	 */
	public int countCircle() throws VCircleMstrDaoException {
		logger
		.debug("Entering method countCircle() throws VCircleMstrDaoException");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;

		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(CircleMasterInterface.SQL_COUNT_CIRCLE);
			ResultSet countCircle = prepareStatement.executeQuery();
			if (countCircle.next()) {
				noofRow = countCircle.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countCircle() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger
		.debug("Exiting method countCircle() throws VCircleMstrDaoException");
		return noofPages;
	}

	/**
	 * This method fetches the data when circle is searched on the basis of
	 * name.
	 * 
	 * @param strCircleName
	 *            :finds the circle by the name String
	 * @return object of String:whether the name is in the database or not..
	 * 
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public String findByCircleName(String strCircleName)
	throws VCircleMstrDaoException {
		logger
		.debug("Entering method findByCircleName(String strCircleName) throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String CircleName = null;
		try {
			String sql = SQL_SELECT_CIRCLE
			+ " WHERE upper(CIRCLE_NAME) = ? WITH UR";
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, strCircleName.toUpperCase());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				CircleName = strCircleName;
			}
			return CircleName;
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method findByCircleName() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
			logger
			.debug("Exiting method findByCircleName(String strCircleName) throws VCircleMstrDaoException");
		}

	}

	/**
	 * This method is called when a new circle is created.
	 * 
	 * @param dto :
	 *            CircleMasterDTO that contains all information of all
	 *            circles...
	 * @return object of int:whether record is nserted properly
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public int insert(CircleMasterDTO dto) throws VCircleMstrDaoException {
		logger
		.debug("Entering method insert(CircleMasterDTO dto) throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int rowsUpdated = 0;
		try {
			String sql = SQL_INSERT_CIRCLE;
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			connection.setAutoCommit(false);
			prepareStatement = connection.prepareStatement(sql);
			int paramCount = 1;
			prepareStatement.setString(paramCount++, dto.getCircleCode());
			prepareStatement.setString(paramCount++, dto.getCircleName());
			prepareStatement.setString(paramCount++, dto.getHubCode());
			prepareStatement.setInt(paramCount++, Constants.Active);
			prepareStatement.setString(paramCount++, dto.getCreatedBy());
			prepareStatement.setString(paramCount++, dto.getUpdatedBy());
			prepareStatement.setString(paramCount++, dto.getMinsatCheck());
			prepareStatement.setString(paramCount++, dto.getApefCheck());
			prepareStatement.setString(paramCount++, dto.getWelcomeMsg());
			prepareStatement.setString(paramCount++, dto.getSimReq());
			prepareStatement.setString(paramCount++, dto.getReleaseTime());
			rowsUpdated = prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method insert() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger
		.debug("Exiting method insert(CircleMasterDTO dto) throws VCircleMstrDaoException");
		return rowsUpdated;
	}

	/**
	 * This method is to search circle by primary key.
	 * 
	 * @param circleCode
	 *            :on the basis of which the circle is found String
	 * @return object of CircleMasterDTO:that contains all the information of
	 *         circle
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public CircleMasterDTO findByPrimaryKey(String circleCode)
	throws VCircleMstrDaoException {
		logger
		.debug("Entering method findByPrimaryKey(String circleCode) throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = SQL_SELECT_CIRCLE
			+ " WHERE cm.CIRCLE_CODE = ? AND cm.HUB_CODE = hm.HUB_CODE WITH UR";
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, circleCode);
			resultSet = prepareStatement.executeQuery();
			return fetchSingleResult(resultSet);
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method findByPrimaryKey() of class ViewEditCircleMasterDAO.",
						e);
			}
			logger
			.debug("Exiting method findByPrimaryKey(String circleCode) throws VCircleMstrDaoException");
		}

	}

	/**
	 * This method is called to update circle details.
	 * 
	 * @param dto
	 *            :this contains all the information of circle CircleMasterDTO
	 * @param flag :
	 *            boolean
	 * @return object of int:whether record is updated successfully
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public int update(CircleMasterDTO dto, boolean bFlag)
	throws VCircleMstrDaoException {
		logger
		.debug("Entering method update(CircleMasterDTO dto, boolean bFlag) throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement1 = null;
		ResultSet circleResult = null;
		int numRows = -1;
		boolean fail = true;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(SQL_CIRCLE_UPDATE_WITH_ID);
			if (bFlag) {
				prepareStatement1 = connection
				.prepareStatement(SQL_CIRCLE_CHECK);
				prepareStatement1.setString(1, dto.getCircleName()
						.toUpperCase());
				circleResult = prepareStatement1.executeQuery();
				if (circleResult.next()) {
					if (circleResult.getString("CIRCLE_NAME").equalsIgnoreCase(
							dto.getCircleName())) {
						fail = false;
					}
				}
				if (fail) {
					prepareStatement.setString(1, dto.getCircleName());
					prepareStatement.setString(2, dto.getUpdatedBy());
					prepareStatement.setInt(3, Integer
							.parseInt(dto.getStatus()));
					prepareStatement.setString(4, dto.getMinsatCheck());
					prepareStatement.setString(5, dto.getApefCheck());
					prepareStatement.setString(6, dto.getWelcomeMsg());
					prepareStatement.setString(7, dto.getSimReq());
					prepareStatement.setString(8, dto.getReleaseTime());
					prepareStatement.setString(9, dto.getCircleCode());

					numRows = prepareStatement.executeUpdate();
					logger
					.debug("Update successful on table:PRODCAT.PC_CIRCLE_MSTR_NICK. Updated:"
							+ numRows + " rows");
				} else {
					numRows = 0;
				}
			} else {
				prepareStatement = connection
				.prepareStatement(SQL_CIRCLE_UPDATE_WITH_ID);
				prepareStatement.setString(1, dto.getCircleName());
				prepareStatement.setString(2, dto.getUpdatedBy());
				prepareStatement.setInt(3, Integer.parseInt(dto.getStatus()));
				prepareStatement.setString(4, dto.getMinsatCheck());
				prepareStatement.setString(5, dto.getApefCheck());
				prepareStatement.setString(6, dto.getWelcomeMsg());
				prepareStatement.setString(7, dto.getSimReq());
				prepareStatement.setString(8, dto.getReleaseTime());
				prepareStatement.setString(9, dto.getCircleCode());
				numRows = prepareStatement.executeUpdate();
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						circleResult);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method update() of class ViewEditCircleMasterDAO.",
						e);
			}
		}
		logger
		.debug("Exiting method update(CircleMasterDTO dto, boolean bFlag) throws VCircleMstrDaoException");
		return numRows;
	}

	/**
	 * This method is called to delete circle details.
	 * 
	 * @param strCircleId
	 *            :which circle is to be deleted String
	 * @return object of int:whether record is properly deleted
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public int delete(String strCircleId) throws VCircleMstrDaoException {
		logger.debug("Entered delete() for table PRODCAT.PC_CIRCLE_MSTR_NICK");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int numRows = -1;
		try {
			String sql = SQL_DELETE_CIRCLE;
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, strCircleId);
			numRows = prepareStatement.executeUpdate();
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method delete() of class ViewEditCircleMasterDAO.",
						e);
			}
		}
		logger
		.debug("Exiting method delete(String strCircleId) throws VCircleMstrDaoException");
		return numRows;
	}

	/**
	 * This method is called to fetch multiple results from searching circle
	 * details.
	 * 
	 * @param resultSet :
	 *            ResultSet
	 * @return object of CircleMasterDTO[]
	 * @throws SQLException
	 * 
	 */
	protected CircleMasterDTO[] fetchMultipleResults(ResultSet resultSet)
	throws SQLException {
		logger
		.debug("Entering method fetchMultipleResults() throws VCircleMstrDaoException");
		ArrayList results = new ArrayList();
		while (resultSet.next()) {
			CircleMasterDTO dto = new CircleMasterDTO();
			populateDto(dto, resultSet);
			results.add(dto);
		}
		CircleMasterDTO retValue[] = new CircleMasterDTO[results.size()];
		results.toArray(retValue);
		logger
		.debug("Exiting method fetchMultipleResults() throws VCircleMstrDaoException");
		return retValue;
	}

	/**
	 * This method is called to fetch single row data from circle details.
	 * 
	 * @param resultSet :
	 *            ResultSet
	 * @return object of CircleMasterDTO
	 * @throws SQLException
	 * 
	 */
	protected CircleMasterDTO fetchSingleResult(ResultSet resultSet)
	throws SQLException {
		if (resultSet.next()) {
			CircleMasterDTO dto = new CircleMasterDTO();
			populateDto(dto, resultSet);
			return dto;
		} else
			return null;
	}

	/**
	 * This method is called to populate DTO.
	 * 
	 * @param dto :
	 *            CircleMasterDTO
	 * @param resultSet :
	 *            ResultSet
	 * @throws SQLException
	 * 
	 */
	protected static void populateDto(CircleMasterDTO dto, ResultSet resultSet)
	throws SQLException {
		dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
		dto.setHubCode(resultSet.getString("HUB_CODE"));
		dto.setCircleName(resultSet.getString("CIRCLE_NAME"));
		dto.setStatus(String.valueOf(resultSet.getInt("STATUS")));
		dto.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));
		dto.setCreatedBy(resultSet.getString("CREATED_BY"));
		dto.setUpdatedDt(resultSet.getTimestamp("MODIFIED_DT"));
		dto.setUpdatedBy(resultSet.getString("MODIFIED_BY"));
		dto.setMinsatCheck(resultSet.getString("MINSAT_CHECK_REQ"));
		dto.setApefCheck(resultSet.getString("AEPF_CHECK_REQ"));
		dto.setWelcomeMsg(resultSet.getString("WELCOME_MSG"));
		dto.setSimReq(resultSet.getString("SIM_REQ_CHECK"));
		dto.setReleaseTime(resultSet.getString("RELEASE_TIME_IN_HRS"));
	}

	/**
	 * This method is called when fetching multiple rows.
	 * 
	 * @param intLb
	 *            :lower bound set to fetch the circle int
	 * @param intUb
	 *            :upper bound set to fetch the circle int
	 * @return object of ArrayList:that contains all the circles
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public ArrayList findAllSelectedRows(int intLb, int intUb)
	throws VCircleMstrDaoException {
		logger
		.debug("Entering method findAllSelectedRows() throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList results = new ArrayList();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(CIRCLE_LIST);
			prepareStatement.setString(1, String.valueOf(intUb));
			prepareStatement.setString(2, String.valueOf(intLb + 1));
			resultSet = prepareStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					CircleMasterDTO dto = new CircleMasterDTO();
					dto.setHubCode(resultSet.getString("HUB"));
					dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
					dto.setCircleName(resultSet.getString("CIRCLE_NAME"));
					dto.setRownum(resultSet.getString("RNUM"));
					String tempStatus = resultSet.getInt("STATUS") == 1 ? "Active"
							: "InActive";
					dto.setStatus(tempStatus);
					results.add(dto);
				}
			}
			return results;
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method findAllSelectedRows() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
			logger
			.debug("Exiting method findAllSelectedRows() throws VCircleMstrDaoException");
		}

	}

	/**
	 * This method is called to check the validity of selected circle
	 * 
	 * @param dto
	 *            :contains the information of circle CircleMasterDTO
	 * @return object of boolean:whether the circle is already there or not
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public boolean isFound(CircleMasterDTO dto) throws VCircleMstrDaoException {
		logger
		.debug("Entering method isFound() throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		boolean res = false;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SQL_CIRCLE_CHECK);
			prepareStatement.setString(1, dto.getCircleName().toUpperCase());
			prepareStatement.setString(2, dto.getCircleCode());
			ResultSet circleResult = prepareStatement.executeQuery();
			if (circleResult.next()) {
				res = true;
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method isFound() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger.debug("Exiting method isFound() throws VCircleMstrDaoException");
		return res;
	}

	/**
	 * This method is used to retrieve list of hub.
	 * 
	 * @return an ArrayList of all hub Name
	 * 
	 * @throws VCircleMstrDaoException
	 * 
	 */

	public ArrayList getHubList() throws VCircleMstrDaoException {
		logger.debug("Entering getHubList in DAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList hubList = new ArrayList();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String query = GET_HUB_LIST;
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				CircleMasterDTO dto = new CircleMasterDTO();
				dto.setHubCode(resultSet.getString("HUB_CODE"));
				dto.setHubName(resultSet.getString("HUB_NAME"));
				hubList.add(dto);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method getHubList() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger.debug("Exiting getHubList in DAOImpl");
		return hubList;

	}

	/**
	 * This method is used to retrieve list of circles.
	 * 
	 * @return an ArrayList of circles
	 * 
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public static ArrayList getCircleList() throws VCircleMstrDaoException {
		logger.debug("Entering getCircleList() in DAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList circleList = new ArrayList();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String query = GET_ALL_CIRCLES
			+ " WHERE STATUS=? ORDER BY CIRCLE_NAME WITH UR";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1,Constants.Active);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				LabelValueBean lvBean = new LabelValueBean(resultSet
						.getString("CIRCLE_CODE"), resultSet
						.getString("CIRCLE_CODE")
						+ "-" + resultSet.getString("CIRCLE_NAME"));

				circleList.add(lvBean);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method getCircleList() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger.debug("Exiting getCircleList() in DAOImpl");
		return circleList;
	}

	/**
	 * This method is used to retrieve list of cities.
	 * 
	 * @return an ArrayList of cities
	 * 
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public static ArrayList getCityList() throws VCircleMstrDaoException {
		logger.debug("Entering getCityList() in DAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList cityList = new ArrayList();
		try {
			connection = DBConnection.getDBConnection();
			String query = GET_ALL_CITY
			+ " WHERE STATUS=? ORDER BY LOCATION_NAME WITH UR";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1,Constants.Active);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				LabelValueBean lvBean = new LabelValueBean(resultSet
						.getString("PARENT_ID"), resultSet
						.getString("LOCATION_NAME"));

				cityList.add(lvBean);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method getCircleList() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger.debug("Exiting getCircleList() in DAOImpl");
		return cityList;
	}
	
	public static ArrayList getZoneList() throws VCircleMstrDaoException {
		logger.debug("Entering getCircleList() in DAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList circleList = new ArrayList();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String query = GET_ALL_CIRCLES
			+ " WHERE STATUS=? ORDER BY CIRCLE_NAME WITH UR";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1,Constants.Active);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				LabelValueBean lvBean = new LabelValueBean(resultSet
						.getString("CIRCLE_CODE"), resultSet
						.getString("CIRCLE_CODE")
						+ "-" + resultSet.getString("CIRCLE_NAME"));

				circleList.add(lvBean);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method getCircleList() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger.debug("Exiting getCircleList() in DAOImpl");
		return circleList;
	}

	
	/**
	 * This method is used to retrieve list of circles on the basis of hub.
	 * 
	 * @param hubCode:on
	 *            the basis of this circles are fetched
	 * @return an ArrayList of circles
	 * 
	 * @throws VCircleMstrDaoException
	 * 
	 */
	public ArrayList getCircleListByHub(String hubCode)
	throws VCircleMstrDaoException {
		logger.debug("Entering getCircleListByHub() in DAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList circleList = new ArrayList();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String query = GET_ALL_CIRCLES
			+ " WHERE STATUS=? AND HUB_CODE = ? ORDER BY CIRCLE_NAME WITH UR";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1,Constants.Active);
			prepareStatement.setString(2, hubCode);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				LabelValueBean lvBean = new LabelValueBean(resultSet
						.getString("CIRCLE_CODE")
						+ "-" + resultSet.getString("CIRCLE_NAME"), resultSet
						.getString("CIRCLE_CODE"));
				circleList.add(lvBean);
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException(Constants.EXCEPTION, e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new VCircleMstrDaoException(
						"Exception occur while closing database resources in method getCircleList() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger.debug("Exiting getCircleList() in DAOImpl");
		return circleList;
	}

	public String circleChildUpdates(String circleCode,int status) throws DAOException
	{
		String result ="childNotUpdated";
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList childList = new ArrayList();
		try{
			connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			connection.setAutoCommit(false);
			String query = GET_CHILD_OF_CIRCLES;
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1,circleCode);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				int childLocId = resultSet.getInt("LOC_DATA_ID");
				childList.add(String.valueOf(childLocId));
			}
			if(!childList.isEmpty()){
				String roleArray [] = (String []) childList.toArray (new String [0]);
				String circleChildStr =Utility.arrayToString(roleArray,null);
				String query1 = UPDATE_CHILD_OF_CIRCLES;
				prepareStatement = connection.prepareStatement(query1.replaceAll("<CIRCLECHILD>", circleChildStr));
				prepareStatement.setInt(1, status);
				prepareStatement.executeUpdate();
				result="childUpdated";
			}else{
				result="childUpdated";
			}
			
		}catch (BatchUpdateException bExp) {
			logger.error("SQL exception encountered: "
					+ bExp.getNextException(), bExp);
			throw new DAOException(bExp.getMessage(), bExp);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				connection.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting insertAllLatestBusinessUserDetails() of MISDataFEEDServiceDAO");
	return result;
	}
	
	public ArrayList circleChildList(String circleCode) throws DAOException
	{
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList childList = new ArrayList();
		try{
			connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			connection.setAutoCommit(false);
			
			String query = GET_CHILD_OF_CIRCLES;
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1,circleCode);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				LocationDataDTO dto = new LocationDataDTO();
				dto.setLocDataId(resultSet.getInt("LOC_DATA_ID"));
				dto.setLocationDataName(resultSet.getString("LOCATION_NAME"));
				childList.add(dto);
			}
						
		}catch (BatchUpdateException bExp) {
			logger.error("SQL exception encountered: "
					+ bExp.getNextException(), bExp);
			throw new DAOException(bExp.getMessage(), bExp);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				connection.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting insertAllLatestBusinessUserDetails() of MISDataFEEDServiceDAO");
	return childList;
	}
	
	public String findHubCodeForCircle(String circleCode)
	throws VCircleMstrDaoException {
		logger
		.debug("Entering method findByCircleName(String strCircleName) throws VCircleMstrDaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String HubCode = null;
		try {
			String sql = HUBCODE_OF_CIRCLE;
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, circleCode);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				HubCode = resultSet.getString("HUB_CODE");
			}
			return HubCode;
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VCircleMstrDaoException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method findByCircleName() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
			logger
			.debug("Exiting method findByCircleName(String strCircleName) throws VCircleMstrDaoException");
		}

	}
	
	public int updateParentOfChildList(String oldParentId, String newParentId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		boolean connCommitStatus = false;
		int numRows;
		try{
				connection = DBConnection
				.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
				connCommitStatus=connection.getAutoCommit();	
				
				prepareStatement = connection
				.prepareStatement(CircleMasterInterface.UPDATE_CHILD_OF_CIRCLE);
				prepareStatement.setString(1, newParentId);
				prepareStatement.setString(2, oldParentId);
				prepareStatement.executeUpdate();
				numRows = prepareStatement.executeUpdate();
				connection.commit();
			
		}catch (BatchUpdateException bExp) {
			logger.error("SQL exception encountered: "
					+ bExp.getNextException(), bExp);
			throw new DAOException(bExp.getMessage(), bExp);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			try{
				connection.rollback();
				connection.setAutoCommit(connCommitStatus);
				return 0;
			}catch (Exception ex) {
				logger.error("Error" + sqe.getMessage(), ex);
			}
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				connection.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting insertAllLatestBusinessUserDetails() of MISDataFEEDServiceDAO");
	return numRows;
	}
}
