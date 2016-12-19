
package com.ibm.dp.dto;

import java.io.Serializable;


public class DPReportDTO implements Serializable{
		
	private static final long serialVersionUID = 1L;
	private String reportName="";
	private String reportId="";
	private int noColumns = 0;
	private String hub = "";
	private String circle = "";
	private String zone = "";
	private String distributorName = "";
	private String distributormsisdn = "";
	private String openingBal = "";
	private String closingBal = "";
	private String returns = "";
	private String received = "";
	private String sales = "";
	private String retCode = "";
	private String retName = "";
	private String retmsisdn = "";
	private String prodType = "";
	private String prodCat = "";

	private String fseName = "";
	private String date="";
	private long mtdSales=0;
	private long lmtdSales=0;
	private long mtdPrimaryPurchase=0;
	private long lmtdPrimaryPurchase=0;
	private int noLtEq90 = 0;
	private int noGt90 = 0;
	private String bucketCode = "";
	private int noOfRetailers = 0;
	private long closingStock = 0;
	private int stockNoOfDays = 0;
	private String category = "";
	private String beatName = "";
	private String zsmName = "";
	private String tmName = "";
	private int grossActivations = 0;
	private String paperRc = "";
	private long easyRecharge = 0;
	private String bucketName = "";
	
	private long subTotal;
	private long total;

	
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(long closingStock) {
		this.closingStock = closingStock;
	}
	public int getStockNoOfDays() {
		return stockNoOfDays;
	}
	public void setStockNoOfDays(int stockNoOfDays) {
		this.stockNoOfDays = stockNoOfDays;
	}

	public int getNoOfRetailers() {
		return noOfRetailers;
	}
	public void setNoOfRetailers(int noOfRetailers) {
		this.noOfRetailers = noOfRetailers;
	}
	public String getBucketCode() {
		return bucketCode;
	}
	public void setBucketCode(String bucketCode) {
		this.bucketCode = bucketCode;
	}
	
	public int getNoGt90() {
		return noGt90;
	}
	public void setNoGt90(int noGt90) {
		this.noGt90 = noGt90;
	}
	public int getNoLtEq90() {
		return noLtEq90;
	}
	public void setNoLtEq90(int noLtEq90) {
		this.noLtEq90 = noLtEq90;
	}
	public String getFseName() {
		return fseName;
	}
	public void setFseName(String fseName) {
		this.fseName = fseName;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getLmtdSales() {
		return lmtdSales;
	}
	public void setLmtdSales(long lmtdSales) {
		this.lmtdSales = lmtdSales;
	}
	public long getMtdSales() {
		return mtdSales;
	}
	public void setMtdSales(long mtdSales) {
		this.mtdSales = mtdSales;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getClosingBal() {
		return closingBal;
	}
	public void setClosingBal(String closingBal) {
		this.closingBal = closingBal;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public int getNoColumns() {
		return noColumns;
	}
	public void setNoColumns(int noColumns) {
		this.noColumns = noColumns;
	}
	public String getOpeningBal() {
		return openingBal;
	}
	public void setOpeningBal(String openingBal) {
		this.openingBal = openingBal;
	}
	public String getProdCat() {
		return prodCat;
	}
	public void setProdCat(String prodCat) {
		this.prodCat = prodCat;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getReceived() {
		return received;
	}
	public void setReceived(String received) {
		this.received = received;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetName() {
		return retName;
	}
	public void setRetName(String retName) {
		this.retName = retName;
	}
	public String getReturns() {
		return returns;
	}
	public void setReturns(String returns) {
		this.returns = returns;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getDistributormsisdn() {
		return distributormsisdn;
	}
	public void setDistributormsisdn(String distributormsisdn) {
		this.distributormsisdn = distributormsisdn;
	}
	public String getRetmsisdn() {
		return retmsisdn;
	}
	public void setRetmsisdn(String retmsisdn) {
		this.retmsisdn = retmsisdn;
	}
	public long getLmtdPrimaryPurchase() {
		return lmtdPrimaryPurchase;
	}
	public void setLmtdPrimaryPurchase(long lmtdPrimaryPurchase) {
		this.lmtdPrimaryPurchase = lmtdPrimaryPurchase;
	}
	public long getMtdPrimaryPurchase() {
		return mtdPrimaryPurchase;
	}
	public void setMtdPrimaryPurchase(long mtdPrimaryPurchase) {
		this.mtdPrimaryPurchase = mtdPrimaryPurchase;
	}
	public String getBeatName() {
		return beatName;
	}
	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}
	public long getEasyRecharge() {
		return easyRecharge;
	}
	public void setEasyRecharge(long easyRecharge) {
		this.easyRecharge = easyRecharge;
	}
	public String getPaperRc() {
		return paperRc;
	}
	public void setPaperRc(String paperRc) {
		this.paperRc = paperRc;
	}
	public String getTmName() {
		return tmName;
	}
	public void setTmName(String tmName) {
		this.tmName = tmName;
	}
	public String getZsmName() {
		return zsmName;
	}
	public void setZsmName(String zsmName) {
		this.zsmName = zsmName;
	}
	public int getGrossActivations() {
		return grossActivations;
	}
	public void setGrossActivations(int grossActivations) {
		this.grossActivations = grossActivations;
	}
	public long getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(long subTotal) {
		this.subTotal = subTotal;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

	

}
