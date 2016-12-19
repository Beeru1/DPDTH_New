/**************************************************************************
 * File     : BulkBizUserAssoDTO.java
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

package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;

/**
 * @author a_gupta
 *
 */
public class BulkBizUserAssoDTO implements Serializable{
	
	

	protected int fileId;
	protected String fileName;
	protected String circleCode;
	protected String status;
	protected String originalFileName;
	protected int mappingType;
	protected int totalSuccessCount;
	protected int totalFailureCount;
	protected int fileType;
	protected String uploadedDate;
	protected String uploadedBy;
	protected int relatedFileId;
	protected int userDataId;
	protected String failedMsg;
	protected int locId;
	protected String hubCode;
	protected String failedFile;
	protected String rownum;
	
	/**
	 * @return the rownum
	 */
	public String getRownum() {
		return rownum;
	}
	/**
	 * @param rownum the rownum to set
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
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
	 * @return the locId
	 */
	public int getLocId() {
		return locId;
	}
	/**
	 * @param locId the locId to set
	 */
	public void setLocId(int locId) {
		this.locId = locId;
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
	 * @return the failedMsg
	 */
	public String getFailedMsg() {
		return failedMsg;
	}
	/**
	 * @param failedMsg the failedMsg to set
	 */
	public void setFailedMsg(String failedMsg) {
		this.failedMsg = failedMsg;
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
	 * @return the fileType
	 */
	public int getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the originalFileName
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}
	/**
	 * @param originalFileName the originalFileName to set
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

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
	 * @return the uploadedBy
	 */
	public String getUploadedBy() {
		return uploadedBy;
	}
	/**
	 * @param uploadedBy the uploadedBy to set
	 */
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	/**
	 * @return the uploadedDate
	 */
	public String getUploadedDate() {
		return uploadedDate;
	}
	/**
	 * @param uploadedDate the uploadedDate to set
	 */
	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the mappingType
	 */
	public int getMappingType() {
		return mappingType;
	}
	/**
	 * @param mappingType the mappingType to set
	 */
	public void setMappingType(int mappingType) {
		this.mappingType = mappingType;
	}
	/**
	 * @return the relatedFileId
	 */
	public int getRelatedFileId() {
		return relatedFileId;
	}
	/**
	 * @param relatedFileId the relatedFileId to set
	 */
	public void setRelatedFileId(int relatedFileId) {
		this.relatedFileId = relatedFileId;
	}
	/**
	 * @return the totalFailureCount
	 */
	public int getTotalFailureCount() {
		return totalFailureCount;
	}
	/**
	 * @param totalFailureCount the totalFailureCount to set
	 */
	public void setTotalFailureCount(int totalFailureCount) {
		this.totalFailureCount = totalFailureCount;
	}
	/**
	 * @return the totalSuccessCount
	 */
	public int getTotalSuccessCount() {
		return totalSuccessCount;
	}
	/**
	 * @param totalSuccessCount the totalSuccessCount to set
	 */
	public void setTotalSuccessCount(int totalSuccessCount) {
		this.totalSuccessCount = totalSuccessCount;
	}
	/**
	 * @return the userDataId
	 */
	public int getUserDataId() {
		return userDataId;
	}
	/**
	 * @param userDataId the userDataId to set
	 */
	public void setUserDataId(int userDataId) {
		this.userDataId = userDataId;
	}
	/**
	 * @return the failedFile
	 */
	public String getFailedFile() {
		return failedFile;
	}
	/**
	 * @param failedFile the failedFile to set
	 */
	public void setFailedFile(String failedFile) {
		this.failedFile = failedFile;
	}

	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - fileId: ").append(fileId)
			.append(", fileName: ").append(fileName)
			.append(", circleCode: ").append(circleCode)
			.append(", status: ").append(status)
			.append(", originalFileName: ").append(originalFileName)
			.append(", mappingType: ").append(mappingType)
			.append(", totalSuccessCount: ").append(totalSuccessCount)
			.append(", totalFailureCount: ").append(totalFailureCount)
			.append(", fileType: ").append(fileType)
			.append(", uploadedDate: ").append(uploadedDate)
			.append(", uploadedBy: ").append(uploadedBy)
			.append(", relatedFileId: ").append(relatedFileId)
			.append(", userDataId: ").append(userDataId)
			.append(", failedMsg: ").append(failedMsg)
			.append(", locId: ").append(locId)
			.append(", failedFile: ").append(failedFile).toString());
			
	}
	
}
