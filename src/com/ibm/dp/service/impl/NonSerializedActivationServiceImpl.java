package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dao.NonSerializedActivationDao;
import com.ibm.dp.dao.impl.NonSerializedActivationDaoImpl;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.NonSerializedActivationService;
import com.ibm.dp.service.TransferPendingSTBService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class NonSerializedActivationServiceImpl implements NonSerializedActivationService{

	Logger logger = Logger.getLogger(NonSerializedActivationServiceImpl.class.getName());
	private static NonSerializedActivationService nonSerializedActivationService = new NonSerializedActivationServiceImpl();
	private NonSerializedActivationServiceImpl() {}
	public static NonSerializedActivationService getInstance() {
		return nonSerializedActivationService;
	}
	private NonSerializedActivationDao nonSerializedActivationDao = NonSerializedActivationDaoImpl.getInstance();	
	public List<CircleDto> getAllCircleList() throws DPServiceException
	{ 
		logger.info("********************** getAllCircleList() **************************************");
		Connection connection = null;
		List<CircleDto> circleListDTO = null;
			
			try {
				connection = DBConnectionManager.getDBConnection();
				circleListDTO = nonSerializedActivationDao.getAllCircleList();
			} 
			catch (Exception e) {		
				logger.error("Exception occured :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return circleListDTO;
		
		
	}
		
	public String getActivationStatus(String circleId) throws DPServiceException
	{ 
		logger.info("********************** getAllCircleList() **************************************");
		Connection connection = null;
		String status="";
			
			try {
				connection = DBConnectionManager.getDBConnection();
				status = nonSerializedActivationDao.getActivationStatus(circleId);
			} 
			catch (Exception e) {		
				logger.error("Exception occured:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return status;		
	}
	
	public int updateStatus(String accountid,String circleId, String status, String changeType) throws DPServiceException
	{ 
		logger.info("********************** getAllCircleList() **************************************");
		Connection connection = null;
		int result=0;
			
			try {
				connection = DBConnectionManager.getDBConnection();
				result = nonSerializedActivationDao.updateStatus(accountid,circleId, status, changeType);
			} 
			catch (Exception e) {		
				logger.error("Exception occured " + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return result;
		
		
	}
	
	public int insertActivation(String circleId, String changeType, String status) throws DPServiceException
	{ 
		logger.info("********************** getAllCircleList() **************************************");
		Connection connection = null;
		int result=0;
			
			try {
				connection = DBConnectionManager.getDBConnection();
				result = nonSerializedActivationDao.insertActivation(circleId, changeType, status);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in insertActivation:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return result;
		
		
	}
		
}
