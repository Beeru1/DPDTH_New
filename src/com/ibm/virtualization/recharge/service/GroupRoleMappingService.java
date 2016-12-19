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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.dto.GroupRoleMapping;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for Group Role Mapping service.
 * 
 * @author Navroze
 * 
 */

public interface GroupRoleMappingService {

	/**
	 * This method is use to get the list of roles assigned to a particular
	 * group
	 * 
	 * @param Group
	 *            Id
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getRoleAssignedList(int groupId)
			throws VirtualizationServiceException;
	
	

	/**
	 * This method is use to get the list of roles assigned to a particular
	 * group for the selected channelType
	 * 
	 * @param Group
	 *            Id
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getRoleAssignedList(int groupId , ChannelType channelType)
			throws VirtualizationServiceException;

	/**
	 * This method is use to get the list of roles not assigned to a particular
	 * group
	 * 
	 * @param Group
	 *            Id
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public ArrayList getRoleNotAssignedList(int groupId)
			throws VirtualizationServiceException;
	

	/**
	 * This method is use to get the list of roles not assigned to a particular
	 * group for the selected ChannelType
	 * 
	 * @param Group
	 *            Id
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public ArrayList getRoleNotAssignedList(int groupId , ChannelType channelType)
			throws VirtualizationServiceException;


	/**
	 * This method update the roles assigned to a particular group
	 * 
	 * @param Group
	 *            Role
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public void updateRoles(GroupRoleMapping groupRole, ChannelType channelType)
			throws VirtualizationServiceException;

}
