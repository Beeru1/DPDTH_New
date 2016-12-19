package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.dp.exception.DPServiceException;

public interface NSBulkUploadService {
	
	List uploadExcel(InputStream inputstream) throws DPServiceException;
	String updateStockQty(List list) throws DPServiceException;
	List<NSBulkUploadDto> getALLStockLevelList()throws DPServiceException;

}
