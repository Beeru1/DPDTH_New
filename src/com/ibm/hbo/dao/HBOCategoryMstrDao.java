/* 
 * ArcCategoryMstrDao.java
 * Created: October 18, 2007
 * 
 * 
 */ 

package com.ibm.hbo.dao;

import com.ibm.hbo.dto.HBOCategoryMstr;
import com.ibm.hbo.exception.DAOException;



/** 
  * DAO for the <b>ARC_CATEGORY_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: ARC_CATEGORY_MSTR
  * ----------------------------------------------
  *     column: CATEGORY_ID        NUMBER null
  *     column: CATEGORY_NAME        VARCHAR2 null
  *     column: CATEGORY_DESCRIPTION        VARCHAR2 null
  * 
  * Primary Key(s):  CATEGORY_ID
  * </pre>
  * 
  */ 
public interface HBOCategoryMstrDao {

 

    public  HBOCategoryMstr[] getCategory() throws DAOException;

}
