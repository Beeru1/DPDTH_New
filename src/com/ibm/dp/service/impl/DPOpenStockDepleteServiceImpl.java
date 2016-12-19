package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPOpenStockDepleteDao;
import com.ibm.dp.dto.DPOpenStockDepletionDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPOpenStockDepleteService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPOpenStockDepleteServiceImpl implements DPOpenStockDepleteService 
{
	Logger logger = Logger.getLogger(DPOpenStockDepleteServiceImpl.class.getName());

	public String depleteOpenStock(String distributorCode, String productCode, int productQuantity)
	throws VirtualizationServiceException 
	{
		logger.info("*********************depleteOpenStock method called********************");
		
		String strServiceMsg = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPOpenStockDepleteDao depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).depleteOpenStockDao(connection);
			
			strServiceMsg = depleteDao.depleteOpenStockDao(distributorCode, productCode, productQuantity);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strServiceMsg;
	}

	public List<List> getOpenStockDepleteInitData(int intCircleID) throws DPServiceException 
	{
		logger.info("*********************getOpenStockDepleteInitData method called********************");
		
		Connection connection = null;
		List<List> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPOpenStockDepleteDao depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getOpenStockDepleteInitDataDao(connection);
			
			listReturn = depleteDao.getOpenStockDepleteInitDataDao(intCircleID);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}

	public List<DPOpenStockDepletionDTO> filterDitributors(Integer intTSMID, Integer intCircleID) throws DPServiceException 
	{
		logger.info("*********************filterDitributors method called********************");
		
		Connection connection = null;
		List<DPOpenStockDepletionDTO> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPOpenStockDepleteDao depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).filterDitributorsDao(connection);
			
			listReturn = depleteDao.filterDitributorsDao(intTSMID, intCircleID);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}

	public Integer getOpenStockBalance(Integer intDistID, Integer intProductID) throws DPServiceException 
	{
		logger.info("*********************getOpenStockBalance method called* 1111111111*******************");
		
		Connection connection = null;
		Integer intReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			//DPOpenStockDepleteDao depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getOpenStockBalanceDao(connection);
			DPOpenStockDepleteDao depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).depleteOpenStockDao(connection);
			intReturn = depleteDao.getOpenStockBalanceDao(intDistID, intProductID);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return intReturn;
	}

	public List<List> depleteOpenStock(long loginUserId, Integer intCircleID , Integer intDistID, Integer intProdID, Integer intDepleteStock) throws DPServiceException 
	{
		logger.info("*********************depleteOpenStock method called********************");
		
		Connection connection = null;
		List<List> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPOpenStockDepleteDao depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).depleteOpenStockDao(connection);
			
			listReturn = depleteDao.depleteOpenStockDao(loginUserId, intCircleID, intDistID, intProdID, intDepleteStock);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}

}
