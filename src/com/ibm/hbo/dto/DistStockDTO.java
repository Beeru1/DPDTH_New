package com.ibm.hbo.dto;

import java.util.ArrayList;

public class DistStockDTO {
	
	int productId=0;
	String ProductName="";
	String optionText="";
	String optionValue="";
	String userName="";
	int userId=0;
	String stockQty="";
	ArrayList roleList=null;
	int circleId=0;
	String serialNo = "";
	String productName = "";
	String damagedStatus = "";
	String damageRemarks = "";
	int categoryId=0;
	int accountFrom=0;
	int accountTo=0;
	int assignedProdQty=0;
	String endingSno="";
	String startingSerial="";
	String remarks="";
	String billNo="xxxz";
	float vat=-1;
	float rate=-1;
	float mrp =0.0f;
	String allocation="";//rajiv jha addedd for allocation
	String secondary="";//rajiv jha addedd for secondary 
	String status="";//rajiv jha addedd for secondary 
	String statusOSCM="";// harbans added for [ALDP,SSDP,SSCP,ERR] Oracle OSCM configuration.
	long distID=0;
	boolean return_to_fse=false;
	boolean validate=true;
	
	String jsArrprodId[];
	String jsArrremarks[];
	String jsArrqty[];
	String jsSrNo[];
	
//	Added by Shilpa for critical changes BFR 14
	String courierAgency;
	String docketNum;
	
	
	int availableProdQty=0;
	String existingSerialNo="";
	
	public String getExistingSerialNo() {
		return existingSerialNo;
	}
	public void setExistingSerialNo(String existingSerialNo) {
		this.existingSerialNo = existingSerialNo;
	}
	public String getStockQty() {
		return stockQty;
	}
	public void setStockQty(String stockQty) {
		this.stockQty = stockQty;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	public int getAssignedProdQty() {
		return assignedProdQty;
	}
	public void setAssignedProdQty(int assignedProdQty) {
		this.assignedProdQty = assignedProdQty;
	}
	public int getAvailableProdQty() {
		return availableProdQty;
	}
	public void setAvailableProdQty(int availableProdQty) {
		this.availableProdQty = availableProdQty;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getEndingSno() {
		return endingSno;
	}
	public void setEndingSno(String endingSno) {
		this.endingSno = endingSno;
	}
	public String getStartingSerial() {
		return startingSerial;
	}
	public void setStartingSerial(String startingSerial) {
		this.startingSerial = startingSerial;
	}
	public ArrayList getRoleList() {
		return roleList;
	}
	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getDamagedStatus() {
		return damagedStatus;
	}
	public void setDamagedStatus(String damagedStatus) {
		this.damagedStatus = damagedStatus;
	}
	public String getDamageRemarks() {
		return damageRemarks;
	}
	public void setDamageRemarks(String damageRemarks) {
		this.damageRemarks = damageRemarks;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getVat() {
		return vat;
	}
	public void setVat(float vat) {
		this.vat = vat;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public float getMrp() {
		return mrp;
	}
	public void setMrp(float mrp) {
		this.mrp = mrp;
	}
	public String getAllocation() {
		return allocation;
	}
	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}
	public String getSecondary() {
		return secondary;
	}
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusOSCM() {
		return statusOSCM;
	}
	public void setStatusOSCM(String statusOSCM) {
		this.statusOSCM = statusOSCM;
	}
	public long getDistID() {
		return distID;
	}
	public void setDistID(long distID) {
		this.distID = distID;
	}
	public String[] getJsArrprodId() {
		return jsArrprodId;
	}
	public void setJsArrprodId(String[] jsArrprodId) {
		this.jsArrprodId = jsArrprodId;
	}
	public String[] getJsArrqty() {
		return jsArrqty;
	}
	public void setJsArrqty(String[] jsArrqty) {
		this.jsArrqty = jsArrqty;
	}
	public String[] getJsArrremarks() {
		return jsArrremarks;
	}
	public void setJsArrremarks(String[] jsArrremarks) {
		this.jsArrremarks = jsArrremarks;
	}
	public String[] getJsSrNo() {
		return jsSrNo;
	}
	public void setJsSrNo(String[] jsSrNo) {
		this.jsSrNo = jsSrNo;
	}
	public String getCourierAgency() {
		return courierAgency;
	}
	public void setCourierAgency(String courierAgency) {
		this.courierAgency = courierAgency;
	}
	public String getDocketNum() {
		return docketNum;
	}
	public void setDocketNum(String docketNum) {
		this.docketNum = docketNum;
	}
	public boolean isReturn_to_fse() {
		return return_to_fse;
	}
	public void setReturn_to_fse(boolean return_to_fse) {
		this.return_to_fse = return_to_fse;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

}
