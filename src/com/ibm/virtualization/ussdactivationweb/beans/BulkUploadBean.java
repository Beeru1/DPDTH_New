/*
 * Created on Jul 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author IBM
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BulkUploadBean extends ActionForm{
	public static final long serialVersionUID = 1L;
	private String circleCode;
	private String circleCode1;
	private String userRole;
	private String totalFailure;
	private String totalRecords;
	private String totalSuccess;
	private String fileId;
	private List circleList;
	private int mappingTypes;
	private String identifier;
	private String methodName;
	private String page1="";
	private String hubCode;
	private List typeOfUserList;
	private int typeOfUserId;
	private int typeOfUserId1;
	private List reportList;
	private int reportType;
	private int reportType1;
	private String fileName ;
	private String status;
	private String status1;
	private int typeOfReport;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the reportType
	 */
	public int getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the reportList
	 */
	public List getReportList() {
		return reportList;
	}

	/**
	 * @param reportList the reportList to set
	 */
	public void setReportList(List reportList) {
		this.reportList = reportList;
	}

	/**
	 * @return the typeOfUserId
	 */
	public int getTypeOfUserId() {
		return typeOfUserId;
	}

	/**
	 * @param typeOfUserId the typeOfUserId to set
	 */
	public void setTypeOfUserId(int typeOfUserId) {
		this.typeOfUserId = typeOfUserId;
	}

	/**
	 * @return the typeOfUserList
	 */
	public List getTypeOfUserList() {
		return typeOfUserList;
	}

	/**
	 * @param typeOfUserList the typeOfUserList to set
	 */
	public void setTypeOfUserList(List typeOfUserList) {
		this.typeOfUserList = typeOfUserList;
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

	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - : ")
			.append(", circleCode: ").append(circleCode)
			.append(", circleList: ").append(circleList)
			.append(", totalFailure: ").append(totalFailure)
			.append(", userRole: ").append(userRole)
			.append(", totalRecords: ").append(totalRecords)
			.append(", totalSuccess: ").append(totalSuccess)
			.append(", userRole: ").append(userRole)
			.append(", fileId: ").append(fileId)
			.append(", mappingTypes: ").append(mappingTypes)
			.append(", identifier: ").append(identifier).toString());
			
	}
	
	/**
	 * @return Returns the fileId.
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * @param fileId The fileId to set.
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return Returns the circleList.
	 */
	public List getCircleList() {
		return circleList;
	}
	/**
	 * @param circleList The circleList to set.
	 */
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}
	/**
	 * @return Returns the totalFailure.
	 */
	public String getTotalFailure() {
		return totalFailure;
	}
	/**
	 * @param totalFailure The totalFailure to set.
	 */
	public void setTotalFailure(String totalFailure) {
		this.totalFailure = totalFailure;
	}
	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return Returns the totalSuccess.
	 */
	public String getTotalSuccess() {
		return totalSuccess;
	}
	/**
	 * @param totalSuccess The totalSuccess to set.
	 */
	public void setTotalSuccess(String totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	/**
	 * @return Returns the userRole.
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole The userRole to set.
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @return Returns the fSOId.
	 */
	
	public void setMappingTypes(int mappingTypes) {
		this.mappingTypes = mappingTypes;
	}
	public int getMappingTypes() {
		return mappingTypes;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	
	public String getPage1() { 
		return page1;
	}
	public void setPage1(String page1) {
		this.page1 = page1;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
	 * @return the typeOfReport
	 */
	public int getTypeOfReport() {
		return typeOfReport;
	}

	/**
	 * @param typeOfReport the typeOfReport to set
	 */
	public void setTypeOfReport(int typeOfReport) {
		this.typeOfReport = typeOfReport;
	}

	/**
	 * @return the reportType1
	 */
	public int getReportType1() {
		return reportType1;
	}

	/**
	 * @param reportType1 the reportType1 to set
	 */
	public void setReportType1(int reportType1) {
		this.reportType1 = reportType1;
	}

	/**
	 * @return the status1
	 */
	public String getStatus1() {
		return status1;
	}

	/**
	 * @param status1 the status1 to set
	 */
	public void setStatus1(String status1) {
		this.status1 = status1;
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
	 * @return the typeOfUserId1
	 */
	public int getTypeOfUserId1() {
		return typeOfUserId1;
	}

	/**
	 * @param typeOfUserId1 the typeOfUserId1 to set
	 */
	public void setTypeOfUserId1(int typeOfUserId1) {
		this.typeOfUserId1 = typeOfUserId1;
	}

}

	