/**************************************************************************
 * File     : SMSPullReportServlet.java
 * Author   : Aalok
 * Created  : july 22, 2008
 * Modified : july 22, 20088
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;


/**
 * This class is used for database transaction to fetch credit and debit records 
 * to generate the corresponding reports
 * 
 * @author Rudra S Chowdhury
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface SmsPermissionDaoInterface {
	

	
	public static final String QUERY_SMS_REQUESTER_ID = new StringBuffer(500)
	.append(" SELECT MASTER_ID, USER_TYPE FROM  ")
	.append(SQLConstants.DISTPORTAL_SCHEMA).append(".V_BUSINESS_USER_MASTER ")
	.append(" WHERE STATUS='A' ORDER BY MASTER_ID ASC WITH UR ").toString();
	
	
	public static final String QUERY_SMS_REPORT_ID = new StringBuffer(500) 
	.append(" SELECT REPORT_ID, REPORT_NAME FROM  ")
	.append(SQLConstants.FTA_SCHEMA).append(".V_REPORT_MSTR WHERE REPORT_NAME_TYPE=? ORDER BY REPORT_ID ASC  WITH UR ").toString();
	
	
		public static final String QUERY_SMS_PERMISSION_CONFIGURATION = new StringBuffer(500)
		.append(" SELECT SMS_REQUESTER_ID,SMS_REPORT_ID FROM ")
		.append(SQLConstants.FTA_SCHEMA).append(".V_SMS_CONFIG WHERE CIRCLE_CODE = ? WITH UR ").toString();
		
		public static final String QUERY_DELETE_SMS_PERMISSION_CONFIGURATION_BY_CIRCLE_CODE =new StringBuffer(500)
		.append(" DELETE FROM ").append(SQLConstants.FTA_SCHEMA).append(".V_SMS_CONFIG WHERE CIRCLE_CODE = ? WITH UR ").toString();
		
		public static final String QUERY_INSERT_SMS_PERMISSION_CONFIGURATION = new StringBuffer(500)
		.append(" INSERT INTO ").append(SQLConstants.FTA_SCHEMA).append(".V_SMS_CONFIG( SMS_REQUESTER_ID, SMS_REPORT_ID, CREATED_DATE,  UPDATED_DATE, CIRCLE_CODE) ")
		.append(" VALUES( ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP, ?)  WITH UR").toString();

		public static final String SELECT_ALL_FROM_SMS_REPORT_MSTR = new StringBuffer(500)
		.append(" select SMS_REPORT_ID, USER_MSTR_ID, ")
		.append(" (SMS_REPORT_NAME || '(' || SMS_KEYWORD || ")
		.append(" case ")
		.append(" when (PARAM is null) ")
		.append(" then '' ")
		.append(" else ' ' || PARAM ")
		.append(" end || ')') as REPORT_NAME ")
		.append(" from ")
		.append(SQLConstants.FTA_SCHEMA)
		.append(".V_SMS_REPORT_MSTR ")
		.append(" order by USER_MSTR_ID, DISPLAY_ORDER with UR ").toString();
		
		public static final String DELETE_FROM_SMS_REPORT_MSTR = new StringBuffer(500)
		.append(" delete ")
		.append(" from ")
		.append(SQLConstants.FTA_SCHEMA)
		.append(".V_SMS_CONFIG where CIRCLE_CODE = ?  OR  SMS_REQUESTER_ID = ?").toString();
		
			
		public static final String INSERT_INTO_SMS_REPORT_MSTR = new StringBuffer(500)
		.append(" INSERT INTO ")
		.append(SQLConstants.FTA_SCHEMA)
		.append(".V_SMS_CONFIG(SMS_REQUESTER_ID,SMS_REPORT_ID,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY,CIRCLE_CODE) ")
		.append(" VALUES(?,?, CURRENT TIMESTAMP,?, CURRENT TIMESTAMP, ?,?)").toString();
		
		public static final String SELECT_ALL_FROM_SMS_CONFIG = new StringBuffer(500)
		.append(" select SMS_REQUESTER_ID, SMS_REPORT_ID ")
		.append(" from ")
		.append(SQLConstants.FTA_SCHEMA)
		.append(".V_SMS_CONFIG ")
		.append(" where CIRCLE_CODE = ?").toString();
		
		public static final String SELECT_ALL_FROM_SMS_CONFIG_ED = new StringBuffer(500)
		.append(" select SMS_REQUESTER_ID, SMS_REPORT_ID ")
		.append(" from ")
		.append(SQLConstants.FTA_SCHEMA)
		.append(".V_SMS_CONFIG ")
		.append(" where SMS_REQUESTER_ID = ? ").toString();



}
