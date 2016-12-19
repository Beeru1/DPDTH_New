package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.List;

public class DPReverseCollectionDto implements Serializable 
{
 public String collectionId = "";
 public String collectionName = "";
 public String defectId = "";
 public String defectName = "";
 public String productId = "";
 public String productName = "";
 public String serialNo = "";
 public String remarks = "";  
 public String takePrint=""; 
 //Added by Shilpa Khanna for new format
 public String newSrno= "";
 public String newProductId = "";
 public String ageing = "";
 public String inventryChngDate = "";
 public String customerId = "";
 public String reqId = "";
 public String manuIdKey="";
 public String manuIdKeyOld="";
 public String flagCheck="false";
 public String dcStatus;
 
public String getReqId() {
	return reqId;
}
public void setReqId(String reqId) {
	this.reqId = reqId;
}
public String getSerialNo() {
	return serialNo;
}
public void setSerialNo(String serialNo) {
	this.serialNo = serialNo;
}
public String getDefectId() {
	return defectId;
}
public void setDefectId(String defectId) {
	this.defectId = defectId;
}
public String getDefectName() {
	return defectName;
}
public void setDefectName(String defectName) {
	this.defectName = defectName;
}
public String getCollectionId() {
	return collectionId;
}
public void setCollectionId(String collectionId) {
	this.collectionId = collectionId;
}
public String getCollectionName() {
	return collectionName;
}
public void setCollectionName(String collectionName) {
	this.collectionName = collectionName;
}
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
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
public String getAgeing() {
	return ageing;
}
public void setAgeing(String ageing) {
	this.ageing = ageing;
}
public String getInventryChngDate() {
	return inventryChngDate;
}
public void setInventryChngDate(String inventryChngDate) {
	this.inventryChngDate = inventryChngDate;
}
public String getNewProductId() {
	return newProductId;
}
public void setNewProductId(String newProductId) {
	this.newProductId = newProductId;
}
public String getNewSrno() {
	return newSrno;
}
public void setNewSrno(String newSrno) {
	this.newSrno = newSrno;
}
public String getTakePrint() {
	return takePrint;
}
public void setTakePrint(String takePrint) {
	this.takePrint = takePrint;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getManuIdKey() {
	return manuIdKey;
}
public void setManuIdKey(String manuIdKey) {
	this.manuIdKey = manuIdKey;
}
public String getManuIdKeyOld() {
	return manuIdKeyOld;
}
public void setManuIdKeyOld(String manuIdKeyOld) {
	this.manuIdKeyOld = manuIdKeyOld;
}

public String getFlagCheck() {
	return flagCheck;
}
public void setFlagCheck(String flagCheck) {
	this.flagCheck = flagCheck;
}
public String getDcStatus() {
	return dcStatus;
}
public void setDcStatus(String dcStatus) {
	this.dcStatus = dcStatus;
}
 
 

}
