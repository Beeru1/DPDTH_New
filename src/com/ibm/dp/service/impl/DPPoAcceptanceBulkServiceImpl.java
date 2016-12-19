package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dao.DPPoAcceptanceBulkDAO;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.service.DPPoAcceptanceBulkService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

 
public class DPPoAcceptanceBulkServiceImpl implements DPPoAcceptanceBulkService
{
	private Logger logger = Logger.getLogger(DPPoAcceptanceBulkServiceImpl.class.getName());
	
	public List uploadExcel(FormFile myXls , String deliveryChallanNo)  throws VirtualizationServiceException
	{
		Connection connection = null;
		DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
		List list = new ArrayList();
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPPoAcceptanceBulkDAO poDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadExcel(connection);
			list = poDAO.uploadExcel(myXls,deliveryChallanNo);		
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
		return list;
	}
	
	public DPPoAcceptanceBulkDTO getWrongShortSrNo(String invoiceNo, String dcNo, int intCircleID ,List list)  throws VirtualizationServiceException
	{
		Connection connection = null;
		DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPPoAcceptanceBulkDAO poDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadExcel(connection);
			dpPoAcceptanceBulkDTO = poDAO.getWrongShortSrNo(invoiceNo,dcNo,intCircleID,list);		
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
		return dpPoAcceptanceBulkDTO;
	}
	
	public DPPoAcceptanceBulkDTO getShortSrNo(String dcNo)  throws VirtualizationServiceException
	{
		Connection connection = null;
		DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPPoAcceptanceBulkDAO poDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadExcel(connection);
			dpPoAcceptanceBulkDTO = poDAO.getShortSrNo(dcNo);		
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
		return dpPoAcceptanceBulkDTO;
	}
	
	
	
}
