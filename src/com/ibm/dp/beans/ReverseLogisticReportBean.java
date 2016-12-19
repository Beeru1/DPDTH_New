package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
/**
 * 
 * @author harbans singh
 *	Bean class for report poc with S&D
 *
 */

public class ReverseLogisticReportBean
{
	private String levelId =""; // roleId
	private String levelName =""; // roleName
	private String accountId =""; // 
	private String accountName =""; //
	private String productName ="";
	private String serialNo="";
	private String status="";
	private String distributorPurchaseDate="";
	private List revLogList = new ArrayList();
	private String assignDate = "";
	private String customerId = "";
	private String customerName = "";
	
		
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public List getRevLogList() {
		return revLogList;
	}
	public void setRevLogList(List revLogList) {
		this.revLogList = revLogList;
	}
	public String getDistributorPurchaseDate() {
		return distributorPurchaseDate;
	}
	public void setDistributorPurchaseDate(String distributorPurchaseDate) {
		this.distributorPurchaseDate = distributorPurchaseDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
}
