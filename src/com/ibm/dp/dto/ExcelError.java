package com.ibm.dp.dto;

import java.util.ArrayList;
import java.util.List;

public class ExcelError {
	private List <InvoiceMsgDto> invoiceDto;
	private List <InvoiceMsgDto> invoiceDtoS;

	public List<InvoiceMsgDto> getInvoiceDto() {
		return invoiceDto;
	}
	public void setInvoiceDto(List<InvoiceMsgDto> invoiceDto) {
		this.invoiceDto = invoiceDto;
	}
	public List<InvoiceMsgDto> getInvoiceDtoS() {
		return invoiceDtoS;
	}
	public void setInvoiceDtoS(List<InvoiceMsgDto> invoiceDtoS) {
		this.invoiceDtoS = invoiceDtoS;
	}
	
}
