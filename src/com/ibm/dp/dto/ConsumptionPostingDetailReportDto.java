package com.ibm.dp.dto;

public class ConsumptionPostingDetailReportDto {
	private static final long serialVersionUID = 1L;
	private String serialNo= "";
	private String assignedDate;
	private String itemCode;
	private String distributorLoginName;
	private String distributorName;
	private String fseDetail;
	private String retailerCode;
	private String customerId;
	private String productName;
	private String circleName;
	public String getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDistributorLoginName() {
		return distributorLoginName;
	}
	public void setDistributorLoginName(String distributorLoginName) {
		this.distributorLoginName = distributorLoginName;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getFseDetail() {
		return fseDetail;
	}
	public void setFseDetail(String fseDetail) {
		this.fseDetail = fseDetail;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getRetailerCode() {
		return retailerCode;
	}
	public void setRetailerCode(String retailerCode) {
		this.retailerCode = retailerCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
}
