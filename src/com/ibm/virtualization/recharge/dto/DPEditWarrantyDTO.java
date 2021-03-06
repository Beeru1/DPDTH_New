package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class DPEditWarrantyDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Handset services
	private int bcode=0;
	private String bname=""; 
	private String productcode="";
	private String productname="";	
	private int warranty=0;	
	private int extwarranty=0;
	private String updatedby="";
	private String selectedSub;	
	private String dateupdated = null;
	private String categorycode="";
	private ArrayList arrBCList=null;
	private String subList="";
	private int productid=0;
	private String LoginName="";
	private String businessCategory ="";
	private ArrayList productList = null;
	private int category=0;


	public int getCategory() {
		return category;
	}


	public void setCategory(int category) {
		this.category = category;
	}


	public ArrayList getArrBCList() {
		return arrBCList;
	}


	public void setArrBCList(ArrayList arrBCList) {
		this.arrBCList = arrBCList;
	}


	public int getBcode() {
		return bcode;
	}


	public void setBcode(int bcode) {
		this.bcode = bcode;
	}


	public String getBname() {
		return bname;
	}


	public void setBname(String bname) {
		this.bname = bname;
	}


	public String getBusinessCategory() {
		return businessCategory;
	}


	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}


	public String getCategorycode() {
		return categorycode;
	}


	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}


	public String getDateupdated() {
		return dateupdated;
	}


	public void setDateupdated(String dateupdated) {
		this.dateupdated = dateupdated;
	}


	public String getLoginName() {
		return LoginName;
	}


	public void setLoginName(String loginName) {
		LoginName = loginName;
	}


	public String getProductcode() {
		return productcode;
	}


	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}


	public int getProductid() {
		return productid;
	}


	public void setProductid(int productid) {
		this.productid = productid;
	}


	public ArrayList getProductList() {
		return productList;
	}


	public void setProductList(ArrayList productList) {
		this.productList = productList;
	}


	public String getProductname() {
		return productname;
	}


	public void setProductname(String productname) {
		this.productname = productname;
	}


	public String getSelectedSub() {
		return selectedSub;
	}


	public void setSelectedSub(String selectedSub) {
		this.selectedSub = selectedSub;
	}


	public String getSubList() {
		return subList;
	}


	public void setSubList(String subList) {
		this.subList = subList;
	}


	public String getUpdatedby() {
		return updatedby;
	}


	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	public int getWarranty() {
		return warranty;
	}


	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}


	public int getExtwarranty() {
		return extwarranty;
	}


	public void setExtwarranty(int extwarranty) {
		this.extwarranty = extwarranty;
	}

}
