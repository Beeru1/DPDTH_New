package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface AccountDetailReportService {
	List getAccountDetailExcel(AccountDetailReportDto reportDto,String reportName) throws VirtualizationServiceException;
	List<SelectionCollection> getLoginIdList() throws  VirtualizationServiceException ;
	List<SelectionCollection> getAccountNameList() throws  VirtualizationServiceException ;
	int getParentId(String accountId) throws  VirtualizationServiceException ;
}
