
package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.common.RDBMSQueries;
import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dto.AccountAccessLevel;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPCreateAccountDaoImpl extends BaseDaoRdbms implements
		DPCreateAccountDao {

	protected static Logger logger = Logger
			.getLogger(DPCreateAccountDaoImpl.class.getName());
	/**
	 * query to Find whether the Account Is Active or not for destination
	 * account
	 */
	protected final static String GET_DIST_LOCATOR = DBQueries.GET_DIST_LOCATOR;
	protected final static String GET_DIST_LOCATOR_EDIT = DBQueries.GET_DIST_LOCATOR_EDIT;
	
	
	protected final static String SQL_SELECT_ISACTIVE_KEY = "SQL_SELECT_ISACTIVE";

	protected static final String SQL_SELECT_ISACTIVE = DBQueries.SQL_SELECT_ISACTIVE;

	/* query to select access level based on the groupId */
	protected final static String SQL_SELECT_ACCESS_LEVEL_KEY = "SQL_SELECT_ACCESS_LEVEL";

	protected static final String SQL_SELECT_ACCESS_LEVEL = DBQueries.SQL_SELECT_ACCESS_LEVEL;
	protected static final String SQL_SELECT_DIST_ACCOUNT_LEVEL = DBQueries.SQL_SELECT_DIST_ACC_LEVEL;
	
	/** query to update Avialable & Operating balance for the source */
	protected final static String SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY = "SQL_UPDATE_ACCOUNT_SOURCE_BALANCE";

	protected static final String SQL_UPDATE_ACCOUNT_SOURCE_BALANCE = DBQueries.SQL_UPDATE_ACCOUNT_SOURCE_BALANCE;
	
	protected static final String SQL_UPDATE_WAREHOUSE_CODE = DBQueries.SQL_UPDATE_WAREHOUSE_CODE;
	

	/** query to update Avialable & Operating balance for the destination */
	protected final static String SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE_KEY = "SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE";

	protected static final String SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE = DBQueries.SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE;

	/** query to Update the Available balance */
	protected final static String SQL_UPDATE_AVAILABLE_BALANCE_KEY = "SQL_UPDATE_AVAILABLE_BALANCE";

	protected static final String SQL_UPDATE_AVAILABLE_BALANCE = DBQueries.SQL_UPDATE_AVAILABLE_BALANCE;

	protected final static String SQL_UPDATE_OPERATING_BALANCE_KEY = "SQL_UPDATE_OPERATING_BALANCE";

	protected static final String SQL_UPDATE_OPERATING_BALANCE = DBQueries.SQL_UPDATE_OPERATING_BALANCE;

	protected final static String SQL_CHECK_UPDATE_OPERATING_BALANCE_KEY = "SQL_CHECK_UPDATE_OPERATING_BALANCE";

	protected static final String SQL_CHECK_UPDATE_OPERATING_BALANCE = DBQueries.SQL_CHECK_UPDATE_OPERATING_BALANCE;

	/** query to find all the child accounts for the source account */
	protected final static String SQL_SELECT_CHILD_ACCOUNT_KEY = "SQL_SELECT_CHILD_ACCOUNT";

	protected final static String SQL_SELECT_CHILD_ACCOUNT = DBQueries.SQL_SELECT_CHILD_ACCOUNT;

	/**
	 * query to chek whether the Receiving Account is Child of Tranferrer
	 * Account or not
	 */
	protected final static String SQL_SELECT_ISCHILD_KEY = "SQL_SELECT_ISCHILD";

	protected static final String SQL_SELECT_ISCHILD = DBQueries.SQL_SELECT_ISCHILD;

	/** query to find the balance of an Account */
	protected final static String SQL_SELECT_BALANCE_KEY = "SQL_SELECT_BALANCE";

	protected static final String SQL_SELECT_BALANCE = DBQueries.SQL_SELECT_BALANCE;

	protected final static String SQL_INSERT_DIST_ACCOUNT_KEY = "SQL_INSERT_DIST_ACCOUNT";

	protected static final String SQL_INSERT_DIST_ACCOUNT = DBQueries.SQL_INSERT_DIST_ACCOUNT;

	protected final static String SQL_INSERT_DIST_DETAIL_KEY = "SQL_INSERT_DIST_DETAIL";
	
	protected final static String SQL_INSERT_ACC_DIST_CHILD_KEY = "SQL_INSERT_ACC_DIST_CHILD";
	
	protected static final String SQL_INSERT_ACC_DIST_CHILD = DBQueries.SQL_INSERT_ACC_DIST_CHILD;
	
	protected static final String SQL_INSERT_DIST_DETAIL = DBQueries.SQL_INSERT_DIST_DETAIL;
	

	protected static final String SQL_INSERT_ACC_DETAIL = DBQueries.SQL_INSERT_ACC_DETAIL;

	protected final static String SQL_SELECT_PARENT_TRADE_KEY = "SQL_SELECT_PARENT_TRADE";

	protected final static String SQL_SELECT_PARENT_TRADE = DBQueries.SQL_SELECT_PARENT_TRADE;

	protected final static String SQL_INSERT_ACC_BALANCE_KEY = "SQL_INSERT_ACC_BALANCE";

	protected static final String SQL_INSERT_ACC_BALANCE = DBQueries.SQL_INSERT_ACC_BALANCE;

	protected final static String SQL_SELECT_MOBILENO_KEY = "SQL_SELECT_MOBILENO";

	protected static final String SQL_SELECT_MOBILENO = DBQueries.SQL_SELECT_MOBILENO_DIST;
	protected final static String SQL_SELECT_ACCOUNTID_KEY = "SQL_SELECT_ACOUNTID";

	protected static final String SQL_SELECT_ACOUNTID = DBQueries.SQL_SELECT_ACOUNTID;

	protected final static String SQL_UPDATE_ACC_DETAIL_KEY = "SQL_UPDATE_ACC_DETAIL";

	protected static final String SQL_UPDATE_ACC_DETAIL = DBQueries.SQL_UPDATE_ACC_DETAIL;
	
	protected static final String SQL_UPDATE_ACC_DIST_CHILD_KEY = DBQueries.SQL_UPDATE_ACC_DIST_CHILD;
	

	protected final static String SQL_UPDATE_ACC_BALANCE_KEY = "SQL_UPDATE_ACC_BALANCE";

	protected static final String SQL_UPDATE_ACC_BALANCE = DBQueries.SQL_UPDATE_ACC_BALANCE;

	protected final static String SQL_SELECT_ACCOUNT_ID_KEY = "SQL_SELECT_ACCOUNT_ID";

	protected static final String SQL_SELECT_ACCOUNT_ID = DBQueries.SQL_SELECT_ACCOUNT_ID;

	// To check if parent and child are from same circle
	protected final static String SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE_KEY = "SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE";

	protected static final String SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE = DBQueries.SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE;
	
	protected static final String SQL_SELECT_LOGIN_ID_SAME_CIRCLE_KEY = "SQL_SELECT_LOGIN_ID_SAME_CIRCLE";
	
	protected static final String SQL_SELECT_LOGIN_ID_SAME_CIRCLE = DBQueries.SQL_SELECT_LOGIN_ID_SAME_CIRCLE;

	protected final static String SQL_SELECT_ACCOUNT_CODE_KEY = "SQL_SELECT_ACCOUNT_CODE";

	protected static final String SQL_SELECT_ACCOUNT_CODE = DBQueries.SQL_SELECT_ACCOUNT_CODE; 
	
	protected final static String SQL_SELECT_ACCOUNT_WITH_CIRCLEID_KEY = "SQL_SELECT_ACCOUNT_WITH_CIRCLEID";

	protected static final String SQL_SELECT_ACCOUNT_WITH_CIRCLEID = DBQueries.SQL_SELECT_ACCOUNT_WITH_CIRCLEID;

	protected final static String SQL_SELECT_ACCOUNT_RATE_KEY = "SQL_SELECT_ACCOUNT_RATE";

	protected static final String SQL_SELECT_ACCOUNT_RATE = DBQueries.SQL_SELECT_ACCOUNT_RATE;

	protected final static String SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY = "SQL_UPDATE_ACCOUNT_AVAILBALANCE";

	protected static final String SQL_UPDATE_ACCOUNT_AVAILBALANCE = DBQueries.SQL_UPDATE_ACCOUNT_AVAILBALANCE;

	protected final static String SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY = "SQL_UPDATE_VR_ACCOUNT_DETAILS";

	protected static final String SQL_UPDATE_VR_ACCOUNT_DETAILS = DBQueries.SQL_UPDATE_VR_ACCOUNT_DETAILS;

	protected final static String SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY = "SQL_SELECT_ALL_ACC_WHERE_CLAUSE";

	protected static final String SQL_SELECT_ALL_ACC_WHERE_CLAUSE = DBQueries.SQL_SELECT_ALL_ACC_WHERE_CLAUSE;

	protected final static String SQL_SELECT_ALL_ACC_KEY = "SQL_SELECT_ALL_ACC";
//	Commented /Edited by Shilpa on 10/1/13 for the coming error while generating retailer due to not find out parent account

	//protected static final String SQL_SELECT_ALL_ACC = DBQueries.SQL_SELECT_ALL_ACC;
	
	protected static final String SQL_SELECT_ALL_ACC = DBQueries.SQL_SELECT_ALL_ACC_DIST;
	
//Ends here 
	protected static final String SQL_SELECT_ALL_ACC_H2_KEY = "SQL_SELECT_ALL_ACC_H2";
	
	protected static final String SQL_SELECT_ALL_ACC_H2 = DBQueries.SQL_SELECT_ALL_ACC_H2;

	protected final static String SQL_SELECT_ALL_ACC_COUNT_KEY = "SQL_SELECT_ALL_ACC_COUNT";

	protected static final String SQL_SELECT_ALL_ACC_COUNT = DBQueries.SQL_SELECT_ALL_ACC_COUNT;

	protected final static String SQL_SELECT_ACC_MOBILE_KEY = "SQL_SELECT_ACC_MOBILE";

	protected static final String SQL_SELECT_ACC_MOBILE = DBQueries.SQL_SELECT_ACC_MOBILE;

	// Removed Balance Details from Above Tables
	protected final static String SQL_SELECT_ACC_DIST_CHILD_KEY = "SQL_SELECT_ACC_DIST_CHILD";   

	protected static final String SQL_SELECT_ACC_DIST_CHILD = DBQueries.SQL_SELECT_ACC_DIST_CHILD;

	protected final static String SQL_SELECT_ACC_BY_MOBILE_KEY = "SQL_SELECT_ACC_BY_MOBILE";

	protected static final String SQL_SELECT_ACC_BY_MOBILE = DBQueries.SQL_SELECT_ACC_BY_MOBILE;

	protected final static String SQL_UPDATE_ACC_OERATINGBALANCE_KEY = "SQL_UPDATE_ACC_OERATINGBALANCE";

	protected static final String SQL_UPDATE_ACC_OERATINGBALANCE = DBQueries.SQL_UPDATE_ACC_OERATINGBALANCE;

	protected final static String SQL_CIRCLE_SELECT_WITH_ACCOUNTID_KEY = "SQL_CIRCLE_SELECT_WITH_ACCOUNTID";

	protected static final String SQL_CIRCLE_SELECT_WITH_ACCOUNTID = DBQueries.SQL_CIRCLE_SELECT_WITH_ACCOUNTID;

	protected final static String SQL_SELECT_DISTRIBUTOR_KEY = "SQL_SELECT_DISTRIBUTOR";

	protected static final String SQL_SELECT_DISTRIBUTOR = DBQueries.SQL_SELECT_DISTRIBUTOR;

	protected final static String SQL_SELECT_COMMISSION_ID_KEY = "SQL_SELECT_COMMISSION_ID";

	protected static final String SQL_SELECT_COMMISSION_ID = DBQueries.SQL_SELECT_COMMISSION_ID;

	protected final static String SQL_SELECT_PARENT_ACCOUNTID_KEY = "SQL_SELECT_PARENT_ACCOUNTID";

	protected static final String SQL_SELECT_PARENT_ACCOUNTID = DBQueries.SQL_SELECT_PARENT_ACCOUNTID;

	protected final static String SQL_IS_MOBILENO_EXIST_KEY = "SQL_IS_MOBILENO_EXIST";

	protected static final String SQL_IS_MOBILENO_EXIST = DBQueries.SQL_IS_MOBILENO_EXIST;
	
	protected final static String SQL_IS_MOBILENO_EXIST_ALL_KEY = "SQL_IS_MOBILENO_EXIST_ALL";

	protected static final String SQL_IS_MOBILENO_EXIST_ALL = DBQueries.SQL_IS_MOBILENO_EXIST_ALL;

	protected final static String SQL_OPENING_DATE_KEY = "SQL_OPENING_DATE";

	protected static final String SQL_OPENING_DATE = DBQueries.SQL_OPENING_DATE;

	// Query Used for Parent Account Pop-Up Page
	protected final static String SQL_SELECT_FOR_PARENT_ACC_KEY = "SQL_SELECT_FOR_PARENT_ACC";

	protected static final String SQL_SELECT_FOR_PARENT_ACC = DBQueries.SQL_SELECT_FOR_PARENT_ACC;

	protected final static String SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY = "SQL_SELECT_FOR_PARENT_ACC_COUNT";

	protected static final String SQL_SELECT_FOR_PARENT_ACC_COUNT = DBQueries.SQL_SELECT_FOR_PARENT_ACC_COUNT;

	protected final static String SQL_SELECT_ACCOUNT_LEVEL_KEY = "SQL_SELECT_ACCOUNT_LEVEL";

	protected static final String SQL_SELECT_ACCOUNT_LEVEL = DBQueries.SQL_SELECT_ACCOUNT_LEVEL;

	protected final static String SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN_KEY = "SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN";

	protected static final String SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN = DBQueries.SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN;

	protected final static String SQL_SELECT_DEFAULT_GROUP_ID_KEY = "SQL_SELECT_DEFAULT_GROUP_ID";

	protected static final String SQL_SELECT_DEFAULT_GROUP_ID = DBQueries.SQL_SELECT_DEFAULT_GROUP_ID;

	protected final static String SQL_SELECT_ALL_CIRCLES_KEY = "SQL_SELECT_ALL_CIRCLES";

	protected static final String SQL_SELECT_ALL_CIRCLES = DBQueries.SQL_SELECT_ALL_CIRCLES;

	protected final static String SQL_SELECT_CITES_KEY = "SQL_SELECT_CITES";

	protected static final String SQL_SELECT_CITES = DBQueries.SQL_SELECT_CITES;

	// select Zone

	protected final static String SQL_SELECT_ZONE_KEY = "SQL_SELECT_ZONES";
	
	protected static final String SQL_SELECT_ZONES = DBQueries.SQL_SELECT_ZONES;
	
	protected final static String SQL_SELECT_STATE_KEY = "SQL_SELECT_STATES";

	protected static final String SQL_SELECT_STATES = DBQueries.SQL_SELECT_STATES;

	protected final static String SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID_KEY = "SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID";

	protected static final String SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID = DBQueries.SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID;
	
	//added by arti 
	
	protected static final String SQL_INSERT_WH_DIST_MAP = DBQueries.SQL_INSERT_WH_DIST_MAP;
	
	protected final static String SQL_SELECT_BILLABLE_ACCOUNT_ID_KEY = "SQL_SELECT_BILLABLE_ACCOUNT_ID";

	protected static final String SQL_SELECT_BILLABLE_ACCOUNT_ID = DBQueries.SQL_SELECT_BILLABLE_ACCOUNT_ID;

	protected final static String SQL_SELECT_CIRCLE_NAME_KEY = "SQL_SELECT_CIRCLE_NAME";

	protected final static String SQL_SELECT_CIRCLE_NAME = DBQueries.SQL_SELECT_CIRCLE_NAME;

	protected final static String SQL_SELECT_ALL_ACC_NATIONAL_KEY = "SQL_SELECT_ALL_ACC_NATIONAL";

	protected final static String SQL_SELECT_ALL_ACC_NATIONAL = DBQueries.SQL_SELECT_ALL_ACC_NATIONAL;

	protected final static String SQL_SELECT_ACC_NATIONAL_KEY = "SQL_SELECT_ACC_NATIONAL";

	protected final static String SQL_SELECT_ACC_NATIONAL = DBQueries.SQL_SELECT_ACC_NATIONAL;

	protected final static String SQL_AGGREGATE_LIST_KEY = "SQL_AGGREGATE_LIST";

	protected final static String SQL_AGGREGATE_LIST = DBQueries.SQL_AGGREGATE_LIST;

	protected final static String SQL_AGGREGATE_COUNT_KEY = "SQL_AGGREGATE_COUNT";

	protected final static String SQL_AGGREGATE_COUNT = DBQueries.SQL_AGGREGATE_COUNT;

	protected final static String SQL_AGGREGATE_COUNT_FOR_DIST_KEY = "SQL_AGGREGATE_COUNT_FOR_DIST";

	protected final static String SQL_AGGREGATE_COUNT_FOR_DIST = DBQueries.SQL_AGGREGATE_COUNT_FOR_DIST;

	protected final static String SQL_AGGREGATE_LIST_EXCEL_KEY = "SQL_AGGREGATE_LIST_EXCEL";

	protected final static String SQL_AGGREGATE_LIST_EXCEL = DBQueries.SQL_AGGREGATE_LIST_EXCEL;

	protected final static String SQL_AGGREGATE_FOR_DIST_KEY = "SQL_AGGREGATE_FOR_DIST";

	protected final static String SQL_AGGREGATE_FOR_DIST = DBQueries.SQL_AGGREGATE_FOR_DIST;

	protected final static String SQL_EXCEL_FOR_DIST_KEY = "SQL_EXCEL_FOR_DIST";

	protected final static String SQL_EXCEL_FOR_DIST = DBQueries.SQL_EXCEL_FOR_DIST;

	protected final static String SQL_DISTRIBUTOR_TYPE_KEY = "SQL_DISTRIBUTOR_TYPE";

	protected final static String SQL_DISTRIBUTOR_TYPE = DBQueries.SQL_DISTRIBUTOR_TYPE;

	protected final static String SQL_DISTRIBUTOR_CATEGORY_KEY = "SQL_DISTRIBUTOR_CATEGORY";

	protected final static String SQL_DISTRIBUTOR_CATEGORY = DBQueries.SQL_DISTRIBUTOR_CATEGORY;

	protected final static String GET_DIST_LEVEL_DETAILS_KEY = "GET_DIST_LEVEL_DETAILS";
	
	protected final static String GET_DIST_LEVEL_DETAILS = DBQueries.GET_DIST_LEVEL_DETAILS;
	
	protected final static String GET_ACCOUNT_LEVEL_DETAILS_KEY = "GET_ACCOUNT_LEVEL_DETAILS";
	
	protected final static String GET_ACCOUNT_LEVEL_DETAILS = DBQueries.GET_ACCOUNT_LEVEL_DETAILS;
	
	protected final static String SQL_ACCOUNT_GROUP_ID_KEY = "SQL_ACCOUNT_GROUP_ID";
	
	protected final static String SQL_ACCOUNT_GROUP_ID = DBQueries.SQL_ACCOUNT_GROUP_ID;
	
	// method for web service
	protected final static String SQL_SELECT_ACCOUNT_KEY="SQL_SELECT_ACCOUNT";
	protected final static String SQL_SELECT_ACCOUNT = DBQueries.SQL_SELECT_ACCOUNT;
	protected final static String SQL_UPDATE_ACCOUNT_KEY="SQL_UPDATE_ACCOUNT";
	protected final static String SQL_UPDATE_ACCOUNT = DBQueries.SQL_UPDATE_ACCOUNT;

	// protected Connection connection = null;
	
	 protected final static String SQL_SELECT_REPORT_AUTHENTICATION_KEY = "SQL_SELECT_REPORT_AUTHENTICATION";
	 protected final static String SQL_SELECT_REPORT_AUTHENTICATION ="select report_ext_password from vr_group_details where group_id = ? with ur";

	 protected final static String SQL_SELECT_ALL_ACC_CHILD_KEY = "SQL_SELECT_ALL_ACC_CHILD";

	 protected static final String SQL_SELECT_ALL_ACC_CHILD = RDBMSQueries.SQL_SELECT_ALL_ACC_CHILD;
	 protected final static String SQL_SELECT_ALL_ACC_CHILD_DB2_KEY = "SQL_SELECT_ALL_ACC_CHILD_DB2";

	protected static final String SQL_SELECT_ALL_ACC_CHILD_DB2 = RDBMSQueries.SQL_SELECT_ALL_ACC_CHILD_DB2;
	
	protected final static String SQL_SELECT_FINANCE_KEY = "SQL_SELECT_FINANCE";
	protected final static String SQL_SELECT_FINANCE = DBQueries.SQL_SELECT_FINANCE;
	protected final static String SQL_SELECT_BEATS_KEY = "SQL_SELECT_BEATS";
	protected final static String SQL_SELECT_BEATS = DBQueries.SQL_SELECT_BEATS;
	
	protected final static String SQL_SELECT_RETAILER_CATEGORY_KEY = "SQL_SELECT_RETAILER_CATEGORY";
	protected final static String SQL_SELECT_RETAILER_CATEGORY = DBQueries.SQL_SELECT_RETAILER_CATEGORY;
	//fetch transaction type for retailer 
	protected final static String SQL_SELECT_RETAILER_TRANSACTION_TYPE_KEY = "SQL_SELECT_RETAILER_TRANSACTION";
	protected final static String SQL_SELECT_RETAILER_TRANSACTION = DBQueries.SQL_SELECT_RETAILER_TRANSACTION;
	protected final static String SQL_SELECT_OUTLET_CATEGORY_KEY = "SQL_SELECT_OUTLET_CATEGORY";
	protected final static String SQL_SELECT_OUTLET_CATEGORY = DBQueries.SQL_SELECT_OUTLET_CATEGORY; 
	
	protected final static String SQL_SELECT_ALT_CHANNEL_KEY = "SQL_SELECT_ALT_CHANNEL";
	protected final static String SQL_SELECT_ALT_CHANNEL = DBQueries.SQL_SELECT_ALT_CHANNEL;
	
	protected final static String SQL_SELECT_CHANNEL_KEY = "SQL_SELECT_CHANNEL";
	protected final static String SQL_SELECT_CHANNEL = DBQueries.SQL_SELECT_CHANNEL;
	
	// fetch email id for forget password
	protected final static String SQL_SELECT_EMAILID_KEY = "SQL_SELECT_EMAILID";
	protected final static String SQL_SELECT_EMAILID = DBQueries.SQL_SELECT_EMAILID;
	
	// Add on 8th August merging by harabns
	protected final static String SQL_IS_ALL_REATILER_MOBILENO_EDIT_MODE_EXIST_KEY = "SQL_IS_ALL_REATILER_MOBILENO_EDIT_MODE_EXIST";

	protected static final String SQL_IS_ALL_REATILER_MOBILENO_EDIT_MODE_EXIST = DBQueries.CHECK_ALL_INPUT_MOBILE_NO_EDIT_MODE_REATILER;
	
//	Add by harbans for retailer infomation.
	protected final static String SQL_IS_ALL_REATILER_MOBILENO_EXIST_KEY = "SQL_IS_ALL_REATILER_MOBILENO_EXIST";

	protected static final String SQL_IS_ALL_REATILER_MOBILENO_EXIST = DBQueries.CHECK_ALL_INPUT_MOBILE_NO_REATILER;

	//Added by Shilpa Khanna on 17-01-2012 
	//fetch Account Name for Inter SSD Stock Transfer by dth Admin to send Email to distributors
	protected final static String SQL_SELECT_LOGIN_NAME_KEY =DBQueries.SQL_SELECT_LOGIN_NAME;
	
	protected final static String SQL_SELECT_PWD_EXPIRY_KEY =DBQueries.SQL_SELECT_PWD_EXPIRY_KEY;
	
	protected final static String SQL_SELECT_IS_NEW_USER_KEY =DBQueries.SQL_SELECT_IS_NEW_USER_KEY;
	
	protected static final String SQL_SELECT_STATUS = DBQueries.SQL_SELECT_STATUS;
	//Added by Neetika for BFR 10 of Release 3 on 5 Aug 2013
	protected final static String SQL_DELETE_DISTRIBUTOR_MAP_KEY ="SQL_DELETE_DISTRIBUTOR_MAP"; //its not delete but update
	protected static final String SQL_DELETE_DISTRIBUTOR_MAP = DBQueries.SQL_DELETE_DISTRIBUTOR_MAP;//its not delete but update
	//protected final static String SQL_DELETE_DISTRIBUTOR_MAP_MAIN_KEY ="SQL_DELETE_DISTRIBUTOR_MAIN_MAP";
	//protected static final String SQL_DELETE_DISTRIBUTOR_MAIN_MAP = DBQueries.SQL_DELETE_DISTRIBUTOR_MAP_MAIN;
	protected final static String SQL_SELECT_DISTRIBUTOR_MAP_MAIN_KEY ="SQL_SELECT_DISTRIBUTOR_MAIN_MAP";
	protected static final String SQL_SELECT_DISTRIBUTOR_MAIN_MAP = DBQueries.SQL_SELECT_DISTRIBUTOR_MAIN_MAP;
	//protected final static String SQL_INSERT_DISTRIBUTOR_LOGIN_KEY ="SQL_INSERT_DISTRIBUTOR_LOGIN";
	//protected static final String SQL_INSERT_DISTRIBUTOR_LOGIN = DBQueries.SQL_INSERT_DISTRIBUTOR_LOGIN;
	protected final static String SQL_DELETE_DISTRIBUTOR_LOGIN_KEY ="SQL_DELETE_DISTRIBUTOR_LOGIN"; //its not delete but update
	protected static final String SQL_DELETE_DISTRIBUTOR_LOGIN = DBQueries.SQL_DELETE_DISTRIBUTOR_LOGIN;//its not delete but update
	//protected final static String SQL_INSERT_DISTRIBUTOR_ACCOUNT_KEY ="SQL_INSERT_DISTRIBUTOR_ACCOUNT";
	//protected static final String SQL_INSERT_DISTRIBUTOR_ACCOUNT = DBQueries.SQL_INSERT_DISTRIBUTOR_ACCOUNT;
	//protected final static String SQL_DELETE_DISTRIBUTOR_ACCOUNT_KEY ="SQL_DELETE_DISTRIBUTOR_ACCOUNT";
	//protected static final String SQL_DELETE_DISTRIBUTOR_ACCOUNT = DBQueries.SQL_DELETE_DISTRIBUTOR_ACCOUNT;
	
	protected static final String SQL_INSERT_ACC_DETAIL_HIST_IT_HELP_RET = DBQueries.SQL_INSERT_ACC_DETAIL_HIST_IT_HELP_RET;
	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public DPCreateAccountDaoImpl(Connection conn) {
		super(conn);
		queryMap.put(SQL_SELECT_ISACTIVE_KEY, SQL_SELECT_ISACTIVE);
		queryMap.put(SQL_SELECT_ACCESS_LEVEL_KEY, SQL_SELECT_ACCESS_LEVEL);
		queryMap.put(SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY,
				SQL_UPDATE_ACCOUNT_SOURCE_BALANCE);
		queryMap.put(SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE_KEY,
				SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE);
		queryMap.put(SQL_UPDATE_AVAILABLE_BALANCE_KEY,
				SQL_UPDATE_AVAILABLE_BALANCE);
		queryMap.put(SQL_UPDATE_OPERATING_BALANCE_KEY,
				SQL_UPDATE_OPERATING_BALANCE);
		queryMap.put(SQL_CHECK_UPDATE_OPERATING_BALANCE_KEY,
				SQL_CHECK_UPDATE_OPERATING_BALANCE);
		queryMap.put(SQL_SELECT_CHILD_ACCOUNT_KEY, SQL_SELECT_CHILD_ACCOUNT);
		queryMap.put(SQL_SELECT_ISCHILD_KEY, SQL_SELECT_ISCHILD);
		queryMap.put(SQL_SELECT_BALANCE_KEY, SQL_SELECT_BALANCE);
		queryMap.put(SQL_INSERT_DIST_ACCOUNT_KEY, SQL_INSERT_DIST_ACCOUNT);
		queryMap.put(SQL_INSERT_ACC_DIST_CHILD_KEY, SQL_INSERT_ACC_DIST_CHILD);
		queryMap.put(SQL_INSERT_ACC_BALANCE_KEY, SQL_INSERT_ACC_BALANCE);
		queryMap.put(SQL_SELECT_MOBILENO_KEY, SQL_SELECT_MOBILENO);
		queryMap.put(SQL_UPDATE_ACC_DETAIL_KEY, SQL_UPDATE_ACC_DETAIL);
		queryMap.put(SQL_UPDATE_ACC_BALANCE_KEY, SQL_UPDATE_ACC_BALANCE);
		queryMap.put(SQL_SELECT_ACCOUNT_ID_KEY, SQL_SELECT_ACCOUNT_ID);
		queryMap.put(SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE_KEY,
				SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE);
		queryMap.put(SQL_SELECT_LOGIN_ID_SAME_CIRCLE_KEY, SQL_SELECT_LOGIN_ID_SAME_CIRCLE);
		queryMap.put(SQL_SELECT_ACCOUNT_CODE_KEY, SQL_SELECT_ACCOUNT_CODE);
		queryMap.put(SQL_SELECT_ACCOUNT_WITH_CIRCLEID_KEY,
				SQL_SELECT_ACCOUNT_WITH_CIRCLEID);
		queryMap.put(SQL_SELECT_ACCOUNT_RATE_KEY, SQL_SELECT_ACCOUNT_RATE);
		queryMap.put(SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY,
				SQL_UPDATE_ACCOUNT_AVAILBALANCE);
		queryMap.put(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY,
				SQL_UPDATE_VR_ACCOUNT_DETAILS);
		queryMap.put(SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY,
				SQL_SELECT_ALL_ACC_WHERE_CLAUSE);
		queryMap.put(SQL_SELECT_ALL_ACC_KEY, SQL_SELECT_ALL_ACC);
		queryMap.put(SQL_SELECT_ALL_ACC_COUNT_KEY, SQL_SELECT_ALL_ACC_COUNT);
		queryMap.put(SQL_SELECT_ACC_DIST_CHILD_KEY, SQL_SELECT_ACC_DIST_CHILD);
		queryMap.put(SQL_SELECT_ACC_BY_MOBILE_KEY, SQL_SELECT_ACC_BY_MOBILE);
		queryMap.put(SQL_UPDATE_ACC_OERATINGBALANCE_KEY,
				SQL_UPDATE_ACC_OERATINGBALANCE);
		queryMap.put(SQL_CIRCLE_SELECT_WITH_ACCOUNTID_KEY,
				SQL_CIRCLE_SELECT_WITH_ACCOUNTID);
		queryMap.put(SQL_SELECT_COMMISSION_ID_KEY, SQL_SELECT_COMMISSION_ID);
		queryMap.put(SQL_SELECT_DISTRIBUTOR_KEY, SQL_SELECT_DISTRIBUTOR);
		queryMap.put(SQL_SELECT_PARENT_ACCOUNTID_KEY,
				SQL_SELECT_PARENT_ACCOUNTID);
		queryMap.put(SQL_IS_MOBILENO_EXIST_KEY, SQL_IS_MOBILENO_EXIST);
		queryMap.put(SQL_OPENING_DATE_KEY, SQL_OPENING_DATE);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_KEY, SQL_SELECT_FOR_PARENT_ACC);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY,
				SQL_SELECT_FOR_PARENT_ACC_COUNT);
		queryMap.put(SQL_SELECT_ACCOUNT_LEVEL_KEY, SQL_SELECT_ACCOUNT_LEVEL);
		queryMap.put(SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN_KEY,
				SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN);
		queryMap.put(SQL_SELECT_DEFAULT_GROUP_ID_KEY,
				SQL_SELECT_DEFAULT_GROUP_ID);
		queryMap.put(SQL_SELECT_ALL_CIRCLES_KEY, SQL_SELECT_ALL_CIRCLES);
		queryMap.put(SQL_SELECT_CITES_KEY, SQL_SELECT_CITES);
		queryMap.put(SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID_KEY,
				SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID);
		queryMap.put(SQL_SELECT_BILLABLE_ACCOUNT_ID_KEY,
				SQL_SELECT_BILLABLE_ACCOUNT_ID);
		queryMap.put(SQL_SELECT_ACC_MOBILE_KEY, SQL_SELECT_ACC_MOBILE);
		queryMap.put(SQL_SELECT_CIRCLE_NAME_KEY, SQL_SELECT_CIRCLE_NAME);
		queryMap.put(SQL_SELECT_ALL_ACC_NATIONAL_KEY,
				SQL_SELECT_ALL_ACC_NATIONAL);
		queryMap.put(SQL_SELECT_ACC_NATIONAL_KEY, SQL_SELECT_ACC_NATIONAL);
		queryMap.put(SQL_SELECT_PARENT_TRADE_KEY, SQL_SELECT_PARENT_TRADE);
		// Aggregate View
		queryMap.put(SQL_AGGREGATE_LIST_KEY, SQL_AGGREGATE_LIST);
		queryMap.put(SQL_AGGREGATE_COUNT_KEY, SQL_AGGREGATE_COUNT);
		queryMap.put(SQL_AGGREGATE_LIST_EXCEL_KEY, SQL_AGGREGATE_LIST_EXCEL);
		queryMap.put(SQL_AGGREGATE_FOR_DIST_KEY, SQL_AGGREGATE_FOR_DIST);
		queryMap.put(SQL_AGGREGATE_COUNT_FOR_DIST_KEY,
				SQL_AGGREGATE_COUNT_FOR_DIST);
		queryMap.put(SQL_EXCEL_FOR_DIST_KEY, SQL_EXCEL_FOR_DIST);
		// zone selection
		queryMap.put(SQL_SELECT_ZONE_KEY, SQL_SELECT_ZONES);
		
		queryMap.put(SQL_SELECT_STATE_KEY, SQL_SELECT_STATES);
		// distributor type
		queryMap.put(SQL_DISTRIBUTOR_TYPE_KEY, SQL_DISTRIBUTOR_TYPE);
		queryMap.put(SQL_DISTRIBUTOR_CATEGORY_KEY, SQL_DISTRIBUTOR_CATEGORY);
		
		queryMap.put(GET_DIST_LEVEL_DETAILS_KEY, GET_DIST_LEVEL_DETAILS);
		queryMap.put(GET_ACCOUNT_LEVEL_DETAILS_KEY, GET_ACCOUNT_LEVEL_DETAILS);
		queryMap.put(SQL_ACCOUNT_GROUP_ID_KEY, SQL_ACCOUNT_GROUP_ID);
		queryMap.put(SQL_IS_MOBILENO_EXIST_ALL_KEY, SQL_IS_MOBILENO_EXIST_ALL);
		queryMap.put(SQL_SELECT_ACCOUNT_KEY, SQL_SELECT_ACCOUNT);
		queryMap.put(SQL_UPDATE_ACCOUNT_KEY, SQL_UPDATE_ACCOUNT);
		queryMap.put(SQL_SELECT_ALL_ACC_H2_KEY, SQL_SELECT_ALL_ACC_H2);
		queryMap.put(SQL_SELECT_ACCOUNTID_KEY, SQL_SELECT_ACOUNTID);
		queryMap.put(SQL_SELECT_REPORT_AUTHENTICATION_KEY, SQL_SELECT_REPORT_AUTHENTICATION);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_KEY, SQL_SELECT_ALL_ACC_CHILD);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_DB2_KEY, SQL_SELECT_ALL_ACC_CHILD_DB2);
		queryMap.put(SQL_SELECT_FINANCE_KEY, SQL_SELECT_FINANCE);
		queryMap.put(SQL_SELECT_BEATS_KEY, SQL_SELECT_BEATS);
		queryMap.put(SQL_SELECT_RETAILER_CATEGORY_KEY, SQL_SELECT_RETAILER_CATEGORY);
		queryMap.put(SQL_SELECT_OUTLET_CATEGORY_KEY, SQL_SELECT_OUTLET_CATEGORY);
		queryMap.put(SQL_SELECT_ALT_CHANNEL_KEY, SQL_SELECT_ALT_CHANNEL);
		queryMap.put(SQL_SELECT_CHANNEL_KEY, SQL_SELECT_CHANNEL);
		queryMap.put(SQL_SELECT_EMAILID_KEY,SQL_SELECT_EMAILID);
		queryMap.put(SQL_IS_ALL_REATILER_MOBILENO_EDIT_MODE_EXIST_KEY,SQL_IS_ALL_REATILER_MOBILENO_EDIT_MODE_EXIST);
		queryMap.put(SQL_IS_ALL_REATILER_MOBILENO_EXIST_KEY,SQL_IS_ALL_REATILER_MOBILENO_EXIST);
		queryMap.put(SQL_DELETE_DISTRIBUTOR_MAP_KEY, SQL_DELETE_DISTRIBUTOR_MAP);
		//queryMap.put(SQL_DELETE_DISTRIBUTOR_MAP_MAIN_KEY ,SQL_DELETE_DISTRIBUTOR_MAIN_MAP);
		queryMap.put(SQL_SELECT_DISTRIBUTOR_MAP_MAIN_KEY, SQL_SELECT_DISTRIBUTOR_MAIN_MAP);
		//queryMap.put(SQL_INSERT_DISTRIBUTOR_LOGIN_KEY,SQL_INSERT_DISTRIBUTOR_LOGIN);
		queryMap.put(SQL_DELETE_DISTRIBUTOR_LOGIN_KEY ,SQL_DELETE_DISTRIBUTOR_LOGIN);
		//queryMap.put(SQL_INSERT_DISTRIBUTOR_ACCOUNT_KEY,SQL_INSERT_DISTRIBUTOR_ACCOUNT);
		//queryMap.put(SQL_DELETE_DISTRIBUTOR_ACCOUNT_KEY ,SQL_DELETE_DISTRIBUTOR_ACCOUNT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#updateAccountBalance(com.ibm.virtualization..recharge.dto.Account)
	 */
	public void updateAccountBalance(DPCreateAccountDto accountDto)
			throws DAOException {
		PreparedStatement ps = null;

		//logger.info("Started ..." + accountDto);

		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY));
			ps.setLong(1, accountDto.getUpdatedBy());
			ps.setLong(2, accountDto.getParentAccountId());

			int i = ps.executeUpdate();
			ps.clearParameters();

			if (i == 0) {
				logger
						.error("Error While Updating Account Balance in VR_ACCOUNT_DETAILS  ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_INSUFFICIENT_OPERATING_BAL);
			}
			if(accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID){	
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_ACC_OERATINGBALANCE_KEY));
			ps.setDouble(1, accountDto.getOpeningBalance());
			ps.setDouble(2, accountDto.getOpeningBalance());
			ps.setDouble(3, accountDto.getOpeningBalance());
			ps.setLong(4, accountDto.getParentAccountId());

			i = ps.executeUpdate();
			//logger.info("No. Of Rows Updated For account Balance :-" + i);

			if (i == 0) {
				logger
						.error(" insufficient Account  Balance While Updating Account Balance in VR_ACCOUNT_DETAILS Table ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_INSUFFICIENT_OPERATING_BAL);
			}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("Sql Exception occured while Updating Balance ."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement, resultset */
			DBConnectionManager.releaseResources(ps, null);

		}
		//logger.info("Executed ...SuccessFully Updated Account balance ");
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
		//logger.info("Started ...creditedAmt:" + creditedAmt
		//		+ " And  accountId: " + accountId + " And  updatedBy: "
		//		+ updatedBy);
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY));
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
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY));
			ps.setDouble(1, creditedAmt);
			ps.setLong(2, accountId);
			i = ps.executeUpdate();
			if (i == 0) {
				logger.error("Error While Updating VR_ACCOUNT_BALANCE Table ");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
			}

			//logger.info("Successfully updated account balance  rows updated  "
				//	+ i);

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("Exception occured while Updating account balance."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
		/* Close the statement, resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed ...");

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
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		//logger.info("Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_DISTRIBUTOR_KEY));
			ps.setInt(1, circleId);
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setAccountId(rs.getLong("ACCOUNT_ID"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountList.add(accountDto);
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
		//logger.info("Executed ... ");
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
		String status="";
		//logger.info(" Started ...");
		try {
			/*ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACCOUNT_ID_KEY));*/
			
			ps = connection.prepareStatement(DBQueries.SQL_SELECT_ACCOUNT_ID_ALL);
			
			ps.setString(1, accountCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				status = rs.getString("STATUS");
				if(status != null && status.equalsIgnoreCase(Constants.ACTIVE_STATUS))
				{
					accountId = rs.getLong("LOGIN_ID");
				}
				else
				{
					accountId = -2;
				}
			}

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while getting Account Id  "
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
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
		//logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACCOUNT_ID_SAME_CIRCLE_KEY));
			ps.setString(1, accountCode);
			//ps.setInt(2, circleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				accountId = rs.getLong("LOGIN_ID");
			} else{
				//logger.info(accountCode
				//		+ " Account doesnot belong to same circle. ");
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
		//logger.info("Executed ...");
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
		String accountCode = "";
		//logger.info("Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACCOUNT_CODE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
			// changed from Login_name to Account_name	
				accountCode = rs.getString("ACCOUNT_NAME");
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
		//logger.info("Executed ...Successfully retrieved Account Code");
		return accountCode;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountCode(long)
	 */
	public String getAccountCodeDist(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String accountCode = "";
		//logger.info("Started ...");
		try 
		{
			ps = connection.prepareStatement(DBQueries.DIST_PARENT_DETAILS);
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
			// changed from Login_name to Account_name	
				accountCode = rs.getString("ACCOUNT_NAME");
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
		//logger.info("Executed ...Successfully retrieved Account Code");
		return accountCode;
	}
	
	public String getAccountLoginCodeDist(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String loginCode = "";
		//logger.info("Started ...");
		try {
			ps = connection.prepareStatement(DBQueries.DIST_PARENT_DETAILS);
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
			// changed from Login_name to Account_name	
				loginCode = rs.getString("LOGIN_NAME");
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
		//logger.info("Executed ...Successfully retrieved Account Code");
		return loginCode;
	}
	
	private long getParentAccountIDDist(long accountId) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int parentAccount = 0;
		//logger.info("Started ...");
		try {
			ps = connection.prepareStatement(DBQueries.DIST_PARENT_DETAILS);
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
			// changed from Login_name to Account_name	
				parentAccount = rs.getInt("PARENT_ACCOUNT");
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
		//logger.info("Executed ...Successfully retrieved Account Code");
		return parentAccount;
	}
	public String getAccountLoginCode(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String loginCode = "";
		//logger.info("Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACCOUNT_CODE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
			// changed from Login_name to Account_name	
				loginCode = rs.getString("LOGIN_NAME");
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
		//logger.info("Executed ...Successfully retrieved Account Code");
		return loginCode;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#insertAccountData(com.ibm.virtualization.recharge.dto.Account)
	 */
	public void insertAccount(DPCreateAccountDto accountDto) throws DAOException {
		//logger.info("Started ..." + accountDto);
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			logger.info("**********************************inside insertAccount***************");
			//SQL_INSERT_ACC_DETAIL_KEY
			ps = connection.prepareStatement(queryMap
					.get(SQL_INSERT_ACC_DIST_CHILD_KEY));
			
			int paramCount = 1;
			
			ps.setLong(paramCount++, accountDto.getAccountId());
			
			if(accountDto.getAccountLevelId() == 9) // Retailer
			{
				ps.setString(paramCount++, accountDto.getRetailerMobileNumber());
			}
			else
			{
				ps.setString(paramCount++, accountDto.getMobileNumber());
			}
			//ps.setString(paramCount++, accountDto.getMobileNumber());
			if(accountDto.getSimNumber().length()!=0){
				ps.setString(paramCount++, accountDto.getSimNumber());
			}
			else
				ps.setString(paramCount++, null);
			ps.setString(paramCount++, accountDto.getAccountName());
			ps.setString(paramCount++, accountDto.getAccountAddress());
			if(accountDto.getEmail().length()!=0){
				ps.setString(paramCount++, accountDto.getEmail());
			}
			else
				ps.setString(paramCount++, null);
			ps.setLong(paramCount++, accountDto.getCircleId());
			
			if (!((accountDto.getAccountLevelId() == Constants.FSE_ID) || (accountDto.getAccountLevelId() == Constants.MOBILITY_ID))) {
				ps.setLong(paramCount++, accountDto.getParentAccountId());
				ps.setLong(paramCount++, accountDto.getRootLevelId());
			}
			else {
					ps.setLong(paramCount++, accountDto.getParentAccountId());
					ps.setLong(paramCount++, accountDto.getParentAccountId());
			}
			if (accountDto.getOpeningDt() != null
					&& accountDto.getOpeningDt().trim().length() != 0) {
				ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
						accountDto.getOpeningDt()).getTime()));// opening
			} else {
				ps.setDate(paramCount++, new java.sql.Date(new java.util.Date()
						.getTime()));// opening
			}
			// date
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, accountDto.getCreatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, accountDto.getUpdatedBy());
			ps.setString(paramCount++, accountDto.getAccountLevel());

			// if billable not accountDto
			
			//ps.setInt(paramCount++, accountDto.getCircleId());
			ps.setInt(paramCount++, accountDto.getAccountStateId());
			if(accountDto.getCircleId() !=0){
			ps.setInt(paramCount++, accountDto.getAccountZoneId());
			ps.setInt(paramCount++, accountDto.getAccountCityId());
			}else{
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
			}
			ps.setInt(paramCount++, accountDto.getAccountPinNumber());
			ps.setString(paramCount++, accountDto.getAccountAddress2());
// if accountDto is to be created for Distriobutor and children.			
			if (accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID){
				ps.setString(paramCount++, accountDto.getIsbillable());
		if(accountDto.getIsbillable().equalsIgnoreCase("N")){		
					ps.setLong(paramCount++, accountDto.getBillableCodeId());
		}else{
				ps.setNull(paramCount++, Types.INTEGER);
			}
				
			if(accountDto.getAccountLevelId() == 9) // Retailer
			{
				ps.setString(paramCount++, accountDto.getMobile1());
				ps.setString(paramCount++, accountDto.getMobile2());
				ps.setString(paramCount++, accountDto.getMobile3());
				ps.setString(paramCount++, accountDto.getMobile4());
			}
			else
			{
				ps.setString(paramCount++, accountDto.getLapuMobileNo());
				ps.setString(paramCount++, accountDto.getLapuMobileNo1());
				ps.setString(paramCount++, accountDto.getFTAMobileNo());
				ps.setString(paramCount++, accountDto.getFTAMobileNo1());
			}
			
			ps.setInt(paramCount++, accountDto.getTradeId());
			ps.setInt(paramCount++, accountDto.getTradeCategoryId());
			ps.setInt(paramCount++, accountDto.getBeatId());
			ps.setInt(paramCount++, Integer.parseInt(accountDto.getOutletType()));
			} else {
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
			}
// Commerce, Finance and Sales id are removed			
			ps.setString(paramCount++, accountDto.getContactName());
			if(accountDto.getCode().equalsIgnoreCase(null)  || accountDto.getCode().equalsIgnoreCase("")){
				ps.setString(paramCount++, null);
			}else{
				ps.setString(paramCount++, accountDto.getCode());
			}
// Added four new fields : Retailer type, outlet type, Channel type and Alternate Channel Type			
			if(accountDto.getAccountLevelId() == Constants.RETAILER_ID){
				ps.setInt(paramCount++, Integer.parseInt(accountDto.getRetailerType()));
				ps.setInt(paramCount++, Integer.parseInt(accountDto.getAltChannelType()));
				ps.setInt(paramCount++, Integer.parseInt(accountDto.getChannelType()));
				ps.setString(paramCount++, accountDto.getCategory());
			}else{
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setString(paramCount++, null);
			}
			if(accountDto.getAccountLevelId() == Constants.DISTRIBUTOR_ID){
				ps.setString(paramCount++, accountDto.getOctroiCharge());
				ps.setString(paramCount++, accountDto.getTinNo());
				ps.setString(paramCount++, accountDto.getServiceTax());
				ps.setString(paramCount++, accountDto.getPanNo());
				ps.setString(paramCount++, accountDto.getCstNo());
				// Insert for STATE value here..
				
				
				
				if (accountDto.getCstDt() != null	&& accountDto.getCstDt().trim().length() != 0) 
				{
					ps.setDate(paramCount++, Utility.getSqlDateFromString(accountDto.getCstDt().trim(), "MM/dd/yyyy"));// opening
				} 
				else 
				{
					ps.setDate(paramCount++, new java.sql.Date(new java.util.Date().getTime()));// CST
				}
				
				if (accountDto.getTinDt() != null	&& accountDto.getTinDt().trim().length() != 0) 
				{
					ps.setDate(paramCount++, Utility.getSqlDateFromString(accountDto.getTinDt().trim(), "MM/dd/yyyy"));// opening
				} 
				else 
				{
					ps.setDate(paramCount++, new java.sql.Date(new java.util.Date().getTime()));// TIN
				}
				
//				 Added for Oracle SCM Integration
				if (accountDto.getDistLocator() != null && !accountDto.getDistLocator().trim().equals(""))
				{
					accountDto.setDistLocator(accountDto.getDistLocator().trim());
					
				}
				ps.setString(paramCount++, accountDto.getDistLocator());
				
				//ps.setInt(paramCount, accountDto.getSwLocatorId());		
//				saumya
				ps.setString(paramCount++, accountDto.getRepairFacility());
				//end
//				ps.setString(paramCount, accountDto.getReportingEmail());
			}else{
				ps.setString(paramCount++, "0");
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, Constants.OPTION_NO);
				//ps.setString(paramCount, null);
//				ps.setString(paramCount, null);
			}
			
			System.out.println("accountDto.getAccountLevelId():::::"+accountDto.getAccountLevelId());
			System.out.println(accountDto.getAccessLevelId());
			System.out.println(accountDto.getAccountLevel());
			System.out.println(accountDto.getGroupId());
			if(accountDto.getAccountLevelId() == Constants.ACC_LEVEL_RETAILER_ID)
			{
				
				ps.setInt(paramCount++,Integer.parseInt(accountDto.getTransactionId()));
				logger.info("***********************transaction type*******"+(accountDto.getTransactionId()));
				String strDemoId1 =  accountDto.getDemoId1();
				String strDemoId2 =  accountDto.getDemoId2();
				String strDemoId3 =  accountDto.getDemoId3();
				String strDemoId4 =  accountDto.getDemoId4();
				if(strDemoId1==null)
					strDemoId1="";
				if(strDemoId2==null)
					strDemoId2="";
				if(strDemoId3==null)
					strDemoId3="";
				if(strDemoId4==null)
					strDemoId4="";
				
				String strDemoIDs = strDemoId1+","+strDemoId2+","+strDemoId3+","+strDemoId4;
				
				ps.setString(paramCount++, strDemoIDs);
				ps.setString(paramCount++, accountDto.getRetailerMobileNumber());
			}
			else
			{
				ps.setInt(paramCount++, Constants.TRANSACTION_TYPE_DEPOSIT);
				ps.setString(paramCount++, "");
				ps.setString(paramCount++, "");
			}
			
			ps.executeUpdate();
			logger.info(" Executed ...Created accountDto Details. ");
			// clear bind parameter for child insert
//			ps.clearParameters();
			// inserting values into second VR_ACCOUNT_BALANCE Table
/*			if(accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID){		
			ps = connection.prepareStatement(queryMap
					.get(SQL_INSERT_ACC_BALANCE_KEY));
			paramCount = 1;
			ps.setLong(paramCount++, accountDto.getAccountId());
			ps.setDouble(paramCount++, accountDto.getOpeningBalance());
			ps.setDouble(paramCount++, accountDto.getRate());
			ps.setDouble(paramCount++, accountDto.getThreshold());
			ps.setDouble(paramCount++, accountDto.getOperatingBalance());
			ps.setDouble(paramCount, accountDto.getOpeningBalance());

			ps.executeUpdate();
			connection.commit();
	}	*/
			
				if(accountDto.getAccountLevelId() == Constants.DISTRIBUTOR_ID){
					
					ps1 = connection.prepareStatement("INSERT INTO DP_OPEN_STOCK_DTL( ID,DIST_ID, DIST_ORACLE_LOCATOR_CODE, PRODUCT_ID, ORACLE_PRODUCT_CODE, OPENING_STOCK, SALE, CLOSING_STOCK, " 
							+" LAST_UPDATE_DATE, PRODUCT_TYPE, PARENT_PRODUCT_ID, RECO_QTY, inv_change) VALUES"
							+" ( ?,?,?,?,(select ORACLE_ITEM_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=?), 0, 0, 0, null, (select PRODUCT_TYPE from DP_PRODUCT_MASTER where PRODUCT_ID=?),"
							+" (select PARENT_PRODUCT_ID from DP_PRODUCT_MASTER where PRODUCT_ID=?), 0, 0) ");
					
					ps2 = connection.prepareStatement("select PRODUCT_ID from DP_PRODUCT_MASTER where CIRCLE_ID=? and CM_STATUS='A' and CATEGORY_CODE=1 with ur");
					ps1.setInt(2,(int) accountDto.getAccountId());
					if (accountDto.getDistLocator() != null && !accountDto.getDistLocator().trim().equals(""))
					{
						accountDto.setDistLocator(accountDto.getDistLocator().trim());
						
					}
					ps1.setString(3, accountDto.getDistLocator());
					ps2.setLong(1, accountDto.getCircleId());
				int productId;
				List<String> productString = new ArrayList<String>();
				
				ResultSet rs2 = ps2.executeQuery();			
				while (rs2.next()) {
					productId = rs2.getInt("PRODUCT_ID");
					productString.add(String.valueOf(productId));
					
				}
				
				//productString.deleteCharAt(productString.length()-1);
				
				
				
				ps.clearParameters();
				String sql = "SELECT ID FROM DP_OPEN_STOCK_DTL  ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY";
				
				
				ps = connection.prepareStatement(sql);
				//logger.info(sql);
				int id =0;
				
				ResultSet rs = ps.executeQuery();			
				while (rs.next()) {
					 id = rs.getInt("ID");					
				}
				id++;		
				for (String string : productString) {
					 ps1.setInt(1, id);
					 int token=Integer.valueOf(string);
					 ps1.setInt(4,token );
					 ps1.setInt(5, token);
					 ps1.setInt(6, token);
					 ps1.setInt(7, token);
					 ps1.addBatch();
					 id++;
				}
				
				int[] numRows;
				numRows = ps1.executeBatch();
				
				}
			
			
			
			//logger.info(" Executed ...Created Account Balance Details. ");
		} catch (SQLException e) {
			
			e.printStackTrace();
			while (e != null) 
			{
				logger.info("SQL State:" + e.getSQLState());
				logger.info("Error Code:" + e.getErrorCode());
				logger.info("Message:" + e.getMessage());
				
				logger.error("SQL State:" + e.getSQLState());
				logger.error("Error Code:" + e.getErrorCode());
				logger.error("Message:" + e.getMessage());
				Throwable t = e.getCause();
				while (t != null) 
				{
					logger.error("Cause:" + t);
					logger.info("Cause:" + t);
					t = t.getCause();
				}
				e = e.getNextException();
			}
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error(
					"SQL Exception occured while inserting.Into Account Tables"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		 finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
			DBConnectionManager.releaseResources(ps1, null);
			DBConnectionManager.releaseResources(ps2, null);
		}
		//logger.info("Executed ...New Account Successfully Created ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountList(java.lang.String,
	 *      java.lang.String, int)
	 */
	public ArrayList getAccountList(ReportInputs reportInputDto, int lb, int ub)
			throws DAOException,
			NumberFormatException,
			com.ibm.virtualization.recharge.exception.VirtualizationServiceException {
		//logger.info("Started ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		int circleId = reportInputDto.getCircleId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		String circleName = null;
		int levelId = Integer.parseInt(reportInputDto.getSessionContext().getAccountLevel());
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder query = new StringBuilder();
			int paramCount = 1;

			if (circleId != 0) {
				circleName = getCircleName(circleId);
				if (circleName.equalsIgnoreCase("NATIONAL")) {
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_H2_KEY));
					sql.append(" AND VLM.STATUS = ? ");
				} else {
					// if circle user, show records of respective circle
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_H2_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND VLM.STATUS = ? ");
				}
			} else {
				// if Administrator , show records from all circles
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_H2_KEY));
				sql.append(" AND VLM.STATUS = ? ");
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
					sql.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				} 
				if ((0 != circleId)
						&& (circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim()
								.equalsIgnoreCase(Constants.SEARCH_TYPE_ALL))) {
					// If user selects search type as ALL
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				} else if ((circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim() != Constants.SEARCH_TYPE_ALL)) {
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
				}

				query.append("SELECT * FROM(SELECT a.*,ROWNUM rnum FROM (");
				query.append(sql);
				query.append(") a Where ROWNUM<=?)Where rnum>=?");
				ps = connection.prepareStatement(query.toString());
				ps.setInt(paramCount++, levelId);
				ps.setInt(paramCount++, levelId);
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}
				ps.setString(paramCount++, String.valueOf(ub));
				ps.setString(paramCount++, String.valueOf(lb + 1));
			}
			rs = ps.executeQuery();
			int count=0;
			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				count++;
				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setThreshold(rs.getDouble("THRESHOLD"));

				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRowNum(rs.getString("RNUM"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedBy(rs.getLong("CREATED_BY"));
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				int numberOfPwdHistory = Integer
						.parseInt(ResourceReader
								.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));
				if (rs.getInt("LOGIN_ATTEMPTED") == numberOfPwdHistory) {
					accountDto.setLocked(true);
				} else {
					accountDto.setLocked(false);
				}
				// Anju
				if (accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID) {
					accountDto.setLapuMobileNo(rs.getString("LAPU_MOBILE_NO"));
				} else {
					accountDto.setLapuMobileNo("NA");

				}
				// accountDto.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(accountDto);
			}
			

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed ...");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getChildAccountList(java.lang.String,
	 *      java.lang.String, long)
	 */
	public ArrayList getChildAccountList(ReportInputs reportInputDto) throws DAOException {

		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		long parentId = reportInputDto.getParentId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int circleId = reportInputDto.getCircleId();
		long accountId = reportInputDto.getSessionContext().getId();
		//logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		StringBuilder query = new StringBuilder();
		int paramCount = 1;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY)
						+ " AND VLM.STATUS = ? AND DETAILS.CIRCLE_ID= "+circleId);
			}
			boolean roleName = reportInputDto.getSessionContext().getRoleList().contains("ROLE_AD");
			if(roleName){
				sql.append(" and DETAILS.PARENT_ACCOUNT= ? OR DETAILS.ROOT_LEVEL_ID= ? ");
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
					sql.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				}else if (searchType.trim().equalsIgnoreCase(
						 Constants.SEARCH_TYPE_ALL)) {
					 sql.append("");
				 }
				sql
						.append(" START WITH DETAILS.ACCOUNT_ID = ? CONNECT BY PRIOR DETAILS.ACCOUNT_ID = DETAILS.PARENT_ACCOUNT ");
				query.append("SELECT * FROM(SELECT a.*,ROWNUM rnum FROM (");
				query.append(sql);
				query.append(") a )  with ur");
				ps = connection.prepareStatement(query.toString());
				if(roleName){
					ps.setLong(paramCount++, accountId);
					ps.setLong(paramCount++, accountId);
				}
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}
				ps.setLong(paramCount++, parentId);
				/*ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));*/
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("ACC_PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRowNum(rs.getString("RNUM"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				// Anju
				if (accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID) {
					accountDto.setLapuMobileNo(rs.getString("LAPU_MOBILE_NO"));
				} else {
					accountDto.setLapuMobileNo("NA");

				}
				accountDto.setOutletType(rs.getInt("OUTLET_TYPE")+"");
				// accountDto.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(accountDto);
			}
		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountLevelAssignedList(long)
	 */
	public ArrayList getAccessLevelAssignedList(long groupId)
			throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountAccessLevel accountAccessLevel = null;
		ArrayList<AccountAccessLevel> accountLevelList = new ArrayList<AccountAccessLevel>();
		logger.info("getAccessLevelAssignedList ="+queryMap
				.get(SQL_SELECT_ACCOUNT_LEVEL_KEY));
		try {
			// ps = connection.prepareStatement(SQL_SELECT_ACCESS_LEVEL);`
			if (groupId != 1) {
				ps = connection.prepareStatement(queryMap
						.get(SQL_SELECT_ACCOUNT_LEVEL_KEY));
				ps.setLong(1, groupId);
				ps.setLong(2, groupId);
				ps.setLong(3, groupId);
			} else {
				ps = connection.prepareStatement(queryMap
						.get(SQL_SELECT_ACCOUNT_LEVEL_FOR_ADMIN_KEY));
				ps.setLong(1, groupId);
			}
			rs = ps.executeQuery();
			int count=0;
			boolean skipFin =false;
			while (rs.next()) {
				if(!((skipFin==true) && rs.getInt("LEVEL_ID")>12))
				{
				accountAccessLevel = new AccountAccessLevel();

				// new change group name added
				accountAccessLevel.setAccessLevelId(rs.getInt("LEVEL_ID"));
				accountAccessLevel.setAccountLevelName(rs
						.getString("LEVEL_NAME"));
				accountAccessLevel.setAccountAccessLevel(rs
						.getString("ACC_LEVEL"));
				accountAccessLevel.setHeirarchyId(rs.getString("HIERARCHY_ID"));
				accountLevelList.add(accountAccessLevel);
				}
				count=count+1;
				if(rs.getInt("LEVEL_ID")==7)
				{
					
					if(count==1)
					{
						logger.info("Loged in from dist..");
						skipFin=true;
					}
				}
				
				
			}
		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving.Account Level List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch (Exception e) {
			logger.error(
					"Exception occured while reteriving.Account Level List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed accountLevel ...");
		return accountLevelList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccount(long)
	 */
	public DPCreateAccountDto getAccount(long accountId) throws DAOException {
		//logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strRetDemoIDS=null;
		String[] arrRetDemoIDS=null;
		DPCreateAccountDto accountDto = null;
		StringTokenizer st = null;
		try 
		{
			//logger.info("Query to get Initial Login Details  ::  "+queryMap.get(SQL_SELECT_ACC_KEY));
			//logger.info("Account ID  ::  "+accountId);
			logger.info("**********************inside getAccount details*******************");
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACC_DIST_CHILD_KEY));
			ps.setInt(1, (int)accountId);
			ps.setInt(2, (int)accountId);
			ps.setInt(3, (int)accountId);
			ps.setInt(4, (int)accountId);
			ps.setInt(5, (int)accountId);
			ps.setInt(6, (int)accountId);
			rs = ps.executeQuery();

			if (rs.next()) {
				accountDto = new DPCreateAccountDto();
				logger.info("in get Account got the list for accountId =="+accountId);
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setRetailerMobileNumber(rs.getString("RETAILER_LAPU"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setUserType(rs.getString("TYPE"));//added by rohit

				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setCircleId(rs.getInt("CIRCLE_ID"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				// parent code according to id
//				if (rs.getString("PARENT_ACCOUNT") != null){
//					accountDto.setParentAccount(getAccountCode(rs.getInt("PARENT_ACCOUNT")));
//					accountDto.setParentLoginName(getAccountLoginCode(rs.getInt("PARENT_ACCOUNT")));
//				}
				
				String strParentID = rs.getString("PARENT_ACCOUNT");
				// parent code according to id
				if ( strParentID!= null && !strParentID.trim().equals("-1"))
				{
					accountDto.setParentAccountId(rs.getLong("PARENT_ACCOUNT"));
					accountDto.setParentAccount(getAccountCode(rs.getInt("PARENT_ACCOUNT")));
					accountDto.setParentLoginName(getAccountLoginCode(rs.getInt("PARENT_ACCOUNT")));
				}
				else if ( strParentID!= null && strParentID.trim().equals("-1"))
				{
					accountDto.setParentAccount(getAccountCodeDist(rs.getInt("ACCOUNT_ID")));
					accountDto.setParentLoginName(getAccountLoginCodeDist(rs.getInt("ACCOUNT_ID")));
					accountDto.setParentAccountId(getParentAccountIDDist(rs.getInt("ACCOUNT_ID")));
				}
				
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setAccountStateId(rs.getInt("STATE_ID"));
				accountDto.setAccountWarehouseCode(rs.getString("WHCODE"));
				accountDto.setAccountWarehouseName(rs.getString("WH_NAME"));
				
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setMPassword(rs.getString("M_PASSWORD"));
				accountDto.setGroupId(rs.getInt("GROUP_ID"));
				accountDto.setIsbillable(rs.getString("BILLABLE_ACCOUNT"));
				accountDto.setAccountPinNumber(rs.getInt("PIN_NUMBER"));
				accountDto.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				accountDto.setRootLevelId(rs.getLong("ROOT_LEVEL_ID"));
				accountDto.setCircleId(rs.getInt("CIRCLE_ID"));
				accountDto.setAccountLevel(rs.getString("ACCOUNT_LEVEL"));
				accountDto.setLevelName(rs.getString("LEVEL_NAME"));
				accountDto.setIPassword(rs.getString("PASSWORD"));
				accountDto.setAccountLevelId(rs.getInt("ACC_LEVEL"));
				accountDto.setHeirarchyId(rs.getInt("HIERARCHY_ID"));
				accountDto.setContactName(rs.getString("CONTACT_NAME"));
				accountDto.setAccountAddress2(rs.getString("ADDRESS_2"));
				accountDto.setIsDisable(rs.getString("IS_DISABLE"));
				
				if(rs.getString("ACCOUNT_CODE")==null||rs.getString("ACCOUNT_CODE").equalsIgnoreCase("")){
					accountDto.setCode("NA");
				}else{
					accountDto.setCode(rs.getString("ACCOUNT_CODE"));
				}
				accountDto.setAccountLevelName(rs.getString("LEVEL_NAME"));
				if (accountDto.getCircleId() != 0) {
					accountDto.setAccountZoneId(rs.getInt("ZONE_ID"));
					accountDto.setAccountCityId(rs.getInt("CITY_ID"));
					accountDto.setAccountZoneName(rs.getString("ZONE_NAME"));
					accountDto.setAccountCityName(rs.getString("CITY_NAME"));
				}
				if (rs.getString("BILLABLE_ACC_ID") != null) {
					accountDto.setBillableCode(getAccountCode(rs
							.getLong("BILLABLE_ACC_ID")));
					accountDto.setBillableLoginName(getAccountLoginCode(rs.getInt("BILLABLE_ACC_ID")));
				}
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setAccountAddress((rs.getString("ACCOUNT_ADDRESS")));
				accountDto.setCircleCode(rs.getString("CIRCLE_CODE"));
				// Anju // All the attributes related to Distributor , FSE and
				// Retailer.
				if (accountDto.getAccountLevelId() <= Constants.DISTRIBUTOR_ID) {
					accountDto.setBillableCode("NA");
				}
				if (accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID) {
					
					if(accountDto.getAccountLevelId() == Constants.RETAILER_ID)
					{
						accountDto.setMobile1(rs.getString("LAPU_MOBILE_NO"));
						accountDto.setMobile2(rs.getString("LAPU_MOBILE_NO_2"));
						accountDto.setMobile3(rs.getString("FTA_MOBILE_NO"));
						accountDto.setMobile4(rs.getString("FTA_MOBILE_NO_2"));
					}
					else
					{
						accountDto.setLapuMobileNo(rs.getString("LAPU_MOBILE_NO"));
						accountDto.setLapuMobileNo1(rs.getString("LAPU_MOBILE_NO_2"));
						accountDto.setFTAMobileNo(rs.getString("FTA_MOBILE_NO"));
						accountDto.setFTAMobileNo1(rs.getString("FTA_MOBILE_NO_2"));
					}
//					accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
//					accountDto.setOperatingBalance(rs
//							.getDouble("OPERATING_BALANCE"));
//					accountDto.setAvailableBalance(rs
//							.getDouble("AVAILABLE_BALANCE"));
//					accountDto.setRate(rs.getDouble("RATE"));
//					accountDto.setThreshold(rs.getDouble("Threshold"));
					// query to retrieve the Distributor type and sub type.
					ps = connection.prepareStatement(queryMap
							.get(SQL_SELECT_PARENT_TRADE_KEY));
					ps.setLong(1, accountDto.getAccountId());
					ResultSet rs1 = null;
					rs1 = ps.executeQuery();
					while (rs1.next()) {
						accountDto.setTradeName(rs1.getString("CHANNEL_NAME"));
						accountDto.setTradeCategoryName(rs1
								.getString("CATEGORY_NAME"));
						accountDto.setTradeId(rs1.getInt("DIST_CHANNEL_ID"));
						accountDto.setTradeCategoryId(rs1
								.getInt("CHANNEL_CATEGORY_ID"));
					}
				} else 
				{
					accountDto.setLapuMobileNo("NA");
					accountDto.setLapuMobileNo1("NA");
					accountDto.setFTAMobileNo("NA");
					accountDto.setFTAMobileNo1("NA");
					accountDto.setTradeName("NA");
					accountDto.setTradeCategoryName("NA");
				}
				accountDto.setBeatName(rs.getString("BEAT_NAME"));
				accountDto.setBeatId(rs.getInt("BEAT_CODE"));
				accountDto.setRetailerType(rs.getInt("RETAILER_TYPE")+"");
				accountDto.setRetailerTypeName(rs.getString("RETAILER_CATEGORY_NAME"));
				accountDto.setOutletType(rs.getInt("OUTLAY_TYPE")+"");
				accountDto.setOutletName(rs.getString("OUTLET_NAME"));
				accountDto.setAltChannelType(rs.getInt("ALT_CHANNEL_TYPE")+"");
				accountDto.setAltChannelName(rs.getString("ALT_CHANNEL_NAME"));
				accountDto.setChannelType(rs.getInt("CHANNEL_TYPE")+"");
				accountDto.setChannelName(rs.getString("CHANNEL_NAME"));
				if(rs.getString("CST_NO") == null || rs.getString("CST_NO") == ""){
					accountDto.setCstNo("NA");
				}else{
					accountDto.setCstNo(rs.getString("CST_NO"));
				}
				
				if(rs.getString("CST_DATE") != null || rs.getString("CST_DATE") != ""){
					accountDto.setCstDt(rs.getString("CST_DATE"));
				}
				
				if(rs.getString("SERVICE_TAX_NO") == null || rs.getString("SERVICE_TAX_NO") == ""){
					accountDto.setServiceTax("NA");
				}else{
					accountDto.setServiceTax(rs.getString("SERVICE_TAX_NO"));
				}
				if(rs.getString("TIN_NO") == null || rs.getString("TIN_NO") == ""){
					accountDto.setTinNo("NA");
				}else{
					accountDto.setTinNo(rs.getString("TIN_NO"));
				}
				
				if(rs.getString("TIN_DATE") != null || rs.getString("TIN_DATE") != ""){
					accountDto.setTinDt(rs.getString("TIN_DATE"));
				}
				
				if(rs.getString("CATEGORY") == null || rs.getString("CATEGORY") == ""){
					accountDto.setCstNo("NA");
				}else{
					accountDto.setCategory(rs.getString("CATEGORY"));
				}
					accountDto.setOctroiCharge(rs.getString("OCTROI_CHARGE"));
//					if(rs.getString("REPORTING_EMAIL") == null || rs.getString("REPORTING_EMAIL") == ""){
//						accountDto.setReportingEmail("NA");
//					}else{
//						accountDto.setReportingEmail(rs.getString("REPORTING_EMAIL"));
//					}
				if(rs.getString("PAN_NO") == null || rs.getString("PAN_NO") == ""){
					accountDto.setPanNo("NA");
				}else{
					accountDto.setPanNo(rs.getString("PAN_NO"));
				}
									
				// Added for Oracle SCM Integration
			    if (rs.getString("DISTRIBUTOR_LOCATOR") == null	|| rs.getString("DISTRIBUTOR_LOCATOR") == "") 
			    {
				    accountDto.setDistLocator("");
			    } else {
				    accountDto.setDistLocator(rs.getString("DISTRIBUTOR_LOCATOR"));
			    }
			    
			    
			    
			    // add by harbans for self Repair
			    if (rs.getString("IS_REPAIR") == null	|| rs.getString("IS_REPAIR") == "") 
			    {
				    accountDto.setRepairFacility("");
			    } else {
				    accountDto.setRepairFacility(rs.getString("IS_REPAIR"));
			    }
			    //accountDto.setSwLocatorId(rs.getInt("SW_LOCATOR_ID"));
			    //add by sugandha 
			    
			    accountDto.setTransactionId(rs.getString("TRANSACTION_TYPE"));
			    strRetDemoIDS = rs.getString("RET_DEMO_IDS") ;
			    logger.info("strRetDemoIDS::"+strRetDemoIDS+"<--");
			    
				String strDemo1 = "";
				String strDemo2 = "";
				String strDemo3 = "";
				String strDemo4 = "";
				
				if(strRetDemoIDS!=null && !strRetDemoIDS.trim().equals(""))
				{
					String[] strArray = strRetDemoIDS.split(",");
					
					for(int i=0; i<strArray.length; i++)
					{
						if(i==0)
							strDemo1 = strArray[i];
						
						if(i==1)
							strDemo2 = strArray[i];
						
						if(i==2)
							strDemo3 = strArray[i];
						
						if(i==3)
							strDemo4 = strArray[i];
					}
				}
				accountDto.setDemoId1(strDemo1);
				accountDto.setDemoId2(strDemo2);
				accountDto.setDemoId3(strDemo3);
				accountDto.setDemoId4(strDemo4);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Exception occured while get Account."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
		return accountDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountDetailsByMobile(java.lang.String)
	 */
	public DPCreateAccountDto getAccountDetailsByMobile(String mobileNO)
			throws DAOException {
	//	logger.info("Started ...mobileNO " + mobileNO);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACC_BY_MOBILE_KEY));
			ps.setString(1, mobileNO);
			rs = ps.executeQuery();

			if (rs.next()) {
				accountDto = new DPCreateAccountDto();
				accountDto.setAccountId(rs.getLong("ACCOUNT_ID"));
				accountDto.setParentAccountId(rs.getLong("PARENT_ACCOUNT"));
				accountDto.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				// accountDto.setCircleId(rs.getInt("CIRCLE_ID"));

				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setRate(rs.getDouble("RATE"));
			}

		} catch (SQLException e) {
			logger.error("Exception occured while getAccount."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
		return accountDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#updateAccount(com.ibm.virtualization.recharge.dto.Account)
	 */
	public void updateAccount(DPCreateAccountDto accountDto) throws DAOException {
		//logger.info("Started ...  account " + accountDto);
		PreparedStatement ps = null;
		PreparedStatement ps3 = null;
		String distLocator = "";
		int rowsUpdated = 0;
		int accID = (int) accountDto.getAccountId();
 
		try {
			logger.info("**************************inside update Account**************");
//			Code added by Karan on 2 July 2012 for duplicate Dist Locator Code Validation
			ps3 = connection.prepareStatement(GET_DIST_LOCATOR_EDIT);
			if (accountDto.getDistLocator() != null && !accountDto.getDistLocator().trim().equals(""))
			{
				accountDto.setDistLocator(accountDto.getDistLocator().trim());
				
			}
			
			ps3.setString(1,accountDto.getDistLocator());
			ps3.setInt(2,accID);
			ResultSet rs3 = ps3.executeQuery();			
			while (rs3.next()) {
				distLocator = rs3.getString("DISTRIBUTOR_LOCATOR");
				}
			
			if(distLocator == null || distLocator.equals(""))
			{
				logger.info("\n\naccID"+accID);
				logger.info("\n\ndistLocator"+distLocator);
			}
			else
			{
				throw new DAOException("Duplicate Dist Locator Code!!");
			}
			
			//Dist Locator Code Validation ends here SQL_UPDATE_ACC_DETAIL_DIST
			
			
			//putting this code for retailers only for acount update report in REG enh
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			if(accountDto.getAccountLevelId() == Constants.ACC_LEVEL_RETAILER_ID)
			{
			insertHistDetails(accountDto.getAccountId(),currentTime);
			}
			
			ps = connection.prepareStatement(SQL_UPDATE_ACC_DIST_CHILD_KEY);
			System.out.println("SQL_UPDATE_ACC_DIST_CHILD_KEY::"+SQL_UPDATE_ACC_DIST_CHILD_KEY);
			int paramCount = 1;
			ps.setString(paramCount++, accountDto.getSimNumber());
			ps.setString(paramCount++, accountDto.getMobileNumber());
			ps.setString(paramCount++, accountDto.getRetailerMobileNumber());
			ps.setString(paramCount++, accountDto.getAccountName());
			ps.setString(paramCount++, accountDto.getAccountAddress());
			ps.setString(paramCount++, accountDto.getEmail());
			ps.setString(paramCount++, accountDto.getCategory());
			 //ps.setInt(paramCount++, accountDto.getCircleId());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, accountDto.getUpdatedBy());

			if(accountDto.getCircleId() != 0){	
			ps.setLong(paramCount++, accountDto.getAccountZoneId());
			ps.setLong(paramCount++, accountDto.getAccountCityId());
			ps.setLong(paramCount++, accountDto.getAccountStateId());
			}else{
				ps.setNull(paramCount++, Types.BIGINT);
				ps.setNull(paramCount++, Types.BIGINT);
				ps.setNull(paramCount++, Types.BIGINT);
			}
			//error in accountDto level id 
			if (accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID){
			ps.setString(paramCount++, accountDto.getIsbillable());
			ps.setInt(paramCount++, accountDto.getTradeId());
			ps.setInt(paramCount++, accountDto.getTradeCategoryId());
			
			if(accountDto.getAccountLevelId() == Constants.RETAILER_ID)
			{
				ps.setString(paramCount++, accountDto.getMobile1());
				ps.setString(paramCount++, accountDto.getMobile2());
				ps.setString(paramCount++, accountDto.getMobile3());
				ps.setString(paramCount++, accountDto.getMobile4());
			}
			else
			{
				ps.setString(paramCount++, accountDto.getLapuMobileNo());
				ps.setString(paramCount++, accountDto.getLapuMobileNo1());
				ps.setString(paramCount++, accountDto.getFTAMobileNo());
				ps.setString(paramCount++, accountDto.getFTAMobileNo1());
			}
			if (accountDto.getIsbillable().equalsIgnoreCase(
					Constants.ACCOUNT_BILLABLE_NO)) {
				ps.setLong(paramCount++, accountDto.getBillableCodeId());
			} else  if(accountDto.getIsbillable().equalsIgnoreCase(
					Constants.ACCOUNT_BILLABLE_YES)){
				// if distributor or MTP user or none billable accountDto
				ps.setNull(paramCount++, Types.INTEGER);
			}
			if(accountDto.getBeatId() != 0)
				ps.setInt(paramCount++, accountDto.getBeatId());
			else
				ps.setNull(paramCount++, Types.INTEGER);
			ps.setInt(paramCount++, Integer.parseInt(accountDto.getOutletType()));
			}else{
				ps.setString(paramCount++, null);
				// This field needs to be changed after data purging and coloumn 
				//billable_account has to be made nullable 
			//	ps.setString(paramCount++, null);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
			}
			// if billable not accountDto
			ps.setInt(paramCount++, accountDto.getAccountPinNumber());
			ps.setString(paramCount++, accountDto.getAccountAddress2());
			ps.setString(paramCount++, accountDto.getContactName());
			ps.setString(paramCount++, accountDto.getCode());
			if(accountDto.getAccountLevelId() == Constants.RETAILER_ID){
				ps.setInt(paramCount++, Integer.parseInt(accountDto.getRetailerType()));
				ps.setInt(paramCount++, Integer.parseInt(accountDto.getAltChannelType()));
				ps.setInt(paramCount++, Integer.parseInt(accountDto.getChannelType()));
			}else{
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
				ps.setNull(paramCount++, Types.INTEGER);
			}
			if(accountDto.getAccountLevelId() == Constants.DISTRIBUTOR_ID){
				ps.setString(paramCount++, accountDto.getOctroiCharge());
				ps.setString(paramCount++, accountDto.getTinNo());
				ps.setString(paramCount++, accountDto.getServiceTax());
				ps.setString(paramCount++, accountDto.getPanNo());
				ps.setString(paramCount++, accountDto.getCstNo());
//				ps.setString(paramCount++, accountDto.getReportingEmail());
				// Added for Oracle SCM Integration
				if (accountDto.getDistLocator() != null && !accountDto.getDistLocator().trim().equals(""))
				{
					accountDto.setDistLocator(accountDto.getDistLocator().trim());
					
				}
				ps.setString(paramCount++, accountDto.getDistLocator());
				ps.setLong(paramCount++, accountDto.getParentAccountId());
				//saumya
					ps.setString(paramCount++, accountDto.getRepairFacility()); 
				//end
				
				//ps.setInt(paramCount++, accountDto.getSwLocatorId());
			
			}else{
				ps.setString(paramCount++, "0");
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setString(paramCount++, null);
				ps.setLong(paramCount++, accountDto.getParentAccountId());
				//saumya
					ps.setString(paramCount++, Constants.OPTION_NO);
				//end
				//ps.setNull(paramCount++, Types.INTEGER);
//			
//				ps.setString(paramCount++, null);
			}
			//ps.setString(paramCount++, accountDto.getRepairFacility());
			//ps.setString(paramCount++, accountDto.getRepairFacility());
			
			if(accountDto.getAccountLevelId()== Constants.DISTRIBUTOR_ID)
			{
				logger.info("Admin ID ****************** ");			
				updateWareHouse(accountDto.getAccountWarehouseCode(),accountDto.getAccountId());
			}
			
			System.out.println("accountDto.getAccountLevelId():::"+accountDto.getAccountLevelId());
			logger.info(accountDto.getAccessLevelId());
			logger.info(accountDto.getAccountLevel());
			logger.info(accountDto.getGroupId());
			
			if(accountDto.getAccountLevelId() == Constants.ACC_LEVEL_RETAILER_ID)
			{
				//System.out.println("1111111111111111");
				ps.setInt(paramCount++,Integer.parseInt(accountDto.getTransactionId()));
				
				String strDemoId1 =  accountDto.getDemoId1();
				String strDemoId2 =  accountDto.getDemoId2();
				String strDemoId3 =  accountDto.getDemoId3();
				String strDemoId4 =  accountDto.getDemoId4();
				if(strDemoId1==null)
					strDemoId1="";
				if(strDemoId2==null)
					strDemoId2="";
				if(strDemoId3==null)
					strDemoId3="";
				if(strDemoId4==null)
					strDemoId4="";
				
				String strDemoIDs = strDemoId1+","+strDemoId2+","+strDemoId3+","+strDemoId4;
				
				ps.setString(paramCount++, strDemoIDs);
			}
			else
			{
				System.out.println("2222222222222222");
				ps.setInt(paramCount++, Constants.TRANSACTION_TYPE_DEPOSIT);
				ps.setString(paramCount++, "");
			}
			
			ps.setInt(paramCount++, (int)accountDto.getAccountId());
			
			ps.executeUpdate();
		
			
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("SQL Exception occured ");
			
			logger.error(
					"SQL Exception occured while updating Account Due to Mobile No. Already Exist"
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Account.ERROR_ACC_UPDATE);
		}
		catch (DAOException de) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			de.printStackTrace();
			logger.error("Exception occured while validatiing Dist Locator Code(Duplicate Dist Locator Code)"+ "Exception Message: ", de);
			throw new DAOException("DupDist");
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		//logger.info("Executed ...");

		// return rowsUpdated;
	}

	
	//** State drop down UPDATE code Starts By: Digvijay
	
	public void updateWareHouse(String wareHouseCode,long distId)
	throws DAOException {
		//logger.info("Started .updateWareHouse.. " +distId+wareHouseCode);
		PreparedStatement pstmt = null;
		try 
		{
			pstmt = connection.prepareStatement(SQL_UPDATE_WAREHOUSE_CODE);
			pstmt.setString(1, wareHouseCode);
			pstmt.setLong(2, distId);
			int updateStatus = pstmt.executeUpdate();
			
			if(updateStatus < 1) 
			{
				pstmt = connection.prepareStatement("insert into DP_WH_DIST_MAP(WH_CODE, DISTID) values (?,?)");
				pstmt.setString(1, wareHouseCode);
				pstmt.setLong(2, distId);
				updateStatus = pstmt.executeUpdate();
			} 
			else 
			{
				logger.info("warehouse code is updated " + distId);
			}

	//logger.info("Successful updation of warehousecode into the table: DP_WH_DIST_MAP:");
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception occured while updation of warehousecode ." + "Exception Message: " + sqlEx.getMessage());
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
	} catch (SQLException e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger
				.fatal("SQL Exception occured while  updation of warehousecode ."
						+ "Exception Message: " + sqlEx.getMessage());

	}
	throw new DAOException(
			ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
		} finally {
	DBConnectionManager.releaseResources(pstmt, null);
		}
		//logger.info("Executed UPDATE STATE...");
 }
	
	/*
	 * For Distributor Transaction (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountRate(java.lang.String)
	 */

	public double getAccountRate(long accountId) throws DAOException {
		//logger.info("Started ... account id:- " + accountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		double rate = 0.0;

		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACCOUNT_RATE_KEY));
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
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed ...Successfully retrieved Account Rate ");
		return rate;
	}

	/*
	 * For Transaction (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getMobileNumber(long)
	 */
	public String getMobileNoForSms(long accountId,int alertType) throws DAOException {
logger.info("DPCreateAccountDaoImpl > getMobileNoForSms 22222222");
		//logger.info("Started ... for accountID " + accountId);
		String mobileNumber = null;
		int accountLevel=0;
		int groupFlag=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String lapu1="";
		String lapu2="";
		try {

			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_MOBILENO_KEY));
			// getting account Id on the basis of Account Code From AccountDAO
			ps.setLong(1, accountId);
			//ps.setLong(2, accountId); //commented by Neetika
			rs = ps.executeQuery();
			// getting mobileNumber according to account Id
			// If mobileNumber not found then throw Invalid account ID exception
			if (rs.next()) {
				mobileNumber = rs.getString("MOBILE_NUMBER");
				accountLevel=rs.getInt("ACCOUNT_LEVEL");
				lapu1=rs.getString("LAPU_MOBILE_NO");
				lapu2=rs.getString("LAPU_MOBILE_NO_2");
				if(accountLevel==6)
				{
					if(!lapu1.equalsIgnoreCase(""))
					mobileNumber = rs.getString("LAPU_MOBILE_NO");
					else if(!lapu2.equalsIgnoreCase(""))
					mobileNumber = rs.getString("LAPU_MOBILE_NO");
					else //this will never happen ideally
					mobileNumber = rs.getString("MOBILE_NUMBER");				
				
				}
			} else {
				// record not found if Account Code invalid hence throw
				// exception
				logger
						.error(" Exception occured :While getting mobileNumber According To Account ID "
								+ accountId);
				//throw new DAOException(
					//	ExceptionCode.Account.ERROR_INVALID_ACCOUNT_CODE);
			}
			// checking group have anabled to receive SMS or not 
			if(mobileNumber!=null)
			{
				ps1=connection.prepareStatement("SELECT ENABLE_FLAG FROM DP_ALERT_GROUP_MAPPING WHERE ALERT_ID=? and GROUP_ID=? with UR");
				ps1.setInt(1, alertType);
				ps1.setInt(2, accountLevel+1);
				rs1=ps1.executeQuery();
				if(rs1.next())
				groupFlag=rs1.getInt("ENABLE_FLAG");
				else
				groupFlag=-1;
				if(groupFlag ==0)
				{
					mobileNumber=null;
					logger
					.error(" Exception occured :While"
							+ accountId +" Group has not enabled to recive SMS alerts for alert type "+alertType);
				//	throw new DAOException("Group has not enabled to recive SMS alerts for alert type "+alertType);
				}
				
					
			}
			
			// end for checking groyou have enabled to receive sms or not
		} catch (SQLException e) {
			logger.error("SQL Exception occured while get mobile no.."
					+ "Exception Message: ", e);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(ps1, rs1);
		}
		logger.info("Executed ...Successfully retrieved mobile Number");
		return mobileNumber;
	}

	
	public String getMobileNumber(long accountId) throws DAOException {

		//logger.info("Started ... for accountID " + accountId);
		String mobileNumber = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_MOBILENO_KEY));
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
		//logger.info("Started ...For Account Id" + accountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountBalance accountBalanceDto = new AccountBalance();
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_BALANCE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//logger.info("Balance for the AccountId exists");
				accountBalanceDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountBalanceDto.setRate(rs.getFloat("RATE"));
				accountBalanceDto.setAccountId(accountId);
				accountBalanceDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountBalanceDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountBalanceDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
			} else {
				logger.error("No Balance exist for the Account.accountId:"
						+ accountId);
			}
			//logger.info("Executed ...");
			return accountBalanceDto;
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
		//logger.info("Started ...For source AccountId" + sourceAccountId
	//			+ "destinationAccountId:" + destinationAccountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			/* Get the PrepareStatement Object using the prepare statement */
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ISCHILD_KEY));
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
	//	logger.info("Started ...For sourceAccountId" + sourceAccountId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> childAccountList = new ArrayList<DPCreateAccountDto>();
		try {
			DPCreateAccountDto accountDto = new DPCreateAccountDto();
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_CHILD_ACCOUNT_KEY));
			ps.setLong(1, sourceAccountId);
			rs = ps.executeQuery();
			while (rs.next()) {
				/*
				 * It sets the DTO of the Account to the values of the selected
				 * child Account info
				 */
				accountDto = new DPCreateAccountDto();
				accountDto.setAccountName(rs.getString(1));
				accountDto.setAccountId(rs.getLong(2));
				accountDto.setMobileNumber(rs.getString(3));
				accountDto.setLoginName(rs.getString(4));
				childAccountList.add(accountDto);
			}
			//logger.info("Number of child Accounts found = "
			//		+ childAccountList.size());
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
	//	logger.info("update the Balance in the Table: VR_ACCOUNT_BALANCE ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_CHECK_UPDATE_OPERATING_BALANCE_KEY));
			ps.setDouble(1, debitAmount);
			ps.setLong(2, accountId);
			ps.setDouble(3, -debitAmount);
			int updateStatus = ps.executeUpdate();
			if (updateStatus == 0) {
				logger.error("Insufficient Balance for account Id :"
						+ accountId);
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_INSUFFICIENT_BAL);
			} else {
				logger
						.info("Successfully updated the balance for the Account Id:"
								+ accountId);
			}
			logger
					.info("successful updation of operating Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		//logger.info("Started ...  Account Id" + accountId + "And debitAmount:"
		//		+ debitAmount);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_OPERATING_BALANCE_KEY));
			ps.setDouble(1, debitAmount);
			ps.setLong(2, accountId);
			ps.executeUpdate();
			logger
					.info("Executed ... successful updation of operating Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		//logger.info("Started ... for Account Id" + accountId + ":debitAmount:"
		//		+ debitAmount);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_AVAILABLE_BALANCE_KEY));
			ps.setDouble(1, debitAmount);
			ps.setLong(2, accountId);
			ps.executeUpdate();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		//logger.info("Started...For Account Id " + accountId);

		PreparedStatement ps = null;
		ResultSet rs = null;
		int numRows = -1;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_CIRCLE_SELECT_WITH_ACCOUNTID_KEY));
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
		//logger.info("Started ...");

		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_VR_ACCOUNT_DETAILS_KEY));
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
			ps = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_ACCOUNT_AVAILBALANCE_KEY));
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
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("Exception occured while Updating."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		//logger.info("Executed ...Successfully Updated Account ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#doUpdate(string,
	 *      double, java.lang.String)
	 */
	public void doUpdateSourceBalance(long accountId, double transactionAmount)
			throws DAOException {
	//	logger.info("Started ...  transactionAmount" + transactionAmount
	//			+ ":accountId:" + accountId);
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY));
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
				logger
						.info("Sufficient Balance for the Accountid:"
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
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		//logger.info("Executed ...");
	}
	

	//** State drop down UPDATE code Starts By: Digvijay
	
	public void updateState(long accountId, double transactionAmount)
	throws DAOException {
		//logger.info("Started ...  transactionAmount" + transactionAmount + ":accountId:" + accountId);
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(queryMap.get(SQL_UPDATE_ACCOUNT_SOURCE_BALANCE_KEY));
			pstmt.setDouble(1, transactionAmount);
			pstmt.setDouble(2, transactionAmount);
			pstmt.setLong(3, accountId);
			pstmt.setDouble(4, -transactionAmount);
			int updateStatus = pstmt.executeUpdate();
			if (updateStatus == 0) {
				logger.error("Insufficient Balance for the Accountid:"+ accountId);
				throw new DAOException(
				ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
			} else {
				logger.info("Sufficient Balance for the Accountid:" + accountId);
	}

	logger.info("Successful updation of Balance into the table: VR_ACCOUNT_BALANCE:");
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception occured while updation of Balance ." + "Exception Message: " + sqlEx.getMessage());
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
	} catch (SQLException e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger
				.fatal("SQL Exception occured while updation of Balance ."
						+ "Exception Message: " + sqlEx.getMessage());

	}
	throw new DAOException(
			ExceptionCode.Transaction.ERROR_UPDATE_ACC_BALANCE);
		} finally {
	DBConnectionManager.releaseResources(pstmt, null);
		}
		//logger.info("Executed UPDATE STATE...");
 }
	//** State drop down UPDATE code ends here 

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RechargeDao#doUpdate(string,
	 *      double, java.lang.String)
	 */
	public void doUpdateDestinationBalance(long accountId,
			double transactionAmount) throws DAOException {
		//logger.info("Started ...  transactionAmount" + transactionAmount
		//		+ ":accountId:" + accountId);
		PreparedStatement pstmt = null;

		try {
			pstmt = connection.prepareStatement(queryMap
					.get(SQL_UPDATE_ACCOUNT_DESTINATION_BALANCE_KEY));
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
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		//logger.info("Executed ...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CustomerTransactionDao#isActive(string)
	 */
	public long isActive(String mobileNo) throws DAOException {
		//logger.info("Started ... mobileNo" + mobileNo);
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ISACTIVE_KEY));
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
		//logger.info(" Started :::parentCode : " + parentCode
		//		+ "  and circleId : " + circleId);
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_PARENT_ACCOUNTID_KEY));
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
		//logger.info("Executed ... Account Id Successfully Retrieved");
		return accountId;
	}
	
	// check for unique Lapu Mobile No.
	public long checkMobileNumberAlreadyExist(String mobileNo)
	throws DAOException {
	//	logger.info(" Started....mobileNo : " + mobileNo);
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accountId = -1;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_IS_MOBILENO_EXIST_KEY));
			ps.setString(1, mobileNo);
			ps.setString(2, mobileNo);
			ps.setString(3, mobileNo);
			ps.setString(4, mobileNo);
			ps.setString(5, mobileNo);
			ps.setString(6, mobileNo);
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
			// Close the resultset, statement. 
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ...ACCOUNT_ID " + accountId);
		}
	return accountId;
} 


//	 Merge on 8Th august merging by harabns
//	 Check for unique Mobile,Lapu1-2, FTA 1-2 Mobile No
	public String checkAllReatilerMobileNumberAlreadyExist(String mobileNo, String lapuMobileNo,String lapuMobileNo1, String ftaMobileNo,String ftaMobileNo1) throws DAOException 
	{
		//logger.info(" Started....mobileNo : " + mobileNo);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String flag = "";
		
		try 
		{
			ps = connection.prepareStatement(queryMap.get(SQL_IS_ALL_REATILER_MOBILENO_EXIST_KEY));
			logger.info( mobileNo+"lapuMobileNo1"+lapuMobileNo+"lapuMobileNo2"+lapuMobileNo1+"ftaMobileNo"+ftaMobileNo+"ftaMobileNo1"+ftaMobileNo1);
			ps.setString(1, mobileNo);
			ps.setString(2, mobileNo);
			ps.setString(3, mobileNo);
			ps.setString(4, mobileNo);
			ps.setString(5, mobileNo);
			ps.setString(6, mobileNo);
			ps.setString(7, lapuMobileNo);
			ps.setString(8, lapuMobileNo);
			ps.setString(9, lapuMobileNo);
			ps.setString(10, lapuMobileNo);
			ps.setString(11, lapuMobileNo);
			ps.setString(12, lapuMobileNo);
			ps.setString(13, lapuMobileNo1);
			ps.setString(14, lapuMobileNo1);
			ps.setString(15, lapuMobileNo1);
			ps.setString(16, lapuMobileNo1);
			ps.setString(17, lapuMobileNo1);
			ps.setString(18, lapuMobileNo1);
			ps.setString(19, ftaMobileNo);
			ps.setString(20, ftaMobileNo);
			ps.setString(21, ftaMobileNo);
			ps.setString(22, ftaMobileNo);
			ps.setString(23, ftaMobileNo);
			ps.setString(24, ftaMobileNo);
			ps.setString(25, ftaMobileNo1);
			ps.setString(26, ftaMobileNo1);
			ps.setString(27, ftaMobileNo1);
			ps.setString(28, ftaMobileNo1);
			ps.setString(29, ftaMobileNo1);
			ps.setString(30, ftaMobileNo1);	
			rs = ps.executeQuery();
			
			// if mobile no. exist
			if (rs.next()) 
			{
				flag = rs.getString("FLAG");
				//System.out.println( flag+"create module ");
			}
		
		} catch (SQLException e) 
		{
			logger.fatal("SQL Exception occured while Checking Entered  Mobile No. is Exist   "	+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally 
		{
			// Close the resultset, statement. 
			DBConnectionManager.releaseResources(ps, rs);
			//logger.info("Executed ...ACCOUNT_ID " + accountId);
		}
		return flag;
} 
	

// Merge on 8Th august merging by harabns
//	 Check for unique Mobile,Lapu1-2, FTA 1-2 Mobile No
	public String checkAllReatilerMobileNumberAlreadyExistEditMode(String accountId, String mobileNo, String lapuMobileNo,String lapuMobileNo1, String ftaMobileNo,String ftaMobileNo1) throws DAOException 
	{
		//logger.info(" Started....mobileNo : " + mobileNo);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String flag = "";
		
		try 
		{
			ps = connection.prepareStatement(queryMap.get(SQL_IS_ALL_REATILER_MOBILENO_EDIT_MODE_EXIST_KEY));
			//System.out.println("lapuMobileNo"+lapuMobileNo+"lapuMobileNo1"+lapuMobileNo1+"ftaMobileNo"+ftaMobileNo+"ftaMobileNo1"+ftaMobileNo1);
			ps.setString(1, accountId);
			ps.setString(2, mobileNo);
			ps.setString(3, mobileNo);
			ps.setString(4, mobileNo);
			ps.setString(5, mobileNo);
			ps.setString(6, mobileNo);
			ps.setString(7, mobileNo);
			ps.setString(8, accountId);
			ps.setString(9, lapuMobileNo);
			ps.setString(10, lapuMobileNo);
			ps.setString(11, lapuMobileNo);
			ps.setString(12, lapuMobileNo);
			ps.setString(13, lapuMobileNo);
			ps.setString(14, lapuMobileNo);
			
			ps.setString(15, accountId);
			ps.setString(16, lapuMobileNo1);
			ps.setString(17, lapuMobileNo1);
			ps.setString(18, lapuMobileNo1);
			ps.setString(19, lapuMobileNo1);
			ps.setString(20, lapuMobileNo1);
			ps.setString(21, lapuMobileNo1);
			ps.setString(22, accountId);
			ps.setString(23, ftaMobileNo);
			ps.setString(24, ftaMobileNo);
			ps.setString(25, ftaMobileNo);
			ps.setString(26, ftaMobileNo);
			ps.setString(27, ftaMobileNo);
			ps.setString(28, ftaMobileNo);
			ps.setString(29, accountId);
			ps.setString(30, ftaMobileNo1);
			ps.setString(31, ftaMobileNo1);
			ps.setString(32, ftaMobileNo1);
			ps.setString(33, ftaMobileNo1);
			ps.setString(34, ftaMobileNo1);
			ps.setString(35, ftaMobileNo1);
			
			rs = ps.executeQuery();
			
			// if mobile no. exist
			if (rs.next()) 
			{
				flag = rs.getString("FLAG");
				//System.out.println("flag is =="+flag);
			}
		
		} catch (SQLException e) 
		{
			logger.fatal("SQL Exception occured while Checking Entered  Mobile No. is Exist   "	+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally 
		{
			// Close the resultset, statement. 
			DBConnectionManager.releaseResources(ps, rs);
			//logger.info("Executed ...ACCOUNT_ID " + accountId);
		}
		return flag;
} 

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getParentAccountList(java.lang.String,
	 *      java.lang.String, long, int, java.lang.String)
	 */
	public ArrayList getParentAccountList(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId, int lb, int ub, int accountLevel)
			throws DAOException {
		//logger.info("Started ...searchType " + searchType
		//		+ " AND searchValue:=  " + searchValue + " AND circleId "
		//		+ parentCircleId + " AND accountLevelId :" + accountLevelId);

		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		String query = null;
		int paramCount = 1;
		try {
			StringBuffer sql = new StringBuffer();

			// show records of respective child and grand-child accounts upto
			// n-th level

			sql.append(queryMap.get(SQL_SELECT_FOR_PARENT_ACC_KEY));
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
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?) Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, parentCircleId);
					// ps.setLong(paramCount++, parentId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
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
					// ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
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
					// ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					// ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
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
					ps.setInt(1, parentCircleId);
					// ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
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
					// ps.setLong(2, parentId);
					ps.setString(2, String.valueOf(ub));
					ps.setString(3, String.valueOf(lb + 1));

				}
			}
			//logger.info(" This is query getParentAccountList:- sql "
			//		+ sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				if (accountLevel >= Constants.DISTRIBUTOR_ID) {
					ps = connection.prepareStatement(queryMap
							.get(SQL_SELECT_PARENT_TRADE_KEY));
					ps.setLong(1, accountDto.getAccountId());
					ResultSet rs1 = null;
					rs1 = ps.executeQuery();
					while (rs1.next()) {
						accountDto.setTradeName(rs1.getString("CHANNEL_NAME"));
						accountDto.setTradeCategoryName(rs1
								.getString("CATEGORY_NAME"));
						accountDto.setTradeId(rs1.getInt("DIST_CHANNEL_ID"));
						accountDto.setTradeCategoryId(rs1
								.getInt("CHANNEL_CATEGORY_ID"));
					}
				}
				// accountDto.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(accountDto);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.accountDto List"
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
			int ub,DPCreateAccountDto accountDTO) throws DAOException {
		//logger.info("Started ...searchType " + searchType
		//		+ " AND searchValue:=  " + searchValue + " AND circleId "
		//		+ circleId + " AND  accountLevelId:- " + accountLevelId);

		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		String query = null;
		boolean roleName = false;
		int paramCount=1;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
			sql.append(" and VLM.STATUS = '"+Constants.ACTIVE_STATUS+"' AND DETAILS.circle_id = ?");
//					+ Constants.ACTIVE_STATUS + "' ");
			roleName = accountDTO.getSessionContaxt().getRoleList().contains("ROLE_AD");
			if(roleName){
				sql.append(" and DETAILS.parent_account="+accountDTO.getAccountId());
			}
			/*
			 * if (Integer.parseInt(accLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql.append(" AND
			 * ACCOUNT_LEVEL <'"); sql.append(accLevelId); sql.append("' AND
			 * ACCOUNT_LEVEL >='"); sql.append(Constants.ACC_LEVEL_MTP);
			 * sql.append("'"); } else { sql.append(" AND ACCOUNT_LEVEL <'");
			 * sql.append(Constants.ACC_LEVEL_MTP); sql.append("' AND
			 * ACCOUNT_LEVEL <'"); sql.append(accLevelId); sql.append("'"); }
			 */
//
//			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
//			sql
//					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
//			sql
//					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
//			sql.append(" WHERE	LEVEL_ID=" + accLevelId
//					+ "   ) AND level1.HIERARCHY_ID =(SELECT ");
//			sql
//					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID="
//							+ accLevelId + "))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?   ");
					query = "SELECT * FROM(SELECT ROWNUMBER() over(order by VLM.LOGIN_NAME) as rownum,"
						+ sql.toString()  
						+ " ) as tr  with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, circleId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
					//ps.setString(paramCount++, String.valueOf(lb + 1));
					//ps.setString(paramCount, String.valueOf(ub));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent accountDto */
					sql
							.append(" AND DETAILS.PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT ROWNUMBER() over(order by VLM.LOGIN_NAME) as rownum,"
						+ sql.toString() 
						+ " ) as tr  with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, circleId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
					//ps.setString(paramCount++, String.valueOf(lb + 1));
					//ps.setString(paramCount, String.valueOf(ub));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to accountDto name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?  ");
					query = "SELECT * FROM(SELECT ROWNUMBER() over(order by VLM.LOGIN_NAME) as rownum,"
						+ sql.toString() 
						+ " ) as tr  with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, circleId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
					//ps.setString(paramCount++, String.valueOf(lb + 1));
					//ps.setString(paramCount, String.valueOf(ub));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ?   ");
					query = "SELECT * FROM(SELECT ROWNUMBER() over(order by VLM.LOGIN_NAME) as rownum,"
						+ sql.toString() 
						+ " ) as tr  with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, circleId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
					//ps.setString(paramCount++, String.valueOf(lb + 1));
					//ps.setString(paramCount, String.valueOf(ub));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
					query = "SELECT * FROM(SELECT ROWNUMBER() over(order by VLM.LOGIN_NAME) as rownum,"
							+ sql.toString() 
							+ " ) as tr  with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, circleId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
					//ps.setString(paramCount++, String.valueOf(lb + 1));
					//ps.setString(paramCount, String.valueOf(ub));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT ROWNUMBER() over(order by VLM.LOGIN_NAME) as rownum,"+
							sql.toString()
							+" ) as tr  with ur";
					
//					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
//							+ sql.toString() + ") a "
//							+ "Where ROWNUM<=?)Where rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, accLevelId);
					ps.setInt(paramCount++, circleId);
					//ps.setString(paramCount++, String.valueOf(lb + 1));
					//ps.setString(paramCount, String.valueOf(ub));
				}
			}
			//logger.info(" this is sql getParentSearchAccountList :-"
			//		+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setBillableLoginName(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
//				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
//				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
//				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setTradeId(rs.getInt("DIST_CHANNEL_ID"));
				accountDto.setTradeCategoryId(rs.getInt("CHANNEL_CATEGORY_ID"));
				// accountDto.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountDto.setOutletType(rs.getInt("OUTLET_TYPE")+"");
				accountList.add(accountDto);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed ...");
		return accountList;
	}

	/**
	 * This method will be used provide total count of accounts created in the
	 * system. for internal user logged in.
	 * 
	 * @param reportInputDto -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return int - total number of records.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while counting the records.
	 */
	public int getAccountListCount(ReportInputs reportInputDto) throws DAOException {
		//logger.info("Started getAccountListCount()...reportInputDto " + reportInputDto);

		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		int circleId = reportInputDto.getCircleId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int noofRow = 1;
		int noofpages = 0;
		int paramCount = 1;
		String circleName = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			if (circleId != 0) {
				circleName = getCircleName(circleId);

				if (circleName.equalsIgnoreCase("NATIONAL")) {
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");
				} else {
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			} else {
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
					sql.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ?");
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

				if (0 != circleId) {
					if ((circleName.equalsIgnoreCase("NATIONAL"))
							&& (searchType.trim()
									.equalsIgnoreCase(Constants.SEARCH_TYPE_ALL))) {
						// If user selects search type other than ALL
						sql.append(queryMap
								.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
					} else if (circleName.equalsIgnoreCase("NATIONAL")
							&& (searchType.trim() != Constants.SEARCH_TYPE_ALL)) {
						// If user selects search type as ALL
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
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
		//logger.info("Executed getAccountListCount()...");
		return noofpages;
	}

	/**
	 * This method will be used provide a list of all account created in the
	 * system for internal user logged in. This will provide all the records and
	 * is independent of pagination logic.
	 * 
	 * @param reportInputDto -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getAccountListAll(ReportInputs reportInputDto) throws DAOException {
		//logger.info("Started getAccountListAll()...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		int circleId = reportInputDto.getCircleId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		String circleName = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();

		try {
			StringBuilder sql = new StringBuilder();
			int paramCount = 1;
			if (circleId != 0) {
				circleName = getCircleName(circleId);
				if (circleName.equalsIgnoreCase("NATIONAL")) {
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND VLM.STATUS = ? ");
				} else {
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND VLM.STATUS = ? ");
				}
			} else {
				/** if Administrator , show records from all circles */
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
				sql.append(" AND VLM.STATUS = ? ");
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
					sql.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ? ");
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

				if ((0 != circleId)
						&& (circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim()
								.equalsIgnoreCase(Constants.SEARCH_TYPE_ALL))) {
					// If user selects search type as ALL
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				} else if ((circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim() != Constants.SEARCH_TYPE_ALL)) {
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedBy(rs.getLong("CREATED_BY"));
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(accountDto);
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
	public ArrayList getChildAccountListAll(ReportInputs reportInputDto)
			throws DAOException {

		//logger.info("Started getChildAccountListAll() ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		long parentId = reportInputDto.getParentId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		int paramCount = 1;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();

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
					/* search according to parent accountDto */
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}
				ps.setLong(paramCount++, parentId);
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedBy(rs.getLong("CREATED_BY"));
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(accountDto);
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
	public int getChildAccountListCount(ReportInputs reportInputDto) throws DAOException {
		//logger.info("Started getChildAccountListCount()...");

		/** get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		long parentId = reportInputDto.getParentId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
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
		//logger.info("Executed getChildAccountListCount()...");
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
		//logger.info("Started ...searchType " + searchType
			//	+ " AND searchValue:=  " + searchValue + " AND circleId "
			//	+ circleId + " AND  accountLevelId:- " + accountLevelId);
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
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?   ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
				}
			}
			//logger.info("SQL............................" + sql);
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
		//logger.info("Executed ...");
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
		//logger.info("Started ...searchType " + searchType
			//	+ " AND searchValue:=  " + searchValue + " AND circleId "
			//	+ parentCircleId + " AND accountLevelId :" + accountLevelId);
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

//			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
//			sql
//					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
//			sql
//					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
//			sql.append(" WHERE	LEVEL_ID=" + accLevelId
//					+ "   ) AND level1.HIERARCHY_ID =(SELECT ");
//			sql
//					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID="
//							+ accLevelId + "))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ");
					sql.append(" with ur ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, parentCircleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME LIKE ? ) ");
					sql.append(" with ur ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, parentCircleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND DETAILS.ACCOUNT_NAME LIKE ? ");
					// logger.info(" SQL is :- " + sql);
					sql.append(" with ur ");
					ps = connection.prepareStatement(sql.toString());
					
					/** set the values in the statement */
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, parentCircleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND VLM.LOGIN_NAME LIKE ?  ");
					sql.append(" with ur ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, parentCircleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND DETAILS.EMAIL LIKE ? ");
					sql.append(" with ur ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, parentCircleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/*
					 * all child and sub-child and sub-sub-child accounts upto
					 * n-th level
					 */
					sql.append(" with ur ");
					ps = connection.prepareStatement(sql.toString());
					/** set the values in the statement */
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, parentCircleId);
				//	ps.setLong(2, parentId);
				}
			}
			logger.info("Query----3333333333333----->"+sql.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
		return noofpages;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAccountOpeningDate(long)
	 */
	public String getAccountOpeningDate(long accountId) throws DAOException {
		//logger.info(" Started.... Account ID : " + accountId);
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection
					.prepareStatement(queryMap.get(SQL_OPENING_DATE_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			Date opnDate;
			Date currDate;
			// if mobile no. exist
			if (rs.next()) {
				opnDate = rs.getDate("OPENING_DT");
				currDate = rs.getDate("TODAY_DT");
			//	logger.info("opening Date:" + opnDate);
				if (opnDate != null) {

					if (opnDate.compareTo(currDate) > 0) {
						logger.fatal("opening date is greater than current date");
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
		//logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_DEFAULT_GROUP_ID_KEY));
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
		//logger.info("Executed ...");
		return groupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getAllStates()
	 */
	public ArrayList getAllCircles() throws DAOException {
		//logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> stateList = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {

			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ALL_CIRCLES_KEY));
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					accountDto = new DPCreateAccountDto();
					accountDto.setCircleId(rs.getInt("CIRCLE_ID"));
					accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
					stateList.add(accountDto);
				}
			}

			return stateList;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getstates."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_STATE_NO_RECORD_FAILED);
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
	public ArrayList getCites(int zoneId) throws DAOException {

		//logger.info("Started...zoneId :" + zoneId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> accountCity = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_SELECT_CITES_KEY));
			ps.setInt(1, zoneId);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					accountDto = new DPCreateAccountDto();
					accountDto.setAccountCityId(rs.getInt("CITY_ID"));
					accountDto.setAccountCityName(rs.getString("CITY_NAME"));
					accountCity.add(accountDto);
				}
			}

			return accountCity;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getCity."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getBillableSearchAccountListCount(java.lang.String,
	 *      java.lang.String, int, java.lang.String, java.lang.String, long)
	 */

	public int getBillableSearchAccountListCount(String searchType,
			String searchValue, int circleId, String accountLevelId,
			String userType, long loginId) throws DAOException {
		//logger.info("Started ...searchType " + searchType
			//	+ " AND searchValue:=  " + searchValue + " AND circleId "
			//	+ circleId + " AND  accountLevelId:- " + accountLevelId
			//	+ " AND  userType :" + userType + " AND loginId:" + loginId);

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
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?   ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					ps = connection.prepareStatement(sql.toString());

					/** set the values in the statement */
					ps.setInt(1, circleId);
				}
			}

			//logger
			//		.info(" this is sql for count getBillableAccountListCount Search "
			//				+ sql.toString());
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
		//logger.info("Executed ...");
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
		//logger.info("Started ...searchType " + searchType
		//		+ " AND searchValue:=  " + searchValue + " AND circleId "
		//		+ circleId + " AND  accountLevelId:- " + accountLevelId
		//		+ " AND userType :" + userType + " AND loginId: " + loginId);
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> billableaccountList = new ArrayList<DPCreateAccountDto>();
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
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent accountDto */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to accountDto name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROWNUM rnum FROM ("
							+ sql.toString() + ") a "
							+ "Where ROWNUM<=?)Where rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
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
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
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
			//logger.info(" this is sql getBillableSearchAccountList :-"
				//	+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				billableaccountList.add(accountDto);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Billable List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed ...");
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
		//logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ROOT_LEVEL_ACCOUNT_ID_KEY));
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
		//logger.info("Executed ...");
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
		//logger.info(" Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_BILLABLE_ACCOUNT_ID_KEY));
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
	public DPCreateAccountDto getAccountByMobileNo(long mobileNo)
			throws DAOException {
		//logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_ACC_MOBILE_KEY));
			ps.setLong(1, mobileNo);
			rs = ps.executeQuery();

			if (rs.next()) {
				accountDto = new DPCreateAccountDto();
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// parent code according to id
				if (rs.getString("PARENT_ACCOUNT") != null)
					accountDto.setParentAccount(getAccountCode(rs
							.getLong("PARENT_ACCOUNT")));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setThreshold(rs.getDouble("Threshold"));
				accountDto.setCircleId(rs.getInt("CIRCLE_ID"));
				accountDto.setGroupId(rs.getInt("GROUP_ID"));
				accountDto.setMPassword(rs.getString("M_PASSWORD"));
				accountDto.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				accountDto.setAccountStateId(rs.getInt("STATE_ID"));
				accountDto.setAccountCityId(rs.getInt("CITY_ID"));
				accountDto.setIsbillable(rs.getString("BILLABLE_ACCOUNT"));
				accountDto.setAccountPinNumber(rs.getInt("PIN_NUMBER"));
				accountDto.setBillableCodeId(rs.getLong("BILLABLE_ACC_ID"));
				accountDto.setRootLevelId(rs.getLong("ROOT_LEVEL_ID"));
				accountDto.setCircleId(rs.getInt("CIRCLE_ID"));
				accountDto.setAccountLevel(rs.getString("ACCOUNT_LEVEL"));
				accountDto.setParentAccountId(rs.getLong("PARENT_ACCOUNT"));
				if (rs.getString("BILLABLE_ACC_ID") != null) {
					accountDto.setBillableCode(getAccountCode(rs
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
		//logger.info("Executed ...");
		return accountDto;
		}

	public long getCommissionByAccountId(long accountId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long commissionId = 0;
		//logger.info("Started ...");
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_DISTRIBUTOR_KEY));
			ps.setLong(1, accountId);
			rs = ps.executeQuery();

			while (rs.next()) {
				commissionId = rs.getLong("COMMISSIONING_ID");
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
		//logger.info("Executed ... ");
		return commissionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getActiveCircleName()
	 */
	public String getCircleName(int circleId) throws DAOException {
		//logger.info("Started...");
		String circleName = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_CIRCLE_NAME_KEY));
			ps.setInt(1, circleId);
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

	public int aggregateCount(ReportInputs reportInputDto) throws DAOException,
			SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		int noofpages = 0;
		try {
			if (!(reportInputDto.getSessionContext().getAccesslevel() == Constants.DISTRIBUTOR_ID)) {
				sql.append(queryMap.get(SQL_AGGREGATE_COUNT_KEY));

				if (reportInputDto.getCircleId() == 0) {
					sql.append(" with ur");
					ps = connection.prepareStatement(sql.toString());
				} else {
					sql.append(" AND account.CIRCLE_ID=? with ur");
					ps = connection.prepareStatement(sql.toString());
					ps.setInt(1, reportInputDto.getCircleId());
				}
			} else {
				sql.append(queryMap.get(SQL_AGGREGATE_COUNT_FOR_DIST_KEY));
				sql.append(" AND account1.CIRCLE_ID=? with ur");
				ps = connection.prepareStatement(sql.toString());
				ps.setLong(1, reportInputDto.getSessionContext().getId());
				ps.setInt(2, reportInputDto.getCircleId());
			}
			int count = 0;

			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(count);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		return noofpages;
	}

	public ArrayList aggregateView(ReportInputs reportInputDto, int lowerbound,
			int upperbound) throws DAOException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList<DPCreateAccountDto> agglist = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {
			String olddist = "";
			String oldfse = "";
			if (!(reportInputDto.getSessionContext().getAccesslevel() == Constants.DISTRIBUTOR_ID)) {
				sql.append(queryMap.get(SQL_AGGREGATE_LIST_KEY));
				if (reportInputDto.getCircleId() != 0) {
					sql.append(" account.ACCOUNT_LEVEL= "
							+ Constants.DIST_LEVEL_ID
							+ "  AND account.CIRCLE_ID=? ");
					sql.append(" ) as tr Where RNUM between ? and ? with ur");
					ps = connection.prepareStatement(sql.toString());
					ps.setInt(1, reportInputDto.getCircleId());
					ps.setString(2, String.valueOf(lowerbound + 1));
					ps.setString(3, String.valueOf(upperbound));
				} else {
					sql.append(" account.ACCOUNT_LEVEL="
							+ Constants.DIST_LEVEL_ID
							+ "  ) as tr Where RNUM between ? and ? with ur");
					ps = connection.prepareStatement(sql.toString());
					ps.setString(1, String.valueOf(lowerbound + 1));
					ps.setString(2, String.valueOf(upperbound));
				}
			} else {
				ps = connection.prepareStatement(queryMap
						.get(SQL_AGGREGATE_FOR_DIST_KEY));
				ps.setInt(1, reportInputDto.getCircleId());
				ps.setLong(2, reportInputDto.getSessionContext().getId());
				ps.setString(3, String.valueOf(lowerbound + 1));
				ps.setString(4, String.valueOf(upperbound));
				// rs = ps.executeQuery();
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				String newdist = "";
				if (!(reportInputDto.getSessionContext().getAccesslevel() == Constants.DISTRIBUTOR_ID)) {
					newdist = rs.getString("D_ID");
					accountDto.setDistId(newdist);
					if (olddist.equals(newdist)) {
						accountDto.setDistName("-----");
						accountDto.setDistLapuNo("-----");
					} else {
						olddist = accountDto.getDistId();
						accountDto.setDistName(rs.getString("D_NAME"));
						accountDto.setDistLapuNo(rs.getString("D_LAPU_NO"));
					}
				} else {
					newdist = String.valueOf(reportInputDto.getSessionContext().getId());
					accountDto.setDistId(newdist);
					if (olddist.equals(newdist)) {
						accountDto.setDistName("-----");
						accountDto.setDistLapuNo("-----");
					} else {
						olddist = accountDto.getDistId();
						accountDto.setDistName(reportInputDto.getSessionContext()
								.getLoginName());
						accountDto.setDistLapuNo(reportInputDto.getSessionContext()
								.getLapuMobileNo());
					}
				}
				String newfse = rs.getString("F_ID");
				accountDto.setFseId(newfse);

				if (newfse != null) {
					if (oldfse.equals(newfse)) {
						accountDto.setFseName("-----");
						accountDto.setFseLapuNo("-----");
					} else {
						oldfse = accountDto.getFseId();
						accountDto.setFseName(rs.getString("F_NAME"));
						accountDto.setFseLapuNo(rs.getString("F_LAPU_NO"));
					}
				} else {
				}
				accountDto.setRetailerId(rs.getString("R_ID"));
				accountDto.setRetailerName(rs.getString("R_NAME"));
				accountDto.setRetailerLapuNo(rs.getString("R_LAPU_NO"));
				accountDto.setRowNum(rs.getString("RNUM"));
				agglist.add(accountDto);
			}
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while retrieving Circle Name )"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		// TODO Auto-generated method stub
		return agglist;
	}

	public ArrayList getAggregateListAll(ReportInputs reportInputDto)
			throws VirtualizationServiceException, DAOException {
		//logger.info("Started getAccountListAll()...");
		/* get the data from thhe Modern Trade DTO */

		int circleId = reportInputDto.getCircleId();

		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> aggregateList = new ArrayList<DPCreateAccountDto>();

		try {
			if (!(reportInputDto.getSessionContext().getAccesslevel() == Constants.DISTRIBUTOR_ID)) {
				sql.append(queryMap.get(SQL_AGGREGATE_LIST_EXCEL_KEY));
				if (circleId == 0) {
					sql.append(" with ur");
					ps = connection.prepareStatement(sql.toString());
				} else {
					sql.append(" AND account.CIRCLE_ID=?  with ur");
					ps = connection.prepareStatement(sql.toString());
					ps.setInt(1, circleId);
				}
			} else {
				ps = connection.prepareStatement(queryMap
						.get(SQL_EXCEL_FOR_DIST_KEY));
				ps.setInt(1, circleId);
				ps.setLong(2, reportInputDto.getSessionContext().getId());
			}
			String olddist = "";
			String oldfse = "";
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				String newdist = "";
				if (!(reportInputDto.getSessionContext().getAccesslevel() == Constants.DISTRIBUTOR_ID)) {
					newdist = rs.getString("D_ID");
					accountDto.setDistId(newdist);
					if (olddist.equals(newdist)) {
						accountDto.setDistName("-----");
						accountDto.setDistLapuNo("-----");
					} else {
						olddist = accountDto.getDistId();
						accountDto.setDistName(rs.getString("D_NAME"));
						accountDto.setDistLapuNo(rs.getString("D_LAPU_NO"));
					}
				} else {
					newdist = String.valueOf(reportInputDto.getSessionContext().getId());
					accountDto.setDistId(newdist);
					if (olddist.equals(newdist)) {
						accountDto.setDistName("-----");
						accountDto.setDistLapuNo("-----");
					} else {
						olddist = accountDto.getDistId();
						accountDto.setDistName(reportInputDto.getSessionContext()
								.getLoginName());
						accountDto.setDistLapuNo(reportInputDto.getSessionContext()
								.getLapuMobileNo());
					}
				}
				String newfse = rs.getString("F_ID");
				accountDto.setFseId(newfse);

				if (newfse != null) {
					if (oldfse.equals(newfse)) {
						accountDto.setFseName("-----");
						accountDto.setFseLapuNo("-----");
					} else {
						oldfse = accountDto.getFseId();
						accountDto.setFseName(rs.getString("F_NAME"));
						accountDto.setFseLapuNo(rs.getString("F_LAPU_NO"));
					}
				} else {
				}
				accountDto.setRetailerId(rs.getString("R_ID"));
				accountDto.setRetailerName(rs.getString("R_NAME"));
				accountDto.setRetailerLapuNo(rs.getString("R_LAPU_NO"));
				aggregateList.add(accountDto);
			}
		} catch (SQLException e) {
			logger.error("Exception occured while reteriving all Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed getAggregateAccountListAll()...");
		return aggregateList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getZones(int)
	 */
	public ArrayList getStates(int circleId) throws DAOException {

		//logger.info("Started...Circle Id :---" + circleId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> accountState = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {

			ps = connection.prepareStatement(DBQueries.SQL_SELECT_STATES);
			ps.setInt(1, circleId);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					accountDto = new DPCreateAccountDto();
					accountDto.setAccountStateId(rs.getInt("STATE_ID"));
					accountDto.setAccountStateName(rs.getString("STATE_NAME"));
					accountState.add(accountDto);
				}
				accountState.size();
			}

			return accountState;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getState drop down..."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

			logger.info("End foe retrieving State Drop Down...Its Size is :---" + accountState.size());
		}
	}



	
	/*
	 * (non-Javadoc) ARTI
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getZones(int)
	 */
	public ArrayList getWareHouses(int circleId) throws DAOException {

		//logger.info("Started...Circle Id :-ARTI--" + circleId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> accountWarehouse = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {

			ps = connection.prepareStatement(DBQueries.SQL_SELECT_WAREHOUSES);
			ps.setInt(1, circleId);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					logger.info("WH_CODE"+rs.getString("WH_CODE"));
					logger.info("WH_NAME"+rs.getString("WH_NAME"));
					accountDto = new DPCreateAccountDto();
					accountDto.setAccountWarehouseCode(rs.getString("WH_CODE"));
					accountDto.setAccountWarehouseName(rs.getString("WH_NAME"));
					accountWarehouse.add(accountDto);
				}
				accountWarehouse.size();
			}

			return accountWarehouse;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getState drop down..."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

			//logger.info("End foe retrieving getWareHouses Drop Down...Its Size is :---" + accountWarehouse.size());
		}
	}

	//Added by ARTI for warehaouse list box BFR -11 release2

	public void insertWhDistMap(String warehouseCode,long loginId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//logger.info(" Started ..insertWhDistMap.");
		try {
			ps = connection.prepareStatement(SQL_INSERT_WH_DIST_MAP);
			ps.setString(1, warehouseCode);
			ps.setLong(2, loginId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured inserting warehousr distributor mapping in table  "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
		
	}

	 

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.AccountDao#getZones(int)
	 */
	public ArrayList getZones(int stateId) throws DAOException {

	//	logger.info("Started...stateId :" + stateId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> accountZone = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ZONE_KEY));
			ps.setInt(1, stateId);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					accountDto = new DPCreateAccountDto();
					accountDto.setAccountZoneId(rs.getInt("ZONE_ID"));
					accountDto.setAccountZoneName(rs.getString("ZONE_NAME"));
					accountZone.add(accountDto);
				}
				accountZone.size();
			}

			return accountZone;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getCity."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			//logger.info("Executed ....");
		}
	}
	
	public ArrayList getTradeList() throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> accountCity = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_DISTRIBUTOR_TYPE_KEY));
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					accountDto = new DPCreateAccountDto();
					accountDto.setTradeId(rs.getInt("CHANNEL_ID"));
					accountDto.setTradeName(rs.getString("CHANNEL_NAME"));
					accountCity.add(accountDto);
				}
			}
			return accountCity;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getCity."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	public ArrayList getTradeCategoryList(int tradeId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DPCreateAccountDto> getTradeCategoryList = new ArrayList<DPCreateAccountDto>();
		DPCreateAccountDto accountDto = null;
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_DISTRIBUTOR_CATEGORY_KEY));
			ps.setInt(1, tradeId);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					accountDto = new DPCreateAccountDto();
					accountDto.setTradeCategoryId(rs.getInt("CATEGORY_ID"));
					accountDto.setTradeCategoryName(rs.getString("CATEGORY_NAME"));
					getTradeCategoryList.add(accountDto);
				}
			}
			return getTradeCategoryList;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getCity."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}
	
	public DPCreateAccountDto getAccountLevelDetails(int levelId)throws DAOException{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		try{
//			ps = connection.prepareStatement(queryMap
//					.get(GET_DIST_LEVEL_DETAILS_KEY));
//			rs = ps.executeQuery();
//			int hId = 0;
//			int accessLevel = 0;
//			while (rs.next()){
//			hId = rs.getInt("HIERARCHY_ID");
//			accessLevel = rs.getInt("ACC_LEVEL");
//			}
			logger.info("Calling getAccountLevelDetails "+queryMap.get(GET_ACCOUNT_LEVEL_DETAILS_KEY)+"level ="+levelId);
			ResultSet rs1 = null;
			ps1 = connection.prepareStatement(queryMap.get(GET_ACCOUNT_LEVEL_DETAILS_KEY));
			ps1.setInt(1, levelId);
			rs1 = ps1.executeQuery();
			accountDto = new DPCreateAccountDto();
			while (rs1.next()){
				accountDto.setAccessLevelId(rs1.getInt("ACC_LEVEL"));
				accountDto.setHeirarchyId(rs1.getInt("HIERARCHY_ID"));
				accountDto.setAccountLevelName(rs1.getString("LEVEL_NAME"));
				//hierarchyId = accountDto.getHeirarchyId()+"";
			}
			
//			if(hId == accountDto.getHeirarchyId() && accessLevel <= accountDto.getAccessLevelId()){
//				flag = "true";
//			}else {
//				flag = "false";
//			}
		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while Checking Entered  Mobile No. is Exist   "
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ...ACCOUNT_ID ");
			}
		return accountDto;
	}
	
	/*
	 * Method to retrieve the External User and Password
	 * 
	 */
      public String getReportExternalInfo(int groupID)
			throws DAOException {
		//logger.info("Started...");
		String extPasswd = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_REPORT_AUTHENTICATION_KEY));
			ps.setInt(1,groupID );
			rs = ps.executeQuery();
			if (rs.next()) {
				extPasswd = rs.getString("REPORT_EXT_PASSWORD");
			} else {
			//	logger.error("NO Password found for ext group ID");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
			}
		//	logger.info("Executed .... ");
			return extPasswd;
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while retrieving Circle Name )"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
	}
  
      public ArrayList getAccountGroupId(int levelId)throws VirtualizationServiceException, DAOException{
    	  PreparedStatement ps = null;
  		ResultSet rs = null;
  		Integer groupId = 0;
  		String gname = null;
  		ArrayList groupDetails = null;
  		try{
  			groupDetails = new ArrayList();
   			ps = connection.prepareStatement(queryMap.get(SQL_ACCOUNT_GROUP_ID_KEY));
  			ps.setInt(1, levelId);
  			rs = ps.executeQuery();
  			while (rs.next()){
  			groupId = rs.getInt("GROUP_ID"); 
  			gname = rs.getString("GROUP_NAME");
  			groupDetails.add(groupId);
  			groupDetails.add(gname);
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException(ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return groupDetails;
      } 
 
	public DPCreateAccountDto getDPAccountID(int accountId) throws DAOException {// By Rohit k
		
		//logger.info("getDPAccountID Started...");
		long accID = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountdto = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ACCOUNTID_KEY));
			ps.setInt(1,accountId );
			rs = ps.executeQuery();
			if (rs.next()) {
				accountdto = new DPCreateAccountDto();
				accountdto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountdto.setAccountName((rs.getString("ACCOUNT_NAME")));
				accountdto.setUserType(rs.getString("TYPE"));
			} else {
			//	logger.error("NO ACCOUNT_ID found for ext ACCOUNT CODE");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
			}
			logger.info("Executed .... ");
			return accountdto;
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while retrieving Circle Name )"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
	}
	public ArrayList getManagerList()throws VirtualizationServiceException,SQLException{
    	PreparedStatement ps = null;
  		ResultSet rs = null;
  		ArrayList<ArrayList<DPCreateAccountDto>> managerDetails = null;
  		try{
  			managerDetails = new ArrayList<ArrayList<DPCreateAccountDto>>();
  			ArrayList<DPCreateAccountDto> financeList = new ArrayList<DPCreateAccountDto>();
  			ArrayList<DPCreateAccountDto> commList = new ArrayList<DPCreateAccountDto>();
  			ArrayList<DPCreateAccountDto> salesList = new ArrayList<DPCreateAccountDto>();
   			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_FINANCE_KEY));
   			rs = ps.executeQuery();
  			while (rs.next()){
  				DPCreateAccountDto createAccount = new DPCreateAccountDto();
  				if(rs.getString("GROUP_NAME").equalsIgnoreCase(Constants.MANAGER_FINANCE)){
  					createAccount.setFinanceId(rs.getInt("USER_ID"));
  	  				createAccount.setFinanceName(rs.getString("LOGIN_NAME"));
  	  				financeList.add(createAccount);
  				}
  				else if(rs.getString("GROUP_NAME").equalsIgnoreCase(Constants.MANAGER_SALES)){
  					createAccount.setSalesId(rs.getInt("USER_ID"));
  	  				createAccount.setSalesName(rs.getString("LOGIN_NAME"));
  	  				salesList.add(createAccount);
  				}
  				else if(rs.getString("GROUP_NAME").equalsIgnoreCase(Constants.MANAGER_COMMERCE)){
  					createAccount.setCommerceId(rs.getInt("USER_ID"));
  	  				createAccount.setCommerceName(rs.getString("LOGIN_NAME"));
  	  				commList.add(createAccount);
  				}
  			}
				managerDetails.add(financeList);
  				managerDetails.add(salesList);
  				managerDetails.add(commList);
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return managerDetails;
      } 
      
      public ArrayList getAllBeats(int cityId)throws VirtualizationServiceException,SQLException{
      	PreparedStatement ps = null;
    		ResultSet rs = null;
    		ArrayList<DPCreateAccountDto> beatList = null;
    		try{
    			beatList = new ArrayList<DPCreateAccountDto>();
     			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_BEATS_KEY));
     			ps.setInt(1, cityId);
     			rs = ps.executeQuery();
    			while (rs.next()){
    				DPCreateAccountDto createAccount = new DPCreateAccountDto();
    				createAccount.setBeatId(rs.getInt("BEAT_CODE"));
    				createAccount.setBeatName(rs.getString("BEAT_NAME"));
    				beatList.add(createAccount);
    			}
    		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			try {
  				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
  			} catch (DAOException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
  		} finally {
  			// Close the resultset, statement.
  			DBConnectionManager.releaseResources(ps, rs);
  		}
  		return beatList;
        }
      
      
      public ArrayList getAllDistBeats(int cityId, Long loginId)throws VirtualizationServiceException,SQLException{
        	PreparedStatement ps = null;
      		ResultSet rs = null;
      		ArrayList<DPCreateAccountDto> beatList = null;
      		try{
      			beatList = new ArrayList<DPCreateAccountDto>();
      			StringBuffer sb = new StringBuffer();
      			sb.append("SELECT BEAT_CODE, BEAT_NAME FROM DP_BEAT_MASTER where CITY_ID=? AND Status = 'A' and created_by=? with ur");
       			ps = connection.prepareStatement(sb.toString());
       			ps.setInt(1, cityId);
       			ps.setLong(2, loginId);
       			rs = ps.executeQuery();
      			while (rs.next()){
      				DPCreateAccountDto createAccount = new DPCreateAccountDto();
      				createAccount.setBeatId(rs.getInt("BEAT_CODE"));
      				createAccount.setBeatName(rs.getString("BEAT_NAME"));
      				beatList.add(createAccount);
      			}
      		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			try {
    				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
    			} catch (DAOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		} finally {
    			// Close the resultset, statement.
    			DBConnectionManager.releaseResources(ps, rs);
    		}
    		return beatList;
          } 

      
      public ArrayList getRetailerCategoryList()throws VirtualizationServiceException{

        	PreparedStatement ps = null;
      		ResultSet rs = null;
      		ArrayList<DPCreateAccountDto> retailerList = null;
      		try{
      			retailerList = new ArrayList<DPCreateAccountDto>();
       			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_RETAILER_CATEGORY_KEY));
       			rs = ps.executeQuery();
      			while (rs.next()){
      				DPCreateAccountDto createAccount = new DPCreateAccountDto();
      				createAccount.setRetailerType(rs.getInt("RETAILER_CATEGORY_ID")+"");
      				createAccount.setRetailerTypeName(rs.getString("RETAILER_CATEGORY_NAME"));
      				retailerList.add(createAccount);
      			}
      		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			try {
    				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
    			} catch (DAOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		} finally {
    			// Close the resultset, statement.
    			DBConnectionManager.releaseResources(ps, rs);
    		}
    		return retailerList;
          
      }

	public ArrayList getOutletList() throws VirtualizationServiceException {

    	PreparedStatement ps = null;
  		ResultSet rs = null;
  		ArrayList<DPCreateAccountDto> outletList = null;
  		try{
  			outletList = new ArrayList<DPCreateAccountDto>();
   			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_OUTLET_CATEGORY_KEY));
   			rs = ps.executeQuery();
  			while (rs.next()){
  				DPCreateAccountDto createAccount = new DPCreateAccountDto();
  				createAccount.setOutletType(rs.getInt("OUTLET_ID")+"");
  				createAccount.setOutletName(rs.getString("OUTLET_NAME"));
  				outletList.add(createAccount);
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return outletList;
  }
	
	public ArrayList getAltChannelList() throws VirtualizationServiceException{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		ArrayList<DPCreateAccountDto> altChannelList = null;
  		try{
  			altChannelList = new ArrayList<DPCreateAccountDto>();
   			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ALT_CHANNEL_KEY));
   			rs = ps.executeQuery();
  			while (rs.next()){
  				DPCreateAccountDto createAccount = new DPCreateAccountDto();
  				createAccount.setAltChannelType(rs.getInt("ALT_CHANNEL_ID")+"");
  				createAccount.setAltChannelName(rs.getString("ALT_CHANNEL_NAME"));
  				altChannelList.add(createAccount);
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return altChannelList;
	}
	
	public ArrayList getChannelList() throws VirtualizationServiceException{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		ArrayList<DPCreateAccountDto> channelList = null;
  		try{
  			channelList = new ArrayList<DPCreateAccountDto>();
   			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_CHANNEL_KEY));
   			rs = ps.executeQuery();
  			while (rs.next()){
  				DPCreateAccountDto createAccount = new DPCreateAccountDto();
  				createAccount.setChannelType(rs.getInt("CHANNEL_TYPE_ID")+"");
  				createAccount.setChannelName(rs.getString("CHANNEL_TYPE_NAME"));
  				channelList.add(createAccount);
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return channelList;
	}
	
	public String getEmailId(long accountId) throws DAOException{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		String emailId = "";
  		try{
   			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_EMAILID_KEY));
   			ps.setLong(1, accountId);
   			rs = ps.executeQuery();
  			while (rs.next()){
  				emailId = rs.getString("EMAIL");
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return emailId;
	}
	
	
	public String[] getRetailerInfo (String mobilenumber) throws DAOException
	{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		String []retailerInfoArray = new String[19];	
  		StringBuffer sbstr = new StringBuffer();
  		String lapuFlag="";
  		
  		try {
			ps=connection.prepareStatement("select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=31");
			rs=ps.executeQuery();
			while(rs.next())
				lapuFlag=rs.getString("DD_VALUE");
			
		} catch (SQLException e1) {
			logger.info("Exception in executing select on DP_CONFIGURATION_DETAILS table");
			e1.printStackTrace();
		}
		try
		{
			if(lapuFlag !=null && lapuFlag.equalsIgnoreCase("N")){
			sbstr.append(" SELECT ACCOUNT_ID, MOBILE_NUMBER, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
			sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
			sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
			sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
			sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
			sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
			sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ");
			sbstr.append(" ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS VAD");
			//sbstr.append(" WHERE ACCOUNT_LEVEL=8 and MOBILE_NUMBER=? with ur ");
			//Changed by Nazim Hussain as retailer number should be check in all relevent fields
			sbstr.append(" WHERE ACCOUNT_LEVEL=8 and (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) with ur ");
			}
			
			else if(lapuFlag!=null && lapuFlag.equalsIgnoreCase("Y"))
			{

				sbstr.append(" SELECT ACCOUNT_ID, RETAILER_LAPU, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
							sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
							sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
							sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
							sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
							sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
							sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ");
							sbstr.append(" ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS VAD");
							
							sbstr.append(" WHERE ACCOUNT_LEVEL=8 and (VAD.RETAILER_LAPU=?) with ur ");
			}
			ps = connection.prepareStatement(sbstr.toString());
			ps.setString(1, mobilenumber);
			if(lapuFlag !=null && lapuFlag.equalsIgnoreCase("N")){
				
				ps.setString(2, mobilenumber);
				ps.setString(3, mobilenumber);
				ps.setString(4, mobilenumber);
				ps.setString(5, mobilenumber);
			}
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
				
				if(lapuFlag !=null && lapuFlag.equalsIgnoreCase("N"))
					retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");
				else if(lapuFlag!=null && lapuFlag.equalsIgnoreCase("Y"))
					retailerInfoArray[1]= rs.getString("RETAILER_LAPU");
				retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
				retailerInfoArray[3]= rs.getString("EMAIL");
				retailerInfoArray[4]= rs.getString("CIRCLE_ID");
				retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
				retailerInfoArray[6]= rs.getString("CITY_ID");
				retailerInfoArray[7]= rs.getString("CITY_NAME");
				retailerInfoArray[8]= rs.getString("ADDRESS_2");
				retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
				retailerInfoArray[10]= rs.getString("CONTACT_NAME");
				retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
				retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
				retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
				retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
				retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
				retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
				retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
				retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
			}
			else
			{
				logger.error("*********** Invalid Mobile Number ****************");
				retailerInfoArray = new String []{"Invalid Mobile Number"};
			}
		
  		} 
		catch (SQLException e) 
		{
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		catch (Exception e)
		{
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally 
		{
			try
			{	
				DBConnectionManager.releaseResources(ps, rs);
				sbstr = null;
			}
			catch (Exception e){ 
				logger.error("Error while releasing connection "+ e.getMessage()); 
				}
		}
		return retailerInfoArray;
	}
	
//	Add by harbans for cannon webservice
	
	
	public String[] getRetailerInfoCannon(String mobilenumber) throws DAOException
	{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		String lapuFlag="";
  		String []retailerInfoArray = new String[23];	
  		StringBuffer sbstr = new StringBuffer();
  		try {
			ps=connection.prepareStatement("select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=31 ");
			rs=ps.executeQuery();
			while(rs.next())
				lapuFlag=rs.getString("DD_VALUE");
			
		} catch (SQLException e1) {
			logger.info("Exception in executing select on DP_CONFIGURATION_DETAILS table");
			e1.printStackTrace();
		}
		
		
		try
		{
			if(lapuFlag !=null && lapuFlag.equalsIgnoreCase("N")){
			sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID,  ");
			sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
			sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE,  ");
			sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
			sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME , ");
			sbstr.append(" VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = VAD3.ACCOUNT_ID) DIST_OLM_ID, ");
			sbstr.append(" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=VAD3.PARENT_ACCOUNT) TSM_MOBILE FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM, VR_ACCOUNT_DETAILS VAD2, VR_ACCOUNT_DETAILS VAD3 ");
			sbstr.append(" WHERE VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID AND VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID AND VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A'  "); 
			sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
			
			}
			
			else if(lapuFlag!=null && lapuFlag.equalsIgnoreCase("Y"))
			{
				sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.RETAILER_LAPU, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID,  ");
				sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
				sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE,  ");
				sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
				sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME , ");
				sbstr.append(" VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = VAD3.ACCOUNT_ID) DIST_OLM_ID, ");
				sbstr.append(" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=VAD3.PARENT_ACCOUNT) TSM_MOBILE FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM, VR_ACCOUNT_DETAILS VAD2, VR_ACCOUNT_DETAILS VAD3 ");
				sbstr.append(" WHERE VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID AND VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID AND VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A'  "); 
				sbstr.append(" AND (VAD.RETAILER_LAPU=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
			}
			logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
			
			ps = connection.prepareStatement(sbstr.toString());
			ps.setString(1, mobilenumber);
			if(lapuFlag !=null && lapuFlag.equalsIgnoreCase("N")){
			ps.setString(2, mobilenumber);
			ps.setString(3, mobilenumber);
			ps.setString(4, mobilenumber);
			ps.setString(5, mobilenumber);
			}
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
				if(lapuFlag!=null && lapuFlag.equalsIgnoreCase("N"))
					retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");
			
				else if(lapuFlag!=null && lapuFlag.equalsIgnoreCase("Y"))
					retailerInfoArray[1]= rs.getString("RETAILER_LAPU");
				retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
				retailerInfoArray[3]= rs.getString("EMAIL");
				retailerInfoArray[4]= rs.getString("CIRCLE_ID");
				retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
				retailerInfoArray[6]= rs.getString("CITY_ID");
				retailerInfoArray[7]= rs.getString("CITY_NAME");
				retailerInfoArray[8]= rs.getString("ADDRESS_2");
				retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
				retailerInfoArray[10]= rs.getString("CONTACT_NAME");
				retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
				retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
				retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
				retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
				retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
				retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
				retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
				retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
				retailerInfoArray[19]= rs.getString("DIST_NAME");
				retailerInfoArray[20]= rs.getString("DIST_MOBLE_NO");
				retailerInfoArray[21]= rs.getString("DIST_OLM_ID");
				retailerInfoArray[22]= rs.getString("TSM_MOBILE");
			}
			else
			{
				logger.error("*********** Invalid Mobile Number ****************");
				retailerInfoArray = new String []{"Invalid Mobile Number"};
			}
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally 
		{
			try
			{	
				DBConnectionManager.releaseResources(ps, rs);
				sbstr = null;
			}
			catch (Exception e){ 
				e.printStackTrace();
				logger.error("Error while releasing connection "+ e.getMessage()); 
				}
		}
		return retailerInfoArray;
	}

	public String[] getRetailerDistInfoCannon(String mobilenumber, String pinCode) throws DAOException 
	{
		PreparedStatement ps = null;
  		ResultSet rs = null;
  		String []retailerInfoArray = new String[23];	
  		StringBuffer sbstr = new StringBuffer();
  		String lapuFlag="";
  		
  		try {
			ps=connection.prepareStatement("select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=31 ");
			rs=ps.executeQuery();
			while(rs.next())
				lapuFlag=rs.getString("DD_VALUE");
			
		} catch (SQLException e1) {
			logger.info("Exception in executing select on DP_CONFIGURATION_DETAILS table");
			e1.printStackTrace();
		}
		
		try
		{
			logger.info("MOBILE NUMBER : " + mobilenumber);
			logger.info("PIN CODE : " + pinCode);
			boolean bol = validPincode(pinCode);
			
			logger.info(" pinCode valid ::  "+bol);
			if(bol)
			{
				String strQuery = "select ACCOUNT_NAME, MOBILE_NUMBER, (SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=?) AS LOGIN_NAME, (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=AD.PARENT_ACCOUNT) TSM_MOBILE from VR_ACCOUNT_DETAILS AD where AD.ACCOUNT_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=(SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=? )) with ur";
				
				ps = connection.prepareStatement(strQuery);
				ps.setString(1, pinCode.trim());
				ps.setString(2, pinCode.trim());
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					retailerInfoArray[19]= rs.getString("ACCOUNT_NAME");
					retailerInfoArray[20]= rs.getString("MOBILE_NUMBER");
					retailerInfoArray[21]= rs.getString("LOGIN_NAME");
					retailerInfoArray[22]= rs.getString("TSM_MOBILE");
					
					logger.info("ACCOUNT_NAME  ::  "+retailerInfoArray[19]);
					logger.info("MOBILE_NUMBER  ::  "+retailerInfoArray[20]);
					logger.info("LOGIN_NAME  ::  "+retailerInfoArray[21]);
					logger.info("TSM_MOBILE  :: "+retailerInfoArray[22]);
					
					DBConnectionManager.releaseResources(ps, rs);
					
					if (lapuFlag.equalsIgnoreCase("Y")){
						sbstr.append(" SELECT ACCOUNT_ID, RETAILER_LAPU, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
					}
					else
					{
						sbstr.append(" SELECT ACCOUNT_ID, MOBILE_NUMBER, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");	
					}
					
					sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
					sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
					sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
					sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
					sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
					sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ACCOUNT_NAME ");
					sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM  ");
					sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' ");
					if (lapuFlag.equalsIgnoreCase("Y"))
					{
					sbstr.append(" AND (VAD.RETAILER_LAPU=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
					}
					else
					{
						sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
					}
					logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
					
					ps = connection.prepareStatement(sbstr.toString());
					ps.setString(1, mobilenumber);
					if(lapuFlag.equalsIgnoreCase("N"))
					{
						ps.setString(2, mobilenumber);
						ps.setString(3, mobilenumber);
						ps.setString(4, mobilenumber);
						ps.setString(5, mobilenumber);
					}
					
					rs = ps.executeQuery();
					
					if(rs.next())
					{
						retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
						if(lapuFlag.equalsIgnoreCase("Y"))
						{
							retailerInfoArray[1]= rs.getString("RETAILER_LAPU");
						}
						else
						{
							retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");	
						}
						
						retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
						retailerInfoArray[3]= rs.getString("EMAIL");
						retailerInfoArray[4]= rs.getString("CIRCLE_ID");
						retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
						retailerInfoArray[6]= rs.getString("CITY_ID");
						retailerInfoArray[7]= rs.getString("CITY_NAME");
						retailerInfoArray[8]= rs.getString("ADDRESS_2");
						retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
						retailerInfoArray[10]= rs.getString("CONTACT_NAME");
						retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
						retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
						retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
						retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
						retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
						retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
						retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
						retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
					}
				}
				else
				{
					if(lapuFlag.equalsIgnoreCase("Y"))
					{
						sbstr.append(" SELECT VAD.RETAILER_LAPU, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID,  ");
					}
					else
					{
						sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID,  ");	
					}
					
					sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
					sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE,  ");
					sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
					sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME , ");
					sbstr.append(" VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = VAD3.ACCOUNT_ID) DIST_OLM_ID, ");
					sbstr.append(" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=VAD3.PARENT_ACCOUNT) TSM_MOBILE FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM, VR_ACCOUNT_DETAILS VAD2, VR_ACCOUNT_DETAILS VAD3 ");
					sbstr.append(" WHERE VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID AND VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID AND VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A'  "); 
					if(lapuFlag.equalsIgnoreCase("Y"))
					{
						sbstr.append(" AND (VAD.RETAILER_LAPU=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
					}
					else
					{
					sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
					}
					logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
					
					ps = connection.prepareStatement(sbstr.toString());
					ps.setString(1, mobilenumber);
					if(lapuFlag.equalsIgnoreCase("N"))
					{
						ps.setString(2, mobilenumber);
						ps.setString(3, mobilenumber);
						ps.setString(4, mobilenumber);
						ps.setString(5, mobilenumber);
					}
					
					rs = ps.executeQuery();
					
					if(rs.next())
					{
						retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
						if(lapuFlag.equalsIgnoreCase("Y"))
						{
							retailerInfoArray[1]= rs.getString("RETAILER_LAPU");
						}
						else
						{
							retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");	
						}
						
						retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
						retailerInfoArray[3]= rs.getString("EMAIL");
						retailerInfoArray[4]= rs.getString("CIRCLE_ID");
						retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
						retailerInfoArray[6]= rs.getString("CITY_ID");
						retailerInfoArray[7]= rs.getString("CITY_NAME");
						retailerInfoArray[8]= rs.getString("ADDRESS_2");
						retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
						retailerInfoArray[10]= rs.getString("CONTACT_NAME");
						retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
						retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
						retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
						retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
						retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
						retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
						retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
						retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
						retailerInfoArray[19]= rs.getString("DIST_NAME");
						retailerInfoArray[20]= rs.getString("DIST_MOBLE_NO");
						retailerInfoArray[21]= rs.getString("DIST_OLM_ID");
					}
				}
			}
			else
			{
				if(lapuFlag.equalsIgnoreCase("Y"))
				{
					sbstr.append(" SELECT ACCOUNT_ID, RETAILER_LAPU, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
				}
				else
				{
					sbstr.append(" SELECT ACCOUNT_ID, MOBILE_NUMBER, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
				}
				
				sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
				sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
				sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
				sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
				sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
				sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ACCOUNT_NAME , ");
				sbstr.append(" (SELECT ACCOUNT_NAME  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT)) DIST_NAME, "); 
				sbstr.append(" (SELECT MOBILE_NUMBER  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT)) DIST_MOBLE_NO, ");
				sbstr.append(" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = (SELECT ACCOUNT_ID  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT))) DIST_OLM_ID ");
				sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM  ");
				sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' ");
				if(lapuFlag.equalsIgnoreCase("Y")){
					sbstr.append(" AND (VAD.RETAILER_LAPU=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
				}
				else
				{
					sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
				}
				
				
			//	logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
				
				ps = connection.prepareStatement(sbstr.toString());
				ps.setString(1, mobilenumber);
				if(lapuFlag.equalsIgnoreCase("N"))
				{
					ps.setString(2, mobilenumber);
					ps.setString(3, mobilenumber);
					ps.setString(4, mobilenumber);
					ps.setString(5, mobilenumber);
				}
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
					if(lapuFlag.equalsIgnoreCase("Y"))
					{
						retailerInfoArray[1]= rs.getString("RETAILER_LAPU");
					}
					else
					{
						retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");	
					}
					retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
					retailerInfoArray[3]= rs.getString("EMAIL");
					retailerInfoArray[4]= rs.getString("CIRCLE_ID");
					retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
					retailerInfoArray[6]= rs.getString("CITY_ID");
					retailerInfoArray[7]= rs.getString("CITY_NAME");
					retailerInfoArray[8]= rs.getString("ADDRESS_2");
					retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
					retailerInfoArray[10]= rs.getString("CONTACT_NAME");
					retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
					retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
					retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
					retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
					retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
					retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
					retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
					retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
					retailerInfoArray[19]= rs.getString("DIST_NAME");
					retailerInfoArray[20]= rs.getString("DIST_MOBLE_NO");
					retailerInfoArray[21]= rs.getString("DIST_OLM_ID");
				}
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally 
		{
			try
			{	
				DBConnectionManager.releaseResources(ps, rs);
				sbstr = null;
			}
			catch (Exception e){ 
				e.printStackTrace();
				logger.error("Error while releasing connection "+ e.getMessage()); 
			}
		}
		return retailerInfoArray;
	}

	private boolean validPincode(String pinCode) 
	{
		boolean bolReturn = false;
		
		if(pinCode!=null && pinCode.trim().length()>0)
		{
			try
			{
				int intPinCode = Integer.parseInt(pinCode.trim());
				bolReturn = true;
			}
			catch(Exception e)
			{
				return bolReturn;
			}
		}
		else
		{
			return bolReturn;
		}
		return bolReturn;
	}
	
	
	//Created by Shilpa on 17-01-2012 for Critical changes CR
	
	public String getAccountName(long accountId) throws DAOException{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		String loginName = "";
  		try{
   			ps = connection.prepareStatement(SQL_SELECT_LOGIN_NAME_KEY);
   			ps.setLong(1, accountId);
   			rs = ps.executeQuery();
  			while (rs.next()){
  				loginName = rs.getString("CONTACT_NAME");
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return loginName;
	}
	
	public String validatePasswordExpiry(Login login) throws DAOException{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		int dateDiff=0;
  		String retFlag="true";
  		int maxPwddays = 0;
  		try{
   			ps = connection.prepareStatement(SQL_SELECT_PWD_EXPIRY_KEY);
   			maxPwddays = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.PASSWORD_EXPIRING_DAYS));
   			ps.setString(1, login.getLoginName());
   			rs = ps.executeQuery();
  			if (rs.next()){
  				dateDiff = rs.getInt("DATEDIFF");
  				
  				if(dateDiff >= maxPwddays)
  				{
  					retFlag="false";
  				}
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
  		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return retFlag;
	}
	
	
	
	public String disLocCodeExist(String disLocCode) throws DAOException {

		PreparedStatement ps3 = null;
  		String distLocator = "";
  		ResultSet rs3 = null;
  		try{
  			//  Code added by Karan on 2 July 2012 for duplicate Dist Locator Code Validation
  			ps3 = connection.prepareStatement(GET_DIST_LOCATOR);
  			ps3.setString(1,disLocCode);
  			rs3 = ps3.executeQuery();			
  			while (rs3.next()) {
  				distLocator = rs3.getString("DISTRIBUTOR_LOCATOR");
  				}
  			
  		/*	if(distLocator == null || distLocator.equals(""))
  			{
  				logger.info("\n\ndistLocator"+distLocator);
  			}
  			else
  			{
  				throw new DAOException("Duplicate Dist Locator Code!!");
  			} */
  			
  			//Dist Locator Code Validation ends here
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps3, rs3);
		}
		return distLocator;
		
		
	}
	

	/**
	 * getSWLocatorList method is called to return the complete list of all SW
	 * Locators
	 * 
	 * @return ArrayList
	 * @throws DAOException
	 */
	/*public ArrayList<SWLocatorListFormBean> getSWLocatorList()
			throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT SW_ID, SW_COMPANY_CODE, SW_AREA_CODE, SW_SUBAREA_CODE, SW_SOURCE_TYPE, SW_CIRCLE, USERID ");
		sql.append("FROM VR_SW_LOCATOR_MASTER");
		ResultSet rs = null;
		ArrayList<SWLocatorListFormBean> swLocatorList = new ArrayList<SWLocatorListFormBean>();
		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				SWLocatorListFormBean swLocator = new SWLocatorListFormBean();
				swLocator.setSwId(rs.getInt("SW_ID"));
				swLocator.setSwCoAreaSubareaSrcType(rs
						.getString("SW_COMPANY_CODE")
						+ "."
						+ rs.getString("SW_AREA_CODE")
						+ "."
						+ rs.getString("SW_SUBAREA_CODE")
						+ "."
						+ rs.getString("SW_SOURCE_TYPE"));
				swLocatorList.add(swLocator);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return swLocatorList;
	}*/
	
	public String isNewUser(long loginId) throws DAOException{

		PreparedStatement ps = null;
  		ResultSet rs = null;
  		int result=0;
  		String retFlag="false";
  		try{
   			ps = connection.prepareStatement(SQL_SELECT_IS_NEW_USER_KEY);
   			ps.setLong(1, loginId);
   			rs = ps.executeQuery();
  			if (rs.next()){
  				result = rs.getInt("result");
  				
  				if(result == 0)
  				{
  					retFlag="true";
  				}
  			}
  		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			// Close the resultset, statement.
			DBConnectionManager.releaseResources(ps, rs);
		}
		return retFlag;
	}
	
	public String getStatus(String accountCode) throws DAOException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		String status="";	
		try {
			ps = connection.prepareStatement(SQL_SELECT_STATUS);
			ps.setString(1, accountCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				status = rs.getString("STATUS");
			}

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while getting Account Id  "
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
			/* Close the statement,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		//logger.info("Executed ...");
		return status;
	}

	public String[] getRetailerDistInfoCannonNewOld(String mobilenumber, String pinCode) throws DAOException 
	{
		PreparedStatement ps = null;
  		ResultSet rs = null;
  		
  		PreparedStatement ps1 = null;
  		ResultSet rs1 = null;
  		
  		String []retailerInfoArray = new String[26];	
  		StringBuffer sbstr = new StringBuffer();
		try
		{
			logger.info("MOBILE NUMBER : " + mobilenumber);
			logger.info("PIN CODE : " + pinCode);
			boolean bol = validPincode(pinCode);
			
			logger.info(" pinCode valid ::  "+bol);
			if(bol)
			{
				
				String mobilevalidQuery = "select MOBILE_NUMBER from VR_ACCOUNT_DETAILS a , VR_LOGIN_MASTER b where ACCOUNT_LEVEL=8  "
										+" AND (MOBILE_NUMBER=? OR FTA_MOBILE_NO=? OR FTA_MOBILE_NO_2=? OR LAPU_MOBILE_NO=? OR LAPU_MOBILE_NO_2=? ) and a.ACCOUNT_ID=b.LOGIN_ID "
										+" and b.STATUS = 'A' with ur ";
										
														
				ps1 = connection.prepareStatement(mobilevalidQuery);
				ps1.setString(1, mobilenumber);
				ps1.setString(2, mobilenumber);
				ps1.setString(3, mobilenumber);
				ps1.setString(4, mobilenumber);
				ps1.setString(5, mobilenumber);
				rs1 = ps1.executeQuery();
				
				if(rs1.next())
				{
					//String strQuery = "select ACCOUNT_NAME, MOBILE_NUMBER, (SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=?) AS LOGIN_NAME, (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=AD.PARENT_ACCOUNT) TSM_MOBILE from VR_ACCOUNT_DETAILS AD where AD.ACCOUNT_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=(SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=? )) with ur";
					//
					//Commented by nazim hussain to change query as TSM is coming form DP_DISTRIBUTOR_MAPPING after AV launch
					/*String strQuery = "select AD.ACCOUNT_NAME, AD.MOBILE_NUMBER, (SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=?) AS LOGIN_NAME, "
									+" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=AD.PARENT_ACCOUNT) TSM_MOBILE, "
									+" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE login_ID=AD.PARENT_ACCOUNT) TSM_OLM_ID, "
									+" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=ad1.PARENT_ACCOUNT) ZSM_MOBILE, "
									+" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE login_ID=ad1.PARENT_ACCOUNT) ZSM_OLM_ID "
									+" from VR_ACCOUNT_DETAILS AD ,VR_ACCOUNT_DETAILS ad1 "
									+" where AD.ACCOUNT_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=(SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=? )) " 
									+" AND ad.PARENT_ACCOUNT=ad1.ACCOUNT_ID "
									+" with ur ";*/
					String strQuery = "select AD.ACCOUNT_NAME, AD.MOBILE_NUMBER, (SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=?) AS LOGIN_NAME, AD1.MOBILE_NUMBER as TSM_MOBILE, lm.LOGIN_NAME as TSM_OLM_ID, (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=ad1.PARENT_ACCOUNT) ZSM_MOBILE," +
							" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE login_ID=ad1.PARENT_ACCOUNT) ZSM_OLM_ID from VR_ACCOUNT_DETAILS AD inner join DP_DISTRIBUTOR_MAPPING DM on AD.ACCOUNT_ID=DM.DP_DIST_ID and TRANSACTION_TYPE_ID=2" +
							" inner join VR_ACCOUNT_DETAILS AD1 on DM.PARENT_ACCOUNT=AD1.ACCOUNT_ID inner join VR_LOGIN_MASTER LM on AD1.ACCOUNT_ID=lm.LOGIN_ID where AD.ACCOUNT_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=(SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=? )) with ur";
					
					ps = connection.prepareStatement(strQuery);
					ps.setString(1, pinCode.trim());
					ps.setString(2, pinCode.trim());
					rs = ps.executeQuery();
					
					if(rs.next())
					{
						retailerInfoArray[19]= rs.getString("ACCOUNT_NAME");
						retailerInfoArray[20]= rs.getString("MOBILE_NUMBER");
						retailerInfoArray[21]= rs.getString("LOGIN_NAME");
						retailerInfoArray[22]= rs.getString("TSM_MOBILE");
						
						retailerInfoArray[23]= rs.getString("TSM_OLM_ID");
						retailerInfoArray[24]= rs.getString("ZSM_MOBILE");
						retailerInfoArray[25]= rs.getString("ZSM_OLM_ID");
					
						DBConnectionManager.releaseResources(ps, rs);
						
						sbstr.append(" SELECT ACCOUNT_ID, MOBILE_NUMBER, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
						sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
						sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
						sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
						sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
						sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
						sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ACCOUNT_NAME ");
						sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM  ");
						sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' ");
						sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
						
						logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
						
						ps = connection.prepareStatement(sbstr.toString());
						ps.setString(1, mobilenumber);
						ps.setString(2, mobilenumber);
						ps.setString(3, mobilenumber);
						ps.setString(4, mobilenumber);
						ps.setString(5, mobilenumber);
						rs = ps.executeQuery();
					
						if(rs.next())
						{
							retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
							retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");
							retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
							retailerInfoArray[3]= rs.getString("EMAIL");
							retailerInfoArray[4]= rs.getString("CIRCLE_ID");
							retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
							retailerInfoArray[6]= rs.getString("CITY_ID");
							retailerInfoArray[7]= rs.getString("CITY_NAME");
							retailerInfoArray[8]= rs.getString("ADDRESS_2");
							retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
							retailerInfoArray[10]= rs.getString("CONTACT_NAME");
							retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
							retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
							retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
							retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
							retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
							retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
							retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
							retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
						}
					}
					else
					{
						/*sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID,  ");
						sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
						sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE,  ");
						sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
						sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME , ");
						sbstr.append(" VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = VAD3.ACCOUNT_ID) DIST_OLM_ID, ");
						sbstr.append(" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=VAD3.PARENT_ACCOUNT) TSM_MOBILE FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM, VR_ACCOUNT_DETAILS VAD2, VR_ACCOUNT_DETAILS VAD3 ");
						sbstr.append(" WHERE VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID AND VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID AND VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A'  "); 
						sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
						*/
						
						sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID, (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME,");  
						sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE, COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE ");
						sbstr.append(" CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME, VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, VLM2.LOGIN_NAME DIST_OLM_ID, VAD4.MOBILE_NUMBER TSM_MOBILE, VLM3.LOGIN_NAME TSM_OLM_ID, VAD5.MOBILE_NUMBER ZSM_MOBILE, VLM4.LOGIN_NAME ZSM_OLM_ID FROM VR_ACCOUNT_DETAILS VAD inner join VR_LOGIN_MASTER VLM on VAD.ACCOUNT_ID=VLM.LOGIN_ID ");
						sbstr.append(" inner join VR_ACCOUNT_DETAILS VAD2 on VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID inner join VR_ACCOUNT_DETAILS VAD3 on VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID inner join VR_LOGIN_MASTER VLM2 on VAD3.ACCOUNT_ID=VLM2.LOGIN_ID inner join DP_DISTRIBUTOR_MAPPING DM on VAD3.ACCOUNT_ID=DM.DP_DIST_ID and TRANSACTION_TYPE_ID=2 inner join VR_ACCOUNT_DETAILS VAD4 on DM.PARENT_ACCOUNT=VAD4.ACCOUNT_ID inner join VR_LOGIN_MASTER VLM3 on VAD4.ACCOUNT_ID=VLM3.LOGIN_ID ");
						sbstr.append(" inner join VR_ACCOUNT_DETAILS VAD5 on VAD4.PARENT_ACCOUNT=VAD5.ACCOUNT_ID inner join VR_LOGIN_MASTER VLM4 on VAD5.ACCOUNT_ID=VLM4.LOGIN_ID WHERE VAD.ACCOUNT_LEVEL=8 AND VLM.STATUS='A' AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR ");
						
						/*
						//
						 * Commented by nazim hussain to take TSM and ZSM details based on AV change
						sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, "); 
						sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE,   ");
						sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME,  ");
						sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME ,  ");
						sbstr.append(" VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = VAD3.ACCOUNT_ID) DIST_OLM_ID, "); 
						sbstr.append(" (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=VAD3.PARENT_ACCOUNT) TSM_MOBILE , ");
						sbstr.append(" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID=VAD3.PARENT_ACCOUNT) TSM_OLM_ID , (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=VAD4.PARENT_ACCOUNT) ZSM_MOBILE , (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID=VAD4.PARENT_ACCOUNT) ZSM_OLM_ID ");
						sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM, VR_ACCOUNT_DETAILS VAD2, VR_ACCOUNT_DETAILS VAD3  ,VR_ACCOUNT_DETAILS VAD4  ");
						sbstr.append(" WHERE VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID AND VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID AND VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' AND VAD3.PARENT_ACCOUNT=VAD4.ACCOUNT_ID ");  
						sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR ");
					*/
						ps = connection.prepareStatement(sbstr.toString());
						ps.setString(1, mobilenumber);
						ps.setString(2, mobilenumber);
						ps.setString(3, mobilenumber);
						ps.setString(4, mobilenumber);
						ps.setString(5, mobilenumber);
						rs = ps.executeQuery();
						
						if(rs.next())
						{
							retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
							retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");
							retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
							retailerInfoArray[3]= rs.getString("EMAIL");
							retailerInfoArray[4]= rs.getString("CIRCLE_ID");
							retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
							retailerInfoArray[6]= rs.getString("CITY_ID");
							retailerInfoArray[7]= rs.getString("CITY_NAME");
							retailerInfoArray[8]= rs.getString("ADDRESS_2");
							retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
							retailerInfoArray[10]= rs.getString("CONTACT_NAME");
							retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
							retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
							retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
							retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
							retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
							retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
							retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
							retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
							retailerInfoArray[19]= rs.getString("DIST_NAME");
							retailerInfoArray[20]= rs.getString("DIST_MOBLE_NO");
							retailerInfoArray[21]= rs.getString("DIST_OLM_ID");
							
							retailerInfoArray[22]= rs.getString("TSM_MOBILE");
							
							retailerInfoArray[23]= rs.getString("TSM_OLM_ID");
							retailerInfoArray[24]= rs.getString("ZSM_MOBILE");
							retailerInfoArray[25]= rs.getString("ZSM_OLM_ID");
							
						}
					else
					{
						logger.error("*********** Invalid Mobile Number ****************");
						retailerInfoArray = new String []{"Invalid Mobile Number"};
					}
				}
				
			}
			else
			{
				logger.error("*********** Invalid Mobile Number ****************");
				retailerInfoArray = new String []{"Invalid Mobile Number"};
			}
			DBConnectionManager.releaseResources(ps1, rs1);
				
				
			}
			else
			{
				/*sbstr.append(" SELECT ACCOUNT_ID, MOBILE_NUMBER, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
				sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
				sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
				sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
				sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
				sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
				sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ACCOUNT_NAME , ");
				sbstr.append(" (SELECT ACCOUNT_NAME  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT)) DIST_NAME, "); 
				sbstr.append(" (SELECT MOBILE_NUMBER  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT)) DIST_MOBLE_NO, ");
				sbstr.append(" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = (SELECT ACCOUNT_ID  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT))) DIST_OLM_ID ");
				sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM  ");
				sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' ");
				sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
				*/
				
				/*
				 * 
				sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID,  ");
				sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME,  ");
				sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME,  ");
				sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, "); 
				sbstr.append(" VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE,  ");
				sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, "); 
				sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME ,  ");
				sbstr.append(" (SELECT ACCOUNT_NAME  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT)) DIST_NAME, ");  
				sbstr.append(" (SELECT MOBILE_NUMBER  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT)) DIST_MOBLE_NO, "); 
				sbstr.append(" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = (SELECT ACCOUNT_ID  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD.PARENT_ACCOUNT))) DIST_OLM_ID, ");
				sbstr.append(" (SELECT MOBILE_NUMBER  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD1.PARENT_ACCOUNT)) TSM_MOBLE_NO,  ");
				sbstr.append(" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = (SELECT ACCOUNT_ID  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD1.PARENT_ACCOUNT))) TSM_OLM_ID, ");
				sbstr.append(" (SELECT MOBILE_NUMBER  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD2.PARENT_ACCOUNT)) ZSM_MOBLE_NO,  ");
				sbstr.append(" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID = (SELECT ACCOUNT_ID  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = (SELECT  PARENT_ACCOUNT  FROM VR_ACCOUNT_DETAILS  WHERE ACCOUNT_ID = VAD2.PARENT_ACCOUNT))) ZSM_OLM_ID ");
				sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM ,VR_ACCOUNT_DETAILS VAD1,VR_ACCOUNT_DETAILS VAD2 ");
				sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A'  ");
				sbstr.append(" AND VAD.PARENT_ACCOUNT=VAD1.ACCOUNT_ID ");
				sbstr.append(" AND VAD1.PARENT_ACCOUNT=VAD2.ACCOUNT_ID ");
				sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR ");
				*/
				sbstr.append(" SELECT VAD.ACCOUNT_ID, VAD.MOBILE_NUMBER, VAD.ACCOUNT_ADDRESS, VAD.EMAIL, VAD.CIRCLE_ID, VAD.STATE_ID, VAD.CITY_ID, (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME,");  
				sbstr.append(" VAD.PIN_NUMBER, VAD.ADDRESS_2, VAD.LAPU_MOBILE_NO, VAD.CONTACT_NAME, VAD.LAPU_MOBILE_NO_2, VAD.FTA_MOBILE_NO, VAD.FTA_MOBILE_NO_2, VAD.RETAILER_TYPE, VAD.CHANNEL_TYPE, COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=VAD.RETAILER_TYPE), 'NONE') RET_TYP_NAME, (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE ");
				sbstr.append(" CHANNEL_TYPE_ID=VAD.CHANNEL_TYPE) CHANNEL_NAME, VAD.ACCOUNT_NAME, VAD3.ACCOUNT_NAME AS DIST_NAME, VAD3.MOBILE_NUMBER AS DIST_MOBLE_NO, VLM2.LOGIN_NAME DIST_OLM_ID, VAD4.MOBILE_NUMBER TSM_MOBILE, VLM3.LOGIN_NAME TSM_OLM_ID, VAD5.MOBILE_NUMBER ZSM_MOBILE, VLM4.LOGIN_NAME ZSM_OLM_ID FROM VR_ACCOUNT_DETAILS VAD inner join VR_LOGIN_MASTER VLM on VAD.ACCOUNT_ID=VLM.LOGIN_ID ");
				sbstr.append(" inner join VR_ACCOUNT_DETAILS VAD2 on VAD.PARENT_ACCOUNT=VAD2.ACCOUNT_ID inner join VR_ACCOUNT_DETAILS VAD3 on VAD2.PARENT_ACCOUNT=VAD3.ACCOUNT_ID inner join VR_LOGIN_MASTER VLM2 on VAD3.ACCOUNT_ID=VLM2.LOGIN_ID inner join DP_DISTRIBUTOR_MAPPING DM on VAD3.ACCOUNT_ID=DM.DP_DIST_ID and TRANSACTION_TYPE_ID=2 inner join VR_ACCOUNT_DETAILS VAD4 on DM.PARENT_ACCOUNT=VAD4.ACCOUNT_ID inner join VR_LOGIN_MASTER VLM3 on VAD4.ACCOUNT_ID=VLM3.LOGIN_ID ");
				sbstr.append(" inner join VR_ACCOUNT_DETAILS VAD5 on VAD4.PARENT_ACCOUNT=VAD5.ACCOUNT_ID inner join VR_LOGIN_MASTER VLM4 on VAD5.ACCOUNT_ID=VLM4.LOGIN_ID WHERE VAD.ACCOUNT_LEVEL=8 AND VLM.STATUS='A' AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR ");
				
				
				
			//	logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
				
				ps = connection.prepareStatement(sbstr.toString());
				ps.setString(1, mobilenumber);
				ps.setString(2, mobilenumber);
				ps.setString(3, mobilenumber);
				ps.setString(4, mobilenumber);
				ps.setString(5, mobilenumber);
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
					retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");
					retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
					retailerInfoArray[3]= rs.getString("EMAIL");
					retailerInfoArray[4]= rs.getString("CIRCLE_ID");
					retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
					retailerInfoArray[6]= rs.getString("CITY_ID");
					retailerInfoArray[7]= rs.getString("CITY_NAME");
					retailerInfoArray[8]= rs.getString("ADDRESS_2");
					retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
					retailerInfoArray[10]= rs.getString("CONTACT_NAME");
					retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
					retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
					retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
					retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
					retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
					retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
					retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
					retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
					retailerInfoArray[19]= rs.getString("DIST_NAME");
					retailerInfoArray[20]= rs.getString("DIST_MOBLE_NO");
					retailerInfoArray[21]= rs.getString("DIST_OLM_ID");
					
					retailerInfoArray[22]= rs.getString("TSM_MOBILE");
					
					retailerInfoArray[23]= rs.getString("TSM_OLM_ID");
					retailerInfoArray[24]= rs.getString("ZSM_MOBILE");
					retailerInfoArray[25]= rs.getString("ZSM_OLM_ID");
				}
				else
				{
					logger.error("*********** Invalid Mobile Number ****************");
					retailerInfoArray = new String []{"Invalid Mobile Number"};
				}
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally 
		{
			try
			{	
				DBConnectionManager.releaseResources(ps, rs);
				sbstr = null;
			}
			catch (Exception e){ 
				e.printStackTrace();
				logger.error("Error while releasing connection "+ e.getMessage()); 
			}
		}
		return retailerInfoArray;
	}

	
	public String[] getRetailerDistInfoCannonNew(String mobilenumber, String pinCode) throws DAOException 
	{
		PreparedStatement ps = null;
  		ResultSet rs = null;
  		
  		PreparedStatement ps1 = null;
  		ResultSet rs1 = null;
  		String lapuFlag="";
  		
  		String []retailerInfoArray = new String[26];	
  		String mobilevalidQuery="";
  		StringBuffer sbstr = new StringBuffer();
  		try {
			ps=connection.prepareStatement("select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=31 ");
			rs=ps.executeQuery();
			while(rs.next())
				lapuFlag=rs.getString("DD_VALUE");
			
		} catch (SQLException e1) {
			logger.info("Exception in executing select on DP_CONFIGURATION_DETAILS table");
			e1.printStackTrace();
		}
		
		try
		{
			logger.info("MOBILE NUMBER : " + mobilenumber);
			logger.info("PIN CODE : " + pinCode);
			boolean bol = validPincode(pinCode);
			
			logger.info(" pinCode valid ::  "+bol);
			if(bol)
			{
				//change
				if(lapuFlag.equalsIgnoreCase("Y"))
				{
					mobilevalidQuery = "select RETAILER_LAPU from VR_ACCOUNT_DETAILS a , VR_LOGIN_MASTER b where ACCOUNT_LEVEL=8  "
						+" AND (RETAILER_LAPU=?) and a.ACCOUNT_ID=b.LOGIN_ID "
						+" and b.STATUS = 'A' with ur ";
				}
				else {
					mobilevalidQuery = "select MOBILE_NUMBER from VR_ACCOUNT_DETAILS a , VR_LOGIN_MASTER b where ACCOUNT_LEVEL=8  "
						+" AND (MOBILE_NUMBER=? OR FTA_MOBILE_NO=? OR FTA_MOBILE_NO_2=? OR LAPU_MOBILE_NO=? OR LAPU_MOBILE_NO_2=? ) and a.ACCOUNT_ID=b.LOGIN_ID "
						+" and b.STATUS = 'A' with ur ";
										
					
				}
				
				ps1 = connection.prepareStatement(mobilevalidQuery);
				if(lapuFlag.equalsIgnoreCase("N"))
				{
					ps1.setString(1, mobilenumber);
					ps1.setString(2, mobilenumber);
					ps1.setString(3, mobilenumber);
					ps1.setString(4, mobilenumber);
					ps1.setString(5, mobilenumber);
				}
				else
				{
					ps1.setString(1,mobilenumber);
				}
				rs1 = ps1.executeQuery();
				
				if(rs1.next())
				{
					String strQuery = "select AD.ACCOUNT_NAME, AD.MOBILE_NUMBER, (SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=?) AS LOGIN_NAME, AD1.MOBILE_NUMBER as TSM_MOBILE, lm.LOGIN_NAME as TSM_OLM_ID, (SELECT MOBILE_NUMBER FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=ad1.PARENT_ACCOUNT) ZSM_MOBILE," +
							" (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE login_ID=ad1.PARENT_ACCOUNT) ZSM_OLM_ID from VR_ACCOUNT_DETAILS AD inner join DP_DISTRIBUTOR_MAPPING DM on AD.ACCOUNT_ID=DM.DP_DIST_ID and TRANSACTION_TYPE_ID=2" +
							" inner join VR_ACCOUNT_DETAILS AD1 on DM.PARENT_ACCOUNT=AD1.ACCOUNT_ID inner join VR_LOGIN_MASTER LM on AD1.ACCOUNT_ID=lm.LOGIN_ID where AD.ACCOUNT_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=(SELECT DIST_OLM_ID FROM DP_DIST_PINCODE_MAP WHERE PINCODE=? )) with ur";
					
					ps = connection.prepareStatement(strQuery);
					ps.setString(1, pinCode.trim());
					ps.setString(2, pinCode.trim());
					rs = ps.executeQuery();
					
					if(rs.next())
					{
						retailerInfoArray[19]= rs.getString("ACCOUNT_NAME");
						retailerInfoArray[20]= rs.getString("MOBILE_NUMBER");//no change
						retailerInfoArray[21]= rs.getString("LOGIN_NAME");
						retailerInfoArray[22]= rs.getString("TSM_MOBILE");
						
						retailerInfoArray[23]= rs.getString("TSM_OLM_ID");
						retailerInfoArray[24]= rs.getString("ZSM_MOBILE");
						retailerInfoArray[25]= rs.getString("ZSM_OLM_ID");
					
						DBConnectionManager.releaseResources(ps, rs);
						//query
						if(lapuFlag.equalsIgnoreCase("N"))
						{
							sbstr.append(" SELECT ACCOUNT_ID, MOBILE_NUMBER, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
							sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
							sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
							sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
							sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
							sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
							sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ACCOUNT_NAME ");
							sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM  ");
							sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' ");
							
							sbstr.append(" AND (VAD.MOBILE_NUMBER=? OR VAD.FTA_MOBILE_NO=? OR VAD.FTA_MOBILE_NO_2=? OR VAD.LAPU_MOBILE_NO=? OR VAD.LAPU_MOBILE_NO_2=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
						}
						else{
							sbstr.append(" SELECT ACCOUNT_ID, RETAILER_LAPU, ACCOUNT_ADDRESS, EMAIL, CIRCLE_ID, STATE_ID, CITY_ID, ");
							sbstr.append(" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER VC WHERE VC.CIRCLE_ID=VAD.CIRCLE_ID) CIRCLE_NAME, ");
							sbstr.append(" (SELECT CITY_NAME FROM VR_CITY_MASTER VCM WHERE VCM.CITY_ID=VAD.CITY_ID) CITY_NAME, ");
							sbstr.append(" PIN_NUMBER, ADDRESS_2, LAPU_MOBILE_NO, CONTACT_NAME, LAPU_MOBILE_NO_2, FTA_MOBILE_NO, ");
							sbstr.append(" FTA_MOBILE_NO_2, RETAILER_TYPE, CHANNEL_TYPE, ");
							sbstr.append(" COALESCE((SELECT RETAILER_CATEGORY_NAME FROM DP_RETAILER_CATEGORY WHERE RETAILER_CATEGORY_ID=RETAILER_TYPE), 'NONE') RET_TYP_NAME, ");
							sbstr.append(" (SELECT CHANNEL_TYPE_NAME FROM DP_CHANNEL_TYPE WHERE CHANNEL_TYPE_ID=CHANNEL_TYPE) CHANNEL_NAME, ACCOUNT_NAME ");
							sbstr.append(" FROM VR_ACCOUNT_DETAILS VAD, VR_LOGIN_MASTER VLM  ");
							sbstr.append(" WHERE VAD.ACCOUNT_LEVEL=8 AND VAD.ACCOUNT_ID=VLM.LOGIN_ID AND VLM.STATUS='A' ");
						
							sbstr.append(" AND (VAD.RETAILER_LAPU=?) ORDER BY VAD.CREATED_DT DESC WITH UR");
						}
						logger.info("Query to get Retailer Info  ::  " + sbstr.toString());
						
						ps = connection.prepareStatement(sbstr.toString());
						if(lapuFlag.equalsIgnoreCase("N"))
						{
							ps.setString(1, mobilenumber);
							ps.setString(2, mobilenumber);
							ps.setString(3, mobilenumber);
							ps.setString(4, mobilenumber);
							ps.setString(5, mobilenumber);
						}
						else
						{
							ps.setString(1, mobilenumber);
						}
						rs = ps.executeQuery();
					
						if(rs.next())
						{
							retailerInfoArray[0]= rs.getString("ACCOUNT_ID");
							if(lapuFlag.equalsIgnoreCase("Y"))
							{
								retailerInfoArray[1]= rs.getString("RETAILER_LAPU");
							}
							else{
								retailerInfoArray[1]= rs.getString("MOBILE_NUMBER");
							}
							
							retailerInfoArray[2]= rs.getString("ACCOUNT_ADDRESS");
							retailerInfoArray[3]= rs.getString("EMAIL");
							retailerInfoArray[4]= rs.getString("CIRCLE_ID");
							retailerInfoArray[5]= rs.getString("CIRCLE_NAME");
							retailerInfoArray[6]= rs.getString("CITY_ID");
							retailerInfoArray[7]= rs.getString("CITY_NAME");
							retailerInfoArray[8]= rs.getString("ADDRESS_2");
							retailerInfoArray[9]= rs.getString("LAPU_MOBILE_NO");
							retailerInfoArray[10]= rs.getString("CONTACT_NAME");
							retailerInfoArray[11]= rs.getString("LAPU_MOBILE_NO_2");
							retailerInfoArray[12]= rs.getString("FTA_MOBILE_NO");
							retailerInfoArray[13]= rs.getString("FTA_MOBILE_NO_2");
							retailerInfoArray[14]= rs.getString("RETAILER_TYPE");
							retailerInfoArray[15]= rs.getString("RET_TYP_NAME");
							retailerInfoArray[16]= rs.getString("CHANNEL_TYPE");
							retailerInfoArray[17]= rs.getString("CHANNEL_NAME");
							retailerInfoArray[18]= rs.getString("ACCOUNT_NAME");
						}
					}
					else
					{
						logger.error("Invalid Pincode : No distributor found in DP against this pincode");
						return new String []{null};
					}
				}
				else
				{
					logger.error("*********** Invalid Mobile Number ****************");
					return new String []{null};
				}
				DBConnectionManager.releaseResources(ps1, rs1);
			}
			else
			{
				logger.error("*********** Invalid Pincode : No Pincode mapping exists ****************");
				return new String []{null};
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally 
		{
			try
			{	
				DBConnectionManager.releaseResources(ps, rs);
				sbstr = null;
			}
			catch (Exception e){ 
				e.printStackTrace();
				logger.error("Error while releasing connection "+ e.getMessage()); 
			}
		}
		return retailerInfoArray;
	}
	public String getStockStatusDAO(int intAccountID, int intLevelID) throws DAOException 
	{
		String strMessage = "OK";
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.info("Validating Account activation for details  ::  intAccountID  ::  "+intAccountID+"  intLevelID  ::  "+intLevelID);
		
		try
		{
			String strQuery = "";
			
			if(intLevelID==8)
				strQuery = "select count(1) from DP_STOCK_INVENTORY where FSE_ID=? with ur";
			else
				strQuery = "select count(1) from DP_STOCK_INVENTORY where RETAILER_ID=? with ur";
			
			ps = connection.prepareStatement(strQuery);
			
			ps.setInt(1, intAccountID);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				if(rs.getInt(1)>0)
					strMessage = "ISSUE";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception in DAO  "+ e.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally 
		{
			try
			{	
				DBConnectionManager.releaseResources(ps, rs);
			}
			catch (Exception e){ 
				e.printStackTrace();
				logger.error("Error while releasing resources "+ e.getMessage()); 
			}
		}
		return strMessage;
	}

	public ArrayList getRetailerTransactionList() throws DAOException {
		PreparedStatement ps = null;
  		ResultSet rs = null;
  		ArrayList<DPCreateAccountDto> retailerTransList = null;
  		DPCreateAccountDto createAccount = null;
  		try{
  			logger.info("************************getRetailerTransactionList***************----");
  			retailerTransList = new ArrayList<DPCreateAccountDto>();
   			ps = connection.prepareStatement(DBQueries.SQL_SELECT_RETAILER_TRANSACTION);
   			logger.info("************************ps***************----"+ps);
   			rs = ps.executeQuery();
  			while (rs.next()){
  				createAccount = new DPCreateAccountDto();
  				createAccount.setRetailerTransId(rs.getInt("ID"));
  				createAccount.setRetailerTypeName(rs.getString("VALUE"));
  				retailerTransList.add(createAccount);
  				createAccount = null;
  			}
  		} catch (SQLException e) {
			e.printStackTrace();
			try {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			} catch (DAOException e1) {
				e1.printStackTrace();
				logger.error("Error while fetching retailerlist "+ e.getMessage()); 
			}
		} finally {
			DBConnectionManager.releaseResources(ps, rs);
		}
		return retailerTransList;
	}
	public void insertTransactionType(long accountId,int transactionType) throws DAOException {
		
		PreparedStatement pstmt  = null;

		try {
			logger.info("the value of selected transaction type for Retailer is::"+transactionType);
			
			String strQuery = "INSERT INTO DPDTH.VR_ACCOUNT_DETAILS(ACCOUNT_ID,TRANSACTION_TYPE) VALUES(?,?)";
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setLong(1, accountId);
			pstmt.setInt(2, transactionType);
			pstmt.executeUpdate();
			}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error in inserting VR_ACCOUNT_DETAILS  ::  "+e.getMessage());
			logger.info("Error in inserting VR_ACCOUNT_DETAILS  ::  "+e);	
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
		}
	}
	
	//Added by Neetika on 2 Aug for BFR 10 of Release 3
	public int terminateAccount(DPCreateAccountDto accountDto)throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		int i=1;
		int remaining=0;
		logger.info("terminateAccount .. in Implementation of DPCreateAccount Dao impl");
	
	try {
			/*
			ps = connection.prepareStatement(queryMap
					.get(SQL_DELETE_DISTRIBUTOR_MAP_KEY));
			logger.info(queryMap.get(SQL_DELETE_DISTRIBUTOR_MAP_KEY));
			logger.info("  Values set are "+accountDto.getAccountId()+" --- "+accountDto.getTerminationId());
			ps.setLong(1, accountDto.getAccountId());
			ps.setInt(2,Integer.parseInt(accountDto.getTerminationId()));

			i = ps.executeUpdate();
			logger.info("  Value of i"+i);
			ps.clearParameters();

			if (i == 0) {
				logger
						.error("Error While Temrinating Distributor Account  ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
			}
			ps = connection.prepareStatement(queryMap
					.get(SQL_DELETE_DISTRIBUTOR_MAP_MAIN_KEY));
			logger.info(queryMap.get(SQL_DELETE_DISTRIBUTOR_MAP_MAIN_KEY));
			ps.setLong(1, accountDto.getAccountId());
			ps.setInt(2,Integer.parseInt(accountDto.getTerminationId()));

			i = ps.executeUpdate();
			ps.clearParameters();

			if (i == 0) {
				logger
						.error("Error While Temrinating Distributor Account  ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
			}
			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_DISTRIBUTOR_MAP_MAIN_KEY));
			logger.info(queryMap.get(SQL_SELECT_DISTRIBUTOR_MAP_MAIN_KEY));
			ps.setLong(1, accountDto.getAccountId());
			

			rs = ps.executeQuery();
			while(rs.next())
			{
				remaining=rs.getInt(1);
			}
			ps.clearParameters();
			if(remaining>0)
			{
				logger.info("No need to delete from VR_LOGIN_MASTER and VR_ACCOUNT_DETAILS.. As still Distributor has mapping");
			}
			else
			{
				// Insert into login master  hist
				ps = connection.prepareStatement(queryMap
						.get(SQL_INSERT_DISTRIBUTOR_LOGIN_KEY));
				logger.info(queryMap.get(SQL_INSERT_DISTRIBUTOR_LOGIN_KEY));
				ps.setLong(1, accountDto.getAccountId());
				
				i = ps.executeUpdate();
				ps.clearParameters();

				if (i == 0) {
					logger
							.error("Error While Temrinating Distributor Account  ");
					throw new DAOException(
							ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
				}
				ps = connection.prepareStatement(queryMap
						.get(SQL_DELETE_DISTRIBUTOR_LOGIN_KEY));
				ps.setLong(1, accountDto.getAccountId());
				
				i = ps.executeUpdate();
				ps.clearParameters();

				if (i == 0) {
					logger
							.error("Error While Temrinating Distributor Account  ");
					throw new DAOException(
							ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
				}
				// Insert into account details hist
				ps = connection.prepareStatement(queryMap
						.get(SQL_INSERT_DISTRIBUTOR_ACCOUNT_KEY));
				logger.info(queryMap.get(SQL_INSERT_DISTRIBUTOR_ACCOUNT_KEY));
				ps.setLong(1, accountDto.getAccountId());
				
				i = ps.executeUpdate();
				ps.clearParameters();

				if (i == 0) {
					logger
							.error("Error While Temrinating Distributor Account  ");
					throw new DAOException(
							ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
				}
				ps = connection.prepareStatement(queryMap
						.get(SQL_DELETE_DISTRIBUTOR_ACCOUNT_KEY));
				logger.info(queryMap.get(SQL_DELETE_DISTRIBUTOR_ACCOUNT_KEY));
				ps.setLong(1, accountDto.getAccountId());
				
				i = ps.executeUpdate();
				ps.clearParameters();

				if (i == 0) {
					logger
							.error("Error While Temrinating Distributor Account  ");
					throw new DAOException(
							ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
				}
			} */
		System.out.println("accountDto.getTerminationId()"+accountDto.getTerminationId());
		
		
		logger.info(queryMap.get(SQL_DELETE_DISTRIBUTOR_MAP_KEY));
		String query=queryMap.get(SQL_DELETE_DISTRIBUTOR_MAP_KEY);
		if(accountDto.getTerminationId().equalsIgnoreCase("3"))
		{
			query=query+"(";
			query=query+""+1+",";
			query=query+""+2;
			query=query+")";
		}
		else
		{
		query=query+"(";
		query=query+Integer.parseInt(accountDto.getTerminationId());
		query=query+")";
		}
		ps = connection.prepareStatement(query);
		ps.setLong(1, accountDto.getAccountId());
		

		i = ps.executeUpdate();
		ps.clearParameters();

		if (i == 0) {
			logger
					.error("Error While Temrinating Distributor Account in mapping table  ");
			throw new DAOException(
					ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
		}
		ps = connection.prepareStatement(queryMap
				.get(SQL_SELECT_DISTRIBUTOR_MAP_MAIN_KEY));
		logger.info(queryMap.get(SQL_SELECT_DISTRIBUTOR_MAP_MAIN_KEY));
		ps.setLong(1, accountDto.getAccountId());
		

		rs = ps.executeQuery();
		while(rs.next())
		{
			remaining=rs.getInt(1);
		}
		ps.clearParameters();
		if(remaining>0)
		{
			logger.info("No need to delete from VR_LOGIN_MASTER and VR_ACCOUNT_DETAILS.. As still Distributor has mapping");
		}
		else
		{
			//Make status as T in VR_LOGIN_MASTER now it will be updated by BoTreee Web service 
			ps = connection.prepareStatement(queryMap
					.get(SQL_DELETE_DISTRIBUTOR_LOGIN_KEY));
			logger.info(queryMap.get(SQL_DELETE_DISTRIBUTOR_LOGIN_KEY));
			ps.setLong(1, accountDto.getAccountId());
			
			i = ps.executeUpdate();
			ps.clearParameters();

			if (i == 0) {
				logger
						.error("Error While Temrinating Distributor Account.. in Login master  ");
				throw new DAOException(
						ExceptionCode.Account.ERROR_TERMINATING_ACCOUNT);
			}
			
		}
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("Sql Exception occured while terminating Distributor Account."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			
			DBConnectionManager.releaseResources(ps,rs);

		}
		logger.info("Executed ...SuccessFully terminated account ");
	
		return i;
	}
	
	/**
	 * 
	 */
	public boolean isMobileExistForDepthThree(String mobileNo) throws DAOException
	{
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		try 
		{
			String strQuery = "SELECT DEPTH, AGENT FROM DP_HIERARCHY_REPORT WHERE DEPTH>=3 and AGENT=? and status in ('ACTIVE') WITH UR";
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, mobileNo);
			rs = pstmt.executeQuery();
			
			return rs.next();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rs);
		}
	}
	
	public void insertHistDetails(long accountId,Timestamp currentTime) throws DAOException {
		
		PreparedStatement ps = null;

		try {
			
			String sql=SQL_INSERT_ACC_DETAIL_HIST_IT_HELP_RET.replace("?2", "timestamp('"+currentTime+"')");
			logger.info( sql);
				ps = connection.prepareStatement(sql);
				//ps.setTimestamp(1, currentTime);
				ps.setLong(1, accountId);
				ps.executeUpdate();
			
			}
		catch (Exception e) {
			
			e.printStackTrace();
				
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		finally
		{
			DBConnectionManager.releaseResources(ps, null);
		}
		
		
			
		}
	@Override
	public int getCutoffdate(Connection conn) throws DAOException {
		String cutOffQuery=" SELECT DD_VALUE FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=33 with ur";
		String monthlyPayout="select CASE when max(CYCLE) is null then 0 ELSE max(cycle) END as CYCLE_NO from DP_MONTHLY_PAYOUT " +
							" where UPLOADED_FR_MNTH=? ";
		PreparedStatement ps=null;
		int [] daysOfCut=new int[5];
		int secCycleFlag=0;
		Calendar calendar=Calendar.getInstance();
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		int month=calendar.get(Calendar.MONTH);
		int year=calendar.get(Calendar.YEAR);
		if(month==0)
		{
			month=12;
			year=year-1;
		}
		String monthFr=month+"-"+year;
		
		if(month<10){
			monthFr="0"+monthFr;
		}
		String cycleOne="select BILL_NO  from DP_INVOICE "+
						" where STATUS='R' and month_fr=? "+
						" union all "+
						" select BILL_NO from DP_PARTNER_INVOICE_2 " +
						" where STATUS='R' and month_fr=?  with ur"; 
		try {
			ps=conn.prepareStatement(cutOffQuery);
			ResultSet rs=ps.executeQuery();
			int counter=0;
			while(rs.next()){
				daysOfCut[counter]=rs.getInt("DD_VALUE");
				counter++;
			}
			
			logger.info(monthFr);
			ps=conn.prepareStatement(monthlyPayout);
			ps.setString(1, monthFr);
			rs=ps.executeQuery();
			int cycle=0;
			if(rs.next()){
				cycle=rs.getInt("CYCLE_NO");
			}
			logger.info(cycle);
			
			if(daysOfCut[4]==0){
				return 0;
			}
			if(cycle==0 && day<=daysOfCut[0])
				return daysOfCut[0];
			if(cycle==1 && day<=daysOfCut[0])
				return 0;
			if(cycle==0 && day>daysOfCut[0])
				return -1;
			if(cycle==1 && day>daysOfCut[0] && day<=daysOfCut[1])
				return 0;
			
			if(day>daysOfCut[1] && day<=daysOfCut[2]){
				ps=conn.prepareStatement(cycleOne);
				ps.setString(1, monthFr);
				ps.setString(2, monthFr);
				rs=ps.executeQuery();
				if(rs.next() && cycle==1){
					secCycleFlag=1;
					return daysOfCut[2];
				}
				else {
					return 0;
				}
			}
			if(day>daysOfCut[2] && cycle==1){
				
				ps=conn.prepareStatement(cycleOne);
				ps.setString(1, monthFr);
				ps.setString(2, monthFr);
				rs=ps.executeQuery();
				if(rs.next()){
					return -1;
				}
				else {
					return 0;
				}
			}
			
				
	
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Error in DB Query Prepare");
		}
		finally{
			
		}
	
		return 0;
	}

	@Override
	public int getCutoffdateDist(Connection conn,String loginNm) throws DAOException {
		// TODO Auto-generated method stub
		String cutOffQuery=" SELECT DD_VALUE FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=33 with ur";
		PreparedStatement ps=null;
		int [] daysOfCut=new int[5];
		Calendar calendar=Calendar.getInstance();
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		int month=calendar.get(Calendar.MONTH);
		int year=calendar.get(Calendar.YEAR);
		if(month==0)
		{
			month=12;
			year=year-1;
		}
		String monthFr=month+"-"+year;
		if(month<10){
			monthFr="0"+monthFr;
		}
		String cycleOne="select BILL_NO  from DP_INVOICE "+
						" where STATUS='U' and OLM_ID=? "+
						" union all "+
						" select BILL_NO from DP_PARTNER_INVOICE_2 " +
						" where STATUS='U' and OLM_ID=? with ur"; 
		try {
			ps=conn.prepareStatement(cutOffQuery);
			ResultSet rs=ps.executeQuery();
			int counter=0;
			while(rs.next()){
				daysOfCut[counter]=rs.getInt("DD_VALUE");
				counter++;
			}
			
			ps=conn.prepareStatement(cycleOne);
			
			ps.setString(1, loginNm);
			ps.setString(2, loginNm);
			rs=ps.executeQuery();
			
			if(daysOfCut[4]==0)
				return 0;
			
			if(rs.next()){
				if(day <= daysOfCut[1])
					return daysOfCut[1];
				else if(day >daysOfCut[1] && day <=daysOfCut[2])
					return -1;
				else if(day>=daysOfCut[2] && day<=daysOfCut[3])
					return daysOfCut[3];
				else if(day>daysOfCut[3])
					return -1;
			}
			else 
				return 0;
			
	}catch(SQLException sqlExp){
		sqlExp.printStackTrace();
		throw new DAOException("Error in DB query");
	}
	
	return 0;
	}
}