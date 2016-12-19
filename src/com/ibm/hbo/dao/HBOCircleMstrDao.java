/* 
 * ArcCircleMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;

import com.ibm.hbo.dto.HBOCircleDTO;
import com.ibm.hbo.exception.DAOException;

/** 
  * DAO for the <b>ARC_CIRCLE_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: ARC_CIRCLE_MSTR
  * ----------------------------------------------
  *     column: CIRCLE_NAME        VARCHAR2 null
  *     column: CIRCLE_ID        NUMBER null
  *     column: CREATED_DT        DATE null
  *     column: CREATED_BY        NUMBER null
  *     column: STATUS        CHAR null
  * 
  * Primary Key(s):  CIRCLE_ID
  * </pre>
  * 
  */
public interface HBOCircleMstrDao {


	/** 
	
		* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
		* @param circleId  The circleId value for which the row needs to be retrieved.
		* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
	**/

	public HBOCircleDTO findByPrimaryKey(String circleId) throws DAOException;

	/** 
	
		* Returns a multiple rows of the database. If there is no match, <code>null</code> is returned.
		* @return DTO Object representing a multiple rows of the table.If there is no match, <code>null</code> is returned.
	 **/
	
	public HBOCircleDTO[] getCircleData() throws DAOException;

}
