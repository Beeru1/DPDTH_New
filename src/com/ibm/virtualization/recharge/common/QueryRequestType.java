/*****************************************************************************\
 **
 ** Virtualization
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \***************************************************************************/
package com.ibm.virtualization.recharge.common;

/**
 * 
 * This enumeration is used to know what the user wants to do for the data
 * (import data or not, what use as source of the data...).
 * 
 * @author ashish
 */
public enum QueryRequestType {

	/**
	 * Query the balance of sender's own account
	 */
	QUERY_SELF_BALANCE,

	/**
	 * Query child account's balance
	 */
	QUERY_CHILD_BALANCE

}
