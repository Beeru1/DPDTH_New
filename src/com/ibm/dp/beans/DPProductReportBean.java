package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import com.ibm.dp.dto.AccountManagementActivityReportDto;
import com.ibm.dp.dto.DPProductDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.GenericOptionDTO;

public class DPProductReportBean extends ActionForm{
	
	private String circleName="";
	private String prodCode="";
	private String prodName="";	
	private String cardGrpName;	
	private String prodType;	
	private String mrp;	
	private String basicValue;
	private String rechargeValue;
	private String activation;
	private String serviceTax;
	private String cess;
	private String cessTax;
	private String distMargin;	
	private String distPrice;
	private String effectiveDate;
	private String creationDate;
	private String status;
	
	private List<DPProductDto> prodList =new ArrayList<DPProductDto>();

	
	
	public List<DPProductDto> getProdList() {
		return prodList;
	}

	public void setProdList(List<DPProductDto> prodList) {
		this.prodList = prodList;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
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

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
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

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
