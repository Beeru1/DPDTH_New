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
 * This class is used for Data Transfer of a Region between different layers.
 * 
 * @author Paras
 * 
 */
public class Region implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5950628432165850255L;

	private int id;

	private String name;

	private String description;

	private String status;

	private long createdBy;

	private long updatedBy;

	private String updatedDateTime;

	private String createdDateTime;

	/**
	 * @return Returns the createdBy.
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            The createdBy to set.
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Returns the createdDateTime.
	 */
	public String getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * @param createdDateTime
	 *            The createdDateTime to set.
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDateTime = createdDt;
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
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(int regionId) {
		this.id = regionId;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String regionName) {
		this.name = regionName;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the updatedBy.
	 */
	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            The updatedBy to set.
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the updatedDateTime.
	 */
	public String getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * @param updatedDateTime
	 *            The updatedDateTime to set.
	 */
	public void setUpdatedDateTime(String updatedDt) {
		this.updatedDateTime = updatedDt;
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

		retValue = "Region ( " + super.toString() + TAB + "id = " + this.id
				+ TAB + "name = " + this.name + TAB + "description = "
				+ this.description + TAB + "status = " + this.status + TAB
				+ "createdBy = " + this.createdBy + TAB + "updatedBy = "
				+ this.updatedBy + TAB + "updatedDateTime = "
				+ this.updatedDateTime + TAB + "createdDateTime = "
				+ this.createdDateTime + TAB + " )";

		return retValue;
	}

}