/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \***************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dao.SequenceDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * 
 * Implementation for SequenceDao
 * 
 * @author ashish
 * 
 */
public class SequenceDaoRdbmsDB2 extends SequenceDaoRdbms {

	

	public SequenceDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SLCT_SEQ_TRAN_ID_KEY, SLCT_SEQ_TRAN_ID);
		queryMap.put(SLCT_RECHARGE_SEQ_TRAN_ID_KEY, SLCT_RECHARGE_SEQ_TRAN_ID);
		// TODO Auto-generated constructor stub
	}

	// Get transaction_id based on next sequence value for account-acount
	// transfer and query
	protected final static String SLCT_SEQ_TRAN_ID_KEY ="SLCT_SEQ_TRAN_ID";
	protected static final String SLCT_SEQ_TRAN_ID = "SELECT NEXT VALUE FOR SEQ_VR_TRANSACTION_ID FROM SYSIBM.SYSDUMMY1";

	// Get transaction_id based on next sequence value for recharge
	protected final static String SLCT_RECHARGE_SEQ_TRAN_ID_KEY ="SLCT_RECHARGE_SEQ_TRAN_ID";
	protected static final String SLCT_RECHARGE_SEQ_TRAN_ID = "SELECT NEXT VALUE FOR SEQ_VR_TRANS_ID_RECHARGE FROM SYSIBM.SYSDUMMY1";


	
	
}
