package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.CircleActivationSummaryReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface CircleActivationSummaryReportService {
	GenericReportsOutputDto getAccountDetailExcel(CircleActivationSummaryReportDTO reportDto) throws VirtualizationServiceException;
	List<SelectionCollection> getLoginIdList() throws  VirtualizationServiceException ;
	List<SelectionCollection> getAccountNameList() throws  VirtualizationServiceException ;
	int getParentId(String accountId) throws  VirtualizationServiceException ;
}
