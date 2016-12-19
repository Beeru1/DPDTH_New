package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.actions.DPStockDetailReport;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPReportDao 
{
	public ArrayList getReportNames() throws DAOException;
	public ArrayList getReportData(String reportId,String lapuMobileNo) throws DAOException;
	
	// Add by harbans
	public List getLevelDetails() throws DAOException;
	public List getAccountDetails(int distId, int levelId) throws DAOException;
	public List getProductsDetails(int levelId, int accountId , int distId) throws DAOException;
	public List<ExceptionReportDTO> getExceptionConsumedReportData(String fromDate, String toDate) throws DAOException;
	public List<StockDetailReport> getStockDetailsReport(int distId) throws DAOException;
	public List<StockDetailReport> getAllDistributor(long accountId,String accountLevel) throws DAOException;
}
