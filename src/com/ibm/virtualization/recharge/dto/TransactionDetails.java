package com.ibm.virtualization.recharge.dto;

import java.util.Date;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.TransactionState;

public class TransactionDetails {
	/* internal id */
	private long internalId;

	/* transaction id */
	private long transactionId;

	/* transaction State */
	private TransactionState transactionState;

	/* transaction key value */
	private String keyValue;

	/* detail message */
	private String detailMessage;

	/* status */
	private TransactionStatus status;

	/* Created By */
	private long createdBy;

	/* Created Date */
	private Date createdDate;

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the detailMessage
	 */
	public String getDetailMessage() {
		return detailMessage;
	}

	/**
	 * @param detailMessage
	 *            the detailMessage to set
	 */
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @param keyValue
	 *            the keyValue to set
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	/**
	 * @return the status
	 */
	public TransactionStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	/**
	 * @return the transactionState
	 */
	public TransactionState getTransactionState() {
		return transactionState;
	}

	/**
	 * @param transactionState
	 *            the transactionState to set
	 */
	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(long transactoinId) {
		this.transactionId = transactoinId;
	}

	/**
	 * @return the internalId
	 */
	public long getInternalId() {
		return internalId;
	}

	/**
	 * @param internalId
	 *            the internalId to set
	 */
	public void setInternalId(long internalId) {
		this.internalId = internalId;
	}

	/**
	 * @return the createdBy
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "TransactionDetails ( " + super.toString() + TAB
				+ "internalId = " + this.internalId + TAB + "transactionId = "
				+ this.transactionId + TAB + "transactionState = "
				+ this.transactionState + TAB + "keyValue = " + this.keyValue
				+ TAB + "detailMessage = " + this.detailMessage + TAB
				+ "status = " + this.status + TAB + "createdBy = "
				+ this.createdBy + TAB + "createdDate = " + this.createdDate
				+ TAB + " )";

		return retValue;
	}

}
