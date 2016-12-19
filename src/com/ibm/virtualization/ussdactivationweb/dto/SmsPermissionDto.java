/**************************************************************************
 * File     : SMSPullReportServlet.java
 * Author   : Aalok
 * Created  : july 22, 2008
 * Modified : july 22, 20088
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Aalok
 *
 * Class hold the information of report
 */
public class SmsPermissionDto implements Serializable{ 
	
	private String smsRequesterId;
	private String smsReportId;
	private int reportId;
	private int userType;
	private String circleCode;
	
	private String reportName;
	private String checked;
	
	private ArrayList edListSmsReport;
	private ArrayList edListSmsConfig;
	
	private ArrayList ceoListSmsReport;
	private ArrayList ceoListSmsConfig;
	
	private ArrayList shListSmsReport;
	private ArrayList shListSmsConfig;
	
	private ArrayList zbmListSmsReport;
	private ArrayList zbmListSmsConfig;
	
	private ArrayList zsmListSmsReport;
	private ArrayList zsmListSmsConfig;
	
	private ArrayList tmListSmsReport;
	private ArrayList tmListSmsConfig;
	
	private ArrayList distListSmsReport;
	private ArrayList distListSmsConfig;
	
	private ArrayList fosListSmsReport;
	private ArrayList fosListSmsConfig;
	
	private ArrayList deaListSmsReport;
	private ArrayList deaListSmsConfig;
	
	
	
	public ArrayList getCeoListSmsConfig() {
		return ceoListSmsConfig;
	}
	public void setCeoListSmsConfig(ArrayList ceoListSmsConfig) {
		this.ceoListSmsConfig = ceoListSmsConfig;
	}
	public ArrayList getCeoListSmsReport() {
		return ceoListSmsReport;
	}
	public void setCeoListSmsReport(ArrayList ceoListSmsReport) {
		this.ceoListSmsReport = ceoListSmsReport;
	}
	public ArrayList getDeaListSmsConfig() {
		return deaListSmsConfig;
	}
	public void setDeaListSmsConfig(ArrayList deaListSmsConfig) {
		this.deaListSmsConfig = deaListSmsConfig;
	}
	public ArrayList getDeaListSmsReport() {
		return deaListSmsReport;
	}
	public void setDeaListSmsReport(ArrayList deaListSmsReport) {
		this.deaListSmsReport = deaListSmsReport;
	}
	public ArrayList getDistListSmsConfig() {
		return distListSmsConfig;
	}
	public void setDistListSmsConfig(ArrayList distListSmsConfig) {
		this.distListSmsConfig = distListSmsConfig;
	}
	public ArrayList getDistListSmsReport() {
		return distListSmsReport;
	}
	public void setDistListSmsReport(ArrayList distListSmsReport) {
		this.distListSmsReport = distListSmsReport;
	}
	public ArrayList getEdListSmsConfig() {
		return edListSmsConfig;
	}
	public void setEdListSmsConfig(ArrayList edListSmsConfig) {
		this.edListSmsConfig = edListSmsConfig;
	}
	public ArrayList getEdListSmsReport() {
		return edListSmsReport;
	}
	public void setEdListSmsReport(ArrayList edListSmsReport) {
		this.edListSmsReport = edListSmsReport;
	}
	public ArrayList getFosListSmsConfig() {
		return fosListSmsConfig;
	}
	public void setFosListSmsConfig(ArrayList fosListSmsConfig) {
		this.fosListSmsConfig = fosListSmsConfig;
	}
	public ArrayList getFosListSmsReport() {
		return fosListSmsReport;
	}
	public void setFosListSmsReport(ArrayList fosListSmsReport) {
		this.fosListSmsReport = fosListSmsReport;
	}
	public ArrayList getShListSmsConfig() {
		return shListSmsConfig;
	}
	public void setShListSmsConfig(ArrayList shListSmsConfig) {
		this.shListSmsConfig = shListSmsConfig;
	}
	public ArrayList getShListSmsReport() {
		return shListSmsReport;
	}
	public void setShListSmsReport(ArrayList shListSmsReport) {
		this.shListSmsReport = shListSmsReport;
	}
	public ArrayList getTmListSmsConfig() {
		return tmListSmsConfig;
	}
	public void setTmListSmsConfig(ArrayList tmListSmsConfig) {
		this.tmListSmsConfig = tmListSmsConfig;
	}
	public ArrayList getTmListSmsReport() {
		return tmListSmsReport;
	}
	public void setTmListSmsReport(ArrayList tmListSmsReport) {
		this.tmListSmsReport = tmListSmsReport;
	}
	public ArrayList getZbmListSmsConfig() {
		return zbmListSmsConfig;
	}
	public void setZbmListSmsConfig(ArrayList zbmListSmsConfig) {
		this.zbmListSmsConfig = zbmListSmsConfig;
	}
	public ArrayList getZbmListSmsReport() {
		return zbmListSmsReport;
	}
	public void setZbmListSmsReport(ArrayList zbmListSmsReport) {
		this.zbmListSmsReport = zbmListSmsReport;
	}
	public ArrayList getZsmListSmsConfig() {
		return zsmListSmsConfig;
	}
	public void setZsmListSmsConfig(ArrayList zsmListSmsConfig) {
		this.zsmListSmsConfig = zsmListSmsConfig;
	}
	public ArrayList getZsmListSmsReport() {
		return zsmListSmsReport;
	}
	public void setZsmListSmsReport(ArrayList zsmListSmsReport) {
		this.zsmListSmsReport = zsmListSmsReport;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * It returns the String representation of the ServiceClassDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer (500)
			.append(" \nServiceClassDTO - smsRequesterId: ").append(smsRequesterId)
			.append(",  smsReportId: ").append(smsReportId)
			.toString();		
	}
	/**
	 * @return the smsReportId
	 */
	public String getSmsReportId() {
		return smsReportId;
	}
	/**
	 * @param smsReportId the smsReportId to set
	 */
	public void setSmsReportId(String smsReportId) {
		this.smsReportId = smsReportId;
	}
	/**
	 * @return the smsRequesterId
	 */
	public String getSmsRequesterId() {
		return smsRequesterId;
	}
	/**
	 * @param smsRequesterId the smsRequesterId to set
	 */
	public void setSmsRequesterId(String smsRequesterId) {
		this.smsRequesterId = smsRequesterId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	

	

}
