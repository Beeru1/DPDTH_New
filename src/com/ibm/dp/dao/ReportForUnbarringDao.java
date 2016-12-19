package com.ibm.dp.dao;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.exception.DAOException;

public interface ReportForUnbarringDao {

	public ArrayList getReportData() throws DAOException;
	public void updateUnbarredStatus(ArrayList serialNo) throws DAOException;
}
