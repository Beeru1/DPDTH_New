package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.C2SBulkUploadDao;
import com.ibm.dp.dao.DistStockDeclarationDao;
import com.ibm.dp.dao.impl.DistStockDeclarationDaoImpl;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.C2SBulkUploadService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;



public class C2SBulkUploadServiceImpl implements C2SBulkUploadService
{
	Logger logger = Logger.getLogger(C2SBulkUploadServiceImpl.class.getName());
	
	
	public String uploadExcel(InputStream inputstream) throws DPServiceException
	{
		
		//DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		String str ="";
		Connection connection = null;
		C2SBulkUploadDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getuploadExcelDao(connection);
			str = bulkupload.uploadExcel(inputstream);
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
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			DBConnectionManager.releaseResources(connection);
		}
		return str;
	}
}
