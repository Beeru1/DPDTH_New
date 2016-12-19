package com.ibm.dp.service.impl;

import java.sql.Connection;

import javax.xml.soap.SOAPElement;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.POStatusUpdateDao;
import com.ibm.dp.service.POStatusUpdateService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class POStatusUpdateServiceImpl implements POStatusUpdateService{
	Logger logger = Logger.getLogger(POStatusUpdateServiceImpl.class.getName());

	public String updateStatus(String poNo, String poStatus, String poStatusTime, String remarks, String productCode, String poQuantity, String userid, String password)throws VirtualizationServiceException 
	{
		logger.info("*********************updateStatus method called********************");
		
		String strServiceMsg = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			POStatusUpdateDao pOStatusUpdateDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getPOStatusUpdateDao(connection);
			
			strServiceMsg = pOStatusUpdateDao.updateStatus(poNo, poStatus, poStatusTime, remarks, productCode, poQuantity, userid, password);
			
			connection.commit();
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
	
	public String updateDeliveryChallan(String prNo, String poNo, String poDate,String poStatusDate , String invoiceNo,String invoiceDate, String dcNo, String dcDate, String ddChequeNo , String ddChequeDate,String productId,String serialNo, String userid, String password)throws VirtualizationServiceException 
	{
		logger.info("*********************updateDeliveryChallan method called********************");
		
		String strServiceMsg = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			POStatusUpdateDao pOStatusUpdateDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getPOStatusUpdateDao(connection);
			
			strServiceMsg = pOStatusUpdateDao.updateDeliveryChallan(prNo,  poNo,  poDate, poStatusDate ,  invoiceNo, invoiceDate,  dcNo,  dcDate,  ddChequeNo ,  ddChequeDate, productId, serialNo, userid, password);
			
			
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
