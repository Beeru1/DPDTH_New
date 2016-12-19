package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MinmReorderDTO implements Serializable {
	
	ArrayList arrproductsList=null;
	ArrayList arrBusinessCat=null;
	ArrayList arrPRList=null;
	ArrayList arrDSList=null;
	String product="";
	String distributor="";
	ArrayList arrgetChangedValues=new ArrayList(); 
	int minreorder=0;
	String message = null;
	String business_catg=""; 
	String bcode = "";
	
	String ActorId = "";
	String UserLoginId = "";
	String productlist = "";
	String reorderlevel = "";
	String record2 = "";
	String record1 = "";
	int accountid=0;
	int productid = 0;
	private String createdby="";
	private String updatedby="";
	private String datecreated = null;
	private String dateupdated = null;
	private String categorycode="";
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public String getActorId() {
		return ActorId;
	}
	public void setActorId(String actorId) {
		ActorId = actorId;
	}
	public ArrayList getArrBusinessCat() {
		return arrBusinessCat;
	}
	public void setArrBusinessCat(ArrayList arrBusinessCat) {
		this.arrBusinessCat = arrBusinessCat;
	}
	public ArrayList getArrDSList() {
		return arrDSList;
	}
	public void setArrDSList(ArrayList arrDSList) {
		this.arrDSList = arrDSList;
	}
	public ArrayList getArrgetChangedValues() {
		return arrgetChangedValues;
	}
	public void setArrgetChangedValues(ArrayList arrgetChangedValues) {
		this.arrgetChangedValues = arrgetChangedValues;
	}
	public ArrayList getArrPRList() {
		return arrPRList;
	}
	public void setArrPRList(ArrayList arrPRList) {
		this.arrPRList = arrPRList;
	}
	public ArrayList getArrproductsList() {
		return arrproductsList;
	}
	public void setArrproductsList(ArrayList arrproductsList) {
		this.arrproductsList = arrproductsList;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getBusiness_catg() {
		return business_catg;
	}
	public void setBusiness_catg(String business_catg) {
		this.business_catg = business_catg;
	}
	public String getCategorycode() {
		return categorycode;
	}
	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(String datecreated) {
		this.datecreated = datecreated;
	}
	public String getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(String dateupdated) {
		this.dateupdated = dateupdated;
	}
	public String getDistributor() {
		return distributor;
	}
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getMinreorder() {
		return minreorder;
	}
	public void setMinreorder(int minreorder) {
		this.minreorder = minreorder;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getProductlist() {
		return productlist;
	}
	public void setProductlist(String productlist) {
		this.productlist = productlist;
	}
	public String getRecord1() {
		return record1;
	}
	public void setRecord1(String record1) {
		this.record1 = record1;
	}
	public String getRecord2() {
		return record2;
	}
	public void setRecord2(String record2) {
		this.record2 = record2;
	}
	public String getReorderlevel() {
		return reorderlevel;
	}
	public void setReorderlevel(String reorderlevel) {
		this.reorderlevel = reorderlevel;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public String getUserLoginId() {
		return UserLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		UserLoginId = userLoginId;
	}

}
