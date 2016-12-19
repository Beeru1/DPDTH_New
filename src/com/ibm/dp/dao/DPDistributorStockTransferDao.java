package com.ibm.dp.dao;

import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPDistributorStockTransferDao
{
	/**
	 * This method is used to get the initial data for Distributor Stock Transfer
	 * 
	 * @param intCircleID
	 * @param intAccountZoneID
	 * @return
	 * @throws DAOException
	 */
	List<List> getInitDistStockTransferDao(int intCircleID, int intAccountZoneID) throws DAOException;

	/**
	 * This method is used to get the available stock corresponding to a Product for a Distributor
	 * 
	 * @param intDistID
	 * @param intProdID
	 * @return
	 * @throws DAOException
	 */
	int getAvailableStockDao(Integer intDistID, Integer intProdID) throws DAOException;

	/**
	 * This method is used to save the Transfered data for Distributor Stock
	 * 
	 * @param tranferedDistStock
	 * @param loginUserId
	 * @param intCircleID
	 * @param intAccountZoneID
	 * @return
	 * @throws DAOException
	 */
	List<List> transferDistStockDao(Map<String, String[]> tranferedDistStock, long loginUserId, int intCircleID,
			int intAccountZoneID) throws DAOException;
	
	List<CircleDto> getAllCircleList() throws  DAOException ;

	List<List<CircleDto>> getInitData(long loginUserId)throws  DAOException ;
	
	int validateToDistDao(Integer intFromDistID, Integer intToDistID) throws DAOException;

	List<StockSummaryReportBean> getDistAccountDetailsDAO(int intTSMID, int circleId, int intBusCat)throws DAOException;

	boolean checkcombination(String fromDistID, Integer intProdID,String toDistid) throws DAOException;
}
