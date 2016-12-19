package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.actions.DPExceptionConsumedReport;
import com.ibm.dp.actions.DPStockDetailReport;
import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.dao.DPReportDao;
import com.ibm.dp.dao.impl.DPReportDaoImpl;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.dp.service.DPReportService;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


public class DPReportServiceImpl implements DPReportService {
	
	private Logger logger = Logger.getLogger(DPCreateBeatServiceImpl.class.getName());
	
	public ArrayList getReportNames() throws VirtualizationServiceException{
		Connection con=null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		ArrayList reportNamesList = null;
		try{
			reportNamesList = reportDao.getReportNames();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return reportNamesList;
	}
	
	public ArrayList getReportData(String reportId,String lapuMobileNo) throws VirtualizationServiceException{
		Connection con=null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		ArrayList reportDataList = null;
		try{
			reportDataList = reportDao.getReportData(reportId,lapuMobileNo);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return reportDataList;
	}
	
	// Add by harbans
	public List getLevelDetails() throws VirtualizationServiceException
	{
		Connection con=null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		List<InventoryStatusBean> levelDetailList = null;
		try
		{
			levelDetailList = reportDao.getLevelDetails();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return levelDetailList;
	}
	
	// Add by harbans
	public List getAccountDetails(int distId, int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		List<InventoryStatusBean> accDetailList = null;
		try
		{
			accDetailList = reportDao.getAccountDetails(distId, levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return accDetailList;
	}
	
//	 Add by harbans
	public List getProductsDetails(int levelId, int accId, int distId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		List<InventoryStatusBean> accDetailList = null;
		try
		{
			accDetailList = reportDao.getProductsDetails(levelId, accId, distId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return accDetailList;
	}
	
	public List<ExceptionReportDTO> getExceptionConsumedReportData(String fromDate, String toDate) throws VirtualizationServiceException
	{
		Connection con = null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		List<ExceptionReportDTO> exceptionReportList = new ArrayList<ExceptionReportDTO>();
		try
		{
			exceptionReportList = reportDao.getExceptionConsumedReportData(fromDate, toDate);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		return exceptionReportList;
		
	}
	
	public List<StockDetailReport> getAllDistributor(long accountId,String accountLevel) throws VirtualizationServiceException
	{
		Connection con = null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		List<StockDetailReport> stockDetailReport = new ArrayList<StockDetailReport>();
		try
		{
			stockDetailReport = reportDao.getAllDistributor(accountId, accountLevel);
			
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		return stockDetailReport;
	}
	
	//	 Stock Report Dtails
	public List<StockDetailReport> getStockReportDetails(int distId) throws VirtualizationServiceException
	{
		Connection con = null;
		DPReportDao reportDao = new DPReportDaoImpl(con);
		List<StockDetailReport> stockDetailReport = new ArrayList<StockDetailReport>();
		try
		{
			stockDetailReport = reportDao.getStockDetailsReport(distId);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		return stockDetailReport;
		
	}
}