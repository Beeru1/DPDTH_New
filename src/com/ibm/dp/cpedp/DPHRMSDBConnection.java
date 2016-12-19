package com.ibm.dp.cpedp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

/** 
 * The DBConnection class, this class is used to get a DB connection using a datasource
 * This class can be used to connect to any datasource, not just Oracle, so the name is a misnomer for this version
 * In case you want this  class to use in your project, replace BarringException with your own exception class 
 * @author Rohit Dhal, adapted by Puneet Lakhina for Code Generator.
 * @date 21-Mar-2007
 */

public class DPHRMSDBConnection {
	
	private static Logger logger = Logger.getLogger(DPHRMSDBConnection.class.toString());
	private static DataSource mem_o_datasource_hrms;
	static int count = 1;
	/**
	 * The default private constructor.Use getDBConnection() to get a connection
	 *
	 */
	private DPHRMSDBConnection() {
	}
	/**
	 * This method does the lookup of the datasource and store it in a memeber variable to be 
	 * used later , to avoid doing lookups again and again
	 * @exception com.ibm.fresh.exception.DAOException This exception is thrown in case data source cannot be looked up
	 */
	private static void getDataSourceHRMS() throws Exception {
		String dataSourceName = null;
		System.out.println("1111111111::"+mem_o_datasource_hrms);
		try {
			synchronized (DPHRMSDBConnection.class){
				if (mem_o_datasource_hrms == null) 
				{
					// TODO Read the data source name from a property file.
					//String lookupname = PropertyReader.getString("fresh.database.JNDI_NAME");
					dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.DATASOURCE_NAME_HRMS);
					InitialContext loc_o_ic = new InitialContext();
					mem_o_datasource_hrms = (DataSource) loc_o_ic.lookup(dataSourceName);
					System.out.println("22222222222::"+mem_o_datasource_hrms);

				}
			}
			//TODO Log that data source has been looked up
		} catch (NamingException namingException) {
			System.out.println("exception--------------------------");
			namingException.printStackTrace();
			//TODO LOG this 
			logger.severe("Lookup of Data Source Failed. Reason:" + namingException.getMessage());
			throw new Exception("Exception in getDataSource().");
		}
	}
	/**
	 * This method returns the db connection using a datasource
	 * @return Connection the db connection instance
	 * @exception com.ibm.fresh.exception.DAOException This exception is thrown in case connection is not established
	 */
	public static Connection getDBConnectionHRMS() throws Exception {
		logger.info("Request for connection received::::"+mem_o_datasource_hrms);
		Connection dbConnection = null;
		try 
		{
			if (mem_o_datasource_hrms == null) 
					getDataSourceHRMS();
			
			System.out.println("44444444444444444444:::::"+mem_o_datasource_hrms);
			dbConnection = mem_o_datasource_hrms.getConnection();
			System.out.println("55555555555555555555::"+dbConnection);
			
			try
			{
				PreparedStatement pstmt = dbConnection.prepareStatement("select sysdate from dual");
				
				ResultSet rs = pstmt.executeQuery();
				
				DBConnectionManager.releaseResources(pstmt, rs);
				//throw new Exception("Manual");
			}
			catch (Exception e) 
			{
				logger.info("Counter  ::  "+count);
				count++;
				logger.info("HRMS COnnection Issue  ::  "+e.getMessage());
				if(count<6)
				{
					dbConnection = getDBConnectionHRMS();
				}
				else
				{
					count = 1;					
					throw new Exception("Error in creating connection with HRMS");
				}
			}
			
			dbConnection.setAutoCommit(false);
			
		// TODO Log that connection has been established
		} 
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
			throw new Exception("Exception in getDBConnection(). from HRMS");
		}
		
		logger.info("Connection Obtained.");
		count = 1;
		return dbConnection;
	}
	
	
	
	/**
	 * This method does the lookup of the datasource and store it in a memeber variable to be 
	 * used later , to avoid doing lookups again and again
	 * @exception com.ibm.fresh.exception.DAOException This exception is thrown in case data source cannot be looked up
	 */
	private static void getDataSourceDP() throws Exception {
		try {
			String dataSourceName="";
			synchronized (DPCPEDBConnection.class){
				if (mem_o_datasource_hrms == null) {
					// TODO Read the data source name from a property file.
					//String lookupname = PropertyReader.getString("fresh.database.JNDI_NAME");
					dataSourceName = ResourceReader.getCoreResourceBundleValue(Constants.DATASOURCE_NAME);
					InitialContext loc_o_ic = new InitialContext();
					
					mem_o_datasource_hrms = (DataSource) loc_o_ic.lookup(dataSourceName);

				}
			}
			//TODO Log that data source has been looked up
		} catch (NamingException namingException) {
			namingException.printStackTrace();
			//TODO LOG this 
			logger.severe("Lookup of Data Source Failed. Reason:" + namingException.getMessage());
			throw new Exception("Exception in getDataSource().");
		}
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
			logger.info(
					"  Could Not Close Connection. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode());
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
			logger.info(
					"  Could Not Close ResultSet and Statement. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode());

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
			logger.info(
					"commitTransaction : Could Not Commit Transaction. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode());
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
	public static void releaseResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {

		try 
		{
			if (statement != null) 
			{
				/* close statement */
				statement.close();
				statement = null;
			}
			
			if (resultSet != null) 
			{
				/* close resultset */
				resultSet.close();
				resultSet = null;
			}
		
			if (connection != null && !connection.isClosed()) 
			{
				connection.close();
				//connection = null;
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			logger.info(
					"  Could Not Close ResultSet and PreparedStatement. Reason:"
							+ sqlException.getMessage() + ". Error Code:"
							+ sqlException.getErrorCode());

		}
	}
	

}

