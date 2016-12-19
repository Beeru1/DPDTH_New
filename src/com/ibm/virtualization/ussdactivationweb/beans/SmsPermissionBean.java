/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import org.apache.struts.action.ActionForm;

/**
 * @author Aalok Sharma
 *
 */
public class SmsPermissionBean extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	private String methodName = "";
	private String circleCode;	
	private List businessUserList;	
	private String userRole = "SuperAdmin";
	private LinkedHashMap requesterIds = null;
	private LinkedHashMap reportIds = null;
	private List smsConfigData;
	private String hubCode1;
	private String circleCode1;
	private ArrayList hubList;
	private String hubCode;
	private String hubName;
	private List circleList = null;
	
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
	
	
	
	/**
	 * @return the circleList
	 */
	public List getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList the circleList to set
	 */
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}

	/**
	 * @return the smsConfigData
	 */
	public List getSmsConfigData() {
		return smsConfigData;
	}

	/**
	 * @param smsConfigData the smsConfigData to set
	 */
	public void setSmsConfigData(List smsConfigData) {
		this.smsConfigData = smsConfigData;
	}

	/**
	 * @return the businessUserList
	 */
	public List getBusinessUserList() {
		return businessUserList;
	}

	/**
	 * @param businessUserList the businessUserList to set
	 */
	public void setBusinessUserList(List businessUserList) {
		this.businessUserList = businessUserList;
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
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	 * @return the reportIds
	 */
	public LinkedHashMap getReportIds() {
		return reportIds;
	}

	/**
	 * @param reportIds the reportIds to set
	 */
	public void setReportIds(LinkedHashMap reportIds) {
		this.reportIds = reportIds;
	}

	/**
	 * @return the requesterIds
	 */
	public LinkedHashMap getRequesterIds() {
		return requesterIds;
	}

	/**
	 * @param requesterIds the requesterIds to set
	 */
	public void setRequesterIds(LinkedHashMap requesterIds) {
		this.requesterIds = requesterIds;
	}

	/**
	 * @return the circleCode1
	 */
	public String getCircleCode1() {
		return circleCode1;
	}

	/**
	 * @param circleCode1 the circleCode1 to set
	 */
	public void setCircleCode1(String circleCode1) {
		this.circleCode1 = circleCode1;
	}

	/**
	 * @return the hubCode
	 */
	public String getHubCode() {
		return hubCode;
	}

	/**
	 * @param hubCode the hubCode to set
	 */
	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}

	/**
	 * @return the hubCode1
	 */
	public String getHubCode1() {
		return hubCode1;
	}

	/**
	 * @param hubCode1 the hubCode1 to set
	 */
	public void setHubCode1(String hubCode1) {
		this.hubCode1 = hubCode1;
	}

	/**
	 * @return the hubList
	 */
	public ArrayList getHubList() {
		return hubList;
	}

	/**
	 * @param hubList the hubList to set
	 */
	public void setHubList(ArrayList hubList) {
		this.hubList = hubList;
	}

	/**
	 * @return the hubName
	 */
	public String getHubName() {
		return hubName;
	}

	/**
	 * @param hubName the hubName to set
	 */
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

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
}
