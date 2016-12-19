package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.InterSSDStockTrnfrReportDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface InterSSDStockTrnfrReportService {
	public List<InterSSDStockTrnfrReportDto> getReportExcel(String circleId , String fromDate,String toDate,int loginRole,String loginCircleId) throws VirtualizationServiceException, DPServiceException;
}
