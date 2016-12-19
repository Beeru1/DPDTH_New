package com.ibm.dp.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.exception.DPServiceException;

public interface AvStockUploadService 
{
	List uploadExcel(FormFile file)throws DPServiceException;
	public String insertAVStockinDP(List list) throws DPServiceException ;
}