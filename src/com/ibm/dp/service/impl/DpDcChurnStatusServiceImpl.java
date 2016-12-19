package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.DpDcChurnStatusDao;
import com.ibm.dp.dao.DpDcDamageStatusDao;
import com.ibm.dp.dto.DpDcChurnStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DpDcChurnStatusService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DpDcChurnStatusServiceImpl implements DpDcChurnStatusService{

	
Logger logger = Logger.getLogger(DpDcChurnStatusServiceImpl.class.getName());
	
	public List<DpDcChurnStatusDto> getAllDCListChurn(Long lngCrBy) throws DPServiceException
	{
		logger.info("*********************in DpDcChurnStatusServiceImpl -> getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcChurnStatusDto> dcChurnStatusDTO = new ArrayList<DpDcChurnStatusDto>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChurnStatusDao dcChurnStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChurnStatusDao(connection);
			
			dcChurnStatusDTO =  dcChurnStatusDao.getAllDCListChurn(lngCrBy);
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
		return dcChurnStatusDTO;
		
		
	}
	
	public String setDCStatusChurn(String[] arrDcNos) throws DPServiceException
	{
		logger.info("*********************in DpDcChurnStatusServiceImpl -> setDCStatus() starts **************************************");
		Connection connection = null;
		String strSuccessMsg ="";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChurnStatusDao dcChurnStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChurnStatusDao(connection);
			
			strSuccessMsg =  dcChurnStatusDao.setDCStatusChurn(arrDcNos);
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
	public List<DpDcChurnStatusDto> viewSerialsStatusChurn(String dc_no) throws DPServiceException
	{
		logger.info("*********************in DpDcChurnStatusServiceImpl ->  Churn--->  getAllDCList() starts **************************************");
		Connection connection = null;
		List<DpDcChurnStatusDto> dcChurnStatusDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChurnStatusDao dcChurnStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChurnStatusDao(connection);
			
			dcChurnStatusDTO =  dcChurnStatusDao.viewSerialsStatusChurn(dc_no);
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
		return dcChurnStatusDTO;
		
		
	}
	
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DPServiceException{
		logger.info("*********************in DpDcDamageStatusServiceImpl -> submitDamageDetail() starts **************************************");
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DpDcChurnStatusDao dcChurnStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDpDcChurnStatusDao(connection);
			
			dcChurnStatusDao.submitDamageDetail( dc_no, courierAgency,docketNum);
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
