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
 * This enumeration is used to know the mode of payment.
 * 
 * @author mohit
 */
public enum PaymentMode {
	CASH("CASH", 1), CHEQUE("CHEQUE", 2), CREDIT("CREDIT", 3), ECS("ECS", 4);

	private String name;

	private int value;

	/**
	 * Returns Name of the PaymentMode
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns Value of the PaymentMode
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	private PaymentMode(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns PaymentMode having given value
	 * 
	 * @param paymentMode
	 * @return
	 */
	public static PaymentMode getPaymentMode(int paymentMode) {
		for (PaymentMode element : PaymentMode.values()) {
			if (element.getValue() == paymentMode) {
				return element;
			}
		}
		return null;
	}
}
