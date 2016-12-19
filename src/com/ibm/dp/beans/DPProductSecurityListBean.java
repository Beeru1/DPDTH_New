package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import com.ibm.dp.dto.DPProductSecurityListDto;

public class DPProductSecurityListBean  extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private FormFile  uploadedFile;
	private String methodName ="";
	private String strmsg = "";
	private String error_flag = "";
	private List error_file = new ArrayList();
	String UserLoginId = "";
	List<DPProductSecurityListDto> productSecurityList=new ArrayList<DPProductSecurityListDto>();
	public String productName;
	public String securityAmt;
	
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
	public String getUserLoginId() {
		return UserLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		UserLoginId = userLoginId;
	}
	
	public String getSecurityAmt() {
		return securityAmt;
	}
	public void setSecurityAmt(String securityAmt) {
		this.securityAmt = securityAmt;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<DPProductSecurityListDto> getProductSecurityList() {
		return productSecurityList;
	}
	public void setProductSecurityList(
			List<DPProductSecurityListDto> productSecurityList) {
		this.productSecurityList = productSecurityList;
	}
	
	
	
}
