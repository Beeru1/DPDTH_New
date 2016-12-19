

package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class ViewEditUserBean extends ActionForm 
{
	private ArrayList displayDetails;
	private String functionType;
	private String loginId;
	
	
	
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
	 * @return Returns the functionType.
	 */
	public String getFunctionType() {
		return functionType;
	}
	/**
	 * @param functionType The functionType to set.
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	/**
	 * @return Returns the displayDetails.
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}
	/**
	 * @param displayDetails The displayDetails to set.
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}
}