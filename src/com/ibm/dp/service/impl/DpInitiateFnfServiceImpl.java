package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.DpInitiateFnfDao;
import com.ibm.dp.dao.impl.DpInitiateFnfDaoImpl;
import com.ibm.dp.dto.DpInitiateFnfDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DpInitiateFnfService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DpInitiateFnfServiceImpl implements DpInitiateFnfService{
	private Logger logger = Logger.getLogger(DpInitiateFnfServiceImpl.class.getName());
	Connection connection = null;
	public List<DpInitiateFnfDto> getDistList(long loginUserId) throws DPServiceException {
		List<DpInitiateFnfDto> list = null;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			list = dao.getDistList(loginUserId);
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
	return list;
	}
	public DpInitiateFnfDto getDistListSearch(String searchedDistOlmId,long loginUserId) throws DPServiceException {
		DpInitiateFnfDto objDpInitiateFnfDto= null;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			objDpInitiateFnfDto = dao.getDistListSearch(searchedDistOlmId, loginUserId);
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
	return objDpInitiateFnfDto;
	}
	
	
	
	public String requestForDistFnF(String accountId, String distRemark, String debitrequired, long loginUserId,String days, String distName) throws DPServiceException
	{
		String msg="";
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			msg = dao.requestForDistFnF(accountId, distRemark,debitrequired,loginUserId,days,distName);
		} 
		catch (Exception e) 
		{		
			logger.error("Exception occured :" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
	
	finally
	{							
		DBConnectionManager.releaseResources(connection);
	}
	return msg;
		
	}
	public List<DpInitiateFnfDto> viewDistStockDetail(String accountId,long loginUserId) throws DPServiceException {

		List<DpInitiateFnfDto> list = null;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			list = dao.viewDistStockDetail(accountId,loginUserId);
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
	return list;
	
		
	}
	public List<DpInitiateFnfDto> getPendingDistApprovalList(long loginUserId) throws DPServiceException {

		List<DpInitiateFnfDto> list = null;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			list = dao.getPendingDistApprovalList(loginUserId);
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
	return list;
	
	}
	public String approveDistFnF(String accountId, String approverRemark, long loginUserId, String distName, String reqId) throws DPServiceException {

		String msg="";
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			msg = dao.approveDistFnF(accountId, approverRemark,loginUserId,distName,reqId);
		} 
		catch (Exception e) 
		{		
			logger.error("Exception occured :" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
	
	finally
	{							
		DBConnectionManager.releaseResources(connection);
	}
	return msg;
		
	
	}
	public List<DpInitiateFnfDto> getConfirmationPendingDistList(long loginUserId,int circleId) throws DPServiceException {


		List<DpInitiateFnfDto> list = null;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			list = dao.getConfirmationPendingDistList(loginUserId,circleId);
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
	return list;
	
	
	}
	public String confirmFnF(String reqId, String confRemark, String distName, long loginUserId) throws DPServiceException {
		String msg="";
		try {
			connection = DBConnectionManager.getDBConnection();
			DpInitiateFnfDao dao = new DpInitiateFnfDaoImpl(connection);		
			msg = dao.confirmFnF(reqId, confRemark,distName,loginUserId);
		} 
		catch (Exception e) 
		{		
			logger.error("Exception occured :" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
	
	finally
	{							
		DBConnectionManager.releaseResources(connection);
	}
	return msg;
		
	
		
	
	}
	

}