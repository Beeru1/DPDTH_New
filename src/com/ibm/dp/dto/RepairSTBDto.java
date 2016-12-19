package com.ibm.dp.dto;

public class RepairSTBDto {
	private String strCollectionType;
	private String strDefectType;
	private String strProduct;
	private String strSerialNo="";
	private String strCollectionDate="";
	private String strRemarks="";
	private String prodId;
	private String rowSrNo;
	private String collectionId;
	private String defectId;
	private String strNewRemarks;
	private String strOldProductId;
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getRowSrNo() {
		return rowSrNo;
	}
	public void setRowSrNo(String rowSrNo) {
		this.rowSrNo = rowSrNo;
	}
	public String getStrCollectionDate() {
		return strCollectionDate;
	}
	public void setStrCollectionDate(String strCollectionDate) {
		this.strCollectionDate = strCollectionDate;
	}
	public String getStrCollectionType() {
		return strCollectionType;
	}
	public void setStrCollectionType(String strCollectionType) {
		this.strCollectionType = strCollectionType;
	}
	public String getStrDefectType() {
		return strDefectType;
	}
	public void setStrDefectType(String strDefectType) {
		this.strDefectType = strDefectType;
	}
	
	public String getStrOldProductId() {
		return strOldProductId;
	}
	public void setStrOldProductId(String strOldProductId) {
		this.strOldProductId = strOldProductId;
	}
	public String getStrNewRemarks() {
		return strNewRemarks;
	}
	public void setStrNewRemarks(String strNewRemarks) {
		this.strNewRemarks = strNewRemarks;
	}
	public String getStrProduct() {
		return strProduct;
	}
	public void setStrProduct(String strProduct) {
		this.strProduct = strProduct;
	}
	public String getStrRemarks() {
		return strRemarks;
	}
	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}
	public String getStrSerialNo() {
		return strSerialNo;
	}
	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = strSerialNo;
	}
	

}
