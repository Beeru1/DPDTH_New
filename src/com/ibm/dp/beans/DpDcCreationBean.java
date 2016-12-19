package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;

public class DpDcCreationBean extends ActionForm{
	public List<DpDcCreationDto>  dcCollectionList  = new ArrayList<DpDcCreationDto>();
	public List<DpDcReverseStockInventory> reverseStockInventoryList;
	public String collectionId = "";
	public String collectionName = "";
	public String strDcNo="";
	public String courierAgency;
	public String docketNumber;
	
	private String[] hidStrCollectionId;
	private String[] hidStrDefectId;
	private String[] hidStrProductId;
	private String[] hidStrSerialNo;
	private String[] hidStrCollectionDate;
	private String strRemarks;
	private String message="";
	private String hidstrDcNo="";
	
	private String[] hidStrCustId;
	private String[] hidStrReqId;
	private String[] hidStrInvChangeDate;
	
	private String isERR;
	public String[] getHidStrCustId() {
		return hidStrCustId;
	}
	public void setHidStrCustId(String[] hidStrCustId) {
		this.hidStrCustId = hidStrCustId;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public List<DpDcCreationDto> getDcCollectionList() {
		return dcCollectionList;
	}
	public void setDcCollectionList(List<DpDcCreationDto> dcCollectionList) {
		this.dcCollectionList = dcCollectionList;
	}
	public List<DpDcReverseStockInventory> getReverseStockInventoryList() {
		return reverseStockInventoryList;
	}
	public void setReverseStockInventoryList(
			List<DpDcReverseStockInventory> reverseStockInventoryList) {
		this.reverseStockInventoryList = reverseStockInventoryList;
	}
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
	public String getStrRemarks() {
		return strRemarks;
	}
	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}
	public String getStrDcNo() {
		return strDcNo;
	}
	public void setStrDcNo(String strDcNo) {
		this.strDcNo = strDcNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHidstrDcNo() {
		return hidstrDcNo;
	}
	public void setHidstrDcNo(String hidstrDcNo) {
		this.hidstrDcNo = hidstrDcNo;
	}
	public String getCourierAgency() {
		return courierAgency;
	}
	public void setCourierAgency(String courierAgency) {
		this.courierAgency = courierAgency;
	}
	public String getDocketNumber() {
		return docketNumber;
	}
	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}
	public String[] getHidStrReqId() {
		return hidStrReqId;
	}
	public void setHidStrReqId(String[] hidStrReqId) {
		this.hidStrReqId = hidStrReqId;
	}
	public String[] getHidStrInvChangeDate() {
		return hidStrInvChangeDate;
	}
	public void setHidStrInvChangeDate(String[] hidStrInvChangeDate) {
		this.hidStrInvChangeDate = hidStrInvChangeDate;
	}
	public String getIsERR() {
		return isERR;
	}
	public void setIsERR(String isERR) {
		this.isERR = isERR;
	}
	
	

}
