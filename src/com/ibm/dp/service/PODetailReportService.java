package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface PODetailReportService {
	List<PODetailReportDto> getPoDetailExcel(PODetailReportDto reportDto) throws VirtualizationServiceException;
	List<SelectionCollection> getPoStatusList() throws  VirtualizationServiceException ;
}
