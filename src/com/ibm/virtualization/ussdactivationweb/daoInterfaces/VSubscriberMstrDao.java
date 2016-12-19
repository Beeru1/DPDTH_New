/**************************************************************************
 * File     : VSubscriberMstrDao.java
 * Author   : Ashad
 * Created  : Sep 10, 2008
 * Modified : Sep 10, 2008
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;




public interface VSubscriberMstrDao {

 
/******************************************db2 queries************************************************************/	
	
	public static final String SQL_INSERT_WITH_ID_SINGLE =new StringBuffer().append("INSERT INTO ")
	.append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR (SM_ID,CIRCLE_CODE, ")
	.append(" IMSI, MSISDN, SIM, STATUS, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY,COMPLETE_SIM,SERVICECLASS_ID) " )
	.append(" VALUES (next value for SEQ_V_SUBSCRIBER_MSTR,?, ?, ?, ?, ?, CURRENT TIMESTAMP, ?, CURRENT TIMESTAMP, ?,?,?)").append(" WITH UR").toString();
	
	public static final String SQL_INSERT_WITH_ID =new StringBuffer().append("INSERT INTO ").append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR (SM_ID,CIRCLE_CODE, ")
	.append("  MSISDN, SIM, STATUS, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY,COMPLETE_SIM,SERVICECLASS_ID) " )
	.append(" VALUES (next value for SEQ_V_SUBSCRIBER_MSTR,?, ?, ?, ?, CURRENT TIMESTAMP, ?, CURRENT TIMESTAMP, ?,?,?)").append(" WITH UR").toString();

	public static final String SQL_SELECT = new StringBuffer().append(" SELECT SM.SM_ID,SM.CIRCLE_CODE,SM.IMSI,SM.COMPLETE_SIM,") 
			.append("SM.MSISDN, SM.SIM, SM.STATUS, SM.CREATED_DT,")
			.append("SM.CREATED_BY,SM.UPDATED_DT,SM.UPDATED_BY,SM.SERVICECLASS_ID FROM ")
			.append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR SM").toString();

	
	public static final String RETRIVE_SUB_BY_MSISDN =new StringBuffer()
	.append("SELECT DISTINCT(VSUB.SERVICECLASS_ID),VSUB.SM_ID,VSUB.CIRCLE_CODE, VSUB.IMSI,VSUB.COMPLETE_SIM,") 
	.append(" VSUB.MSISDN, VSUB.SIM, VSUB.STATUS, VSUB.CREATED_DT,")
	.append("VSUB.CREATED_BY, VSUB.UPDATED_DT, VSUB.UPDATED_BY FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR VSUB ")
	.append(" WHERE MSISDN=? ").toString();
	
	public static final String RETRIVE_SEVICECLASS_BY_IDS =new StringBuffer()
	.append("SELECT SERVICECLASS_ID,SERVICECLASS_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR  ")
	.append(" WHERE SERVICECLASS_ID IN (<SC_ID>) ")
	.append(" WITH UR").toString();
	
	
	/*public static final String CHECK_SUB_BY_MSISDN_SIM 
	=new StringBuffer().append(" SELECT SM.MSISDN ,SM.SERVICECLASS_ID,SM.STATUS,")
	.append(" SM.SM_ID,SM.IMSI,SM.SIM,SM.STATUS,SM.COMPLETE_SIM,SM.CIRCLE_CODE FROM ") 
	.append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR SM ")
	.append(" WHERE (SM.MSISDN = ? AND SM.SIM=?) ")
	.append(" OR (SM.MSISDN =? OR SM.SIM=?) AND SM.STATUS=? WITH UR ").toString();*/
	
	public static final String CHECK_SUB_BY_MSISDN_SIM 
	=new StringBuffer().append(" SELECT SM.MSISDN ,SM.SERVICECLASS_ID,SM.STATUS,")
	.append(" SM.SM_ID,SM.IMSI,SM.SIM,SM.STATUS,SM.COMPLETE_SIM,SM.CIRCLE_CODE FROM ") 
	.append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR SM ")
	.append(" WHERE (SM.MSISDN = ? AND SM.SIM=? AND STATUS=? AND CIRCLE_CODE=?) ")
	.append(" WITH UR ").toString();
	
/*public static final String CHECK_SUB_BY_MSISDN_OR_SIM 
	=new StringBuffer().append(" SELECT SM.MSISDN ,SM.SERVICECLASS_ID,SM.STATUS,")
	.append(" SM.SM_ID,SM.IMSI,SM.SIM,SM.STATUS,SM.COMPLETE_SIM,SM.CIRCLE_CODE FROM ") 
	.append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR SM ")
	.append(" WHERE (SM.MSISDN <> ? AND SM.SIM=? AND CIRCLE_CODE=? AND STATUS=?)")
	.append(" OR (SM.MSISDN <> ? AND SM.SIM<>? AND CIRCLE_CODE=? AND STATUS=?)")
	.append(" OR (SM.MSISDN = ? AND SM.SIM=? AND CIRCLE_CODE<>? AND STATUS=?)")
	.append(" OR (SM.MSISDN = ? AND SM.SIM<>? AND CIRCLE_CODE<>? AND STATUS=?)")
	.append(" OR (SM.MSISDN <> ? AND SM.SIM=? AND CIRCLE_CODE<>? AND STATUS=?)")
	.append(" WITH UR ").toString();*/
	
	public static final String PROC_CREATE_SUBSCRIBER = new StringBuffer(
			500).append("{call ").append(SQLConstants.FTA_SCHEMA).append(".")
			.append(
					Utility.getValueFromBundle(
							Constants.PROC_CREATE_SUBSCEIBER,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE)).append(
					"(?,?,?,?,?,?,?,?,?,?,?,?)}").toString();
	
	public static final String SQL_UPDATE = 
		new StringBuffer().append("UPDATE ").append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR")
		.append("SET IMSI = ?,UPDATED_DT = CURRENT TIMESTAMP,")
		.append(" STATUS = ?,UPDATED_BY = ?,SIM = ?,CIRCLE_CODE=?,")
		.append(" COMPLETE_SIM=?,SERVICECLASS_ID=? WHERE MSISDN = ? AND IMSI=? AND COMPLETE_SIM=?")
		.append(" WITH UR").toString();
	
	public static final String SQL_UPDATE_ON_EDIT = 
		 new StringBuffer().append("UPDATE ").append(SQLConstants.FTA_SCHEMA).append(".V_SUBSCRIBER_MSTR").append(" SET IMSI = ?,UPDATED_DT = CURRENT TIMESTAMP,")
		 .append(" STATUS = ?,UPDATED_BY = ?,SIM = ?,CIRCLE_CODE=?,") 
		 .append(" COMPLETE_SIM=?,SERVICECLASS_ID=? ") 
		 .append(" where sm_id=?").append(" WITH UR").toString();
	
	public static final String SQL_DELETE = new  StringBuffer().append("DELETE FROM ").append(SQLConstants.FTA_SCHEMA).
	append(".V_SUBSCRIBER_MSTR").append(" WHERE MSISDN = ? AND SIM = ?")
	.append(" WITH UR").toString();
	
//	public static final String TRACK_SUBSCRIBER =new  StringBuffer()
//	.append("SELECT CONCAT(CHAR(cm.CIRCLE_NAME),CONCAT('--',CHAR(sm.CIRCLE_CODE))) AS CIRCLE,sm.MSISDN,sm.IMSI,sm.SIM,scm.SERVICECLASS_NAME, ")
//	.append("tm.TRAN_TYPE_ID,ttm.TRAN_TYPE_NAME,tm.CREATED_DT,tm.REQUESTER_MSISDN,tm.STATUS, ")
//	.append("CASE ")
//	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
//	.append("THEN 'NotDone' ")
//	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ") 
//	.append("THEN 'Done' ")
//	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
//	.append("THEN 'NotDone' ")
//	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
//	.append("THEN 'Done' ")
//	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
//	.append("THEN 'NotDone' ")
//	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
//	.append("THEN 'Done' ")
//	.append("ELSE 'Y' ")
//	.append("END SUBSCRIBERSTATUS FROM ")
//	.append(SQLConstants.PRODCAT_SCHEMA)
//	.append(".PC_CIRCLE_MSTR_NICK cm ,")
//	.append(SQLConstants.FTA_SCHEMA)
//	.append(".V_SUBSCRIBER_MSTR sm,")
//	.append(SQLConstants.PRODCAT_SCHEMA)
//	.append(".PC_SERVICECLASS_MSTR_NICK scm ,")
//	.append(SQLConstants.FTA_SCHEMA)
//	.append(".V_TRANSACTION_MSTR tm,")
//	.append(SQLConstants.FTA_SCHEMA)
//	.append(".V_TRAN_TYPE_MSTR ttm ")
//	.append(" WHERE sm.CIRCLE_CODE=cm.CIRCLE_CODE ")
//	.append("AND sm.SERVICECLASS_ID=scm.SERVICECLASS_ID AND tm.SUBS_MSISDN = sm.MSISDN ")
//	.append("AND tm.TRAN_TYPE_ID=ttm.TRAN_TYPE_ID ")
//	.toString();
	
	
 public static final String GET_CIRCLE_SERVICECLASS = new StringBuffer()
	.append(" SELECT CONCAT(CHAR(cm.CIRCLE_CODE),CONCAT('--',CHAR(cm.CIRCLE_NAME))) AS CIRCLE,scm.SERVICECLASS_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm ,")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR scm WHERE scm.SERVICECLASS_ID=? AND cm.CIRCLE_CODE=scm.CIRCLE_CODE AND scm.CIRCLE_CODE=? ")
	.toString();
	
	public static final String GET_MSISDN_DETAILS =new  StringBuffer()
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT sm.CIRCLE_CODE,sm.IMSI,sm.MSISDN,sm.SIM,sm.SERVICECLASS_ID,tm.SUBS_SIM,tm.TRAN_TYPE_ID,ttm.TRAN_TYPE_NAME,tm.CREATED_DT,")
	.append("tm.REQUESTER_MSISDN,tm.REQUESTER_TYPE,tm.STATUS,tm.OTHER_DETAILS,tm.SUBSCRIBER_NAME,")
	.append("CASE ")
	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Initiated' ")
	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ") 
	.append("THEN 'Done' ")
	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Initiated' ")
	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Done' ")
	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Initiated' ")
	.append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Done' ")
	 .append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Failed' ")
    .append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?) ")
	.append("THEN 'Failed' ")
    .append("WHEN(tm.TRAN_TYPE_ID = ? AND tm.STATUS=?)")
	.append("THEN 'Failed' ")
	.append("ELSE 'Not Initiated' ")
	.append("END SUBSCRIBERSTATUS FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR sm LEFT OUTER JOIN ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_MSTR tm on sm.MSISDN = tm.SUBS_MSISDN AND sm.CIRCLE_CODE=tm.CIRCLE_CODE LEFT OUTER JOIN ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRAN_TYPE_MSTR ttm on ttm.TRAN_TYPE_ID = tm.TRAN_TYPE_ID ")
	.append(" WHERE sm.STATUS=? ")
	.toString();
	
	
	public static final String SQL_COUNT_SUBSCRIBER_DETAILS = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR sm LEFT OUTER JOIN ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_MSTR tm on sm.MSISDN = tm.SUBS_MSISDN LEFT OUTER JOIN ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRAN_TYPE_MSTR ttm on ttm.TRAN_TYPE_ID = tm.TRAN_TYPE_ID ")
	.append(" WHERE sm.STATUS=? ")
	.toString();
	
	
	public static final String SQL_STATUS_OF_MSISDN = new StringBuffer(500)
	.append("SELECT sm.MSISDN,sm.STATUS FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR sm ")
	.append(" WHERE sm.MSISDN=? ")
	.toString();
	
	
	public static final String GET_SUBS_REPORT_PENDING_ACTIVATION =new StringBuffer()
	.append("SELECT RD.SUBS_MSISDN,RD.SUBS_SIM,")
	.append("RD.REGISTRATION_DATE,RD.REGISTRATION_COMPLETED_DATE,")
	.append("RD.ACTIVATION_DATE,RD.ACTIVATION_COMPLETED_DATE,RD.VARIFICATION_DATE,RD.VARIFICATION_COMPLETED_DATE,")
	.append("(SELECT SM.SERVICECLASS_ID FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR SM ")
	.append("WHERE RD.SUBS_MSISDN = SM.MSISDN AND SM.STATUS='A'),")
	.append("CASE ")
	.append("WHEN (RD.REGISTRATION_STATUS ='I') ")
	.append("THEN 'Initiated' ")
	.append("WHEN (RD.REGISTRATION_STATUS ='S') ")
	.append("THEN 'Done' ")
	.append("WHEN (RD.REGISTRATION_STATUS ='F') ")
	.append("THEN 'Failed' ")
	.append("ELSE 'Not Required' ")
	.append("END AS REG_STATUS, ")
	.append("CASE ")
	.append("WHEN (RD.VERTIFICATION_STATUS ='I') ")
	.append("THEN 'Initiated' ")
	.append("WHEN (RD.VERTIFICATION_STATUS  ='S') ")
	.append("THEN 'Done' ")
	.append("WHEN (RD.VERTIFICATION_STATUS  ='F') ")
	.append("THEN 'Failed' ")
	.append("ELSE 'Not Required' ")
	.append("END AS VER_STATUS, ")
	.append("CASE ")
	.append("WHEN (RD.ACTIVATION_STATUS ='I') ")
	.append("THEN 'Initiated' ")
	.append("WHEN (RD.ACTIVATION_STATUS  ='F') ")
	.append("THEN 'Failed' ")
	.append("ELSE 'Not Initiated' ")
	.append("END AS ACT_STATUS ")
	.append("FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_REPORT_DETAILS RD ")
	.append("WHERE (RD.ACTIVATION_COMPLETED_DATE BETWEEN ? AND ? OR RD.ACTIVATION_COMPLETED_DATE is null)")
	.append("AND RD.ACTIVATION_STATUS NOT IN('S') OR RD.ACTIVATION_STATUS is null AND ")
	.append("RD.CIRCLE_CODE= ? ")
	.append("WITH UR").toString();
	
		
	public static final String GET_PENDING_SUBS_REPORT_PENDING =new StringBuffer()
	.append("SELECT SM.MSISDN,SM.SIM,SM.CREATED_DT,SM.SERVICECLASS_ID,SM.ACTION_DATE,SM.ACTION_TAKEN,UM.LOGIN_ID ")
	.append("FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR SM, ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_USER_MSTR UM ")
	.append("WHERE SM.CIRCLE_CODE=? ")
	.append("AND ")
	.append("(SM.ACTION_TAKEN = ? ")
	.append("OR SM.ACTION_TAKEN = ? ")
	.append("OR SM.ACTION_TAKEN = ?) ")
	.append("AND SM.STATUS=? ")
	.append("AND DATE(SM.CREATED_DT) <=? ")
	.append("AND DATE(SM.ACTION_DATE) <=? AND SM.CREATED_BY = UM.USER_ID ")
	.append("WITH UR").toString();

/* public static final String SQL_DELETE_MAPPING = new  StringBuffer().append(" DELETE FROM ")
 .append(SQLConstants.FTA_SCHEMA).append("V_DIST_SUBSCRIBER_MAPPING WHERE MSISDN=?")
 .append(" WITH UR").toString();
 
 
 
	*/
	
}
