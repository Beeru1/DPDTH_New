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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.PaymentMode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.DistributorTransactionDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
/**
 * This class provides the implementation for all the method declarations in
 * Distributor Transaction interface
 * 
 * @author bhanu
 * 
 * 
 */

public class DistributorTransactionDaoRdbms extends BaseDaoRdbms implements
		DistributorTransactionDao {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(DistributorTransactionDaoRdbms.class.getName());

	/* SQL Used within DaoImpl */

	protected final static String SQL_INSERT_TRANSACTION_KEY ="SQL_INSERT_TRANSACTION";
	protected static final String SQL_INSERT_TRANSACTION = "INSERT INTO VR_ACCOUNT_TRANSACTION (TRANSACTION_ID,DEST_ACCOUNT_ID,TRANSACTION_AMOUNT,CREDITED_AMOUNT,TRANSACTION_CHANNEL,RATE,PAYMENT_MODE,TRANSACTION_DT,CHEQUE_CC_NO,BANK_NAME,CC_VALID_DATE,REVIEW_STATUS,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,CHEQUE_DATE,ECS_NO,PURCHASE_NO,PURCHASE_DT, REVIEW_COMMENT) VALUES (SEQ_VR_ACC_TRANSACTION.nextval,?,?,?,?,?,?,?,?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";

	// for review details
	protected final static String SQL_SELECT_TRANSACTION_REVIEW_DETAILS_KEY ="SQL_SELECT_TRANSACTION_REVIEW_DETAILS";
	protected static final String SQL_SELECT_TRANSACTION_REVIEW_DETAILS = "SELECT TRANSACTION_ID,LOGIN_NAME,DEST_ACCOUNT_ID,TRANSACTION_AMOUNT,CREDITED_AMOUNT,RATE,"
			+ " TRANSACTION_DT ,CHEQUE_CC_NO,CHEQUE_DATE ,PAYMENT_MODE,CC_VALID_DATE,BANK_NAME,TRANSACTION_CHANNEL,REVIEW_STATUS,REVIEW_COMMENT,ECS_NO,ACCOUNT_NAME,PURCHASE_NO,PURCHASE_DT "
			+ " FROM VR_ACCOUNT_TRANSACTION t,VR_LOGIN_MASTER lm,VR_ACCOUNT_DETAILS a  WHERE "
			+ "   t.DEST_ACCOUNT_ID = a.ACCOUNT_ID AND  a.ACCOUNT_ID=lm.LOGIN_ID "
			+ "	AND t.TRANSACTION_ID=? AND STATUS='"
			+ Constants.ACTIVE_STATUS
			+ "'";

	// for updating or approve or reject the pending transfer
	protected final static String UPDATE_VR_ACCOUNT_TRANSACTION_INFO_KEY ="UPDATE_VR_ACCOUNT_TRANSACTION_INFO";
	protected static final String UPDATE_VR_ACCOUNT_TRANSACTION_INFO = " UPDATE VR_ACCOUNT_TRANSACTION SET REVIEW_STATUS = ?,REVIEW_COMMENT = ?, UPDATED_BY = ?,UPDATED_DT =?,REVIEWED_DT =?,REVIEWED_BY =? WHERE TRANSACTION_ID = ?";

	// for getting transaction list based on status

	/*
	 * public static final String SQL_SELECT_TRANSACTION = " SELECT
	 * lm.LOGIN_NAME,TRANSACTION_ID,REVIEW_STATUS,
	 * DEST_ACCOUNT_ID,TRANSACTION_AMOUNT,CREDITED_AMOUNT,RATE,to_char(TRANSACTION_DT,'yyyy-mm-dd
	 * HH:MM:SS')TRANSACTION_DT,CHEQUE_CC_NO,ECS_NO,ACCOUNT_NAME,PURCHASE_NO,TO_CHAR(PURCHASE_DT,
	 * 'DD/MM/YYYY') PURCHASE_DT,CHEQUE_DATE " + " FROM VR_ACCOUNT_TRANSACTION t ,
	 * VR_ACCOUNT_DETAILS a ,VR_LOGIN_MASTER lm " + " WHERE t.REVIEW_STATUS=?
	 * AND t.DEST_ACCOUNT_ID = a.ACCOUNT_ID AND a.ACCOUNT_ID=lm.LOGIN_ID" + "
	 * AND lm.STATUS='"+Constants.ACTIVE_STATUS+"' ORDER BY t.TRANSACTION_ID
	 * DESC ";
	 */
	
	//New Where clause for SQL_SELECT_REVIEW_TRANSACTION
	protected final static String SQL_SELECT_TRANSACTION_WHERE_CLAUSE_KEY ="SQL_SELECT_TRANSACTION_WHERE_CLAUSE";
	protected static final String SQL_SELECT_TRANSACTION_WHERE_CLAUSE = "FROM  VR_ACCOUNT_TRANSACTION TRANS , VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_CIRCLE_MASTER CIRCLE, VR_LOGIN_MASTER LOGIN1 ,VR_LOGIN_MASTER LOGIN2 WHERE  TRANS.DEST_ACCOUNT_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID=LOGIN.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND "
		+ "TRANS.REVIEW_STATUS=? AND DETAILS.CIRCLE_ID = ? AND LOGIN.STATUS='"
			+ Constants.ACTIVE_STATUS + "'";


	//New where clause for SQL_SELECT_TRANSACTION
	protected final static String SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE_KEY ="SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE";
	protected static final String SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE = "FROM  VR_ACCOUNT_TRANSACTION TRANS , VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN1 , VR_CIRCLE_MASTER CIRCLE WHERE  TRANS.DEST_ACCOUNT_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID=LOGIN.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND "
		+ "TRANS.REVIEW_STATUS=? AND DETAILS.CIRCLE_ID = ? AND LOGIN.STATUS='"
			+ Constants.ACTIVE_STATUS + "'";
		
	
/**	public static final String SQL_SELECT_TRANSACTION_WHERE_CLAUSE = "FROM (SELECT LOGIN_NAME NAME1,LOGIN_ID from VR_LOGIN_MASTER) LOGIN2  VR_ACCOUNT_TRANSACTION TRANS , VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_CIRCLE_MASTER CIRCLE, VR_LOGIN_MASTER LOGIN1 WHERE  TRANS.DEST_ACCOUNT_ID = DETAILS.ACCOUNT_ID AND TRANS.CREATED_BY=LOGIN.LOGIN_ID AND DETAILS.ACCOUNT_ID=LOGIN.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND "
		+ " TRANS.REVIEW_STATUS=? AND DETAILS.CIRCLE_ID = ? ";
	
		public static final String SQL_SELECT_TRANSACTION_WHERE_CLAUSE = "FROM  VR_ACCOUNT_TRANSACTION TRANS , VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_CIRCLE_MASTER CIRCLE, VR_LOGIN_MASTER LOGIN1 WHERE  TRANS.DEST_ACCOUNT_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID=LOGIN.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND "
		+ "TRANS.REVIEW_STATUS=? AND DETAILS.CIRCLE_ID = ? ";
	
	public static final String SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE = "FROM  VR_ACCOUNT_TRANSACTION TRANS , VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_CIRCLE_MASTER CIRCLE WHERE  TRANS.DEST_ACCOUNT_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID=LOGIN.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND "
		+ "TRANS.REVIEW_STATUS=? AND DETAILS.CIRCLE_ID = ? ";
	
	public static final String SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE = " FROM (SELECT LOGIN_NAME NAME1,LOGIN_ID from VR_LOGIN_MASTER) LOGIN1 , VR_ACCOUNT_TRANSACTION TRANS , VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_CIRCLE_MASTER CIRCLE WHERE  TRANS.DEST_ACCOUNT_ID = DETAILS.ACCOUNT_ID AND TRANS.CREATED_BY=LOGIN.LOGIN_ID AND DETAILS.ACCOUNT_ID=LOGIN1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND  "
				+ " TRANS.REVIEW_STATUS=? AND DETAILS.CIRCLE_ID = ? ";
	
	public static final String SQL_SELECT_TRANSACTION = "SELECT LOGIN.LOGIN_NAME , DETAILS.CIRCLE_ID, TRANS.TRANSACTION_ID,TRANS.REVIEW_STATUS,TRANS.DEST_ACCOUNT_ID,TRANS.TRANSACTION_AMOUNT,TRANS.CREDITED_AMOUNT,TRANS.RATE,TRANS.TRANSACTION_DT,TRANS.CHEQUE_CC_NO,TRANS.ECS_NO,DETAILS.ACCOUNT_NAME,"
			+ "TRANS.PURCHASE_NO,TRANS.PURCHASE_DT,TRANS.CHEQUE_DATE, TRANS.PAYMENT_MODE, TRANS.BANK_NAME,TRANS.CC_VALID_DATE, TRANS.CREATED_BY, TRANS.REVIEWED_DT, TRANS.REVIEWED_BY, TRANS.REVIEW_COMMENT, COUNT(TRANS.TRANSACTION_ID)over() RECORD_COUNT "
			+ SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE;

*/
		
	//New Query SQL_SELECT_TRANSACTION
	protected final static String SQL_SELECT_TRANSACTION_KEY ="SQL_SELECT_TRANSACTION";
	protected static final String SQL_SELECT_TRANSACTION = "SELECT LOGIN.LOGIN_NAME , DETAILS.CIRCLE_ID, TRANS.TRANSACTION_ID,TRANS.REVIEW_STATUS,TRANS.DEST_ACCOUNT_ID,TRANS.TRANSACTION_AMOUNT,TRANS.CREDITED_AMOUNT,TRANS.RATE,TRANS.TRANSACTION_DT,TRANS.CHEQUE_CC_NO,TRANS.ECS_NO,DETAILS.ACCOUNT_NAME,"
			+ "TRANS.PURCHASE_NO,TRANS.PURCHASE_DT,TRANS.CHEQUE_DATE, TRANS.PAYMENT_MODE, TRANS.BANK_NAME,TRANS.CC_VALID_DATE, LOGIN1.LOGIN_NAME CREATED_BY, TRANS.REVIEWED_DT, TRANS.REVIEWED_BY, TRANS.REVIEW_COMMENT, COUNT(TRANS.TRANSACTION_ID)over() RECORD_COUNT "
			+ SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE
			+ " AND TRANS.CREATED_BY = LOGIN1.LOGIN_ID";
		
		
	//New  Query SQL_SELECT_REVIEW_TRANSACTION
	protected final static String SQL_SELECT_REVIEW_TRANSACTION_KEY ="SQL_SELECT_REVIEW_TRANSACTION";	
	protected static final String SQL_SELECT_REVIEW_TRANSACTION = "SELECT LOGIN.LOGIN_NAME, DETAILS.CIRCLE_ID, TRANS.TRANSACTION_ID,TRANS.REVIEW_STATUS,TRANS.DEST_ACCOUNT_ID,TRANS.TRANSACTION_AMOUNT,TRANS.CREDITED_AMOUNT,TRANS.RATE,TRANS.TRANSACTION_DT,TRANS.CHEQUE_CC_NO,TRANS.ECS_NO,DETAILS.ACCOUNT_NAME,"
			+ " TRANS.PURCHASE_NO,TRANS.PURCHASE_DT,TRANS.CHEQUE_DATE, TRANS.PAYMENT_MODE, TRANS.BANK_NAME,TRANS.CC_VALID_DATE, LOGIN2.LOGIN_NAME CREATED_BY, TRANS.REVIEWED_DT, TRANS.REVIEWED_BY, TRANS.REVIEW_COMMENT, LOGIN1.LOGIN_NAME REVIEWER, COUNT(TRANS.TRANSACTION_ID)over() RECORD_COUNT "
			+ SQL_SELECT_TRANSACTION_WHERE_CLAUSE
			+ " AND TRANS.REVIEWED_BY = LOGIN1.LOGIN_ID AND TRANS.CREATED_BY=LOGIN2.LOGIN_ID";	
	
		
		
		
/**	public static final String SQL_SELECT_TRANSACTION = "SELECT LOGIN1.NAME1, DETAILS.CIRCLE_ID, TRANS.TRANSACTION_ID,TRANS.REVIEW_STATUS,TRANS.DEST_ACCOUNT_ID,TRANS.TRANSACTION_AMOUNT,TRANS.CREDITED_AMOUNT,TRANS.RATE,TRANS.TRANSACTION_DT,TRANS.CHEQUE_CC_NO,TRANS.ECS_NO,DETAILS.ACCOUNT_NAME,"
	+ "TRANS.PURCHASE_NO,TRANS.PURCHASE_DT,TRANS.CHEQUE_DATE, TRANS.PAYMENT_MODE, TRANS.BANK_NAME,TRANS.CC_VALID_DATE, LOGIN.LOGIN_NAME CREATED_BY, TRANS.REVIEWED_DT, TRANS.REVIEWED_BY, TRANS.REVIEW_COMMENT, COUNT(TRANS.TRANSACTION_ID)over() RECORD_COUNT "
	+ SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE;
	
	public static final String SQL_SELECT_REVIEW_TRANSACTION = "SELECT LOGIN.NAME, DETAILS.CIRCLE_ID, TRANS.TRANSACTION_ID,TRANS.REVIEW_STATUS,TRANS.DEST_ACCOUNT_ID,TRANS.TRANSACTION_AMOUNT,TRANS.CREDITED_AMOUNT,TRANS.RATE,TRANS.TRANSACTION_DT,TRANS.CHEQUE_CC_NO,TRANS.ECS_NO,DETAILS.ACCOUNT_NAME,"
			+ " TRANS.PURCHASE_NO,TRANS.PURCHASE_DT,TRANS.CHEQUE_DATE, TRANS.PAYMENT_MODE, TRANS.BANK_NAME,TRANS.CC_VALID_DATE, TRANS.CREATED_BY, TRANS.REVIEWED_DT, TRANS.REVIEWED_BY, TRANS.REVIEW_COMMENT, LOGIN1.LOGIN_NAME REVIEWER, COUNT(TRANS.TRANSACTION_ID)over() RECORD_COUNT "
			+ SQL_SELECT_TRANSACTION_WHERE_CLAUSE
			+ " AND TRANS.REVIEWED_BY = LOGIN1.LOGIN_ID";
			
	public static final String SQL_SELECT_REVIEW_TRANSACTION = "SELECT LOGIN2_NAME NAME1, DETAILS.CIRCLE_ID, TRANS.TRANSACTION_ID,TRANS.REVIEW_STATUS,TRANS.DEST_ACCOUNT_ID,TRANS.TRANSACTION_AMOUNT,TRANS.CREDITED_AMOUNT,TRANS.RATE,TRANS.TRANSACTION_DT,TRANS.CHEQUE_CC_NO,TRANS.ECS_NO,DETAILS.ACCOUNT_NAME,"
		+ " TRANS.PURCHASE_NO,TRANS.PURCHASE_DT,TRANS.CHEQUE_DATE, TRANS.PAYMENT_MODE, TRANS.BANK_NAME,TRANS.CC_VALID_DATE, LOGIN.LOGIN_NAME CREATED_BY, TRANS.REVIEWED_DT, TRANS.REVIEWED_BY, TRANS.REVIEW_COMMENT, LOGIN1.LOGIN_NAME REVIEWER, COUNT(TRANS.TRANSACTION_ID)over() RECORD_COUNT "
		+ SQL_SELECT_TRANSACTION_WHERE_CLAUSE
		+ " AND TRANS.REVIEWED_BY = LOGIN1.LOGIN_ID";		
			
			
*/
	

	protected final static String SQL_GET_TRANSACTION_COUNT_KEY ="SQL_GET_TRANSACTION_COUNT";
	protected static final String SQL_GET_TRANSACTION_COUNT = "SELECT COUNT(TRANS.TRANSACTION_ID) "
			+ SQL_SELECT_TRANSACTION_WHERE_CLAUSE;

	
//	protected Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public DistributorTransactionDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_INSERT_TRANSACTION_KEY,SQL_INSERT_TRANSACTION);
		queryMap.put(SQL_SELECT_TRANSACTION_REVIEW_DETAILS_KEY,SQL_SELECT_TRANSACTION_REVIEW_DETAILS);
		queryMap.put(UPDATE_VR_ACCOUNT_TRANSACTION_INFO_KEY,UPDATE_VR_ACCOUNT_TRANSACTION_INFO);
		queryMap.put(SQL_SELECT_TRANSACTION_WHERE_CLAUSE_KEY,SQL_SELECT_TRANSACTION_WHERE_CLAUSE);
		queryMap.put(SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE_KEY,SQL_SELECT_PENDING_TRANSACTION_WHERE_CLAUSE);
		queryMap.put(SQL_SELECT_TRANSACTION_KEY,SQL_SELECT_TRANSACTION);
		queryMap.put(SQL_SELECT_REVIEW_TRANSACTION_KEY,SQL_SELECT_REVIEW_TRANSACTION);
		queryMap.put(SQL_GET_TRANSACTION_COUNT_KEY,SQL_GET_TRANSACTION_COUNT);
	}

	/**
	 * see
	 * com.ibm.virtualization.recharge.dao.DistributorTransactionDao#insertDistributorTransactionData(DistributorTransaction
	 * moneyTransaction) return
	 */
	public void insertDistributorTransactionData(
			DistributorTransaction distributorTransactionDto)
			throws DAOException {
		logger.info("Started..." + distributorTransactionDto);
		PreparedStatement ps = null;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_INSERT_TRANSACTION_KEY));
			int paramCount = 1;
			ps.setLong(paramCount++, distributorTransactionDto.getAccountId());
			ps.setDouble(paramCount++, distributorTransactionDto
					.getTransactionAmount());
			ps.setDouble(paramCount++, distributorTransactionDto
					.getCreditedAmount());
			ps.setString(paramCount++, Constants.DISTRIBUTOR_CH_TYPE);
			ps.setDouble(paramCount++, distributorTransactionDto.getRate());
			ps.setString(paramCount++, distributorTransactionDto
					.getPaymentMode().name());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps
					.setLong(paramCount++, distributorTransactionDto
							.getChqccNumber());
			ps.setString(paramCount++, distributorTransactionDto.getBankName());
			ps.setDate(paramCount++, (Date) distributorTransactionDto
					.getCcvalidDate());
			ps.setString(paramCount++, distributorTransactionDto
					.getReviewStatus());// Review Status
			ps.setLong(paramCount++, distributorTransactionDto.getCreatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, distributorTransactionDto.getUpdatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setDate(paramCount++, (Date) distributorTransactionDto
					.getChequeDate());
			ps.setString(paramCount++, distributorTransactionDto.getEcsno());// ECS
			// NO
			ps.setString(paramCount++, distributorTransactionDto
					.getPurchaseOrderNo());
			ps.setDate(paramCount++, (Date) distributorTransactionDto
					.getPurchaseOrderDate());
			ps.setString(paramCount, distributorTransactionDto
					.getReviewComment());
			ps.executeUpdate();
			logger
					.info("Row insertion successful on table:VR_ACCOUNT_TRANSACTION. Inserted:  rows");

		} catch (SQLException e) {

			logger
					.fatal(
							"SQL Exception occured while inserting. distributor transaction Data into Table "
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(ps, null);
			logger.info("Executed ...");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.DistributorTransactionDao#getDistributorTransactionList(java.lang.String)
	 */

	public ArrayList getDistributorTransactionList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {
		logger.info("Started...");
		/* get the data from the input DTO */
		String status = mtDTO.getStatus();
		int circleId = mtDTO.getCircleId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		StringBuilder sql = new StringBuilder();
		if (Constants.TRANS_DIST_PENDING.equals(status)) {
			sql.append(queryMap.get(SQL_SELECT_TRANSACTION_KEY));
		} else {
			sql.append(queryMap.get(SQL_SELECT_REVIEW_TRANSACTION_KEY));
		}
		if (startDt != null && !startDt.equals("")) {
			/** search according to Start Date */
			sql.append(" AND TRUNC(TRANS.TRANSACTION_DT) >= ? ");
		}
		if (endDt != null && !endDt.equals("")) {
			/** search according to End date */
			sql.append(" AND TRUNC(TRANS.TRANSACTION_DT) <= ? ");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		// use to store DTO objects
		ArrayList<DistributorTransaction> results = new ArrayList<DistributorTransaction>();
		DistributorTransaction distributorTransaction = null;
		StringBuilder query = new StringBuilder();
		try {
			int paramCount = 1;
			sql.append(" ORDER BY TRANS.TRANSACTION_ID DESC");
			query.append("SELECT * FROM(SELECT a.*,ROWNUM rnum FROM (");
			query.append(sql.toString());
			query.append(") a Where ROWNUM<=?)Where rnum>=?");
			ps = connection.prepareStatement(query.toString());

			ps.setString(paramCount++, status.trim());
			ps.setInt(paramCount++, circleId);
			if (startDt != null && !startDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						startDt).getTime()));
			}
			if (endDt != null && !endDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						endDt).getTime()));
			}
			ps.setString(paramCount++, String.valueOf(upperBound));
			ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			rs = ps.executeQuery();
			// getting results into DTO and Put DTO into ArrayList
			while (rs.next()) {

				// creating new dto object for each record
				distributorTransaction = new DistributorTransaction();
				distributorTransaction.setTransactionId(rs
						.getLong("TRANSACTION_ID"));
				distributorTransaction.setAccountCode(rs
						.getString("LOGIN_NAME"));
				distributorTransaction.setAccountId(rs
						.getLong("DEST_ACCOUNT_ID"));
				distributorTransaction.setTransactionAmount(rs
						.getDouble("TRANSACTION_AMOUNT"));
				distributorTransaction.setCreditedAmount(rs
						.getDouble("CREDITED_AMOUNT"));
				distributorTransaction.setRate(rs.getDouble("RATE"));
				distributorTransaction.setTransactionDate(rs
						.getTimestamp("TRANSACTION_DT"));
				distributorTransaction.setChqccNumber(rs
						.getLong("CHEQUE_CC_NO"));
				distributorTransaction.setChequeDate(rs
						.getTimestamp("CHEQUE_DATE"));
				distributorTransaction.setReviewStatus(rs
						.getString("REVIEW_STATUS"));

				distributorTransaction.setEcsno(rs.getString("ECS_NO"));
				distributorTransaction.setPurchaseOrderNo(rs
						.getString("PURCHASE_NO"));
				distributorTransaction.setPurchaseOrderDate(rs
						.getTimestamp("PURCHASE_DT"));
				distributorTransaction.setBankName(rs.getString("BANK_NAME"));
				distributorTransaction.setCcvalidDate(rs
						.getTimestamp("CC_VALID_DATE"));
				distributorTransaction.setCreatedByName(rs
    					.getString("CREATED_BY"));								
			
				distributorTransaction.setReviewedDate(rs
						.getTimestamp("REVIEWED_DT"));
				if (!Constants.TRANS_DIST_PENDING.equals(status)) {
					distributorTransaction.setReviewedBy(rs
							.getString("REVIEWER"));
				}
				distributorTransaction.setReviewComment(rs
						.getString("REVIEW_COMMENT"));
				distributorTransaction.setRowNum(rs.getString("RNUM"));
				distributorTransaction.setTotalRecords(rs
						.getInt("RECORD_COUNT"));
				if (PaymentMode.CASH == (PaymentMode.valueOf(rs
						.getString("PAYMENT_MODE")))) {
					distributorTransaction.setPaymentMode(PaymentMode.CASH);
				} else if (PaymentMode.CHEQUE == (PaymentMode.valueOf(rs
						.getString("PAYMENT_MODE")))) {
					distributorTransaction.setPaymentMode(PaymentMode.CHEQUE);
				} else if (PaymentMode.CREDIT == (PaymentMode.valueOf(rs
						.getString("PAYMENT_MODE")))) {
					distributorTransaction.setPaymentMode(PaymentMode.CREDIT);
				} else if (PaymentMode.ECS == (PaymentMode.valueOf(rs
						.getString("PAYMENT_MODE")))) {
					distributorTransaction.setPaymentMode(PaymentMode.ECS);
				}
				// adding each DTO object into array List
				results.add(distributorTransaction);
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while Selecting. Distribtor Transaction List "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed .... Distributor Transaction List SuccessFully Retreive");
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.DistributorTransactionDao#updateDistributorTransaction(com.ibm.virtualization.recharge.dto.DistributorTransaction)
	 */

	public int updateDistributorTransaction(
			DistributorTransaction distributorTransaction) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		int rowsUpdated = 0;
		try {

			ps = connection
					.prepareStatement(UPDATE_VR_ACCOUNT_TRANSACTION_INFO);
			int paramCount = 1;
			ps.setString(paramCount++, distributorTransaction.getReviewStatus()
					.trim());
			ps.setString(paramCount++, distributorTransaction
					.getReviewComment().trim());
			ps.setLong(paramCount++, distributorTransaction.getUpdatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, distributorTransaction.getUpdatedBy());
			ps.setLong(paramCount, distributorTransaction.getTransactionId());

			rowsUpdated = ps.executeUpdate();

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info("Executed ...");
		return rowsUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.DistributorTransactionDao#getDistributorTransactionReviewDetail(java.lang.String)
	 */

	public DistributorTransaction getDistributorTransactionReviewDetail(
			long transactionId) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		DistributorTransaction distributorTransaction = null;
		try {
			connection.setAutoCommit(false);
			ps = connection
					.prepareStatement(queryMap.get(SQL_SELECT_TRANSACTION_REVIEW_DETAILS_KEY));
			ps.setLong(1, transactionId);
			rs = ps.executeQuery();
			/* getting values from database and set into DTO */
			if (rs.next()) {
				distributorTransaction = new DistributorTransaction();
				distributorTransaction.setTransactionId((rs
						.getLong("TRANSACTION_ID")));
				distributorTransaction.setAccountCode(rs
						.getString("LOGIN_NAME"));
				distributorTransaction.setAccountId((rs
						.getLong("DEST_ACCOUNT_ID")));
				distributorTransaction.setTransactionAmount(rs
						.getDouble("TRANSACTION_AMOUNT"));
				distributorTransaction.setCreditedAmount(rs
						.getDouble("CREDITED_AMOUNT"));
				distributorTransaction.setRate(rs.getDouble("RATE"));
				distributorTransaction.setTransactionDate(rs
						.getTimestamp("TRANSACTION_DT"));
				distributorTransaction.setChqccNumber(rs
						.getLong("CHEQUE_CC_NO"));
				distributorTransaction.setChequeDate(rs
						.getDate("CHEQUE_DATE"));
				distributorTransaction.setPaymentMode(PaymentMode.valueOf(rs
						.getString("PAYMENT_MODE")));
				distributorTransaction.setCcvalidDate(rs
						.getDate("CC_VALID_DATE"));
				distributorTransaction.setBankName(rs.getString("BANK_NAME"));
				distributorTransaction.setReviewStatus(rs
						.getString("REVIEW_STATUS"));
				distributorTransaction.setReviewComment(rs
						.getString("REVIEW_COMMENT"));
				distributorTransaction.setEcsno(rs.getString("ECS_NO"));
				distributorTransaction.setPurchaseOrderNo(rs
						.getString("PURCHASE_NO"));
				distributorTransaction.setPurchaseOrderDate(rs
						.getDate("PURCHASE_DT"));
				distributorTransaction.setAccountName(rs
						.getString("ACCOUNT_NAME"));
			}
			logger.info("Executed ....");
			return distributorTransaction;

		} catch (SQLException e) {
			// need for resultset,connection handling
			logger.error("Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.DistributorTransactionDao#getDistributorTransactionCount(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public int getDistributorTransactionCount(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getDistributorTransactionCount()...");

		/* get the data from the input DTO */
		String status = mtDTO.getStatus();
		int circleId = mtDTO.getCircleId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		int noofRow = 1;
		int noofpages = 0;
		int paramCount = 1;
		StringBuilder sql = new StringBuilder();

		sql.append(queryMap.get(SQL_GET_TRANSACTION_COUNT_KEY));
		if (startDt != null && !startDt.equals("")) {
			/** search according to Start Date */
			sql.append(" AND TRUNC(TRANS.TRANSACTION_DT) >= ? ");
		}
		if (endDt != null && !endDt.equals("")) {
			/** search according to End date */
			sql.append(" AND TRUNC(TRANS.TRANSACTION_DT) <= ? ");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql.toString());

			ps.setString(paramCount++, status.trim());
			ps.setInt(paramCount++, circleId);
			if (startDt != null && !startDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						startDt).getTime()));
			}
			if (endDt != null && !endDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						endDt).getTime()));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while counting the records "
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed .... Distributor Transaction List Count SuccessFully Retreive");
		return noofpages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.DistributorTransactionDao#getDistributorTransactionListAll(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public ArrayList getDistributorTransactionListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getDistributorTransactionListAll()...");

		/* get the data from the input DTO */
		String status = mtDTO.getStatus();
		int circleId = mtDTO.getCircleId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		StringBuilder sql = new StringBuilder();

		sql.append(queryMap.get(SQL_SELECT_TRANSACTION_KEY));
		if (startDt != null && !startDt.equals("")) {
			/** search according to Start Date */
			sql.append(" AND TRUNC(TRANS.TRANSACTION_DT) >= ? ");
		}
		if (endDt != null && !endDt.equals("")) {
			/** search according to End date */
			sql.append(" AND TRUNC(TRANS.TRANSACTION_DT) <= ? ");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		// use to store DTO objects
		ArrayList<DistributorTransaction> results = new ArrayList<DistributorTransaction>();
		DistributorTransaction distributorTransaction = null;
		try {
			int paramCount = 1;
			sql.append(" ORDER BY TRANS.TRANSACTION_ID DESC");
			ps = connection.prepareStatement(sql.toString());

			ps.setString(paramCount++, status.trim());
			ps.setInt(paramCount++, circleId);
			if (startDt != null && !startDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						startDt).getTime()));
			}
			if (endDt != null && !endDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						endDt).getTime()));
			}
			rs = ps.executeQuery();
			// getting results into DTO and Put DTO into ArrayList
			while (rs.next()) {

				// creating new dto object for each record
				distributorTransaction = new DistributorTransaction();
				distributorTransaction.setTransactionId(rs
						.getLong("TRANSACTION_ID"));
				distributorTransaction.setAccountCode(rs
						.getString("LOGIN_NAME"));
				distributorTransaction.setAccountId(rs
						.getLong("DEST_ACCOUNT_ID"));
				distributorTransaction.setTransactionAmount(rs
						.getDouble("TRANSACTION_AMOUNT"));
				distributorTransaction.setCreditedAmount(rs
						.getDouble("CREDITED_AMOUNT"));
				distributorTransaction.setRate(rs.getDouble("RATE"));
				distributorTransaction.setTransactionDate(rs
						.getTimestamp("TRANSACTION_DT"));
				distributorTransaction.setChqccNumber(rs
						.getLong("CHEQUE_CC_NO"));
				distributorTransaction.setChequeDate(rs
						.getTimestamp("CHEQUE_DATE"));
				distributorTransaction.setReviewStatus(rs
						.getString("REVIEW_STATUS"));

				distributorTransaction.setEcsno(rs.getString("ECS_NO"));
				distributorTransaction.setPurchaseOrderNo(rs
						.getString("PURCHASE_NO"));
				distributorTransaction.setPurchaseOrderDate(rs
						.getTimestamp("PURCHASE_DT"));
				distributorTransaction.setBankName(rs.getString("BANK_NAME"));
				distributorTransaction.setCcvalidDate(rs
						.getTimestamp("CC_VALID_DATE"));
				distributorTransaction.setCreatedByName(rs
						.getString("CREATED_BY"));
				distributorTransaction.setReviewedDate(rs
						.getTimestamp("REVIEWED_DT"));
				distributorTransaction.setReviewedBy(String.valueOf(rs
						.getLong("REVIEWED_BY")));
				distributorTransaction.setReviewComment(rs
						.getString("REVIEW_COMMENT"));
				if (PaymentMode.CASH.name().equals(rs.getString("PAYMENT_MODE"))) {
					distributorTransaction.setPaymentMode(PaymentMode.CASH);
				} else if (PaymentMode.CHEQUE.name().equals(rs
						.getString("PAYMENT_MODE"))) {
					distributorTransaction.setPaymentMode(PaymentMode.CHEQUE);
				} else if (PaymentMode.CREDIT.name().equals(rs
						.getString("PAYMENT_MODE"))) {
					distributorTransaction.setPaymentMode(PaymentMode.CREDIT);
				} else if (PaymentMode.ECS.name().equals(rs.getString("PAYMENT_MODE"))) {
					distributorTransaction.setPaymentMode(PaymentMode.ECS);
				}
				// adding each DTO object into array List
				results.add(distributorTransaction);
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while Selecting. Distribtor Transaction List "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed .... Distributor Transaction List SuccessFully Retreive");
		return results;
	}

	

	
	
	
	
}
