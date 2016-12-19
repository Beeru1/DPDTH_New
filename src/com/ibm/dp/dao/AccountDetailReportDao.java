package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface AccountDetailReportDao {
	List<AccountDetailReportDto> getAccountDetailExcel(AccountDetailReportDto reportDto) throws DAOException;
	List<AccountDetailReportDto> getAccountUpdateExcel(AccountDetailReportDto reportDto) throws DAOException;
	List<SelectionCollection> getLoginIdList() throws DAOException;
	List<SelectionCollection> getAccountNameList() throws DAOException;
	int getParentId(String accountId) throws  DAOException ;
}
