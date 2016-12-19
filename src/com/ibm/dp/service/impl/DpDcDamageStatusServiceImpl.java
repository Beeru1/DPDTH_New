package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.DpDcDamageStatusDao;
import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DpDcDamageStatusService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DpDcDamageStatusServiceImpl implements DpDcDamageStatusService{

	
Logger logger = Logger.getLogger(DpDcDamageStatusServiceImpl.class.getName());
	
	public List<DpDcDamageStatusDto> getAllDCList(Long lngCrBy) throws DPServiceException
	{
		logger.info("*********************in DpDcDAMAGEStatusServiceImpl -> getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcDamageStatusDto> dcDamageStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcDamageStatusDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcDamageStatusDao(connection);
			
			dcDamageStatusDTO =  dcDamageStatusDao.getAllDCList(lngCrBy);
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
		return dcDamageStatusDTO;
		
		
	}
	
	public String setDCStatus(String[] arrDcNos) throws DPServiceException
	{
		logger.info("*********************in DpDcDamageStatusServiceImpl -> setDCStatus() starts **************************************");
		Connection connection = null;
		String strSuccessMsg ="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcDamageStatusDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcDamageStatusDao(connection);
			
			strSuccessMsg =  dcDamageStatusDao.setDCStatus(arrDcNos);
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
	public List<DpDcDamageStatusDto> viewSerialsStatus(String dc_no) throws DPServiceException
	{
		logger.info("*********************in DpDcDamageStatusServiceImpl -> getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcDamageStatusDto> dcDamageStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcDamageStatusDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcDamageStatusDao(connection);
			
			dcDamageStatusDTO =  dcDamageStatusDao.viewSerialsStatus(dc_no);
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
		return dcDamageStatusDTO;
		
		
	}
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DPServiceException{
		logger.info("*********************in DpDcDamageStatusServiceImpl -> submitDamageDetail() starts **************************************");
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcDamageStatusDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcDamageStatusDao(connection);
			
			dcDamageStatusDao.submitDamageDetail( dc_no, courierAgency,docketNum);
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
		
	}
	
}
