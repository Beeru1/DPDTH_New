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

/**
 * This class is used for Data Transfer of a Account Details between different
 * layers.
 * 
 * @author bhanu
 * 
 */
import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {

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

	
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	 * @return the accountLevelId
	 */
	public String getAccountLevelId() {
		return accountLevelId;
	}

	/**
	 * @param accountLevelId the accountLevelId to set
	 */
	public void setAccountLevelId(String accountLevelId) {
		this.accountLevelId = accountLevelId;
	}

	/**
	 * @return the createdByName
	 */
	public String getCreatedByName() {
		return createdByName;
	}

	/**
	 * @param createdByName the createdByName to set
	 */
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * @return Returns the parentaccountId.
	 */
	public long getParentAccountId() {
		return parentAccountId;
	}

	/**
	 * @param parentaccountId
	 *            The parentaccountId to set.
	 */
	public void setParentAccountId(long parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	/**
	 * @return Returns the accountLevel.
	 */
	public String getAccountLevel() {
		return accountLevel;
	}

	/**
	 * @param accountLevel
	 *            The accountLevel to set.
	 */
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	/**
	 * @return Returns the operatingBalance.
	 */
	public double getOperatingBalance() {
		return operatingBalance;
	}

	/**
	 * @param operatingBalance
	 *            The operatingBalance to set.
	 */
	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}

	/**
	 * @return Returns the availableBalance.
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            The availableBalance to set.
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/** Creates a dto for the REC_ACCOUNT_MSTR table */
	public Account() {
	}

	/**
	 * Get accountId associated with this object.
	 * 
	 * @return accountId
	 */

	public long getAccountId() {
		return accountId;
	}

	/**
	 * Set accountId associated with this object.
	 * 
	 * @param accountId
	 *            The accountId value to set
	 */

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * Get mobileNumber associated with this object.
	 * 
	 * @return mobileNumber
	 */

	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Set mobileNumber associated with this object.
	 * 
	 * @param mobileNumber
	 *            The mobileNumber value to set
	 */

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Get simNumber associated with this object.
	 * 
	 * @return simNumber
	 */

	public String getSimNumber() {
		return simNumber;
	}

	/**
	 * Set simNumber associated with this object.
	 * 
	 * @param simNumber
	 *            The simNumber value to set
	 */

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	/**
	 * Get accountName associated with this object.
	 * 
	 * @return accountName
	 */

	public String getAccountName() {
		return accountName;
	}

	/**
	 * Set accountName associated with this object.
	 * 
	 * @param accountName
	 *            The accountName value to set
	 */

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Get accountAddress associated with this object.
	 * 
	 * @return accountAddress
	 */

	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * Set accountAddress associated with this object.
	 * 
	 * @param accountAddress
	 *            The accountAddress value to set
	 */

	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	/**
	 * Get regionId associated with this object.
	 * 
	 * @return regionId
	 */

	public String getRegionId() {
		return regionId;
	}

	/**
	 * Set regionId associated with this object.
	 * 
	 * @param regionId
	 *            The regionId value to set
	 */

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	/**
	 * Get email associated with this object.
	 * 
	 * @return email
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Set email associated with this object.
	 * 
	 * @param email
	 *            The email value to set
	 */

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get category associated with this object.
	 * 
	 * @return category
	 */

	public String getCategory() {
		return category;
	}

	/**
	 * Set category associated with this object.
	 * 
	 * @param category
	 *            The category value to set
	 */

	public void setCategory(String category) {
		this.category = category;
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
	 * Get status associated with this object.
	 * 
	 * @return status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * Set status associated with this object.
	 * 
	 * @param status
	 *            The status value to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get openingBalance associated with this object.
	 * 
	 * @return openingBalance
	 */

	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * Set openingBalance associated with this object.
	 * 
	 * @param openingBalance
	 *            The openingBalance value to set
	 */

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * Get openingDt associated with this object.
	 * 
	 * @return openingDt
	 */

	public String getOpeningDt() {
		return openingDt;
	}

	/**
	 * Set openingDt associated with this object.
	 * 
	 * @param openingDt
	 *            The openingDt value to set
	 */

	public void setOpeningDt(String openingDt) {
		this.openingDt = openingDt;
	}

	/**
	 * Get createdDt associated with this object.
	 * 
	 * @return createdDt
	 */

	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * Set createdDt associated with this object.
	 * 
	 * @param createdDt
	 *            The createdDt value to set
	 */

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * Get updateDt associated with this object.
	 * 
	 * @return updateDt
	 */

	public Date getUpdateDt() {
		return updateDt;
	}

	/**
	 * Set updateDt associated with this object.
	 * 
	 * @param updateDt
	 *            The updateDt value to set
	 */

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get updatedBy associated with this object.
	 * 
	 * @return updatedBy
	 */

	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy associated with this object.
	 * 
	 * @param updatedBy
	 *            The updatedBy value to set
	 */

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode
	 *            The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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
	 * @return Returns the iPassword.
	 */
	public String getIPassword() {
		return iPassword;
	}

	/**
	 * @param password
	 *            The iPassword to set.
	 */
	public void setIPassword(String password) {
		iPassword = password;
	}

	/**
	 * @return Returns the mPassword.
	 */
	public String getMPassword() {
		return mPassword;
	}

	/**
	 * @param password
	 *            The mPassword to set.
	 */
	public void setMPassword(String password) {
		mPassword = password;
	}

	/**
	 * @return Returns the parentAccount.
	 */
	public String getParentAccount() {
		return parentAccount;
	}

	/**
	 * @param parentAccount
	 *            The parentAccount to set.
	 */
	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}

	/**
	 * @return Returns the rate.
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the threshold.
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold
	 *            The threshold to set.
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
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
	 * @return the accountCityId
	 */
	public int getAccountCityId() {
		return accountCityId;
	}

	/**
	 * @param accountCityId the accountCityId to set
	 */
	public void setAccountCityId(int accountCityId) {
		this.accountCityId = accountCityId;
	}

	/**
	 * @return the accountPinNumber
	 */
	public int getAccountPinNumber() {
		return accountPinNumber;
	}

	/**
	 * @param accountPinNumber the accountPinNumber to set
	 */
	public void setAccountPinNumber(int accountPinNumber) {
		this.accountPinNumber = accountPinNumber;
	}

	/**
	 * @return the accountStateId
	 */
	public int getAccountStateId() {
		return accountStateId;
	}

	/**
	 * @param accountStateId the accountStateId to set
	 */
	public void setAccountStateId(int accountStateId) {
		this.accountStateId = accountStateId;
	}

	
	/**
	 * @return the accountCityName
	 */
	public String getAccountCityName() {
		return accountCityName;
	}

	/**
	 * @param accountCityName the accountCityName to set
	 */
	public void setAccountCityName(String accountCityName) {
		this.accountCityName = accountCityName;
	}

	/**
	 * @return the accountStateName
	 */
	public String getAccountStateName() {
		return accountStateName;
	}

	/**
	 * @param accountStateName the accountStateName to set
	 */
	public void setAccountStateName(String accountStateName) {
		this.accountStateName = accountStateName;
	}
	
	

	/**
	 * @return the billableCode
	 */
	public String getBillableCode() {
		return billableCode;
	}

	/**
	 * @param billableCode the billableCode to set
	 */
	public void setBillableCode(String billableCode) {
		this.billableCode = billableCode;
	}

	/**
	 * @return the isbillable
	 */
	public String getIsbillable() {
		return isbillable;
	}

	/**
	 * @param isbillable the isbillable to set
	 */
	public void setIsbillable(String isbillable) {
		this.isbillable = isbillable;
	}
	
	

	/**
	 * @return the billableCodeId
	 */
	public long getBillableCodeId() {
		return billableCodeId;
	}

	/**
	 * @param billableCodeId the billableCodeId to set
	 */
	public void setBillableCodeId(long billableCodeId) {
		this.billableCodeId = billableCodeId;
	}
	
	

	/**
	 * @return the rootLevelId
	 */
	public long getRootLevelId() {
		return rootLevelId;
	}

	/**
	 * @param rootLevelId the rootLevelId to set
	 */
	public void setRootLevelId(long rootLevelId) {
		this.rootLevelId = rootLevelId;
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
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "Account ( " + super.toString() + TAB + "accountId = "
				+ this.accountId + TAB + "parentAccountId = "
				+ this.parentAccountId + TAB + "accountCode = "
				+ this.accountCode 
				+ TAB + "groupId = " + this.groupId + TAB + "groupName = "
				+ this.groupName + TAB + "rate = " + this.rate + TAB
				+ "threshold = " + this.threshold + TAB + 
				 "circleName = " + this.circleName
				+ TAB + "mobileNumber = " + this.mobileNumber + TAB
				+ "parentAccount = " + this.parentAccount + TAB
				+ "simNumber = " + this.simNumber + TAB + "accountName = "
				+ this.accountName + TAB + "accountAddress = "
				+ this.accountAddress + TAB + "regionId = " + this.regionId
				+ TAB + "email = " + this.email + TAB + "category = "
				+ this.category + TAB + "circleId = " + this.circleId + TAB
				+ "status = " + this.status + TAB + "openingBalance = "
				+ this.openingBalance + TAB + "operatingBalance = "
				+ this.operatingBalance + TAB + "availableBalance = "
				+ this.availableBalance + TAB + "openingDt = " + this.openingDt
				+ TAB + "createdDt = " + this.createdDt + TAB + "updateDt = "
				+ this.updateDt + TAB + "createdBy = " + this.createdBy + TAB
				+ "updatedBy = " + this.updatedBy + TAB + "levelName = " + this.levelName + "accountLevel = "
				+ this.accountLevel + TAB + "accountLevelId :"+accountLevelId+ TAB +"accountStateId:"+this.accountStateId+ TAB+"accountPinNumber:" +this.accountPinNumber+"billableCode = "
				+ this.billableCode + TAB + "isbillable :"+isbillable+TAB + "accountCityId :"+this.accountCityId+TAB +"billableCodeId :"+this.billableCodeId+" circleCode : "+this.circleCode+")";

		return retValue;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the checkIPassword
	 */
	public String getCheckIPassword() {
		return checkIPassword;
	}

	/**
	 * @param checkIPassword the checkIPassword to set
	 */
	public void setCheckIPassword(String checkIPassword) {
		this.checkIPassword = checkIPassword;
	}

	/**
	 * @return the checkMpassword
	 */
	public String getCheckMpassword() {
		return checkMpassword;
	}

	/**
	 * @param checkMpassword the checkMpassword to set
	 */
	public void setCheckMpassword(String checkMpassword) {
		this.checkMpassword = checkMpassword;
	}
	/**
	 * @return get Account Level Name
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param set Account Level Name
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}





	
}
