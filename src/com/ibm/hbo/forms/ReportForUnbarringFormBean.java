package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ReportForUnbarringFormBean extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList serialNos;

	public ArrayList getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(ArrayList serialNos) {
		this.serialNos = serialNos;
	}
}
