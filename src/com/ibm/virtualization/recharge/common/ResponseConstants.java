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
 * This class stores all the Response Codes, Messages and Status ID
 * 
 * @author shilpi
 * 
 */
public interface ResponseConstants {

	/**
	 * Codes For Requests
	 */

	/* Code & message if Invalid Subscriber Number */
	public static String RC_INVALID_SUBSNO = "R001";

	public static String RM_INVALID_SUBSNO = "Invalid Subscriber Number. ";
	
	public static String RM_INVALID_SETTOPBOXNO = "Invalid Set Top box no";

	/* Code & message if Invalid SIM Number */
	public static String RC_INVALID_AMT = "R002";

	public static String RM_INVALID_AMT = "Invalid Amount. Should be Greater than Zero.";

	/* Code & message if Invalid Message */
	public static String RC_INVALID_RECHARGE_MSG = "R003";

	public static String RM_INVALID_RECHARGE_MSG = "Not a valid message. Message should have Physical Id, M-Password, Subscriber Number, Amount and Request Time separated by a space.";

	/* Code & message if Invalid Request */
	public static String RC_SUBSNO_NUM_SERIES_FAILED = "R004";

	public static String RM_SUBSNO_NUM_SERIES_FAILED = "Validation of Subscriber Number from Number Series Failed.";

	/* Code & message if Amount Does Not Consists Of Digits */
	public static String RC_AMT_DIGIT_FAILED = "R005";

	public static String RM_AMT_DIGIT_FAILED = "Validation Failed. Amount Should consist of only Digits.";

	/* Code & message if Invalid Message For Query - IS NULL */
	public static String RC_INVALID_MSG_NULL = "R006";

	public static String RM_INVALID_MSG_NULL = "Invalid Request Parameters. Either Mobile Number or Message is Null.";

	
	/* Code & message if Invalid Sim Number */

	
	public static String RM_INVALID_SIM_NO = "Invalid Physical Id. Should consist of 20 digits.";

	
	
	
	/**
	 * Code For Query Request
	 */

	/* Code & message if Invalid Request Type For Query */
	public static String RC_INVALID_REQ_TYPE_QUERY = "R007";

	public static String RM_INVALID_REQ_TYPE_QUERY = "Invalid request type.Should consists of RequestType either 0 or 1  ";

	/* Code & message if Invalid Message For Query Of Child */
	public static String RC_INVALID_QUERY_MSG_1 = "R008";

	public static String RM_INVALID_QUERY_MSG_1 = "Not a valid message. Should consist of Physical Id, Request Type, M-Password and Request Time separated by a space.";

	/* Code & message if Invalid Message For Query Of Self */
	public static String RC_INVALID_QUERY_MSG_2 = "R009";

	public static String RM_INVALID_QUERY_MSG_2 = "Not a valid message. Should consist of Physical Id, Request Type, Child's Mobile Number, Password and Request Time separated by a space.";
	/* Code & Message if Invalid Message For Reset M Password */
	public static String RC_INVALID_RESET_MSG_2="RM002";
	
	public static String RM_INVALID_RESET_MSG_2 = "Not a valid message. Should consist of Physical Id, Request Type,and Request Time separated by a space.";
	
	/* Code & Message if Invalid Message For Change M Password */
	public static String RC_INVALID_CHANGE_PASSWORD_MSG_2="CMP002";
	
	public static String RM_INVALID_CHANGE_PASSWORD_MSG_2 = "Not a valid message. Should consist of Physical Id, Request Type,Old M Password,New M Password and Request Time separated by a space.";
	/* Code & message if Invalid Child's Mobile Number */
	public static String RC_INVALID_QUERY_CHILD_NO = "R0010";

	public static String RM_INVALID_QUERY_CHILD_NO = "Invalid Child's Mobile Number. Should consist of 10 digits.";

	/* Code & message if Invalid Sim Number */

	public static String RM_INVALID_QUERY_SIM_NO = "Invalid Physical Id. Should consist of 20 digits.";
	
	
	/**
	 * Code For Account To Account Transfer Request
	 */
	/* Code & message if Invalid Receiver's Mobile Number */
	public static String RC_INVALID_ACC_RECEIVER_NO = "R0011";

	public static String RM_INVALID_ACC_RECEIVER_NO = "Invalid Receiver's Mobile Number. Should consist of 10 digits.";

	/* Code & message if Invalid Account transfer Request Message */
	public static String RC_INVALID_ACC_MSG = "R0012";

	public static String RM_INVALID_ACC_MSG = "Invalid Message. Should consist of Physical Id, M-Password, Receiver's Number, Amount and Request Time separated by Space.";

	/* Code & message if Invalid Sim Number */

	public static String RM_INVALID_ACC_SIM_NO = "Invalid Physical Id. Should consist of 20 digits.";

	
	
	/**
	 * Code & Messages For General Purpose
	 */

	/* Invalid Request */
	public static String RC_INVALID_REQUEST = "R0013";

	public static String RM_INVALID_REQUEST = "Invalid Request. Try Again...";

	/* Some Exception Occured */
	public static String RC_SOME_EXCEPTION = "A0013";

	public static String RM_SOME_EXCEPTION = "Some Exception Occured.";

	/* Status ID */
	public static String STATUS_ID_FAILED = "1";

	public static String STATUS_ID_SUCCESS = "0";

	/* Code and messge for MQ Exception */
	public static String RC_MQ_EXCEPTION = "A0014";

	public static String RM_MQ_EXCEPTION = "Some Problem Occured while Putting Message in Queue";

	/* Code and Message For SQL Exception */
	public static String RC_SQL_EXCEPTION = "A0015";

	public static String RM_SQL_EXCEPTION = "SQL Exception Occured while generating TransactionId .";

	public static String RM_TRANSID_EXCEPTION = "Transaction Id not generated.";

	public static String RC_TRANSID_EXCEPTION = "Transaction Id not generated.";
	
	/*Code incase post paid reqquest type is invalid*/
	public static String RC_POSTPAID_INVALID_REQUEST = "P0001";

	public static String RM_POSTPAID_INVALID_REQUEST = "Post Paid Request type should be either 5 or 6 .";
	

	/*Code incase post paid reqquest type is invalid*/
	public static String RC_POSTPAID_INVALID_REQUEST_CNT = "P0002";

	public static String RM_POSTPAID_INVALID_REQUEST_CNT = "Invalid number of Parameters";

	
	/*Code incase ABTS Confirmation number is invalid*/
	public static String RC_POSTPAID_INVALID_CONFIRMATION_NO = "P0003";

	public static String RM_POSTPAID_INVALID_CONFIRMATION_NO = "Conformation number is not valid";

	

	public static String RC_POSTPAID_INVALID_MOBILE_NO = "P0004";

	public static String RM_POSTPAID_INVALID_MOBILE_NO = "Invalid Mobile Number";
	
	public static String CC_INVALID_MESSAGE ="Invalid Message : Request not processed";
	
	public static String CC_REQUEST_FAILED ="Request not processed, please try again after some time";
	
	public static String RM_POSTPAID_MOBILITY_INVALID_REQUEST_CNT = "Invalid Message. Should Consist of Physical ID, Request Type, M-Password, Subscriber's Mobile Number, Amount and Request Time Separated By Space.";

	public static String RM_POSTPAID_ABTS_INVALID_REQUEST_CNT = "Invalid Message. Should Consist Of Physical ID, Request Type, M-Password, Subscriber's Mobile Number, Amount, Confirmation Number and Request Time Separated By Space.";
}