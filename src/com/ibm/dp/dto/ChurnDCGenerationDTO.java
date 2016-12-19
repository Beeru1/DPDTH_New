package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ChurnDCGenerationDTO implements Serializable{
	private String DcNo="";
	private String message=""; 	
	private String courierAgency;	
	private String docketNumber;
	private String serial_Number="";
	private String serial_NumberN="";
	private String product_Id=""; 
	private String vc_Id=""; 
	private String customer_Id=""; 
	private String si_Id=""; 
	private int ageing=0;
	private String vc_IdN=""; 
	private String customer_IdN=""; 
	private String si_IdN=""; 
	private int ageingN=0;
	private String collectionDateN;
	public ArrayList poList; 
	private String remarks=""; 
	private String whouseId=""; 
	private ArrayList whAddressList; 
	private String product_Name="";
	private String product_NameN=""; 
	private String whName="";
	private String dc_Date=""; 
	private String accountWarehouseCode; 
	private String accountWarehouseName;
	private String reqId = "";
	private String reqIdN = "";
	private ArrayList<String> allCheckedSTB ;
	private int intCircleID = 0;
	private long lnUserID = 0l;
	private String collectionDate;
	
	
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getAccountWarehouseCode() {
		return accountWarehouseCode;
	}
	public void setAccountWarehouseCode(String accountWarehouseCode) {
		this.accountWarehouseCode = accountWarehouseCode;
	}
	public String getAccountWarehouseName() {
		return accountWarehouseName;
	}
	public void setAccountWarehouseName(String accountWarehouseName) {
		this.accountWarehouseName = accountWarehouseName;
	}
	public int getAgeing() {
		return ageing;
	}
	public void setAgeing(int ageing) {
		this.ageing = ageing;
	}
	public ArrayList<String> getAllCheckedSTB() {
		return allCheckedSTB;
	}
	public void setAllCheckedSTB(ArrayList<String> allCheckedSTB) {
		this.allCheckedSTB = allCheckedSTB;
	}
	public String getCourierAgency() {
		return courierAgency;
	}
	public void setCourierAgency(String courierAgency) {
		this.courierAgency = courierAgency;
	}
	public String getCustomer_Id() {
		return customer_Id;
	}
	public void setCustomer_Id(String customer_Id) {
		this.customer_Id = customer_Id;
	}
	public String getDc_Date() {
		return dc_Date;
	}
	public void setDc_Date(String dc_Date) {
		this.dc_Date = dc_Date;
	}
	public String getDcNo() {
		return DcNo;
	}
	public void setDcNo(String dcNo) {
		DcNo = dcNo;
	}
	public String getDocketNumber() {
		return docketNumber;
	}
	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList getPoList() {
		return poList;
	}
	public void setPoList(ArrayList poList) {
		this.poList = poList;
	}
	public String getProduct_Id() {
		return product_Id;
	}
	public void setProduct_Id(String product_Id) {
		this.product_Id = product_Id;
	}
	public String getProduct_Name() {
		return product_Name;
	}
	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getReqIdN() {
		return reqIdN;
	}
	public void setReqIdN(String reqIdN) {
		this.reqIdN = reqIdN;
	}
	public String getSerial_Number() {
		return serial_Number;
	}
	public void setSerial_Number(String serial_Number) {
		this.serial_Number = serial_Number;
	}
	public String getSi_Id() {
		return si_Id;
	}
	public void setSi_Id(String si_Id) {
		this.si_Id = si_Id;
	}
	public String getVc_Id() {
		return vc_Id;
	}
	public void setVc_Id(String vc_Id) {
		this.vc_Id = vc_Id;
	}
	public ArrayList getWhAddressList() {
		return whAddressList;
	}
	public void setWhAddressList(ArrayList whAddressList) {
		this.whAddressList = whAddressList;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public String getWhouseId() {
		return whouseId;
	}
	public void setWhouseId(String whouseId) {
		this.whouseId = whouseId;
	}
	public int getIntCircleID() {
		return intCircleID;
	}
	public void setIntCircleID(int intCircleID) {
		this.intCircleID = intCircleID;
	}
	public long getLnUserID() {
		return lnUserID;
	}
	public void setLnUserID(long lnUserID) {
		this.lnUserID = lnUserID;
	}
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
	public String getProduct_NameN() {
		return product_NameN;
	}
	public void setProduct_NameN(String product_NameN) {
		this.product_NameN = product_NameN;
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
	public String getCollectionDateN() {
		return collectionDateN;
	}
	public void setCollectionDateN(String collectionDateN) {
		this.collectionDateN = collectionDateN;
	}
	
}
