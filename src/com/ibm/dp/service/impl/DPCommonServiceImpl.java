package com.ibm.dp.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.cpedp.DPHRMSDBConnection;
import com.ibm.dp.dao.DPCommonDAO;
import com.ibm.dp.dao.impl.DPCommonDAOImpl;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPCommonService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPCommonServiceImpl implements DPCommonService {
	private static Logger logger = Logger.getLogger(DPCommonServiceImpl.class.getName());
	
	private static DPCommonService commonService = new DPCommonServiceImpl();
	
	public DPCommonServiceImpl(){};
	public static DPCommonService getInstance() {
		return commonService;
	}
	
	DPCommonDAO commonDao = DPCommonDAOImpl.getInstance();
	
	public Integer getDistMaxPOQty(Integer intDistributorID, Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getDistMaxPOQty(intDistributorID, intProductID);
	}

	public Integer getDistStockEligibilityMax(Integer intDistributorID, Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getDistStockEligibilityMax(intDistributorID, intProductID);
	}

	public Integer getDistStockEligibilityMin(Integer intDistributorID, Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getDistStockEligibilityMin(intDistributorID, intProductID);
	}

	public Integer[] getDistStockEligibilityMinMax(Integer intDistributorID, Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getDistStockEligibilityMinMax(intDistributorID, intProductID);
	}

	public Integer getDistTotalStock(Integer intDistributorID, Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getDistTotalStock(intDistributorID, intProductID);
	}

	public Map<Integer, Integer> getDistTotalStock(Integer intDistributorID) throws DPServiceException, DAOException {
		return commonDao.getDistTotalStock(intDistributorID);	
	}

	public Double getDistTotalStockEffectPrice(Integer intDistributorID, Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getDistTotalStockEffectPrice(intDistributorID, intProductID);	
	}

	public Map<Integer, Double> getDistTotalStockEffectPrice(Integer intDistributorID) throws DPServiceException, DAOException {
		return commonDao.getDistTotalStockEffectPrice(intDistributorID);	
	}
	
	public Map<Integer, Double> getDistTotalStockEffectPriceAll(Integer intDistributorID) throws DPServiceException, DAOException {
		return commonDao.getDistTotalStockEffectPriceAll(intDistributorID);	
	}

	public Double getProductEffectPrice(Integer intProductID) throws DPServiceException, DAOException {
		return commonDao.getProductEffectPrice(intProductID);	
	}

	public String isValidOLMID(String strOLMID) throws DPServiceException 
	{
		java.sql.Connection connection = null;
		String strReturn = null;
		try
		{
			DPCommonDAO commonDAO = DPCommonDAOImpl.getInstance();
			
			connection = DPHRMSDBConnection.getDBConnectionHRMS();
			System.out.println("connection:::::::::::in service imple::::"+connection);
			strReturn = commonDAO.isValidOLMIDDao(strOLMID, connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while validating User OLM ID with HRMS  ::  "+e.getMessage());
			logger.info(e);
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strReturn;
	}
	//Added by neetika BFR 2 on 25-07-2014
	public Double getNewUploadedBalance(Integer intDistributorID,int productId) throws  DAOException {
		return commonDao.getNewUploadedBalance(intDistributorID,productId);	
	}
	
	public int getUploadedEligibility(Integer intDistributorID, int intProductID) throws  DAOException {
		return commonDao.getUploadedEligibility( intDistributorID,  intProductID);
	}
}