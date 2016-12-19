package com.ibm.virtualization.recharge.beans;

import java.util.ArrayList;


import org.apache.struts.validator.ValidatorForm;



public class MinReorderFormBean extends ValidatorForm
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList arrproductsList=null;
	ArrayList arrBusinessCat=null;
	ArrayList arrPRList=null;
	ArrayList arrDSList=null;
	 private ArrayList warrantyList=null;
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
	




	public void reset1(){
		
		reorderlevel=null;
		record2=null;
		record1=null;
		minreorder=0;
		
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

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	/**
	 * @return
	 */

	public String getRecord1() {
		return record1;
	}

	public void setRecord1(String record1) {
		this.record1 = record1;
	}

	/**
	 * @return
	 */
	
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

	/**
	 * @return
	 */
	public ArrayList getArrproductsList() {
		return arrproductsList;
	}

	/**
	 * @return
	 */
	public String getDistributor() {
		return distributor;
	}

	/**
	 * @return
	 */
	public int getMinreorder() {
		return minreorder;
	}

	/**
	 * @return
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param list
	 */
	

	/**
	 * @param list
	 */
	public void setArrproductsList(ArrayList list) {
		arrproductsList = list;
	}

	/**
	 * @param string
	 */
	public void setDistributor(String string) {
		distributor = string;
	}

	/**
	 * @param i
	 */
	public void setMinreorder(int i) {
		minreorder = i;
	}

	/**
	 * @param string
	 */
	public void setProduct(String string) {
		product = string;
	}

	/**
	 * @return
	 */
	public ArrayList getArrgetChangedValues() {
		return arrgetChangedValues;
	}

	/**
	 * @param list
	 */
	public void setArrgetChangedValues(ArrayList list) {
		arrgetChangedValues = list;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	public void reset(){
		this.arrDSList=null;
		this.arrgetChangedValues=null;
		this.arrproductsList=null;
		this.distributor=null;
		this.message=null;
		this.minreorder=0;
	    this.product=null;
		this.arrBusinessCat=null;
		this.business_catg=null;
		this.arrPRList=null;
	}
	/**
	 * @return
	 */
	public ArrayList getArrBusinessCat() {
		return arrBusinessCat;
	}

	/**
	 * @param list
	 */
	public void setArrBusinessCat(ArrayList list) {
		arrBusinessCat = list;
	}

	/**
	 * @return
	 */
	public String getBusiness_catg() {
		return business_catg;
	}

	/**
	 * @param string
	 */
	public void setBusiness_catg(String string) {
		business_catg = string;
	}

	/**
	 * @return
	 */
	public String getBcode() {
		return bcode;
	}

	/**
	 * @param string
	 */
	public void setBcode(String string) {
		bcode = string;
	}

	public String getWarehouseID() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getActorId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserLoginId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setActorId(String actorId) {
		ActorId = actorId;
	}

	public void setUserLoginId(String userLoginId) {
		UserLoginId = userLoginId;
	}

	public ArrayList getArrDSList() {
		return arrDSList;
	}

	public void setArrDSList(ArrayList arrDSList) {
		this.arrDSList = arrDSList;
	}

	public ArrayList getArrPRList() {
		return arrPRList;
	}

	public void setArrPRList(ArrayList arrPRList) {
		this.arrPRList = arrPRList;
	}

	public String getProductlist() {
		return productlist;
	}

	public void setProductlist(String productlist) {
		this.productlist = productlist;
	}

	public ArrayList getWarrantyList() {
		return warrantyList;
	}

	public void setWarrantyList(ArrayList warrantyList) {
		this.warrantyList = warrantyList;
	}


	}
