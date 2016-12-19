package com.ibm.dp.dto;

import java.io.Serializable;

public class WebServiceDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

// Authentication
	private String userName;
	private String password;
	
// City and Zone Web Service	
	private String cityId;
	private String cityName;
	private String cityCode;
// State Mapping	
	private String stateCode;
	private String stateName;
	
	private String zoneCode;
	private String zoneName;
	private String zoneId;
	private String circleId;
	private String circleCode;
	private String createdBy;
	private String createdDt;
	private String updatedDt;
	private String updatedBy;

// TSM and Distributor data	
	private String tsmId;
	private String tsmName;
	private String tsmCode;
	private String tsmLevel;
	private String tsmEmail;
	private String distId;
	private String distName;
	private String distCode;
	private String distAddress;
	private String distAlternateAddress;
	private String distContact;
	private String distLapuNo;
	private String distEmail;
	private String distAppointmentDt;
	private String distActivationDt;
	private String contactName;
	private String lstNo;
	private String serviceNo;
	private String cstNo;
	private String panNo;
	private String octroiCharge;
	
// Circle Web Service
	private String circleName;
	private String regionId;
	private String status;
	private float rate;
	private String description;
	private double openingBalance;
	private double operatingBalance;
	private double availableBalance;
	private double threshold;
	private String regionName;
	private String countryCode;
// Fields Added for 14_OCT_RFC
	private String circleTerms1;
	private String circleTerms2; 
	private String circleTerms3;
	private String circleTerms4;
	private String circleRemarks;
	private String circleCompanyName;
	private String circleAddress1;
	private String circleAddress2;
	private String circlePhoneNo;
	private String circleFaxNo;
	private String circleCSTNo;
	private String circleSaleTaxNo;
	private String circleServiceTaxNo;
	private String circleTinNo;
	
	
	
// Region Web Service

	private String regionCode;
	private String countryName;
	
// Create PO Webservice
	private String poNo;
	private String lapuNo;
	private String poType;
	private String remarks;
	private String productArray;
	private String quantity;
	
	
	
	public String getDistActivationDt() {
		return distActivationDt;
	}
	public void setDistActivationDt(String distActivationDt) {
		this.distActivationDt = distActivationDt;
	}
	public String getDistAppointmentDt() {
		return distAppointmentDt;
	}
	public void setDistAppointmentDt(String distAppointmentDt) {
		this.distAppointmentDt = distAppointmentDt;
	}
	public String getDistEmail() {
		return distEmail;
	}
	public void setDistEmail(String distEmail) {
		this.distEmail = distEmail;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getTsmCode() {
		return tsmCode;
	}
	public void setTsmCode(String tsmCode) {
		this.tsmCode = tsmCode;
	}
	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public String getTsmLevel() {
		return tsmLevel;
	}
	public void setTsmLevel(String tsmLevel) {
		this.tsmLevel = tsmLevel;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getDistAddress() {
		return distAddress;
	}
	public void setDistAddress(String distAddress) {
		this.distAddress = distAddress;
	}
	public String getDistAlternateAddress() {
		return distAlternateAddress;
	}
	public void setDistAlternateAddress(String distAlternateAddress) {
		this.distAlternateAddress = distAlternateAddress;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getDistContact() {
		return distContact;
	}
	public void setDistContact(String distContact) {
		this.distContact = distContact;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getDistLapuNo() {
		return distLapuNo;
	}
	public void setDistLapuNo(String distLapuNo) {
		this.distLapuNo = distLapuNo;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getTsmEmail() {
		return tsmEmail;
	}
	public void setTsmEmail(String tsmEmail) {
		this.tsmEmail = tsmEmail;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public double getOperatingBalance() {
		return operatingBalance;
	}
	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getLapuNo() {
		return lapuNo;
	}
	public void setLapuNo(String lapuNo) {
		this.lapuNo = lapuNo;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getPoType() {
		return poType;
	}
	public void setPoType(String poType) {
		this.poType = poType;
	}
	public String getProductArray() {
		return productArray;
	}
	public void setProductArray(String productArray) {
		this.productArray = productArray;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCstNo() {
		return cstNo;
	}
	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}
	public String getLstNo() {
		return lstNo;
	}
	public void setLstNo(String lstNo) {
		this.lstNo = lstNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getOctroiCharge() {
		return octroiCharge;
	}
	public void setOctroiCharge(String octroiCharge) {
		this.octroiCharge = octroiCharge;
	}
	public String getCircleRemarks() {
		return circleRemarks;
	}
	public void setCircleRemarks(String circleRemarks) {
		if(circleRemarks == null || circleRemarks.equalsIgnoreCase("null"))
			this.circleRemarks = "";
		else
			this.circleRemarks = circleRemarks;
	}
	public String getCircleTerms1() {
		return circleTerms1;
	}
	public void setCircleTerms1(String circleTerms1) {
		if(circleTerms1 == null || circleTerms1.equalsIgnoreCase("null"))
			this.circleTerms1 = "";
		else
			this.circleTerms1 = circleTerms1;
	}
	public String getCircleTerms2() {
		return circleTerms2;
	}
	public void setCircleTerms2(String circleTerms2) {
		if(circleTerms2 == null || circleTerms2.equalsIgnoreCase("null"))
			this.circleTerms2 = "";
		else
			this.circleTerms2 = circleTerms2;
	}
	public String getCircleTerms3() {
		return circleTerms3;
	}
	public void setCircleTerms3(String circleTerms3) {
		if(circleTerms3 == null || circleTerms3.equalsIgnoreCase("null"))
			this.circleTerms3 = "";
		else
			this.circleTerms3 = circleTerms3;
	}
	public String getCircleTerms4() {
		return circleTerms4;
	}
	public void setCircleTerms4(String circleTerms4) {
		if(circleTerms4 == null || circleTerms4.equalsIgnoreCase("null"))
			this.circleTerms4 = "";
		else
			this.circleTerms4 = circleTerms4;
	}
	public String getCircleAddress1() {
		return circleAddress1;
	}
	public void setCircleAddress1(String circleAddress1) {
		if(circleAddress1 == null || circleAddress1.equalsIgnoreCase("null"))
			this.circleAddress1 = "";
		else
			this.circleAddress1 = circleAddress1;
	}
	public String getCircleAddress2() {
		return circleAddress2;
	}
	public void setCircleAddress2(String circleAddress2) {
		if(circleAddress2 == null || circleAddress2.equalsIgnoreCase("null"))
			this.circleAddress2 = "";
		else
			this.circleAddress2 = circleAddress2;
	}
	public String getCircleCompanyName() {
		return circleCompanyName;
	}
	public void setCircleCompanyName(String circleCompanyName) {
		if(circleCompanyName == null || circleCompanyName.equalsIgnoreCase("null"))
			this.circleCompanyName = "";
		else
			this.circleCompanyName = circleCompanyName;
	}
	public String getCircleCSTNo() {
		return circleCSTNo;
	}
	public void setCircleCSTNo(String circleCSTNo) {
		if(circleCSTNo == null || circleCSTNo.equalsIgnoreCase("null"))
			this.circleCSTNo = "";
		else
			this.circleCSTNo = circleCSTNo;
	}
	public String getCircleFaxNo() {
		return circleFaxNo;
	}
	public void setCircleFaxNo(String circleFaxNo) {
		if(circleFaxNo == null || circleFaxNo.equalsIgnoreCase("null"))
			this.circleFaxNo = "";
		else
			this.circleFaxNo = circleFaxNo;
	}
	public String getCirclePhoneNo() {
		return circlePhoneNo;
	}
	public void setCirclePhoneNo(String circlePhoneNo) {
		if(circlePhoneNo == null || circlePhoneNo.equalsIgnoreCase("null"))
			this.circlePhoneNo = "";
		else
			this.circlePhoneNo = circlePhoneNo;
	}
	public String getCircleSaleTaxNo() {
		return circleSaleTaxNo;
	}
	public void setCircleSaleTaxNo(String circleSaleTaxNo) {
		if(circleSaleTaxNo == null || circleSaleTaxNo.equalsIgnoreCase("null"))
			this.circleSaleTaxNo = "";
		else
			this.circleSaleTaxNo = circleSaleTaxNo;
	}
	public String getCircleServiceTaxNo() {
		return circleServiceTaxNo;
	}
	public void setCircleServiceTaxNo(String circleServiceTaxNo) {
		if(circleServiceTaxNo == null || circleServiceTaxNo.equalsIgnoreCase("null"))
			this.circleServiceTaxNo = "";
		else
			this.circleServiceTaxNo = circleServiceTaxNo;
	}
	public String getCircleTinNo() {
		return circleTinNo;
	}
	public void setCircleTinNo(String circleTinNo) {
		if(circleTinNo == null || circleTinNo.equalsIgnoreCase("null"))
			this.circleTinNo = "";
		else
			this.circleTinNo = circleTinNo;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
