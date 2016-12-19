/*****************************************************************************\
 **
 ** Virtualization - Recharge. 
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.beans;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;

/**
 * Form Bean class for Account to Account Transaction Report
 * 
 * @author Paras
 * 
 */
public class TransactionRptA2AFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String searchFieldName;

	private String searchFieldValue;

	private String status;

	private String viewstatus;

	private String startDt;

	private String endDt;

	private String methodName;

	private ArrayList displayDetails;

	private String transactionType;

	private String circleId;

	private String transactionId;

	private ArrayList circleList = null;

	private String mtpAccountCode;

	private boolean flagForCircleDisplay;

	private String customerMobile;

	private String transactionAmount;

	private String transactionDate;

	private String reason;

	private String createdBy;
	
	private String subscriberCircleId;
	
	private String requesterCircleId;
	
	private double sourceAvailBalBeforeRecharge;
	
	private double sourceAvailBalAfterRecharge;
	
	private double debitAmount; 
	
	private String cardGroup;
		
	
	/* ServiceTax holds the serviceTax for the Transaction */
	private String serviceTax = null;

	/* processingFee holds the processingFee for the Transaction */
	private String processingFee = null;

	/* espCommission holds the espCommission for the Transaction */
	private String espCommission = null;

	private String validity = null;

	/* espCommission holds the espCommission for the Transaction */
	private String talkTime = null;

	private String parentName;

	private String parentName1;
	
	private String parentName2;

	private String parentName3;
	
	private String receiverAccountId;
	
	private String page;
	
	private String selfcareStatus;
	
	private String inStatus;


	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentName1() {
		return parentName1;
	}

	public void setParentName1(String parentName1) {
		this.parentName1 = parentName1;
	}

	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the espCommission
	 */
	public String getEspCommission() {
		return espCommission;
	}

	/**
	 * @param espCommission
	 *            the espCommission to set
	 */
	public void setEspCommission(String espCommission) {
		if (!espCommission.equals("")) {
			this.espCommission = Utility.formatAmount(Double
					.parseDouble(espCommission));
		} else {
			this.espCommission = espCommission;
		}
	}

	/**
	 * @return the processingFee
	 */
	public String getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee
	 *            the processingFee to set
	 */
	public void setProcessingFee(String processingFee) {
		if (!processingFee.equals("")) {
			this.processingFee = Utility.formatAmount(Double
					.parseDouble(processingFee));
		} else {
			this.processingFee = processingFee;
		}
	}

	/**
	 * @return the serviceTax
	 */
	public String getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax
	 *            the serviceTax to set
	 */
	public void setServiceTax(String serviceTax) {
		if (!serviceTax.equals("")) {
			this.serviceTax = Utility.formatAmount(Double
					.parseDouble(serviceTax));
		} else {
			this.serviceTax = serviceTax;
		}
	}

	/**
	 * @return the validity
	 */
	/*
	 * public String getValidity() { return validity; }
	 */
	/**
	 * @param validity
	 *            the validity to set
	 */
	/*
	 * public void setValidity(String validity) { this.validity = validity;
	 *  }
	 */

	/**
	 * @return the displayDetails
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}

	/**
	 * @param displayDetails
	 *            the displayDetails to set
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}

	/**
	 * @return the searchFieldName
	 */
	public String getSearchFieldName() {
		return searchFieldName;
	}

	/**
	 * @param searchFieldName
	 *            the searchFieldName to set
	 */
	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}

	/**
	 * @return the searchFieldValue
	 */
	public String getSearchFieldValue() {
		return searchFieldValue;
	}

	/**
	 * @param searchFieldValue
	 *            the searchFieldValue to set
	 */
	public void setSearchFieldValue(String searchFieldValue) {
		this.searchFieldValue = searchFieldValue;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

	}

	/**
	 * @return the circleList
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList
	 *            the circleList to set
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the flagForCircleDisplay
	 */
	public boolean getFlagForCircleDisplay() {
		return flagForCircleDisplay;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param flagForCircleDisplay
	 *            the flagForCircleDisplay to set
	 */
	public void setFlagForCircleDisplay(boolean flagForCircleDisplay) {
		this.flagForCircleDisplay = flagForCircleDisplay;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the customerMobile
	 */
	public String getCustomerMobile() {
		return customerMobile;
	}

	/**
	 * @param customerMobile
	 *            the customerMobile to set
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
	 * @param mtpAccountCode
	 *            the mtpAccountCode to set
	 */
	public void setMtpAccountCode(String mtpAccountCode) {
		this.mtpAccountCode = mtpAccountCode;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the transactionAmount
	 */
	public String getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the talkTime
	 */
	public String getTalkTime() {
		return talkTime;
	}

	/**
	 * @param talkTime
	 *            the talkTime to set
	 */
	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}

	/**
	 * @return the validity
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * @param validity
	 *            the validity to set
	 */
	public void setValidity(String validity) {
		this.validity = validity;
	}

	/**
	 * @return the viewstatus
	 */
	public String getViewstatus() {
		return viewstatus;
	}

	/**
	 * @param viewstatus
	 *            the viewstatus to set
	 */
	public void setViewstatus(String viewstatus) {
		this.viewstatus = viewstatus;
	}

	/**
	 * @return the requesterCircleId
	 */
	public String getRequesterCircleId() {
		return requesterCircleId;
	}

	/**
	 * @param requesterCircleId the requesterCircleId to set
	 */
	public void setRequesterCircleId(String requesterCircleId) {
		this.requesterCircleId = requesterCircleId;
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
	 * @return the subscriberCircleId
	 */
	public String getSubscriberCircleId() {
		return subscriberCircleId;
	}

	/**
	 * @param subscriberCircleId the subscriberCircleId to set
	 */
	public void setSubscriberCircleId(String subscriberCircleId) {
		this.subscriberCircleId = subscriberCircleId;
	}
	
	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getSelfcareStatus() {
		return selfcareStatus;
	}

	public void setSelfcareStatus(String selfcareStatus) {
		this.selfcareStatus = selfcareStatus;
	}

	public String getInStatus() {
		return inStatus;
	}

	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
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


	public String getCardGroup() {
		return cardGroup;
	}

	public void setCardGroup(String cardGroup) {
		this.cardGroup = cardGroup;
	}

	/**
	 * @return the receiverAccountId
	 */
	public String getReceiverAccountId() {
		return receiverAccountId;
	}

	/**
	 * @param receiverAccountId the receiverAccountId to set
	 */
	public void setReceiverAccountId(String receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}

	
}
