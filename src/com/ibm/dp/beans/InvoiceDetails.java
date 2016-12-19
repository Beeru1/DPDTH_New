package com.ibm.dp.beans;

public class InvoiceDetails {
	
	private String partnerNm;
	private String billNo;
	private String olmId;
	private String invoiceNo;
	private String monthFor;
	private String status;
	
	public String getPartnerNm() {
		return partnerNm;
	}
	public void setPartnerNm(String partnerNm) {
		this.partnerNm = partnerNm;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getOlmId() {
		return olmId;
	}
	public void setOlmId(String olmId) {
		this.olmId = olmId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String i) {
		this.invoiceNo = i;
	}
	public void setMonthFor(String monthFor) {
		this.monthFor = monthFor;
	}
	public String getMonthFor() {
		return monthFor;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	
	
	
}
