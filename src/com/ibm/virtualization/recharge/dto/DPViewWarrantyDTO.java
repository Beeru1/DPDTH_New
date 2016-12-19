package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.ArrayList;


public class DPViewWarrantyDTO implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bcode=0;
	private String bname=""; 	
	 private ArrayList warrantyList=null;
	private String productcode="";
	private String productname="";
	private String cardgroup="";
	private String packtype="";
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
	private int category=0;
	private String businessCategory ="";
	private int mnreorder =0;
	private String imeino ="";
	private String imeiNo ="";
	private String imeiwrhs ="";
	private String dmgstatus ="";
	private int stdwrnty = 0;
	private int extwrnty = 0;
	private int prdcode = 0;
	
	
	private ArrayList productList = null;


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


	public String getPacktype() {
		return packtype;
	}


	public void setPacktype(String packtype) {
		this.packtype = packtype;
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


	public int getCategory() {
		return category;
	}


	public void setCategory(int category) {
		this.category = category;
	}


	public int getExtwarranty() {
		return extwarranty;
	}


	public void setExtwarranty(int extwarranty) {
		this.extwarranty = extwarranty;
	}


	public String getDmgstatus() {
		return dmgstatus;
	}


	public void setDmgstatus(String dmgstatus) {
		this.dmgstatus = dmgstatus;
	}


	public int getExtwrnty() {
		return extwrnty;
	}


	public void setExtwrnty(int extwrnty) {
		this.extwrnty = extwrnty;
	}


	public String getImeino() {
		return imeino;
	}


	public void setImeino(String imeino) {
		this.imeino = imeino;
	}


	public String getImeiwrhs() {
		return imeiwrhs;
	}


	public void setImeiwrhs(String imeiwrhs) {
		this.imeiwrhs = imeiwrhs;
	}


	public int getMnreorder() {
		return mnreorder;
	}


	public void setMnreorder(int mnreorder) {
		this.mnreorder = mnreorder;
	}


	public int getPrdcode() {
		return prdcode;
	}


	public void setPrdcode(int prdcode) {
		this.prdcode = prdcode;
	}


	public int getStdwrnty() {
		return stdwrnty;
	}


	public void setStdwrnty(int stdwrnty) {
		this.stdwrnty = stdwrnty;
	}


	public ArrayList getWarrantyList() {
		return warrantyList;
	}


	public void setWarrantyList(ArrayList warrantyList) {
		this.warrantyList = warrantyList;
	}


	public String getImeiNo() {
		return imeiNo;
	}


	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}
}
