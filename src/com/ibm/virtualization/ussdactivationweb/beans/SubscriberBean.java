package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author Ashad
 */
public class SubscriberBean extends ActionForm
{
private String loginId="";
private String name="";
private String password="";
private String smsisdn="";
private String simsi="";
private String ssim="";

private String userId="";

private String serviceClass="";

private String dealerName="";
private String dealerStatus="";
private Date registerationDate;

private String fosStatus="";
private Date verificationDate;
private String fosName="";

private String activationStatus="";
private Date activationDate;
private String activatedBy="";


private String circle = null;

private String methodName = null;

private String circleId;

private String userRole;

private List circleList = null;

private String initialCircleValue=null;

private ArrayList userList;
private String circleCode;

private String status;
private int tranTypeId;
private String tranTypeName;
private String createdDt;
private String requesterMsisdn;
private ArrayList requesterDeatils;
private String MobileNo;
private String userName;
private String userType;
private String page1 = "";

private String releasedDate ;


private ArrayList fileInfoDataList;
private String reportCreatedDate = "";
private String reportCurrentDate = "";
private String fileName="";
private String reportType1;
private String noOfDays;

private String registeredDate;

private String subsName;

private String verifiedDate;
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
/**
 * @return the reportType1
 */
public String getReportType1() {
	return reportType1;
}
/**
 * @param reportType1 the reportType1 to set
 */
public void setReportType1(String reportType1) {
	this.reportType1 = reportType1;
}

/**
 * @return the fileInfoDataList
 */
public ArrayList getFileInfoDataList() {
	return fileInfoDataList;
}
/**
 * @param fileInfoDataList the fileInfoDataList to set
 */
public void setFileInfoDataList(ArrayList fileInfoDataList) {
	this.fileInfoDataList = fileInfoDataList;
}


private String description;
private ArrayList subList;
private String message;

private String msisdn;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public ArrayList getSubList() {
	return subList;
}
public void setSubList(ArrayList subList) {
	this.subList = subList;
}
public String getPage1() {
	return page1;
}
public void setPage1(String page1) {
	this.page1 = page1;
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
 * @return Returns the loginId.
 */

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
public String getUserId() {
	return userId;
}
/**
 * @param userId The userId to set.
 */
public void setUserId(String userId) {
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
 * @return the circleId
 */
public String getCircleId() {
	return circleId;
}
/**
 * @param circleId the circleId to set
 */
public void setCircleId(String circleId) {
	this.circleId = circleId;
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
public String getCircleCode() {
	return circleCode;
}
public void setCircleCode(String circleCode) {
	this.circleCode = circleCode;
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
/**
 * @return the userType
 */
public String getUserType() {
	return userType;
}
/**
 * @param userType the userType to set
 */
public void setUserType(String userType) {
	this.userType = userType;
}
/**
 * @return the reportCreatedDate
 */
public String getReportCreatedDate() {
	return reportCreatedDate;
}
/**
 * @param reportCreatedDate the reportCreatedDate to set
 */
public void setReportCreatedDate(String reportCreatedDate) {
	this.reportCreatedDate = reportCreatedDate;
}
/**
 * @return the reportCurrentDate
 */
public String getReportCurrentDate() {
	return reportCurrentDate;
}
/**
 * @param reportCurrentDate the reportCurrentDate to set
 */
public void setReportCurrentDate(String reportCurrentDate) {
	this.reportCurrentDate = reportCurrentDate;
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
public String getReleasedDate() {
	return releasedDate;
}
public void setReleasedDate(String releasedDate) {
	this.releasedDate = releasedDate;
}
public String getMsisdn() {
	return msisdn;
}
public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
}
/**
 * @return the noOfDays
 */
public String getNoOfDays() {
	return noOfDays;
}
/**
 * @param noOfDays the noOfDays to set
 */
public void setNoOfDays(String noOfDays) {
	this.noOfDays = noOfDays;
}


}
