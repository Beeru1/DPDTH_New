package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.CheckRetailerBalanceDto;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.CloseInactivePartnersDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.dp.dto.UploadFileDto;

public class CloseInactivePartnersBean extends ValidatorForm{
	
	
	private String methodName;
	private boolean fileEmptyFlag = true;
	private String status="";
	private String error_flag="";
	private UploadFileDto uploadFileDto=null;
	private static final long serialVersionUID = 1098305345870553453L;
	private String circleId="";
	private String uploadbtn="";
	private FormFile uploadedFile;
	private String success_message="";
	private String success="";
	private String file_message="";
	private FormFile file;
	private String olmid="";
	private String browseButtonName;
	private String strmsg;
	private ArrayList<CloseInactivePartnersDto> displayDetails;
	
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	
	public String getBrowseButtonName() {
		return browseButtonName;
	}
	public void setBrowseButtonName(String browseButtonName) {
		this.browseButtonName = browseButtonName;
	}
	public String getOlmid() {
		return olmid;
	}
	public void setOlmid(String olmid) {
		this.olmid = olmid;
	}
	public String getFile_message() {
		return file_message;
	}
	public void setFile_message(String file_message) {
		this.file_message = file_message;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getSuccess_message() {
		return success_message;
	}
	public void setSuccess_message(String success_message) {
		this.success_message = success_message;
	}
	public String getUploadbtn() {
		return uploadbtn;
	}
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public void setUploadbtn(String uploadbtn) {
		this.uploadbtn = uploadbtn;
	}
	
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public UploadFileDto getUploadFileDto() {
		return uploadFileDto;
	}
	public void setUploadFileDto(UploadFileDto uploadFileDto) {
		this.uploadFileDto = uploadFileDto;
	}
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public boolean isFileEmptyFlag() {
		return fileEmptyFlag;
	}


	public void setFileEmptyFlag(boolean fileEmptyFlag) {
		this.fileEmptyFlag = fileEmptyFlag;
	}
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile stockList) {
		this.file = file;
	}
	public void setDisplayDetails(ArrayList<CloseInactivePartnersDto> displayDetails) {
		this.displayDetails = displayDetails;
	}
	public ArrayList<CloseInactivePartnersDto> getDisplayDetails() {
		return displayDetails;
	}
	

	
}
