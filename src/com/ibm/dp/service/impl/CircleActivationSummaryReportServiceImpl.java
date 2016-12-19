package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.CircleActivationSummarylReportDao;
import com.ibm.dp.dto.CircleActivationSummaryReportDTO;
import com.ibm.dp.service.CircleActivationSummaryReportService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class CircleActivationSummaryReportServiceImpl implements CircleActivationSummaryReportService{
    
	private Logger logger = Logger.getLogger(CircleActivationSummaryReportServiceImpl.class.getName());
	public GenericReportsOutputDto getAccountDetailExcel(CircleActivationSummaryReportDTO reportDto) throws VirtualizationServiceException {
		System.out.println("service implement");
		Connection connection=null;
		GenericReportsOutputDto genericReportsOutputDto = null;
		try
		{ 
			logger.info("************************************************************IN SERVICE IMPL*******************************************************");
			connection = DBConnectionManager.getDBConnection();
			CircleActivationSummarylReportDao accountDetailReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCircleActivationSummarylReportDao(connection);
			genericReportsOutputDto = accountDetailReportDao.getCircleActivationSummaryDetailExcel(reportDto);
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
		return genericReportsOutputDto;
	}
	public List<SelectionCollection> getAccountNameList()
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public List<SelectionCollection> getLoginIdList()
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public int getParentId(String accountId)
			throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
