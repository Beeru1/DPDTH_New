package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.DPDetailReportDTO;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.SCMConsumptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.dp.dto.OpenStockDepleteDTO;

public interface DPDetailReportService {
	public List<DPDetailReportDTO> getdetailReportExcel(String fromDate, String toDate) throws VirtualizationServiceException;
		
}

