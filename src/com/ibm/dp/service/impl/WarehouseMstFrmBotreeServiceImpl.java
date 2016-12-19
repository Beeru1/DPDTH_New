package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import com.ibm.dp.dao.WarehouseMstFrmBotreeDao;
import com.ibm.dp.dto.WarehouseMstFrmBotreeDTO;
import com.ibm.dp.service.WarehouseMstFrmBotreeService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class WarehouseMstFrmBotreeServiceImpl implements WarehouseMstFrmBotreeService{

	Logger logger = Logger.getLogger(WarehouseMstFrmBotreeServiceImpl.class.getName());

	public int fetchCircleId(String circleCode) throws VirtualizationServiceException 
	{
		logger.info("*********************fetchCircleId method called********************");
		
		int circleId = 0;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			WarehouseMstFrmBotreeDao whtDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWarehouseMasterExcelDao(connection);
			
			circleId = whtDao.fetchCircleId(circleCode);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return 0;
	}
	
	public String[] updateWhtMasterData(WarehouseMstFrmBotreeDTO whDTO)throws VirtualizationServiceException 
	{
		logger.info("*********************updateWhtMasterData method called********************");
		
		//String strServiceMsg = "SUCCESS";
		String[] strServiceMsg = new String[2];
		strServiceMsg[0]="SUCCESS";
		strServiceMsg[0]="";
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			
			WarehouseMstFrmBotreeDao whtDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWarehouseMasterExcelDao(connection);
			
			strServiceMsg = whtDao.updateWhtMasterData(whDTO);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				connection.rollback();
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in WarehouseMstFrmBotreeDaoImpl in updateWhtMasterDatadddddd WebService -------->",	sqle);
			}
			strServiceMsg[0] = "OTHERS";
			strServiceMsg[1] = "";
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return strServiceMsg;
	}
}
