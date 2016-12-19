/**
 * 
 */
package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author abhipsa
 *
 */
public class RegistrationOfAllDTO implements Serializable {
	
	protected String contactNo = null;
	protected String businessUserName = null;
    protected String address = null;
    protected String circle = null;
    protected String code = null;
    protected String bussinessUserId = null;
    protected String methodName = null;
    protected String status = null;
    protected String oldStatus;
    protected String circleId = null;
    protected String userRole = null;
    protected List bussinessUserList = null;
    protected List circleList = null;
    protected String initialCircleValue=null;
    protected int typeOfUser;
    protected String fsoCheck;
	protected boolean fosCheck;
	protected int createdBy;
	protected int updatedBy;
	protected String createdDt;
	protected String updatedDt;
	protected Date createdDate;
	protected Date updatedDate;
	protected String regNumber;
	protected List regNoList;
	protected String regNumberId;
	protected String oldRegNumber;
	protected String circleCode;
	protected  String mappingType;
	protected String aepfCheck;
	protected String fosActRights;
	protected boolean fosActRightsBool;
	protected String searchBasis;
	protected List typeOfUserList;
	protected int typeOfUserId;
	protected String identifier;
	protected ArrayList BusinessUserDTO;
	protected String  errorCode;
	protected String  errorMessage;
	protected int parentId;
	protected int dataId;
	protected String baseLoc;
	protected String typeOfUserValue;
	protected int cityId;
	protected String cityValue;
	protected List cityList;
	protected int zoneId;
	protected String zoneValue;
	protected List zoneList;
	protected ArrayList hubList;
	protected String hubCode;
	protected String hubName;
	protected String allServices;
	protected String includeServices;
	protected String excludeServices;
	protected String allServiceClass;
	protected String includeServiceClass;
	protected String excludeServiceClass;
	protected String circleName;
	protected int locId;
	
	protected int moveToBizuser;
	
	protected String iphoneActRightString;
	protected boolean iphoneActRightBool;
	
	public int getMoveToBizuser() {
		return moveToBizuser;
	}



	public void setMoveToBizuser(int moveToBizuser) {
		this.moveToBizuser = moveToBizuser;
	}



	public int getLocId() {
		return locId;
	}



	public void setLocId(int locId) {
		this.locId = locId;
	}



	/**
	 * It returns the String representation of the RegistrationOfAllDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - contactNo: ").append(contactNo)
			.append(", name: ").append(businessUserName)
			.append(", address: ").append(address)
			.append(", circleName: ").append(circleName)
			.append(", code: ").append(code)
			.append(", circleList: ").append(circleList)
			.append(", bussinessUserId: ").append(bussinessUserId)
			.append(", status: ").append(status)
			.append(", createdBy: ").append(createdBy)
			.append(", createdDate: ").append(createdDate)
			.append(", userRole: ").append(userRole)
			.append(", aepfCheck: ").append(aepfCheck)
			.append(", regNumber: ").append(regNumber)
			.append(", regNumberId: ").append(regNumberId)
			.append(", oldRegNumber: ").append(oldRegNumber)
			.append(", circleCode: ").append(circleCode)
			.append(", hubCode: ").append(hubCode)
			.append(", hubName: ").append(hubName)
			.append(", searchBasis: ").append(searchBasis)
			.append(", identifier: ").append(identifier)
			.append(", cityId: ").append(cityId)
			.append(", fosActRights: ").append(fosActRights).toString());
			
	}
	
	
	
	/**
	 * @return the allServiceClass
	 */
	public String getAllServiceClass() {
		return allServiceClass;
	}

	/**
	 * @param allServiceClass the allServiceClass to set
	 */
	public void setAllServiceClass(String allServiceClass) {
		this.allServiceClass = allServiceClass;
	}

	/**
	 * @return the excludeServiceClass
	 */
	public String getExcludeServiceClass() {
		return excludeServiceClass;
	}

	/**
	 * @param excludeServiceClass the excludeServiceClass to set
	 */
	public void setExcludeServiceClass(String excludeServiceClass) {
		this.excludeServiceClass = excludeServiceClass;
	}

	/**
	 * @return the includeServiceClass
	 */
	public String getIncludeServiceClass() {
		return includeServiceClass;
	}

	/**
	 * @param includeServiceClass the includeServiceClass to set
	 */
	public void setIncludeServiceClass(String includeServiceClass) {
		this.includeServiceClass = includeServiceClass;
	}

	public RegistrationOfAllDTO(){
		
	}
	
			
	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the cityList
	 */
	public List getCityList() {
		return cityList;
	}

	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(List cityList) {
		this.cityList = cityList;
	}

	/**
	 * @return the cityValue
	 */
	public String getCityValue() {
		return cityValue;
	}

	/**
	 * @param cityValue the cityValue to set
	 */
	public void setCityValue(String cityValue) {
		this.cityValue = cityValue;
	}
	/**
	 * @return the zoneId
	 */
	public int getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	/**
	 * @return the zoneList
	 */
	public List getZoneList() {
		return zoneList;
	}

	/**
	 * @param zoneList the zoneList to set
	 */
	public void setZoneList(List zoneList) {
		this.zoneList = zoneList;
	}

	/**
	 * @return the zoneValue
	 */
	public String getZoneValue() {
		return zoneValue;
	}

	/**
	 * @param zoneValue the zoneValue to set
	 */
	public void setZoneValue(String zoneValue) {
		this.zoneValue = zoneValue;
	}


	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSearchBasis() {
		return searchBasis;
	}

	public void setSearchBasis(String searchBasis) {
		this.searchBasis = searchBasis;
	}

	public boolean isFosActRightsBool() {
		return fosActRightsBool;
	}

	public void setFosActRightsBool(boolean fosActRightsBool) {
		this.fosActRightsBool = fosActRightsBool;
	}

	public String getFosActRights() {
		return fosActRights;
	}

	public void setFosActRights(String fosActRights) {
		this.fosActRights = fosActRights;
	}

	public String getOldRegNumber() {
		return oldRegNumber;
	}

	public void setOldRegNumber(String oldRegNumber) {
		this.oldRegNumber = oldRegNumber;
	}

	/**
	 * @return the fosCheck
	 */
	public boolean isFosCheck() {
		return fosCheck;
	}

	/**
	 * @param fosCheck the fosCheck to set
	 */
	public void setFosCheck(boolean fosCheck) {
		this.fosCheck = fosCheck;
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
	 * @return the circle
	 */
	public String getCircle() {
		return circle;
	}

	/**
	 * @param circle the circle to set
	 */
	public void setCircle(String circle) {
		this.circle = circle;
	}

	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
	 * @return the bussinessUserId
	 */
	public String getBussinessUserId() {
		return bussinessUserId;
	}

	/**
	 * @param bussinessUserId the bussinessUserId to set
	 */
	public void setBussinessUserId(String bussinessUserId) {
		this.bussinessUserId = bussinessUserId;
	}

	/**
	 * @return the bussinessUserList
	 */
	public List getBussinessUserList() {
		return bussinessUserList;
	}

	/**
	 * @param bussinessUserList the bussinessUserList to set
	 */
	public void setBussinessUserList(List bussinessUserList) {
		this.bussinessUserList = bussinessUserList;
	}

	/**
	 * @return the circleList
	 */
	public List getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList the circleList to set
	 */
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}

	/**
	 * @return the fsoCheck
	 */
	public String getFsoCheck() {
		return fsoCheck;
	}

	/**
	 * @param fsoCheck the fsoCheck to set
	 */
	public void setFsoCheck(String fsoCheck) {
		this.fsoCheck = fsoCheck;
	}

	/**
	 * @return the initialCircleValue
	 */
	public String getInitialCircleValue() {
		return initialCircleValue;
	}

	/**
	 * @param initialCircleValue the initialCircleValue to set
	 */
	public void setInitialCircleValue(String initialCircleValue) {
		this.initialCircleValue = initialCircleValue;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the typeOfUser
	 */
	public int getTypeOfUser() {
		return typeOfUser;
	}

	/**
	 * @param typeOfUser the typeOfUser to set
	 */
	public void setTypeOfUser(int typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	/**
	 * @return the regNoList
	 */
	public List getRegNoList() {
		return regNoList;
	}

	/**
	 * @param regNoList the regNoList to set
	 */
	public void setRegNoList(List regNoList) {
		this.regNoList = regNoList;
	}

	public String getRegNumberId() {
		return regNumberId;
	}

	public void setRegNumberId(String regNumberId) {
		this.regNumberId = regNumberId;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}


	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}


	public String getAepfCheck() {
		return aepfCheck;
	}

	public void setAepfCheck(String aepfCheck) {
		this.aepfCheck = aepfCheck;
	}


	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	 * @return the typeOfUserList
	 */
	public List getTypeOfUserList() {
		return typeOfUserList;
	}

	/**
	 * @param typeOfUserList the typeOfUserList to set
	 */
	public void setTypeOfUserList(List typeOfUserList) {
		this.typeOfUserList = typeOfUserList;
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
	 * @return the hubList
	 */
	public ArrayList getHubList() {
		return hubList;
	}

	/**
	 * @param hubList the hubList to set
	 */
	public void setHubList(ArrayList hubList) {
		this.hubList = hubList;
	}

	/**
	 * @return the hubName
	 */
	public String getHubName() {
		return hubName;
	}

	/**
	 * @param hubName the hubName to set
	 */
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	/**
	 * @return the allServices
	 */
	public String getAllServices() {
		return allServices;
	}

	/**
	 * @param allServices the allServices to set
	 */
	public void setAllServices(String allServices) {
		this.allServices = allServices;
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
	 * @return the businessUserDTO
	 */
	public ArrayList getBusinessUserDTO() {
		return BusinessUserDTO;
	}

	/**
	 * @param businessUserDTO the businessUserDTO to set
	 */
	public void setBusinessUserDTO(ArrayList businessUserDTO) {
		BusinessUserDTO = businessUserDTO;
	}


	/**
	 * @return the createdDt
	 */
	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * @return the mappingType
	 */
	public String getMappingType() {
		return mappingType;
	}



	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
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



	/**
	 * @param mappingType the mappingType to set
	 */
	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}



	public String getBusinessUserName() {
		return businessUserName;
	}



	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}



	public String getCircleName() {
		return circleName;
	}



	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}



	public boolean isIphoneActRightBool() {
		return iphoneActRightBool;
	}



	public void setIphoneActRightBool(boolean iphoneActRightBool) {
		this.iphoneActRightBool = iphoneActRightBool;
	}



	public String getIphoneActRightString() {
		return iphoneActRightString;
	}



	public void setIphoneActRightString(String iphoneActRightString) {
		this.iphoneActRightString = iphoneActRightString;
	}


    

}
