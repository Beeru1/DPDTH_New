package com.ibm.dp.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class DPMissingStockApprovalDTO implements Serializable 
{
	private String strSerialNo;
	private Integer intProductID;
	private String strProductName ;
	private String strDCNo;
	private String strInvoiceNo;
	private Integer intDistID;
	private String strDistName;
	private String strDistClaimDate;
	private String strSwapped;
	private String strNewDistID;
	private String strNewDistName;
	private String strNewDCNo;
	private String strNewInvoiceNo;
	private String strStatus;
	private String strAction;
	private String strActionValue;
	private String strActionLabel;
	private Timestamp timeStampCreated; 
	private List<DPMissingStockApprovalDTO> listAction;
	
	public String getStrSerialNo() {
		return strSerialNo;
	}
	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = strSerialNo;
	}
	public String getStrSwapped() {
		return strSwapped;
	}
	public void setStrSwapped(String strSwapped) {
		this.strSwapped = strSwapped;
	}
	public String getStrDCNo() {
		return strDCNo;
	}
	public void setStrDCNo(String strDCNo) {
		this.strDCNo = strDCNo;
	}
	public String getStrDistName() {
		return strDistName;
	}
	public void setStrDistName(String strDistName) {
		this.strDistName = strDistName;
	}
	public String getStrNewDistName() {
		return strNewDistName;
	}
	public void setStrNewDistName(String strNewDistName) {
		this.strNewDistName = strNewDistName;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public Integer getIntDistID() {
		return intDistID;
	}
	public void setIntDistID(Integer intDistID) {
		this.intDistID = intDistID;
	}
	public Integer getIntProductID() {
		return intProductID;
	}
	public String getStrNewDistID() {
		return strNewDistID;
	}
	public void setStrNewDistID(String strNewDistID) {
		this.strNewDistID = strNewDistID;
	}
	public void setIntProductID(Integer intProductID) {
		this.intProductID = intProductID;
	}
	public String getStrDistClaimDate() {
		return strDistClaimDate;
	}
	public void setStrDistClaimDate(String strDistClaimDate) {
		this.strDistClaimDate = strDistClaimDate;
	}
	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}
	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = strInvoiceNo;
	}
	public String getStrNewDCNo() {
		return strNewDCNo;
	}
	public void setStrNewDCNo(String strNewDCNo) {
		this.strNewDCNo = strNewDCNo;
	}
	public String getStrNewInvoiceNo() {
		return strNewInvoiceNo;
	}
	public void setStrNewInvoiceNo(String strNewInvoiceNo) {
		this.strNewInvoiceNo = strNewInvoiceNo;
	}
	public String getStrProductName() {
		return strProductName;
	}
	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}
	public List<DPMissingStockApprovalDTO> getListAction() {
		return listAction;
	}
	public void setListAction(List<DPMissingStockApprovalDTO> listAction) {
		this.listAction = listAction;
	}
	public String getStrAction() {
		return strAction;
	}
	public void setStrAction(String strAction) {
		this.strAction = strAction;
	}
	public String getStrActionLabel() {
		return strActionLabel;
	}
	public void setStrActionLabel(String strActionLabel) {
		this.strActionLabel = strActionLabel;
	}
	public String getStrActionValue() {
		return strActionValue;
	}
	public void setStrActionValue(String strActionValue) {
		this.strActionValue = strActionValue;
	}
	public Timestamp getTimeStampCreated() {
		return timeStampCreated;
	}
	public void setTimeStampCreated(Timestamp timeStampCreated) {
		this.timeStampCreated = timeStampCreated;
	}
	
}
