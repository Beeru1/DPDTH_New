package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOPopulateUserDao;
import com.ibm.hbo.dto.HBOLogin;
import com.ibm.hbo.exception.DAOException;

public class HBOPopulateUserDaoImpl implements HBOPopulateUserDao {

	
	/*
		 * Logger for the class.
		 */
	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOPopulateUserDaoImpl.class);
	}
	
	/*SQL Used within ArcPopulateUserDaoImpl*/
	//"SELECT HBO_MODULE_MSTR.*,HBO_USER_MSTR.*,HBO_ACTOR_MSTR.*,HBO_CIRCLE_MSTR.* FROM HBO_MODULE_MSTR,HBO_USER_MSTR,HBO_ACTOR_MSTR,HBO_CIRCLE_MSTR,HBO_MODULE_ACTOR_MAPPING where USER_LOGIN_ID = ? AND USER_PASSWORD = ? AND HBO_USER_MSTR.ACTOR_ID = HBO_ACTOR_MSTR.ACTOR_ID AND HBO_USER_MSTR.ACTOR_ID = HBO_MODULE_ACTOR_MAPPING.ACTOR_ID AND HBO_MODULE_ACTOR_MAPPING.MODULE_ID = HBO_MODULE_MSTR.MODULE_ID AND HBO_USER_MSTR.CIRCLE_ID = HBO_CIRCLE_MSTR.CIRCLE_ID AND HBO_USER_MSTR.STATUS = 'A' ORDER BY HBO_MODULE_MSTR.MODULE_ID  ";
	protected static final String SQL_SELECT = "SELECT MODULE_MASTER.*,USER_MASTER.*,ROLE_MASTER.*,MODULE_ACTOR_MAPPING.* FROM MODULE_MASTER,USER_MASTER,ROLE_MASTER,CIRCLE_MASTER,MODULE_ACTOR_MAPPING where USER_LOGIN_ID = ? AND USER_PASSWORD = ? AND USER_MASTER.ACTOR_ID = ROLE_MASTER.ROLE_ID AND USER_MASTER.ACTOR_ID = MODULE_ACTOR_MAPPING.ROLE_ID AND MODULE_ACTOR_MAPPING.MODULE_ID = MODULE_MASTER.MODULE_ID AND USER_MASTER.CIRCLE_ID = CIRCLE_MASTER.CIRCLE_ID(+) AND USER_MASTER.STATUS = 'A' ORDER BY MODULE_MASTER.MODULE_ID";
		  
	protected static final String SQL_SELECT_COLOUMN =
		"SELECT MAX(COLUMN_ID) COLUMN_COUNT,TABLE_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME IN ('MODULE_MASTER','USER_MASTER','ROLE_MASTER','MODULE_ACTOR_MAPPING') GROUP BY TABLE_NAME";

	protected static final String SQL_SELECT_CATEGORY =
		"SELECT CATEGORY_ID,CATEGORY_NAME,CATEGORY_DESCRIPTION FROM CATEGORY_MASTER";

	/*
			 * Start value of column
			*/
	public static final int CAOL_COLOUMN_START = 1;

	public List populateValues(HBOLogin dto) throws DAOException {

		logger.info("Populating user details"+ dto.getUserId());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rst = null;
		ResultSetMetaData rsmd = null;
		List returnList = null;
		try {
			logger.info("User Logged-in:"+ dto.getUserId());
			String sql = "select login_flag from user_master where USER_LOGIN_ID = ? ";
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getUserId());
			rst = ps.executeQuery();
			String loginFlag = "";
			//logger.info("SQL_SELECT"+sql+dto.getUserId());
			while(rst.next()) {
				loginFlag = rst.getString("login_flag");
				logger.info("loginFlag:"+loginFlag);
			}
			if(!loginFlag.equals("Y")) { //Already Logged-in
				logger.info("SQL_SELECT"+SQL_SELECT);
				ps = con.prepareStatement(SQL_SELECT);
				ps.setString(1, dto.getUserId());
				ps.setString(2, dto.getPassword());
				rst = ps.executeQuery();
				rsmd = rst.getMetaData();
				returnList = fetchUserDetails(rsmd, rst);
				logger.info("returnList"+returnList.size());
				if(returnList.size() > 0) {
					sql = "update user_master set login_flag='Y',last_login_ip=?, last_login_time=sysdate where USER_LOGIN_ID = ? ";
					ps = con.prepareStatement(sql);
					logger.info("dto.getUserIP()"+dto.getUserIP());
					logger.info("dto.getUserId()"+dto.getUserId());
					logger.info("sql:---------"+sql);
					ps.setString(1, dto.getUserIP());
					ps.setString(2, dto.getUserId());
					ps.executeUpdate();
					
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while fetching user details."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while fetching user details."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rst != null) {
					ps.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}

	protected List fetchUserDetails(ResultSetMetaData rsmd, ResultSet rst) throws DAOException {
		List results = new ArrayList();
		List HBO_MODULE_MSTR = null;
		List HBO_USER_MSTR = null;
		List HBO_ACTORS = null;
		List HBO_MODULE_ACTOR_MAPPING = null;
		List resultList = null;

		//Method getColumnCount() called
		HashMap hMap = getColumnCount();

		Iterator iterator = (hMap.keySet()).iterator();
		int HBO_MODULE_MSTR_COLOUMN = 0;
		int HBO_USER_MSTR_COLOUMN = 0;
		int HBO_ACTORS_COLOUMN = 0;
		int HBO_MDL_ACTOR_MAP_COLUMN = 0;
		
		try {

			while (iterator.hasNext()) {
				String colCount = iterator.next().toString();
				if (("MODULE_MASTER").equals(colCount)) {

					HBO_MODULE_MSTR_COLOUMN = ((Integer) hMap.get(colCount)).intValue();

				}
				if (("USER_MASTER").equals(colCount)) {

					HBO_USER_MSTR_COLOUMN = ((Integer) hMap.get(colCount)).intValue();

				}
				if (("ROLE_MASTER").equals(colCount)) {

					HBO_ACTORS_COLOUMN = ((Integer) hMap.get(colCount)).intValue();
				}
				if (("MODULE_ACTOR_MAPPING").equals(colCount)) {

					HBO_MDL_ACTOR_MAP_COLUMN = ((Integer) hMap.get(colCount)).intValue();
				}
			}
			String[] colNames = new String[rsmd.getColumnCount()];
			for (int cols = 1; cols <= rsmd.getColumnCount(); cols++) {
				colNames[cols - 1] = rsmd.getColumnName(cols);
			}
			logger.info("rsmd.getColumnCount():"+rsmd.getColumnCount());
			HBO_MODULE_MSTR = new ArrayList();
			HBO_USER_MSTR = new ArrayList();
			HBO_ACTORS = new ArrayList();
			HBO_MODULE_ACTOR_MAPPING = new ArrayList();
			while (rst.next()) {
				HashMap moduleMap = new HashMap();
				HashMap userMap = new HashMap();
				HashMap actorMap = new HashMap();
				HashMap moduleactorMap = new HashMap();

				for (int val = 1; val <= rsmd.getColumnCount(); val++) {
					//HBO_MODULE_MSTR
					if (val >= CAOL_COLOUMN_START && val < HBO_MODULE_MSTR_COLOUMN) {
						moduleMap.put(colNames[val - 1], rst.getObject(val));
					}
					//HBO_USER_MSTR
					if (val >= HBO_MODULE_MSTR_COLOUMN && val < HBO_USER_MSTR_COLOUMN) {
						userMap.put(colNames[val - 1], rst.getObject(val));
					}
					//HBO_ACTOR_MSTR
					if (val >= HBO_USER_MSTR_COLOUMN && val < HBO_ACTORS_COLOUMN) {
						actorMap.put(colNames[val - 1], rst.getObject(val));
					}
					//HBO_CIRCLE_MSTR
					if (val >= HBO_ACTORS_COLOUMN && val < HBO_MDL_ACTOR_MAP_COLUMN) {
						moduleactorMap.put(colNames[val - 1], rst.getObject(val));
					}
				}
				HBO_MODULE_MSTR.add(moduleMap);
				HBO_USER_MSTR.add(userMap);
				HBO_ACTORS.add(actorMap);
				HBO_MODULE_ACTOR_MAPPING.add(moduleactorMap);

				moduleMap = null;
				userMap = null;
				actorMap = null;
				moduleactorMap = null;
			}
			resultList = new ArrayList();
			resultList.add(HBO_MODULE_MSTR);
			resultList.add(HBO_USER_MSTR);
			resultList.add(HBO_ACTORS);
			resultList.add(HBO_MODULE_ACTOR_MAPPING);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while fetching user details."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		}
	}

	/**
	 * This method return the column count of the tables
	 * @return HashMap
	 */
	private HashMap getColumnCount() throws DAOException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		HashMap columnMap = null;
		try {
			con = DBConnection.getDBConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_COLOUMN);
			int HBO_MODULE_MSTR_COLOUMN_COUNT = 0;
			int HBO_USER_MSTR_COLOUMN_COUNT = 0;
			int HBO_ACTORS_COLOUMN_COUNT = 0;
			int HBO_MDL_ACTOR_MAP_COLUMN_COUNT = 0;
			while (rs.next()) {
				//Contains total number of column in HBO_MODULE_MSTR
				if (("MODULE_MASTER").equals(rs.getString("TABLE_NAME"))) {
					HBO_MODULE_MSTR_COLOUMN_COUNT = rs.getInt("COLUMN_COUNT");
				}
				//Contains total number of column in HBO_USER_MSTR
				if (("USER_MASTER").equals(rs.getString("TABLE_NAME"))) {
					HBO_USER_MSTR_COLOUMN_COUNT = rs.getInt("COLUMN_COUNT");
				}
				//Contains total number of column in HBO_ACTOR_MSTR
				if (("ROLE_MASTER").equals(rs.getString("TABLE_NAME"))) {
					HBO_ACTORS_COLOUMN_COUNT = rs.getInt("COLUMN_COUNT");
				}
				//Contains total number of column in HBO_CIRCLE_MSTR
				if (("MODULE_ACTOR_MAPPING").equals(rs.getString("TABLE_NAME"))) {
					HBO_MDL_ACTOR_MAP_COLUMN_COUNT = rs.getInt("COLUMN_COUNT");
				}

			}
			int HBO_MODULE_MSTR_COLUMN_ENDS = CAOL_COLOUMN_START + HBO_MODULE_MSTR_COLOUMN_COUNT;
			int HBO_USER_MSTR_COLUMN_ENDS =
				HBO_MODULE_MSTR_COLUMN_ENDS + HBO_USER_MSTR_COLOUMN_COUNT;
			int HBO_ACTORS_COLUMN_END = HBO_USER_MSTR_COLUMN_ENDS + HBO_ACTORS_COLOUMN_COUNT;
			int HBO_MDL_ACTOR_MAP_COLUMN_END = HBO_ACTORS_COLUMN_END + HBO_MDL_ACTOR_MAP_COLUMN_COUNT;
			
			columnMap = new HashMap();
			columnMap.put("MODULE_MASTER", new Integer(HBO_MODULE_MSTR_COLUMN_ENDS));
			columnMap.put("USER_MASTER", new Integer(HBO_USER_MSTR_COLUMN_ENDS));
			columnMap.put("ROLE_MASTER", new Integer(HBO_ACTORS_COLUMN_END));
			columnMap.put("MODULE_ACTOR_MAPPING", new Integer(HBO_MDL_ACTOR_MAP_COLUMN_END));

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			logger.error(
				"SQLException occured while getting coloumn count.SQLException Message: "
					+ sqle.getMessage());
			throw new DAOException("SQLException: " + sqle.getMessage(), sqle);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting coloumn count.Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				/*
				 * Close the resultset, statement, connection.
				 */
				DBConnection.releaseResources(con, stmt, rs);
			} catch (DAOException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return columnMap;

	}
	/**
	 * 
	 * @return
	 */
	public HashMap category() throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		HashMap categoryMap = new HashMap();
		ArrayList categoryDesc = null;
		try {
			con = DBConnection.getDBConnection();
			pst = con.prepareStatement(SQL_SELECT_CATEGORY);
			rs = pst.executeQuery();
			while (rs.next()) {
				categoryDesc = new ArrayList();
				categoryDesc.add(rs.getString(2));
				categoryDesc.add(rs.getString(3));
				categoryMap.put(rs.getString(1), categoryDesc);

			}

		} catch (SQLException sqle) {
			logger.error(
				"SQLException occured while getting category.SQLException Message: "
					+ sqle.getMessage());
			throw new DAOException("SQLException: " + sqle.getMessage(), sqle);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting category.Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {

				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error("Exception in Finally from category() >> " + e);
			}
		}
		return categoryMap;
	}

	public String logoutUser(String userID) throws DAOException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String returnMessage="success";
		
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement("update user_master set login_flag='N' where user_login_id=?");
				ps.setString(1, userID);
				ps.executeUpdate();
			
			} catch (Exception e) {
				returnMessage="failure";
				logger.error(
					"Exception occured while getting category.Exception Message: " + e.getMessage());
				throw new DAOException("Exception: " + e.getMessage(), e);
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					logger.error("Exception in Finally from category() >> " + e);
				}
			}
			return returnMessage;
		}


}
