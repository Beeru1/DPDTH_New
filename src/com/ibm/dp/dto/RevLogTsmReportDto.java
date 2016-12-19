package com.ibm.dp.dto;

public class RevLogTsmReportDto
{
	String accountName;
	String role;
	String productName;
	String openingStockQty;
	String stockReceivedQty;
	String allocationQty;
	String saleByRetailersQty;
	String closingStock;
	
	String distID;
	String distName;
	
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
	public String getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(String closingStock) {
		this.closingStock = closingStock;
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
	
	
	
	
}
