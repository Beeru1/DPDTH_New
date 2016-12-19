package com.ibm.reports.dto;


public class ReportDetailDTO {

	private int reportId;
	private String reportName;
	private String reportLabel;
	private String reportProcName;
	private String distData;
	private String otherUserData;
	private boolean isActive;
	
	/**
	 * @return the reportId
	 */
	public int getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	/**
	 * @return the reportLabel
	 */
	public String getReportLabel() {
		return reportLabel;
	}
	/**
	 * @param reportLabel the reportLabel to set
	 */
	public void setReportLabel(String reportLabel) {
		this.reportLabel = reportLabel;
	}
	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * @return the reportProcName
	 */
	public String getReportProcName() {
		return reportProcName;
	}
	/**
	 * @param reportProcName the reportProcName to set
	 */
	public void setReportProcName(String reportProcName) {
		this.reportProcName = reportProcName;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getDistData() {
		return distData;
	}
	public void setDistData(String distData) {
		this.distData = distData;
	}
	public String getOtherUserData() {
		return otherUserData;
	}
	public void setOtherUserData(String otherUserData) {
		this.otherUserData = otherUserData;
	}	
}

