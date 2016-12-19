package com.ibm.dp.service.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPDeliveryChallanAcceptDao;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPDeliveryChallanAcceptService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPDeliveryChallanAcceptServiceImpl implements DPDeliveryChallanAcceptService
{
	
	Logger logger = Logger.getLogger(DPDeliveryChallanAcceptServiceImpl.class.getName());
	
	public ArrayList<DPDeliveryChallanAcceptDTO> getInitDeliveryChallan(long loginUserId)
	throws DPServiceException
	{
		java.sql.Connection connection = null;
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDeliveryChallanAcceptDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitDeliveryChallanDao(connection);
			
			arrReturn = dpDCADao.getInitDeliveryChallanDao(loginUserId);
			
			if(arrReturn.size()==0)
			{
				logger.error("No delivery challan found for acceptance");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for DCA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return arrReturn;
	}
	
	public ArrayList<DPDeliveryChallanAcceptDTO> reportDeliveryChallan(String[] arrCheckedDC, String dc_report_accept,
			long loginUserId)throws DPServiceException
	{
		java.sql.Connection connection = null;
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDeliveryChallanAcceptDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).reportDeliveryChallanDao(connection);
			
			arrReturn = dpDCADao.reportDeliveryChallanDao(arrCheckedDC, dc_report_accept, loginUserId);
			
			if(arrReturn.size()==0)
			{
				logger.error("No delivery challan found for acceptance");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for DCA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return arrReturn;
	}
		
		public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfDeliveryChallan(String invoiceNo) throws DPServiceException
		{
			java.sql.Connection connection = null;
			ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				DPDeliveryChallanAcceptDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
												.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitDeliveryChallanDao(connection);
				
				arrReturn = dpDCADao.vewAllSerialsOfDeliveryChallan(invoiceNo);
				
				if(arrReturn.size()==0)
				{
					logger.error("No serials found of given invoice no.");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("***********Exception occured vewAllSerialsOfDeliveryChallan************"+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return arrReturn;
		}
		public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfPOStatus(String invoiceNo,String ProductId,String circleId) throws DPServiceException
		{
			java.sql.Connection connection = null;
			ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				DPDeliveryChallanAcceptDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
												.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitDeliveryChallanDao(connection);
				
				arrReturn = dpDCADao.vewAllSerialsOfPOStatus(invoiceNo,ProductId,circleId);
				
				if(arrReturn.size()==0)
				{
					logger.error("No serials found of given invoice no.");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("***********Exception occured vewAllSerialsOfPOStatus************"+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return arrReturn;
		}
		
	
}
