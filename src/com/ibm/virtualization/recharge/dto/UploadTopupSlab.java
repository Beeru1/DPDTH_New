package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

public class UploadTopupSlab implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int circleId;
	
	private String transactionType;

	private String startAmount;

	private String tillAmount;

	private String espCommission;

	private String pspCommission;

	private String serviceTax;

	private String processingFee;

	private String inCardGroup;

	private String processingCode;

	private String validity;

	private Date createdDt;

	private long createdBy;

	private Date updatedDt;

	private long updatedBy;

	private int topupConfigId;
	
	//private String status;
	
	private String filePath;
		

	/**
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the createdBy
	 */
	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
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
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the inCardGroup
	 */
	public String getInCardGroup() {
		return inCardGroup;
	}

	/**
	 * @param inCardGroup the inCardGroup to set
	 */
	public void setInCardGroup(String inCardGroup) {
		this.inCardGroup = inCardGroup;
	}

	/**
	 * @return the processingCode
	 */
	public String getProcessingCode() {
		return processingCode;
	}

	/**
	 * @param processingCode the processingCode to set
	 */
	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}

	/**
	 * @return the processingFee
	 */
	public String getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee the processingFee to set
	 */
	public void setProcessingFee(String processingFee) {
		this.processingFee = processingFee;
	}

	/**
	 * @return the pspCommission
	 */
	public String getPspCommission() {
		return pspCommission;
	}

	/**
	 * @param pspCommission the pspCommission to set
	 */
	public void setPspCommission(String pspCommission) {
		this.pspCommission = pspCommission;
	}

	/**
	 * @return the serviceTax
	 */
	public String getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the startAmount
	 */
	public String getStartAmount() {
		return startAmount;
	}

	/**
	 * @param startAmount the startAmount to set
	 */
	public void setStartAmount(String startAmount) {
		this.startAmount = startAmount;
	}

	/**
	 * @return the tillAmount
	 */
	public String getTillAmount() {
		return tillAmount;
	}

	/**
	 * @param tillAmount the tillAmount to set
	 */
	public void setTillAmount(String tillAmount) {
		this.tillAmount = tillAmount;
	}

	/**
	 * @return the topupConfigId
	 */
	public int getTopupConfigId() {
		return topupConfigId;
	}

	/**
	 * @param topupConfigId the topupConfigId to set
	 */
	public void setTopupConfigId(int topupConfigId) {
		this.topupConfigId = topupConfigId;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the updatedBy
	 */
	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
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

	
}

