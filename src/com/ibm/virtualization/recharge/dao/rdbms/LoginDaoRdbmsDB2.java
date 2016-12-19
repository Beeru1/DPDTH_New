package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;

public class LoginDaoRdbmsDB2 extends LoginDaoRdbms {

	protected static String SQL_LOGIN_SELECT_SEQID = "SELECT NEXT VALUE FOR SEQ_VR_LOGIN_MASTER NEXTVAL FROM SYSIBM.SYSDUMMY1 ";
	
	protected final static String SQL_LOGIN_RESET_IPASSWORD_KEY = "SQL_LOGIN_RESET_IPASSWORD";
	protected static String SQL_LOGIN_RESET_IPASSWORD = " UPDATE VR_LOGIN_MASTER SET PASSWORD = ?, IPWD_CHANGED_DT = CURRENT TIMESTAMP- ? day WHERE PASSWORD = ? AND LOGIN_ID = ?";
	protected final static String SQL_LOGIN_INSERT_KEY = "SQL_LOGIN_INSERT";
	protected static String SQL_LOGIN_INSERT = "INSERT INTO VR_LOGIN_MASTER (LOGIN_ID, LOGIN_NAME, PASSWORD, TYPE, STATUS, LOGIN_ATTEMPTED, GROUP_ID, IPWD_CHANGED_DT) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP- ? day)";
	
//	 for insertion of use based on account code
	protected final static String SQL_ACC_INSERT_KEY = "SQL_ACC_INSERT";
	protected static String SQL_ACC_INSERT = "INSERT INTO VR_LOGIN_MASTER (LOGIN_ID, LOGIN_NAME,GROUP_ID, PASSWORD, TYPE, STATUS,IPWD_CHANGED_DT,LOGIN_ATTEMPTED,M_PASSWORD) VALUES (?, ?, ?, ?, ?,?,CURRENT TIMESTAMP- ? day,?,?)";
	
	
	public LoginDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_LOGIN_SELECT_SEQID_KEY, SQL_LOGIN_SELECT_SEQID);
		queryMap.put(SQL_LOGIN_RESET_IPASSWORD_KEY, SQL_LOGIN_RESET_IPASSWORD);
		queryMap.put(SQL_LOGIN_INSERT_KEY, SQL_LOGIN_INSERT);
		queryMap.put(SQL_ACC_INSERT_KEY, SQL_ACC_INSERT);
		
	}

}
