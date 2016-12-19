/**
 * 
 */
package com.ibm.virtualization.recharge.dto;

import java.util.Date;

/**
 * @author mohit
 *
 */
public class CustomerTransaction {
	
	private long transactionId;
	
	private String transationType;
	
	private long sourceAccount;
	
	private String customerMobileNo;
	
	private String confirmMobileNo;
	
	private double transactionAmount;
	
	private double serviceTax;
	
	private double processingFee;
	
	private double espCommission;
	
	private double pspCommission;
	
	private double talkTime;
	
	private Date transactionDate;
	
	private String status;
	
	private String reason;

	private String rowNum;
	
	private String startDate;
	
	private String endDate;
	
	private UserSessionContext sessionContext;
	
	private String sourceAccountCode;
	
	private int totalRecords;
	
	private String validity;
	
	
	private String subscriberCircleId;
	
	private String requesterCircleId;
	
	private double sourceAvailBalBeforeRecharge;
	
	private double sourceAvailBalAfterRecharge;
	
	private double debitAmount;
	
	private String inStatus;
	
	private String abtsNo;
	
	private String dthNo;
	
	
	/**
	 * @return the debitAmount
	 */
	public double getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @param debitAmount the debitAmount to set
	 */
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
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
	 * @return the sourceAccountCode
	 */
	public String getSourceAccountCode() {
		return sourceAccountCode;
	}

	/**
	 * @param sourceAccountCode the sourceAccountCode to set
	 */
	public void setSourceAccountCode(String sourceAccountCode) {
		this.sourceAccountCode = sourceAccountCode;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the sessionContext
	 */
	public UserSessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext the sessionContext to set
	 */
	public void setSessionContext(UserSessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	 * @return the customerMobileNo
	 */
	public String getCustomerMobileNo() {
		return customerMobileNo;
	}

	/**
	 * @param customerMobileNo the customerMobileNo to set
	 */
	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
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
	 * @return the sourceAccount
	 */
	public long getSourceAccount() {
		return sourceAccount;
	}

	/**
	 * @param sourceAccount the sourceAccount to set
	 */
	public void setSourceAccount(long sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
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
	 * @return the transationType
	 */
	public String getTransationType() {
		return transationType;
	}

	/**
	 * @param transationType the transationType to set
	 */
	public void setTransationType(String transationType) {
		this.transationType = transationType;
	}

	/**
	 * @return the espCommission
	 */
	public double getEspCommission() {
		return espCommission;
	}

	/**
	 * @param espCommission the espCommission to set
	 */
	public void setEspCommission(double espCommission) {
		this.espCommission = espCommission;
	}

	/**
	 * @return the pspCommission
	 */
	public double getPspCommission() {
		return pspCommission;
	}

	/**
	 * @param pspCommission the pspCommission to set
	 */
	public void setPspCommission(double pspCommission) {
		this.pspCommission = pspCommission;
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

	public String getInStatus() {
		return inStatus;
	}

	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}

	public String getConfirmMobileNo() {
		return confirmMobileNo;
	}

	public void setConfirmMobileNo(String confirmMobileNo) {
		this.confirmMobileNo = confirmMobileNo;
	}

	/**
	 * @return the abtsNo
	 */
	public String getAbtsNo() {
		return abtsNo;
	}

	/**
	 * @param abtsNo the abtsNo to set
	 */
	public void setAbtsNo(String abtsNo) {
		this.abtsNo = abtsNo;
	}

	/**
	 * @return the dthNo
	 */
	public String getDthNo() {
		return dthNo;
	}

	/**
	 * @param dthNo the dthNo to set
	 */
	public void setDthNo(String dthNo) {
		this.dthNo = dthNo;
	}
	
	
	

}
