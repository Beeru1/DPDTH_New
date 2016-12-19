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

import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.exception.DAOException;


/**
 * Interface for GroupDao Implementation.
 * 
 * @author Paras
 * 
 */
public interface GroupDao {

	/**
	 * This method returns list of groups
	 * 
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getGroups(int groupId) throws DAOException;
	public ArrayList getGroups() throws DAOException;
	/**
	 * This method inserts details of a group
	 * 
	 * @param group
	 * @throws DAOException
	 */
	public void insertGroup(Group group) throws DAOException;

	/**
	 * This method returns details of a group based on groupName
	 * 
	 * @param groupName
	 * @return Group
	 * @throws DAOException
	 */
	public Group getGroup(String groupName) throws DAOException;
	
	/**
	 * This method returns details of a group based on groupId
	 * 
	 * @param groupId
	 * @return Group
	 * @throws DAOException
	 */
	public Group getGroup(int groupId) throws DAOException;	

	/**
	 * This method updates details of a group.
	 * 
	 * @param group
	 * @throws DAOException
	 */
	public void updateGroup(Group group) throws DAOException;

	/**
	 * This method returns name of the group based on the groupId.
	 * 
	 * @param groupId
	 * @return String
	 * @throws DAOException
	 */
	public String getGroupName(int groupId) throws DAOException;
	/**
	 * This method use to get group for External or Internal User 
	 * @param type
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getGroups(String loginId) throws DAOException ;

}