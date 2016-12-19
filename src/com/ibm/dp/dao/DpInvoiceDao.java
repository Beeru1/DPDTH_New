package com.ibm.dp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.InvoiceDetails;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.ErrorDto;
import com.ibm.dp.dto.PartnerDetails;
import com.ibm.dp.dto.RateDTO;

public interface DpInvoiceDao {
	public void uploadInvoiceDetails(Map<String,DpInvoiceDto> dpInvMap) throws Exception;
	public void uploadInvoiceSheet(Map<String,DpInvoiceDto> dpInvMap) throws Exception;
	
	public List<InvoiceDetails> listInvoices(String loginNm) throws Exception;
	public DpInvoiceDto partnerInvoice(int billNo) throws Exception;
	public String genericInvoice(int billNo,String invoiceNo,String status,String rem) throws Exception;
	public String genericInvoiceSTB(int billNo,String invoiceNo,String status,String rem) throws Exception;
	public List<InvoiceDetails> listRejectedInvoices() throws Exception;
	public List<InvoiceDetails> listInvoicesSTB(String loginNm) throws Exception;
	public DpInvoiceDto partnerInvoiceSTB(int billNo) throws Exception;
	public ErrorDto validateOlmIdPartner(String olmId,String partnerNm,String monthFr) throws Exception;
	public ErrorDto validateOlmIdPartnerNew(String olmId,String partnerNm,String monthFr) throws Exception;
	public ErrorDto validateDistCircle(String olmId,String circle) throws Exception;
	public String invoiceUploaded(String absFile,long loginId) throws Exception;
	public List<InvoiceDetails> listInvoicesAcc(String status) throws Exception;
	public List<InvoiceDetails> listInvoicesDist(String loginNm,String statFlag) throws Exception;
	public List<InvoiceDetails> listAllInvoicesDist(String loginNm) throws Exception;
	public PartnerDetails partnerDetails(String olmId) throws Exception;
	public RateDTO fetchRates(Date billDt) throws Exception;
	public String fetchAddress() throws Exception;
	
	
	
	
	
}
