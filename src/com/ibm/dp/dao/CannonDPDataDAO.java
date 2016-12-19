package com.ibm.dp.dao;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface CannonDPDataDAO {

	String updateCannonData(String recId, String serialNo, String distOlmId, String itemCode, String assignedDate, String customerId, String status, String stbType, String transactionType, String modelManKey, String modelManKeyOld, String userId,String password)throws DAOException;
	String updateCannonInventory(String recordId,String defectiveSerialNo,String defectiveStbType,String newSerialNo,String newStbType,String inventoryChangeDate,String inventoryChangeType,String distributorOlmId,String status,String errorMsg,String customerId,int  defectId,String modelManKey,String modelManKeyOld,String userid,String password)throws DAOException;

}
