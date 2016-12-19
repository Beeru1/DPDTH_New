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

import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for UserDao Implementation.
 * 
 * @author Paras
 * 
 */
public interface UserDao {

	/**
	 * This method inserts details of the user.
	 * 
	 * @param user
	 * @throws DAOException
	 */

	public void insertUser(User user) throws DAOException;

	/**
	 * This method updates details of a user.
	 * 
	 * @param user
	 * @throws DAOException
	 */

	public void updateUser(User user) throws DAOException;

	/**
	 * This method returns details of a user based on the login Id.
	 * 
	 * @param loginId
	 * @return user
	 * @throws DAOException
	 */

	public User getUser(long loginId) throws DAOException;

	/**
	 * This method provides ArrayList of all the users based on the passed
	 * arguments
	 * 
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getAllUsers(ReportInputs inputDto,int lb,int ub) throws DAOException;
	
	/**
	 * This method provides ArrayList of all the users based on the passed
	 * arguments for Download purpose
	 * 
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getAllUsers(ReportInputs inputDto) throws DAOException;
	
	/**
	 * This method returns the Total no of Users
	 * @param inputDto
	 * @return
	 * @throws DAOException
	 */
	public int getUsersCount(ReportInputs inputDto) throws DAOException;

	/**
	 * This method returns login id for the login name provided.
	 * 
	 * @param loginName
	 * @return loginId
	 * @throws DAOException
	 */

	public long getUser(String loginName) throws DAOException;
}