package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;

public class PODetailReportBean extends ActionForm{
	
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	private String circleId;	
	private String tsmId;	
	private String distId;	
	private String fromDate;
	private String toDate;
	private String poStatus;
	private String dateOption;
	private String searchOption;
	private String searchCriteria;
	private String methodName;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenPoStatusSelec;
	
	
	
	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> tsmList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> distList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> poStatusList =new ArrayList<SelectionCollection>();
	private List<PODetailReportDto> reportList =new ArrayList<PODetailReportDto>();
	
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
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	public String getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
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
	public List<PODetailReportDto> getReportList() {
		return reportList;
	}
	public void setReportList(List<PODetailReportDto> reportList) {
		this.reportList = reportList;
	}
	public List<SelectionCollection> getPoStatusList() {
		return poStatusList;
	}
	public void setPoStatusList(List<SelectionCollection> poStatusList) {
		this.poStatusList = poStatusList;
	}
	
	

}
