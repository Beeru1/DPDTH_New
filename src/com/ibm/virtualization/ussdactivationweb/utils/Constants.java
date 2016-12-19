
/**************************************************************************
 * File     : SubscriberAction.java
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
package com.ibm.virtualization.ussdactivationweb.utils;

/**
 * This class stores constants used in FTA APP.
 * @author Ashad
 *
 */


public   class Constants {
		


		/**
		 * Automatically generated constructor: Constants
		 */
		public Constants() {}
		
		/*************************FOR User ***********************/
		public  static final String LoginAttempted = "0";
		public  static final String InitialState = "I";
		public  static final int FIRST_TIME_LOGIN =0;
		public static final String RELEASED="RELEASED";
		public static final String INSTALLED="INSTALLED";
		public static final String VERIFIED="VERIFIED";
		public static final String REGISTERED="REGISTERED";
		public static final String ACTIVATED="ACTIVATED";
		public static final String REPROVISIONED="REPROVISIONED";
		public static final String DELETED="DELETED";
		/*************************FOR Bulk ***********************/
		public  static final String FILE_STATUS_SAVED = "SAVED";
		public  static final String TRUE = "TRUE";
		public  static final String FALSE = "FALSE";
		public  static final String FILE_STATUS_FAILED = "FAILED";
		public  static final String FILE_STATUS_FAILED_FILE = "FAILED FILE";
		public  static final String FILE_FAILED_MSG_SAVED = "File Saved";
		public  static final String FILE_FAILED_MSG_FAILED = "File Failed";
		public  static final String FILE_STATUS_UPLOADING = "UPLOADING";
		public  static final String FILE_STATUS_FILEUPLOADED = "FILE UPLOADED";
		public  static final String STATUS_UPDATED = "TRUE";
		public  static final String STATUS_NOT_UPDATED = "FALSE";
		public  static final String MAX_THREAD_COUNT = "fileUpload.maxThreadCount";
		public  static final String BulkAssociation = "1";
		public  static final String BulkSubscriber = "2";
		public  static final int FILE_TYPE_FAILED_FILE = 2;
		public  static final int FILE_TYPE_ORIGINAL_FILE = 1;
		public  static final int FILE_TYPE_BULK_BIZ_USER_FILE = 1100;		
		public  static final String SEARCH_STATUS_ALL = "0";
		public  static final String SEARCH_STATUS_SAVED = "1";
		public  static final String SEARCH_STATUS_UPLOADING = "2";
		public  static final String SEARCH_STATUS_UPLOADED = "3";
		public  static final String SEARCH_STATUS_FAILED = "4";
		//used in mapping from resources
		public  static final String code_not_in_proper_format = "code.not.in.proper.format";
		public  static final String not_desired_type = "not.desired.type";
		public  static final String status_inactive = "status.inactive";
		public  static final String allready_mapped = "allready.mapped";
		public  static final String different_loaction = "different.loaction";
		public  static final String different_city = "different.city";
		public  static final String different_zone = "different.zone";
		public  static final String different_circle = "different.circle";
		public  static final String different_hub = "different.hub";
		
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

		/*************************FOR Track subscriber ***********************/
		public  static final int Registration = 8;
		public  static final int Activation = 9;
		public  static final int Verification = 51;
		public static final String ActiveTran = "S";
		public static final String InActiveTrans = "I";
		public static final String InFailedTrans = "F";
		
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
		public static final String STR_FOS ="FOS";
		public static final String STR_DIST ="DISTRIBUTOR";
		public static final String STR_TM ="TM";
		public  static final String USER_ROLE= "USER_ROLE";
		
		public  static final String hubList= "hubList";
		public  static final String typeOfUserList= "typeOfUserList";
		public  static final String baseLoc= "baseLoc";
		public  static final String cityList= "cityList";
		public  static final String zoneList= "zoneList";
		public  static final String parentList= "parentList";
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
		public  static final String no_city_for_mapped= "no_cities_for_mapped_city";
		public  static final String no_parent= "no_parent";
		public  static final String no_cicles= "no_cicles";
		public  static final String no_zone= "no_zone";
		public  static final String NO_ZONE_FOR_MAPPED= "no_zone_for_mapped_zone";
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
		
		/*************************Pagination in Location*****************/
		public  static final int PAGE_TRUE = 1;
		public  static final int PAGE_FALSE = 0;
		public  static final int PAGE_DROPDOWN = 2;
		
		public static final String CHANGEPASSWORD = "changePassword";
		public static final String GROUP_ID = "GROUP_ID";
		public static final String SESSION_EXPIRED = "Session Expired";
		public static final String STARTDT = "startDt";
		public  static final String ENDDT = "endDt";
		
		public  static final String SubDelFile = "Delete";
		
		public  static final String HUB_CODE = "ALL";
		// Stores forward for successful action.
		public static final String SUCCESS = "Success";

		public static final String ERRORLOGID = "errors.loginId.inUse";	
		public static final String ERRORPWD = "errors.pwd";	
		public static final String ERRORAUTOPWD = "errors.auto.pwd";
		public static final String ERRORSTATUS = "errors.status.not.changed";
		public  static final String ERRORUPDATELOCATION= "errors.update.location";
		public  static final String SUCCESSUPDATELOCATION= "success.update.location";
	
	public static final String SUCCESS1 ="Success";
//	Stores forward for unsuccessful action.
	public static final String  FAILURE = "failure";
	public static final String FAILURE1 = "Failure";

	// Stores mapping for USSDLogin
	public static final String LOGININIT= "/Login";	
	
	//Stores Mapping for USSDLoginSubmit
	public static final String LOGINSUBMIT= "/LoginSubmit";
	
	//Stores mapping for USSDReLogin
	public static final String RELOGIN= "/ReLogin";
	
	//Stores mapping for USSDLogout
	public static final String LOGOUT= "/Logout";
	
	//Stores mapping for insert
	public static final String INSERT = "insert";
	
	//Stores 0
	public static final String ZERO = "0";
	
	//Stores mapping for forward
	public static final String FORWARD = "forward";
	
	//Stores mapping for update
	public static final String UPDATE = "update";
	
	public static final String MESSAGE = "message";
	
	public static final String USER_INFO = "USER_INFO";
	
	public static final String USER_TYPE = "USER_TYPE";
	
	public static final String CIRCLE_LIST = "circleList";
	
	public static final String INIT= "INIT";
	
	public static final String FSO_LIST= "FSOList";
	
	public static final String INIT_SEARCH_FSO="INITSEARCHFSO";
	
	public static final String INSERTED="INSERTED";
	
	public static final String SUPER_ADMIN="SuperAdmin";
	
	public static final String CIRCLE_ADMIN="CircleAdmin";
	public static final String Zone_User="ZoneUser";

	public static final String Retailer_User="RetailerHelpDisk"	;
	
	public static final String CIRCLE_ADMIN_USERBEAN="CA";
	
	public static final String Zone_User_USERBEAN="ZU";
	
	public static final String ERROR="ERROR";
	
	public static final String ERRORS="ERRORS";
	
	public static final String ERROR_KEY="ErrorKey";
	
	public static final String DATABASE_ERROR="Database Exception is : ";
	
	public static final String EXCEPTION="Exception has occured :";
	
	public static final String SQL_EXCEPTION = "SQLException:"; 
	
	
	public static final String GENERAL_ERROR="general.error";
	
	public static final String USER_ERROR="No.User.Found.mapped";
	
	
	public static final String DIST_LIST="distList";
	
	public static final String FILE_STORAGE_PATH="bulk.file.storage.path";
	
	public static final String FILE_UPLOAD_BATCH_SIZE="bulk.file.batch.size";
	
	public static final String INVALID_FILE="error.invalid.file";
	
	public static final String MAX_FILE_SIZE="bulk.max.file.size";
	
	public static final String MAX_FILE_SIZE_BU="bulk.max.file.size.bu";
	
	public static final String SAVE="Saved";
	
	public static final String EXCEPTION_E="Exception";
	
	public static final String RESULT_NOT_FOUND="RESULT_NOT_FOUND";
	
	public static final String STATUS="STATUS";
	
	public static final String FILE_ID="FILE_ID";
	
	public static final String INITIALIZE = "Initialize";
	
	public static final String DISTRIBUTORID = "DistributorId";
	
	public static final String PAGE = "page1";
	
	public static final String SEARCH_PAGE= "SearchPage";
	
	public static final String DISTCODE ="DISTCODE";
	public static final String DISTNAME ="DISTNAME";
	public static final String NEXT ="NEXT";
	public static final String PRE ="PRE";
	public static final String PAGES = "PAGES";
	public static final String TYPE = "TYPE";
	public static final String CODE = "CODE";
	public static final String NAME = "NAME"; 
	public static final String NAME1 = "name";
	public static final String SERCH_DIST_BASIS = "searchDistBasis";
	public static final String SERCH_FOS_BASIS = "searchFSOBasis";
	public static final String DEALERLIST = "dealerList";
	public static final String INSERT_CAP = "Insert";
	public static final String INSERT_CAP1 = "Insert";
	public static final String FSO_NAME ="FSONAME";
	public static final String FSO_CODE ="FSOCODE";
	public static final String DISTRIBITORLIST = "distributorList";
	public static final String CIRCLECODE = "circleCode";
	
	public static final String REGNO = "regNumber.SQLException";
	public static final String  SUB_DAILY= "SubsReportInfo.reportType.Daily.D";
	public static final String  SUB_MONTHLY= "SubsReportInfo.reportType.Monthly.M";
	public static final String  DAILY= "MisReportInfo.reportType.Daily.D";
	public static final String  MONTHLY= "MisReportInfo.reportType.Monthly.M";
	public static final String  DFD ="MisReportInfo.reportType.DataFeed.DFD.F";
	public static final String  PENDING ="MisReportInfo.reportType.Daily.P";
	
	/* CONSTANTS FOR PAGINATION */
	public static final String PAGINATION_RECORDS_PER_PAGE ="pagination.records.perpage" ;
	public static final String PAGINATION_LINKS_PER_PAGE ="pagination.links.perpage" ;
	
	
	public static final String LOWER_BOUND_REPORT ="lowerBound.for.report" ;
	public static final String UPPER_BOUND_REPORT ="upperBound.for.report" ;
	public static final String BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE="com.ibm.virtualization.ussdactivationweb.resources.BulkUpload";
	public static final String WEB_APPLICATION_RESOURCE_BUNDLE = "com.ibm.virtualization.ussdactivationweb.resources.FTAResources";
	public static final String INIT_DETAILED_MIS_REPORT = "INIT_DETAILED_MIS_REPORT";
	public static final String DETAILED_REPORT_TYPE="MIS.Detailed.Report.type";
	public static final String MIS_REPORT_LAST_SEVEN_DAYS="MisReport.Last.Seven.Days";
	public static final String DAILY_REPORT_PREVIOUS_DAYS="Daily.Report.Previous.Days";
	public static final String MESSAGE_SUB="message";
	public static final String DAILY_REPORT_INCLUDE_CURRENT_DAY="Daily.Report.Include.Current.Day";
	public static final String MONTHLY_REPORT_PREVIOUS_DAYS="Monthly.Report.Previous.Days";
	public static final String MONTHLY_REPORT_INCLUDE_CURRENT_DAY="Monthly.Report.Include.Current.Month";

	public static final String KEY_MIS_REPORT_FILE_STORAGE_PATH = "MIS.file.storage.path";
	public static final String KEY_SUBS_REPORT_FILE_STORAGE_PATH = "SUBS.file.storage.path";
	public static final String DELETED_SUB_REPORT = "deleted.subscribers.report";
	public static final String CSV_FILE_EXTENSION="reportMIS.file.postfix";
	public static final String MIS_REPORT_GENERATION_DAILY_ZONE_WISE="Mis.Report.Generation.Daily.Zone.Wise";
	public static final String MIS_REPORT_GENERATION_DAILY_CITY_WISE="Mis.Report.Generation.Daily.City.Wise";
	public static final String MIS_REPORT_GENERATION_DAILY_DISTRIBUTER_WISE="Mis.Report.Generation.Daily.Distributer.Wise";
	public static final String MIS_REPORT_GENERATION_DAILY_TM_WISE="Mis.Report.Generation.Daily.TM.Wise";
	public static final String MIS_REPORT_GENERATION_DAILY_DEALER_WISE="Mis.Report.Generation.Daily.Dealer.Wise";
	public static final String MIS_REPORT_GENERATION_DAILY_DETAILED_WISE="Mis.Report.Generation.Daily.Detailed.Wise";
	public static final String MIS_REPORT_GENERATION_MONTHLY_ZONE_WISE="Mis.Report.Generation.Monthly.Zone.Wise";
	public static final String MIS_REPORT_GENERATION_MONTHLY_CITY_WISE="Mis.Report.Generation.Monthly.City.Wise";
	public static final String MIS_REPORT_GENERATION_MONTHLY_DISTRIBUTER_WISE="Mis.Report.Generation.Monthly.Distributer.Wise";
	public static final String MIS_REPORT_GENERATION_MONTHLY_TM_WISE="Mis.Report.Generation.Monthly.TM.Wise";
	public static final String MIS_REPORT_GENERATION_MONTHLY_DEALER_WISE="Mis.Report.Generation.Monthly.Dealer.Wise";
	public static final String MIS_REPORT_FATCH_SIZE ="Mis.Report.Fatch.Size";
	public static final String SUBS_REPORT_FATCH_SIZE="Subs.Report.Batch.Size";
	public static final String REPORT_RUN_INTERVAL ="Report.Run.Interval";
	public static final String SUBSCRIBER_RELEASE_RUN_INTERVAL ="sub.release.Run.Interval";
	public static final String PROC_UDATE_REPORT_NEXT_SCHEDULE_DATE = "Proc.Udate.Report.Next.Schedule.Date";
	public static final String INSERT_V_REPORT_DETAILS ="Insert.V.Report.Details";
	public static final String RELESE_SUBSCRIBER_PROC ="Release.subscriber";
	public static final String INSERT_V_REPORT_SUMMARY ="Insert.V.Report.Summary";
	public static final String MIS_PENDING_REPORT_GENERATION_DAILY_WISE="Mis.Pending.Report.Generation.Daily.Wise";
	public static final String RELESE_SUBSCRIBER_TIME ="Release.subscriber.time";
	
	public static final String Error_Code1 ="error.code1";
	public static final String Error_Code2="error.code2";
	public static final String Error_Code3="error.code3";
	public static final String Reg_No_Blank="RegNo.Blank";
	public static final String No_REQ_FOR_RGENO="No.requetser.for.register.number";
	public static final String ACTIVE_CIRCLE="No.circle.active";
	public static final String ParentId_Blank="ParentId.Blank";
	public static final String No_REQ_FOR_DATAID="No.requester.for.id";
	public static final String ServiceClass_Blank="ServiceClass.blank";
	public static final String No_ServiceClass="No.ServiceClass";
	public static final String CircleCode_blank="CircleCode.blank";
	public static final String No_Circle="No.Circle";
	public static final String LocId_Blank="LocId.Blank";
	public static final String No_Location="No.Location";
	public static final String Date_Blank="Either.blank";
	public static final String No_BizUser="No.BizUser";
	
	
	/*******************  SMS Report **********************/
	public static final String SMS_PULL_REPORT_SERVLET_NAME="SMS_PULL_REPORT_SERVLET_NAME";
	public static final String SMS ="SMS";
	public static final String ALL_SMS_REPORTS = "ALL_SMS_REPORTS";
	public static final String ACT_DONE_AT_DIST_FOR_FTD_FORMAT = "ACT_DONE_AT_DIST_FOR_FTD_FORMAT";
	public static final String ACT_STATUS_FOR_SINGLE_SUB_FORMAT = "ACT_STATUS_FOR_SINGLE_SUB_FORMAT";
	public static final String HUB_WISE_ACT_COUNT_MTD_FORMAT = "HUB_WISE_ACT_COUNT_MTD_FORMAT";
	public static final String HUB_WISE_ACT_COUNT_FOR_FTD_FORMAT = "HUB_WISE_ACT_COUNT_FOR_FTD_FORMAT";
	public static final String CIRCLE_WISE_ACT_COUNT_MTD_FORMAT = "CIRCLE_WISE_ACT_COUNT_MTD_FORMAT";
	public static final String CIRCLE_WISE_ACT_COUNT_FOR_FTD_FORMAT = "CIRCLE_WISE_ACT_COUNT_FOR_FTD_FORMAT";
	public static final String DIST_WISE_ACT_COUNT_FOR_FTD_FORMAT = "DIST_WISE_ACT_COUNT_FOR_FTD_FORMAT";
	public static final String DIST_WISE_ACT_COUNT_MTD_FORMAT = "DIST_WISE_ACT_COUNT_MTD_FORMAT";
	public static final String DEALER_WISE_ACT_COUNT_FOR_FTD_FORMAT = "DEALER_WISE_ACT_COUNT_FOR_FTD_FORMAT";
	public static final String DEALER_WISE_ACT_COUNT_MTD_FORMAT = "DEALER_WISE_ACT_COUNT_MTD_FORMAT";
	public static final String HBO_WISE_ACT_COUNT_FOR_FTD_FORMAT = "HBO_WISE_ACT_COUNT_FOR_FTD_FORMAT";
	
	
	public static final String ACT_PENDING_AT_DIST_FOR_FTD ="ACT_PENDING_AT_DIST_FOR_FTD";
	public static final String ACT_DONE_AT_DIST_FOR_FTD="ACT_DONE_AT_DIST_FOR_FTD";
	public static final String ACT_STATUS_FOR_SINGLE_SUB="ACT_STATUS_FOR_SINGLE_SUB";
	public static final String HUB_WISE_ACT_COUNT_MTD="HUB_WISE_ACT_COUNT_MTD";
	public static final String HUB_WISE_ACT_COUNT_FOR_FTD="HUB_WISE_ACT_COUNT_FOR_FTD";
	public static final String CIRCLE_WISE_ACT_COUNT_MTD="CIRCLE_WISE_ACT_COUNT_MTD";
	public static final String CIRCLE_WISE_ACT_COUNT_FOR_FTD="CIRCLE_WISE_ACT_COUNT_FOR_FTD";
	public static final String DIST_WISE_ACT_COUNT_FOR_FTD="DIST_WISE_ACT_COUNT_FOR_FTD";	
	public static final String DIST_WISE_ACT_COUNT_MTD="DIST_WISE_ACT_COUNT_MTD";
	public static final String DEALER_WISE_ACT_COUNT_FOR_FTD="DEALER_WISE_ACT_COUNT_FOR_FTD";	
	public static final String DEALER_WISE_ACT_COUNT_MTD="DEALER_WISE_ACT_COUNT_MTD";
	public static final String HBO_WISE_ACT_COUNT_FOR_FTD="HBO_WISE_ACT_COUNT_FOR_FTD";	
	public static final String SMS_REPORT_RESOURCE_BUNDLE="com.ibm.virtualization.ussdactivationweb.resources.SMSReport";
	public static final int  DAILY_REPORT = 1; 
	public static final int  MONTHLY_REPORT = 2; 
	public static final int  NO_PERMISSION = -1; 
	public static final String  NO_PERM = "F"; 
	public static final String SUCCESS_REPORT="S";
	public static final String PENDING_REPORT="I";
	public static final String FAILED_REPORT="F";
	public static final String ACTIVATION_TRANS_ID="activation.transaction.code";
	public static final String VERIFICATION_TRANS_ID="verification.transaction.code";
	public static final String REGISTRATION_TRANS_ID="registration.transaction.code";
	public static final String SMS_COMMON_MESAGE = "Error occurred while getting the information. Please try again later or contact the administrator.";
	public static final String SQL_EXC_IN_CONN_CLOSING= "SQLException occur while closing database resources.";
	public static final String IS_SMS_REPORT_FLAG_ON="IS_SMS_REPORT_FLAG_ON";
	
	
	public static final String REPORT_ENGINE_RUN_INTERVAL="report.engine.run.interval";	
	public static final String BULK_UPLOAD_RUN_INTERVAL="bulk.upload.run.interval";	
	public static final String BULK_UPLOAD_EMPTY_FILE="error.empty.file";	
	public static final String BULK_UPLOAD_MAX_FILE_SIZE="error.max.file.size";	
	public static final String BULK_UPLOAD_FILE_UPLOADED="file.uploaded";	
	
	public static final String REPORT_ENGINE_RESOURCE_BUNDLE=WEB_APPLICATION_RESOURCE_BUNDLE;
	public static final String BULK_UPLOAD_RESOURCE_BUNDLE=BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE;
	

	public static final String LOGIN_TYPE_FOR_SUPER_ADMIN="A";
	public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
	public static final int  NO_LOGIN_ATTEMPTS = 3;
	public static final int RESET_NO_LOGIN_ATTEMPTS= 0;
	public static final String SENDING_FROM=  "Administrator";
	public static final String SUBJECT="Your Password Information";
	public static final String SUBJECT_fOR_ACCOUNT="Your User Account Information ";
	public static final String EMAIL_SUBJECT="EMAIL.SUBJECT";
	public static final String EMAIL_RESOURCE_BUNDLE=WEB_APPLICATION_RESOURCE_BUNDLE;
	public static final String EMAIL_MESSAGE="EMAIL.MESSAGE";
	public static final String EMAIL_SUBJECT_FORGORT_PASSWORD="EMAIL.SUBJECT.FORGOT.PASSWORD";
	public static final String EMAIL_MESSAGE_FORGOT_PASSWORD="EMAIL.MESSAGE.FORGOT.MESSAGE";
	public static final String EMAIL_MESSAGE_CHANGE_PASSWORD="EMAIL_MESSAGE.CHANGE.PASSWORD";
	
	public static final String DATA_FEED_PROCESS_START = "S";
	public static final String DATA_FEED_END_PROCESS_END = "E";
	public static final String DATA_FEED_END_PROCESS_N = "N";
	public static final String DATA_FEED_SCHEDULE_PROCESS_F= "F";
	public static final String FORCE_START_PROCESS_Y = "Y";
	public static final String FORCE_START_PROCESS_N = "N";
	public static final String PROC_STATUC_11111 = "11111";
	
	/** Susbscriber Consatnts **/
	public static final String IMSI = "IMSI";
	public static final String MSISDN = "MSISDN";
	public static final String COMPLETE_SIM = "COMPLETE_SIM";
	public static final String SM_ID = "SM_ID";
	public static final String CIRCLE_CODE = "CIRCLE_CODE";
	public static final String MSISDN_MAX_LENGH = "sub.msisdn.max.lenth";
	public static final String MSISDN_MIN_LENGH = "sub.msisdn.min.lenth";
	public static final String SIM_SUBSTRING_INDEX = "sub.sim.substring.index";
	public static final String SIM_MAX_LENGH = "sub.sim.max.lenth";
	public static final String IMSI_MAX_LENGH ="sub.imsi.max.lenth";
	public static final String SUBSCRIBER_INFO_ARRAY_LENGH = "sub.info.array.length";
	public static final String SUBSCRIBER_DEL_INFO_ARRAY_LENGH = "sub.delete.info.array.length";
	public static final String SERVICECLASS_ID_MAX_LENGH = "ServiceClassID.max.length";
	public static final String MSISDN_START_WITH_ZERO = "sub.msisdn.startwith.zero";
	public static final String MSISDN_START_WITH_NINE_ONE = "sub.msisdn.startwith.nineone";
	public static final String MSISDN_START_DIGIT_CHECK_MSG = "sub.msisdn.start.digit.check";
	public static final String SUB_MSISDN_LENGTH_CHECK_MSG = "sub.msisdn.lenth.check";
	public static final String SUB_SIM_LENGTH_CHECK_MSG = "sub.sim.lenth.check";
	public static final String SUB_IMSI_LENGTH_CHECK_MSG = "sub.imsi.lenth.check";
	public static final String SERVICECLASS_ID_LENGTH_CHECK_MSG = "Service.MSG_SERVICECLASS_LENGTH";
	public static final String SERVICECLASS_ID_NOT_EXIST_OR_INACTIVE_MSG = "serviceclass.inactive.or.not.exist.in.circle";
	public static final String IMSI_DUPLICATE="sub.error.msg";
	public static final String MSISDN_OR_SIM_ALREADY_EXIST = "sub.msisdn.sim.already.exist";
	public static final String SERVICECLASS_ID_NOT_EXIST = "serviceclass.not.exist";
	public static final String MSISDN_SIM_FORMAT_INCORRECT = "msisdn.sim.format.incorrec";
	public static final String SERVICECLASS_ID_FORMAT_INCORRECT = "serviceclass.id.format.incorrect";
	public static final String MSISDN_SIM_SERVICECLASS_ID_BLANK_MSG="msisdn.sim.serviceclass.id.blank.msg";
	public static final String MSISDN_SIM_SERVICECLASS_ID_FORMAT_MSG ="msisdn.sim.serviceclass.format.incorrect.msg";
	public static final String PROC_CREATE_SUBSCEIBER = "Proc.Create.Subscriber";
	
	public static final String SUBSCRIBER_REPORT_DAYS = "Subscriber.Report.Days";
	
	/************************************** Bulk Business User Creation **********************************************/
	public static final String BIZ_USER_CREATION_FILE_TYPE = "Business.User.Creation.File.Type";
	public static final String BIZ_USER_CREATION_RESOURCE_BUNDLE="com.ibm.virtualization.ussdactivationweb.resources.BulkUserCreation";
	public static final String BIZ_USER_CREATION_FILE_UPLOAD_BATCH_SIZE="Business.User.Creation.File.size";
	public static final String BIZ_USER_CREATION_FILE_STORAGE_PATH="Business.User.Creation.File.Storage.Path";
	public static final String BIZ_USER_INFO_ARRAY_LENGTH = "Business.User.Creation.Info.Array.Length";
	public static final String BIZ_USER_DISTRIBUTOR_INFO_ARRAY_LENGTH = "Business.User.Creation.Distributor.Info.Array.Length";
	public static final String BIZ_USER_CREATION_FORMAT_MSG ="Business.User.Creation.Format.Incorrect.Msg";
	public static final String BIZ_USER_DISTRIBUTOR_CREATION_BLANK_MSG="Business.User.Creation.Distributor.Blank.Msg";
	public static final String BIZ_USER_DISTRIBUTOR_CREATION_BLANK_SERVICE_CLASS_ID_MSG="Business.User.Creation.Distributor.Service.Class.Blank.Msg";
	public static final String BIZ_USER_CREATION_BLANK_MSG="Business.User.Creation.Blank.Msg";
	public static final String BIZ_USER_NAME_MIN_LENGTH="Business.User.Minimum.Length";
	public static final String BIZ_USER_NAME_MAX_LENGTH="Business.User.Maximum.Length";
	public static final String BIZ_USER_NAME_LENGTH_LESS_MSG="Business.User.Length.Less.Msg";
	public static final String BIZ_USER_NAME_LENGTH_MORE_MSG="Business.User.Length.More.Msg";
	public static final String BIZ_USER_CONTACT_NO_START_WITH_ZERO = "Business.User.ContactNo.Start.Zero";
	public static final String BIZ_USER_CONTACT_NO_START_WITH_NINE_ONE = "Business.User.ContactNo.Start.Nine.One";
	public static final String BIZ_USER_CONTACT_NO_START_DIGIT_CHECK_MSG = "Business.User.ContactNo.Start.Digit.Check";
	public static final String BIZ_USER_REG_NO_START_WITH_ZERO = "Business.User.RegNo.Start.Zero";
	public static final String BIZ_USER_REG_NO_START_WITH_NINE_ONE = "Business.User.RegNo.Start.Nine.One";
	public static final String BIZ_USER_REG_NO_START_DIGIT_CHECK_MSG = "Business.User.RegNo.Start.Digit.Check";
	public static final String BIZ_USER_CODE_LENGTH="Business.User.Code.Length";
	public static final String BIZ_USER_CODE_LENGTH_MSG="Business.User.Code.Length.Check";
	public static final String BIZ_USER_CODE_TYPE_MSG="Business.User.Code.Type.Check";
	public static final String BIZ_USER_SERVICE_CLASS_TYPE_LENGTH="Business.User.Service.Class.Type.Length";
	public static final String BIZ_USER_SERVICE_CLASS_TYPE_LENGTH_MSG="Business.User.Service.Class.Type.Check";
	public static final String BIZ_USER_INCLUDE_SERVICE_CLASS_TYPE="Business.User.Include.Service.Class.Type";
	public static final String BIZ_USER_EXCLUDE_SERVICE_CLASS_TYPE="Business.User.Exclude.Service.Class.Type";
	public static final String BIZ_USER_ALL_SERVICE_CLASS_TYPE="Business.User.All.Service.Class.Type";
	public static final String BIZ_USER_SERVICE_CLASS_VALUE_TYPE_MSG="Business.User.Service.Class.Value.Type.Check";
	public static final String BIZ_USER_ALL_SERVICE_CLASS_FLAG_YES="Business.User.All.Service.Class.Yes";
	public static final String BIZ_USER_ALL_SERVICE_CLASS_FLAG_NO="Business.User.All.Service.Class.No";
	public static final String BIZ_USER_SERVICE_CLASS_TOTAL_LENGTH="Business.User.Service.Class.Total.Length";
	public static final String BIZ_USER_SERVICE_CLASS_TOTAL_LENGTH_MSG="Business.User.Service.Class.Total.Length.Check";
	public static final String BIZ_USER_SERVICE_CLASS_LENGTH="Business.User.Service.Class.Length";
	public static final String BIZ_USER_SERVICE_CLASS_LENGTH_MSG="Business.User.Service.Class.Length.Check";
	public static final String BIZ_USER_SERVICE_CLASS_NOT_EXISTS="Business.User.Service.Class.Exists.Check";
	public static final String BIZ_USER_REG_NO_EXISTS="Business.User.Registration.No.Exists.Check";
	
	
	public static final String BULK_UPLOAD_BIZ_USER_CREATION_RUN_INTERVAL="Business.User.Bulk.Upload.Run.Interval";
	public static final String BULK_BIZ_USER_LEVEL_CIRCLE="Circle";
	public static final String BULK_BIZ_USER_LEVEL_ZONE="Zone";
	public static final String BULK_BIZ_USER_LEVEL_CITY="City";
	public static final String BULK_BIZ_USER_LEVEL_RETAILER="Retailer";
	public static final String BULK_BIZ_USER_LEVEL_NONE="None";
	public static final String BULK_BIZ_USER_LEVEL_ATTRIBUTE="selectedRoleType";
	public static final String BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE="selectedRoleRetailer";
	
	public static final String BULK_BIZ_USER_REPORT_ROLE_ATTRIBUTE="reportRoleColumn";
	public static final String BULK_BIZ_USER_REPORT_ROLE_YES_ATTRIBUTE="reportRoleColumnYes";
	
	
	public static final String AUTHENTICATED_USER = "USER_INFO";
	

}
