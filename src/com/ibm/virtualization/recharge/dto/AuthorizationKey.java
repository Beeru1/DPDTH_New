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

import com.ibm.virtualization.framework.bean.ChannelType;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class AuthorizationKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private long groupId;

	private ChannelType channelType;

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object o) {
		if (o != null
				&& o instanceof AuthorizationKey
				&& (((AuthorizationKey) o).getGroupId() == this.groupId)
				&& (((AuthorizationKey) o).getChannelType() == this.channelType)) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		int channelTypeLength = ChannelType.values().length;
		return (int) ((this.groupId - 1) * channelTypeLength
				+ this.channelType.getValue() + 1);
	}
}
