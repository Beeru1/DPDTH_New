package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.exception.DPServiceException;


public interface DPStockLevelBulkUploadService {

	List uploadExcel(InputStream inputstream) throws DPServiceException;
	String addStockLevel(List list) throws DPServiceException;
	List<DPStockLevelBulkUploadDto> getALLStockLevelList() throws DPServiceException;
}
