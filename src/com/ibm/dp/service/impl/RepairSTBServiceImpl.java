package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPDcCreationDao;
import com.ibm.dp.dao.RepairSTBDao;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RepairSTBDto;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.RepairSTBService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class RepairSTBServiceImpl implements RepairSTBService{
	
	
Logger logger = Logger.getLogger(RepairSTBServiceImpl.class.getName());
	
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DPServiceException
	{
		logger.info("********************** getProductTypeMaster() **************************************");
		Connection connection = null;
		List<ProductMasterDto> productListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			RepairSTBDao repairStbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRepairSTBDao(connection);
			
			productListDTO =  repairStbDao.getProductTypeMaster(circleId);
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
		return productListDTO;
		
		
	}
	
	public List<RepairSTBDto> getStockCollectionList(Long lngCrBy) throws DPServiceException
	{
		logger.info("********************** getStockCollectionList() **************************************");
		Connection connection = null;
		List<RepairSTBDto> dcStockCollectionListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			RepairSTBDao repairStbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRepairSTBDao(connection);
			
			dcStockCollectionListDTO =  repairStbDao.getStockCollectionList(lngCrBy);
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
		return dcStockCollectionListDTO;
		
		
	}
	
	public String insertStockCollectionRepair(ListIterator<RepairSTBDto> dcCreationDtoListItr,Long lngDistId,String circleId) throws VirtualizationServiceException, DPServiceException 
	{
		logger.info("********************** getStockCollectionList() **************************************");
		Connection connection = null;
		String strDcNo="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			RepairSTBDao repairStbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRepairSTBDao(connection);
		
			strDcNo=repairStbDao.insertStockCollectionRepair(dcCreationDtoListItr,lngDistId,circleId);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DPServiceException("Exception: " + e.getMessage());
		}
		//Added by Shilpa Khanna to Release DB2 connection
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strDcNo;
	}
	

	public String isValid(Long lngDistId) throws DPServiceException 
	{
		logger.info("********************** isValid() **************************************");
		Connection connection = null;
		String strIsValid="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			RepairSTBDao repairStbDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRepairSTBDao(connection);
		
			strIsValid=repairStbDao.isValid(lngDistId);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DPServiceException("Exception: " + e.getMessage());
		}//Added by Shilpa Khanna to Release DB2 connection
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strIsValid;
	}
	

}
