package com.ibm.dp.dto;

public class RejectedPartnerInvDTO {
	String OlmId;
	public String getOlmId() {
		return OlmId;
	}
	public void setOlmId(String olmId) {
		OlmId = olmId;
	}
	String partnerName;
	Long HDT1;
	Long HDT2;
	Long HDT3;
	Long MULTIT1;
	Long MULTIT2;
	Long MULTIT3;
	Long MULTIDVRT1;
	Long MULTIDVRT2;
	Long MULTIDVRT3;
	Long TOTALACT;
	Long TOTALAmount;
	Long Amount;
	Long totalInvAmt;
	String invoiceNumber="";
	String remarks="";
	String status="";
	String actionDate ="";
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Long getHDT1() {
		return HDT1;
	}
	public void setHDT1(Long hdt1) {
		HDT1 = hdt1;
	}
	public Long getHDT2() {
		return HDT2;
	}
	public void setHDT2(Long hdt2) {
		HDT2 = hdt2;
	}
	public Long getHDT3() {
		return HDT3;
	}
	public void setHDT3(Long hdt3) {
		HDT3 = hdt3;
	}
	public Long getMULTIT1() {
		return MULTIT1;
	}
	public void setMULTIT1(Long multit1) {
		MULTIT1 = multit1;
	}
	public Long getMULTIT2() {
		return MULTIT2;
	}
	public void setMULTIT2(Long multit2) {
		MULTIT2 = multit2;
	}
	public Long getMULTIT3() {
		return MULTIT3;
	}
	public void setMULTIT3(Long multit3) {
		MULTIT3 = multit3;
	}
	public Long getMULTIDVRT1() {
		return MULTIDVRT1;
	}
	public void setMULTIDVRT1(Long multidvrt1) {
		MULTIDVRT1 = multidvrt1;
	}
	public Long getMULTIDVRT2() {
		return MULTIDVRT2;
	}
	public void setMULTIDVRT2(Long multidvrt2) {
		MULTIDVRT2 = multidvrt2;
	}
	public Long getMULTIDVRT3() {
		return MULTIDVRT3;
	}
	public void setMULTIDVRT3(Long multidvrt3) {
		MULTIDVRT3 = multidvrt3;
	}
	public Long getTOTALACT() {
		return TOTALACT;
	}
	public void setTOTALACT(Long totalact) {
		TOTALACT = totalact;
	}
	public Long getTOTALAmount() {
		return TOTALAmount;
	}
	public void setTOTALAmount(Long amount) {
		TOTALAmount = amount;
	}
	public Long getAmount() {
		return Amount;
	}
	public void setAmount(Long amount) {
		Amount = amount;
	}
	public Long getTotalInvAmt() {
		return totalInvAmt;
	}
	public void setTotalInvAmt(Long totalInvAmt) {
		this.totalInvAmt = totalInvAmt;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	
	
}
