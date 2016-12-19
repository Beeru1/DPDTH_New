/**************************************************************************
 * File     : VUserMstrDaoImpl.java
 * Author   : Pragati
 * Created  : Sep 6, 2008
 * Modified : Sep 6, 2008
 * Version  : 0.1
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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.VUserMstrDao;
import com.ibm.virtualization.ussdactivationweb.dto.VUserMstrDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.exception.VUserMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.email.EmailDTO;
import com.ibm.virtualization.ussdactivationweb.utils.email.EmailUtility;

/*******************************************************************************
 * This class helps to make the database connections to manage the user related
 * methods like create,edit and find.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class VUserMstrDaoImpl implements VUserMstrDao {

	private static final Logger logger = Logger
	.getLogger(VUserMstrDaoImpl.class);

	public VUserMstrDaoImpl() {
	}

	/**
	 * This method countUser() counts the number of users
	 * 
	 * @return no of users in int
	 * @exception VUserMstrDaoException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */

	public int countUser() throws VUserMstrDaoException {
		logger
		.debug("Entering method countUser() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		int noofRow = 0;
		int noofPages = 0;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(USER_COUNT);
			ResultSet countUser = pStmt.executeQuery();
			if (countUser.next()) {
				noofRow = countUser.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException("SQLException occur: ", sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException("Exception occur : ", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting method countUser() throws VUserMstrDaoException");
		return noofPages;
	}

	/**
	 * This method insert the user information in to the database.
	 * 
	 * @param dto :
	 *            VUserMstrDTO that contains all the information of user
	 * @return no of record inserted in the database
	 * @throws VUserMstrDaoException
	 */
	public int insert(final VUserMstrDTO dto) throws VUserMstrDaoException {
		logger.debug("Entering method insert() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		int intRowsUpdated = 0;
		try {
			conn = DBConnection.getDBConnection();
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement(SQL_INSERT_USER_WITHOUT_ID);
			int intParamCount = 1;
			pStmt.setString(intParamCount++, dto.getLoginId());
			pStmt.setString(intParamCount++, dto.getCreatedBy());
			pStmt.setString(intParamCount++, dto.getMailId());
			pStmt.setString(intParamCount++, dto.getUpdatedBy());
			pStmt.setString(intParamCount++, dto.getCircleCode());
			pStmt.setString(intParamCount++, dto.getPassword());
			pStmt.setString(intParamCount++, dto.getUserType());
			pStmt.setString(intParamCount++, dto.getGroupId());
			pStmt.setString(intParamCount++, dto.getLoginAttempted());
			pStmt.setString(intParamCount++, dto.getLoginStatus());
			pStmt.setInt(intParamCount++, dto.getZoneCode());
			intRowsUpdated = pStmt.executeUpdate();
			conn.commit();
			logger
			.debug("Row insertion successful on table:V_USER_MSTR. Inserted:"
					+ intRowsUpdated + " rows");
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"Exception occur while inserting user data.", sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException(
					"Exception occur while inserting user data. ", e);
		} finally {
			try {
				conn.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(conn, pStmt);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting method insert() throws VUserMstrDaoException");
		return intRowsUpdated;
	}

	/**
	 * This method search the user in to the database by login id.
	 * 
	 * @param strLoginId :
	 *            String on the basis of which the user availability is find out
	 * @return object of VUserMstrDTO class which contains the user detail
	 * @throws VUserMstrDaoException
	 */
	public VUserMstrDTO findByPrimaryKey(String strLoginId)
	throws VUserMstrDaoException {
		logger
		.debug("Entering method findByPrimaryKey() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(SQL_SELECT_USER
					+ " WHERE V_USER_MSTR.LOGIN_ID = ? WITH UR");
			pStmt.setString(1, strLoginId);
			resultSet = pStmt.executeQuery();
			return fetchSingleResult(resultSet);
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"SQLException occured while find user. ", sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException("Exception: ", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
			logger
			.debug("Exiting method findByPrimaryKey() throws VUserMstrDaoException");
		}
	}

	
	/**
	 * This method will search the all user in to the database.
	 * 
	 * @param intLb :
	 *            contains the lower value of paging in int
	 * @param intUb :
	 *            contains the uper value of paging in int
	 * @return object of VUserMstrDTO class which contains the user detail
	 * @throws VUserMstrDaoException
	 */
	public List findAllUsers(int intLb, int intUb) throws VUserMstrDaoException {
		logger
		.info("Entering method findAllUsers() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		ArrayList userList = new ArrayList();
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(VUserMstrDao.SQL_SELECT_ALL_USERS);
			pStmt.setString(1, String.valueOf(intUb));
			pStmt.setString(2, String.valueOf(intLb+1));
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				VUserMstrDTO dto = new VUserMstrDTO();
				if ((Constants.LockStatus).equalsIgnoreCase(resultSet.getString("LOCK_STATUS"))) {
					dto.setLockStatus(resultSet.getString("LOCK_STATUS"));
				}
				dto.setZoneCode(resultSet.getInt("ZONE_CODE"));
			if(dto.getZoneCode()!= 0 ){
				dto.setZoneName(getZoneName(dto.getZoneCode()));
				
			}
				
//				if ((Constants.ZoneStatus)== (resultSet.getInt("ZONE_CODE"))) {
//					dto.setZoneCode(resultSet.getInt("ZONE_CODE"));
//				}
				dto.setUserId(resultSet.getString("USER_ID"));
				dto.setLoginId(resultSet.getString("LOGIN_ID"));
				// Only two valid values exist for status A=Active D=In-Active
				if ((Constants.ActiveState).equalsIgnoreCase(resultSet.getString(Constants.STATUS))) {
					dto.setStatusName(Constants.strActive);
				} else {
					dto.setStatusName(Constants.strInAcive);
				}
				dto.setStatus(resultSet.getString(Constants.STATUS));
				dto.setCreatedBy(resultSet.getString("CREATED_BY"));
				dto.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));
				dto.setUpdatedDt(resultSet.getTimestamp("UPDATED_DT"));
				dto.setMailId(resultSet.getString("EMAIL_ID"));
				dto.setUpdatedBy(resultSet.getString("UPDATED_BY"));
				dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				/**
				 * getCircleName() method to retrieve circle name based on the
				 * CircleId dto should have setCircleName()
				 */
				if (dto.getCircleCode() != null && dto.getCircleCode() != "") {
					dto.setCircleName(getCircleName(dto.getCircleCode()));
				}
				dto.setPasswordChangedDt(resultSet
						.getTimestamp("PASSWORD_CHANGED_DT"));
				// Only two static values exist for user type A=Admin CA=Circle
				// Admin
				if ((Constants.circleLogin).equalsIgnoreCase(resultSet.getString(Constants.USER_TYPE))) {
					dto.setUserTypeName(Constants.CIRCLE_ADMIN);
				}if ((Constants.retailerLogin).equalsIgnoreCase(resultSet.getString(Constants.USER_TYPE))) {
						dto.setUserTypeName(Constants.Retailer_User);
				} if((Constants.zoneLogin).equalsIgnoreCase(resultSet.getString(Constants.USER_TYPE))) {
						dto.setUserTypeName(Constants.Zone_User);
				}if((Constants.AdminType).equalsIgnoreCase(resultSet.getString(Constants.USER_TYPE))) {
					dto.setUserTypeName(Constants.Admin);
				}
				dto.setUserType(resultSet.getString(Constants.USER_TYPE));
				dto.setGroupId(resultSet.getString(Constants.GROUP_ID));

				/**
				 * getGroupName() method to retrieve Group name based on the
				 * GroupId dto should have setGroupName()
				 */
				dto.setGroupName(getGroupName(resultSet
						.getString(Constants.GROUP_ID)));
				dto.setLoginAttempted(resultSet.getString("LOGIN_ATTEMPTED"));
				dto.setRownum(resultSet.getString("RNUM"));
				dto.setLastAccessTime(resultSet.getString("LAST_LOGIN_TIME"));

				userList.add(dto);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"SQLException occur in finaAllUsers().", sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException(
					"SQLException occur in finaAllUsers().", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
		}
		logger
		.debug("Exiting method findAllUsers() throws VUserMstrDaoException");
		return userList;
	}

	/**
	 * This method will search the all user in to the database.
	 * 
	 * @param dto :
	 *            contains the user information
	 * @return no of records updated in int
	 * @throws VUserMstrDaoException
	 */
	public int update(VUserMstrDTO dto) throws VUserMstrDaoException {
		logger.debug("Entering method update() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		int intNumRows = -1;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(SQL_UPDATE_USER1);
			pStmt.setString(1, dto.getUserId());
			pStmt.setString(2, dto.getLoginId());
			pStmt.setString(3, dto.getStatus());
			pStmt.setString(4, dto.getMailId());
			pStmt.setString(5, dto.getUpdatedBy());
			pStmt.setString(6, dto.getCircleCode());
			pStmt.setString(7, dto.getUserType());
			pStmt.setString(8, dto.getGroupId());
			pStmt.setString(9, dto.getLoginAttempted());
			pStmt.setString(10, dto.getPassword());
			pStmt.setInt(11,dto.getZoneCode());
			pStmt.setString(12, dto.getUserId());
		
			
			intNumRows = pStmt.executeUpdate();
			if(dto.getMessage()!= null && dto.getSubject()!= null){
			sendingEmail(dto);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"SQLException occured while updating user information.",
					sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException(
					"Exception occured while updating user information.", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting method update() throws VUserMstrDaoException");
		return intNumRows;
	}

	/**
	 * This method will unlock all the user in the database.
	 * 
	 * @param dto :
	 *            contains the user information
	 * @return no of records updated in int
	 * @throws VUserMstrDaoException
	 */
	public int updateUser(VUserMstrDTO dto) throws VUserMstrDaoException {
		logger
		.info("Entering method updateUser() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		int intNumRows = -1;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(VUserMstrDao.UPDATE_LOCKED_USER);

			int loginAttempts = Integer.parseInt(dto.getLoginAttempted());
			pStmt.setInt(1, loginAttempts);
			pStmt.setString(2, dto.getLoginStatus());
			pStmt.setString(3, dto.getLoginId());
			intNumRows = pStmt.executeUpdate();
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"SQLException occured while updating user information.",
					sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException(
					"Exception occured while updating user information.", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
		}
		logger.info("Exiting method updateUser() throws VUserMstrDaoException");
		return intNumRows;
	}

	/**
	 * This method will search the all user in to the database.
	 * 
	 * @param strUserId :
	 *            contains user id in String
	 * @return no of records updated in int
	 * @throws VUserMstrDaoException
	 */
	public int delete(String strUserId) throws VUserMstrDaoException {
		logger.debug("Entering method delete() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		int intNumRows = -1;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(SQL_DELETE_USER);
			pStmt.setString(1, strUserId);
			intNumRows = pStmt.executeUpdate();
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"SQLException occur while deleting user information. ",
					sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException(
					"Exception occur while deleting user information.", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting method delete() throws VUserMstrDaoException");
		return intNumRows;
	}

	/**
	 * This method will search the all user in to the database.
	 * 
	 * @param resultSet :
	 *            contains users
	 * @return no of records updated in int
	 * @throws VUserMstrDaoException
	 */
	protected VUserMstrDTO[] fetchMultipleResults(ResultSet resultSet)
	throws SQLException {
		logger.debug("Entering method VUserMstrDTO() throws SQLException");
		ArrayList results = new ArrayList();
		VUserMstrDTO dto = null;
		while (resultSet.next()) {
			dto = new VUserMstrDTO();
			populateDto(dto, resultSet);
			results.add(dto);
		}
		VUserMstrDTO retValue[] = new VUserMstrDTO[results.size()];
		results.toArray(retValue);
		logger.debug("Exiting method VUserMstrDTO() throws SQLException");
		return retValue;
	}

	/**
	 * Method to display list of all the Users in the V_USER_MSTR table
	 * 
	 * @param resultSet :
	 *            resultSet of users
	 * @return Arraylist of DTO
	 * @throws SQLException
	 */
	public List findAllSelectedRows(ResultSet resultSet) throws SQLException {
		ArrayList results = new ArrayList();
		VUserMstrDTO dto = null;
		while (resultSet.next()) {
			dto = new VUserMstrDTO();
			populateDto(dto, resultSet);
			results.add(dto);
		}
		return results;
	}

	/**
	 * This method returns the loginId availablity status to the
	 * UserMasterAction
	 * 
	 * @param RecordSet
	 *            resultSet which holds the records if the loginId is already in
	 *            use or/else is null;
	 * @return
	 * @throws SQLException
	 */
	protected boolean isAvailable(ResultSet resultSet) throws SQLException {
		boolean bolResponse;
		if (resultSet.next()) {
			bolResponse = false;
		} else {
			bolResponse = true;
		}
		return bolResponse;
	}

	/**
	 * This method returns the loginId availablity status to the
	 * UserMasterAction
	 * 
	 * @param RecordSet
	 *            resultSet which holds the records if the loginId is already in
	 *            use or/else is null;
	 * @return Object of VUserMstrDTO class containing user information
	 * @throws SQLException
	 */
	protected VUserMstrDTO fetchSingleResult(ResultSet resultSet)
	throws SQLException {
		VUserMstrDTO dto = null;
		if (resultSet.next()) {
			dto = new VUserMstrDTO();
			populateDto(dto, resultSet);
		}
		return dto;
	}

	/**
	 * This method returns the loginId availablity status to the
	 * UserMasterAction
	 * 
	 * @param dto:
	 *            it contains the information of user
	 * @param RecordSet
	 *            resultSet which holds the records if the loginId is already in
	 *            use or/else is null;
	 * @return Object of VUserMstrDTO class containing user information
	 * @throws SQLException
	 */
	protected static void populateDto(VUserMstrDTO dto, ResultSet resultSet)
	throws SQLException {
		logger
		.debug("Entering method populateDto() throws VUserMstrDaoException");
		try {
			dto.setUserId(resultSet.getString("USER_ID"));
			dto.setLoginId(resultSet.getString("LOGIN_ID"));
			// Only two valid values exist for status A=Active D=In-Active
			if ((Constants.ActiveState).equals(resultSet.getString(Constants.STATUS))) {
				dto.setStatusName(Constants.strActive);
			} else {
				dto.setStatusName(Constants.strInAcive);
			}
			dto.setStatus(resultSet.getString(Constants.STATUS));
			dto.setCreatedBy(resultSet.getString("CREATED_BY"));
			dto.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));
			dto.setUpdatedDt(resultSet.getTimestamp("UPDATED_DT"));
			dto.setMailId(resultSet.getString("EMAIL_ID"));
			dto.setUpdatedBy(resultSet.getString("UPDATED_BY"));
			if (resultSet.getString("CIRCLE_CODE") != null) {
				dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				/**
				 * getCircleName() method to retrieve circle name based on the
				 * CircleId dto should have setCircleName()
				 */
				dto.setCircleName(getCircleName(resultSet
						.getString("CIRCLE_CODE")));
			}
			dto.setPasswordChangedDt(resultSet
					.getTimestamp("PASSWORD_CHANGED_DT"));
			dto.setPassword(resultSet
					.getString("PASSWORD"));
			// Only two static values exist for user type A=Admin CA=Circle
			// Admin
			if ((Constants.circleLogin).equalsIgnoreCase(resultSet.getString(Constants.USER_TYPE))) {
				dto.setUserTypeName(Constants.CIRCLE_ADMIN);
			} else {
				dto.setUserTypeName(Constants.Admin);
			}
			dto.setUserType(resultSet.getString(Constants.USER_TYPE));
			dto.setGroupId(resultSet.getString(Constants.GROUP_ID));
			/**
			 * getGroupName() method to retrieve Group name based on the GroupId
			 * dto should have setGroupName()
			 */
			dto.setGroupName(getGroupName(resultSet
					.getString(Constants.GROUP_ID)));
			dto.setLoginAttempted(resultSet.getString("LOGIN_ATTEMPTED"));
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new SQLException("Exception occur in populateDto() method."
					+ sqlEx.toString());
		} catch (VUserMstrDaoException e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new SQLException("Exception occur in populateDto() method."
					+ e.toString());
		}
		logger
		.debug("Exiting method populateDto() throws VUserMstrDaoException");
	}

	/**
	 * This method returns the Group Name from V_Group_Mstr table for the
	 * groupId provided as an argument.
	 * 
	 * @param groupId :
	 *            conatins group id
	 * @return groupName : group name
	 * @throws VUserMstrDaoException
	 */
	private static String getGroupName(String strGroupId)
	throws VUserMstrDaoException {
		logger
		.debug("Entering method getGroupName() throws VUserMstrDaoException");
		String strGroupName = "";
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(GET_GROUP_NAME);
			pStmt.setInt(1, Integer.parseInt(strGroupId));
			resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				strGroupName = resultSet.getString("GROUP_NAME");
			}
			return strGroupName;
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException("SQLException occured: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException("Exception occured: "
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
			logger
			.debug("Exiting method getGroupName() throws VUserMstrDaoException");
		}
	}

	/**
	 * This method returns the Circle Name from V_CIRCLE_Mstr table for the
	 * circleId provided as an argument.
	 * 
	 * @param String
	 *            circleId
	 * @return String circleName
	 * @throws VUserMstrDaoException
	 */
	private static String getCircleName(String strCircleId)
	throws VUserMstrDaoException {
		logger
		.debug("Entering method getCircleName() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String forward = null;
		try {
			conn = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			pStmt = conn.prepareStatement(GET_CIRCLE_NAME);
			pStmt.setString(1, strCircleId);
			resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				forward = resultSet.getString("CIRCLE_NAME");
			}
			return forward;

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException("SQLException occured : "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException("Exception occured: "
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
			logger
			.debug("Exiting method getCircleName() throws VUserMstrDaoException");
		}
	}
	
	private static String getZoneName(int zoneCode)
	throws VUserMstrDaoException {
		logger
		.debug("Entering method getCircleName() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String forward = null;
		try {
			
			conn = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			pStmt = conn.prepareStatement("select LOCATION_NAME from Prodcat.PC_LOCATION_DATA where LOC_DATA_ID= ? ");
			pStmt.setInt(1, zoneCode);
			resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				forward = resultSet.getString("LOCATION_NAME");
			}
			return forward;

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException("SQLException occured : "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException("Exception occured: "
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
			logger
			.debug("Exiting method getCircleName() throws VUserMstrDaoException");
		}
	}

	/**
	 * This method is used to check if the loginId requested to create a new
	 * user is already in use or isAvailable for the current user creation.
	 * 
	 * @param String
	 *            loginId
	 * @return boolean status
	 * @throws VUserMstrDaoException
	 */
	public boolean findByLoginId(String strLoginId)
	throws VUserMstrDaoException {
		logger
		.debug("Entering method findByLoginId() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		boolean bolResponse;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(SQL_SELECT_USER
					+ " WHERE upper(V_USER_MSTR.LOGIN_ID) = ? WITH UR");
			pStmt.setString(1, strLoginId.toUpperCase());
			resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				bolResponse = true;
			} else {
				bolResponse = false;
			}
			return bolResponse;

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException(
					"SQLException occured while finding user by id.", sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException(
					"Exception occured while finding user by id."
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
			logger
			.debug("Exiting method findByLoginId() throws VUserMstrDaoException");
		}
	}
	
	
	
	/**	This method sendingEmail() require four parameters and sends email 
	 * @param userdto
	 * @throws DAOException
	 */
	public void sendingEmail(VUserMstrDTO userdto) throws DAOException {
		logger.debug("Into UserDAOImpl:sendingEmail() ");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DBConnection.getDBConnection();
			
			if(userdto.getMessage()!= null){
			// Sending mail
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setTo(userdto.getMailId());
			emailDTO.setFrom(Constants.SENDING_FROM);
			emailDTO.setSubject(userdto.getSubject());
			emailDTO.setMessage(userdto.getMessage());
			EmailUtility.sendEmail(emailDTO);
			}else
				logger.info("No message for this email");
			logger.debug("No message for this email");
		}
		catch (Exception e) {
			logger.debug("Exception");
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			try {
				DBConnectionUtil.closeDBResources(connection, preparedStatement,
						resultSet);
			} catch (SQLException e) {
				logger.debug("Exception in closing database resources.", e);
			}
			logger.debug("Exiting UserDAOImpl:sendingEmail() ... ");
		}
	}
	
}
