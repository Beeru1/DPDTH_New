/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service;

import java.sql.Timestamp;

import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for Login service. date 30-aug-2007
 * 
 * @author bhanu
 * 
 */
public interface LoginService {
	/**
	 * This method returns user type and groupId for a user
	 * 
	 * @param loginId
	 * @return login
	 * @throws VirtualizationServiceException
	 */
	public Login getUserInformation(long loginId)
			throws VirtualizationServiceException;
	
	/**
	 * This method returns user type and groupId for a user
	 * 
	 * @param login id
	 * @throws VirtualizationServiceException
	 */
	public void unlockUser(long loginId)
			throws VirtualizationServiceException;
	/**
	 * This method returns true if the user is locked
	 * 
	 * @param login id
	 * @throws VirtualizationServiceException
	 */
	
	public boolean isUserLocked(long loginId) throws VirtualizationServiceException;
	
	public int checkPswdExpiring(String loginId)throws VirtualizationServiceException ;
	
	public void inActiveIdHelpdeskUser(long loginId)
			throws VirtualizationServiceException;
	
	public void activeIdHelpdeskUser(long loginId)
	throws VirtualizationServiceException;
}
