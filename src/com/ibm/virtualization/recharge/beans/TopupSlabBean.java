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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.ibm.virtualization.recharge.common.Utility;

/**
 * Form bean for a Circle Topup Config module.
 * 
 * @version 1.0
 * @author Kumar Saurabh
 */
public class TopupSlabBean extends ActionForm

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String inCardGroup = null;

	private String espCommission = null;

	private String pspCommission = null;

	private String startAmount = null;

	private String validity = null;

	private String circleId = null;

	private String serviceTax = null;

	private String tillAmount = null;

	private String processingFee = null;

	private String processingCode = null;

	private String methodName = null;

	private String topupConfigId = null;

	private String transactionType = null;

	private String circleName = null;

	private String endAmount = null;

	private String startDate = null;

	private String endDate = null;

	private boolean flagForCircleDisplay;

	private ArrayList circleList = null;

	private List topupList = null;

	private String status = null;

	private String isCircleAdmin = null;

	private String disabledCircle = null;

	private String editStatus = null;
	
	private String listStatus = null;
	
	private FormFile uploadFile; 
		
	private String HdnCircleId = null;
	
	private boolean viewFailedRecords;
	
	private String fileId = null;
	
	private String page;
	
	
	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the circleList
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList
	 *            the circleList to set
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
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
		if (espCommission.trim().equals(""))
			this.espCommission = espCommission;
		else
			this.espCommission = Utility.formatAmount(Double
					.parseDouble(espCommission));

	}

	/**
	 * @return the inCardGroup
	 */
	public String getInCardGroup() {
		return inCardGroup;
	}

	/**
	 * @param inCardGroup
	 *            the inCardGroup to set
	 */
	public void setInCardGroup(String inCardGroup) {
		this.inCardGroup = inCardGroup;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the processingCode
	 */
	public String getProcessingCode() {
		return processingCode;
	}

	/**
	 * @param processingCode
	 *            the processingCode to set
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
	 * @param processingFee
	 *            the processingFee to set
	 */
	public void setProcessingFee(String processingFee) {
		
		
		if (processingFee.trim().equals(""))
			this.processingFee = processingFee;
		else
			this.processingFee = Utility.formatAmountPfee(Double
					.parseDouble(processingFee));
		
	}

	/**
	 * @return the pspCommission
	 */
	public String getPspCommission() {
		return pspCommission;
	}

	/**
	 * @param pspCommission
	 *            the pspCommission to set
	 */
	public void setPspCommission(String pspCommission) {
		if (pspCommission.trim().equals(""))
			this.pspCommission = pspCommission;
		else
			this.pspCommission = Utility.formatAmount(Double
					.parseDouble(pspCommission));
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
		if (serviceTax.trim().equals(""))
			this.serviceTax = serviceTax;
		else
			this.serviceTax = Utility.formatAmount(Double
					.parseDouble(serviceTax));
	}

	/**
	 * @return the startAmount
	 */
	public String getStartAmount() {
		return startAmount;
	}

	/**
	 * @param startAmount
	 *            the startAmount to set
	 */
	public void setStartAmount(String startAmount) {
		if (startAmount.trim().equals(""))
			this.startAmount = startAmount;
		else
			this.startAmount = Utility.formatAmount(Double
					.parseDouble(startAmount));
	}

	/**
	 * @return the tillAmount
	 */
	public String getTillAmount() {
		return tillAmount;
	}

	/**
	 * @param tillAmount
	 *            the tillAmount to set
	 */
	public void setTillAmount(String tillAmount) {
		if (tillAmount.trim().equals(""))
			this.tillAmount = tillAmount;
		else
			this.tillAmount = Utility.formatAmount(Double
					.parseDouble(tillAmount));
	}

	/**
	 * @return the topupConfigId
	 */
	public String getTopupConfigId() {
		return topupConfigId;
	}

	/**
	 * @param topupConfigId
	 *            the topupConfigId to set
	 */
	public void setTopupConfigId(String topupConfigId) {
		this.topupConfigId = topupConfigId;
	}

	/**
	 * @return the topupList
	 */
	public List getTopupList() {
		return topupList;
	}

	/**
	 * @param topupList
	 *            the topupList to set
	 */
	public void setTopupList(List topupList) {
		this.topupList = topupList;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the isCircleAdmin
	 */
	public String getIsCircleAdmin() {
		return isCircleAdmin;
	}

	/**
	 * @param isCircleAdmin
	 *            the isCircleAdmin to set
	 */
	public void setIsCircleAdmin(String isCircleAdmin) {
		this.isCircleAdmin = isCircleAdmin;
	}

	/**
	 * @return the disabledCircle
	 */
	public String getDisabledCircle() {
		return disabledCircle;
	}

	/**
	 * @param disabledCircle
	 *            the disabledCircle to set
	 */
	public void setDisabledCircle(String disabledCircle) {
		this.disabledCircle = disabledCircle;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset values are provided as samples only. Change as appropriate.

		inCardGroup = null;
		espCommission = null;
		pspCommission = null;
		startAmount = null;
		validity = null;
		circleId = null;
		serviceTax = null;
		tillAmount = null;
		processingFee = null;
		processingCode = null;
		topupConfigId = null;
		transactionType = null;
		circleList = null;
		status = null;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(String endAmount) {
		this.endAmount = endAmount;
	}

	/**
	 * @return the flagForCircleDisplay
	 */
	public boolean isFlagForCircleDisplay() {
		return flagForCircleDisplay;
	}

	/**
	 * @param flagForCircleDisplay
	 *            the flagForCircleDisplay to set
	 */
	public void setFlagForCircleDisplay(boolean flagForCircleDisplay) {
		this.flagForCircleDisplay = flagForCircleDisplay;
	}

	/**
	 * @return the validity
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * @param validity
	 *            the validity to set
	 */
	public void setValidity(String validity) {
		this.validity = validity;
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

	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}	

	/**
	 * @return the hdnCircleId
	 */
	public String getHdnCircleId() {
		return HdnCircleId;
	}

	/**
	 * @param hdnCircleId the hdnCircleId to set
	 */
	public void setHdnCircleId(String hdnCircleId) {
		HdnCircleId = hdnCircleId;
	}



	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the viewFailedRecords
	 */
	public boolean isViewFailedRecords() {
		return viewFailedRecords;
	}

	/**
	 * @param viewFailedRecords the viewFailedRecords to set
	 */
	public void setViewFailedRecords(boolean viewFailedRecords) {
		this.viewFailedRecords = viewFailedRecords;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}




}
