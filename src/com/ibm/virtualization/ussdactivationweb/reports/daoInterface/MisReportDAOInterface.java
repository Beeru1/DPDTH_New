/**************************************************************************
 * File      : MisReportDAOInterface.java
 * Author   : Aalok Sharma
 * Created  : Oct 3, 2008
 * Modified : Oct 3, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 3, 2008 	Aalok Sharma	First Cut.
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.reports.daoInterface;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This interface list all methods used in Creating and Modifying ServiceClass.
 * 
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/

public interface MisReportDAOInterface {

	/** ***** Queries for MIS Report -- view the report ***** */

	public static final String QUERY_GET_FILE_INFO_DATA = new StringBuffer(500)
			.append(" SELECT FILE_ID, FILE_NAME, FILE_PATH FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_FILE_REPORT_INFO ")
			.append(
					"WHERE REPORT_DATE= ? AND CIRCLE_CODE= ?  AND  REPORT_ID = ?  ORDER BY REPORT_DATE DESC WITH UR")
			.toString();

	public static final String QUERY_GET_FILE_INFO_BY_FILE_ID = new StringBuffer(
			500).append(" SELECT FILE_NAME, FILE_PATH FROM ").append(
			SQLConstants.FTA_SCHEMA).append(
			".V_FILE_REPORT_INFO  WHERE FILE_ID= ?  WITH UR").toString();

	public static final String QUERY_GET_FILE_INFO_DATA_BY_MONTH = new StringBuffer(
			500)
			.append(" SELECT FILE_ID, FILE_NAME, FILE_PATH, REPORT_DATE FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_FILE_REPORT_INFO ")
			.append(
					" WHERE REPORT_DATE BETWEEN ? AND ? AND CIRCLE_CODE= ? AND REPORT_ID = ? ORDER BY REPORT_DATE DESC WITH UR")
			.toString();

	// Common for MIS Report generation and view
	public static final String QUERY_GET_REPORT_IDS_BY_REPORT_TYPE = new StringBuffer(
			500)
			.append("SELECT REPORT_ID, REPORT_NAME FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_MSTR WHERE REPORT_TYPE=? AND STATUS='A' AND REPORT_NAME_TYPE='MIS'  WITH UR")
			.toString();

	/** ***** Queries for MIS Report- Generate the report***** */

	public static final String QUERY_INSERT_MIS_FILE_INFO = new StringBuffer(
			500)
			.append("INSERT INTO ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_FILE_REPORT_INFO(FILE_ID, FILE_NAME,FILE_PATH,CIRCLE_CODE,REPORT_ID,REPORT_DATE")
			.append(
					",CREATED_DT) VALUES(NEXT VALUE FOR SEQ_V_FILE_MSTR,?,?,?,?,?,CURRENT_TIMESTAMP)")
			.toString();

	public static final String QUERY_GET_REPORT_TYPE_BY_ID = new StringBuffer(
			500).append("SELECT REPORT_NAME FROM ").append(
			SQLConstants.FTA_SCHEMA).append(
			".V_REPORT_MSTR WHERE REPORT_ID=? WITH UR").toString();

	// Single Day MIS report
	public static final String QUERY_GET_MIS_REPORT_DEL_WISE_ACTV_COUNT_SINGLE_DAY = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(
					" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME, ")
			.append(
					" FOS_MOBILE_NO, DEALER_CODE,DEALER_NAME, DEALER_MOBILE_NO, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE CIRCLE_CODE=? AND SUMMARY_DATE = ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(
					" ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME,FOS_MOBILE_NO,DEALER_CODE, ")
			.append(" DEALER_NAME,DEALER_MOBILE_NO WITH UR").toString();

	public static final String QUERY_GET_MIS_REPORT_DIS_WISE_ACTV_COUNT_SINGLE_DAY = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(
					" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO ")
			.append(" SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE CIRCLE_CODE=? AND SUMMARY_DATE = ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(
					" ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO WITH UR")
			.toString();

	public static final String QUERY_GET_MIS_REPORT_TM_WISE_ACTV_COUNT_SINGLE_DAY = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(
					" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE CIRCLE_CODE=? AND SUMMARY_DATE = ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(" ZSM_NAME,TM_CODE,TM_NAME WITH UR").toString();

	public static final String QUERY_GET_MIS_REPORT_ZONE_WISE_REPORT_SINGLE_DAY = new StringBuffer(
			500)
			.append(
					" SELECT CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE CIRCLE_CODE=? AND SUMMARY_DATE = ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME WITH UR")
			.toString();

	public static final String QUERY_GET_MIS_REPORT_CITY_WISE_REPORT_SINGLE_DAY = new StringBuffer(
			500)
			.append(
					" SELECT CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME,CITY_ID,CITY_NAME, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE CIRCLE_CODE=? AND SUMMARY_DATE = ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME,CITY_ID,CITY_NAME  WITH UR")
			.toString();

	// Detailed MIS report
	public static final String QUERY_GET_MIS_REPORT_DTL_WISE_REPORT_SINGLE_DAY = new StringBuffer(
			500)
			.append(
					" SELECT CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME,CITY_ID,CITY_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(
					" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME,FOS_MOBILE_NO")
			.append(
					" DEALER_CODE,DEALER_NAME,DEALER_MOBILE_NO, SUBS_MSISDN,SUBS_SIM,IS_IPHONE,REGISTRATION_DATE,REGISTRATION_COMPLETED_DATE,VARIFICATION_DATE,VARIFICATION_COMPLETED_DATE,ACTIVATION_DATE,ACTIVATION_COMPLETED_DATE ")
			.append(
					" , CASE WHEN (REGISTRATION_COMPLETED_DATE IS NOT NULL AND ACTIVATION_COMPLETED_DATE IS NOT NULL) THEN   " +
					"CHAR(TIMESTAMPDIFF(4, CAST(ACTIVATION_COMPLETED_DATE  - REGISTRATION_COMPLETED_DATE AS CHAR(22))))   " +
					"ELSE '' END AS TAT_IN_MIN FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_DETAILS WHERE CIRCLE_CODE=? AND ACTIVATION_DT = ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZONE_ID, ")
			.append(" ZONE_NAME,CITY_ID,CITY_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(
					" ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME,FOS_MOBILE_NO,DEALER_CODE, ")
			.append(
					" DEALER_NAME,DEALER_MOBILE_NO, SUBS_MSISDN,SUBS_SIM,IS_IPHONE,REGISTRATION_DATE,REGISTRATION_COMPLETED_DATE,VARIFICATION_DATE,VARIFICATION_COMPLETED_DATE,ACTIVATION_DATE,ACTIVATION_COMPLETED_DATE WITH UR")
			.toString();
	
	// Pending MIS report
	/*public static final String QUERY_GET_PENDING_MIS_REPORT_SINGLE_DAY = new StringBuffer(500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME,CITY_ID,CITY_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME,FOS_MOBILE_NO, ")
			.append(" DEALER_CODE,DEALER_NAME,DEALER_MOBILE_NO,SUBS_MSISDN,SUBS_SIM,REGISTRATION_DATE,REGISTRATION_COMPLETED_DATE,VARIFICATION_DATE,VARIFICATION_COMPLETED_DATE,ACTIVATION_DATE ")
			.append(" FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_REPORT_DETAILS WHERE CIRCLE_CODE=? AND ACTIVATION_DT = ? AND ACTIVATION_STATUS='I'")
			.append(" OEDER BY CIRCLE_CODE,ZONE_ID, ")
			.append(" CITY_ID,ZBM_CODE,ZSM_CODE, ")
			.append(" TM_CODE,DISTRIBUTOR_CODE,FOS_CODE,DEALER_CODE ")
			.toString();*/
	
	
	public static final String QUERY_GET_PENDING_MIS_REPORT_SINGLE_DAY = new StringBuffer(500)
	.append("SELECT CIRCLE_CODE,CIRCLE_NAME,ZONE_ID,ZONE_NAME,CITY_ID,CITY_NAME,ZBM_CODE,ZBM_NAME,")
	.append("ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DIST_CODE,DIST_NAME,DIST_REG_NO, FOS_CODE,FOS_NAME,FOS_REG_NO, ")
	.append("DEALER_CODE,DEALER_NAME,DEALER_REG_NO,MSISDN,SUBS_SIM,REGISTRATION_DATE,REGISTRATION_COMPLETED_DATE,")
	.append("VARIFICATION_DATE,VERIFICATION_COMPLETED_DATE,ACTIVATION_DATE FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRAN_BIZ_DETAILS WHERE CIRCLE_CODE=? AND REGISTRATION_STATUS = 'S' AND (ACTIVATION_STATUS IS NULL OR ACTIVATION_STATUS<>'S') ")
	.append(" ORDER BY CIRCLE_CODE,ZONE_ID, CITY_ID,ZBM_CODE,ZSM_CODE, TM_CODE, DIST_CODE, FOS_CODE,DEALER_CODE WITH UR").toString();
	

	// Month Wise MIS report
	public static final String QUERY_GET_MIS_REPORT_DEL_WISE_ACTV_COUNT_MONTH_WISE = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(
					" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME,FOS_MOBILE_NO, ")
			.append(" DEALER_CODE,DEALER_NAME,DEALER_MOBILE_NO FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE CIRCLE_CODE=? AND SUMMARY_DATE BETWEEN ? AND ? ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(
					" ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO, FOS_CODE,FOS_NAME,FOS_MOBILE_NO,DEALER_CODE, ")
			.append(" DEALER_NAME,DEALER_MOBILE_NO WITH UR").toString();

	public static final String QUERY_GET_MIS_REPORT_DIS_WISE_ACTV_COUNT_MONTH_WISE = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(
					" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE  CIRCLE_CODE=? AND SUMMARY_DATE BETWEEN ? AND ?  ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(
					" ZSM_NAME,TM_CODE,TM_NAME, DISTRIBUTOR_CODE,DISTRIBUTOR_NAME,DISTRIBUTOR_MOBILE_NO WITH UR")
			.toString();

	public static final String QUERY_GET_MIS_REPORT_TM_WISE_ACTV_COUNT_MONTH_WISE = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME, ")
			.append(" ZSM_CODE,ZSM_NAME,TM_CODE,TM_NAME FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE  CIRCLE_CODE=? AND SUMMARY_DATE BETWEEN ? AND ?   ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME,ZBM_CODE,ZBM_NAME,ZSM_CODE, ")
			.append(" ZSM_NAME,TM_CODE,TM_NAME WITH UR").toString();

	public static final String QUERY_GET_MIS_REPORT_ZONE_WISE_ACTV_COUNT_MONTH_WISE = new StringBuffer(
			500)
			.append(
					" SELECT CIRCLE_CODE,CIRCLE_NAME, ZONE_ID,ZONE_NAME FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE  CIRCLE_CODE=? AND SUMMARY_DATE BETWEEN ? AND ?     ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME, ZONE_ID,ZONE_NAME WITH UR")
			.toString();

	public static final String QUERY_GET_MIS_REPORT_CITY_WISE_ACTV_COUNT_MONTH_WISE = new StringBuffer(
			500)
			.append(" SELECT CIRCLE_CODE,CIRCLE_NAME, ZONE_ID,ZONE_NAME, CITY_ID,CITY_NAME FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE  CIRCLE_CODE=? AND SUMMARY_DATE BETWEEN ? AND ?    ")
			.append(
					" GROUP BY CIRCLE_CODE,CIRCLE_NAME, ZONE_ID,ZONE_NAME, CITY_ID,CITY_NAME WITH UR")
			.toString();

	// get the actv count for every date of the month (Monthly report)
	public static final String QUERY_GET_ACTC_COUNT_OF_MONTH_BY_DEALER = new StringBuffer(
			500)
			.append(
					"SELECT SUMMARY_DATE, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE SUMMARY_DATE BETWEEN ? AND ?  AND DEALER_CODE= ? GROUP BY SUMMARY_DATE WITH UR")
			.toString();

	public static final String QUERY_GET_ACTC_COUNT_OF_MONTH_BY_DIST = new StringBuffer(
			500)
			.append(
					"SELECT SUMMARY_DATE, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE SUMMARY_DATE BETWEEN ? AND ?  AND DISTRIBUTOR_CODE= ? GROUP BY SUMMARY_DATE WITH UR")
			.toString();

	public static final String QUERY_GET_ACTC_COUNT_OF_MONTH_BY_TM = new StringBuffer(
			500)
			.append(
					"SELECT SUMMARY_DATE, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE SUMMARY_DATE BETWEEN ? AND ?  AND  TM_CODE= ? GROUP BY SUMMARY_DATE WITH UR")
			.toString();

	public static final String QUERY_GET_ACTC_COUNT_OF_MONTH_BY_ZONE = new StringBuffer(
			500)
			.append(
					"SELECT SUMMARY_DATE, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE SUMMARY_DATE BETWEEN ? AND ?  AND ZONE_ID= ?  GROUP BY SUMMARY_DATE WITH UR")
			.toString();

	public static final String QUERY_GET_ACTC_COUNT_OF_MONTH_BY_CITY = new StringBuffer(
			500)
			.append(
					"SELECT SUMMARY_DATE, SUM(ACTIVATION_COUNT) AS ACTV_COUNT FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(
					".V_REPORT_SUMMARY WHERE SUMMARY_DATE BETWEEN ? AND ?  AND CITY_ID= ? GROUP BY SUMMARY_DATE WITH UR")
			.toString();

	// mis report schedule queires
	public static String FETCH_MIS_REPORT_SCHEDULE = new StringBuffer(500)
			.append(
					" SELECT REPORT_START_DATE,CIRCLE_CODE,REPORT_ID,REPORT_NAME,FORCE_START,FORCE_START_DATE  FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_REPORT_MSTR WHERE ")
			.append(
					" ((REPORT_START_DATE <= CURRENT_TIMESTAMP AND (UPPER(FORCE_START)='N')) ")
			.append(
					" OR (UPPER(FORCE_START)='Y' AND UPPER(FORCE_START_APP)='Y' AND FORCE_START_DATE IS NOT NULL) ) ")
			.append(" AND UPPER(RE_RUN_FLAG)='Y' ")
			.append(
					" AND STATUS='A' AND REPORT_NAME_TYPE='MIS' AND REPORT_TYPE=? WITH UR ")
			.toString();
	
	public static String FETCH_PENDING_REPORT_SCHEDULE = new StringBuffer(500)
	.append(
			" SELECT REPORT_START_DATE,CIRCLE_CODE,REPORT_ID,REPORT_NAME,FORCE_START,FORCE_START_DATE  FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_REPORT_MSTR WHERE ")
	.append(
			" ((REPORT_START_DATE <= CURRENT_TIMESTAMP AND (UPPER(FORCE_START)='N')) ")
	.append(
			" OR (UPPER(FORCE_START)='Y' AND UPPER(FORCE_START_APP)='Y' AND FORCE_START_DATE IS NOT NULL) ) ")
	.append(" AND UPPER(RE_RUN_FLAG)='Y' ")
	.append(
			" AND STATUS='A' AND REPORT_NAME_TYPE='PND' AND REPORT_TYPE=? WITH UR ")
	.toString();

	// Data feed schedule query
	public static String FETCH_MIS_REPORT_DATA_FEED_SCHEDULE = new StringBuffer(
			500)
			.append(
					" SELECT REPORT_START_DATE,CIRCLE_CODE,REPORT_ID,REPORT_NAME,FORCE_START,FORCE_START_DATE  FROM ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_REPORT_MSTR WHERE ")
			.append(
					" ((REPORT_START_DATE <= CURRENT_TIMESTAMP AND (UPPER(FORCE_START)='N')) ")
			.append(
					" OR (UPPER(FORCE_START)='Y' AND UPPER(FORCE_START_APP)='Y' AND FORCE_START_DATE IS NOT NULL) ) ")
			.append(" AND UPPER(RE_RUN_FLAG)='Y' ")
			.append(
					" AND STATUS='A' AND REPORT_NAME_TYPE='DFD' AND REPORT_TYPE=? WITH UR ")
			.toString();
	
	
	public static final String UPDATE_MIS_SCHEDULE_TIME = new StringBuffer(500)
			.append("UPDATE  ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_REPORT_MSTR ")
			.append(
					" SET REPORT_START_DATE= REPORT_START_DATE + 2 HOUR WHERE CIRCLE_CODE=? AND REPORT_ID=?")
			.append(" WITH UR").toString();

	// In future this quueires will be added into ReportDAO Interface
	// This queiry is used in the following conditions :
	// When we start the report --RE_ RUN FLAG -NO
	public static final String UPDATE_PARAMETERS_FOR_REPORT_START = new StringBuffer(
			500).append("UPDATE  ").append(SQLConstants.FTA_SCHEMA).append(
			".V_REPORT_MSTR ").append(" SET RE_RUN_FLAG='N' WHERE REPORT_ID=?")
			.append(" WITH UR").toString();

	// When we end the report id the force start flag is Y
	public static final String UPDATE_PARAMETERS_FOR_REPORT_END = new StringBuffer(
			500)
			.append("UPDATE  ")
			.append(SQLConstants.FTA_SCHEMA)
			.append(".V_REPORT_MSTR ")
			.append(
					" SET RE_RUN_FLAG='Y', FORCE_START='N', FORCE_START_DATE = NULL WHERE REPORT_ID=?")
			.append(" WITH UR").toString();

	public static final String UDATE_REPORT_NEXT_SCHEDULE_DATE = new StringBuffer(
			500).append("{call ").append(SQLConstants.FTA_SCHEMA).append(".")
			.append(
					Utility.getValueFromBundle(
							Constants.PROC_UDATE_REPORT_NEXT_SCHEDULE_DATE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE)).append(
					"(?,?,?)}").toString();
	
	
}
