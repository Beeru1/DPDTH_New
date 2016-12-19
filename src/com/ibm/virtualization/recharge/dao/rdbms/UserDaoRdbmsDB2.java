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
import java.util.ArrayList;
import java.util.Date;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
public class UserDaoRdbmsDB2 extends UserDaoRdbms {

	public UserDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_KEY, SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED);
		queryMap.put(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT_KEY, SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT);

	}
	
	protected static final String SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED = " SELECT LGN.LOGIN_NAME CreatedByName,USR.USER_ID, LGN.LOGIN_NAME, LGN.STATUS, USR.CREATED_BY, "
		+ " USR.CREATED_DT, USR.UPDATED_DT, USR.EMAIL_ID, USR.UPDATED_BY,"
		+ " CRM.CIRCLE_NAME, GRP.GROUP_NAME, USR.CONTACT_NO, LGN.TYPE,"
		+ " LGN.GROUP_ID, USR.CIRCLE_ID, COUNT(USR.USER_ID)over() RECORD_COUNT,LGN.LOGIN_ATTEMPTED FROM"
		+ " VR_LOGIN_MASTER LGN , VR_GROUP_DETAILS GRP , VR_USER_DETAILS USR left outer join VR_CIRCLE_MASTER CRM on usr.CIRCLE_ID = crm.CIRCLE_ID " 
		+"WHERE USR.USER_ID = LGN.LOGIN_ID AND "
		+ " GRP.GROUP_ID = LGN.GROUP_ID AND LGN.TYPE = '"
		+ Constants.USER_TYPE_INTERNAL + "' AND  LGN.STATUS = ? ";
	
	
	protected static final String SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_COUNT = "SELECT COUNT(*)"
			+ "FROM VR_LOGIN_MASTER LGN, VR_GROUP_DETAILS GRP, VR_USER_DETAILS USR LEFT OUTER JOIN" 
			+ " VR_CIRCLE_MASTER CRM ON CRM.CIRCLE_ID = USR.CIRCLE_ID"
			+ "WHERE USR.USER_ID = LGN.LOGIN_ID AND GRP.GROUP_ID = LGN.GROUP_ID AND LGN.TYPE = '"
			+ Constants.USER_TYPE_INTERNAL + "' AND LGN.STATUS = ?";

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
					"SELECT * from ( select a.*,ROW_NUMBER() OVER() rnum FROM (");
			query.append(queryMap.get(SQL_USER_LOGIN_SELECT_INTERNAL_PARAMETER_BASED_KEY));
			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				query.append(" AND USR.CIRCLE_ID = ? ");
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(USR.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(USR.CREATED_DT) <= ? ");
			}
			query
					.append(" ORDER BY USER_ID DESC )a)b where rnum <= ? AND rnum >= ?");

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
			logger.error("UserDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
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
				query.append(" AND DATE(USR.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(USR.CREATED_DT) <= ?");
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
				query.append(" AND DATE(USR.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(USR.CREATED_DT) <= ? ");
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

}
