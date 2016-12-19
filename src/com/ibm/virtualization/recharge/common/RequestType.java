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
 * @author Sushil
 */
public enum RequestType {
	RECHARGE(TransactionType.RECHARGE.name(), TransactionType.RECHARGE.getValue()), QUERY("QUERY", 1), ACCOUNT(TransactionType.ACCOUNT.name(), TransactionType.ACCOUNT.getValue()), VAS(TransactionType.VAS.name(),TransactionType.VAS.getValue()), POSTPAID_MOBILE(TransactionType.POSTPAID_MOBILE.name(),TransactionType.POSTPAID_MOBILE.getValue()), CHANGEMPWD("CHANGEMPWD", 5), RESETMPWD("RESETMPWD", 6), POSTPAID_ABTS(TransactionType.POSTPAID_ABTS.name(), TransactionType.POSTPAID_ABTS.getValue()), POSTPAID_DTH(TransactionType.POSTPAID_DTH.name(), TransactionType.POSTPAID_DTH.getValue());

	private String name;

	private int value;

	/**
	 * Returns Name of the Request Type
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns Value of the Request Type
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	private RequestType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns RequestType having given value
	 * 
	 * @param RequestType
	 * @return
	 */
	public static RequestType getRequestType(int requestType) {
		for (RequestType element : RequestType.values()) {
			if (element.getValue() == requestType) {
				return element;
			}
		}
		return null;
	}
}
