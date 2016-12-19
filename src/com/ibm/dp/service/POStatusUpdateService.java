package com.ibm.dp.service;


import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface POStatusUpdateService {	
	String updateStatus(String poNo, String poStatus, String poStatusTime, String remarks , String productCode, String poQuantity, String userid, String password)throws VirtualizationServiceException;
	String updateDeliveryChallan(String prNo, String poNo, String poDate,String poStatusDate , String invoiceNo,String invoiceDate, String dcNo, String dcDate, String ddChequeNo , String ddChequeDate,String productId,String serialNo, String userid, String password)throws VirtualizationServiceException;
}
