package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.actions.DPExceptionConsumedReport;
import com.ibm.dp.actions.DPStockDetailReport;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPReportService {
	
	public ArrayList getReportNames() throws VirtualizationServiceException, DAOException;
	public ArrayList getReportData(String reportId,String lapuMobileNo) throws VirtualizationServiceException, DAOException;
	
	// Add by harbans
	public List getLevelDetails() throws VirtualizationServiceException; 
	public List getAccountDetails(int roleId, int distId) throws VirtualizationServiceException;
	public List getProductsDetails(int levelId, int accId, int distId)throws VirtualizationServiceException;
	public List<ExceptionReportDTO> getExceptionConsumedReportData(String fromDate, String toDate) throws VirtualizationServiceException;
	
	public List<StockDetailReport> getAllDistributor(long accountId,String accountLevel) throws VirtualizationServiceException;
	
	public List<StockDetailReport> getStockReportDetails(int distId) throws VirtualizationServiceException;
		
}
