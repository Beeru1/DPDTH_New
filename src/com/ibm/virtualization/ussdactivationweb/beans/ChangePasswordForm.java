/*
 * Created on 15 july 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
package com.ibm.virtualization.ussdactivationweb.beans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author Ashad
 */


public class ChangePasswordForm extends ActionForm {

	
	/**
	 * Deafult Constructor
	 */
	public ChangePasswordForm() {
	}

	private String confirmPassword = null;
	private String oldPassword = null;
	private String newPassword = null;
	private String loginName = null;
	private String message = null;
	private String userId = null;
	private String methodName = null;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Get confirmPassword
	 * @return String
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Set confirmPassword
	 * @param c String
	 */
	public void setConfirmPassword(String c) {
		this.confirmPassword = c;
	}

	/**
	 * Get oldPassword
	 * @return String
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * Set oldPassword
	 * @param o String
	 */
	public void setOldPassword(String o) {
		this.oldPassword = o;
	}

	/**
	 * Get newPassword
	 * @return String
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Set newPassword
	 * @param n String
	 */
	public void setNewPassword(String n) {
		this.newPassword = n;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset values are provided as samples only. Change as appropriate.

		confirmPassword = null;
		oldPassword = null;
		newPassword = null;

	}

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

	}
	/**
	 * @return String
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param string String
	 */
	public void setLoginName(String string) {
		loginName = string;
	}

	/**
	 * @param string String
	 */
	public void setUserId(String string) {
		userId = string;
	}

	/**
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param string String
	 */
	public void setMessage(String string) {
		message = string;
	}

}
