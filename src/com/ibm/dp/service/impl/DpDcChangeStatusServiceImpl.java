package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.DpDcChangeStatusDao;
import com.ibm.dp.dto.DpDcChangeStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DpDcChangeStatusService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DpDcChangeStatusServiceImpl implements DpDcChangeStatusService{

	
Logger logger = Logger.getLogger(DpDcChangeStatusServiceImpl.class.getName());
	
	public List<DpDcChangeStatusDto> getAllDCList(Long lngCrBy) throws DPServiceException
	{
		logger.info("*********************in DpDcChangeStatusServiceImpl -> getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcChangeStatusDto> dcChangeStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChangeStatusDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChangeStatusDao(connection);
			
			dcChangeStatusDTO =  dcChangeStatusDao.getAllDCList(lngCrBy);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return dcChangeStatusDTO;
		
		
	}
	
	public String setDCStatus(String[] arrDcNos) throws DPServiceException
	{
		logger.info("*********************in DpDcChangeStatusServiceImpl -> setDCStatus() starts **************************************");
		Connection connection = null;
		String strSuccessMsg ="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChangeStatusDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChangeStatusDao(connection);
			
			strSuccessMsg =  dcChangeStatusDao.setDCStatus(arrDcNos);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return strSuccessMsg;
		
		
	}
	public List<DpDcChangeStatusDto> viewSerialsStatus(String dc_no) throws DPServiceException
	{
		logger.info("*********************in DpDcChangeStatusServiceImpl -> getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcChangeStatusDto> dcChangeStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChangeStatusDao dcChangeStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChangeStatusDao(connection);
			
			dcChangeStatusDTO =  dcChangeStatusDao.viewSerialsStatus(dc_no);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return dcChangeStatusDTO;
		
		
	}
	
}
