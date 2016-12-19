package com.ibm.dp.service.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DistributorSTBMappingDao;
import com.ibm.dp.service.DistributorSTBMappingService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DistributorSTBMappingServiceImpl implements DistributorSTBMappingService{

	Logger logger = Logger.getLogger(DistributorSTBMappingServiceImpl.class.getName());

	public String checkOLMId(String distOLMId) throws VirtualizationServiceException 
	{
		logger.info("*********************checkOLMId method called********************");
		
		String strDistId = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistributorSTBMappingDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistSTBMapDao(connection);
			
			strDistId = stbDao.checkOLMId(distOLMId);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			strDistId = "-1";
			logger.error("ERROR IN DIST STB WEBSERVICE  ::  "+e.getMessage());
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strDistId;
	}
	
	public String[] checkMapping(String distId, String stbNo,String distOLMId,int requestType,String product) throws VirtualizationServiceException 
	{
		logger.info("*********************checkMapping method called********************");
		
		//String strServiceMsg = "SUCCESS";
		String[] strServiceMsg = new String[2];
		strServiceMsg[0]="SUCCESS";
		strServiceMsg[0]="";
		Connection connection = null;
		
		try
		{

			connection = DBConnectionManager.getDBConnection();
			
			DistributorSTBMappingDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistSTBMapDao(connection);
			
			strServiceMsg = stbDao.checkMapping(distId,stbNo,distOLMId,requestType,product);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			strServiceMsg[0] = "OTHERS";
			strServiceMsg[1] = "";
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strServiceMsg;
	}

	
	
	public String[] checkMappingForAndroid(String distId, String stbNo,
			String andrdStbNo, String distOLMId, int requestType,
			String andrdProdName, String productName) throws VirtualizationServiceException 
	{
		logger.info("*********************checkMapping method called********************");
		
		//String strServiceMsg = "SUCCESS";
		String[] strServiceMsg = new String[2];
		strServiceMsg[0]="SUCCESS";
		strServiceMsg[1]="";
		Connection connection = null;
		
		try
		{

			connection = DBConnectionManager.getDBConnection();
			
			DistributorSTBMappingDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistSTBMapDao(connection);
			
			strServiceMsg = stbDao.checkMappingForAndroid( distId,  stbNo,andrdStbNo,  distOLMId, requestType,andrdProdName ,productName);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			strServiceMsg[0] = "OTHERS";
			strServiceMsg[1] = "";
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strServiceMsg;
	}

	
	
	
	public String[] avRestrictionCheck(String avSerialNumber, String distOLMId) throws VirtualizationServiceException 
	{
		logger.info("*********************avRestrictionCheck method called********************");
		
		//String strServiceMsg = "SUCCESS";
		String[] strServiceMsg = new String[2];
		strServiceMsg[0]="SUCCESS";
		strServiceMsg[0]="";
		Connection connection = null;
		
		try
		{

			connection = DBConnectionManager.getDBConnection();
			
			DistributorSTBMappingDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistSTBMapDao(connection);
			
			strServiceMsg = stbDao.avRestrictionCheck(avSerialNumber, distOLMId);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			strServiceMsg[0] = "OTHERS";
			strServiceMsg[1] = "";
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strServiceMsg;
	}
	
	
	public String[] avRestrictionCheck2(String avSerialNumber, String distOLMId) throws VirtualizationServiceException 
	{
		logger.info("*********************avRestrictionCheck2 method called********************");
		
		//String strServiceMsg = "SUCCESS";
		String[] strServiceMsg = new String[2];
		strServiceMsg[0]="SUCCESS-Secondary Sales done for this Serial Number";
		strServiceMsg[1]="";
		Connection connection = null;
		
		try
		{

			connection = DBConnectionManager.getDBConnection();
			
			DistributorSTBMappingDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistSTBMapDao(connection);
			
			strServiceMsg = stbDao.avRestrictionCheck2(avSerialNumber, distOLMId);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			strServiceMsg[0] = "OTHERS";
			strServiceMsg[1] = "";
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strServiceMsg;
	}

	@Override
	public String checkSameOlmId(String distId, String stbNo,String andrdStbNo) throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		logger.info("*********************checkSameOlmId method called********************");
		
		//String strServiceMsg = "SUCCESS";

		String ID = "" ;
		Connection connection = null;
		
		try
		{

			connection = DBConnectionManager.getDBConnection();
			
			DistributorSTBMappingDao stbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistSTBMapDao(connection);
			
			ID = stbDao.checkSameOlmId(distId, stbNo,andrdStbNo);
			System.out.println("login ID :" + ID);
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
		
		return ID;
	
	}
	
	
	
	
	
	
	
	
	

	
}
