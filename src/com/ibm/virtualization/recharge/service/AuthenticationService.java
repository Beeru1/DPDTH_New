package com.ibm.virtualization.recharge.service;

import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.exception.HBOException;
import com.ibm.virtualization.recharge.dto.ChangePasswordDto;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.RechargeTransCircleDetail;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface AuthenticationService {

	/**
	 * This function used to get UserSessionContext object for mobile user based
	 * on mobileNumber and mPassword passed as argument
	 * 
	 * @param mobileNumber
	 *            String
	 * @param mPassword
	 *            String
	 * @return UserSessionContext and
	 * @throws VirtualizationServiceException
	 *             if any exception occured
	 */
	public UserSessionContext authenticateMobileNo(String mobileNumber,
			String mPassword) throws VirtualizationServiceException;

	/**
	 * This method changes the password for the login id
	 * 
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @throws VirtualizationServiceException
	 */
	public void changePassword(long loginId, String oldPassword,
			String newPassword) throws VirtualizationServiceException;

	/**
	 * This method changes the m-password for the login id
	 * 
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @return returnTransStatus
	 * @throws VirtualizationServiceException
	 */
	public int changeMPassword(ChangePasswordDto changePasswordDto)
			throws VirtualizationServiceException;

	/**
	 * This method authenticates the external (account) user.
	 * 
	 * @param accountCode
	 *            the login name for account user.
	 * @param password
	 *            the internet password.
	 * @throws VirtualizationServiceException
	 *             if the authentication fails.
	 */
	public UserSessionContext authenticateAccount(String accountCode,
			String password) throws VirtualizationServiceException;

	/**
	 * this method check that login user circle is active(return true) or
	 * not(return false)
	 * 
	 * @param circleId
	 * @return boolean
	 * @throws VirtualizationServiceException
	 */
	public void isCircleActive(int circleId)
			throws VirtualizationServiceException;

	/**
	 * 
	 * @param changePasswordDto
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public int resetMPassword(ChangePasswordDto changePasswordDto)
			throws VirtualizationServiceException;

	/**
	 * for reset i password
	 * 
	 * @param loginId
	 * @param userType
	 * @throws VirtualizationServiceException
	 */
	public void resetPassword(long loginId, String userType)
			throws VirtualizationServiceException;
	
	/**
	 * for validate user & password
	 * 
	 * @param loginId
	 * @param userType
	 * @throws VirtualizationServiceException
	 */
	public void validateUserPassword(Login login)
			throws VirtualizationServiceException;

	public String validatePasswordExpiry(Login login)
	throws VirtualizationServiceException;
	
	
	/**
	 * for validate old & new & confirm password
	 * 
	 * @param oldpassword
	 * @param newpassword
	 * @param confirmnewpassword
	 * @throws VirtualizationServiceException
	 */
	public void validateChangePassword(ChangePasswordDto changepassword)
			throws VirtualizationServiceException;
	
	/**
	 * This method finds the Source & Suscriber's CircleId. If the Source circle
	 * id is not null then it finds both else it finds only subscriber's
	 * circleId.
	 * 
	 * @param RechargeTransCircleDetail
	 * @return RechargeTransCircleDetail
	 * @throws VirtualizationServiceException
	 */
	public void getRechargeCircleDetail(
			RechargeTransCircleDetail rechargeTransCircleDetailObj);
	
	public String pwdMailingService(String userLogingId, String userEmail, String mobileNo) throws VirtualizationServiceException;
	
	public int changePasswordService(HBOUserMstr dto) throws VirtualizationServiceException;
	
	public long getAccountId(String accountCode) throws VirtualizationServiceException;
	
	public String getEmailId(long accountId) throws VirtualizationServiceException ;
	
	public String getMobileNo(long accountId) throws VirtualizationServiceException ;
	public String getMobileNoForSms(long accountId,int smsAlertType) throws VirtualizationServiceException ;
	
	public void changePassword(long loginId,
			String newPassword) throws VirtualizationServiceException;
//	Created by Shilpa on 17-01-2012 for Critical changes CR
	public String getAccountName(long accountId) throws VirtualizationServiceException ;
	
	public String isNewUser(long accountId) throws VirtualizationServiceException ;
	
	public String getStatus(String accountCode) throws VirtualizationServiceException;

}
