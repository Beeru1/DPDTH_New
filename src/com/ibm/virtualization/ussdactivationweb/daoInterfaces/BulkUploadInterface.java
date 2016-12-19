/**************************************************************************
 * File     : BulkUploadInterface.java
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

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author Ashad
 *
 * 
 */
public interface BulkUploadInterface {
	
	public static final String INSERT_DIST_FOS = new StringBuffer(500) 
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DISTRIBUTOR_FOS_MAPPING(DISTRIBUTOR_ID,FOS_ID) values(?,?)")
	.append(" WITH UR").toString();
	
	
	public static final String INSERT_Mapping = new StringBuffer(500) 
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA set PARENT_ID = ? where USER_CODE  = ? ")
	.append(" WITH UR").toString();
	
	public static final String GETTING_HUB_CODE = new StringBuffer(500) 
	.append("Select HUB_CODE FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where DATA_ID  = ? ")
	.append(" WITH UR").toString();
	
	public static final String INSERT_DIST_DEALER = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DISTRIBUTOR_DEALER_MAPPING(DISTRIBUTOR_ID,DEALER_ID) values(?,?)")
	.append(" WITH UR").toString();
	
	public static final String INSERT_DIST_SUBS = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DIST_SUBSCRIBER_MAPPING(DIST_SUBS_ID,DISTRIBUTOR_ID,STATUS,")
	.append("CREATED_BY,UPDATED_DT,UPDATED_BY,CREATED_DT,IMSI,MSISDN,SIM)")
	.append("values(SEQ_V_DIST_SUBS_ID.nextval,?,'A',?,sysdate,?,sysdate,?,?,?)")
	.append(" WITH UR").toString();
	
	public static final String CHECK_FOS = new StringBuffer(500)
	.append("Select FOS_ID,STATUS from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FOS_MSTR where CIRCLE_CODE = ? and FOS_ID = ?")
	.append(" WITH UR").toString();
	
	public static final String CHECK_Business_User_Child_Circle = new StringBuffer(500)
	.append("Select DATA_ID,STATUS from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where CIRCLE_CODE = ? and USER_CODE = ?")
	.append(" WITH UR").toString();
	
	public static final String CHECK_Business_User_Child_Hub = new StringBuffer(500)
	.append("Select DATA_ID,STATUS from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where HUB_CODE = ? and USER_CODE = ?")
	.append(" WITH UR").toString();
	
	public static final String CHECK_DEALER = new StringBuffer(500)
	.append("Select DEALER_ID,STATUS from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DEALER_MSTR where CIRCLE_CODE = ? and DEALER_ID = ?")
	.append(" WITH UR").toString();
	
	public static final String CHECK_SUBSCRIBER = new StringBuffer(500)
	.append("SELECT CIRCLE_CODE , IMSI , MSISDN , SIM , COMPLETE_SIM , STATUS , CREATED_DT , CREATED_BY , UPDATED_DT , UPDATED_BY , SM_ID , SERVICECLASS_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR WHERE CIRCLE_CODE = ? AND MSISDN = ? AND IMSI=? AND COMPLETE_SIM=?")
	.append(" WITH UR").toString();
	
	public static final String ALREADY_EXIST_SUBSCRIBER = new StringBuffer(500)
	.append("SELECT CIRCLE_CODE , IMSI , MSISDN , SIM , COMPLETE_SIM , STATUS , CREATED_DT , CREATED_BY , UPDATED_DT , UPDATED_BY , SM_ID , SERVICECLASS_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR WHERE CIRCLE_CODE = ? AND MSISDN = ? AND IMSI=? AND COMPLETE_SIM=? AND STATUS=?")
	.append(" WITH UR").toString();
	
	public static final String ALREADY_MAPPED_SUBSCRIBER= new StringBuffer(500)
	.append("SELECT DIST_SUBS_ID , DISTRIBUTOR_ID , STATUS , CREATED_BY , UPDATED_DT , UPDATED_BY , CREATED_DT , IMSI , MSISDN , SIM FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DIST_SUBSCRIBER_MAPPING where MSISDN = ? AND IMSI=? AND SIM=?")
	.append(" WITH UR").toString();
	
		
	public static final String CHECK_AREADY_MAPPED_Business_User_Child= new StringBuffer(500)
	.append("SELECT PARENT_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ?")
	.append(" WITH UR").toString();
	
	
	public static final String INSERT_ERROR_DATA= new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FAILED_TRANSACTION(FILE_ID,FAILED_TRANSACTION,FAILURE_DETAILS,TRANSACTION_DATE) values(?,?,?,current timestamp)")
	.append(" WITH UR").toString();
	
	public static final String CHECK_MAPPING_TYPE= new StringBuffer(500)
	.append("SELECT USER_CODE FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND MASTER_ID =? ")
	.append(" WITH UR").toString();
	
	
	public static final String INSERT_FAILED_DATA= new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION(FILE_ID,FAILED_TRANSACTION,FAILURE_DETAILS,TRANSACTION_DATE) values(?,?,?,current timestamp)")
	.append(" WITH UR").toString();

	public static final String INSERT_FSO_DEALER = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FOS_DEALER_MAPPING(FOS_ID,DEALER_ID) values(?,?)")
	.append(" WITH UR").toString();
	
		
	public static final String IS_SERVICE_EXIST_CIRCLE= new StringBuffer(500)
	.append("SELECT SERVICECLASS_ID FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR WHERE CIRCLE_CODE=? AND STATUS=?")
	.append(" WITH UR").toString();
	
	public static final String CHK_SUBSCRIBER= new StringBuffer(500)
	.append("SELECT V_SUBSCRIBER_MSTR.CIRCLE_CODE, V_SUBSCRIBER_MSTR.IMSI,V_SUBSCRIBER_MSTR.COMPLETE_SIM, V_SUBSCRIBER_MSTR.MSISDN, ")
	.append(" V_SUBSCRIBER_MSTR.SIM, V_SUBSCRIBER_MSTR.STATUS, V_SUBSCRIBER_MSTR.CREATED_DT, ")
    .append(" V_SUBSCRIBER_MSTR.CREATED_BY, V_SUBSCRIBER_MSTR.UPDATED_DT, V_SUBSCRIBER_MSTR.UPDATED_BY FROM ")
    .append(SQLConstants.FTA_SCHEMA)
    .append(".V_SUBSCRIBER_MSTR V_SUBSCRIBER_MSTR")
    .append(" WHERE (V_SUBSCRIBER_MSTR.MSISDN = ? AND V_SUBSCRIBER_MSTR.SIM=?)OR ((V_SUBSCRIBER_MSTR.MSISDN = ? " )
    .append(" OR V_SUBSCRIBER_MSTR.SIM=?) AND V_SUBSCRIBER_MSTR.STATUS=? AND CIRCLE_CODE=?)")
	.append(" WITH UR").toString();
}
