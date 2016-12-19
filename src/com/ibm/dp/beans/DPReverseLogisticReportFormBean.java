package com.ibm.dp.beans;



import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.DPSCMConsumptionReportDto;
import com.ibm.dp.dto.ProductMasterDto;

public class DPReverseLogisticReportFormBean extends ActionForm
{
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
	private String noShowTSM="";
	
	private String reporttype="";
	private String reportType="";
	
	List<DPReverseLogisticReportDTO> SCMexceptionReportList;
	List<DPReverseLogisticReportDTO> revLogReportList;
	List<DPReverseLogisticReportDTO> poReportList;
	private List tsmList = new ArrayList();
	private List revLogList = new ArrayList();
	public List<ProductMasterDto> productList = new ArrayList<ProductMasterDto>();
	public String selectedProductId = "";
	public String selectedProductName = "";
	// Add by harbans on MSI Report
	List<DPSCMConsumptionReportDto> dpscmReportList = new ArrayList<DPSCMConsumptionReportDto>();

	public List getRevLogList() {
		return revLogList;
	}
	public void setRevLogList(List revLogList) {
		this.revLogList = revLogList;
	}

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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
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
	public List<DPReverseLogisticReportDTO> getSCMexceptionReportList() {
		return SCMexceptionReportList;
	}
	public void setSCMexceptionReportList(
			List<DPReverseLogisticReportDTO> mexceptionReportList) {
		SCMexceptionReportList = mexceptionReportList;
	}
	
	
	
	public List<DPReverseLogisticReportDTO> getRevLogReportList() {
		return revLogReportList;
	}
	public void setRevLogReportList(
			List<DPReverseLogisticReportDTO> revLogReportList) {
		this.revLogReportList = revLogReportList;
	}
	public List getTsmList() {
		return tsmList;
	}
	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	

	public List<DPSCMConsumptionReportDto> getDpscmReportList() {
		return dpscmReportList;
	}
	public void setDpscmReportList(List<DPSCMConsumptionReportDto> dpscmReportList) {
		this.dpscmReportList = dpscmReportList;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getNoShowTSM() {
		return noShowTSM;
	}
	public void setNoShowTSM(String noShowTSM) {
		this.noShowTSM = noShowTSM;
	}
	public List<DPReverseLogisticReportDTO> getPoReportList() {
		return poReportList;
	}
	public void setPoReportList(List<DPReverseLogisticReportDTO> poReportList) {
		this.poReportList = poReportList;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
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
