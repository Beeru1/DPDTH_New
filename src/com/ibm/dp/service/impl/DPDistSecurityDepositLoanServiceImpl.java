package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPDistSecurityDepositLoanDao;
import com.ibm.dp.dao.DPStockLevelBulkUploadDao;
import com.ibm.dp.dto.DPDistSecurityDepositLoanDto;
import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPDistSecurityDepositLoanService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;



public class DPDistSecurityDepositLoanServiceImpl implements DPDistSecurityDepositLoanService
{
	Logger logger = Logger.getLogger(DPDistSecurityDepositLoanServiceImpl.class.getName());
	public List uploadExcel(InputStream inputstream) throws DPServiceException
	{
		List list = new ArrayList();
		Connection connection = null;
		DPDistSecurityDepositLoanDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getSecurityLoanDepositExcelDao(connection);
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
	
	public String addSecurityLoan(List list,String userId) throws DPServiceException
	{
		
		String strMessage ="";
		Connection connection = null;
		DPDistSecurityDepositLoanDao  bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getSecurityLoanDepositExcelDao(connection);
			strMessage = bulkupload.addSecurityLoan(list,userId);
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
	
	
	public List<DPDistSecurityDepositLoanDto> getALLDistSecurityLoan() throws DPServiceException
	{
			List<DPDistSecurityDepositLoanDto> reportStockDataList = null;
			DPDistSecurityDepositLoanDao  excelbulkDownload = null;
			Connection connection=null;
			try {
				connection = DBConnectionManager.getDBConnection();
				excelbulkDownload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
				.getSecurityLoanDepositExcelDao(connection);
				reportStockDataList = excelbulkDownload.getALLDistSecurityLoan();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportStockDataList;
	}
	
}
