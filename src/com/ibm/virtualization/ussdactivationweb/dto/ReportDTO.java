/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ashad
 *
 */
public class ReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int reportId;
	
	private String reportName;
	
	private Timestamp reportDateTime;
	
	private String userRole;
	
	private String circleCode;
	
	private String forceStart;
	
	private String reportTypeFlag;
	
	public ReportDTO() {
		
	}

	/**
	 * @return the circleCode
	 */
	public String getCircleCode() {
		return circleCode;
	}

	/**
	 * @param circleCode the circleCode to set
	 */
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	/**
	 * @return the reportDateTime
	 */
	public Timestamp getReportDateTime() {
		return reportDateTime;
	}

	/**
	 * @param reportDateTime the reportDateTime to set
	 */
	public void setReportDateTime(Timestamp reportDateTime) {
		this.reportDateTime = reportDateTime;
	}



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
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the forceStart
	 */
	public String getForceStart() {
		return forceStart;
	}

	/**
	 * @param forceStart the forceStart to set
	 */
	public void setForceStart(String forceStart) {
		this.forceStart = forceStart;
	}

	/**
	 * @return the reportTypeFlag
	 */
	public String getReportTypeFlag() {
		return reportTypeFlag;
	}

	/**
	 * @param reportTypeFlag the reportTypeFlag to set
	 */
	public void setReportTypeFlag(String reportTypeFlag) {
		this.reportTypeFlag = reportTypeFlag;
	}
	

	
}
