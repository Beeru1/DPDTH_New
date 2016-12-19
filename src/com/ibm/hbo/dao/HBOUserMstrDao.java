/* 
 * ArcUserMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;

import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.exception.DAOException;

/** 
  * DAO for the <b>ARC_USER_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: ARC_USER_MSTR
  * ----------------------------------------------
  *     column: USER_ID        NUMBER null
  *     column: USER_LOGIN_ID        VARCHAR2 null
  *     column: USER_FNAME        VARCHAR2 null
  *     column: USER_MNAME        VARCHAR2 null
  *     column: USER_LNAME        VARCHAR2 null
  *     column: USER_MOBILE_NUMBER        NUMBER null
  *     column: USER_EMAILID        VARCHAR2 null
  *     column: USER_PASSWORD        VARCHAR2 null
  *     column: USER_PSSWRD_EXPRY_DT        DATE null
  *     column: CREATED_DT        DATE null
  *     column: CREATED_BY        NUMBER null
  *     column: UPDATED_DT        DATE null
  *     column: UPDATED_BY        NUMBER null
  *     column: CONNECT_ID        NUMBER null
  *     column: ZONAL_ID        NUMBER null
  *     column: CIRCLE_ID        NUMBER null
  *     column: CAOL_ACTOR_ID        NUMBER null
  *     column: LOGIN_ATTEMPTED        NUMBER null
  *     column: LAST_LOGIN_TIME        DATE null
  *     column: LAST_LOGIN_IP        VARCHAR2 null
  *     column: STATUS        CHAR null
  * 
  * Primary Key(s):  USER_ID
  * </pre>
  * 
  */
public interface HBOUserMstrDao {

	/** 
	
		* Inserts a row in the ARC_USER_MSTR table.
		* @param dto  DTO Object holding the data to be inserted.
		* @return  The no. of rows inserted. 
		* @throws HBOUserMstrDaoException In case of an error
	**/

	public int insertUserDetails(HBOUserMstr dto) throws DAOException;

	/** 
	
		* Updates a row in the ARC_USER_MSTR table.
		* The Primary Key Object determines wich row gets updated.
		* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
		* @return  The no. of rows updated. 
		* @throws HBOUserMstrDaoException In case of an error
	**/

	public int updateUser(HBOUserMstr dto) throws DAOException;

	/** 
	
		* Deletes a row in the database based on the primary key supplied.
		* @param userId  The userId value for which the row needs to be deleted
		* @return  The no. of rows deleted.
		* @throws HBOUserMstrDaoException In case of an error
	**/

	public int delete(String userId) throws DAOException;

	/** 
	
		* Returns a multiple rows of the database from table ARC_USER_MSTR and ARC_ACTOR_MSTR. If there is no match, <code>null</code> is returned.
		* @return DTO Object representing a multiple rows of the table from table ARC_USER_MSTR and ARC_ACTOR_MSTR.If there is no match, <code>null</code> is returned.
	**/

	public HBOUserMstr[] getUserData(String actorId,String userId) throws DAOException;
	
	/**
	 * Checking for duplicate user Id 
	 * @param userLoginId
	 * @return
	 * @throws DAOException
	 */
	
	public boolean CheckUserId(String userLoginId) throws DAOException;
	
	/**
	 * To update the password
	 * @param dto
	 * @return
	 * @throws DAOException
	 */
	public int updatePassword(HBOUserMstr dto) throws DAOException;
	
	/**
	 * Return email id for valid login id
	 * @param userLoginId
	 * @return
	 * @throws DAOException
	 */
	public HBOUserMstr loginEmailId(String userLoginId) throws DAOException;

}
