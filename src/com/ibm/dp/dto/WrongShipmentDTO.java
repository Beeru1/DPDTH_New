package com.ibm.dp.dto;

public class WrongShipmentDTO 
{
	private String wrongSerialsNos ="";
	private String shortShipmentSerials ="";
	private String allSerialsNos="";
	private String serialID="";
	private String invoiceNo="";
	private String deliveryChallanNo="";
	private String productName="";
	private String productId="";
	private String dcId="";
	
	//Added by Nazim Hussain for WRONG SHIP WEBSERVICE
	private String strCircleCode;
	private String strExtDistID;
	private Integer intDistID;
	private Integer intProductID;
	private String strProductCode;
	private String strInvoiceNo;
	private String strPONo;
	private String strTSMDesicion;
	private Integer intShipType;
	

	public String getDcId() {
		return dcId;
	}
	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getAllSerialsNos() {
		return allSerialsNos;
	}
	public void setAllSerialsNos(String allSerialsNos) {
		this.allSerialsNos = allSerialsNos;
	}
	public String getSerialID() {
		return serialID;
	}
	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}
	public String getShortShipmentSerials() {
		return shortShipmentSerials;
	}
	public void setShortShipmentSerials(String shortShipmentSerials) {
		this.shortShipmentSerials = shortShipmentSerials;
	}
	public String getWrongSerialsNos() {
		return wrongSerialsNos;
	}
	public void setWrongSerialsNos(String wrongSerialsNos) {
		this.wrongSerialsNos = wrongSerialsNos;
	}
	public String getDeliveryChallanNo() {
		return deliveryChallanNo;
	}
	public void setDeliveryChallanNo(String deliveryChallanNo) {
		this.deliveryChallanNo = deliveryChallanNo;
	}
	public Integer getIntDistID() {
		return intDistID;
	}
	public void setIntDistID(Integer intDistID) {
		this.intDistID = intDistID;
	}
	public String getStrProductCode() {
		return strProductCode;
	}
	public void setStrProductCode(String strProductCode) {
		this.strProductCode = strProductCode;
	}
	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}
	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = strInvoiceNo;
	}
	public Integer getIntProductID() {
		return intProductID;
	}
	public void setIntProductID(Integer intProductID) {
		this.intProductID = intProductID;
	}
	public String getStrCircleCode() {
		return strCircleCode;
	}
	public void setStrCircleCode(String strCircleCode) {
		this.strCircleCode = strCircleCode;
	}
	public String getStrExtDistID() {
		return strExtDistID;
	}
	public void setStrExtDistID(String strExtDistID) {
		this.strExtDistID = strExtDistID;
	}
	public String getStrPONo() {
		return strPONo;
	}
	public void setStrPONo(String strPONo) {
		this.strPONo = strPONo;
	}
	public String getStrTSMDesicion() {
		return strTSMDesicion;
	}
	public void setStrTSMDesicion(String strTSMDesicion) {
		this.strTSMDesicion = strTSMDesicion;
	}
	public Integer getIntShipType() {
		return intShipType;
	}
	public void setIntShipType(Integer intShipType) {
		this.intShipType = intShipType;
	}

	
}
