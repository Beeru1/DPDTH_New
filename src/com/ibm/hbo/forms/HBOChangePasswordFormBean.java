package com.ibm.hbo.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * Users may access 4 fields on this form:
 * <ul>
 * <li>confirmPassword - [your comment here]
 * <li>oldPassword - [your comment here]
 * <li>newPassword - [your comment here]
 * <li>loginActorId - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class HBOChangePasswordFormBean extends ActionForm {

	private String confirmPassword = null;
	private String oldPassword = null;
	private String newPassword = null;
	private String loginActorId = null;
	private String message = null;

	/**
	 * Get confirmPassword
	 * @return String
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Set confirmPassword
	 * @param <code>String</code>
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
	 * @param <code>String</code>
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
	 * @param <code>String</code>
	 */
	public void setNewPassword(String n) {
		this.newPassword = n;
	}

	/**
	 * Get loginActorId
	 * @return String
	 */
	public String getLoginActorId() {
		return loginActorId;
	}

	/**
	 * Set loginActorId
	 * @param <code>String</code>
	 */
	public void setLoginActorId(String l) {
		this.loginActorId = l;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset values are provided as samples only. Change as appropriate.

		confirmPassword = null;
		oldPassword = null;
		newPassword = null;
		loginActorId = null;

	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

	}
	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

}
