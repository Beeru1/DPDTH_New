package com.ibm.dp.dto;

import java.io.Serializable;

public class AccountWebDTO implements Serializable {
	
	private String processType;
	private String accountCode;	
	private String accountZoneId;	
	private String contactName;
	private String iPassword;
	private String checkIPassword;
	//private String groupId;
	private String groupId ;
	private String groupName;
	private String statusName;
	private String rate;
	private String threshold;
	private String mPassword;
	private String mobileNumber;
	private String parentAccount;
	private String simNumber;
	private String accountName;
	private String accountAddress;
	private String regionId;
	private String email;
	private String category;
	private String circleId;
	private String status;
	private String openingBalance;
	private String openingDt;
	private String createdDt;
	private String updateDt;
	private String createdBy;
	private String updatedBy;
	private String methodName;
	private String circleName;
	private String regionName;
	private String searchFieldName;
	private String searchFieldValue;	
	private String availableBalance;
	private String operatingBalance;
	private String accountLevel;
	private String accountStatus;	
	private String checkMpassword;	
	private String userType; 
	private String showCircle; 	
	private String startDt;	
	private String endDt;	
	private String submitStatus="0";	
	private boolean flagForCircleDisplay ;	
	private String accountLevelStatus ;	
	private String accountLevelId ;	
	private String editStatus;
    private String accountStateId ;	
	private String accountCityId ;	
	//private String accountPinNumber ;
	private String accountPinNumber ;	
	private String isbillable ;	
	private String billableCode ; 	
	//private String billableCodeId ; 
	private String billableCodeId ;	
	private String showBillableCode ;	
	private String parentAccountId;
	private String rootLevelId;	
	private boolean flagForCityFocus ;	
	private String listStatus ;	
	private String unlockStatus;	
	private String resetStatus;	
	private String loginAttempted;	
	private boolean locked;	
	private String levelName;	
    private String page;    
    private String lapuMobileNo;    
    private String lapuMobileNo2;    
    private String FTAMobileNo;    
    private String FTAMobileNo1;    
    private String ZBMId ;    
    private String ZSMId ;    
    private String tradeId ;    
    private String accountAddress2; 
    private String distId;
    private String fseId;
    private String retailerId;
    private String distName;
    private String distLapuNo;
    private String fseName;
    private String fseLapuNo;
    private String retailerName;
    private String retailerLapuNo;
    private String accountId ;
    private String circleid ;
	public String getAccountAddress() {
		return accountAddress;
	}
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}
	public String getAccountAddress2() {
		return accountAddress2;
	}
	public void setAccountAddress2(String accountAddress2) {
		this.accountAddress2 = accountAddress2;
	}
	public String getAccountCityId() {
		return accountCityId;
	}
	public void setAccountCityId(String accountCityId) {
		this.accountCityId = accountCityId;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getAccountLevelId() {
		return accountLevelId;
	}
	public void setAccountLevelId(String accountLevelId) {
		this.accountLevelId = accountLevelId;
	}
	public String getAccountLevelStatus() {
		return accountLevelStatus;
	}
	public void setAccountLevelStatus(String accountLevelStatus) {
		this.accountLevelStatus = accountLevelStatus;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPinNumber() {
		return accountPinNumber;
	}
	public void setAccountPinNumber(String accountPinNumber) {
		this.accountPinNumber = accountPinNumber;
	}
	public String getAccountStateId() {
		return accountStateId;
	}
	public void setAccountStateId(String accountStateId) {
		this.accountStateId = accountStateId;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountZoneId() {
		return accountZoneId;
	}
	public void setAccountZoneId(String accountZoneId) {
		this.accountZoneId = accountZoneId;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getBillableCode() {
		return billableCode;
	}
	public void setBillableCode(String billableCode) {
		this.billableCode = billableCode;
	}
	public String getBillableCodeId() {
		return billableCodeId;
	}
	public void setBillableCodeId(String billableCodeId) {
		this.billableCodeId = billableCodeId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCheckIPassword() {
		return checkIPassword;
	}
	public void setCheckIPassword(String checkIPassword) {
		this.checkIPassword = checkIPassword;
	}
	public String getCheckMpassword() {
		return checkMpassword;
	}
	public void setCheckMpassword(String checkMpassword) {
		this.checkMpassword = checkMpassword;
	}
	public String getCircleid() {
		return circleid;
	}
	public void setCircleid(String circleid) {
		this.circleid = circleid;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getDistLapuNo() {
		return distLapuNo;
	}
	public void setDistLapuNo(String distLapuNo) {
		this.distLapuNo = distLapuNo;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getEditStatus() {
		return editStatus;
	}
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public boolean isFlagForCircleDisplay() {
		return flagForCircleDisplay;
	}
	public void setFlagForCircleDisplay(boolean flagForCircleDisplay) {
		this.flagForCircleDisplay = flagForCircleDisplay;
	}
	public boolean isFlagForCityFocus() {
		return flagForCityFocus;
	}
	public void setFlagForCityFocus(boolean flagForCityFocus) {
		this.flagForCityFocus = flagForCityFocus;
	}
	public String getFseId() {
		return fseId;
	}
	public void setFseId(String fseId) {
		this.fseId = fseId;
	}
	public String getFseLapuNo() {
		return fseLapuNo;
	}
	public void setFseLapuNo(String fseLapuNo) {
		this.fseLapuNo = fseLapuNo;
	}
	public String getFseName() {
		return fseName;
	}
	public void setFseName(String fseName) {
		this.fseName = fseName;
	}
	public String getFTAMobileNo() {
		return FTAMobileNo;
	}
	public void setFTAMobileNo(String mobileNo) {
		FTAMobileNo = mobileNo;
	}
	public String getFTAMobileNo1() {
		return FTAMobileNo1;
	}
	public void setFTAMobileNo1(String mobileNo1) {
		FTAMobileNo1 = mobileNo1;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getIPassword() {
		return iPassword;
	}
	public void setIPassword(String password) {
		iPassword = password;
	}
	public String getIsbillable() {
		return isbillable;
	}
	public void setIsbillable(String isbillable) {
		this.isbillable = isbillable;
	}
	public String getLapuMobileNo() {
		return lapuMobileNo;
	}
	public void setLapuMobileNo(String lapuMobileNo) {
		this.lapuMobileNo = lapuMobileNo;
	}
	public String getLapuMobileNo2() {
		return lapuMobileNo2;
	}
	public void setLapuMobileNo2(String lapuMobileNo2) {
		this.lapuMobileNo2 = lapuMobileNo2;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getListStatus() {
		return listStatus;
	}
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String getLoginAttempted() {
		return loginAttempted;
	}
	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMPassword() {
		return mPassword;
	}
	public void setMPassword(String password) {
		mPassword = password;
	}
	public String getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}
	public String getOpeningDt() {
		return openingDt;
	}
	public void setOpeningDt(String openingDt) {
		this.openingDt = openingDt;
	}
	public String getOperatingBalance() {
		return operatingBalance;
	}
	public void setOperatingBalance(String operatingBalance) {
		this.operatingBalance = operatingBalance;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getParentAccount() {
		return parentAccount;
	}
	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}
	public String getParentAccountId() {
		return parentAccountId;
	}
	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getResetStatus() {
		return resetStatus;
	}
	public void setResetStatus(String resetStatus) {
		this.resetStatus = resetStatus;
	}
	public String getRetailerId() {
		return retailerId;
	}
	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}
	public String getRetailerLapuNo() {
		return retailerLapuNo;
	}
	public void setRetailerLapuNo(String retailerLapuNo) {
		this.retailerLapuNo = retailerLapuNo;
	}
	public String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	public String getRootLevelId() {
		return rootLevelId;
	}
	public void setRootLevelId(String rootLevelId) {
		this.rootLevelId = rootLevelId;
	}
	public String getSearchFieldName() {
		return searchFieldName;
	}
	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}
	public String getSearchFieldValue() {
		return searchFieldValue;
	}
	public void setSearchFieldValue(String searchFieldValue) {
		this.searchFieldValue = searchFieldValue;
	}
	public String getShowBillableCode() {
		return showBillableCode;
	}
	public void setShowBillableCode(String showBillableCode) {
		this.showBillableCode = showBillableCode;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getSimNumber() {
		return simNumber;
	}
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getUnlockStatus() {
		return unlockStatus;
	}
	public void setUnlockStatus(String unlockStatus) {
		this.unlockStatus = unlockStatus;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getZBMId() {
		return ZBMId;
	}
	public void setZBMId(String id) {
		ZBMId = id;
	}
	public String getZSMId() {
		return ZSMId;
	}
	public void setZSMId(String id) {
		ZSMId = id;
	}
  

}
