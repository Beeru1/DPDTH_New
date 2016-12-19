package com.ibm.virtualization.recharge.common;

/**
 * The Class TransactionState.
 * 
 * @author Sushil
 */
public enum TransactionState {
	IN_REQUEST_LISTENER("IN_REQUEST_LISTENER", 0), IN_RESPONSE_LISTENER(
			"IN_RESPONSE_LISTENER", 1), IN_ROUTER_LISTENER(
			"IN_ROUTER_LISTENER", 2), QUERY_OUT_LISTENER("QUERY_OUT_LISTENER",
			3), QUERY_REQUEST_LISTENER("QUERY_REQUEST_LISTENER", 4), RECHARGE_OUT_LISTENER(
			"RECHARGE_OUT_LISTENER", 5), RECHARGE_REQUEST_LISTENER(
			"RECHARGE_REQUEST_LISTENER", 6), TRANSFER_OUT_LISTENER(
			"TRANSFER_OUT_LISTENER", 7), TRANSFER_REQUEST_LISTENER(
			"TRANSFER_REQUEST_LISTENER", 8), POSTPAID_REQUEST_LISTENER(
			"POSTPAID_REQUEST_LISTENER", 9), POSTPAID_OUT_LISTENER(
			"POSTPAID_OUT_LISTENER", 10), WEB("WEB", 11), VAS_REQUEST_LISTENER(
			"VAS_REQUEST_LISTENER", 12), CHANGE_PWD_REQUEST_LISTENER("CHANGE_PWD_REQUEST_LISTENER", 13), 
			CHANGE_PWD_OUT_LISTENER("CHANGE_PWD_OUT_LISTENER", 14),
			RESET_PWD_REQUEST_LISTENER("RESET_PWD_REQUEST_LISTENER", 15),
			RESET_PWD_OUT_LISTENER("RESET_PWD_OUYT_LISTENER", 16);
	

	private String name;

	private int value;

	/**
	 * Returns Name of the Transaction State
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns Value of the Transaction State
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	private TransactionState(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns TransactionState having given value
	 * 
	 * @param transactionState
	 * @return
	 */
	public static TransactionState getTransactionState(int transactionState) {
		for (TransactionState element : TransactionState.values()) {
			if (element.getValue() == transactionState) {
				return element;
			}
		}
		return null;
	}

}