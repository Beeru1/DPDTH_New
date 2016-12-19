package com.ibm.reports.dto;

import java.util.List;

public class UploadTrailReportsOutputDTO {
	
	
	private List<String> headers;
	private List<String[]> output;
	
	private int groupId;
	
	
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
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupId() {
		return groupId;
	}
	
	

}
