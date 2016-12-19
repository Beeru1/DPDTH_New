package com.ibm.dp.dto;

import java.io.File;
import java.io.Serializable;

public class UploadFileDto implements Serializable {
	
	private static final long serialVersionUID = -7873439235989078737L;
	
	private String fileName="";
	private String errorMessage="";
	private String status="";
	private String loginIds="";
	private String fileId="";
	private String totalUsers="";
	private String usersCreated="";
	private String usersDeleted="";
	private String filePath="";
	private String elementId="";
	private String uploadedBy="";
	private String partnerName="";
	private String usersUpdated="";
	private String fileType="";
	private File errorFile=null;
	
	
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public File getErrorFile() {
		return errorFile;
	}
	public void setErrorFile(File errorFile) {
		this.errorFile = errorFile;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getLoginIds() {
		return loginIds;
	}
	public void setLoginIds(String loginIds) {
		this.loginIds = loginIds;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(String totalUsers) {
		this.totalUsers = totalUsers;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getUsersCreated() {
		return usersCreated;
	}
	public void setUsersCreated(String usersCreated) {
		this.usersCreated = usersCreated;
	}
	public String getUsersDeleted() {
		return usersDeleted;
	}
	public void setUsersDeleted(String usersDeleted) {
		this.usersDeleted = usersDeleted;
	}
	public String getUsersUpdated() {
		return usersUpdated;
	}
	public void setUsersUpdated(String usersUpdated) {
		this.usersUpdated = usersUpdated;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
