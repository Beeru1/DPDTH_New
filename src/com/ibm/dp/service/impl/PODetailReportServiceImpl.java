package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.PODetailReportDao;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dp.service.PODetailReportService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class PODetailReportServiceImpl implements PODetailReportService{

	private Logger logger = Logger.getLogger(PODetailReportServiceImpl.class.getName());
	public List<PODetailReportDto> getPoDetailExcel(PODetailReportDto reportDto) throws VirtualizationServiceException {
		Connection connection=null;
		List<PODetailReportDto> poDetailList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			PODetailReportDao poDetailDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getPoDetailReportDao(connection);
			poDetailList = poDetailDao.getPoDetailExcel(reportDto);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return poDetailList;
	}
	public List<SelectionCollection> getPoStatusList() throws  VirtualizationServiceException {
		Connection connection=null;
		List<SelectionCollection> poStatusList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			PODetailReportDao poDetailDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getPoDetailReportDao(connection);
			
			poStatusList = poDetailDao.getPoStatusList();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return poStatusList;
	}
}
