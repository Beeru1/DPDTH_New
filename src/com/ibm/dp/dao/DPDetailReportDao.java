package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.DPDetailReportDTO;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.SCMConsumptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dp.dto.OpenStockDepleteDTO;
 
public interface DPDetailReportDao 
{
	public List<DPDetailReportDTO> getDetailReportExcel(String fromDate, String toDate) throws DAOException;
}

