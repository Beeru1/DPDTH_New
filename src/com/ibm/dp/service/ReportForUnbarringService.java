package com.ibm.dp.service;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface ReportForUnbarringService {
	public ArrayList getReportData() throws VirtualizationServiceException, DAOException;
	public void updateUnbarredStatus (ArrayList serialNo) throws VirtualizationServiceException,DAOException;

}
