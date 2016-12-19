/*****************************************************************************\
 **
 ** Virtualization - PostpaidTransaction - Post Bill Payment.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 *
 ** Oct 18, 2007 3:55:06 PM
 ** @ author :- Mohit Aggarwal
 \****************************************************************************/
package com.ibm.virtualization.recharge.dto;

import com.ibm.virtualization.recharge.common.TransactionType;

/**
 * This class is used for Data Transfer from Account to Customer between
 * different layers.
 */

public class PostpaidTransaction extends BaseTransaction {

	/* The ID of the Source Account */
	private long sourceAccountId;

	/* The Source Account Mobile Number */
	private String sourceMobileNo;

	/* The customer mobile no. */
	private String destinationNo;

	/* The confirmation Mobile Number */
	private String confirmationMobileNo;

	/* The credit amount. */
	private double espCommission;

	/* The circle Id. */
	private int circleId;
	
	/* The requseter circle Code */
	private String requesterCircleCode;

	/* The Subscriber circle Id. */
	private int subscriberCircleId;
	
	/* The Subscriber circle Code. */
	private String subscriberCircleCode;

	private String sourceCircleCode;
	
	/* Source debit Amount */
	private double debitAmount;

	/* Reason id */
	private String reasonId;

	/* message */
	private String message;
	
	/* The Transaction Type */
	private TransactionType transactionType;

	private UserSessionContext userSessionContext;
	
	private String ResponseCode;
	
	private String cellId;

	private String selfCareStatus;
	
	private String serviceAddressLocation;
	
	private double sourceAvailBalAfteTrans;
	
	
	public double getSourceAvailBalAfterTrans() {
		return sourceAvailBalAfteTrans;
	}

	public void setSourceAvailBalAfterTrans(double sourceAvailBalAfterRecharge) {
		this.sourceAvailBalAfteTrans = sourceAvailBalAfterRecharge;
	}
	public UserSessionContext getUserSessionContext() {
		return userSessionContext;
	}

	public void setUserSessionContext(UserSessionContext userSessionContext) {
		this.userSessionContext = userSessionContext;
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
	 * @return the updatedBy
	 */
	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

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
	 * Gets the espCommission
	 * 
	 * @return the espCommission
	 */
	public double getEspCommission() {
		return espCommission;
	}

	/**
	 * Sets the espCommission
	 * 
	 * @param espCommission
	 * 
	 */
	public void setEspCommission(double creditAmount) {
		this.espCommission = creditAmount;
	}

	/* Gets the ID of the Source Account */
	public long getSourceAccountId() {
		return sourceAccountId;
	}

	/* Sets the ID of the Source Account */
	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	/* Gets the customer Mobile No */
	public String getDestinationNo() {
		return destinationNo;
	}

	/* Sets the customer Mobile No */
	public void setDestinationNo(String customerMobileNo) {
		this.destinationNo = customerMobileNo;
	}

	/* Gets the Source Mobile Number */
	public String getSourceMobileNo() {
		return sourceMobileNo;
	}

	/**
	 * Sets the Source Mobile Number
	 * 
	 * @param soruceMobileNo
	 */

	public void setSourceMobileNo(String soruceMobileNo) {
		this.sourceMobileNo = soruceMobileNo;
	}

	/**
	 * @return the debitAmount
	 */
	public double getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @param debitAmount
	 *            the debitAmount to set
	 */
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
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
	 * @return the subsCircleId
	 */
	public int getSubscriberCircleId() {
		return subscriberCircleId;
	}

	/**
	 * @param subsCircleId
	 *            the subsCircleId to set
	 */
	public void setSubscriberCircleId(int subsCircleId) {
		subscriberCircleId = subsCircleId;
	}

	public String getConfirmationMobileNo() {
		return confirmationMobileNo;
	}

	public void setConfirmationMobileNo(String confirmationMobileNo) {
		this.confirmationMobileNo = confirmationMobileNo;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the selfCareStatus
	 */
	public String getSelfCareStatus() {
		return selfCareStatus;
	}

	/**
	 * @param selfCareStatus the selfCareStatus to set
	 */
	public void setSelfCareStatus(String selfCareStatus) {
		this.selfCareStatus = selfCareStatus;
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

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
	
		StringBuffer xml = new StringBuffer();
		xml.append("<PostPaidTransaction>")
		.append("<subscriberCircleId>").append(this.getSubscriberCircleId()).append("</subscriberCircleId>")
		.append("<requesterCircleId>").append(this.getCircleId()).append("</requesterCircleId>")
		.append("<subscriberCircleCode>").append(this.getSubscriberCircleCode()).append("</subscriberCircleCode>")
		.append("<requesterCircleCode>").append(this.getRequesterCircleCode()).append("</requesterCircleCode>")
		.append("<sourceMobileNo>").append(	this.sourceMobileNo).append("</sourceMobileNo>")
		.append("<destinationMobileNo>").append(this.destinationNo).append("</destinationMobileNo>")
		.append("<confirmationMobileNo>").append(this.confirmationMobileNo).append("</confirmationMobileNo>")
		.append("<espCommission>").append(this.getEspCommission()).append("</espCommission>")
		.append("<CreditedAmount>").append(this.getCreditedAmount()).append("</CreditedAmount>")
		.append("<debitAmount>").append(this.getDebitAmount()).append("</debitAmount>")
			.append("<TransactionAmount>").append(this.getTransactionAmount()).append("</TransactionAmount>")
		.append("<TransactionChannel>").append(this.getTransactionChannel()).append("</TransactionChannel>")
		.append("<SelfCareUrl>").append(this.serviceAddressLocation).append("</SelfCareUrl>")
		.append("<ReasonID>").append(this.getReasonId()).append("</ReasonID>")
		.append("<ResponseCode>").append(this.getResponseCode()).append("</ResponseCode>")
		.append("<HostAddress>").append(this.getMessage()).append("</HostAddress>")
		.append("</PostPaidTransaction>");

		return xml.toString();
	}

	public String getSubscriberCircleCode() {
		return subscriberCircleCode;
	}

	public void setSubscriberCircleCode(String subscriberCircleCode) {
		this.subscriberCircleCode = subscriberCircleCode;
	}

	public String getRequesterCircleCode() {
		return requesterCircleCode;
	}

	public void setRequesterCircleCode(String requesterCircleCode) {
		this.requesterCircleCode = requesterCircleCode;
	}

	public String getServiceAddressLocation() {
		return serviceAddressLocation;
	}

	public void setServiceAddressLocation(String serviceAddressLocation) {
		this.serviceAddressLocation = serviceAddressLocation;
	}

	public double getSourceAvailBalAfteTrans() {
		return sourceAvailBalAfteTrans;
	}

	public void setSourceAvailBalAfteTrans(double sourceAvailBalAfteTrans) {
		this.sourceAvailBalAfteTrans = sourceAvailBalAfteTrans;
	}

	public String getSourceCircleCode() {
		return sourceCircleCode;
	}

	public void setSourceCircleCode(String sourceCircleCode) {
		this.sourceCircleCode = sourceCircleCode;
	}

}
