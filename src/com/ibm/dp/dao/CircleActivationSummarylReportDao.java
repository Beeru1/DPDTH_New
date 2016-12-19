package com.ibm.dp.dao;

import com.ibm.dp.dto.CircleActivationSummaryReportDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface CircleActivationSummarylReportDao {
	GenericReportsOutputDto getCircleActivationSummaryDetailExcel(CircleActivationSummaryReportDTO reportDto) throws DAOException;
	
}
