/**************************************************************************
 * File     : MisReportBean.java
 * Author   : Aalok Sharma
 * Created  : Oct 3, 2008
 * Modified : Oct 3, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 3, 2008 	Aalok Sharma	First Cut.
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * MisReportBean
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/
public class MisReportBean extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String methodName = "";

	private String businessUserList;

	private String circleCode;

	private String reportId;

	private String reportDate = "";

	private String userRole = "";

	private String reportType = (Utility.getValueFromBundle(Constants.DAILY,
			Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

	private ArrayList fileInfoDataList = null;
	
	private String reportType1 ="";

	/**
	 * @return the reportType1
	 */
	public String getReportType1() {
		return reportType1;
	}

	/**
	 * @param reportType1 the reportType1 to set
	 */
	public void setReportType1(String reportType1) {
		this.reportType1 = reportType1;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType
	 *            the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the fileInfoDataList
	 */
	public ArrayList getFileInfoDataList() {
		return fileInfoDataList;
	}

	/**
	 * @param fileInfoDataList
	 *            the fileInfoDataList to set
	 */
	public void setFileInfoDataList(ArrayList fileInfoDataList) {
		this.fileInfoDataList = fileInfoDataList;
	}

	/**
	 * @return the reportDate
	 */
	public String getReportDate() {
		return reportDate;
	}

	/**
	 * @param reportDate
	 *            the reportDate to set
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId
	 *            the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the businessUserList
	 */
	public String getBusinessUserList() {
		return businessUserList;
	}

	/**
	 * @param businessUserList
	 *            the businessUserList to set
	 */
	public void setBusinessUserList(String businessUserList) {
		this.businessUserList = businessUserList;
	}

	/**
	 * @return the circleCode
	 */
	public String getCircleCode() {
		return circleCode;
	}

	/**
	 * @param circleCode
	 *            the circleCode to set
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
	 * @param methodName
	 *            the methodName to set
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
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
