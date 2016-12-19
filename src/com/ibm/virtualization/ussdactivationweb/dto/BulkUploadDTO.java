/*
 * Created on Sep 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.dto;

import java.util.List;

import org.apache.struts.upload.FormFile;

/**
 * @author ECO2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BulkUploadDTO {
	
	protected String distributorId;
	protected String distributorName;
	protected String circleId;
	protected String FSOName;
	protected String FSOId;
	protected String businessUserId;
	protected String businessUserName;
	protected String circleCode;
	protected FormFile uploadFile; 
	protected String mappingType;
	protected List distList;
	protected String userRole;
	protected String totalFailure;
	protected String totalRecords;
	protected String totalSuccess;
	protected String fileId;
	protected String label;
	protected List circleList;
	protected int mappingTypes;
	protected List busniessUserList;
	protected String identifier;
	protected String methodName;
	protected String page1="";
	
	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - uploadFile: ").append(uploadFile)
			.append(", mappingType: ").append(mappingType)
			.append(", businessUserId: ").append(businessUserId)
			.append(", circleId: ").append(circleId)
			.append(", circleCode: ").append(circleCode)
			.append(", circleList: ").append(circleList)
			.append(", totalFailure: ").append(totalFailure)
			.append(", userRole: ").append(userRole)
			.append(", totalRecords: ").append(totalRecords)
			.append(", totalSuccess: ").append(totalSuccess)
			.append(", userRole: ").append(userRole)
			.append(", fileId: ").append(fileId)
			.append(", label: ").append(label)
			.append(", mappingTypes: ").append(mappingTypes)
			.append(", businessUserId: ").append(businessUserId)
			.append(", identifier: ").append(identifier).toString());
			
	}

	public List getBusniessUserList() {
		return busniessUserList;
	}
	public void setBusniessUserList(List busniessUserList) {
		this.busniessUserList = busniessUserList;
	}
	public List getCircleList() {
		return circleList;
	}
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMappingType() {
		return mappingType;
	}
	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}
	public int getMappingTypes() {
		return mappingTypes;
	}
	public void setMappingTypes(int mappingTypes) {
		this.mappingTypes = mappingTypes;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getPage1() {
		return page1;
	}
	public void setPage1(String page1) {
		this.page1 = page1;
	}
	public String getTotalFailure() {
		return totalFailure;
	}
	public void setTotalFailure(String totalFailure) {
		this.totalFailure = totalFailure;
	}
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getTotalSuccess() {
		return totalSuccess;
	}
	public void setTotalSuccess(String totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	public FormFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getBusinessUserId() {
		return businessUserId;
	}
	public void setBusinessUserId(String businessUserId) {
		this.businessUserId = businessUserId;
	}
	public String getBusinessUserName() {
		return businessUserName;
	}
	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}
	/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId The circleId to set.
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	/**
	 * @return Returns the distributorId.
	 */
	public String getDistributorId() {
		return distributorId;
	}
	/**
	 * @param distributorId The distributorId to set.
	 */
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	/**
	 * @return Returns the distributorName.
	 */
	public String getDistributorName() {
		return distributorName;
	}
	/**
	 * @param distributorName The distributorName to set.
	 */
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	/**
	 * @return Returns the fSOId.
	 */
	public String getFSOId() {
		return FSOId;
	}
	/**
	 * @param id The fSOId to set.
	 */
	public void setFSOId(String id) {
		FSOId = id;
	}
	/**
	 * @return Returns the fSOName.
	 */
	public String getFSOName() {
		return FSOName;
	}
	/**
	 * @param name The fSOName to set.
	 */
	public void setFSOName(String name) {
		FSOName = name;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
}
