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

import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for Group management service.
 * 
 * @author Paras
 * 
 */
public interface GroupService {

	/**
	 * This method returns list of all groups.
	 * 
	 * @return groupList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getGroups(int groupId) throws VirtualizationServiceException;
	public ArrayList getGroups() throws VirtualizationServiceException;
	/**
	 * This method creates a new Group.
	 * 
	 * @param group
	 * @throws VirtualizationServiceException
	 * @throws com.ibm.dp.exception.VirtualizationServiceException 
	 * @throws NumberFormatException 
	 */
	public void createGroup(Group group) throws VirtualizationServiceException, NumberFormatException, VirtualizationServiceException;

	/**
	 * This method updates details of a group.
	 * 
	 * @param group
	 * @throws VirtualizationServiceException
	 */
	public void updateGroup(Group group) throws VirtualizationServiceException;

	/**
	 * This method returns details of a group based on the groupName provided.
	 * 
	 * @param groupName
	 * @return group
	 * @throws VirtualizationServiceException
	 */
	public Group getGroup(String groupName)
			throws VirtualizationServiceException;

	/**
	 * This method returns details of a group based on the groupId provided.
	 * 
	 * @param groupId
	 * @return group
	 * @throws VirtualizationServiceException
	 */
	public Group getGroup(int groupId)
			throws VirtualizationServiceException;

	
	/**
	 * This method returns name of the group based on the groupId provided.
	 * 
	 * @param groupId
	 * @return groupName
	 * @throws VirtualizationServiceException
	 */
	public String getGroupName(int groupId)
			throws VirtualizationServiceException;
    /**
     * This method get Internal  or External Groups
     * @return ArrayList
     * @throws VirtualizationServiceException
     */    
 
	public ArrayList getGroups(String loginId) throws VirtualizationServiceException;
	
	
}