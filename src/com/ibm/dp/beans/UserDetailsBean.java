package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * Form bean for a Struts application.
 * 
 * @version 	1.0
 * @author      Ashad
 */
public class UserDetailsBean extends ActionForm 
{
	
	private static final long serialVersionUID = 1L;
	private String userId ="";
	private String loginId="";
	//private String forgotloginId="";
	private int groupId;
	private String firstName="";
	private String middleName="";
	private String lastName="";
	private String mobile="";
	private String email="";
	private String password="";
	private String passwdExpiryDate="";
	private String passwdLastChange="";
	private String circleId;
	private ArrayList circleList;
	private ArrayList groupList;
	private ArrayList userRightsList;
	private ArrayList moduleList;
	private String selectedCircleId="";
	private String selectedGroupId="";
	private String status="";
	private ArrayList userTypeList = null;
	private String selectedUserType = "";
	private String circleName ="";
	private String groupName ="";
	private String groupType = "";
	private String userType = null;
	private String smsisdn="";
	private String simsi="";
	private String ssim="";
	private String dealerName="";
	private String circleCode;
	private String userIp;
	private int firstTimeLogIn;
	private int loginAttempted;
	private String loginStatus;
	private String hubCode;
	private String hubName;
	private ArrayList zoneList ;
	private int zoneCode;
	private String zoneName;
	
	public String getHubName() {
		return hubName;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public String getHubCode() {
		return hubCode;
	}
	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}
	/**
	 * @return the loginStatus
	 */
	public String getLoginStatus() {
		return loginStatus;
	}
	/**
	 * @param loginStatus the loginStatus to set
	 */
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	/**
	 * @return the loginAttempted
	 */
	public int getLoginAttempted() {
		return loginAttempted;
	}
	/**
	 * @param loginAttempted the loginAttempted to set
	 */
	public void setLoginAttempted(int loginAttempted) {
		this.loginAttempted = loginAttempted;
	}
	/**
	 * @return Returns the dealerName.
	 */
	public String getDealerName() {
		return dealerName;
	}
	/**
	 * @param dealerName The dealerName to set.
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	/**
	 * @return Returns the simsi.
	 */
	public String getSimsi() {
		return simsi;
	}
	/**
	 * @param simsi The simsi to set.
	 */
	public void setSimsi(String simsi) {
		this.simsi = simsi;
	}
	/**
	 * @return Returns the smsisdn.
	 */
	public String getSmsisdn() {
		return smsisdn;
	}
	/**
	 * @param smsisdn The smsisdn to set.
	 */
	public void setSmsisdn(String smsisdn) {
		this.smsisdn = smsisdn;
	}
	/**
	 * @return Returns the ssim.
	 */
	public String getSsim() {
		return ssim;
	}
	/**
	 * @param ssim The ssim to set.
	 */
	public void setSsim(String ssim) {
		this.ssim = ssim;
	}
	/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId The circleId to set.
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	/**
	 * @return Returns the circleList.
	 */
	public ArrayList getCircleList() {
		return circleList;
	}
	/**
	 * @param circleList The circleList to set.
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
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
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the groupId.
	 */
	public int getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return Returns the groupList.
	 */
	public ArrayList getGroupList() {
		return groupList;
	}
	/**
	 * @param groupList The groupList to set.
	 */
	public void setGroupList(ArrayList groupList) {
		this.groupList = groupList;
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
	/**
	 * @return Returns the groupType.
	 */
	public String getGroupType() {
		return groupType;
	}
	/**
	 * @param groupType The groupType to set.
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId The loginId to set.
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return Returns the mobile.
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return Returns the moduleList.
	 */
	public ArrayList getModuleList() {
		return moduleList;
	}
	/**
	 * @param moduleList The moduleList to set.
	 */
	public void setModuleList(ArrayList moduleList) {
		this.moduleList = moduleList;
	}
	/**
	 * @return Returns the passwdExpiryDate.
	 */
	public String getPasswdExpiryDate() {
		return passwdExpiryDate;
	}
	/**
	 * @param passwdExpiryDate The passwdExpiryDate to set.
	 */
	public void setPasswdExpiryDate(String passwdExpiryDate) {
		this.passwdExpiryDate = passwdExpiryDate;
	}
	/**
	 * @return Returns the passwdLastChange.
	 */
	public String getPasswdLastChange() {
		return passwdLastChange;
	}
	/**
	 * @param passwdLastChange The passwdLastChange to set.
	 */
	public void setPasswdLastChange(String passwdLastChange) {
		this.passwdLastChange = passwdLastChange;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the selectedCircleId.
	 */
	public String getSelectedCircleId() {
		return selectedCircleId;
	}
	/**
	 * @param selectedCircleId The selectedCircleId to set.
	 */
	public void setSelectedCircleId(String selectedCircleId) {
		this.selectedCircleId = selectedCircleId;
	}
	/**
	 * @return Returns the selectedGroupId.
	 */
	public String getSelectedGroupId() {
		return selectedGroupId;
	}
	/**
	 * @param selectedGroupId The selectedGroupId to set.
	 */
	public void setSelectedGroupId(String selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}
	/**
	 * @return Returns the selectedUserType.
	 */
	public String getSelectedUserType() {
		return selectedUserType;
	}
	/**
	 * @param selectedUserType The selectedUserType to set.
	 */
	public void setSelectedUserType(String selectedUserType) {
		this.selectedUserType = selectedUserType;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the userId.
	 */
	
	public ArrayList getUserRightsList() {
		return userRightsList;
	}
	/**
	 * @param userRightsList The userRightsList to set.
	 */
	public void setUserRightsList(ArrayList userRightsList) {
		this.userRightsList = userRightsList;
	}
	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return Returns the userTypeList.
	 */
	public ArrayList getUserTypeList() {
		return userTypeList;
	}
	/**
	 * @param userTypeList The userTypeList to set.
	 */
	public void setUserTypeList(ArrayList userTypeList) {
		this.userTypeList = userTypeList;
	}

	
	
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	/**
	 * @return the userIp
	 */
	public String getUserIp() {
		return userIp;
	}
	/**
	 * @param userIp the userIp to set
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	/**
	 * @return the firstTimeLogIn
	 */
	public int getFirstTimeLogIn() {
		return firstTimeLogIn;
	}
	/**
	 * @param firstTimeLogIn the firstTimeLogIn to set
	 */
	public void setFirstTimeLogIn(int firstTimeLogIn) {
		this.firstTimeLogIn = firstTimeLogIn;
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
