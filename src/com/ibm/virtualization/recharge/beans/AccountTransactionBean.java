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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;

/**
 * Form Bean class for table VR_ACCOUNT_TRANSACTION
 * 
 * @author Mohit
 * 
 */
public class AccountTransactionBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1923864151572222817L;

	/*
	 * childAccountList is list of all the child Account of the transferring
	 * Account
	 */
	private ArrayList childAccountList = null;

	/* TtransactionId holds the id which is unique for each transaction. */
	private String transactionId = null;

	/*
	 * TransactionAmount holds the amount which is to be deducted form Source
	 * account.
	 */
	private String transactionAmount = null;

	/* ReceivedAccount holds the Code of the receivers account. */
	private String receivingAccount = null;

	/*
	 * stores the destination Id before confirming from the user :req for
	 * Corfirmation logic
	 */
	private String destinationId = null;

	/* Stores the amount credited to destination :req for Corfirmation logic */
	private String creditedAmount = null;

	/* stores the source Id :req for Corfirmation logic */
	private String sourceId = null;

	/* Boolean variable :req for Corfirmation logic */
	private boolean flag = false;

	/* Stores SourceId from the session :req for Corfirmation logic */
	private String contextSourceId = null;

	/* Stores rate applied for processing */
	private String rate = null;

	/* Stores rate applied for processing */
	private boolean async = false;
	
	/* Stores the Current Date */
	private String requestTime = null;
	
	/* Stores the PhysicalId */
	private String physicalId = null;

	/* Stores the Login Name */
	private String accountCode = null;
	
	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return the creditedAmount
	 */
	public String getCreditedAmount() {
		return creditedAmount;
	}

	/**
	 * @param creditedAmount
	 *            the creditedAmount to set
	 */
	public void setCreditedAmount(String creditedAmount) {
		if (!creditedAmount.equals("")) {
			this.creditedAmount = Utility.formatAmount(Double
					.parseDouble(creditedAmount));
		} else {
			this.creditedAmount = creditedAmount;
		}
	}

	/*
	 * Gets ArralyList of all the childs of the Source Account assoicated with
	 * this object
	 * 
	 * @return Returns the childAccountList.
	 */
	public ArrayList getChildAccountList() {
		return childAccountList;
	}

	/*
	 * Sets ArralyList of all the childs of the Source Account assoicated with
	 * this object
	 * 
	 * @param childAccountList The childAccountList to set.
	 */
	public void setChildAccountList(ArrayList getChildAccountList) {
		this.childAccountList = getChildAccountList;
	}

	/*
	 * Gets the Transaction Amount associated with this object
	 * 
	 * @return Returns the transactionAmount.
	 */
	public String getTransactionAmount() {
		return transactionAmount;
	}

	/*
	 * Sets the Transaction Amount associated with this object
	 * 
	 * @param transactionAmount The transactionAmount to set.
	 */
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = Utility.formatNumber(Double
				.parseDouble(transactionAmount));
	}

	/*
	 * Gets the Transaction Id associated with this object
	 * 
	 * @return Returns the transactionId.
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/*
	 * Sets the Transaction Amount associated with this object
	 * 
	 * @param transactionId The transactionId to set.
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/*
	 * Gets the Receiving Account associated with this object
	 * 
	 * @return Returns the receivingAccount.
	 */
	public String getReceivingAccount() {
		return receivingAccount;
	}

	/*
	 * Sets the Receiving Account associated with this object
	 * 
	 * @param receivingAccount The receivingAccount to set.
	 */
	public void setReceivingAccount(String receivingAccount) {
		this.receivingAccount = receivingAccount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.childAccountList = null;
		this.transactionId = null;
		this.transactionAmount = null;
		this.receivingAccount = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		// Replace with Actual code.This method is called if validate for
		// this action mapping is set to true. and if errors is not null and
		// not emoty it forwards to the page defined in input attritbute of the
		// ActionMapping
		return errors;
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
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the contextSourceId
	 */
	public String getContextSourceId() {
		return contextSourceId;
	}

	/**
	 * @param contextSourceId
	 *            the contextSourceId to set
	 */
	public void setContextSourceId(String contextSourceId) {
		this.contextSourceId = contextSourceId;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public boolean getAsync() {
		return async;
	}

	public void setAsync(boolean isAsync) {
		this.async = isAsync;
	}

	/**
	 * @return the requestTime
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime the requestTime to set
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
	 * @param physicalId the physicalId to set
	 */
	public void setPhysicalId(String physicalId) {
		this.physicalId = physicalId;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

}