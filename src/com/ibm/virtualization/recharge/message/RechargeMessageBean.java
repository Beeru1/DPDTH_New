/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.message;

import java.util.ArrayList;

import com.ibm.virtualization.framework.bean.ConnectionBean;
import com.ibm.virtualization.recharge.common.TransactionType;

/**
 * @author shilpi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RechargeMessageBean extends CoreMessageBean {
	private static final long serialVersionUID = 2768907818813126169L;

	// Subscriber sourceCircleId
	private long subscriberCircleId;

	// Subscriber mobile number
	private String subscriberMobileNo;

	// AirUrl list
	private ArrayList mobileSeriesMappingList;

	private String airURL;
	// card group
	private String cardGroup;

	// Source groupId
	private long groupId;

	// Recharge Amounts
	private double rechargeAmount;

	// Source debit Amount
	private double debitAmount;

	// Subscriber Credit Amount
	private double creditAmount;

	// Subscriber balance after recharge
	private double balanceAfterRecharge;

	/** IN Queue Connection Properties * */
	private ConnectionBean connBeanIn;

	/** OUT Queue Connection Properties * */
	private ConnectionBean connBeanOut;

	private String transactionKeyValue;

	private TransactionType transactionType;

	private int externalConfigId;
	
	private String accountAddress;

	/** Updated By */
	private long updatedBy;
	
	private String validity;
	
	private String inStatus;
	
	// Source CircleCode
	private String sourceCircleCode;
	
	
	//Subscriber CircleId
	private String subscriberCircleCode;

	/**
	 * @return Returns the connBeanIn.
	 */
	public ConnectionBean getConnBeanIn() {
		return connBeanIn;
	}

	/**
	 * @param connBeanIn
	 *            The connBeanIn to set.
	 */
	public void setConnBeanIn(ConnectionBean connBeanIn) {
		this.connBeanIn = connBeanIn;
	}

	/**
	 * @return Returns the connBeanOut.
	 */
	public ConnectionBean getConnBeanOut() {
		return connBeanOut;
	}

	/**
	 * @param connBeanOut
	 *            The connBeanOut to set.
	 */
	public void setConnBeanOut(ConnectionBean connBeanOut) {
		this.connBeanOut = connBeanOut;
	}

	/**
	 * @return the creditAmount
	 */
	public double getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @param creditAmount
	 *            the creditAmount to set
	 */
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * @return the debitAmount
	 */
	public double getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @param debitAmount
	 *            the debitAmount to set
	 */
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	/**
	 * @return the groupId
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the subscriberCircleId
	 */
	public long getSubscriberCircleId() {
		return subscriberCircleId;
	}

	/**
	 * @param subscriberCircleId
	 *            the subscriberCircleId to set
	 */
	public void setSubscriberCircleId(long subscriberCircleId) {
		this.subscriberCircleId = subscriberCircleId;
	}

	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the balanceAfterRecharge
	 */
	public double getBalanceAfterRecharge() {
		return balanceAfterRecharge;
	}

	/**
	 * @param balanceAfterRecharge
	 *            the balanceAfterRecharge to set
	 */
	public void setBalanceAfterRecharge(double balanceAfterRecharge) {
		this.balanceAfterRecharge = balanceAfterRecharge;
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

	public String getSubscriberMobileNo() {
		return subscriberMobileNo;
	}

	public void setSubscriberMobileNo(String subscriberMobileNo) {
		this.subscriberMobileNo = subscriberMobileNo;
	}

	public String getCardGroup() {
		return cardGroup;
	}

	public void setCardGroup(String cardGroup) {
		this.cardGroup = cardGroup;
	}

	/**
	 * 
	 * @return
	 */
	public String getTransactionKeyValue() {
		return transactionKeyValue;
	}

	/**
	 * 
	 * @param transactionKeyValue
	 */
	public void setTransactionKeyValue(String transactionKeyValue) {
		this.transactionKeyValue = transactionKeyValue;
	}

	public int getExternalConfigId() {
		return externalConfigId;
	}

	public void setExternalConfigId(int externalConfigId) {
		this.externalConfigId = externalConfigId;
	}

	public double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(double rechargeAmt) {
		this.rechargeAmount = rechargeAmt;
	}

	public String XMLMessage(long sourceCircleId){
		StringBuffer xml = new StringBuffer();
		xml.append("<RechargeTransaction>")
		.append("<subscriberCircleId>").append(this.getSubscriberCircleId()).append("</subscriberCircleId>")
		.append("<requesterCircleId>").append(sourceCircleId).append("</requesterCircleId>").append("<sourceCircleCode>").append(sourceCircleCode).append("</sourceCircleCode>").append("<subscriberCircleCode>").append(subscriberCircleCode).append("</subscriberCircleCode>")
		.append("</RechargeTransaction>");
		return xml.toString();
	}
	/**
	 * @return the accountAddress
	 */
	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * @param accountAddress the accountAddress to set
	 */
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
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

	public String getSourceCircleCode() {
		return sourceCircleCode;
	}

	public void setSourceCircleCode(String sourceCircleCode) {
		this.sourceCircleCode = sourceCircleCode;
	}

	public String getSubscriberCircleCode() {
		return subscriberCircleCode;
	}

	public void setSubscriberCircleCode(String subscriberCircleCode) {
		this.subscriberCircleCode = subscriberCircleCode;
	}

	public ArrayList getMobileSeriesMappingList() {
		return mobileSeriesMappingList;
	}

	public void setMobileSeriesMappingList(ArrayList airUrlList) {
		this.mobileSeriesMappingList = airUrlList;
	}

	public String getAirURL() {
		return airURL;
	}

	public void setAirURL(String airURL) {
		this.airURL = airURL;
	}
}
