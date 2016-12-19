package com.ibm.reports.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;
import com.ibm.reports.dto.UploadTrailReportDTO;

public class UploadTrailReportsBean  extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String uploadType;
	private String methodName;
	private String fromDate;
	private String toDate;
	private String date;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private List<String> headers;
	private List<String[]> output;
	private List<String> reportData;
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String[]> getOutput() {
		return output;
	}

	public void setOutput(List<String[]> output) {
		this.output = output;
	}

	public List<String> getReportData() {
		return reportData;
	}

	public void setReportData(List<String> reportData) {
		this.reportData = reportData;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private List<UploadTrailReportDTO>  uploadTypeList  =null;
	


	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadTypeList(List<UploadTrailReportDTO> uploadTypeList) {
		this.uploadTypeList = uploadTypeList;
	}

	public List<UploadTrailReportDTO> getUploadTypeList() {
		return uploadTypeList;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}



	

}
