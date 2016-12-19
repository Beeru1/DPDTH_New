package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPAlertConfigurationBean;
import com.ibm.dp.dao.DPAlertConfigurationDAO;
import com.ibm.dp.dao.DPReportDao;
import com.ibm.dp.dao.NonSerializedActivationDao;
import com.ibm.dp.dao.impl.DPAlertConfigurationDAOImpl;
import com.ibm.dp.dao.impl.DPReportDaoImpl;
import com.ibm.dp.dao.impl.NonSerializedActivationDaoImpl;
import com.ibm.dp.dto.AlertConfigurationDTO;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPAlertConfigurationService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPAlertConfigurationServiceImpl implements DPAlertConfigurationService {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(DPAlertConfigurationServiceImpl.class.getName());
	Connection connection = null;
	public List<AlertConfigurationDTO> getAlertList() throws DPServiceException
	{ 
		//***to get alert list for dthadmin
		List<AlertConfigurationDTO> list = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);	
				list = dao.getAlertList();
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
	
	public int updateStatus(HttpServletRequest request, List<AlertConfigurationDTO> alertList) throws DPServiceException
	{
		
		int idStatuschanged[] = new int[alertList.size()];
		boolean changedStatus[] = new boolean[alertList.size()];
		int j =0;
		int updateStatus=0;
		
		for(int i=0;i<alertList.size();i++)
		{
			String alertIdName="alertId"+i;
			String statusid="status"+i;
			logger.info(" Alert ID :: "+request.getParameter(alertIdName)+"  :  Status = "+request.getParameter(statusid));
			
			if ( ! request.getParameter(statusid).equals(alertList.get(i).isEnableFlag()+"") )
			{
			logger.info(request.getParameter(statusid)+" : "+alertList.get(i).isEnableFlag());
			    idStatuschanged[j] = alertList.get(i).getAlertId();
			    
			    if("true".equals(request.getParameter(statusid)+""))
			    	changedStatus[j++] = true;
			    else
			    	changedStatus[j++] = false;
			}
		}
		
		for(int i=0; i<idStatuschanged.length; i++)
		{
			if(idStatuschanged[i] >0)
			{
			logger.info("alert IDs :: "+idStatuschanged[i] +" New Status :: "+changedStatus[i]);
			}
		}

		
		try {
			connection = DBConnectionManager.getDBConnection();
			DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
			updateStatus =  dao.updateAlertStatus(idStatuschanged,changedStatus);
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
	
	return updateStatus;
	}
	
	public List<AlertConfigurationDTO> getuserGroupId(int groupId , String loginId)throws DPServiceException
	{

		List<AlertConfigurationDTO> list = null;
			
			try {
				connection = DBConnectionManager.getDBConnection();
				DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
				list = dao.getuserGroupId(groupId,loginId);
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

	
	public int otherupdateStatus(int[] alertId, int[] status, List<AlertConfigurationDTO> userGroupId,String loginId) throws DPServiceException
	{
		int updateStatus =0;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
			updateStatus =  dao.otherupdateStatus(alertId,status,userGroupId,loginId);
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
	
	return updateStatus;
	
	}

	public AlertConfigurationDTO getTsmGracePeriod() throws DPServiceException 
	{
		AlertConfigurationDTO obj =null;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
			obj =  dao.getTsmGracePeriod();
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
	
	
	return obj;
	}

	public AlertConfigurationDTO getZsmGracePeriod() throws DPServiceException 
	{
		AlertConfigurationDTO obj1 =null;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
			obj1 =  dao.getZsmGracePeriod();
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
	
	
	return obj1;
	}

	public AlertConfigurationDTO updateTsmGracePeriod(String tsmGracePeriod) throws DPServiceException
	{
		AlertConfigurationDTO obj =null;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
			obj =  dao.updateTsmGracePeriod(tsmGracePeriod);
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
	
	
	return obj;
	}

	public AlertConfigurationDTO updateZsmGracePeriod(String zsmGracePeriod) throws DPServiceException {

		AlertConfigurationDTO obj1 =null;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPAlertConfigurationDAO dao = new DPAlertConfigurationDAOImpl(connection);		
			obj1 =  dao.updateZsmGracePeriod(zsmGracePeriod);
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
	
	
	return obj1;
	}
	
	
}
