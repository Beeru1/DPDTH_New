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

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for DBConnection on Data Source from
 * resource bundle
 */
public class OracleDSConnection implements DBConnection {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(OracleDSConnection.class
			.getName());

	private static OracleDSConnection oracledataSourceConnection;
	
	private static OracleDSConnection reportDataSourceConnection;

	/**
	 * Private Constructor for DSConnection
	 * 
	 */
	private OracleDSConnection() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of DSConnection
	 * 
	 * @return dataSourceConnection
	 */
	public static OracleDSConnection getInstance() {
		logger.info("Entered getInstance method for DSConnection.");

	if (oracledataSourceConnection == null) {
			oracledataSourceConnection = new OracleDSConnection();
		}
		logger
				.info("getInstance : successfully retured DSConnection instance.");

		return oracledataSourceConnection;
	}

	/**
	 * This method returns connection object for datasource name specified
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	
	
	/**
	 * This method returns instance of DSConnection
	 * 
	 * @return dataSourceConnection
	 */
	public static OracleDSConnection getreportInstance() {
		logger.info("Entered getInstance method for DSConnection.");

		if (reportDataSourceConnection == null) {
			reportDataSourceConnection = new OracleDSConnection();
		}
		logger
				.info("getInstance : successfully retured DSConnection instance.");

		return reportDataSourceConnection;
	}

	/**
	 * This method returns connection object for datasource name specified
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getDBConnection() throws DAOException {
		logger.info("Entered getOracleDBConnection method.");
		Connection connection = null;
		String dataSourceName = null;
		try{
			/* read the data source name from property file */
			dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.ORADATASOURCE_NAME);
			logger.info("dataSourceName---------"+dataSourceName);
			logger.info("DSConnection:caught VirtualizationServiceException :Oracle Db Connection");
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DSConnection:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		
		/* retrieve connection for the datasource specified in the property file */
		connection = RechargeDBConnection.getOracleDBConnection(dataSourceName);
		if (connection == null) {
			logger.fatal(" Exception occured while get connection.");
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		logger.info("Connection successfully returned.");

		return connection;
	}
	
	
	/**
	 * This method returns connection object for datasource name specified for reports
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getReportDBConnection() throws DAOException {
		logger.info("Entered getDBConnection method.");

		Connection connection = null;
		String dataSourceName = null;
		try{
			/* read the data source name from property file */
			dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.REPORT_DATASOURCE_NAME);
			
			
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DSConnection:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		

		/* retrieve connection for the datasource specified in the property file */
		connection = RechargeDBConnection.getReportDBConnection(dataSourceName);
		if (connection == null) {
			logger.fatal(" Exception occured while get connection.");
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		
			logger.info("Connection successfully returned");
		

		return connection;
	}

	public Connection getDBConnectionSnD() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
//	Added By Shilpa on 16-3-12
	/**
	 * This method returns connection object for datasource name specified for reports
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getMisReportDBConnection() throws DAOException {
		logger.info("Entered getDBConnection method.");

		Connection connection = null;
		String dataSourceName = null;
		try{
			/* read the data source name from property file */
			dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.MIS_REPORT_DATASOURCE_NAME);
			
			
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DSConnection:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		

		/* retrieve connection for the datasource specified in the property file */
		connection = RechargeDBConnection.getReportDBConnection(dataSourceName);
		if (connection == null) {
			logger.fatal(" Exception occured while get connection.");
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		
			logger.info("Connection successfully returned");
		

		return connection;
	}
}
