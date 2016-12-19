package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPProductSecurityListDao;
import com.ibm.dp.dao.DPStockLevelBulkUploadDao;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPStockLevelBulkUploadService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;



public class DPStockLevelBulkUploadServiceImpl implements DPStockLevelBulkUploadService
{
	Logger logger = Logger.getLogger(DPStockLevelBulkUploadServiceImpl.class.getName());
	public List uploadExcel(InputStream inputstream) throws DPServiceException
	{
		List list = new ArrayList();
		Connection connection = null;
		DPStockLevelBulkUploadDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getStockLevelExcelDao(connection);
			list = bulkupload.uploadExcel(inputstream);
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
			}
			e.printStackTrace();
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			try
			{
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	
	public String addStockLevel(List list) throws DPServiceException
	{
		
		String strMessage ="";
		Connection connection = null;
		DPStockLevelBulkUploadDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getStockLevelExcelDao(connection);
			strMessage = bulkupload.addStockLevel(list);
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
			}
			e.printStackTrace();
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			try
			{
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			DBConnectionManager.releaseResources(connection);
		}
		return strMessage;
	}
	
	
	public List<DPStockLevelBulkUploadDto> getALLStockLevelList() throws DPServiceException
	{
			List<DPStockLevelBulkUploadDto> reportStockDataList = null;
			DPStockLevelBulkUploadDao  excelbulkDownload = null;
			Connection connection=null;
			try {
				connection = DBConnectionManager.getDBConnection();
				excelbulkDownload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
				.getStockLevelExcelDao(connection);
				reportStockDataList = excelbulkDownload.getALLStockLevelList();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportStockDataList;
	}
	
	
}
