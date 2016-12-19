package com.ibm.virtualization.recharge.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class DPUpdateWebServiceInfo implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bcode=0;
	private String bname=""; 	
	private String companyname="";
	private String productdesc="";
	private String stocktype="";
	

		
	
	// SUK Services
	private String productcode="";
	private String productname="";
	private String productname1="";
	private String Productname2="";
	private String cardgroup="";
	private String packtype="";
	private double mrp=0;
	private double simcardcost=0;
	private double talktime=0;
	private String activation="";
	private double processingfee=0;
	private double salestax=0;
	private double servicetax=0;
	private double cesstax=0;
	private double vat=0;
	private double turnovertax=0;
	private double sh_educess=0;
	private double goldennumbercharge=0;
	private double discount=0;
	private double distprice=0;
	private double costprice=0;
	private double octoriprice=0;
	private String effectivedate="";
	private String version="";
	
	private String freeservice="";

	
	
	// commmon services
	private int warranty=0;
	private String createddate="";
	private String createdby="";
	private String updatedby="";
	private String selectedSub;
	private String status="A";
	private String datecreeated = null;
	private String dateupdated = null;
	private String categorycode="";
	private ArrayList arrBCList=null;
	private String subList="";
	private String productid="";
	private int prodid=0;
	private String LoginName="";
	private String businessCategory =""; 
	private ArrayList arrCIList=null;
	private int circleid = 0;
	public String getActivation() {
		return activation;
	}
	public void setActivation(String activation) {
		this.activation = activation;
	}
	public ArrayList getArrBCList() {
		return arrBCList;
	}
	public void setArrBCList(ArrayList arrBCList) {
		this.arrBCList = arrBCList;
	}
	public ArrayList getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
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
	public String getCardgroup() {
		return cardgroup;
	}
	public void setCardgroup(String cardgroup) {
		this.cardgroup = cardgroup;
	}
	public String getCategorycode() {
		return categorycode;
	}
	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}
	public double getCesstax() {
		return cesstax;
	}
	public void setCesstax(double cesstax) {
		this.cesstax = cesstax;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public double getCostprice() {
		return costprice;
	}
	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getDatecreeated() {
		return datecreeated;
	}
	public void setDatecreeated(String datecreeated) {
		this.datecreeated = datecreeated;
	}
	public String getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(String dateupdated) {
		this.dateupdated = dateupdated;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getDistprice() {
		return distprice;
	}
	public void setDistprice(double distprice) {
		this.distprice = distprice;
	}
	public String getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}
	public String getFreeservice() {
		return freeservice;
	}
	public void setFreeservice(String freeservice) {
		this.freeservice = freeservice;
	}
	public double getGoldennumbercharge() {
		return goldennumbercharge;
	}
	public void setGoldennumbercharge(double goldennumbercharge) {
		this.goldennumbercharge = goldennumbercharge;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	public double getOctoriprice() {
		return octoriprice;
	}
	public void setOctoriprice(double octoriprice) {
		this.octoriprice = octoriprice;
	}
	public String getPacktype() {
		return packtype;
	}
	public void setPacktype(String packtype) {
		this.packtype = packtype;
	}
	public double getProcessingfee() {
		return processingfee;
	}
	public void setProcessingfee(double processingfee) {
		this.processingfee = processingfee;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductname1() {
		return productname1;
	}
	public void setProductname1(String productname1) {
		this.productname1 = productname1;
	}
	public String getProductname2() {
		return Productname2;
	}
	public void setProductname2(String productname2) {
		Productname2 = productname2;
	}
	public double getSalestax() {
		return salestax;
	}
	public void setSalestax(double salestax) {
		this.salestax = salestax;
	}
	public String getSelectedSub() {
		return selectedSub;
	}
	public void setSelectedSub(String selectedSub) {
		this.selectedSub = selectedSub;
	}
	public double getServicetax() {
		return servicetax;
	}
	public void setServicetax(double servicetax) {
		this.servicetax = servicetax;
	}
	public double getSh_educess() {
		return sh_educess;
	}
	public void setSh_educess(double sh_educess) {
		this.sh_educess = sh_educess;
	}
	public double getSimcardcost() {
		return simcardcost;
	}
	public void setSimcardcost(double simcardcost) {
		this.simcardcost = simcardcost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStocktype() {
		return stocktype;
	}
	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}
	public String getSubList() {
		return subList;
	}
	public void setSubList(String subList) {
		this.subList = subList;
	}
	public double getTalktime() {
		return talktime;
	}
	public void setTalktime(double talktime) {
		this.talktime = talktime;
	}
	public double getTurnovertax() {
		return turnovertax;
	}
	public void setTurnovertax(double turnovertax) {
		this.turnovertax = turnovertax;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getWarranty() {
		return warranty;
	}
	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}
	public int getProdid() {
		return prodid;
	}
	public void setProdid(int prodid) {
		this.prodid = prodid;
	}

}
