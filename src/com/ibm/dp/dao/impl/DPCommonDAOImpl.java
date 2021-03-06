package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPCommonDAO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPCommonDAOImpl extends BaseDaoRdbms implements DPCommonDAO {


	private Logger logger = Logger.getLogger(DPCommonDAOImpl.class.getName());

	public static final String SQL_GET_PRODUCT_EFFECT_PRICE = "SELECT PRODUCT_SECURITY FROM DP_PRODUCT_SECURITY WHERE PRODUCT_TYPE = ? AND PRODUCT_CATEGORY = ? WITH UR";
	
	public static final String SQL_GET_PRODUCT_TYPE_CAT = "SELECT PRODUCT_TYPE, PRODUCT_CATEGORY FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = ? WITH UR";

	public static final String SQL_GET_TOTAL_STOCK_DIST_PROD = 
		"select (FWD_STOCK+REW_STOCK1+REW_STOCK2+REW_STOCK3+CHURN_STOCK1+coalesce(NSER_STOCK, 0)+FRESH_STOCK1+coalesce(PENDING_STOCK1,0)+coalesce(PENDING_STOCK2,0)) AS TOTAL_STOCK, records.PRODUCT_ID from " +
		" (select (select count(1) from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS FWD_STOCK, " + 
		" (select count(1) from DP_REV_INVENTORY_CHANGE " +
		" where NEW_DIST_D=a.DISTRIBUTOR_ID and a.PRODUCT_ID=DEFECTIVE_PRODUCT_ID and STATUS='REC')  AS REW_STOCK1, " +
		" (select count(1) from DP_REV_STOCK_INVENTORY " +
		" where CREATED_BY=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID ) AS REW_STOCK2, " +
		" (select count(1) from DP_REV_DELIVERY_CHALAN_DETAIL " +
		" where DIST_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID and STATUS = 'S2W' ) AS REW_STOCK3, " +
		" (select count(1) from DP_REV_CHURN_INVENTORY " +
		" where CREATED_BY=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS CHURN_STOCK1, " +
		" (select count(1) from DP_REV_FRESH_STOCK " +  
		" where DISTRIBUTOR_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID ) AS FRESH_STOCK1, " + 
		" (select CLOSING_STOCK from DP_OPEN_STOCK_DTL where DIST_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS NSER_STOCK, " +
		" (select sum(PD.APPROVED_QTY) from DP_PR_HEADER PH  inner join DP_PR_DETAILS PD on PH.PR_NO=PD.PR_NO and PH.WEBSER_OP_FLAG='DP' " +
		" where PH.PR_DIST_ID=a.DISTRIBUTOR_ID and PD.PRODUCT_ID=a.PRODUCT_ID group by PD.PRODUCT_ID) as PENDING_STOCK1, " +
		" (select sum(PD.APPROVED_QTY) from DP_PR_HEADER PH inner join DP_PR_DETAILS PD on PH.PR_NO=PD.PR_NO and PH.WEBSER_OP_FLAG='BT' " +
		" inner join PO_HEADER PO on PH.PR_NO=PO.PR_NO and PO.PO_STATUS in (1,2,3,4) where PH.PR_DIST_ID=a.DISTRIBUTOR_ID and PD.PRODUCT_ID=a.PRODUCT_ID " +
		" group by PD.PRODUCT_ID) as PENDING_STOCK2, a.PRODUCT_ID " +
		" from DP_DIST_PRODUCT_STOCK a where a.DISTRIBUTOR_ID=? and a.PRODUCT_ID = ?" +
		" group by DISTRIBUTOR_ID, PRODUCT_ID) as records " +
		" group by FWD_STOCK,REW_STOCK1,REW_STOCK2,REW_STOCK3,CHURN_STOCK1,NSER_STOCK,FRESH_STOCK1,PENDING_STOCK1,PENDING_STOCK2, PRODUCT_ID with ur ";
	
	public static final String SQL_GET_TOTAL_STOCK_DIST = 
		"select (FWD_STOCK+REW_STOCK1+REW_STOCK2+REW_STOCK3+CHURN_STOCK1+coalesce(NSER_STOCK, 0)+FRESH_STOCK1+coalesce(PENDING_STOCK1,0)+coalesce(PENDING_STOCK2,0)) AS TOTAL_STOCK, records.PRODUCT_ID from " +
		" (select (select count(1) from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS FWD_STOCK, " + 
		" (select count(1) from DP_REV_INVENTORY_CHANGE " +
		" where NEW_DIST_D=a.DISTRIBUTOR_ID and a.PRODUCT_ID=DEFECTIVE_PRODUCT_ID and STATUS='REC')  AS REW_STOCK1, " +
		" (select count(1) from DP_REV_STOCK_INVENTORY " +
		" where CREATED_BY=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID ) AS REW_STOCK2, " +
		" (select count(1) from DP_REV_DELIVERY_CHALAN_DETAIL " +
		" where DIST_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID and STATUS = 'S2W' ) AS REW_STOCK3, " +
		" (select count(1) from DP_REV_CHURN_INVENTORY " +
		" where CREATED_BY=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS CHURN_STOCK1, " +
		" (select count(1) from DP_REV_FRESH_STOCK " +  
		" where DISTRIBUTOR_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID ) AS FRESH_STOCK1, " + 
		" (select CLOSING_STOCK from DP_OPEN_STOCK_DTL where DIST_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS NSER_STOCK, " +
		" (select sum(PD.APPROVED_QTY) from DP_PR_HEADER PH  inner join DP_PR_DETAILS PD on PH.PR_NO=PD.PR_NO and PH.WEBSER_OP_FLAG='DP' " +
		" where PH.PR_DIST_ID=a.DISTRIBUTOR_ID and PD.PRODUCT_ID=a.PRODUCT_ID group by PD.PRODUCT_ID) as PENDING_STOCK1, " +
		" (select sum(PD.APPROVED_QTY) from DP_PR_HEADER PH inner join DP_PR_DETAILS PD on PH.PR_NO=PD.PR_NO and PH.WEBSER_OP_FLAG='BT' " +
		" inner join PO_HEADER PO on PH.PR_NO=PO.PR_NO and PO.PO_STATUS in (1,2,3,4) where PH.PR_DIST_ID=a.DISTRIBUTOR_ID and PD.PRODUCT_ID=a.PRODUCT_ID " +
		" group by PD.PRODUCT_ID) as PENDING_STOCK2, a.PRODUCT_ID " +
		" from DP_DIST_PRODUCT_STOCK a where a.DISTRIBUTOR_ID=? " +
		" group by DISTRIBUTOR_ID, PRODUCT_ID) as records " +
		" group by FWD_STOCK,REW_STOCK1,REW_STOCK2,REW_STOCK3,CHURN_STOCK1,NSER_STOCK,FRESH_STOCK1,PENDING_STOCK1,PENDING_STOCK2, PRODUCT_ID with ur ";
	
	
	public static final String SQL_GET_TOTAL_STOCK_DIST_ALL = "select OVER_ALL_RECORDS.DISTRIBUTOR_ID, (OVER_ALL_RECORDS.DIST_SECURITY- sum(OVER_ALL_RECORDS.STOCK_SECURITY)) as CURRENT_SECURITY from" +
			"	 (select all_record.DISTRIBUTOR_ID as DISTRIBUTOR_ID, all_record.STOCK_security, all_record.dist_security" +
			"	 from (select records.DISTRIBUTOR_ID, sum ((records.cnt*records.cost)) as STOCK_security" +
			" ,(ds.SECURITY_AMOUNT+ds.loan_amount) as dist_security" +
			" from (select a.DISTRIBUTOR_ID, a.PRODUCT_ID, count(a.SERIAL_NO) cnt,  case when c.PRODUCT_SECURITY is null then 0" +
			"	 else c.PRODUCT_SECURITY end cost" +
			"	 from DP_STOCK_INVENTORY a" +
			" inner join DP_PRODUCT_MASTER b on a.PRODUCT_ID=b.PRODUCT_ID" +
			"	 left outer join DP_PRODUCT_SECURITY c on c.PRODUCT_TYPE=b.PRODUCT_TYPE " +
			" and c.PRODUCT_CATEGORY=b.PRODUCT_CATEGORY" +
			"" +
			" group by DISTRIBUTOR_ID, a.PRODUCT_ID, PRODUCT_SECURITY) records, DP_DIST_SECURITY_LOAN ds" +
			"	 where ds.DIST_ID=records.DISTRIBUTOR_ID" +
			"	 group by records.DISTRIBUTOR_ID, SECURITY_AMOUNT,loan_amount) all_record" +
			"	 Union All" +
			"" +
			" select all_record.DIST_ID as DISTRIBUTOR_ID, all_record.STOCK_security, all_record.dist_security" +
			"	 from (select records.DIST_ID, sum ((records.cnt*records.cost)) as STOCK_security" +
			" ,(ds.SECURITY_AMOUNT+ds.loan_amount) as dist_security" +
			" from (select a.DIST_ID, a.PRODUCT_ID, a.CLOSING_STOCK as cnt,  case when c.PRODUCT_SECURITY is null then 0" +
			" else c.PRODUCT_SECURITY end cost" +
			"	 from DP_OPEN_STOCK_DTL a" +
			"	 inner join DP_PRODUCT_MASTER b on a.PRODUCT_ID=b.PRODUCT_ID" +
			"	 left outer join DP_PRODUCT_SECURITY c on c.PRODUCT_TYPE=b.PRODUCT_TYPE " +
			" and c.PRODUCT_CATEGORY=b.PRODUCT_CATEGORY" +
			"	 group by DIST_ID, a.PRODUCT_ID, PRODUCT_SECURITY, CLOSING_STOCK) records, DP_DIST_SECURITY_LOAN ds" +
			"	 where ds.DIST_ID=records.DIST_ID" +
			"	 group by records.DIST_ID, SECURITY_AMOUNT,loan_amount) all_record" +
			"	 UNION ALL	" +
			"" +
			" select all_record.DISTRIBUTOR_ID as DISTRIBUTOR_ID, all_record.STOCK_security, all_record.dist_security" +
			"" +
			" from (select records.DISTRIBUTOR_ID, sum ((records.cnt*records.cost)) as STOCK_security" +
			" ,(ds.SECURITY_AMOUNT+ds.loan_amount) as dist_security" +
			"	 from (select a.NEW_DIST_D as DISTRIBUTOR_ID, b.PRODUCT_ID, count(a.DEFECTIVE_SR_NO) cnt,  case when c.PRODUCT_SECURITY is null then 0" +
			"	 else c.PRODUCT_SECURITY end cost" +
			"	 from DP_REV_INVENTORY_CHANGE a" +
			"	 inner join DP_PRODUCT_MASTER b on a.DEFECTIVE_PRODUCT_ID=b.PRODUCT_ID" +
			"	 left outer join DP_PRODUCT_SECURITY c on c.PRODUCT_TYPE=b.PRODUCT_TYPE " +
			" and c.PRODUCT_CATEGORY=b.PRODUCT_CATEGORY" +
			"	 where a.STATUS='REC'" +
			"	 group by NEW_DIST_D, b.PRODUCT_ID, PRODUCT_SECURITY) records, DP_DIST_SECURITY_LOAN ds" +
			"	 where ds.DIST_ID=records.DISTRIBUTOR_ID" +
			"	 group by records.DISTRIBUTOR_ID, SECURITY_AMOUNT,loan_amount) all_record" +
			"	 UNION ALL" +
			"	" +
			" select all_record.DISTRIBUTOR_ID as DISTRIBUTOR_ID, all_record.STOCK_security, all_record.dist_security" +
			"	 from (select records.DISTRIBUTOR_ID, sum ((records.cnt*records.cost)) as STOCK_security" +
			"	 ,(ds.SECURITY_AMOUNT+ds.loan_amount) as dist_security" +
			"	 from (select a.CREATED_BY as DISTRIBUTOR_ID, b.PRODUCT_ID, count(a.SERIAL_NO_COLLECT) cnt,  case when c.PRODUCT_SECURITY is null then 0" +
			"	  	 else c.PRODUCT_SECURITY end cost	 from DP_REV_STOCK_INVENTORY a	 inner join DP_PRODUCT_MASTER b on a.PRODUCT_ID=b.PRODUCT_ID" +
			"	 left outer join DP_PRODUCT_SECURITY c on c.PRODUCT_TYPE=b.PRODUCT_TYPE " +
			" and c.PRODUCT_CATEGORY=b.PRODUCT_CATEGORY" +
			"	 group by a.CREATED_BY, b.PRODUCT_ID, PRODUCT_SECURITY) records, DP_DIST_SECURITY_LOAN ds" +
			"	 where ds.DIST_ID=records.DISTRIBUTOR_ID" +
			"	 group by records.DISTRIBUTOR_ID, SECURITY_AMOUNT,loan_amount) all_record" +
			"	 " +
			"	 union all" +
			"	 select all_record.DISTRIBUTOR_ID as DISTRIBUTOR_ID, all_record.STOCK_security, all_record.dist_security" +
			"	" +
			" from (select records.DISTRIBUTOR_ID, sum ((records.cnt*records.cost)) as STOCK_security" +
			"	 ,(ds.SECURITY_AMOUNT+ds.loan_amount) as dist_security" +
			"	 from (select a.CREATED_BY as DISTRIBUTOR_ID, b.PRODUCT_ID, count(a.SERIAL_NO_COLLECT) cnt,  case when c.PRODUCT_SECURITY is null then 0" +
			"	 " +
			" else c.PRODUCT_SECURITY end cost" +
			"	 from DP_REV_STOCK_INVENTORY_HIST a" +
			"	 " +
			" inner join DP_PRODUCT_MASTER b on a.PRODUCT_ID=b.PRODUCT_ID" +
			"	 " +
			" left outer join DP_PRODUCT_SECURITY c on c.PRODUCT_TYPE= b.PRODUCT_TYPE " +
			" and c.PRODUCT_CATEGORY=b.PRODUCT_CATEGORY where a.STATUS='S2W'" +
			"	 group by a.CREATED_BY, b.PRODUCT_ID, PRODUCT_SECURITY) records, DP_DIST_SECURITY_LOAN ds" +
			"	 where ds.DIST_ID=records.DISTRIBUTOR_ID	 group by records.DISTRIBUTOR_ID, SECURITY_AMOUNT,loan_amount) all_record " +
			"  union all" +
			" select all_record.DISTRIBUTOR_ID as DISTRIBUTOR_ID, all_record.STOCK_security, all_record.dist_security" +
			" from (select records.DISTRIBUTOR_ID, sum ((records.cnt*records.cost)) as STOCK_security" +
			" ,(ds.SECURITY_AMOUNT+ds.loan_amount) as dist_security" +
			" from (select a.CREATED_BY as DISTRIBUTOR_ID, b.PRODUCT_ID," +
			" count(a.SERIAL_NUMBER) cnt,  case when c.PRODUCT_SECURITY is null then 0 " +
			" else c.PRODUCT_SECURITY end cost from DP_REV_CHURN_INVENTORY a " +
			" inner join DP_PRODUCT_MASTER b on a.PRODUCT_ID=b.PRODUCT_ID" +
			" left outer join DP_PRODUCT_SECURITY c on c.PRODUCT_TYPE= b.PRODUCT_TYPE  " +
			" and c.PRODUCT_CATEGORY=b.PRODUCT_CATEGORY " +
			" group by a.CREATED_BY, b.PRODUCT_ID, PRODUCT_SECURITY) records, DP_DIST_SECURITY_LOAN ds " +
			" where ds.DIST_ID=records.DISTRIBUTOR_ID " +
			" group by records.DISTRIBUTOR_ID, SECURITY_AMOUNT,loan_amount) all_record )" +
			" OVER_ALL_RECORDS where DISTRIBUTOR_ID=? group by DISTRIBUTOR_ID, DIST_SECURITY  with ur";
	
	public static final String SQL_MAX_DAYS = "SELECT MAX_DAYS FROM DP_DIST_STOCK_LEVEL WHERE DIST_ID = ? AND PRODUCT_ID = ? WITH UR";
	
	public static final String SQL_MIN_DAYS = "SELECT MIN_DAYS FROM DP_DIST_STOCK_LEVEL WHERE DIST_ID = ? AND PRODUCT_ID = ? WITH UR";
	
	//public static final String SQL_CURRENT_STOCK = "SELECT COUNT(*) AS TOTAL_CURR_STOCK FROM DP_STOCK_INVENTORY WHERE  DISTRIBUTOR_ID = ? AND PRODUCT_ID = ? AND MARK_DAMAGED = 'N'";
	
	public static final String SQL_GET_PRODUCT_TYPE = "SELECT PRODUCT_TYPE,PRODUCT_CATEGORY FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = ? WITH UR"; //PRODUCT_CATEGORY Added by NEetika
	public static final String SQL_GET_PRODUCT_TYPE_ID = "SELECT PRODUCT_TYPE,PRODUCT_CATEGORY,ID FROM DP_PRODUCT_MASTER, DP_CONFIGURATION_DETAILS WHERE PRODUCT_ID =? and PRODUCT_CATEGORY=value and config_id=8  WITH UR"; //to make the code configurable
	public static final String SQL_GET_CONFIGURATION="select FIELD_NAME from DP_ELIGIBILITY_CONFIGURATION where prod_config_id=? and product_type=? and dist_type=? with ur";
	private static final String SQL_GET_TOTAL_STOCK_DIST_SECURITY = "select total.PRODUCT_ID, (int(total.TOTAL_STOCK) * int(ps.PRODUCT_SECURITY)) as STOCK_PRICE" +
			" from (select (FWD_STOCK+REW_STOCK1+REW_STOCK2+REW_STOCK3+CHURN_STOCK1+coalesce(NSER_STOCK, 0)+FRESH_STOCK1+coalesce(PENDING_STOCK1,0)+coalesce(PENDING_STOCK2,0)) AS TOTAL_STOCK," +
			" records.PRODUCT_ID, records.Distributor_ID from" +
			" (select (select count(1) from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS FWD_STOCK," +
			" (select count(1) from DP_REV_INVENTORY_CHANGE" +
			" where NEW_DIST_D=a.DISTRIBUTOR_ID and a.PRODUCT_ID=DEFECTIVE_PRODUCT_ID and STATUS='REC')  AS REW_STOCK1," +
			" (select count(1) from DP_REV_STOCK_INVENTORY" +
			" where CREATED_BY=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS REW_STOCK2," +
			" (select count(1) from DP_REV_DELIVERY_CHALAN_DETAIL" +
			" where DIST_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID and STATUS in ('S2W') ) AS REW_STOCK3," +
			" (select count(1) from DP_REV_CHURN_INVENTORY " +
			" where CREATED_BY=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS CHURN_STOCK1," +
			" (select count(1) from DP_REV_FRESH_STOCK" +
			" where DISTRIBUTOR_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID ) AS FRESH_STOCK1," +
			" (select CLOSING_STOCK from DP_OPEN_STOCK_DTL where DIST_ID=a.DISTRIBUTOR_ID and PRODUCT_ID=a.PRODUCT_ID) AS NSER_STOCK," +
			" (select sum(PD.APPROVED_QTY) from DP_PR_HEADER PH  inner join DP_PR_DETAILS PD on PH.PR_NO=PD.PR_NO and PH.WEBSER_OP_FLAG='DP'" +
			" where PH.PR_DIST_ID=a.DISTRIBUTOR_ID and PD.PRODUCT_ID=a.PRODUCT_ID group by PD.PRODUCT_ID) as PENDING_STOCK1," +
			" (select sum(PD.APPROVED_QTY) from DP_PR_HEADER PH  inner join DP_PR_DETAILS PD on PH.PR_NO=PD.PR_NO and PH.WEBSER_OP_FLAG='BT'" +
			" inner join PO_HEADER PO on PH.PR_NO=PO.PR_NO and PO.PO_STATUS in (1,2,3,4) where PH.PR_DIST_ID=a.DISTRIBUTOR_ID and PD.PRODUCT_ID=a.PRODUCT_ID " +
			" group by PD.PRODUCT_ID) as PENDING_STOCK2," +
			" a.PRODUCT_ID, a.DISTRIBUTOR_ID from DP_DIST_PRODUCT_STOCK a where DISTRIBUTOR_ID=? group by DISTRIBUTOR_ID, PRODUCT_ID) as records" +
			" group by FWD_STOCK,REW_STOCK1,REW_STOCK2,REW_STOCK3,CHURN_STOCK1,NSER_STOCK,FRESH_STOCK1,PENDING_STOCK1,PENDING_STOCK2,PRODUCT_ID, Distributor_ID ) as total" +
			" inner join DP_PRODUCT_MASTER pm on total.PRODUCT_ID=pm.PRODUCT_ID inner join DP_PRODUCT_SECURITY ps on pm.PRODUCT_CATEGORY=ps.PRODUCT_CATEGORY" +
			" and pm.PRODUCT_TYPE=ps.PRODUCT_TYPE with ur";
	//For Balance
	public static final String SQL_GET_ELIGIBILITY = "select SECURITY_DEPOSIT as BALANCE from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) and CREATEDDATE=( select max(a.CREATEDDATE) from  ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name ) as a  group by LOGIN_NAME  ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_COMM="select SECURITY_BALANCE as BALANCE from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?)"
	+" and CREATEDDATE=(" +
	"  select max(a.CREATEDDATE) from "+
	" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union  "+
	" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
	" ) as a  group by LOGIN_NAME  ) union" +
	" select SECURITY_BALANCE as BALANCE from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?)"
	+ " and CREATEDDATE=(" +
	"  select max(a.CREATEDDATE) from "+
	" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union  "+
	" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
	" ) as a  group by LOGIN_NAME  ) with ur";
	//following are old queries dont refer them
	//For SWAP 
	/*
	public static final String SQL_GET_ELIGIBILITY_SNORMAL = "select SD_ELIGIBILITY as ELIG from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
			" and CREATEDDATE=( "+
		" select max(a.CREATEDDATE) from " +
	 " ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
	 " select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
	 " ) as a  group by LOGIN_NAME )   "+
			" union  "+
			 " select SD_CONSUMPTION as ELIG from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
			" and CREATEDDATE=( "+
	 " select max(a.CREATEDDATE) from "+
	 " ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
	 " select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
	 " ) as a  group by LOGIN_NAME "+
	" ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_SHD = "select HD_ELIGIBILITY as ELIG from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select HD_CONSUMPTION as ELIG from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_SPVR = " select PVR_ELIGIBILITY as ELIG from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select PVR_CONSUMPTION as ELIG from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_SHDDVR = "select HDDVR_ELIGBILITY as ELIG from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select HDDVR_CONSUMPTION as ELIG from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_SSDPLUS = "select SDPLUS_ELIBILITY as ELIG from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select SDPLUS_CONSUMPTION as ELIG from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur";
	//For commercial

	public static final String SQL_GET_ELIGIBILITY_CNORMAL = " select MAX_ELIG_SD as ELIG from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select MAX_ELIG_SD as ELIG from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_CHD = " select MAX_ELIG_HD as ELIG from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select MAX_ELIG_HD as ELIG from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur";
	
	public static final String SQL_GET_ELIGIBILITY_CSDPLUS = " select MAX_ELIG_SD as ELIG from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+ 
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from " +
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union   "+
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME )   "+
	" union  "+
	 " select MAX_ELIG_SD as ELIG from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) "+
	" and CREATEDDATE=( "+
" select max(a.CREATEDDATE) from "+
" ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name union "+ 
" select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= (select login_name from vr_login_master where login_id=?) group by login_name "+
" ) as a  group by LOGIN_NAME "+
" ) with ur"; */
	
	private static DPCommonDAO commonDao = new DPCommonDAOImpl();
	private DPCommonDAOImpl(){};
	public static DPCommonDAO getInstance() {
		return commonDao;
	}

	public Integer getDistMaxPOQty(Integer intDistributorID, Integer intProductID) throws DAOException {
		Integer maxQuantity = 0;
		Integer maxPOQuantity = 0;
		Integer totalCurrStock = 0;		
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;
		
		try {
			totalCurrStock = getDistTotalStock(intDistributorID, intProductID);
			maxQuantity = getDistStockEligibilityMax(intDistributorID, intProductID);
			maxPOQuantity = maxQuantity - totalCurrStock;
			if(maxPOQuantity < 0) {
				maxPOQuantity = 0;
			}
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}

		return maxPOQuantity;
	}

	public Integer getDistStockEligibilityMax(Integer intDistributorID, Integer intProductID) throws DAOException {
		//Max Stock - (No. of Activations / No. of Days) * Max Days

			//System.out.println("DIST ID:" + intDistributorID + "PROD ID:" + intProductID);
		Integer distStockEligibilityMax = 0;
		double distStockEligibilityMaxDouble = 0.0;
		int activationCount = 0;
		int daysCount = 0;
		int maxDays = 0;
		int productType = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;
		
		try {

			connection = DBConnectionManager.getDBConnection();
			productType = getProductType(intProductID, connection);
			
			maxDays = getMaxDays(intDistributorID, intProductID, connection);
			
			GregorianCalendar cal = new GregorianCalendar() ;
			
				
			if(productType == 0) { //SWAP
				cal.add(Calendar.MONTH, -1);
				daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				int invChangeCount = 0;
				int invChangeHistCount = 0;
				
				StringBuffer invChangeSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_REV_INVENTORY_CHANGE WHERE NEW_DIST_D = ? AND DEFECTIVE_PRODUCT_ID = ? AND COLLECTION_ID = '1' ");
				invChangeSql.append("AND DATE(INV_CHANGE_DATE) BETWEEN ((CURRENT DATE - 1 month) + 1 days  - day(CURRENT DATE)days) AND (current date - (day(CURRENT DATE)) days)");
				
				StringBuffer invChangeHistSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_REV_INVENTORY_CHANGE_HIST WHERE NEW_DIST_D = ? AND DEFECTIVE_PRODUCT_ID = ? AND COLLECTION_ID = '1'");
				invChangeHistSql.append("AND DATE(INV_CHANGE_DATE) BETWEEN ((CURRENT DATE - 1 month) + 1 days  - day(CURRENT DATE)days) AND (current date - (day(CURRENT DATE)) days)");
				//logger.info(daysCount+" === daysCount"+invChangeHistSql+"    =====   invChangeHistSql");
				
				/*StringBuffer invChangeSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_REV_INVENTORY_CHANGE WHERE NEW_DIST_D = ? AND DEFECTIVE_PRODUCT_ID = ? ");
				StringBuffer invChangeHistSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_REV_INVENTORY_CHANGE_HIST WHERE NEW_DIST_D = ? AND DEFECTIVE_PRODUCT_ID = ? ");
				if(day < 3 ) {
					//Take previous months activation data
					invChangeSql = invChangeSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)"); 
					invChangeHistSql = invChangeHistSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)");
					daysCount = 30;
				} else {
					//Take current month
					day = day - 1;
					invChangeSql = invChangeSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - " + day + " days) AND DATE(current date)");
					invChangeHistSql = invChangeHistSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - " + day + " days) AND DATE(current date)");
					daysCount = day;
				}*/

				pstmt = connection.prepareStatement(invChangeSql.toString());
				pstmt.setInt(1, intDistributorID);
				pstmt.setInt(2, intProductID);
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					invChangeCount = rset.getInt("TOTAL_ACTIVATIONS");
				}
				
				DBConnectionManager.releaseResources(pstmt, rset);

				pstmt = connection.prepareStatement(invChangeHistSql.toString());
				pstmt.setInt(1, intDistributorID);
				pstmt.setInt(2, intProductID);
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					invChangeHistCount = rset.getInt("TOTAL_ACTIVATIONS");
				}

				activationCount = invChangeCount + invChangeHistCount;
				
				//logger.info("invChangeCount:" + invChangeCount + "invChangeHistCount:"+ invChangeHistCount + "maxDays:"+ maxDays + "intDistributorID:" + intDistributorID + "intProductID:" + intProductID);
			} else { //Commercial
				StringBuffer activationSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_STOCK_INVENTORY_ASSIGNED WHERE DISTRIBUTOR_ID = ? AND PRODUCT_ID = ? AND STATUS = 3 AND IS_SCM IN ('Y', 'U') ");
				int day = cal.get(Calendar.DAY_OF_MONTH);
				if(day < 3 ) {
					//Take previous months activation data
					activationSql = activationSql.append(" AND DATE(ASSIGN_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)"); 
					daysCount = 30;
				} else {
					//Take current month
					day = day - 1;
					//logger.info("day ================================================="+day);
					activationSql = activationSql.append(" AND DATE(ASSIGN_DATE) BETWEEN DATE(current date - " + day + " days) AND DATE(current date)");
					daysCount = day;
				}


				pstmt = connection.prepareStatement(activationSql.toString());
				pstmt.setInt(1, intDistributorID);
				pstmt.setInt(2, intProductID);
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					activationCount = rset.getInt("TOTAL_ACTIVATIONS");
				}
				//logger.info("activationCount:" + activationCount + "maxDays:"+ maxDays + "intDistributorID:" + intDistributorID + "intProductID:" + intProductID);
			}
			
			double daysCountDouble = Double.valueOf(daysCount);
			distStockEligibilityMaxDouble =  (double)((activationCount*maxDays)/daysCountDouble);
			
			distStockEligibilityMax = (int) Math.round(distStockEligibilityMaxDouble);
			
			//System.out.println("distStockEligibilityMaxDouble:" + distStockEligibilityMaxDouble + "distStockEligibilityMax:" + distStockEligibilityMax);
			//int i  = (int) Math.round(distStockEligibilityMaxDouble);
		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			
			//Added to fix DEFECT  ::  MASDB00185656 (Close the opend)
			DBConnectionManager.releaseResources(connection);
		}
		return distStockEligibilityMax;
	}

	public Integer getDistStockEligibilityMin(Integer intDistributorID, Integer intProductID) throws DAOException {
		Integer distStockEligibilityMin = 0;
		double distStockEligibilityMinDouble = 0.0;
		int activationCount = 0;
		int daysCount = 0;
		int minDays = 0;
		int productType = 0;		
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;
		
		try {
			
			connection = DBConnectionManager.getDBConnection();
			productType = getProductType(intProductID, connection);
			
			minDays = getMinDays(intDistributorID, intProductID, connection);
			
			GregorianCalendar cal = new GregorianCalendar() ;
			

			if(productType == 0) { //SWAP
				cal.add(Calendar.MONTH, -1);
				daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				int invChangeCount = 0;
				int invChangeHistCount = 0;
				
				StringBuffer invChangeSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_REV_INVENTORY_CHANGE WHERE NEW_DIST_D = ? AND DEFECTIVE_PRODUCT_ID = ? AND COLLECTION_ID = '1' ");
				invChangeSql.append("AND DATE(INV_CHANGE_DATE) BETWEEN ((CURRENT DATE - 1 month) + 1 days  - day(CURRENT DATE)days) AND (current date - (day(CURRENT DATE)) days)");
				
				StringBuffer invChangeHistSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_REV_INVENTORY_CHANGE_HIST WHERE NEW_DIST_D = ? AND DEFECTIVE_PRODUCT_ID = ? AND COLLECTION_ID = '1' ");
				invChangeHistSql.append("AND DATE(INV_CHANGE_DATE) BETWEEN ((CURRENT DATE - 1 month) + 1 days  - day(CURRENT DATE)days) AND (current date - (day(CURRENT DATE)) days)");
				
				//invChangeSql = invChangeSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)"); 
				//invChangeHistSql = invChangeHistSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)");
				
				/*if(day < 3 ) {
					//Take previous months activation data
					invChangeSql = invChangeSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)"); 
					invChangeHistSql = invChangeHistSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)");
					daysCount = 30;
				} else {
					//Take current month
					day = day - 1;
					invChangeSql = invChangeSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - " + day + " days) AND DATE(current date)");
					invChangeHistSql = invChangeHistSql.append(" AND DATE(INV_CHANGE_DATE) BETWEEN DATE(current date - " + day + " days) AND DATE(current date)");
					daysCount = day;
				}*/

				pstmt = connection.prepareStatement(invChangeSql.toString());
				pstmt.setInt(1, intDistributorID);
				pstmt.setInt(2, intProductID);
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					invChangeCount = rset.getInt("TOTAL_ACTIVATIONS");
				}
				
				DBConnectionManager.releaseResources(pstmt, rset);

				pstmt = connection.prepareStatement(invChangeHistSql.toString());
				pstmt.setInt(1, intDistributorID);
				pstmt.setInt(2, intProductID);
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					invChangeHistCount = rset.getInt("TOTAL_ACTIVATIONS");
				}

				activationCount = invChangeCount + invChangeHistCount;
				//logger.info("invChangeCount:" + invChangeCount + "invChangeHistCount:"+ invChangeHistCount + "minDays:"+ minDays + "intDistributorID:" + intDistributorID + "intProductID:" + intProductID);
				
			} else { //Commercial
				int day = cal.get(Calendar.DAY_OF_MONTH);
				StringBuffer activationSql = new StringBuffer("SELECT count(*) AS TOTAL_ACTIVATIONS FROM DP_STOCK_INVENTORY_ASSIGNED WHERE DISTRIBUTOR_ID = ? AND PRODUCT_ID = ? AND STATUS = 3 AND IS_SCM IN ('Y', 'U') ");			
				if(day < 3 ) {
					//Take previous months activation data
					activationSql = activationSql.append(" AND DATE(ASSIGN_DATE) BETWEEN DATE(current date - 30 days) AND DATE(current date)"); 
					daysCount = 30;
				} else {
					//Take current month
					day = day - 1;
					activationSql = activationSql.append(" AND DATE(ASSIGN_DATE) BETWEEN DATE(current date - " + day + " days) AND DATE(current date)");
					daysCount = day;
				}

				pstmt = connection.prepareStatement(activationSql.toString());
				pstmt.setInt(1, intDistributorID);
				pstmt.setInt(2, intProductID);
				
				rset = pstmt.executeQuery();
	
				while (rset.next()) {
					activationCount = rset.getInt("TOTAL_ACTIVATIONS");
				}
				//logger.info("activationCount:" + activationCount + "minDays:"+ minDays + "intDistributorID:" + intDistributorID + "intProductID:" + intProductID);
			}
			double daysCountDouble = Double.valueOf(daysCount);
			distStockEligibilityMinDouble = (double)((activationCount*minDays)/daysCountDouble);
			distStockEligibilityMin = (int) Math.round(distStockEligibilityMinDouble);;


		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return distStockEligibilityMin;
	}

	public Integer[] getDistStockEligibilityMinMax(Integer intDistributorID, Integer intProductID) throws DAOException {
		Integer[] distStockEligibilityMinMax = new Integer[2];
		distStockEligibilityMinMax[0] = getDistStockEligibilityMin(intDistributorID, intProductID);
		distStockEligibilityMinMax[1] = getDistStockEligibilityMax(intDistributorID, intProductID);
		return distStockEligibilityMinMax;
	}

	public Integer getDistTotalStock(Integer intDistributorID, Integer intProductID) throws DAOException {
		Integer distTotalStock = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;

		try {

			connection = DBConnectionManager.getDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_TOTAL_STOCK_DIST_PROD);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intProductID);
			
			rset = pstmt.executeQuery();

			while (rset.next()) {
				distTotalStock = rset.getInt("TOTAL_STOCK");
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return distTotalStock;
	}

	public Map<Integer, Integer> getDistTotalStock(Integer intDistributorID) throws DAOException {

		Map<Integer, Integer> distTotalStockMap = new HashMap<Integer, Integer>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;

		try {

			connection = DBConnectionManager.getDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_TOTAL_STOCK_DIST);
			pstmt.setInt(1, intDistributorID);
			
			rset = pstmt.executeQuery();
			int prodId = 0;
			int totalStock = 0;
			while (rset.next()) {
				prodId = rset.getInt("PRODUCT_ID");
				totalStock = rset.getInt("TOTAL_STOCK");
				distTotalStockMap.put(prodId, totalStock);				
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return distTotalStockMap;
	}

	public Double getDistTotalStockEffectPrice(Integer intDistributorID, Integer intProductID) throws DAOException {
		Integer distTotalStock = 0;
		Double prodEffectivePrice = 0.0;
		Double totalStockEffectivePrice = 0.0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;

		try {

			connection = DBConnectionManager.getDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_TOTAL_STOCK_DIST_PROD);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intProductID);
			
			rset = pstmt.executeQuery();

			while (rset.next()) {
				distTotalStock = rset.getInt("TOTAL_STOCK");
			}
			prodEffectivePrice = getProductEffectPrice(intProductID);
			totalStockEffectivePrice = distTotalStock * prodEffectivePrice;
			
		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return totalStockEffectivePrice;
	}

	public Map<Integer, Double> getDistTotalStockEffectPrice(Integer intDistributorID) throws DAOException {
		Map<Integer, Double> distTotalStockMap = new HashMap<Integer, Double>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;

		try {

			connection = DBConnectionManager.getDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_TOTAL_STOCK_DIST_SECURITY);
			pstmt.setInt(1, intDistributorID);
			
			rset = pstmt.executeQuery();
			int prodId = 0;
			int totalStock = 0;
			Double prodEffectivePrice = 0.0;
			while (rset.next()) {
				prodId = rset.getInt("PRODUCT_ID");
				//totalStock = rset.getInt("TOTAL_STOCK");
				//prodEffectivePrice = getProductEffectPrice(prodId);
				prodEffectivePrice = Double.valueOf(rset.getString("STOCK_PRICE"));
				
			//	distTotalStockMap.put(prodId, totalStock*prodEffectivePrice);		
				distTotalStockMap.put(prodId, prodEffectivePrice);//neetika
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return distTotalStockMap;
	}
	
	public Map<Integer, Double> getDistTotalStockEffectPriceAll(Integer intDistributorID) throws DAOException {
		Map<Integer, Double> distTotalStockMap = new HashMap<Integer, Double>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;

		try {

			connection = DBConnectionManager.getDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_TOTAL_STOCK_DIST_ALL);
			pstmt.setInt(1, intDistributorID);
			
			rset = pstmt.executeQuery();
		
			Double currentSecurity = 0.0;
			while (rset.next()) {
				currentSecurity = rset.getDouble("CURRENT_SECURITY");			
				
				distTotalStockMap.put(intDistributorID, currentSecurity);				
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return distTotalStockMap;
	}
	

	public Double getProductEffectPrice(Integer intProductID) throws DAOException {
		Double productEffectPrice = 0.0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection =null;

		try {

			connection = DBConnectionManager.getDBConnection();

			String[] prodTypeCatArr = getProductTypeCategory(intProductID, connection);

			pstmt = connection.prepareStatement(SQL_GET_PRODUCT_EFFECT_PRICE);
			pstmt.setInt(1, Integer.parseInt(prodTypeCatArr[0]));
			pstmt.setString(2, prodTypeCatArr[1]);
			
			rset = pstmt.executeQuery();

			while (rset.next()) {
				productEffectPrice = rset.getDouble("PRODUCT_SECURITY");
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return productEffectPrice;
	}
	
	private String[] getProductTypeCategory(Integer intProductID, Connection connection) throws DAOException {
		
		String[] prodTypeCatArr = new String[2];
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = connection.prepareStatement(SQL_GET_PRODUCT_TYPE_CAT);
			pstmt.setInt(1, intProductID);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				prodTypeCatArr[0] = String.valueOf(rset.getInt("PRODUCT_TYPE"));
				prodTypeCatArr[1] = rset.getString("PRODUCT_CATEGORY");

			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return prodTypeCatArr;		
	}
	
	
	public int getMaxDays(Integer intDistributorID, Integer intProductID, Connection connection) throws DAOException {
		
		int maxDays = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = connection.prepareStatement(SQL_MAX_DAYS);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intProductID);	
			
			rset = pstmt.executeQuery();

			while (rset.next()) {
				maxDays = rset.getInt("MAX_DAYS");
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return maxDays;		
	}

	public int getMinDays(Integer intDistributorID, Integer intProductID, Connection connection) throws DAOException {
		
		int minDays = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = connection.prepareStatement(SQL_MIN_DAYS);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intProductID);	
			rset = pstmt.executeQuery();

			while (rset.next()) {
				minDays = rset.getInt("MIN_DAYS");
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return minDays;		
	}

	private int getProductType(Integer intProductID, Connection connection) throws DAOException {
		
		int productType = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = connection.prepareStatement(SQL_GET_PRODUCT_TYPE);
			pstmt.setInt(1, intProductID);	
			rset = pstmt.executeQuery();

			while (rset.next()) {
				productType = rset.getInt("PRODUCT_TYPE");
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return productType;		
	}

	/**
	 * @author Nazim Hussain
	 * 
	 * @param strOLMID
	 * @param con
	 * 
	 */
	public String isValidOLMIDDao(String strOLMID, Connection con) throws DAOException 
	{
		String strReturn = Constants.DP_HRMS_SUCCESS_MSG;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try
		{
			//String sqlOLMIDCheck = "SELECT STATUS FROM BTVL_HRMS_L5D_EMP_V where USER_NAME=? ";
			
			String sqlOLMIDCheck = ResourceReader.getCoreResourceBundleValue("hrms.valid.query");
			logger.info("sqlOLMIDCheck::::::"+sqlOLMIDCheck);
			pstmt = con.prepareStatement(sqlOLMIDCheck);
			pstmt.setString(1, strOLMID);
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				String strOLMStatus = rset.getString(1);
				
				if(!strOLMStatus.equals(Constants.HRMS_ACTIVE_STATUS))
					strReturn = Constants.HRMS_OLM_ID_INACTIVE;
			}
			else
				strReturn = Constants.HRMS_OLM_ID_NOT_EXISTS;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception in validating User OLM ID with HRMS  ::  "+e.getMessage());
			logger.info("Exception in validating User OLM ID with HRMS  ::  "+e);
			throw new DAOException("Error in validating OLM ID from HRMS ::  "+e.getMessage());
		}
		return strReturn;
	}
	//Neetika BFR 2 25-07-2014
	public Double getNewUploadedBalance(Integer intDistributorID,int productId) throws DAOException {
		
		int productType = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Double balance=0.0;
		try {
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement(SQL_GET_PRODUCT_TYPE);
			pstmt.setInt(1, productId);	
			rset = pstmt.executeQuery();

			while (rset.next()) {
				productType = rset.getInt("PRODUCT_TYPE");
			}
			pstmt.clearParameters();
			logger.info("productType "+productType);
			if(productType==0) //SWAP
			{
				//logger.info("query being executed is : "+SQL_GET_ELIGIBILITY);
				pstmt = connection.prepareStatement(ResourceReader.getCoreResourceBundleValue("SQL_GET_ELIGIBILITY"));
				pstmt.setInt(1, intDistributorID);	
				pstmt.setInt(2, intDistributorID);	
				pstmt.setInt(3, intDistributorID);
				
				
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					balance = rset.getDouble("BALANCE");
				}
				pstmt.clearParameters();
			}
			else if(productType==1) //Commercial
			{
				//logger.info("query being executed is : "+SQL_GET_ELIGIBILITY_COMM);
				pstmt = connection.prepareStatement( ResourceReader.getCoreResourceBundleValue("SQL_GET_ELIGIBILITY_COMM"));
				pstmt.setInt(1, intDistributorID);	
				pstmt.setInt(2, intDistributorID);	
				pstmt.setInt(3, intDistributorID);
				pstmt.setInt(4, intDistributorID);	
				pstmt.setInt(5, intDistributorID);	
				pstmt.setInt(6, intDistributorID);
			
				
				rset = pstmt.executeQuery();

				while (rset.next()) {
					balance = rset.getDouble("BALANCE");
				}
			}

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
			
		
		return balance;//if not found in SSD tables, then automzatically value will go as 0.0
	}
	
	
	//Neetika BFR 2 28-07-2014 Made confiurable using Recharge core
	public int getUploadedEligibility(Integer intDistributorID,int productId) throws DAOException {
		int elig=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int productType = 0;
		String prodCat=null;
		int config_id=0;
		String field_name_sf="";
		String field_name_ssd="";
		String generalQuery="";
		try {
		connection = DBConnectionManager.getDBConnection();
		pstmt = connection.prepareStatement(SQL_GET_PRODUCT_TYPE_ID);
		pstmt.setInt(1, productId);	
		rset = pstmt.executeQuery();
		
		
		while (rset.next()) {
			productType = rset.getInt("PRODUCT_TYPE");
			prodCat=rset.getString("PRODUCT_CATEGORY");
			config_id=rset.getInt("ID");
		}
		pstmt.clearParameters();
		
		

		pstmt = connection.prepareStatement(SQL_GET_CONFIGURATION);
		pstmt.setInt(1, config_id);	
		pstmt.setInt(2, productType);	
		pstmt.setString(3, "SF");	
		rset = pstmt.executeQuery();
		
		while (rset.next()) {
			field_name_sf=rset.getString("FIELD_NAME");
		}
		pstmt.clearParameters();
		
		
		pstmt = connection.prepareStatement(SQL_GET_CONFIGURATION);
		pstmt.setInt(1, config_id);	
		pstmt.setInt(2, productType);	
		pstmt.setString(3, "SSD");	
		rset = pstmt.executeQuery();
		
		while (rset.next()) {
			field_name_ssd=rset.getString("FIELD_NAME");
		}
		pstmt.clearParameters();
		/*
		 config_id==20 //HD in prod
		 config_id==50 //SD PLus in prod
		 config_id==40//HD DVR in prod
		 config_id==30 //DVR in prod
		 config_id==10//No	rmal or SD in prod
		 config_id==60 //CAM in prod
		 config_id==70 //product new in prod
		 config_id==80 ///product new in prod
		 config_id==90 //product new in prod
		 */
		logger.info("PRODUCT_CATEGORY "+prodCat+"config_id"+config_id);
		if(productType==1) //COMM
		{
			generalQuery=ResourceReader.getCoreResourceBundleValue("SQL_ELIGIBILITY_COMM_CONFIG");
			generalQuery=generalQuery.replaceAll("field_name_sf", field_name_sf);
			generalQuery=generalQuery.replaceAll("field_name_ssd", field_name_ssd);
			logger.info("Query is "+generalQuery);
			pstmt = connection.prepareStatement(generalQuery);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		else //SWAP
		{
			generalQuery=ResourceReader.getCoreResourceBundleValue("SQL_ELIGIBILITY_SWAP_CONFIG");
			generalQuery=generalQuery.replaceAll("field_name_sf", field_name_sf);
			generalQuery=generalQuery.replaceAll("field_name_ssd", field_name_ssd);
			logger.info("Query is "+generalQuery);
			pstmt = connection.prepareStatement(generalQuery);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		pstmt.clearParameters();
		
		
/*
		
		//Handle till product cat till 9
		//logger.info("productType "+productType);
		if(productType==0) //SWAP
		{
		swapquery="SQL_GET_ELIGIBILITY_"+config_id+"0"; //swap needs a zero

		
		/*
		
		pstmt = connection.prepareStatement(ResourceReader.getCoreResourceBundleValue(swapquery));
		pstmt.setInt(1, intDistributorID);
		pstmt.setInt(2, intDistributorID);
		pstmt.setInt(3, intDistributorID);
		pstmt.setInt(4, intDistributorID);
		pstmt.setInt(5, intDistributorID);
		pstmt.setInt(6, intDistributorID);
		rset = pstmt.executeQuery();
		while(rset.next())
		{
			elig=rset.getInt("ELIG");
		}
		}
		else
		{
		commquery="SQL_GET_ELIGIBILITY_"+config_id; //comm (1-9) */
		/*
		 config_id==20 //HD in prod
		 config_id==50 //SD PLus in prod
		 config_id==40//HD DVR in prod
		 config_id==30 //DVR in prod
		 config_id==10//No	rmal or SD in prod
		 config_id==60 //CAM in prod
		 config_id==70 //product new in prod
		 config_id==80 ///product new in prod
		 config_id==90 //product new in prod
		 */
		/*
		logger.info("PRODUCT_CATEGORY "+prodCat+"config_id"+config_id+"commquery"+commquery);
		pstmt = connection.prepareStatement(ResourceReader.getCoreResourceBundleValue(commquery));
		pstmt.setInt(1, intDistributorID);
		pstmt.setInt(2, intDistributorID);
		pstmt.setInt(3, intDistributorID);
		pstmt.setInt(4, intDistributorID);
		pstmt.setInt(5, intDistributorID);
		pstmt.setInt(6, intDistributorID);
		rset = pstmt.executeQuery();
		while(rset.next())
		{
			elig=rset.getInt("ELIG");
		}
		}
		
		*/
	
		//HD
		/*if(productType==0 && prodCat.equalsIgnoreCase(Constants.HD))
		{
			logger.info(SQL_GET_ELIGIBILITY_SHD);
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_SHD);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		// SD PLUS
		else if(productType==0 && prodCat.equalsIgnoreCase(Constants.SD_Plus))
		{
			logger.info(SQL_GET_ELIGIBILITY_SSDPLUS);
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_SSDPLUS);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		// HD DVR
		else if(productType==0 && prodCat.equalsIgnoreCase(Constants.HD_DVR))
		{
			logger.info(SQL_GET_ELIGIBILITY_SHDDVR);
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_SHDDVR);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		// DVR or PVR
		else if(productType==0 && prodCat.equalsIgnoreCase(Constants.DVR))
		{
			logger.info(SQL_GET_ELIGIBILITY_SPVR);
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_SPVR);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		// Normal or SD
		else if(productType==0 && prodCat.equalsIgnoreCase(Constants.NORMAL))
		{
			logger.info(SQL_GET_ELIGIBILITY_SNORMAL);
		
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_SNORMAL);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		//commercial 
		else if(productType==1 && prodCat.equalsIgnoreCase(Constants.HD))
		{
			//logger.info(SQL_GET_ELIGIBILITY_CHD);
			
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_CHD);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		
		else if(productType==1 && prodCat.equalsIgnoreCase(Constants.SD_Plus))
		{
			//logger.info(SQL_GET_ELIGIBILITY_CSDPLUS);
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_CSDPLUS);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}
		else if(productType==1 && prodCat.equalsIgnoreCase(Constants.HD_DVR))
		{
			elig=0;
		}
		else if(productType==1 && prodCat.equalsIgnoreCase(Constants.DVR))
		{
			elig=0;
		}
		else if(productType==1 && prodCat.equalsIgnoreCase(Constants.NORMAL))
		{
			logger.info(SQL_GET_ELIGIBILITY_CNORMAL);
			
			pstmt = connection.prepareStatement(SQL_GET_ELIGIBILITY_CNORMAL);
			pstmt.setInt(1, intDistributorID);
			pstmt.setInt(2, intDistributorID);
			pstmt.setInt(3, intDistributorID);
			pstmt.setInt(4, intDistributorID);
			pstmt.setInt(5, intDistributorID);
			pstmt.setInt(6, intDistributorID);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				elig=rset.getInt("ELIG");
			}
		}*/
		pstmt.clearParameters();
		}
		catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
			
		return elig;
	}
	
}