package com.ibm.dp.dto;

public class AccountManagementActivityReportDto {
	
	private int accountLevel=0;
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	private String fromDate;
	private String toDate;
	private String dateOption;
	private String searchOption;
	private String searchCriteria;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenPoStatusSelec;
	private String parentAccountType;
	private String distName;
	private String tsmName;
	private String distLoginId;
	private String accountType;
	private String accountName;
	private String parentLoginId;
	private String loginId;
	private String circle;
	private String city;
	private String emailId;
	private String address;
	private String pin;
	private String zone;
	private String botreeCode;
	private String oracleLocatorCode;
	private String status;
	private String createdDate;
	private String createdBy;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String lastLoginDate;
	private long accountId;
	private String parentAccountName;
	private String whName;
	private String lapuNo; 
	private String mobileNo;
	private String passChangeDT;
	// for upgrade report
	private String action;
	private String hisDate;
	private String srNo;
	private String approval1;
	private String approval2;
	private String remarks;
	
	private String lapuNo2;
	private String ftaNo;
	private String ftaNo2;
	
	private String approver1;
	private String approver2;
		
	public String getApprover1() {
		return approver1;
	}
	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}
	public String getApprover2() {
		return approver2;
	}
	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	// end for upgrade report
	
	
	
	
	public String getAccountName() {
		return accountName;
	}
	public String getLapuNo2() {
		return lapuNo2;
	}
	public void setLapuNo2(String lapuNo2) {
		this.lapuNo2 = lapuNo2;
	}
	public String getFtaNo() {
		return ftaNo;
	}
	public void setFtaNo(String ftaNo) {
		this.ftaNo = ftaNo;
	}
	public String getFtaNo2() {
		return ftaNo2;
	}
	public void setFtaNo2(String ftaNo2) {
		this.ftaNo2 = ftaNo2;
	}
	public String getHisDate() {
		return hisDate;
	}
	public void setHisDate(String hisDate) {
		this.hisDate = hisDate;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBotreeCode() {
		return botreeCode;
	}
	public void setBotreeCode(String botreeCode) {
		this.botreeCode = botreeCode;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenPoStatusSelec() {
		return hiddenPoStatusSelec;
	}
	public void setHiddenPoStatusSelec(String hiddenPoStatusSelec) {
		this.hiddenPoStatusSelec = hiddenPoStatusSelec;
	}
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getOracleLocatorCode() {
		return oracleLocatorCode;
	}
	public void setOracleLocatorCode(String oracleLocatorCode) {
		this.oracleLocatorCode = oracleLocatorCode;
	}
	public String getParentLoginId() {
		return parentLoginId;
	}
	public void setParentLoginId(String parentLoginId) {
		this.parentLoginId = parentLoginId;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getShowDist() {
		return showDist;
	}
	public void setShowDist(String showDist) {
		this.showDist = showDist;
	}
	public String getShowTSM() {
		return showTSM;
	}
	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public int getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(int accountLevel) {
		this.accountLevel = accountLevel;
	}
	/**
	 * @return the parentAccountType
	 */
	public String getParentAccountType() {
		return parentAccountType;
	}
	/**
	 * @param parentAccountType the parentAccountType to set
	 */
	public void setParentAccountType(String parentAccountType) {
		this.parentAccountType = parentAccountType;
	}
	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the parentAccountName
	 */
	public String getParentAccountName() {
		return parentAccountName;
	}
	/**
	 * @param parentAccountName the parentAccountName to set
	 */
	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}
	public String getLapuNo() {
		return lapuNo;
	}
	public void setLapuNo(String lapuNo) {
		this.lapuNo = lapuNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public String getPassChangeDT() {
		return passChangeDT;
	}
	public void setPassChangeDT(String passChangeDT) {
		this.passChangeDT = passChangeDT;
	}
	
	
}
