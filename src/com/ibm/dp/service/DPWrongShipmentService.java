package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPWrongShipmentService 
{
	public List<WrongShipmentDTO> getListOfRejectedDCNo(Long distId , String dc_status) throws VirtualizationServiceException;
	public List<WrongShipmentDTO> getAllSerialNos(String invoiceNo, String dcNo, Integer intCircleID) throws VirtualizationServiceException;
	public List<WrongShipmentDTO> getAllProducts(String invoiceNo, String dcNo) throws VirtualizationServiceException;
	public boolean submitWrongShipmentDetails(String[] availableSerialsArray, String[] shortSerialsArray, String[] extraSerialsArray, String invoiceNo, String deliveryChallanNo , String distId) throws VirtualizationServiceException;
	public String getDeliveryChallanNo(String invoiceNo) throws VirtualizationServiceException;
	public String getInvoiceNoOfDCNO(String dcNo) throws VirtualizationServiceException;
	public String checkErrorDC(String dcNo) throws VirtualizationServiceException;
	
}
