package com.ibm.virtualization.recharge.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;

/**
 * @author Code Generator Created On Wed Aug 08 10:59:49 IST 2007 Auto Generated
 *         Form Bean class for table REC_TRANSACTION
 * 
 */
public class DistributorTransactionFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4511370066209843587L;

	private String circleId;

	private String transactionId;

	private String accountId;

	private String accountCode;

	private String transactionAmount;

	private String creditedAmount;

	private String transactionDate;

	private String requestType;

	private String paymentMode;

	private String chqccNumber;

	private String chequeDate;

	private String ccvalidDate;

	private String createdBy;

	private String updatedBy;

	private String createdTime;

	private String updatedTime;

	private String methodName;

	private String bankName;

	private String channelType;

	private String transactionListType;

	private ArrayList transactionList;

	private String reviewStatus;

	private String reviewComment;

	private String rate;

	private String createdByName;

	private ArrayList circleList;

	private ArrayList accountList;

	private String disableCircle;

	private String submitStatus = "0";

	private String ecsno;

	private String purchaseOrderNo;

	private String purchaseOrderDate;

	private String accountName;

	private String startDate;

	private String endDate;

	private String circleName;

	private boolean flagForCircleDisplay;

	private String reviewedDate;

	private String reviewedBy;
	
	private String editStatus;
	
	private String flag;
	
	private String page;
	
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return the flagForCircleDisplay
	 */
	public boolean isFlagForCircleDisplay() {
		return flagForCircleDisplay;
	}

	/**
	 * @param flagForCircleDisplay the flagForCircleDisplay to set
	 */
	public void setFlagForCircleDisplay(boolean flagForCircleDisplay) {
		this.flagForCircleDisplay = flagForCircleDisplay;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = "0";
	}

	/**
	 * @return Returns the accountList.
	 */
	public ArrayList getAccountList() {
		return accountList;
	}

	/**
	 * @param accountList
	 *            The accountList to set.
	 */
	public void setAccountList(ArrayList accountList) {
		this.accountList = accountList;
	}

	/**
	 * @return Returns the circleList.
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList
	 *            The circleList to set.
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}

	/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            The circleId to set.
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
	 * @return Returns the rate.
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the reviewComment.
	 */
	public String getReviewComment() {
		return reviewComment;
	}

	/**
	 * @param reviewComment
	 *            The reviewComment to set.
	 */
	public void setReviewComment(String ReviewComment) {
		reviewComment = ReviewComment;
	}

	/**
	 * @return Returns the reviewStatus.
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}

	/**
	 * @param reviewStatus
	 *            The reviewStatus to set.
	 */
	public void setReviewStatus(String ReviewStatus) {
		reviewStatus = ReviewStatus;
	}

	/**
	 * @return Returns the transactionList.
	 */
	public ArrayList getTransactionList() {
		return transactionList;
	}

	/**
	 * @param transactionList
	 *            The transactionList to set.
	 */
	public void setTransactionList(ArrayList transactionList) {
		this.transactionList = transactionList;
	}

	/**
	 * @return Returns the transactionListType.
	 */
	public String getTransactionListType() {
		return transactionListType;
	}

	/**
	 * @param transactionListType
	 *            The transactionListType to set.
	 */
	public void setTransactionListType(String transactionListType) {
		this.transactionListType = transactionListType;
	}

	/**
	 * @return Returns the channelType.
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType
	 *            The channelType to set.
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @return Returns the bankName.
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            The bankName to set.
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/** Creates a dto for the REC_TRANSACTION table */
	public DistributorTransactionFormBean() {
	}

	/**
	 * Get transactionId associated with this object.
	 * 
	 * @return transactionId
	 */

	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Set transactionId associated with this object.
	 * 
	 * @param transactionId
	 *            The transactionId value to set
	 */

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Get accountId associated with this object.
	 * 
	 * @return accountId
	 */

	public String getAccountId() {
		return accountId;
	}

	/**
	 * Set accountId associated with this object.
	 * 
	 * @param accountId
	 *            The accountId value to set
	 */

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * Get transactionAmount associated with this object.
	 * 
	 * @return transactionAmount
	 */

	public String getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * Set transactionAmount associated with this object.
	 * 
	 * @param transactionAmount
	 *            The transactionAmount value to set
	 */

	public void setTransactionAmount(String transactionAmt) {
		this.transactionAmount = Utility.formatNumber(Double
				.parseDouble(transactionAmt));
	}

	/**
	 * Get creditedAmount associated with this object.
	 * 
	 * @return creditedAmount
	 */

	public String getCreditedAmount() {
		return creditedAmount;
	}

	/**
	 * Set creditedAmount associated with this object.
	 * 
	 * @param creditedAmount
	 *            The creditedAmount value to set
	 */

	public void setCreditedAmount(String creditedAmt) {
		this.creditedAmount = Utility.formatAmount(Double
				.parseDouble(creditedAmt));
	}

	/**
	 * Get transactionDate associated with this object.
	 * 
	 * @return transactionDate
	 */

	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Set transactionDate associated with this object.
	 * 
	 * @param transactionDate
	 *            The transactionDate value to set
	 */

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * Get chqccNumber associated with this object.
	 * 
	 * @return chqccNumber
	 */

	public String getChqccNumber() {
		return chqccNumber;
	}

	/**
	 * Set chqccNumber associated with this object.
	 * 
	 * @param chqccNumber
	 *            The chqccNumber value to set
	 */

	public void setChqccNumber(String chqccNumber) {
		this.chqccNumber = chqccNumber;
	}

	/**
	 * Get chequeDate associated with this object.
	 * 
	 * @return chequeDate
	 */

	public String getChequeDate() {
		return chequeDate;
	}

	/**
	 * Set chequeDate associated with this object.
	 * 
	 * @param chequeDate
	 *            The chequeDate value to set
	 */

	public void setChequeDate(String chequeDate) {
		if(chequeDate != null && chequeDate.indexOf(" 00:00:00.0") > -1)
			this.chequeDate = chequeDate.substring(0, chequeDate.indexOf(" 00:00:00.0"));
		else
			this.chequeDate = chequeDate;
	}

	/**
	 * Get ccvalidDate associated with this object.
	 * 
	 * @return ccvalidDate
	 */

	public String getCcvalidDate() {
		return ccvalidDate;
	}

	/**
	 * Set ccvalidDate associated with this object.
	 * 
	 * @param ccvalidDate
	 *            The ccvalidDate value to set
	 */

	public void setCcvalidDate(String ccvalidDate) {
		
		if(ccvalidDate != null && ccvalidDate.indexOf(" 00:00:00.0") > -1)
			this.ccvalidDate = ccvalidDate.substring(0, ccvalidDate.indexOf(" 00:00:00.0"));
		else
			this.ccvalidDate = ccvalidDate;
		
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get updatedBy associated with this object.
	 * 
	 * @return updatedBy
	 */

	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy associated with this object.
	 * 
	 * @param updatedBy
	 *            The updatedBy value to set
	 */

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Get createdTime associated with this object.
	 * 
	 * @return createdTime
	 */

	public String getCreatedTime() {
		return createdTime;
	}

	/**
	 * Set createdTime associated with this object.
	 * 
	 * @param createdTime
	 *            The createdTime value to set
	 */

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * Get updatedTime associated with this object.
	 * 
	 * @return updatedTime
	 */

	public String getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * Set updatedTime associated with this object.
	 * 
	 * @param updatedTime
	 *            The updatedTime value to set
	 */

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return Returns the createdByName.
	 */
	public String getCreatedByName() {
		return createdByName;
	}

	/**
	 * @param createdByName
	 *            The createdByName to set.
	 */
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset values are provided as samples only. Change as appropriate.

		transactionId = null;
		chqccNumber = null;
		ccvalidDate = null;
		transactionAmount = null;
		accountId = null;
		accountCode = null;
		requestType = null;
		createdByName = null;
		creditedAmount = null;
		channelType = null;
		transactionListType = null;
		bankName = null;
		chequeDate = null;
		methodName = null;
		paymentMode = null;
		ecsno = null;
		purchaseOrderNo = null;
		purchaseOrderDate = null;
		reviewComment = null;

	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentType) {
		this.paymentMode = paymentType;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getDisableCircle() {
		return disableCircle;
	}

	public void setDisableCircle(String disableCircle) {
		this.disableCircle = disableCircle;
	}

	public String getEcsno() {
		return ecsno;
	}

	public void setEcsno(String ecsno) {
		this.ecsno = ecsno;
	}

	public String getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(String purchageOrderDate) {
		if(purchaseOrderDate != null && purchaseOrderDate.indexOf(" 00:00:00.0") > -1){
			this.purchaseOrderDate = purchaseOrderDate.substring(0, purchaseOrderDate.indexOf(" 00:00:00.0"));
		}else{
				this.purchaseOrderDate = purchageOrderDate;
		}
		
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchageOrderNo) {
		this.purchaseOrderNo = purchageOrderNo;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(String reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	/**
	 * @return the editStatus
	 */
	public String getEditStatus() {
		return editStatus;
	}

	/**
	 * @param editStatus the editStatus to set
	 */
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
