package com.ibm.dp.dao;

import java.sql.Connection;
import java.util.Map;

import com.ibm.dp.exception.DAOException;


public interface DPCommonDAO 
{
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DPDAOException
	 * 
	 * This method will provide the Minimum stock eligibility of distributor for a particular product
	 * 
	 */
	public Integer getDistStockEligibilityMin(Integer intDistributorID, Integer intProductID)throws DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DAOException
	 * 
	 * This method will provide the Maximum stock eligibility of distributor for a particular product
	 * 
	 */
	public Integer getDistStockEligibilityMax(Integer intDistributorID, Integer intProductID)throws DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DAOException
	 * 
	 * This method will provide the Minimum and Maximum stock eligibility of distributor for a particular product
	 * 
	 * The return parameter is an array where Minimum Stock Eligibility will be on 0 index and
	 * Minimum Stock Eligibility will be on 1 index  
	 * 
	 */
	public Integer[] getDistStockEligibilityMinMax(Integer intDistributorID, Integer intProductID)throws DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DAOException
	 * 
	 * This method will provide the Total Stock of distributor for a particular product
	 * 
	 * Total Stock = Damaged Pending Stock (Including pending for acceptance by WH in DC) 
	 * 				+ Serialized Stock (Non-Activated) + Non Serialized Stock + Pending PO stock which is dispatched by WH
	 * 				+ Churn Pending Stock (Including pending for acceptance by WH in DC).
	 */
	public Integer getDistTotalStock(Integer intDistributorID, Integer intProductID)throws DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @return
	 * @throws DAOException
	 * 
	 * This method will provide the Total Stock of distributor.
	 * 
	 * Total Stock = Damaged Pending Stock (Including pending for acceptance by WH in DC) 
	 * 				+ Serialized Stock (Non-Activated) + Non Serialized Stock + Pending PO stock which is dispatched by WH
	 * 				+ Churn Pending Stock (Including pending for acceptance by WH in DC).
	 */
	public Map<Integer, Integer> getDistTotalStock(Integer intDistributorID)throws DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @return
	 * @throws DAOException
	 * 
	 * This method will provide the Total Stock of distributor.
	 * 
	 * Total Stock = Damaged Pending Stock (Including pending for acceptance by WH in DC) 
	 * 				+ Serialized Stock (Non-Activated) + Non Serialized Stock + Pending PO stock which is dispatched by WH
	 * 				+ Churn Pending Stock (Including pending for acceptance by WH in DC).
	 */
	public Double getDistTotalStockEffectPrice(Integer intDistributorID, Integer intProductID)throws DAOException;
	
	public Map<Integer, Double> getDistTotalStockEffectPrice(Integer intDistributorID)throws DAOException;
	
	public Map<Integer, Double> getDistTotalStockEffectPriceAll(Integer intDistributorID)throws DAOException;
	
	public Double getProductEffectPrice(Integer intProductID)throws DAOException;
	
	public Integer getDistMaxPOQty(Integer intDistributorID, Integer intProductID)throws DAOException;
	
	public int getMinDays(Integer intDistributorID, Integer intProductID,Connection con)throws DAOException;
	public int getMaxDays(Integer intDistributorID, Integer intProductID,Connection con)throws DAOException;
	
	public String isValidOLMIDDao(String strOLMID, Connection con)throws DAOException;
//Neetika BFR 2 28 July 2014
	public Double getNewUploadedBalance(Integer intDistributorID,int productId) throws DAOException;
	public int getUploadedEligibility(Integer intDistributorID,int productId) throws DAOException;
}
