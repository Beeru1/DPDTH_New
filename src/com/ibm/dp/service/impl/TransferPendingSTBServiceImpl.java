package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPReverseCollectionDao;
import com.ibm.dp.dao.TransferPendingSTBDao;
import com.ibm.dp.dao.impl.TransferPendingSTBDaoImpl;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.TransferPendingSTBDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.TransferPendingSTBService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class TransferPendingSTBServiceImpl implements TransferPendingSTBService{

	Logger logger = Logger.getLogger(TransferPendingSTBServiceImpl.class.getName());
	private static TransferPendingSTBService transferPendingSTBService = new TransferPendingSTBServiceImpl();
	private TransferPendingSTBServiceImpl(){}
	public static TransferPendingSTBService getInstance() {
		return transferPendingSTBService;
	}
	private TransferPendingSTBDao transferSTBDao = TransferPendingSTBDaoImpl.getInstance();
		
		public List<CircleDto> getAllCircleList() throws DPServiceException
		{
			logger.info("********************** getAllCircleList() **************************************");
			//Connection connection = null;
			List<CircleDto> circleListDTO = null;
				
				try {
					//connection = DBConnectionManager.getDBConnection();
					circleListDTO = transferSTBDao.getAllCircleList();
				} 
				catch (Exception e) {		
					logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
					e.printStackTrace();
					throw new DPServiceException(e.getMessage());
				}
			
			finally
			{							
				//DBConnectionManager.releaseResources(connection);
			}
			return circleListDTO;
			
			
		}
		
		public List<TransferPendingSTBDto> getCollectionData(String distId,String collectionId) throws DPServiceException
		{
			logger.info("********************** getCollectionData() **************************************");
			//Connection connection = null;
			List<TransferPendingSTBDto> transferListDTO = null;
			try
			{
				//connection = DBConnectionManager.getDBConnection();
				
				TransferPendingSTBDao transferDao = TransferPendingSTBDaoImpl.getInstance();			
				transferListDTO =  transferDao.getCollectionData(distId,collectionId);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{							
				//DBConnectionManager.releaseResources(connection);
			}
			return transferListDTO;
			
			
		}
		
		public void updateRevInventory(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException
		{
			logger.info("********************** getCollectionData() **************************************");
			//Connection connection = null;
			//List<TransferPendingSTBDto> transferListDTO = null;
			try
			{
				//connection = DBConnectionManager.getDBConnection();
				
				TransferPendingSTBDao transferDao = TransferPendingSTBDaoImpl.getInstance();			
				transferDao.updateRevInventory(transferDto, connection);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				//DBConnectionManager.releaseResources(connection);
			}
			//return transferListDTO;
			
			
		}
		
		public void updateRevStockInventory(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException
		{
			logger.info("********************** getCollectionData() **************************************");
			//Connection connection = null;
			//List<TransferPendingSTBDto> transferListDTO = null;
			try
			{
				//connection = DBConnectionManager.getDBConnection();
				
				TransferPendingSTBDao transferDao = TransferPendingSTBDaoImpl.getInstance();			
				transferDao.updateRevStockInventory(transferDto, connection);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				//DBConnectionManager.releaseResources(connection);
			}
			
		}

		
		public void insertRevPendingTransfer(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException
		{
			logger.info("********************** getCollectionData() **************************************");
			//Connection connection = null;
			//List<TransferPendingSTBDto> transferListDTO = null;
			try
			{
				//connection = DBConnectionManager.getDBConnection();
				
				TransferPendingSTBDao transferDao = TransferPendingSTBDaoImpl.getInstance();			
				transferDao.insertRevPendingTransfer(transferDto, connection);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				//DBConnectionManager.releaseResources(connection);
			}
		
		}
		public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DPServiceException
		{
			logger.info("********************** getCollectionTypeMaster() **************************************");
			List<DPReverseCollectionDto> reverseCollectionListDTO = new ArrayList<DPReverseCollectionDto>(); 
			try
			{
				TransferPendingSTBDao transferDao = TransferPendingSTBDaoImpl.getInstance();	
				reverseCollectionListDTO =  transferDao.getCollectionTypeMaster();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				//DBConnectionManager.releaseResources(connection);
			}
			return reverseCollectionListDTO;
		}
		

		public void updateRevChurnInventory(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException
		{
			logger.info("********************** updateRevChurnInventory() **************************************");
			//Connection connection = null;
			//List<TransferPendingSTBDto> transferListDTO = null;
			try
			{
				//connection = DBConnectionManager.getDBConnection();
				
				TransferPendingSTBDao transferDao = TransferPendingSTBDaoImpl.getInstance();			
				transferDao.updateRevChurnInventory(transferDto, connection);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				//DBConnectionManager.releaseResources(connection);
			}
			
		}

		
}
