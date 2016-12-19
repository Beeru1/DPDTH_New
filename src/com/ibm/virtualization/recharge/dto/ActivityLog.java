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
import java.util.List;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.TransactionState;

/**
 * This dto is used to generate log in xml format from list of dto's
 * 
 * @author sushilku
 * 
 */
public class ActivityLog {

	/* internal id */
	private long internalId;

	/* transaction id */
	private long transactionId;

	/* transaction State */
	private TransactionState transactionState;

	/* transaction key value */
	private String keyValue;

	/* status */
	private TransactionStatus status;

	/* Created By */
	private long createdBy;

	/* Created Date */
	private Date createdDate;



	/* Time Stamp */
	private String requestTime;

	private String message = new String();


	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getInternalId() {
		return internalId;
	}

	public void setInternalId(long internalId) {
		this.internalId = internalId;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

	public String getMessage() {
		return this.message;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
