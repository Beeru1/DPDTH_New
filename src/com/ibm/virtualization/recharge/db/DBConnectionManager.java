/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
/**
 * This class provides functionality to get Database Connection based on the
 * cofiguration file, Virtualization Framework connection object is provided if
 * no configuration is defined.
 *
 * @author Paras
 *
 */
public class DBConnectionManager 
{
	public static String dataSourceType = null;
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DBConnectionManager.class.getName());

	/**
	 * This method returns Oracle connection object based on the data source type
	 * specified in the resource bundle, else Virtualization Framework
	 * connection object is provided if no connection type is specified in the
	 * configuration file.
	 *
	 * @return Connection
	 * @throws DAOException
	 */
	public static Connection getDBConnection(int db) throws DAOException 
	{
		Connection connection = null;
		if(db != 1) 
		{
			connection = getDBConnection();
		}
		else 
		{
			try 
			{
				if (dataSourceType == null) 
				{
					dataSourceType = ResourceReader.getCoreResourceBundleValue(Constants.DB_CONNECTION_TYPE);
				}
				if ("datasource".equalsIgnoreCase(dataSourceType)) 
				{
					DBConnection dbConn = OracleDSConnection.getInstance();
					connection = dbConn.getDBConnection();
				} 
				else 
				{
					DBConnection dbConn = FrameworkDBConnection.getInstance();
					connection = dbConn.getDBConnection();
				}
			}
			catch(VirtualizationServiceException virtualizationExp)
			{
				logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
				throw new DAOException(virtualizationExp.getMessage());
			}
		}
		return connection;
	}
	/**
	 * This method returns DB connection object based on the data source type
	 * specified in the resource bundle, else Virtualization Framework
	 * connection object is provided if no connection type is specified in the
	 * configuration file.
	 *
	 * @return Connection
	 * @throws DAOException
	 */
	public static Connection getDBConnection() throws DAOException 
	{
		Connection connection = null;
		try
		{
			if (dataSourceType == null) 
			{
				dataSourceType = ResourceReader.getCoreResourceBundleValue(Constants.DB_CONNECTION_TYPE);
			}
			
		}
		catch(VirtualizationServiceException virtualizationExp)
		{
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		if ("datasource".equalsIgnoreCase(dataSourceType)) 
		{
			DBConnection dbConn = DSConnection.getInstance();
			connection = dbConn.getDBConnection();
		} 
		else 
		{

			logger.info("Get Database Connection from com.ibm.virtualization.framework.db.ConnectionFactory");
			DBConnection dbConn = FrameworkDBConnection.getInstance();
			connection = dbConn.getDBConnection();
		}
		return connection;
	}

	// Add by harbans from MIS reports.
	public static Connection getDBConnectionOracleSCM() throws DAOException 
	{
		Connection connection = null;
		try
		{
			if (dataSourceType == null) 
			{
				dataSourceType = ResourceReader.getCoreResourceBundleValue(Constants.DB_CONNECTION_TYPE);
			}
		}
		catch(VirtualizationServiceException virtualizationExp)
		{
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		if ("datasource".equalsIgnoreCase(dataSourceType)) 
		{
			DBConnection dbConn = DPSCMConnection.getInstance();
			connection = dbConn.getDBConnection();
		} 
		else 
		{
			logger.info("Get Database Connection from com.ibm.virtualization.framework.db.ConnectionFactory");
			DBConnection dbConn = FrameworkDBConnection.getInstance();
			connection = dbConn.getDBConnection();
		}
		return connection;
	}


// Connecting S&D Database

	public static Connection getDBConnectionSnD() throws DAOException 
	{
		Connection connection = null;
		try
		{
			if (dataSourceType == null) 
			{
				dataSourceType = ResourceReader.getCoreResourceBundleValue(Constants.DB_CONNECTION_SND);
			}
		}
		catch(VirtualizationServiceException virtualizationExp)
		{
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		if ("datasource".equalsIgnoreCase(dataSourceType)) 
		{
			DBConnection dbConn = DSConnection.getInstance();
			connection = dbConn.getDBConnectionSnD();
		} else 
		{
			logger.info("Get Database Connection from com.ibm.virtualization.framework.db.ConnectionFactory");
			DBConnection dbConn = FrameworkDBConnection.getInstance();
			connection = dbConn.getDBConnection();
		}
		return connection;
	}

	/**
	 * This method returns connection object for datasource name specified for reports
	 *
	 * @return
	 * @throws DAOException
	 */
	public static Connection getReportDBConnection() throws DAOException 
	{

		DBConnection dbConn = DSConnection.getreportInstance();
		Connection connection = dbConn.getReportDBConnection();

		return connection;

	}

	/**
	 * This method releases the Connection
	 *
	 * @param connection
	 * @throws DAOException
	 */
	public static void releaseResources(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				//connection = null;
			}

		} catch (SQLException sqlException) {
			logger.error(
					"  Could Not Close Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
		}
	}

	/**
	 * This method releases the statement and resultset
	 *
	 * @param statement
	 * @param resultSet
	 * @throws DAOException
	 */
	public static void releaseResources(Statement statement, ResultSet resultSet) {

		try {

			if (resultSet != null) {
				/* close resultset */
				resultSet.close();
				resultSet = null;
			}
			if (statement != null) {
				/* close statement */
				statement.close();
				statement = null;
			}

		} catch (SQLException sqlException) {
			logger.error(
					"  Could Not Close ResultSet and Statement. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);

		}
	}

	/**
	 * This method commits the transaction.
	 *
	 * @param connection
	 * @throws DAOException
	 */
	public static void commitTransaction(Connection connection)
			throws DAOException {
		try {
			/* commit connection */
			connection.commit();
		} catch (SQLException sqlException) {
			logger.error(
					"commitTransaction : Could Not Commit Transaction. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
			throw new DAOException("errors.transaction.commit");
		}
	}

	/**
	 * This method releases the statement and resultset
	 *
	 * @param statement
	 * @param resultSet
	 * @throws DAOException
	 */
	public static void releaseResources(PreparedStatement statement, ResultSet resultSet) {

		try {

			if (resultSet != null) {
				/* close resultset */
				resultSet.close();
				resultSet = null;
			}
			if (statement != null) {
				/* close statement */
				statement.close();
				statement = null;
			}

		} catch (SQLException sqlException) {
			logger.error(
					"  Could Not Close ResultSet and PreparedStatement. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);

		}
	}
	/*public static Connection getMISReportDBConnection() throws DAOException {
		Connection connection = null;
		String dataSourceName = null;
		try{
			 read the data source name from property file
			dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.MIS_REPORT_DATASOURCE_NAME);


		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DSConnection:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}


		 retrieve connection for the datasource specified in the property file
		connection = RechargeDBConnection.getReportDBConnection(dataSourceName);
		logger.info("Datat SourCe Name for MIS REPORTS == "+dataSourceName);
		if (connection == null) {
			logger.fatal(" Exception occured while get connection.");
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		return connection;
	}*/

	//Added By Shilpa on 16-3-12
	public static Connection getMISReportDBConnection() throws DAOException {
		Connection connection = null;
		try{
			if (dataSourceType == null) {
				dataSourceType = ResourceReader.getCoreResourceBundleValue(Constants.MIS_REPORT_DATASOURCE_NAME);
			}
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}


		if ("datasource".equalsIgnoreCase(dataSourceType)) {
			DBConnection dbConn = DSConnection.getInstance();
			connection = dbConn.getMisReportDBConnection();
		} else {

			logger
					.info("Get Database Connection from com.ibm.virtualization.framework.db.ConnectionFactory");
			DBConnection dbConn = FrameworkDBConnection.getInstance();
			connection = dbConn.getMisReportDBConnection();
		}
		return connection;
	}

}
