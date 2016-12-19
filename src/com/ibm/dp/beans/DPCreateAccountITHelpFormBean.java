package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.RoleDto;
import com.ibm.dpmisreports.common.SelectionCollection;

public class DPCreateAccountITHelpFormBean  extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098305345870553453L;

	private String accountId;
	
	private String accountLevelNameHidden;
	
	private String accountCode;		// user name/ login id

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
	
	
	
	private String userCircleId;

	private String status;

	private String openingBalance;

	private String openingDt;

	private String createdDt;

	private String updateDt;

	private String createdBy;

	private String updatedBy;

	private String methodName;

	private String circleName;
	
	
	
	private String userRole;

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
	//added by sugandha to show selected transaction id for TSM 
	private int showTransactionId;
	
	private String showZoneCity;
	
	private String showState;
	
    private int accountStateId;
    
    private String errorFlag="";
    
    private String circleIdList;
    private String hiddenCircleIdList;
    private String distTranctionId;
    private String hiddenTranctionIds;
    private List<SelectionCollection> roleList =null;
    private ArrayList<SelectionCollection> distTranctionList =null;
    // added by ARTI for warehaouse list box BFR -11 release2
    private String accountWarehouseCode;
	private String accountWarehouseName;
	private ArrayList wareHouseList;
//	 Ended by ARTI for warehaouse list box BFR -11 release2
	 //added by sugandha for TSM  transaction type Sales or Deposite 
	private String transactionId;
	 private String hiddenTranctionId;
    private String accountStateName;
    
	private String startDt;
	
	private String endDt;
	
	private String submitStatus="0";
	
	private boolean flagForCircleDisplay ;
	
	private String accountLevelStatus ;
	
	private int accountLevelId ;
	
	private String editStatus;

	private String accountCityId ;
	
	private String accountCityName;
	
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
	
	private ArrayList cityList;
	
	private String loginAttempted;
	
	private boolean locked;
	
	private String levelName;
	
    private String page;
    
    private String lapuMobileNo;
    
    private String lapuMobileNo1;
    
    private String accountAddress2;
    
    private ArrayList stateList;
    
    private String FTAMobileNo;
    
    private String FTAMobileNo1;
    
    private int accountCircleId;
    
    private String accountCircleName;
    
    private ArrayList accountCircleList;
    
    private int accountZoneId;
    
    private ArrayList zoneList=new ArrayList();
    
    private boolean flagForZoneFocus ;
    
    private String accountZoneName;
    
    private String contactName;
    
    private String ZBMName;
    
    private int ZBMId;
    
    private ArrayList ZBMList;
    
    private String ZSMName;
    
    private int ZSMId;
    
    private ArrayList ZSMList;

    private String showNumbers;

    private int newCode;
    
    private int accessLevelId;
    
    private int heirarchyId;
    
    private String srNumber;
    
    private String approval1;
    
    private String approval2;
    
    private String accountLevelFlag;
    private String roleName;
    private String accountLevelName;
    private String retailerType;
    private String retailerTypeName;
    private String outletType;
    private String outletName;
    private ArrayList retailerCatList;
    private ArrayList outletList;
    private String altChannelType;
    private String altChannelName;
    private String channelType;
    private String channelName;
    private ArrayList altChannelTypeList;
    private ArrayList channelTypeList;
    private String reportingEmail;
    private String cstNo;
    private String cstDt;
    private String panNo;
    
    private String serviceTax;
    private String tinNo;
    private String tinDt;
    private String octroiCharge;
    //saumya 
    private String repairFacility;
    //end by saumya
    
    //private String accountWareHouseName;
    // distributor type
    private String code;	// oracle id/ employee id/ account_code in database
    private ArrayList tradeList;
    private int tradeId;
    private String tradeName;
    private ArrayList tradeCategoryList;
    private int tradeCategoryId;
    private String tradeCategoryName;
    private boolean flagForCategoryFocus;
    private String tradeIdInBack;
    private String tradeCategoryIdInBack;
    private String isBillableInBack;
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
    private String showAttributes;
    private ArrayList groupList;
    private int hiddenZoneId=0;
    private int hiddenCircleId=0;
    private int hiddenCityId=0;
    private int hiddenOutletType=0;
    private String parentLoginName;
	private String parentAccountName;
	private String billableLoginName;
	private String billableAccountName;
	private String hiddenStateId;
	private String hiddenWarehouseId;
	private String hiddenAccountLevel;
	private String remarks;
	private String isERR;
	
	//saumya
	private String repFacility;
	//end saumya
	
	// Added for Oracle SCM Integration
    private String distLocator; 
    private int swLocatorId;
    //private ArrayList<SWLocatorFormBean> swConcatenatedLocatorList =new ArrayList<SWLocatorFormBean>();
    
    //Added by sugandha for TSM transaction Type -sales or deposite 
	private ArrayList transactionTypeList;
	//Added by sugandha for deposite type TSM
	private String depositeTypeTSM;
	//Added by sugandha for sales type TSM
	private String salesTypeTSM;
	
	/* Added by Parnika */
	
	private String depositeTypeTSMLoginName;
	private String depositeTypeTSMAccountName;
	private String salesTypeTSMAccountName;
	private String salesTypeTSMLoginName;
	private String distTypeId="";
	private long depositeTypeTSMId;
	private long salesTypeTSMId;
	
	/*End of changes by Parnika */
	//Changes for distributor type ( riju )
	
	private String disttype;
	  
	 	
	public String getDisttype() {
		return disttype;
	}


	public void setDisttype(String disttype) {
		this.disttype = disttype;
	}

//Changes for distributor type ( riju )
	// Added by Sugandha for user termination BFR-10 
	 private ArrayList<SelectionCollection> terminationList =new ArrayList<SelectionCollection>();
	private int terminationId;
	//end of changes added by sugandha BFR-10 DP Phase -3
	public String getDepositeTypeTSM() {
		return depositeTypeTSM;
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


	public String getDistTypeId() {
		return distTypeId;
	}


	public void setDistTypeId(String distTypeId) {
		this.distTypeId = distTypeId;
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

	public String getRepFacility() {
		return repFacility;
	}

	public void setRepFacility(String repFacility) {
		this.repFacility = repFacility;
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

	public void setDepositeTypeTSM(String depositeTypeTSM) {
		this.depositeTypeTSM = depositeTypeTSM;
	}

	public String getBillableAccountName() {
		return billableAccountName;
	}

	public void setBillableAccountName(String billableAccountName) {
		this.billableAccountName = billableAccountName;
	}

	public String getBillableLoginName() {
		return billableLoginName;
	}

	public void setBillableLoginName(String billableLoginName) {
		this.billableLoginName = billableLoginName;
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

	public int getHiddenOutletType() {
		return hiddenOutletType;
	}

	public void setHiddenOutletType(int hiddenOutletType) {
		this.hiddenOutletType = hiddenOutletType;
	}

	public ArrayList getStateList() {
		return stateList;
	}

	public void setStateList(ArrayList stateList) {
		this.stateList = stateList;
	}

	public String getAccountAddress2() {
		return accountAddress2;
	}

	public void setAccountAddress2(String accountAddress2) {
		this.accountAddress2 = accountAddress2;
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

	public int getAccountLevelId() {
		return accountLevelId;
	}

	public void setAccountLevelId(int accountLevelId) {
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

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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

	public String getLapuMobileNo() {
		return lapuMobileNo;
	}

	public void setLapuMobileNo(String lapuMobileNo) {
		this.lapuMobileNo = lapuMobileNo;
	}

	public void reset() {
		
		 accountId = "";

		 accountCode = "";
		 contactName = "";
		 code = "";
		  iPassword="";

		  checkIPassword="";

		  groupId="";

		  groupName="";

		  statusName="";

		  rate="";

		  threshold="";

		  mPassword="";

		  mobileNumber="";

		  parentAccount="";

		  simNumber="";

		  accountName="";

		  accountAddress="";

		  regionId="";

		  email="";

		  category="";

		  circleId="";

		  status="";

		  openingBalance="";

		  openingDt="";

		  createdDt="";

		  updateDt="";

		  createdBy="";

		  updatedBy="";

		  methodName="";

		  circleName="";

		  regionName="";

		  searchFieldName="";

		  searchFieldValue="";

		  availableBalance="";

		  operatingBalance="";

		  accountLevel="";

		  accountStatus="";
		
		  checkMpassword="";
		
		  userType="";
	 
		  showCircle=""; 
		
		  startDt="";
		
		  endDt="";
		
		  submitStatus="0";
		
		  accountLevelStatus ="";
		
		  editStatus="";
	
		  accountCityId ="";
		
		  accountPinNumber="" ;
		
		  isbillable ="";
		
		  billableCode =""; 
		
		  billableCodeId =""; 
		
		  showBillableCode =""; 
		
		  listStatus ="";
		
		  unlockStatus="";

		  loginAttempted = "";
		
		  levelName ="";
		
	      page="";
	    
	      lapuMobileNo="";
	      lapuMobileNo1="";
	      FTAMobileNo="";
	      FTAMobileNo1="";
	    
	      accountAddress2="";
	      accountCode = "";
	      openingDt = "";
	      heirarchyId=0;
	      cstNo="";
	      cstDt="";
	      panNo="";
	      serviceTax="";
	      tinNo="";
	      tinDt="";
	      octroiCharge="0";
	      //saumya
	      repairFacility="";
	      //end saumya
	      srNumber="";
	      approval1="";
	      approval2="";
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

	public String getDistId() {
		return distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public String getFseId() {
		return fseId;
	}

	public void setFseId(String fseId) {
		this.fseId = fseId;
	}

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public ArrayList getAggList() {
		return aggList;
	}

	public void setAggList(ArrayList aggList) {
		this.aggList = aggList;
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

	public int getAccountZoneId() {
		return accountZoneId;
	}

	public void setAccountZoneId(int accountZoneId) {
		this.accountZoneId = accountZoneId;
	}

	public ArrayList getZoneList() {
		return zoneList;
	}

	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
	}

	public boolean isFlagForZoneFocus() {
		return flagForZoneFocus;
	}

	public void setFlagForZoneFocus(boolean flagForZoneFocus) {
		this.flagForZoneFocus = flagForZoneFocus;
	}

	public String getAccountZoneName() {
		return accountZoneName;
	}

	public void setAccountZoneName(String accountZoneName) {
		this.accountZoneName = accountZoneName;
	}

	public ArrayList getCityList() {
		return cityList;
	}

	public void setCityList(ArrayList cityList) {
		this.cityList = cityList;
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

	public String getUserCircleId() {
		return userCircleId;
	}

	public void setUserCircleId(String userCircleId) {
		this.userCircleId = userCircleId;
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
		return accountCircleList;
	}

	public void setAccountCircleList(ArrayList accountCircleList) {
		this.accountCircleList = accountCircleList;
	}

	public String getAccountCircleName() {
		return accountCircleName;
	}

	public void setAccountCircleName(String accountCircleName) {
		this.accountCircleName = accountCircleName;
	}

	public String getShowAttributes() {
		return showAttributes;
	}

	public void setShowAttributes(String showAttributes) {
		this.showAttributes = showAttributes;
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

	public String getAccountCityName() {
		return accountCityName;
	}

	public void setAccountCityName(String accountCityName) {
		this.accountCityName = accountCityName;
	}

	public String getAccountLevelFlag() {
		return accountLevelFlag;
	}

	public void setAccountLevelFlag(String accountLevelFlag) {
		this.accountLevelFlag = accountLevelFlag;
	}

	public String getAccountLevelName() {
		return accountLevelName;
	}

	public void setAccountLevelName(String accountLevelName) {
		this.accountLevelName = accountLevelName;
	}

	public ArrayList getGroupList() {
		return groupList;
	}

	public void setGroupList(ArrayList groupList) {
		this.groupList = groupList;
	}

	public String getTradeIdInBack() {
		return tradeIdInBack;
	}

	public void setTradeIdInBack(String tradeIdInBack) {
		this.tradeIdInBack = tradeIdInBack;
	}

	public String getIsBillableInBack() {
		return isBillableInBack;
	}

	public void setIsBillableInBack(String isBillableInBack) {
		this.isBillableInBack = isBillableInBack;
	}

	public String getTradeCategoryIdInBack() {
		return tradeCategoryIdInBack;
	}

	public void setTradeCategoryIdInBack(String tradeCategoryIdInBack) {
		this.tradeCategoryIdInBack = tradeCategoryIdInBack;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAccountLevelNameHidden() {
		return accountLevelNameHidden;
	}

	public void setAccountLevelNameHidden(String accountLevelNameHidden) {
		this.accountLevelNameHidden = accountLevelNameHidden;
	}

	public int getNewCode() {
		return newCode;
	}

	public void setNewCode(int newCode) {
		this.newCode = newCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getShowZoneCity() {
		return showZoneCity;
	}

	public void setShowZoneCity(String showZoneCity) {
		this.showZoneCity = showZoneCity;
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

	public ArrayList getOutletList() {
		return outletList;
	}

	public void setOutletList(ArrayList outletList) {
		this.outletList = outletList;
	}

	public ArrayList getRetailerCatList() {
		return retailerCatList;
	}

	public void setRetailerCatList(ArrayList retailerCatList) {
		this.retailerCatList = retailerCatList;
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

	public String getAltChannelType() {
		return altChannelType;
	}

	public void setAltChannelType(String altChannelType) {
		this.altChannelType = altChannelType;
	}

	public ArrayList getAltChannelTypeList() {
		return altChannelTypeList;
	}

	public void setAltChannelTypeList(ArrayList altChannelTypeList) {
		this.altChannelTypeList = altChannelTypeList;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public ArrayList getChannelTypeList() {
		return channelTypeList;
	}

	public void setChannelTypeList(ArrayList channelTypeList) {
		this.channelTypeList = channelTypeList;
	}

	public String getAltChannelName() {
		return altChannelName;
	}

	public void setAltChannelName(String altChannelName) {
		this.altChannelName = altChannelName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public int getSwLocatorId() {
		return swLocatorId;
	}

	public void setSwLocatorId(int swLocatorId) {
		this.swLocatorId = swLocatorId;
	}
//saumya

	public String getRepairFacility() {
		return repairFacility;
	}

	public void setRepairFacility(String repairFacility) {
		this.repairFacility = repairFacility;
	}

	public String getAccountStateName() {
		return accountStateName;
	}

	public void setAccountStateName(String accountStateName) {
		this.accountStateName = accountStateName;
	}

	public String getShowState() {
		return showState;
	}

	public void setShowState(String showState) {
		this.showState = showState;
	}

	public int getAccountStateId() {
		return accountStateId;
	}

	public void setAccountStateId(int accountStateId) {
		this.accountStateId = accountStateId;
	}
	
	// Added by ARTI for warehaouse list box BFR -11 release2
	
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
	
	public ArrayList getWareHouseList() {
		return wareHouseList;
	}

	public void setWareHouseList(ArrayList wareHouseList) {
		this.wareHouseList = wareHouseList;
	}
//	 Ended by ARTI for warehaouse list box BFR -11 release2

	public String getCircleIdList() {
		return circleIdList;
	}

	public void setCircleIdList(String circleIdList) {
		this.circleIdList = circleIdList;
	}

	public String getHiddenCircleIdList() {
		return hiddenCircleIdList;
	}

	public void setHiddenCircleIdList(String hiddenCircleIdList) {
		this.hiddenCircleIdList = hiddenCircleIdList;
	}


	public List<SelectionCollection> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SelectionCollection> roleList) {
		this.roleList = roleList;
	}

	

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}

	public String getHiddenStateId() {
		return hiddenStateId;
	}

	public void setHiddenStateId(String hiddenStateId) {
		this.hiddenStateId = hiddenStateId;
	}

	public String getHiddenWarehouseId() {
		return hiddenWarehouseId;
	}

	public void setHiddenWarehouseId(String hiddenWarehouseId) {
		this.hiddenWarehouseId = hiddenWarehouseId;
	}

	public String getHiddenAccountLevel() {
		return hiddenAccountLevel;
	}

	public void setHiddenAccountLevel(String hiddenAccountLevel) {
		this.hiddenAccountLevel = hiddenAccountLevel;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsERR() {
		return isERR;
	}

	public void setIsERR(String isERR) {
		this.isERR = isERR;
	}

	public String getDistTranctionId() {
		return distTranctionId;
	}

	public void setDistTranctionId(String distTranctionId) {
		this.distTranctionId = distTranctionId;
	}

	public String getHiddenTranctionIds() {
		return hiddenTranctionIds;
	}

	public void setHiddenTranctionIds(String hiddenTranctionIds) {
		this.hiddenTranctionIds = hiddenTranctionIds;
	}

	public ArrayList getTransactionTypeList() {
		return transactionTypeList;
	}

	public void setTransactionTypeList(ArrayList transactionTypeList) {
		this.transactionTypeList = transactionTypeList;
	}

	

	public int getShowTransactionId() {
		return showTransactionId;
	}

	public void setShowTransactionId(int showTransactionId) {
		this.showTransactionId = showTransactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getHiddenTranctionId() {
		return hiddenTranctionId;
	}

	public void setHiddenTranctionId(String hiddenTranctionId) {
		this.hiddenTranctionId = hiddenTranctionId;
	}

	public String getSalesTypeTSM() {
		return salesTypeTSM;
	}

	public void setSalesTypeTSM(String salesTypeTSM) {
		this.salesTypeTSM = salesTypeTSM;
	}
	public int getTerminationId()
	{
		return terminationId;
	}


	public void setTerminationId(int terminationId) {
		this.terminationId = terminationId;
	}


	public ArrayList<SelectionCollection> getDistTranctionList() {
		return distTranctionList;
	}


	public void setDistTranctionList(
			ArrayList<SelectionCollection> distTranctionList) {
		this.distTranctionList = distTranctionList;
	}


	public ArrayList<SelectionCollection> getTerminationList() {
		return terminationList;
	}


	public void setTerminationList(ArrayList<SelectionCollection> terminationList) {
		this.terminationList = terminationList;
	}

}
