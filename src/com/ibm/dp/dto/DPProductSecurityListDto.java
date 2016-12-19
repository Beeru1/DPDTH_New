package com.ibm.dp.dto;

public class DPProductSecurityListDto {
	
	public String securityAmt;
	public String loanAmt;
	public String err_msg="";
	public String productName="";
	Integer intProducType = 0;
	
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getIntProducType() {
		return intProducType;
	}
	public void setIntProducType(Integer intProducType) {
		this.intProducType = intProducType;
	}

	

}
