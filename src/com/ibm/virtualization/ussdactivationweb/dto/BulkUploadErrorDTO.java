/*
 * Created on Nov 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;

/**
 * @author Ravindra
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BulkUploadErrorDTO implements Serializable{
	private static final long serialVersionUID = 4328743l;
	private String strTotal;
	private String strSuccess;
	private String strFailure;
	private String strFailedTran;
	private String strFailureDetails;
	private String strTranDate;
	private String strRowNum;

	/**
	 * @return Returns the serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return Returns the strFailedTran.
	 */
	public String getStrFailedTran() {
		return strFailedTran;
	}
	/**
	 * @param strFailedTran The strFailedTran to set.
	 */
	public void setStrFailedTran(String strFailedTran) {
		this.strFailedTran = strFailedTran;
	}
	/**
	 * @return Returns the strFailure.
	 */
	public String getStrFailure() {
		return strFailure;
	}
	/**
	 * @param strFailure The strFailure to set.
	 */
	public void setStrFailure(String strFailure) {
		this.strFailure = strFailure;
	}
	/**
	 * @return Returns the strFailureDetails.
	 */
	public String getStrFailureDetails() {
		return strFailureDetails;
	}
	/**
	 * @param strFailureDetails The strFailureDetails to set.
	 */
	public void setStrFailureDetails(String strFailureDetails) {
		this.strFailureDetails = strFailureDetails;
	}
	/**
	 * @return Returns the strRowNum.
	 */
	public String getStrRowNum() {
		return strRowNum;
	}
	/**
	 * @param strRowNum The strRowNum to set.
	 */
	public void setStrRowNum(String strRowNum) {
		this.strRowNum = strRowNum;
	}
	/**
	 * @return Returns the strSuccess.
	 */
	public String getStrSuccess() {
		return strSuccess;
	}
	/**
	 * @param strSuccess The strSuccess to set.
	 */
	public void setStrSuccess(String strSuccess) {
		this.strSuccess = strSuccess;
	}
	/**
	 * @return Returns the strTotal.
	 */
	public String getStrTotal() {
		return strTotal;
	}
	/**
	 * @param strTotal The strTotal to set.
	 */
	public void setStrTotal(String strTotal) {
		this.strTotal = strTotal;
	}
	/**
	 * @return Returns the strTranDate.
	 */
	public String getStrTranDate() {
		return strTranDate;
	}
	/**
	 * @param strTranDate The strTranDate to set.
	 */
	public void setStrTranDate(String strTranDate) {
		this.strTranDate = strTranDate;
	}
	public String getFailure() {
		return strFailure;
	}
	/**
	 * @param failure The failure to set.
	 */
	public void setFailure(String failure) {
		this.strFailure = failure;
	}
	/**
	 * @return Returns the success.
	 */
	public String getSuccess() {
		return strSuccess;
	}
	/**
	 * @param success The success to set.
	 */
	public void setSuccess(String strSuccess) {
		this.strSuccess = strSuccess;
	}
	/**
	 * @return Returns the total.
	 */
	public String getTotal() {
		return strTotal;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(String strTotal) {
		this.strTotal = strTotal;
	}
	/**
	 * @return Returns the failedTransaction.
	 */
	public String getFailedTransaction() {
		return strFailedTran;
	}
	/**
	 * @param failedTransaction The failedTransaction to set.
	 */
	public void setFailedTransaction(String strFailedTran) {
		this.strFailedTran = strFailedTran;
	}
	/**
	 * @return Returns the fAILURE_DETAILS.
	 */
	public String getFailureDetails() {
		return strFailureDetails;
	}
	/**
	 * @param failure_details The fAILURE_DETAILS to set.
	 */
	public void setFailureDetails(String strFailureDetails) {
		this.strFailureDetails = strFailureDetails;
	}
	/**
	 * @return Returns the tRANSACTION_DATE.
	 */
	public String getTransactionDate() {
		return strTranDate;
	}
	/**
	 * @param transaction_date The tRANSACTION_DATE to set.
	 */
	public void setTransactionDate(String strTranDate) {
		this.strTranDate = strTranDate;
	}
}
