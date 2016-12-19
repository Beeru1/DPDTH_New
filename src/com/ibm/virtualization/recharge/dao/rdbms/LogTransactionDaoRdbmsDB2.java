package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.exception.DAOException;

public class LogTransactionDaoRdbmsDB2 extends LogTransactionDaoRdbms{

	
	public static final String SQL_INSERT_TRANSACTION_LOG = "INSERT INTO VR_TRANS_DETAIL(ID,TRANS_ID, TRANS_STATE, TRANS_KEY_VALUE, DETAIL_MSG, STATUS, "
		+ "CREATED_BY, CREATED_DATE,REQUEST_TIME) VALUES(NEXT VALUE FOR SEQ_VR_TRANSACTION_DETAIL, ?, ?,  ?, ?, ?, ?,CURRENT TIMESTAMP,?)";
	
	public LogTransactionDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_INSERT_TRANSACTION_LOG_KEY,SQL_INSERT_TRANSACTION_LOG);
	}
	
}
