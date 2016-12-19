package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ErrorPOFormBean extends ActionForm
{
	
	private String methodName;
	private ArrayList duplicateSTBList;
	private String strCheckedDC;

	private static final long serialVersionUID = 1L;
	
	public String getStrCheckedDC() {
		return strCheckedDC;
	}

	public void setStrCheckedDC(String strCheckedDC) {
		this.strCheckedDC = strCheckedDC;
	}

	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public void reset()
	{
		
	}

	public ArrayList getDuplicateSTBList() {
		return duplicateSTBList;
	}

	public void setDuplicateSTBList(ArrayList duplicateSTBList) {
		this.duplicateSTBList = duplicateSTBList;
	}
}
