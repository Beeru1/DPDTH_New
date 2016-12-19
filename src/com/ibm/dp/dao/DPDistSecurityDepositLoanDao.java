package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPDistSecurityDepositLoanDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPDistSecurityDepositLoanDao {

	List uploadExcel(InputStream inputstream) throws DAOException;
	String addSecurityLoan(List list,String userId) throws DAOException;
	List<DPDistSecurityDepositLoanDto> getALLDistSecurityLoan() throws DPServiceException;
}
