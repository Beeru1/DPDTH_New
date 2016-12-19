/* 
 * ArcZoneMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */ 

package com.ibm.hbo.dao;

import com.ibm.hbo.dto.HBOZoneMstr;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.exception.DAOException;




/** 
  * DAO for the <b>ARC_ZONE_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: ARC_ZONE_MSTR
  * ----------------------------------------------
  *     column: ZONE_ID        NUMBER null
  *     column: ZONE_NAME        VARCHAR2 null
  *     column: CREATED_DT        DATE null
  *     column: CREATED_BY        NUMBER null
  *     column: UPDATED_DT        DATE null
  *     column: UPDATED_BY        NUMBER null
  *     column: CIRCLE_ID        NUMBER null
  *     column: STATUS        CHAR null
  * 
  * Primary Key(s):  ZONE_ID
  * </pre>
  * 
  */ 
public interface HBOZoneMstrDao {

 /** 

	* Inserts a row in the ARC_ZONE_MSTR table.
	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws ArcZoneMstrDaoException In case of an error
 **/ 

    public  int insert(HBOZoneMstr dto) throws DAOException;

 /** 

	* Updates a row in the ARC_ZONE_MSTR table.
	* The Primary Key Object determines wich row gets updated.
	* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
	* @return  The no. of rows updated. 
	* @throws ArcZoneMstrDaoException In case of an error
 **/ 

    public  int update(HBOZoneMstr dto) throws DAOException;

 /** 

	* Deletes a row in the database based on the primary key supplied.
	* @param zoneId  The zoneId value for which the row needs to be deleted
	* @return  The no. of rows deleted.
	* @throws ArcZoneMstrDaoException In case of an error
 **/ 

    public  int delete(String zoneId) throws DAOException;

 /** 

	* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
	* @param zoneId  The zoneId value for which the row needs to be retrieved.
	* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
 **/ 

    public  HBOZoneMstr findByPrimaryKey(String zoneId) throws DAOException;
    
	/** 

		* Returns a multiple row of the database. If there is no match, <code>null</code> is returned.
		* @return DTO Object representing a multiple rows of the table.If there is no match, <code>null</code> is returned.
	 **/ 

		public  HBOZoneMstr[] getZoneData() throws DAOException;

}
