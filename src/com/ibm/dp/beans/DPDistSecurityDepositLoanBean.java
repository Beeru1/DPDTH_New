package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPDistSecurityDepositLoanDto;

public class DPDistSecurityDepositLoanBean  extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private FormFile  uploadedFile;
	private String methodName ="";
	private String strmsg = "";
	private String error_flag = "";
	private List error_file = new ArrayList();
	String UserLoginId = "";
	List<DPDistSecurityDepositLoanDto> distSecLoanList=new ArrayList<DPDistSecurityDepositLoanDto>();
	public String distOlmId;
	public String securityAmt;
	public String loanAmt;
	public String DistName;
	
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
	public List<DPDistSecurityDepositLoanDto> getDistSecLoanList() {
		return distSecLoanList;
	}
	public void setDistSecLoanList(
			List<DPDistSecurityDepositLoanDto> distSecLoanList) {
		this.distSecLoanList = distSecLoanList;
	}
	public String getDistName() {
		return DistName;
	}
	public void setDistName(String distName) {
		DistName = distName;
	}
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getSecurityAmt() {
		return securityAmt;
	}
	public void setSecurityAmt(String securityAmt) {
		this.securityAmt = securityAmt;
	}
	
	
	
}
