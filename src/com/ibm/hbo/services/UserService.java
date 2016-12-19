/*
 * Created on Oct 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.exception.HBOException;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public interface UserService {
	/** 
	
		* Returns different userType
		* @return List
	 **/

	public List getUserTypeService(String roleId) throws HBOException;

	/** 
	
		* Returns circle details
		* @return List
	 **/

	public List getCircleDataService() throws HBOException;

	/** 
	
		* Returns zone detalis
		* @return List
	 **/

	

	

	public String createUserService(HBOUserMstr userMstrDto, HBOUserBean sessionUserBean)
		throws HBOException;

	/**
		 * To edit user details into Database
		 * @param userMstrDto
		 * @param sessionUserBean
		 */

	public int editUserService(HBOUserMstr userMstrDto) throws HBOException;

	/**
	 * To view the user details
	 * @param actorId
	 * @return List
	 * @throws HBOException
	 */
	public HBOUserMstr userLoginEmail(String userLogingId) throws HBOException;

			/**
						 * Returns email id for userLogin Id
						 * @param userLogingId
						 * @return boolean
						 * @throws ICEException
						 */

	

	public String pwdMailingService(String userLogingId, String userEmail) throws HBOException;
	/**
	 * Change password service
	 * @param dto
	 * @throws HBOException
	 */
	public int changePasswordService(HBOUserMstr dto) throws HBOException;

	/**
			 * Returns email id for userLogin Id
			 * @param userLogingId
			 * @return boolean
			 * @throws HBOException
			 */
	public List viewUserService(String actorId,String userId) throws HBOException;
	
	public HBOUserMstr getEditUserDetails(String userId, ArrayList userList) throws HBOException;

	public boolean checkDuplicateUserLogin(String userLogingId) throws HBOException;

		/**
		 * Change password service
		 * @param dto
		 * @throws HBOException
		 */
	


}
