/*
 * Created on Oct 16, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import com.ibm.hbo.dto.HBOLogin;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public interface LoginService {

	/**
		 * This method populate the user details in ArcUserBean
		 * @param ArcLogin
		 * @return ArcUserBean
		 * @throws ArcException
		 */
	public HBOUserBean populateUserDetails(HBOLogin login) throws HBOException;
	public String logoutUser(String userID) throws DAOException;;
}
