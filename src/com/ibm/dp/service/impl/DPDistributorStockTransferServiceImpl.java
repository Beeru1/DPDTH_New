package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPDistributorStockTransferFormBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPDistributorStockTransferDao;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPDistributorStockTransferService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPDistributorStockTransferServiceImpl implements DPDistributorStockTransferService 
{
	Logger logger = Logger.getLogger(DPDistributorStockTransferService.class.getName());
	
	public List<CircleDto> getAllCircleList() throws DPServiceException
	{
		logger.info("********************** getAllCircleList() **************************************");
		Connection connection = null;
		List<CircleDto> circleListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDistributorStockTransferDao interSSDDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitDistStockTransferDao(connection);
			
			circleListDTO =  interSSDDao.getAllCircleList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return circleListDTO;
		
		
	}
	
	
	
	public List<List> getInitDistStockTransfer(int intCircleID,int intAccountZoneID) throws DPServiceException 
	{
		java.sql.Connection connection = null;
		List<List> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDistributorStockTransferDao dpDSTDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitDistStockTransferDao(connection);
			
			listReturn = dpDSTDao.getInitDistStockTransferDao(intCircleID, intAccountZoneID);
			
			if(listReturn.size()==0)
			{
				logger.error("No record found for Distributor Stock Transfer");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for Distributor Stock Transfer************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}
	
	public int getAvailableStock(Integer intDistID, Integer intProdID) throws DPServiceException
	{
		java.sql.Connection connection = null;
		int intReturn = 0;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDistributorStockTransferDao dpDSTDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAvailableStockDao(connection);
			
			intReturn = dpDSTDao.getAvailableStockDao(intDistID, intProdID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching Available Stock of a Product************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return intReturn;
	}

	public List<List> transferDistStock(DPDistributorStockTransferFormBean formBeanDST, long loginUserId, 
			int intCircleID, int intAccountZoneID) throws DPServiceException
	{
		java.sql.Connection connection = null;
		List<List> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDistributorStockTransferDao dpDSTDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).transferDistStockDao(connection);
			
			Map<String, String[]> tranferedDistStock = new HashMap<String, String[]>();
			
			tranferedDistStock.put(Constants.DST_FROM_DIST_ARRAY, formBeanDST.getArrFromDistID());
			tranferedDistStock.put(Constants.DST_TO_DIST_ARRAY, formBeanDST.getArrToDistID());
			tranferedDistStock.put(Constants.DST_PRODUCT_ARRAY, formBeanDST.getArrProductID());
			tranferedDistStock.put(Constants.DST_TRANS_QTY_ARRAY, formBeanDST.getArrTransQty());
			
			listReturn = dpDSTDao.transferDistStockDao(tranferedDistStock, loginUserId, intCircleID, intAccountZoneID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while Saving Transfered Distributor Stock ***********"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}
	
	public List<List<CircleDto>> getInitData(long loginUserId) throws DPServiceException
	{
		logger.info("********************** getInitData() **************************************");
		Connection connection = null;
		List<List<CircleDto>> retListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDistributorStockTransferDao interSSDDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitDistStockTransferDao(connection);
			
			retListDTO =  interSSDDao.getInitData(loginUserId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return retListDTO;
}
	public int validateToDist(Integer intFromDistID, Integer intToDistID) throws DPServiceException
	{
		java.sql.Connection connection = null;
		int intReturn = 0;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDistributorStockTransferDao dpDSTDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAvailableStockDao(connection);
			
			intReturn = dpDSTDao.validateToDistDao( intFromDistID, intToDistID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching Available Stock of a Product************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return intReturn;
	}



	public List getDistAccountDetails(int intTSMID, int circleId, int intBusCat) throws DPServiceException {
		Connection connection=null;
		List<StockSummaryReportBean> revLogDistAccDetailList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDistributorStockTransferDao dpDSTDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAvailableStockDao(connection);
			
			revLogDistAccDetailList = dpDSTDao.getDistAccountDetailsDAO(intTSMID , circleId, intBusCat);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return revLogDistAccDetailList;
	}
	
	
	public boolean checkcombination(String fromDistID, Integer intProdID,String toDistid) throws DPServiceException
	{
		java.sql.Connection connection = null;
		boolean intReturn = false;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDistributorStockTransferDao dpDSTDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAvailableStockDao(connection);
			
			intReturn = dpDSTDao.checkcombination(fromDistID,intProdID,toDistid);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching Available Stock of a Product************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return intReturn;
	}
}