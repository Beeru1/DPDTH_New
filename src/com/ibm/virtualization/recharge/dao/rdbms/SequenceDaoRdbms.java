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
public class SequenceDaoRdbms extends BaseDaoRdbms implements SequenceDao {

	/* Logger for this class */
	protected static Logger logger = Logger.getLogger(SequenceDaoRdbms.class
			.getName());

	// Get transaction_id based on next sequence value for account-acount
	// transfer and query
	protected final static String SLCT_SEQ_TRAN_ID_KEY ="SLCT_SEQ_TRAN_ID";
	protected static final String SLCT_SEQ_TRAN_ID = "SELECT SEQ_VR_TRANSACTION_ID.NEXTVAL FROM DUAL";

	// Get transaction_id based on next sequence value for recharge
	protected final static String SLCT_RECHARGE_SEQ_TRAN_ID_KEY ="SLCT_RECHARGE_SEQ_TRAN_ID";
	protected static final String SLCT_RECHARGE_SEQ_TRAN_ID = "SELECT SEQ_VR_TRANS_ID_RECHARGE.NEXTVAL FROM DUAL";

//	protected Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public SequenceDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SLCT_SEQ_TRAN_ID_KEY, SLCT_SEQ_TRAN_ID);
		queryMap.put(SLCT_RECHARGE_SEQ_TRAN_ID_KEY, SLCT_RECHARGE_SEQ_TRAN_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SequenceDao#getNextTransIDForQueryAndAccTransfer()
	 */
	public long getNextTransID() throws DAOException {
		logger.info("started.....");

		PreparedStatement pstmtReq = null;
		ResultSet rsReq = null;

		long transId = 0;
		try {
			pstmtReq = connection.prepareStatement(queryMap.get(SLCT_SEQ_TRAN_ID_KEY));
			rsReq = pstmtReq.executeQuery();
			if (rsReq.next()) {
				return rsReq.getInt(1);
			}
		} catch (SQLException se) {
			logger.error(
					"SQL Exception : Occured  while generating Transaction Id "
							+ transId, se);
			throw new DAOException("errors.db.sql");
		} finally {
			// Finally closing statement and resultset.
			DBConnectionManager.releaseResources(pstmtReq, rsReq);
		}
		logger.info("Successfully generated transaction Id ");

		return transId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SequenceDao#getNextTransIDForRecharge()
	 */
	public long getNextTransIDForRecharge() throws DAOException {
		logger.info("started.....");

		PreparedStatement pstmtReq = null;
		ResultSet rsReq = null;

		int transId = 0;
		try {
			pstmtReq = connection.prepareStatement(queryMap.get(SLCT_RECHARGE_SEQ_TRAN_ID_KEY));
			rsReq = pstmtReq.executeQuery();
			if (rsReq.next()) {
				return rsReq.getInt(1);
			}
		} catch (SQLException se) {
			logger
					.error(
							"SQL Exception : Occured  while generating Transaction Id ",
							se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			// Finally closing statement and resultset.
			DBConnectionManager.releaseResources(pstmtReq, rsReq);
		}
		logger.info("Successfully generated transaction Id ");
		return transId;

	}
}
