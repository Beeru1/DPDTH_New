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

/**
 * Dto used for Account2Account Transactions Report.
 * 
 */
public class Account2AccountReport {
	
	private long transactionId;
	
	private String transferringAccountCode;
	
	private String receivingAccountCode;
	
	private double transactionAmount;
	
	private String status;
	
	private Date transactionDate;
	
	private String rowNum;
	
	private String reason;
	
	private String createdBy;
	
	private int totalRecords;
	
	private double debitAmount;
	
	private double sourceAvailBalBeforeRecharge;
	
	private double sourceAvailBalAfterRecharge;

	
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
	 * @return the receivingAccountCode
	 */
	public String getReceivingAccountCode() {
		return receivingAccountCode;
	}

	/**
	 * @param receivingAccountCode the receivingAccountCode to set
	 */
	public void setReceivingAccountCode(String receivingAccountCode) {
		this.receivingAccountCode = receivingAccountCode;
	}

	/**
	 * @return the transferringAccountCode
	 */
	public String getTransferringAccountCode() {
		return transferringAccountCode;
	}

	/**
	 * @param transferringAccountCode the transferringAccountCode to set
	 */
	public void setTransferringAccountCode(String transferringAccountCode) {
		this.transferringAccountCode = transferringAccountCode;
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

}
