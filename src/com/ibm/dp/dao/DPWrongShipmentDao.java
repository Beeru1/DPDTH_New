package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.actions.DPStockDetailReport;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPWrongShipmentDao 
{

	public List<WrongShipmentDTO> getListOfRejectedDCNo(Long distId , String dc_status) throws DAOException;
	public List<WrongShipmentDTO> getAllSerialNos(String invoiceNo, String dcNo, Integer intCircleID) throws DAOException;
	public List<WrongShipmentDTO> getAllProducts(String invoiceNo, String dcNo) throws DAOException;
	public boolean submitWrongShipmentDetails(String[] availableSerialsArray, String[] shortSerialsArray, String[] extraSerialsArray, String invoiceNo, String deliveryChallanNo , String distIdS) throws DAOException;
	public String getDeliveryChallanNo(String invoiceNo) throws DAOException;
	public String getInvoiceNoOfDCNO(String dcNo) throws DAOException;
	public String checkWrongInventory(String extraSerialNo, String productID, String distId, String deliveryChallanNo) throws VirtualizationServiceException;
	public String checkErrorDC(String dcNo) throws DAOException;
	
	
}
