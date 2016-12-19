/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author a_gupta
 *
 */
public interface SubscriberReleaseDAOInterface {
	
	public static String COUNT_RELEASED_SUB1 = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR ")
	.append("where ACTION_TAKEN=? AND DATE(ACTION_DATE)=? ")
	.append(" WITH UR").toString();
	
	
	
	public static String COUNT_RELEASED_SUB = new StringBuffer(500)
	.append("SELECT COUNT(*)")
	.append("FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR SM, ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_HISTORY TM ")
	.append("WHERE ")
	.append("SM.ACTION_TAKEN=? AND  DATE(SM.ACTION_DATE)=? AND TM.SUBS_MSISDN=SM.MSISDN ")
	.append("AND TM.STATUS='S' AND SM.STATUS='A' ")
	.append(" WITH UR").toString();
	
	public static String RELEASED_SUB1 = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR ")
	.append("where ACTION_TAKEN=? AND DATE(ACTION_DATE)=? ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String RELEASED_SUB = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT SM.MSISDN,SM.SIM,SM.STATUS,SM.ACTION_DATE, ")
	.append("CASE ")
	.append("WHEN(TM.TRAN_TYPE_ID=8) ")
	.append("THEN TM.UPDATED_DT ")
	.append("ELSE NULL ")
	.append("END AS REGISTERED_DATE, ")
	.append("CASE ")
	.append("WHEN(TM.TRAN_TYPE_ID=51) ")
	.append("THEN TM.UPDATED_DT ")
	.append("ELSE NULL ")
	.append("END AS VERIFIED_DATE ")
	.append("FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR SM, ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_HISTORY TM ")
	.append("WHERE ")
	.append("SM.ACTION_TAKEN=? AND  DATE(SM.ACTION_DATE)=? AND TM.SUBS_MSISDN=SM.MSISDN ")
	.append("AND TM.STATUS='S' AND SM.STATUS='A'  ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String RELEASED_SUB2 = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT SM.MSISDN,SM.SIM,SM.STATUS,SM.ACTION_DATE,") 
	.append(" (select UPDATED_DT from ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_HISTORY where SUBS_MSISDN=SM.MSISDN and TRAN_TYPE_ID=8 and STATUS='S') ")
	.append("as REGISTERED_DATE ,")
	.append(" (select UPDATED_DT from ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_HISTORY where SUBS_MSISDN=SM.MSISDN and TRAN_TYPE_ID=51 and STATUS='S') ")
	.append("as VERIFIED_DATE ")
	.append(" FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR SM ")
	.append("WHERE ")
	.append("SM.ACTION_TAKEN=? AND  DATE(SM.ACTION_DATE)=? AND SM.STATUS='A'  ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
}
