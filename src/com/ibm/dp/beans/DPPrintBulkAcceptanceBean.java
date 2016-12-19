package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;

public class DPPrintBulkAcceptanceBean extends ActionForm {

	private String poNo="";
	private String poQty="";
	private String dcNo="";
	private String invoiceQty="";
	private String totalReceived="";
	private String totalWrongShipped="";
	private String totalMissing="";
	private List<DPPrintBulkAcceptanceDTO> stbcorrectList;
	private List<DPPrintBulkAcceptanceDTO> stbwrongList;
	private List<DPPrintBulkAcceptanceDTO> stbmissingList;
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getInvoiceQty() {
		return invoiceQty;
	}
	public void setInvoiceQty(String invoiceQty) {
		this.invoiceQty = invoiceQty;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getPoQty() {
		return poQty;
	}
	public void setPoQty(String poQty) {
		this.poQty = poQty;
	}
	public List<DPPrintBulkAcceptanceDTO> getStbcorrectList() {
		return stbcorrectList;
	}
	public void setStbcorrectList(List<DPPrintBulkAcceptanceDTO> stbcorrectList) {
		this.stbcorrectList = stbcorrectList;
	}
	public List<DPPrintBulkAcceptanceDTO> getStbmissingList() {
		return stbmissingList;
	}
	public void setStbmissingList(List<DPPrintBulkAcceptanceDTO> stbmissingList) {
		this.stbmissingList = stbmissingList;
	}
	public List<DPPrintBulkAcceptanceDTO> getStbwrongList() {
		return stbwrongList;
	}
	public void setStbwrongList(List<DPPrintBulkAcceptanceDTO> stbwrongList) {
		this.stbwrongList = stbwrongList;
	}
	public String getTotalMissing() {
		return totalMissing;
	}
	public void setTotalMissing(String totalMissing) {
		this.totalMissing = totalMissing;
	}
	public String getTotalReceived() {
		return totalReceived;
	}
	public void setTotalReceived(String totalReceived) {
		this.totalReceived = totalReceived;
	}
	public String getTotalWrongShipped() {
		return totalWrongShipped;
	}
	public void setTotalWrongShipped(String totalWrongShipped) {
		this.totalWrongShipped = totalWrongShipped;
	}
}
