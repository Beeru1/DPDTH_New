/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \***************************************************************************/

package com.ibm.virtualization.recharge.dao;

import java.util.ArrayList;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.dto.GroupRoleMapping;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface definition for account management dao.
 * 
 * @author Navroze
 * 
 */

public interface GroupRoleMappingDao {

	/**
	 * This method get the role list that are assigned to particular Group
	 * 
	 * @param GroupId
	 * @return
	 * @throws DAOException
	 */

	public ArrayList getRoleAssignedList(int groupId) throws DAOException;
	
	/**
	 * This method get the role list that are assigned to particular Group
	 * for the selected ChannelType
	 * 
	 * @param GroupId
	 * @return
	 * @throws DAOException
	 */

	public ArrayList getRoleAssignedList(int groupId,ChannelType channelType) throws DAOException;


	/**
	 * This method get the role list that are not assigned to particular Group
	 * 
	 * @param GroupId
	 * @return
	 * @throws DAOException
	 */

	public ArrayList getRoleNotAssignedList(int groupId) throws DAOException;
	
	/**
	 * This method get the role list that are not assigned to particular Group
	 * for the selected ChannelType
	 * 
	 * @param GroupId
	 * @return
	 * @throws DAOException
	 */

	public ArrayList getRoleNotAssignedList(int groupId,ChannelType channelType) throws DAOException;

	/**
	 * This method update the role list assigned to particular Group
	 * 
	 * @param Group
	 *            Role
	 * @return
	 * @throws DAOException
	 */

	public void updateRoles(GroupRoleMapping groupRole,ChannelType channelType) throws DAOException;

}
