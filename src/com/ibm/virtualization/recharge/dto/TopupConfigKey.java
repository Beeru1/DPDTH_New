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

import com.ibm.virtualization.recharge.common.TransactionType;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class TopupConfigKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private int circleId;

	private TransactionType transactionType;
	
	private long comissionGroupId;

	public long getComissionGroupId() {
		return comissionGroupId;
	}

	public void setComissionGroupId(long comissionGroupId) {
		this.comissionGroupId = comissionGroupId;
	}

	public int getCircleId() {
		return this.circleId;
	}

	public TransactionType getTransactionType() {
		return this.transactionType;
	}

	public boolean equals(Object o) {
		if (o != null
				&& o instanceof TopupConfigKey
				&& (((TopupConfigKey) o).getCircleId() == this.circleId)
				&& (((TopupConfigKey) o).getComissionGroupId() == this.comissionGroupId)
				&& (((TopupConfigKey) o).getTransactionType() == this.transactionType)) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		int transTypeLength = TransactionType.values().length;
		return this.circleId * transTypeLength + (transTypeLength - 1)
				+ this.transactionType.getValue() + (int)comissionGroupId ^2;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	public String toString() {
		return "CircleId = " + circleId + " TransactionType = " + transactionType+" commission group id:"+comissionGroupId;
	} 
}
