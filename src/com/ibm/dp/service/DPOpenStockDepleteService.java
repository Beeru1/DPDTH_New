package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DPOpenStockDepletionDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPOpenStockDepleteService 
{
	/**
	 * @author Nazim Hussain
	 * This method is used to deplete the open stock quantity(productQuantity) of a product(productCode) 
	 * from its existing quantity for a distributor(distributorCode)
	 * 
	 * @param distributorCode
	 * @param productCode
	 * @param productQuantity
	 * @return
	 * @throws VirtualizationServiceException
	 */
	String depleteOpenStock(String distributorCode, String productCode, int productQuantity)throws VirtualizationServiceException;

	/**
	 * @author Nazim Hussain
	 * This method is used to get the Initial Data on Open Stock Deplete Screen
	 * 
	 * @param intCircleID
	 * @return
	 * @throws DPServiceException
	 */
	List<List> getOpenStockDepleteInitData(int intCircleID) throws DPServiceException;
	
	/**
	 * @author Nazim Hussain
	 * This method is used to get the distributors belonging to a TSM
	 *
	 * @param intTSMID
	 * @param intCircleID
	 * @return
	 * @throws DPServiceException
	 */
	List<DPOpenStockDepletionDTO> filterDitributors(Integer intTSMID, Integer intCircleID) throws DPServiceException;

	/**
	 * @author Nazim Hussain
	 * This method is used to get the Open Stock balance of a product for a distributor 
	 *
	 * @param intDistID
	 * @param intProductID
	 * @return
	 * @throws DPServiceException
	 */
	Integer getOpenStockBalance(Integer intDistID, Integer intProductID) throws DPServiceException;

	/**
	 * @author Nazim Hussain
 	 * This method is used to deplete the open stock quantity(intDepleteStock) of a product(intProdID) 
	 * from its existing quantity for a distributor(intDistID) 
	 *
	 * @param loginUserId
	 * @param intCircleID
	 * @param intDistID
	 * @param intProdID
	 * @param intDepleteStock
	 * @return
	 * @throws DPServiceException
	 */
	List<List> depleteOpenStock(long loginUserId, Integer intCircleID, Integer intDistID, Integer intProdID, Integer intDepleteStock) throws DPServiceException;

}
