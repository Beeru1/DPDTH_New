package com.ibm.dp.dto;

public class InterSsdTransferAdminDto {
	
	private String fromDistId;
	private String toDistId;
	private String productId;
	private String circleId;
	private String serialNo;
	private String prodQty;
	private String loginUser;
	private String stockType;
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getProdQty() {
		return prodQty;
	}
	public void setProdQty(String prodQty) {
		this.prodQty = prodQty;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getStockType() {
		return stockType;
	}
	
	

}
