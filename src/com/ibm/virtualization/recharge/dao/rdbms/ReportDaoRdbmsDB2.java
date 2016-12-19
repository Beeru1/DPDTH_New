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
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account2AccountReport;
import com.ibm.virtualization.recharge.dto.Account2AccountTransferReport;
import com.ibm.virtualization.recharge.dto.CustomerTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.TransactionReport;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides the implementation for the methods used for DB2.
 * 
 * @author Mohit Aggarwal
 */

public class ReportDaoRdbmsDB2 extends ReportDaoRdbms {
	/* logger for this class */
	private static Logger logger = Logger.getLogger(ReportDaoRdbmsDB2.class
			.getName());
	
	protected final static String SQL_CONNECT_BY_QUERY_KEY = "SQL_CONNECT_BY_QUERY";
	protected static final String SQL_CONNECT_BY_QUERY = "WITH DETAILS(ACCOUNT_ID) AS (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT DETAILS1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as DETAILS1, DETAILS WHERE DETAILS.ACCOUNT_ID = DETAILS1.PARENT_ACCOUNT) ";
	
	protected static final String SQL_TRANSACTION_REPORT_CHILD_WHERE_CLAUSE = " FROM VR_TRANS_MASTER TRANS, VR_ACCOUNT_DETAILS DETAILS, VR_LOGIN_MASTER LOGIN "
		+ " , VR_LOGIN_MASTER LOGIN1,  VR_ACCOUNT_DETAILS DETAILS1 "
		+ " WHERE TRANS.REQUESTER_ID = DETAILS.ACCOUNT_ID AND TRANS.REQUESTER_ID = LOGIN.LOGIN_ID "
		+ " AND LOGIN1.LOGIN_ID = DETAILS1.PARENT_ACCOUNT AND  TRANS.REQUESTER_ID = DETAILS1.ACCOUNT_ID   "
		+ " AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS )";

	protected static final String SQL_TRANSACTION_REPORT_CHILD_COUNT = "SELECT COUNT(*) "
		+ SQL_TRANSACTION_REPORT_CHILD_WHERE_CLAUSE;
	
	protected static final String SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_WHERE_CLAUSE = " FROM VR_TRANS_MASTER TRANS, VR_TRANS_DETAIL TDETAIL , VR_ACCOUNT_DETAILS DETAILS, VR_LOGIN_MASTER LOGIN "
		+ " , VR_LOGIN_MASTER LOGIN1,  VR_ACCOUNT_DETAILS DETAILS1 "
		+ " WHERE TRANS.REQUESTER_ID = DETAILS.ACCOUNT_ID "
		+ "  AND TRANS.TRANS_ID = TDETAIL.TRANS_ID AND TRANS.REQUESTER_ID = LOGIN.LOGIN_ID AND DETAILS1.ACCOUNT_ID = LOGIN1.LOGIN_ID "
		+ " AND LOGIN1.LOGIN_ID = DETAILS1.PARENT_ACCOUNT AND  TRANS.REQUESTER_ID = DETAILS1.ACCOUNT_ID "
		+ " AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS ) ORDER BY TRANS.TRANS_ID DESC ";

	
	protected static final String SQL_ALL_CHILD_TRANSACTION_REPORT_DATA = "SELECT TRANS.TRANS_ID,  TRANS.CREATED_BY , TRANS.TRANS_TYPE, TRANS.TRANS_AMOUNT, TRANS.TRANS_STATUS, TRANS.REASON_ID, TRANS.TRANS_MSG, TRANS.TRANS_DATE , TRANS.CREATED_DATE, TRANS.REQUESTER_ID, DETAILS.PARENT_ACCOUNT, LOGIN.LOGIN_NAME TRANSFER_ACC_CODE, TDETAIL.DETAIL_MSG ,"
		+ " DETAILS.ACCOUNT_NAME TRANSFER_ACC, DETAILS1.ACCOUNT_NAME RECEIVER_ACC, LOGIN1.LOGIN_NAME RECEIVER_ACC_CODE, DETAILS.CIRCLE_ID, TRANS.RECEIVER_ID, DETAILS1.ACCOUNT_ID, LOGIN.LOGIN_NAME, TRANS.TRANS_KEY_VALUE, TRANS.THIRD_PARTY, COUNT(TRANS.TRANS_ID)over() RECORD_COUNT "
		+ SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_WHERE_CLAUSE;
	
	protected static final String SQL_TRANSFER_SUMMARY_CHILD_COUNT_RECHARGE = "SELECT COUNT(*)TOTAL_TRANSCTIONS, SUM(TRANS.TRANS_AMOUNT) TRANSACTION_AMT FROM VR_TRANS_MASTER TRANS, VR_ACCOUNT_DETAILS DETAILS, VR_LOGIN_MASTER LOGIN,VR_CIRCLE_MASTER CIRCLE,  VR_ACCOUNT_DETAILS DETAILS1, VR_LOGIN_MASTER LOGIN1, VR_TRANS_DETAIL TDETAIL WHERE TRANS.REQUESTER_ID = DETAILS.ACCOUNT_ID  AND TRANS.REQUESTER_ID = LOGIN.LOGIN_ID  AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID  AND TRANS.RECEIVER_ID = DETAILS1.MOBILE_NUMBER  AND DETAILS1.ACCOUNT_ID = LOGIN1.LOGIN_ID "
		+ " AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS ) ";

	protected final static String SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE = " AND REQUESTER_ID IN (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYNAME(CAST(? AS VARCHAR(64)))) AS A)";
	
	protected final static String SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE = " AND ACCOUNT_ID IN (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYNAME(CAST(? AS VARCHAR(64)))) AS A)";
	
	protected final static String SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_ACC_CODE = " AND REQUESTER_ID IN (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A)";
	
	protected final static String SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_ACC_CODE = " AND ACCOUNT_ID IN (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A)";

	protected final static String SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_USRNAME_EXT_USERS = " AND ACC_DETAILS.ACCOUNT_ID in ((SELECT ACCOUNT_ID  FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A) intersect (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A))";

	protected final static String SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS = " AND REQUESTER_ID in ((SELECT ACCOUNT_ID  FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A) intersect (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYNAME(CAST(? AS VARCHAR(64)))) AS A))";
	
	protected final static String SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS = " AND ACCOUNT_ID in ((SELECT ACCOUNT_ID  FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A) intersect (SELECT ACCOUNT_ID FROM TABLE (CONNECTBYNAME(CAST(? AS VARCHAR(64)))) AS A))";

	protected final static String SQL_CONNECT_BY_NAME_QUERY_KEY = "SQL_CONNECT_BY_NAME_QUERY";

	protected static final String SQL_CONNECT_BY_NAME_QUERY = "WITH DETAILS(ACCOUNT_ID) AS (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS d,VR_LOGIN_MASTER l WHERE d.ACCOUNT_ID =l.LOGIN_ID and UPPER(login_name) like ?  UNION ALL SELECT DETAILS1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as DETAILS1, DETAILS WHERE DETAILS.ACCOUNT_ID = DETAILS1.PARENT_ACCOUNT) ";
	
	
	protected final static String SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_KEY="SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE";
	protected final static String SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE = " AND DETAILS1.ACCOUNT_ID IN (SELECT DETAILS1.ACCOUNT_ID FROM TABLE (CONNECTBYNAME(CAST(? AS VARCHAR(64)))) AS A)";
	protected final static String SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS_KEY="SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS";
	protected final static String SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS = " AND DETAILS1.ACCOUNT_ID in ((SELECT DETAILS1.ACCOUNT_ID  FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A) intersect (SELECT DETAILS1.ACCOUNT_ID FROM TABLE (CONNECTBYNAME(CAST(? AS VARCHAR(64)))) AS A))";
	
	protected final static String SQL_SELECT_CUSTOMER_TRANSACTION_WHERE_CLAUSE_WITHOUT_ID_KEY ="SQL_SELECT_CUSTOMER_TRANSACTION_WHERE_CLAUSE_WITHOUT_ID";
	protected static final String SQL_SELECT_CUSTOMER_TRANSACTION_WHERE_CLAUSE_WITHOUT_ID = "FROM VR_TRANS_MASTER TRANS, VR_ACCOUNT_DETAILS DETAILS, VR_LOGIN_MASTER LOGIN WHERE TRANS.REQUESTER_ID = DETAILS.ACCOUNT_ID "
			+ "AND TRANS.REQUESTER_ID = LOGIN.LOGIN_ID AND TRANS.TRANS_TYPE != "
			+ RequestType.ACCOUNT.getValue();

	protected final static String SQL_SELECT_CUSTOMER_TRANSACTION_WITHOUT_ID_KEY ="SQL_SELECT_CUSTOMER_TRANSACTION_WITHOUT_ID";
	protected static final String SQL_SELECT_CUSTOMER_TRANSACTION_WITHOUT_ID = "SELECT DISTINCT TRANS.TRANS_ID, TRANS.TRANS_TYPE, LOGIN.LOGIN_NAME, TRANS.TRANS_AMOUNT, TRANS.REQUESTER_ID, DETAILS.ACCOUNT_NAME, DETAILS.MOBILE_NUMBER, DETAILS.PARENT_ACCOUNT, DETAILS.CIRCLE_ID, TRANS.TRANS_DATE, TRANS.TRANS_STATUS, TRANS.REASON_ID, TRANS.RECEIVER_ID, TRANS.TRANS_KEY_VALUE, TRANS.THIRD_PARTY "
		+ SQL_SELECT_CUSTOMER_TRANSACTION_WHERE_CLAUSE_WITHOUT_ID;
	
	protected final static String SQL_FETCH_CUST_TRAN_WHERE_CLAUSE_EXT_USERS_KEY ="SQL_FETCH_CUST_TRAN_WHERE_CLAUSE_EXT_USERS";
	protected final static String SQL_FETCH_CUST_TRAN_WHERE_CLAUSE_EXT_USERS = " AND ACC_DETAILS.ACCOUNT_ID in ((SELECT ACCOUNT_ID  FROM TABLE (CONNECTBYID(CAST(? AS  BIGINT))) AS A))";
	
	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public ReportDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_ACC_CODE_KEY,SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_ACC_CODE);
		queryMap.put(SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_ACC_CODE_KEY,SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_ACC_CODE);
		queryMap.put(SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_USRNAME_EXT_USERS_KEY, SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_USRNAME_EXT_USERS);
		queryMap.put(SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_KEY, SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE);
		queryMap.put(SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS_KEY, SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS);
		queryMap.put(SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_KEY, SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE);
		queryMap.put(SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS_KEY, SQL_FETCH_TRANS_REPORT_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS);
		queryMap.put(SQL_CONNECT_BY_QUERY_KEY,SQL_CONNECT_BY_QUERY);
		queryMap.put(SQL_TRANSACTION_REPORT_CHILD_WHERE_CLAUSE_KEY,SQL_TRANSACTION_REPORT_CHILD_WHERE_CLAUSE);
		queryMap.put(SQL_TRANSACTION_REPORT_CHILD_COUNT_KEY,SQL_TRANSACTION_REPORT_CHILD_COUNT);
		queryMap.put(SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_WHERE_CLAUSE_KEY,SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_WHERE_CLAUSE);
		queryMap.put(SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_KEY,SQL_ALL_CHILD_TRANSACTION_REPORT_DATA);
		queryMap.put(SQL_TRANSFER_SUMMARY_CHILD_COUNT_RECHARGE_KEY,SQL_TRANSFER_SUMMARY_CHILD_COUNT_RECHARGE);
		
		queryMap.put(SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_KEY,
				SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE);
		queryMap
				.put(
						SQL_FETCH_A2A_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS_KEY,
						SQL_FETCH_TRAN_WHERE_CLAUSE_SEARCH_BY_PNT_ACC_CODE_EXT_USERS);
		queryMap.put(SQL_ACC2ACC_REPORT_WHERE_CLAUSE_KEY,
				SQL_ACC2ACC_REPORT_WHERE_CLAUSE);
		
		queryMap.put(SQL_SELECT_CUSTOMER_TRANSACTION_WITHOUT_ID_KEY, SQL_SELECT_CUSTOMER_TRANSACTION_WITHOUT_ID);
		queryMap.put(SQL_SELECT_CUSTOMER_TRANSACTION_WHERE_CLAUSE_WITHOUT_ID_KEY, SQL_SELECT_CUSTOMER_TRANSACTION_WHERE_CLAUSE_WITHOUT_ID);
		
		queryMap.put(SQL_FETCH_CUST_TRAN_WHERE_CLAUSE_EXT_USERS_KEY, SQL_FETCH_CUST_TRAN_WHERE_CLAUSE_EXT_USERS);
		
	}

	/**
	 * This method will be used provide a list of account to account
	 * transactions for external user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransactionRptA2AChildList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {
		logger.info("Inside getTransactionRptA2AChildList()...");

		/** get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account2AccountReport accountReport = null;
		ArrayList<Account2AccountReport> acc2accList = new ArrayList<Account2AccountReport>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder connectByQuery = new StringBuilder();
			StringBuilder query = new StringBuilder();
			int paramCount = 1;
			/**
			 * if status is null then fetch all successful as well as failure
			 * transactions else fetch the transactions based on the status
			 */
			connectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			sql.append(queryMap.get(SQL_ACC2ACC_REPORT_KEY));
			if (!"-1".equals(status)) {
				sql.append(" AND TRANS.TRANS_STATUS = ?");
			}
			sql.append(" AND TRANS_TYPE = ");
			sql.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {

					/** search according to parent account */
					sql
							.append(" AND DETAILS1.PARENT_ACCOUNT = LOGIN.LOGIN_ID AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				}
				/*sql
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ORDER BY TRANS.TRANS_ID DESC ");*/
				sql.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS ) ORDER BY TRANS.TRANS_ID DESC ");
				
				/** apply the pagination query */
				query.append("SELECT * from(select a.*,ROW_NUMBER() over() rnum FROM (");
				query.append(sql);
				query.append(") a)b Where rnum<=? AND rnum>=?");
				connectByQuery.append(query);
				ps = connection.prepareStatement(connectByQuery.toString());

				/** set the values in the statement */
				ps.setLong(paramCount++, parentId);
				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				}
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			}
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				accountReport = new Account2AccountReport();
				accountReport.setReceivingAccountCode(rs
						.getString("RECEIVER_ACC_CODE"));
				accountReport
						.setTransactionAmount(rs.getDouble("TRANS_AMOUNT"));
				accountReport.setTransactionId(rs.getLong("TRANS_ID"));
				accountReport.setStatus(rs.getString("TRANS_STATUS"));
				accountReport.setTransferringAccountCode(rs
						.getString("TRANSFER_ACC_CODE"));
				accountReport.setTransactionDate(rs
						.getTimestamp("CREATED_DATE"));
				accountReport.setCreatedBy(rs.getString("TRANSFER_ACC_CODE"));
				accountReport.setReason(rs.getString("REASON_ID"));
				accountReport.setRowNum(rs.getString("RNUM"));
				accountReport.setTotalRecords(rs.getInt("RECORD_COUNT"));
				/** add each record to the list */
				acc2accList.add(accountReport);
			}

		} catch (SQLException e) {
			logger
					.error(
							"Exception occured while reteriving Account2Account list for external user logged in."
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/** Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed getTransactionRptA2AChildList()...");
		/** return the list */
		return acc2accList;
	}

	/**
	 * This method will be used provide a list of account to account
	 * transactions for internal user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records.
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransactionRptA2AList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {
		logger.info("Inside getTransactionRptA2AList()...");

		/** get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account2AccountReport accountReport = null;
		ArrayList<Account2AccountReport> acc2accList = new ArrayList<Account2AccountReport>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder query = new StringBuilder();
			int paramCount = 1;
			/**
			 * if status is null then fetch all successful as well as failure
			 * transactions else fetch the transactions based on the status.
			 */
			sql.append(queryMap.get(SQL_ACC2ACC_REPORT_KEY));
			if (!"-1".equals(status)) {
				sql.append(" AND TRANS.TRANS_STATUS = ?");
			}
			if (0 != circleId) {
				/** if circle user, show records of respective circle */
				sql.append(" AND DETAILS.CIRCLE_ID = ");
				sql.append(circleId);
			}
			sql.append(" AND TRANS_TYPE = ");
			sql.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					sql
							.append(" AND DETAILS1.PARENT_ACCOUNT = LOGIN.LOGIN_ID AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				}
				sql.append(" ORDER BY TRANS.TRANS_ID DESC");
				/** apply the pagination query */
				query.append("SELECT * from(select a.*,ROW_NUMBER() over() rnum FROM (");
				query.append(sql);
				query.append(") a)b Where rnum<=? AND rnum>=?");
				ps = connection.prepareStatement(query.toString());

				/** set the values in the statement */
				if (!"-1".equals(status)) {
					ps.setString(paramCount++, status);
				}
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				}
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			}
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				accountReport = new Account2AccountReport();
				accountReport.setReceivingAccountCode(rs
						.getString("RECEIVER_ACC_CODE"));
				accountReport
						.setTransactionAmount(rs.getDouble("TRANS_AMOUNT"));
				accountReport.setTransactionId(rs.getLong("TRANS_ID"));
				accountReport.setStatus(rs.getString("TRANS_STATUS"));
				accountReport.setTransferringAccountCode(rs
						.getString("TRANSFER_ACC_CODE"));
				accountReport.setTransactionDate(rs
						.getTimestamp("CREATED_DATE"));
				accountReport.setCreatedBy(rs.getString("TRANSFER_ACC_CODE"));
				accountReport.setReason(rs.getString("REASON_ID"));
				accountReport.setRowNum(rs.getString("RNUM"));
				accountReport.setTotalRecords(rs.getInt("RECORD_COUNT"));
				
				/** add each record to the list */
				acc2accList.add(accountReport);
			}

		} catch (SQLException e) {
			logger
					.error(
							"Exception occured while reteriving Account2Account List for internal user logged in."
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/** Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed getTransactionRptA2AList()...");
		/** return the list */
		return acc2accList;
	}

	
	
	
	/**
	 * This method will be used provide a list of all account to account
	 * transfer summary for external user logged in. This will provide all the
	 * records and is independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransferSummaryRptA2AChildListAll(
			ReportInputs mtDTO) throws DAOException {
		logger.info("Inside getTransferSummaryRptA2AChildListAll()...");

		/** get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtTotalTran = null;
		ResultSet rs = null;
		Account2AccountTransferReport acc2accTransferReport = null;
		ArrayList<Account2AccountTransferReport> acc2accList = new ArrayList<Account2AccountTransferReport>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder connectByQuery = new StringBuilder();
			int paramCount = 1;
			double espCommission = 0.0;

			/**
			 * if status is null then fetch all successful as well as failure
			 * transactions else fetch the transactions based on the status.
			 */
			connectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			sql.append(queryMap.get(SQL_TRANSFER_SUMMARY_COUNT_KEY));
			if (!"-1".equals(status)) {
				sql.append(" AND TRANS.TRANS_STATUS = ?");
			}
			sql.append(" AND TRANS_TYPE = ");
			sql.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					sql
							.append(" AND DETAILS1.PARENT_ACCOUNT = LOGIN.LOGIN_ID AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				/*sql
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ");*/
				sql.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS )");
				connectByQuery.append(sql);
				ps = connection.prepareStatement(connectByQuery.toString());

				/** set the values in the statement */
				ps.setLong(paramCount++, parentId);
				if (!"-1".equals(status)) {
					ps.setString(paramCount++, status);
				}
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				}
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
			}
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				if (rs.getLong("TOTAL_TRANSCTIONS") == 0) {
					logger.error("No records found for the search criteria");
					throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
				}
				acc2accTransferReport = new Account2AccountTransferReport();
				/** if status is successful */
				if ("0".equals(status)) {
					acc2accTransferReport.setNoSuccessfulTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
					acc2accTransferReport.setValueSuccessfulTransaction(rs
							.getDouble("TRANSACTION_AMT"));
					acc2accTransferReport.setTotalTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
				} else if ("1".equals(status)) {
					/** if status is failure */
					acc2accTransferReport.setNoFailedTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
					acc2accTransferReport.setValueFailedTransaction(rs
							.getDouble("TRANSACTION_AMT"));
					acc2accTransferReport.setTotalTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
				} else if ("-1".equals(status)) {
					/** if status is to show all transactions */
					acc2accTransferReport.setValueSuccessfulTransaction(rs
							.getDouble("TRANSACTION_AMT"));
					acc2accTransferReport.setTotalTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
				}
			}

			/**
			 * This query will fetch the msg details information for each
			 * transaction
			 */
			StringBuilder msgDetailsQuery = new StringBuilder();
			StringBuilder msgDetailConnectByQuery = new StringBuilder();
			int msgParamCount = 1;
			/**
			 * if status is null then fetch all successful as well as failure
			 * transactions else fetch the transactions based on the status.
			 */
			msgDetailConnectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			msgDetailsQuery.append(queryMap.get(SQL_TRANSFER_SUMMARY_REPORT_KEY));
			if (!"-1".equals(status)) {
				msgDetailsQuery.append(" AND TRANS.TRANS_STATUS = ?");
			}
			msgDetailsQuery.append(" AND TRANS_TYPE = ");
			msgDetailsQuery.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				msgDetailsQuery.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				msgDetailsQuery.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}
			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					msgDetailsQuery
							.append(" AND DETAILS1.PARENT_ACCOUNT IN(SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ?)");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					msgDetailsQuery
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				/*msgDetailsQuery
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ORDER BY TRANS.TRANS_ID DESC ");*/
				msgDetailsQuery.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS )");
				msgDetailConnectByQuery
				.append(msgDetailsQuery);
				pstmt = connection.prepareStatement(msgDetailConnectByQuery.toString());

				/** set the values in the statement */
				pstmt.setLong(msgParamCount++, parentId);
				if (!"-1".equals(status)) {
					pstmt.setString(msgParamCount++, status);
				}
				if (startDt != null && !startDt.equals("")) {
					pstmt.setDate(msgParamCount++, Utility.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					pstmt.setDate(msgParamCount++, Utility.getSqlDate(endDt));
				}
				pstmt.setString(msgParamCount++, searchValue.toUpperCase()
						+ "%");
			}
			/** execute the query */
			rs = pstmt.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				String message = rs.getString("DETAIL_MSG");
				espCommission = espCommission
						+ Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(
								Constants.REPORT_ESP_COMMISSION_START));

				logger.info("Esp Commission : " + espCommission);

			}
			acc2accTransferReport.setTotalEspCommission(espCommission);
			acc2accTransferReport.setTransactionType(RequestType.ACCOUNT
					.toString());

			/**
			 * This query is used to fetch the total number of transactions
			 * count performed by the user
			 */
			StringBuilder totalTransactionsQuery = new StringBuilder();
			StringBuilder totalTranConnectByQuery = new StringBuilder();
			int totalTranCount = 1;
			
			totalTranConnectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			totalTransactionsQuery.append(queryMap.get(SQL_TRANSFER_SUMMARY_COUNT_KEY));
			totalTransactionsQuery.append(" AND TRANS_TYPE = ");
			totalTransactionsQuery.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				totalTransactionsQuery
						.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				totalTransactionsQuery
						.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}
			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					totalTransactionsQuery
							.append(" AND DETAILS1.PARENT_ACCOUNT IN(SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ?)");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					totalTransactionsQuery
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				/*totalTransactionsQuery
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ");*/
				totalTransactionsQuery.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS )");
				totalTranConnectByQuery.append(totalTransactionsQuery);
				pstmtTotalTran = connection
						.prepareStatement(totalTranConnectByQuery.toString());

				/** set the values in the statement */
				pstmtTotalTran.setLong(totalTranCount++, parentId);
				if (startDt != null && !startDt.equals("")) {
					pstmtTotalTran.setDate(totalTranCount++, Utility
							.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					pstmtTotalTran.setDate(totalTranCount++, Utility
							.getSqlDate(endDt));
				}
				pstmtTotalTran.setString(totalTranCount++, searchValue
						.toUpperCase()
						+ "%");
			}
			/** execute the query */
			rs = pstmtTotalTran.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				acc2accTransferReport.setTotalTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));
			}

			/** add each record to the list */
			acc2accList.add(acc2accTransferReport);
		} catch (SQLException e) {
			logger
					.error(
							"Exception occured while reteriving all account to account transfer summary for external user logged in"
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("ReportDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/** Close the statement,resultset for success or failure transactions */
			DBConnectionManager.releaseResources(ps, rs);
			/** Close the statement,resultset for msg detail query */
			DBConnectionManager.releaseResources(pstmt, rs);
			/** Close the statement,resultset for total transations */
			DBConnectionManager.releaseResources(pstmtTotalTran, rs);
		}
		logger.info("Executed getTransferSummaryRptA2AChildListAll()...");
		/** return the list */
		return acc2accList;
	}

	/**
	 * This method will be used provide a list of all account to account
	 * transfer summary for internal user logged in. This will provide all the
	 * records and is independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransferSummaryRptA2AListAllOld(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Inside getTransferSummaryRptA2AListAll()...");
		/** get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtTotalTran = null;
		ResultSet rs = null;
		Account2AccountTransferReport acc2accTransferReport = null;
		ArrayList<Account2AccountTransferReport> acc2accList = new ArrayList<Account2AccountTransferReport>();

		try {
			StringBuilder sql = new StringBuilder();
			int paramCount = 1;
			double espCommission = 0.0;

			/**
			 * if status is null then fetch all successful as well as failure
			 * transactions else fetch the transactions based on the status.
			 */
			sql.append(queryMap.get(SQL_TRANSFER_SUMMARY_COUNT_KEY));
			if (!"-1".equals(status)) {
				sql.append(" AND TRANS.TRANS_STATUS = ?");
			}
			if (0 != circleId) {
				/** if circle user, show records of respective circle */
				sql.append(" AND DETAILS.CIRCLE_ID = ");
				sql.append(circleId);
			}
			sql.append(" AND TRANS_TYPE = ");
			sql.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					sql
							.append(" AND DETAILS1.PARENT_ACCOUNT = LOGIN.LOGIN_ID AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				ps = connection.prepareStatement(sql.toString());

				/** set the values in the statement */
				if (!"-1".equals(status)) {
					ps.setString(paramCount++, status);
				}
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				}
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
			}
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				if (rs.getLong("TOTAL_TRANSCTIONS") == 0) {
					logger.error("No records found for the search criteria");
					throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
				}
				acc2accTransferReport = new Account2AccountTransferReport();
				if ("0".equals(status)) {
					/** if status is success */
					acc2accTransferReport.setNoSuccessfulTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
					acc2accTransferReport.setValueSuccessfulTransaction(rs
							.getDouble("TRANSACTION_AMT"));
					acc2accTransferReport.setTotalTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
				} else if ("1".equals(status)) {
					/** if status is failure */
					acc2accTransferReport.setNoFailedTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
					acc2accTransferReport.setValueFailedTransaction(rs
							.getDouble("TRANSACTION_AMT"));
					acc2accTransferReport.setTotalTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
				} else if ("-1".equals(status)) {
					/** if status is to show all transactions */
					acc2accTransferReport.setValueSuccessfulTransaction(rs
							.getDouble("TRANSACTION_AMT"));
					acc2accTransferReport.setTotalTransaction(rs
							.getLong("TOTAL_TRANSCTIONS"));
				}
				acc2accTransferReport.setTransactionType(RequestType.ACCOUNT
						.toString());
			}

			/**
			 * This query will fetch the msg details information for each
			 * transaction
			 */
			StringBuilder msgDetailsQuery = new StringBuilder();
			int msgParamCount = 1;
			/**
			 * if status is null then fetch all successful as well as failure
			 * transactions else fetch the transactions based on the status.
			 */
			msgDetailsQuery.append(queryMap.get(SQL_TRANSFER_SUMMARY_REPORT_KEY));
			if (!"-1".equals(status)) {
				msgDetailsQuery.append(" AND TRANS.TRANS_STATUS = ?");
			}
			if (0 != circleId) {
				/** if circle user, show records of respective circle */
				msgDetailsQuery.append(" AND DETAILS.CIRCLE_ID = ");
				msgDetailsQuery.append(circleId);
			}
			msgDetailsQuery.append(" AND TRANS_TYPE = ");
			msgDetailsQuery.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				msgDetailsQuery.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				msgDetailsQuery.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					msgDetailsQuery
							.append(" AND DETAILS1.PARENT_ACCOUNT IN(SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ?)");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					msgDetailsQuery
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				pstmt = connection.prepareStatement(msgDetailsQuery.toString());

				/** set the values in the statement */
				if (!"-1".equals(status)) {
					pstmt.setString(msgParamCount++, status);
				}
				if (startDt != null && !startDt.equals("")) {
					pstmt.setDate(msgParamCount++, Utility.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					pstmt.setDate(msgParamCount++, Utility.getSqlDate(endDt));
				}
				pstmt.setString(msgParamCount++, searchValue.toUpperCase()
						+ "%");
			}
			/** execute the query */
			rs = pstmt.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				String message = rs.getString("DETAIL_MSG");
				espCommission = espCommission
						+ Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_ESP_COMMISSION_START));

				logger.info("Esp Commission : " + espCommission);

			}
			acc2accTransferReport.setTotalEspCommission(espCommission);

			/**
			 * This query is used to fetch the total number of transactions
			 * count performed by the user
			 */
			StringBuilder totalTransactionsQuery = new StringBuilder();
			int totalTranCount = 1;
			totalTransactionsQuery.append(queryMap.get(SQL_TRANSFER_SUMMARY_COUNT_KEY));

			if (0 != circleId) {
				/** if circle user, show records of respective circle */
				totalTransactionsQuery.append(" AND DETAILS.CIRCLE_ID = ");
				totalTransactionsQuery.append(circleId);
			}
			totalTransactionsQuery.append(" AND TRANS_TYPE = ");
			totalTransactionsQuery.append(RequestType.ACCOUNT.getValue());

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				totalTransactionsQuery
						.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				totalTransactionsQuery
						.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/** search according to parent account */
					totalTransactionsQuery
							.append(" AND DETAILS1.PARENT_ACCOUNT IN(SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ?)");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/** search according to account code */
					totalTransactionsQuery
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}

				pstmtTotalTran = connection
						.prepareStatement(totalTransactionsQuery.toString());

				/** set the values in the statement */
				if (startDt != null && !startDt.equals("")) {
					pstmtTotalTran.setDate(totalTranCount++, Utility
							.getSqlDate(startDt));
				}
				if (endDt != null && !endDt.equals("")) {
					pstmtTotalTran.setDate(totalTranCount++, Utility
							.getSqlDate(endDt));
				}
				pstmtTotalTran.setString(totalTranCount++, searchValue
						.toUpperCase()
						+ "%");
			}
			/** execute the query */
			rs = pstmtTotalTran.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				acc2accTransferReport.setTotalTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));
			}

			/** add each record to the list */
			acc2accList.add(acc2accTransferReport);
		} catch (SQLException e) {
			logger
					.error(
							"Exception occured while retrieving a list of all account to account transfer summary for internal user logged in"
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("ReportDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		} finally {
			/** Close the statement,resultset for success or failure transactions */
			DBConnectionManager.releaseResources(ps, rs);
			/** Close the statement,resultset for msg detail query */
			DBConnectionManager.releaseResources(pstmt, rs);
			/** Close the statement,resultset for total transations */
			DBConnectionManager.releaseResources(pstmtTotalTran, rs);
		}
		logger.info("Executed getTransferSummaryRptA2AListAll ...");
		return acc2accList;
	}

	

		// *********** Implementation for Transaction Report Starts *********

	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.ReportDao#getTransactionRptList(com.ibm.virtualization.recharge.dto.ReportInputs,
	 *      int, int)
	 */
	public ArrayList getTransactionRptList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {
		logger.info("Inside getTransactionRptList()...");

		/* get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		RequestType requestType = mtDTO.getRequestType();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		TransactionReport transactionReport = null;
		ArrayList<TransactionReport> transactionList = new ArrayList<TransactionReport>();

		try {
			StringBuilder strSql = new StringBuilder();
			StringBuilder strQuery = new StringBuilder();
			int paramCount = 1;
			strSql.append(queryMap.get(SQL_TRANSACTION_REPORT_DATA_KEY));
			if (0 != circleId) {
				// if circle user, show records of respective circle
				strSql.append(" AND DETAILS.CIRCLE_ID = ");
				strSql.append(circleId);
			}

			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());
			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}// else if Request Type is VAS

			// TODO : Paras November 23, 2007 , if Request Type is VAS
			// , append TransactionState accordingly
			strSql.append(" OR TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");

			strSql.append(" AND TRANS.TRANS_TYPE = ? ");
			strSql.append(" AND TRANS.TRANS_STATUS = ? ");
			strSql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			strSql.append("AND DATE(TRANS.CREATED_DATE) <= ? ");
			/*
			 * if(requestType.getValue()!=RequestType.RECHARGE.getValue()){
			 * strSql.append(" AND TRANS.RECEIVER_ID = DETAILS1.MOBILE_NUMBER
			 * "); }else{ strSql.append(" AND TRANS.RECEIVER_ID =
			 * DETAILS1.ACCOUNT_ID "); }
			 */
			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					strSql
							.append(" AND DETAILS.PARENT_ACCOUNT = LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ");

					strQuery
							.append("SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (");
					strQuery.append(strSql.toString());
					strQuery.append(" ORDER BY TRANS.TRANS_ID DESC  ) a "
							+ ")b Where rnum<= ? AND rnum>=? ");
					// SQL Query
					logger
							.info(" Query for getTransactionRptList for Parent Code "
									+ strQuery.toString());

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					strSql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");

					strQuery
							.append(" SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (");
					strQuery.append(strSql.toString());

					strQuery.append(" ORDER BY TRANS.TRANS_ID DESC ) a ")
							.append(")b Where rnum<= ? AND rnum>=? ");
					// SQL Query
					logger
							.info(" Query for getTransactionRptList for Account Code "
									+ strQuery.toString());
				}
				ps = connection.prepareStatement(strQuery.toString());
				logger.info(strQuery.toString());
				ps.setInt(paramCount++, requestType.getValue());
				ps.setInt(paramCount++, Integer.parseInt(status));
				ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				transactionReport = new TransactionReport();

				// new change group name added
				transactionReport.setTransactionId(rs.getLong("TRANS_ID"));
				transactionReport.setTransactionType(TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE")));
				// transactionReport.setMtpAccountCode(rs.getString("REQUESTER_ID"));
				transactionReport.setMtpAccountCode(rs
						.getString("TRANSFER_ACC_CODE"));

				transactionReport
						.setCustomerMobile(rs.getString("RECEIVER_ID"));
				transactionReport.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				// till the time message is parsed using the parseMessage method
				transactionReport.setCommission(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "espCommission"));
				transactionReport.setProcessingFee(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "processingFee"));
				transactionReport.setServiceTax(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "serviceTax"));
				transactionReport.setTalkTime(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "CreditedAmount"));
				transactionReport.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				transactionReport.setStatus(rs.getInt("TRANS_STATUS"));
				transactionReport.setCreatedBy(rs.getString("CREATED_BY"));
				transactionReport.setReason(rs.getString("REASON_ID"));
				transactionReport.setRowNum(rs.getString("RNUM"));
				transactionReport.setTotalRecords(rs.getInt("RECORD_COUNT"));
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"),
						ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
		        logger.debug("Validity : " + validity);
		        transactionReport.setValidity(validity);
				transactionList.add(transactionReport);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("ReportDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getTransactionRptList()...");
		return transactionList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.ReportDao#getTransactionRptChildList(com.ibm.virtualization.recharge.dto.ReportInputs,
	 *      int, int)
	 */
	public ArrayList getTransactionRptChildList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {
		logger.info(" Started... getTransactionRptChildList()...");

		/* get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		RequestType requestType = mtDTO.getRequestType();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		TransactionReport transactionReport = null;
		ArrayList<TransactionReport> transactionList = new ArrayList<TransactionReport>();

		try {
			StringBuilder strSql = new StringBuilder();
			StringBuilder strQuery = new StringBuilder();
			StringBuilder connectByQuery = new StringBuilder();
			
			int paramCount = 1;
			connectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			strSql.append(queryMap.get(SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_KEY));
			//	
			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());
			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}// else if Request Type is VAS

			// TODO : Paras November 23, 2007 , if Request Type is VAS
			// , append TransactionState accordingly
			strSql.append(" OR TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");

			if (0 != parentId) {
				strSql
						.append(" AND TRANS.TRANS_STATUS = ? AND TRANS.TRANS_TYPE = ? ");
				strSql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
				strSql.append("AND DATE(TRANS.CREATED_DATE) <= ? ");
			}

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					strSql
							.append(" AND DETAILS.PARENT_ACCOUNT = LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ");

					strQuery
							.append("SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (");
					strQuery.append(strSql.toString());
					strQuery.append(" ORDER BY TRANS.TRANS_ID DESC ) a ")
							.append(")b Where rnum<=? AND rnum>=?");
					// SQL Query
					logger
							.info(" Query for getTransactionRptChildList for Parent Code "
									+ strQuery.toString());
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					strSql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");

					strQuery
							.append("SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (");
					strQuery.append(strSql.toString());
					strQuery.append(" ORDER BY TRANS.TRANS_ID DESC ) a ")
							.append(")b Where rnum<=? AND rnum>=?");
					// SQL Query
					logger
							.info(" Query for getTransactionRptChildList for Account Code "
									+ strQuery.toString());
				}
				connectByQuery.append(strQuery);
				ps = connection.prepareStatement(connectByQuery.toString());
				ps.setLong(paramCount++, parentId);
				ps.setInt(paramCount++, Integer.parseInt(status));
				ps.setInt(paramCount++, requestType.getValue());
				ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				transactionReport = new TransactionReport();
				transactionReport.setTransactionId(rs.getLong("TRANS_ID"));
				transactionReport.setTransactionType(TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE")));
				// transactionReport.setMtpAccountCode(rs.getString("TRANSFER_ACC_CODE"));
				transactionReport.setMtpAccountCode(rs
						.getString("TRANSFER_ACC_CODE"));

				transactionReport
						.setCustomerMobile(rs.getString("RECEIVER_ID"));
				transactionReport.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				// till the time message is parsed using the parseMessage method
				transactionReport.setCommission(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "espCommission"));
				transactionReport.setProcessingFee(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "processingFee"));
				transactionReport.setServiceTax(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "serviceTax"));
				transactionReport.setTalkTime(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "CreditedAmount"));
				transactionReport.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				transactionReport.setStatus(rs.getInt("TRANS_STATUS"));
				transactionReport.setCreatedBy(rs.getString("CREATED_BY"));
				transactionReport.setReason(rs.getString("REASON_ID"));
				// new change group name added
				transactionReport.setRowNum(rs.getString("RNUM"));
				transactionReport.setTotalRecords(rs.getInt("RECORD_COUNT"));
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"),
						ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
		        logger.debug("Validity : " + validity);
				transactionList.add(transactionReport);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("ReportDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getTransactionRptChildList()...");
		return transactionList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.ReportDao#getTransactionRptListAll(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public ArrayList getTransactionRptListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Inside getTransactionRptListAll()...");
		/* get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		RequestType requestType = mtDTO.getRequestType();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		TransactionReport transactionReport = null;
		ArrayList<TransactionReport> transactionList = new ArrayList<TransactionReport>();

		try {
			StringBuilder strSql = new StringBuilder();
			int paramCount = 1;

			strSql.append(queryMap.get(SQL_TRANSACTION_REPORT_DATA_KEY));

			if (0 != circleId) {
				strSql.append(" AND DETAILS.CIRCLE_ID = ");
				strSql.append(circleId);
			}

			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());

			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}// else if Request Type is VAS, PostPaid

			// TODO : Paras November 23, 2007 , if Request Type is VAS, PostPaid
			// , append TransactionState accordingly
			strSql.append(" OR TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");

			strSql.append(" AND TRANS.TRANS_STATUS = ?");
			strSql.append(" AND TRANS.TRANS_TYPE = ?");
			strSql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			strSql.append(" AND DATE(TRANS.CREATED_DATE) <= ?");

			if (searchType != null) {
				// search according to parent code
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					strSql
							.append(" AND DETAILS.PARENT_ACCOUNT = LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ORDER BY TRANS_ID DESC ");

					// SQL
					
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					strSql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ORDER BY TRANS_ID DESC ");
					
				}
				StringBuilder connectbyClause = new StringBuilder();
				connectbyClause.append(queryMap.get(SQL_CONNECT_BY_NAME_QUERY_KEY));
				strSql=connectbyClause.append(strSql);
				logger
				.info(" Query for getTransactionRptListAll for Account Code "
						+ strSql.toString());
				ps = connection.prepareStatement(strSql.toString());
				ps.setString(paramCount++,searchValue );
				ps.setInt(paramCount++, Integer.parseInt(status));
				ps.setInt(paramCount++, requestType.getValue());
				ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				transactionReport = new TransactionReport();

				// new change group name added
				transactionReport.setTransactionId(rs.getLong("TRANS_ID"));
				transactionReport.setTransactionType(TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE")));
				// transactionReport.setMtpAccountCode(rs.getString("TRANSFER_ACC_CODE"));
				transactionReport.setMtpAccountCode(rs
						.getString("TRANSFER_ACC_CODE"));
				transactionReport
						.setCustomerMobile(rs.getString("RECEIVER_ID"));
				transactionReport.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				// till the time message is parsed using the parseMessage method
				transactionReport.setCommission(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "espCommission"));
				transactionReport.setProcessingFee(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "processingFee"));
				transactionReport.setServiceTax(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "serviceTax"));
				transactionReport.setTalkTime(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "CreditedAmount"));
				transactionReport.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				transactionReport.setStatus(rs.getInt("TRANS_STATUS"));
				transactionReport.setCreatedBy(rs.getString("CREATED_BY"));
				transactionReport.setReason(rs.getString("REASON_ID"));
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"),
						ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
		        logger.debug("Validity : " + validity);
				transactionList.add(transactionReport);
			}

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving All Transsaction List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("ReportDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getTransactionRptListAll ...");
		return transactionList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.ReportDao#getTransactionRptChildListAll(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public ArrayList getTransactionRptChildListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Inside getTransactionRptChildListAll()...");
		/* get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		RequestType requestType = mtDTO.getRequestType();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		TransactionReport transactionReport = null;
		ArrayList<TransactionReport> transactionList = new ArrayList<TransactionReport>();

		try {
			StringBuilder strSql = new StringBuilder();
			StringBuilder connectByQuery = new StringBuilder();
			int paramCount = 1;
			
			connectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			strSql.append(queryMap.get(SQL_ALL_CHILD_TRANSACTION_REPORT_DATA_KEY));

			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());
			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				strSql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}// else if Request Type is VAS,

			// TODO : Paras November 23, 2007 , if Request Type is VAS
			// , append TransactionState accordingly
			strSql.append(" OR TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");

			if (0 != circleId) {
				// if circle user, show records of respective circle
				strSql.append(" AND DETAILS.CIRCLE_ID = ");
				strSql.append(circleId);
			}

			strSql.append(" AND LOGIN.STATUS = ? ");
			strSql.append(" AND TRANS.TRANS_TYPE = ? ");
			strSql.append(" AND DATE(TRANS.CREATED_DATE) >= ? ");
			strSql.append(" AND DATE(TRANS.CREATED_DATE) <= ? ");

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					strSql
							.append(" AND DETAILS.PARENT_ACCOUNT=LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ORDER BY TRANS_ID ");

					logger
							.info(" Query for getTransactionRptChildListAll for Parent Code "
									+ strSql.toString());

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					strSql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ORDER BY TRANS_ID ");

					logger
							.info(" Query for getTransactionRptChildListAll for Account Code "
									+ strSql.toString());
				}
				connectByQuery.append(strSql);
				
				ps = connection.prepareStatement(connectByQuery.toString());
				ps.setLong(paramCount++, parentId);
				ps.setInt(paramCount++, Integer.parseInt(status));
				ps.setInt(paramCount++, requestType.getValue());
				ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				transactionReport = new TransactionReport();
				transactionReport.setTransactionId(rs.getLong("TRANS_ID"));
				transactionReport.setTransactionType(TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE")));
				// transactionReport.setMtpAccountCode(rs.getString("TRANSFER_ACC_CODE"));
				transactionReport.setMtpAccountCode(rs
						.getString("TRANSFER_ACC_CODE"));

				transactionReport
						.setCustomerMobile(rs.getString("RECEIVER_ID"));
				transactionReport.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				// till the time message is parsed using the parseMessage method
				transactionReport.setCommission(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "espCommission"));
				transactionReport.setProcessingFee(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "processingFee"));
				transactionReport.setServiceTax(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "serviceTax"));
				transactionReport.setTalkTime(Utility.parseMessage(rs
						.getString("DETAIL_MSG"), "CreditedAmount"));

				transactionReport.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				transactionReport.setStatus(rs.getInt("TRANS_STATUS"));
				transactionReport.setCreatedBy(rs.getString("CREATED_BY"));
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"),
						ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
		        logger.debug("Validity : " + validity);
				transactionList.add(transactionReport);
			}

		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("ReportDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}catch (SQLException e) {
			logger.error("Exception occured while reteriving All Child List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getTransactionRptChildListAll()...");
		return transactionList;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.ReportDao#getTransactionSummaryRptChildData(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */

	// if not internal user
	public ArrayList getTransactionSummaryRptChildData(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started..." + mtDTO.toString());
		/* get the data from the input DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		long parentId = mtDTO.getParentId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		PreparedStatement ps = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtTotalTran = null;
		ResultSet rs = null;

		Account2AccountTransferReport acc2accTransferReport = null;
		ArrayList<Account2AccountTransferReport> transactionSumReportList = new ArrayList<Account2AccountTransferReport>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder connectByQuery = new StringBuilder();
			
			int paramCount = 1;
			double espCommission = 0.0;
			/**
			 * if status is null then show all successful as well as failure
			 * transactions
			 */
			connectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			sql.append(queryMap.get(SQL_TRANSFER_SUMMARY_CHILD_COUNT_RECHARGE_KEY));
			sql.append(" AND TRANS.TRANS_STATUS = 0 ");
			// for successful transaction start
			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {
				sql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				sql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());
			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				sql.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}
			sql.append(" OR  TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");

			// TODO : BHANU

			if (0 != circleId) {
				// if circle user, show records of respective circle
				sql.append(" AND DETAILS.CIRCLE_ID = ").append(circleId);

			}
			// if Administrator , show records from all circles
			sql
					.append(" AND TRANS_TYPE = ")
					.append(mtDTO.getRequestType().getValue())
					.append(
							" AND DATE(TRANS.CREATED_DATE) >= ? AND DATE(TRANS.CREATED_DATE) <= ?");

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					sql
							.append(" AND DETAILS1.ACCOUNT_ID IN(SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ?)");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				connectByQuery.append(sql);
				ps = connection.prepareStatement(connectByQuery.toString());
				
				ps.setLong(paramCount++, parentId);
				ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				acc2accTransferReport = new Account2AccountTransferReport();
				acc2accTransferReport.setNoSuccessfulTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));
				acc2accTransferReport.setValueSuccessfulTransaction(rs
						.getDouble("TRANSACTION_AMT"));
				acc2accTransferReport.setTotalTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));

				acc2accTransferReport.setTransactionType(mtDTO.getRequestType()
						.name());

				logger.info("TOTAL_TRANSATCIONS..."
						+ rs.getLong("TOTAL_TRANSCTIONS"));
				logger.info("TRANSACTION_AMT..."
						+ rs.getDouble("TRANSACTION_AMT"));
				logger.info("TOTAL_TRANSATCIONS..."
						+ rs.getLong("TOTAL_TRANSCTIONS"));
			}
			// end of successful transaction

			// start of failure
			StringBuilder sqlFailure = new StringBuilder();
			StringBuilder sqlFailureConnectByQuery = new StringBuilder();
			
			paramCount = 1;
			sqlFailureConnectByQuery.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			sql.append(queryMap.get(SQL_TRANSFER_SUMMARY_CHILD_COUNT_RECHARGE_KEY));
			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {

				sqlFailure.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				sqlFailure.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());

			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				sqlFailure.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}
			sqlFailure.append(" OR  TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");

			sqlFailure.append(" AND TRANS_TYPE = ").append(
					mtDTO.getRequestType().getValue());
			sqlFailure.append("  AND TRANS.TRANS_STATUS = 2 ");

			if (0 != circleId) {
				// if circle user, show records of respective circle
				sqlFailure.append(" AND DETAILS.CIRCLE_ID = ").append(circleId);
			}
			// if Administrator , show records from all circles
			sqlFailure
					.append("  AND DATE(TRANS.CREATED_DATE) >= ? AND DATE(TRANS.CREATED_DATE) <= ?");

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					sqlFailure
							.append(" AND DETAILS.PARENT_ACCOUNT=LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					sqlFailure.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");

				}
				sqlFailureConnectByQuery.append(sqlFailure);				
				ps = connection.prepareStatement(sqlFailureConnectByQuery.toString());
				
				ps.setLong(paramCount++, parentId);
				ps.setDate(paramCount++, Utility.getSqlDate(startDt));
				ps.setDate(paramCount++, Utility.getSqlDate(endDt));
				ps.setString(paramCount++, searchValue.toUpperCase() + "%");
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				// acc2accTransferReport = new Account2AccountTransferReport();
				acc2accTransferReport.setNoFailedTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));
				acc2accTransferReport.setValueFailedTransaction(rs
						.getDouble("TRANSACTION_AMT"));
				acc2accTransferReport.setTotalTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));

				logger.info("FailedTransaction TOTAL_TRANSATCIONSq..."
						+ rs.getLong("TOTAL_TRANSCTIONS"));
				logger.info("FailedTransaction TRANSACTION_AMTq..."
						+ rs.getDouble("TRANSACTION_AMT"));
				logger.info("FailedTransaction TOTAL_TRANSATCIONSq..."
						+ rs.getLong("TOTAL_TRANSCTIONS"));

				// acc2accTransferReport.setTransactionType(mtDTO.getTransactionType().name());
			}

			// end of failure

			// total
			StringBuilder msgDetailsQuery = new StringBuilder();
			StringBuilder msgDetailsConnectByQuery = new StringBuilder();
			
			int msgParamCount = 1;
			/**
			 * if status is null then show all successful as well as failure
			 * transactions
			 */
			msgDetailsConnectByQuery
			.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			msgDetailsQuery
					.append(queryMap.get(SQL_ALL_CHILD_TRANSFER_SUMMARY_REPORT_RECHARGE_KEY));
			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {

				msgDetailsQuery.append(" AND TDETAIL.TRANS_STATE = ").append(
						TransactionState.RECHARGE_REQUEST_LISTENER.getValue());

			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				msgDetailsQuery.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.POSTPAID_REQUEST_LISTENER.getValue());

			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				msgDetailsQuery.append(" AND ( TDETAIL.TRANS_STATE = ").append(
						TransactionState.VAS_REQUEST_LISTENER.getValue());

			}

			msgDetailsQuery.append(" OR  TDETAIL.TRANS_STATE = ").append(
					TransactionState.WEB.getValue()).append(" ) ");
			msgDetailsQuery.append(" AND TRANS.TRANS_STATUS = ?   ");

			if (0 != circleId) {
				// if circle user, show records of respective circle
				msgDetailsQuery.append(" AND DETAILS.CIRCLE_ID = ").append(
						circleId);

			}
			// if Administrator , show records from all circles
			msgDetailsQuery
					.append(" AND TRANS_TYPE = ")
					.append(mtDTO.getRequestType().getValue())
					.append(
							" AND DATE(TRANS.CREATED_DATE) >= ? AND DATE(TRANS.CREATED_DATE) <= ?");

			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					msgDetailsQuery
							.append(" AND DETAILS.PARENT_ACCOUNT=LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					msgDetailsQuery
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				}
				msgDetailsConnectByQuery.append(msgDetailsQuery);
				pstmt = connection.prepareStatement(msgDetailsConnectByQuery.toString());
				
				ps.setLong(paramCount++, parentId);
				pstmt.setInt(msgParamCount++, 0);
				// pstmt.setInt(msgParamCount++, 2);
				pstmt.setDate(msgParamCount++, Utility.getSqlDate(startDt));
				pstmt.setDate(msgParamCount++, Utility.getSqlDate(endDt));
				pstmt.setString(msgParamCount++, searchValue.toUpperCase()
						+ "%");
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				espCommission = espCommission
						+ Utility.parseMessage(rs.getString("DETAIL_MSG"),
								"espCommission");
				logger.info("Esp Commission : " + espCommission);
			}
			acc2accTransferReport.setTotalEspCommission(espCommission);

			StringBuilder totalTransactionsQuery = new StringBuilder();
			StringBuilder totalTranConnectByQuery = new StringBuilder();
			
			int totalTranCount = 1;
			totalTranConnectByQuery
			.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			totalTransactionsQuery
					.append(queryMap.get(SQL_TRANSFER_SUMMARY_CHILD_COUNT_RECHARGE_KEY));
			if (mtDTO.getRequestType().getValue() == RequestType.RECHARGE
					.getValue()) {
				totalTransactionsQuery.append(" AND ( TDETAIL.TRANS_STATE = ")
						.append(
								TransactionState.RECHARGE_REQUEST_LISTENER
										.getValue());
			} else if 
			((mtDTO.getRequestType().getValue() == RequestType.POSTPAID_MOBILE.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_ABTS.getValue())||(mtDTO.getRequestType().getValue() == RequestType.POSTPAID_DTH.getValue())) {
				totalTransactionsQuery.append(" AND ( TDETAIL.TRANS_STATE = ")
						.append(
								TransactionState.POSTPAID_REQUEST_LISTENER
										.getValue());

			} else if (mtDTO.getRequestType().getValue() == RequestType.VAS
					.getValue()) {
				totalTransactionsQuery
						.append(" AND ( TDETAIL.TRANS_STATE = ")
						.append(TransactionState.VAS_REQUEST_LISTENER.getValue());

			}
			totalTransactionsQuery.append(" OR  TDETAIL.TRANS_STATE = ")
					.append(TransactionState.WEB.getValue()).append(" ) ");

			if (0 != circleId) {
				// if circle user, show records of respective circle
				totalTransactionsQuery.append(" AND DETAILS.CIRCLE_ID = ")
						.append(circleId);
			}
			totalTransactionsQuery.append(" AND TRANS_TYPE = ").append(
					mtDTO.getRequestType().getValue());
			totalTransactionsQuery
					.append(" AND DATE(TRANS.CREATED_DATE) >= ? AND DATE(TRANS.CREATED_DATE) <= ?");
			if (searchType != null) {
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT_CODE)) {
					/* search according to parent account */
					totalTransactionsQuery
							.append(" AND DETAILS.PARENT_ACCOUNT=LOGIN1.LOGIN_ID AND UPPER(LOGIN1.LOGIN_NAME) LIKE ? ");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ACCOUNT_CODE)) {
					/* search according to account code */
					totalTransactionsQuery
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");

				}
				totalTranConnectByQuery.append(totalTransactionsQuery);
				pstmtTotalTran = connection
						.prepareStatement(totalTranConnectByQuery.toString());
				
				ps.setLong(paramCount++, parentId);
				pstmtTotalTran.setDate(totalTranCount++, Utility
						.getSqlDate(startDt));
				pstmtTotalTran.setDate(totalTranCount++, Utility
						.getSqlDate(endDt));
				pstmtTotalTran.setString(totalTranCount++, searchValue
						.toUpperCase()
						+ "%");
			}
			rs = pstmtTotalTran.executeQuery();
			while (rs.next()) {
				acc2accTransferReport.setTotalTransaction(rs
						.getLong("TOTAL_TRANSCTIONS"));

			}

			transactionSumReportList.add(acc2accTransferReport);
		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset for success or failure transactions */
			DBConnectionManager.releaseResources(ps, rs);
			/* Close the statement,resultset for msg detail query */
			DBConnectionManager.releaseResources(pstmt, rs);
			/* Close the statement,resultset for total transations */
			DBConnectionManager.releaseResources(pstmtTotalTran, rs);
		}
		logger.info("Executed  ...");
		return transactionSumReportList;
	}

	/**
	 * This method will be used provide a list of customer transactions for
	 * internal user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records.
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.bhanu
	 */
	public ArrayList getCustomerTransactionList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {

		logger.info("Started getCustomerTransactionList()..");

		/** get the data from the input DTO */
		String searchFieldValue = mtDTO.getSearchFieldValue();
		int tranId = 0;
		
		if (searchFieldValue != null && !searchFieldValue.equals("")) {
			tranId = Integer.parseInt(searchFieldValue);
		}
		int tranType;
		if (null != mtDTO.getRequestType()) {
			tranType = mtDTO.getRequestType().getValue();
		} else {
			tranType = -1;
		}

		int circleId = mtDTO.getCircleId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		/** use to store DTO objects */
		ArrayList<CustomerTransaction> results = new ArrayList<CustomerTransaction>();
		CustomerTransaction customerTransaction = null;
		StringBuilder query = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		try {
			int paramCount = 1;
				if (0 != tranId) {
						sql.append(queryMap.get(SQL_SELECT_CUSTOMER_TRANSACTION_KEY));
					}else{
						sql.append(queryMap.get(SQL_SELECT_CUSTOMER_TRANSACTION_WITHOUT_ID_KEY));
					}
					if (0 != circleId) {
						/** if circle user, show records of respective circle */
						sql.append(" AND DETAILS.CIRCLE_ID = ");
						sql.append(circleId);
					}
		
					if (-1 != tranType) {
						sql.append(" AND TRANS.TRANS_TYPE = ?");
					}	
					if (0 != tranId) {
						/** search according to Transaction Id */
						sql.append(" AND TRANS.TRANS_ID = ? ");
					}
					if (startDt != null && !startDt.equals("")) {
						/** search according to Start Date */
						sql.append(" AND DATE(TRANS.TRANS_DATE) >= ? ");
					}
					if (endDt != null && !endDt.equals("")) {
						/** search according to End date */
						sql.append(" AND DATE(TRANS.TRANS_DATE) <= ? ");
					}
					sql.append(" ORDER BY TRANS.TRANS_ID DESC");
					/** apply the pagination query */
					query.append("SELECT * FROM(SELECT a.*,ROW_NUMBER() OVER() rnum FROM (SELECT c.*, COUNT(*) over() RECORD_COUNT FROM ( ");
					//query.append("SELECT * FROM  ( SELECT a.*, ROW_NUMBER() over() rnum FROM ( ");
					query.append(sql);
					//query.append(" )a ) b WHERE rnum<=100 AND rnum>=? ");
					query.append(")c)a)b Where rnum<=? AND rnum>=?");
					
					logger.debug("Final Query is "+query.toString());
					
					ps = connection.prepareStatement(query.toString());
		
					/** set the values in the statement */
					if (-1 != tranType) {
						ps.setInt(paramCount++, tranType);
					}
		
					if (0 != tranId) {
						ps.setInt(paramCount++, tranId);
					}
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
					
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				customerTransaction = new CustomerTransaction();
				customerTransaction.setTransactionId(rs.getLong("TRANS_ID"));
				customerTransaction.setSourceAccountCode(rs
						.getString("LOGIN_NAME"));
				customerTransaction.setCustomerMobileNo(rs
						.getString("RECEIVER_ID"));
				customerTransaction.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				customerTransaction.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				

				// Send the Transaction Status ordinal value instead of constant
				// charachers.
				customerTransaction.setStatus(String.valueOf(rs
						.getInt("TRANS_STATUS")));

					customerTransaction.setReason(rs.getString("REASON_ID"));
					customerTransaction.setTotalRecords(rs.getInt("RECORD_COUNT"));
					customerTransaction.setRowNum(rs.getString("RNUM"));
					customerTransaction.setTransationType((TransactionType
							.getTransactionType(rs.getInt("TRANS_TYPE"))).name());
					


				if (0 != tranId) {
					
				String message = rs.getString("DETAIL_MSG");
				double serviceTax = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_SERVICE_TAX_START));
				logger.info("serviceTax : " + serviceTax);
				customerTransaction.setServiceTax(serviceTax);

				double espCommission = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_ESP_COMMISSION_START));
				logger.info("espCommission : " + espCommission);
				customerTransaction.setEspCommission(espCommission);

				double processingFee = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_PROCESSING_FEE_START));
				logger.info("processingFee : " + processingFee);
				customerTransaction.setProcessingFee(processingFee);
				
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"), ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
				logger.info("validity : " + validity);
				customerTransaction.setValidity(validity);


				double talkTime = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_TALK_TIME_START));
				logger.info("talkTime : " + talkTime);
				customerTransaction.setTalkTime(talkTime);
			}
				/** adding each DTO object into array List */
				results.add(customerTransaction);
			}

		} catch (SQLException e) {
			logger
					.fatal(
							"Exception occured while reteriving customer transation list for internal user logged in."
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DistributorTransactionDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		finally {
			/** Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed getCustomerTransactionList().... Customer Transaction List SuccessFully Retreived");
		return results;
	}

	/**
	 * This method will be used provide a list of all customer transactions for
	 * internal user logged in. This will provide all the records and is
	 * independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getCustomerTransactionListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getCustomerTransactionListAll()...");

		/** get the data from the input DTO */
		String searchFieldValue = mtDTO.getSearchFieldValue();
		int tranId = 0;
		if (searchFieldValue != null && !searchFieldValue.equals("")) {
			tranId = Integer.parseInt(searchFieldValue);
		}
		int tranType;
		if (null != mtDTO.getRequestType()) {
			tranType = mtDTO.getRequestType().getValue();
		} else {
			tranType = -1;
		}
		int circleId = mtDTO.getCircleId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		/** use to store DTO objects */
		ArrayList<CustomerTransaction> results = new ArrayList<CustomerTransaction>();
		CustomerTransaction customerTransaction = null;
		StringBuilder sql = new StringBuilder();
		try {
			int paramCount = 1;
			sql.append(queryMap.get(SQL_SELECT_CUSTOMER_TRANSACTION_KEY));

			if (0 != circleId) {
				/** if circle user, show records of respective circle */
				sql.append(" AND DETAILS.CIRCLE_ID = ");
				sql.append(circleId);
			}
			if (-1 != tranType) {
				sql.append(" AND TRANS.TRANS_TYPE = ?");
				if (tranType == RequestType.RECHARGE.getValue()) {
					sql.append(" AND (TDETAIL.TRANS_STATE = "
							+ TransactionState.RECHARGE_REQUEST_LISTENER
									.getValue() + " OR TDETAIL.TRANS_STATE = "
							+ TransactionState.WEB.getValue() + ")");
				} else if 

				((tranType == RequestType.POSTPAID_MOBILE.getValue())||(tranType == RequestType.POSTPAID_ABTS.getValue())||(tranType == RequestType.POSTPAID_DTH.getValue())) {


					sql.append(" AND (TDETAIL.TRANS_STATE = "
							+ TransactionState.POSTPAID_REQUEST_LISTENER
									.getValue() + " OR TDETAIL.TRANS_STATE = "
							+ TransactionState.WEB.getValue() + ")");
				}
				/*
				 * else if(tranType == RequestType.VAS.getValue()){ sql.append("
				 * AND (TDETAIL.TRANS_STATE = " +
				 * TransactionState.VAS_REQUEST_LISTENER.getValue() + " OR
				 * TDETAIL.TRANS_STATE = " + TransactionState.WEB.getValue() +
				 * ")"); }
				 */
			} else {
				sql
						.append(" AND TDETAIL.TRANS_STATE = (SELECT MIN(TDETAIL1.TRANS_STATE) FROM VR_TRANS_DETAIL TDETAIL1  WHERE TDETAIL1.TRANS_ID = TDETAIL.TRANS_ID)");
			}
			if (0 != tranId) {
				/** search according to Transaction Id */
				sql.append(" AND TRANS.TRANS_ID = ? ");
			}
			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.TRANS_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.TRANS_DATE) <= ? ");
			}
			sql.append(" ORDER BY TRANS.TRANS_ID DESC");
			ps = connection.prepareStatement(sql.toString());

			/** set the values in the statement */
			if (-1 != tranType) {
				ps.setInt(paramCount++, tranType);
			}
			if (0 != tranId) {
				ps.setInt(paramCount++, tranId);
			}
			if (startDt != null && !startDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						startDt).getTime()));
			}
			if (endDt != null && !endDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						endDt).getTime()));
			}
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				customerTransaction = new CustomerTransaction();
				customerTransaction.setTransactionId(rs.getLong("TRANS_ID"));
				customerTransaction.setSourceAccountCode(rs
						.getString("LOGIN_NAME"));
				customerTransaction.setCustomerMobileNo(rs
						.getString("RECEIVER_ID"));
				customerTransaction.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				customerTransaction.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				// Send the Transaction Status ordinal value instead of constant
				// charachers.
				customerTransaction.setStatus(String.valueOf(rs
						.getInt("TRANS_STATUS")));

				/*
				 * if (TransactionStatus.SUCCESS.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_SUCCESS); } else if
				 * (TransactionStatus.PENDING.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_IN_PROGRESS); } else if
				 * (TransactionStatus.FAILURE.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_FAILURE); }
				 */
				customerTransaction.setReason(rs.getString("REASON_ID"));
				
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"), ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
				logger.info("validity : " + validity);
				customerTransaction.setValidity(validity);
				
				customerTransaction.setTransationType((TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE"))).name());
				


				String message = rs.getString("DETAIL_MSG");
				double serviceTax = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_SERVICE_TAX_START));
				logger.info("serviceTax : " + serviceTax);
				customerTransaction.setServiceTax(serviceTax);

				double espCommission = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_ESP_COMMISSION_START));
				logger.info("espCommission : " + espCommission);
				customerTransaction.setEspCommission(espCommission);

				double processingFee = Utility.parseMessage(message,ResourceReader.getCoreResourceBundleValue(Constants.REPORT_PROCESSING_FEE_START));
				logger.info("processingFee : " + processingFee);
				customerTransaction.setProcessingFee(processingFee);

				double talkTime = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_TALK_TIME_START));
				logger.info("talkTime : " + talkTime);
				customerTransaction.setTalkTime(talkTime);

				/** adding each DTO object into array List */
				results.add(customerTransaction);
			}

		} catch (SQLException e) {
			logger
					.fatal(
							"Exception occured while reteriving a list of all customer transactions for internal user logged in."
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("DistributorTransactionDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/** Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed getCustomerTransactionListAll().... all Customer Transaction List SuccessFully Retreived");
		return results;
	}

	/**
	 * This method will be used provide a list of all customer transactions for
	 * external user logged in. This will provide all the records and is
	 * independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getCustomerChildAccountListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getCustomerChildAccountListAll()...");

		/** get the data from the input DTO */
		String searchFieldValue = mtDTO.getSearchFieldValue();
		int tranId = 0;
		if (searchFieldValue != null && !searchFieldValue.equals("")) {
			tranId = Integer.parseInt(searchFieldValue);
		}
		int tranType;
		if (null != mtDTO.getRequestType()) {
			tranType = mtDTO.getRequestType().getValue();
		} else {
			tranType = -1;
		}
		long parentId = mtDTO.getParentId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		/** use to store DTO objects */
		ArrayList<CustomerTransaction> results = new ArrayList<CustomerTransaction>();
		CustomerTransaction customerTransaction = null;
		StringBuilder sql = new StringBuilder();
		try {
			int paramCount = 1;
			sql.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			sql.append(queryMap.get(SQL_SELECT_CUSTOMER_TRANSACTION_KEY));

			if (-1 != tranType) {
				sql.append(" AND TRANS.TRANS_TYPE = ?");
				if (tranType == RequestType.RECHARGE.getValue()) {
					sql.append(" AND (TDETAIL.TRANS_STATE = "
							+ TransactionState.RECHARGE_REQUEST_LISTENER
									.getValue() + " OR TDETAIL.TRANS_STATE = "
							+ TransactionState.WEB.getValue() + ")");
				} else if 


				((tranType == RequestType.POSTPAID_MOBILE.getValue())||(tranType == RequestType.POSTPAID_ABTS.getValue())||(tranType == RequestType.POSTPAID_DTH.getValue())) {

					sql.append(" AND (TDETAIL.TRANS_STATE = "
							+ TransactionState.POSTPAID_REQUEST_LISTENER
									.getValue() + " OR TDETAIL.TRANS_STATE = "
							+ TransactionState.WEB.getValue() + ")");
				}
				/*
				 * else if(tranType == RequestType.VAS.getValue()){ sql.append("
				 * AND (TDETAIL.TRANS_STATE = " +
				 * TransactionState.VAS_REQUEST_LISTENER.getValue() + " OR
				 * TDETAIL.TRANS_STATE = " + TransactionState.WEB.getValue() +
				 * ")"); }
				 */
			} else {
				sql
						.append(" AND TDETAIL.TRANS_STATE = (SELECT MIN(TDETAIL1.TRANS_STATE) FROM VR_TRANS_DETAIL TDETAIL1  WHERE TDETAIL1.TRANS_ID = TDETAIL.TRANS_ID)");
			}

			if (0 != tranId) {
				/** search according to Transaction Id */
				sql.append(" AND TRANS.TRANS_ID = ? ");
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.TRANS_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.TRANS_DATE) <= ? ");
			}
			/*sql
					.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ORDER BY TRANS.TRANS_ID DESC ");*/
			sql.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS ) ORDER BY TRANS.TRANS_ID DESC ");
			
			ps = connection.prepareStatement(sql.toString());

			/** set the values in the statement */
			ps.setLong(paramCount++, parentId);
			if (-1 != tranType) {
				ps.setInt(paramCount++, tranType);
			}
			if (0 != tranId) {
				ps.setInt(paramCount++, tranId);
			}
			if (startDt != null && !startDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						startDt).getTime()));
			}
			if (endDt != null && !endDt.equals("")) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						endDt).getTime()));
			}
			
			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				customerTransaction = new CustomerTransaction();
				customerTransaction.setTransactionId(rs.getLong("TRANS_ID"));
				customerTransaction.setSourceAccountCode(rs
						.getString("LOGIN_NAME"));
				customerTransaction.setCustomerMobileNo(rs
						.getString("RECEIVER_ID"));
				customerTransaction.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				customerTransaction.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));

				// Send the Transaction Status ordinal value instead of constant
				// charachers.
				customerTransaction.setStatus(String.valueOf(rs
						.getInt("TRANS_STATUS")));
				/*
				 * if (TransactionStatus.SUCCESS.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_SUCCESS); } else if
				 * (TransactionStatus.PENDING.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_IN_PROGRESS); } else if
				 * (TransactionStatus.FAILURE.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_FAILURE); }
				 */
				customerTransaction.setReason(rs.getString("REASON_ID"));
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"), ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
				logger.info("validity : " + validity);
				customerTransaction.setValidity(validity);
				
				customerTransaction.setTransationType((TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE"))).name());
				



				String message = rs.getString("DETAIL_MSG");
				double serviceTax = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_SERVICE_TAX_START));
				logger.info("serviceTax : " + serviceTax);
				customerTransaction.setServiceTax(serviceTax);

				double espCommission = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_ESP_COMMISSION_START));
				logger.info("espCommission : " + espCommission);
				customerTransaction.setEspCommission(espCommission);

				double processingFee = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_PROCESSING_FEE_START));
				logger.info("processingFee : " + processingFee);
				customerTransaction.setProcessingFee(processingFee);

				double talkTime = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_TALK_TIME_START));
				logger.info("talkTime : " + talkTime);
				customerTransaction.setTalkTime(talkTime);

				/** adding each DTO object into array List */
				results.add(customerTransaction);
			}
		} catch (SQLException e) {
			logger
					.fatal(
							"Exception occured while reteriving all customer transaction list for external user logged in. "
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("DistributorTransactionDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		} finally {
			/** Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed getCustomerChildAccountListAll().... All Customer child Transaction List SuccessFully Retreived");
		return results;
	}
	
	
	/**
	 * This method will be used provide a list of customer transactions for
	 * external user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getCustomerChildAccountList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {
		logger.info("Started getCustomerChildAccountList()..");

		/** get the data from the input DTO */
		String searchFieldValue = mtDTO.getSearchFieldValue();
		long tranId = 0;
		if (searchFieldValue != null && !searchFieldValue.equals("")) {
			tranId = Long.parseLong(searchFieldValue);
		}
		int tranType;
		if (null != mtDTO.getRequestType()) {
			tranType = mtDTO.getRequestType().getValue();
		} else {
			tranType = -1;
		}

		long parentId = mtDTO.getParentId();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		/** use to store DTO objects */
		ArrayList<CustomerTransaction> results = new ArrayList<CustomerTransaction>();
		CustomerTransaction customerTransaction = null;
		StringBuilder query = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		StringBuilder connectBySql = new StringBuilder();
		try {
			int paramCount = 1;
			connectBySql.append(queryMap.get(SQL_CONNECT_BY_QUERY_KEY));
			sql.append(queryMap.get(SQL_SELECT_CUSTOMER_TRANSACTION_KEY));
			if (-1 != tranType) {
				sql.append(" AND TRANS.TRANS_TYPE = ?");
				if (tranType == RequestType.RECHARGE.getValue()) {
					sql.append(" AND (TDETAIL.TRANS_STATE = "
							+ TransactionState.RECHARGE_REQUEST_LISTENER
									.getValue() + " OR TDETAIL.TRANS_STATE = "
							+ TransactionState.WEB.getValue() + ")");
				} else if 
				((tranType == RequestType.POSTPAID_MOBILE.getValue())||(tranType == RequestType.POSTPAID_ABTS.getValue())||(tranType == RequestType.POSTPAID_DTH.getValue())) {
					sql.append(" AND (TDETAIL.TRANS_STATE = "
							+ TransactionState.POSTPAID_REQUEST_LISTENER
									.getValue() + " OR TDETAIL.TRANS_STATE = "
							+ TransactionState.WEB.getValue() + ")");
				}
				/*
				 * else if(tranType == RequestType.VAS.getValue()){ sql.append("
				 * AND (TDETAIL.TRANS_STATE = " +
				 * TransactionState.VAS_REQUEST_LISTENER.getValue() + " OR
				 * TDETAIL.TRANS_STATE = " + TransactionState.WEB.getValue() +
				 * ")"); }
				 */
			} else {
				sql
						.append(" AND TDETAIL.TRANS_STATE = (SELECT MIN(TDETAIL1.TRANS_STATE) FROM VR_TRANS_DETAIL TDETAIL1  WHERE TDETAIL1.TRANS_ID = TDETAIL.TRANS_ID)");
			}
			if (0 != tranId) {
				/** search according to Transaction Id */
				sql.append(" AND TRANS.TRANS_ID = ? ");
			}
			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(TRANS.TRANS_DATE) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(TRANS.TRANS_DATE) <= ? ");
			}
			/*sql
					.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ORDER BY TRANS.TRANS_ID DESC ");*/
			sql.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM DETAILS ) ORDER BY TRANS.TRANS_ID DESC ");
			
			/** apply the pagination query */
			query.append("SELECT * FROM(SELECT a.*,ROW_NUMBER() OVER() rnum FROM (SELECT c.*, COUNT(*) over() RECORD_COUNT FROM ( ");
			query.append(sql);
			query.append(")c)a)b Where rnum<=? AND rnum>=?");
			
			connectBySql.append(query.toString());
			ps = connection.prepareStatement(connectBySql.toString());
			logger.info("connectBySql  "+connectBySql.toString());
			/** set the values in the statement */
			ps.setLong(paramCount++, parentId);
			if (-1 != tranType) {
				ps.setInt(paramCount++, tranType);
			}
			if (0 != tranId) {
				ps.setLong(paramCount++, tranId);
			}
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

			/** execute the query */
			rs = ps.executeQuery();

			/** iterate the resultset */
			while (rs.next()) {
				customerTransaction = new CustomerTransaction();
				customerTransaction.setTransactionId(rs.getLong("TRANS_ID"));
				customerTransaction.setSourceAccountCode(rs
						.getString("LOGIN_NAME"));
				customerTransaction.setCustomerMobileNo(rs
						.getString("RECEIVER_ID"));
				customerTransaction.setTransactionAmount(rs
						.getDouble("TRANS_AMOUNT"));
				customerTransaction.setTransactionDate(rs
						.getTimestamp("TRANS_DATE"));
				customerTransaction.setTotalRecords(rs.getInt("RECORD_COUNT"));

				// Send the Transaction Status ordinal value instead of constant
				// charachers.
				customerTransaction.setStatus(String.valueOf(rs
						.getInt("TRANS_STATUS")));

				/*
				 * if (TransactionStatus.SUCCESS.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_SUCCESS); } else if
				 * (TransactionStatus.PENDING.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_IN_PROGRESS); } else if
				 * (TransactionStatus.FAILURE.getValue() == rs
				 * .getInt("TRANS_STATUS")) { customerTransaction
				 * .setStatus(Constants.TRANSACTION_FAILURE); }
				 */
				customerTransaction.setReason(rs.getString("REASON_ID"));
				customerTransaction.setRowNum(rs.getString("RNUM"));
				String validity = Utility.parseMessageString(rs.getString("THIRD_PARTY"), ResourceReader.getWebResourceBundleValue(Constants.REPORT_VALIDITY_XML));
				logger.info("validity : " + validity);
				customerTransaction.setValidity(validity);
				
				customerTransaction.setTransationType((TransactionType
						.getTransactionType(rs.getInt("TRANS_TYPE"))).name());
				



				String message = rs.getString("DETAIL_MSG");
				double serviceTax = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_SERVICE_TAX_START));
				logger.info("serviceTax : " + serviceTax);
				customerTransaction.setServiceTax(serviceTax);

				double espCommission = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_ESP_COMMISSION_START));
				logger.info("espCommission : " + espCommission);
				customerTransaction.setEspCommission(espCommission);

				double processingFee = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_PROCESSING_FEE_START));
				logger.info("processingFee : " + processingFee);
				customerTransaction.setProcessingFee(processingFee);

				double talkTime = Utility.parseMessage(message, ResourceReader.getCoreResourceBundleValue(Constants.REPORT_TALK_TIME_START));
				logger.info("talkTime : " + talkTime);
				customerTransaction.setTalkTime(talkTime);

				/** adding each DTO object into array List */
				results.add(customerTransaction);
			}

		} catch (SQLException e) {
			logger
					.fatal(
							"Exception occured while reteriving customer transaction list for external user logged in. "
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("DistributorTransactionDaoRdbmsDB2:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/** Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		logger
				.info("Executed getCustomerChildAccountList().... Customer child Transaction List SuccessFully Retreived");
		return results;
	}

	// transaction sum end
	// *********** Implementation for Transaction Report Ends *********

}
