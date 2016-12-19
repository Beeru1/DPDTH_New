package com.ibm.virtualization.ussdactivationweb.beans;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * 
 */

/**
 * @author IBM
 *
 */
public class UserBean extends ValidatorForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId=null;
	private String loginId=null;
	private Integer circleId=null;
	private String dealerName=null;
	private String dealerCode=null;
	private Integer registeredMobile=null;
	private String address=null;
	private String emailId=null;
	private String userType=null;
	private String status=null;
	private String passwordChangeDt=null;
	private String mode="Insert";
	private String actionPerformed;
	private Integer updatedBy;
	private Integer createdBy;
	private java.sql.Timestamp updatedDt;
	private java.sql.Timestamp createdDt;
	private java.sql.Timestamp PasswordChangedDt;
	private String circleCode;
	/**
	 * @return Returns the passwordChangedDt.
	 */
	public java.sql.Timestamp getPasswordChangedDt() {
		return PasswordChangedDt;
	}
	/**
	 * @param passwordChangedDt The passwordChangedDt to set.
	 */
	public void setPasswordChangedDt(java.sql.Timestamp passwordChangedDt) {
		PasswordChangedDt = passwordChangedDt;
	}
	/**
	 * @return Returns the createdDt.
	 */
	public java.sql.Timestamp getCreatedDt() {
		return createdDt;
	}
	/**
	 * @param createdDt The createdDt to set.
	 */
	public void setCreatedDt(java.sql.Timestamp createdDt) {
		this.createdDt = createdDt;
	}
	/**
	 * @return Returns the updatedDt.
	 */
	public java.sql.Timestamp getUpdatedDt() {
		return updatedDt;
	}
	/**
	 * @param updatedDt The updatedDt to set.
	 */
	public void setUpdatedDt(java.sql.Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}
	private Integer groupId;

	
	
	/**
	 * @return Returns the createdBy.
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy The createdBy to set.
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return Returns the groupId.
	 */
	public Integer getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return Returns the passwordChangeDt.
	 */
	public String getPasswordChangeDt() {
		return passwordChangeDt;
	}
	/**
	 * @param passwordChangeDt The passwordChangeDt to set.
	 */
	public void setPasswordChangeDt(String passwordChangeDt) {
		this.passwordChangeDt = passwordChangeDt;
	}
	/**
	 * @return Returns the updatedBy.
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy The updatedBy to set.
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		return super.validate(arg0, arg1); 
	}
	/**
	 * @return Returns the actionPerformed.
	 */
	public String getActionPerformed() {
		return actionPerformed;
	}
	/**
	 * @param actionPerformed The actionPerformed to set.
	 */
	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the circleId
	 */
	public Integer getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	/**
	 * @return the dealerCode
	 */
	public String getDealerCode() {
		return dealerCode;
	}
	/**
	 * @param dealerCode the dealerCode to set
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	/**
	 * @return the dealerName
	 */
	public String getDealerName() {
		return dealerName;
	}
	/**
	 * @param dealerName the dealerName to set
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the registeredMobile
	 */
	public Integer getRegisteredMobile() {
		return registeredMobile;
	}
	/**
	 * @param registeredMobile the registeredMobile to set
	 */
	public void setRegisteredMobile(Integer registeredMobile) {
		this.registeredMobile = registeredMobile;
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
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	
}
