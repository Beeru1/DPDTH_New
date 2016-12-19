package com.ibm.dp.service;

import java.util.ArrayList;

import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.exception.DPServiceException;

public interface DPDeliveryChallanAcceptService 
{
	/**
	 * This method can be used to get List of all Delivery challan for Acceptance
	 * 
	 * @param loginUserId
	 * @return
	 * @throws DPServiceException
	 */
	ArrayList<DPDeliveryChallanAcceptDTO> getInitDeliveryChallan(long loginUserId)throws DPServiceException;

	/**
	 * This method is used to ACCEPT/REJECT a delivery challan
	 * 
	 * @param arrCheckedDC
	 * @param dc_report_accept
	 * @param loginUserId
	 * @return
	 * @throws DPServiceException
	 */
	ArrayList<DPDeliveryChallanAcceptDTO> reportDeliveryChallan(String[] arrCheckedDC, String dc_report_accept,long loginUserId)
	throws DPServiceException;
	
	
	/**
	 * This method is used to get all Serials of given invoice a delivery challan
	 * @param invoiceNo
	 * @return
	 * @throws DPServiceException
	 */
	public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfDeliveryChallan(String invoiceNo) throws DPServiceException;
	/**
	 * This method is used to get all Serials of given invoice a delivery challan
	 * @param invoiceNo
	 * @return
	 * @throws DPServiceException
	 */
	public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfPOStatus(String invoiceNo,String ProductId,String circleId) throws DPServiceException;
	
}