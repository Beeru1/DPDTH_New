/**************************************************************************
 * File     : VUserMstrDTO.java
 * Author   : Banita
 * Created  : Sep 6, 2008
 * Modified : Sep 6, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;

/*******************************************************************************
 * This class holds the user information date
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class VUserMstrDTO implements Serializable {

	protected String userId;

	protected String loginId;

	protected String status;

	protected String createdBy;

	protected java.sql.Timestamp createdDt;

	protected java.sql.Timestamp updatedDt;

	//protected String emailId;
	
	protected String mailId;

	protected String updatedBy;

	protected String circleId;

	protected String password;

	protected java.sql.Timestamp passwordChangedDt;

	protected String userType;

	protected String groupId;

	protected String loginAttempted;

	protected String groupName;

	protected String circleName;

	protected String userTypeName;

	protected String statusName;

	protected String loginStatus;

	protected String circleCode;

	protected String lastAccessTime;

	protected String rownum;
	protected String lockStatus;
	protected String subject;
	protected String message;
	protected int zoneCode;
	protected String zoneName;

	/**
	 * It returns the String representation of the VUserMstrDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer(500).append(" \n VUserMstrDTO - userId: ")
				.append(userId).append(",  loginId: ").append(loginId).append(
						",  status: ").append(status).append(",  createdBy: ")
				.append(createdBy).append(",  updatedBy: ").append(updatedBy)
				.append(",  updatedDt: ").append(updatedDt).append(
						",  mailId: ").append(mailId).append(",  password: ")
				.append(password).append(",  circleCode: ").append(circleCode)
				.append(",  loginStatus: ").append(loginStatus).append(
						",  statusName: ").append(statusName).append(
						",  userTypeName: ").append(userTypeName).append(
						",  circleName: ").append(circleName).append(
						",  groupName: ").append(groupName).append(
						",  zoneCode: ").append(zoneCode).toString();
	}

	/** Creates a dto for the V_USER_MSTR table */
	public VUserMstrDTO() {
	}

	/**
	 * @return Returns the loginStatus.
	 */
	public String getLoginStatus() {
		return loginStatus;
	}

	/**
	 * @param loginStatus The loginStatus to set.
	 */
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	/**
	 * @return Returns the rownum.
	 */
	public String getRownum() {
		return rownum;
	}

	/**
	 * @param rownum The rownum to set.
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	/**
	 * @return Returns the statusName.
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName The statusName to set.
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return Returns the userTypeName.
	 */
	public String getUserTypeName() {
		return userTypeName;
	}

	/**
	 * @param userTypeName The userTypeName to set.
	 */
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	/** 
	 * Get userId associated with this object.
	 * @return userId
	 **/

	public String getUserId() {
		return userId;
	}

	/** 
	 * Set userId associated with this object.
	 * @param userId The userId value to set
	 **/

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/** 
	 * Get loginId associated with this object.
	 * @return loginId
	 **/

	public String getLoginId() {
		return loginId;
	}

	/** 
	 * Set loginId associated with this object.
	 * @param loginId The loginId value to set
	 **/

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/** 
	 * Get status associated with this object.
	 * @return status
	 **/

	public String getStatus() {
		return status;
	}

	/** 
	 * Set status associated with this object.
	 * @param status The status value to set
	 **/

	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	 * Get createdBy associated with this object.
	 * @return createdBy
	 **/

	public String getCreatedBy() {
		return createdBy;
	}

	/** 
	 * Set createdBy associated with this object.
	 * @param createdBy The createdBy value to set
	 **/

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/** 
	 * Get createdDt associated with this object.
	 * @return createdDt
	 **/

	public java.sql.Timestamp getCreatedDt() {
		return createdDt;
	}

	/** 
	 * Set createdDt associated with this object.
	 * @param createdDt The createdDt value to set
	 **/

	public void setCreatedDt(java.sql.Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/** 
	 * Get updatedDt associated with this object.
	 * @return updatedDt
	 **/

	public java.sql.Timestamp getUpdatedDt() {
		return updatedDt;
	}

	/** 
	 * Set updatedDt associated with this object.
	 * @param updatedDt The updatedDt value to set
	 **/

	public void setUpdatedDt(java.sql.Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	/** 
	 * Get updatedBy associated with this object.
	 * @return updatedBy
	 **/

	public String getUpdatedBy() {
		return updatedBy;
	}

	/** 
	 * Set updatedBy associated with this object.
	 * @param updatedBy The updatedBy value to set
	 **/

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/** 
	 * Get circleId associated with this object.
	 * @return circleId
	 **/

	public String getCircleId() {
		return circleId;
	}

	/** 
	 * Set circleId associated with this object.
	 * @param circleId The circleId value to set
	 **/

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/** 
	 * Get password associated with this object.
	 * @return password
	 **/

	public String getPassword() {
		return password;
	}

	/** 
	 * Set password associated with this object.
	 * @param password The password value to set
	 **/

	public void setPassword(String password) {
		this.password = password;
	}

	/** 
	 * Get passwordChangedDt associated with this object.
	 * @return passwordChangedDt
	 **/

	public java.sql.Timestamp getPasswordChangedDt() {
		return passwordChangedDt;
	}

	/** 
	 * Set passwordChangedDt associated with this object.
	 * @param passwordChangedDt The passwordChangedDt value to set
	 **/

	public void setPasswordChangedDt(java.sql.Timestamp passwordChangedDt) {
		this.passwordChangedDt = passwordChangedDt;
	}

	/** 
	 * Get userType associated with this object.
	 * @return userType
	 **/

	public String getUserType() {
		return userType;
	}

	/** 
	 * Set userType associated with this object.
	 * @param userType The userType value to set
	 **/

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/** 
	 * Get groupId associated with this object.
	 * @return groupId
	 **/

	public String getGroupId() {
		return groupId;
	}

	/** 
	 * Set groupId associated with this object.
	 * @param groupId The groupId value to set
	 **/

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/** 
	 * Get loginAttempted associated with this object.
	 * @return loginAttempted
	 **/

	public String getLoginAttempted() {
		return loginAttempted;
	}

	/** 
	 * Set loginAttempted associated with this object.
	 * @param loginAttempted The loginAttempted value to set
	 **/

	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
	}

	/**
	 * @return Returns the circleName.
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName The circleName to set.
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public String getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * @return the lockStatus
	 */
	public String getLockStatus() {
		return lockStatus;
	}

	/**
	 * @param lockStatus the lockStatus to set
	 */
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the mailId
	 */
	public String getMailId() {
		return mailId;
	}

	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * @return the zoneCode
	 */
	public int getZoneCode() {
		return zoneCode;
	}

	/**
	 * @param zoneCode the zoneCode to set
	 */
	public void setZoneCode(int zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
}
