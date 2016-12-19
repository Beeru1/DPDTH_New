package com.ibm.dp.dto;

public class NSBulkUploadDto {
	public String distOlmId;
	public String productName;
	public String stockQty;
	public String distId;
	public String err_msg="";
	public String productId;
	public String distOracleLocatorCode;
	public String oracleProductCode;
	public String productType;
	
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStockQty() {
		return stockQty;
	}
	public void setStockQty(String stockQty) {
		this.stockQty = stockQty;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDistOracleLocatorCode() {
		return distOracleLocatorCode;
	}
	public void setDistOracleLocatorCode(String distOracleLocatorCode) {
		this.distOracleLocatorCode = distOracleLocatorCode;
	}
	public String getOracleProductCode() {
		return oracleProductCode;
	}
	public void setOracleProductCode(String oracleProductCode) {
		this.oracleProductCode = oracleProductCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	
	

}
