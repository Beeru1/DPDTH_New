package com.ibm.dp.webServices.local;


public interface CanonDPDataWebservice_SEI extends java.rmi.Remote
{
  public java.lang.String canonDataUpdate(java.lang.String recId,java.lang.String serialNo,java.lang.String distOlmId,java.lang.String itemCode,java.lang.String assignedDate,java.lang.String customerId,java.lang.String status,java.lang.String stbType,java.lang.String transactionType,java.lang.String modelManKey,java.lang.String modelManKeyOld,java.lang.String userId,java.lang.String password);
  public java.lang.String updateCannonInventory(java.lang.String recordId,java.lang.String defectiveSerialNo,java.lang.String defectiveStbType,java.lang.String newSerialNo,java.lang.String newStbType,java.lang.String inventoryChangeDate,java.lang.String inventoryChangeType,java.lang.String distributorOlmId,java.lang.String status,java.lang.String errorMsg,java.lang.String customerId,int defectId,java.lang.String modelManKey,java.lang.String modelManKeyOld,java.lang.String userid,java.lang.String password);
}