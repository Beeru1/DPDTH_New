package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.DPSCMConsumptionReportDto;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPReverseLogisticReportService {
	
	// Add by harbans
	public List getRevLogAccountDetails(int roleId, int distId, int tsmId) throws VirtualizationServiceException;
	public List getRevLogDistAccountDetails(int roleId ,  int circleId) throws VirtualizationServiceException;
	public List<DPReverseLogisticReportDTO> getRevLogReportExcel(int distId , String fromDate, String toDate , String circleId , String tsmId) throws VirtualizationServiceException;
	
	//added by saumya for po status report
	public List<DPReverseLogisticReportDTO> getPoReportExcel(int distId , String fromDate, String toDate , String circleId , String tsmId) throws VirtualizationServiceException;
	
	public List<DPReverseLogisticReportDTO> getRevLogReportDetailExcel(int distId , String fromDate, String toDate , String circleId , String tsmId) throws VirtualizationServiceException;
	public List getRevLogTsmAccountDetails(int roleId)throws VirtualizationServiceException;
	public List getRevLogTsmDistLogin(int distId)throws VirtualizationServiceException;
	public List<DPReverseLogisticReportDTO> getlastPOReportExcel(int distId , String circleId , String tsmId) throws VirtualizationServiceException;
	public List<DPReverseLogisticReportDTO> getInHandQtyReportExcel(int distId , String circleId , String tsmId, String productId) throws VirtualizationServiceException;
	
	public List<DPSCMConsumptionReportDto> getConsumptionReportExcel( int distId ,  int tsmId, int circleId, String fromDate, String toDate) throws VirtualizationServiceException;
	public List getRevLogDistAccountDetails(int roleId, int circleId, long accountID) throws VirtualizationServiceException;
}
