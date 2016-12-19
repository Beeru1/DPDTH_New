/* 
 * ArcActorMstrDao.java
 * Created: October 23, 2007
 * 
 * 
 */ 

package com.ibm.hbo.dao;

import com.ibm.hbo.dto.HBOActorMstr;
import com.ibm.hbo.exception.DAOException;




/** 
  * DAO for the <b>ARC_ACTOR_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: ARC_ACTOR_MSTR
  * ----------------------------------------------
  *     column: ACTOR_ID        NUMBER null
  *     column: ACTOR_NAME        VARCHAR2 null
  *     column: CREATED_DT        DATE null
  *     column: CREATED_BY        NUMBER null
  * 
  * Primary Key(s):  ACTOR_ID
  * </pre>
  * 
  */ 
public interface HBOActorMstrDao {

 
 
 /** 

	* Returns a multiple row of the database. If there is no match, <code>null</code> is returned.
	* @return DTO Object representing a multiple rows of the table.If there is no match, <code>null</code> is returned.
 **/ 

    public  HBOActorMstr[] getActorName(String roleId) throws DAOException;

}
