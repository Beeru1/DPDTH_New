package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.beans.DPDistributorStockTransferFormBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;

public interface DPDistributorStockTransferService 
{
	/**
	 * This method is used to get the initial data for Distributor Stock Transfer
	 * 
	 * @param intCircleID
	 * @param intAccountZoneID
	 * @return
	 * @throws DPServiceException
	 */
	public List<List> getInitDistStockTransfer(int intCircleID,int intAccountZoneID) throws DPServiceException;

	/**
	 * This method is used to get the available stock corresponding to a Product for a Distributor
	 * 
	 * @param intDistID
	 * @param intProdID
	 * @return
	 * @throws DPServiceException
	 */
	public int getAvailableStock(Integer intDistID, Integer intProdID) throws DPServiceException;
	
	/**
	 * This method is used to save the Transfered data for Distributor Stock
	 * @param formBeanDST
	 * @param loginUserId
	 * @param intCircleID
	 * @param intAccountZoneID
	 * @return
	 */
	public List<List> transferDistStock(DPDistributorStockTransferFormBean formBeanDST, long loginUserId, 
			int intCircleID, int intAccountZoneID) throws DPServiceException;
	
	List<CircleDto> getAllCircleList() throws DPServiceException;

	public List<List<CircleDto>> getInitData(long loginUserId)throws DPServiceException ;
	
	public int validateToDist(Integer intFromDistID, Integer intToDistID) throws DPServiceException;

	public List getDistAccountDetails(int intTSMID, int circleId, int intBusCat)throws DPServiceException;
	public boolean checkcombination(String fromDistID, Integer intProdID,String toDistid) throws DPServiceException;
	
}
