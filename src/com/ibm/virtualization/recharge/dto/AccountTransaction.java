/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dto;

import com.ibm.virtualization.recharge.common.TransactionType;

/**
 * This class is used for Data Transfer within Account between different layers.
 * 
 * @author Sushil Kumar
 * 
 */
public class AccountTransaction extends BaseTransaction {

	/* The ID of the Source Account */
	private long sourceAccountId;

	/* The ID of the Destination Account */
	private long destinationAccountId;

	/* The Source Account Mobile Number */
	private String sourceMobileNo;

	/* The Destination Account Mobile Number */
	private String destinationMobileNo;

	/* The Transaction Type */
	private TransactionType transactionType;
	
	/* The Source Account Balance */
	private double sourceAccountBalance;
	
	/* The Desitnation Account Balance */
	private double destinationAccountBalance;

	/* The Rate for transaction */
	private double rate;

	/* The mPassword of the Account */
	private String mPassword;

	/* The circle Id. */
	private int circleId = 0;

	private long contextSourceId;

	private UserSessionContext userSessionContext;

	private String ResponseCode;
	
	private String cellId;
	/**
	 * Gets the circle Id
	 * 
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * Sets the circle Id
	 * 
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the mPassword
	 */
	public String getMPassword() {
		return mPassword;
	}

	/**
	 * @param password
	 *            the mPassword to set
	 */
	public void setMPassword(String password) {
		mPassword = password;
	}

	/* Gets the Destination Mobile Number */
	public String getDestinationMobileNo() {
		return destinationMobileNo;
	}

	/* Sets the Destination Mobile Number */
	public void setDestinationMobileNo(String destinationMobileNo) {
		this.destinationMobileNo = destinationMobileNo;
	}

	/* Gets the Rate for transaction */
	public double getRate() {
		return rate;
	}

	/* Sets the Rate for transaction */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/* Gets the Source Mobile Number */
	public String getSourceMobileNo() {
		return sourceMobileNo;
	}

	/* Sets the Source Mobile Number */
	public void setSourceMobileNo(String soruceMobileNo) {
		this.sourceMobileNo = soruceMobileNo;
	}

	/* Gets the ID of the Destination Account */
	public long getDestinationAccountId() {
		return destinationAccountId;
	}

	/* Sets the ID of the Destination Account */
	public void setDestinationAccountId(long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	/* Gets the ID of the Source Account */
	public long getSourceAccountId() {
		return sourceAccountId;
	}

	/* Gets the ID of the Source Account */
	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		
		StringBuffer xml = new StringBuffer();
		xml.append("<AccountTransaction>")
		.append("<CircleId>").append(this.getCircleId()).append("</CircleId>")
		.append("<Rate>").append(this.getRate()).append("</Rate>")
		.append("<SourceMobileNo>").append(	this.getSourceMobileNo()).append("</SourceMobileNo>")
		.append("<DestinationMobileNo>").append(this.getDestinationMobileNo()).append("</DestinationMobileNo>")
		.append("<CreditedAmount>").append(this.getCreditedAmount()).append("</CreditedAmount>")
		.append("<debitAmount>").append(this.getTransactionAmount()).append("</debitAmount>")
		.append("<TransactionAmount>").append(this.getTransactionAmount()).append("</TransactionAmount>")
		.append("<TransactionChannel>").append(this.getTransactionChannel()).append("</TransactionChannel>")
		.append("<ReasonID>").append(this.getReasonId()).append("</ReasonID>")
		.append("</AccountTransaction>");
		
		return xml.toString();
	}

	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the contextSourceId
	 */
	public long getContextSourceId() {
		return contextSourceId;
	}

	/**
	 * @param contextSourceId
	 *            the contextSourceId to set
	 */
	public void setContextSourceId(long contextSourceId) {
		this.contextSourceId = contextSourceId;
	}

	public UserSessionContext getUserSessionContext() {
		return userSessionContext;
	}

	public void setUserSessionContext(UserSessionContext userSessionContext) {
		this.userSessionContext = userSessionContext;
	}

	public double getDestinationAccountBalance() {
		return destinationAccountBalance;
	}

	public void setDestinationAccountBalance(double destinationAccountBalance) {
		this.destinationAccountBalance = destinationAccountBalance;
	}

	public double getSourceAccountBalance() {
		return sourceAccountBalance;
	}

	public void setSourceAccountBalance(double sourceAccountBalance) {
		this.sourceAccountBalance = sourceAccountBalance;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return ResponseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}

	/**
	 * @return the cellId
	 */
	public String getCellId() {
		return cellId;
	}

	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	/*
	 * public static void main(String arg[]) { AccountTransaction
	 * accountTransaction = new AccountTransaction(); 
	 */
}
