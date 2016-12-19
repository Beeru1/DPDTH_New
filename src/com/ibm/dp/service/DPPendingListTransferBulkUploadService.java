package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.exception.DPServiceException;


public interface DPPendingListTransferBulkUploadService {

	List uploadExcel(InputStream inputstream) throws DPServiceException;
	String updatePendingListTransfer(List list, String userId) throws DPServiceException;
	
}
