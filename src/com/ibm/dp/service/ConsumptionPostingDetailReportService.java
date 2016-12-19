package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface ConsumptionPostingDetailReportService {
	public List<DistributorDTO> getAllDistList(String strLoginId,String stracLevel) throws DPServiceException, VirtualizationServiceException ;
	public List<ConsumptionPostingDetailReportDto> getReportExcel(int distId , String fromDate,String toDate, String circleId,int selectedCircleId,String strTSMId) throws VirtualizationServiceException, DPServiceException;
	public List getRevLogTsmAccountDetails(int roleId)throws VirtualizationServiceException;
	public List getRevLogDistAccountDetails(int roleId ,  int circleId) throws VirtualizationServiceException;
	
}
