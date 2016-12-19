/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.message;

/**
 * This is a bean for Account to Account Transfer through SMSC
 * 
 */

public class AccountTransferMessageBean extends CoreMessageBean {
	private static final long serialVersionUID = 95;

	// *** Receiver Mobile Number ***//
	private String destinationMobileNo;

	// *** Amount for which the Destination Account has to be recharged
	private double transactionAmount;

	// *** Balance of the Source Account
	private double sourceBalance;

	// *** Balance of the Destination Account
	private double destinationBalance;

	// *** Amount for which the Destination Account has actually been recharged
	private double creditedAmount;
	
	/* The ID of the Destination Account */
	private long destinationAccountId;
	
	/**
	 * @return the destinationAccountId
	 */
	public long getDestinationAccountId() {
		return destinationAccountId;
	}

	/**
	 * @param destinationAccountId the destinationAccountId to set
	 */
	public void setDestinationAccountId(long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	/**
	 * @return the creditedAmount
	 */
	public double getCreditedAmount() {
		return creditedAmount;
	}

	/**
	 * @param creditedAmount
	 *            the creditedAmount to set
	 */
	public void setCreditedAmount(double creditedAmount) {
		this.creditedAmount = creditedAmount;
	}

	/**
	 * @return the destinationMobileNo
	 */
	public String getDestinationMobileNo() {
		return destinationMobileNo;
	}

	/**
	 * @param destinationMobileNo
	 *            the destinationMobileNo to set
	 */
	public void setDestinationMobileNo(String destinationMobileNo) {
		this.destinationMobileNo = destinationMobileNo;
	}

	/**
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the destinationBalance
	 */
	public double getDestinationBalance() {
		return destinationBalance;
	}

	/**
	 * @param destinationBalance
	 *            the destinationBalance to set
	 */
	public void setDestinationBalance(double destinationBalance) {
		this.destinationBalance = destinationBalance;
	}

	/**
	 * @return the sourceBalance
	 */
	public double getSourceBalance() {
		return sourceBalance;
	}

	/**
	 * @param sourceBalance
	 *            the sourceBalance to set
	 */
	public void setSourceBalance(double sourceBalance) {
		this.sourceBalance = sourceBalance;
	}

	
	
	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue = retValue.append("<AccountTransferMessageBean>")
		.append("<destinationMobileNo>").append(this.destinationMobileNo).append("</destinationMobileNo>")
		.append("<transactionAmount>").append(this.transactionAmount).append("</transactionAmount>")
		.append("<sourceBalance>").append(this.sourceBalance).append("</sourceBalance>")
			.append("<destinationBalance>").append(this.destinationBalance).append("</destinationBalance>")
			.append("<creditedAmount>").append(this.creditedAmount).append("</creditedAmount>")
	     .append(super.toString()).append(
				"</AccountTransferMessageBean>");
		return retValue.toString();
	}

	
}