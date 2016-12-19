/*
 * Created on Jul 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ashad
 *
 */
public class SubscriberDTO 
{
	protected String loginId="";
	protected String name="";
	protected String password="";
	protected String smsisdn;
	protected String simsi="";
	protected String ssim="";
	protected String circleCode;
	
	protected int subId;
	protected int userId;
	protected int circleId;
	protected String email="";
	protected String userType = null;
	protected int groupId;
	protected String serviceClass="";

	protected String dealerName="";
	protected String dealerStatus="";
	protected Date registerationDate;

	protected String fosStatus="";
	protected Date verificationDate;
	protected String fosName="";

	protected String activationStatus="";
	protected Date activationDate;
	protected String activatedBy="";
	protected String circle = null;
	protected String methodName = null;
	protected String userRole;

	protected List circleList = null;

	protected String initialCircleValue=null;

	protected ArrayList userList;
	
	protected String status;
	protected int tranTypeId;
	protected String tranTypeName;
	protected String createdDt;
	protected String requesterMsisdn;
	protected ArrayList requesterDeatils;
	protected String MobileNo;
	protected String userName;
	protected int serviceClassId;
	protected String rowNum;
	protected String subscriberStatus;
	protected String releasedDate = "";
	
	protected String fileId;
	protected String description;
	
	protected String msisdn;


	protected String fileName;

	protected String reportEndDate;
	
	protected String registeredDate;
	
	protected String verifiedDate;
	
	protected String subsName;
	
	/**
	 * @return the subsName
	 */
	public String getSubsName() {
		return subsName;
	}
	/**
	 * @param subsName the subsName to set
	 */
	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	protected String reportStartDate;


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the subscriberStatus
	 */
	public String getSubscriberStatus() {
		return subscriberStatus;
	}
	/**
	 * @param subscriberStatus the subscriberStatus to set
	 */
	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	/**
	 * @return Returns the circleId.
	 */
	public int getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId The circleId to set.
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
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
	 * @return Returns the subId.
	 */
	public int getSubId() {
		return subId;
	}
	/**
	 * @param subId The subId to set.
	 */
	public void setSubId(int subId) {
		this.subId = subId;
	}
	/**
	 * @return Returns the dealerName.
	 */
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return Returns the dealername.
	 */
	
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
	 * @return Returns the userId.
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the activatedBy
	 */
	public String getActivatedBy() {
		return activatedBy;
	}
	/**
	 * @param activatedBy the activatedBy to set
	 */
	public void setActivatedBy(String activatedBy) {
		this.activatedBy = activatedBy;
	}
	/**
	 * @return the activationDate
	 */
	public Date getActivationDate() {
		return activationDate;
	}
	/**
	 * @param activationDate the activationDate to set
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	/**
	 * @return the activationStatus
	 */
	public String getActivationStatus() {
		return activationStatus;
	}
	/**
	 * @param activationStatus the activationStatus to set
	 */
	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}
	/**
	 * @return the circle
	 */
	public String getCircle() {
		return circle;
	}
	/**
	 * @param circle the circle to set
	 */
	public void setCircle(String circle) {
		this.circle = circle;
	}
	/**
	 * @return the circleList
	 */
	public List getCircleList() {
		return circleList;
	}
	/**
	 * @param circleList the circleList to set
	 */
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}
	/**
	 * @return the dealerStatus
	 */
	public String getDealerStatus() {
		return dealerStatus;
	}
	/**
	 * @param dealerStatus the dealerStatus to set
	 */
	public void setDealerStatus(String dealerStatus) {
		this.dealerStatus = dealerStatus;
	}
	/**
	 * @return the fosName
	 */
	public String getFosName() {
		return fosName;
	}
	/**
	 * @param fosName the fosName to set
	 */
	public void setFosName(String fosName) {
		this.fosName = fosName;
	}
	/**
	 * @return the fosStatus
	 */
	public String getFosStatus() {
		return fosStatus;
	}
	/**
	 * @param fosStatus the fosStatus to set
	 */
	public void setFosStatus(String fosStatus) {
		this.fosStatus = fosStatus;
	}
	/**
	 * @return the initialCircleValue
	 */
	public String getInitialCircleValue() {
		return initialCircleValue;
	}
	/**
	 * @param initialCircleValue the initialCircleValue to set
	 */
	public void setInitialCircleValue(String initialCircleValue) {
		this.initialCircleValue = initialCircleValue;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the registerationDate
	 */
	public Date getRegisterationDate() {
		return registerationDate;
	}
	/**
	 * @param registerationDate the registerationDate to set
	 */
	public void setRegisterationDate(Date registerationDate) {
		this.registerationDate = registerationDate;
	}
	/**
	 * @return the serviceClass
	 */
	public String getServiceClass() {
		return serviceClass;
	}
	/**
	 * @param serviceClass the serviceClass to set
	 */
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	/**
	 * @return the userList
	 */
	public ArrayList getUserList() {
		return userList;
	}
	/**
	 * @param userList the userList to set
	 */
	public void setUserList(ArrayList userList) {
		this.userList = userList;
	}
	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @return the verificationDate
	 */
	public Date getVerificationDate() {
		return verificationDate;
	}
	/**
	 * @param verificationDate the verificationDate to set
	 */
	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}
	public String getSmsisdn() {
		return smsisdn;
	}
	public void setSmsisdn(String smsisdn) {
		this.smsisdn = smsisdn;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the tranTypeId
	 */
	public int getTranTypeId() {
		return tranTypeId;
	}
	/**
	 * @param tranTypeId the tranTypeId to set
	 */
	public void setTranTypeId(int tranTypeId) {
		this.tranTypeId = tranTypeId;
	}
	/**
	 * @return the tranTypeName
	 */
	public String getTranTypeName() {
		return tranTypeName;
	}
	/**
	 * @param tranTypeName the tranTypeName to set
	 */
	public void setTranTypeName(String tranTypeName) {
		this.tranTypeName = tranTypeName;
	}
	/**
	 * @return the createdDt
	 */
	public String getCreatedDt() {
		return createdDt;
	}
	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	/**
	 * @return the requesterMsisdn
	 */
	public String getRequesterMsisdn() {
		return requesterMsisdn;
	}
	/**
	 * @param requesterMsisdn the requesterMsisdn to set
	 */
	public void setRequesterMsisdn(String requesterMsisdn) {
		this.requesterMsisdn = requesterMsisdn;
	}
	/**
	 * @return the requesterDeatils
	 */
	public ArrayList getRequesterDeatils() {
		return requesterDeatils;
	}
	/**
	 * @param requesterDeatils the requesterDeatils to set
	 */
	public void setRequesterDeatils(ArrayList requesterDeatils) {
		this.requesterDeatils = requesterDeatils;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return MobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setServiceClassId(int serviceClassId) {
		this.serviceClassId = serviceClassId;
	}
	/**
	 * @return the serviceClassId
	 */
	public int getServiceClassId() {
		return serviceClassId;
	}

	public String getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the reportEndDate
	 */
	public String getReportEndDate() {
		return reportEndDate;
	}
	/**
	 * @param reportEndDate the reportEndDate to set
	 */
	public void setReportEndDate(String reportEndDate) {
		this.reportEndDate = reportEndDate;
	}
	/**
	 * @return the reportStartDate
	 */
	public String getReportStartDate() {
		return reportStartDate;
	}
	/**
	 * @param reportStartDate the reportStartDate to set
	 */
	public void setReportStartDate(String reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	
	
}
