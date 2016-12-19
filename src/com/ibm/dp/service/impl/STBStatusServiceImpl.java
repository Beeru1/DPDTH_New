package com.ibm.dp.service.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;
import com.ibm.dp.dao.STBStatusDao;
import com.ibm.dp.service.STBStatusService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class STBStatusServiceImpl implements STBStatusService{
	Logger logger = Logger.getLogger(STBStatusServiceImpl.class.getName());

	public int checkSrNoAvailibility(String strNo)throws VirtualizationServiceException 
	{
		logger.info("*********************checkSrNoAvailibility method called********************");
		
		int strServiceMsg = 0;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			STBStatusDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSTBStatusDao(connection);
			
			strServiceMsg = stbDao.checkSrNoAvailibility(strNo);
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
	
	public String updateStatus(String strNos,String status)throws VirtualizationServiceException 
	{
		logger.info("*********************updateStatus method called********************");
		
		String strServiceMsg = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			STBStatusDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSTBStatusDao(connection);
			
			strServiceMsg = stbDao.updateStatus(strNos,status);
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

}
