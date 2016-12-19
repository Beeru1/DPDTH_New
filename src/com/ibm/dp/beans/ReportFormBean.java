package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;
/**
 * 
 * @author Rohit kunder
 *	Bean class for report poc with S&D
 *
 */
public class ReportFormBean extends ActionForm{

	
	private String user ="";
	private String password ="";
	private String methodName="";
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
