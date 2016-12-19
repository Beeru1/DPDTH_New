package com.ibm.dp.service;

import java.io.InputStream;

import com.ibm.dp.exception.DPServiceException;


public interface C2SBulkUploadService {

	String uploadExcel(InputStream inputstream) throws DPServiceException;
	
	
}
