package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.GenericOptionDTO;

public class AccountDetailReportBean extends ActionForm{
	
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	private String circleId;	
	private String tsmId;	
	private String distId;	
	private String fromDate;
	private String toDate;
	private String status;
	private String searchOption;
	private String searchCriteria;
	private String methodName;
	private String accountType;	
	private String loginId;
	private String accountName;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenPoStatusSelec;
	private String lapuNo; 
	private String mobileNo;
	private String reportName;
	private String accountTypeMultiSelect;
	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> tsmList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> distList =new ArrayList<SelectionCollection>();
	private List<AccountDetailReportDto> reportList =new ArrayList<AccountDetailReportDto>();
	private List<SelectionCollection> loginIdList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> accountNameList =new ArrayList<SelectionCollection>();
	private List<GenericOptionDTO> accountTypeList = new ArrayList<GenericOptionDTO>();
	private String accountTypeMultiSelectIds;
	private int accountLevel;
	

	
	public int getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(int accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getAccountTypeMultiSelectIds() {
		return accountTypeMultiSelectIds;
	}
	public void setAccountTypeMultiSelectIds(String accountTypeMultiSelectIds) {
		this.accountTypeMultiSelectIds = accountTypeMultiSelectIds;
	}
	public String getAccountTypeMultiSelect() {
		return accountTypeMultiSelect;
	}
	public void setAccountTypeMultiSelect(String accountTypeMultiSelect) {
		this.accountTypeMultiSelect = accountTypeMultiSelect;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public List<SelectionCollection> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<SelectionCollection> circleList) {
		this.circleList = circleList;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public List<SelectionCollection> getDistList() {
		return distList;
	}
	public void setDistList(List<SelectionCollection> distList) {
		this.distList = distList;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
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
	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public List<SelectionCollection> getTsmList() {
		return tsmList;
	}
	public void setTsmList(List<SelectionCollection> tsmList) {
		this.tsmList = tsmList;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenPoStatusSelec() {
		return hiddenPoStatusSelec;
	}
	public void setHiddenPoStatusSelec(String hiddenPoStatusSelec) {
		this.hiddenPoStatusSelec = hiddenPoStatusSelec;
	}
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}

	public List<AccountDetailReportDto> getReportList() {
		return reportList;
	}
	public void setReportList(List<AccountDetailReportDto> reportList) {
		this.reportList = reportList;
	}
	public List<SelectionCollection> getAccountNameList() {
		return accountNameList;
	}
	public void setAccountNameList(List<SelectionCollection> accountNameList) {
		this.accountNameList = accountNameList;
	}
	public List<SelectionCollection> getLoginIdList() {
		return loginIdList;
	}
	public void setLoginIdList(List<SelectionCollection> loginIdList) {
		this.loginIdList = loginIdList;
	}
	/**
	 * @return the accountTypeList
	 */
	public List<GenericOptionDTO> getAccountTypeList() {
		return accountTypeList;
	}
	/**
	 * @param accountTypeList the accountTypeList to set
	 */
	public void setAccountTypeList(List<GenericOptionDTO> accountTypeList) {
		this.accountTypeList = accountTypeList;
	}
	public String getLapuNo() {
		return lapuNo;
	}
	public void setLapuNo(String lapuNo) {
		this.lapuNo = lapuNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
}
