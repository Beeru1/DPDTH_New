package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPWrongShipmentDao;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.service.DPWrongShipmentService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dp.common.DBQueries;

public class DPWrongShipmentServiceImpl implements DPWrongShipmentService
{
	
	private Logger logger = Logger.getLogger(DPWrongShipmentServiceImpl.class.getName());
	
	public List<WrongShipmentDTO> getListOfRejectedDCNo(Long distId ,String dc_status ) throws VirtualizationServiceException
	{
		Connection connection = null;
		List<WrongShipmentDTO> wrongShipmentList = new ArrayList<WrongShipmentDTO>();
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			wrongShipmentList = wrongDAO.getListOfRejectedDCNo(distId , dc_status);		
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return wrongShipmentList;
	}
	
	public List<WrongShipmentDTO> getAllSerialNos(String invoiceNo, String dcNo, Integer intCircleID) throws VirtualizationServiceException
	{
		Connection connection = null;
		List<WrongShipmentDTO> wrongShipmentList = new ArrayList<WrongShipmentDTO>();
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			wrongShipmentList = wrongDAO.getAllSerialNos(invoiceNo, dcNo, intCircleID);		
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return wrongShipmentList;
	}
	
	public List<WrongShipmentDTO> getAllProducts(String invoiceNo, String dcNo) throws VirtualizationServiceException
	{
		Connection connection = null;
		List<WrongShipmentDTO> wrongShipmentList = new ArrayList<WrongShipmentDTO>();
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			wrongShipmentList = wrongDAO.getAllProducts(invoiceNo, dcNo);		
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return wrongShipmentList;
	}
	
	public String getDeliveryChallanNo(String invoiceNo) throws VirtualizationServiceException
	{
		Connection connection = null;
		String dcNo = "";
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			dcNo = 	wrongDAO.getDeliveryChallanNo(invoiceNo);
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return dcNo;
	}
	
	
	public String checkErrorDC(String dcNo) throws VirtualizationServiceException
	{

		Connection connection = null;
		String errorFlag = "";
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			errorFlag = 	wrongDAO.checkErrorDC(dcNo);
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return errorFlag;
	}
	
	
	public String getInvoiceNoOfDCNO(String dcNo) throws VirtualizationServiceException
	{

		Connection connection = null;
		String invoiceNo = "";
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			invoiceNo = 	wrongDAO.getInvoiceNoOfDCNO(dcNo);
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return invoiceNo;
	}
	
	
	public boolean submitWrongShipmentDetails(String[] availableSerialsArray, String[] shortSerialsArray, String[] extraSerialsArray, String invoiceNo, String deliveryChallanNo , String distId) throws VirtualizationServiceException
	{
		Connection connection = null;
		boolean flag = false;
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			flag = wrongDAO.submitWrongShipmentDetails(availableSerialsArray, shortSerialsArray, extraSerialsArray, invoiceNo, deliveryChallanNo, distId);
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return flag;
	}
	public String checkWrongInventory(String extraSerialNo , String productID , String distId , String deliveryChallanNo)  throws VirtualizationServiceException
	{
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String chackInv = "";
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			
			DPWrongShipmentDao wrongDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWrongShipmentDAO(connection);
			chackInv = wrongDAO.checkWrongInventory(extraSerialNo, productID, distId,deliveryChallanNo );
		}
		catch(DAOException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(connection);
		}
		return chackInv;
	}
}
