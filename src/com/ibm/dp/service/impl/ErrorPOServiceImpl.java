package com.ibm.dp.service.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.ErrorPODao;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ErrorPOService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class ErrorPOServiceImpl implements ErrorPOService
{
	
	Logger logger = Logger.getLogger(ErrorPOServiceImpl.class.getName());
	
		public ArrayList<DuplicateSTBDTO> getDuplicateSTB(String loginUserIdAndGroup)	throws DPServiceException
		{
			java.sql.Connection connection = null;
			ArrayList<DuplicateSTBDTO> stbList = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				ErrorPODao  errorPODao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
												.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getErrorSTBListDao(connection);
				stbList = errorPODao.getSTBListDao(loginUserIdAndGroup);
				
				if(stbList.size()==0)
				{
					logger.error("No duplicate STB found.");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("***********Exception occured while fetching initial data for getDuplicateSTB : "+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return stbList;
		}
	
		
		public ArrayList<DuplicateSTBDTO>  viewPODetailList(String poNumber) throws DPServiceException
		{
			java.sql.Connection connection = null;
			ArrayList<DuplicateSTBDTO> poDetailList = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				ErrorPODao errorPODao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
						.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getErrorSTBListDao(connection);
				
				poDetailList = errorPODao.getpoDetailList(poNumber);
				
				if(poDetailList.size()==0)
				{
				logger.error("No PO Details Found.");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("***********Exception occured viewPODetailList : "+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally 
			{
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return poDetailList;
		}
		
	
}
