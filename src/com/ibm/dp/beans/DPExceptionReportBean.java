package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.SCMConsumptionReportDTO;

public class DPExceptionReportBean extends ActionForm 
{
	
	  String recId;
	  String serialNumber;
	  String status;
	  String assignDate;
	  String dealerCode;
	  String distributionCode;
	  String aceCode;
	  String fromDate;
	  String toDate;
	  List<ExceptionReportDTO> exceptionReportList;
	  List<SCMConsumptionReportDTO> SCMexceptionReportList;
	  
	  int circleid;
	  private ArrayList arrCIList=null;
	  private String showCircle="";
	  private ArrayList arrCGList=null;
	  
	  
	  
	  
	  
	  
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


	public List<ExceptionReportDTO> getExceptionReportList() {
		return exceptionReportList;
	}


	public void setExceptionReportList(List<ExceptionReportDTO> exceptionReportList) {
		this.exceptionReportList = exceptionReportList;
	}


	public List<SCMConsumptionReportDTO> getSCMexceptionReportList() {
		return SCMexceptionReportList;
	}


	public void setSCMexceptionReportList(
			List<SCMConsumptionReportDTO> mexceptionReportList) {
		SCMexceptionReportList = mexceptionReportList;
	}


	public ArrayList getArrCGList() {
		return arrCGList;
	}


	public void setArrCGList(ArrayList arrCGList) {
		this.arrCGList = arrCGList;
	}


	public ArrayList getArrCIList() {
		return arrCIList;
	}


	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
	}


	public int getCircleid() {
		return circleid;
	}


	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}


	public String getShowCircle() {
		return showCircle;
	}


	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	
}
