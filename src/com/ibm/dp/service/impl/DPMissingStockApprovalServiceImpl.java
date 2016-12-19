package com.ibm.dp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPMissingStockApprovalDao;
import com.ibm.dp.dto.DCNoListDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPMissingStockApprovalService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPMissingStockApprovalServiceImpl implements
		DPMissingStockApprovalService 
{
	
	Logger logger = Logger.getLogger(DPMissingStockApprovalServiceImpl.class.getName());
	
	public List<List> getInitMissingStock(long loginUserId,String strSelectedDC )
	throws DPServiceException
	{
		java.sql.Connection connection = null;
		List<List> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			
			listReturn = dpDCADao.getInitMissingStockDao(loginUserId,strSelectedDC );
			
			if(listReturn.size()==0)
			{
				logger.error("No Missing Stock found for Approval");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for MSA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}
	
	public List<List> saveMissingStock(Map mapMSAGridData, String[] arrCheckedMSA, long loginUserId,String strSelectedDC)
	throws DPServiceException
	{
		logger.info("saveMissingStock method called");
		java.sql.Connection connection = null;
		List<List> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).saveMissingStockDao(connection);
			
			listReturn = dpDCADao.saveMissingStockDao(mapMSAGridData, arrCheckedMSA, loginUserId,strSelectedDC);
			
			if(listReturn.size()==0)
			{
				logger.error("No Missing Stock found for Approval");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while saving data for MSA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}
	
	public List<DCNoListDto> getDCNosList(String loginUserId) throws DPServiceException
	{
		java.sql.Connection connection = null;
		List<DCNoListDto> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			
			listReturn = dpDCADao.getDCNosList(loginUserId);
			
			if(listReturn.size()==0)
			{
				logger.error("No Missing Stock found for Approval");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for MSA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listReturn;
	}
	public ArrayList viewPOList(long accountID, int circleID, int lowerbound, int upperbound) throws DPServiceException {
		// TODO Auto-generated method stub
		java.sql.Connection connection = null;
		ArrayList POList = null;
		
		try {
			
			connection = DBConnectionManager.getDBConnection();
			DPMissingStockApprovalDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			POList = dppod.viewPODetails(accountID,circleID,lowerbound,upperbound);
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("***ERROR IN METHOD ViewPOList() DPPurchaseOrderServiceImpl class **********"+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return POList;
	}
	
}
