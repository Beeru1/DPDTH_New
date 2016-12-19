/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**************************************************************************
 * File     : BulkSubscriberDAO.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
public class BulkSubscriberInterface {

	
	public static String FETCH_SCHEDULE_FILES = new StringBuffer(500)
	.append(" SELECT FILE_ID ,FILE_NAME ,CIRCLE_CODE,STATUS,ORIGINAL_FILE_NAME,TOTAL_SUCCESS_COUNT ,")
	.append("TOTAL_FAILURE_COUNT ,FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID ,FAILED_MSG FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR WHERE STATUS= ? WITH UR").toString();
	
	public static String UPDATE_SCHEDULE_FILES = new StringBuffer(500)
	.append(" UPDATE ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR SET STATUS = ? WHERE FILE_ID = ? AND STATUS = ? ").toString();
	
	
	public static String FILE_ENTRY_Subscriber = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, ")
	.append("CIRCLE_CODE, STATUS, ORIGINAL_FILE_NAME ,TOTAL_SUCCESS_COUNT ,TOTAL_FAILURE_COUNT, ")
	.append("FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID,FAILED_MSG )VALUES(?,?,?,?,?,?,?,?, current timestamp ,?,?,?) ")
	.append(" WITH UR").toString();
	
	public static final String NEXT_VALUE_Subscriber = new StringBuffer(500)
	.append("SELECT NEXTVAL FOR SEQ_V_FILE_MSTR FROM  SYSIBM.SYSDUMMY1 ")
	.append("FETCH FIRST 1 ROWS ONLY ")
	.append(" WITH UR").toString();
	
	
	public static String UPDATE_FILE_ENTRY_Subscriber = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR SET TOTAL_SUCCESS_COUNT = ? ,TOTAL_FAILURE_COUNT = ? ,RELATED_FILE_ID = ? , STATUS = ? WHERE  FILE_ID = ?").toString();
	
	
	public static String FILES_LIST_FOR_SUBSCRIBER = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT FILE_ID ,FILE_NAME ,CIRCLE_CODE,STATUS,ORIGINAL_FILE_NAME,")
	.append("TOTAL_SUCCESS_COUNT,TOTAL_FAILURE_COUNT ,FILE_TYPE ,UPLOADED_DT , ")
	.append("UPLOADED_BY,RELATED_FILE_ID , ")
	.append("CASE ")
	.append("WHEN STATUS = 'FILE UPLOADED' AND RELATED_FILE_ID NOT IN (0) ")
	.append("THEN (SELECT FILE_NAME FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR subfile WHERE subfile.RELATED_FILE_ID=super.FILE_ID) ")
	.append("WHEN STATUS = 'FILE UPLOADED' AND RELATED_FILE_ID IN (0) ")
	.append("THEN 'All Records Updated' ")
	.append("WHEN STATUS = 'SAVED' ")
	.append("THEN 'File Not Initiated' ")
	.append("WHEN STATUS = 'FAILED' ")
	.append("THEN 'File failed to Upload' ")
	.append("WHEN STATUS = 'UPLOADING' ")
	.append("THEN 'File is in the Process of Uploading' ")
	.append("END AS FAILED_FILE ")
	.append("FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR super WHERE CIRCLE_CODE = ? AND STATUS NOT IN (?) ").toString();
	
	public static String COUNT_FILES_LIST_FOR_SUBSCRIBER = new StringBuffer(500)
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR super WHERE CIRCLE_CODE = ? AND STATUS NOT IN (?) ")
	.toString();
	
	public static final String FILE_FAILED_UPLOAD = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET STATUS = ? , FAILED_MSG = ? WHERE FILE_ID = ? ").toString();
	
	public static final String BULK_DELETE = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR SET STATUS = ? , UPDATED_DT = current timestamp , ACTION_TAKEN = ?,ACTION_DATE = current timestamp WHERE MSISDN = ? AND COMPLETE_SIM = ? AND STATUS = ? AND CIRCLE_CODE = ?").toString();
	
	public static final String GET_USER_ID_BY_LOGIN_ID = new StringBuffer(500)
	.append("SELECT USER_ID FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_USER_MSTR WHERE LOGIN_ID = ? ").toString();
	
}
