package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class DPCreateBeatFormBean extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7148212150719246968L;
	private int beatId;
	private String beatName;
	private String description;
	private int createdBy;
	private String createdDt;
	private String methodName;
	private int accountCityId;
	private String accountCityName;
	private ArrayList cityList;
	private String endDate;
	private String startDate;
	private String beatStatus;
	private int rowNum;
	private String circleName;
	private int circleId;
	private String accountZoneName;
	private int accountZoneId;
	private String editStatus;
	private ArrayList beatDetails;
	private ArrayList circleList;
	private ArrayList zoneList;
	private String flag;
	private int page;
	private int updatedBy;
	private String updatedDt;
	
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ArrayList getCircleList() {
		return circleList;
	}
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}
	public ArrayList getZoneList() {
		return zoneList;
	}
	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
	}
	public ArrayList getBeatDetails() {
		return beatDetails;
	}
	public void setBeatDetails(ArrayList beatDetails) {
		this.beatDetails = beatDetails;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getEditStatus() {
		return editStatus;
	}
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getBeatStatus() {
		return beatStatus;
	}
	public void setBeatStatus(String beatStatus) {
		this.beatStatus = beatStatus;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public ArrayList getCityList() {
		return cityList;
	}
	public void setCityList(ArrayList cityList) {
		this.cityList = cityList;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getBeatId() {
		return beatId;
	}
	public void setBeatId(int beatId) {
		this.beatId = beatId;
	}
	public String getBeatName() {
		return beatName;
	}
	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void reset(){
		beatName="";
		description = "";
	}
	public int getAccountCityId() {
		return accountCityId;
	}
	public void setAccountCityId(int accountCityId) {
		this.accountCityId = accountCityId;
	}
	public String getAccountCityName() {
		return accountCityName;
	}
	public void setAccountCityName(String accountCityName) {
		this.accountCityName = accountCityName;
	}
	public int getAccountZoneId() {
		return accountZoneId;
	}
	public void setAccountZoneId(int accountZoneId) {
		this.accountZoneId = accountZoneId;
	}
	public String getAccountZoneName() {
		return accountZoneName;
	}
	public void setAccountZoneName(String accountZoneName) {
		this.accountZoneName = accountZoneName;
	}
}
