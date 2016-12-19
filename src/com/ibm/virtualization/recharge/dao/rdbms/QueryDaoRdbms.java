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

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dao.QueryDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.QueryBalanceDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * QueryDaoRdbms is a concrete class that encapsulates data access and
 * manipulation. An implementation of QueryDaoRdbms which is specific to a
 * Oracle database.
 * 
 * This class must be package-private, to ensure that the business layer remains
 * unaware of its existence.
 * 
 * @author prakash
 */
public class QueryDaoRdbms implements QueryDao {

	/* Logger for this class */
	protected static Logger logger = Logger.getLogger(QueryDaoRdbms.class
			.getName());

	// Get transaction_id based on next sequence value
//	protected final String SLCT_SEQ_TRAN_ID = "SELECT SEQ_VR_TRANSACTION_ID.NEXTVAL FROM DUAL";

	/* Used to get account details bases on ACCOUNT ID passed at run time */
	protected static final String SQL_SELECT_VR_ACCOUNT_BALANCE_ON_ACCOUNT_ID = "SELECT VR_ACCOUNT_BALANCE.AVAILABLE_BALANCE,VR_ACCOUNT_BALANCE.OPERATING_BALANCE, VR_ACCOUNT_BALANCE.OPENING_BALANCE  FROM VR_ACCOUNT_BALANCE WHERE VR_ACCOUNT_BALANCE.ACCOUNT_ID=? ";

	/* Used to get parent account based on mobile number passed at run time */
	protected static final String SQL_SELECT_PARENT_MOBILE_NUMBER = "SELECT VR_ACCOUNT_DETAILS.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS  WHERE VR_ACCOUNT_DETAILS.MOBILE_NUMBER=? AND VR_ACCOUNT_DETAILS.PARENT_ACCOUNT =?";

	protected Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public QueryDaoRdbms(Connection connection) {
		this.connection = connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.QueryDao#getBalanceByMobileNumber(java.lang.String)
	 */
	public QueryBalanceDTO getBalance(long loginId) throws DAOException {
		logger.info("Starting ::::");
		PreparedStatement ps = null;
		ResultSet rs = null;
		QueryBalanceDTO accountDetails = new QueryBalanceDTO();

		try {

			ps = connection
					.prepareStatement(SQL_SELECT_VR_ACCOUNT_BALANCE_ON_ACCOUNT_ID);
			ps.setLong(1, loginId);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountDetails = extractQueryBalanceDTOFromResultSet(rs);
			} else {
				logger.error("No Record Found for Login Id " + loginId);
				throw new DAOException(
						ExceptionCode.Query.ERROR_NO_RECORD_FOUND_MOBILE);
			}
		} catch (SQLException se) {
			logger.fatal(
					" SQL Exception  occured while finding account balance for mobileNumber "
							+ loginId, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, prepared statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger
				.info("Executed ...Successfully found account balance for loginId "
						+ loginId);
		return accountDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.QueryDao#isChildAccountByMobileNumber(java.lang.String,
	 *      java.lang.String)
	 */
	public long isImmediateChild(long parentAccId, String childMobileNumber)
			throws DAOException {
		logger.info("Started...");
		logger.info(" method called for parentAccId " + parentAccId
				+ " and childMobileNumber " + childMobileNumber);
		PreparedStatement ps = null;
		ResultSet rs = null;
		long childAccId = -1;
		try {

			ps = connection.prepareStatement(SQL_SELECT_PARENT_MOBILE_NUMBER);
			ps.setString(1, childMobileNumber);
			ps.setLong(2, parentAccId);
			rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("login id" + rs.getLong(1));
				childAccId = rs.getLong(1);
			} else {
				throw new DAOException(
						ExceptionCode.Query.ERROR_INVALID_CHILD_MOBILE_NO);
			}
		} catch (SQLException se) {
			logger
					.fatal(
							" SQL Exception  "
									+ se.getMessage()
									+ " occured while finding parent account for child mobileNumber "
									+ childMobileNumber, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, preparedstatement. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger.info("Executed ....");
		return childAccId;
	}

	/**
	 * Extracts the query object from the resultset
	 * 
	 * @param rs
	 *            ResultSet - the name of the resultset from which the object is
	 *            to distilled
	 * 
	 * @return QueryDTO Object educed from the resultset
	 * 
	 * @throws SQLException
	 */
	public QueryBalanceDTO extractQueryBalanceDTOFromResultSet(ResultSet rs)
			throws SQLException {
		logger.info("Started...");
		QueryBalanceDTO queryDto = new QueryBalanceDTO();
		queryDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
		queryDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
		queryDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
		logger.info("Executed ....");
		return queryDto;
	}
}