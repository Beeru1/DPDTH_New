package com.ibm.dp.dto;

public class DPPendingListTransferBulkUploadDto {
	
	private String  err_msg="";
	private String fromDistOlmId;
	private String fromDistId;
	private String toDistOlmId;	
	private String toDistId;
	private String productNameDef;
	private String productIdDef;
	private String serialNoDef;	
	private String productNameChange;
	private String productIdChange;
	private String serialNoChange;	
		
	public String getFromDistOlmId() {
		return fromDistOlmId;
	}
	public void setFromDistOlmId(String fromDistOlmId) {
		this.fromDistOlmId = fromDistOlmId;
	}
	public String getToDistOlmId() {
		return toDistOlmId;
	}
	public void setToDistOlmId(String toDistOlmId) {
		this.toDistOlmId = toDistOlmId;
	}
	
	public String getSerialNoDef() {
		return serialNoDef;
	}
	public void setSerialNoDef(String serialNoDef) {
		this.serialNoDef = serialNoDef;
	}
	
	public String getSerialNoChange() {
		return serialNoChange;
	}
	public void setSerialNoChange(String serialNoChange) {
		this.serialNoChange = serialNoChange;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	public String getProductNameDef() {
		return productNameDef;
	}
	public void setProductNameDef(String productNameDef) {
		this.productNameDef = productNameDef;
	}
	public String getProductIdDef() {
		return productIdDef;
	}
	public void setProductIdDef(String productIdDef) {
		this.productIdDef = productIdDef;
	}
	public String getProductNameChange() {
		return productNameChange;
	}
	public void setProductNameChange(String productNameChange) {
		this.productNameChange = productNameChange;
	}
	public String getProductIdChange() {
		return productIdChange;
	}
	public void setProductIdChange(String productIdChange) {
		this.productIdChange = productIdChange;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	
	
	
	
	

}
