package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;

public class InvoiceForm extends ActionForm {
	
	private String invoiceNo;
	private String methodName;
	private int bilNo;
	private String invoiceMessage;
	private String partnerRem;
	private String address;
	
	public String getInvoiceMessage() {
		return invoiceMessage;
	}
	public void setInvoiceMessage(String invoiceMessage) {
		this.invoiceMessage = invoiceMessage;
	}
	public int getBilNo() {
		return bilNo;
	}
	public void setBilNo(int bilNo) {
		this.bilNo = bilNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setPartnerRem(String partnerRem) {
		this.partnerRem = partnerRem;
	}
	public String getPartnerRem() {
		return partnerRem;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
   
	

}
