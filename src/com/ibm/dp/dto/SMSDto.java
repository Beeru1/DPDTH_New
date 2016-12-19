package com.ibm.dp.dto;

import java.util.List;

public class SMSDto {

	private String dcNo="";
	private String distId="";
	private String poNo="";
	private String prNo="";
	private String loginName="";
	private String mobileNumber="";
	private String newPassword="";
	private String invNumber="";
	private String parentMobileNumber="";
	private String parentMobileNumber2="";
	private int accountLevel;
	private String swapManagerMobileNo="";
	private String deliveryChallanNo="";
	private String productName="";
	private String remarks="";
	private String poRejectDesc="";
	private int alertType;
	private List prodQuantityList=null;
	private String tsmName="";
	private int countSrNo=0;
	private String userAccountId = "";
	private String accountName="";
	private String dynamicSMSContent="";
	private String zsmMobile="";
	private String zsmName="";
	private String zsmID="";
	public String getZsmID() {
		return zsmID;
	}
	public void setZsmID(String zsmID) {
		this.zsmID = zsmID;
	}
	public String getTsmID() {
		return tsmID;
	}
	public void setTsmID(String tsmID) {
		this.tsmID = tsmID;
	}

	private String tsmID="";
	private String message="";
	private String status="";
	private String days="";
	private String hours="";
	private String statusDate="";
	private int smsId;
	private String stbType="";
	private String prdId="";
	private String lapuMobile1;
	private String lapuMobile2;
	private boolean bothTypeAccount = false;
	private String StbStatus=null;
	

	public String getStbStatus() {
		return StbStatus;
	}
	public void setStbStatus(String stbStatus) {
		StbStatus = stbStatus;
	}
	public String getPrdId() {
		return prdId;
	}
	public void setPrdId(String prdId) {
		this.prdId = prdId;
	}
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}

	private String recoPeriod="";
	private String gracePeriod="";

   
				
	public String getRecoPeriod() {
		return recoPeriod;
	}
	public void setRecoPeriod(String recoPeriod) {
		this.recoPeriod = recoPeriod;
	}
	public String getGracePeriod() {
		return gracePeriod;
	}
	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDynamicSMSContent() {
		return dynamicSMSContent;
	}
	public void setDynamicSMSContent(String dynamicSMSContent) {
		this.dynamicSMSContent = dynamicSMSContent;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(String userAccountId) {
		this.userAccountId = userAccountId;
	}
	public int getCountSrNo() {
		return countSrNo;
	}
	public void setCountSrNo(int countSrNo) {
		this.countSrNo = countSrNo;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getDeliveryChallanNo() {
		return deliveryChallanNo;
	}
	public void setDeliveryChallanNo(String deliveryChallanNo) {
		this.deliveryChallanNo = deliveryChallanNo;
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
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getPrNo() {
		return prNo;
	}
	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getInvNumber() {
		return invNumber;
	}
	public void setInvNumber(String invNumber) {
		this.invNumber = invNumber;
	}

	public String getParentMobileNumber() {
		return parentMobileNumber;
	}
	public void setParentMobileNumber(String parentMobileNumber) {
		this.parentMobileNumber = parentMobileNumber;
	}
	public String getSwapManagerMobileNo() {
		return swapManagerMobileNo;
	}
	public void setSwapManagerMobileNo(String swapManagerMobileNo) {
		this.swapManagerMobileNo = swapManagerMobileNo;
	}
	public int getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(int accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPoRejectDesc() {
		return poRejectDesc;
	}
	public void setPoRejectDesc(String poRejectDesc) {
		this.poRejectDesc = poRejectDesc;
	}
	public int getAlertType() {
		return alertType;
	}
	public void setAlertType(int alertType) {
		this.alertType = alertType;
	}
	public List getProdQuantityList() {
		return prodQuantityList;
	}
	public void setProdQuantityList(List prodQuantityList) {
		this.prodQuantityList = prodQuantityList;
	}
	public int getSmsId() {
		return smsId;
	}
	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	public String getZsmMobile() {
		return zsmMobile;
	}
	public void setZsmMobile(String zsmMobile) {
		this.zsmMobile = zsmMobile;
	}
	public String getZsmName() {
		return zsmName;
	}
	public void setZsmName(String zsmName) {
		this.zsmName = zsmName;
	}
	public String getLapuMobile1() {
		return lapuMobile1;
	}
	public void setLapuMobile1(String lapuMobile1) {
		this.lapuMobile1 = lapuMobile1;
	}
	public String getLapuMobile2() {
		return lapuMobile2;
	}
	public void setLapuMobile2(String lapuMobile2) {
		this.lapuMobile2 = lapuMobile2;
	}
	public String getParentMobileNumber2() {
		return parentMobileNumber2;
	}
	public void setParentMobileNumber2(String parentMobileNumber2) {
		this.parentMobileNumber2 = parentMobileNumber2;
	}
	public boolean isBothTypeAccount() {
		return bothTypeAccount;
	}
	public void setBothTypeAccount(boolean bothTypeAccount) {
		this.bothTypeAccount = bothTypeAccount;
	}

	
	
	
}
