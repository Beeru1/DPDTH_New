/* 
 * ArcUserMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;
import java.util.ArrayList;
import com.ibm.hbo.exception.DAOException;
 
public interface HBOWarrantyDAO {

	/** 
	
		* Inserts a row in the ARC_USER_MSTR table.
		* @param dto  DTO Object holding the data to be inserted.
		* @return  The no. of rows inserted. 
		* @throws ArcUserMstrDaoException In case of an error
	**/

	public ArrayList findByPrimaryKey(String userId,String master,String imeino,String condition) throws DAOException;
	public void insert(String userId,Object formBean,String master,String condition) throws DAOException;
	
}
