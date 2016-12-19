/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.AccountAccessLevel;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
/**
 * Interface definition for account management .
 * 
 * @author bhanu
 * 
 */
public class AccountDaoRdbms extends BaseDaoRdbms implements AccountDao {

	/** Logger for class AccountDaoRdbms.* */

	protected static Logger logger = Logger.getLogger(AccountDaoRdbms.class
			.getName());

	/**
	 * query to Find whether the Account Is Active or not for destination
	 * account
	 */
	protected final static String SQL_SELECT_ISACTIVE_KEY ="SQL_SELECT_ISACTIVE";
	protected static final String SQL_SELECT_ISACTIVE = "SELECT LOGIN.LOGIN_ID FROM VR_LOGIN_MASTER LOGIN,VR_ACCOUNT_DETAILS ACCOUNT WHERE LOGIN.LOGIN_ID=ACCOUNT.ACCOUNT_ID AND ACCOUNT.MOBILE_NUMBER=?";

	/** query to select access level based on the groupId */
	protected final static String SQL_SELECT_ACCESS_LEVEL_KEY ="SQL_SELECT_ACCESS_LEVEL";
	protected static final String SQL_SELECT_ACCESS_LEVEL = "select a.GROUP_ID,a.GROUP_NAME,a.DESCRIPTION from VR_GROUP_DETAILS  a , VR_GROUP_ACCESS_LEVEL_MAPPING  b where a.GROUP_ID=b.GROUP_ACCESS_LEVEL_ID and b.GROUP_ID=?";

	/** query to update Avialable & Operating balance for the source */
	protected final static String SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY = "SQL_UPDATE_ACCOUNT_SOURCE_BALANCE";
	protected static final String SQL_UPDATE_ACCOUNT_SOURCE_BALANCE = "UPDATE VR_ACCOUNT_BALANCE SET AVAILABLE_BALANCE=AVAILABLE_BALANCE + ?,OPERATING_BALANCE=OPERATING_BALANCE + ? WHERE ACCOUNT_ID=? AND OPERATING_BALANCE>=?";

	/** query to update Avialable & Operating balance for the destination */
	protected final static String SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE_KEY = "SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE"; 
	protected static final String SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE = "UPDATE VR_ACCOUNT_BALANCE SET AVAILABLE_BALANCE=AVAILABLE_BALANCE + ?,OPERATING_BALANCE=OPERATING_BALANCE + ? WHERE ACCOUNT_ID=? ";

	/** query to Update the Available balance */
	protected final static String SQL_UPDATE_AVAILABLE_BALANCE_KEY = "SQL_UPDATE_AVAILABLE_BALANCE";
	protected static final String SQL_UPDATE_AVAILABLE_BALANCE = "UPDATE VR_ACCOUNT_BALANCE SET AVAILABLE_BALANCE= AVAILABLE_BALANCE + ? WHERE ACCOUNT_ID= ?";

	protected final static String SQL_UPDATE_OPERATING_BALANCE_KEY = "SQL_UPDATE_OPERATING_BALANCE";
	protected static final String SQL_UPDATE_OPERATING_BALANCE = "UPDATE VR_ACCOUNT_BALANCE SET OPERATING_BALANCE= OPERATING_BALANCE + ? WHERE ACCOUNT_ID= ?  ";

	protected final static String SQL_CHECK_UPDATE_OPERATING_BALANCE_KEY = "SQL_CHECK_UPDATE_OPERATING_BALANCE";
	protected static final String SQL_CHECK_UPDATE_OPERATING_BALANCE = "UPDATE VR_ACCOUNT_BALANCE SET OPERATING_BALANCE= OPERATING_BALANCE + ? WHERE ACCOUNT_ID= ? AND OPERATING_BALANCE >= ? ";

	/** query to find all the child accounts for the source account */
	protected final static String SQL_SELECT_CHILD_ACCOUNT_KEY = "SQL_SELECT_CHILD_ACCOUNT";
	protected static final String SQL_SELECT_CHILD_ACCOUNT = "SELECT VR_ACCOUNT_DETAILS.ACCOUNT_NAME,VR_ACCOUNT_DETAILS.ACCOUNT_ID,VR_ACCOUNT_DETAILS.MOBILE_NUMBER,VR_LOGIN_MASTER.LOGIN_NAME FROM VR_ACCOUNT_DETAILS,VR_LOGIN_MASTER WHERE PARENT_ACCOUNT=? AND  VR_LOGIN_MASTER.LOGIN_ID=VR_ACCOUNT_DETAILS.ACCOUNT_ID AND  VR_LOGIN_MASTER.STATUS='"
			+ Constants.ACTIVE_STATUS + "'";

	/**
	 * query to chek whether the Receiving Account is Child of Tranferrer
	 * Account or not
	 */
	protected final static String SQL_SELECT_ISCHILD_KEY = "SQL_SELECT_ISCHILD";
	protected static final String SQL_SELECT_ISCHILD = "SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=? AND PARENT_ACCOUNT=?";

	/** query to find the balance of an Account */
	protected final static String SQL_SELECT_BALANCE_KEY = "SQL_SELECT_BALANCE";
	protected static final String SQL_SELECT_BALANCE = "SELECT OPENING_BALANCE,RATE,THRESHOLD,OPERATING_BALANCE,AVAILABLE_BALANCE FROM VR_ACCOUNT_BALANCE WHERE ACCOUNT_ID=?";

	protected final static String SQL_INSERT_DIST_ACCOUNT_KEY = "SQL_INSERT_DIST_ACCOUNT";
	protected static final String SQL_INSERT_DIST_ACCOUNT = "	INSERT INTO VR_ACCOUNT_DETAILS (ACCOUNT_ID,MOBILE_NUMBER,SIM_NUMBER, ACCOUNT_NAME,ACCOUNT_ADDRESS, EMAIL,CATEGORY, CIRCLE_ID,OPENING_DT, CREATED_DT,CREATED_BY, UPDATED_DT, UPDATED_BY,ACCOUNT_LEVEL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected final static String SQL_INSERT_ACC_DETAIL_KEY = "SQL_INSERT_ACC_DETAIL";
	protected static final String SQL_INSERT_ACC_DETAIL = " INSERT INTO VR_ACCOUNT_DETAILS (ACCOUNT_ID,MOBILE_NUMBER,SIM_NUMBER, ACCOUNT_NAME,ACCOUNT_ADDRESS,EMAIL,CATEGORY, CIRCLE_ID,PARENT_ACCOUNT,ROOT_LEVEL_ID,OPENING_DT, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY,ACCOUNT_LEVEL,BILLABLE_ACC_ID,STATE_ID,CITY_ID,PIN_NUMBER, BILLABLE_ACCOUNT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected final static String SQL_INSERT_ACC_BALANCE_KEY = "SQL_INSERT_ACC_BALANCE";
	protected static final String SQL_INSERT_ACC_BALANCE = " INSERT INTO VR_ACCOUNT_BALANCE (ACCOUNT_ID,OPENING_BALANCE,RATE, THRESHOLD, OPERATING_BALANCE,AVAILABLE_BALANCE) VALUES (?,?,?,?,?,?)";

	protected final static String SQL_SELECT_MOBILENO_KEY = "SQL_SELECT_MOBILENO";
	protected static final String SQL_SELECT_MOBILENO = "SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE VR_ACCOUNT_DETAILS.ACCOUNT_ID=?";

	protected final static String SQL_UPDATE_ACC_DETAIL_KEY ="SQL_UPDATE_ACC_DETAIL";
	protected static final String SQL_UPDATE_ACC_DETAIL = " UPDATE VR_ACCOUNT_DETAILS  SET SIM_NUMBER =?,MOBILE_NUMBER =?,ACCOUNT_NAME = ?,ACCOUNT_ADDRESS = ?,EMAIL = ?,CATEGORY = ?,UPDATED_DT =?, UPDATED_BY = ?,BILLABLE_ACC_ID=?,STATE_ID=?,CITY_ID=?, BILLABLE_ACCOUNT =?, PIN_NUMBER=?	WHERE ACCOUNT_ID=? ";

	protected final static String SQL_UPDATE_ACC_BALANCE_KEY = "SQL_UPDATE_ACC_BALANCE";
	protected static final String SQL_UPDATE_ACC_BALANCE = " UPDATE  VR_ACCOUNT_BALANCE SET RATE =?,THRESHOLD=? WHERE ACCOUNT_ID=? ";

	protected final static String SQL_SELECT_ACCOUNT_ID_KEY = "SQL_SELECT_ACCOUNT_ID";
	protected static final String SQL_SELECT_ACCOUNT_ID = " SELECT  LOGIN_ID FROM VR_LOGIN_MASTER WHERE  LOGIN_NAME=? AND STATUS='"
			+ Constants.ACTIVE_STATUS + "'";

	// To check if parent and child are from same circle
	protected final static String SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE_KEY = "SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE";
	protected static final String SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE = " SELECT  LOGIN_ID FROM VR_LOGIN_MASTER , VR_ACCOUNT_DETAILS WHERE  LOGIN_NAME = ? AND STATUS='"
			+ Constants.ACTIVE_STATUS
			+ "' AND LOGIN_ID = ACCOUNT_ID AND CIRCLE_ID = ?";

	protected final static String SQL_SELECT_ACCOUNT_CODE_KEY = "SQL_SELECT_ACCOUNT_CODE";
	protected static final String SQL_SELECT_ACCOUNT_CODE = " SELECT  LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID=? "; // AND

	// STATUS='"+Constants.ACTIVE_STATUS+"'";
	protected final static String SQL_SELECT_ACCOUNT_WITH_CIRCLEID_KEY = "SQL_SELECT_ACCOUNT_WITH_CIRCLEID";
	protected static final String SQL_SELECT_ACCOUNT_WITH_CIRCLEID = " SELECT ab.RATE,ab.OPERATING_BALANCE,ab.AVAILABLE_BALANCE, ad.ACCOUNT_CODE,  ad.ACCOUNT_NAME,ad.MOBILE_NUMBER,ad.ACCOUNT_ID FROM  VR_ACCOUNT_DETAILS ad INNER  join VR_ACCOUNT_BALANCE ab on ad.ACCOUNT_ID = ab.ACCOUNT_ID  WHERE  ad.CIRCLE_ID=? AND ACCOUNT_LEVEL='D' AND ad.STATUS='"
			+ Constants.ACTIVE_STATUS + "'";

	protected final static String SQL_SELECT_ACCOUNT_RATE_KEY = "SQL_SELECT_ACCOUNT_RATE";
	protected static final String SQL_SELECT_ACCOUNT_RATE = " SELECT RATE FROM VR_ACCOUNT_BALANCE,VR_LOGIN_MASTER WHERE ACCOUNT_ID=LOGIN_ID AND STATUS='"
			+ Constants.ACTIVE_STATUS + "' AND ACCOUNT_ID=?";

	protected final static String SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY = "SQL_UPDATE_ACCOUNT_AVAILBALANCE";
	protected static final String SQL_UPDATE_ACCOUNT_AVAILBALANCE = " UPDATE VR_ACCOUNT_BALANCE SET AVAILABLE_BALANCE=(AVAILABLE_BALANCE+?),OPERATING_BALANCE=(OPERATING_BALANCE+?) WHERE ACCOUNT_ID=? ";

	protected final static String SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY = "SQL_UPDATE_VR_ACCOUNT_DETAILS";
	protected static final String SQL_UPDATE_VR_ACCOUNT_DETAILS = " UPDATE VR_ACCOUNT_DETAILS SET UPDATED_BY=?,UPDATED_DT=? WHERE ACCOUNT_ID=? ";

	protected final static String SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY = "SQL_SELECT_ALL_ACC_WHERE_CLAUSE";
	protected static final String SQL_SELECT_ALL_ACC_WHERE_CLAUSE = " FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2, VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY ";

	protected final static String SQL_SELECT_ALL_ACC_KEY = "SQL_SELECT_ALL_ACC";
	protected static final String SQL_SELECT_ALL_ACC = " SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME,LOGIN.LOGIN_ATTEMPTED,L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE, BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, DETAILS.CATEGORY,LOGIN.STATUS, BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, BALANCE.RATE, BALANCE.THRESHOLD, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME "
			+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;

	protected final static String SQL_SELECT_ALL_ACC_COUNT_KEY = "SQL_SELECT_ALL_ACC_COUNT";
	protected static final String SQL_SELECT_ALL_ACC_COUNT = "SELECT COUNT(DETAILS.ACCOUNT_ID) "
			+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;

	// public static final String SQL_SELECT_ALL_CHILD_ACC = " SELECT
	// GROUPD.GROUP_NAME,LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent,
	// LOGIN2.LOGIN_NAME as CREATEDBYNAME , DETAILS.PARENT_ACCOUNT
	// PCODE,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,
	// DETAILS.CREATED_DT,
	// DETAILS.CATEGORY,LOGIN.STATUS,BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE,
	// BALANCE.RATE, BALANCE.THRESHOLD, REGION.REGION_NAME FROM
	// VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON
	// DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER
	// LOGIN,VR_LOGIN_MASTER LOGIN2 , VR_ACCOUNT_BALANCE
	// BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER
	// REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID =
	// BALANCE.ACCOUNT_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND
	// LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID
	// AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY ";

	//public static final String SQL_SELECT_ACC = "SELECT DETAILS.BILLABLE_ACC_ID,LOGIN.M_PASSWORD,LOGIN.LOGIN_NAME,LOGIN.M_PASSWORD,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,BALANCE.Threshold,BALANCE.RATE,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CATEGORY,DETAILS.PARENT_ACCOUNT,LOGIN.STATUS,BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, DETAILS.CIRCLE_ID, LOGIN.GROUP_ID FROM VR_LOGIN_MASTER LOGIN,VR_ACCOUNT_DETAILS DETAILS,VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE WHERE  LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND  DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID AND DETAILS.CIRCLE_ID=CIRCLE.CIRCLE_ID AND DETAILS.ACCOUNT_ID=? ";

	
	protected final static String SQL_SELECT_ACC_MOBILE_KEY = "SQL_SELECT_ACC_MOBILE";
	protected static final String SQL_SELECT_ACC_MOBILE = "SELECT LOGIN.LOGIN_NAME,LOGIN.M_PASSWORD,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,BALANCE.Threshold,BALANCE.RATE,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CATEGORY,DETAILS.PARENT_ACCOUNT,LOGIN.STATUS,BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, DETAILS.CIRCLE_ID, LOGIN.GROUP_ID ,DETAILS.BILLABLE_ACC_ID,DETAILS.STATE_ID,DETAILS.CITY_ID,DETAILS.BILLABLE_ACCOUNT,DETAILS.PIN_NUMBER,DETAILS.BILLABLE_ACC_ID,DETAILS.ROOT_LEVEL_ID,DETAILS.ACCOUNT_LEVEL,DETAILS.CIRCLE_ID FROM VR_LOGIN_MASTER LOGIN,VR_ACCOUNT_DETAILS DETAILS,VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID	 AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID AND	 DETAILS.CIRCLE_ID=CIRCLE.CIRCLE_ID AND DETAILS.MOBILE_NUMBER=? ";
	
	protected final static String SQL_SELECT_ACC_KEY = "SQL_SELECT_ACC";
	//protected static final String SQL_SELECT_ACC = "SELECT CIRCLE.CIRCLE_CODE as CIRCLE_CODE , LOGIN.PASSWORD,REGION.REGION_NAME,LOGIN.LOGIN_NAME,LOGIN.M_PASSWORD,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,BALANCE.Threshold,BALANCE.RATE,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CATEGORY,DETAILS.PARENT_ACCOUNT,LOGIN.STATUS,BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, DETAILS.CIRCLE_ID, LOGIN.GROUP_ID ,DETAILS.BILLABLE_ACC_ID,DETAILS.STATE_ID,DETAILS.CITY_ID,DETAILS.BILLABLE_ACCOUNT,DETAILS.PIN_NUMBER,DETAILS.BILLABLE_ACC_ID,DETAILS.ROOT_LEVEL_ID,DETAILS.ACCOUNT_LEVEL,LEVEL.LEVEL_NAME,DETAILS.CIRCLE_ID,DETAILS.CREATED_DT,DETAILS.ACCOUNT_ADDRESS FROM VR_REGION_MASTER REGION, VR_LOGIN_MASTER LOGIN,VR_ACCOUNT_DETAILS DETAILS,VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_ACCOUNT_LEVEL_MASTER LEVEL WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID	 AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID AND DETAILS.CIRCLE_ID=CIRCLE.CIRCLE_ID AND LEVEL.LEVEL_ID = DETAILS.ACCOUNT_LEVEL AND CIRCLE.REGION_ID = REGION.REGION_ID AND DETAILS.ACCOUNT_ID=? ";
	// Removed Balance Details from Above Tables
	protected static final String SQL_SELECT_ACC = "SELECT CIRCLE.CIRCLE_CODE as CIRCLE_CODE , LOGIN.PASSWORD,REGION.REGION_NAME,LOGIN.LOGIN_NAME,LOGIN.M_PASSWORD,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CATEGORY,DETAILS.PARENT_ACCOUNT,LOGIN.STATUS,DETAILS.CIRCLE_ID, LOGIN.GROUP_ID ,DETAILS.BILLABLE_ACC_ID,DETAILS.STATE_ID,DETAILS.CITY_ID,DETAILS.BILLABLE_ACCOUNT,DETAILS.PIN_NUMBER,DETAILS.BILLABLE_ACC_ID,DETAILS.ROOT_LEVEL_ID,DETAILS.ACCOUNT_LEVEL,LEVEL.LEVEL_NAME,DETAILS.CIRCLE_ID,DETAILS.CREATED_DT,DETAILS.ACCOUNT_ADDRESS	FROM VR_REGION_MASTER REGION, VR_LOGIN_MASTER LOGIN,VR_ACCOUNT_DETAILS DETAILS,VR_CIRCLE_MASTER CIRCLE,VR_ACCOUNT_LEVEL_MASTER LEVEL WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.CIRCLE_ID=CIRCLE.CIRCLE_ID AND LEVEL.LEVEL_ID = DETAILS.ACCOUNT_LEVEL AND CIRCLE.REGION_ID = REGION.REGION_ID AND DETAILS.ACCOUNT_ID=?";
  
	
	protected final static String SQL_SELECT_ACC_BY_MOBILE_KEY = "SQL_SELECT_ACC_BY_MOBILE";
	protected static final String SQL_SELECT_ACC_BY_MOBILE = "SELECT BALANCE.RATE,DETAILS.ACCOUNT_ID,DETAILS.PARENT_ACCOUNT,DETAILS.BILLABLE_ACC_ID,LOGIN.STATUS FROM VR_ACCOUNT_BALANCE BALANCE, VR_ACCOUNT_DETAILS DETAILS,VR_LOGIN_MASTER LOGIN WHERE DETAILS.ACCOUNT_ID=LOGIN.LOGIN_ID AND BALANCE.ACCOUNT_ID=LOGIN.LOGIN_ID AND DETAILS.MOBILE_NUMBER=?";

	protected final static String SQL_UPDATE_ACC_OERATINGBALANCE_KEY = "SQL_UPDATE_ACC_OERATINGBALANCE";
	protected static final String SQL_UPDATE_ACC_OERATINGBALANCE = " UPDATE VR_ACCOUNT_BALANCE  SET OPERATING_BALANCE=(OPERATING_BALANCE-?),AVAILABLE_BALANCE=(AVAILABLE_BALANCE-?) WHERE (OPERATING_BALANCE-?)>=0 AND ACCOUNT_ID=? ";

	protected final static String SQL_CIRCLE_SELECT_WITH_ACCOUNTID_KEY = "SQL_CIRCLE_SELECT_WITH_ACCOUNTID";
	protected static final String SQL_CIRCLE_SELECT_WITH_ACCOUNTID = "SELECT CIRCLE_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=?";

	protected final static String SQL_SELECT_DISTRIBUTOR_KEY = "SQL_SELECT_DISTRIBUTOR";
	protected static final String SQL_SELECT_DISTRIBUTOR = " SELECT ab.RATE, ab.OPERATING_BALANCE, ab.AVAILABLE_BALANCE,  lm.LOGIN_NAME, ad.ACCOUNT_NAME,ad.MOBILE_NUMBER,ad.ACCOUNT_ID   FROM  VR_ACCOUNT_DETAILS ad,VR_ACCOUNT_BALANCE ab,VR_LOGIN_MASTER lm    WHERE  ad.CIRCLE_ID=? AND PARENT_ACCOUNT is NULL AND   ad.ACCOUNT_ID = ab.ACCOUNT_ID AND ad.ACCOUNT_ID=lm.LOGIN_ID   AND lm.STATUS='"
			+ Constants.ACTIVE_STATUS + "'";
	
	protected final static String SQL_SELECT_COMMISSION_ID_KEY = "SQL_SELECT_COMMISSION_ID";
	protected static final String SQL_SELECT_COMMISSION_ID = "SELECT COMMISSIONING_ID FROM VR_COMMISS_GRP_MAPPING WHERE ACCOUNT_ID=?";

	protected final static String SQL_SELECT_PARENT_ACCOUNTID_KEY = "SQL_SELECT_PARENT_ACCOUNTID";
	protected static final String SQL_SELECT_PARENT_ACCOUNTID = " SELECT LOGIN_ID FROM VR_LOGIN_MASTER,VR_ACCOUNT_DETAILS WHERE LOGIN_NAME=? AND LOGIN_ID=ACCOUNT_ID AND  CIRCLE_ID=? AND STATUS ='"
			+ Constants.ACTIVE_STATUS + "'";

	protected final static String SQL_IS_MOBILENO_EXIST_KEY = "SQL_IS_MOBILENO_EXIST";
	protected static final String SQL_IS_MOBILENO_EXIST = " SELECT ACCOUNT_ID FROM VR_LOGIN_MASTER,VR_ACCOUNT_DETAILS WHERE MOBILE_NUMBER=? AND LOGIN_ID=ACCOUNT_ID AND STATUS='A' ";

	protected final static String SQL_OPENING_DATE_KEY = "SQL_OPENING_DATE";
	protected static final String SQL_OPENING_DATE = " SELECT OPENING_DT FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? ";

	// Query Used for Parent Account Pop-Up Page
	protected final static String SQL_SELECT_FOR_PARENT_ACC_KEY = "SQL_SELECT_FOR_PARENT_ACC";
	protected static final String SQL_SELECT_FOR_PARENT_ACC = " SELECT GROUPD.GROUP_NAME,LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CATEGORY,LOGIN.STATUS,BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER LOGIN, VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD  WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID  AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND DETAILS.CIRCLE_ID = ? AND LOGIN.STATUS = '"
			+ Constants.ACTIVE_STATUS
			+ "' AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS START WITH ACCOUNT_ID = ? CONNECT BY PRIOR ACCOUNT_ID = PARENT_ACCOUNT ) ";

	protected final static String SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY = "SQL_SELECT_FOR_PARENT_ACC_COUNT";
	protected static final String SQL_SELECT_FOR_PARENT_ACC_COUNT = " SELECT COUNT(*) FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER LOGIN, VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD  WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID  AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND DETAILS.CIRCLE_ID = ? AND LOGIN.STATUS = '"
			+ Constants.ACTIVE_STATUS
			+ "' AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS START WITH ACCOUNT_ID = ? CONNECT BY PRIOR ACCOUNT_ID = PARENT_ACCOUNT ) ";

	protected final static String SQL_SELECT_ACCOUNT_LEVEL_KEY = "SQL_SELECT_ACCOUNT_LEVEL";
	protected static final String SQL_SELECT_ACCOUNT_LEVEL = " SELECT LEVEL_ID,LEVEL_NAME,ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level1 "
			+ "  WHERE level1.ACC_LEVEL>(SELECT ACC_LEVEL "
			+ "  FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE  level2.level_id=(SELECT ACCOUNT_LEVEL FROM VR_ACCOUNT_DETAILS "
			+ "  WHERE ACCOUNT_ID=?  ) ) AND level1.HIERARCHY_ID in (SELECT HIERARCHY_ID FROM   VR_ACCOUNT_LEVEL_MASTER  WHERE "
			+ "	LEVEL_ID=(SELECT ACCOUNT_LEVEL  FROM	 VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID=?))order by  level1.HIERARCHY_ID ";

	protected final static String SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN_KEY = "SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN";
	protected static final String SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN = " SELECT  LEVEL_ID,LEVEL_NAME,ACC_LEVEL  FROM VR_ACCOUNT_LEVEL_MASTER order by HIERARCHY_ID  ";

	protected final static String SQL_SELECT_DEFAULT_GROUP_ID_KEY = "SQL_SELECT_DEFAULT_GROUP_ID";
	protected static final String SQL_SELECT_DEFAULT_GROUP_ID = " SELECT GROUP_ID  FROM VR_ACCOUNT_LEVEL_MASTER 	WHERE LEVEL_ID=? ";

	protected final static String SQL_SELECT_ALL_STATES_KEY = "SQL_SELECT_ALL_STATES";
	protected static final String SQL_SELECT_ALL_STATES = " SELECT STATE_ID ,STATE_NAME FROM VR_STATES_MASTER WHERE STATUS='Y' ";

	protected final static String SQL_SELECT_CITES_KEY = "SQL_SELECT_CITES";
	
	protected final static String SQL_SELECT_STATES_KEY = "SQL_SELECT_STATES";
	
	protected static final String SQL_SELECT_CITES = " SELECT CITY_ID ,CITY_NAME FROM VR_CITY_MASTER WHERE STATE_ID=? ";
	
	protected static final String SQL_SELECT_STATES = " SELECT STATE_ID, STATE_NAME FROM DP_STATES_MASTER SM, VR_CIRCLE_MASTER CM WHERE CM.CIRCLE_CODE = SM.CIRCLE_CODE AND CM.CIRCLE_ID = ? ";

	protected final static String SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID_KEY = "SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID";
	protected static final String SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID = " SELECT  ROOT_LEVEL_ID  FROM VR_ACCOUNT_DETAILS WHERE  ACCOUNT_ID=? ";

	protected final static String SQL_SELECT_BILLABLE_ACCOUNT_ID_KEY = "SQL_SELECT_BILLABLE_ACCOUNT_ID";
	protected static final String SQL_SELECT_BILLABLE_ACCOUNT_ID = " SELECT  COUNT(BILLABLE_ACC_ID) FROM VR_LOGIN_MASTER ,VR_ACCOUNT_DETAILS WHERE  STATUS='"
			+ Constants.ACTIVE_STATUS
			+ "' AND LOGIN_ID = ACCOUNT_ID AND BILLABLE_ACC_ID = ?";
	
    protected final static String SQL_SELECT_CIRCLE_NAME_KEY = "SQL_SELECT_CIRCLE_NAME";
	protected final static String SQL_SELECT_CIRCLE_NAME = "SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER WHERE STATUS = '"
			+ Constants.ACTIVE_STATUS + "' AND CIRCLE_ID = ?";
	
	// TODO :Anuradha Remove hard coding for circle name "NATIONAL"
	protected final static String SQL_SELECT_ALL_ACC_NATIONAL_KEY = "SQL_SELECT_ALL_ACC_NATIONAL";
	protected final static String SQL_SELECT_ALL_ACC_NATIONAL = "AND DETAILS.ROOT_LEVEL_ID IN( SELECT ACCOUNT.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS ACCOUNT,VR_CIRCLE_MASTER CIRCLE WHERE ACCOUNT.CIRCLE_ID=CIRCLE.CIRCLE_ID AND UPPER(CIRCLE.CIRCLE_NAME)='NATIONAL')";
	
	protected final static String SQL_SELECT_ACC_NATIONAL_KEY = "SQL_SELECT_ACC_NATIONAL";
	protected final static String SQL_SELECT_ACC_NATIONAL = "AND DETAILS.ROOT_LEVEL_ID IN (SELECT ACCOUNT.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS ACCOUNT,VR_CIRCLE_MASTER CIRCLE WHERE ACCOUNT.CIRCLE_ID=CIRCLE.CIRCLE_ID AND UPPER(CIRCLE.CIRCLE_NAME)='NATIONAL')	";
	

//	protected Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public AccountDaoRdbms(Connection conn) {
		super(conn);
		queryMap.put(SQL_SELECT_ISACTIVE_KEY,SQL_SELECT_ISACTIVE);
		queryMap.put(SQL_SELECT_ACCESS_LEVEL_KEY , SQL_SELECT_ACCESS_LEVEL);
		queryMap.put(SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY , SQL_UPDATE_ACCOUNT_SOURCE_BALANCE);
		queryMap.put(SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE_KEY,SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE);
		queryMap.put(SQL_UPDATE_AVAILABLE_BALANCE_KEY,SQL_UPDATE_AVAILABLE_BALANCE);
		queryMap.put(SQL_UPDATE_OPERATING_BALANCE_KEY,SQL_UPDATE_OPERATING_BALANCE);
		queryMap.put(SQL_CHECK_UPDATE_OPERATING_BALANCE_KEY,SQL_CHECK_UPDATE_OPERATING_BALANCE);
		queryMap.put(SQL_SELECT_CHILD_ACCOUNT_KEY,SQL_SELECT_CHILD_ACCOUNT);
		queryMap.put(SQL_SELECT_ISCHILD_KEY,SQL_SELECT_ISCHILD);
		queryMap.put(SQL_SELECT_BALANCE_KEY,SQL_SELECT_BALANCE);
		queryMap.put(SQL_INSERT_DIST_ACCOUNT_KEY,SQL_INSERT_DIST_ACCOUNT);
		queryMap.put(SQL_INSERT_ACC_DETAIL_KEY,SQL_INSERT_ACC_DETAIL);
		queryMap.put(SQL_INSERT_ACC_BALANCE_KEY,SQL_INSERT_ACC_BALANCE);
		queryMap.put(SQL_SELECT_MOBILENO_KEY,SQL_SELECT_MOBILENO);
		queryMap.put(SQL_UPDATE_ACC_DETAIL_KEY,SQL_UPDATE_ACC_DETAIL);
		queryMap.put(SQL_UPDATE_ACC_BALANCE_KEY,SQL_UPDATE_ACC_BALANCE);
		queryMap.put(SQL_SELECT_ACCOUNT_ID_KEY,SQL_SELECT_ACCOUNT_ID);
		queryMap.put(SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE_KEY,SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE);
		queryMap.put(SQL_SELECT_ACCOUNT_CODE_KEY,SQL_SELECT_ACCOUNT_CODE);
		queryMap.put(SQL_SELECT_ACCOUNT_WITH_CIRCLEID_KEY,SQL_SELECT_ACCOUNT_WITH_CIRCLEID);
		queryMap.put(SQL_SELECT_ACCOUNT_RATE_KEY,SQL_SELECT_ACCOUNT_RATE);
		queryMap.put(SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY,SQL_UPDATE_ACCOUNT_AVAILBALANCE);
		queryMap.put(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY,SQL_UPDATE_VR_ACCOUNT_DETAILS);
		queryMap.put(SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY,SQL_SELECT_ALL_ACC_WHERE_CLAUSE);
		queryMap.put(SQL_SELECT_ALL_ACC_KEY,SQL_SELECT_ALL_ACC);
		queryMap.put(SQL_SELECT_ALL_ACC_COUNT_KEY,SQL_SELECT_ALL_ACC_COUNT);
		queryMap.put(SQL_SELECT_ACC_KEY,SQL_SELECT_ACC);
		queryMap.put(SQL_SELECT_ACC_BY_MOBILE_KEY,SQL_SELECT_ACC_BY_MOBILE);
		queryMap.put(SQL_UPDATE_ACC_OERATINGBALANCE_KEY,SQL_UPDATE_ACC_OERATINGBALANCE);
		queryMap.put(SQL_CIRCLE_SELECT_WITH_ACCOUNTID_KEY,SQL_CIRCLE_SELECT_WITH_ACCOUNTID);
		queryMap.put(SQL_SELECT_COMMISSION_ID_KEY,SQL_SELECT_COMMISSION_ID);
		queryMap.put(SQL_SELECT_DISTRIBUTOR_KEY,SQL_SELECT_DISTRIBUTOR);
		queryMap.put(SQL_SELECT_PARENT_ACCOUNTID_KEY,SQL_SELECT_PARENT_ACCOUNTID);
		queryMap.put(SQL_IS_MOBILENO_EXIST_KEY,SQL_IS_MOBILENO_EXIST);
		queryMap.put(SQL_OPENING_DATE_KEY,SQL_OPENING_DATE);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_KEY,SQL_SELECT_FOR_PARENT_ACC);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY,SQL_SELECT_FOR_PARENT_ACC_COUNT);
		queryMap.put(SQL_SELECT_ACCOUNT_LEVEL_KEY,SQL_SELECT_ACCOUNT_LEVEL);
		queryMap.put(SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN_KEY,SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN);
		queryMap.put(SQL_SELECT_DEFAULT_GROUP_ID_KEY,SQL_SELECT_DEFAULT_GROUP_ID);
		queryMap.put(SQL_SELECT_ALL_STATES_KEY,SQL_SELECT_ALL_STATES);
		queryMap.put(SQL_SELECT_CITES_KEY,SQL_SELECT_CITES);
		queryMap.put(SQL_SELECT_STATES_KEY,SQL_SELECT_STATES);
		queryMap.put(SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID_KEY,SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID);
		queryMap.put(SQL_SELECT_BILLABLE_ACCOUNT_ID_KEY,SQL_SELECT_BILLABLE_ACCOUNT_ID);
		queryMap.put(SQL_SELECT_ACC_MOBILE_KEY,SQL_SELECT_ACC_MOBILE);
		queryMap.put(SQL_SELECT_CIRCLE_NAME_KEY ,SQL_SELECT_CIRCLE_NAME);
		queryMap.put(SQL_SELECT_ALL_ACC_NATIONAL_KEY ,SQL_SELECT_ALL_ACC_NATIONAL);
		queryMap.put(SQL_SELECT_ACC_NATIONAL_KEY ,SQL_SELECT_ACC_NATIONAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#updateAccountBalance(com.ibm.virtualization..recharge.dto.Account)
	 */
	public void updateAccountBalance(Account account) throws DAOException {
		PreparedStatement ps = null;

		logger.info("Started ..." + account);

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY));
			ps.setLong(1, account.getUpdatedBy());
			ps.setTimestamp(2, Utility.getDateTime());
			if (account.getParentAccountId() == -1) {
				// MTP or DISTRIBUTOR Level Account
				ps.setNull(3, Types.INTEGER);
			} else {
				ps.setLong(3, account.getParentAccountId());
			}

			int i = ps.executeUpdate();
			ps.clearParameters();

			if (i == 0) {
				logger
						.error("Error While Updating Account Balance in VR_ACCOUNT_DETAILS  ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_INSUFFICIENT_OPERATING_BAL);
			}
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_ACC_OERATINGBALANCE_KEY));
			ps.setDouble(1, account.getOpeningBalance());
			ps.setDouble(2, account.getOpeningBalance());
			ps.setDouble(3, account.getOpeningBalance());
			ps.setLong(4, account.getParentAccountId());

			i = ps.executeUpdate();
			logger.info("No. Of Rows Updated For account Balance :-" + i);

			if (i == 0) {
				logger
						.error(" insufficient Account  Balance While Updating Account Balance in VR_ACCOUNT_DETAILS Table ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_INSUFFICIENT_OPERATING_BAL);
			}

		} catch (SQLException e) {
			logger.error("Sql Exception occured while Updating Balance ."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement, resultset */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info("Executed ...SuccessFully Updated Account balance ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#updateAccountAvailBalance(java.lang.double,
	 *      java.lang.long)
	 */

	public void updateAccountAvailBalance(double creditedAmt, long accountId,
			long updatedBy) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.info("Started ...creditedAmt:" + creditedAmt
				+ " And  accountId: " + accountId + " And  updatedBy: "
				+ updatedBy);
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY));
			ps.setLong(1, updatedBy);
			ps.setTimestamp(2, Utility.getDateTime());
			ps.setLong(3, accountId);
			int i = ps.executeUpdate();
			ps.clearParameters();

			if (i == 0) {
				logger
						.error("Error While updating account available  balance in VR_ACCOUNT_DETAILS Table ");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
			}
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY));
			ps.setDouble(1, creditedAmt);
			ps.setLong(2, accountId);
			i = ps.executeUpdate();
			if (i == 0) {
				logger.error("Error While Updating VR_ACCOUNT_BALANCE Table ");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
			}

			logger.info("Successfully updated account balance  rows updated  "
					+ i);

		} catch (SQLException e) {
			logger.error("Exception occured while Updating account balance."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement, resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccounts(
	 *      java.lang.String)
	 */

	public ArrayList getDistributorAccounts(int circleId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();
		logger.info("Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_DISTRIBUTOR_KEY));
			ps.setInt(1, circleId);
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setAccountId(rs.getLong("ACCOUNT_ID"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setRate(rs.getDouble("RATE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error(
					"Exception occured while getting Distributor Accounts List."
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement, resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ... ");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountId(java.lang.String)
	 */
	public long getAccountId(String accountCode) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNT_ID_KEY));
			ps.setString(1, accountCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountId = rs.getLong("LOGIN_ID");
			}

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while getting Account Id  "
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...");
		return accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountId(java.lang.String)
	 */
	public long getAccountId(String accountCode, int circleId)
			throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE_KEY));
			ps.setString(1, accountCode);
			ps.setInt(2, circleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountId = rs.getLong("LOGIN_ID");
			} else {
				logger.info(accountCode
						+ " Account doesnot belong to same circle. ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_ACCOUNT_PARENT_CHILD_DIFF_CIRCLE);
			}

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while getting Account Id  "
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...");
		return accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountCode(long)
	 */
	public String getAccountCode(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String accountCode = null;
		logger.info("Started ...");
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNT_CODE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountCode = rs.getString("LOGIN_NAME");
			} else {
				logger.info("Exception occured :Invalid Parent Account Code");
				throw new DAOException(
						ExceptionCode.Account.ERROR_INVALID_PARENT_CODE);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while get account code."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/** Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...Successfully retrieved Account Code");
		return accountCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#insertAccountData(com.ibm.virtualization.recharge.dto.Account)
	 */
	public void insertAccount(Account account) throws DAOException {
		logger.info("Started ..." + account);
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_INSERT_ACC_DETAIL_KEY));
			int paramCount = 1;

			ps.setLong(paramCount++, account.getAccountId());
			ps.setString(paramCount++, account.getMobileNumber());
			ps.setString(paramCount++, account.getSimNumber());
			ps.setString(paramCount++, account.getAccountName());
			ps.setString(paramCount++, account.getAccountAddress());
			ps.setString(paramCount++, account.getEmail());
			ps.setString(paramCount++, account.getCategory());
			ps.setLong(paramCount++, account.getCircleId());

			// if not distributor or MTP user
			if (account.getParentAccountId() != 0
					&& !(account.getAccountLevelId()
							.equalsIgnoreCase(Constants.ACC_LEVEL_MTP_DISTRIBUTOR))) {
				ps.setLong(paramCount++, account.getParentAccountId());
				ps.setLong(paramCount++, account.getRootLevelId());
			} else {
				// if distributor or MTP user
				ps.setNull(paramCount++, Types.INTEGER);
				// in this case root level self account id
				ps.setLong(paramCount++, account.getAccountId());

			}

			if (account.getOpeningDt() != null
					&& account.getOpeningDt().trim().length() != 0) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						account.getOpeningDt()).getTime()));// opening
			} else {
				ps.setDate(paramCount++, new java.sql.Date(new java.util.Date()
						.getTime()));// opening
			}
			// date
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, account.getCreatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, account.getUpdatedBy());
			ps.setString(paramCount++, account.getAccountLevel());

			// if billable not account
			if (account.getIsbillable().equalsIgnoreCase(
					Constants.ACCOUNT_BILLABLE_NO)) {
				ps.setLong(paramCount++, account.getBillableCodeId());
			} else {
				// if distributor or MTP user or none billable account
				ps.setNull(paramCount++, Types.INTEGER);
			}
			ps.setInt(paramCount++, account.getAccountStateId());
			ps.setInt(paramCount++, account.getAccountCityId());
			ps.setInt(paramCount++, account.getAccountPinNumber());
			ps.setString(paramCount, account.getIsbillable());

			ps.executeUpdate();
			logger.info(" Executed ...Created Account Details. ");
			// clear bind parameter for child insert
			ps.clearParameters();
			// inserting values into second VR_ACCOUNT_BALANCE Table
			ps = connection.prepareStatement(queryMap.get(SQL_INSERT_ACC_BALANCE_KEY));
			paramCount = 1;
			ps.setLong(paramCount++, account.getAccountId());
			ps.setDouble(paramCount++, account.getOpeningBalance());
			ps.setDouble(paramCount++, account.getRate());
			ps.setDouble(paramCount++, account.getThreshold());
			ps.setDouble(paramCount++, account.getOperatingBalance());
			ps.setDouble(paramCount, account.getOpeningBalance());
			ps.executeUpdate();
			logger.info(" Executed ...Created Account Balance Details. ");
		} catch (SQLException e) {
			logger.error(
					"SQL Exception occured while inserting.Into Account Tables"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...New Account Successfully Created ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountList(java.lang.String,
	 *      java.lang.String, int)
	 */
	public ArrayList getAccountList(ReportInputs mtDTO, int lb, int ub)
			throws DAOException {
		logger.info("Started ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		String circleName=null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder query = new StringBuilder();
			int paramCount = 1;
			
			
			if(circleId!=0){
				circleName=getCircleName(circleId);
				if(circleName.equalsIgnoreCase("NATIONAL")){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");			
				}else {	
					// if circle user, show records of respective circle
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			}else {
				// if Administrator , show records from all circles
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
			 }
			
			
			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) <= ? ");
			 }
			
			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				} /*
					 * else if (searchType.trim().equalsIgnoreCase(
					 * Constants.SEARCH_TYPE_ALL)) {
					 * 
					 * ps.setString(paramCount++, status);
					 * ps.setDate(paramCount++, new
					 * java.sql.Date(Utility.getDate( startDt).getTime()));
					 * ps.setDate(paramCount++, new
					 * java.sql.Date(Utility.getDate( endDt).getTime()));
					 * ps.setString(paramCount++, String.valueOf(ub));
					 * ps.setString(paramCount++, String.valueOf(lb + 1)); }
					 */
				
				
				if((0 != circleId)&& (circleName.equalsIgnoreCase("NATIONAL")) &&(searchType.trim().equalsIgnoreCase(
						Constants. SEARCH_TYPE_ALL))){	
					// If user selects search type as ALL	
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				}else if((circleName.equalsIgnoreCase("NATIONAL"))&& (searchType.trim()!=Constants. SEARCH_TYPE_ALL)){					
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
				}
				
				query.append("SELECT * FROM(SELECT a.*,ROWNUM rnum FROM (");
				query.append(sql);
				query.append(") a Where ROWNUM<=?)Where rnum>=?");
				ps = connection.prepareStatement(query.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				ps.setString(paramCount++, String.valueOf(ub));
				ps.setString(paramCount++, String.valueOf(lb + 1));
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));

				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRowNum(rs.getString("RNUM"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedBy(rs.getLong("CREATED_BY"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));	
				if(rs.getInt("LOGIN_ATTEMPTED")==numberOfPwdHistory){
					account.setLocked(true);
				}
				else{
					account.setLocked(false);
				}
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("AccountDaoRdbms:caught VirtualizationServiceException"+virtualizationExp.getMessage());
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getChildAccountList(java.lang.String,
	 *      java.lang.String, long)
	 */
	public ArrayList getChildAccountList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {

		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		StringBuilder query = new StringBuilder();
		int paramCount = 1;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY) + " AND LOGIN.STATUS = ? ");
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
				// // logger.info(" SQL is :- " + sql);
				// query = "SELECT * from(select a.*,ROWNUM rnum FROM (" + sql
				// + ") a " + "Where ROWNUM<=?)Where rnum>=?";
				// ps = connection.prepareStatement(query);
				// ps.setLong(paramCount++, parentId);
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// ps.setString(paramCount++, String.valueOf(upperBound));
				// ps.setString(paramCount++, String.valueOf(lowerBound + 1));
				// }
				sql
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ");
				query.append("SELECT * FROM(SELECT a.*,ROWNUM rnum FROM (");
				query.append(sql);
				query.append(") a Where ROWNUM<=?)Where rnum>=?");
				ps = connection.prepareStatement(query.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				ps.setLong(paramCount++, parentId);
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRowNum(rs.getString("RNUM"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountLevelAssignedList(long)
	 */
	public ArrayList getAccesstLevelAssignedList(long login)
			throws DAOException {
		logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountAccessLevel accountAccessLevel = null;
		ArrayList<AccountAccessLevel> accountLevelList = new ArrayList<AccountAccessLevel>();

		try {
			// ps = connection.prepareStatement(SQL_SELECT_ACCESS_LEVEL);`

			if (login != 0) {
				ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNT_LEVEL_KEY));
				ps.setLong(1, login);
				ps.setLong(2, login);
			} else {
				ps = connection
						.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN_KEY));

			}
			rs = ps.executeQuery();

			while (rs.next()) {
				accountAccessLevel = new AccountAccessLevel();

				// new change group name added
				accountAccessLevel.setAccessLevelId(rs.getInt("LEVEL_ID"));
				accountAccessLevel.setAccountLevelName(rs
						.getString("LEVEL_NAME"));
				accountAccessLevel.setAccountAccessLevel(rs
						.getString("ACC_LEVEL"));

				accountLevelList.add(accountAccessLevel);
			}

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving.Account Level List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountLevelList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccount(long)
	 */
	public Account getAccount(long accountId) throws DAOException {
		logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACC_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();

			if (rs.next()) {
				account = new Account();
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setCategory(rs.getString("CATEGORY"));
				// parent code according to id
				if (rs.getString("PARENT_ACCOUNT") != null)
					account.setParentAccount(getAccountCode(rs
							.getLong("PARENT_ACCOUNT")));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				//account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				//account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				//account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				//account.setRate(rs.getDouble("RATE"));
				//account.setThreshold(rs.getDouble("Threshold"));
				account.setCircleId(rs.getInt("CIRCLE_ID"));
				account.setGroupId(rs.getInt("GROUP_ID"));
				account.setMPassword(rs.getString("M_PASSWORD"));
				account.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				account.setAccountStateId(rs.getInt("STATE_ID"));
				account.setAccountCityId(rs.getInt("CITY_ID"));
				account.setIsbillable(rs.getString("BILLABLE_ACCOUNT"));
				account.setAccountPinNumber(rs.getInt("PIN_NUMBER"));
				account.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				account.setRootLevelId(rs.getLong("ROOT_LEVEL_ID"));
				account.setCircleId(rs.getInt("CIRCLE_ID"));
				account.setAccountLevel(rs.getString("ACCOUNT_LEVEL"));
				account.setLevelName(rs.getString("LEVEL_NAME"));
				account.setParentAccountId(rs.getLong("PARENT_ACCOUNT"));
				account.setIPassword(rs.getString("PASSWORD"));
				
				if (rs.getString("BILLABLE_ACC_ID") != null) {
					account.setBillableCode(getAccountCode(rs
							.getLong("BILLABLE_ACC_ID")));
				}
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setAccountAddress((rs.getString("ACCOUNT_ADDRESS")));
				
				account.setCircleCode(rs.getString("CIRCLE_CODE"));
			}

		} catch (SQLException e) {
			logger.error("Exception occured while get Account."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...");
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountDetailsByMobile(java.lang.String)
	 */
	public Account getAccountDetailsByMobile(String mobileNO)
			throws DAOException {
		logger.info("Started ...mobileNO " + mobileNO);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACC_BY_MOBILE_KEY));
			ps.setString(1, mobileNO);
			rs = ps.executeQuery();

			if (rs.next()) {
				account = new Account();
				account.setAccountId(rs.getLong("ACCOUNT_ID"));
				account.setParentAccountId(rs.getLong("PARENT_ACCOUNT"));
				account.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				// account.setCircleId(rs.getInt("CIRCLE_ID"));

				account.setStatus(rs.getString("STATUS"));
				account.setRate(rs.getDouble("RATE"));
			}

		} catch (SQLException e) {
			logger.error("Exception occured while getAccount."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...");
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#updateAccount(com.ibm.virtualization.recharge.dto.Account)
	 */
	public void updateAccount(Account account) throws DAOException {
		logger.info("Started ...  account " + account);
		PreparedStatement ps = null;
		int rowsUpdated = 0;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_ACC_DETAIL_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, account.getSimNumber());
			ps.setString(paramCount++, account.getMobileNumber());
			ps.setString(paramCount++, account.getAccountName());
			ps.setString(paramCount++, account.getAccountAddress());
			ps.setString(paramCount++, account.getEmail());
			ps.setString(paramCount++, account.getCategory());
			// ps.setInt(paramCount++, account.getCircleId());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, account.getUpdatedBy());

			// if billable not account
			if (account.getIsbillable().equalsIgnoreCase(
					Constants.ACCOUNT_BILLABLE_NO)) {
				ps.setLong(paramCount++, account.getBillableCodeId());
			} else {
				// if distributor or MTP user or none billable account
				ps.setNull(paramCount++, Types.INTEGER);
			}

			ps.setInt(paramCount++, account.getAccountStateId());
			ps.setLong(paramCount++, account.getAccountCityId());
			ps.setString(paramCount++, account.getIsbillable());
			ps.setInt(paramCount++, account.getAccountPinNumber());
			ps.setLong(paramCount, account.getAccountId());
			ps.executeUpdate();

			logger
					.info("VR_ACCOUNT_DETAILS TABLE SUCCESSFULY UPDATED NOW UPDATING VR_ACCOUNT_BALANCE TABLE  ");
			ps.clearParameters();

			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_ACC_BALANCE_KEY));
			paramCount = 1;
			// ps.setString(paramCount++, account.getStatus());
			ps.setDouble(paramCount++, account.getRate());
			ps.setDouble(paramCount++, account.getThreshold());
			ps.setLong(paramCount, account.getAccountId());
			rowsUpdated = ps.executeUpdate();
			logger
					.info("Row Update successful for table VR_ACCOUNT_BALANCE on table:. updated:"
							+ rowsUpdated + " rows");
		} catch (SQLException e) {
			logger.error(
					"SQL Exception occured while updating.Account Due to Mobile No. Already Exist"
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...");

		// return rowsUpdated;
	}

	/*
	 * For Distributor Transaction (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountRate(java.lang.String)
	 */

	public double getAccountRate(long accountId) throws DAOException {
		logger.info("Started ... account id:- " + accountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		double rate = 0.0;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNT_RATE_KEY));
			// getting account Id on the basis of Account Code From AccountDAO
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			// getting rate parameter according to account Id
			// If rate not found then throw Invalid account ID exception
			if (rs.next()) {
				rate = rs.getDouble("RATE");
			} else {
				// record not found if Account Code invalid hence throw
				// exception
				logger
						.fatal(" Rate coud not be found for the destination AccountId:"
								+ accountId);
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_LOG_TRANSACTION);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while getting rate."
					+ "Exception Message: ", e);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...Successfully retrieved Account Rate ");
		return rate;
	}

	/*
	 * For Transaction (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getMobileNumber(long)
	 */
	public String getMobileNumber(long accountId) throws DAOException {

		logger.info("Started ... for accountID " + accountId);
		String mobileNumber = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_MOBILENO_KEY));
			// getting account Id on the basis of Account Code From AccountDAO
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			// getting mobileNumber according to account Id
			// If mobileNumber not found then throw Invalid account ID exception
			if (rs.next()) {
				mobileNumber = rs.getString("MOBILE_NUMBER");
			} else {
				// record not found if Account Code invalid hence throw
				// exception
				logger
						.error(" Exception occured :While getting mobileNumber According To Account ID "
								+ accountId);
				throw new DAOException(
						ExceptionCode.Account.ERROR_INVALID_ACCOUNT_CODE);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while get mobile no.."
					+ "Exception Message: ", e);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...Successfully retrieved mobile Number");
		return mobileNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#getBalance(string)
	 */
	public AccountBalance getBalance(long accountId) throws DAOException {
		logger.info("Started ...For Account Id" + accountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountBalance dto = new AccountBalance();
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_BALANCE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("Balance for the AccountId exists");
				dto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				dto.setRate(rs.getFloat("RATE"));
				dto.setAccountId(accountId);
				dto.setThreshold(rs.getDouble("THRESHOLD"));
				dto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				dto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
			} else {
				logger.error("No Balance exist for the Account.accountId:"
						+ accountId);
			}
			logger.info("Executed ...");
			return dto;
		} catch (SQLException e) {
			logger.error(" SQL Exception occured while finding Balance."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Transaction.ERROR_GET_BALANCE);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#isChild(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isChild(long sourceAccountId, long destinationAccountId)
			throws DAOException {
		logger.info("Started ...For source AccountId" + sourceAccountId
				+ "destinationAccountId:" + destinationAccountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			/* Get the PrepareStatement Object using the prepare statement */
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ISCHILD_KEY));
			ps.setLong(1, destinationAccountId);
			ps.setLong(2, sourceAccountId);
			rs = ps.executeQuery();
			if (rs.next()) {
				logger
						.info("Receiving account is a Child of Transferrer Account.");
				rs.getInt("ACCOUNT_ID");
				return true;
			} else {
				logger
						.error("Receiving account is not a Child of Transferrer Account.Source:"
								+ sourceAccountId
								+ " & destination:"
								+ destinationAccountId);
			}
			return false;
		} catch (SQLException e) {
			logger
					.fatal(
							"SQL Exception occured while finding receiving account is Child of Transferrer Account.",
							e);
			throw new DAOException(ExceptionCode.Transaction.ERROR_IS_CHILD);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#getChildAccounts(java.lang.String)
	 */
	public ArrayList getChildAccounts(long sourceAccountId) throws DAOException {
		logger.info("Started ...For sourceAccountId" + sourceAccountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Account> childAccountList = new ArrayList<Account>();
		try {
			Account dto = new Account();
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_CHILD_ACCOUNT_KEY));
			ps.setLong(1, sourceAccountId);
			rs = ps.executeQuery();
			while (rs.next()) {
				/*
				 * It sets the DTO of the Account to the values of the selected
				 * child Account info
				 */
				dto = new Account();
				dto.setAccountName(rs.getString(1));
				dto.setAccountId(rs.getLong(2));
				dto.setMobileNumber(rs.getString(3));
				dto.setLoginName(rs.getString(4));
				childAccountList.add(dto);
			}
			logger.info("Number of child Accounts found = "
					+ childAccountList.size());
			if (childAccountList.size() == 0) {
				logger
						.error("No child for the Source Account Exists.sourceAccountId:"
								+ sourceAccountId);
			}
			return childAccountList;

		} catch (SQLException sqlException) {
			logger
					.fatal("SQL Exception occured while finding child Accouts for the SourceAccount."
							+ "Exception Message:" + sqlException.getMessage());
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_NO_CHILD_ACCOUNT);
		} finally {

			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#updateBalance(string,
	 *      double, java.lang.String, java.lang.String)
	 */
	public void checkUpdateOperatingBalance(long accountId, double debitAmount)
			throws DAOException, VirtualizationServiceException {
		logger.info("update the Balance in the Table: VR_ACCOUNT_BALANCE ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection
					.prepareStatement(queryMap.get(SQL_CHECK_UPDATE_OPERATING_BALANCE_KEY));
			ps.setDouble(1, debitAmount);
			ps.setLong(2, accountId);
			ps.setDouble(3, -debitAmount);
			int updateStatus = ps.executeUpdate();
			if (updateStatus == 0) {
				logger.error("Insufficient Balance for account Id :"
						+ accountId);
				throw new DAOException(ExceptionCode.Transaction.ERROR_INSUFFICIENT_BAL);
			} else {
				logger
						.info("Successfully updated the balance for the Account Id:"
								+ accountId);
			}
			logger
					.info("successful updation of operating Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException e) {
			logger
					.fatal("SQL Exception occured while updation of operating Balance ."
							+ "Exception Message: " + e.getMessage());
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_UPDATE_OPERATING_BAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#updateBalance(string,
	 *      double, java.lang.String, java.lang.String)
	 */
	public void updateOperatingBalance(long accountId, double debitAmount)
			throws DAOException {
		logger.info("Started ...  Account Id" + accountId + "And debitAmount:"
				+ debitAmount);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_OPERATING_BALANCE_KEY));
			ps.setDouble(1, debitAmount);
			ps.setLong(2, accountId);
			ps.executeUpdate();
			logger
					.info("Executed ... successful updation of operating Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while updation of operating Balance ."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_UPDATE_OPERATING_BAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...Successfully Updated Account Balance");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#updateAvailableBalance(long,
	 *      double)
	 */
	public void updateAvailableBalance(long accountId, double debitAmount)
			throws DAOException {
		logger.info("Started ... for Account Id" + accountId + ":debitAmount:"
				+ debitAmount);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_AVAILABLE_BALANCE_KEY));
			ps.setDouble(1, debitAmount);
			ps.setLong(2, accountId);
			ps.executeUpdate();

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while updation of available Balance ."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_UPDATE_AVAILABLE_BAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...Successfully Updated Account Balance");
	}

	public int getCircleId(long accountId) throws DAOException {
		logger.info("Started...For Account Id " + accountId);

		PreparedStatement ps = null;
		ResultSet rs = null;
		int numRows = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_WITH_ACCOUNTID_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
				numRows = rs.getInt("CIRCLE_ID");
			}

		} catch (SQLException e) {
			logger.error("SQL Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_FOUND);
		} finally {
			/* Close the preparedstatement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...");
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#updateAccountAvailBalance(java.lang.double,
	 *      java.lang.long)
	 */

	public void increaseAccountBalance(double creditedAmt, long accountId,
			long updatedBy) throws DAOException {
		PreparedStatement ps = null;
		logger.info("Started ...");

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY));
			ps.setLong(1, updatedBy);
			ps.setTimestamp(2, Utility.getDateTime());
			ps.setLong(3, accountId);
			int i = ps.executeUpdate();
			ps.clearParameters();

			if (i == 0) {
				logger.error("Error While Updating VR_ACCOUNT_DETAILS Table  ");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
			}
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY));
			ps.setDouble(1, creditedAmt);
			ps.setDouble(2, creditedAmt);
			ps.setLong(3, accountId);
			i = ps.executeUpdate();
			if (i == 0) {
				logger.error("Error While Updating  VR_ACCOUNT_BALANCE Table ");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
			}

			logger
					.info("Executed ...Successfully updated VR_ACCOUNT_BALANCE  row updated  "
							+ i);

		} catch (SQLException e) {
			logger.error("Exception occured while Updating."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...Successfully Updated Account ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#doUpdate(string,
	 *      double, java.lang.String)
	 */
	public void doUpdateSourceBalance(long accountId, double transactionAmount)
			throws DAOException {
		logger.info("Started ...  transactionAmount" + transactionAmount
				+ ":accountId:" + accountId);
		PreparedStatement pstmt = null;
		try {
			pstmt = connection
					.prepareStatement(queryMap.get(SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY));
			pstmt.setDouble(1, transactionAmount);
			pstmt.setDouble(2, transactionAmount);
			pstmt.setLong(3, accountId);
			pstmt.setDouble(4, -transactionAmount);
			int updateStatus = pstmt.executeUpdate();
			if (updateStatus == 0) {
				logger.error("Insufficient Balance for the Accountid:"
						+ accountId);
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
			} else {
				logger.info("Sufficient Balance for the Accountid:"
						+ accountId);
			}

			logger
					.info("Successful updation of Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception occured while updation of Balance ."
					+ "Exception Message: " + sqlEx.getMessage());
			try {

				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (SQLException e) {
				logger
						.fatal("SQL Exception occured while updation of Balance ."
								+ "Exception Message: " + sqlEx.getMessage());

			}
			// there is no error msg for
			// (errors.customertransaction.updation_error)
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);

		} finally {
			/* Close the preparedstatement, resultset. */
			DBConnectionManager.releaseResources(pstmt, null);
		}
		logger.info("Executed ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#doUpdate(string,
	 *      double, java.lang.String)
	 */
	public void doUpdateDestinationBalance(long accountId,
			double transactionAmount) throws DAOException {
		logger.info("Started ...  transactionAmount" + transactionAmount
				+ ":accountId:" + accountId);
		PreparedStatement pstmt = null;

		try {
			pstmt = connection
					.prepareStatement(queryMap.get(SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE_KEY));
			pstmt.setDouble(1, transactionAmount);
			pstmt.setDouble(2, transactionAmount);
			pstmt.setLong(3, accountId);
			pstmt.executeUpdate();
			logger
					.info("Executed ... Successful updation of Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception occured while updation of Balance ."
					+ "Exception Message: " + sqlEx.getMessage());
			try {

				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (SQLException e) {
				logger
						.fatal("SQL Exception occured while updation of Balance ."
								+ "Exception Message: " + sqlEx.getMessage());

			}
			// there is no error msg for
			// (errors.customertransaction.updation_error)
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);

		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(pstmt, null);
		}
		logger.info("Executed ...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#isActive(string)
	 */
	public long isActive(String mobileNo) throws DAOException {
		logger.info("Started ... mobileNo" + mobileNo);
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ISACTIVE_KEY));
			ps.setString(1, mobileNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountId = rs.getLong("LOGIN_ID");
				logger.info("Specified destination Account is Active");
			} else {
				logger
						.error("Record for the destination Account Active or not does not exists."
								+ "accountId:" + mobileNo);
			}
			logger.info("Executed ...");
			return accountId;
		} catch (SQLException e) {

			logger
					.fatal(
							"SQL Exception occured while finding whether the destination Account is Active or not."
									+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Transaction.ERROR_IS_ACTIVE_ACCOUNT);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getParentAccountId(java.lang.String,
	 *      int)
	 */
	public long getParentAccountId(String parentCode, int circleId)
			throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = 0;
		logger.info(" Started :::parentCode : " + parentCode
				+ "  and circleId : " + circleId);
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_PARENT_ACCOUNTID_KEY));
			ps.setString(1, parentCode);
			ps.setInt(2, circleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountId = rs.getLong("LOGIN_ID");
			} else {
				throw new DAOException(
						ExceptionCode.Account.ERROR_INVALID_PARENT_CODE);
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while getting Account Id From Table  "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ... Account Id Successfully Retrieved");
		return accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#checkMobileNumberAlreadyExist(java.lang.String)
	 */
	public long checkMobileNumberAlreadyExist(String mobileNo)
			throws DAOException {
		logger.info(" Started....mobileNo : " + mobileNo);
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_IS_MOBILENO_EXIST_KEY));
			ps.setString(1, mobileNo);
			rs = ps.executeQuery();
			// if mobile no. exist
			if (rs.next()) {
				accountId = rs.getLong("ACCOUNT_ID");
				return accountId;
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while Checking Entered  Mobile No. is Exist   "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ...ACCOUNT_ID " + accountId);
		}

		return accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getParentAccountList(java.lang.String,
	 *      java.lang.String, long, int, java.lang.String)
	 */
	public ArrayList getParentAccountList(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId, int lb, int ub) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ parentCircleId + " AND accountLevelId :" + accountLevelId);
		
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();
		String query;
		int paramCount = 1;
		try {
			StringBuffer sql = new StringBuffer();

			// show records of respective child and grand-child accounts upto
			// n-th level

			sql.append(queryMap.get(SQL_SELECT_FOR_PARENT_ACC_KEY));
			/*
			 * if (Integer.parseInt(accLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql.append(" AND
			 * ACCOUNT_LEVEL <'" + accLevelId + "' AND ACCOUNT_LEVEL >='" +
			 * Constants.ACC_LEVEL_MTP + "'"); } else { sql.append(" AND
			 * ACCOUNT_LEVEL <'" + Constants.ACC_LEVEL_MTP + "' AND
			 * ACCOUNT_LEVEL <'" + accLevelId + "'"); }
			 */

			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID='" + accLevelId
					+ "'   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID='"
							+ accLevelId + "'))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, parentCircleId);
					ps.setLong(paramCount++, parentId);
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
					ps.setString(paramCount++, String.valueOf(ub));
					ps.setString(paramCount, String.valueOf(lb + 1));

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					// logger.info(" SQL is :- " + sql);
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);

					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/*
					 * all child and sub-child and sub-sub-child accounts upto
					 * n-th level
					 */
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				}
			}
			logger.info(" This is query getParentAccountList:- sql "
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getParentSearchAccountList(java.lang.String,
	 *      java.lang.String, int, java.lang.String)
	 */

	public ArrayList getParentSearchAccountList(String searchType,
			String searchValue, int circleId, String accountLevelId, int lb,
			int ub) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId);
		
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();
		String query;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
			sql.append(" AND DETAILS.CIRCLE_ID=? AND  LOGIN.STATUS='"
					+ Constants.ACTIVE_STATUS + "' ");

			/*
			 * if (Integer.parseInt(accLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql.append(" AND
			 * ACCOUNT_LEVEL <'"); sql.append(accLevelId); sql.append("' AND
			 * ACCOUNT_LEVEL >='"); sql.append(Constants.ACC_LEVEL_MTP);
			 * sql.append("'");
			 *  } else { sql.append(" AND ACCOUNT_LEVEL <'");
			 * sql.append(Constants.ACC_LEVEL_MTP); sql.append("' AND
			 * ACCOUNT_LEVEL <'"); sql.append(accLevelId); sql.append("'");
			 *  }
			 */

			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=" + accLevelId
					+ "   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID="
							+ accLevelId + "))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, String.valueOf(ub));
					ps.setString(3, String.valueOf(lb + 1));
				}
			}
			logger.info(" this is sql getParentSearchAccountList :-"
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));

				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;
	}

	/**
	 * This method will be used provide total count of accounts created in the
	 * system. for internal user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return int - total number of records.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while counting the records.
	 */
	public int getAccountListCount(ReportInputs mtDTO) throws DAOException {
		logger.info("Started getAccountListCount()...mtDTO " + mtDTO);

		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		int noofRow = 1;
		int noofpages = 0;
		int paramCount = 1;
		String circleName=null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			if(circleId!=0){
				circleName=getCircleName(circleId);

				if(circleName.equalsIgnoreCase("NATIONAL")){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");			
				}else{
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				} 
			}else {
				/** if Administrator , show records from all circles */
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
			}			

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /* all accounts */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				
				if(0 != circleId){ 
					if((circleName.equalsIgnoreCase("NATIONAL"))&&(searchType.trim().equalsIgnoreCase(Constants. SEARCH_TYPE_ALL))){	
						//	If user selects search type other than ALL
						sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
					}else if(circleName.equalsIgnoreCase("NATIONAL")&& (searchType.trim()!=Constants. SEARCH_TYPE_ALL)){	
						//	If user selects search type as ALL	
						sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
					}
				}	
				
				ps = connection.prepareStatement(sql.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);
		} catch (SQLException e) {
			logger.error("Exception occured while counting the records"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed getAccountListCount()...");
		return noofpages;
	}

	/**
	 * This method will be used provide a list of all account created in the
	 * system for internal user logged in. This will provide all the records and
	 * is independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getAccountListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getAccountListAll()...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		String circleName=null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			int paramCount = 1;
			if(circleId!=0){
				circleName=getCircleName(circleId);
				if(circleName.equalsIgnoreCase("NATIONAL")){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");			
				}else {	
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			}else {
					/** if Administrator , show records from all circles */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");
			 }

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				/** search according to mobile no */
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/** search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/** search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/** search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /* all accounts */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				
				if((0 != circleId)&& (circleName.equalsIgnoreCase("NATIONAL")) &&(searchType.trim().equalsIgnoreCase(
						Constants. SEARCH_TYPE_ALL))){	
					// If user selects search type as ALL	
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				}else if((circleName.equalsIgnoreCase("NATIONAL"))&& (searchType.trim()!=Constants. SEARCH_TYPE_ALL)){					
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
				}
				
				ps = connection.prepareStatement(sql.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedBy(rs.getLong("CREATED_BY"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving all Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getAccountListAll()...");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getChildAccountListAll(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public ArrayList getChildAccountListAll(ReportInputs mtDTO)
			throws DAOException {

		logger.info("Started getChildAccountListAll() ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		int paramCount = 1;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
				// logger.info("SQL APPENDED IS : "+sql);
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setLong(paramCount++, parentId);
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				sql
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ");
				ps = connection.prepareStatement(sql.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				ps.setLong(paramCount++, parentId);
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedBy(rs.getLong("CREATED_BY"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving all child accounts List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getChildAccountListAll()...");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getChildAccountListCount(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public int getChildAccountListCount(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getChildAccountListCount()...");

		/** get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		int noofRow = 1;
		int noofpages = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND TRUNC(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setLong(paramCount++, parentId);
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				sql
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ");
				ps = connection.prepareStatement(sql.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				ps.setLong(paramCount++, parentId);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving Child account list count"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getChildAccountListCount()...");
		return noofpages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getParentAccountListCount(java.lang.String,
	 *      java.lang.String, int, java.lang.String)
	 */
	public int getParentAccountListCount(String searchType, String searchValue,
			int circleId, String accountLevelId) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId);
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int noofRow = 1;
		int noofpages = 0;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
			sql.append(" AND DETAILS.CIRCLE_ID=? AND  LOGIN.STATUS='"
					+ Constants.ACTIVE_STATUS + "' ");
			/*
			 * if (Integer.parseInt(accLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql = sql + " AND
			 * DETAILS.ACCOUNT_LEVEL <'" + accLevelId + "' AND
			 * DETAILS.ACCOUNT_LEVEL >='" + Constants.ACC_LEVEL_MTP + "'"; }
			 * else { sql = sql + " AND DETAILS.ACCOUNT_LEVEL <'" +
			 * Constants.ACC_LEVEL_MTP + "' AND DETAILS.ACCOUNT_LEVEL <'" +
			 * accLevelId + "'"; }
			 */

			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=" + accLevelId
					+ "   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID="
							+ accLevelId + "))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?   ");
					ps = connection.prepareStatement(sql.toString());
					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?   ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
				}
			}
			logger.info("SQL............................"+sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return noofpages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getParentChildAccountListCount(java.lang.String,
	 *      java.lang.String, long, int, java.lang.String)
	 */
	public int getParentChildAccountListCount(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ parentCircleId + " AND accountLevelId :" + accountLevelId);
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int noofRow = 1;
		int noofpages = 0;
		try {
			StringBuffer sql = new StringBuffer();

			// show records of respective child and grand-child accounts upto
			// n-th level

			sql.append(queryMap.get(SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY));
			/*
			 * if (Integer.parseInt(accLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql.append(" AND
			 * ACCOUNT_LEVEL <'" + accLevelId + "' AND ACCOUNT_LEVEL >='" +
			 * Constants.ACC_LEVEL_MTP + "'"); } else { sql.append(" AND
			 * ACCOUNT_LEVEL <'" + Constants.ACC_LEVEL_MTP + "' AND
			 * ACCOUNT_LEVEL <'" + accLevelId + "'"); }
			 */

			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID='" + accLevelId
					+ "'   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID='"
							+ accLevelId + "'))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ORDER BY LOGIN_NAME  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					// logger.info(" SQL is :- " + sql);
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/*
					 * all child and sub-child and sub-sub-child accounts upto
					 * n-th level
					 */
					ps = connection.prepareStatement(sql.toString());
					/** set the values in the statement */
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return noofpages;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountOpeningDate(long)
	 */
	public String getAccountOpeningDate(long accountId) throws DAOException {
		logger.info(" Started.... Account ID : " + accountId);
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_OPENING_DATE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			Date opnDate;
			// if mobile no. exist
			if (rs.next()) {
				opnDate = rs.getDate("OPENING_DT");
				logger.info("opening Date:" + opnDate);
				if (opnDate != null) {

					if (opnDate.compareTo(new Date()) > 0) {
						logger
								.fatal("opening date is greater than current date");
						return opnDate.toString();
					}

				}
			}
		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while Checking Account Opening Date   "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ...ACCOUNT_ID " + accountId);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountDefaultGroupId(int)
	 */
	public int getAccountDefaultGroupId(int accountLevelId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int groupId = -1;
		logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_DEFAULT_GROUP_ID_KEY));
			ps.setInt(1, accountLevelId);
			rs = ps.executeQuery();
			if (rs.next()) {
				groupId = rs.getInt("GROUP_ID");
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while getting default group Id  "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...");
		return groupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAllStates()
	 */
	public ArrayList getAllStates() throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Account> stateList = new ArrayList<Account>();
		Account account = null;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ALL_STATES_KEY));
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					account = new Account();
					account.setAccountStateId(rs.getInt("STATE_ID"));
					account.setAccountStateName(rs.getString("STATE_NAME"));
					stateList.add(account);
				}
			}

			return stateList;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getstates."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.ERROR_STATE_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getCites(int)
	 */
	public ArrayList getCites(int stateId) throws DAOException {

		logger.info("Started...stateId :" + stateId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Account> accountCity = new ArrayList<Account>();
		Account account = null;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_CITES_KEY));
			ps.setInt(1, stateId);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					account = new Account();
					account.setAccountCityId(rs.getInt("CITY_ID"));
					account.setAccountCityName(rs.getString("CITY_NAME"));
					accountCity.add(account);
				}
			}

			return accountCity;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getCity."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getCites(int)
	 */
//	public ArrayList getStates(int circleId) throws DAOException {
//
//		logger.info("Circle Id :: " + circleId);
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ArrayList<Account> accountState = new ArrayList<Account>();
//		Account account = null;
//		try {
//
//			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_STATES_KEY));
//			ps.setInt(1, circleId);
//			rs = ps.executeQuery();
//
//			if (rs != null) {
//				while (rs.next()) {
//					account = new Account();
//					account.setAccountStateId(rs.getInt("STATE_ID"));
//					account.setAccountStateName(rs.getString("STATE_NAME"));
//					accountState.add(account);
//				}
//			}
//
//			return accountState;
//		} catch (SQLException e) {
//			logger.info("SQL Exception occured while getState...."
//					+ "Exception Message: ", e);
//			throw new DAOException(
//					ExceptionCode.ERROR_STATE_NO_RECORD_FAILED);
//		} finally {
//			DBConnectionManager.releaseResources(ps, rs);
//			logger.info("Executed  STATE ....");
//		}
//	}
//
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getBillableSearchAccountListCount(java.lang.String,
	 *      java.lang.String, int, java.lang.String, java.lang.String, long)
	 */

	public int getBillableSearchAccountListCount(String searchType,
			String searchValue, int circleId, String accountLevelId,
			String userType, long loginId) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId
				+ " AND  userType :" + userType + " AND loginId:" + loginId);
		
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int noofRow = 1;
		int noofpages = 0;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
			sql.append(" AND DETAILS.BILLABLE_ACCOUNT='Y' ");
			if (userType.trim().equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL)) {
				sql
						.append(" AND DETAILS.ROOT_LEVEL_ID IN (SELECT ROOT_LEVEL_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=");
				sql.append(loginId);
				sql.append(")");
			}
			sql.append(" AND DETAILS.CIRCLE_ID=? AND  LOGIN.STATUS='");
			sql.append(Constants.ACTIVE_STATUS);
			sql.append("' ");
			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT ");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=");
			sql.append(accLevelId);
			sql.append(") AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=");
			sql.append(accLevelId);
			sql.append("))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?   ");
					ps = connection.prepareStatement(sql.toString());
					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?   ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
				}
			}

			logger
					.info(" this is sql for count getBillableAccountListCount Search "
							+ sql.toString());
			rs = ps.executeQuery();

			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving.Billable Account List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return noofpages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getBillableSearchAccountList(java.lang.String,
	 *      java.lang.String, int, java.lang.String, int, int, java.lang.String,
	 *      long)
	 */

	// for admin and external user
	public ArrayList getBillableSearchAccountList(String searchType,
			String searchValue, int circleId, String accountLevelId, int lb,
			int ub, String userType, long loginId) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId
				+ " AND userType :" + userType + " AND loginId: " + loginId);
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> billableaccountList = new ArrayList<Account>();
		String query;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));

			sql.append(" AND DETAILS.BILLABLE_ACCOUNT='Y' ");
			if (userType.trim().equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL)) {
				sql
						.append(" AND DETAILS.ROOT_LEVEL_ID IN (SELECT ROOT_LEVEL_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=");
				sql.append(loginId);
				sql.append(")");
			}
			sql.append(" AND DETAILS.CIRCLE_ID=? AND  LOGIN.STATUS='");
			sql.append(Constants.ACTIVE_STATUS);
			sql.append("' ");
			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT ");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=");
			sql.append(accLevelId);
			sql.append("   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=");
			sql.append(accLevelId);
			sql.append("))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, String.valueOf(ub));
					ps.setString(3, String.valueOf(lb + 1));
				}
			}
			logger.info(" this is sql getBillableSearchAccountList :-"
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				billableaccountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Billable List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return billableaccountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountRootLevelId(long)
	 */
	public long getAccountRootLevelId(long loginId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID_KEY));
			ps.setLong(1, loginId);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountId = rs.getLong("ROOT_LEVEL_ID");
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while getting ROOT_LEVEL Account Id  "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...");
		return accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getCountBillableAccountId(long)
	 */
	public long getCountBillableAccountId(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long billableId;
		logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_BILLABLE_ACCOUNT_ID_KEY));
			ps.setLong(1, accountId);

			rs = ps.executeQuery();
			if (rs.next()) {
				billableId = rs.getLong(1);
			} else {
				logger
						.info(accountId
								+ " can't deactivated while billable childs activated   ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_ACCOUNT_PARENT_CHILD_DIFF_CIRCLE);
			}

		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while getting Billable accounts Id  "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...");
		return billableId;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccount(long)
	 */
	public Account getAccountByMobileNo(long mobileNo) throws DAOException {
		logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACC_MOBILE_KEY));
			ps.setLong(1, mobileNo);
			rs = ps.executeQuery();

			if (rs.next()) {
				account = new Account();
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setCategory(rs.getString("CATEGORY"));
				// parent code according to id
				if (rs.getString("PARENT_ACCOUNT") != null)
					account.setParentAccount(getAccountCode(rs
							.getLong("PARENT_ACCOUNT")));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("Threshold"));
				account.setCircleId(rs.getInt("CIRCLE_ID"));
				account.setGroupId(rs.getInt("GROUP_ID"));
				account.setMPassword(rs.getString("M_PASSWORD"));
				account.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				account.setAccountStateId(rs.getInt("STATE_ID"));
				account.setAccountCityId(rs.getInt("CITY_ID"));
				account.setIsbillable(rs.getString("BILLABLE_ACCOUNT"));
				account.setAccountPinNumber(rs.getInt("PIN_NUMBER"));
				account.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				account.setRootLevelId(rs.getLong("ROOT_LEVEL_ID"));
				account.setCircleId(rs.getInt("CIRCLE_ID"));
				account.setAccountLevel(rs.getString("ACCOUNT_LEVEL"));
				account.setParentAccountId(rs.getLong("PARENT_ACCOUNT"));
				if (rs.getString("BILLABLE_ACC_ID") != null) {
					account.setBillableCode(getAccountCode(rs
							.getLong("BILLABLE_ACC_ID")));
				}

			}

		} catch (SQLException e) {
			logger.error("Exception occured while get Account."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ...");
		return account;
	}

	
	public long  getCommissionByAccountId(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long commissionId=0;
		logger.info("Started ...");
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_DISTRIBUTOR_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();

			while (rs.next()) {
				commissionId=rs.getLong("COMMISSIONING_ID");
			}

		} catch (SQLException e) {
			logger.error(
					"Exception occured while getting Distributor Accounts List."
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement, resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ... ");
		return commissionId;
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getActiveCircleName()
	 */
	public String getCircleName(int circleId) throws DAOException {
		logger.info("Started...");
		String circleName = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_CIRCLE_NAME_KEY));
			ps.setInt(1,circleId );
			rs = ps.executeQuery();
			if (rs.next()) {
				circleName = rs.getString("CIRCLE_NAME");
				logger.info("Circle Name retrieved  is : " + circleName);
			} else {
				logger.error("Circle Name Does not Exist for circleId= "
						+ circleName);
				throw new DAOException(
						ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
			}
			logger.info("Executed .... ");
			return circleName;
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while retrieving Circle Name )"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
 }

}
