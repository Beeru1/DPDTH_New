package com.ibm.dp.dto;

public class DPCreateBeatDto {
	
	private int beatId;
	private String beatName;
	private String description;
	private int createdBy;
	private String createdDt;
	private int updatedBy;
	private String updatedDt;
	private int accountCityId;
	private String accountCityName;
	private String endDate;
	private String startDate;
	private String beatStatus;
	private int rowNum;
	private String circleName;
	private int circleId;
	private String accountZoneName;
	private int accountZoneId;
	private int totalRecords;
	private Long loginId;
	private String accountLevel;
	
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
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
	public String getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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
