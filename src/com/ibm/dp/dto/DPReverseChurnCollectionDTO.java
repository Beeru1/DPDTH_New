package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DPReverseChurnCollectionDTO implements Serializable 
{
	private String serial_Number=""; 
	private String product_Id=""; 
	private String vc_Id="";  
	private String customer_Id="";
	private String si_Id=""; 
	private int ageing; 
	private List list_srno = new ArrayList(); 
	private String product_Name="";
	private String err_msg=""; 
	private String statusId="";
	private Integer intReqID = 0;
	int prod_Id;
	private String collectionDate;
	
	public int getAgeing() {
		return ageing;
	}
	public void setAgeing(int ageing) {
		this.ageing = ageing;
	}
	public String getCustomer_Id() {
		return customer_Id;
	}
	public void setCustomer_Id(String customer_Id) {
		this.customer_Id = customer_Id;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public List getList_srno() {
		return list_srno;
	}
	public void setList_srno(List list_srno) {
		this.list_srno = list_srno;
	}
	public int getProd_Id() {
		return prod_Id;
	}
	public void setProd_Id(int prod_Id) {
		this.prod_Id = prod_Id;
	}
	public String getProduct_Id() {
		return product_Id;
	}
	public void setProduct_Id(String product_Id) {
		this.product_Id = product_Id;
	}
	public String getProduct_Name() {
		return product_Name;
	}
	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}
	public String getSerial_Number() {
		return serial_Number;
	}
	public void setSerial_Number(String serial_Number) {
		this.serial_Number = serial_Number;
	}
	public String getSi_Id() {
		return si_Id;
	}
	public void setSi_Id(String si_Id) {
		this.si_Id = si_Id;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getVc_Id() {
		return vc_Id;
	}
	public void setVc_Id(String vc_Id) {
		this.vc_Id = vc_Id;
	}
	public Integer getIntReqID() {
		return intReqID;
	}
	public void setIntReqID(Integer intReqID) {
		this.intReqID = intReqID;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	
}
