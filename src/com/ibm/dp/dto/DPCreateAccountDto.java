package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.virtualization.recharge.dto.UserSessionContext;

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
	
	private String accountLevelName;

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

	private double openingBalance=0.0;

	private double operatingBalance=0.0;

	private double availableBalance=0.0;

	private String openingDt;

	private Date createdDt;

	private Date updateDt;

	private long createdBy;

	private long updatedBy;
	
	private String levelName;

	private String accountLevel;
	
	private String rowNum;
	
	private String createdByName;
	
	private int accountLevelId;
	
	private int accountCityId;
	
	private int accountStateId;
	//Added by ARTI for warehaouse list box BFR -11 release2 
	private String accountWarehouseCode;
	private String accountWarehouseName;
	
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
	
	private String lapuMobileNo1;
	
	private String accountAddress2;
	
	private String FTAMobileNo;
    
    private String FTAMobileNo1;
    
    private int accountCircleId;
    
    private String accountCircleName;
    
    private ArrayList AccountCircleList;
    
    private int accountZoneId;
    
    private String accountZoneName;
    
    private ArrayList zoneList=new ArrayList<String>();
    
    private boolean flagForZoneFocus ;
    
    private String contactName;
    
    private String ZBMName;
    
    private int ZBMId;
    
    private ArrayList ZBMList;
    
    private String ZSMName;
    
    private int ZSMId;
    
    private ArrayList ZSMList;
    
    private String showNumbers;
    
    private String code;
    
    private int accessLevelId;
    
    private int heirarchyId;
    
    private String retailerType;
    private String retailerTypeName;
    private String outletType;
    private String outletName;
    private String altChannelType;
    private String altChannelName;
    private String channelType;
    private String channelName;
    
    // distributor type
    
    private ArrayList tradeList;
    private int tradeId;
    private String tradeName;
    private ArrayList tradeCategoryList;
    private int tradeCategoryId;
    private String tradeCategoryName;
    private boolean flagForCategoryFocus;
    private int financeId;
    private String financeName;
    private ArrayList financeList;
    private int commerceId;
    private String commerceName;
    private ArrayList commerceList;
    private int salesId;
    private String salesName;
    private ArrayList salesList;
    private int beatId;
    private String beatName;
    private ArrayList beatList;
    private String reportingEmail;
    private String cstNo;
    private String cstDt;
    private String panNo;
    private String serviceTax;
    private String tinNo;
    private String tinDt;
    private String octroiCharge;
    
    
	// attributes for Aggregate View
	
	private String distId;
    private String fseId;
    private String retailerId;
    private String distName;
    private String distLapuNo;
    private String fseName;
    private String fseLapuNo;
    private String retailerName;
    private String retailerLapuNo;
    private String showAttributes;
    private int hiddenZoneId=0;
    private int hiddenCircleId=0;
    private int hiddenCityId=0;
    
    private String parentLoginName;
	private String parentAccountName;
	private String parentAccountStatus;
	private String billableLoginName;
	private String billableAccountName;
	private String oldParentAccountId;
//	 Added for Oracle SCM Integration
    private String distLocator; 
    private String isDisable; 
    //private int swLocatorId;
    private String circleIdList;
  private String srNumber;
    
    private String approval1;
    
    private String approval2;
    private String remarks;
    private String unlockStatus;
    private String distTranctionId;
    private String addedTransactionId;
    
    private UserSessionContext sessionContaxt;
    
    private int isCircleChanged;
//  Added by Sugandha Transaction Type for TSM
    private String transactionId;
    private String TransactionType;
// Added by Sugandha Transaction Type for Retailer  
    private int retailerTransId;
    private String retailerTransType;
    private String demoId1;
	private String demoId2;
	private String demoId3;
	private String demoId4;
	
    /* Added By Parnika */
	
	private String depositeTypeTSMLoginName;
	private String depositeTypeTSMAccountName;
	private String salesTypeTSMAccountName;
	private String salesTypeTSMLoginName;
	private String distTypeId="";
	private String hiddenTranctionIds;
	private long depositeTypeTSMId;
	private long salesTypeTSMId;
	private String oldSalesTypeTSMId;
	private String oldDepositeTypeTSMId;
	private String depositeTypeTSM;
	private String salesTypeTSM;
	private String existingTransacId;
	private int smsAlertType;
	
    /* End of changes by Parnika */
	
	private String fseMobileNumber;
	private String retailerMobileNumber;
	
	private String mobile1;
	private String mobile2;
	private String mobile3;
	private String mobile4;
	//Changes for distributor type ( riju )
	private String disttype;
	//Changes for distributor type ( riju )
	
	public int getSmsAlertType() {
		return smsAlertType;
	}

	public void setSmsAlertType(int smsAlertType) {
		this.smsAlertType = smsAlertType;
	}
	//Added By Sugandha For User Termination
	private String terminationId;
	private String terminationType;
	//added by sugandha ends here
	public String getDemoId1() {
		return demoId1;
	}

	public String getExistingTransacId() {
		return existingTransacId;
	}

	public void setExistingTransacId(String existingTransacId) {
		this.existingTransacId = existingTransacId;
	}

	public String getDepositeTypeTSM() {
		return depositeTypeTSM;
	}

	public void setDepositeTypeTSM(String depositeTypeTSM) {
		this.depositeTypeTSM = depositeTypeTSM;
	}

	public String getSalesTypeTSM() {
		return salesTypeTSM;
	}

	public void setSalesTypeTSM(String salesTypeTSM) {
		this.salesTypeTSM = salesTypeTSM;
	}

	public String getOldSalesTypeTSMId() {
		return oldSalesTypeTSMId;
	}

	public void setOldSalesTypeTSMId(String oldSalesTypeTSMId) {
		this.oldSalesTypeTSMId = oldSalesTypeTSMId;
	}

	public String getOldDepositeTypeTSMId() {
		return oldDepositeTypeTSMId;
	}

	public void setOldDepositeTypeTSMId(String oldDepositeTypeTSMId) {
		this.oldDepositeTypeTSMId = oldDepositeTypeTSMId;
	}

	public String getDepositeTypeTSMLoginName() {
		return depositeTypeTSMLoginName;
	}

	public void setDepositeTypeTSMLoginName(String depositeTypeTSMLoginName) {
		this.depositeTypeTSMLoginName = depositeTypeTSMLoginName;
	}

	public String getDepositeTypeTSMAccountName() {
		return depositeTypeTSMAccountName;
	}

	public void setDepositeTypeTSMAccountName(String depositeTypeTSMAccountName) {
		this.depositeTypeTSMAccountName = depositeTypeTSMAccountName;
	}

	public String getSalesTypeTSMAccountName() {
		return salesTypeTSMAccountName;
	}

	public void setSalesTypeTSMAccountName(String salesTypeTSMAccountName) {
		this.salesTypeTSMAccountName = salesTypeTSMAccountName;
	}

	public String getSalesTypeTSMLoginName() {
		return salesTypeTSMLoginName;
	}

	public void setSalesTypeTSMLoginName(String salesTypeTSMLoginName) {
		this.salesTypeTSMLoginName = salesTypeTSMLoginName;
	}

	public String getDistTypeId() {
		return distTypeId;
	}

	public void setDistTypeId(String distTypeId) {
		this.distTypeId = distTypeId;
	}

	public String getHiddenTranctionIds() {
		return hiddenTranctionIds;
	}

	public void setHiddenTranctionIds(String hiddenTranctionIds) {
		this.hiddenTranctionIds = hiddenTranctionIds;
	}

	public long getDepositeTypeTSMId() {
		return depositeTypeTSMId;
	}

	public void setDepositeTypeTSMId(long depositeTypeTSMId) {
		this.depositeTypeTSMId = depositeTypeTSMId;
	}

	public long getSalesTypeTSMId() {
		return salesTypeTSMId;
	}

	public void setSalesTypeTSMId(long salesTypeTSMId) {
		this.salesTypeTSMId = salesTypeTSMId;
	}

	public void setDemoId1(String demoId1) {
		this.demoId1 = demoId1;
	}

	public String getDemoId2() {
		return demoId2;
	}

	public void setDemoId2(String demoId2) {
		this.demoId2 = demoId2;
	}

	public String getDemoId3() {
		return demoId3;
	}

	public void setDemoId3(String demoId3) {
		this.demoId3 = demoId3;
	}

	public String getDemoId4() {
		return demoId4;
	}

	public void setDemoId4(String demoId4) {
		this.demoId4 = demoId4;
	}

	public int getRetailerTransId() {
		return retailerTransId;
	}

	public void setRetailerTransId(int retailerTransId) {
		this.retailerTransId = retailerTransId;
	}

	public String getRetailerTransType() {
		return retailerTransType;
	}

	public void setRetailerTransType(String retailerTransType) {
		this.retailerTransType = retailerTransType;
	}

	public UserSessionContext getSessionContaxt() {
		return sessionContaxt;
	}

	public void setSessionContaxt(UserSessionContext sessionContaxt) {
		this.sessionContaxt = sessionContaxt;
	}

	public String getShowAttributes() {
		return showAttributes;
	}

	public void setShowAttributes(String showAttributes) {
		this.showAttributes = showAttributes;
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

	
	public String getCircleIdList() {
		return circleIdList;
	}

	public void setCircleIdList(String circleIdList) {
		this.circleIdList = circleIdList;
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

	public int getAccountLevelId() {
		return accountLevelId;
	}

	public void setAccountLevelId(int accountLevelId) {
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

	
	//Added by ARTI for warehaouse list box BFR -11 release2
	
	public String getAccountWarehouseName() {
		return accountWarehouseName;
	}

	public void setAccountWarehouseName(String accountWarehouseName) {
		this.accountWarehouseName = accountWarehouseName;
	}
	
	
	public String getAccountWarehouseCode() {
		return accountWarehouseCode;
	}

	public void setAccountWarehouseCode(String accountWarehouseCode) {
		this.accountWarehouseCode = accountWarehouseCode;
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

	public int getAccountZoneId() {
		return accountZoneId;
	}

	public void setAccountZoneId(int accountZoneId) {
		this.accountZoneId = accountZoneId;
	}

	public boolean isFlagForZoneFocus() {
		return flagForZoneFocus;
	}

	public void setFlagForZoneFocus(boolean flagForZoneFocus) {
		this.flagForZoneFocus = flagForZoneFocus;
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

	public String getLapuMobileNo1() {
		return lapuMobileNo1;
	}

	public void setLapuMobileNo1(String lapuMobileNo1) {
		this.lapuMobileNo1 = lapuMobileNo1;
	}

	public ArrayList getZoneList() {
		return zoneList;
	}

	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
	}

	public String getAccountZoneName() {
		return accountZoneName;
	}

	public void setAccountZoneName(String accountZoneName) {
		this.accountZoneName = accountZoneName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getZBMId() {
		return ZBMId;
	}

	public void setZBMId(int id) {
		ZBMId = id;
	}

	public ArrayList getZBMList() {
		return ZBMList;
	}

	public void setZBMList(ArrayList list) {
		ZBMList = list;
	}

	public String getZBMName() {
		return ZBMName;
	}

	public void setZBMName(String name) {
		ZBMName = name;
	}

	public int getZSMId() {
		return ZSMId;
	}

	public void setZSMId(int id) {
		ZSMId = id;
	}

	public ArrayList getZSMList() {
		return ZSMList;
	}

	public void setZSMList(ArrayList list) {
		ZSMList = list;
	}

	public String getZSMName() {
		return ZSMName;
	}

	public void setZSMName(String name) {
		ZSMName = name;
	}

	public String getShowNumbers() {
		return showNumbers;
	}

	public void setShowNumbers(String showNumbers) {
		this.showNumbers = showNumbers;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTradeCategoryId() {
		return tradeCategoryId;
	}

	public void setTradeCategoryId(int tradeCategoryId) {
		this.tradeCategoryId = tradeCategoryId;
	}

	public ArrayList getTradeCategoryList() {
		return tradeCategoryList;
	}

	public void setTradeCategoryList(ArrayList tradeCategoryList) {
		this.tradeCategoryList = tradeCategoryList;
	}

	public String getTradeCategoryName() {
		return tradeCategoryName;
	}

	public void setTradeCategoryName(String tradeCategoryName) {
		this.tradeCategoryName = tradeCategoryName;
	}

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public ArrayList getTradeList() {
		return tradeList;
	}

	public void setTradeList(ArrayList tradeList) {
		this.tradeList = tradeList;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public boolean isFlagForCategoryFocus() {
		return flagForCategoryFocus;
	}

	public void setFlagForCategoryFocus(boolean flagForCategoryFocus) {
		this.flagForCategoryFocus = flagForCategoryFocus;
	}

	public int getAccountCircleId() {
		return accountCircleId;
	}

	public void setAccountCircleId(int accountCircleId) {
		this.accountCircleId = accountCircleId;
	}

	public ArrayList getAccountCircleList() {
		return AccountCircleList;
	}

	public void setAccountCircleList(ArrayList accountCircleList) {
		AccountCircleList = accountCircleList;
	}

	public String getAccountCircleName() {
		return accountCircleName;
	}

	public void setAccountCircleName(String accountCircleName) {
		this.accountCircleName = accountCircleName;
	}

	public int getAccessLevelId() {
		return accessLevelId;
	}

	public void setAccessLevelId(int accessLevelId) {
		this.accessLevelId = accessLevelId;
	}

	public int getHeirarchyId() {
		return heirarchyId;
	}

	public void setHeirarchyId(int heirarchyId) {
		this.heirarchyId = heirarchyId;
	}

	public String getAccountLevelName() {
		return accountLevelName;
	}

	public void setAccountLevelName(String accountLevelName) {
		this.accountLevelName = accountLevelName;
	}

	public int getBeatId() {
		return beatId;
	}

	public void setBeatId(int beatId) {
		this.beatId = beatId;
	}

	public ArrayList getBeatList() {
		return beatList;
	}

	public void setBeatList(ArrayList beatList) {
		this.beatList = beatList;
	}

	public String getBeatName() {
		return beatName;
	}

	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}

	public int getCommerceId() {
		return commerceId;
	}

	public void setCommerceId(int commerceId) {
		this.commerceId = commerceId;
	}

	public ArrayList getCommerceList() {
		return commerceList;
	}

	public void setCommerceList(ArrayList commerceList) {
		this.commerceList = commerceList;
	}

	public String getCommerceName() {
		return commerceName;
	}

	public void setCommerceName(String commerceName) {
		this.commerceName = commerceName;
	}

	public int getFinanceId() {
		return financeId;
	}

	public void setFinanceId(int financeId) {
		this.financeId = financeId;
	}

	public ArrayList getFinanceList() {
		return financeList;
	}

	public void setFinanceList(ArrayList financeList) {
		this.financeList = financeList;
	}

	public String getFinanceName() {
		return financeName;
	}

	public void setFinanceName(String financeName) {
		this.financeName = financeName;
	}

	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public ArrayList getSalesList() {
		return salesList;
	}

	public void setSalesList(ArrayList salesList) {
		this.salesList = salesList;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getOutletType() {
		return outletType;
	}

	public void setOutletType(String outletType) {
		this.outletType = outletType;
	}

	public String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(String retailerType) {
		this.retailerType = retailerType;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getRetailerTypeName() {
		return retailerTypeName;
	}

	public void setRetailerTypeName(String retailerTypeName) {
		this.retailerTypeName = retailerTypeName;
	}

	public int getHiddenCircleId() {
		return hiddenCircleId;
	}

	public void setHiddenCircleId(int hiddenCircleId) {
		this.hiddenCircleId = hiddenCircleId;
	}

	public int getHiddenCityId() {
		return hiddenCityId;
	}

	public void setHiddenCityId(int hiddenCityId) {
		this.hiddenCityId = hiddenCityId;
	}

	public int getHiddenZoneId() {
		return hiddenZoneId;
	}

	public void setHiddenZoneId(int hiddenZoneId) {
		this.hiddenZoneId = hiddenZoneId;
	}

	public String getAltChannelName() {
		return altChannelName;
	}

	public void setAltChannelName(String altChannelName) {
		this.altChannelName = altChannelName;
	}

	public String getAltChannelType() {
		return altChannelType;
	}

	public void setAltChannelType(String altChannelType) {
		this.altChannelType = altChannelType;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getOctroiCharge() {
		return octroiCharge;
	}

	public void setOctroiCharge(String octroiCharge) {
		this.octroiCharge = octroiCharge;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getReportingEmail() {
		return reportingEmail;
	}

	public void setReportingEmail(String reportingEmail) {
		this.reportingEmail = reportingEmail;
	}

	public String getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}

	public String getParentAccountName() {
		return parentAccountName;
	}

	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}

	public String getParentLoginName() {
		return parentLoginName;
	}

	public void setParentLoginName(String parentLoginName) {
		this.parentLoginName = parentLoginName;
	}

	public String getBillableLoginName() {
		return billableLoginName;
	}

	public void setBillableLoginName(String billableLoginName) {
		this.billableLoginName = billableLoginName;
	}

	public String getBillableAccountName() {
		return billableAccountName;
	}

	public void setBillableAccountName(String billableAccountName) {
		this.billableAccountName = billableAccountName;
	}

	public String getCstDt() {
		return cstDt;
	}

	public void setCstDt(String cstDt) {
		this.cstDt = cstDt;
	}

	public String getTinDt() {
		return tinDt;
	}

	public void setTinDt(String tinDt) {
		this.tinDt = tinDt;
	}

	public String getDistLocator() {
		return distLocator;
	}

	public void setDistLocator(String distLocator) {
		this.distLocator = distLocator;
	}
//by saumya
	private String repairFacility;

	public String getRepairFacility() {
		return repairFacility;
	}

	public void setRepairFacility(String repairFacility) {
		this.repairFacility = repairFacility;
	}

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	public String getApproval1() {
		return approval1;
	}

	public void setApproval1(String approval1) {
		this.approval1 = approval1;
	}

	public String getApproval2() {
		return approval2;
	}

	public void setApproval2(String approval2) {
		this.approval2 = approval2;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public int getIsCircleChanged() {
		return isCircleChanged;
	}

	public void setIsCircleChanged(int isCircleChanged) {
		this.isCircleChanged = isCircleChanged;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUnlockStatus() {
		return unlockStatus;
	}

	public void setUnlockStatus(String unlockStatus) {
		this.unlockStatus = unlockStatus;
	}

	public String getParentAccountStatus() {
		return parentAccountStatus;
	}

	public void setParentAccountStatus(String parentAccountStatus) {
		this.parentAccountStatus = parentAccountStatus;
	}
	public String getOldParentAccountId() {
		return oldParentAccountId;
	}

	public void setOldParentAccountId(String oldParentAccountId) {
		this.oldParentAccountId = oldParentAccountId;
	}

	public String getDistTranctionId() {
		return distTranctionId;
	}

	public void setDistTranctionId(String distTranctionId) {
		this.distTranctionId = distTranctionId;
	}

	public String getAddedTransactionId() {
		return addedTransactionId;
	}

	public void setAddedTransactionId(String addedTransactionId) {
		this.addedTransactionId = addedTransactionId;
	}

	

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getTerminationId() {
		return terminationId;
	}

	public void setTerminationId(String terminationId) {
		this.terminationId = terminationId;
	}

	public String getTerminationType() {
		return terminationType;
	}

	public void setTerminationType(String terminationType) {
		this.terminationType = terminationType;
	}

	public String getFseMobileNumber() {
		return fseMobileNumber;
	}

	public void setFseMobileNumber(String fseMobileNumber) {
		this.fseMobileNumber = fseMobileNumber;
	}

	public String getRetailerMobileNumber() {
		return retailerMobileNumber;
	}

	public void setRetailerMobileNumber(String retailerMobileNumber) {
		this.retailerMobileNumber = retailerMobileNumber;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getMobile3() {
		return mobile3;
	}

	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}

	public String getMobile4() {
		return mobile4;
	}

	public void setMobile4(String mobile4) {
		this.mobile4 = mobile4;
	}

	public String getDisttype() {
		return disttype;
	}

	public void setDisttype(String disttype) {
		this.disttype = disttype;
	}

		
}
