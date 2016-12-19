package com.ibm.dp.service.impl;

import java.sql.Connection;

import com.ibm.dp.dao.CannonDPDataDAO;
import com.ibm.dp.service.CannonDPDataService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class CannonDPDataServiceImpl implements CannonDPDataService{

	@Override
	public String updateCannonData(String recId, String serialNo, String distOlmId, String itemCode, String assignedDate, String customerId, String status, String stbType, String transactionType, String modelManKey, String modelManKeyOld, String userId,String password)
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		
		String strServiceMsg = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			CannonDPDataDAO canonDPDatadao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCannonDPDataDAO(connection);
		//CannonDPDataDAO canonDPDatadao = new CannonDPDataDAOImpl();
			strServiceMsg= canonDPDatadao.updateCannonData(recId, serialNo, distOlmId, itemCode, assignedDate, customerId, status, stbType, transactionType, modelManKey, modelManKeyOld, userId,password);
			//connection.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return strServiceMsg;
	}

	@Override
	public String updateCannonInventory(String recordId,String defectiveSerialNo,String defectiveStbType,String newSerialNo,String newStbType,String inventoryChangeDate,String inventoryChangeType,String distributorOlmId,String status,String errorMsg,String customerId,int  defectId,String modelManKey,String modelManKeyOld,String userid,String password)throws VirtualizationServiceException{
		String strServiceMsg = null;
		Connection connection = null;
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			CannonDPDataDAO canonDPDatadao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCannonDPDataDAO(connection);
		//CannonDPDataDAO canonDPDatadao = new CannonDPDataDAOImpl();
			strServiceMsg = canonDPDatadao.updateCannonInventory(recordId, defectiveSerialNo, defectiveStbType, newSerialNo, newStbType, inventoryChangeDate, inventoryChangeType, distributorOlmId, status, errorMsg, customerId,  defectId, modelManKey, modelManKeyOld, userid, password);
			//connection.commit();
		}
		catch(Exception ex){
			
		}
		
		return strServiceMsg;
	}

}
