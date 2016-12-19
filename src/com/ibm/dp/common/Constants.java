/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.dp.common;

/**
 * Class to define all the constants.
 *
 * @author Rohit kunder
 *
 */
public final class Constants {

	//TODO Harsh Mar 06, Split file logically.

	/* AUTHENTICATED_USER stores details of logged in user */
	public static final String AUTHENTICATED_USER = "USER_INFO";

	/* TRANSACTION_DISTRIBUTION details */
	public static final String USER_NOT_AUTHERIZED = "autherization";
	public static final String DATABASE_TYPE = "database.type";
	public static final String SUCCESS_MESSAGE = "success";
	public static final String TRANS_DIST_PENDING = "P";
	public static final String ERROR_MESSAGE = "error";
	public static final String TRANS_DIST_APPROVED = "A";
	public static final String ERROR_OCCURED_MESSAGE = "ERROR_OCCURED";
	public static final String TRANS_DIST_REJECTED = "R";
	public static final String AIRTEL_DISTRIBUTOR = "Airtel Distributor";
	
	public static final String DISTRIBUTOR_NAME = "Distributor";
	
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

	// CREATE_ACCOUNT create account
	public static final String ACCOUNT_ERROR = "error";;

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

	public static final String VIEW_ACCOUNT = "viewAccountDetails";

	public static final String OPEN_SEARCH_PAGE = "showSearchAccountJsp";

	public static final String Acc_Code = "account.acccode";

	// set into Account Service
	public static final String USER_TYPE_EXTERNAL = "E";

	public static final String ACC_TYPE_DISTRIBUTOR = "D";

	public static final String ACC_TYPE_RETAILER = "R";

	public static final String ACC_TYPE = "DE";

	public static final String ACC_TYPE_DEALER = "A";

	public static final String ACCOUNT_ACTIVE_STATUS = "A";

	public static final String GROUP_UPDATE_AUTH = "A";

	public static final String GROUP_UPDATE_NOT_AUTH = "N";

	public static final int ACC_DEFAULT_GROUP_ID =3 ;

	// set into DAO

	public static final String SEARCH_TYPE_MOBILE = "M";

	public static final String SEARCH_TYPE_PARENT = "P";

	public static final String SEARCH_TYPE_CODE = "C";

	public static final String SEARCH_TYPE_EMAIL = "E";

	public static final String SEARCH_TYPE_ALL = "A";

	public static final String SEARCH_TYPE_NAME = "N";

	public static final String USER_TYPE_INTERNAL = "I";

	public static final String CIRCLE_NAME="NATIONAL";

	public static final String POSTPAID_APPLICATION_RESOURCE_BUNDLE ="PostBillPaymentAdapter";

	public static final String WEB_APPLICATION_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.VirtualizationResources";

	public static final String APLLICATION_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.RechargeCore";

	public static final String ERROR_RESOURCE_BUNDLE = "com.ibm.virtualization.recharge.resources.ErrorResources";

	public static final String WEB_RESOURCE_BUNDLE= "com.ibm.virtualization.recharge.resources.VLWebResources";

	public static final String SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE = "com.ibm.virtualization.recharge.resources.frontController";

	public static final String SRC_USER_BEAN = "com.ibm.virtualization.recharge.beans.UserBean";

	public static final String DATASOURCE_NAME = "datasource_name";

	public static final String ORADATASOURCE_NAME = "oradatasource_name";

	public static final String REPORT_DATASOURCE_NAME = "report_datasource_name";
	
	public static final String MIS_REPORT_DATASOURCE_NAME = "datasource_name_RDB";
	
	public static final String MIS_REPORT_DOWNTIME_FROM= "report_from_time";
	
	public static final String MIS_REPORT_DOWNTIME_TO= "report_to_time";

	public static final String DB_CONNECTION_TYPE = "connection_type";

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

	public static final String DB_POOL_MIN_EVICTABLE_IDLE_TIME="database.pool.minEvictableIdleTime.minutes";

	public static final String DB_POOL_MIN_NUM_TSETS_PER_EVICTION_RUN="database.pool.numTestsPerEvictionRun";

	public static final int DEFAULT_GROUP_ID =4 ;

	public static final String GSD_RESOURCE_BUNDLE = "GSD";

	public static final String PWD_EXPIRY_LIMIT = "PwdExpiryLimit";

	public static final String ACTIVE_STATUS = "A";

	public static final String INACTIVE_STATUS = "I";




	public static final int ADMIN_GROUP_ID = 1;
	public static final int DTH_ADMIN_GROUP_ID = 2;
	public static final int TSM_GROUP_ID = 6;
	public static final int GROUP_ID_CIRCLE_ADMIN = 3;
	public static final int GROUP_ID_ZBM = 4;
	public static final int GROUP_ID_ZSM = 5;
	
	public static String POSTPAID_REQUEST_MOBILITY = "4";

	public static String POSTPAID_REQUEST_ABTS = "7";

	public static String POSTPAID_REQUEST_DTH = "8";

	


	public static final String PASSWORD_HISTORY_MAX_COUNT = "password.history.maxCount";


	// Report Constants
	public static final int ADMIN_CIRCLE_ID = 0;

	public static final String CIRCLE_DISABLED = "Y";
	
	public static final String TSM = "TSM";

	public static final String ACC_LEVEL_CA = "2";

	public static final String ACC_LEVEL_TSM = "5"; // Added by Mohammad Aslam

	public static final String ACC_LEVEL_MTP = "6";

	public static final String ACC_LEVEL_MTP_DISTRIBUTOR = "1";

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

	public static final String STB_REQUEST_TYPE = "Request type should be only 2.";
	
	// bhanu start
	public static final String REPORT_DEBIT_AMOUNT ="debitAmount";

	public static final String REPORT_SOURCE_MOBILE ="sourceMobileNo";

	public static final String SHOW_TRANS_SUMMARY_REPORT_CRITERIA = "showTransactionSummaryReportCriteria";

	public static final String REPORT_PROCESSING_FEE_START = "processingFee";

	public static final String REPORT_VALIDITY_XML = "report.validity.xml";

	public static final String REPORT_IN_STATUS = "report.in.status.xml";

//	public static final String REPORT_PROCESSING_FEE_END = "report.processingFee.end";

	public static final String REPORT_TALK_TIME_START = "CreditedAmount";

//	public static final String REPORT_TALK_TIME_END = "report.talkTime.end";

	public static final String REPORT_SERVICE_TAX_START = "serviceTax";

	public static final String REPORT_SERVICE_TAX_END = "report.serviceTax.end";

	public static final String REPORT_AVAILABLE_BALANCE_START = "balBfrRec";  //report.availableBalance.start

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

	public static final String IN_VALIDITY_DATE_PATTERN="in.validity.datePattern";

	public static final String VALIDITY_DATE_PATTERN="sms.validity.datePattern";

	public static final String DTH_SETTOPBOX_LENGTH="dth.settopbox.length";
	public static final String FILE_PATH_TO_SAVE = "DP.FilePathToSave";
//	 *** REPORT URL
	public static final String REPORT_URL = "DP.reportExtURL";
	public static final String DP_reportExtUSER ="DP.reportExtUSER";
	public static final String	DP_reportExtPASSWORD="DP.reportExtPASSWORD";


	/*************************Business User Type***********************/
	public  static final int CEO = 2;
	public  static final int ED = 1;
	public  static final int SALES_HEAD= 3;
	public  static final int ZBM = 4;
	public  static final int ZSM = 5;
	public  static final int TM = 6;
	public  static final int DISTIBUTOR = 7;
	public  static final int FOS = 8;
	public  static final int DEALER = 9;
	public  static final String all= "All";
	public  static final String Include= "Include";

	public  static final String USER_ROLE= "USER_ROLE";

	public  static final String hubList= "hubList";
	public  static final String typeOfUserList= "typeOfUserList";
	public  static final String baseLoc= "baseLoc";
	public  static final String cityList= "cityList";
	public  static final String zoneList= "zoneList";
	public  static final String territoryList= "territoryList";
	public  static final String UPDATED= "UPDATED";
	public  static final String VALID= "VALID";
	public  static final String CODE_PREFIX= "CODE_PREFIX";
	public  static final String REGISTERNUMBER= "REGISTERNUMBER";
	public  static final String CODE_EXIST= "CODE_EXIST";
	public  static final String INVALID= "INVALID";
	public  static final String singleUpload= "singleUpload";
	public  static final String bulkUpload= "bulkUpload";
	public  static final String Code= "Code";
	public  static final String Name= "Name";
	public  static final String regNumber= "regNumber";

	// used in mapping from resources
	public  static final String no_cities= "no_cities";
	public  static final String no_cicles= "no_cicles";
	public  static final String no_zone= "no_zone";
	public  static final String no_territory= "no_territory";
	public  static final String Y= "Y";
	public  static final String N= "N";
	public  static final String minusOne= "-1";
	public  static final int LengthOfInserted = 8;
	public  static final String SUCCESSCAPS= "SUCCESS";
	public  static final String errors_service_class= "errors.service.class";
	public  static final String errors_registered_number= "errors.registered.number";
	public  static final String error_list_noListFound = "error.list.noListFound";
	public  static final String SERVICE_CLASS_EXSISTS= "SERVICE_CLASS_EXSISTS";
	public  static final String errors_already= "errors.already";
	public  static final String errors_insert= "errors.insert";
	public  static final String UserCreated= "UserCreated";

	//find forwards
	public  static final String Profile= "Profile";
	public static final String SUPER_ADMIN="SuperAdmin";

	public static final String CIRCLE_ADMIN="CircleAdmin";
	public static final String Zone_User="ZoneUser";

	public static final String Retailer_User="RetailerHelpDisk"	;

	public static final String CIRCLE_ADMIN_USERBEAN="CA";
	public static final String Zone_User_USERBEAN="ZU";

	public static final String ERROR="ERROR";

	public static final String ERRORS="ERRORS";

	public static final String ERROR_KEY="ErrorKey";

	/*************************LOCATION Type***********************/
	public  static final int City = 4;
	public  static final int Zone = 3;
	public static final int CityParentId=3;
	public  static final int Territory = 5;
	public  static final int Circle= 2;
	public  static final int Hub= 1;
	public  static final String Location = "Location";
	public  static final String BizUser = "BusinessUser";

	public  static final String strCircle= "Circle";
	public  static final String strZone= "Zone";
	public  static final String strTerritory= "Territory";
	public  static final String strCity= "City";
	public  static final String strHub= "Hub";
	public  static final String CIRCLE = "circle";
	public  static final String CITY = "city";
	public  static final String ZONE = "zone";
	public  static final String ACTIVE = "active";

	/*************************Pagination in Location*****************/
	public  static final int PAGE_TRUE = 1;
	public  static final int PAGE_FALSE = 0;
	public  static final int PAGE_DROPDOWN = 2;

	public static final String CHANGEPASSWORD = "changePassword";
	public static final String GROUP_ID = "GROUP_ID";
	public static final String SESSION_EXPIRED = "Session Expired";
	public static final String STARTDT = "startDt";
	public  static final String ENDDT = "endDt";

	public  static final String HUB_CODE = "ALL";

	public static final String ERRORLOGID = "errors.loginId.inUse";
	public static final String ERRORPWD = "errors.pwd";
	public static final String ERRORAUTOPWD = "errors.auto.pwd";
	public static final String ERRORSTATUS = "errors.status.not.changed";
	public  static final String ERRORUPDATELOCATION= "errors.update.location";
	public  static final String SUCCESSUPDATELOCATION= "success.update.location";
	public static final String USER_TYPE = "USER_TYPE";

	public static final String CIRCLE_LIST = "circleList";

	/*************************FOR ACTION FORWARD ***********************/
	public  static final int InAcive = 0;
	public  static final int Active = 1;
	public  static final String ActiveState = "A";
	public  static final String LockStatus = "Y";
	public  static final int ZoneStatus = 1;
	public  static final String strActive = "Active";
	public  static final String strInAcive = "InActive";
	public  static final String circleLogin = "CA";
	public  static final String retailerLogin = "RHD";
	public  static final String circleAdmin = "Circle Admin";
	public  static final String zoneLogin = "ZU";
	public  static final String ZoneUser = "Zone User";
	public  static final String AdminType = "A";
	public  static final String Admin = "Admin";
	public  static final String InActiveState = "I";
	public  static final String InActive = "D";
	public static final String GENERAL_ERROR="general.error";
	public static final String INIT= "INIT";
	public static final String INSERTED="INSERTED";
	public static final String EXCEPTION="Exception has occured :";

	// web service Authentication

	//public static final String username = "integration";
	//public static final String password = "MtuSBETdTNRJeWZidIv9Eg==";
	//public static final String password = "R4QsmElNvPDoDOt4iEp3vw==";
	public static final String WEBSERVICES_USER_NAME = "webservices.user.name";
	public static final String WEBSERVICES_PASSWORD = "webservices.user.password";

	// Add by harbans
	public static final String PARAM_CPE_TO_DP= "CPE_TO_DP";
	public static final String CPE_SCHEMA = "CPEUSER.";

	// Oracle SCM Status
	public static final String TRANS_TYPE_ALDP = "ALDP";
	public static final String TRANS_TYPE_SSDP = "SSDP";
	public static final String TRANS_TYPE_SSCP = "SSCP";
	public static final String TRANS_TYPE_ERR = "ERR";

	public static final String TRANS_TYPE_RESTRICTED = "5";
	public static final String WRONG_SHIPMENT_TYPE_SHORT = "SHORT";
	public static final String WRONG_SHIPMENT_TYPE_WRONG = "WRONG";
	public static final String WRONG_SHIPMENT_TYPE_EXTRA = "EXTRA";
	public static final String WRONG_SHIPMENT_TSM_APPROVAL_STATUS = "TSM_APPROVAL_PENDING";

	// add by harbans
	public static final String TRANS_MESSAGE = "Transaction successfully completed.";


	//added by Aslam
	public static final String ST_ACTION_TRANSFER = "TRANSFER";
	public static final String ST_ACTION_CANCEL = "CANCEL";
	public static final String ST_ACTION_INTRANSIT = "INTRANSIT";




//----------------Added by Nazim Hussain--------------------------------------------
	public static final String DST_FROM_DIST_ARRAY = "DST_FROM_DIST_ARRAY";
	public static final String DST_TO_DIST_ARRAY = "DST_TO_DIST_ARRAY";
	public static final String DST_PRODUCT_ARRAY = "DST_PRODUCT_ARRAY";
	public static final String DST_TRANS_QTY_ARRAY = "DST_TRANS_QTY_ARRAY";

	public static final String DST_STATUS_INITIATED = "INITIATED";

	public static final String ST_STOCK_SUCCESS_MESSAGE = "Stock Transfered Successfully for DC No. : ";
	public static final String ST_STOCK_SUCCESS_MESSAGE_CANCEL = "Stock Cancelled Successfully for DC No. : ";
	public static final String WRONG_SHIP_FLAG_NEW = "NEW";
	public static final String WRONG_SHIP_FLAG_DP = "DP";
	public static final String WRONG_SHIP_FLAG_BT = "BT";
	public static final int WRONG_SHIP_LAST_RUN_MSG_LEN = 1490;

	public static final int OPEN_STOCK_ERROR_DIST = 1;
	public static final int OPEN_STOCK_ERROR_PROD = 2;
	public static final int OPEN_STOCK_ERROR_QTY = 3;
	public static final int OPEN_STOCK_ERROR_OTHERS = 4;
	public static final String OPEN_STOCK_WS_MSG_SUCCESS = "SUCCESS";
	public static final String OPEN_STOCK_WS_MSG_OTHERS = "OTHERS";

	public static final int ACC_LEVEL_DIST = 6;
	public static final String STOCK_DEPLETE_SUCCESS_MSG = "Stock depleted successfully";
//	----------------Added by Nazim Hussain ends here---------------------------------------

	public static final String PUSH_DC_TO_BOTREE_SUCCESS_MSG = "Values are inserted successfully";
	public static final String PUSH_DC_TO_BOTREE_CREATED_STATUS = "CREATED";
	public static final String PUSH_DC_TO_BOTREE_SUCCESS_STATUS = "SUCCESS";
	public static final String PUSH_DC_TO_READY_DISPATCH_STATUS = " ready to dispatch";
	public static final String PUSH_DC_TO_BOTREE_ERROR_STATUS = "ERROR";
	public static final String PUSH_DC_TO_BOTREE_CANCEL_STATUS = "CANCEL";
	public static final String PUSH_DC_TO_BOTREE_ERROR_S2W = "S2W";
	public static final String PUSH_DC_TO_BOTREE_ERROR_ERR = "ERR";
	//Added by Shilpa for Critical changes CR, BFR 14
	public static final String PUSH_DC_TO_BOTREE_DRAFT_STATUS = "DRAFT";

	public static final String INTER_SSD_FSE_TRANSFER= "FSE";
	public static final String INTER_SSD_FSE_TRANSFER_DISPLAY= "FSE TRANSFER";
	public static final String INTER_SSD_RETAILE_TRANSFER = "RET";
	public static final String INTER_SSD_RETAILER_TRANSFER_DISPLAY= "RETAILER TRANSFER";

	public static final String INTER_SSD_WITH_RETAILER= "WR";
	public static final String INTER_SSD_WITH_RETAILER_DISPLAY= "WITH RETAILER";
	public static final String INTER_SSD_WITH_OUT_RETAILER= "WOR";
	public static final String INTER_SSD_WITH_OUT_RETAILER_DISPLAY= "WITHOUT RETAILER";

	public static final String HIER_TRANS_FORWARD = "FWD";


	public static final String HEIR_TRANS_STATUS_ACPT = "ACPT";

	public static final Object HEIR_TRANS_TYPE_FSE = "FSE";

	public static final String HEIR_TRANS_STATUS_INIT = "INIT";



	public static final String C2S_BULK_UPLOAD_HEADER = "STB";
	public static final int C2S_BULK_UPLOAD_NO_LENGTH= 11;
	public static final int C2S_BULK_UPLOAD_NO_LENGTH_AV= 16;
	public static final int C2S_BULK_UPLOAD_FILE_MAX_SIZE= 200000;

	public static final String C2S_BULK_UPLOAD_ALREADY_EXIST_TODAY= "ALREADY_EXIST";
	public static final String C2S_BULK_UPLOAD_ALREADY_NOT_EXIST_TODAY= "ALREADY_NOT_EXIST";

	
	public static final String CONSUMPTION_TYPE ="Airtel DP Consumption Booking";

	public static final String PRODUCT_UNIT_R = "R";
	public static final String PRODUCT_UNIT_RUPEES = "Rupees";
	
	public static final String PRODUCT_UNIT_N = "N";
	public static final String PRODUCT_UNIT_NOS = "Nos";
	
	public static final String PRODUCT_UNIT_Z = "Z";
	public static final String PRODUCT_UNIT_NONE = "None";
	public static final String COLLECTION_TYPE = "collection Type";
	public static final String STB_TYPE = "stb Type";
	public static final String FROM_DATE = "from Date";
	public static final String TO_DATE = "to Date";
	public static final String STATUS = "status";
	
	
	public static final String ANDR_AND_STB_NO_IS_NOT_SAME = "Android and STB No should have same distributor id";

	public static final String ANDR_FLAG_IS_NULL = "Please provide flag (Y/N).";

	public static final String STB_PRODUCT_TYPE=" The Product type not matching.";
	
	public static final String STB_STATUS_WS_MSG_INVALID_SR_NO_LIST = "Invalid_Serial_Number_List";
	public static final String STB_STATUS_WS_MSG_INVALID_STATUS_NO_LIST = "Invalid_Status_List";
	public static final String STB_STATUS_WS_MSG_INVALID_USER_NAME = "Invalid_User_Name";
	public static final String STB_STATUS_WS_MSG_INVALID_PASSWORD = "Invalid_Password";
	public static final String STB_STATUS_WS_MSG_INVALID_STB_PRODUCT_NOS = "Number of Products and Serial nos are differ.";
	
	//added by Karan
	public static final String STB_STATUS_WS_MSG_NULL_INPUT = "Some WebService Parameters are Null or Blank";
	public static final String STB_STATUS_WS_MSG_INVALID_PO = "Invalid_PO_No";
	
	public static final String STB_STATUS_WS_MSG_SUCCESS = "SUCCESS";

	//Added by nazim hussain for Reverse Collection types
	public static final int COLLECTION_SWAP = 1;
	public static final int COLLECTION_DOA = 2;
	public static final int COLLECTION_C2S = 3;
	public static final int COLLECTION_CHURN = 4;
	public static final int COLLECTION_UPGRADE = 5;

	public static final String REV_COLL_MSG_SRNO_NOT_ASSIG = "Serial Number has not been activated yet";
	public static final String REV_COLL_MSG_DOA_30_DAYS = "Serial Number has been activated before 15 days";
	public static final String REV_COLL_MSG_INVALID_PRODUCT = "Invalid Product selected";
	public static final String REV_COLL_MSG_SRNO_UNASSIGNED = "Serial Number has not been activated yet";
	public static final String REV_COLL_MSG_INVALID_SRNO_C2S = "This Serial Number can not be collected against Commercial To SWAP";
	public static final String REV_COLL_MSG_SRNO_ALREADY_REC = "Serial Number has already been received";
	public static final String REV_COLL_MSG_SRNO_ALREADY_S2W = "Serial number has already been sent to Warehouse";
	public static final String INERNAL_ERROR = "Internal error occured";
	public static final String REV_COLL_MSG_INVALID_SRNO = "Invalid Serial Number";
	public static final String REV_COLL_MSG_SWAP_STB_DOA = "SWAP type STB does not qualify for Dead On Arrival";
	
	//Added by nazim hussain for the Reverse DC Status
	public static final String DC_STATUS_COMP_RECEIVE = "Received by Warehouse";
	public static final String DC_STATUS_PARTIAL_RECEIVE = "DC Accepted with Modifications";
	public static final String DC_STATUS_COMP_REJECT = "DC Rejected";
	
//	Added by nazim hussain for the default Parent product code
	public static final String PARENT_PRODUCT_DEFAULT = "0";

	
	public static final String DC_SR_NO_STATUS_S2W = "S2W";
	public static final String DC_SR_NO_STATUS_S2W_Full= "Sent To Warehouse";
	public static final String DC_SR_NO_STATUS_AIW = "AIW";
	public static final String DC_SR_NO_STATUS_AIW_FULL = "Accepted in Warehouse";
	public static final String DC_SR_NO_STATUS_IDC= "IDC";
	public static final String DC_SR_NO_STATUS_IDC_FULL= "Ready to Dispatch";
	public static final String DC_SR_NO_STATUS_IDC_STATUS="In Incomplete DC";
	public static final String DC_SR_NO_STATUS_ERR= "ERR";
	public static final String DC_SR_NO_STATUS_ERR_FULL= "Error in Sending to Warehouse";
	public static final String DC_SR_NO_STATUS_MSN= "MSN";
	public static final String DC_SR_NO_STATUS_MSN_FULL= "Missing Serial Number";
	public static final String DC_SR_NO_STATUS_ABW = "ABW";
	public static final String DC_SR_NO_STATUS_ABW_FULL = "Added By Warehouse";

	
	//Added by shilpa Khanna
	public static final String REV_COLL_MSG_SRNO_NOT_ASSIG_COMM = "Serial Number is commercial type and has not been activated yet";

//	Added by nazim hussain for DC Type
	public static final String DC_TYPE_REVERSE = "REVERSE";
	public static final String DC_TYPE_FRESH = "FRESH";
	public static final String DC_TYPE_CHURN = "CHURN";
	public static final String DC_TYPE_REVERSE_PREFIX = "STN";
	public static final String DC_TYPE_FRESH_PREFIX = "RFD";
	public static final String DC_TYPE_CHURN_PREFIX = "CHR";
	public static final int DC_TYPE_REVERSE_CONFIG_ID = 3;
	public static final int DC_TYPE_FRESH_CONFIG_ID = 10;
	public static final int DC_TYPE_CHURN_CONFIG_ID = 16;
	public static final int DP_RECO_GRACE_PERIOD = 11;
	
	public static final int STB_STATUS_RETRUN_TO_WH = 14;
	
	public static final int STB_STATUS_RETRUN_TO_WH_SWAP = 11;
	public static final String PRODUCT_BUSINESS_CATEGORY_CPE = "1";

	public static final String SEL_DEF_VAL = "-1";
	public static final String SEL_DEF_TXT = "---Please Select---";
	
//	Added by shilpa Khanna
	public static final String STOCK_TRANSFER_STATUS_RECEIVED = "RECEIVED";
	public static final String STOCK_TRANSFER_STATUS_INTERFACE = "INTERFACE";
	public static final String STOCK_TRANSFER_STATUS_ACCEPTED = "ACCEPTED";
	

	
	//Added by Priya 
	public static final String RECO_GRACE_PERIOD = "RECO_GRACE_PERIOD";

//Added by Shilpa for Reco detail	
	public static final String PARTNER_RECO_DETAIL_SUCCESS = "SUCCESS";
	public static final String PO_CREATE_DATE = "PO_CREATE_DATE";
	public static final String DISPATCH_DATE = "DISPATCH_DATE";
	public static final String PO_ACCEPTANCE_DATE = "PO_CREATE_DATE";
	
	public static final String PR_NO = "PR_NO";
	public static final String PO_NO = "PO_NO";
	public static final String DC_NO = "DC_NO";
	
	public static final String DT_FORMAT_SQL = "dd/mm/yyyy hh:mm:ss a";
	public static final String DT_FMT_SQL = "MM/dd/yyyy";
	public static final String DT_FMT_RECO  = "MM-dd-yyyy";
	public static final String DATE_FORMAT  = "yyyy-MM-dd";
	public static final String DT_STB_TRANSFER  = "dd-MMM-yyyy";
	public static final String PO_STATUS_WS_MSG_SUCCESS ="SUCCESS";
	public static final String PO_STATUS_WS_MSG_FAIL ="Invalid PO No";
	public static final String INVALID_DC_NO_WS_MSG_FAIL ="Invalid DC No";
	public static final String INVALID_INV_NO_WS_MSG_FAIL ="Invalid Invoice No";

	public static final String PO_STATUS_WS_MSG_INVALID_STATUS_DATE = "Invalid PO Status Date Format";
	public static final String PO_STATUS_WS_MSG_MANDATORY_STATUS_DATE = "PO Status Date is Mandatory";

	public static final String PO_STATUS_WS_MSG_INVALID_STATUS = "Invalid PO Status";
//Added  by Shilpa for Dist STB Mapping Webservice
	public static final String STB_STB_MAP_WS_INVALID_USER_NAME = "Invalid User Name or Password";
	public static final String STB_STB_MAP_WS_INVALID_PASSWORD = "Invalid User Name or Password";
	public static final String CAMAND_PRODUCT_TYPE="Product type should be CAM AND.";
	public static final String ANDPROD_PRODUCT_TYPE="Product type should be ";
	public static final String ANDROID_RECOVERY = "Only Android No is required";
	
	public static final String DIST_STB_MAP_WS_INVALID_OLM_ID = "Invalid OLM ID";
	public static final String DIST_STB_MAP_WS_SUCCESS = "SUCCESS";
	public static final String DIST_STB_MAP_WS_INVALID_STB = "Invalid Serial No";
	public static final String DIST_STB_MAP_WS_INVALID_MAPPING = "STB does not belong to OLM ID";
	public static final String DIST_STB_MAP_WS_STATUS_1 = "SUCCESS";
	public static final String DOA_HDB="Success with HDB";
	public static final String DIST_STB_MAP_WS_STATUS_5 = "STB is in restricted mode";
	public static final String DIST_STB_MAP_WS_STATUS_10  = "STB can be activated after 30 mins";
	public static final String DIST_STB_MAP_WS_NOT_EXIST  = "STB Does not Exist In DPDTH.";
	public static final String DIST_STB_MAP_WS_NOT_MATCH  = "STB Does not belong to Distributor.";
	
	public static final String WH_MST_BOtree_WS_STATUS_1 = "SUCCESS";
	public static final String WH_MST_BOtree_WS_NOT_EXIST ="Warehouse Code Does not Exist In DPDTH.";
	public static final String WH_MST_BOtree_WS_MAP_DIST ="Warehouse is already mapped with some distributor in DP";
	public static final String WH_MST_BOtree_WS_EXIST ="Warehouse Code Already Exist In DPDTH.";
	public static final String WH_MST_BOtree_CIRCLE_NOT_EXIST = "Circle Code Does not Exist In DPDTH.";

	public static final String PO_STATUS_WS_MSG_INVALID_PO_STATUS = "INVALID_PO_STATUS";
	public static final String PO_STATUS_WS_MSG_INVALID_PR_NO = "INVALID_PR_NO";
	public static final String PO_STATUS_WS_MSG_INVALID_PRODUCT_CODE = "Invalid Product Code";

	public static final int PO_STATUS_INVOICE_GENERATED = 8;
	public static final int PO_STATUS_INVOICE_CANCELLED = 12;

	public static final int PO_STATUS_DC_CREATED = 7;
	
	public static final int PO_STATUS_REJECTED_SALES = 11;
	
	public static final int PO_STATUS_REJECTED_COMMERCIAL = 13;
	
	public static final int PO_STATUS_REJECTED_FINANCE = 14;

	public static final String PO_STATUS_DC_PENDING = "DC_PENDING";

	public static final String PO_INVOICE_CANCELLED = "INVOICE_CANCELLED";

	public static final int PO_STATUS_DC_CANCELLED_REQ = 15;

	public static final int PO_STATUS_INVOICE_CANCELLED_REQ = 16;

	public static final String PO_STATUS_DC_PROCESSED = "DC_CANCELL_NOT_POSSIBLE";

	public static final int PO_STATUS_DC_CANCELLED = 10;

	public static final String PO_DC_CANCELLED = "DC_CANCELLED";

	
	public static final String DIST_ID_NOT_VALID ="OLM ID Invalid";
	public static final String STB_BELONG_DIST ="STB is not belonging to this distributor";
	public static final String STB_BELONG_TO_CORRECT_PROD ="STB is not belonging to correct Product";
	public static final String STB_TYPE_HDB="Product type Of the given Serial no in DP:";
	public static final String DIST_STB_MAP_WS_NOTUNASSIGNED ="STB is not Unassigned (Secondary sale not done in DP)";
	public static final String STB_NON_SER_STOCK = "Non-Serialized Stock";
	public static final String STB_NON_SER_STOCK_REST = "STB cannot be activated due to non-serialized restriction";
	public static final String STB_SER_NO_REST = "Serialized Stock - No Restriction";
	
	public static final String DP_MAIN_DB_SCHEMA = "dp.maindb.schema";
	
	public static final String DP_REPORT_DB_SCHEMA = "dp.reportdb.schema";

	public static final int PO_STATUS_DISPATCHED = 9;

	//public static final int CHURN_REFURBHISHMENT_AGEING = 730;	//This ageing is in number of days (2 years)
	
	public static final String CHURN_REFURBHISHMENT_AGEING= "churn.refurbhishment.ageing";	//This ageing is in number of days (2 years)

	public static final String DRAFT_DC_MESSAGE = " has been saved as draft and will be sent to Warehouse only after Courier Agency and Docket Number will be entered.";

	public static final String DC_MESSAGE = " has been created successfully";

	public static final int CPE_STATUS_UNASSIGNED = 1;
	
	public static final int CPE_STATUS_RESERVED = 2;
	
	public static final int CPE_STATUS_REPAIRED = 4;

	public static final int CPE_STATUS_ASSIGNED = 3;

	public static final int CPE_STATUS_RESTRICTED = 5;

	public static final String CPE_BLANK_DATA_MSG = "Blank in CPE";

	public static final String PO_STATUS_PROD_QTY_INCORRECT = "Number of Product and PO quantity are not equal";

	public static final String PO_STATUS_PROD_INCORRECT = "Invalid Product code";
	
	

	// For Loggers
	
	public static final String InterSSD_Stock_Transfer_Critical = "InterSSD.Stock.Transfer.Critical";
	 
	public static final String STB_Search_Bulk_Critical = "STB.Search.Bulk.Critical";
	
	public static final String  STB_Bulk_Flush_Out_Critical = "STB.Bulk.Flush.Out.Critical";
	
	public static final String  Shipment_Error_Correction_Critical = "Shipment.Error.Correction.Critical";
	
	public static final String Return_To_Fse_Critical = "Return.To.FSE.Critical";
	
	public static final String CPE_Recovery_Critical = "CPE.Recovery.Critical";
	
	public static final String  Excess_Stock_Full_Final_Critical = "Excess.Stock.Full.Final.Critical";
	
	public static final String  PO_Stock_Acceptance_MissingDC_Critical = "PO.Stock.Acceptance.MissingDC.Critical";
	
	public static final String PO_Stock_Acceptance_Upload_Critical ="PO.Stock.Acceptance.Upload.Critical";
	
	public static final String PO_FLOW_Critical = "PO.FLOW.Critical";
	
	public static final String PASSWORD_EXPIRING_DAYS = "Password.Expiring.Days";
	
	public static final String DAYS_REMAIN_FOR_PSSWD_CHANGE = "Days.Remain.For.Psswd.Change";
	
	public static final String HRMS_ACTIVE_STATUS = "ACTIVE";

	public static final String HRMS_OLM_ID_NOT_EXISTS = "hrms.olm.id.not.exists";

	public static final String HRMS_OLM_ID_INACTIVE = "hrms.olm.id.inactive";

	public static final String DP_HRMS_SUCCESS_MSG = "VALID";

	public static final String DC_TYPE_SWAP_DOA = "SWAP_DOA";

	public static final int DC_TYPE_SWAP_DAO_CONFIG_ID = 21;

	public static final String DC_TYPE_SWAP_DAO_PREFIX = "SDC";;
	
	public static final String DC_ALREADY_DISPATCHED = "dc.already.dispatched";
	
	public static final String PRODUCT_CATEGORY_CPE="1";

	public static final int DISTRIBUTOR_TYPE_SALES = 1;
	public static final String TRANSFER_HIERARCHY_MSG = "TRANS_HIER";

	public static final String TRANS_HIER_CHILD_DETAILS = "TRANS_HIER_CHILD_DETAILS";
	
	public static final String TRANS_HIER_PARENT_DETAILS = "TRANS_HIER_PARENT_DETAILS";
	
	public static final int DISTRIBUTOR_TYPE_DEPOSIT = 2;

	public static final String AV_UPLOAD_COUNT_MAX = "av.upload.count.max";
	public static final String ERROR_PRODUCT_NOT_PROCESSED = "Product is not created yet, in Warehouse, please wait";

	public static final String AV_RESTICTION_MSG_NO_SEC_SALE = "No secondary sale done in DP for this AV serial number";

	public static final String AV_RESTICTION_MSG_NOT_IN_DP = "AV serial number does not exist in DP";


	public static final String REPLACE_PLACEHOLDER = "QUERY_APPEND";


	public static final int REPORT_ID_VIEW_HIERARCHY_FNF = 1;

	// Closing Stock Constants
	public static final int CLOSING_SERIALIZED_STOCK = 1;  
	public static final int CLOSING_DEFECTIVE_STOCK = 2;
	public static final int CLOSING_UPGRADE_STOCK = 3;
	public static final int CLOSING_CHURN_STOCK = 4;
	public static final String CLOSING_DEFAULT_SERIAL_NO = "11111111111";
	// Constants added for BFR 2 stock eligibility
	public static final String NORMAL = "NORMAL";  
	public static final String HD = "HD";
	public static final String DVR = "DVR";
	public static final String HD_DVR = "HD DVR";
	public static final String SD_Plus = "SD Plus";
	
	public static final String HDB="HDB";
	public static final String DOA=":DOA";
	
	
	//Constants for PAYOUT
	
	public static final double MF=100.0;
	public static final double RF=1.0;
	public static final String SR_SLA="SR SLA Reward";
	public static final String CPE_REC="CPE Recovery";
	public static final String RET="Retention";
	public static final String PTR="PTR";
	public static final String OLM_ID="OLM ID";
	public static final String PARTN_NM="Partner Name";
	public static final String TOTAL_INV_AM="Total Invoice Amt.";
	public static final String SERV_TX="Service Tax";
	public static final String SHEET_NM="Invoice Format2";
	public static final String OTHER="Others";
	public static final String OTHER_1="Others1";
}
