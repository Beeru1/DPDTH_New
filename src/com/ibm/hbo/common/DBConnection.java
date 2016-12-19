/*
 * Created on Oct 17, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ibm.hbo.exception.DAOException;
import com.ibm.utilities.PropertyReader;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public class DBConnection {
	/*
		 * Logger for the class.
		 */
	private static final Logger logger;

	static {

		logger = Logger.getLogger(DBConnection.class);
	}
	private static DataSource datasource;

	/**
	 * The default private constructor.Use getDBConnection() to get a connection
	 *
	 */
	private DBConnection() {
	}
	/**
	 * This method does the lookup of the datasource and store it in a memeber variable to be 
	 * used later , to avoid doing lookups again and again
	 * @exception DAOException This exception is thrown in case data source cannot be looked up
	 */
	private static void getDataSource() throws DAOException {
		logger.info("getDataSource : Entered getDataSource().");
		try {
			synchronized (DBConnection.class) {
				if (datasource == null) {
					//Reading the data source name from a property file.
					InitialContext loc_o_ic = new InitialContext();
					datasource =
						(DataSource) loc_o_ic.lookup(
							PropertyReader.getValue("DATASOURCE_LOOKUP_NAME"));

				}
			}
			logger.info("getDataSource : Lookup of Data Source successful.");
		} catch (NamingException namingException) {
			logger.error(
				"getDataSource : Lookup of Data Source Failed. Reason:"
					+ namingException.getMessage());
			throw new DAOException("errors.dbconnection.create_connection");
		}
	}
	
// Method to form connection to S&D Database
	
	private static void getDataSourceSnD() throws DAOException {
		logger.info("getDataSource : Entered getDataSource().");
		try {
			synchronized (DBConnection.class) {
				if (datasource == null) {
					//Reading the data source name from a property file.
					InitialContext loc_o_ic = new InitialContext();
					datasource =
						(DataSource) loc_o_ic.lookup(
							PropertyReader.getValue("DATASOURCE_S&D"));

				}
			}
			logger.info("getDataSource : Lookup of Data Source successful.");
		} catch (NamingException namingException) {
			logger.error(
				"getDataSource : Lookup of Data Source Failed. Reason:"
					+ namingException.getMessage());
			throw new DAOException("errors.dbconnection.create_connection");
		}
	}
	/**
	 * This method returns the db connection using a datasource
	 * @return Connection the db connection instance
	 * @exception DAOException This exception is thrown in case connection is not established
	 */
	public static Connection getDBConnection() throws DAOException {
		logger.info("getDBConnection : Request for connection received");
		Connection dbConnection = null;
		try {
			if (datasource == null) {
				getDataSource();
			}
			dbConnection = datasource.getConnection();

		} catch (SQLException sqlException) {
			logger.info(
				"getDBConnection : Could Not Obtain Connection. Reason:"
					+ sqlException.getMessage()
					+ ". Error Code:"
					+ sqlException.getErrorCode());
			throw new DAOException("errors.dbconnection.create_connection");
		}
		logger.info("getDBConnection : Connection Obtained.");
		return dbConnection;
	}
	
// get S&D connection
	
	public static Connection getDBConnectionSnD() throws DAOException {
		logger.info("getDBConnection : Request for connection received");
		Connection dbConnection = null;
		try {
			if (datasource == null) {
				getDataSourceSnD();
			}
			dbConnection = datasource.getConnection();

		} catch (SQLException sqlException) {
			logger.info(
				"getDBConnection : Could Not Obtain Connection. Reason:"
					+ sqlException.getMessage()
					+ ". Error Code:"
					+ sqlException.getErrorCode());
			throw new DAOException("errors.dbconnection.create_connection");
		}
		logger.info("getDBConnection : Connection Obtained.");
		return dbConnection;
	}

	public static void releaseResources(
		Connection connection,
		Statement statement,
		ResultSet resultSet)
		throws DAOException {
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
				"getDBConnection : Could Not Close Connection. Reason:"
					+ sqlException.getMessage()
					+ ". Error Code:"
					+ sqlException.getErrorCode());
			throw new DAOException("errors.dbconnection.close_connection");
		}

	}
}
