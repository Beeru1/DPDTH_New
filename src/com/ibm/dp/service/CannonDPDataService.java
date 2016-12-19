package com.ibm.dp.service;

import java.sql.Date;

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface CannonDPDataService {

	String updateCannonData(String recId, String serialNo, String distOlmId, String itemCode, String assignedDate, String customerId, String status, String stbType, String transactionType, String modelManKey, String modelManKeyOld, String userId,String password)throws VirtualizationServiceException;
	String updateCannonInventory(String recordId,String defectiveSerialNo,String defectiveStbType,String newSerialNo,String newStbType,String inventoryChangeDate,String inventoryChangeType,String distributorOlmId,String status,String errorMsg,String customerId,int  defectId,String modelManKey,String modelManKeyOld,String userid,String password)throws VirtualizationServiceException;

}
