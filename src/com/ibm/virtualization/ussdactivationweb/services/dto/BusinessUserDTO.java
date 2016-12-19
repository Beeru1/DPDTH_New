/**************************************************************************
 * File     : BusinessUserDTO.java
 * Author   : Banita
 * Created  : Oct 6, 2008
 * Modified : Oct 6, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 6, 2008 	Banita	First Cut.
 * V0.2		Oct 6, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.services.dto;

import java.io.Serializable;
/*******************************************************************************
 * This Data Transfer object class provides attributes to store business 
 * user details, and defines setter getter methods for the attributes.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class BusinessUserDTO  implements Serializable {
	protected int userId;
	protected int dataId;
	protected String code = null;
	protected String name = null;
	protected int typeOfUserId;
	protected int parentId;
	protected String status = null;
	protected String contactNo = null;
	protected String address = null;
	protected String circleCode;
	protected String hubCode;
	protected boolean fsoCheck;
	protected boolean fosActRights;
	protected int locId;
	protected boolean allServices;
	protected String includeServices;
	protected String excludeServices;
	protected int createdBy;
	protected int updatedBy;
	protected String createdDt;
	protected String updatedDt;
	protected String typeOfUserValue;
	protected String baseLoc;
	protected String regNumber;
	protected String errorCode;
    protected String errorMessage;
    protected String smsMessage;  // required in SMS Report
    protected String iphoneActRights;
    protected String locationName;
	
   
	
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getIphoneActRights() {
		return iphoneActRights;
	}

	public void setIphoneActRights(String iphoneActRights) {
		this.iphoneActRights = iphoneActRights;
	}

	/**
	 * It returns the String representation of the RegistrationOfAllDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer (500)
			.append(" \nBusinessUserDTO - circleCode: ").append(circleCode)
			.append(",  userId: ").append(userId)
			.append(",  dataId: ").append(dataId)
			.append(",  status: ").append(status)
			.append(",  name: ").append(name)
			.append(",  address: ").append(address)
			.append(",  code: ").append(code)
			.append(",  baseLoc: ").append(baseLoc)
			.append(",  typeOfUserValue: ").append(typeOfUserValue)
			.append(",  hubCode: ").append(hubCode)
			.append(",  allServices: ").append(allServices)
			.append(",  includeServices: ").append(includeServices)
			.append(",  excludeServices: ").append(excludeServices)
			.append(",  contactNo: ").append(contactNo)
			.append(",  fosActRights: ").append(fosActRights)
			.append(",  regNumber: ").append(regNumber)
			.append(",  errorCode: ").append(errorCode)
			.append(",  smsMessage: ").append(errorMessage)
			.append(",  smsMessage: ").append(smsMessage)
			.toString();		
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the baseLoc
	 */
	public String getBaseLoc() {
		return baseLoc;
	}

	/**
	 * @param baseLoc the baseLoc to set
	 */
	public void setBaseLoc(String baseLoc) {
		this.baseLoc = baseLoc;
	}

	/**
	 * @return the circleCode
	 */
	public String getCircleCode() {
		return circleCode;
	}

	/**
	 * @param circleCode the circleCode to set
	 */
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the dataId
	 */
	public int getDataId() {
		return dataId;
	}

	/**
	 * @param dataId the dataId to set
	 */
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	

	/**
	 * @return the excludeServices
	 */
	public String getExcludeServices() {
		return excludeServices;
	}

	/**
	 * @param excludeServices the excludeServices to set
	 */
	public void setExcludeServices(String excludeServices) {
		this.excludeServices = excludeServices;
	}


	/**
	 * @return the hubCode
	 */
	public String getHubCode() {
		return hubCode;
	}

	/**
	 * @param hubCode the hubCode to set
	 */
	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}

	/**
	 * @return the includeServices
	 */
	public String getIncludeServices() {
		return includeServices;
	}

	/**
	 * @param includeServices the includeServices to set
	 */
	public void setIncludeServices(String includeServices) {
		this.includeServices = includeServices;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the regNumber
	 */
	public String getRegNumber() {
		return regNumber;
	}

	/**
	 * @param regNumber the regNumber to set
	 */
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
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

	/**
	 * @return the typeOfUserId
	 */
	public int getTypeOfUserId() {
		return typeOfUserId;
	}

	/**
	 * @param typeOfUserId the typeOfUserId to set
	 */
	public void setTypeOfUserId(int typeOfUserId) {
		this.typeOfUserId = typeOfUserId;
	}

	/**
	 * @return the typeOfUserValue
	 */
	public String getTypeOfUserValue() {
		return typeOfUserValue;
	}

	/**
	 * @param typeOfUserValue the typeOfUserValue to set
	 */
	public void setTypeOfUserValue(String typeOfUserValue) {
		this.typeOfUserValue = typeOfUserValue;
	}

	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public String getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	public java.lang.String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }

    public java.lang.String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }

	/**
	 * @return the locId
	 */
	public int getLocId() {
		return locId;
	}

	/**
	 * @param locId the locId to set
	 */
	public void setLocId(int locId) {
		this.locId = locId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @param fosActRights the fosActRights to set
	 */
	public void setFosActRights(boolean fosActRights) {
		this.fosActRights = fosActRights;
	}

	/**
	 * @param fsoCheck the fsoCheck to set
	 */
	public void setFsoCheck(boolean fsoCheck) {
		this.fsoCheck = fsoCheck;
	}

	/**
	 * @return the fosActRights
	 */
	public boolean isFosActRights() {
		return fosActRights;
	}

	/**
	 * @return the fsoCheck
	 */
	public boolean isFsoCheck() {
		return fsoCheck;
	}

	/**
	 * @param allServices the allServices to set
	 */
	public void setAllServices(boolean allServices) {
		this.allServices = allServices;
	}

	/**
	 * @return the allServices
	 */
	public boolean isAllServices() {
		return allServices;
	}

	 /**
	 * @return the smsMessage
	 */
	public String getSmsMessage() {
		return smsMessage;
	}

	/**
	 * @param smsMessage the smsMessage to set
	 */
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	public BusinessUserDTO(){
		
	}
	
}
