/**************************************************************************
 * File     : BulkBizUserAssoBean.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


/**
 * @author a_gupta
 *
 */
public class BulkBizUserAssoBean extends ActionForm{


		public static final long serialVersionUID = 1L;
		private FormFile uploadFile; 
		private String mappingType;
		private String circleId;
		private String circleCode;
		private List distList;
		private String userRole;
		private String hubCode;
		private String totalFailure;
		private String totalRecords;
		private String totalSuccess;
		private String fileId;
		private String label;
		private List circleList;
		private int mappingTypes;
		private List busniessUserList;
		private String businessUserId;
		private String identifier;
		private String methodName;
		private String page1="";
		private String businessUserName;
		private List typeOfUserList;
		private int typeOfUserId;
		private List reportList;
		
		
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
		 * @return Returns the distList.
		 */
		public List getDistList() {
			return distList;
		}
		/**
		 * @param distList The distList to set.
		 */
		public void setDistList(List distList) {
			this.distList = distList;
		}
	
		/**
		 * @return Returns the label.
		 */
		public String getLabel() {
			return label;
		}
		/**
		 * @param label The label to set.
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		/**
		 * @return Returns the mappingType.
		 */
		public String getMappingType() {
			return mappingType;
		}
		/**
		 * @param mappingType The mappingType to set.
		 */
		public void setMappingType(String mappingType) {
			this.mappingType = mappingType;
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
		 * @return Returns the uploadFile.
		 */
		public FormFile getUploadFile() {
			return uploadFile;
		}
		/**
		 * @param uploadFile The uploadFile to set.
		 */
		public void setUploadFile(FormFile uploadFile) {
			this.uploadFile = uploadFile;
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
		 * @return the businessUserId
		 */
		public String getBusinessUserId() {
			return businessUserId;
		}
		/**
		 * @param businessUserId the businessUserId to set
		 */
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

	}

