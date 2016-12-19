package com.ibm.dp.dto;

import java.io.Serializable;

public class DPDeliveryChallanAcceptDTO implements Serializable
{
	private Integer intPRNo;
	private String strPONo;
	private String strInvoiceNo;
	private String strDCNo;
	private String strStatus;
	private String strProductName;
	private String serialNo;
	private String strDCDate;
	private int isError;
	
	public Integer getIntPRNo() {
		return intPRNo;
	}
	public void setIntPRNo(Integer intPRNo) {
		this.intPRNo = intPRNo;
	}
	public String getStrDCNo() {
		return strDCNo;
	}
	public void setStrDCNo(String strDCNo) {
		this.strDCNo = strDCNo;
	}
	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}
	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = strInvoiceNo;
	}
	public String getStrPONo() {
		return strPONo;
	}
	public void setStrPONo(String strPONo) {
		this.strPONo = strPONo;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getStrProductName() {
		return strProductName;
	}
	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getStrDCDate() {
		return strDCDate;
	}
	public void setStrDCDate(String strDCDate) {
		this.strDCDate = strDCDate;
	}
	public int getIsError() {
		return isError;
	}
	public void setIsError(int isError) {
		this.isError = isError;
	}

}
