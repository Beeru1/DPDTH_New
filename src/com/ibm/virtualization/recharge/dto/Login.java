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
 * This class is used for Data Transfer of a Login between layers.
 * 
 * @author bhanu
 * 
 */
public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8934769170203628691L;

	private long loginId;

	private String loginName;

	private String password;

	private String mPassword;

	private String type;

	private String status;

	private int loginAttempted;

	private int groupId;

	private String groupName;

	private String passwordChangedDt;
	
	private String heiarachyId;

	public String getHeiarachyId() {
		return heiarachyId;
	}

	public void setHeiarachyId(String heiarachyId) {
		this.heiarachyId = heiarachyId;
	}

	/**
	 * @return Returns the groupId.
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            The groupId to set.
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return Returns the loginAttempted.
	 */
	public int getLoginAttempted() {
		return loginAttempted;
	}

	/**
	 * @param loginAttempted
	 *            The loginAttempted to set.
	 */
	public void setLoginAttempted(int loginAttempted) {
		this.loginAttempted = loginAttempted;
	}

	/**
	 * @return Returns the loginId.
	 */
	public long getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            The loginId to set.
	 */
	public void setLoginId(long loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return Returns the mPassword.
	 */
	public String getMPassword() {
		return mPassword;
	}

	/**
	 * @param password
	 *            The mPassword to set.
	 */
	public void setMPassword(String password) {
		mPassword = password;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the passwordChangedDt.
	 */
	public String getPasswordChangedDt() {
		return passwordChangedDt;
	}

	/**
	 * @param passwordChangedDt
	 *            The passwordChangedDt to set.
	 */
	public void setPasswordChangedDt(String passwordChangedDt) {
		this.passwordChangedDt = passwordChangedDt;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

		retValue = "Login ( " + super.toString() + TAB + "loginId = "
				+ this.loginId + TAB + "loginName = " + this.loginName + TAB
				+ this.mPassword + TAB + "type = " + this.type + TAB
				+ "status = " + this.status + TAB + "loginAttempted = "
				+ this.loginAttempted + TAB + "groupId = " + this.groupId + TAB
				+ "groupName = " + this.groupName + TAB
				+ "passwordChangedDt = " + this.passwordChangedDt + TAB + " )";

		return retValue;
	}

}