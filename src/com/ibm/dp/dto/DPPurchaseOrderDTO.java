/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.dp.dto;

import java.io.Serializable;

/**
 * DPPurchaseOrderDTO class holds the Serializable Properties for Purchase Order Requisition
 *
 * @author Rohit Kunder
 */
public class DPPurchaseOrderDTO implements Serializable {
	
private int bcode=0;
private String bname="";
private int productID;//view
private String catCode="";
private String productName="";
private int porNumber=0; 
private String productCode="";
private String businessCategory="";
private String quantity="";
private String categoryCode="";
private String categoryName="";
private int por_no;
private String product ="";
private String  pr_dt ="";
private long raised_quantity; //view
private String po_no;
private String po_dt="";
private String invoice_no; //v
private String invoice_dt ="";//v
private long invqty;//v
private String dc_no ;//v
private String dc_dt ="";//v
private String dd_cheque_no;//v
private String dd_cheque_dt ="";//v
private String status ="";
private int circleId;
private int loginID;
private String distName;
private long createdBy;
private long updatedBy;
private String prcancelStatus ="";
private String acceptStatus = "";
private String prNo="";
private String countPrNo="";
private String unit;
private String unitName;
//added by saumya
private String invprod;
private String remarks;
private long accptqty;//v
private long missingqty;
private long addedty;
private long poqty;
private String dcNo ;//v
//added by harpreet
private String statusValue;
private String statusId;
private String productIDNew;//view




public String getUnitName() {
	return unitName;
}

public void setUnitName(String unitName) {
	this.unitName = unitName;
}

public String getUnit() {
	return unit;
}

public void setUnit(String unit) {
	this.unit = unit;
}

public String getCountPrNo() {
	return countPrNo;
}

public void setCountPrNo(String countPrNo) {
	this.countPrNo = countPrNo;
}

public String getPrNo() {
	return prNo;
}

public void setPrNo(String prNo) {
	this.prNo = prNo;
}

public String getAcceptStatus() {
	return acceptStatus;
}

public void setAcceptStatus(String acceptStatus) {
	this.acceptStatus = acceptStatus;
}

public String getPrcancelStatus() {
	return prcancelStatus;
}

public void setPrcancelStatus(String prcancelStatus) {
	this.prcancelStatus = prcancelStatus;
}

public String getDc_dt() {
	return dc_dt;
}

public void setDc_dt(String dc_dt) {
	this.dc_dt = dc_dt;
}

public String getDc_no() {
	return dc_no;
}

public void setDc_no(String dc_no) {
	this.dc_no = dc_no;
}

public String getDd_cheque_dt() {
	return dd_cheque_dt;
}

public void setDd_cheque_dt(String dd_cheque_dt) {
	this.dd_cheque_dt = dd_cheque_dt;
}

public String getDd_cheque_no() {
	return dd_cheque_no;
}

public void setDd_cheque_no(String dd_cheque_no) {
	this.dd_cheque_no = dd_cheque_no;
}

public String getInvoice_dt() {
	return invoice_dt;
}

public void setInvoice_dt(String invoice_dt) {
	this.invoice_dt = invoice_dt;
}

public String getInvoice_no() {
	return invoice_no;
}

public void setInvoice_no(String invoice_no) {
	this.invoice_no = invoice_no;
}

public String getPo_dt() {
	return po_dt;
}

public void setPo_dt(String po_dt) {
	this.po_dt = po_dt;
}

public String getPo_no() {
	return po_no;
}

public void setPo_no(String po_no) {
	this.po_no = po_no;
}

public int getPor_no() {
	return por_no;
}

public void setPor_no(int por_no) {
	this.por_no = por_no;
} 

public String getPr_dt() {
	return pr_dt;
}

public void setPr_dt(String pr_dt) {
	this.pr_dt = pr_dt;
}

public String getProduct() {
	return product;
}

public void setProduct(String product) {
	this.product = product;
}

public long getRaised_quantity() {
	return raised_quantity;
}

public void setRaised_quantity(long raised_quantity) { //view
	this.raised_quantity = raised_quantity;
}


/**
 * @return
 */
public int getBcode() {
	return bcode;
}

/**
 * @return
 */
public String getBname() {
	return bname;
}

/**
 * @param i
 */
public void setBcode(int i) {
	bcode = i;
}

/**
 * @param string
 */
public void setBname(String string) {
	bname = string;
}


public String getCatCode() {
	return catCode;
}

public void setCatCode(String catCode) {
	this.catCode = catCode;
}

public String getBusinessCategory() {
	return businessCategory;
}

public void setBusinessCategory(String businessCategory) {
	this.businessCategory = businessCategory;
}

public int getPorNumber() {
	return porNumber;
}

public void setPorNumber(int porNumber) {
	this.porNumber = porNumber;
}

public String getProductCode() {
	return productCode;
}

public void setProductCode(String productCode) {
	this.productCode = productCode;
}

public String getQuantity() {
	return quantity;
}

public void setQuantity(String quantity) {
	this.quantity = quantity;
}

public String getCategoryCode() {
	return categoryCode;
}

public void setCategoryCode(String categoryCode) {
	this.categoryCode = categoryCode;
}

public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public int getCircleId() {
	return circleId;
}

public void setCircleId(int circleId) {
	this.circleId = circleId;
}

public long getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(long createdBy) {
	this.createdBy = createdBy;
}

public long getUpdatedBy() {
	return updatedBy;
}

public void setUpdatedBy(long updatedBy) {
	this.updatedBy = updatedBy;
}

public int getLoginID() {
	return loginID;
}

public void setLoginID(int loginID) {
	this.loginID = loginID;
}

public int getProductID() {
	return productID;
}

public void setProductID(int productID) {
	this.productID = productID;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public String getDistName() {
	return distName;
}

public void setDistName(String distName) {
	this.distName = distName;
}

public long getInvqty() {
	return invqty;
}

public void setInvqty(long invqty) {
	this.invqty = invqty;
}

public String getInvprod() {
	return invprod;
}

public void setInvprod(String invprod) {
	this.invprod = invprod;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public long getAccptqty() {
	return accptqty;
}

public void setAccptqty(long accptqty) {
	this.accptqty = accptqty;
}

public long getAddedty() {
	return addedty;
}

public void setAddedty(long addedty) {
	this.addedty = addedty;
}

public long getMissingqty() {
	return missingqty;
}

public void setMissingqty(long missingqty) {
	this.missingqty = missingqty;
}

public long getPoqty() {
	return poqty;
}

public void setPoqty(long poqty) {
	this.poqty = poqty;
}

public String getDcNo() {
	return dcNo;
}

public void setDcNo(String dcNo) {
	this.dcNo = dcNo;
}

public String getStatusId() {
	return statusId;
}

public void setStatusId(String statusId) {
	this.statusId = statusId;
}

public String getStatusValue() {
	return statusValue;
}

public void setStatusValue(String statusValue) {
	this.statusValue = statusValue;
}

public String getProductIDNew() {
	return productIDNew;
}

public void setProductIDNew(String productIDNew) {
	this.productIDNew = productIDNew;
}

}

