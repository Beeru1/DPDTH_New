package com.ibm.dp.service;

import java.util.Map;

import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;

public interface DPCommonService {
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DAOException 
	 * @throws DPDPServiceException
	 * 
	 * This method will provide the Minimum stock eligibility of distributor for a particular product
	 * 
	 */
	public Integer getDistStockEligibilityMin(Integer intDistributorID, Integer intProductID)throws DPServiceException, DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DPServiceException
	 * 
	 * This method will provide the Maximum stock eligibility of distributor for a particular product
	 * @throws DAOException 
	 * 
	 */
	public Integer getDistStockEligibilityMax(Integer intDistributorID, Integer intProductID)throws DPServiceException, DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DPServiceException
	 * 
	 * This method will provide the Minimum and Maximum stock eligibility of distributor for a particular product
	 * 
	 * The return parameter is an array where Minimum Stock Eligibility will be on 0 index and
	 * Minimum Stock Eligibility will be on 1 index  
	 * @throws DAOException 
	 * 
	 */
	public Integer[] getDistStockEligibilityMinMax(Integer intDistributorID, Integer intProductID)throws DPServiceException, DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @param intProductID
	 * @return
	 * @throws DPServiceException
	 * 
	 * This method will provide the Total Stock of distributor for a particular product
	 * 
	 * Total Stock = Damaged Pending Stock (Including pending for acceptance by WH in DC) 
	 * 				+ Serialized Stock (Non-Activated) + Non Serialized Stock + Pending PO stock which is dispatched by WH
	 * 				+ Churn Pending Stock (Including pending for acceptance by WH in DC).
	 * @throws DAOException 
	 */
	public Integer getDistTotalStock(Integer intDistributorID, Integer intProductID)throws DPServiceException, DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @return
	 * @throws DPServiceException
	 * 
	 * This method will provide the Total Stock of distributor.
	 * 
	 * Total Stock = Damaged Pending Stock (Including pending for acceptance by WH in DC) 
	 * 				+ Serialized Stock (Non-Activated) + Non Serialized Stock + Pending PO stock which is dispatched by WH
	 * 				+ Churn Pending Stock (Including pending for acceptance by WH in DC).
	 * @throws DAOException 
	 */
	public Map<Integer, Integer> getDistTotalStock(Integer intDistributorID)throws DPServiceException, DAOException;
	
	/**
	 * @author Nazim Hussain
	 * @param intDistributorID
	 * @return
	 * @throws DPServiceException
	 * 
	 * This method will provide the Total Stock of distributor.
	 * 
	 * Total Stock = Damaged Pending Stock (Including pending for acceptance by WH in DC) 
	 * 				+ Serialized Stock (Non-Activated) + Non Serialized Stock + Pending PO stock which is dispatched by WH
	 * 				+ Churn Pending Stock (Including pending for acceptance by WH in DC).
	 * @throws DAOException 
	 */
	public Double getDistTotalStockEffectPrice(Integer intDistributorID, Integer intProductID)throws DPServiceException, DAOException;
	
	public Map<Integer, Double> getDistTotalStockEffectPrice(Integer intDistributorID)throws DPServiceException, DAOException;
	
	public Map<Integer, Double> getDistTotalStockEffectPriceAll(Integer intDistributorID)throws DPServiceException, DAOException;

	
	public Double getProductEffectPrice(Integer intProductID)throws DPServiceException, DAOException;
	
	public Integer getDistMaxPOQty(Integer intDistributorID, Integer intProductID)throws DPServiceException, DAOException;
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method is used to validate OLM ID from HRMS
	 * 
	 * @param strOLMID
	 * @return
	 * @throws DPServiceException
	 */
	public String isValidOLMID(String strOLMID)throws DPServiceException;
	//Neetika 25-07-2014 BFR2 
	public Double getNewUploadedBalance(Integer intDistributorID,int productId) throws  DAOException; 
	public int getUploadedEligibility(Integer intDistributorID,int productId) throws  DAOException; 
}
