package com.ibm.dpmisreports.common;

import java.io.Serializable;

public class SelectionCollection implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private String strValue;
	private String strText;
	
	public String getStrText() {
		return strText;
	}
	public void setStrText(String strText) {
		this.strText = strText;
	}
	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
}
