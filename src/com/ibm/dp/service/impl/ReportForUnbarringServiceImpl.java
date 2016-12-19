package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ReportForUnbarringDao;
import com.ibm.dp.dao.impl.ReportForUnbarringDaoImpl;
import com.ibm.dp.service.ReportForUnbarringService;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class ReportForUnbarringServiceImpl implements ReportForUnbarringService{

	private Logger logger = Logger.getLogger(ReportForUnbarringServiceImpl.class.getName());
	public ArrayList getReportData() throws VirtualizationServiceException, DAOException{
		ArrayList serialNos = null;
		Connection con=null;
		ReportForUnbarringDao reportDao = new ReportForUnbarringDaoImpl(con);
		try{
			serialNos = reportDao.getReportData();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		return serialNos;
	}
	
	public void updateUnbarredStatus(ArrayList serialNo) throws VirtualizationServiceException,DAOException{
		Connection con=null;
		ReportForUnbarringDao reportDao = new ReportForUnbarringDaoImpl(con);
		try{
			reportDao.updateUnbarredStatus(serialNo);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
	}
}
