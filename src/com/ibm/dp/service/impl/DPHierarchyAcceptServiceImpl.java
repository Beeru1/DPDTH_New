package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPHierarchyAcceptDAO;
import com.ibm.dp.dto.DPHierarchyAcceptDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPHierarchyAcceptService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPHierarchyAcceptServiceImpl implements DPHierarchyAcceptService 
{
	Logger logger = Logger.getLogger(DPHierarchyAcceptServiceImpl.class.getName());
	
	public List<DPHierarchyAcceptDTO> getHierarchyTransferInit(long loginUserId) throws DPServiceException 
	{
		logger.info("*********************getHierarchyTransferInit method called********************");
		
		List<DPHierarchyAcceptDTO> listInitData = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPHierarchyAcceptDAO depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferInit(connection);
			
			listInitData = depleteDao.getHierarchyTransferInitDao(loginUserId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for Hier Trans************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listInitData;
	}
	public List<DPHierarchyAcceptDTO> viewHierarchyAcceptList(long longLoginId, String strTRNo, Integer intTransferBy, String strTrnsTime) throws DPServiceException{

		Connection connection = null;
		List<DPHierarchyAcceptDTO> viewHierarchyListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPHierarchyAcceptDAO hierarchyListDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyListDao(connection);
			
			viewHierarchyListDTO =  hierarchyListDao.viewHierarchyAcceptList(strTRNo, longLoginId, intTransferBy, strTrnsTime);
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
		return viewHierarchyListDTO;	
	}
	
	public List<DPHierarchyAcceptDTO> acceptHierarchy(String[] arrCheckedTR, long loginUserId) 
	throws DPServiceException 
	{
		logger.info("*********************getHierarchyTransferInit method called********************");
		
		List<DPHierarchyAcceptDTO> listInitData = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPHierarchyAcceptDAO depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).acceptHierarchy(connection);
			
			listInitData = depleteDao.acceptHierarchyDao(arrCheckedTR, loginUserId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for Hier Trans************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listInitData;
	}

	public List<DPHierarchyAcceptDTO> getStockDetails(String account_id, String role) 
	throws DPServiceException 
	{
		logger.info("*********************getHierarchyTransferInit method called********************");
		
		List<DPHierarchyAcceptDTO> listInitData = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPHierarchyAcceptDAO depleteDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).acceptHierarchy(connection);
			
			listInitData = depleteDao.getStockDetails(account_id, role);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for Hier Trans************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listInitData;
	}
	
	
}
