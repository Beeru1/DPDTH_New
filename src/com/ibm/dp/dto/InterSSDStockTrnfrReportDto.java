package com.ibm.dp.dto;

public class InterSSDStockTrnfrReportDto {
	
	private static final long serialVersionUID = 1L;
	
	private String initiationDate= "";
	private String circleName= "";
	private String zbmZsmName= "";
	private String fromDistName= "";
	private String productName= "";
	private String requestedQty= "";
	private String transferDate= "";
	private String toDistName= "";
	private String transferedQty= "";
	private String receiptDate= "";
	private String fromTSM= "";
	private String toTSM= "";
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getFromDistName() {
		return fromDistName;
	}
	public void setFromDistName(String fromDistName) {
		this.fromDistName = fromDistName;
	}
	public String getInitiationDate() {
		return initiationDate;
	}
	public void setInitiationDate(String initiationDate) {
		this.initiationDate = initiationDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getRequestedQty() {
		return requestedQty;
	}
	public void setRequestedQty(String requestedQty) {
		this.requestedQty = requestedQty;
	}
	public String getToDistName() {
		return toDistName;
	}
	public void setToDistName(String toDistName) {
		this.toDistName = toDistName;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransferedQty() {
		return transferedQty;
	}
	public void setTransferedQty(String transferedQty) {
		this.transferedQty = transferedQty;
	}
	public String getZbmZsmName() {
		return zbmZsmName;
	}
	public void setZbmZsmName(String zbmZsmName) {
		this.zbmZsmName = zbmZsmName;
	}
	public String getFromTSM() {
		return fromTSM;
	}
	public void setFromTSM(String fromTSM) {
		this.fromTSM = fromTSM;
	}
	public String getToTSM() {
		return toTSM;
	}
	public void setToTSM(String toTSM) {
		this.toTSM = toTSM;
	}
	

}
