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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.TransactionType;
import java.lang.StringBuilder;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class SysConfigKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private int circleId;

	private TransactionType transactionType;

	private ChannelType channelType;

	public int getCircleId() {
		return this.circleId;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public TransactionType getTransactionType() {
		return this.transactionType;
	}

	public boolean equals(Object o) {
		if (o != null
				&& o instanceof SysConfigKey
				&& (((SysConfigKey) o).getCircleId() == this.circleId)
				&& (((SysConfigKey) o).getTransactionType() == this.transactionType)
				&& (((SysConfigKey) o).getChannelType() == this.channelType)) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		int transTypeLength = TransactionType.values().length;
		int chnlTypeLength = ChannelType.values().length;
		return (this.circleId - 1) * (transTypeLength * chnlTypeLength) + 1
				+ this.transactionType.getValue() + this.channelType.getValue();
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	public String toString() {
		return 	"CircleId = " + this.circleId + " ChannelType = " + this.channelType +	" TransactionType : "
						   +this.transactionType;
	}
}
