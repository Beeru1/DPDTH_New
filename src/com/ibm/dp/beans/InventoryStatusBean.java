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

public class InventoryStatusBean
{
	private String levelId =""; // roleId
	private String levelName =""; // roleName
	private String accountId =""; // 
	private String accountName =""; //
	private String productName ="";
	private String serialNo="";
	private String status="";
	private String distributorPurchaseDate="";
	private List productStatusList = new ArrayList();
	private String assignDate = "";
	private String customerId = "";
	private String customerName = "";
	private String role ="";
	private String prNo ="";
	private String poNo="";
	private String accountType="";
	
		
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
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
	public List getProductStatusList() {
		return productStatusList;
	}
	public void setProductStatusList(List productStatusList) {
		this.productStatusList = productStatusList;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPrNo() {
		return prNo;
	}
	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}
}
