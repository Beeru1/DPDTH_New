/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form Bean class for table USER_MASTER
 * 
 * @author Paras
 * 
 */
public class UserFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2884075345762204428L;

	private String loginId;

	private String loginName;

	private String password;

	private String checkPassword;

	private String mPassword;

	private String type;

	private String status;

	private String loginAttempted;

	private String groupId;

	private String groupName;

	private String passwordChangedDt;

	private String emailId;

	private String circleId;

	private String circleName;

	private String contactNumber;

	private String createdBy;

	private String createdDt;

	private String updatedBy;

	private String updatedDt;

	private String methodName;

	private ArrayList userList;

	private String userAuthStatus;

	private String startDt;

	private String endDt;

	private ArrayList displayDetails;

	private String pages;

	private String next;

	private String pre;

	private String editStatus;

	private boolean flagForCircleDisplay = false;

	private ArrayList allUserList = null;

	private String selectedCircleId;
	
	private String selectedStatus;
	
	private String createdByName;
	
	private String flag;
	
	private String unlockStatus;
	
	private String resetStatus;
	
	private boolean locked;
	
    private String page;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.loginId = null;
		this.loginName = null;
		this.password = null;
		this.checkPassword = null;
		this.mPassword = null;
		this.type = null;
		this.status = null;
		this.loginAttempted = null;
		this.groupId = null;
		this.groupName = null;
		this.passwordChangedDt = null;
		this.emailId = null;
		this.circleId = null;
		this.circleName = null;
		this.contactNumber = null;
		this.createdBy = null;
		this.createdDt = null;
		this.updatedBy = null;
		this.updatedDt = null;
		this.methodName = null;
		this.userList = null;
		this.userAuthStatus = null;
		this.startDt = null;
		this.endDt = null;
		this.displayDetails = null;
		this.pages = null;
		this.next = null;
		this.pre = null;
		this.selectedCircleId=null;
		this.selectedStatus=null;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}


	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}


	
	/**
	 * @return the selectedStatus
	 */
	public String getSelectedStatus() {
		return selectedStatus;
	}



	/**
	 * @param selectedStatus the selectedStatus to set
	 */
	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}



	/**
	 * @return the selectedCircleId
	 */
	public String getSelectedCircleId() {
		return selectedCircleId;
	}

	/**
	 * @param selectedCircleId the selectedCircleId to set
	 */
	public void setSelectedCircleId(String selectedCircleId) {
		this.selectedCircleId = selectedCircleId;
	}




	/**
	 * @return the allUserList
	 */
	public ArrayList getAllUserList() {
		return allUserList;
	}

	/**
	 * @param allUserList
	 *            the allUserList to set
	 */
	public void setAllUserList(ArrayList allUserList) {
		this.allUserList = allUserList;
	}

	/**
	 * @return the displayDetails
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}

	/**
	 * @param displayDetails
	 *            the displayDetails to set
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}

	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            the createdDt to set
	 */
	public void setCreatedDt(String createdDt) {
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	 * @return the loginAttempted
	 */
	public String getLoginAttempted() {
		return loginAttempted;
	}

	/**
	 * @param loginAttempted
	 *            the loginAttempted to set
	 */
	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the mPassword
	 */
	public String getMPassword() {
		return mPassword;
	}

	/**
	 * @param password
	 *            the mPassword to set
	 */
	public void setMPassword(String password) {
		mPassword = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the checkPassword
	 */
	public String getCheckPassword() {
		return checkPassword;
	}

	/**
	 * @param checkPassword
	 *            the checkPassword to set
	 */
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

	/**
	 * @return the passwordChangedDt
	 */
	public String getPasswordChangedDt() {
		return passwordChangedDt;
	}

	/**
	 * @param passwordChangedDt
	 *            the passwordChangedDt to set
	 */
	public void setPasswordChangedDt(String passwordChangedDt) {
		this.passwordChangedDt = passwordChangedDt;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
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
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public String getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            the updatedDt to set
	 */
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * @return the userList
	 */
	public ArrayList getUserList() {
		return userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(ArrayList userList) {
		this.userList = userList;
	}

	public String getUserAuthStatus() {
		return userAuthStatus;
	}

	public void setUserAuthStatus(String userAuthStatus) {
		this.userAuthStatus = userAuthStatus;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * @return the pages
	 */
	public String getPages() {
		return pages;
	}

	/**
	 * @param pages
	 *            the pages to set
	 */
	public void setPages(String pages) {
		this.pages = pages;
	}

	/**
	 * @return the pre
	 */
	public String getPre() {
		return pre;
	}

	/**
	 * @param pre
	 *            the pre to set
	 */
	public void setPre(String pre) {
		this.pre = pre;
	}

	/**
	 * @return the flagForCircleDisplay
	 */
	public boolean isFlagForCircleDisplay() {
		return flagForCircleDisplay;
	}

	/**
	 * @param flagForCircleDisplay
	 *            the flagForCircleDisplay to set
	 */
	public void setFlagForCircleDisplay(boolean flagForCircleDisplay) {
		this.flagForCircleDisplay = flagForCircleDisplay;
	}

	/**
	 * @return the editStatus
	 */
	public String getEditStatus() {
		return editStatus;
	}

	/**
	 * @param editStatus the editStatus to set
	 */
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}



	public String getCreatedByName() {
		return createdByName;
	}



	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getResetStatus() {
		return resetStatus;
	}

	public void setResetStatus(String resetStatus) {
		this.resetStatus = resetStatus;
	}

	public String getUnlockStatus() {
		return unlockStatus;
	}

	public void setUnlockStatus(String unlockStatus) {
		this.unlockStatus = unlockStatus;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	 
	

}