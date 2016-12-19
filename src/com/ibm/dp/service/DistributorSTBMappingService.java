package com.ibm.dp.service;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DistributorSTBMappingService {
	
	String checkOLMId(String distOLMId)throws VirtualizationServiceException;
	String[] checkMapping(String distId, String stbNo,String distOLMId,int requestType,String productName)throws VirtualizationServiceException;
	String[] avRestrictionCheck(String distId, String avSerialNumber)throws VirtualizationServiceException;
	String[] avRestrictionCheck2(String avSerialNumber, String distOLMId) throws VirtualizationServiceException ;
	String checkSameOlmId(String distId, String stbNo, String andrdStbNo) throws VirtualizationServiceException;
	
	String[] checkMappingForAndroid(String distId, String stbNo,
			String andrdStbNo, String distOLMId, int requestType,
			String andrdProdName, String productName) throws VirtualizationServiceException;

}
