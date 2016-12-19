package com.ibm.virtualization.recharge.dto;

import com.ibm.virtualization.recharge.common.TransactionType;

public class EnquiryTransaction extends BaseTransaction {

	/* The ID of the Source Account */
	private long sourceAccountId;

	/* The ID of the Destination Account */
	private long childAccountId;

	/* The Source Account Mobile Number */
	private String sourceMobileNo;

	/* The Destination Account Mobile Number */
	private String childMobileNo;

	/* The Transaction Type */
	private TransactionType transactionType;

	/* The Source Account Balance */
	private double sourceAccountBalance;

	/* The Desitnation Account Balance */
	private double childAccountBalance;

	/* The circle Id. */
	private int circleId = 0;
	
	/* The cell Id. */
	private String cellId;


	private UserSessionContext userSessionContext;

	/**
	 * @return the userSessionContext
	 */
	public UserSessionContext getUserSessionContext() {
		return userSessionContext;
	}

	/**
	 * @param userSessionContext
	 *            the userSessionContext to set
	 */
	public void setUserSessionContext(UserSessionContext userSessionContext) {
		this.userSessionContext = userSessionContext;
	}

	/**
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the childAccountBalance
	 */
	public double getChildAccountBalance() {
		return childAccountBalance;
	}

	/**
	 * @param childAccountBalance
	 *            the childAccountBalance to set
	 */
	public void setChildAccountBalance(double childAccountBalance) {
		this.childAccountBalance = childAccountBalance;
	}

	/**
	 * @return the childAccountId
	 */
	public long getChildAccountId() {
		return childAccountId;
	}

	/**
	 * @param childAccountId
	 *            the childAccountId to set
	 */
	public void setChildAccountId(long childAccountId) {
		this.childAccountId = childAccountId;
	}

	/**
	 * @return the childMobileNo
	 */
	public String getChildMobileNo() {
		return childMobileNo;
	}

	/**
	 * @param childMobileNo
	 *            the childMobileNo to set
	 */
	public void setChildMobileNo(String childMobileNo) {
		this.childMobileNo = childMobileNo;
	}

	/**
	 * @return the sourceAccountBalance
	 */
	public double getSourceAccountBalance() {
		return sourceAccountBalance;
	}

	/**
	 * @param sourceAccountBalance
	 *            the sourceAccountBalance to set
	 */
	public void setSourceAccountBalance(double sourceAccountBalance) {
		this.sourceAccountBalance = sourceAccountBalance;
	}

	/**
	 * @return the sourceAccountId
	 */
	public long getSourceAccountId() {
		return sourceAccountId;
	}

	/**
	 * @param sourceAccountId
	 *            the sourceAccountId to set
	 */
	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	/**
	 * @return the sourceMobileNo
	 */
	public String getSourceMobileNo() {
		return sourceMobileNo;
	}

	/**
	 * @param sourceMobileNo
	 *            the sourceMobileNo to set
	 */
	public void setSourceMobileNo(String sourceMobileNo) {
		this.sourceMobileNo = sourceMobileNo;
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
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue.append("<EnquiryTransaction>").append("<SourceAccountId>")
				.append(this.getSourceAccountId()).append("</SourceAccountId>")
				.append("<SourceMobileNo>").append(
						this.getSourceMobileNo()).append("</SourceMobileNo>")
				.append("<CircleId>").append(this.getCircleId()).append(
						"</CircleId>").append("<CellId>").append(this.getCellId()).append(
						"</CellId>");
		if (this.getChildMobileNo()!= null && this.getChildMobileNo().length() == 10) {
			retValue.append("<ChildMobileNo>").append(this.getChildMobileNo())
					.append("</ChildMobileNo>")	;
		}
		retValue.append(super.toString()).append("</EnquiryTransaction>");
		return retValue.toString();
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
