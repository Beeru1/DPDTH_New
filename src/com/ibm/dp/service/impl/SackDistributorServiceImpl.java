package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.SackDistributorDao;
import com.ibm.dp.dto.SackDistributorDetailDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.SackDistributorService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class SackDistributorServiceImpl implements SackDistributorService {
	
Logger logger = Logger.getLogger(SackDistributorServiceImpl.class.getName());
	
	public List<TSMDto> getTSMMaster(String parentId) throws DPServiceException
	{
		logger.info("********************** getTSMMaster() **************************************");
		Connection connection = null;
		List<TSMDto> tsmListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			SackDistributorDao sackDistDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSackDistributorDao(connection);
			
			tsmListDTO =  sackDistDao.getTSMMaster(parentId);
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
		return tsmListDTO;
		
		
	}
	
	public List<SackDistributorDetailDto> getDistDetailList(String tsmId,String circleId) throws VirtualizationServiceException, DPServiceException
	{
		logger.info("********************** getTSMMaster() **************************************");
		Connection connection = null;
		List<SackDistributorDetailDto> tsmListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			SackDistributorDao sackDistDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSackDistributorDao(connection);
			
			tsmListDTO =  sackDistDao.getDistDetailList(tsmId,circleId);
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
		return tsmListDTO;
		
		
	}

	public String sackDistributor(ListIterator<SackDistributorDetailDto> distDetailDtoListItr) throws VirtualizationServiceException ,DPServiceException{
		logger.info("********************** getDistStockCount() **************************************");
		Connection connection = null;
		String strMsg ="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			SackDistributorDao sackDistDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSackDistributorDao(connection);
			
			strMsg =  sackDistDao.sackDistributor(distDetailDtoListItr);
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
		return strMsg;
	}
}
