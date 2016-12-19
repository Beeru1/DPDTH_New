package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPDistPinCodeMapDao;
import com.ibm.dp.dao.WHdistmappbulkDao;
import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPDistPinCodeMapService;
import com.ibm.dp.service.DPProductSecurityListService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;



public class DPDistPinCodeMapServiceImpl implements DPDistPinCodeMapService
{

	Logger logger = Logger.getLogger(DPProductSecurityListServiceImpl.class.getName());

	public List<DPDistPinCodeMapDto> getALLDistPinList() throws DPServiceException {

		List<DPDistPinCodeMapDto> reportStockDataList = null;
		DPDistPinCodeMapDao  excelbulkDownload = null;
		Connection connection=null;
		try {
			connection = DBConnectionManager.getDBConnection();
			excelbulkDownload = (DPDistPinCodeMapDao) DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDistPincodeMapExcelDao(connection);
			reportStockDataList = excelbulkDownload.getALLDistPinList();
		} 
		catch (Exception e) {		
			logger.error("Exception occured in DPDistPinCodeMapServiceImpl getALLDistPinList method  :" + e.getMessage());
		}
	return reportStockDataList;

	}

	public List uploadExcel(InputStream inputstream) throws DPServiceException {

		List list = new ArrayList();
		Connection connection = null;
		DPDistPinCodeMapDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getDistPincodeMapExcelDao(connection);
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

	public String addDistPinCodeMap(List list) throws DPServiceException {

		
		String strMessage ="";
		Connection connection = null;
		DPDistPinCodeMapDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getDistPincodeMapExcelDao(connection);
			strMessage = bulkupload.addDistPinCodeMap(list);
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
	
}
