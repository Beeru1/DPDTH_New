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

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.TransactionType;

/**
 * This class is used for Data Transfer from Account to Customer between
 * different layers.
 * 
 * @author Sushil Kumar
 * 
 */

public class RechargeTransaction extends BaseTransaction {

	/* The ID of the Source Account */
	private long sourceAccountId;

	/* The Source Account Mobile Number */
	private String sourceMobileNo;

	/* The customer mobile no. */
	private String destinationMobileNo;

	/* The credit amount. */
	private double espCommission;

	/* The service tax. */
	private double serviceTax;

	/* The processing fee. */
	private double processingFee;

	/* The circle Id. */
	private int circleId;

	/* The Subscriber circle Id. */
	private int subscriberCircleId;

	/* Source debit Amount */
	private double debitAmount;

	/* card group */
	private String cardGroup;
	
	/* validity */
	private String validity;

	/* Air URL */
	private String airUrl;

	/* Subscriber balance after transaction */
	private double balanceAfterRecharge;

	private TransactionStatus transactionStatus;

	/* The Transaction Type */
	private TransactionType transactionType;

	/* message */
	private String message;

	private UserSessionContext userSessionContext;

	private String ResponseCode;
	
	private String inStatus;
	
	private double sourceAvailBalAfterRecharge;
	
	private String cellId;

	private String sourceCircleCode;
	
	private String subscriberCircleCode;
	
	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
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
	public void setEspCommission(double espCommission) {
		this.espCommission = espCommission;
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
	public String getDestinationMobileNo() {
		return destinationMobileNo;
	}

	/* Sets the customer Mobile No */
	public void setDestinationMobileNo(String customerMobileNo) {
		this.destinationMobileNo = customerMobileNo;
	}

	/* Gets the processing Fee */
	public double getProcessingFee() {
		return processingFee;
	}

	/* Sets the processing Fee */
	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}

	/* Gets the service Tax */
	public double getServiceTax() {
		return serviceTax;
	}

	/* Sets the service Tax */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
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
	 * @return the transactionStatus
	 */
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param transactionStatus
	 *            the transactionStatus to set
	 */
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the balanceAfterRecharge
	 */
	public double getBalanceAfterRecharge() {
		return balanceAfterRecharge;
	}

	/**
	 * @param balanceAfterRecharge
	 *            the balanceAfterRecharge to set
	 */
	public void setBalanceAfterRecharge(double balanceAfterRecharge) {
		this.balanceAfterRecharge = balanceAfterRecharge;
	}

	/**
	 * @return the cardGroup
	 */
	public String getCardGroup() {
		return cardGroup;
	}

	/**
	 * @param cardGroup
	 *            the cardGroup to set
	 */
	public void setCardGroup(String cardGroup) {
		this.cardGroup = cardGroup;
	}

	/**
	 * @return the airUrl
	 */
	public String getAirUrl() {
		return airUrl;
	}

	/**
	 * @param airUrl
	 *            the airUrl to set
	 */
	public void setAirUrl(String airUrl) {
		this.airUrl = airUrl;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
	
		StringBuffer xml = new StringBuffer();
		xml.append("<RechargeTransaction>")
		.append("<subscriberCircleId>").append(this.getSubscriberCircleId()).append("</subscriberCircleId>")
		.append("<requesterCircleId>").append(this.getCircleId()).append("</requesterCircleId>")
		.append("<espCommission>").append(this.getEspCommission()).append("</espCommission>")
		.append("<serviceTax>").append(this.getServiceTax()).append("</serviceTax>")
		.append("<processingFee>").append(this.getProcessingFee()).append("</processingFee>")
		.append("<CreditedAmount>").append(this.getCreditedAmount()).append("</CreditedAmount>")
		.append("<debitAmount>").append(this.getDebitAmount()).append("</debitAmount>")
		.append("<cardGroup>").append(this.getCardGroup()).append("</cardGroup>")
		.append("<airUrl>").append(this.getAirUrl()).append("</airUrl>")
		.append("<validity>").append(this.validity).append("</validity>")
		.append("<balanceAfterRecharge>").append(this.getBalanceAfterRecharge()).append("</balanceAfterRecharge>")
			.append("<TransactionAmount>").append(this.getTransactionAmount()).append("</TransactionAmount>")
		.append("<TransactionChannel>").append(this.getTransactionChannel()).append("</TransactionChannel>")
		.append("<ReasonID>").append(this.getReasonId()).append("</ReasonID>")
		.append("<ErrorMessage>").append(this.getErrorMessage()).append("</ErrorMessage>")
				.append("<ResponseCode>").append(this.getResponseCode()).append("</ResponseCode>").append("<sourceCircleCode>").append(this.getSourceCircleCode()).append("</sourceCircleCode>").append("<subscriberCircleCode>").append(this.subscriberCircleCode).append("</subscriberCircleCode>")
		.append("<ResponseCode>").append(this.getResponseCode()).append("</ResponseCode>")
		.append("</RechargeTransaction>");

	
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

	public UserSessionContext getUserSessionContext() {
		return userSessionContext;
	}

	public void setUserSessionContext(UserSessionContext userSessionContext) {
		this.userSessionContext = userSessionContext;
	}

	/**
	 * @return the validity
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public void setValidity(String validity) {
		this.validity = validity;
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
	 * @return the inStatus
	 */
	public String getInStatus() {
		return inStatus;
	}

	/**
	 * @param inStatus the inStatus to set
	 */
	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}

	/**
	 * @return the sourceAvailBalAfterRecharge
	 */
	public double getSourceAvailBalAfterRecharge() {
		return sourceAvailBalAfterRecharge;
	}

	/**
	 * @param sourceAvailBalAfterRecharge the sourceAvailBalAfterRecharge to set
	 */
	public void setSourceAvailBalAfterRecharge(double sourceAvailBalAfterRecharge) {
		this.sourceAvailBalAfterRecharge = sourceAvailBalAfterRecharge;
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

	/*
	 * public static void main(String arg[]) { RechargeTransaction
	 * rechargeTransaction = new RechargeTransaction();
	 *  }
	 */

}
