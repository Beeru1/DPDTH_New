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

public class DPInventoryStatusFormBean extends ActionForm
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
	private List reportDataList = new ArrayList();
	private String assignDate = "";
	private String customerId = "";
	private String customerName = "";
	private String fromDate = "";
	private String toDate = "";
	private String role="";
//	saumya
	private List productList = new ArrayList();
	private String productId="";
	//saumya
		
	
		
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public List getReportDataList() {
		return reportDataList;
	}
	public void setReportDataList(List reportDataList) {
		this.reportDataList = reportDataList;
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
	public List getProductList() {
		return productList;
	}
	public void setProductList(List productList) {
		this.productList = productList;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
