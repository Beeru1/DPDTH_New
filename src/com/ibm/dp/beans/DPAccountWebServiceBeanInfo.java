package com.ibm.dp.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class DPAccountWebServiceBeanInfo implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private String accountId;
	private String processType;

	private String accountCode;
	
	private String accountZoneId;
	
	private String contactName;

	private String iPassword;

	private String checkIPassword;

	//private String groupId;
	private int groupId =0;

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

	private ArrayList displayDetails;

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
	private int accountPinNumber = 0;
	
	private String isbillable ;
	
	private String billableCode ; 
	
	//private String billableCodeId ; 
	private int billableCodeId=0;
	
	private String showBillableCode ; 
	
	private long parentAccountId;
	private long rootLevelId;
	
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
    
    private int ZBMId = 0;
    
    private int ZSMId = 0;
    
    private int tradeId =0;
    
    private String accountAddress2;
    
    private ArrayList stateList;
    
    // Attributes for Aggregate View
    
    private ArrayList aggList;
    private String distId;
    private String fseId;
    private String retailerId;
    private String distName;
    private String distLapuNo;
    private String fseName;
    private String fseLapuNo;
    private String retailerName;
    private String retailerLapuNo;
    private int accountId = 0;
    private int circleid =0;
    private int code = 0;
    private int tradeCategoryId;
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
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
	public ArrayList getAggList() {
		return aggList;
	}
	public void setAggList(ArrayList aggList) {
		this.aggList = aggList;
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
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
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
	public long getParentAccountId() {
		return parentAccountId;
	}
	public void setParentAccountId(long parentAccountId) {
		this.parentAccountId = parentAccountId;
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
	public ArrayList getStateList() {
		return stateList;
	}
	public void setStateList(ArrayList stateList) {
		this.stateList = stateList;
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
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getAccountPinNumber() {
		return accountPinNumber;
	}
	public void setAccountPinNumber(int accountPinNumber) {
		this.accountPinNumber = accountPinNumber;
	}
	public int getBillableCodeId() {
		return billableCodeId;
	}
	public void setBillableCodeId(int billableCodeId) {
		this.billableCodeId = billableCodeId;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public long getRootLevelId() {
		return rootLevelId;
	}
	public void setRootLevelId(long rootLevelId) {
		this.rootLevelId = rootLevelId;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getAccountZoneId() {
		return accountZoneId;
	}
	public void setAccountZoneId(String accountZoneId) {
		this.accountZoneId = accountZoneId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getLapuMobileNo2() {
		return lapuMobileNo2;
	}
	public void setLapuMobileNo2(String lapuMobileNo2) {
		this.lapuMobileNo2 = lapuMobileNo2;
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
	public int getZBMId() {
		return ZBMId;
	}
	public void setZBMId(int id) {
		ZBMId = id;
	}
	public int getZSMId() {
		return ZSMId;
	}
	public void setZSMId(int id) {
		ZSMId = id;
	}
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public int getTradeCategoryId() {
		return tradeCategoryId;
	}
	public void setTradeCategoryId(int tradeCategoryId) {
		this.tradeCategoryId = tradeCategoryId;
	}
}
