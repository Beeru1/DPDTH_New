/**
 * 
 */
package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.OpenStockDepleteDTO;

/**
 * @author Kanika
 *
 */
public class DPOpenStockDepleteReportBean extends ActionForm{
	
	  String distributorCode;
	  String loginName;
	  String productCode;
	  String quantity;
	  String createdDate;
	  String status;
	  String fromDate;
	  String toDate;
	  String errorDesc;
	  
	  String distributorName;
	  String distOracleLocatorCode;
	  String productName;
	  String oracleProductCode;
	  String openingStock;
	  String sale;
	  String closingStock;
	  String lastUpdateDate;

	  
	  
	  List<OpenStockDepleteDTO> openStockDepleteList;
	  
	  
	public List<OpenStockDepleteDTO> getOpenStockDepleteList() {
		return openStockDepleteList;
	}
	public void setOpenStockDepleteList(
			List<OpenStockDepleteDTO> openStockDepleteList) {
		this.openStockDepleteList = openStockDepleteList;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDistributorCode() {
		return distributorCode;
	}
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(String closingStock) {
		this.closingStock = closingStock;
	}
	public String getDistOracleLocatorCode() {
		return distOracleLocatorCode;
	}
	public void setDistOracleLocatorCode(String distOracleLocatorCode) {
		this.distOracleLocatorCode = distOracleLocatorCode;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(String openingStock) {
		this.openingStock = openingStock;
	}
	public String getOracleProductCode() {
		return oracleProductCode;
	}
	public void setOracleProductCode(String oracleProductCode) {
		this.oracleProductCode = oracleProductCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	  
	  
}
