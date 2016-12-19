package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPDcCreationDao;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPDcCreationService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPDcCreationServiceImpl implements DPDcCreationService{

Logger logger = Logger.getLogger(DPDcCreationServiceImpl.class.getName());
	
	public List<DpDcCreationDto> getCollectionTypeMaster() throws DPServiceException
	{
		logger.info("********************** getCollectionTypeMaster() **************************************");
		Connection connection = null;
		List<DpDcCreationDto> dcCollectionListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDcCreationDao dcCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPDcCreationDao(connection);
			
			dcCollectionListDTO =  dcCollectionDao.getCollectionTypeMaster();
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
		return dcCollectionListDTO;
		
		
	}
	
	public List<DpDcReverseStockInventory> getStockCollectionList(String collectionId,Long lngCrBy) throws DPServiceException
	{
		logger.info("********************** getStockCollectionList() **************************************");
		Connection connection = null;
		List<DpDcReverseStockInventory> dcStockCollectionListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDcCreationDao dcCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPDcCreationDao(connection);
			
			dcStockCollectionListDTO =  dcCollectionDao.getStockCollectionList(collectionId,lngCrBy);
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
	
	public String insertStockCollection(ListIterator<DpDcReverseStockInventory> dcCreationDtoListItr,Long lngDistId,String circleId,String strRemarks,String agencyName,String docketNumber) throws VirtualizationServiceException, DAOException 
	{
		logger.info("********************** getStockCollectionList() **************************************");
		Connection connection = null;
		String strDcNo="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDcCreationDao dcCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPDcCreationDao(connection);
		
			strDcNo=dcCollectionDao.insertStockCollection(dcCreationDtoListItr,lngDistId,circleId, strRemarks,agencyName,docketNumber);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		//Added By Shilpa Khanna to release database connection
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strDcNo;
	}
	
	public String checkERR(String rowSrNo) throws VirtualizationServiceException, DAOException 
	{
		logger.info("********************** getStockCollectionList() **************************************");
		Connection connection = null;
		String strERR="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDcCreationDao dcCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPDcCreationDao(connection);
		
			strERR=dcCollectionDao.checkERR(rowSrNo);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}		
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strERR;
	}

	@Override
	public DpDcReverseStockInventory getStockDetails(String srnoCollect)
			throws DPServiceException,DAOException {
		logger.info("********************** getStockDetails **************************************");
		Connection connection = null;
		DpDcReverseStockInventory dpdcRevDto;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDcCreationDao dcCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPDcCreationDao(connection);
			dpdcRevDto=dcCollectionDao.getStockDetail(srnoCollect);
			
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}		
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return dpdcRevDto;
	}
	
	@Override
	public int updateInactiveSecondaryStock(String prodId,long distId,String serialNum)
			throws Exception {
		logger.info("********************** updateInactiveSecondaryStock **************************************");
		Connection connection = null;
		DpDcReverseStockInventory dpdcRevDto;
		int i;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPDcCreationDao dcCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPDcCreationDao(connection);
		i=dcCollectionDao.updateInactiveSecondaryStock(prodId, distId, serialNum);
			
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}		
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return i;
	}
}
