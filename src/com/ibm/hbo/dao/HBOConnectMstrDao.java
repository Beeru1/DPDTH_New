/* 
 * ArcConnectMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;

import com.ibm.hbo.dto.HBOConnectMstr;
import com.ibm.hbo.exception.DAOException;

/** 
  * DAO for the <b>ARC_CONNECT_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: ARC_CONNECT_MSTR
  * ----------------------------------------------
  *     column: ARC_ID        NUMBER null
  *     column: ARC_NAME        VARCHAR2 null
  *     column: ARC_ADDRESS        VARCHAR2 null
  *     column: ARC_LOCATION_ID        NUMBER null
  *     column: ARC_DEMOGRAPHY_ID        NUMBER null
  *     column: ARC_OWNER        VARCHAR2 null
  *     column: ARC_SIZE        VARCHAR2 null
  *     column: ARC_PHONE_NO        VARCHAR2 null
  *     column: ARC_EMAIL_ID        VARCHAR2 null
  *     column: ARC_PAYMENT_KIOSK        VARCHAR2 null
  *     column: ARC_VAS_KIOSK        VARCHAR2 null
  *     column: ARC_QMS        VARCHAR2 null
  *     column: SELLING_ABTS        VARCHAR2 null
  *     column: ARC_MANAGER_NAME        VARCHAR2 null
  *     column: ARC_CREATED_DT        DATE null
  *     column: ARC_CREATED_BY        NUMBER null
  *     column: ARC_UPDATED_DT        DATE null
  *     column: ARC_UPDATED_BY        NUMBER null
  *     column: ZONE_ID        NUMBER null
  *     column: CIRCLE_ID        NUMBER null
  *     column: STATUS        CHAR null
  *     column: MAX_USERS        NUMBER null
  *     column: TOTAL_NO_USERS        NUMBER null
  * 
  * Primary Key(s):  ARC_ID
  * </pre>
  * 
  */
public interface HBOConnectMstrDao {

	/** 
	
		* Inserts a row in the ARC_CONNECT_MSTR table.
		* @param dto  DTO Object holding the data to be inserted.
		* @return  The no. of rows inserted. 
		* @throws ArcConnectMstrDaoException In case of an error
	**/

	public int insert(HBOConnectMstr dto) throws DAOException;

	/** 
	
		* Updates a row in the ARC_CONNECT_MSTR table.
		* The Primary Key Object determines wich row gets updated.
		* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
		* @return  The no. of rows updated. 
		* @throws ArcConnectMstrDaoException In case of an error
	**/

	public int update(HBOConnectMstr dto) throws DAOException;

	/** 
	
		* Deletes a row in the database based on the primary key supplied.
		* @param arcId  The arcId value for which the row needs to be deleted
		* @return  The no. of rows deleted.
		* @throws ArcConnectMstrDaoException In case of an error
	**/

	public int delete(String arcId) throws DAOException;

	/** 
	
		* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
		* @param arcId  The arcId value for which the row needs to be retrieved.
		* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
	**/

	public HBOConnectMstr findByPrimaryKey(String arcId) throws DAOException;

	/** 
	
			* Returns a multiple row of the database. If there is no match, <code>null</code> is returned.
			* @return DTO Object representing a multiple rows of the table.If there is no match, <code>null</code> is returned.
		 **/

	public HBOConnectMstr[] getConnectData() throws DAOException;

}
