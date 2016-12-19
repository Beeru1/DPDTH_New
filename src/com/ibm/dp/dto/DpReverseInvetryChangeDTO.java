package com.ibm.dp.dto;

import java.util.List;

public class DpReverseInvetryChangeDTO {
	
	private String collectionType;
	private String collectionId;
	private String defectiveSerialNo;
	private String defectiveProductName;
	private String defectiveProductId;
	private String changedSerialNo;
	private String changedProductName;
	private String changedProductId;
	private String inventoryChangedDate;
	private String ageing;
	private String defect;
	private String defectName;
	private String defectType;
	private String defectId;
	private String remarks;
	private String customerId;
	private String reqId;
	private String rejectedDC;//Neetika on 19 Feb
	public String getRejectedDC() {
		return rejectedDC;
	}
	public void setRejectedDC(String rejectedDC) {
		this.rejectedDC = rejectedDC;
	}
	private List<DpReverseInvetryChangeDTO> listDefect;
	
	public String getAgeing() {
		return ageing;
	}
	public void setAgeing(String ageing) {
		this.ageing = ageing;
	}
	public String getChangedProductId() {
		return changedProductId;
	}
	public void setChangedProductId(String changedProductId) {
		this.changedProductId = changedProductId;
	}
	public String getChangedSerialNo() {
		return changedSerialNo;
	}
	public void setChangedSerialNo(String changedSerialNo) {
		this.changedSerialNo = changedSerialNo;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getDefectiveProductId() {
		return defectiveProductId;
	}
	public void setDefectiveProductId(String defectiveProductId) {
		this.defectiveProductId = defectiveProductId;
	}
	public String getDefectiveSerialNo() {
		return defectiveSerialNo;
	}
	public void setDefectiveSerialNo(String defectiveSerialNo) {
		this.defectiveSerialNo = defectiveSerialNo;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public String getInventoryChangedDate() {
		return inventoryChangedDate;
	}
	public void setInventoryChangedDate(String inventoryChangedDate) {
		this.inventoryChangedDate = inventoryChangedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public String getChangedProductName() {
		return changedProductName;
	}
	public void setChangedProductName(String changedProductName) {
		this.changedProductName = changedProductName;
	}
	public String getDefectiveProductName() {
		return defectiveProductName;
	}
	public void setDefectiveProductName(String defectiveProductName) {
		this.defectiveProductName = defectiveProductName;
	}
	public List<DpReverseInvetryChangeDTO> getListDefect() {
		return listDefect;
	}
	public void setListDefect(List<DpReverseInvetryChangeDTO> listDefect) {
		this.listDefect = listDefect;
	}
	public String getDefect() {
		return defect;
	}
	public void setDefect(String defect) {
		this.defect = defect;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDefectName() {
		return defectName;
	}
	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	

}
