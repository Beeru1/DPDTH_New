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
import java.util.Date;

/**
 * This class is used for Data Transfer of a Circle Topup Configuration between
 * different layers.
 * 
 * @author Kumar Saurabh Created On Thu Aug 23 16:33:04 IST 2007
 */
public class CircleTopupConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int circleId;
	
	private String transactionType;

	private double startAmount;

	private double tillAmount;

	private double espCommission;

	private double pspCommission;

	private double serviceTax;

	private double processingFee;

	private String inCardGroup;

	private String processingCode;

	private int validity;
	
	private int commisionGroupId;

	private Date createdDt;

	private long createdBy;

	private Date updatedDt;

	private long updatedBy;

	private int topupConfigId;
	
	/* Added as per the reports requirement */
	private String circleName;
	
	private double endAmount;
	
	private String status;
	
	private String loginName;

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

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** Creates a dto for the VR_CIRCLE_TOPUP_CONFIG table */
	public CircleTopupConfig() {
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
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            The createdDt to set.
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return Returns the espCommission.
	 */
	public double getEspCommission() {
		return espCommission;
	}

	/**
	 * @param espCommission
	 *            The espCommission to set.
	 */
	public void setEspCommission(double espCommission) {
		this.espCommission = espCommission;
	}

	/**
	 * @return Returns the inCardGroup.
	 */
	public String getInCardGroup() {
		return inCardGroup;
	}

	/**
	 * @param inCardGroup
	 *            The inCardGroup to set.
	 */
	public void setInCardGroup(String inCardGroup) {
		this.inCardGroup = inCardGroup;
	}

	/**
	 * @return Returns the processingCode.
	 */
	public String getProcessingCode() {
		return processingCode;
	}

	/**
	 * @param processingCode
	 *            The processingCode to set.
	 */
	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}

	/**
	 * @return Returns the processingFee.
	 */
	public double getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee
	 *            The processingFee to set.
	 */
	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}

	/**
	 * @return Returns the pspCommission.
	 */
	public double getPspCommission() {
		return pspCommission;
	}

	/**
	 * @param pspCommission
	 *            The pspCommission to set.
	 */
	public void setPspCommission(double pspCommission) {
		this.pspCommission = pspCommission;
	}

	/**
	 * @return Returns the serviceTax.
	 */
	public double getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax
	 *            The serviceTax to set.
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return Returns the startAmount.
	 */
	public double getStartAmount() {
		return startAmount;
	}

	/**
	 * @param startAmount
	 *            The startAmount to set.
	 */
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}

	/**
	 * @return Returns the tillAmount.
	 */
	public double getTillAmount() {
		return tillAmount;
	}

	/**
	 * @param tillAmount
	 *            The tillAmount to set.
	 */
	public void setTillAmount(double tillAmount) {
		this.tillAmount = tillAmount;
	}

	/**
	 * @return Returns the topupConfigId.
	 */
	public int getTopupConfigId() {
		return topupConfigId;
	}

	/**
	 * @param topupConfigId
	 *            The topupConfigId to set.
	 */
	public void setTopupConfigId(int topupConfigId) {
		this.topupConfigId = topupConfigId;
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
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            The updatedDt to set.
	 */
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * @return Returns the validity.
	 */
	public int getValidity() {
		return validity;
	}

	/**
	 * @param validity
	 *            The validity to set.
	 */
	public void setValidity(int validity) {
		this.validity = validity;
	}
	
	

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

		retValue = "CircleTopupConfig ( " + super.toString() + TAB
				+ "circleId = " + this.circleId + TAB + "transactionType = "
				+ this.transactionType + TAB + "startAmount = "
				+ this.startAmount + TAB + "tillAmount = " + this.tillAmount
				+ TAB + "espCommission = " + this.espCommission + TAB
				+ "pspCommission = " + this.pspCommission + TAB
				+ "serviceTax = " + this.serviceTax + TAB + "processingFee = "
				+ this.processingFee + TAB + "inCardGroup = "
				+ this.inCardGroup + TAB + "processingCode = "
				+ this.processingCode + TAB + "validity = " + this.validity
				+ TAB + "createdDt = " + this.createdDt + TAB + "createdBy = "
				+ this.createdBy + TAB + "updatedDt = " + this.updatedDt + TAB
				+ "updatedBy = " + this.updatedBy + TAB + "topupConfigId = "
				+ this.topupConfigId + TAB +  "status = "
				+ this.status + "\n" + " )";

		return retValue;
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
	 * @return the endAmount
	 */
	public double getEndAmount() {
		return endAmount;
	}

	/**
	 * @param endAmount the endAmount to set
	 */
	public void setEndAmount(double endAmount) {
		this.endAmount = endAmount;
	}

	public int getCommisionGroupId() {
		return commisionGroupId;
	}

	public void setCommisionGroupId(int commisionGroupId) {
		this.commisionGroupId = commisionGroupId;
	}
}
