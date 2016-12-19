/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author Nitesh
 *
 */
public interface BulkBizUserCreationInterface {

	public static String FETCH_SCHEDULE_FILES = new StringBuffer(500)
	.append(" SELECT FILE_ID ,FILE_NAME ,CIRCLE_CODE,STATUS,ORIGINAL_FILE_NAME,TOTAL_SUCCESS_COUNT ,")
	.append("TOTAL_FAILURE_COUNT ,FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID ,FAILED_MSG, LOC_ID, ")
	.append(" PARENT_ID, USER_MASTER_ID, ZONE_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR WHERE STATUS = ? AND FILE_TYPE = ? WITH UR").toString();
	
	public static String UPDATE_SCHEDULE_FILES = new StringBuffer(500)
	.append(" UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET STATUS = ? WHERE FILE_ID = ? AND STATUS = ? ").toString();
	
	
	public static final String NEXT_VALUE_FOR_FILE = new StringBuffer(500)
	.append("SELECT NEXTVAL FOR SEQ_V_FILE_MSTR FROM  SYSIBM.SYSDUMMY1 ")
	.append("FETCH FIRST 1 ROWS ONLY ")
	.append(" WITH UR").toString();
	
	public static String FILE_ENTRY_BIZ_USER = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, ")
	.append("CIRCLE_CODE, STATUS, ORIGINAL_FILE_NAME ,TOTAL_SUCCESS_COUNT ,TOTAL_FAILURE_COUNT, ")
	.append("FILE_TYPE ,UPLOADED_DT ,UPLOADED_BY,RELATED_FILE_ID,FAILED_MSG, LOC_ID, PARENT_ID, USER_MASTER_ID, ZONE_ID )VALUES(?,?,?,?,?,?,?,?, current timestamp ,?,?,?, ?, ?, ?, ?) ")
	.append(" WITH UR").toString();
	
	public static String COUNT_FILES_LIST_FOR_BULK_BIZ_USER_CREATION = new StringBuffer(500)
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR super WHERE FILE_TYPE = ? AND CIRCLE_CODE = ? AND STATUS NOT IN (?) ")
	.toString();
	
	public static final String FILE_FAILED_UPLOAD = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET STATUS = ? , FAILED_MSG = ? WHERE FILE_ID = ? ").toString();
	
	public static String UPDATE_FILE_ENTRY_BULK_BIZ_USER_CREATION = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR SET TOTAL_SUCCESS_COUNT = ? ,TOTAL_FAILURE_COUNT = ? ,RELATED_FILE_ID = ? , STATUS = ? WHERE  FILE_ID = ?").toString();

	public static String FILES_LIST_FOR_BIZ_USER_CREATION = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT FILE_ID ,FILE_NAME ,CIRCLE_CODE,super.STATUS,ORIGINAL_FILE_NAME,")
	.append("TOTAL_SUCCESS_COUNT,TOTAL_FAILURE_COUNT ,FILE_TYPE ,UPLOADED_DT , USER_MASTER_ID, role_master.USER_TYPE, ")
	.append("UPLOADED_BY,RELATED_FILE_ID , ")
	.append("CASE ")
	.append("WHEN super.STATUS = 'FILE UPLOADED' AND RELATED_FILE_ID NOT IN (0) ")
	.append("THEN (SELECT FILE_NAME FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR subfile WHERE subfile.RELATED_FILE_ID=super.FILE_ID) ")
	.append("WHEN super.STATUS = 'FILE UPLOADED' AND RELATED_FILE_ID IN (0) ")
	.append("THEN 'All Records Updated' ")
	.append("WHEN super.STATUS = 'SAVED' ")
	.append("THEN 'File Not Initiated' ")
	.append("WHEN super.STATUS = 'FAILED' ")
	.append("THEN 'File failed to Upload' ")
	.append("WHEN super.STATUS = 'UPLOADING' ")
	.append("THEN 'File is in the Process of Uploading' ")
	.append("END AS FAILED_FILE ")
	.append("FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR super " )
	.append(" INNER JOIN ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER role_master ON role_master.MASTER_ID = super.USER_MASTER_ID ")
	.append(" WHERE FILE_TYPE = ? AND CIRCLE_CODE = ? AND super.STATUS NOT IN (?) ").toString();

	public static final String Code_FOR_BIZ_USER = new StringBuffer(500)
	.append("VALUES NEXTVAL FOR ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".SEQ_V_BUSINESS_USER_CODE ")
	.append(" WITH UR").toString();
	
	public static final String Prefix_FOR_BIZ_USER_CODE = new StringBuffer(500)
	.append("select MASTER_ID ,USER_TYPE ,STATUS ,PARENT_ID ,BASE_LOC ,USER_MAPPING ,CODE_PREFIX from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER where MASTER_ID =?")
	.append(" WITH UR").toString();
	
	public static final String GET_NEXT_BIZ_USER_ID= new StringBuffer(500).append("VALUES NEXTVAL FOR ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".SEQ_V_BUSINESS_USER_DATA_ID").append(" WITH UR").toString();
	
	public static final String INSERT_BIZ_USER_DATA = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA(DATA_ID,USER_CODE,USER_NAME,MASTER_ID,")
	.append(" PARENT_ID,STATUS,CONTACT_NO,ADDRESS,CIRCLE_CODE,HUB_CODE,FOS_CHECK_REQ,FOS_ACTV_CHK,LOC_ID,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,ALL_SERVICE_CLASS,INCLUDE_SERVICE,EXCLUDE_SERVICE) ")
	.append("values(")
	.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,current timestamp,?,current timestamp,?,?,?)")
	.append(" WITH UR").toString();
	
	public static final String INSERT_STMT_DIST = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER(MOBILE_ID, ")
	.append(" MOBILE_NO  ,BUS_USER_ID , STATUS, CREATED_BY, UPDATED_DT, UPDATED_BY, CREATED_DT) values(next value ")
	.append(" for ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".SEQ_V_REGISTERED_NUMBER,?,?,?,?,current timestamp,?,current timestamp) ")
	.append(" with ur ").toString();

	public static final String GET_USER_ID_BY_LOGIN_ID = new StringBuffer(500)
	.append("SELECT USER_ID FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_USER_MSTR WHERE LOGIN_ID = ? ").toString();
	
}
