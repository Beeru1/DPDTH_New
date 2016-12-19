/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.common;

/**
 * Class to define all the constants.
 * 
 * @author Paras
 * 
 */
public final class Constants {

	//TODO Harsh Mar 06, Split file logically.

	/* AUTHENTICATED_USER stores details of logged in user */
	public static final String AUTHENTICATED_USER = "USER_INFO";
	
	public static final String CREATE_PDF="CREATE.PDF.PATH";
	
	public static final String DS_HOST="payout.sftp.hostname";
	
	public static final String DS_USER="payout.sftp.server.username";
	
	public static final String DS_PASS="payout.sftp.server.password";
	
	public static final String DS_DEST="payout.sftp.server.remotepath";
	
	public static final String DOWNLOAD_FILE="DOWNLOAD_PATH";
	
	public static final String DOWNLOAD_FILE1="DOWNLOAD_PATH1";
	
	public static final String PAYOUT_PATH="PAYOUT_PATH";
	/* TRANSACTION_DISTRIBUTION details */
	public static final String USER_NOT_AUTHERIZED = "autherization";

	public static final String DATABASE_TYPE = "database.type";

	public static final String TRANS_DIST_PENDING = "P";

	public static final String TRANS_DIST_APPROVED = "A";
	
	public static final String RETURN_TO_FSE = "RETURN TO FSE";
	
	public static final String RETURN_TO_DISTRIBUTOR = "RETURN TO DISTRIBUTOR";
	
	public static final String ALLOCATION = "ALLOCATION";
	
	public static final String SECONDARY_SALES = "SECONDARY SALES";
	

	public static final String TRANS_DIST_REJECTED = "R";

	public static final String CREATE_DIST_TRANSACTION = "newDistributorTransaction";

	public static final String DISTRIBUTOR_LIST = "showDistributorList";

	public static final String SEARCH_DIST_TRANSACTION = "searchDistributorTransaction";

	public static final String DIST_TRANSACTION_LIST = "distributorTransactionList";

	public static final String UPDATED_SEARCH_LIST = "searchUpdatedDistributorList";

	public static final String DIST_REVIEW_DETAILS = "distributorReviewDetail";

	public static final String DIST_REVIEW_DETAILS_VIEW = "distributorReviewDetailView";

	public static final String DISTRIBUTOR_LIST_ERROR = "error";

	public static final String DISTRIBUTOR_CH_TYPE = "WEB";

	public static final String DISTRIBUTOR_TRANS_TYPE_CASH = "CASH";

	public static final String DISTRIBUTOR_TRANS_TYPE_CHEQUE = "CHEQUE";

	public static final String DISTRIBUTOR_TRANS_TYPE_CREDIT = "CREDIT";

	
	public static int TSM_ACCOUNT_LEVEL = 5;
	
	// CREATE_ACCOUNT create account
	public static final String ACCOUNT_ERROR = "error";

	public static final String PRODUCT_ERROR = "error";

	// Autoruisation Related Constants
	public static final String USER_NOT_AUTHORISED = "error";

	// PostPaid related Constants
	public static final String POSTPAID_USER_NOT_AUTHORISED = "error";

	public static final String CREATE_ACCOUNT = "createAccount";

	public static final String SUCCESS_TRANSACTION = "success.transaction";

	public static final String SUCCESS_TRANSACTION_SYNC = "success.transaction.sync";

	public static final String OPEN_ACCOUNT = "openAccount";

	public static final String OPEN_ACCOUNT_ERROR = "createAccountError";

	public static final String SEARCH_ACCOUNT = "searchAccount";

	public static final String EDIT_ACCOUNT = "editAccountDetails";
	
	public static final String EDIT_ACCOUNT_DISTRIBUTOR = "editAccountDistributor";

	// vivek starts
	public static final String EDIT_PRODUCT = "ProductDetails";

	public static final String CREATE_PRODUCT = "ProductDetails";

	public static final String SELECT_PRODUCT = "ProductDetails";

	public static final String EDIT_WARRANTY = "editWarrantyDetails";
	
	public static final String WARRANTY_ERROR = "errorInUpdation";

	// vivek ends

	public static final String VIEW_ACCOUNT = "viewAccountDetails";

	public static final String OPEN_SEARCH_PAGE = "showSearchAccountJsp";

	public static final String Acc_Code = "account.acccode";

	// set into Account Service
	public static final String USER_TYPE_EXTERNAL = "E";

	public static final String ACC_TYPE_DISTRIBUTOR = "D";

	public static final String ACC_TYPE_RETAILER = "R";

	public static final String ACC_TYPE = "DE";

	public static final String ACC_TYPE_DEALER = "A";


	public static final String GROUP_UPDATE_AUTH = "A";

	public static final String GROUP_UPDATE_NOT_AUTH = "N";

	public static final int ACC_DEFAULT_GROUP_ID = 3;

	// set into DAO

	public static final String SEARCH_TYPE_MOBILE = "M";

	public static final String SEARCH_TYPE_PARENT = "P";

	public static final String SEARCH_TYPE_CODE = "C";

	public static final String SEARCH_TYPE_EMAIL = "E";

	public static final String SEARCH_TYPE_ALL = "A";

	public static final String SEARCH_TYPE_NAME = "N";

	public static final String USER_TYPE_INTERNAL = "I";

	public static final String CIRCLE_NAME = "NATIONAL";

	public static final String POSTPAID_APPLICATION_RESOURCE_BUNDLE = "PostBillPaymentAdapter";

	public static final String WEB_APPLICATION_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.VirtualizationResources";

	public static final String APLLICATION_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.RechargeCore";
	
	public static final String WEBSERVICE_RESOURCE_BUNDLE = "ApplicationResources";
	
//	public static final String WEBSERVICE_RESOURCE_BUNDLE = "E:\\Properties\\ApplicationResources";

	public static final String ERROR_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.ErrorResources";

	public static final String WEB_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.VLWebResources";

	public static final String SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE = "com.ibm.virtualization.recharge.resources.frontController";

	public static final String SRC_USER_BEAN = "com.ibm.virtualization.recharge.beans.UserBean";

	public static final String DATASOURCE_NAME = "datasource_name";
	
	// Add for MIS report
	public static final String DATASOURCE_NAME_SCM = "datasource_name_scm";

	public static final String ORADATASOURCE_NAME = "oradatasource_name";

	public static final String REPORT_DATASOURCE_NAME = "report_datasource_name";
	
	public static final String MIS_REPORT_DATASOURCE_NAME = "datasource_name_RDB";

	public static final String DB_CONNECTION_TYPE = "connection_type";
	
// S&D data source : Anju
	public static final String DATASOURCE_NAME_SND = "datasource_name_snd";
	public static final String DB_CONNECTION_SND = "datasource_snd";

	public static final String DB_DRIVER_NAME = "database.driver";

	public static final String DB_URL = "database.url";

	public static final String DB_USERNAME = "database.username";

	public static final String DB_PASSWORD = "database.password";

	public static final String DB_POOL_MAX_ACTIVE = "database.pool.maxActive";

	public static final String DB_POOL_MAX_IDLE = "database.pool.maxIdle";

	public static final String DB_POOL_MAX_WAIT = "database.pool.maxWait.millisec";

	public static final String DB_POOL_MIN_IDLE = "database.pool.minIdle";

	public static final String DB_POOL_VALIDATION_QUERY = "database.pool.validationQuery";

	public static final String DB_POOL_TEST_WHILE_IDLE = "database.pool.testWhileIdle";

	public static final String DB_POOL_TEST_ON_BORROW = "database.pool.testOnBorrow";

	public static final String DB_POOL_TIME_BETWEEN_EVICTIONRUNS = "database.pool.timeBetweenEvictionRuns.minutes";

	public static final String DB_POOL_MIN_EVICTABLE_IDLE_TIME = "database.pool.minEvictableIdleTime.minutes";

	public static final String DB_POOL_MIN_NUM_TSETS_PER_EVICTION_RUN = "database.pool.numTestsPerEvictionRun";

	public static final int DEFAULT_GROUP_ID = 4;

	public static final String GSD_RESOURCE_BUNDLE = "GSD";

	public static final String PWD_EXPIRY_LIMIT = "PwdExpiryLimit";

	public static final String ACTIVE_STATUS = "A";
	
	public static final String DACTIVE_STATUS = "D";

	public static final String INACTIVE_STATUS = "I";

	public static final int ADMIN_GROUP_ID = 1;

	public static String POSTPAID_REQUEST_MOBILITY = "4";

	public static String POSTPAID_REQUEST_ABTS = "7";

	public static String POSTPAID_REQUEST_DTH = "8";

	public static final String PASSWORD_HISTORY_MAX_COUNT = "password.history.maxCount";
	public static final String PASSWORD_INVALID_COUNT = "password.invalid.Count";
	public static final String PASSWORD_HISTORY_NOREPEAT_COUNT= "password.history.noRepeat.count";
	
	
	// Report Constants
	public static final int ADMIN_CIRCLE_ID = 0;

	public static final String CIRCLE_DISABLED = "Y";

	public static final String ACC_LEVEL_DISTRIBUTOR = "2";

	public static final String ACC_LEVEL_MTP = "6";

	public static final String ACC_LEVEL_MTP_DISTRIBUTOR = "7";

	public static final int MOBILITY_ID = 2;
	
	public static final int CIRCLE_ADMIN_ID = 3;

	public static final int IT_HELP_DESK =14;
	
	public static final int ADMIN_ID = 1;
	
	public static final int ND_GROUP_ID = 11;
	
	public static final int IT_HELP_DESK_LEVEL =13;

	// Report Constants
	public static final String SHOW_A2A_REPORT_CRITERIA = "showA2ATransactionReportCriteria";

	public static final String SHOW_A2A_REPORT_SUCCESS = "showA2ATransactionReportSuccess";

	public static final String SHOW_A2A_REPORT_FAILURE = "showA2ATransactionReportFailure";

	public static final String EXPORT_A2A_REPORT_SUCCESS = "exportA2ATransactionReportSuccess";

	public static final String SHOW_A2A_TFR_SUMMARY_REPORT_CRITERIA = "showA2ASummaryReportCriteria";

	public static final String SHOW_A2A_TFR_SUMMARY_REPORT_FAILURE = "showA2ASummaryReportFailure";

	public static final String SHOW_A2A_TFR_SUMMARY_REPORT_SUCCESS = "showA2ASummaryReportSuccess";

	public static final String EXPORT_A2A_TFR_SUMMARY_REPORT_SUCCESS = "exportA2ASummaryReportSuccess";

	public static final String SHOW_CUSTOMER_RECHARGE_TRANSACTION_REPORT = "showCustomerRechargeTransactionReportCriteria";

	public static final String SEARCH_TYPE_ACCOUNT_CODE = "AC";

	public static final String SEARCH_TYPE_PARENT_CODE = "PC";

	public static final String SEARCH_TYPE_TRAN_ID = "TI";

	public static final String SEARCH_TYPE_TRAN_TYPE = "TT";

	public static final String GROUP_TYPE_EXTERNAL = "E";

	public static final String GROUP_TYPE_INTERNAL = "I";

	// bhanu start
	public static final String REPORT_DEBIT_AMOUNT = "debitAmount";

	public static final String REPORT_SOURCE_MOBILE = "sourceMobileNo";

	public static final String SHOW_TRANS_SUMMARY_REPORT_CRITERIA = "showTransactionSummaryReportCriteria";

	public static final String REPORT_PROCESSING_FEE_START = "processingFee";

	public static final String REPORT_VALIDITY_XML = "report.validity.xml";

	public static final String REPORT_IN_STATUS = "report.in.status.xml";

	//	public static final String REPORT_PROCESSING_FEE_END = "report.processingFee.end";

	public static final String REPORT_TALK_TIME_START = "CreditedAmount";

	//	public static final String REPORT_TALK_TIME_END = "report.talkTime.end";

	public static final String REPORT_SERVICE_TAX_START = "serviceTax";

	public static final String REPORT_SERVICE_TAX_END = "report.serviceTax.end";

	public static final String REPORT_AVAILABLE_BALANCE_START = "balBfrRec"; //report.availableBalance.start

	public static final String REPORT_AVAILABLE_BALANCE_END = "balAftrRec"; //report.availableBalance.end

	public static final String SHOW_TRANS_SUMMARY_REPORT_FAILURE = "showTransactionSummaryReportFailure";

	public static final String SHOW_TRANS_SUMMARY_REPORT_SUCCESS = "showTransactionSummaryReportSuccess";

	public static final String EXPORT_TRANS_SUMMARY_REPORT_SUCCESS = "exportTransactionSummaryReportSuccess";

	public static final String SHOW_TRANS_REPORT_CRITERIA = "showTransactionReportCriteria";

	public static final String REPORT_ESP_COMMISSION_START = "espCommission";

	public static final String REPORT_CONFIRM_MOBILE_NUMBER = "confirmationMobileNo";

	public static final String SHOW_TRANS_REPORT_FAILURE = "showTransactionReportFailure";

	public static final String REPORT_SUBSCRIBER_CIRCLEID = "subscriberCircleId";

	public static final String REPORT_REQUESTER_CIRCLEID = "requesterCircleId";

	//	public static final String REPORT_ESP_COMMISSION_END = "report.espCommission.end";

	public static final String SHOW_TRANS_REPORT_SUCCESS = "showTransactionReportSuccess";

	public static final String SHOW_CUSTOMER_RECHARGE_TRANS_REPORT_SUCCESS = "showRechargeTransactionReportSuccess";

	public static final String SHOW_CUSTOMER_RECHARGE_TRANS_REPORT_SUCCESS_WITHID = "showRechargeTransactionReportSuccessWithId";

	public static final String SHOW_CUSTOMER_RECHARGE_TRANS_REPORT_FAILURE = "showRechargeTransactionReportFailure";

	public static final String SHOW_TRANS_REPORT_VIEW_SUCCESS = "showTransactionReportViewSuccess";

	public static final String EXPORT_TRANS_REPORT_SUCCESS = "exportTransactionReportSuccess";

	public static final String EXPORT_CUSTOMER_RECHARGE_REPORT_SUCCESS = "exportCustomerRechargeReportSuccess";

	public static final String EXPORT_CUSTOMER_VIEW_LIST = "downloadCustomerTransList";

	public static final String TRANSACTION_SUCCESS = "S";

	public static final String TRANSACTION_FAILURE = "F";

	public static final String TRANSACTION_IN_PROGRESS = "P";

	public static final String RECORDS_PER_PAGE = "pagination.records.perpage";

	public static final String LINKS_PER_PAGE = "pagination.links.perpage";

	public static final String VIEW_AUTHORIZED = "Y";

	public static final String VIEW_UNAUTHORIZED = "N";

	public static final String EDIT_AUTHORIZED = "Y";

	public static final String EDIT_UNAUTHORIZED = "N";

	public static final String ACCOUNT_BILLABLE_YES = "Y";

	public static final String ACCOUNT_BILLABLE_NO = "N";

	public static final String APPLICATION_TRANSACTION_CACHE_REFRESH_TIME = "trans_cache.refreshtime.minutes";

	public static final String APPLICATION_TRANSACTION_BLACKOUT_TIME = "trans.blackouttime.minutes";

	public static final String ACCOUNT_SHOW_CIRCLE = "A";

	public static final String ACCOUNT_NOT_SHOW_CIRCLE = "N";

	public static final String ACCOUNT_DISABLE_CIRCLE = "N";

	//mpassword for vendor recharge poc
	public static final String MPASSWORD_VENDOR_RECHARGE_POC = "mpassword.vendor.recharge.poc";

	//flag value for view-verify transaction
	public static final String TRANSACTION_FLAG_VALUE = "Y";

	//	flag value for listing error
	public static final String LIST_ERROR_FLAG = "Y";

	//	 *** name of the resource file
	public static final String RESOURCE_FILE_PASSWORD = "com.ibm.virtualization.recharge.resources.RechargeCore";

	/*	  Succcess */
	public static final String SUCCESS = "Success";

	//	 *** Name of the sender of the message
	public static final String SMS_SENDER_NAME = "SendSmsName.smsSender";

	/* Send SMS  Ip */

	public static final String SMS_SENDER_HOST = "SendSms.ip";

	/* Send SMS  Connect Port */
	public static final String SMS_SENDER_PORT = "SendSms.connect.port";

	/* Send SMS User Id */
	public static final String SMS_SENDER_USRID = "SendSms.userid";

	/* Send SMS Pass */
	public static final String SMS_SENDER_PASS = "SendSms.pass";

	/*	  SMS Sender Prefix */
	public static final String SMS_SENDER_PREFIX = "sendSms.india";

	/*	  Send SMS or Not */
	public static final String SMS_SENDER_SEND = "sendSms";

	/*	  Send MAIL or Not */
	public static final String MAIL_SENDER_SEND = "sendMail";

	public static final String UNLOCK_AUTHORIZED = "Y";

	public static final String UNLOCK_UNAUTHORIZED = "N";

	public static final String RESET_AUTHORIZED = "Y";

	public static final String RESET_UNAUTHORIZED = "N";

	public static final String TRANSACTION_TIMEOUT = "transaction.timeout.minutes";

	public static final String IN_VALIDITY_DATE_PATTERN = "in.validity.datePattern";

	public static final String VALIDITY_DATE_PATTERN = "sms.validity.datePattern";

	public static final String DTH_SETTOPBOX_LENGTH = "dth.settopbox.length";

	// Send Email by Anju

	public static final String SENDER_ID = "Distributor Portal";
	public static final String SENDER_MAIL = "dp@airtel.com";
	public static final String CC_RECEIVER_ID = "DP Admin";
	public static final String CC_RECEIVER_MAIL="dpadmin@airtel.com";

	public static final int DISTRIBUTOR_ID = 7;
	
	public static final int FINANCE_USER_ID = 15;
	
	public static final int TSM_ID = 6;

	public static final int FSE_ID = 8;

	public static final int RETAILER_ID = 9;
	
	public static final int ACC_LEVEL_RETAILER_ID = 9;
	
	public static final int DIST_LEVEL_ID = 6;
	
	public static final String DIST_ACCOUNT_LEVEL= "6";
	
	public static final String disableLink= "true";

	public static final int HIERARCHY_1 = 1;
	
	public static final int HIERARCHY_2 = 2;
	
	public static final int BILLABLE_CODE_ID = 0;
	public static final String SSF_ID = "dp.SSF.LoginID";
	public static final String MANAGER_FINANCE = "Finance";
	public static final String MANAGER_COMMERCE = "Commercial";
	public static final String MANAGER_SALES = "Sales";
	public static final String SEARCH_BEAT_PAGE = "searchBeatPage";
	public static final String EDIT_BEAT_PAGE = "editBeatPage";
	
//	 vivek product constant
	public static final String PRODUCT_CODE = "1";
	public static final String PRODUCT_CODE1="2";
	public static final String PRODUCT_CODE2="4";
	public static final String PRODUCT_CODE3="3";
	public static final String PRODUCT_NAME4 = "Easy Recharge";
	public static final String PRODUCT_NAME3 = "HANDSET";
	public static final String PRODUCT_NAME1 = "SUK";
	public static final String PRODUCT_NAME2="RC";
	
	//Add by harbans on Reservation Obserbation 6th july
	public static final String PRODUCT_CPE="CPE";
	
	public static int PAN_PRODUCTS =0;
	public static final String WEBSERVICE_PWD_CHANGE_DATE="webservice.pwd.changedate";
	public static final String WEBSERVICE_PWD_RECEIVER_ADD="webservice.pwd.receiver.add";
	public static final String WEBSERVICE_PWD_MSG1="webservice.pwd.txt.msg1";
	public static final String WEBSERVICE_PWD_MSG2="webservice.pwd.txt.msg2";
	public static final String WEBSERVICE_SMTP="webservice.pwd.smtp";
	public static final String WEBSERVICE_PWD_SENDER = "webservice.pwd.sender.name";
	public static final String WEBSERVICE_PWD_FROM_EMAIL ="webservice.pwd.from.email";
	public static final String WEBSERVICE_PWD_CC_EMAIL = "webservice.pwd.cc.email"; 
	public static final int consecutiveChars =3;
	public static final String EXPORT_FILE_PATH = "export.file.path";
	
	// Added for Schedular 
	public static final String SCHEDULAR_WEBSERVICE_TIME = "schedular.webservice.timeinterval";
	public static final String SCHEDULAR_CPE_TIME = "schedular.cpe.timeinterval";
	public static final String SCHEDUALR_CPEDP_INTFLAG = "schedular.cpedp.intflag";
	public static final String SCHEDULAR_HOST_IP1 = "schedular.host.ip1";
	public static final String SCHEDULAR_HOST_IP2 = "schedular.host.ip2";
	public static final String SCHEDULAR_HOST_INTERVAL = "schedular.host.interval";
		
	// Added for CPE Schema
	public static final String DB_DPDTH_CPE_SCHEMA = "db.dpdth.cpe.schema";
	
	//Added for Delivery Challan Acceptance
	public static final String DC_REPORT_ACCEPT = "ACCEPT";
	public static final String DC_REPORT_REJECT = "REJECT";
	
	//Added for Missing Stock Approval
	public static final String MSA_GRID_MAP = "MSA_GRID_MAP";
	public static final String MSA_SWAPPED_YES = "YES";
	public static final String MSA_STOCK_STATUS_WRONG = "WRONG";
	
	public static final String MSA_STOCK_STATUS_ID_SELECT = "0";
	public static final String MSA_STOCK_STATUS_ID_WRONG = "WS";
	public static final String MSA_STOCK_STATUS_ID_SWAP = "SWP";
	public static final String MSA_STOCK_STATUS_ID_CANCEL = "CAN";

	public static final int MSA_STATUS_RESTRICTED = 5;
	
	
	// Added by harbans
	public static final int COLLECTION_TYPE_CHURN = 4;
	public static final int COLLECTION_TYPE_UPGRADE = 5;
	public static final int COLLECTION_TYPE_DOA_BEFORE_INST = 2;
	public static final int COLLECTION_TYPE_DOA_AFTER_INST = 6;
	public static final int COLLECTION_TYPE_DEFFECTIVE = 1;
	public static final int COLLECTION_TYPE_COMMERCIAL_TO_SWAP =3;
	public static final int COLLECTION_TYPE_DEAD_ON_ARRIVAL_AFTER_INST = 6;
	
//	Commented by Nazim Hussain as in BoTree code were 0 and 1
//	public static final String COLLECTION_PRODUCT_TYPE_SWP = "2"; 
//	public static final String COLLECTION_PRODUCT_TYPE_COM = "1";
	
//	Modified by Nazim Hussain to make in sync with BoTree	
	public static final String COLLECTION_PRODUCT_TYPE_SWP = "0"; 
	public static final String COLLECTION_PRODUCT_TYPE_COM = "1";
	
	public static final String COLLECTION_STATUS_TYPE_COL = "COL"; // DOA , S TO C, DEFECTIVE 
	public static final String COLLECTION_STATUS_TYPE_REU = "REU"; // CHURN , UPDATE
	public static final String COLLECTION_STATUS_TYPE_ERR = "ERR";
	public static final String COLLECTION_SUCCESS_MESSAGE = "The stock has been received successfully !!!!!!!!";
	public static final String UPGRADE_COLLECTION_SUCCESS_MESSAGE = "Following Upgrade boxes have been added to your Ok Stock!!!!!!";
	public static final String COLLECTION_FAILURE_MESSAGE = "Internal error occurred!!!!!!!!!!! Try again !!!!!!!!";

	//added by saumya
		public static final String OPTION_NO = null;
	//end
		public static final String 	USER_DEFAULT_PASSWORD ="user.default.password";
		
		public static final String 	DC_STATUS_INVALID_DC="INVALID_DC";
		public static final String 	DC_STATUS_PENDING_ON_COMMERCIAL="PENDING_ON_COMMERCIAL";
		public static final String 	DC_STATUS_RECEIVED_BY_WAREHOUSE="RECEIVED_BY_WAREHOUSE";
		public static final String 	DC_STATUS_REJECTED_BY_COMMERCIAL="REJECTED_BY_COMMERCIAL";
		public static final String BULK_UPLAOD_MAX_LIMIT = "bulk.upload.maxlimit";
		public static final String BULK_LAPU_MAX_LIMIT = "bulk.lapu.maxlimit";
		public static final String BULK_UPLAOD_FOR_DIST_STOCK_ELIGIBILITY_MAX_LIMIT = "bulk.upload.for_dist_stock_eligibility_maxlimit";
		
     // Added for CPE Update during Flush Out
		public static final int STATUS_UNASSIGNED_PENDING = 10;

		public static final int STATUS_RESTRICTED = 5;
		
		public static final int STATUS_UNASSIGNED_COMPLETE = 1;

		public static final int STATUS_UNASSIGNED_COMPLETE_INCONSISTENT = 21;
		
		public static final int STATUS_ASSIGNED = 3;
		
		public static final String STATUS_SYSTEM_OVERRIDEN = "-2"; 

		public static final String UPLOAD_FILE_PATH = "upload.file.save.path" ;
		public static final String UPLOAD_FILE_PTH = "upload.file.path" ;
		// Update by harbans on Reverse Enhancement.
		public static final int STOCK_STATUS_RESTRICTED  = 5;
		public static final int STOCK_STATUS_UNASSIGNED_PENDING  = 10;
		public static final int STOCK_STATUS_UNASSIGNED_COMPLETE  = 1;
		public static final int STOCK_STATUS_ASSIGNED  = 3;
		public static final int STOCK_STATUS_ASSIGNED_DAO_COLLECTION  = 11;
		// Add by harbans 18th July Stb Status Report
		public static final int STOCK_STATUS_ASSIGNED_SWAP_REPLACEMENT_COLLECTION = 12;
				
//		 Add by harbans on Reservation Obserbation 30th June
		public static final String 	REVERSE_COM_PRODUCT_TYPE = "COMMERCIAL";
		public static final String 	REVERSE_COM_PRODUCT_VALUE = "1";
		public static final String 	REVERSE_SWAP_PRODUCT_VALUE = "0";
		public static final String 	REVERSE_AV_PRODUCT_VALUE = "2";
		
		
//Added by Shilpa Khanna for Reverse collection of Swap type
		public static final int STOCK_STATUS_ASSIGNED_SWAP_COLLECTION  = 12;
		
		public static final String DP_CPE_USER="dp.cpe.user";

		public static final String ACC_STATUS = "A";
		public static final String PO_TSM_ACTION_TAKEN = "TSM_ACTION_TAKEN";

		public static final Integer PO_STATUS_PO_ACCEPT = 31;
		public static final Integer PO_STATUS_PO_ERROR = 32;
		public static final Integer PO_STATUS_PO_TSM_ACTION_TAKEN = 33;
		public static final Integer PO_STATUS_PO_TSM_ACTION_PENDING = 34;
		public static final int PO_PR_ACTIVE_STATUS = 1;
		public static final int PO_PR_INACTIVE_STATUS = 2;
		public static final String DATASOURCE_NAME_CPE = "datasource_name_cpe";

		public static final String CHURN_STOCK_OK_MSG = "Validated - OK";
		
		public static final String 	SPECIAL_CHARS_PASSWORD ="specialChars";
		public static final String 	UPPER_CHARS_PASSWORD ="upperChars";
		public static final String 	LOWER_CHARS_PASSWORD ="lowerChars";
		public static final String LOWER_CHARS_CONST ="lowerCharsConst";
		public static final String 	MAIL_SMTP_HOST = "mail.smtp.host";
		public static final String 	LOGIN_SMTP ="login.smtp";
		public static final String 	SENT_FROM_EMAIL ="sendFrom.email";

		public static final String MAIL_NEW_USER_PASSWRD_SUBJECT = "mail.new.user.password.subject";
		public static final String NEW_USER_PASSWRD_MAIL_SURNAME = "new.user.passwrd.mail.surname";
		public static final String NEW_USER_PASSWRD_MAIL_MSG = "new.user.passwrd.mail.message";
		public static final String NEW_USER_PASSWRD_MAIL_PASSWRD_TEXT = "new.user.passwrd.mail.passwrd.text";
		public static final String NEW_USER_PASSWRD_MAIL_FOOTER = "new.user.passwrd.mail.footer";
		public static final String NEW_USER_PASSWRD_MAIL_SPACE1 = "new.user.passwrd.mail.space1";
		public static final String NEW_USER_PASSWRD_MAIL_SPACE2 = "new.user.passwrd.mail.space2";
		public static final String NEW_USER_PASSWRD_MAIL_SPACE3 = "new.user.passwrd.mail.space3";
		public static final String NEW_USER_PASSWRD_MAIL_SPACE4 = "new.user.passwrd.mail.space4";
		public static final String NEW_USER_PASSWRD_MAIL_SPACE5 = "new.user.passwrd.mail.space5";
		public static final String NEW_USER_PASSWRD_MAIL_Critical = "new.user.passwrd.mail.error.critical";
		public static final String NEW_USER_PASSWRD_MAIL_REGARD = "new.user.passwrd.mail.regard";
		public static final String NEW_USER_PASSWRD_MAIL_REGARDBY = "new.user.passwrd.mail.regardby";
		
		public static final String ACCESS_LEVEL_IDS_IT_HELP = "1,2,3,4,5,6,14";
		
		public static final String DATASOURCE_NAME_HRMS = "datasource_name_hrms";
		public static final String HRMS_VALIDATION= "hrms.validation.flag";
		public static final int PRODUCT_CATEGORY_CODE_AV = 4;
		public static final int TRANSACTION_TYPE_DEPOSIT = 2;

		public static final String ALERT_NOTE_MSG = "self.alert.note.msg";

		public static final int CONFIRM_ID_DC_CREATION= 1;
		public static final int CONFIRM_ID_CHANGE_PASSWORD = 2;
		public static final int CONFIRM_ID_WRONG_SHIPMENT = 3;
		public static final int CONFIRM_ID_PO_REJECT = 4;
		public static final int CONFIRM_ID_DC_DISPATCHED= 5;
		public static final int CONFIRM_ID_CREATE_PO= 6;
		public static final int CONFIRM_ID_PO_APPROVE_TSM= 7;
		public static final int CONFIRM_ID_MISSING_DC= 9;

		public static final int CONFIRM_ID_SMS_PULL=22;

		public static final int CONFIRM_ID_RECO_GRACE_DIST= 19;
		public static final int CONFIRM_ID_RECO_GRACE_TSM= 20;
		public static final int CONFIRM_ID_RECO_GRACE_ZSM= 21;		

		public static final String DIST_PINCODE_BULKUPLOAD_UPLAOD_MAX_LIMIT = "distpinmap.bulk.upload.maxlimit";
		//Added by Neetika
		public static final int DISTRIBUTOR_TYPE_SALES = 1;
		public static final int DISTRIBUTOR_TYPE_DEPOSIT = 2;
		
		
		public static final String IS_SCM_FLUSH = "is.scm.flush";//Added by aman for DP_Flushout on 14/03/14
		public static final int total_columns_COMMSF=53;
		public static final int total_columns_COMMSSD=56;
		public static final int total_columns_SWAPSF=48;
		public static final int total_columns_SWAPSSD=55;
		
		//Added by Sanjay
		public static final String PENDING_LIST_TRANSFER_BULK_UPLOAD_STATUS = "Pending List Transfer";		
		public static final String INTER_SSD_TRANSFER_BULK_UPLOAD_STATUS = "Inter SSD Transfer";		
		public static final String STOCK_TRANSFER_STATUS_ACCEPTED ="ACCEPTED";
		public static final String STOCK_TRANSFER_STATUS_RECEIVED = "RECEIVED";
		
		public static final String DOWNLOAD_PDF="DOWNLOAD_PDF";
		
}
