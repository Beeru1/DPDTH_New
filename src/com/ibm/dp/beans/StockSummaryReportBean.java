package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.StockSummaryReportDto;

public class StockSummaryReportBean extends ActionForm{

	
	String accountName;
	String accountId;
	String distAccId;
	String distAccName;
	String role;
	String productName;
	String distID;
	String distName;
	List distList=new ArrayList();
	private ArrayList arrCGList=null;
	private int circleid = 0;
	private ArrayList arrCIList=null;
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	String fromDate;
	String toDate;
	List<StockSummaryReportDto> reportList;
	private List tsmList = new ArrayList();
	public List<ProductMasterDto> productList = new ArrayList<ProductMasterDto>();
	public String selectedProductId = "";
	public String selectedProductName = "";
	
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
	public String getDistID() {
		return distID;
	}
	public void setDistID(String distID) {
		this.distID = distID;
	}
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<StockSummaryReportDto> getReportList() {
		return reportList;
	}
	public void setReportList(List<StockSummaryReportDto> reportList) {
		this.reportList = reportList;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getShowDist() {
		return showDist;
	}
	public void setShowDist(String showDist) {
		this.showDist = showDist;
	}
	public String getShowTSM() {
		return showTSM;
	}
	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}
	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List getTsmList() {
		return tsmList;
	}
	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
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

}
