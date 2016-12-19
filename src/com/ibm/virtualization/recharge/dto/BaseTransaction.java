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

import java.util.Date;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.TransactionState;

/*******************************************************************************
 * ***************************************************************************\ * *
 * Virtualization - Recharge. * * Copyright (c) 2007-2008 IBM. * All Rights
 * Reserved * * \
 ******************************************************************************/

public abstract class BaseTransaction {
	protected long transactionId;

	protected double creditedAmount;

	protected double transactionAmount;

	protected Date transactionDate;

	protected long createdBy;

	protected long updatedBy;

	protected String createdDate;

	protected String updatedDate;

	protected RequestType requestType;

	protected ChannelType transactionChannel;

	protected String requestTime;

	protected String physicalId;

	protected TransactionState transactionState;

	private TransactionStatus transactionStatus;

	private String errorMessage;

	/* Reason id */
	private String reasonId;
	
	/* Stores the LoginName */
	private String accountCode;

	/**
	 * @return the physicalId
	 */
	public String getPhysicalId() {
		return physicalId;
	}

	/**
	 * @param physicalId
	 *            the physicalId to set
	 */
	public void setPhysicalId(String physicalId) {
		this.physicalId = physicalId;
	}

	/**
	 * @return the requestTime
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime
	 *            the requestTime to set
	 */
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @return Returns the createdBy.
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            The createdBy to set.
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Returns the transactionId.
	 */
	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            The transactionId to set.
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return Returns the creditedAmount.
	 */
	public double getCreditedAmount() {
		return creditedAmount;
	}

	/**
	 * @param creditedAmount
	 *            The creditedAmount to set.
	 */
	public void setCreditedAmount(double creditedAmount) {
		this.creditedAmount = creditedAmount;
	}

	/**
	 * @return Returns the transactionDate.
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            The transactionDate to set.
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return Returns the transactionAmount.
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            The transactionAmount to set.
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return Returns the updatedBy.
	 */
	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            The updatedBy to set.
	 */
	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the createdDate.
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            The createdDate to set.
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return Returns the updatedDate.
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            The updatedDate to set.
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return Returns the transactionChannel.
	 */
	public ChannelType getTransactionChannel() {
		return transactionChannel;
	}

	/**
	 * @param transactionChannel
	 *            The transactionChannel to set.
	 */
	public void setTransactionChannel(ChannelType transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue.append("<RequestType>").append(
						this.getRequestType()).append("</RequestType>").append(
						"<TransactionChannel>").append(
						this.getTransactionChannel()).append(
						"</TransactionChannel>");
		return retValue.toString();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the reasonId
	 */
	public String getReasonId() {
		return reasonId;
	}

	/**
	 * @param reasonId
	 *            the reasonId to set
	 */
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
}