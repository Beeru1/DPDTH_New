/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;

import org.apache.log4j.Logger;

/**
 * This class provides the implementation for the methods used for DB2.
 * 
 * @author Mohit Aggarwal
 */

public class SysConfigDaoRdbmsDB2 extends SysConfigDaoRdbms {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(SysConfigDaoRdbmsDB2.class
			.getName());

	/* SQL Used within DaoImpl */

	protected static String SQL_SYSCONFIG_INSERT = "INSERT INTO VR_SYSTEM_CONFIG (VR_SYSTEM_CONFIG_ID, CIRCLE_ID, TRANSACTION_TYPE, CHANNEL, MIN_AMOUNT, MAX_AMOUNT,CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT ) VALUES (NEXT VALUE FOR SEQ_VR_SYSTEM_CONFIG, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public SysConfigDaoRdbmsDB2(Connection connection) {
		super(connection);
		logger.info("Setting SQL_SYSCONFIG_INSERT_KEY");
		queryMap.put(SQL_SYSCONFIG_INSERT_KEY, SQL_SYSCONFIG_INSERT);

	}

}
