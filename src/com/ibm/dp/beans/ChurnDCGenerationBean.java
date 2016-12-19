package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ChurnDCGenerationBean extends ActionForm {
	String message=""; 	
	public String courierAgency;	
	public String docketNumber; 
	private String[] hidStrSerialNo;
	String serial_Number=""; 
	String product_Id=""; 
	String vc_Id=""; 
	String customer_Id=""; 
	String si_Id=""; 
	int ageing=0;
	public ArrayList poList; 
	String remarks=""; 
	String whouseId=""; 
	public ArrayList whAddressList; 
	String product_Name="";  
	String whAddress="";
	private ArrayList wareHouseList;
	private String accountWarehouseCode;
	private String hidstrDcNo;
	private String isERR;
	public String[] getHidStrSerialNo() {		return hidStrSerialNo;	}
	public void setHidStrSerialNo(String[] hidStrSerialNo) {		this.hidStrSerialNo = hidStrSerialNo;	}


	public String getCourierAgency() {		return courierAgency;	}
	public void setCourierAgency(String courierAgency) {		this.courierAgency = courierAgency;	}
	
	public String getDocketNumber() {		return docketNumber;	}
	public void setDocketNumber(String docketNumber) {		this.docketNumber = docketNumber;	}
	
	public ArrayList getWhAddressList() {		return whAddressList;	}
	public void setWhAddressList(ArrayList whAddressList) {		this.whAddressList = whAddressList;	}

	public String getRemarks() {		return remarks;	}
	public void setRemarks(String remarks) {		this.remarks = remarks;	}
	
	public String getWhouseId() {		return whouseId;	}
	public void setWhouseId(String whouseId) {		this.whouseId = whouseId;	}

	public ArrayList getPoList() {		return poList;	}
	public void setPoList(ArrayList poList) {		this.poList = poList;	}
	
	
	public String getMessage() {		return message;	}
	public void setMessage(String message) {		this.message = message;	}
	
	public int getAgeing() {		return ageing;	}
	public void setAgeing(int ageing) {		this.ageing = ageing;	}
	
	public String getCustomer_Id() {		return customer_Id;	}
	public void setCustomer_Id(String customer_Id) {		this.customer_Id = customer_Id;	}
	
	public String getProduct_Id() {		return product_Id;	}
	public void setProduct_Id(String product_Id) {		this.product_Id = product_Id;	}
	
	public String getSerial_Number() {		return serial_Number;	}
	public void setSerial_Number(String serial_Number) {		this.serial_Number = serial_Number;	}
	
	public String getSi_Id() {		return si_Id;	}
	public void setSi_Id(String si_Id) {		this.si_Id = si_Id;	}
	
	public String getVc_Id() {		return vc_Id;	}
	public void setVc_Id(String vc_Id) {		this.vc_Id = vc_Id;	}
		
	public String getProduct_Name() {		return product_Name;	}
	public void setProduct_Name(String product_Name) {		this.product_Name = product_Name;	}
	
	String serial_NumberN=""; String product_IdN=""; String vc_IdN=""; 
	String customer_IdN=""; String si_IdN=""; int ageingN=0;  String product_NameN="";
	public ArrayList poListN;

	public int getAgeingN() {
		return ageingN;
	}
	public void setAgeingN(int ageingN) {
		this.ageingN = ageingN;
	}
	public String getCustomer_IdN() {
		return customer_IdN;
	}
	public void setCustomer_IdN(String customer_IdN) {
		this.customer_IdN = customer_IdN;
	}
	public ArrayList getPoListN() {		return poListN;	}
	public void setPoListN(ArrayList poListN) {		this.poListN = poListN;	}
	
	public String getProduct_IdN() {
		return product_IdN;
	}
	public void setProduct_IdN(String product_IdN) {
		this.product_IdN = product_IdN;
	}
	public String getSerial_NumberN() {
		return serial_NumberN;
	}
	public void setSerial_NumberN(String serial_NumberN) {
		this.serial_NumberN = serial_NumberN;
	}
	public String getSi_IdN() {
		return si_IdN;
	}
	public void setSi_IdN(String si_IdN) {
		this.si_IdN = si_IdN;
	}
	public String getVc_IdN() {
		return vc_IdN;
	}
	public void setVc_IdN(String vc_IdN) {
		this.vc_IdN = vc_IdN;
	}
	
	public String getProduct_NameN() {		return product_NameN;	}
	public void setProduct_NameN(String product_NameN) {		this.product_NameN = product_NameN;	}
	public String getWhAddress() {
		return whAddress;
	}
	public void setWhAddress(String whAddress) {
		this.whAddress = whAddress;
	}
	public ArrayList getWareHouseList() {		return wareHouseList;	}
	public void setWareHouseList(ArrayList wareHouseList) {		this.wareHouseList = wareHouseList;	}
	public String getAccountWarehouseCode() {
		return accountWarehouseCode;
	}
	public void setAccountWarehouseCode(String accountWarehouseCode) {
		this.accountWarehouseCode = accountWarehouseCode;
	}
	public String getHidstrDcNo() {
		return hidstrDcNo;
	}
	public void setHidstrDcNo(String hidstrDcNo) {
		this.hidstrDcNo = hidstrDcNo;
	}
	public String getIsERR() {
		return isERR;
	}
	public void setIsERR(String isERR) {
		this.isERR = isERR;
	}

}
