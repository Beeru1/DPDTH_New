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
import java.util.LinkedHashMap;
import java.util.List;

import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * The Authorizer interface, all classes providing authrization services should
 * be extending this
 * 
 * @author Rohit Dhall
 * @date 07-Sep-2007
 * 
 */
public interface AuthorizationInterface {
	/**
	 * This method returns an Map containing names of all the roles as Keys
	 * which have been assigned to a user.Default values of each would be Y
	 * 
	 * @param groupID
	 *            String
	 * @param channelType
	 * 				ChannelType
	 * @return Map
	 */
	public ArrayList getUserCredentials(long groupID,ChannelType channelType);

	/**
	 * This method returns a boolean indicating whether the passed group have
	 * all passed Roles
	 * 
	 * @param groupID
	 *            String
	 *@param channelType
	 * 				ChannelType           
	 * @param roleNames
	 *            List
	 * @return boolean
	 */
	public boolean isUserAuthorized(long groupId,ChannelType channelType, List roleNames);

	/**
	 * This method returns a boolean indicating whether the passed group have
	 * passed Role
	 * 
	 * @param groupID
	 *            String
	 * @param channelType
	 * 				ChannelType;
	 * @param roleName
	 *            String
	 * @return boolean
	 */
	public boolean isUserAuthorized(long groupId,ChannelType channeltype, String roleName);

	/**
	 * This method returns a map of role link mappings(Key:Role name, Value:Role
	 * object)
	 * 
	 * @return HashMap
	 * @throws VirtualizationServiceException
	 */
	public LinkedHashMap loadRoleLinkMap()
			throws VirtualizationServiceException;
	
	
	public int forceToRecoPage(Long l);

}

