package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;

public class ConsumptionPostingDetailReportBeans  extends ActionForm{
	String accountName;
	String accountId;
	private String DistributorId;
	private String fromDate = "";
	private String toDate = "";
	//private List<DistributorDTO> DistributorList= new ArrayList<DistributorDTO>();
	private String serialNo= "";
	private String assignedDate;
	private String itemCode;
	private String distributorLoginName;
	private String distributorName;
	private String fseDetail;
	private String retailerCode;
	private String customerId;
	private List<ConsumptionPostingDetailReportDto> reportDataList;
	private List tsmList = new ArrayList();
	private int circleid = -1;
	private ArrayList arrCIList=null;
	private String showCircle="";
	String distAccId;
	String distAccName;
	List distList=new ArrayList();
	
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
	public List getTsmList() {
		return tsmList;
	}
	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
	}
	public String getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
/*	public List<DistributorDTO> getDistributorList() {
		return DistributorList;
	}
	public void setDistributorList(List<DistributorDTO> distributorList) {
		DistributorList = distributorList;
	}*/
	public String getDistributorLoginName() {
		return distributorLoginName;
	}
	public void setDistributorLoginName(String distributorLoginName) {
		this.distributorLoginName = distributorLoginName;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFseDetail() {
		return fseDetail;
	}
	public void setFseDetail(String fseDetail) {
		this.fseDetail = fseDetail;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getRetailerCode() {
		return retailerCode;
	}
	public void setRetailerCode(String retailerCode) {
		this.retailerCode = retailerCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public List<ConsumptionPostingDetailReportDto> getReportDataList() {
		return reportDataList;
	}
	public void setReportDataList(
			List<ConsumptionPostingDetailReportDto> reportDataList) {
		this.reportDataList = reportDataList;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
	}
	public String getDistAccId() {
		return distAccId;
	}
	public void setDistAccId(String distAccId) {
		this.distAccId = distAccId;
	}
	public String getDistAccName() {
		return distAccName;
	}
	public void setDistAccName(String distAccName) {
		this.distAccName = distAccName;
	}
	
	
	
	
}
