package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DayWiseActivationSummarylReportDao;
import com.ibm.dp.dto.DayWiseActivationSummaryReportDTO;
import com.ibm.dp.service.DayWiseActivationSummaryReportService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DayWiseActivationSummaryReportServiceImpl implements DayWiseActivationSummaryReportService{
    
	private Logger logger = Logger.getLogger(DayWiseActivationSummaryReportServiceImpl.class.getName());
	public GenericReportsOutputDto getAccountDetailExcel(DayWiseActivationSummaryReportDTO reportDto) throws VirtualizationServiceException {
		System.out.println("service implement");
		Connection connection=null;
		GenericReportsOutputDto genericReportsOutputDto = null;
		try
		{ 
			logger.info("************************************************************IN SERVICE IMPL*******************************************************");
			connection =  DBConnectionManager.getMISReportDBConnection();
			DayWiseActivationSummarylReportDao accountDetailReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDayWiseActivationSummarylReportDao(connection);
			genericReportsOutputDto = accountDetailReportDao.getDayWiseActivationSummarylReportDao(reportDto);
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
