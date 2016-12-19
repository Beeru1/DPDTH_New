package com.ibm.dp.beans;



import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class DPStockDetailsReportBean extends ActionForm
{
	String accountName;
	String role;
	String productName;
	String openingStockQty;
	String stockReceivedQty;
	String allocationQty;
	String saleByRetailersQty;
	String distID;
	String distName;
	List distList=new ArrayList();
	List reportStockDataList=new ArrayList();
	
		
	public String getDistID() {
		return distID;
	}
	public void setDistID(String distID) {
		this.distID = distID;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAllocationQty() {
		return allocationQty;
	}
	public void setAllocationQty(String allocationQty) {
		this.allocationQty = allocationQty;
	}
	public String getOpeningStockQty() {
		return openingStockQty;
	}
	public void setOpeningStockQty(String openingStockQty) {
		this.openingStockQty = openingStockQty;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSaleByRetailersQty() {
		return saleByRetailersQty;
	}
	public void setSaleByRetailersQty(String saleByRetailersQty) {
		this.saleByRetailersQty = saleByRetailersQty;
	}
	public String getStockReceivedQty() {
		return stockReceivedQty;
	}
	public void setStockReceivedQty(String stockReceivedQty) {
		this.stockReceivedQty = stockReceivedQty;
	}
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
	}
	public List getReportStockDataList() {
		return reportStockDataList;
	}
	public void setReportStockDataList(List reportStockDataList) {
		this.reportStockDataList = reportStockDataList;
	}
	
	
	
	


}
