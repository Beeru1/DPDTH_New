package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class DPReportForm extends ActionForm{
	
	private ArrayList reportNamesList = null;
	private String reportId = "";
	private ArrayList reportDataList = null;
	private int noOfColumns = 0;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public ArrayList getReportNamesList() {
		return reportNamesList;
	}

	public void setReportNamesList(ArrayList reportNamesList) {
		this.reportNamesList = reportNamesList;
	}

	public ArrayList getReportDataList() {
		return reportDataList;
	}

	public void setReportDataList(ArrayList reportDataList) {
		this.reportDataList = reportDataList;
	}

	public int getNoOfColumns() {
		return noOfColumns;
	}

	public void setNoOfColumns(int noOfColumns) {
		this.noOfColumns = noOfColumns;
	}


	

}
