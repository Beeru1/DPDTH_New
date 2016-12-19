/**
 * 
 */
package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author Rohit Kunder
 *
 */
public class DPSecFileUploadBean extends ActionForm{

	
	private FormFile thefile;
	private ArrayList uploadFileList;
	private String FileName;
	private String FileType;
	private int FileId;
	private String UploadedDate;
	private String Uploadedby;
	private String ProcessedStatus;
	private String ProcessedDate;
	private String filePath = null;
	private String uploadStatus=null;
	private String successFile=null;
	private String errorFile=null;	
	private ArrayList arrReqList = null;
	private String selectFile = "";
	private String DistId="";
	private String methodName="";
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public ArrayList getArrReqList() {
		return arrReqList;
	}
	public void setArrReqList(ArrayList arrReqList) {
		this.arrReqList = arrReqList;
	}
	public String getDistId() {
		return DistId;
	}
	public void setDistId(String distId) {
		DistId = distId;
	}
	public String getErrorFile() {
		return errorFile;
	}
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}
	public int getFileId() {
		return FileId;
	}
	public void setFileId(int fileId) {
		FileId = fileId;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	public String getProcessedDate() {
		return ProcessedDate;
	}
	public void setProcessedDate(String processedDate) {
		ProcessedDate = processedDate;
	}
	public String getProcessedStatus() {
		return ProcessedStatus;
	}
	public void setProcessedStatus(String processedStatus) {
		ProcessedStatus = processedStatus;
	}
	public String getSelectFile() {
		return selectFile;
	}
	public void setSelectFile(String selectFile) {
		this.selectFile = selectFile;
	}
	public String getSuccessFile() {
		return successFile;
	}
	public void setSuccessFile(String successFile) {
		this.successFile = successFile;
	}
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	public String getUploadedby() {
		return Uploadedby;
	}
	public void setUploadedby(String uploadedby) {
		Uploadedby = uploadedby;
	}
	public String getUploadedDate() {
		return UploadedDate;
	}
	public void setUploadedDate(String uploadedDate) {
		UploadedDate = uploadedDate;
	}
	public ArrayList getUploadFileList() {
		return uploadFileList;
	}
	public void setUploadFileList(ArrayList uploadFileList) {
		this.uploadFileList = uploadFileList;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
}
