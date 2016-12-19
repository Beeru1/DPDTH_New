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

import com.ibm.virtualization.framework.bean.TransactionStatus;

/**
 * QueryDTO represents the 'needed' data from multiple domain objects. To
 * aggregate the transaction data to be passed between the web-tier and the
 * service-tier. The service class fills this DTO object from an graph of
 * business objects
 * 
 * @author prakash
 */
public class QueryBalanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7910937619735092805L;

	private String accountCode;

	private long mobileNumber;

	private double openingBalance;

	private double operatingBalance;

	private double availableBalance;
	
	private TransactionStatus transactionStatus;
	
	

	/**
	 * @return the transactionStatus
	 */
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode
	 *            The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return Returns the availableBalance.
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            The availableBalance to set.
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return Returns the mobileNumber.
	 */
	public long getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            The mobileNumber to set.
	 */
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return Returns the openingBalance.
	 */
	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance
	 *            The openingBalance to set.
	 */
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return Returns the operatingBalance.
	 */
	public double getOperatingBalance() {
		return operatingBalance;
	}

	/**
	 * @param operatingBalance
	 *            The operatingBalance to set.
	 */
	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {

		StringBuilder retValue = new StringBuilder();

		retValue = retValue.append("<QueryBalanceDTO>").append("accountCode")
				.append(this.accountCode).append("accountCode").append(
						"mobileNumber").append(this.mobileNumber).append(
						"mobileNumber").append("openingBalance").append(
						this.openingBalance).append("openingBalance").append(
						"operatingBalance").append(this.operatingBalance)
				.append("operatingBalance").append("availableBalance").append(
						this.availableBalance).append("availableBalance");

		return retValue.toString();
	}
}