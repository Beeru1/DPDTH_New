/**
 * 
 */
package com.ibm.dp.dto;

/**
 * @author Administrator
 *
 */
public class RetailerLapuDataDto 
{
	private String loginId;
	private long accountid;
	
	private String accountName;
	private String lapuNumber;
	private String mobile1;
	private String mobile2;
	private String mobile3;
	private String mobile4;
	private String err_msg;
	public long getAccountid() {
		return accountid;
	}
	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getLapuNumber() {
		return lapuNumber;
	}
	public void setLapuNumber(String lapuNumber) {
		this.lapuNumber = lapuNumber;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getMobile3() {
		return mobile3;
	}
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	public String getMobile4() {
		return mobile4;
	}
	public void setMobile4(String mobile4) {
		this.mobile4 = mobile4;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
}
