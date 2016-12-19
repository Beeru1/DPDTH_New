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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * INRouterDao interface
 * 
 * @author Mohit Aggarwal
 */

public class INRouterDaoRdbmsDB2 extends INRouterDaoRdbms{

	private static Logger logger = Logger.getLogger(INRouterDaoRdbmsDB2.class);

	protected static final String GET_IN_ID_QUERY = "SELECT config.EXTERNAL_ID,series.CIRCLE_ID,config.EXTERNAL_URL FROM VR_MOBILE_SERIES_CONFIG series,VR_MOBILE_SERIES_IN_MAPPING mapping,VR_EXTERNAL_CONFIG_MSTR config WHERE "
			+ " series.MOBILE_SERIES_ID=mapping.MOBILE_SERIES_ID AND mapping.EXTERNAL_ID=config.EXTERNAL_ID AND config.EXTERNAL_INT_TYPE=2 AND "
			+ " cast(series.PRIMARY_ID AS BIGINT) <= ? AND cast(series.SECONDARY_ID AS BIGINT) >= ? ";
	
	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public INRouterDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(GET_IN_ID_QUERY_KEY,GET_IN_ID_QUERY);
	}

}
