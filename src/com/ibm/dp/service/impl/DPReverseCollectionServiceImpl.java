package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dao.DPReverseCollectionDao;
import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpReverseInvetryChangeDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPReverseCollectionService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPReverseCollectionServiceImpl implements DPReverseCollectionService
{
	Logger logger = Logger.getLogger(DPReverseCollectionServiceImpl.class.getName());
	
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DPServiceException
	{
		logger.info("********************** getCollectionTypeMaster() **************************************");
		Connection connection = null;
		List<DPReverseCollectionDto> reverseCollectionListDTO = new ArrayList<DPReverseCollectionDto>(); 
		try
		{
			logger.info("enter try ::DPReverseCollectionServiceImpl");//remove
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			logger.info("before going to getCollectionTypeMaster() in dao::DPReverseCollectionServiceImpl");
			reverseCollectionListDTO =  reverseCollectionDao.getCollectionTypeMaster();
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
		return reverseCollectionListDTO;
	}
		
	public List<DPReverseCollectionDto> getCollectionDefectType(int collectionId) throws DPServiceException
	{
		logger.info("********************** getCollectionDefectType() **************************************");
		Connection connection = null;
		List<DPReverseCollectionDto> reverseCollectionListDTO =  new ArrayList<DPReverseCollectionDto>(); 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			reverseCollectionListDTO =  reverseCollectionDao.getCollectionDefectType(collectionId);
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
		return reverseCollectionListDTO;
	}
		
	public List<DPReverseCollectionDto> getProductCollection(String collectionId, int circleId) throws DPServiceException
	{

		logger.info("********************** getProductCollection() **************************************");
		Connection connection = null;
		List<DPReverseCollectionDto> reverseCollectionListDTO =  new ArrayList<DPReverseCollectionDto>(); 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			reverseCollectionListDTO =  reverseCollectionDao.getProductCollection(collectionId, circleId);
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
		return reverseCollectionListDTO;
	}
	
	public List<List> getDCDetails(String dc_no) throws DPServiceException
	{

		Connection connection = null;
		List<List> DCDetailsList =  new ArrayList<List>(); 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao dcDetailsCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			DCDetailsList =  dcDetailsCollectionDao.getDCDetails(dc_no);
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
		return DCDetailsList;
	}
	
	
	
	public List<DPReverseCollectionDto> submitReverseCollection(List<DPReverseCollectionDto> collectList, int circleId, long  accountId) throws DPServiceException
	{

		logger.info("********************** getProductCollection() **************************************");
		Connection connection = null;
		List<DPReverseCollectionDto> revDataList =new ArrayList<DPReverseCollectionDto>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			 
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			revDataList=reverseCollectionDao.submitReverseCollection(collectList, circleId, accountId);
		}
		catch (DAOException de) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new DPServiceException(de.getMessage());
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
		return revDataList;
	}
	
	
	public String validateCollectSerialNo(int productId, String serialNo) throws DPServiceException
	{

		logger.info("********************** validateCollectSerialNo() **************************************");
		Connection connection = null;
		String  errorMsg = "";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			errorMsg = reverseCollectionDao.validateCollectSerialNo(productId, serialNo);
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
		
		return errorMsg;
	}
	
	
	public boolean validateUniqueCollectSerialNo(int productId, String serialNo, String accId) throws DPServiceException
	{

		logger.info("********************** validateCollectSerialNo() **************************************");
		Connection connection = null;
		boolean flag = false;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			logger.info("in try:::validateUniqueCollectSerialNo ");
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			logger.info("b4 flag");
			flag = reverseCollectionDao.validateUniqueCollectSerialNo(productId, serialNo, accId);
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
		
		return flag;
	}
	
	
//	Update by harbans on Reverse Enhancement.
	public boolean validateC2SType(String serialNo)  throws DPServiceException
	{

		logger.info("********************** validateCollectSerialNo() **************************************");
		Connection connection = null;
		boolean flag = false;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			logger.info("in try ::::validateC2SType");
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			logger.info("b4 flag ::::validateC2SType");
			flag = reverseCollectionDao.validateC2SType(serialNo);
			logger.info("aftr flag ::::validateC2SType");
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
		
		return flag;
	}

	public String validateSerialNumber(String strSerialNo, Integer intCollectionID, Integer intProductID) 
	throws DPServiceException 
	{
		logger.info("********************** validateCollectSerialNo() **************************************");
		Connection connection = null;
		String strReturn = "SUCCESS";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			strReturn = reverseCollectionDao.validateSerialNumberDAO(strSerialNo, intCollectionID, intProductID);
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
	
	
	
	/*public String validateSerialNoAll(String serialNo , int circle_Id) throws DPServiceException
	{

		logger.info("********************** validateCollectSerialNo() **************************************");
		Connection connection = null;
		 String flagProduct = "";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			flagProduct = reverseCollectionDao.validateSerialNoAll(serialNo , circle_Id);
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
		
		return flagProduct;
	}*/
	
	
	//Added by Shilpa Khanna for Inventory Change
	
	public List<DpReverseInvetryChangeDTO> getInventoryChangeList(String collectionId,String accountId )throws DPServiceException
	{

		logger.info("********************** getInventoryChangeList() **************************************");
		Connection connection = null;
		List<DpReverseInvetryChangeDTO> invChngListDTO =  new ArrayList<DpReverseInvetryChangeDTO>(); 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			invChngListDTO =  reverseCollectionDao.getInventoryChangeList(collectionId,accountId);
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
		return invChngListDTO;
	}
	public List<DpReverseInvetryChangeDTO> getGridDefectList(String collectionId)throws DPServiceException
	{

		logger.info("********************** getGridDefectList() **************************************");
		Connection connection = null;
		List<DpReverseInvetryChangeDTO> defectListDTO =  new ArrayList<DpReverseInvetryChangeDTO>(); 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseCollectionDao reverseCollectionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPReverseCollectionDao(connection);
			
			defectListDTO =  reverseCollectionDao.getGridDefectList(collectionId);
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
		return defectListDTO;
	}
	public int checkTransactionTypeReverse(Long distId)throws DPServiceException
	{
	java.sql.Connection connection  = null;
	int total = 0;
	try 
	{
		connection = DBConnectionManager.getDBConnection();
		DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
		total = dppod.checkTransactionTypeReverse(distId);
			
	}catch (Exception e) {
		
		throw new DPServiceException(e.getMessage());
	}
	 finally {
			/* Close the connection */
		DBConnectionManager.releaseResources(connection);
	}
	return total;
	}

}
