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

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.exception.DAOException;
/**
 * This class provides implementation for DBConnection from Virtualization
 * framework DB-Connection Factory
 * 
 * @author Paras
 * 
 */
public class FrameworkDBConnection implements DBConnection {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(FrameworkDBConnection.class
			.getName());

	private static FrameworkDBConnection virtualizationFrameworkConnection;

	private static javax.sql.DataSource ds = null;
	
	private static GenericObjectPool connectionPool= new GenericObjectPool(null);

	/**
	 * Private Constructor for FrameworkDBConnection
	 * 
	 */
	private FrameworkDBConnection() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of FrameworkDBConnection
	 * 
	 * @return virtualizationFrameworkConnection
	 */
	public static FrameworkDBConnection getInstance() {
		logger.info("Entered getInstance method for FrameworkDBConnection.");

		if (virtualizationFrameworkConnection == null) {
			virtualizationFrameworkConnection = new FrameworkDBConnection();
		}
		logger
				.info("getInstance : successfully retured FrameworkDBConnection instance.");

		return virtualizationFrameworkConnection;
	}

	/**
	 * Set dbcp datasource properties.
	 * 
	 */
	private static DataSource getDataSource() throws DAOException {

		PoolingDataSource dataSource = null;

		try {

			java.lang.Class.forName(
					ResourceReader.getValueFromBundle(
							Constants.DB_DRIVER_NAME,
							Constants.APLLICATION_RESOURCE_BUNDLE))
					.newInstance();
			connectionPool.setMaxIdle(Integer.parseInt(ResourceReader
					.getValueFromBundle(
							Constants.DB_POOL_MAX_IDLE,
							Constants.APLLICATION_RESOURCE_BUNDLE).trim()));
			connectionPool.setMinIdle(Integer.parseInt(ResourceReader
					.getValueFromBundle(
							Constants.DB_POOL_MIN_IDLE,
							Constants.APLLICATION_RESOURCE_BUNDLE).trim()));
			connectionPool.setMaxWait(Long.valueOf(ResourceReader
					.getValueFromBundle(
							Constants.DB_POOL_MAX_WAIT,
							Constants.APLLICATION_RESOURCE_BUNDLE).trim()));
			connectionPool.setMaxActive(Integer.parseInt(ResourceReader
					.getValueFromBundle(
							Constants.DB_POOL_MAX_ACTIVE,
							Constants.APLLICATION_RESOURCE_BUNDLE).trim()));
			connectionPool.setNumTestsPerEvictionRun(Integer.valueOf(ResourceReader
					.getValueFromBundle(
							Constants.DB_POOL_MIN_NUM_TSETS_PER_EVICTION_RUN,
							Constants.APLLICATION_RESOURCE_BUNDLE).trim()));
			Float minEvictableIdleTimeMillis=Float.valueOf(ResourceReader
					.getValueFromBundle(
							Constants.DB_POOL_MIN_EVICTABLE_IDLE_TIME,
							Constants.APLLICATION_RESOURCE_BUNDLE).trim())*60000;
			Float timeBetweenEvictionRunsMillis=Float
			.valueOf(ResourceReader.getValueFromBundle(
					Constants.DB_POOL_TIME_BETWEEN_EVICTIONRUNS,
					Constants.APLLICATION_RESOURCE_BUNDLE).trim())*60000;
			connectionPool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis.longValue());
			connectionPool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis.longValue());
			if ("true".equalsIgnoreCase(ResourceReader.getValueFromBundle(
					Constants.DB_POOL_TEST_WHILE_IDLE,
					Constants.APLLICATION_RESOURCE_BUNDLE))) {
				logger.info("testWhileIdle:true");
				connectionPool.setTestWhileIdle(true);
			}else
			{
				logger.info("testWhileIdle:false");
				connectionPool.setTestWhileIdle(false);
			}
			if ("true".equalsIgnoreCase(ResourceReader.getValueFromBundle(
					Constants.DB_POOL_TEST_ON_BORROW,
					Constants.APLLICATION_RESOURCE_BUNDLE))) {
				logger.info("testOnBorrow:true");
				connectionPool.setTestOnBorrow(true);
			}else{
				logger.info("testOnBorrow:false");
				connectionPool.setTestOnBorrow(false);
			}
			
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
					ResourceReader.getValueFromBundle(Constants.DB_URL,
							Constants.APLLICATION_RESOURCE_BUNDLE), ResourceReader
							.getValueFromBundle(
									Constants.DB_USERNAME,
									Constants.APLLICATION_RESOURCE_BUNDLE),
									ResourceReader.getValueFromBundle(
							Constants.DB_PASSWORD,
							Constants.APLLICATION_RESOURCE_BUNDLE));
			
			/*
			 * Now we'll create the PoolableConnectionFactory, which wraps the
			 * "real" Connections created by the ConnectionFactory with the
			 * classes that implement the pooling functionality.
			 */
			
			 PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
					connectionFactory, connectionPool, null, null, false, false);
			poolableConnectionFactory.setValidationQuery(ResourceReader
					.getValueFromBundle(Constants.DB_POOL_VALIDATION_QUERY,
							Constants.APLLICATION_RESOURCE_BUNDLE));
			displayDBCPConfig();
			dataSource = new PoolingDataSource(connectionPool);
           
		} catch (Exception exp) {
			logger.fatal("Error when attempting to obtain DB Driver ", exp);
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		return dataSource;

	}

	/**
	 * This method returns connection object for virtualization framework
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getDBConnection() throws DAOException {
		logger.info("Entered getDBConnection method.");
		Connection connection = null;
		try {
			if (ds == null) {
				synchronized (this) {
					if (ds == null) {
						ds = getDataSource();
					}
				}
			}
			connection = ds.getConnection();
			
			/* if unable to create connection */
			if (connection == null) {
				logger.fatal(" Exception occured while get connection.");
				throw new DAOException(
						ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
			}else
			{
				connection.setAutoCommit(false);
			}
		} catch (SQLException sqe) {
			logger
					.fatal("Exception occured while get connection. "
							+ sqe.getMessage());
			/* Convert the Exception to DAOException */
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		
		logger.info("  successfully returned Connection.");

		/* return connection object */
		return connection;
	}

	public static void displayDBCPConfig()
	{
		if (connectionPool != null) {
			logger.info("max active :"
					+ connectionPool.getMaxActive());
			logger.info("max idle :"
					+ connectionPool.getMaxIdle());
			logger.info("min idle :"
					+ connectionPool.getMinIdle());
			logger.info("max wait Millis :"
					+ connectionPool.getMaxWait());
		
			logger.info("validation query :"
					+ ResourceReader.getValueFromBundle(
							Constants.DB_POOL_VALIDATION_QUERY,
							Constants.APLLICATION_RESOURCE_BUNDLE));
			logger.info("isTestWhileIdle :"
					+ ResourceReader.getValueFromBundle(
							Constants.DB_POOL_TEST_WHILE_IDLE,
							Constants.APLLICATION_RESOURCE_BUNDLE));
			logger.info("isTestOnBorrow :"
					+ ResourceReader.getValueFromBundle(
							Constants.DB_POOL_TEST_ON_BORROW,
							Constants.APLLICATION_RESOURCE_BUNDLE));
			logger.info("timeBetweenEvictionRunsMillis :"
					+ connectionPool.getTimeBetweenEvictionRunsMillis());
			logger.info("NumTestsPerEvictionRun :"
					+ connectionPool.getNumTestsPerEvictionRun());
			logger.info("MinEvictableIdleTimeMillis :"
					+ connectionPool.getMinEvictableIdleTimeMillis());
			logger.info("active connnection:" + connectionPool.getNumActive());
			logger.info("idle connnection:" + connectionPool.getNumIdle());
			
		}
	}
	/**
	 * This method returns connection object for datasource name specified for reports
	 * 
	 * @return
	 * @throws DAOException
	 */
	public Connection getReportDBConnection() throws DAOException {
		return null;
		
	}

	public Connection getDBConnectionSnD() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}	
	
//	Added By Shilpa on 16-3-12
	/**
	 * This method returns connection object for virtualization framework
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getMisReportDBConnection() throws DAOException {
		logger.info("Entered getMisReportDBConnection method.");
		Connection connection = null;
		try {
			if (ds == null) {
				synchronized (this) {
					if (ds == null) {
						ds = getDataSource();
					}
				}
			}
			connection = ds.getConnection();
			
			/* if unable to create connection */
			if (connection == null) {
				logger.fatal(" Exception occured while get connection.");
				throw new DAOException(
						ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
			}else
			{
				connection.setAutoCommit(false);
			}
		} catch (SQLException sqe) {
			logger
					.fatal("Exception occured while get connection. "
							+ sqe.getMessage());
			/* Convert the Exception to DAOException */
			throw new DAOException(
					ExceptionCode.Connection.ERROR_CONNECTION_NOT_FOUND);
		}
		
		logger.info("  successfully returned Connection.");

		/* return connection object */
		return connection;
	}

}
