package com.ibm.dp.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPPoAcceptanceBulkService {

	public List uploadExcel(FormFile myXls ,String deliveryChallanNo) throws VirtualizationServiceException;
	
	public DPPoAcceptanceBulkDTO getWrongShortSrNo(String invoiceNo, String dcNo, int intCircleID ,List list) throws VirtualizationServiceException;
	
	public DPPoAcceptanceBulkDTO getShortSrNo(String dcNo) throws VirtualizationServiceException;
	
}
