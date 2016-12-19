package com.ibm.dp.service.impl;

import java.util.List;
import java.util.Map;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.ManufacturerDetailsDao;
import com.ibm.dp.dao.TransferHierarchyDao;
import com.ibm.dp.dao.impl.ManufacturerDetailsDaoImpl;
import com.ibm.dp.dto.ManufacturerDetailsDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ManufacturerService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;


public class ManufacturerServiceImpl implements ManufacturerService{
	java.sql.Connection connection = null;
	
	
	public  List<ManufacturerDetailsDto> getManufacturerData(){	
		List<ManufacturerDetailsDto> mapReturn = null;
		try
		{	
			connection = DBConnectionManager.getDBConnection();
			ManufacturerDetailsDao mdeDao = new ManufacturerDetailsDaoImpl();
			mapReturn = mdeDao.getManufacturerDetailsDao(connection);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return mapReturn;
	}

	@Override
	public void saveManufacturerData(String manuList,String flag){
		try
		{	
		connection = DBConnectionManager.getDBConnection();
		ManufacturerDetailsDao mdDao = new ManufacturerDetailsDaoImpl();
		mdDao.saveManufacturerDao(connection,manuList,flag);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	

}
