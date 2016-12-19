package com.ibm.dpmisreports.beans;

import org.apache.struts.action.ActionForm;

public class DropDownUtilityAjaxForm extends ActionForm
{
	private static final long serialVersionUID = 5552462712269888936L;
	
	private String methodName;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
