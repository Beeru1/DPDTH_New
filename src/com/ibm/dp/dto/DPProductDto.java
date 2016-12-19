package com.ibm.dp.dto;

import java.util.ArrayList;
import java.util.List;

public class DPProductDto {

	
	private String dpCircleName="";
	private String prodCode="";
	private String prodName="";	
	private String cardGrpName;	
	private String prodType;	
	private String dpMrp;	
	private String basicValue;
	private String rechargeValue;
	private String dpActivation;
	private String serviceTax;
	private String cess;
	private String cessTax;
	private String distMargin;	
	private String distPrice;
	private String effectiveDate;
	private String creationDate;
	private String dpStatus;
	
	
	private List prodList =new ArrayList();
	
	
	
	
	public List getProdList() {
		return prodList;
	}
	public void setProdList(List prodList) {
		this.prodList = prodList;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getCardGrpName() {
		return cardGrpName;
	}
	public void setCardGrpName(String cardGrpName) {
		this.cardGrpName = cardGrpName;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getBasicValue() {
		return basicValue;
	}
	public void setBasicValue(String basicValue) {
		this.basicValue = basicValue;
	}
	public String getRechargeValue() {
		return rechargeValue;
	}
	public void setRechargeValue(String rechargeValue) {
		this.rechargeValue = rechargeValue;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getCess() {
		return cess;
	}
	public void setCess(String cess) {
		this.cess = cess;
	}
	public String getCessTax() {
		return cessTax;
	}
	public void setCessTax(String cessTax) {
		this.cessTax = cessTax;
	}
	public String getDistMargin() {
		return distMargin;
	}
	public void setDistMargin(String distMargin) {
		this.distMargin = distMargin;
	}
	public String getDistPrice() {
		return distPrice;
	}
	public void setDistPrice(String distPrice) {
		this.distPrice = distPrice;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getDpCircleName() {
		return dpCircleName;
	}
	public void setDpCircleName(String dpCircleName) {
		this.dpCircleName = dpCircleName;
	}
	public String getDpMrp() {
		return dpMrp;
	}
	public void setDpMrp(String dpMrp) {
		this.dpMrp = dpMrp;
	}
	public String getDpActivation() {
		return dpActivation;
	}
	public void setDpActivation(String dpActivation) {
		this.dpActivation = dpActivation;
	}
	public String getDpStatus() {
		return dpStatus;
	}
	public void setDpStatus(String dpStatus) {
		this.dpStatus = dpStatus;
	}


}
