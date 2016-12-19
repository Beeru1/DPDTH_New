package com.ibm.dp.beans;

import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.PartnerDetails;
import com.ibm.dp.dto.RateDTO;

public class DpInvoiceBeanCollection {
	
	
	private DpInvoiceDto invDto;
	private RateDTO rateDto;
	private PartnerDetails partnerDet;
	public DpInvoiceDto getInvDto() {
		return invDto;
	}
	public void setInvDto(DpInvoiceDto invDto) {
		this.invDto = invDto;
	}
	public RateDTO getRateDto() {
		return rateDto;
	}
	public void setRateDto(RateDTO rateDto) {
		this.rateDto = rateDto;
	}
	public PartnerDetails getPartnerDet() {
		return partnerDet;
	}
	public void setPartnerDet(PartnerDetails partnerDet) {
		this.partnerDet = partnerDet;
	}

}
