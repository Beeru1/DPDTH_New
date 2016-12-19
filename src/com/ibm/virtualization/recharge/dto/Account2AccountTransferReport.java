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
 * Dto used for Account2Account Transfer Summary Report.
 * 
 */
public class Account2AccountTransferReport {

    private String transactionType;
	
	private long noSuccessfulTransaction;
	
	private double valueSuccessfulTransaction;
	
	private long noFailedTransaction;
	
	private double valueFailedTransaction;
	
	private long totalTransaction;
	
	private double totalEspCommission;
	
	private String rowNum;

	/**
	 * @return the noFailedTransaction
	 */
	public long getNoFailedTransaction() {
		return noFailedTransaction;
	}

	/**
	 * @param noFailedTransaction the noFailedTransaction to set
	 */
	public void setNoFailedTransaction(long noFailedTransaction) {
		this.noFailedTransaction = noFailedTransaction;
	}

	/**
	 * @return the noSuccessfulTransaction
	 */
	public long getNoSuccessfulTransaction() {
		return noSuccessfulTransaction;
	}

	/**
	 * @param noSuccessfulTransaction the noSuccessfulTransaction to set
	 */
	public void setNoSuccessfulTransaction(long noSuccessfulTransaction) {
		this.noSuccessfulTransaction = noSuccessfulTransaction;
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
	 * @return the totalEspCommission
	 */
	public double getTotalEspCommission() {
		return totalEspCommission;
	}

	/**
	 * @param totalEspCommission the totalEspCommission to set
	 */
	public void setTotalEspCommission(double totalEspCommission) {
		this.totalEspCommission = totalEspCommission;
	}

	/**
	 * @return the totalTransaction
	 */
	public long getTotalTransaction() {
		return totalTransaction;
	}

	/**
	 * @param totalTransaction the totalTransaction to set
	 */
	public void setTotalTransaction(long totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the valueFailedTransaction
	 */
	public double getValueFailedTransaction() {
		return valueFailedTransaction;
	}

	/**
	 * @param valueFailedTransaction the valueFailedTransaction to set
	 */
	public void setValueFailedTransaction(double valueFailedTransaction) {
		this.valueFailedTransaction = valueFailedTransaction;
	}

	/**
	 * @return the valueSuccessfulTransaction
	 */
	public double getValueSuccessfulTransaction() {
		return valueSuccessfulTransaction;
	}

	/**
	 * @param valueSuccessfulTransaction the valueSuccessfulTransaction to set
	 */
	public void setValueSuccessfulTransaction(double valueSuccessfulTransaction) {
		this.valueSuccessfulTransaction = valueSuccessfulTransaction;
	}

	
}
