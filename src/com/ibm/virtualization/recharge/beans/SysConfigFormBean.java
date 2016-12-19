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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;

/**
 * Form Bean class for table VR_SYSTEM_CONFIG
 * 
 * @author Navroze
 * 
 */

public class SysConfigFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8048769623045694769L;

	private String sysConfigId;

	private String circleId;

	private String channelName;

	private String transactionType;

	private String createdBy;

	private String updatedBy;

	private String createdDt;

	private String updatedDt;

	private String circleName;

	private String methodName;

	private ArrayList sysConfigList;

	private String minAmount=null;

	private String maxAmount=null;
	
	private String isCircleAdmin;
	
	private String disabledCircle;
	
	private String editStatus;
	
	private String selectedCircleId;

	
	
	/**
	 * @return the selectedCircleId
	 */
	public String getSelectedCircleId() {
		return selectedCircleId;
	}

	/**
	 * @param selectedCircleId the selectedCircleId to set
	 */
	public void setSelectedCircleId(String selectedCircleId) {
		this.selectedCircleId = selectedCircleId;
	}

	/**
	 * @return Returns the sysConfigId.
	 */
	public String getSysConfigId() {
		return sysConfigId;
	}

	/**
	 * @param sysConfigId
	 *            The sysConfigId to set.
	 */
	public void setSysConfigId(String sysConfigId) {
		this.sysConfigId = sysConfigId;
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
	 * @return Returns the circleName.
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName
	 *            The circleName to set.
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return Returns the channelName.
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName
	 *            The channelName to set.
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return Returns the createdBy.
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            The createdBy to set.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Returns the createdDt.
	 */
	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            The createdDt to set.
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
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
	 * @return Returns the sysConfigList.
	 */
	public ArrayList getSysConfigList() {
		return sysConfigList;
	}

	/**
	 * @param sysConfigList
	 *            The sysConfigList to set.
	 */
	public void setSysConfigList(ArrayList sysConfigList) {
		this.sysConfigList = sysConfigList;
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
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            The updatedBy to set.
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the updatedDt.
	 */
	public String getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            The updatedDt to set.
	 */
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * Reset method for the form bean.
	 * 
	 * @param mapping
	 * @param request
	 * 
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		/* Reset field values here. * */
		circleId = null;
		sysConfigId = null;
		channelName = null;
		transactionType = null;
		minAmount=null;
		maxAmount=null;

	}

	/**
	 * @return the maxAmount
	 */
	public String getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param maxAmount
	 *            the maxAmount to set
	 */
	public void setMaxAmount(String maxAmount) {
		if(maxAmount.trim().equals(""))
    		this.maxAmount = maxAmount;
    	else
		this.maxAmount = Utility.formatAmount(Double.parseDouble(maxAmount));
	}

	/**
	 * @return the minAmount
	 */
	public String getMinAmount() {
		return minAmount;
	}

	/**
	 * @param minAmount
	 *            the minAmount to set
	 */
	public void setMinAmount(String minAmount) {
		if(minAmount.trim().equals(""))
    		this.minAmount = minAmount;
    	else
		this.minAmount = Utility.formatAmount(Double.parseDouble(minAmount));
	}

	/**
	 * @return the disabledCircle
	 */
	public String getDisabledCircle() {
		return disabledCircle;
	}

	/**
	 * @param disabledCircle the disabledCircle to set
	 */
	public void setDisabledCircle(String disabledCircle) {
		this.disabledCircle = disabledCircle;
	}

	/**
	 * @return the isCircleAdmin
	 */
	public String getIsCircleAdmin() {
		return isCircleAdmin;
	}

	/**
	 * @param isCircleAdmin the isCircleAdmin to set
	 */
	public void setIsCircleAdmin(String isCircleAdmin) {
		this.isCircleAdmin = isCircleAdmin;
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

	

	
}