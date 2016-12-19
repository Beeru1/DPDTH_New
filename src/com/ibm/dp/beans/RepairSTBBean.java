package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RepairSTBDto;

public class RepairSTBBean  extends ActionForm{

	public List<RepairSTBDto>  reverseStockInventoryList  = new ArrayList<RepairSTBDto>();
	public List<ProductMasterDto> productList = new ArrayList<ProductMasterDto>();
	public String selectedProductId = "";
	public String selectedProductName = "";
	public String strSrNo="";
	
	private String[] hidStrCollectionId;
	private String[] hidStrDefectId;
	private String[] hidStrProductId;
	private String[] hidStrOldProductId;
	private String[] hidStrSerialNo;
	private String[] hidStrCollectionDate;
	private String[] hidStrNewRemarks;
	private String[] hidStrOldRemarks;
	private String strRemarks;
	private String message="";
	private String isValidUser="";
	public String[] getHidStrCollectionDate() {
		return hidStrCollectionDate;
	}
	public void setHidStrCollectionDate(String[] hidStrCollectionDate) {
		this.hidStrCollectionDate = hidStrCollectionDate;
	}
	public String[] getHidStrCollectionId() {
		return hidStrCollectionId;
	}
	public void setHidStrCollectionId(String[] hidStrCollectionId) {
		this.hidStrCollectionId = hidStrCollectionId;
	}
	public String[] getHidStrDefectId() {
		return hidStrDefectId;
	}
	public void setHidStrDefectId(String[] hidStrDefectId) {
		this.hidStrDefectId = hidStrDefectId;
	}
	public String[] getHidStrProductId() {
		return hidStrProductId;
	}
	public void setHidStrProductId(String[] hidStrProductId) {
		this.hidStrProductId = hidStrProductId;
	}
	public String[] getHidStrSerialNo() {
		return hidStrSerialNo;
	}
	public void setHidStrSerialNo(String[] hidStrSerialNo) {
		this.hidStrSerialNo = hidStrSerialNo;
	}
	public String getIsValidUser() {
		return isValidUser;
	}
	public void setIsValidUser(String isValidUser) {
		this.isValidUser = isValidUser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ProductMasterDto> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductMasterDto> productList) {
		this.productList = productList;
	}
	public String getSelectedProductId() {
		return selectedProductId;
	}
	public void setSelectedProductId(String selectedProductId) {
		this.selectedProductId = selectedProductId;
	}
	public String getSelectedProductName() {
		return selectedProductName;
	}
	public void setSelectedProductName(String selectedProductName) {
		this.selectedProductName = selectedProductName;
	}
	public String getStrRemarks() {
		return strRemarks;
	}
	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}
	public String getStrSrNo() {
		return strSrNo;
	}
	public void setStrSrNo(String strSrNo) {
		this.strSrNo = strSrNo;
	}
	public List<RepairSTBDto> getReverseStockInventoryList() {
		return reverseStockInventoryList;
	}
	public void setReverseStockInventoryList(
			List<RepairSTBDto> reverseStockInventoryList) {
		this.reverseStockInventoryList = reverseStockInventoryList;
	}
	public String[] getHidStrOldProductId() {
		return hidStrOldProductId;
	}
	public void setHidStrOldProductId(String[] hidStrOldProductId) {
		this.hidStrOldProductId = hidStrOldProductId;
	}
	public String[] getHidStrNewRemarks() {
		return hidStrNewRemarks;
	}
	public void setHidStrNewRemarks(String[] hidStrNewRemarks) {
		this.hidStrNewRemarks = hidStrNewRemarks;
	}
	public String[] getHidStrOldRemarks() {
		return hidStrOldRemarks;
	}
	public void setHidStrOldRemarks(String[] hidStrOldRemarks) {
		this.hidStrOldRemarks = hidStrOldRemarks;
	}
	
	
	
	
}
