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

/**
 * This class is used for Data Transfer of a System Configuration between
 * different layers.
 * 
 * @author Navroze
 * 
 */
public class SysConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303736071506592277L;

	private long sysConfigId;

	private int circleId;

	private String circleName;

	private String channelName;

	private String transactionType;

	private long createdBy;

	private long updatedBy;

	private String createdDt;

	private String updatedDt;
	
	private double minAmount;
	
	private double maxAmount;

	/**
	 * @return Returns the sysConfigId.
	 */
	public long getSysConfigId() {
		return sysConfigId;
	}

	/**
	 * @param sysConfigId
	 *            The sysConfigId to set.
	 */
	public void setSysConfigId(long sysConfigId) {
		this.sysConfigId = sysConfigId;
	}

	/**
	 * @return Returns the circleId.
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            The circleId to set.
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return Returns the circleName.
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName
	 *            The circleName to set.
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return Returns the channelName.
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName
	 *            The channelName to set.
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return Returns the createdBy.
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            The createdBy to set.
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Returns the createdDt.
	 */
	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            The createdDt to set.
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return Returns the updatedBy.
	 */
	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            The updatedBy to set.
	 */
	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the updatedDt.
	 */
	public String getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            The updatedDt to set.
	 */
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
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

		retValue = "SysConfig ( " + super.toString() + TAB + "sysConfigId = "
				+ this.sysConfigId + TAB + "circleId = " + this.circleId + TAB
				+ "circleName = " + this.circleName + TAB + "channelName = "
				+ this.channelName + TAB + "transactionType = "
				+ this.transactionType + TAB + "minTransferAmount = "
				+ "createdBy = " + this.createdBy
				+ TAB + "updatedBy = " + this.updatedBy + TAB + "createdDt = "
				+ this.createdDt + TAB + "updatedDt = " + this.updatedDt + TAB +"\n"
				+ " )";

		return retValue;
	}

	/**
	 * @return the maxAmount
	 */
	public double getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param maxAmount the maxAmount to set
	 */
	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * @return the minAmount
	 */
	public double getMinAmount() {
		return minAmount;
	}

	/**
	 * @param minAmount the minAmount to set
	 */
	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	

	
}