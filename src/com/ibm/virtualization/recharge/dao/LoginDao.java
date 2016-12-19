/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.ibm.dp.dto.SMSDto;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.RechargeTransCircleDetail;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface for LoginDao Implementation.
 * 
 * @author bhanu
 * 
 */
public interface LoginDao {

	/**
	 * This method returns login id created for the user / account
	 * 
	 * @param user
	 * @return loginId
	 * @throws DAOException
	 */
	public long insertUser(User user) throws DAOException;

	/**
	 * This method returns user type and group details according to loginId
	 * 
	 * @param loginId
	 * @return login
	 * @throws DAOException
	 */
	public Login getAuthenticationDetails(long loginId) throws DAOException;

	/**
	 * Authenticates the account user for internet password.
	 * @param accountCode 
	 * @param password 
	 * @return Returns the user session context of provided account user.
	 * @throws DAOException
	 */
	public UserSessionContext authenticateAccount(String accountCode, String password)
			throws DAOException;

	/**
	 * This method returns inserted id for account user
	 * 
	 * @param loginId
	 * @return login
	 * @throws DAOException
	 */
	public long insertAccountUser(Login login,Timestamp currentTime) throws DAOException;

	/**
	 * This method returns inserted id for account user
	 * 
	 * @param loginId
	 * @return login
	 * @throws DAOException
	 */
	public long insertIdHelpAccountUser(Login login,Timestamp currentTime) throws DAOException;

	/**
	 * This method updates user login details
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void updateUserLogin(User user) throws DAOException;

	/**
	 * This method updates account user .
	 * 
	 * @param accountId
	 * @param accountCode
	 * @param parentCode
	 * @param status
	 * @throws DAOException
	 */
	public void updateAccountUser(long accountId, String accountCode,
			String status,int groupId, String parentCode,Timestamp currentTime) throws DAOException;

	/**
	 * This method provide UserSessionObject based on loginId
	 * 
	 * @param loginId
	 *            long
	 * @return UserSessionObject
	 * @throws DAOException
	 *             if any Exception occured
	 */
	public UserSessionContext getUserContext(long loginId) throws DAOException;

	/**
	 * This method will used to get UserSessionContext based on mobileNumber.
	 * 
	 * @param mobileNumber
	 *            String
	 * @return UserSessionContext throws DAOException in case any Exception
	 *         occured while retrieving UserSessionContext
	 */
	public UserSessionContext getMUserContext(String mobileNumber,
			String mPassword) throws DAOException;

	/**
	 * This method updates the password for the user
	 * 
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @throws DAOException
	 */
	public void updatePassword(long loginId, String oldPassword,
			String newPassword) throws DAOException;

	/**
	 * This method creates password history for the user
	 * 
	 * @param loginId
	 * @param oldPassword
	 * @param numberOfPwdHistory
	 * @throws DAOException
	 */
	public void updatePasswordHistory(long loginId, String oldPassword, int numberOfPwdHistory)
			throws DAOException;

	/**
	 * This method updates the m-password for the user
	 * 
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @throws DAOException
	 */
	public void updateMPassword(long loginId,
			String newPassword,String oldPassword) throws DAOException;
	
	
	
	/**
	 * This method checks whether the user is locked or not
	 * 
	 * @param loginId
	 * @throws DAOException
	 */
	public boolean isUserLocked(long loginId) 
	throws DAOException;
	
	
	/**
	 * This method returns a map of role link mappings(Key:Role name, Value:Role
	 * object)
	 * 
	 * @return HashMap
	 * @throws DAOException
	 */
	public LinkedHashMap<String, ArrayList> getRoleLinkMap() throws DAOException ;
	
	
	/**
	 * This method unlock the user.
	 * 
	 * @param loginName
	 * @throws DAOException
	 */

	public void unlockUser(long loginId) throws DAOException;
	
	/**this method u reset m-password
	 *  
	 * @param loginId
	 * @param newPassword
	 * @throws DAOException
	 */
	
	public void resetMPassword(long loginId,
			String newPassword) throws DAOException;
	
	/**this method update  i-password and set update date less then password exp. date   
	 * 
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @throws DAOException
	 */
	public void resetIPassword(long loginId, String oldPassword,
			String newPassword,int ExpDays) throws DAOException;
	


	/**
	 * This function will fetch the CircleId for source Mobile No (requester)
	 * 
	 * @param mobileNumber
	 *            String
	 * @return UserSessionContext throws DAOException in case any Exception
	 *         occured while retrieving UserSessionContext
	 */
	public RechargeTransCircleDetail getSourceCircleId(String mobileNumber) throws DAOException;
	
	public void changePassword(String changePwd , long loginId,String newPassword) throws DAOException;
	
	public int checkPswdExpiring(String loginId)  throws DAOException;
	public void inActiveIdHelpdeskUser(long loginId,Timestamp currentTime) throws DAOException;
	public void activeIdHelpdeskUser(long loginId,Timestamp currentTime) throws DAOException;
	public void changePassword(long loginId,String newPassword) throws DAOException;

	public void sendPasswordSMS(SMSDto sMSDto) throws DAOException ;
}