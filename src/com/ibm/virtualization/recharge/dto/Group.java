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

/**
 * This class is used for Data Transfer of a Group between different layers.
 * 
 * @author Paras
 * 
 */
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143785149144381711L;

	private int groupId;

	private String groupName;

	private String description;

	private long createdBy;

	private String createdDt;

	private long updatedBy;

	private String updatedDt;

	private String type; 
	
	// private String status;

	/** Creates a dto for the VR_GROUP_DETAILS table */

	/**
	 * Get groupId associated with this object.
	 * 
	 * @return groupId
	 */

	public int getGroupId() {
		return groupId;
	}

	/**
	 * Set groupId associated with this object.
	 * 
	 * @param groupId
	 *            The groupId value to set
	 */

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * Get groupName associated with this object.
	 * 
	 * @return groupName
	 */

	public String getGroupName() {
		return groupName;
	}

	/**
	 * Set groupName associated with this object.
	 * 
	 * @param groupName
	 *            The groupName value to set
	 */

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get createdDt associated with this object.
	 * 
	 * @return createdDt
	 */

	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * Set createdDt associated with this object.
	 * 
	 * @param createdDt
	 *            The createdDt value to set
	 */

	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * Get updatedBy associated with this object.
	 * 
	 * @return updatedBy
	 */

	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy associated with this object.
	 * 
	 * @param updatedBy
	 *            The updatedBy value to set
	 */

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Get updatedDt associated with this object.
	 * 
	 * @return updatedDt
	 */

	public String getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * Set updatedDt associated with this object.
	 * 
	 * @param updatedDt
	 *            The updatedDt value to set
	 */

	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
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

		retValue = "Group ( " + super.toString() + TAB + "groupId = "
				+ this.groupId + TAB + "groupName = " + this.groupName + TAB
				+ "description = " + this.description + TAB + "createdBy = "
				+ this.createdBy + TAB + "createdDt = " + this.createdDt + TAB
				+ "updatedBy = " + this.updatedBy + TAB + "updatedDt = "
				+ this.updatedDt + TAB + " )";

		return retValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get status associated with this object.
	 * 
	 * @return status
	 */

	/*
	 * public String getStatus() { return status; }
	 */

	/**
	 * Set status associated with this object.
	 * 
	 * @param status
	 *            The status value to set
	 */

	/*
	 * public void setStatus(String status) { this.status = status; }
	 */

}