package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.DpDcFreshStatusDao;
import com.ibm.dp.dto.DpDcFreshStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DpDcFreshStatusService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DpDcFreshStatusServiceImpl implements DpDcFreshStatusService{

	
Logger logger = Logger.getLogger(DpDcFreshStatusServiceImpl.class.getName());
	
	public List<DpDcFreshStatusDto> getAllDCListFresh(Long lngCrBy) throws DPServiceException
	{
		logger.info("*********************in DpDcFreshStatusServiceImpl -> getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcFreshStatusDto> dcFreshStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcFreshStatusDao dcFreshStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcFreshStatusDao(connection);
			
			dcFreshStatusDTO =  dcFreshStatusDao.getAllDCListFresh(lngCrBy);
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
		return dcFreshStatusDTO;
		
		
	}
	
	public String setDCStatusFresh(String[] arrDcNos) throws DPServiceException
	{
		logger.info("*********************in DpDcFreshStatusServiceImpl -> setDCStatus() starts **************************************");
		Connection connection = null;
		String strSuccessMsg ="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcFreshStatusDao dcFreshStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcFreshStatusDao(connection);
			
			strSuccessMsg =  dcFreshStatusDao.setDCStatusFresh(arrDcNos);
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
	public List<DpDcFreshStatusDto> viewSerialsStatusFresh(String dc_no) throws DPServiceException
	{
		logger.info("*********************in DpDcFreshStatusServiceImpl ->  Fresh--->  getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcFreshStatusDto> dcFreshStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcFreshStatusDao dcFreshStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcFreshStatusDao(connection);
			
			dcFreshStatusDTO =  dcFreshStatusDao.viewSerialsStatusFresh(dc_no);
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
		return dcFreshStatusDTO;
		
		
	}
	
}
