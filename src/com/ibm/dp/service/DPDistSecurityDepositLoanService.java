package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPDistSecurityDepositLoanDto;
import com.ibm.dp.exception.DPServiceException;


public interface DPDistSecurityDepositLoanService {

	List uploadExcel(InputStream inputstream) throws DPServiceException;
	String addSecurityLoan(List list,String userId) throws DPServiceException;
	public List<DPDistSecurityDepositLoanDto> getALLDistSecurityLoan() throws DPServiceException;
}
