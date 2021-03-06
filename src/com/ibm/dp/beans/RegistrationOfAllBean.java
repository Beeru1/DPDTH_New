package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class RegistrationOfAllBean extends ActionForm

{
	public static final long serialVersionUID = 1L;
	
	private String contactNo = null;
    private String businessUserName=null;
    private String address;
    private String circleName;
    private String circle = null;
    private String code=null;
    private String bussinessUserId="0";
    private String methodName = null;
    private String status;
    private String oldStatus;
    private String circleId;
    private String userRole;
    private List bussinessUserList;
    private List typeOfUserList;
    private int typeOfUserId;
    private List circleList = null;
    private String initialCircleValue=null;
    private int typeOfUser;
    private String fsoCheck;
   	private boolean fosCheck;
	private int createdBy;
	private int updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private String regNumber;
	private List regNumberList;
	private ArrayList userList;
	private String regNumberId;
	private String oldRegNumber;
	private String circleCode;
	private String aepfCheck;
	private String fosActRights;
	private boolean fosActRightsBool;
	private ArrayList hubList;
	private String hubCode;
	private String hubName;
	private String searchBasis=null;
	private String identifier;
	private String page1="";
	private int parentId;
	private String baseLoc;
	private String typeOfUserValue;
	private int cityId;
	private String cityValue;
	private List cityList;
	private int zoneId;
	private String zoneValue;
	private List zoneList;
	private  String mappingType;
	private String allServiceClass;
	private String includeServiceClass;
	private String includeServiceClass1;
	private String excludeServiceClass;
	private String excludeServiceClass1;
	private String label="";
	private int jspPageLoadControl ;
	
	private int typeOfUserId1;
	private String hubCode1;
	private String circleCode1;
	private int cityId1;
	private int zoneId1;
	
	private String hubCode2;
	private String circleCode2;
	private int cityId2;
	private int zoneId2;
	
	private int locId;
	
	private int moveToBizUser;
	private ArrayList moveBizUsers;
	
	private String iphoneActRightString;
	private boolean iphoneActRightBool;
	
	public ArrayList getMoveBizUsers() {
		return moveBizUsers;
	}


	public void setMoveBizUsers(ArrayList moveBizUsers) {
		this.moveBizUsers = moveBizUsers;
	}


	public int getMoveToBizUser() {
		return moveToBizUser;
	}


	public void setMoveToBizUser(int moveToBizUser) {
		this.moveToBizUser = moveToBizUser;
	}


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
	/**
	 * @return the mappingType
	 */
	public String getMappingType() {
		return mappingType;
	}
	/**
	 * @param mappingType the mappingType to set
	 */
	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
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
	public String getAepfCheck() {
		return aepfCheck;
	}
	public void setAepfCheck(String aepfCheck) {
		this.aepfCheck = aepfCheck;
	}
	public String getOldRegNumber() {
		return oldRegNumber;
	}
	public void setOldRegNumber(String oldRegNumber) {
		this.oldRegNumber = oldRegNumber;
	}
	public String getRegNumberId() {
		return regNumberId;
	}
	public void setRegNumberId(String regNumberId) {
		this.regNumberId = regNumberId;
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
	/**
	 * @return Returns the initialCircleValue.
	 */
	public String getInitialCircleValue() {
		return initialCircleValue;
	}
	/**
	 * @param initialCircleValue The initialCircleValue to set.
	 */
	public void setInitialCircleValue(String initialCircleValue) {
		this.initialCircleValue = initialCircleValue;
	}
 
	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
   
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        // Reset values are provided as samples only. Change as appropriate.

        contactNo = null;
        businessUserName = null;
        address = null;
        circle = null;
        code = null;
        bussinessUserId = null;
        methodName = null;
        status = null;

    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        // Validate the fields in your form, adding
        // adding each error to this.errors as found, e.g.

        // if ((field == null) || (field.length() == 0)) {
        //   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
        // }
        return errors;

    }
	/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId The circleId to set.
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	/**
	 * @return Returns the userRole.
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole The userRole to set.
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return Returns the circleList.
	 */
	public List getCircleList() {
		return circleList;
	}
	/**
	 * @param circleList The circleList to set.
	 */
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}

	public boolean isFosCheck() {
		return fosCheck;
	}
	public void setFosCheck(boolean fosCheck) {
		this.fosCheck = fosCheck;
	}
	public String getFsoCheck() {
		return fsoCheck;
	}
	public void setFsoCheck(String fsoCheck) {
		this.fsoCheck = fsoCheck;
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
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	/**
	 * @return the userList
	 */
	public ArrayList getUserList() {
		return userList;
	}
	/**
	 * @param userList the userList to set
	 */
	public void setUserList(ArrayList userList) {
		this.userList = userList;
	}
	public List getRegNumberList() {
		return regNumberList;
	}
	public void setRegNumberList(List regNumberList) {
		this.regNumberList = regNumberList;
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

	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
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

	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	/**
	 * @return the page1
	 */
	public String getPage1() {
		return page1;
	}
	/**
	 * @param page1 the page1 to set
	 */
	public void setPage1(String page1) {
		this.page1 = page1;
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
	/**
	 * @return the includeServiceClass1
	 */
	public String getIncludeServiceClass1() {
		return includeServiceClass1;
	}
	/**
	 * @param includeServiceClass1 the includeServiceClass1 to set
	 */
	public void setIncludeServiceClass1(String includeServiceClass1) {
		this.includeServiceClass1 = includeServiceClass1;
	}
	/**
	 * @return the excludeServiceClass1
	 */
	public String getExcludeServiceClass1() {
		return excludeServiceClass1;
	}
	/**
	 * @param excludeServiceClass1 the excludeServiceClass1 to set
	 */
	public void setExcludeServiceClass1(String excludeServiceClass1) {
		this.excludeServiceClass1 = excludeServiceClass1;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public int getJspPageLoadControl() {
		return jspPageLoadControl;
	}


	public void setJspPageLoadControl(int jspPageLoadControl) {
		this.jspPageLoadControl = jspPageLoadControl;
	}


	public int getTypeOfUserId1() {
		return typeOfUserId1;
	}


	public void setTypeOfUserId1(int typeOfUserId1) {
		this.typeOfUserId1 = typeOfUserId1;
	}


	public String getCircleCode1() {
		return circleCode1;
	}


	public void setCircleCode1(String circleCode1) {
		this.circleCode1 = circleCode1;
	}


	public int getCityId1() {
		return cityId1;
	}


	public void setCityId1(int cityId1) {
		this.cityId1 = cityId1;
	}


	public String getHubCode1() {
		return hubCode1;
	}


	public void setHubCode1(String hubCode1) {
		this.hubCode1 = hubCode1;
	}

	public int getZoneId1() {
		return zoneId1;
	}


	public void setZoneId1(int zoneId1) {
		this.zoneId1 = zoneId1;
	}


	public String getBusinessUserName() {
		return businessUserName;
	}


	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}


	/**
	 * @return the circleCode2
	 */
	public String getCircleCode2() {
		return circleCode2;
	}


	/**
	 * @param circleCode2 the circleCode2 to set
	 */
	public void setCircleCode2(String circleCode2) {
		this.circleCode2 = circleCode2;
	}


	/**
	 * @return the cityId2
	 */
	public int getCityId2() {
		return cityId2;
	}


	/**
	 * @param cityId2 the cityId2 to set
	 */
	public void setCityId2(int cityId2) {
		this.cityId2 = cityId2;
	}


	/**
	 * @return the hubCode2
	 */
	public String getHubCode2() {
		return hubCode2;
	}


	/**
	 * @param hubCode2 the hubCode2 to set
	 */
	public void setHubCode2(String hubCode2) {
		this.hubCode2 = hubCode2;
	}

	public int getLocId() {
		return locId;
	}


	public void setLocId(int locId) {
		this.locId = locId;
	}


	/**
	 * @return the zoneId2
	 */
	public int getZoneId2() {
		return zoneId2;
	}


	/**
	 * @param zoneId2 the zoneId2 to set
	 */
	public void setZoneId2(int zoneId2) {
		this.zoneId2 = zoneId2;
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
