package com.ibm.dp.dto;

public class DPDistSecurityDepositLoanDto {
	public String distOlmId;
	public String securityAmt;
	public String loanAmt;
	public String DistName;
	public String err_msg="";
	public String distId;
	public String circleName="";
	
	
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	
	
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
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
	public String getDistName() {
		return DistName;
	}
	public void setDistName(String distName) {
		DistName = distName;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	
	

}
