package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;
import com.ibm.dp.dto.CheckRetailerBalanceDto;
import com.ibm.dp.dto.InterSSDReportDTO;


public class CheckRetailerBalanceBean extends ActionForm {
	private int retailerid;
	private String retailername;
	private String retailerlapu;
	private String balance;
	private ArrayList<CheckRetailerBalanceDto> displayDetails = new ArrayList<CheckRetailerBalanceDto>();
	private long distributorid;
	private int fseid;
	private String fsename;
	private String depth;
	private String fsemobile;
	
	
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
	public void setDisplayDetails(ArrayList<CheckRetailerBalanceDto> displayDetails) {
		this.displayDetails = displayDetails;
	}
	public ArrayList<CheckRetailerBalanceDto> getDisplayDetails() {
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
