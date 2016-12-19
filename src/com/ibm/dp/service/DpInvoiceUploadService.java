package com.ibm.dp.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.ibm.dp.beans.InvoiceDetails;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.ExcelError;
import com.ibm.dp.dto.PartnerDetails;
import com.ibm.dp.dto.RateDTO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface DpInvoiceUploadService {
	
	public void invFileUploadls(String absFile) throws Exception;
	public void invFileUploadlsNew(String path) throws Exception;
	public void invFileUploadsx(String absFile) throws Exception;
	public void invFileUploadsxNew(String absFile) throws Exception;
	
	public List<InvoiceDetails> listInvoices(String loginNm) throws Exception;
	public DpInvoiceDto partnerInvoice(int billNo) throws Exception;	
	public String acceptInvoice(int billNo,String invoiceNo,String remarks) throws Exception;
	public String acceptInvoiceSTB(int billNo,String invoiceNo,String remarks) throws Exception;
	public String rejectInvoice(int billNo,String invoiceNo,String rem) throws Exception;
	public String rejectInvoiceSTB(int billNo,String invoiceNo,String rem) throws Exception;
	public List<InvoiceDetails> listRejectedInvoices() throws Exception;
	public List<InvoiceDetails> listInvoicesSTB(String loginNm) throws Exception;
	public List<InvoiceDetails> listInvoicesAcc(String status) throws Exception;
	public DpInvoiceDto partnerInvoiceSTB(int billNo) throws Exception;	
	
	
	public ExcelError validatePayoutInv(String absFile,String fileFlag) throws Exception;
	public String errorFileDownload(ExcelError excErr,String fileFlag) throws Exception;
	public String invoiceUploaded(String absFile,long loginId) throws Exception;
	public List<InvoiceDetails> listInvoicesDist(String loginNm,String statFlag) throws Exception;
	public List<InvoiceDetails> listAllInvoicesDist(String loginNm) throws Exception;
	public PartnerDetails partnerDetails(String olmId) throws Exception;
	public RateDTO fetchRates(Date billDt) throws Exception;
	public List<String> listSignedInvoices(String loginNm) throws Exception;
	public boolean sftpInvoiceUpload(String localPath, String ftpPath) throws JSchException, SftpException,
	IOException;
	public String fetchAddress() throws Exception;
	
	
	
}

