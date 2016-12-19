/**************************************************************************
 * File     : FTADataFetchServiceDAO.java
 * Author   : Banita
 * Created  : Nov 04, 2008
 * Modified : Nov 04, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Nov 04, 2008 	Banita	First Cut.
 * V0.2		Nov 04, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.reports.dao;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.FTAReportServiceInterface;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;

/*******************************************************************************
 * This class is used to fetch or update the dates.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class FTADataFetchServiceDAO {

	private static final Logger logger = Logger
	.getLogger(FTADataFetchServiceDAO.class);

	/**
	 * This method getCurrentDate() makes the connection with database and
	 * fetches the current date
	 * 
	 * @return currentDate:current date from database in string
	 */
	public String getCurrentDate() throws DAOException {
		logger.debug("Entering method getCurrentDate()");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String currentDate = "";
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(FTAReportServiceInterface.GET_CURRENT_DATE);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				currentDate = USSDCommonUtility.getTimestampAsString(resultSet
						.getTimestamp(1), "dd-MM-yyyy-HH-mm-ss");

			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting method getCurrentDate()");
		return currentDate;
	}

	/**
	 * This method getLastModifiedDate() makes the connection with the database
	 * that fetches the last modified date on the basis of parameter provided
	 * 
	 * @param type :
	 *            filter to fetch the date which signifies whether its for user
	 *            or location
	 * 
	 * @return modifiedDate: date from database in string
	 */
	public String getLastModifiedDate(String type) throws DAOException {
		logger.debug("Entering method getLastModifiedDate()");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String modifiedDate = "";
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(FTAReportServiceInterface.GET_LAST_MODIFY_DATE);
			if (type != null) {
				type = type.toUpperCase();
			}
			prepareStatement.setString(1, type);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				modifiedDate = USSDCommonUtility.getTimestampAsString(resultSet
						.getTimestamp("LAST_MODIFIED_DT"),
				"dd-MM-yyyy-HH-mm-ss");
			}

		} catch (BatchUpdateException bExp) {
			logger.error("SQL exception encountered: "
					+ bExp.getNextException(), bExp);
			throw new DAOException(bExp.getMessage(), bExp);
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting method getLastModifiedDate()");
		return modifiedDate;
	}

	/**
	 * This method updateLastModifiedDate() makes the connection with the
	 * database that updates the date with the current date
	 * 
	 * @param connection
	 * @param type :
	 *            for which the data has to be updated
	 * @param currentDate :
	 *            update the data with this date
	 * 
	 */
	public void updateLastModifiedDate(Connection connection, String name,
			String currentDate) throws DAOException {
		logger.debug("Entering method updateLastModifiedDate()");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String modifiedDate = new String(currentDate);
			Date dt = sdf.parse(modifiedDate);
			Timestamp timestamp = new Timestamp(dt.getTime());
			prepareStatement = connection
			.prepareStatement(FTAReportServiceInterface.UPDATE_LAST_DATE);
			prepareStatement.setTimestamp(1, timestamp);
			if (name != null) {
				name = name.toUpperCase();
			}
			prepareStatement.setString(2, name);
			prepareStatement.executeUpdate();
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(null, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting method updateLastModifiedDate()");
	}

}
