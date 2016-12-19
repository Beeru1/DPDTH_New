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
 * Form Bean class for table CUSTOMER_TRANSACTIONS
 * 
 * @author Mohit
 * 
 */
public class CustomerTransactionFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7601585159094857208L;

	/* AccountCode holds the Code of the Sellers Account. */
	private String accountCode = null;

	/*
	 * TransactionAmount holds the amount which is to be deducted from the
	 * Source account.
	 */
	private String transactionAmount = null;

	/* MobileNo holds customers mobile number. */
	private String mobileNo = null;
	
	private String confirmMobileNo = null;

	/* DebitAmount holds Amount to be deducted from Source Account. */
	private String debitAmount = null;

	/* ServiceTax holds the serviceTax for the Transaction */
	private String serviceTax = null;

	/* processingFee holds the processingFee for the Transaction */
	private String processingFee = null;

	/* creditedAmount holds the Amount to be added to the Reciver Account */
	private String creditedAmount = null;

	/* espCommission holds the espCommission for the Transaction */
	private String espCommission = null;

	/* Boolean variable :req for Corfirmation logic */
	private boolean flag = false;
	
	private boolean transFlag = false;
	
	/* transaction id holds transaction id for transaction */
	private String transactionId=null;
	
	private String transationType=null;
	
	private String sourceAccount=null;
	
	private String talkTime=null;
	
	private String transactionDate=null;
	
	private String status=null;
	
	private String reason=null;
	
	private String startDate;

	private String endDate;
	
	private ArrayList displayDetails;
	
	private String methodName;
	
	private boolean async = false;

	/* Stores the Current Date */
	private String requestTime;
	
	/* Stores the PhysicalId */
	private String physicalId;
	
	/* card group */
	private String cardGroup;
	
	private String transDetailId;
	
	private String searchCriteria;	
	
	private String page;
	
	private String abtsNo;
	
	private String dthNo;
	
	private boolean flagPostPaid =false;
	
	
	public boolean getFlagPostPaid() {
		return flagPostPaid;
	}

	public void setFlagPostPaid(boolean flagPostPaid) {
		this.flagPostPaid = flagPostPaid;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @return the transDetailId
	 */
	public String getTransDetailId() {
		return transDetailId;
	}

	/**
	 * @param transDetailId the transDetailId to set
	 */
	public void setTransDetailId(String transDetailId) {
		this.transDetailId = transDetailId;
	}

	/**
	 * @return the cardGroup
	 */
	public String getCardGroup() {
		return cardGroup;
	}

	/**
	 * @param cardGroup the cardGroup to set
	 */
	public void setCardGroup(String cardGroup) {
		this.cardGroup = cardGroup;
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

	/*
	 * Gets the AccountCode of the Source Account associated with this object
	 * 
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/*
	 * Sets the AccountCode of the Source Account associated with this object
	 * 
	 * @param accountCode The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/*
	 * Gets the Mobile Number of the reciever associated with this object
	 * 
	 * @return Returns the mobileNo.
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/*
	 * Sets the Mobile Number of the reciever associated with this object
	 * 
	 * @param mobileNo The mobileNo to set.
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
		 
		if(transactionAmount!=null && !(transactionAmount.equalsIgnoreCase(""))){
				this.transactionAmount = Utility.formatNumber(Double
				.parseDouble(transactionAmount));
		}else{
				this.transactionAmount = transactionAmount;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */public void reset(ActionMapping mapping, HttpServletRequest request) {

		accountCode = null;
		transactionAmount = null;
		mobileNo = null;
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

	/**
	 * @return the debitAmount
	 */
	public String getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @param debitAmount
	 *            the debitAmount to set
	 */
	public void setDebitAmount(String debitAmount) {
		if (!debitAmount.equals("")) {
			this.debitAmount = Utility.formatAmount(Double
					.parseDouble(debitAmount));
		} else {
			this.debitAmount = debitAmount;
		}
	}

	/**
	 * @return the espCommission
	 */
	public String getEspCommission() {
		return espCommission;
	}

	/**
	 * @param espCommission
	 *            the espCommission to set
	 */
	public void setEspCommission(String espCommission) {
		if (!espCommission.equals("")) {
			this.espCommission = Utility.formatAmount(Double
					.parseDouble(espCommission));
		} else {
			this.espCommission = espCommission;
		}
	}

	/**
	 * @return the processingFee
	 */
	public String getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee
	 *            the processingFee to set
	 */
	public void setProcessingFee(String processingFee) {
		if (!processingFee.equals("")) {
			this.processingFee = Utility.formatAmount(Double
					.parseDouble(processingFee));
		} else {
			this.processingFee = processingFee;
		}
	}

	/**
	 * @return the serviceTax
	 */
	public String getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax
	 *            the serviceTax to set
	 */
	public void setServiceTax(String serviceTax) {
		if (!serviceTax.equals("")) {
			this.serviceTax = Utility.formatAmount(Double
					.parseDouble(serviceTax));
		} else {
			this.serviceTax = serviceTax;
		}
	}

	public boolean getAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transationType;
	}

	public void setTransactionType(String transactionType) {
		this.transationType = transactionType;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the displayDetails
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}

	/**
	 * @param displayDetails the displayDetails to set
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	 * @return the transFlag
	 */
	public boolean isTransFlag() {
		return transFlag;
	}

	/**
	 * @param transFlag the transFlag to set
	 */
	public void setTransFlag(boolean transFlag) {
		this.transFlag = transFlag;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getConfirmMobileNo() {
		return confirmMobileNo;
	}

	public void setConfirmMobileNo(String confirmMobileNo) {
		this.confirmMobileNo = confirmMobileNo;
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
