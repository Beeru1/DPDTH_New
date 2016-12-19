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

import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface to get Database Connection
 * 
 * @author Paras
 * 
 */
public interface DBConnection {

	/**
	 * This method returns Connection Object for Database based on the
	 * configuration file. Virtualization Framework connection object is
	 * provided if no configuration is defined.
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getDBConnection() throws DAOException;
	
	/**
	 * This method returns connection object for datasource name specified for reports
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	public Connection getReportDBConnection() throws DAOException;
	
	
	
	public Connection getDBConnectionSnD() throws DAOException;
	//Added By Shilpa on 16-3-12
	/**
	 * This method returns Connection Object for Database based on the
	 * configuration file. Virtualization Framework connection object is
	 * provided if no configuration is defined.
	 * 
	 * @return Connection
	 * @throws DAOException
	 */
	
	public Connection getMisReportDBConnection() throws DAOException;
	
}
