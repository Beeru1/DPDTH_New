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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * This object captures the input fields sent through the request. and creates a
 * parameter in the request for each field on the form. The QueryFormBean has a
 * corresponding property for each field on the HTML form.
 * 
 * @author Prakash
 * 
 */
public class QueryFormBean extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8614836072507527556L;

	/* Logger for this class */
	private static java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(QueryFormBean.class.getName());

	/* Parent loginId */
	private String loginId = null;

	/* Child Login ID */
	private String childMobileNumber = null;

	/* Used to get or set the method name which is called */
	private String methodName = null;

	private String rdbSelect = null;

	private boolean async;
	
	private String physicalId;
	
	private String requestTime;	

	

	public boolean getAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	/**
	 * This function is used to reset the values of QueryFormbean to some
	 * initial value.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		logger
				.fine("reset(QueryformBean): Entered reset() method of QueryFormBean");

		loginId = null;
		childMobileNumber = null;
		methodName = null;
		async = false;
		logger
				.fine("reset(QueryformBean): Successfully reset values of QueryFormBean");
	}

	/**
	 * Method to validate QueryFormBean
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @return errors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (getMethodName()
				.equalsIgnoreCase("getChildAccBalanceByMobileNumber")) {
			if (getChildMobileNumber() == null
					|| getChildMobileNumber().equalsIgnoreCase("")) {
				errors.add("query.prompt.enterchildmobilno", new ActionError(
						"query.prompt.enterchildmobilno"));
				return errors;
			}
		}
		return errors;
	}

	/**
	 * @return Returns the childMobileNumber.
	 */
	public String getChildMobileNumber() {
		return childMobileNumber;
	}

	/**
	 * @param childMobileNumber
	 *            The childMobileNumber to set.
	 */
	public void setChildMobileNumber(String childMobileNumber) {
		this.childMobileNumber = childMobileNumber;
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
	 * @return Returns the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            String loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRdbSelect() {
		return rdbSelect;
	}

	public void setRdbSelect(String rdbSelect) {
		this.rdbSelect = rdbSelect;
	}

	/**
	 * @return the physicalId
	 */
	public String getPhysicalId() {
		return physicalId;
	}

	/**
	 * @param physicalId the physicalId to set
	 */
	public void setPhysicalId(String physicalId) {
		this.physicalId = physicalId;
	}

	/**
	 * @return the requestTime
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

}