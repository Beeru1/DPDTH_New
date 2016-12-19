package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.HierarchyTransferDao;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.FseDTO;
import com.ibm.dp.dto.HierarchyTransferDto;
import com.ibm.dp.dto.RetailorDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.HierarchyTransferService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class HierarchyTransferServiceImpl implements HierarchyTransferService{
Logger logger = Logger.getLogger(DpDcChangeStatusServiceImpl.class.getName());
	
	public List<DistributorDTO> getAllDistList(String strLoginId,String strAcLeveid) throws DPServiceException
	{
		logger.info("*********************in HierarchyTransferServiceImpl -> getAllDistList() starts **************************************");
		Connection connection = null;
		List<DistributorDTO> dcHierarchyDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			dcHierarchyDTO =  dcChangeStatusDao.getAllDistList(strLoginId,strAcLeveid);
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
		return dcHierarchyDTO;
		
		
	}
	public List<FseDTO> getAllFSeList(String strLoginId) throws DPServiceException
	{
		logger.info("*********************in HierarchyTransferServiceImpl -> getAllDistList() starts **************************************");
		Connection connection = null;
		List<FseDTO> dcHierarchyDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			dcHierarchyDTO =  dcChangeStatusDao.getAllFSeList(strLoginId);
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
		return dcHierarchyDTO;
		
		
	}
	public List<RetailorDTO> getAllRetlerList(String strLoginId) throws DPServiceException
	{
		logger.info("*********************in HierarchyTransferServiceImpl -> getAllDistList() starts **************************************");
		Connection connection = null;
		List<RetailorDTO> dcHierarchyDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			dcHierarchyDTO =  dcChangeStatusDao.getAllRetlerList(strLoginId);
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
		return dcHierarchyDTO;
		
		
	}
	
	
	public String insertHierarchyTransfer(HierarchyTransferDto transfDto,String crcleId) throws DPServiceException
	{
		String strReturn ="";
		
		logger.info("*********************in HierarchyTransferServiceImpl -> getAllDistList() starts **************************************");
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			strReturn =  dcChangeStatusDao.insertHierarchyTransfer(transfDto,crcleId);
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
		
		return strReturn;
	}
	public List<CircleDto> getAllCircleList() throws DPServiceException
	{
		logger.info("********************** getAllCircleList() **************************************");
		Connection connection = null;
		List<CircleDto> circleListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			circleListDTO =  dcChangeStatusDao.getAllCircleList();
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
	
	/* Added By Parnika */
	
	public boolean isMutuallyExclusive(String fromDistributorId, String toDistId) throws  DPServiceException
	{
		boolean isExclusive = false;
		
		logger.info("*********************in HierarchyTransferServiceImpl -> isMutuallyExclusive() starts **************************************");
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao transferDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			isExclusive =  transferDao.isMutuallyExclusive(fromDistributorId,toDistId);
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
		
		return isExclusive;
	}
	
	/* End of changes by Parnika */
	//Added by neetika
	
	public List<CircleDto> getAllCircleListCircleAdmin(long id) throws DPServiceException
	{
		logger.info("********************** getAllCircleListCircleAdmin() **************************************");
		Connection connection = null;
		List<CircleDto> circleListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			HierarchyTransferDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getHierarchyTransferDao(connection);
			
			circleListDTO =  dcChangeStatusDao.getAllCircleListCircleAdmin(id);
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
	

}
