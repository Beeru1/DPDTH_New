/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author a_gupta
 *
 */
public interface BulkBizUserAssoInterface {
	
	public static String FETCH_SCHEDULE_FILES = new StringBuffer(500)
	.append(" SELECT FILE_ID ,FILE_NAME ,CIRCLE_CODE,STATUS,ORIGINAL_FILE_NAME,MAPPING_TYPE ,TOTAL_SUCCESS_COUNT ,")
	.append("TOTAL_FAILURE_COUNT ,FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID ,USER_DATA_ID ,FAILED_MSG FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR WHERE STATUS= ? WITH UR").toString();
	
	public static String FILES_LIST = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT FILE_ID ,FILE_NAME ,CIRCLE_CODE,STATUS,ORIGINAL_FILE_NAME,MAPPING_TYPE ,")
	.append("TOTAL_SUCCESS_COUNT,TOTAL_FAILURE_COUNT ,FILE_TYPE ,UPLOADED_DT , ")
	.append("UPLOADED_BY,RELATED_FILE_ID ,USER_DATA_ID , ")
	.append("CASE ")
	.append("WHEN STATUS = 'FILE UPLOADED' AND RELATED_FILE_ID NOT IN (0)")
	.append("THEN (SELECT FILE_NAME FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
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
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR super WHERE CIRCLE_CODE = ? AND MAPPING_TYPE = ?   AND STATUS NOT IN (?) AND FILE_TYPE IN (1,2) ").toString();
	
	public static String UPDATE_SCHEDULE_FILES = new StringBuffer(500)
	.append(" UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET STATUS = ? WHERE FILE_ID = ? AND STATUS = ? ").toString();
	
	public static final String CHECK_STATUS = new StringBuffer(500)
	.append("Select DATA_ID,STATUS from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND STATUS = ?")
	.append(" WITH UR").toString();
	
	public static final String CHECK_AREADY_MAPPED= new StringBuffer(500)
	.append("SELECT PARENT_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND PARENT_ID = ? ")
	.append(" WITH UR").toString();
	
	public static final String CHECK_LOCATION= new StringBuffer(500)
	.append("SELECT DATA_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND LOC_ID = ? ")
	.append(" WITH UR").toString();
	
	public static final String CHECK_UNDER_LOCATION= new StringBuffer(500)
	.append("SELECT DATA_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND LOC_ID IN (<LOCATION>) ")
	.append(" WITH UR").toString();
	
	public static final String CHECK_UNDER_CIRCLE= new StringBuffer(500)
	.append("SELECT DATA_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND CIRCLE_CODE IN (<LOCATION>) ")
	.append(" WITH UR").toString();
	
	public static final String CHECK_UNDER_HUB= new StringBuffer(500)
	.append("SELECT DATA_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND CIRCLE_CODE = ? ")
	.append(" WITH UR").toString();

	
	public static final String Bussiness_data = new StringBuffer(500) 
	.append("SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE DATA_ID = ? ")
	.append(" WITH UR").toString();
	
	public static final String Number_unmapped_users = new StringBuffer(500) 
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE PARENT_ID = ? AND MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String List_unmapped_users = new StringBuffer(500) 
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE PARENT_ID = ? AND MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String Number_of_parents = new StringBuffer(500) 
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String List_of_parents = new StringBuffer(500) 
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String CHECK_MAPPING_TYPE= new StringBuffer(500)
	.append("SELECT USER_CODE FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND MASTER_ID =? ")
	.append(" WITH UR").toString();
	
	public static final String FILE_FAILED_UPLOAD = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET STATUS = ? , FAILED_MSG = ? WHERE FILE_ID = ? ").toString();
	
	public static String UPDATE_MAPPING = new StringBuffer(500)
	.append(" UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET PARENT_ID = ? WHERE USER_CODE IN (<USER_CODE>) ").toString();
	
	public static final String NEXT_VALUE_Business_User = new StringBuffer(500)
	.append("SELECT NEXTVAL FOR SEQ_V_FILE_MSTR FROM  SYSIBM.SYSDUMMY1 ")
	.append("FETCH FIRST 1 ROWS ONLY ")
	.append(" WITH UR").toString();
	
	public static String FILE_ENTRY_Business_User = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, ")
	.append("CIRCLE_CODE, STATUS, ORIGINAL_FILE_NAME ,MAPPING_TYPE ,TOTAL_SUCCESS_COUNT ,TOTAL_FAILURE_COUNT, ")
	.append("FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID,USER_DATA_ID ,FAILED_MSG )VALUES(<FILE_ID>,'<FILE_NAME>','<CIRCLE_CODE>','<STATUS>','<ORIGINAL_FILE_NAME>',<MAPPING_TYPE>,<TOTAL_SUCCESS_COUNT>,<TOTAL_FAILURE_COUNT>,<FILE_TYPE>, current timestamp ,'<UPLOADED_BY>',<RELATED_FILE_ID>,<USER_DATA_ID>,'<FAILED_MSG>') ")
	.toString();
//	public static String FILE_ENTRY_Business_User = new StringBuffer(500)
//	.append("INSERT INTO ")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, ")
//	.append("CIRCLE_CODE, STATUS, ORIGINAL_FILE_NAME ,MAPPING_TYPE ,TOTAL_SUCCESS_COUNT ,TOTAL_FAILURE_COUNT, ")
//	.append("FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID,USER_DATA_ID ,FAILED_MSG )" )
//	.append("VALUES(277,'Failedcode-11-12-2008-12-15-31.txt','AS','FAILED FILE','code-11-12-2008-12-15-31.txt',7,0,0,2, current timestamp ,'ashad@in.ibm.com',261,409,'File Failed') ")
//	.toString();
	
	public static String UPDATE_FILE_ENTRY = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET TOTAL_SUCCESS_COUNT = ? ,TOTAL_FAILURE_COUNT = ? ,RELATED_FILE_ID = ? , STATUS = ? WHERE  FILE_ID = ?").toString();
	
	public static String COUNT_FILES_LIST = new StringBuffer(500)
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR super WHERE CIRCLE_CODE = ? AND MAPPING_TYPE = ?   AND STATUS NOT IN (?) AND FILE_TYPE IN (1,2) ").toString();


}
