/**************************************************************************
 * File     : VUserMstrDao.java
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

package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import java.sql.SQLException;
import java.util.List;

import com.ibm.virtualization.ussdactivationweb.dto.VUserMstrDTO;
import com.ibm.virtualization.ussdactivationweb.exception.VUserMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * DAO for the <b>V_USER_MSTR</b> database table. Provides methods to perform
 * insert, update, delete and select queries. <br>
 * 
 * <pre>
 *   Table: V_USER_MSTR
 *   ----------------------------------------------
 *       column: USER_ID        NUMBER null
 *       column: LOGIN_ID        VARCHAR2 null
 *       column: STATUS        VARCHAR2 null
 *       column: CREATED_BY        NUMBER null
 *       column: CREATED_DT        DATE null
 *       column: UPDATED_DT        DATE null
 *       column: EMAIL_ID        VARCHAR2 null
 *       column: UPDATED_BY        NUMBER null
 *       column: CIRCLE_CODE        NUMBER null
 *       column: PASSWORD        VARCHAR2 null
 *       column: PASSWORD_CHANGED_DT        DATE null
 *       column: USER_TYPE        VARCHAR2 null
 *       column: GROUP_ID        NUMBER null
 *       column: LOGIN_ATTEMPTED        NUMBER null
 *   
 *   Primary Key(s):  USER_ID
 * </pre>
 * 
 */
public interface VUserMstrDao {

	// SQL statement strings for user managers
	public static final String SQL_INSERT_USER_WITHOUT_ID = new StringBuffer(
			500)
			.append("INSERT INTO ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_USER_MSTR (USER_ID, LOGIN_ID, STATUS, CREATED_BY, CREATED_DT, UPDATED_DT, EMAIL_ID, UPDATED_BY, CIRCLE_CODE, PASSWORD, PASSWORD_CHANGED_DT, USER_TYPE, GROUP_ID, LOGIN_ATTEMPTED,LOGIN_STATUS,ZONE_CODE) VALUES (next value for SEQ_V_USER_MSTR, ?, 'A', ?,")
			.append(
					"CURRENT TIMESTAMP, CURRENT TIMESTAMP, ?, ?, ?, ?, CURRENT TIMESTAMP, ?, ?, ?, ?,? ) ")
			.append(" WITH UR").toString();

	public static final String SQL_INSERT_USER_WITH_ID = new StringBuffer(500)
			.append("INSERT INTO ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_USER_MSTR (USER_ID, LOGIN_ID, STATUS, CREATED_BY, CREATED_DT, UPDATED_DT, EMAIL_ID, UPDATED_BY, CIRCLE_CODE, PASSWORD, PASSWORD_CHANGED_DT, USER_TYPE, GROUP_ID, LOGIN_ATTEMPTED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
			.append(" WITH UR").toString();

	public static final String SQL_SELECT_USER = new StringBuffer(500)
			.append(
					"SELECT USER_ID, LOGIN_ID, STATUS, CREATED_BY, CREATED_DT, UPDATED_DT, EMAIL_ID,UPDATED_BY, CIRCLE_CODE, PASSWORD,PASSWORD_CHANGED_DT, USER_TYPE,GROUP_ID,LOGIN_ATTEMPTED,LAST_LOGIN_TIME,ZONE_CODE FROM ")
			.append(SQLConstants.FTA_SCHEMA).append(".V_USER_MSTR ").toString();

	// for pagination

	public static final String SQL_SELECT_ALL_USERS = new StringBuffer(500)
			.append("SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (" )
			.append("SELECT USER_ID, LOGIN_ID, STATUS, CREATED_BY, CREATED_DT, " )
			.append("UPDATED_DT, EMAIL_ID, UPDATED_BY, CIRCLE_CODE,PASSWORD_CHANGED_DT, ")
			.append("USER_TYPE, GROUP_ID, LOGIN_ATTEMPTED,LAST_LOGIN_TIME,ZONE_CODE, ")
			.append("CASE WHEN (LOGIN_ATTEMPTED >=4) THEN 'Y' ELSE 'N' END AS LOCK_STATUS ")
			.append("FROM V_USER_MSTR  ORDER BY V_USER_MSTR.LOGIN_ID) a)b Where rnum<=? and rnum>=?")
			.append(" WITH UR").toString();
	
	

	public static final String SQL_UPDATE_USER1 = new StringBuffer(500)
			.append("UPDATE ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR SET USER_ID = ?, LOGIN_ID = ?, STATUS = ?, ")
			.append("UPDATED_DT = CURRENT TIMESTAMP, EMAIL_ID = ?, UPDATED_BY = ?, ")
			.append("CIRCLE_CODE = ?, USER_TYPE = ?, GROUP_ID = ?, LOGIN_ATTEMPTED = ?, ")
			.append("PASSWORD = ?, ZONE_CODE= ?  WHERE USER_ID = ? ")
			.toString();
	
	public static final String UPDATE_LOCKED_USER = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_USER_MSTR SET LOGIN_ATTEMPTED = ?,LOGIN_STATUS = ? ")
	.append("WHERE LOGIN_ID = ? ")
	.toString();

	public static final String USER_COUNT = new StringBuffer(500).append(
			"SELECT COUNT(*) FROM ").append(SQLConstants.FTA_SCHEMA).append(
			".V_USER_MSTR ").append(" WITH UR").toString();

	public static final String SQL_UPDATE_USER = new StringBuffer(500)
			.append("UPDATE ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_USER_MSTR SET USER_ID = ?, LOGIN_ID = ?, STATUS = ?, CREATED_BY = ?, CREATED_DT = ?, UPDATED_DT = ?, EMAIL_ID = ?, UPDATED_BY = ?, CIRCLE_CODE = ?, PASSWORD = ?, PASSWORD_CHANGED_DT = ?, USER_TYPE = ?, GROUP_ID = ?, LOGIN_ATTEMPTED = ? WHERE USER_ID = ?")
			.append(" WITH UR").toString();

	public static final String SQL_DELETE_USER = new StringBuffer(500).append(
			"DELETE FROM ").append(SQLConstants.FTA_SCHEMA).append(
			".V_USER_MSTR WHERE USER_ID = ?").append(" WITH UR").toString();

	public static final String GET_GROUP_NAME = new StringBuffer(500).append(
			"select GROUP_NAME from ").append(SQLConstants.FTA_SCHEMA).append(
			".V_GROUP_MSTR where GROUP_ID = ? ").append(" WITH UR").toString();

	public static final String GET_CIRCLE_NAME = new StringBuffer(500).append(
			"SELECT CIRCLE_NAME from ").append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_CIRCLE_MSTR where CIRCLE_CODE = ? ")
			.append(" WITH UR").toString();
	
	public static final String GET_ZONE_NAME = new StringBuffer(500).append(
			"select LOCATION_NAME from Prodcat.PC_LOCATION_DATA where LOC_DATA_ID= ? ")
//	"SELECT LOCATION_NAME from ").append(SQLConstants.PRODCAT_SCHEMA)
//	.append(".PC_LOCATION_DATA where LOC_DATA_ID =? ")
	.append(" WITH UR").toString();

	public static final String SQL_USER_LOGIN_ID = new StringBuffer(500)
			.append("SELECT LOGIN_ID FROM ").append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR ").toString();

	/**
	 * ********************** QUERIES FOR LOGIN AND CHANGE
	 * PASSWORD***************************
	 */

	public static final String USERMASTER_VERIFYUSER = new StringBuffer()
			.append(
					" SELECT V_GROUP_RIGHTS.*,V_MODULE_MSTR.MODULE_URL,V_MODULE_MSTR.MODULE_NAME,")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_MODULE_MSTR.SECTION,")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR.* FROM")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_MODULE_MSTR,")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR,")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_GROUP_RIGHTS WHERE ")
			.append(
					" UCASE(LOGIN_ID)  = UCASE(?) AND V_GROUP_RIGHTS.MODULE_ID  = V_MODULE_MSTR.MODULE_ID ")
			.append(" ORDER BY V_MODULE_MSTR.MODULE_ID").append(" WITH UR")
			.toString();

	public static final String USERMASTER_VERIFYUSER1 = new StringBuffer()
			.append("SELECT MODULE_NAME,MODULE_URL,SECTION FROM ").append(
					SQLConstants.FTA_SCHEMA).append(".V_MODULE_MSTR ").append(
					"WHERE MODULE_ID IN ").append("(SELECT MODULE_ID FROM ")
			.append(SQLConstants.FTA_SCHEMA).append(
					".V_GROUP_RIGHTS WHERE	GROUP_ID  =(SELECT GROUP_ID FROM ")
			.append(SQLConstants.FTA_SCHEMA).append(
					".V_USER_MSTR WHERE lower(trim(LOGIN_ID))=?)) ORDER BY MODULE_ID ")
			.append(" WITH UR").toString();

	public static final String UPDATE_LOGIN_STATUS_AND_LAST_ACCESS_TIME = new StringBuffer()
			.append(" UPDATE ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR ")
			.append(" SET LOGIN_USER_IP=?,LOGIN_STATUS=?,LAST_LOGIN_TIME=CURRENT TIMESTAMP ")
			.append("WHERE LOGIN_ID=? ").toString();

	public static final String UPDATE_FIRST_TIME_LOGIN_STATUS = new StringBuffer()
			.append("UPDATE ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR ")
			.append("SET FIRST_TIME_LOGIN = FIRST_TIME_LOGIN+1 ")
			.append("WHERE LOGIN_ID=? ").toString();
	
	public static final String CHANGE_PASSWORD = new StringBuffer()
			.append("UPDATE ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR ")
			.append(
					" SET PASSWORD = ?,UPDATED_DT = CURRENT TIMESTAMP WHERE  USER_ID  = ? ")
			.toString();
	

	//	
	// public static final String RETRIEVE_USER_DETAILS=new StringBuffer()
	// .append("SELECT um.USER_ID
	// ,um.GROUP_ID,um.LOGIN_ID,um.USER_TYPE,um.PASSWORD,um.FIRST_TIME_LOGIN,um.CIRCLE_CODE
	// ,")
	// .append("CASE um.USER_TYPE ")
	// .append("WHEN 'CA' THEN (SELECT cm.CIRCLE_NAME FROM ")
	// .append(SQLConstants.PRODCAT_SCHEMA)
	// .append(".PC_CIRCLE_MSTR_NICK cm WHERE cm.CIRCLE_CODE = um.CIRCLE_CODE)")
	// .append("END CIRCLE_NAME FROM ")
	// .append(SQLConstants.FTA_SCHEMA)
	// .append(".V_USER_MSTR um WHERE um.LOGIN_ID=? ")
	// .append("WITH UR").toString();

	
	
	public static final String RETRIEVE_USER_DETAILS = new StringBuffer()
			.append("SELECT USER_ID,LOGIN_ATTEMPTED, ")
			.append("GROUP_ID,LOGIN_ID,USER_TYPE,FIRST_TIME_LOGIN,CIRCLE_CODE,LOGIN_STATUS,ZONE_CODE FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR where lower(trim(LOGIN_ID))=? and PASSWORD=? ")
			.append("WITH UR").toString();

	public static final String RETRIEVE_CIRCLE_DETAILS = new StringBuffer()
	.append(" SELECT CIRCLE_CODE, CIRCLE_NAME  ,cm.HUB_CODE ,hm.HUB_NAME FROM ").append(
	SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm , ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_HUB_MSTR hm where CIRCLE_CODE=? AND cm.HUB_CODE = hm.HUB_CODE ")
	.append(" WITH UR")
	.toString();

	public static final String GET_PASSWORD = new StringBuffer().append(
			"SELECT PASSWORD").append(" FROM ").append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR WHERE USER_ID=?").append(" WITH UR")
			.toString();

	// public static final String GET_CIRCLE_STATUS=new StringBuffer().append("
	// SELECT cm.STATUS FROM ")
	// .append(SQLConstants.PRODCAT_SCHEMA)
	// .append(".PC_CIRCLE_MSTR_NICK cm,V_USER_MSTR")
	// .append(" WHERE cm.CIRCLE_CODE=V_USER_MSTR.CIRCLE_CODE AND LOGIN_ID=?
	// ").append(" WITH UR").toString();

	public static final String GET_USER_CIRCLE = new StringBuffer().append(
			" SELECT CIRCLE_CODE FROM ").append(SQLConstants.FTA_SCHEMA)
			.append(".V_USER_MSTR").append(" WHERE LOGIN_ID=? ").append(
					" WITH UR").toString();

	public static final String GET_CIRCLE_STATUS = new StringBuffer().append(
			" SELECT STATUS FROM ").append(SQLConstants.PRODCAT_SCHEMA).append(
			".PC_CIRCLE_MSTR").append(" WHERE CIRCLE_CODE=? ").append(
			" WITH UR").toString();

	public static String GET_USER_BY_LOGINID = new StringBuffer(
			"SELECT EMAIL_ID FROM ").append(SQLConstants.FTA_SCHEMA).append(
			".V_USER_MSTR WHERE LOGIN_ID=?").append(" WITH UR").toString();

	public static String FORGOT_PWD = new StringBuffer("UPDATE ").append(
			SQLConstants.FTA_SCHEMA).append(
			".V_USER_MSTR SET PASSWORD=?,FIRST_TIME_LOGIN=0 WHERE LOGIN_ID=?")
			.toString();

	/**
	 * 
	 * Inserts a row in the V_USER_MSTR table.
	 * 
	 * @param dto
	 *            DTO Object holding the data to be inserted.
	 * @return The no. of rows inserted.
	 * @throws VUserMstrDaoException
	 *             In case of an error
	 */

	public int insert(VUserMstrDTO dto) throws VUserMstrDaoException;

	/**
	 * 
	 * Updates a row in the V_USER_MSTR table. The Primary Key Object determines
	 * wich row gets updated.
	 * 
	 * @param dto
	 *            DTO Object holding the data to be updated. The primary key
	 *            vales determine which row will be updated
	 * @return The no. of rows updated.
	 * @throws VUserMstrDaoException
	 *             In case of an error
	 */

	public int update(VUserMstrDTO dto) throws VUserMstrDaoException;

	/**
	 * 
	 * Deletes a row in the database based on the primary key supplied.
	 * 
	 * @param userId
	 *            The userId value for which the row needs to be deleted
	 * @return The no. of rows deleted.
	 * @throws VUserMstrDaoException
	 *             In case of an error
	 */

	public int delete(String userId) throws VUserMstrDaoException;

	/**
	 * 
	 * Returns a single row of the database based on the primary key information
	 * supplied. If there is no match, <code>null</code> is returned.
	 * 
	 * @param userId
	 *            The userId value for which the row needs to be retrieved.
	 * @return DTO Object representing a single row of the table based on the
	 *         primary key information supplied.If there is no match,
	 *         <code>null</code> is returned.
	 */

	public VUserMstrDTO findByPrimaryKey(String userId)
			throws VUserMstrDaoException;

	/**
	 * Returns mu;lltiple rows of available records
	 * 
	 * @return
	 * @throws SQLException
	 */

	public List findAllUsers(int lb, int ub) throws VUserMstrDaoException;

}
