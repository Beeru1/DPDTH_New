/*
 * Created on Nov 30, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOMarkDamagedFormBean extends ValidatorForm{
	public	ArrayList arrImeino=new ArrayList();
	public ArrayList arrImeiList =new ArrayList();
	public ArrayList arrSnoList = new ArrayList();
	public String imeiNo="";
	public String simnoList="";
	public	String status="";
	public String simno="";
	public String newImeiNo="";
	public String invoice = "";
	public ArrayList categoryList = null;
	public String category = "";
	public String product = "";
	public String serialNo = "";
	public String remarks = "";
	public float cost=0;
	public String productType="";
	public String role = ""; 
	public String catType = "";
	public String simNo = "";
	public ArrayList damagedProdList = null;
	
	public String getCatType() {
		return catType;
	}
	public void setCatType(String catType) {
		this.catType = catType;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public void reset(){
		categoryList = null;
		category = null;
		product = null;
		serialNo = null;
		remarks = null;
		cost = 0;
	}
	public ArrayList getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return
	 */
	public ArrayList getArrImeino() {
		return arrImeino;
	}

	/**
	 * @param list
	 */
	public void setArrImeino(ArrayList list) {
		arrImeino = list;
	}

	/**
	 * @return
	 */
	public ArrayList getArrImeiList() {
		return arrImeiList;
	}

	/**
	 * @param list
	 */
	public void setArrImeiList(ArrayList list) {
		arrImeiList = list;
	}

	

	/**
	 * @return
	 */
	public String getSimnoList() {
		return simnoList;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	

	/**
	 * @param string
	 */
	public void setSimnoList(String string) {
		simnoList = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @return
	 */
	public String getImeiNo() {
		return imeiNo;
	}

	/**
	 * @param string
	 */
	public void setImeiNo(String string) {
		imeiNo = string;
	}

	/**
	 * @return
	 */
	public String getSimno() {
		return simno;
	}

	/**
	 * @param string
	 */
	public void setSimno(String string) {
		simno = string;
	}

	/**
	 * @return
	 */
	public String getNewImeiNo() {
		return newImeiNo;
	}

	/**
	 * @param string
	 */
	public void setNewImeiNo(String string) {
		newImeiNo = string;
	}

	/**
	 * @return
	 */
	public String getInvoice() {
		return invoice;
	}

	/**
	 * @param string
	 */
	public void setInvoice(String string) {
		invoice = string;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ArrayList getArrSnoList() {
		return arrSnoList;
	}
	public void setArrSnoList(ArrayList arrSnoList) {
		this.arrSnoList = arrSnoList;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public ArrayList getDamagedProdList() {
		return damagedProdList;
	}
	public void setDamagedProdList(ArrayList damagedProdList) {
		this.damagedProdList = damagedProdList;
	}

}
