package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class InvoiceListForm  extends ActionForm{
	

	private List<InvoiceDetails>invoiceList;
	private List<String> signedInvoice;

	public void setInvoiceList(List<InvoiceDetails> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<InvoiceDetails> getInvoiceList() {
		return invoiceList;
	}

	public void setSignedInvoice(List<String> signedInvoice) {
		this.signedInvoice = signedInvoice;
	}

	public List<String> getSignedInvoice() {
		return signedInvoice;
	}
	
	
	
	

}
