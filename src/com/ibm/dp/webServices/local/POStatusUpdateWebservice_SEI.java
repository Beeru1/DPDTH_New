package com.ibm.dp.webServices.local;


public interface POStatusUpdateWebservice_SEI extends java.rmi.Remote
{
  public java.lang.String processDeliveryChallan(java.lang.String prNo,java.lang.String poNo,java.lang.String poDate,java.lang.String poStatusDate,java.lang.String invoiceNo,java.lang.String invoiceDate,java.lang.String dcNo,java.lang.String dcDate,java.lang.String ddChequeNo,java.lang.String ddChequeDate,java.lang.String productId,java.lang.String serialNo,java.lang.String userid,java.lang.String password);
  public java.lang.String poStatusUpdate(java.lang.String poNo,java.lang.String poStatus,java.lang.String poStatusTime,java.lang.String remarks,java.lang.String productCode,java.lang.String poQuantity,java.lang.String userid,java.lang.String password);
}