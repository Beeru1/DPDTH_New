package com.ibm.dp.dto;

import java.util.Date;

public class ExceptionReportDTO {
	
	  String recId;
	  String serialNumber;
	  String status;
	  String assignDate;
	  String dealerCode;
	  String distributionCode;
	  String aceCode;
	  String fromDate;
	  String toDate;
	  
	  
	/**
	 * @return the aceCode
	 */
	public String getAceCode() {
		return aceCode;
	}
	/**
	 * @param aceCode the aceCode to set
	 */
	public void setAceCode(String aceCode) {
		this.aceCode = aceCode;
	}
	
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
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
	/**
	 * @return the dealerCode
	 */
	public String getDealerCode() {
		return dealerCode;
	}
	/**
	 * @param dealerCode the dealerCode to set
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	/**
	 * @return the distributionCode
	 */
	public String getDistributionCode() {
		return distributionCode;
	}
	/**
	 * @param distributionCode the distributionCode to set
	 */
	public void setDistributionCode(String distributionCode) {
		this.distributionCode = distributionCode;
	}
	/**
	 * @return the recId
	 */
	public String getRecId() {
		return recId;
	}
	/**
	 * @param recId the recId to set
	 */
	public void setRecId(String recId) {
		this.recId = recId;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
