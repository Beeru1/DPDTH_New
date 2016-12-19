package com.ibm.dp.dao;

import java.util.ArrayList;

import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPDeliveryChallanAcceptDao 
{
	/**
	 * This method is used to get List of all Delivery challan for Acceptance
	 * 
	 * @param loginUserId
	 * @return
	 * @throws DAOException
	 */
	ArrayList<DPDeliveryChallanAcceptDTO> getInitDeliveryChallanDao(long loginUserId)throws DAOException;

	/**
	 * This method is used to accept/reject the Delivery challans
	 * 
	 * @param arrCheckedDC
	 * @param dc_report_accept
	 * @param loginUserId
	 * @return
	 * @throws DAOException
	 */
	ArrayList<DPDeliveryChallanAcceptDTO> reportDeliveryChallanDao(String[] arrCheckedDC, String dc_report_accept, long loginUserId)
	throws DAOException;
	
	/**
	 * This method is used to get List of all serials no of Delivery challan
	 * 
	 * @param invoiceNo
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfDeliveryChallan(String invoiceNo) throws DAOException;
	
	/**
	 * This method is used to get List of all serials no of Delivery challan
	 * 
	 * @param invoiceNo
	 * @ productId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfPOStatus(String invoiceNo,String ProductId,String circleId) throws DAOException;
	
	
	
}
