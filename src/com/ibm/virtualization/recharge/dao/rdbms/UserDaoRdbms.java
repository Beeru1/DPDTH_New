/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.UserDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides the implementation for all the method declarations in
 * UserDao interface
 * 
 * @author Paras
 * 
 */
public class UserDaoRdbms extends BaseDaoRdbms implements UserDao {

	/*
	 * Logger for this class.
	 */

	protected static Logger logger = Logger.getLogger(UserDaoRdbms.class
			.getName());

	/* SQLs used within the class */
	protected final static String SQL_VR_USER_DETAILS_INSERT_KEY = "SQL_VR_USER_DETAILS_INSERT";
	protected static final String SQL_VR_USER_DETAILS_INSERT = "INSERT INTO VR_USER_DETAILS (USER_ID, EMAIL_ID, CIRCLE_ID, CONTACT_NO, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	protected final static String SQL_USER_LOGIN_SELECT_INTERNAL_KEY = "SQL_USER_LOGIN_SELECT_INTERNAL";
	protected static final String SQL_USER_LOGIN_SELECT_INTERNAL = "SELECT USR.USER_ID, LGN.LOGIN_NAME, LGN.STATUS, USR.CREATED_BY,"
			+ " USR.CREATED_DT, USR.UPDATED_DT, USR.EMAIL_ID, USR.UPDATED_BY,"
			+ " CRM.CIRCLE_NAME, GRP.GROUP_NAME, USR.CONTACT_NO, LGN.TYPE,"
			+ " LGN.GROUP_ID, USR.CIRCLE_ID "
			+ " FROM VR_LOGIN_MASTER LGN , VR_GROUP_DETAILS GRP ,"
			+ " VR_USER_DETAILS USR LEFT JOIN VR_CIRCLE_MASTER CRM ON CRM.CIRCLE_ID = USR.CIRCLE_ID"
			+ " WHERE USR.USER_ID = LGN.LOGIN_ID AND GRP.GROUP_ID = LGN.GROUP_ID AND LGN.TYPE = '"
			+ Constants.USER_TYPE_INTERNAL + "'" + " ORDER BY USER_ID DESC";

	protected final static String SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_KEY = "SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED";
	protected static final String SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED = " SELECT LOGIN1.LOGIN_NAME CreatedByName,USR.USER_ID, LGN.LOGIN_NAME, LGN.STATUS, USR.CREATED_BY, "
			+ " USR.CREATED_DT, USR.UPDATED_DT, USR.EMAIL_ID, USR.UPDATED_BY,"
			+ " CRM.CIRCLE_NAME, GRP.GROUP_NAME, USR.CONTACT_NO, LGN.TYPE,"
			+ " LGN.GROUP_ID, USR.CIRCLE_ID, COUNT(USR.USER_ID)over() RECORD_COUNT,LGN.LOGIN_ATTEMPTED FROM"
			+ " VR_LOGIN_MASTER LGN , VR_GROUP_DETAILS GRP , VR_USER_DETAILS USR ,"
			+ " VR_CIRCLE_MASTER CRM , VR_LOGIN_MASTER LOGIN1 WHERE USR.CIRCLE_ID = CRM.CIRCLE_ID(+) AND USR.USER_ID = LGN.LOGIN_ID AND "
			+ " GRP.GROUP_ID = LGN.GROUP_ID AND LGN.TYPE = '"
			+ Constants.USER_TYPE_INTERNAL
			+ "' AND  LGN.STATUS = ? AND USR.CREATED_BY = LOGIN1.LOGIN_ID ";


	protected final static String SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT_KEY = "SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT";
	protected static final String SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT = "SELECT COUNT(*) FROM"
			+ " VR_LOGIN_MASTER LGN , VR_GROUP_DETAILS GRP , VR_USER_DETAILS USR ,"
			+ " VR_CIRCLE_MASTER CRM WHERE USR.CIRCLE_ID = CRM.CIRCLE_ID(+) AND USR.USER_ID = LGN.LOGIN_ID AND "
			+ " GRP.GROUP_ID = LGN.GROUP_ID AND LGN.TYPE = '"
			+ Constants.USER_TYPE_INTERNAL + "' AND  LGN.STATUS = ?";

	protected final static String SQL_USER_SELECT_ACTIVE_INTERNAL_KEY = "SQL_USER_SELECT_ACTIVE_INTERNAL";
	protected static final String SQL_USER_SELECT_ACTIVE_INTERNAL = "SELECT USR.USER_ID, LGN.LOGIN_NAME, LGN.STATUS, USR.CREATED_BY,"
			+ " USR.CREATED_DT, USR.UPDATED_DT, USR.EMAIL_ID, USR.UPDATED_BY,"
			+ " CRM.CIRCLE_NAME, GRP.GROUP_NAME, USR.CONTACT_NO, LGN.TYPE,"
			+ " LGN.GROUP_ID, USR.CIRCLE_ID "
			+ " FROM VR_LOGIN_MASTER LGN , VR_GROUP_DETAILS GRP ,"
			+ " VR_USER_DETAILS USR LEFT JOIN VR_CIRCLE_MASTER CRM ON CRM.CIRCLE_ID = USR.CIRCLE_ID"
			+ " WHERE USR.USER_ID = LGN.LOGIN_ID AND GRP.GROUP_ID = LGN.GROUP_ID AND LGN.STATUS = '"
			+ Constants.ACTIVE_STATUS
			+ "' AND LGN.TYPE = '"
			+ Constants.USER_TYPE_INTERNAL + "'" + " ORDER BY USER_ID DESC";

	protected final static String SQL_USER_SELECT_ON_LOGIN_ID_KEY = "SQL_USER_SELECT_ON_LOGIN_ID";
	protected static final String SQL_USER_SELECT_ON_LOGIN_ID = "SELECT LGN.PASSWORD,USR.USER_ID, LGN.LOGIN_NAME, LGN.STATUS, USR.CREATED_BY, USR.CREATED_DT, USR.UPDATED_DT, USR.EMAIL_ID, USR.UPDATED_BY, USR.CONTACT_NO, LGN.TYPE, LGN.GROUP_ID, USR.CIRCLE_ID FROM VR_LOGIN_MASTER LGN , VR_USER_DETAILS USR WHERE USR.USER_ID = LGN.LOGIN_ID AND LGN.LOGIN_ID = ? ";

	// Used in change password module in case of password expiry
	protected final static String SQL_USER_SELECT_ON_LOGIN_NAME_KEY = "SQL_USER_SELECT_ON_LOGIN_NAME";
	protected static final String SQL_USER_SELECT_ON_LOGIN_NAME = "SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ? ";

	protected final static String SQL_USER_UPDATE_KEY = "SQL_USER_UPDATE";
	protected static final String SQL_USER_UPDATE = "UPDATE VR_USER_DETAILS SET EMAIL_ID = ?, UPDATED_BY = ?, CIRCLE_ID = ?, CONTACT_NO = ?, UPDATED_DT = ? WHERE USER_ID = ?";

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public UserDaoRdbms(Connection connection) {
		super(connection);
		queryMap
				.put(SQL_VR_USER_DETAILS_INSERT_KEY, SQL_VR_USER_DETAILS_INSERT);
		queryMap.put(SQL_USER_LOGIN_SELECT_INTERNAL_KEY,
				SQL_USER_LOGIN_SELECT_INTERNAL);
		queryMap.put(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_KEY,
				SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED);
		queryMap.put(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT_KEY,
				SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT);
		queryMap.put(SQL_USER_SELECT_ACTIVE_INTERNAL_KEY,
				SQL_USER_SELECT_ACTIVE_INTERNAL);
		queryMap.put(SQL_USER_SELECT_ON_LOGIN_ID_KEY,
				SQL_USER_SELECT_ON_LOGIN_ID);
		queryMap.put(SQL_USER_SELECT_ON_LOGIN_NAME_KEY,
				SQL_USER_SELECT_ON_LOGIN_NAME);
		queryMap.put(SQL_USER_UPDATE_KEY, SQL_USER_UPDATE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#insertUser(com.ibm.virtualization.recharge.dto.User)
	 */
	public void insertUser(User user) throws DAOException {
		logger.info("Started... " + user);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {

			/* Level-2 inserting values into VR_USER_DETAILS Table */
			ps = connection.prepareStatement(queryMap.get(SQL_VR_USER_DETAILS_INSERT_KEY));
			int paramCount = 1;
			ps.setLong(paramCount++, user.getUserId());
			ps.setString(paramCount++, user.getEmailId());
			if (0 == user.getCircleId()) {
				/* If user doesnot belong to any specific circle */
				ps.setNull(paramCount++, Types.INTEGER);
			} else {
				ps.setInt(paramCount++, user.getCircleId());
			}
			ps.setString(paramCount++, user.getContactNumber());
			ps.setLong(paramCount++, user.getCreatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, user.getUpdatedBy());
			ps.setTimestamp(paramCount, Utility.getDateTime());

			rowsUpdated = ps.executeUpdate();

			logger
					.info("Executed ...Row insertion successful on table: VR_LOGIN_MASTER and VR_USER_DETAILS. Inserted : "
							+ rowsUpdated + " rows");
		} catch (SQLException e) {
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.User.ERROR_USER_NAME_ALREADY_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getUser(long)
	 */
	public User getUser(long loginId) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(queryMap.get(SQL_USER_SELECT_ON_LOGIN_ID_KEY));
			ps.setLong(1, loginId);
			rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("USER_ID"));
				/* set login id to be passed to form bean */
				user.setLoginId(user.getUserId());
				user.setLoginName(rs.getString("LOGIN_NAME"));
				user.setStatus(rs.getString("STATUS"));
				user.setCreatedBy(rs.getLong("CREATED_BY"));
				user.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				user.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				user.setEmailId(rs.getString("EMAIL_ID"));
				user.setUpdatedBy(rs.getLong("UPDATED_BY"));
				user.setCircleId(rs.getInt("CIRCLE_ID"));
				user.setType(rs.getString("TYPE"));
				user.setGroupId(rs.getInt("GROUP_ID"));
				user.setContactNumber(rs.getString("CONTACT_NO"));
				user.setPassword(rs.getString("PASSWORD"));

				return user;
			} else {
				logger.info("No user found with user Id = " + loginId);
				return null;
			}

		} catch (SQLException e) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getAllUsers()
	 */
	public ArrayList getAllUsers(ReportInputs inputDto, int lb, int ub)
			throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		try {
			StringBuilder query = new StringBuilder(
					"SELECT * from ( select a.*,ROWNUM rnum FROM (");
			query.append(queryMap.get(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_KEY));
			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				query.append(" AND USR.CIRCLE_ID = ? ");
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND TRUNC(USR.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND TRUNC(USR.CREATED_DT) <= ? ");
			}
			query
					.append(" ORDER BY USER_ID DESC )a Where ROWNUM <= ? ) where rnum >= ?");

			ps = connection.prepareStatement(query.toString());

			logger.info("Query after changes=" + query.toString());

			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());
			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				ps.setInt(paramCount++, inputDto.getCircleId());
			}
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getStartDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getEndDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}

			ps.setInt(paramCount++, ub);
			ps.setInt(paramCount++, lb + 1);

			rs = ps.executeQuery();
			ArrayList<User> userList = new ArrayList<User>();
			while (rs.next()) {
				// userList.add(populateDto(rs));
				User user = new User();
				user.setUserId(rs.getInt("USER_ID"));
				/* set login id to be passed to form bean */
				user.setLoginId(user.getUserId());
				user.setLoginName(rs.getString("LOGIN_NAME"));
				user.setStatus(rs.getString("STATUS"));
				user.setCreatedBy(rs.getLong("CREATED_BY"));
				user.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				user.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				user.setEmailId(rs.getString("EMAIL_ID"));
				user.setUpdatedBy(rs.getLong("UPDATED_BY"));
				user.setCircleName(rs.getString("CIRCLE_NAME"));
				user.setCircleId(rs.getInt("CIRCLE_ID"));
				user.setType(rs.getString("TYPE"));
				user.setGroupName(rs.getString("GROUP_NAME"));
				user.setGroupId(rs.getInt("GROUP_ID"));
				user.setContactNumber(rs.getString("CONTACT_NO"));
				user.setRowNum(rs.getString("RNUM"));
				user.setTotalRecords(rs.getInt("RECORD_COUNT"));
				user.setCreatedByName(rs.getString("CreatedByName"));
				int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));	
				if(rs.getInt("LOGIN_ATTEMPTED")==numberOfPwdHistory){
					user.setLocked(true);
				}
				else{
					user.setLocked(false);
				}
				
				userList.add(user);
			}
			logger.info(" Number of Users found = " + userList.size());
			if (0 == userList.size()) {
				logger.error(" No Users found  .");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_USER_DOESNOT_EXIST);
			}
			logger.info("Executed .... ");
			logger.info(" Number of Users found = " + userList.size());
			return userList;
		} catch (SQLException e) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("UserDaoRdbms:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getAllUsers()
	 */
	public ArrayList getAllUsers(ReportInputs inputDto)
			throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		try {
			StringBuilder query = new StringBuilder();
			query.append(queryMap.get(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_KEY));
			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				query.append(" AND USR.CIRCLE_ID = ? ");
			}
			logger.info("Query=" + query.toString());
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND TRUNC(USR.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND TRUNC(USR.CREATED_DT) <= ? ");
			}
			query.append(" ORDER BY USER_ID ");
			ps = connection.prepareStatement(query.toString());

			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());
			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}

			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				ps.setInt(paramCount++, inputDto.getCircleId());
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getStartDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getEndDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}

			logger.info("Query after changes=" + query.toString());

			rs = ps.executeQuery();
			ArrayList<User> userList = new ArrayList<User>();
			while (rs.next()) {
				userList.add(populateDto(rs));
			}
			logger.info(" Number of Users found = " + userList.size());
			if (0 == userList.size()) {
				logger.error(" No Users found  .");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_USER_DOESNOT_EXIST);
			}
			logger.info("Executed .... ");
			logger.info(" Number of Users found = " + userList.size());
			return userList;
		} catch (SQLException e) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	public int getUsersCount(ReportInputs inputDto) throws DAOException {
		int noofpages = 0;
		int noofRow = 1;
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		try {
			StringBuilder query = new StringBuilder();
			query.append(queryMap.get(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT_KEY));
			if (inputDto.getCircleId() != 0) {
				query.append(" AND USR.CIRCLE_ID = ? ");
			}

			logger.info("Query=" + query.toString());
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND TRUNC(USR.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND TRUNC(USR.CREATED_DT) <= ?");
			}
			ps = connection.prepareStatement(query.toString());

			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());
			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}

			if (inputDto.getCircleId() != 0) {
				ps.setInt(paramCount++, inputDto.getCircleId());
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getStartDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getEndDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			logger.info("Before execution");

			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			if (0 == noofRow) {
				logger.error(" No User found  .");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_USER_DOESNOT_EXIST);
			}
			noofpages = Utility.getPaginationSize(noofRow);
			logger.info(" Number of Users found = " + noofRow);
			logger.info("Executed .... ");
			return noofpages;
		} catch (SQLException e) {

			logger.error("SQL Exception occured while finding the no of Users."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#updateUser(com.ibm.virtualization.recharge.dto.User)
	 */
	public void updateUser(User user) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		int numRows = -1;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_USER_UPDATE_KEY));
			int paramCount = 1;

			ps.setString(paramCount++, user.getEmailId());
			ps.setLong(paramCount++, user.getUpdatedBy());
			/* check if the user doenot belong to any circle */
			if (0 == user.getCircleId()) {
				ps.setNull(paramCount++, Types.INTEGER);
			} else {
				ps.setInt(paramCount++, user.getCircleId());
			}
			ps.setString(paramCount++, user.getContactNumber());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount, user.getUserId());

			numRows = ps.executeUpdate();
			logger.info(" Update successful on table:USER_MASTER. Updated:"
					+ numRows + " rows");
			logger.info("Executed ....");
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.User.ERROR_USER_NAME_ALREADY_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
			logger.info("Executed ....");
		}
	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	protected static User populateDto(ResultSet rs) throws DAOException,
			SQLException {
		logger.info("Started...");
		User user = new User();
		user.setUserId(rs.getInt("USER_ID"));
		/* set login id to be passed to form bean */
		user.setLoginId(user.getUserId());
		user.setLoginName(rs.getString("LOGIN_NAME"));
		user.setStatus(rs.getString("STATUS"));
		user.setCreatedBy(rs.getLong("CREATED_BY"));
		user.setCreatedDt(rs.getTimestamp("CREATED_DT"));
		user.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
		user.setEmailId(rs.getString("EMAIL_ID"));
		user.setUpdatedBy(rs.getLong("UPDATED_BY"));
		user.setCircleName(rs.getString("CIRCLE_NAME"));
		user.setCircleId(rs.getInt("CIRCLE_ID"));
		user.setType(rs.getString("TYPE"));
		user.setGroupName(rs.getString("GROUP_NAME"));
		user.setGroupId(rs.getInt("GROUP_ID"));
		user.setContactNumber(rs.getString("CONTACT_NO"));
		logger.info("Executed ....");
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getUser(java.lang.String)
	 */
	public long getUser(String loginName) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		long loginId = -1;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_USER_SELECT_ON_LOGIN_NAME_KEY));
			ps.setString(1, loginName);
			rs = ps.executeQuery();
			if (rs.next()) {
				loginId = rs.getLong("LOGIN_ID");
			} else {
				logger.info(" No user found with login name = " + loginName);
			}
		} catch (SQLException e) {
			logger.error("getUser() : SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

		return loginId;

	}
}
