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

import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for user management service.
 * 
 * @author Paras
 * 
 */
public interface UserService {

	/**
	 * This method creates a new user.
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void createUser(User user) throws VirtualizationServiceException;

	/**
	 * This method updates details of the use
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void updateUser(User user) throws VirtualizationServiceException;

	
	/**
	 * This method returns a list of users based on the passed arguments.
	 * 
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAllUsers(ReportInputs inputDto,int lb,int ub ) throws VirtualizationServiceException;
	
	/**
	 * This method returns a list of users based on the passed arguments for Download purpose.
	 * 
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAllUsersList(ReportInputs inputDto) throws VirtualizationServiceException;
	
	/**
	 * This method returns the Total no of Users
	 * @param inputDto
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public int getUsersCount(ReportInputs inputDto) throws VirtualizationServiceException;

	/**
	 * This method returns details of user based on the loginId provided.
	 * 
	 * @param loginId
	 * @return User
	 * @throws VirtualizationServiceException
	 */
	public User getUser(long loginId) throws VirtualizationServiceException;

	
	/**
	 * This method returns login id for the login name provided.
	 * 
	 * @param loginName
	 * @return loginId
	 * @throws VirtualizationServiceException
	 */
	public long getUser(String loginName) throws VirtualizationServiceException;
}