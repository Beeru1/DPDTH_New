package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.DPSCMConsumptionReportDto;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.SCMConsumptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dp.dto.OpenStockDepleteDTO;
 
public interface DPReverseLogisticReportDao 
{
	public List getRevLogTsmAccountDetails( int levelId) throws DAOException; 
	public List getRevLogTsmDistLogin( int distId) throws DAOException; 
	public List getRevLogDistAccountDetails( int levelId , int circleId) throws DAOException;
	public List<DPReverseLogisticReportDTO> getRevLogReportExcel(int distId , String fromDate, String toDate , String circleId , String tsmId) throws DAOException;
	
	//added by saumya for po status report
	public List<DPReverseLogisticReportDTO> getPoReportExcel(int distId , String fromDate, String toDate , String circleId , String tsmId) throws DAOException;
	
	public List<DPReverseLogisticReportDTO> getRevLogReportDetailExcel(int distId , String fromDate, String toDate , String circleId , String tsmId) throws DAOException;
	public List<DPReverseLogisticReportDTO> getlastPOReportExcel(int distId , String circleId , String tsmId) throws DAOException;
	public List<DPReverseLogisticReportDTO> getInHandQtyReportExcel(int distId , String circleId , String tsmId, String productId) throws DAOException;

//	 Add by harbans on MIS Reports consumption
	
	public List<DPSCMConsumptionReportDto> getConsumptionReportExcelPanIndia(int distId , int tsmId, String fromDate, String toDate) throws DAOException;
	public List<DPSCMConsumptionReportDto> getConsumptionReportExcel(int distId , int tsmId, int circleId, String fromDate, String toDate) throws DAOException;
	public List<DPReverseLogisticReportFormBean> getRevLogDistAccountDetails(int roleId, int circleId, long accountID) throws DAOException;

}
