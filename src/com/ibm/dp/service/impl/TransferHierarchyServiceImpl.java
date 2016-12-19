package com.ibm.dp.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.TransferHierarchyDao;
import com.ibm.dp.dto.TransferHierarchyDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.TransferHierarchyService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class TransferHierarchyServiceImpl implements TransferHierarchyService 
{
	Logger logger = Logger.getLogger(TransferHierarchyServiceImpl.class.getName());
/*	
	@Override
	public List<TransferHierarchyDto> getChildUser(String userName)	throws DPServiceException 
	{
		java.sql.Connection connection = null;
		List<TransferHierarchyDto> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			TransferHierarchyDao hierTrnsDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getTransHierConnDao(connection);
			
			listReturn = hierTrnsDao.getChildUser(userName);
			
			if(listReturn.size()==0)
			{
				logger.error("No hierarchy found for transfer");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching child user********"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}
	
	public List<TransferHierarchyDto> getParentUser(String userName)	throws DPServiceException
	{
		java.sql.Connection connection = null;
		List<TransferHierarchyDto> listReturn = null;
		try
		{
			
			connection = DBConnectionManager.getDBConnection();
			TransferHierarchyDao hierTrnsDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getTransHierConnDao(connection);
			listReturn=hierTrnsDao.getParentUser(userName);
			
		
		if(listReturn.size()==0)
		{
			logger.error("No hierarchy found for transfer");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		logger.error("***********Exception occured while fetching parent user ************"+e.getMessage());
		throw new DPServiceException(e.getMessage());
	}
	finally
	{
		//Releasing the database connection
		DBConnectionManager.releaseResources(connection);
	}
	return listReturn;
	}
	*/
	
	public Map<String, List<TransferHierarchyDto>> getHierarchyDetails(	String userName) throws DPServiceException 
	{
		java.sql.Connection connection = null;
		Map<String, List<TransferHierarchyDto>> mapReturn = null;
		try
		{
			
			connection = DBConnectionManager.getDBConnection();
			TransferHierarchyDao hierTrnsDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getTransHierConnDao(connection);
			mapReturn = hierTrnsDao.getHierarchyDetailsDao(userName);
			if(mapReturn==null)
			logger.info("==mapReturn is null ==");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching parent user ************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return mapReturn;
	}
	
	public String saveTransfferedHierarchy(String childParentMapping, String strOLMID) throws DPServiceException
	{
		java.sql.Connection connection = null;
		String result = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			TransferHierarchyDao hierTrnsDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getTransHierConnDao(connection);
			result=hierTrnsDao.saveTransfferedHierarchy(childParentMapping, strOLMID);
			
			connection.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error("***********Exception occured while Saving transfer hierarchy ************"+ex);
			logger.error("***********Exception occured while Saving transfer hierarchy ************"+ex.getMessage());
			
			try
			{
				connection.rollback();
			}
			catch(Exception exx)
			{
				logger.error("=Exception while connection commit in Method saveTransfferedHierarchy() in TransferHierarchyImpl");
				exx.printStackTrace();
				logger.error("Exception occured Method saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+exx.getMessage());
				logger.error("Exception occured Method saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+exx);
				throw new DPServiceException("Exception occured while saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+exx.getMessage());
			}
		
			throw new DPServiceException("Exception occured while saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+ex.getMessage());
		}
		
		return result;
	}
}
