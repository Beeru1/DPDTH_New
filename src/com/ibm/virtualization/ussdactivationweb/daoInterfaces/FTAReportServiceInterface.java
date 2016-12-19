/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author Banita
 *
 */
public interface FTAReportServiceInterface {
	
	public static final String INSERT_LOC_DETAILS = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".PRODCAT_LOCATION_DATA(LOC_DATA_ID,LOCATION_NAME, STATUS,LOC_MSTR_ID, ")
	.append(" PARENT_ID,LAST_MODIFIED_DT,MSTR_LOCATION_NAME) ")
	.append(" VALUES ( ?, ?, ? ,?, ?, ?, ?) ")
	.toString();

	
	public static final String UPDATE_LOC_DETAILS = new StringBuffer(500)
	  .append("UPDATE ")
	 .append(SQLConstants.FTA_SCHEMA)
	 .append(".PRODCAT_LOCATION_DATA ")
	.append("SET LOC_DATA_ID = ?,LOCATION_NAME= ?,STATUS =?,LOC_MSTR_ID = ?,PARENT_ID=?,LAST_MODIFIED_DT=?,MSTR_LOCATION_NAME=?" )
	  .append("WHERE LOC_DATA_ID=?").toString();
	
	
	
	public static final String INSERT_BIZ_USER_DETAILS = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".DISTPORTAL_BIZ_USER_DATA(DATA_ID,USER_CODE, USER_NAME,MASTER_ID, ")
	.append(" PARENT_ID ,LOC_ID,BASE_LOC,CIRCLE_CODE,STATUS,MOBILE_NO,LAST_MODIFIED_DT ,USER_TYPE) ")
	.append(" VALUES ( ?, ?, ? ,?, ?, ?, ?, ?, ?, ?,?,?) ")
	.toString();
	
	public static final String UPDATE_BIZ_USER_DETAILS = new StringBuffer(500)
	  .append("UPDATE ")
	 .append(SQLConstants.FTA_SCHEMA)
	 .append(".DISTPORTAL_BIZ_USER_DATA ")
	.append("SET DATA_ID = ?,USER_CODE= ?,USER_NAME =?,MASTER_ID = ?,PARENT_ID=?,LOC_ID=? ,BASE_LOC=?, " )
	 .append("CIRCLE_CODE=?,STATUS=?,MOBILE_NO=?,LAST_MODIFIED_DT=?,USER_TYPE=? ")
	  .append("WHERE DATA_ID =?").toString();
	
	public static final String UPDATE_LAST_DATE = new StringBuffer(500)
	  .append("UPDATE ")
	 .append(SQLConstants.FTA_SCHEMA)
	 .append(".LAST_DATA_FEED ")
	.append(" SET LAST_MODIFIED_DT = ? ")
	  .append("WHERE UPPER(NAME_TYPE)=?").toString();
	
	
	public static final String GET_CURRENT_DATE = new StringBuffer(500)
	.append("select current timestamp from sysibm.sysdummy1")
	.append(" WITH UR").toString();
	
	public static final String GET_LAST_MODIFY_DATE = new StringBuffer(500)
	.append("SELECT LAST_MODIFIED_DT FROM ")
		.append(SQLConstants.FTA_SCHEMA)
	.append(".LAST_DATA_FEED WHERE UPPER(NAME_TYPE)=?")
	.append(" WITH UR").toString();
	
	
	public static final String REFRESH_LOCATION = new StringBuffer(500)
	.append("REFRESH TABLE ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".PIVOT_LOCATION_DATA_MQT ")
	.toString();
	
	
	public static final String REFRESH_BIZ_USER = new StringBuffer(500)
	.append("REFRESH TABLE ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".PIVOT_BIZ_USER_DATA_MQT ")
	.toString();
	
	public static final  String GENERATE_REPORT_DETAIL=new StringBuffer(500)
	.append("{call  ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".")
	.append(Utility
			.getValueFromBundle(
					Constants.INSERT_V_REPORT_DETAILS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
	.append("(?,?,?)}").toString();

	
	public static final  String GENERATE_REPORT_SUMMARY=new StringBuffer(500)
	.append("{call  ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".")
	.append(Utility
			.getValueFromBundle(
					Constants.INSERT_V_REPORT_SUMMARY,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
	.append("(?,?,?,?)}").toString();
	

}
