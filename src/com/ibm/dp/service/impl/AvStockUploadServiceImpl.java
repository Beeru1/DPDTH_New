package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.AvStockUploadDao;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.AvStockUploadService;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class AvStockUploadServiceImpl implements AvStockUploadService
{
	
	Logger logger = Logger.getLogger(AvStockUploadServiceImpl.class.getName());
	public boolean validateCell(String str)
	{
		if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH_AV)
		{
			return false;
		}
		else if(!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}
		return true;
	}
	public List uploadExcel(FormFile myXls) throws DPServiceException 
	{
		Connection connection = null;
		List list = new ArrayList();
		try 
		{
			logger.info("***************AvStockUploadServiceImpl********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFile(connection);
			logger.info("***************in avstockimpl2********");
			list = avStockUploadDao.uploadExcel(myXls);	
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			logger.info("***************12345466447 in serviceImpl********");
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	
	
	public String insertAVStockinDP(List list) throws DPServiceException 
	{
		Connection connection = null;
		String insertStatus="";
		try 
		{
			logger.info("***************getting the database connection for insertAVStockinDP********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFile(connection);
			logger.info("***************in insertAVStockinDP********");
			insertStatus= avStockUploadDao.insertAVStockinDP(connection,list);	
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			logger.info("*************** exception ocurred @$&%*********");
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return insertStatus;
	}
	
}

