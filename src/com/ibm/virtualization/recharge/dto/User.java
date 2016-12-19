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

/**
 * Data Trasnfer Object class for table USER_MASTER
 * 
 * @author Paras
 */
public class User extends Login implements Serializable {

	/* Logger for this class. */

	// private static Logger logger = Logger.getLogger(User.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = -5204033146626955067L;

	private long userId;

	private int groupId;

	private String emailId;

	private int circleId;

	private String circleName;

	private String contactNumber;

	private long createdBy;

	private Date createdDt;

	private long updatedBy;

	private Date updatedDt;

	private String startDt;

	private String endDt;

	private String rowNum;

	private int totalRecords;
	
	private String createdByName;
	
	private boolean locked;
	
	private String checkPassword;
	
	private String accountLevel;
	
	private int newAccountLevel;
	
	private int heirarchyId;

	
	public int getHeirarchyId() {
		return heirarchyId;
	}

	public void setHeirarchyId(int heirarchyId) {
		this.heirarchyId = heirarchyId;
	}

	public int getNewAccountLevel() {
		return newAccountLevel;
	}

	public void setNewAccountLevel(int newAccountLevel) {
		this.newAccountLevel = newAccountLevel;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum
	 *            the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName
	 *            the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;

	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the createdBy
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            the createdDt to set
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the updatedBy
	 */
	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            the updatedDt to set
	 */
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
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

		retValue = "User ( " + super.toString() + TAB + "userId = "
				+ this.userId + TAB + "groupId = " + this.groupId + TAB
				+ "emailId = " + this.emailId + TAB + "circleId = "
				+ this.circleId + TAB + "circleName = " + this.circleName + TAB
				+ "contactNumber = " + this.contactNumber + TAB
				+ "createdBy = " + this.createdBy + TAB + "createdDt = "
				+ this.createdDt + TAB + "updatedBy = " + this.updatedBy + TAB
				+ "updatedDt = " + this.updatedDt + TAB + " )";

		return retValue;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the checkPassword
	 */
	public String getCheckPassword() {
		return checkPassword;
	}

	/**
	 * @param checkPassword the checkPassword to set
	 */
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}
	
	

}