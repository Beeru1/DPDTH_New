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

import com.ibm.virtualization.recharge.common.Utility;

/**
 * Form Bean class for  ACCOUNT
 * 
 * @author bhanu
 *  
 */
public class AccountFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098305345870553453L;

	private String accountId;

	private String accountCode;

	private String iPassword;

	private String checkIPassword;

	private String groupId;

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
	
	private String accountPinNumber ;
	
	private String isbillable ;
	
	private String billableCode ; 
	
	private String billableCodeId ; 
	
	private String showBillableCode ; 
	
	private long parentAccountId;
	
	private boolean flagForCityFocus ;
	
	private String listStatus ;
	
	private String unlockStatus;
	
	private String resetStatus;
	
	
	private String loginAttempted;
	
	private boolean locked;
	
	private String levelName;
	
    private String page;
	
	
	

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
	 * @return the accountLevelStatus
	 */
	public String getAccountLevelStatus() {
		return accountLevelStatus;
	}

	/**
	 * @param accountLevelStatus the accountLevelStatus to set
	 */
	public void setAccountLevelStatus(String accountLevelStatus) {
		this.accountLevelStatus = accountLevelStatus;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = "0";
	}

	public String getShowCircle() {
		return showCircle;
	}

	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCheckMpassword() {
		return checkMpassword;
	}

	public void setCheckMpassword(String checkMpassword) {
		this.checkMpassword = checkMpassword;
	}

	/**
	 * @return Returns the accountStatus.
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            The accountStatus to set.
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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
	 * @return Returns the accountLevel Name.
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param accountLevel Name
	 *            The accountLevel Name to set.
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}


	/**
	 * @return Returns the operatingBalance.
	 */
	public String getOperatingBalance() {
		return operatingBalance;
	}

	/**
	 * @param operatingBalance
	 *            The operatingBalance to set.
	 */
	public void setOperatingBalance(String operatingBalance) {
		if(operatingBalance.trim().equals(""))
    		this.operatingBalance = operatingBalance;
    	else
		this.operatingBalance = Utility.formatAmount(Double.parseDouble(operatingBalance));
	}

	/**
	 * @return Returns the availableBalance.
	 */
	public String getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            The availableBalance to set.
	 */
	public void setAvailableBalance(String availableBalance) {
		if(availableBalance.trim().equals(""))
    		this.availableBalance = availableBalance;
    	else
		this.availableBalance = Utility.formatAmount(Double.parseDouble(availableBalance));
	}

	
	/**
	 * Get accountId associated with this object.
	 * 
	 * @return accountId
	 */

	public String getAccountId() {
		return accountId;
	}

	/**
	 * Set accountId associated with this object.
	 * 
	 * @param accountId
	 *            The accountId value to set
	 */

	public void setAccountId(String accountId) {
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
	 * Get circleId associated with this object.
	 * 
	 * @return circleId
	 */

	public String getCircleId() {
		return circleId;
	}

	/**
	 * Set circleId associated with this object.
	 * 
	 * @param circleId
	 *            The circleId value to set
	 */

	public void setCircleId(String circleId) {
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

	public String getOpeningBalance() {
		
		return openingBalance;
	}

	/**
	 * Set openingBalance associated with this object.
	 * 
	 * @param openingBalance
	 *            The openingBalance value to set
	 */

	public void setOpeningBalance(String openingBalance) {
		if(openingBalance.trim().equals(""))
    		this.openingBalance = openingBalance;
    	else
		this.openingBalance = Utility.formatAmount(Double.parseDouble(openingBalance));
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

	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * Set createdDt associated with this object.
	 * 
	 * @param createdDt
	 *            The createdDt value to set
	 */

	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * Get updateDt associated with this object.
	 * 
	 * @return updateDt
	 */

	public String getUpdateDt() {
		return updateDt;
	}

	/**
	 * Set updateDt associated with this object.
	 * 
	 * @param updateDt
	 *            The updateDt value to set
	 */

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get updatedBy associated with this object.
	 * 
	 * @return updatedBy
	 */

	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy associated with this object.
	 * 
	 * @param updatedBy
	 *            The updatedBy value to set
	 */

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
	 * @return Returns the regionName.
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            The regionName to set.
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return Returns the searchFieldName.
	 */
	public String getSearchFieldName() {
		return searchFieldName;
	}

	/**
	 * @param searchFieldName
	 *            The searchFieldName to set.
	 */
	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}

	/**
	 * @return Returns the searchFieldValue.
	 */
	public String getSearchFieldValue() {
		return searchFieldValue;
	}

	/**
	 * @param searchFieldValue
	 *            The searchFieldValue to set.
	 */
	public void setSearchFieldValue(String searchFieldValue) {
		this.searchFieldValue = searchFieldValue;
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
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(String rate) {
		if((rate!=null) && (!rate.trim().equals(""))){
			this.rate = Utility.formatAmount(Double.parseDouble(rate));	
		}else{
			this.rate=rate;
		}
	}

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
	 * @return Returns the threshold.
	 */
	public String getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold
	 *            The threshold to set.
	 */
	public void setThreshold(String threshold) {
		
		if(threshold.trim().equals(""))
    		this.threshold = threshold;		
		else
			this.threshold = Utility.formatAmount(Double.parseDouble(threshold));
		

	}

	/**
	 * @return Returns the checkIPassword.
	 */
	public String getCheckIPassword() {
		return checkIPassword;
	}

	/**
	 * @param checkIPassword
	 *            The checkIPassword to set.
	 */
	public void setCheckIPassword(String checkIPassword) {
		this.checkIPassword = checkIPassword;
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

	public boolean isFlagForCircleDisplay() {
		return flagForCircleDisplay;
	}

	public void setFlagForCircleDisplay(boolean flagForCircleDisplay) {
		this.flagForCircleDisplay = flagForCircleDisplay;
	}
	
	

	/**
	 * @return the accountCityId
	 */
	public String getAccountCityId() {
		return accountCityId;
	}

	/**
	 * @param accountCityId the accountCityId to set
	 */
	public void setAccountCityId(String accountCityId) {
		this.accountCityId = accountCityId;
	}

	/**
	 * @return the accountPinNumber
	 */
	public String getAccountPinNumber() {
		return accountPinNumber;
	}

	/**
	 * @param accountPinNumber the accountPinNumber to set
	 */
	public void setAccountPinNumber(String accountPinNumber) {
		this.accountPinNumber = accountPinNumber;
	}

	/**
	 * @return the accountStateId
	 */
	public String getAccountStateId() {
		return accountStateId;
	}

	/**
	 * @param accountStateId the accountStateId to set
	 */
	public void setAccountStateId(String accountStateId) {
		this.accountStateId = accountStateId;
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
	public String getBillableCodeId() {
		return billableCodeId;
	}

	/**
	 * @param billableCodeId the billableCodeId to set
	 */
	public void setBillableCodeId(String billableCodeId) {
		this.billableCodeId = billableCodeId;
	}

	/**
	 * @return the showBillableCode
	 */
	public String getShowBillableCode() {
		return showBillableCode;
	}

	/**
	 * @param showBillableCode the showBillableCode to set
	 */
	public void setShowBillableCode(String showBillableCode) {
		this.showBillableCode = showBillableCode;
	}
	
	

	/**
	 * @return the parentAccountId
	 */
	public long getParentAccountId() {
		return parentAccountId;
	}

	/**
	 * @param parentAccountId the parentAccountId to set
	 */
	public void setParentAccountId(long parentAccountId) {
		this.parentAccountId = parentAccountId;
	}
	
	
	public boolean isFlagForCityFocus() {
		return flagForCityFocus;
	}

	public void setFlagForCityFocus(boolean flagForCityFocus) {
		this.flagForCityFocus = flagForCityFocus;
	}

	
	
	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		accountId = null;
		accountCode = null;
		iPassword = null;
		checkIPassword = null;
		groupId = null;
		groupName = null;
		statusName = null;
		rate = null;
		threshold = null;
		mPassword = null;
		mobileNumber = null;
		parentAccount = null;
		simNumber = null;
		accountName = null;
		accountAddress = null;
		regionId = null;
		email = null;
		category = null;
		circleId = null;
		status = null;
		openingBalance = null;
		openingDt = null;
		createdDt = null;
		updateDt = null;
		createdBy = null;
		updatedBy = null;
		methodName = null;
		circleName = null;
		regionName = null;
		searchFieldName = null;
		searchFieldValue = "";
		displayDetails = null;
		availableBalance = null;
		operatingBalance = null;
		checkMpassword = null;
		userType=null;
		levelName=null;
		accountLevel=null;
		this.submitStatus="0";
		this.editStatus=null;
		this.accountCityId=null;
		this.accountStateId=null;
		this.accountPinNumber=null;
		this.isbillable=null ;
		this.billableCode=null ;
		this.editStatus=null;
		this.showBillableCode=null;
		flagForCityFocus=false;
		

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


	public String getLoginAttempted() {
		return loginAttempted;
	}

	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
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