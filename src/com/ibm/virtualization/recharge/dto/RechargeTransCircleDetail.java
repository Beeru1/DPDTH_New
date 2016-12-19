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
/**
 * This class is used for fetching the information for Source & Suscriber.
 *   
 * 
 * @author bhanu
 * 
 */
import java.io.Serializable;

import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.TransactionType;

public class RechargeTransCircleDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705849822851941625L;

	// Source MPassword
	private String mPassword;
	
	// Source CircleId
	private int sourceCircleId;
	
	private long sourceAccountId;
	
	//Source Mobile Number
	private String sourceMobileNumber;
	
	// Subscriber Mobile Number
	private String subscriberMobileNumber;
	
	// Subscriber CircleId
	private int subscriberCircleId;
	
	//Subscriber CircleCode
	private String subscriberCircleCode;
	
	// Source CircleCode
	private String sourceCircleCode;
	
	private TransactionType transactionType;

	/**
	 * @return the mPassword
	 */
	public String getMPassword() {
		return mPassword;
	}

	/**
	 * @param password the mPassword to set
	 */
	public void setMPassword(String password) {
		mPassword = password;
	}

	/**
	 * @return the sourceCircleId
	 */
	public int getSourceCircleId() {
		return sourceCircleId;
	}

	/**
	 * @param sourceCircleId the sourceCircleId to set
	 */
	public void setSourceCircleId(int sourceCircleId) {
		this.sourceCircleId = sourceCircleId;
	}

	/**
	 * @return the sourceMobileNumber
	 */
	public String getSourceMobileNumber() {
		return sourceMobileNumber;
	}

	/**
	 * @param sourceMobileNumber the sourceMobileNumber to set
	 */
	public void setSourceMobileNumber(String sourceMobileNumber) {
		this.sourceMobileNumber = sourceMobileNumber;
	}

	/**
	 * @return the subscriberCircleId
	 */
	public int getSubscriberCircleId() {
		return subscriberCircleId;
	}

	/**
	 * @param subscriberCircleId the subscriberCircleId to set
	 */
	public void setSubscriberCircleId(int subscriberCircleId) {
		this.subscriberCircleId = subscriberCircleId;
	}

	/**
	 * @return the subscriberMobileNumber
	 */
	public String getSubscriberMobileNumber() {
		return subscriberMobileNumber;
	}

	/**
	 * @param subscriberMobileNumber the subscriberMobileNumber to set
	 */
	public void setSubscriberMobileNumber(String subscriberMobileNumber) {
		this.subscriberMobileNumber = subscriberMobileNumber;
	}

	/**
	 * @return the sourceAccountId
	 */
	public long getSourceAccountId() {
		return sourceAccountId;
	}

	/**
	 * @param sourceAccountId the sourceAccountId to set
	 */
	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public String getSourceCircleCode() {
		return sourceCircleCode;
	}

	public void setSourceCircleCode(String sourceCircleCode) {
		this.sourceCircleCode = sourceCircleCode;
	}

	public String getSubscriberCircleCode() {
		return subscriberCircleCode;
	}

	public void setSubscriberCircleCode(String subscriberCircleCode) {
		this.subscriberCircleCode = subscriberCircleCode;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}


	

}
