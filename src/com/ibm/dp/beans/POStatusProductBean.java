package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

public class POStatusProductBean {

	
	private String productid= "";
	private int prdQty;
	private int invQty;
	private int intProdID;
	
	
	private List serialNos= new ArrayList();
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public List getSerialNos() {
		return serialNos;
	}
	public void setSerialNos(List serialNos) {
		this.serialNos = serialNos;
	}
	public int getInvQty() {
		return invQty;
	}
	public void setInvQty(int invQty) {
		this.invQty = invQty;
	}
	public int getPrdQty() {
		return prdQty;
	}
	public void setPrdQty(int prdQty) {
		this.prdQty = prdQty;
	}
	public int getIntProdID() {
		return intProdID;
	}
	public void setIntProdID(int intProdID) {
		this.intProdID = intProdID;
	}
	
	 
		
}
