package com.ibm.virtualization.ussdactivationweb.dao;

//Standarad JDK Classes
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Java Extension Classes
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//Project specific classes
import com.ibm.virtualization.ussdactivationweb.utils.USSDException;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.PropertyReader;

/** 
 * The OracleConnection class, this class is used to get a DB connection using a datasource
 * This class can be used to connect to any datasource, not just Oracle, so the name is a misnomer for this version
 * In case you want this  class to use in your project, replace USSDException with your own exception class 
 * @author Ashad
 * @date 21-july-2007
 */

public class OracleConnection {

	private static DataSource mem_o_datasource;
	private static final String CLASS_NAME =
		"com.ibm.barring.dao.OracleConnection";
	private static final String GET_DATA_SOURCE_METHOD_NAME = "getDataSource()";
	private static final String GET_DB_CONNECTION_METHOD_NAME =
		"getDBConnection()";
	private static final String RELEASE_RESOURCES_METHOD_NAME =
		"releaseResources()";
	/**
	 * The default private constructor.Use getDBConnection() to get a connection
	 *
	 */
	private OracleConnection() {
	}
	/**
	 * This method does the lookup of the datasource and store it in a memeber variable to be 
	 * used later , to avoid doing lookups again and again
	 * @exception USSDException This exception is thrown in case data source cannot be looked up
	 */
	private static void getDataSource() throws USSDException {
		try {
			synchronized (OracleConnection.class){
				if (mem_o_datasource == null) {
					String lookupname =
						PropertyReader.getValue("DATASOURCE_LOOKUP_NAME");
					InitialContext loc_o_ic = new InitialContext();
					mem_o_datasource = (DataSource) loc_o_ic.lookup(lookupname);

				}
			}
			//TODO Log that data source has been looked up
		} catch (NamingException namingException) {
			//TODO LOG this 
			USSDException barringException =
				new USSDException(
					CLASS_NAME,
					GET_DATA_SOURCE_METHOD_NAME,
					namingException.getMessage(),
					ErrorCodes.SYSTEMEXCEPTION);
			throw barringException;
		}
	}
	/**
	 * This method returns the db connection using a datasource
	 * @return Connection the db connection instance
	 * @exception USSDException This exception is thrown in case connection is not established
	 */
	public static Connection getDBConnection() throws USSDException {
		Connection dbConnection = null;
		try {

			if (mem_o_datasource == null) {
					getDataSource();
				}
			
			dbConnection =
				mem_o_datasource.getConnection();					
				
				
		// TODO Log that connection has been established
		} catch (SQLException sqlException) {
			// TODO LOG THIS
			throw new USSDException(
				CLASS_NAME,
				GET_DB_CONNECTION_METHOD_NAME,
				sqlException.getMessage(),
				ErrorCodes.SQLEXCEPTION);
		}

		return dbConnection;
	}

	/**
	 * This method releases all database objects.
	 *@param connection The database connection object which needs to be released
	 *@param statement The database statement object which needs to be released
	 *@param resultSet The database resultset object which needs to be released
	 *@throws USSDException
	 */

	public static void releaseResources(
		Connection connection,
		Statement statement,
		ResultSet resultSet)
		throws USSDException {
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
			// TODO Log this
			throw new USSDException(
				CLASS_NAME,
				RELEASE_RESOURCES_METHOD_NAME,
				sqlException.getMessage(),
				ErrorCodes.SQLEXCEPTION);

		}
	}

}
