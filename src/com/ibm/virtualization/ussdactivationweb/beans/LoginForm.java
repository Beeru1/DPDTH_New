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
 * Users may access 2 fields on this form:
 * <ul>
 * <li>password - [your comment here]
 * <li>loginId - [your comment here]
 * </ul>
 * @version 	1.0
 * @author Ashad
 */
public class LoginForm extends ActionForm {

	private String password = null;
	private String loginId = null;
	private String validateStatus = "0";

	/**
	 * Get password
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 * @param <code>String</code>
	 */
	public void setPassword(String p) {
		this.password = p;
	}

	/**
	 * Get loginId
	 * @return String
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * Set loginId
	 * @param <code>String</code>
	 */
	public void setLoginId(String l) {
		this.loginId = l;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset values are provided as samples only. Change as appropriate.

		password = null;
		loginId = null;

	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
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
	 * @return Returns the validateStatus.
	 */
	public String getValidateStatus() {
		return validateStatus;
	}
	/**
	 * @param validateStatus The validateStatus to set.
	 */
	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
	}
}
