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
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.PaymentMode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.CustomerTransaction;
import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides the implementation for all the method declarations in
 * Distributor Transaction interface
 * 
 * @author bhanu
 * 
 * 
 */

public class DistributorTransactionDaoRdbmsDB2 extends DistributorTransactionDaoRdbms {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(DistributorTransactionDaoRdbmsDB2.class.getName());

	/* SQL Used within DaoImpl */

	protected static final String SQL_INSERT_TRANSACTION = "INSERT INTO VR_ACCOUNT_TRANSACTION (TRANSACTION_ID,DEST_ACCOUNT_ID,TRANSACTION_AMOUNT,CREDITED_AMOUNT,TRANSACTION_CHANNEL,RATE,PAYMENT_MODE,TRANSACTION_DT,CHEQUE_CC_NO,BANK_NAME,CC_VALID_DATE,REVIEW_STATUS,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,CHEQUE_DATE,ECS_NO,PURCHASE_NO,PURCHASE_DT, REVIEW_COMMENT) VALUES (NEXT VALUE FOR SEQ_VR_ACC_TRANSACTION,?,?,?,?,?,?,?,?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";

	protected final static String SQL_CONNECT_BY_QUERY_KEY ="SQL_CONNECT_BY_QUERY";
	protected static final String SQL_CONNECT_BY_QUERY = "WITH DETAILS(ACCOUNT_ID) AS (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT DETAILS1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as DETAILS1, DETAILS WHERE DETAILS.ACCOUNT_ID = DETAILS1.PARENT_ACCOUNT) ";
	
	
	
	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public DistributorTransactionDaoRdbmsDB2(Connection connection) {
		super(connection);
		
		
		queryMap.put(SQL_INSERT_TRANSACTION_KEY,SQL_INSERT_TRANSACTION);
		queryMap.put(SQL_CONNECT_BY_QUERY_KEY,SQL_CONNECT_BY_QUERY);
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
			sql.append(" AND DATE(TRANS.TRANSACTION_DT) >= ? ");
		}
		if (endDt != null && !endDt.equals("")) {
			/** search according to End date */
			sql.append(" AND DATE(TRANS.TRANSACTION_DT) <= ? ");
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
			query.append("SELECT * FROM(SELECT a.*,ROW_NUMBER() OVER() rnum FROM (");
			query.append(sql.toString());
			query.append(") a)b Where rnum<=? AND rnum>=?");
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
						.getString("LOGIN_NAME"));
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
			sql.append(" AND DATE(TRANS.TRANSACTION_DT) >= ? ");
		}
		if (endDt != null && !endDt.equals("")) {
			/** search according to End date */
			sql.append(" AND DATE(TRANS.TRANSACTION_DT) <= ? ");
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
			sql.append(" AND DATE(TRANS.TRANSACTION_DT) >= ? ");
		}
		if (endDt != null && !endDt.equals("")) {
			/** search according to End date */
			sql.append(" AND DATE(TRANS.TRANSACTION_DT) <= ? ");
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
						.getString("LOGIN_NAME"));
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
