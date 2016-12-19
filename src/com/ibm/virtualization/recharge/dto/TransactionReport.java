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

import java.io.Serializable;
import java.util.Date;

import com.ibm.virtualization.recharge.common.TransactionType;

/**
 * This class is used for Data Transfer of a Transaction Report between different layers.
 * 
 * @author Paras
 * 
 */
public class TransactionReport implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long transactionId;
	
	private TransactionType transactionType;

	private String mtpAccountCode;

	private String customerMobile;

	private double transactionAmount;

	private double serviceTax;

	private double processingFee;

	private double commission;

	private double talkTime;

	private Date transactionDate;

	private int status;
	
	private String reason;
	
	private String createdBy;
	
	private String rowNum;
	
	private int totalRecords;
	

	private String cardGroup;
	
	private String validity;
	
	private String inStatus;
	
	private String selfcareStatus;
	
	private String subscriberCircleId;
	
	private String requesterCircleId;
	
	private String requesterMobile;
	
	private double debitAmount; 
	
	private double sourceAvailBalBeforeRecharge;
	
	private double sourceAvailBalAfterRecharge;
	
	private String parentName;
	
	private String parentName1;

	private String parentName2;
	
	private String parentName3;
	
	private long receiverAccountId;
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



	/**
	 * @return the sourceAvailBalBeforeRecharge
	 */
	public double getSourceAvailBalBeforeRecharge() {
		return sourceAvailBalBeforeRecharge;
	}



	/**
	 * @param sourceAvailBalBeforeRecharge the sourceAvailBalBeforeRecharge to set
	 */
	public void setSourceAvailBalBeforeRecharge(double sourceAvailBalBeforeRecharge) {
		this.sourceAvailBalBeforeRecharge = sourceAvailBalBeforeRecharge;
	}



	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}



	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}



	/**
	 * @return the commission
	 */
	public double getCommission() {
		return commission;
	}



	/**
	 * @param commission the commission to set
	 */
	public void setCommission(double commission) {
		this.commission = commission;
	}



	/**
	 * @return the customerMobile
	 */
	public String getCustomerMobile() {
		return customerMobile;
	}



	/**
	 * @param customerMobile the customerMobile to set
	 */
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}



	/**
	 * @return the mtpAccountCode
	 */
	public String getMtpAccountCode() {
		return mtpAccountCode;
	}



	/**
	 * @param mtpAccountCode the mtpAccountCode to set
	 */
	public void setMtpAccountCode(String mtpAccountCode) {
		this.mtpAccountCode = mtpAccountCode;
	}



	/**
	 * @return the processingFee
	 */
	public double getProcessingFee() {
		return processingFee;
	}



	/**
	 * @param processingFee the processingFee to set
	 */
	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}



	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}



	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}



	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}



	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}



	/**
	 * @return the serviceTax
	 */
	public double getServiceTax() {
		return serviceTax;
	}



	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}



	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}



	/**
	 * @return the talkTime
	 */
	public double getTalkTime() {
		return talkTime;
	}



	/**
	 * @param talkTime the talkTime to set
	 */
	public void setTalkTime(double talkTime) {
		this.talkTime = talkTime;
	}



	/**
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}



	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}



	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}



	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}



	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}



	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}



	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}



	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}


	

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}



	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
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

		retValue = "TransactionReport ( " + super.toString() + TAB + "transactionId = "
				+ this.transactionId + TAB + "transactionType = " + this.transactionType + TAB 
				+ "mtpAccountCode = " + this.mtpAccountCode + TAB
				+ "customerMobile = " + this.customerMobile + TAB + "transactionAmount = "
				+ this.transactionAmount + TAB + "serviceTax = " + this.serviceTax + TAB
				+ "processingFee = " + this.processingFee + TAB
				+ "commission = " + this.commission + TAB
				+ "talkTime = " + this.talkTime + TAB
				+ "transactionDate = " + this.transactionDate + TAB
				+ "status = " + this.status + TAB + "reason = "
				+ this.reason + TAB + "createdBy = " + this.createdBy +" validity = "+this.validity
				+ TAB + " )";

		return retValue;
	}



	/**
	 * @return the cardGroup
	 */
	public String getCardGroup() {
		return cardGroup;
	}



	/**
	 * @param cardGroup the cardGroup to set
	 */
	public void setCardGroup(String cardGroup) {
		this.cardGroup = cardGroup;
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



	public String getRequesterCircleId() {
		return requesterCircleId;
	}



	public void setRequesterCircleId(String requesterCircleId) {
		this.requesterCircleId = requesterCircleId;
	}



	public String getSubscriberCircleId() {
		return subscriberCircleId;
	}



	public void setSubscriberCircleId(String subscriberCircleId) {
		this.subscriberCircleId = subscriberCircleId;
	}



	public double getDebitAmount() {
		return debitAmount;
	}



	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}



	public String getRequesterMobile() {
		return requesterMobile;
	}



	public void setRequesterMobile(String requesterMobile) {
		this.requesterMobile = requesterMobile;
	}



	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}



	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}



	/**
	 * @return the parentName1
	 */
	public String getParentName1() {
		return parentName1;
	}



	/**
	 * @param parentName1 the parentName1 to set
	 */
	public void setParentName1(String parentName1) {
		this.parentName1 = parentName1;
	}



	public String getSelfcareStatus() {
		return selfcareStatus;
	}



	public void setSelfcareStatus(String selfcareStatus) {
		this.selfcareStatus = selfcareStatus;
	}



	public String getParentName2() {
		return parentName2;
	}



	public void setParentName2(String parentName2) {
		this.parentName2 = parentName2;
	}



	public String getParentName3() {
		return parentName3;
	}



	public void setParentName3(String parentName3) {
		this.parentName3 = parentName3;
	}



	/**
	 * @return the receiverAccountId
	 */
	public long getReceiverAccountId() {
		return receiverAccountId;
	}



	/**
	 * @param receiverAccountId the receiverAccountId to set
	 */
	public void setReceiverAccountId(long receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}




	
	
	
}