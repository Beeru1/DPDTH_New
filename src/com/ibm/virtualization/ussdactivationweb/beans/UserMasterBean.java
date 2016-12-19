/**************************************************************************
 * File     : UserMasterBean.java
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
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/*******************************************************************************
 * This class holds the user information date
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class UserMasterBean extends ActionForm {

	public static final long serialVersionUID = 1L;

	private String userId;

	private String loginId;

	private String status;

	//private String emailId;
	
	private String mailId;

	private String oldEmailId;

	private String circleId;

	private String groupId;

	private String loginAttempted;

	private String userCreateDate;

	private String userCreatedBy;

	private String userUpdatedDate;

	private String userUpdatedBy;

	private String userType;

	private String password;

	private String passwordChangedDate;

	private String methodName;

	private String checkPassword;

	private String circleName;

	private String groupName;

	private String userCreatedByName;

	private String userUpdatedByName;

	private String userTypeName;

	private String statusName;

	private String hiddenCircleId;

	private String circleCode;

	private String lastAccessTime;

	private String page1 = "";
	private String loginFlag;
	private int zoneCode;
	private ArrayList zoneList;
	private int userZoneCode;
	private String flag;
	private String zoneName;
	

	/**
	 * @return the userZoneCode
	 */
	public int getUserZoneCode() {
		return userZoneCode;
	}

	/**
	 * @param userZoneCode the userZoneCode to set
	 */
	public void setUserZoneCode(int userZoneCode) {
		this.userZoneCode = userZoneCode;
	}

	/**
	 * @return the loginFlag
	 */
	public String getLoginFlag() {
		return loginFlag;
	}

	/**
	 * @param loginFlag the loginFlag to set
	 */
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	/**
	 * It returns the String representation of the UserMasterBean.
	 * 
	 * @return Returns a <code>String</code> having all the content
	 *         information of this object.
	 */
	public String toString() {
		return new StringBuffer(500).append(" \n UserMasterBean - userId: ")
				.append(userId).append(",  loginId: ").append(loginId).append(
						",  status: ").append(status).append(
						",  userCreatedBy: ").append(userCreatedBy).append(
						",  userUpdatedBy: ").append(userUpdatedBy).append(
						",  userUpdatedDate: ").append(userUpdatedDate).append(
						",  mailId: ").append(mailId).append(",  password: ")
				.append(password).append(",  circleCode: ").append(circleCode)
				.append(",  lastAccessTime: ").append(lastAccessTime).append(
						",  statusName: ").append(statusName).append(
						",  userTypeName: ").append(userTypeName).append(
						",  circleName: ").append(circleName).append(
						",  groupName: ").append(groupName).toString();
	}

	/**
	 * @return the lastAccessTime
	 */
	public String getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * @param lastAccessTime
	 *            the lastAccessTime to set
	 */
	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * @return Returns the hiddenCircleId.
	 */
	public String getHiddenCircleId() {
		return hiddenCircleId;
	}

	/**
	 * @param hiddenCircleId
	 *            The hiddenCircleId to set.
	 */
	public void setHiddenCircleId(String hiddenCircleId) {
		this.hiddenCircleId = hiddenCircleId;
	}

	private ArrayList displayDetails;

	/**
	 * @return Returns the checkPassword.
	 */
	public String getCheckPassword() {
		return checkPassword;
	}

	/**
	 * @param checkPassword
	 *            The checkPassword to set.
	 */
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

	/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            The circleId to set.
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return Returns the circleName.
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName
	 *            The circleName to set.
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return Returns the displayDetails.
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}

	/**
	 * @param displayDetails
	 *            The displayDetails to set.
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}

	/**
	 * @return Returns the groupId.
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            The groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return Returns the loginAttempted.
	 */
	public String getLoginAttempted() {
		return loginAttempted;
	}

	/**
	 * @param loginAttempted
	 *            The loginAttempted to set.
	 */
	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
	}

	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            The loginId to set.
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	 * @return Returns the passwordChangedDate.
	 */
	public String getPasswordChangedDate() {
		return passwordChangedDate;
	}

	/**
	 * @param passwordChangedDate
	 *            The passwordChangedDate to set.
	 */
	public void setPasswordChangedDate(String passwordChangedDate) {
		this.passwordChangedDate = passwordChangedDate;
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
	 * @return Returns the userCreateDate.
	 */
	public String getUserCreateDate() {
		return userCreateDate;
	}

	/**
	 * @param userCreateDate
	 *            The userCreateDate to set.
	 */
	public void setUserCreateDate(String userCreateDate) {
		this.userCreateDate = userCreateDate;
	}

	/**
	 * @return Returns the userCreatedBy.
	 */
	public String getUserCreatedBy() {
		return userCreatedBy;
	}

	/**
	 * @param userCreatedBy
	 *            The userCreatedBy to set.
	 */
	public void setUserCreatedBy(String userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	/**
	 * @return Returns the userCreatedByName.
	 */
	public String getUserCreatedByName() {
		return userCreatedByName;
	}

	/**
	 * @param userCreatedByName
	 *            The userCreatedByName to set.
	 */
	public void setUserCreatedByName(String userCreatedByName) {
		this.userCreatedByName = userCreatedByName;
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return Returns the userUpdatedBy.
	 */
	public String getUserUpdatedBy() {
		return userUpdatedBy;
	}

	/**
	 * @param userUpdatedBy
	 *            The userUpdatedBy to set.
	 */
	public void setUserUpdatedBy(String userUpdatedBy) {
		this.userUpdatedBy = userUpdatedBy;
	}

	/**
	 * @return Returns the userUpdatedByName.
	 */
	public String getUserUpdatedByName() {
		return userUpdatedByName;
	}

	/**
	 * @param userUpdatedByName
	 *            The userUpdatedByName to set.
	 */
	public void setUserUpdatedByName(String userUpdatedByName) {
		this.userUpdatedByName = userUpdatedByName;
	}

	/**
	 * @return Returns the userUpdatedDate.
	 */
	public String getUserUpdatedDate() {
		return userUpdatedDate;
	}

	/**
	 * @param userUpdatedDate
	 *            The userUpdatedDate to set.
	 */
	public void setUserUpdatedDate(String userUpdatedDate) {
		this.userUpdatedDate = userUpdatedDate;
	}

	// public ActionErrors validate(ActionMapping mapping, HttpServletRequest
	// request){
	// ActionErrors errors=new ActionErrors();
	//		
	//		
	// logger.info("..........UserMasterBean validate method
	// invoked..........");
	// /*
	// if(loginId.trim().equals("")||loginId.length()==0)
	// {
	// errors.add("loginId",new ActionError("errors.loginId.required"));
	// }
	// */
	// /**
	// * If A new User is created ,
	// * then the password field and checkPassword fields need to be checked
	// *//*
	// if(!(methodName==null)&&(methodName.trim().equalsIgnoreCase("createUser"))){
	// // check if password is blank
	// if(password.trim().equals("")||password.length()==0)
	// {
	// errors.add("password",new ActionError("errors.password.required"));
	// }
	// // check if checkPassword is blank
	// if(checkPassword.trim().equals("")||checkPassword.length()==0)
	// {
	// errors.add("checkPassword",new
	// ActionError("errors.checkPassword.required"));
	// }
	// // check if password and checkPassword are different
	// if(!(checkPassword.trim().equals(password.trim())))
	// {
	// errors.add("password",new ActionError("errors.password.mismatch"));
	// }
	// }
	//		
	// if(circleId.equals("0")){
	// errors.add("circleId",new ActionError("errors.circleId.required"));
	// }
	// if(groupId.equals("0")){
	// errors.add("groupId",new ActionError("errors.groupId.required"));
	// }
	// if((emailId==null)||(emailId.length()==0)||(emailId.trim().equals(""))){
	// errors.add("emailId",new ActionError("errors.emailId.required"));
	// }
	// if((status==null)||(status.length()==0)||(status.trim().equals(""))){
	// errors.add("status",new ActionError("errors.status.required"));
	// }
	// if((userType==null)||(userType.length()==0)){
	// errors.add("userType",new ActionError("errors.userType.required"));
	// }
	// */
	// return errors;
	// }
	//	

	/**
	 * @return Returns the statusName.
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            The statusName to set.
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
	 * @param userTypeName
	 *            The userTypeName to set.
	 */
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	/**
	 * @return the newEmailId
	 */
	public String getOldEmailId() {
		return oldEmailId;
	}

	/**
	 * @param newEmailId
	 *            the newEmailId to set
	 */
	public void setOldEmailId(String oldEmailId) {
		this.oldEmailId = oldEmailId;
	}

	/**
	 * @return the page1
	 */
	public String getPage1() {
		return page1;
	}

	/**
	 * @param page1
	 *            the page1 to set
	 */
	public void setPage1(String page1) {
		this.page1 = page1;
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
	 * @return the zoneList
	 */
	public ArrayList getZoneList() {
		return zoneList;
	}

	/**
	 * @param zoneList the zoneList to set
	 */
	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
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
