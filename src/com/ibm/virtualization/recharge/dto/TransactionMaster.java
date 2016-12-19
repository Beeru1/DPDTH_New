package com.ibm.virtualization.recharge.dto;

import java.util.Date;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.RequestType;

public class TransactionMaster {
	/* transaction id */
	private long transactoinId;

	/* request type */
	private RequestType requestType;

	/* channel type */
	private ChannelType transactionChannel;

	/* transaction Date */
	private Date transactionDate;

	/* Source account Id */
	private long sourceId;

	/* Destination account Id */
	private String destinationId;

	/* Third party */
	private String thirdParty;

	/* transaction key value */
	private String keyValue;

	/* transaction amount */
	private double transactionAmount;

	/* transaction message */
	private String transactionMessage;

	/* transaction stuts */
	private TransactionStatus transactionStatus;

	/* Reason Id */
	private String reasonId;

	/* Created By */
	private long createdBy;

	/* Created Date */
	private Date createdDate;

	/* Updated By */
	private long updatedBy;

	/* Updated Date */
	private Date updatedDate;

	/* Sim Number of Source Account */
	private String physicalId;

	/* Time Stamp */
	private String requestTime;

	private long reset;
	
	/* Cell Id */
	private String cellId;

	/**
	 * @return the createdBy
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the destinationId
	 */
	public String getDestinationId() {
		return destinationId;
	}

	/**
	 * @param destinationId
	 *            the destinationId to set
	 */
	public void setDestinationId(String destinationAccountId) {
		this.destinationId = destinationAccountId;
	}

	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @param keyValue
	 *            the keyValue to set
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	/**
	 * @return the reasonId
	 */
	public String getReasonId() {
		return reasonId;
	}

	/**
	 * @param reasonId
	 *            the reasonId to set
	 */
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @return the sourceId
	 */
	public long getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */
	public void setSourceId(long sourceAccountId) {
		this.sourceId = sourceAccountId;
	}

	/**
	 * @return the thirdParty
	 */
	public String getThirdParty() {
		return thirdParty;
	}

	/**
	 * @param thirdParty
	 *            the thirdParty to set
	 */
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	/**
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the transactionChannel
	 */
	public ChannelType getTransactionChannel() {
		return transactionChannel;
	}

	/**
	 * @param transactionChannel
	 *            the transactionChannel to set
	 */
	public void setTransactionChannel(ChannelType transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionMessage
	 */
	public String getTransactionMessage() {
		return transactionMessage;
	}

	/**
	 * @param transactionMessage
	 *            the transactionMessage to set
	 */
	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
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
	 * @return the transactoinId
	 */
	public long getTransactoinId() {
		return transactoinId;
	}

	/**
	 * @param transactoinId
	 *            the transactoinId to set
	 */
	public void setTransactoinId(long transactoinId) {
		this.transactoinId = transactoinId;
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
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

		retValue = "TransactionMaster ( " + TAB + "transactoinId = "
				+ this.transactoinId + TAB + "requestType = "
				+ this.requestType + TAB + "transactionChannel = "
				+ this.transactionChannel + TAB + "transactionDate = "
				+ this.transactionDate + TAB + "sourceId = " + this.sourceId
				+ TAB + "destinationId = " + this.destinationId + TAB
				+ "thirdParty = " + this.thirdParty + TAB + "keyValue = "
				+ this.keyValue + TAB + "transactionAmount = "
				+ this.transactionAmount + TAB + "transactionMessage = "
				+ this.transactionMessage + TAB + "transactionStatus = "
				+ this.transactionStatus + TAB + "reasonId = " + this.reasonId
				+ TAB + "createdBy = " + this.createdBy + TAB
				+ "createdDate = " + this.createdDate + TAB + "updatedBy = "
				+ this.updatedBy + TAB + "updatedDate = " + this.updatedDate
				+ TAB + " )";

		return retValue;
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the requestTime
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime
	 *            the requestTime to set
	 */
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @return the physicalId
	 */
	public String getPhysicalId() {
		return physicalId;
	}

	/**
	 * @param physicalId
	 *            the physicalId to set
	 */
	public void setPhysicalId(String simNumber) {
		this.physicalId = simNumber;
	}

	/**
	 * @return the reset
	 */
	public long getReset() {
		return reset;
	}

	/**
	 * @param reset
	 *            the reset to set
	 */
	public void setReset(long reset) {
		this.reset = reset;
	}

	/**
	 * @return the cellId
	 */
	public String getCellId() {
		return cellId;
	}

	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

}
