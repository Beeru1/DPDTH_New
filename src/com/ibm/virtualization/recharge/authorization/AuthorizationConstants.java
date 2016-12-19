/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.authorization;

import java.util.List;

/**
 * The AuthorizationConstants class, holds all constants such as roles for
 * Authrization Framework
 * 
 * @author Rohit Dhall
 * @date 07-Sep-2007
 * 
 */

public final class AuthorizationConstants {
	// ****************** ROLE Definition *******************

	// Role to perform ADD CIRCLE
	public static final String ROLE_ADD_CIRCLE = "ROLE_ADD_CIRCLE";
	
	public static final String ROLE_CONFIRM_FNF = "ROLE_CONFIRM_FNF";
	
	public static final String ROLE_APPROVE_FNF = "ROLE_APPROVE_FNF";
	
	public static final String ROLE_DISTRIBUTOR_PINCODE_MAPPING = "ROLE_DISTRIBUTOR_PINCODE_MAPPING";
	
		
	public static final String ROLE_INITIATE_FNF = "ROLE_INITIATE_FNF";
	
	public static final String ROLE_AD = "ROLE_AD";
   //Role to perform view circle list or view details
	public static final String ROLE_VIEW_CIRCLE = "ROLE_VIEW_CIRCLE";
	
	//Role to perform view circle list
	public static final String ROLE_ADD_USER = "ROLE_ADD_USER";
		
	//Role to perform view user list or view details
	public static final String ROLE_VIEW_USER= "ROLE_VIEW_USER";
	
	//Role to perform ADD GROUP
	public static final String ROLE_CREATE_GROUP= "ROLE_CREATE_GROUP";
	
   //Role to perform view GROUP list or view details
	public static final String ROLE_VIEW_GROUP= "ROLE_VIEW_GROUP";

	//Role to perform GROUP_ROLE_MAPPING
	public static final String ROLE_VIEW_GRP_ROLE_MAPPING= "ROLE_VIEW_GRP_ROLE_MAPPING";
	
	//Role to perform CREATE SYSTEM CONFIG
	public static final String ROLE_CREATE_SYS_CONFIG= "ROLE_CREATE_SYS_CONFIG";
	
	//Role to perform view  system config list
	public static final String ROLE_VIEW_SYS_CONFIG= "ROLE_VIEW_SYS_CONFIG";
	
	
	//Role to perform CREATE SLAB CONFIG
	public static final String ROLE_CREATE_SLAB_CONFIG= "ROLE_CREATE_SLAB_CONFIG";
	
	//Role to perform view  slab config list or view details
	public static final String ROLE_VIEW_SLAB_CONFIG= "ROLE_VIEW_SLAB_CONFIG";
	
	
	//Role to perform view  account  list or view account details
	public static final String ROLE_VIEW_ACCOUNT= "ROLE_VIEW_ACCOUNT";
	
	public static final String ROLE_VIEW_ACCOUNT_DIST= "ROLE_ACCOUNT_DIST";
	
	//Role to perform create circle to distributor transaction  
	public static final String ROLE_CREATE_TRAN= "ROLE_CREATE_TRAN";
	
	//Role to perform view  distributor transaction  list or view transaction details
	public static final String ROLE_VIEW_TRAN= "ROLE_VIEW_TRAN";
	
	//Role to perform view account to account transaction   
	public static final String ROLE_VIEW_A2A_TRANSACTIONS= "ROLE_VIEW_A2A_TRANSACTIONS";
	
	public static final String ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT= "ROLE_VIEW_CIRCLE_RECHARGE_TRANSACTION_REPORT";
	
	
	//Role to perform view customer transaction   
	public static final String ROLE_VIEW_CUSTOMER_TRANSACTION= "ROLE_VIEW_CUSTOMER_TRANSACTION";
	
	
  /* //Role to perform change m password    
	public static final String ROLE_CHANGE_M_PASSWORD= "ROLE_CHANGE_M_PASSWORD";
	
	
    //Role to perform change m password    
	public static final String ROLE_CHANGE_I_PASSWORD= "ROLE_CHANGE_I_PASSWORD";*/
	
	
   //Role to view  account to account transaction report  
	public static final String ROLE_VIEW_A2A_TRANSACTION_REPORT= "ROLE_VIEW_A2A_TRANSACTION_REPORT";
	
   //Role to view  account to account summary transaction report  
	public static final String ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT= "ROLE_VIEW_A2A_TRANSFER_SUMMARY_REPORT";
	
   //Role to view  transaction  report  
	public static final String ROLE_VIEW_TRANSACTION_REPORT= "ROLE_VIEW_TRANSACTION_REPORT";
	
	//Role to view  transaction  summary report  
	public static final String ROLE_VIEW_TRANSACTION_SUMMARY_REPORT= "ROLE_VIEW_TRANSACTION_SUMMARY_REPORT";
	
	// Role to perform UPDATE CIRCLE,without any BALANCE update
	public static final String ROLE_UPDATE_CIRCLE_WITHOUT_BALANCE = "ROLE_UPDATE_CIRCLE_WITHOUT_BALANCE";

	// Role to perform UPDATE CIRCLE,ONLY BALANCE update
	public static final String ROLE_UPDATE_CIRCLE_ONLY_BALANCE = "ROLE_UPDATE_CIRCLE_WITH_BALANCE";

	// Role to Add Account.
	public static final String ROLE_ADD_ACCOUNT = "ROLE_ADD_ACCOUNT";
	
	//Roel to add Account by IT Help Desk
	public static final String ROLE_CREATE_ACCOUNT_IT_HELPDESK = "ROLE_CREATE_ACCOUNT_IT_HELPDESK";
	
	public static final String ROLE_VIEW_IT_ACCOUNT = "ROLE_VIEW_IT_ACCOUNT";

	// Role to perfoem update account ,including any balabce detail.
	public static final String ROLE_UPDATE_ACCOUNT_WITH_BALANCE = "ROLE_UPDATE_ACCOUNT_WITH_BALANCE";

	// Role to perfoem update account ,excluding any balabce detail.
	public static final String ROLE_UPDATE_ACCOUNT_WITHOUT_BALANCE = "ROLE_UPDATE_ACCOUNT_WITHOUT_BALANCE";

	// Role to retrieve circle details
	public static final String ROLE_RETRIEVE_CIRCLE = "ROLE_RETRIEVE_CIRCLE";

	// Role for self query balance detail
	public static final String ROLE_QUERY_BALANCE = "ROLE_QUERY_BALANCE";

	// Role for child query balance
	public static final String ROLE_QUERY_CHILD_BALANCE = "ROLE_QUERY_CHILD_BALANCE";

	// Role for account to account transfer
	public static final String ROLE_TRANSFER_TO_CHILD_ACC = "ROLE_TRANSFER_TO_CHILD_ACC";

	// Role For Recharge
	public static final String ROLE_CAN_RECHARGE = "ROLE_CAN_RECHARGE";
	
	// Role For Postpaid
	public static final String ROLE_POSTPAID_PAYMENT = "ROLE_POSTPAID_PAYMENT";

	// ****************** GROUP Definition *******************

	// SYS_ADM group
	public static final String GROUP_SYS_ADMN = "SYS_ADMN";

	// CIRCLE_ADMN group
	public static final String GROUP_CIRCLE_ADMN = "CIRCLE_ADMN";

	// ACC_USR group
	public static final String GROUP_ACC_USR = "ACC_USR";

	// ACC_ADMN group
	public static final String GROUP_ACC_ADMN = "ACC_ADMN";

	// CIRCLE_USER group
	public static final String GROUP_CIRCLE_USER = "CIRCLE_USER";

	// FIN_ADMN group
	public static final String GROUP_FIN_ADMN = "FIN_ADMN";

	// Role for Group
	public static final String ROLE_UPDATE_GROUP = "ROLE_UPDATE_GROUP";
	
	public static final String ROLE_UPDATE_PRODUCT = "ROLE_UPDATE_PRODUCT";

	public static final String GROUP_VIEW_GRP_ROLE_MAPPING = "GROUP_VIEW_GRP_ROLE_MAPPING";
	
	public static final String ROLE_EDIT_CIRCLE = "ROLE_EDIT_CIRCLE";
	
	public static final String ROLE_EDIT_ACCOUNT = "ROLE_EDIT_ACCOUNT";
	
	public static final String ROLE_UPDATE_SLAB_CONFIG = "ROLE_UPDATE_SLAB_CONFIG";
	
	public static final String ROLE_UPDATE_SYS_CONFIG = "ROLE_UPDATE_SYS_CONFIG";
	
//	 Role for User
	public static final String ROLE_UPDATE_USER = "ROLE_UPDATE_USER";

	//Role for Transaction
	public static final String ROLE_AUTH_TRAN = "ROLE_AUTH_TRAN";
	
    public static final String RESET_AUTHORIZED = "Y";
	
	public static final String RESET_UNAUTHORIZED = "N"; 

	public static final String ROLE_RESET_I_PASSWORD="ROLE_RESET_I_PASSWORD";
	
	public static final String ROLE_UNLOCK_USER="ROLE_UNLOCK_USER";
	
	public static final String ROLE_VIEW_AGGREGATE="ROLE_VIEW_AGGREGATE";
	
	public static final String ROLE_ADD_BEAT = "ROLE_ADD_BEAT";
	
	public static final String ACCOUNT_MOVEMENT_MAPPING = "ACCOUNT_MOVEMENT_MAPPING";
	
	public static final String ROLE_AGGREGATE_ACCOUNT = "ROLE_AGGREGATE_ACCOUNT";
	
	public static final String ROLE_VIEW_BEAT = "ROLE_VIEW_BEAT";
	
	public static final String ROLE_EDIT_BEAT = "ROLE_EDIT_BEAT";
	
//	 Role to perform ADD GEOGRAPHY
	public static final String ROLE_ADD_GEOGRAPHY = "ROLE_ADD_GEOGRAPHY";
	public static final String ROLE_EDIT_GEOGRAPHY ="ROLE_EDIT_GEOGRAPHY";
	public static final String  ROLE_VIEW_GEOGRAPHY = "ROLE_VIEW_GEOGRAPHY";
	
// Role to perform Add configuration detail
	public static final String  ROLE_ADD_CONFIGURATION = "ROLE_ADD_CONFIGURATION";
	
	public static final String ROLE_VIEW_ERROR_PO = "ROLE_VIEW_ERROR_PO";
	
	public static final String ROLE_STB_FLUSHOUT = "ROLE_STB_FLUSHOUT";
	
//--------------------------Added by Nazim Hussain----------------------------------------------------------------
//	Role to Accept Delivery Challan
	public static final String ROLE_DC_ACCEPTANCE = "ROLE_DC_ACCEPTANCE";
	
//	Role to Approve Missing Stock
	public static final String ROLE_MISSING_STOCK_APPROVAL = "ROLE_MISSING_STOCK_APPROVAL";
	
//	Role to Transfer Distributor Stock
	public static final String ROLE_DISTRIBUTOR_STOCK_TRANSFER = "ROLE_DISTRIBUTOR_STOCK_TRANSFER";
	
//	Role to Deplete Open Stock
	public static final String ROLE_OPEN_DEPLETION = "ROLE_OPEN_DEPLETION";
	
//	Role to Hierarchy Accept
	public static final String ROLE_HIERARCHY_ACCEPT = "ROLE_HIERARCHY_ACCEPT";
	
//--------------------------Added by Nazim Hussain ends here------------------------------------------------------
	public static final String ROLE_CREATE_ID_HELPDESK ="ROLE_CREATE_ID_HELPDESK";
	
	public static final String ROLE_VIEW_ID_HELPDESK ="ROLE_VIEW_ID_HELPDESK";

	public static final String ROLE_TO_HIERARCHY_TRANSFER = "ROLE_TO_HIERARCHY_TRANSFER";

	public static final String ROLE_UPLOAD_AVSTOCK = "ROLE_UPLOAD_AVSTOCK";

	public static final String ROLE_DIST_UPLOAD_AVSTOCK = "ROLE_DIST_UPLOAD_AVSTOCK";
	
	public static final String ROLE_DIST_UPLOAD_NSERSTOCK = "NSER_TO_SER_STOCK_CONVERSION";

	public static final String ROLE_VIEW_STOCK_ELIGIBILITY = "ROLE_VIEW_STOCK_ELIGIBILITY";
	public static final String ROLE_VIEW_DIST_ELIGIBILITY = "ROLE_VIEW_DIST_ELIGIBILITY"; //Neetika for eligibility enh
	public static final String  ROLE_STB_IN_BULK="ROLE_STB_IN_BULK";
	public static final String ROLE_RECO_PERIOD_CONF="ROLE_RECO_PERIOD_CONF";
	public static final String ROLE_NSR_BULK_UPLOAD="ROLE_NSR_BULK_UPLOAD";
	public static final String ROLE_Transfer_Pending_List="ROLE_Transfer_Pending_List";
	public static final String ROLE_CREATE_VIEW_PRODUCT="ROLE_CREATE_VIEW_PRODUCT";
	public static final String ROLE_ALERT_CONFIGURATION_ADMIN="ROLE_ALERT_CONFIGURATION_ADMIN";
	public static final String ROLE_ALERT_CONFIGURATION="ROLE_ALERT_CONFIGURATION";
	public static final String ROLE_ACCOUNT_DETAIL_REPORT="ROLE_ACCOUNT_DETAIL_REPORT";
	public static final String ROLE_ACCOUNT_MANAGE_REPORT="ROLE TO GENERATE ACCOUNT MANAGEMENT ACTIVITY REPORT";
	public static final String ROLE_ACTIVATION_DETAIL_REPORT="ROLE_ACTIVATION_DETAIL_REPORT";
	public static final String ROLE_CHURN_DC_GENERATION_DIST="ROLE_CHURN_DC_GENERATION_DIST";
	public static final String ROLE_CIRCLE_ACTIVATION_SUMMARY_REPORT="ROLE_CIRCLE_ACTIVATION_SUMMARY_REPORT";
	public static final String ROLE_COLLECTION_DETAIL_REPORT="ROLE_COLLECTION_DETAIL_REPORT";
	public static final String ROLE_NONSERIALIZED_CONSUMPTION_REPORT_DETAILS="ROLE_NONSERIALIZED_CONSUMPTION_REPORT_DETAILS";
	
}


