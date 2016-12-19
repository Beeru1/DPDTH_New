package com.ibm.hbo.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOUserMstrDao;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.exception.DAOException;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class HBOUserMstrDaoImpl implements HBOUserMstrDao {

	/*
		 * Logger for the class.
		 */
	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOUserMstrDaoImpl.class);
	}

	protected static final String SQL_INSERT_WITH_ID =
	//======Chnage by sachin on 18th march
	
		"INSERT INTO USER_MASTER (  USER_ID,  USER_LOGIN_ID,  USER_FNAME, "
		+ " USER_MNAME,  USER_LNAME,  USER_MOBILE_NUMBER,"
		+ "  USER_EMAILID,  USER_PASSWORD,  CREATED_DT,  CREATED_BY, "
		+ " ACTOR_ID,  LAST_LOGIN_TIME,  LAST_LOGIN_IP,   "
		+ "STATUS,USER_ADDRESS,USER_CITY,USER_STATE,USER_START_DATE,WAREHOUSE_ID,"
		+ "  UPDATED_BY,  UPDATED_DT  )VALUES   "
		+ "(USER_MSTR_SEQ.NEXTVAL,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    SYSDATE,    ?,    ?,    ?,    ?,    "
		+ "'A', ? ,?, ?, ?, ?, ?,    SYSDATE)";
	
	//==========================================

	protected static final String SQL_SELECT =
		"SELECT USER_ID, USER_LOGIN_ID, USER_FNAME, USER_MNAME, USER_LNAME, USER_MOBILE_NUMBER, USER_EMAILID, USER_PASSWORD, USER_PSSWRD_EXPRY_DT, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, CONNECT_ID, ZONAL_ID, CIRCLE_ID, ACTOR_ID, LOGIN_ATTEMPTED, LAST_LOGIN_TIME, LAST_LOGIN_IP, STATUS FROM USER_MASTER ";

	protected static final String SQL_SELECT_USERID =
		"SELECT USER_LOGIN_ID FROM USER_MASTER ";

	protected static final String SQL_SELECT_LOGINEMAILID =
		"SELECT USER_LOGIN_ID,USER_PASSWORD,USER_EMAILID FROM USER_MASTER WHERE USER_MASTER.USER_LOGIN_ID = ? ";

	protected static final String SQL_SELECT_ACTORID =
		"SELECT USER_MASTER.USER_ID,WAREHOUSE_MASTER.WAREHOUSE_NAME, USER_MASTER.USER_LOGIN_ID, USER_MASTER.USER_FNAME, USER_MASTER.USER_MNAME, USER_MASTER.USER_LNAME, USER_MASTER.USER_MOBILE_NUMBER, USER_MASTER.USER_EMAILID, USER_MASTER.ACTOR_ID,USER_MASTER.CREATED_BY,ROLE_MASTER.ROLE_NAME, USER_MASTER.STATUS, USER_MASTER.USER_ADDRESS, USER_MASTER.USER_CITY, USER_MASTER.USER_STATE FROM USER_MASTER,ROLE_MASTER,WAREHOUSE_MASTER ";

	protected static final String SQL_UPDATE =
		"UPDATE USER_MASTER SET USER_FNAME = ?, USER_MNAME = ?, USER_LNAME = ?, USER_MOBILE_NUMBER = ?, USER_EMAILID = ?, UPDATED_DT = SYSDATE, UPDATED_BY = ?, STATUS = ?, USER_STATE=?, USER_CITY=?, USER_ADDRESS=?  WHERE USER_LOGIN_ID = ?";

	protected static final String SQL_UPDATE_PWD ="update vr_login_master set password = ?, IPWD_CHANGED_DT=current timestamp where login_id=? with ur";
//		"UPDATE USER_MASTER SET USER_PASSWORD=?,UPDATED_DT=SYSDATE,UPDATED_BY=? WHERE USER_LOGIN_ID = ? ";

	protected static final String SQL_DELETE =
		"DELETE FROM USER_MASTER WHERE USER_LOGIN_ID = ?";

	public int insertUserDetails(HBOUserMstr dto) throws DAOException {

		logger.info("Entered insert for table USER_MASTER");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int rowsInserted = 0;
		try {
			String sql = SQL_INSERT_WITH_ID;
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			int paramCount = 1;
			//String wid="";

			ps.setString(paramCount++, dto.getUserLoginId());
			ps.setString(paramCount++, dto.getUserFname());
			ps.setString(paramCount++, dto.getUserMname());
			ps.setString(paramCount++, dto.getUserLname());
			ps.setString(paramCount++, dto.getUserMobileNumber());
			ps.setString(paramCount++, dto.getUserEmailid());
			ps.setString(paramCount++, dto.getUserPassword());
			ps.setString(paramCount++, dto.getCreatedBy());
			//ps.setString(paramCount++, dto.getConnectId());
			//ps.setString(paramCount++, dto.getZonalId());
			//ps.setString(paramCount++, dto.getCircleId());
			ps.setString(paramCount++, dto.getActorId());
			
			//ps.setString(paramCount++, "3");
			ps.setString(paramCount++, dto.getLastLoginTime());
			ps.setString(paramCount++, dto.getLastLoginIp());

			ps.setString(paramCount++, dto.getUserAddress());
			ps.setString(paramCount++, dto.getUserCity());
			ps.setString(paramCount++, dto.getUserState());

			//			by sachin on 24 th
			//					 if(dto.getWarehouseId().equalsIgnoreCase("0")){
			//						 wid=null;	
			//					 }
			//					 else{
			//						 wid=dto.getWarehouseId();
			//					 }
			//					 ps.setString(paramCount++, wid);
			//			Changes on 18 March by sachin===
			
					  Date date = new Date(0000-00-00);
					  String dd=dto.getUserStartDate().substring(0,2);
					  String mm=dto.getUserStartDate().substring(3,5);
					  String yyyy=dto.getUserStartDate().substring(6,10);
					  String startDate=yyyy+"-"+mm+"-"+dd;
					  ps.setDate(paramCount++, date.valueOf(startDate));
				  //================================

			ps.setString(paramCount++, dto.getWarehouseId());

			ps.setString(paramCount++, dto.getCreatedBy());
			rowsInserted = ps.executeUpdate();
			con.commit();

			logger.info(
				"Row insertion successful on table:USER_MASTER. Inserted:"
					+ rowsInserted
					+ " rows");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (Exception e) {
			}
		}

		return rowsInserted;
	}

	public HBOUserMstr[] getUserData(String roleId, String userId)
		throws DAOException {
		logger.info("Entered findByActorId for table:USER_MASTER");
		logger.info("userId: " + userId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iRoleID = 0;
		if (roleId != null && !roleId.equals("")) {
			iRoleID = Integer.parseInt(roleId);
		}
		try {
			String sql =
				SQL_SELECT_ACTORID
					+ " WHERE USER_MASTER.ACTOR_ID = ROLE_MASTER.ROLE_ID AND WAREHOUSE_MASTER.WAREHOUSE_ID(+)=USER_MASTER.WAREHOUSE_ID ";
			switch (iRoleID) {
				case 1 :
					sql = sql + " AND USER_MASTER.ACTOR_ID in (1,2,3)";
					break;
				case 2 :
					sql =
						sql
							+ " AND USER_MASTER.ACTOR_ID in (2,6,7) and warehouse_circle_id = (select warehouse_circle_id from user_master um, warehouse_master wmi ";
					sql =
						sql
							+ " where um.WAREHOUSE_ID= wmi.warehouse_id	and um.USER_LOGIN_ID = '"
							+ userId
							+ "') ";
					break;
				case 3 :
					sql = sql + " AND USER_MASTER.ACTOR_ID in (3,4,5)";
					break;

			}
			//sql = sql + "AND USER_MASTER.CREATED_BY";
			//AND USER_MASTER.CREATED_BY=?";
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			//ps.setString(1,userId);
			rs = ps.executeQuery();
			ArrayList results = new ArrayList();
			logger.info(sql.toString());

			while (rs.next()) {
				HBOUserMstr dto = new HBOUserMstr();
				dto.setUserId(rs.getString("USER_ID"));
				dto.setUserLoginId(rs.getString("USER_LOGIN_ID"));
				dto.setUserFname(rs.getString("USER_FNAME"));
				dto.setUserMname(rs.getString("USER_MNAME"));
				dto.setUserLname(rs.getString("USER_LNAME"));
				dto.setUserMobileNumber(rs.getString("USER_MOBILE_NUMBER"));
				dto.setUserEmailid(rs.getString("USER_EMAILID"));
				dto.setActorId(rs.getString("ACTOR_ID"));
				dto.setUserActorName(rs.getString("ROLE_NAME"));
				dto.setCreatedBy(rs.getString("CREATED_BY"));
				dto.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
				dto.setStatus(rs.getString("STATUS"));
				if (rs.getString("STATUS").equals("A")) {
					dto.setFinalStatus("Active");
				} else {
					dto.setFinalStatus("DeActive");
				}
				dto.setUserAddress(rs.getString("USER_ADDRESS"));
				dto.setUserCity(rs.getString("USER_CITY"));
				dto.setUserState(rs.getString("USER_STATE"));

				//dto.setStatus(rs.getString("STATUS"));
				results.add(dto);
				logger.info(
					"i am in View User Details>>>>>"
						+ rs.getString("USER_ADDRESS"));
			}
			HBOUserMstr retValue[] = new HBOUserMstr[results.size()];
			results.toArray(retValue);
			return retValue;

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while findByActorId."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while findByActorId."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}
	/**
	 * To check duplicate user login id
	 * @param userLoginId
	 * @return
	 * @throws DAOException
	 */

	public boolean CheckUserId(String userLoginId) throws DAOException {

		logger.info("Entered CheckUserId for table:USER_MASTER");

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean isExist = false;
		try {
			String sql =
				SQL_SELECT_USERID + " WHERE USER_MASTER.USER_LOGIN_ID = ?";
			con = DBConnection.getDBConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, userLoginId);
			rs = pst.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while CheckUserId."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while CheckUserId."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return isExist;

	}

	/**
		 * Returns emailID for valid user login id
		 * @param userLoginId
		 * @return
		 * @throws DAOException
		 */

//---------------by sachin on 13th feb---------------

	public HBOUserMstr loginEmailId(String userLoginId) throws DAOException {

		logger.info("Entered CheckUserId for table:USER_MASTER");

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		HBOUserMstr dto = null;
		try {
			con = DBConnection.getDBConnection();
			pst = con.prepareStatement(SQL_SELECT_LOGINEMAILID);
			pst.setString(1, userLoginId);
			rs = pst.executeQuery();
			logger.info(
				"SQL_SELECT_LOGINEMAILID: " + SQL_SELECT_LOGINEMAILID);
			if (rs.next()) {
				dto = new HBOUserMstr();
				dto.setUserLoginId(rs.getString(1));
				dto.setOldPassword(rs.getString(2));
				dto.setUserEmailid(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while loginEmailId."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while loginEmailId."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return dto;

	}

///-------------end of method-----------------

	/* (non-Javadoc)
	 * @see com.ibm.hbo.dao.HBOUserMstrDao#updateUser(com.ibm.hbo.dto.HBOUserMstr)
	 */
	public int updateUser(HBOUserMstr dto) throws DAOException {

		logger.info("Entered update for table USER_MASTER");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;
		int updateCnt = 1;

		try {
			String sql = SQL_UPDATE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			if (dto.getUserFname() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt++, dto.getUserFname());
			if (dto.getUserMname() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt++, dto.getUserMname());
			if (dto.getUserLname() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt++, dto.getUserLname());
			if (dto.getUserMobileNumber() == null)
				ps.setNull(updateCnt++, Types.DECIMAL);
			else
				ps.setString(updateCnt++, dto.getUserMobileNumber());
			if (dto.getUserEmailid() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt++, dto.getUserEmailid());
			if (dto.getUpdatedBy() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt++, dto.getUpdatedBy());
			if (dto.getStatus() == null)
				ps.setNull(updateCnt++, Types.CHAR);
			else
				ps.setString(updateCnt++, dto.getStatus());

			if (dto.getUserState() == null)
				ps.setNull(updateCnt++, Types.CHAR);
			else
				ps.setString(updateCnt++, dto.getUserState());

			if (dto.getUserCity() == null)
				ps.setNull(updateCnt++, Types.CHAR);
			else
				ps.setString(updateCnt++, dto.getUserCity());

			if (dto.getUserAddress() == null)
				ps.setNull(updateCnt++, Types.CHAR);
			else
				ps.setString(updateCnt++, dto.getUserAddress());

			ps.setString(updateCnt++, dto.getUserLoginId());

			//logger.info("user LOGIN ID ==="+dto.getStatus());

			numRows = ps.executeUpdate();

			logger.info(
				"Update successful on table:USER_MASTER. Updated:"
					+ numRows
					+ " rows");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while updateUser."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while updateUser."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return numRows;
	}

	public int updatePassword(HBOUserMstr dto) throws DAOException {

		logger.info("Entered update password for table USER_MASTER");
		logger.info(dto.getNewPassword());
		logger.info(dto.getUserLoginId());

		Connection con = null;
		PreparedStatement ps = null;
		CallableStatement cs = null;
		int numRows = -1;
		int updateCnt = 1;

		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(SQL_UPDATE_PWD);
			if (dto.getNewPassword() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt++, dto.getNewPassword());
			if (dto.getUserLoginId() == null)
				ps.setNull(updateCnt++, Types.VARCHAR);
			else
				ps.setString(updateCnt, dto.getUserLoginId());

	//		ps.setString(updateCnt++, dto.getUserLoginId());

			numRows = ps.executeUpdate();

			if (1 == numRows) {
				cs = con.prepareCall("{call PROC_HBOPASSWORD_HISTORY(?,?)}");
				cs.setString(1, dto.getUserLoginId());
				cs.setString(2, dto.getOldPassword());
				logger.info("userId" + dto.getUserLoginId());
				logger.info("oldpassword" + dto.getOldPassword());

				cs.execute();

			}

			logger.info(
				"Update successful for password on table:USER_MASTER. Updated:"
					+ numRows
					+ " rows");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while updatePassword."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while updatePassword."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (cs != null)
					cs.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return numRows;
	}

	public int delete(String userId) throws DAOException {

		logger.info("Entered delete for table USER_MASTER");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			String sql = SQL_DELETE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			numRows = ps.executeUpdate();

			logger.info(
				"Delete successful on table:USER_MASTER. Deleted:"
					+ numRows
					+ " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while delete."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while delete."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return numRows;
	}

	protected HBOUserMstr[] fetchMultipleResults(ResultSet rs)
		throws SQLException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			HBOUserMstr dto = new HBOUserMstr();
			populateDto(dto, rs);
			results.add(dto);
		}
		HBOUserMstr retValue[] = new HBOUserMstr[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected HBOUserMstr fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			HBOUserMstr dto = new HBOUserMstr();
			populateDto(dto, rs);
			return dto;
		} else
			return null;
	}

	protected static void populateDto(HBOUserMstr dto, ResultSet rs)
		throws SQLException {
		dto.setUserId(rs.getString("USER_ID"));

		dto.setUserLoginId(rs.getString("USER_LOGIN_ID"));

		dto.setUserFname(rs.getString("USER_FNAME"));

		dto.setUserMname(rs.getString("USER_MNAME"));

		dto.setUserLname(rs.getString("USER_LNAME"));

		dto.setUserMobileNumber(rs.getString("USER_MOBILE_NUMBER"));

		dto.setUserEmailid(rs.getString("USER_EMAILID"));

		dto.setUserPassword(rs.getString("USER_PASSWORD"));

		dto.setUserPsswrdExpryDt(rs.getString("USER_PSSWRD_EXPRY_DT"));

		dto.setCreatedDt(rs.getString("CREATED_DT"));

		dto.setCreatedBy(rs.getString("CREATED_BY"));

		dto.setUpdatedDt(rs.getString("UPDATED_DT"));

		dto.setUpdatedBy(rs.getString("UPDATED_BY"));

		dto.setConnectId(rs.getString("CONNECT_ID"));

		dto.setZonalId(rs.getString("ZONAL_ID"));

		dto.setCircleId(rs.getString("CIRCLE_ID"));

		dto.setActorId(rs.getString("ACTOR_ID"));

		dto.setLoginAttempted(rs.getString("LOGIN_ATTEMPTED"));

		dto.setLastLoginTime(rs.getString("LAST_LOGIN_TIME"));

		dto.setLastLoginIp(rs.getString("LAST_LOGIN_IP"));

		dto.setStatus(rs.getString("STATUS"));

	}

}
