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
 * 
 * This enumeration is used to define varous transaction types used to define
 * System Configuration.
 * 
 * @author Paras
 */

public enum TransactionType {
	RECHARGE("RECHARGE", 0), VAS("VAS", 3), ACCOUNT("ACCOUNT", 2), POSTPAID_ABTS(
			"POSTPAID_ABTS", 7), POSTPAID_MOBILE("POSTPAID_MOBILE", 4),POSTPAID_DTH("POSTPAID_DTH", 8);

	private String name;

	private int value;

	/**
	 * Returns Name of the Transaction Type
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns Value of the Transaction Type
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	private TransactionType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns TransactionType having given value
	 * 
	 * @param transactionType
	 * @return
	 */
	public static TransactionType getTransactionType(int transactionType) {
		for (TransactionType element : TransactionType.values()) {
			if (element.getValue() == transactionType) {
				return element;
			}
		}
		return null;
	}
}
