package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.ChurnDCGenerationDAO;
import com.ibm.dp.dto.ChurnDCGenerationDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ChurnDCGenerationService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ChurnDCGenerationServiceImpl implements ChurnDCGenerationService 
{
	Logger logger = Logger.getLogger(ChurnDCGenerationServiceImpl.class.getName());

	public ArrayList<ArrayList<ChurnDCGenerationDTO>> viewChurnPendingForDCList(long accountID)throws DPServiceException, DAOException, SQLException 
	{
		Connection connection = null;
		
		ArrayList<ArrayList<ChurnDCGenerationDTO>> arrReturn = null;
		
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			ChurnDCGenerationDAO churnDAO = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getviewDCDetails(connection);
			
			arrReturn = churnDAO.viewChurnPendingForDCList(accountID);
        	
			connection.commit();
		} 
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			e.printStackTrace();
			logger.info("***ERROR IN METHOD ViewPOList() DPPurchaseOrderServiceImpl class **********"+e);
			throw new DPServiceException(e.getMessage());
		}		
		finally 
		{				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}		
		
		return arrReturn;
	}

	public ArrayList<ArrayList<ChurnDCGenerationDTO>> reportDCCreation(ChurnDCGenerationDTO churnDTO) throws DPServiceException	
	{
		Connection connection = null;
		
		ArrayList<ArrayList<ChurnDCGenerationDTO>> arrReturn = null;
		try		
		{
			connection = DBConnectionManager.getDBConnection();
			ChurnDCGenerationDAO churnDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getviewDCDetails(connection);
			
			arrReturn = churnDao.reportDCGenerationDao(churnDTO);
			
			connection.commit();
		}		
		catch(Exception e)		
		{
			try			
			{				
				connection.rollback();			
			}			
			catch(Exception e2)			
			{				
				e2.printStackTrace();			
			}		
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
	
	public String checkERR(String rowSrNo) throws DPServiceException{
		java.sql.Connection connection = null;
		String strERR="";
		try		{
			connection = DBConnectionManager.getDBConnection();
			ChurnDCGenerationDAO churnDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getviewDCDetails(connection);
			strERR=churnDao.checkERR(rowSrNo);
			
		}	catch(Exception e)		{			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for DCA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}  finally		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strERR;
		
	}
}
