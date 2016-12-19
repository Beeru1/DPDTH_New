package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.Date;

public class DPCreateAccountDto implements Serializable {

	private static final long serialVersionUID = -6700888532136994323L;

	private long accountId;

	private long parentAccountId;

	private String accountCode;

	private String iPassword;

	private int groupId;

	private String groupName;

	private double rate;

	private double threshold;

	private String mPassword;

	private String circleName;

	private String mobileNumber;

	private String parentAccount;

	private String simNumber;

	private String accountName;

	private String accountAddress;

	private String regionId;

	private String email;

	private String category;

	private int circleId;

	private String status;

	private double openingBalance;

	private double operatingBalance;

	private double availableBalance;

	private String openingDt;

	private Date createdDt;

	private Date updateDt;

	private long createdBy;

	private long updatedBy;
	
	private String levelName;

	private String accountLevel;
	
	private String rowNum;
	
	private String createdByName;
	
	private String accountLevelId;
	
	private int accountCityId;
	
	private int accountStateId;
	
	private int accountPinNumber;
	
	private String accountCityName;
	
	private String accountStateName;
	
	private String isbillable ;
	
	private String billableCode ; 
	
	private long billableCodeId ; 
	
	private long rootLevelId ; 
	
	private String userType ; 
	
	private int totalRecords;
	
	private boolean locked;
	
	private String checkMpassword;
	
	private String checkIPassword;
	
	private String loginName;
	
	private String circleCode;
	
	private String lapuMobileNo;
	
	private String accountAddress2;

	public String getAccountAddress2() {
		return accountAddress2;
	}

	public void setAccountAddress2(String accountAddress2) {
		this.accountAddress2 = accountAddress2;
	}

	public String getLapuMobileNo() {
		return lapuMobileNo;
	}

	public void setLapuMobileNo(String lapuMobileNo) {
		this.lapuMobileNo = lapuMobileNo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAccountAddress() {
		return accountAddress;
	}

	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	public int getAccountCityId() {
		return accountCityId;
	}

	public void setAccountCityId(int accountCityId) {
		this.accountCityId = accountCityId;
	}

	public String getAccountCityName() {
		return accountCityName;
	}

	public void setAccountCityName(String accountCityName) {
		this.accountCityName = accountCityName;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountPinNumber() {
		return accountPinNumber;
	}

	public void setAccountPinNumber(int accountPinNumber) {
		this.accountPinNumber = accountPinNumber;
	}

	public int getAccountStateId() {
		return accountStateId;
	}

	public void setAccountStateId(int accountStateId) {
		this.accountStateId = accountStateId;
	}

	public String getAccountStateName() {
		return accountStateName;
	}

	public void setAccountStateName(String accountStateName) {
		this.accountStateName = accountStateName;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getBillableCode() {
		return billableCode;
	}

	public void setBillableCode(String billableCode) {
		this.billableCode = billableCode;
	}

	public long getBillableCodeId() {
		return billableCodeId;
	}

	public void setBillableCodeId(long billableCodeId) {
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

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
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

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getOpeningDt() {
		return openingDt;
	}

	public void setOpeningDt(String openingDt) {
		this.openingDt = openingDt;
	}

	public double getOperatingBalance() {
		return operatingBalance;
	}

	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public long getRootLevelId() {
		return rootLevelId;
	}

	public void setRootLevelId(long rootLevelId) {
		this.rootLevelId = rootLevelId;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


}
