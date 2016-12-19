/**************************************************************************
 * File     : MisReportDaoImpl.java
 * Author   : Aalok Sharma
 * Created  : Oct 3, 2008
 * Modified : Oct 3, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 3, 2008 	Aalok Sharma	First Cut.
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.reports.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.reports.daoInterface.MisReportDAOInterface;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;


/*******************************************************************************
 * This class contains methods for Mis Report
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/
public class ReportDaoImpl implements MisReportDAOInterface {

	private static final Logger logger = Logger
	.getLogger(ReportDaoImpl.class.toString());

	//	 In future this quueires will be added into ReportDAO
	/**
	 * This method updateReRunFlag() updates Report Re Run Flag information 
	 * This is used in the following conditions : 
	 * When we start the report --RE_ RUN FLAG -NO
	 * When we end the report id the force start flag is N --RERUN FLA G==Y
	 * 
	 * @param reportId
	 * @param reportStartEndFlag S start the report , E=for end report 
	 *            
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public void updateParametersForReportSchedule(int reportId, String reportStartEndFlag) throws DAOException {
		logger
				.debug("Entering method updateMISReportScheduleTime throws DAOException");
	
		Connection connection = null;
		PreparedStatement prepareStatement = null;

		try {
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if((Constants.DATA_FEED_PROCESS_START).equalsIgnoreCase(reportStartEndFlag)) {
				prepareStatement = connection.prepareStatement(MisReportDAOInterface.UPDATE_PARAMETERS_FOR_REPORT_START);
			} else if((Constants.DATA_FEED_END_PROCESS_END).equalsIgnoreCase(reportStartEndFlag)) {
				prepareStatement = connection.prepareStatement(MisReportDAOInterface.UPDATE_PARAMETERS_FOR_REPORT_END);
			}
			prepareStatement.setInt(1,reportId);
			prepareStatement.executeUpdate();
		} catch (SQLException sqe) {
			logger.debug("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.debug("Exception");
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
			logger
				.debug("Exiting method updateMISReportScheduleTime throws DAOException");

		}

	}
	
	/*
	 * This method call update schedule procedure to update the next schedule date.
	 * 
	 * Input parameters is report Id. 
	 * 
	 * @param String 
	 * parameters password @return void @exception USSDException
	 * 
	 */
	public void updateMISReportScheduleTimeProc(int reportId)
			throws DAOException {
		logger
				.debug(" Entering method updateMISReportScheduleTimeProc() -- DAO-Impl..");
		CallableStatement cs = null;
		String procedureStatus = null;
		String procedureMsg = null;
		Connection connection = null;;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			cs = connection
					.prepareCall(MisReportDAOInterface.UDATE_REPORT_NEXT_SCHEDULE_DATE);
			cs.setInt(1, reportId);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();

			procedureStatus = cs.getString(2);
			procedureMsg = cs.getString(3);
			if(!(Constants.PROC_STATUC_11111).equalsIgnoreCase(procedureStatus)) {
				logger.debug("Procedure failed reason code - " +procedureStatus +",Message =" + procedureMsg );
				throw new DAOException("Status =" + procedureStatus + ", Message=" +procedureMsg);
			}
			logger.debug(" Stored Procedure PROC_UDATE_REPORT_NEXT_SCHEDULE_DATE_V0 executed.. ");
			
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.debug("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception");
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, cs);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
			logger
				.debug("Exiting method updateMISReportScheduleTimeProc throws DAOException");

		}
	}
	
}
