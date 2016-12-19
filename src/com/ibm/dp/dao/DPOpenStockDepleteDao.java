package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DPOpenStockDepletionDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPOpenStockDepleteDao 
{
	/**
	 * @author Nazim Hussain
	 * 
	 * This method is used to deplete the open stock quantity(productQuantity) of a product(productCode) 
	 * from its existing quantity for a distributor(distributorCode)
	 * 
	 * @param distributorCode
	 * @param productCode
	 * @param productQuantity
	 * @return
	 * @throws DAOException
	 */
	
	String depleteOpenStockDao(String distributorCode, String productCode, int productQuantity) throws DAOException;

	/**
	 * @author Nazim Hussain
	 * This method is used to get the Initial Data on Open Stock Deplete Screen
	 *
	 * @param intCircleID
	 * @return
	 * @throws DAOException
	 */
	List<List> getOpenStockDepleteInitDataDao(int intCircleID) throws DAOException;

	/**
	 * @author Nazim Hussain
	 * This method is used to get the distributors belonging to a TSM
	 * 
	 * @param intTSMID
	 * @param intCircleID
	 * @return
	 * @throws DAOException
	 */
	List<DPOpenStockDepletionDTO> filterDitributorsDao(Integer intTSMID, Integer intCircleID) throws DAOException;
	
	/**
	 * @author Nazim Hussain
	 * This method is used to get the Open Stock balance of a product for a distributor 
	 * 
	 * @param intDistID
	 * @param intProductID
	 * @return
	 * @throws DAOException
	 */
	Integer getOpenStockBalanceDao(Integer intDistID, Integer intProductID) throws DAOException;

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
	 * @throws DAOException
	 */
	List<List> depleteOpenStockDao(long loginUserId,Integer intCircleID, Integer intDistID, Integer intProdID, Integer intDepleteStock) throws DAOException; 

}
