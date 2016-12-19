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
 * This class is used for Data Transfer of a Group between different layers.
 * 
 * @author Navroze
 * 
 */

public class GroupRoleMapping implements Serializable {
	


	private static final long serialVersionUID = 1L;

	private int groupId;

	private int groupName;

	private long roleId;

	private String roleName;

	private long createdBy;

	private String createdDt;

	private long updatedBy;

	private String updatedDt;

	private int[] rolesId;
	
	private ChannelType channelType;
	
	
	public void setChannelType(String channelType) {
		this.channelType= ChannelType.valueOf(channelType);
	}
	
	

	/**
	 * @return the channelType
	 */
	public ChannelType getChannelType() {
		return channelType;
	}

	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupName() {
		return groupName;
	}

	public void setGroupName(int groupName) {
		this.groupName = groupName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	public int[] getRolesId() {
		return rolesId;
	}

	public void setRolesId(int[] rolesId) {
		this.rolesId = rolesId;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "GroupRoleMapping ( " + super.toString() + TAB
				+ "groupId = " + this.groupId + TAB + "groupName = "
				+ this.groupName + TAB + "roleId = " + this.roleId + TAB
				+ "roleName = " + this.roleName + TAB + "createdBy = "
				+ this.createdBy + TAB + "createdDt = " + this.createdDt + TAB
				+ "updatedBy = " + this.updatedBy + TAB + "updatedDt = "
				+ this.updatedDt + TAB + "rolesId = " + this.rolesId + TAB
				+ " )";

		return retValue;
	}

	
}