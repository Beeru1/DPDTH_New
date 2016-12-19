package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.dp.exception.DPServiceException;

public interface DistAvStockUploadService 
{
	List uploadExcel(FormFile file, int intCatCode)throws DPServiceException;
	public String insertAVStockinDP(List list, int intCatCode, long lnDistID, int intCircleID, int intProductID) throws DPServiceException ;
	List<List<CircleDto>> getInitData(int intCircleID)throws DPServiceException ;
	//added by aman
	public List<NonSerializedToSerializedDTO> getSerializedConversion(String strOLMId) throws DPServiceException;
	List<NonSerializedToSerializedDTO> getALLProdSerialData(String circleId)throws DPServiceException;
	List uploadExcel(InputStream myxls, String distOlmID, String circleId)throws DPServiceException;
	String convertNserToSerStock(List list, String distOlmID, String circleId)throws DPServiceException;
}