package com.ibm.dp.dto;

import java.sql.Timestamp;

public class DPHierarchyAcceptDTO
{

	private String strTRNO 			= null;
	private String strTransferType 	= null;
	private String strRequestBy		= null;
	private String strRequestOn		= null;
	private String strTransferBy	= null;
	private String strTransferOn	= null;
	private String strTransferReq	= null;
	private Integer intTransferQty	= null;
	private String strRole			= null;
	private String strAccountName	= null;
	private String strContactName	= null;
	private String strMobileNo		= null;
	//private String strMobileNo		= null;
	//private String strMobileNo		= null;

	private String strZone			= null;
	private String strCity			= null;
	private Integer intTrnsBy		= null;

	private Timestamp tsTrnsTime	= null;

	private Integer account_id		= null;
	private String request_type		= null;



	public Timestamp getTsTrnsTime() {
		return tsTrnsTime;
	}
	public void setTsTrnsTime(Timestamp tsTrnsTime) {
		this.tsTrnsTime = tsTrnsTime;
	}


	private Integer stockQty		= null;
	private String  productName		= null;



	public Integer getIntTransferQty() {
		return intTransferQty;
	}
	public void setIntTransferQty(Integer intTransferQty) {
		this.intTransferQty = intTransferQty;
	}
	public String getStrAccountName() {
		return strAccountName;
	}
	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}
	public String getStrCity() {
		return strCity;
	}
	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}
	public String getStrContactName() {
		return strContactName;
	}
	public void setStrContactName(String strContactName) {
		this.strContactName = strContactName;
	}
	public String getStrMobileNo() {
		return strMobileNo;
	}
	public void setStrMobileNo(String strMobileNo) {
		this.strMobileNo = strMobileNo;
	}
	public String getStrRequestBy() {
		return strRequestBy;
	}
	public void setStrRequestBy(String strRequestBy) {
		this.strRequestBy = strRequestBy;
	}
	public String getStrRequestOn() {
		return strRequestOn;
	}
	public void setStrRequestOn(String strRequestOn) {
		this.strRequestOn = strRequestOn;
	}
	public String getStrRole() {
		return strRole;
	}
	public void setStrRole(String strRole) {
		this.strRole = strRole;
	}
	public String getStrTransferBy() {
		return strTransferBy;
	}
	public void setStrTransferBy(String strTransferBy) {
		this.strTransferBy = strTransferBy;
	}
	public String getStrTransferOn() {
		return strTransferOn;
	}
	public void setStrTransferOn(String strTransferOn) {
		this.strTransferOn = strTransferOn;
	}
	public String getStrTransferType() {
		return strTransferType;
	}
	public void setStrTransferType(String strTransferType) {
		this.strTransferType = strTransferType;
	}
	public String getStrTRNO() {
		return strTRNO;
	}
	public void setStrTRNO(String strTRNO) {
		this.strTRNO = strTRNO;
	}
	public String getStrZone() {
		return strZone;
	}
	public void setStrZone(String strZone) {
		this.strZone = strZone;
	}
	public Integer getIntTrnsBy() {
		return intTrnsBy;
	}
	public void setIntTrnsBy(Integer intTrnsBy) {
		this.intTrnsBy = intTrnsBy;
	}
	public Integer getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}
	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public String getStrTransferReq() {
		return strTransferReq;
	}
	public void setStrTransferReq(String strTransferReq) {
		this.strTransferReq = strTransferReq;
	}

}
