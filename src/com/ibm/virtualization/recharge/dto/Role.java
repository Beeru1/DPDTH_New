/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.Date;

import com.ibm.virtualization.framework.bean.ChannelType;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private long groupID;

	private String roleName;

	private	ChannelType channelType;

	/**
	 * @param groupID
	 *            The groupID to set.
	 */
	public void setGroupId(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @param roleName
	 *            The roleName to set.
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return roleName String
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @return groupID Srting
	 */
	public long getGroupID() {
		return groupID;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

}
