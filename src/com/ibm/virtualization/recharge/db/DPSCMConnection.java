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
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for DBConnection on Data Source from
 * resource bundle
 * 
 * @author Paras
 * 
 */
public class DPSCMConnection implements DBConnection 
{

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPSCMConnection.class.getName());

	private static DPSCMConnection dataSourceConnection;
	

	/**
	 * Private Constructor for DPSCMConnection
	 * 
	 */
	private DPSCMConnection() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of DSConnection
	 * 
	 * @return dataSourceConnection
	 */
	public static DPSCMConnection getInstance() {
		if (dataSourceConnection == null) {
			dataSourceConnection = new DPSCMConnection();
		}
		return dataSourceConnection;
	}

	/**
	 * This method returns instance of DSConnection
	 * 
	 * @return dataSourceConnection
	 */
	public static DSConnection getreportInstance() {return null;}

	/**
	 * This method returns connection object for datasource name specified
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getDBConnection() throws DAOException {
		Connection connection = null;
		String dataSourceName = null;
		try{
			/* read the data source name from property file */
			dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.DATASOURCE_NAME_SCM);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DSConnection:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		
		/* Retrieve connection for the datasource specified in the property file */
		connection = RechargeDBConnection.getOracleDBConnection(dataSourceName);
		if (connection == null) 
		{
			logger.fatal(" Exception occured while get connection.");
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		return connection;
	}
		
	
	
	/**
	 * This method returns connection object for datasource name specified for reports
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getReportDBConnection() throws DAOException {	return null; };
	
	/**
	 * This method returns connection SND object for datasource name specified for reports
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getDBConnectionSnD() throws DAOException {	return null; };
	
//	Added By Shilpa on 16-3-12
	/**
	 * This method returns connection object for datasource name specified
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getMisReportDBConnection() throws DAOException {
		Connection connection = null;
		String dataSourceName = null;
		try{
			/* read the data source name from property file */
			dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.MIS_REPORT_DATASOURCE_NAME);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DSConnection:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		
		/* Retrieve connection for the datasource specified in the property file */
		connection = RechargeDBConnection.getOracleDBConnection(dataSourceName);
		if (connection == null) 
		{
			logger.fatal(" Exception occured while get connection.");
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		return connection;
	}
}
