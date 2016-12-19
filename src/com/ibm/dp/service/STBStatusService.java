package com.ibm.dp.service;

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface STBStatusService {
	int checkSrNoAvailibility(String strNo)throws VirtualizationServiceException;
	String updateStatus(String strNos,String status)throws VirtualizationServiceException;
}
