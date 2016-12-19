package com.ibm.virtualization.recharge.service;

import java.util.List;

import com.ibm.dp.dto.RejectedInvoiceDTO;
import com.ibm.dp.dto.RejectedPartnerInvDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface RejectedInvoiceService {
	public List<RejectedInvoiceDTO>  getRejectedInvExcel(String monthID) throws VirtualizationServiceException ;
	public List<RejectedPartnerInvDTO>  getRejectedPartnerInvExcel(String monthID) throws VirtualizationServiceException ;
	public List<String>  getDateList() throws VirtualizationServiceException ;

}
