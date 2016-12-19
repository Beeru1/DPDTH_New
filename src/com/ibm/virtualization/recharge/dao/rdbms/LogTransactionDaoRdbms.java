package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.LogTransactionDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.exception.DAOException;

public class LogTransactionDaoRdbms extends BaseDaoRdbms implements LogTransactionDao {

	protected static Logger logger = Logger
			.getLogger(LogTransactionDaoRdbms.class.getName());

	// TODO Sushil,Sept 14 2007 Correct table name and sequence name
	public static final String SQL_INSERT_TRANSACTION_LOG_KEY="SQL_INSERT_TRANSACTION_LOG";
	/** query to insert data into the VR_TRANS_DETAIL Table */
	protected static final String SQL_INSERT_TRANSACTION_LOG = "INSERT INTO VR_TRANS_DETAIL(ID,TRANS_ID, TRANS_STATE, TRANS_KEY_VALUE, DETAIL_MSG, STATUS, "
			+ "CREATED_BY, CREATED_DATE,REQUEST_TIME) VALUES(SEQ_VR_TRANSACTION_DETAIL.NEXTVAL, ?, ?,  ?, ?, ?, ?,SYSDATE, ?)";

	

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
		
	public LogTransactionDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_INSERT_TRANSACTION_LOG_KEY,SQL_INSERT_TRANSACTION_LOG);
	}

	public void insertLog(ActivityLog activityLog) throws DAOException {
		/* Logger for this class. */

		logger.info(" Started... transactionId:"
				+ activityLog.getTransactionId());
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_INSERT_TRANSACTION_LOG_KEY));
			int paramCount = 1;
			ps.setLong(paramCount++, activityLog.getTransactionId());
			if (activityLog.getTransactionState() != null) {
				ps.setInt(paramCount++, activityLog.getTransactionState()
						.getValue());
			} else {
				ps.setNull(paramCount++, Types.INTEGER);
			}
			ps.setString(paramCount++, activityLog.getKeyValue());
			ps.setString(paramCount++, activityLog.getMessage());
			if (activityLog.getStatus() != null) {
				ps.setInt(paramCount++, activityLog.getStatus().getValue());
			} else {
				ps.setNull(paramCount++, Types.INTEGER);
			}
			ps.setLong(paramCount++, activityLog.getCreatedBy());
//			ps.setTimestamp(paramCount++, Utility.getDateTime());
			
			/* set REQUEST_TIME FOR ASYNC */
			java.sql.Timestamp requestTime;
			if (null == activityLog.getRequestTime()) {
				requestTime = Utility.getDateTime();
			} else {
				requestTime = Utility.getDateAsTimestamp(activityLog
						.getRequestTime());
			}
			
			ps.setTimestamp(paramCount++, requestTime);
			ps.executeUpdate();
			DBConnectionManager.commitTransaction(connection);
		} catch (SQLException e) {
			logger.error("SQL Exception occured while insertion ."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_LOG_TRANSACTION);
		} finally {
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger
				.info(" Executed .... successful insertion of record into the table");

	}
}
