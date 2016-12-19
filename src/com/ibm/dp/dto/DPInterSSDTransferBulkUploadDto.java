package com.ibm.dp.dto;

public class DPInterSSDTransferBulkUploadDto {
	
	private String  err_msg="";
	private String fromDistOlmId;
	private String fromDistId;
	private String toDistOlmId;	
	private String toDistId;			
	private String productName;
	private String productId;
	private String serialNo;
	private String toDistIdOld;
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getFromDistOlmId() {
		return fromDistOlmId;
	}
	public void setFromDistOlmId(String fromDistOlmId) {
		this.fromDistOlmId = fromDistOlmId;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	public String getToDistOlmId() {
		return toDistOlmId;
	}
	public void setToDistOlmId(String toDistOlmId) {
		this.toDistOlmId = toDistOlmId;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getToDistIdOld() {
		return toDistIdOld;
	}
	public void setToDistIdOld(String toDistIdOld) {
		this.toDistIdOld = toDistIdOld;
	}	
		
	
	
	

}
