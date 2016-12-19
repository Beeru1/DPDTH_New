package com.ibm.dp.dao;

import com.ibm.virtualization.recharge.exception.DAOException;

public interface POStatusUpdateDao {
	String updateStatus(String poNo, String poStatus, String poStatusTime, String remarks, String productCode, String poQuantity, String userid, String password)throws DAOException;
	String updateDeliveryChallan(String prNo, String poNo, String poDate,String poStatusDate , String invoiceNo,String invoiceDate, String dcNo, String dcDate, String ddChequeNo , String ddChequeDate,String productId,String serialNo, String userid, String password)throws DAOException;
}
