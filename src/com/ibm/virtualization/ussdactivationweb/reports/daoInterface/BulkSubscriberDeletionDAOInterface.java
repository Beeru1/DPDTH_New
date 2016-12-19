/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.daoInterface;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author a_gupta
 *
 */
public interface BulkSubscriberDeletionDAOInterface {
	
	public static String FETCH_SUBSCRIBER = new StringBuffer(500)
	.append(" SELECT MSISDN ,COMPLETE_SIM ,ACTION_TAKEN,ACTION_DATE ,DATE(ACTION_DATE) AS DEL_DATE FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR WHERE ACTION_TAKEN = ? AND DATE(ACTION_DATE) = DATE(current timestamp) WITH UR").toString();
	
	public static String FILE_ENTRY_Business_User = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_REPORT_INFO(FILE_ID, FILE_NAME,FILE_PATH,CIRCLE_CODE,REPORT_ID,REPORT_DATE ")
	.append(",CREATED_DT) VALUES(NEXT VALUE FOR SEQ_V_FILE_MSTR,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP) ")
	.toString();
}
