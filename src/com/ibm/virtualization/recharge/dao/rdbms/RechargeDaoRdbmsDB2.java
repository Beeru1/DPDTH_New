package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;

public class RechargeDaoRdbmsDB2 extends RechargeDaoRdbms {

	/** query to find the next TRANSACTIONID from sequence */
	protected static final String SQL_SELECT_CUSTOMER_TRANSACTIONID = "SELECT NEXT VALUE FOR SEQ_VR_TRANSACTION_ID TRANSACTIONID FROM SYSIBM.SYSDUMMY1";

	protected static final String SQL_INSERT_TRANSACTIONS_MASTER = "INSERT INTO VR_TRANS_MASTER(TRANS_ID, TRANS_TYPE, TRANS_CHANNEL, TRANS_DATE, REQUESTER_ID, "
			+ "RECEIVER_ID,  TRANS_AMOUNT, TRANS_MSG, TRANS_STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, REQUEST_TIME,PHYSICAL_ID, RESET,REASON_ID)"
			+ "VALUES(?, ?, ?,? , ?, ?, ?, ?, ?, ?, current timestamp, ?, current timestamp, ?, ?, ?, ?)";

	public RechargeDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_SELECT_CUSTOMER_TRANSACTIONID_KEY, SQL_SELECT_CUSTOMER_TRANSACTIONID);
		queryMap.put(SQL_INSERT_TRANSACTIONS_MASTER_KEY,SQL_INSERT_TRANSACTIONS_MASTER);
	}
	
	/**
	 * @return the uniqueConstraintVoilationErrMsg
	 */
	public String getUniqueConstraintVoilationErrMsg() {
		return "SQLSTATE: 23505";
	}	
}
