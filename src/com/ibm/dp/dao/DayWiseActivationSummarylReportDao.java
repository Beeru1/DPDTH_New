package com.ibm.dp.dao;

import com.ibm.dp.dto.DayWiseActivationSummaryReportDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DayWiseActivationSummarylReportDao {
	GenericReportsOutputDto getDayWiseActivationSummarylReportDao(DayWiseActivationSummaryReportDTO reportDto) throws DAOException;
	
}
