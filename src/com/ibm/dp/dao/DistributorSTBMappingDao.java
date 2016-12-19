package com.ibm.dp.dao;

import com.ibm.virtualization.recharge.exception.DAOException;

public interface DistributorSTBMappingDao {

	String checkOLMId(String distOLMId) throws DAOException;
	String[] checkMapping(String distId, String stbNo,String distOLMId,int requestType,String productName)throws DAOException;
	String[] avRestrictionCheck(String avSerialNumber, String distOLMId)throws DAOException;
	String checkLapuMobileNO(String lapuMobileNo) throws DAOException;
	String[] avRestrictionCheck2(String avSerialNumber, String distOLMId) throws DAOException ;
	String checkSameOlmId(String distId, String stbNo, String andrdStbNo);
	
	String[] checkMappingForAndroid(String distId, String stbNo,
			String andrdStbNo, String distOLMId, int requestType,
			String andrdProdName ,String productName) throws DAOException;
	
}
