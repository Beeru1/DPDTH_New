/* 
 * ArcUserMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;
import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.HBOCommonDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
 
public interface HBOMasterDAO {

	/** 
	
		* Inserts a row in the ARC_USER_MSTR table.
		* @param dto  DTO Object holding the data to be inserted.
		* @return  The no. of rows inserted. 
		* @throws ArcUserMstrDaoException In case of an error
	**/

	public ArrayList findByPrimaryKey(String userId,int master,String condition) throws DAOException;
	public ArrayList findByPrimaryKey(String userId,String master,String condition) throws DAOException;
	public ArrayList findByPrimaryKey(HBOUser obj) throws DAOException;
	public void insert(String userId,Object formBean,String master,String condition) throws DAOException;
	public void insertMRL(String userId,Object minReordDTO,String master,String condition ) throws DAOException;
	public ArrayList getChange(ArrayList arr) throws DAOException;
	public int getProjectionQty(ArrayList params) throws DAOException;
	/**
	 * @param circle
	 * @param hboUserBean
	 * @return
	 */
	public String findByPrimaryKey(HBOUserBean hboUserBean) throws DAOException;
	public ArrayList getAccountList(String userIdFrom,String conditionA,String conditionB)throws DAOException;

}
