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
import org.apache.struts.action.ActionForm;

/**
 * Form Bean class for login Form
 * 
 * @author Paras
 *  
 */
public class LoginFormBean extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4195923003241572139L;
	private String loginId;
	private String loginName;
	private String password;
	private String mPassword;
	private String type;
	private String status;
	private String loginAttempted;
	private String groupId;
	private String passwordChangedDt;
	private String loginbutton;
	
	
	
	/**
	 * @return Returns the groupId.
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return Returns the loginAttempted.
	 */
	public String getLoginAttempted() {
		return loginAttempted;
	}
	/**
	 * @param loginAttempted The loginAttempted to set.
	 */
	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
	}
	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId The loginId to set.
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return Returns the mPassword.
	 */
	public String getMPassword() {
		return mPassword;
	}
	/**
	 * @param password The mPassword to set.
	 */
	public void setMPassword(String mpassword) {
		this.mPassword = mpassword;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the passwordChangedDt.
	 */
	public String getPasswordChangedDt() {
		return passwordChangedDt;
	}
	/**
	 * @param passwordChangedDt The passwordChangedDt to set.
	 */
	public void setPasswordChangedDt(String passwordChangedDt) {
		this.passwordChangedDt = passwordChangedDt;
	}
	public String getLoginbutton() {
		return loginbutton;
	}
	public void setLoginbutton(String loginbutton) {
		this.loginbutton = loginbutton;
	}
}
