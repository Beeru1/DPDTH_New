package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.Date;

import com.ibm.virtualization.recharge.common.PaymentMode;

/**
 * @author Code Generator Created On Wed Aug 08 10:59:49 IST 2007 Auto Generated
 *         Data Trasnfer Object class for table REC_TRANSACTION
 * 
 */
public class DistributorTransaction extends BaseTransaction implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2140156258828826077L;

	private long accountId;

	private String accountCode;

	private long chqccNumber;

	private Date chequeDate;

	private Date ccvalidDate;

	private String bankName;

	private String reviewStatus;

	private String reviewComment;

	private double rate;

	private String createdByName;
	
	private PaymentMode paymentMode;
	
	private String ecsno;
	
	private String purchaseOrderNo;
	
	private Date purchaseOrderDate;
	
	private String accountName;
	
	private Date reviewedDate;
	
	private String reviewedBy;
	
	private String rowNum;
	
	private int totalRecords;
	

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEcsno() {
		return ecsno;
	}

	public void setEcsno(String ecsno) {
		this.ecsno = ecsno;
	}

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
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

	/**
	 * @return Returns the rate.
	 */
	public double getRate() {
		return rate;
	}

	public String getStrRate() {
		return rate + "";
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(double rate) {
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
		this.reviewComment = ReviewComment;
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
		this.reviewStatus = ReviewStatus;
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
	public DistributorTransaction() {
	}

	/**
	 * Get accountId associated with this object.
	 * 
	 * @return accountId
	 */

	public long getAccountId() {
		return accountId;
	}

	/**
	 * Set accountId associated with this object.
	 * 
	 * @param accountId
	 *            The accountId value to set
	 */

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * Get chqccNumber associated with this object.
	 * 
	 * @return chqccNumber
	 */

	public long getChqccNumber() {
		return chqccNumber;
	}

	/**
	 * Set chqccNumber associated with this object.
	 * 
	 * @param chqccNumber
	 *            The chqccNumber value to set
	 */

	public void setChqccNumber(long chqccNumber) {
		this.chqccNumber = chqccNumber;
	}

	/**
	 * Get chequeDate associated with this object.
	 * 
	 * @return chequeDate
	 */

	public Date getChequeDate() {
		return chequeDate;
	}

	/**
	 * Set chequeDate associated with this object.
	 * 
	 * @param chequeDate
	 *            The chequeDate value to set
	 */

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * Get ccvalidDate associated with this object.
	 * 
	 * @return ccvalidDate
	 */

	public Date getCcvalidDate() {
		return ccvalidDate;
	}

	/**
	 * Set ccvalidDate associated with this object.
	 * 
	 * @param ccvalidDate
	 *            The ccvalidDate value to set
	 */

	public void setCcvalidDate(Date ccvalidDate) {
		this.ccvalidDate = ccvalidDate;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
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
	 * @return the reviewedDate
	 */
	public Date getReviewedDate() {
		return reviewedDate;
	}

	/**
	 * @param reviewedDate the reviewedDate to set
	 */
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
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

		retValue = "DistributorTransaction ( " + super.toString() + TAB
				+ "accountId = " + this.accountId + TAB + "accountCode = "
				+ this.accountCode + TAB + "chqccNumber = " + this.chqccNumber
				+ TAB + "chequeDate = " + this.chequeDate + TAB
				+ "ccvalidDate = " + this.ccvalidDate + TAB + "bankName = "
				+ this.bankName + TAB + "reviewStatus = " + this.reviewStatus
				+ TAB + "reviewComment = " + this.reviewComment + TAB
				+ "rate = " + this.rate + TAB + "createdByName = "
				+ this.createdByName + TAB + " )";

		return retValue;
	}

	/**
	 * @return the paymentMode
	 */
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	

}
