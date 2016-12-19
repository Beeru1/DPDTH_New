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
 * This Class Contains MessageIds which will be used to fetch Details from
 * VR_MSG_MSTR
 * 
 * @author Paras
 * 
 */
public final class ResponseCode {

	/* Recharge Failure */
	public static String RECHARGE_FAILURE = "R002";

	/* Retailer Mobile Number Not Valid */
	public static String RECHARGE_AUTENTICATION_FAILURE_CODE = "R001";
	/* When Validation Fails */
	public static String  RECHARGE_INVALID_DETAILS="R003";
	/* When Validation Fails */
	public static String  POSTPAID_INVALID_DETAILS="P003";
	/* Database Exception  Created */
	public static String  RECHARGE_DATABASE_ERROR="R004";
	/* Database Exception  Created */
	public static String  POSTPAID_DATABASE_ERROR="P004";
	/* Invalid Amount */
	public static String RECHARGE_INVALID_AMT = "R005";

	/* Invalid Amount */
	public static String POSTPAID_INVALID_AMT="P005";
	/* Recharge Successful */
	public static String RECHARGE_SUCCESS = "0";

	/* System Exption has occured */
	public static String RECHARGE_SYSTEM_ERROR = "R008";

	/* System Exption has occured */
	public static String POSTPAID_SYSTEM_ERROR="P008";
	/* Incase Subscriber number is Invalid */
	/* Incase Subscriber number is Invalid */
	public static String POSTPAID_INVALID_SUBSCRIBER_NO="P102";
	public static String POSTPAID_INVALID_RECEIVING_MOBILENO="P102";
	
	
	public static String RECHARGE_INVALID_SUBSCRIBER_NO = "102";

	/* inative source account */
	public static String INACTIVE_SOURCE_ACCOUNT = "103";

	/* insufficient balance */
	public static String RECHARGE_INSUFFICIENT_BALANCE = "R009";

	/* M Password changed successfully */
	public static String CHANGE_M_PASSWORD_SUCCESS = "C001";
	
	/* M Password failed */
	public static String CHANGE_M_PASSWORD_FAILURE = "C002";

	/* M Password RESET successfully */
	public static String RESET_M_PASSWORD_SUCCESS = "RM001";
	
	/* M Password RESET failed */
	public static String RESET_M_PASSWORD_FAILURE = "RM002";
	
	/* M Password invalid old Password */
	public static String CHANGE_M_PASSWORD_INVALID_OLD = "C003";
	/* insufficient balance*/
	public static String POSTPAID_INSUFFICIENT_BALANCE="P009";
	/* post paid transaction successful */
    public static String POSTPAID_SUCCESS="P010";
    
    /* post paid transaction successful */
    public static String POSTPAID_SUCCESS_DTH="P012";
    
    /* post paid transaction successful */
    public static String POSTPAID_SUCCESS_ABTS="P013";
    
    /* post paid transaction successful */
    public static String POSTPAID_BLACKOUT="P014";
    
    /* post paid transaction failure */
    public static String POSTPAID_FAILURE="P011";
    
    public static String INACTIVE_SOURCE_CIRCLE = "138";
    
    public static String RECHARGE_SYSTEM_TIMEOUT = "R010";
    
    public static String POSTPAID_SYSTEM_TIMEOUT = "P015";
    
    public static String RECHARGE_AIRURL_NULL = "R011";
    /* not authorized for recharge */
	public static String AUTHORIZATION_FAILURE_CODE = "R012";
    /* blackoout request */
	public static String BLACKOUT_REQUEST = "R013";
	
	public static String RECHARGE_SERIES_MAPPING_LIST_NULL = "R014";
}