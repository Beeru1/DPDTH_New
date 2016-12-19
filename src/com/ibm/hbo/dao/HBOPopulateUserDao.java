/*
 * Created on Oct 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dao;

import java.util.HashMap;
import java.util.List;

import com.ibm.hbo.dto.HBOLogin;
import com.ibm.hbo.exception.DAOException;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public interface HBOPopulateUserDao {
	/** 
	
		* Select user details from ARC_MODULE_MSTR,ARC_USER_MSTR,ARC_ACTOR_MSTR,ARC_CIRCLE_MSTR table.
		* @param dto  DTO Object holding the data.
		* @return  List 
		* @throws DAOException In case of an error
	 **/

	public List populateValues(HBOLogin dto) throws DAOException;
	public String logoutUser(String userID) throws DAOException;

	/** 
	
		* Select category details from ARC_CATEGORY_MSTR table.
		* @return  HashMap 
		* @throws DAOException In case of an error
	**/

	public HashMap category() throws DAOException;
}
