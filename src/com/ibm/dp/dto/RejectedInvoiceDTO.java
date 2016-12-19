package com.ibm.dp.dto;

public class RejectedInvoiceDTO {
	Integer cpeRecNo;
	Double cpeRate;
	Double cpeAmount;
	String invoiceNumber="";
	String partnerName="";
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	String tinNo="";
	String panNo="";
	String srcvTaxCT ="";
	Integer srRewNo;
	Double srRate;
	Double srRewAmount;
	Integer retenNo;
	Double retenRate;
	Double retenAmount;
	String status ="";
	String remarks ="";
	String actionDate ="";
	String olmId;
	public String getOlmId() {
		return olmId;
	}
	public void setOlmId(String olmId) {
		this.olmId = olmId;
	}
	public String getTinNo() {
		return tinNo;
	}
	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getSrcvTaxCT() {
		return srcvTaxCT;
	}
	public void setSrcvTaxCT(String srcvTaxCT) {
		this.srcvTaxCT = srcvTaxCT;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public Double getCpeAmount() {
		return cpeAmount;
	}
	public void setCpeAmount(Double cpeAmount) {
		this.cpeAmount = cpeAmount;
	}
	public Integer getCpeRecNo() {
		return cpeRecNo;
	}
	public void setCpeRecNo(Integer cpeRecNo) {
		this.cpeRecNo = cpeRecNo;
	}
	public Double getCpeRate() {
		return cpeRate;
	}
	public void setCpeRate(Double cpeRate) {
		this.cpeRate = cpeRate;
	}
	public Integer getSrRewNo() {
		return srRewNo;
	}
	public void setSrRewNo(Integer srRewNo) {
		this.srRewNo = srRewNo;
	}
	public Double getSrRate() {
		return srRate;
	}
	public void setSrRate(Double srRate) {
		this.srRate = srRate;
	}
	public Double getSrRewAmount() {
		return srRewAmount;
	}
	public void setSrRewAmount(Double srRewAmount) {
		this.srRewAmount = srRewAmount;
	}
	public Integer getRetenNo() {
		return retenNo;
	}
	public void setRetenNo(Integer retenNo) {
		this.retenNo = retenNo;
	}
	public Double getRetenRate() {
		return retenRate;
	}
	public void setRetenRate(Double retenRate) {
		this.retenRate = retenRate;
	}
	public Double getRetenAmount() {
		return retenAmount;
	}
	public void setRetenAmount(Double retenAmount) {
		this.retenAmount = retenAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 
	
	
	
}
