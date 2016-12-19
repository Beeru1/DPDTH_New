package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.exception.DPServiceException;


public interface DPInterSSDTransferBulkUploadService {

	List uploadExcel(InputStream inputstream) throws DPServiceException;
	String updateInterSSDListTransfer(List list, String userId) throws DPServiceException;
	
}
