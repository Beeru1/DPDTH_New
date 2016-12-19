/*
 * Created on Nov 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dto;

/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProductDTO {
	
 int bcode=0;
 String productcode="";
 String modelcode="";
 String modelname="";
 String companyname="";
 String productdesc="";
 String stType=""; //by sachin
 String productId="";
 String stocktype="";
 String createddate="";
 String createdby="";
 String updateddate="";
 String updatedby="";
 String bname="";
 int product_id=0;
 
  String prod_stck_type="";
  String pseudo_prodcode="";
  String bndlStatus="";
  int packType=0;
  String circleCode = ""; 
  int Status=1;
 
  int warranty=0;

  String prodCardGroup="";
  boolean beditMode=false;
  String userName="";
  String passWord="";
  
  
	/**
	 * @return
	 */
	

public int getPackType() {
	return packType;
}

public void setPackType(int packType) {
	this.packType = packType;
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
public String getCompanyname() {
	return companyname;
}

/**
 * @return
 */
public String getModelcode() {
	return modelcode;
}

/**
 * @return
 */
public String getModelname() {
	return modelname;
}

/**
 * @return
 */
public String getProductcode() {
	return productcode;
}

/**
 * @return
 */
public String getProductdesc() {
	return productdesc;
}

/**
 * @return
 */
public String getStocktype() {
	return stocktype;
}

/**
 * @return
 */
public int getWarranty() {
	return warranty;
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
public void setCompanyname(String string) {
	companyname = string;
}

/**
 * @param string
 */
public void setModelcode(String string) {
	modelcode = string;
}

/**
 * @param string
 */
public void setModelname(String string) {
	modelname = string;
}

/**
 * @param string
 */
public void setProductcode(String string) {
	productcode = string;
}

/**
 * @param string
 */
public void setProductdesc(String string) {
	productdesc = string;
}

/**
 * @param string
 */
public void setStocktype(String string) {
	stocktype = string;
}

/**
 * @param i
 */
public void setWarranty(int i) {
	warranty = i;
}

/**
 * @return
 */
public String getCreatedby() {
	return createdby;
}

/**
 * @return
 */
public String getCreateddate() {
	return createddate;
}

/**
 * @return
 */
public String getUpdatedby() {
	return updatedby;
}

/**
 * @return
 */
public String getUpdateddate() {
	return updateddate;
}

/**
 * @param string
 */
public void setCreatedby(String string) {
	createdby = string;
}

/**
 * @param string
 */
public void setCreateddate(String string) {
	createddate = string;
}

/**
 * @param string
 */
public void setUpdatedby(String string) {
	updatedby = string;
}

/**
 * @param string
 */
public void setUpdateddate(String string) {
	updateddate = string;
}

/**
 * @return
 */
public String getBname() {
	return bname;
}

/**
 * @param string
 */
public void setBname(String string) {
	bname = string;
}

/**
 * @return
 */
public String getBndlStatus() {
	return bndlStatus;
}

/**
 * @return
 */
public String getProd_stck_type() {
	return prod_stck_type;
}

/**
 * @return
 */
public String getPseudo_prodcode() {
	return pseudo_prodcode;
}

/**
 * @param string
 */
public void setBndlStatus(String string) {
	bndlStatus = string;
}

/**
 * @param string
 */
public void setProd_stck_type(String string) {
	prod_stck_type = string;
}

/**
 * @param string
 */
public void setPseudo_prodcode(String string) {
	pseudo_prodcode = string;
}

/**
 * @return
 */
public String getStType() {
	return stType;
}

/**
 * @param string
 */
public void setStType(String string) {
	stType = string;
}

public String getProductId() {
	return productId;
}

public void setProductId(String productId) {
	this.productId = productId;
}

public int getProduct_id() {
	return product_id;
}

public void setProduct_id(int product_id) {
	this.product_id = product_id;
}

public String getCircleCode() {
	return circleCode;
}

public void setCircleCode(String circleCode) {
	this.circleCode = circleCode;
}

public int getStatus() {
	return Status;
}

public void setStatus(int status) {
	Status = status;
}

}
