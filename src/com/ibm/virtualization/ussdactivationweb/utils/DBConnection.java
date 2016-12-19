package com.ibm.virtualization.ussdactivationweb.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;

/**
 * The DBConnection class, this class is used to get a DB connection using a
 * datasource This class can be used to connect to any datasource, not just
 * Oracle, so the name is a misnomer for this version In case you want this
 * class to use in your project, replace BarringException with your own
 * exception class
 * 
 * @author Rohit Dhal, adapted by Puneet Lakhina for Code Generator.
 * @date 21-Mar-2007
 */

public final class DBConnection {

	private static final Logger logger = Logger.getLogger(DBConnection.class);

	private static DataSource FTA_DATASOURCE = null;

	private static DataSource PRODCAT_DATASOURCE = null;

	private static DataSource DISTPORTAL_DATASOURCE = null;

	/**
	 * The default private constructor.Use getDBConnection() to get a connection
	 * 
	 */
	private DBConnection() {
	}

	/**
	 * This method does the lookup of the datasource and store it in a memeber
	 * variable to be used later , to avoid doing lookups again and again
	 * 
	 * @exception com.puneet.ledger.exception.DAOException
	 *                This exception is thrown in case data source cannot be
	 *                looked up
	 */

	/**
	 * This method returns the db connection using a datasource
	 * 
	 * @return Connection the db connection instance
	 * @exception com.puneet.ledger.exception.DAOException
	 *                This exception is thrown in case connection is not
	 *                established
	 */
	public static Connection getDBConnection() throws DAOException {
		logger.info("Request for connection received");
		Connection dbConnection = null;
		try {
			getDataSource();
			dbConnection = DBConnection.FTA_DATASOURCE.getConnection();
		} catch (SQLException sqlException) {
			// TODO LOG THIS
			logger.error("Could Not Obtain Connection. Reason:"
					+ sqlException.getMessage() + ". Error Code:"
					+ sqlException.getErrorCode());
			throw new DAOException(
					"Exception Occured while obtaining Connection.",
					sqlException);

		}
		logger.info("Connection Obtained.");
		return dbConnection;
	}

	/**
	 * This method does the lookup of the datasource and store it in a memeber
	 * variable to be used later , to avoid doing lookups again and again
	 * 
	 * @exception com.puneet.ledger.exception.DAOException
	 *                This exception is thrown in case data source cannot be
	 *                looked up
	 */
	private static void getDataSource() throws DAOException {
		try {
			synchronized (DBConnection.class) {
				if (DBConnection.FTA_DATASOURCE == null) {
					FTA_DATASOURCE = (DataSource) new InitialContext()
							.lookup(SQLConstants.FTA_JNDI_NAME);
				}
				if (DBConnection.PRODCAT_DATASOURCE == null) {
					PRODCAT_DATASOURCE = (DataSource) new InitialContext()
							.lookup(SQLConstants.PRODCAT_JNDI_NAME);
				}

				 if (DBConnection.DISTPORTAL_DATASOURCE == null) {
					 DISTPORTAL_DATASOURCE = (DataSource) new
					 	InitialContext().lookup(SQLConstants.DISTPORTAL_JNDI_NAME);
				 }
			}
		} catch (NamingException namingException) {
			// TODO LOG this
			logger.error("Lookup of Data Source Failed. Reason:"
					+ namingException.getMessage());
			throw new DAOException(
					"Exception Occured while data source lookup.",
					namingException);

		}
	}

	/**
	 * This method returns the db connection using a datasource
	 * 
	 * @return Connection the db connection instance
	 * @exception com.puneet.ledger.exception.DAOException
	 *                This exception is thrown in case connection is not
	 *                established
	 */
	public static Connection getDBConnection(String jndiLookupName)
			throws DAOException {
		logger.info("Request for connection received");
		Connection dbConnection = null;
		try {

			getDataSource();
			if (jndiLookupName.equals(SQLConstants.FTA_JNDI_NAME)) {
				dbConnection = DBConnection.FTA_DATASOURCE.getConnection();
			} else if (jndiLookupName.equals(SQLConstants.PRODCAT_JNDI_NAME)) {
				dbConnection = DBConnection.PRODCAT_DATASOURCE.getConnection();
			}
			 else {
			 dbConnection =
			 DBConnection.DISTPORTAL_DATASOURCE.getConnection();
			 }

			// TODO Log that connection has been established
		} catch (SQLException sqlException) {
			// TODO LOG THIS
			logger.error("Could Not Obtain Connection. Reason:"
					+ sqlException.getMessage() + ". Error Code:"
					+ sqlException.getErrorCode());
			throw new DAOException(
					"Exception Occured while obtaining Connection.",
					sqlException);

		}
		logger.info("Connection Obtained.");
		return dbConnection;
	}
}
