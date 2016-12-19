/**
 * 
 */
package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.AccountDetailReportDao;
import com.ibm.dp.dao.DPProductSecurityListDao;
import com.ibm.dp.dao.RetailerLapuDataDao;
import com.ibm.dp.dto.RetailerLapuDataDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.RetailerLapuDataService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * @author Administrator
 *
 */
public class RetailerLapuDataServiceImpl implements RetailerLapuDataService
{
	private Logger logger = Logger.getLogger(RetailerLapuDataServiceImpl.class.getName());
	
	public List<RetailerLapuDataDto> getAllRetailerLapuData()throws VirtualizationServiceException
	{
		Connection connection=null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			RetailerLapuDataDao retailerLapuDataDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRetailerLapuDataDao(connection);
			return retailerLapuDataDao.getAllRetailerLapuData();
		}
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
	}
	
	public List<RetailerLapuDataDto> validateExcel(InputStream inputstream) throws DPServiceException
	{
		List list = new ArrayList();
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			RetailerLapuDataDao retailerLapuDataDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRetailerLapuDataDao(connection);
			list = retailerLapuDataDao.validateExcel(inputstream);
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

	public String updateLapuNumbers(List<RetailerLapuDataDto> lapuData) throws DPServiceException
	{
		String strMessage ="";
		Connection connection = null;
		RetailerLapuDataDao bulkupload = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getRetailerLapuDataDao(connection);
			strMessage = bulkupload.updateLapuNumbers(lapuData);
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
