package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPPendingListTransferBulkUploadDto;
import com.ibm.dp.dto.DPProductSecurityListDto;

public class DPPendingListTransferBulkUploadBean  extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private FormFile  uploadedFile;
	private String methodName ="";
	private String strmsg = "";
	private String error_flag = "";
	private List error_file = new ArrayList();
	
	//List<DPPendingListTransferBulkUploadDto> pendingListTransferBulkUpload=new ArrayList<DPPendingListTransferBulkUploadDto>();
	
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public List getError_file() {
		return error_file;
	}
	public void setError_file(List error_file) {
		this.error_file = error_file;
	}
		/*public void setUserLoginId(String userLoginId) {
		UserLoginId = userLoginId;
	}
	public List<DPPendingListTransferBulkUploadDto> getPendingListTransferBulkUpload() {
		return pendingListTransferBulkUpload;
	}
	public void setPendingListTransferBulkUpload(
			List<DPPendingListTransferBulkUploadDto> pendingListTransferBulkUpload) {
		this.pendingListTransferBulkUpload = pendingListTransferBulkUpload;
	}*/
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	

	
	
	
}
