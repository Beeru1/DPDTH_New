package com.ibm.dp.dto;

import java.util.ArrayList;

public class CheckRetailerBalanceDto {
	
	private int rowNum;
	private int totalRecords;
	private Long loginId;
	private int retailerid;
	private String retailername;
	private String retailerlapu;
	private String balance;
	private ArrayList displayDetails;
	private long distributorid;
	private int fseid;
	private String fsename;
	private String depth;
	private String fsemobile;
	
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setRetailerid(int retailerid) {
		this.retailerid = retailerid;
	}
	public int getRetailerid() {
		return retailerid;
	}
	public void setRetailername(String retailername) {
		this.retailername = retailername;
	}
	public String getRetailername() {
		return retailername;
	}
	public void setRetailerlapu(String retailerlapu) {
		this.retailerlapu = retailerlapu;
	}
	public String getRetailerlapu() {
		return retailerlapu;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBalance() {
		return balance;
	}
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}
	public void setDistributorid(long distributorid) {
		this.distributorid = distributorid;
	}
	public long getDistributorid() {
		return distributorid;
	}
	public void setFseid(int fseid) {
		this.fseid = fseid;
	}
	public int getFseid() {
		return fseid;
	}
	public void setFsename(String fsename) {
		this.fsename = fsename;
	}
	public String getFsename() {
		return fsename;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDepth() {
		return depth;
	}
	public void setFsemobile(String fsemobile) {
		this.fsemobile = fsemobile;
	}
	public String getFsemobile() {
		return fsemobile;
	}
	
	

}
