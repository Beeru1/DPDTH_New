/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * @author Pragati
 *
 */
public class LocationDataBean extends ActionForm{
	
	public static final long serialVersionUID = 1L;
	
    private String circleName;
    private String circleCode;
    private String status;
    private String oldStatus;
    private ArrayList circleList;
    private String createdBy;
    private Timestamp createdDate;
	private String methodName;
	private String userRole;
	private String page1="";
	private String modifiedBy;
	private Timestamp modifiedDate;
	
    /*Location mstr details*/
    private String locationName;
    private String locaionType;
    private ArrayList locationList;
    private int locMstrId;
    private int rownum;
    
  
    private String locationCode;
    
    
    /*Location data details*/
    private String locationDataName;
    private ArrayList locationDataList;
    private int locDataId;
    private ArrayList cityList= null;
    private ArrayList zoneList= null;
    private String cityName;
    private String parentId;
    private String zoneName;
    private String locationClassId;
    private String locationClassName;
    private ArrayList showLocationList= null;
        
    private String locDataCode;
    private String flag;
    private String oldLocationDataName;
    private String circleCode1;
    private String cityName1;
    private String zoneName1;
    private int locMstrId1;
    private String zoneCode;
    private String userType;
   
   
    
    private int moveToLocation;
    private ArrayList moveLocList;
 
   

  
	public ArrayList getMoveLocList() {
		return moveLocList;
	}

	public void setMoveLocList(ArrayList moveLocList) {
		this.moveLocList = moveLocList;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - circleName: ").append(circleName)
			.append(", circleCode: ").append(circleCode)
			.append(", locationName: ").append(locationName)
			.append(", locationDataName: ").append(locationDataName)
			.append(", locaionType: ").append(locaionType)
			.append(", circleList: ").append(circleList)
			.append(", parentId: ").append(parentId)
			.append(", locationList: ").append(locationList)
			.append(", locationDataList: ").append(locationDataList)
			.append(", createdBy: ").append(createdBy)
			.append(", createdDate: ").append(createdDate)
			.append(", modifiedBy: ").append(modifiedBy)
			.append(", modifiedDate: ").append(modifiedDate)
			.append(", CityList: ").append(locationName)
			.append(", Location data Id: ").append(locDataId)
			.append(", ShowLocationList: ").append(showLocationList)
			.append(", oldLocationDataName: ").append(oldLocationDataName)
			.append(", locDataCode: ").append(locDataCode)
			.toString());
			
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
	 * @return the circleList
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList the circleList to set
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	 * @return the locaionType
	 */
	public String getLocaionType() {
		return locaionType;
	}

	/**
	 * @param locaionType the locaionType to set
	 */
	public void setLocaionType(String locaionType) {
		this.locaionType = locaionType;
	}

	/**
	 * @return the locationList
	 */
	public ArrayList getLocationList() {
		return locationList;
	}

	/**
	 * @param locationList the locationList to set
	 */
	public void setLocationList(ArrayList locationList) {
		this.locationList = locationList;
	}

	/**
	 * @return the locationDataName
	 */
	public String getLocationDataName() {
		return locationDataName;
	}

	/**
	 * @param locationDataName the locationDataName to set
	 */
	public void setLocationDataName(String locationDataName) {
		this.locationDataName = locationDataName;
	}

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

	/**
	 * @return the locMstrId
	 */
	public int getLocMstrId() {
		return locMstrId;
	}

	/**
	 * @param locMstrId the locMstrId to set
	 */
	public void setLocMstrId(int locMstrId) {
		this.locMstrId = locMstrId;
	}

	
	/**
	 * @return the locDataId
	 */
	public int getLocDataId() {
		return locDataId;
	}

	/**
	 * @param locDataId the locDataId to set
	 */
	public void setLocDataId(int locDataId) {
		this.locDataId = locDataId;
	}

	/**
	 * @return the locationDataList
	 */
	public ArrayList getLocationDataList() {
		return locationDataList;
	}

	/**
	 * @param locationDataList the locationDataList to set
	 */
	public void setLocationDataList(ArrayList locationDataList) {
		this.locationDataList = locationDataList;
	}

	/**
	 * @return the cityList
	 */
	public ArrayList getCityList() {
		return cityList;
	}

	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(ArrayList cityList) {
		this.cityList = cityList;
	}

	/**
	 * @return the zoneList
	 */
	public ArrayList getZoneList() {
		return zoneList;
	}

	/**
	 * @param zoneList the zoneList to set
	 */
	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	
	/**
	 * @return the showLocationList
	 */
	public ArrayList getShowLocationList() {
		return showLocationList;
	}

	/**
	 * @param showLocationList the showLocationList to set
	 */
	public void setShowLocationList(ArrayList showLocationList) {
		this.showLocationList = showLocationList;
	}
	

	/**
	 * @return the locationClassId
	 */
	public String getLocationClassId() {
		return locationClassId;
	}

	/**
	 * @param locationClassId the locationClassId to set
	 */
	public void setLocationClassId(String locationClassId) {
		this.locationClassId = locationClassId;
	}

	/**
	 * @return the locationClassName
	 */
	public String getLocationClassName() {
		return locationClassName;
	}

	/**
	 * @param locationClassName the locationClassName to set
	 */
	public void setLocationClassName(String locationClassName) {
		this.locationClassName = locationClassName;
	}

	/**
	 * @return the rownum
	 */
	public int getRownum() {
		return rownum;
	}

	/**
	 * @param rownum the rownum to set
	 */
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	/**
	 * @return the locationCode
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return the locDataCode
	 */
	public String getLocDataCode() {
		return locDataCode;
	}

	/**
	 * @param locDataCode the locDataCode to set
	 */
	public void setLocDataCode(String locDataCode) {
		this.locDataCode = locDataCode;
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
	 * This method is used to reset the values.
	 * 
	 */
	public void reset(){
		circleCode=null;
		locationDataName=null;
		locMstrId= -1;
		
		zoneName= "-1";
		cityName=null;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the oldLocationDataName
	 */
	public String getOldLocationDataName() {
		return oldLocationDataName;
	}

	/**
	 * @param oldLocationDataName the oldLocationDataName to set
	 */
	public void setOldLocationDataName(String oldLocationDataName) {
		this.oldLocationDataName = oldLocationDataName;
	}

	/**
	 * @return the circleCode1
	 */
	public String getCircleCode1() {
		return circleCode1;
	}

	/**
	 * @param circleCode1 the circleCode1 to set
	 */
	public void setCircleCode1(String circleCode1) {
		this.circleCode1 = circleCode1;
	}

	/**
	 * @return the cityName1
	 */
	public String getCityName1() {
		return cityName1;
	}

	/**
	 * @param cityName1 the cityName1 to set
	 */
	public void setCityName1(String cityName1) {
		this.cityName1 = cityName1;
	}

	/**
	 * @return the locMstrId1
	 */
	public int getLocMstrId1() {
		return locMstrId1;
	}

	/**
	 * @param locMstrId1 the locMstrId1 to set
	 */
	public void setLocMstrId1(int locMstrId1) {
		this.locMstrId1 = locMstrId1;
	}

	/**
	 * @return the zoneName1
	 */
	public String getZoneName1() {
		return zoneName1;
	}

	/**
	 * @param zoneName1 the zoneName1 to set
	 */
	public void setZoneName1(String zoneName1) {
		this.zoneName1 = zoneName1;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public int getMoveToLocation() {
		return moveToLocation;
	}

	public void setMoveToLocation(int moveToLocation) {
		this.moveToLocation = moveToLocation;
	}

	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * @param zoneCode the zoneCode to set
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

}
