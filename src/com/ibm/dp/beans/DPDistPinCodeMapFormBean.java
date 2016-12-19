package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.dp.dto.DPProductSecurityListDto;

public class DPDistPinCodeMapFormBean  extends ActionForm

{
	private FormFile  uploadedFile;
	private String methodName ="";
	private String strmsg = "";
	private String error_flag = "";
	private List error_file = new ArrayList();
	String UserLoginId = "";
	List<DPDistPinCodeMapDto> distPinList=new ArrayList<DPDistPinCodeMapDto>();
	private String strDistOLMIds="";
	public String pincode="";
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public List getError_file() {
		return error_file;
	}
	public void setError_file(List error_file) {
		this.error_file = error_file;
	}
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
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
	public String getUserLoginId() {
		return UserLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		UserLoginId = userLoginId;
	}
	public List<DPDistPinCodeMapDto> getDistPinList() {
		return distPinList;
	}
	public void setDistPinList(List<DPDistPinCodeMapDto> distPinList) {
		this.distPinList = distPinList;
	}
	public String getStrDistOLMIds() {
		return strDistOLMIds;
	}
	public void setStrDistOLMIds(String strDistOLMIds) {
		this.strDistOLMIds = strDistOLMIds;
	}
	}
