package com.ibm.virtualization.recharge.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;

public class PostPaidBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7484769563839986774L;

	/*
	 * payementType represents the Type of phone for which the payment is to be
	 * made. It can be either ATBS or Mobile
	 */
	private String paymentType;

	/*
	 * receivingMobileNo represents the mobile no of the receiver(retailer)
	 * which will make the payment on behalf of the logged in User
	 */
	private String receivingNo;

	/*
	 * Amount specifies the Bill Amount which is to be paid
	 */
	private String amount=null;

	/*
	 * If the paymentType is ABTS then it is shown. It represents the mobile no
	 * on which the msg will be received
	 */
	private String confirmMobileNo="";
	
	/*
	 * If the paymentType is DTH then it is shown. It represents the DTH no
	 * on which the msg will be received
	 */
	
	private String confirmDTHNo;

	/*
	 * it represents the method to be called
	 */
	private String methodName;
		
	private String physicalId;
	
	private String requestTime; 
	
	/* Boolean variable :req for Corfirmation logic */
	private boolean flag = false;
	
	/* Stores SourceId from the session :req for Corfirmation logic */
	private String contextSourceId = null;
	
	/* espCommission holds the espCommission for the Transaction */
	private String espCommission = null;
	
	private String accountCode = null;
	
	/* stores the amount to be debited from the Requester */
	private String debitAmount = null;
	
	/* stores the amount to be credited to the receiver */
	private String creditAmount = null;
	
	private String transactionId ;
	
	private String transactionType; 
	
	private ArrayList displayDetails; 
	
	private String postPaidFlag;
	
	
	public String getPostPaidFlag() {
		return postPaidFlag;
	}

	public void setPostPaidFlag(String postPaidFlag) {
		this.postPaidFlag = postPaidFlag;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the espCommission
	 */
	public String getEspCommission() {
		return espCommission;
	}

	/**
	 * @param espCommission the espCommission to set
	 */
	public void setEspCommission(String espCommission) {
		this.espCommission = espCommission;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * This function is used to reset the values of QueryFormbean to some
	 * initial value.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		this.receivingNo=null;
		this.amount=null;
		this.methodName = null;
		this.confirmMobileNo=null;
		this.paymentType=null;
		
	}



	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		if(amount!=null && !(amount.equalsIgnoreCase(""))){
			this.amount = Utility.formatNumber(Double.parseDouble(amount));
		}else{
				this.amount = amount;
		}
		
	}

	/**
	 * @return the confirmMobileNo
	 */
	public String getConfirmMobileNo() {
		return confirmMobileNo;
	}

	/**
	 * @param confirmMobileNo the confirmMobileNo to set
	 */
	public void setConfirmMobileNo(String confirmMobileNo) {
		this.confirmMobileNo = confirmMobileNo;
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
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the receivingNo
	 */
	public String getReceivingNo() {
		return receivingNo;
	}

	/**
	 * @param receivingNo the receivingNo to set
	 */
	public void setReceivingNo(String receivingNo) {
		this.receivingNo = receivingNo;
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
	 * @return the contextSourceId
	 */
	public String getContextSourceId() {
		return contextSourceId;
	}

	/**
	 * @param contextSourceId the contextSourceId to set
	 */
	public void setContextSourceId(String contextSourceId) {
		this.contextSourceId = contextSourceId;
	}

	/**
	 * @return the creditAmount
	 */
	public String getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @param creditAmount the creditAmount to set
	 */
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * @return the debitAmount
	 */
	public String getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @param debitAmount the debitAmount to set
	 */
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	/**
	 * @return the confirmDTHNo
	 */
	public String getConfirmDTHNo() {
		return confirmDTHNo;
	}

	/**
	 * @param confirmDTHNo the confirmDTHNo to set
	 */
	public void setConfirmDTHNo(String confirmDTHNo) {
		this.confirmDTHNo = confirmDTHNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	/**
	 * @return the displayDetails
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}

	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}
}
