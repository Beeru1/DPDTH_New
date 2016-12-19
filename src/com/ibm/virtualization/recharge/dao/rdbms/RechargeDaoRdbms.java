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
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * This class provides the implementation for all the method declarations in
 * RechargeDao interface
 * 
 * @author Mohit
 * 
 */
public class RechargeDaoRdbms extends BaseDaoRdbms implements RechargeDao {

	/* Logger for this class. */
	protected static Logger logger = Logger.getLogger(RechargeDaoRdbms.class
			.getName());

	/* connection used for the inner class only */

	/* SQL Used within DaoImpl */
	/** query to Upadte the Status in the Trnsaction Master Table */
	protected final static String SQL_UPDATE_TRANSACTIONMASTER_STATUS_KEY = "SQL_UPDATE_TRANSACTIONMASTER_STATUS";

	protected static final String SQL_UPDATE_TRANSACTIONMASTER_STATUS = "UPDATE VR_TRANS_MASTER SET TRANS_STATUS=? , UPDATED_BY=?, UPDATED_DATE=? WHERE TRANS_ID=?";

	/** query to find the next TRANSACTIONID from sequence */
	protected final static String SQL_SELECT_CUSTOMER_TRANSACTIONID_KEY = "SQL_SELECT_CUSTOMER_TRANSACTIONID";

	protected static final String SQL_SELECT_CUSTOMER_TRANSACTIONID = "SELECT SEQ_VR_TRANSACTION_ID.NEXTVAL TRANSACTIONID FROM DUAL";

	/** query to insert data into the Transaction Master Table */
	/** query to insert data into the Transaction Master Table */
	protected final static String SQL_INSERT_TRANSACTIONS_MASTER_KEY = "SQL_INSERT_TRANSACTIONS_MASTER";

	protected static final String SQL_INSERT_TRANSACTIONS_MASTER = "INSERT INTO VR_TRANS_MASTER(TRANS_ID, TRANS_TYPE, TRANS_CHANNEL, TRANS_DATE, REQUESTER_ID, "
			+ "RECEIVER_ID,  TRANS_AMOUNT, TRANS_MSG, TRANS_STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, REQUEST_TIME,PHYSICAL_ID, RESET,REASON_ID)"
			+ "VALUES(?, ?, ?,? , ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate, ?, ?, ?, ?)";

	/** query to update Table:transaction master if the recharging fails on IN */
	protected final static String SQL_UPDATE_TRANSACTION_STATUS_KEY = "SQL_UPDATE_TRANSACTION_STATUS";

	protected static final String SQL_UPDATE_TRANSACTION_STATUS = "UPDATE VR_TRANS_MASTER SET TRANS_STATUS=?,REASON_ID=?,UPDATED_BY=?,UPDATED_DATE=?,THIRD_PARTY=? WHERE TRANS_ID=?";

	protected static StringBuilder whereClause = new StringBuilder(
			" AND TRANS_STATUS NOT IN(").append(
			TransactionStatus.DUPLICATE.ordinal()).append(", ").append(
			+TransactionStatus.BLACKOUT.ordinal()).append(", ").append(TransactionStatus.FAILURE.ordinal()).append(")");

	/** query to find if request is duplicate */
	protected final static String SQL_SELECT_DUPLICATE_TRANSACTION_KEY = "SQL_SELECT_DUPLICATE_TRANSACTION";

	protected static final String SQL_SELECT_DUPLICATE_TRANSACTION = "SELECT MAX(REQUEST_TIME) RQST_TIME FROM VR_TRANS_MASTER WHERE TRANS_TYPE = ? AND TRANS_CHANNEL = ? AND REQUESTER_ID = ? "
			+ " AND RECEIVER_ID = ? AND TRANS_AMOUNT = ? AND TRANS_ID != ? "
			+ whereClause.toString();

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public RechargeDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_UPDATE_TRANSACTIONMASTER_STATUS_KEY,
				SQL_UPDATE_TRANSACTIONMASTER_STATUS);
		queryMap.put(SQL_SELECT_CUSTOMER_TRANSACTIONID_KEY,
				SQL_SELECT_CUSTOMER_TRANSACTIONID);
		queryMap.put(SQL_INSERT_TRANSACTIONS_MASTER_KEY,
				SQL_INSERT_TRANSACTIONS_MASTER);
		queryMap.put(SQL_UPDATE_TRANSACTION_STATUS_KEY,
				SQL_UPDATE_TRANSACTION_STATUS);
		queryMap.put(SQL_SELECT_DUPLICATE_TRANSACTION_KEY,
				SQL_SELECT_DUPLICATE_TRANSACTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#getTransactionId()
	 */
	public long getTransactionId() throws DAOException {
		logger.trace("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		long transactionId = 0;
		try {

			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_CUSTOMER_TRANSACTIONID_KEY));

			rs = ps.executeQuery();
			if (rs.next()) {
				transactionId = rs.getLong("TRANSACTIONID");
			} else {
				logger.error("TransactionId could not be generated.");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_TRANSACTON);
			}
			logger
					.trace("successfully found the getTransactionId from the sequence: ");
			return transactionId;
		} catch (SQLException sqe) {
			logger.error(" SQL Exception occured while finding TransactionId.",
					sqe);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_GET_TRANSACTION_ID);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.trace(" Executed ....");

		}
	}

	/**
	 * 
	 * Checks if the current transaction is the case of blackout or duplicate
	 * 
	 * @param transactionMaster
	 * @return
	 * @throws DAOException
	 */

	private int isDuplicateRequest(TransactionMaster transactionMaster)
			throws DAOException {
		logger.debug("Started..." + transactionMaster.toString());

		PreparedStatement ps = null;
		ResultSet rs = null;
		int retVal = 0;// New Transaction Request
		PreparedStatement psDuplicate = null;
		ResultSet rsDuplicate = null;
		try {
			long validTransTimeDelay = 0;

			try {
				// pick Time Interval from Resource Bundle and convert to
				// millis
				// eg : 10800 = 3 mins

				validTransTimeDelay = Long
						.parseLong(ResourceReader
								.getCoreResourceBundleValue(
										Constants.APPLICATION_TRANSACTION_BLACKOUT_TIME));
			} catch (NumberFormatException ne) {
				logger.error(" Error occured while formating the blackout time");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_TRANSACTION_VALID_TRANSACTION_TIME_DELAY);
			} catch (VirtualizationServiceException virtualizationExp) {
				logger
						.error("RechargeDaoRdbms:caught VirtualizationServiceException"
								+ virtualizationExp.getMessage());
				throw new DAOException(virtualizationExp.getMessage());
			}

			int transactionType = transactionMaster.getRequestType().getValue();
			StringBuffer sql = new StringBuffer(queryMap
					.get(SQL_SELECT_DUPLICATE_TRANSACTION_KEY));

			sql.append(" WITH UR ");
			logger.info(" Query is: " + sql.toString());
			ps = connection.prepareStatement(sql.toString());
			int paramCount = 1;

			ps.setInt(paramCount++, transactionType);
			ps.setInt(paramCount++, transactionMaster.getTransactionChannel()
					.getValue());
			ps.setLong(paramCount++, transactionMaster.getSourceId());
			ps.setString(paramCount++, transactionMaster.getDestinationId());
			ps
					.setDouble(paramCount++, transactionMaster
							.getTransactionAmount());
			ps.setLong(paramCount++, transactionMaster.getTransactoinId());
			//ps.setLong(paramCount, validTransTimeDelay);
			rs = ps.executeQuery();

			if (rs.next() && rs.getTimestamp("RQST_TIME") != null) {
				Date transactionDate = rs.getTimestamp("RQST_TIME");
				long transactionTime = transactionDate.getTime();
				long currentTime =Utility
				.getRequestTimeDate(transactionMaster.getRequestTime())
				.getTime(); 

				long diffDate = currentTime - transactionTime;
				logger.debug("transactionDate.getTime()"+ transactionDate.getTime() + " and currentRequestTime"+ Utility
						.getRequestTimeDate(transactionMaster.getRequestTime())
						.getTime());
				// compareTo method returns 0 if two dates are equal.
				if (0 == diffDate) {
					// it is a DUPLICATE request
					logger.info(" DUPLICATE Transaction Request.");
					retVal = 2;
				}else if(diffDate < (validTransTimeDelay * 60 * 1000)){
					logger.info(" BLACKOUT Transaction Request.");
					retVal = 1;
				}
			}

		} catch (SQLException sqe) {
			logger.error(
					" SQL Exception occured while checking Duplicate Request.",
					sqe);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_GET_TRANSACTION_ID);
		} finally {
			DBConnectionManager.releaseResources(psDuplicate, rsDuplicate);
			DBConnectionManager.releaseResources(ps, rs);
			logger.trace(" Executed ....");

		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#insertCustomerTransaction(com.ibm.virtualization.recharge.dto.TransactionDTO)
	 */
	public int insertTransactionMasterPreProcess(
			TransactionMaster transactionMaster) throws DAOException {
		logger.trace("Started...");
		int returnTransStatus = -1;
		int transactionType = transactionMaster.getRequestType().getValue();

		try {
			if (transactionType == RequestType.RECHARGE.ordinal()
					|| transactionType == RequestType.ACCOUNT.ordinal()
					|| transactionType == RequestType.POSTPAID_ABTS.ordinal() 
					|| transactionType == RequestType.POSTPAID_DTH.ordinal() 
				|| transactionType == RequestType.POSTPAID_MOBILE.ordinal()) 
			{
				transactionMaster.setReset(0);
			} else {
				transactionMaster
						.setReset(transactionMaster.getTransactoinId());
			}
			insertTransactionMaster(transactionMaster);
			// If the transaction request is new then updates the
			// transactionoffset cache till the time the cache is refreshed by
			// the Timer Task

			logger
					.debug("Executed ...successful insertion of record into the table: transaction master. transactionId:"
							+ transactionMaster.getTransactoinId());
		} catch (DAOException sqe) {
			// in case of Oracle and DB2 the error message would be different
			if (sqe.getMessage().indexOf(getUniqueConstraintVoilationErrMsg() ) > -1 || sqe.getMessage().indexOf("unique constraint or unique index identified by ") > -1) {
				/*
				 * change the reset value with the current datetime to make the
				 * record unique
				 */
				transactionMaster
						.setReset(transactionMaster.getTransactoinId());
				try {
					insertTransactionMaster(transactionMaster);
				} catch (Exception e) {
					logger
							.error(
									"SQL  Exception occurred while inserting Duplicate/Blackout transaction into transaction master .Transaction ID:"
											+ transactionMaster
													.getTransactoinId(), sqe);
					throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
				}
				if (transactionType == RequestType.RECHARGE.ordinal()
						|| transactionType == RequestType.ACCOUNT.ordinal()
						|| transactionType == RequestType.POSTPAID_ABTS.ordinal() 
					|| transactionType == RequestType.POSTPAID_DTH.ordinal() 
				|| transactionType == RequestType.POSTPAID_MOBILE.ordinal()) {

					int chkExitsCode = isDuplicateRequest(transactionMaster);

					/* set TRANS_STATUS */
					if (chkExitsCode == 2) {
						transactionMaster
								.setTransactionStatus(TransactionStatus.DUPLICATE);
						returnTransStatus = TransactionStatus.DUPLICATE
								.getValue();
						transactionMaster.setReasonId(TransactionStatus.DUPLICATE.getName());
						logger.info("Duplicate Case :"
								+ transactionMaster.getTransactoinId());
					} else if (chkExitsCode == 1) {
						transactionMaster
								.setTransactionStatus(TransactionStatus.BLACKOUT);
						returnTransStatus = TransactionStatus.BLACKOUT
								.getValue();
						transactionMaster.setReasonId(TransactionStatus.BLACKOUT.getName());
						logger.info("Blackout Case :"
								+ transactionMaster.getTransactoinId());
					} else {
						if(transactionMaster.getTransactionStatus().ordinal() != TransactionStatus.FAILURE.ordinal()){
							transactionMaster.setTransactionStatus(TransactionStatus.PENDING);
						}
					}
					updateTransactionStatus(transactionMaster
							.getTransactoinId(), transactionMaster
							.getTransactionStatus(), transactionMaster.getReasonId()
							, transactionMaster
							.getUpdatedBy(), null);
					DBConnectionManager.commitTransaction(connection);

				} else {// any other case i.e. query, change m-password, reset
					logger
							.fatal("The Non-recharge/non-Account2Account/non-Postpaid transaction should not come here. The transaction is duplicate.");
				}
				// re-insert the record with the value of reset changed now

			} else {
				logger
						.error(
								"SQL Exception occured while inserting transaction into transaction master .Transaction ID:"
										+ transactionMaster.getTransactoinId(),
								sqe);
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}
		}
		return returnTransStatus;
	}

	/**
	 * Inserts the transaction in the transction master table. In case of 
	 * transaction timeout only
	 * 
	 * @param transactionMaster -
	 *            the transacation object to be inserted
	 * @throws SQLException
	 * @throws DAOException
	 */
	public void insertTransactionMaster(TransactionMaster transactionMaster)
			throws  DAOException {
		logger.trace(" Started ....");
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_INSERT_TRANSACTIONS_MASTER_KEY));
			int paramCount = 1;
			if(transactionMaster.getTransactionStatus().ordinal() != TransactionStatus.FAILURE.ordinal()){
				transactionMaster.setTransactionStatus(TransactionStatus.PENDING);
			}
			/* set TRANS_ID */
			ps.setLong(paramCount++, transactionMaster.getTransactoinId());
			/* set Request_TYPE */
			ps.setLong(paramCount++, transactionMaster.getRequestType()
					.getValue());
			/* set TRANS_CHANNEL */
			ps.setLong(paramCount++, transactionMaster.getTransactionChannel()
					.getValue());
			/* set TRANS_DATE */
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			/* set REQUESTER_ID */
			ps.setLong(paramCount++, transactionMaster.getCreatedBy());
			/* set RECEIVER_ID */
			ps.setString(paramCount++, transactionMaster.getDestinationId());
			/* set TRANS_AMOUNT */
			ps
					.setDouble(paramCount++, transactionMaster
							.getTransactionAmount());
			/* set TRANS_MSG */
			ps.setString(paramCount++, transactionMaster
					.getTransactionMessage());
			ps.setLong(paramCount++, transactionMaster.getTransactionStatus()
					.getValue());
			/* set CREATED_BY */
			ps.setLong(paramCount++, transactionMaster.getCreatedBy());
			/* set CREATED_DATE */
			// ps.setTimestamp(paramCount++, Utility.getDateTime());
			/* set UPDATED_BY */
			ps.setLong(paramCount++, transactionMaster.getUpdatedBy());
			/* set UPDATED_DATE */
			// ps.setTimestamp(paramCount++, Utility.getDateTime());
			/* set REQUEST_TIME FOR ASYNC */
			java.sql.Timestamp requestTime;
			if (null == transactionMaster.getRequestTime()) {
				requestTime = Utility.getDateTime();
			} else {
				requestTime = Utility.getDateAsTimestamp(transactionMaster
						.getRequestTime());
			}
			
			/* set REQUEST_TIME */
			ps.setTimestamp(paramCount++, requestTime);
			/* set PHYSICAL_ID FOR ASYNC */
			ps.setString(paramCount++, transactionMaster.getPhysicalId());
			ps.setLong(paramCount++, transactionMaster.getReset());
			ps.setString(paramCount, transactionMaster.getReasonId());
			logger.debug("======================" + transactionMaster.getDestinationId());
			ps.executeUpdate();
			DBConnectionManager.commitTransaction(connection);
			logger.trace("successfully inserted into transaction master");
		} catch (SQLException sqle) {
			if (sqle.getMessage().indexOf(getUniqueConstraintVoilationErrMsg()) > -1
					|| sqle.getMessage().indexOf(
							"unique constraint or unique index identified by ") > -1) {
				logger.debug("sql exception:", sqle);
			} else {
				logger.error("sql exception:", sqle);
			}
			throw new DAOException(sqle.getMessage());
		}
		finally {
			DBConnectionManager.releaseResources(ps, null);
			logger.trace(" Executed ....");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#updateCustomerTransactionStatus(long,
	 *      boolean)
	 */

	public void updateTransactionStatus(long transactionId,
			TransactionStatus transactionStatus, String reasonId, long updatedBy, String responseCode)
			throws DAOException {
		logger.trace("Started... transaction id:" + transactionId);

		PreparedStatement ps = null;

		try {

			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_TRANSACTION_STATUS_KEY));
			// TODO Sep.17,2007 Sushil Find better alternative of getValue.
			ps.setLong(1, transactionStatus.getValue());
			ps.setString(2, reasonId);
			ps.setLong(3, updatedBy);
			/* set TRANS_DATE */
			ps.setTimestamp(4, Utility.getDateTime());
			ps.setString(5, responseCode);
			ps.setLong(6, transactionId);

			ps.executeUpdate();

			logger
					.trace("Executed .... Successful updation of Status of transaction into transaction master ");
		} catch (SQLException sqe) {
			logger
					.error(
							"SQL Exception occured while Status of transaction into transaction master .Transaction ID:"
									+ transactionId, sqe);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_UPDATE_CUSTOMER_TRANSACTON_STATUS);
		} finally {
			DBConnectionManager.releaseResources(ps, null);
		}
	}

	/**
	 * @return the uniqueConstraintVoilationErrMsg
	 */
	public String getUniqueConstraintVoilationErrMsg() {
		return "IDX_COMP_TR_MSTR) violated";
	}
}
