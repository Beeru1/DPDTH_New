package com.ibm.reports.dto;

import java.util.List;

public class UploadTrailReportsParameterDTO {
	private List<String> headers;
	private List<String[]> output;
	
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
	private String uploadType;
	
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	public List<String> getHeaders() {
		return headers;
	}
	public void setOutput(List<String[]> output) {
		this.output = output;
	}
	public List<String[]> getOutput() {
		return output;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getUploadType() {
		return uploadType;
	}
	

}
