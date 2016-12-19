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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * The DBConnection class is used to get a DB connection using a datasource This
 * class can be used to connect to any datasource, not just Oracle, so the name
 * is a misnomer for this version In case you want this class to use in your
 * project, replace BarringException with your own exception class
 * 
 * @author Rohit Dhal, adapted by Puneet Lakhina for Code Generator, adapted by
 *         Paras for Connection Factory Implementation in Recharge Project.
 * @date 21-Mar-2007
 */

public class RechargeDBConnection {
	/*
	 * Logger for the class.
	 */
	private static Logger logger = Logger.getLogger(RechargeDBConnection.class
			.getName());

	private static DataSource datasource;
	private static DataSource snddatasource;
	private static DataSource oracledatasource;
	
	
	
	private static DataSource reportingDatasource;

	/**
	 * The default private constructor.Use getDBConnection() to get a connection
	 * 
	 */
	private RechargeDBConnection() {
	}

	/**
	 * This method does the lookup of the datasource and store it in a memeber
	 * variable to be used later , to avoid doing lookups again and again
	 * 
	 * @exception DAOException
	 *                This exception is thrown in case data source cannot be
	 *                looked up
	 */
	private static void getDataSource(String jndiName) throws DAOException {
		logger.info("getDataSource : Entered getDataSource().");
		try {
			synchronized (RechargeDBConnection.class) {
				if (datasource == null) {
					String lookupname = jndiName;
					logger.info("================ jndiname in rechargeDBConneciton class ::::"+jndiName+"  lookupname:"+lookupname);
					InitialContext context = new InitialContext();
					datasource = (DataSource) context.lookup(lookupname);
				}
			}
			logger.info("getDataSource : Lookup of Data Source successful.");
		} catch (NamingException namingException) {
			logger
					.error("getDataSource : Lookup of Data Source Failed. Reason:"
							+ namingException.getMessage());
			throw new DAOException("errors.dbconnection.create_connection");
		}
	}
	
	private static void getSnDDataSource(String jndiName) throws DAOException {
		logger.info("getDataSource : Entered getDataSource().");
		try {
			synchronized (RechargeDBConnection.class) {
				if (snddatasource == null) {
					String lookupname = jndiName;
					InitialContext context = new InitialContext();
					snddatasource = (DataSource) context.lookup(lookupname);
				}
			}
			logger.info("getDataSource : Lookup of Data Source successful.");
		} catch (NamingException namingException) {
			logger
					.error("getDataSource : Lookup of Data Source Failed. Reason:"
							+ namingException.getMessage());
			throw new DAOException("errors.dbconnection.create_connection");
		}
	}
	
	
	private static void getOracleDataSource(String jndiName) throws DAOException {
		logger.info("getDataSource : Entered getDataSource().");
		try {
			synchronized (RechargeDBConnection.class) {
				if (oracledatasource == null) {
					String lookupname = jndiName;
					InitialContext context = new InitialContext();
					oracledatasource = (DataSource) context.lookup(lookupname);
				}
			}
			logger.info("getDataSource : Lookup of Data Source successful.");
		} catch (NamingException namingException) {
			logger
					.error("getDataSource : Lookup of Data Source Failed. Reason:"
							+ namingException.getMessage());
			throw new DAOException("errors.dbconnection.create_connection");
		}
	}
	/**
	 * This method does the lookup of the datasource and store it in a memeber
	 * variable to be used later , to avoid doing lookups again and again
	 * 
	 * @exception DAOException
	 *                This exception is thrown in case data source cannot be
	 *                looked up
	 */
	private static void getReportingDataSource(String jndiName) throws DAOException {
		logger.info("getDataSource : Entered getDataSource().");
		try {
			synchronized (RechargeDBConnection.class) {
				if (reportingDatasource == null) {
					String lookupname = jndiName;
					InitialContext context = new InitialContext();
					reportingDatasource = (DataSource) context.lookup(lookupname);
					
				}
			}
			logger.info("getDataSource : Lookup of Data Source successful.");
		} catch (NamingException namingException) {
			logger
					.error("getDataSource : Lookup of Data Source Failed. Reason:"
							+ namingException.getMessage());
			throw new DAOException("errors.dbconnection.create_connection");
		}
	}

	/**
	 * This method returns the db connection using a datasource
	 * 
	 * @return Connection the db connection instance
	 * @exception DAOException
	 *                This exception is thrown in case connection is not
	 *                established
	 */
	public static Connection getDBConnection(String jndiName)
			throws DAOException {

		//logger.info("Request for connection received");
		Connection dbConnection = null;
		try {
			if (datasource == null) {
				getDataSource(jndiName);
			}
			dbConnection = datasource.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			logger.error(
					"Could Not Obtain Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
			throw new DAOException("errors.dbconnection.create_connection");
		}
		//logger.info("********* Connection Obtained ***********");
		return dbConnection;
	}
	
	public static Connection getDBConnectionSnD(String jndiName)
	throws DAOException {
		logger.info("Request for connection received");
		Connection dbConnection = null;
		try {
			if (snddatasource == null) {
				getSnDDataSource(jndiName);
			}
			dbConnection = snddatasource.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException sqlException) {
			logger.error(
					"Could Not Obtain Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
			throw new DAOException("errors.dbconnection.create_connection");
		}
		logger.info("Connection Obtained.");
		return dbConnection;
	}
	
	
	public static Connection getOracleDBConnection(String jndiName)
				throws DAOException {
			logger.info("Request for connection received");
			Connection dbConnection = null;
			try {
				if (oracledatasource == null) {
					getOracleDataSource(jndiName);
				}
				dbConnection = oracledatasource.getConnection();
				dbConnection.setAutoCommit(false);
			} catch (SQLException sqlException) {
				logger.error(
						"Could Not Obtain Connection. Reason:"
								+ sqlException.getMessage() + ". Error Code:"
								+ sqlException.getErrorCode(), sqlException);
				throw new DAOException("errors.dbconnection.create_connection");
			}
			logger.info("Connection Obtained.");
			return dbConnection;
	}

	/**
	 * This method returns the db connection using a datasource
	 * 
	 * @return Connection the db connection instance
	 * @exception DAOException
	 *                This exception is thrown in case connection is not
	 *                established
	 */
	public static Connection getReportDBConnection(String jndiName)
			throws DAOException {
		logger.info("Request for connection received");
		Connection dbConnection = null;
		try {
			if (reportingDatasource == null) {
				getReportingDataSource(jndiName);
			}
			dbConnection = reportingDatasource.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException sqlException) {
			logger.error(
					"Could Not Obtain Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
			throw new DAOException("errors.dbconnection.create_connection");
		}
		logger.info("Connection Obtained.");
		return dbConnection;
	}

	
	public static void releaseResources(Connection connection,
			Statement statement, ResultSet resultSet) throws DAOException {
		try {

			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}

		} catch (SQLException sqlException) {
			logger.error(
					"Could Not Close Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
			throw new DAOException("errors.dbconnection.close_connection");
		}
	}
	//Added by SHilpa on 16-3-12
	/**
	 * This method returns the db connection using a datasource
	 * 
	 * @return Connection the db connection instance
	 * @exception DAOException
	 *                This exception is thrown in case connection is not
	 *                established
	 */
	public static Connection getMISReportDBConnection(String jndiName)
			throws DAOException {
		logger.info("Request for connection received");
		Connection dbConnection = null;
		try {
			if (reportingDatasource == null) {
				getReportingDataSource(jndiName);
			}
			dbConnection = reportingDatasource.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException sqlException) {
			logger.error(
					"Could Not Obtain Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode(), sqlException);
			throw new DAOException("errors.dbconnection.create_connection");
		}
		logger.info("Connection Obtained.");
		return dbConnection;
	}


}
