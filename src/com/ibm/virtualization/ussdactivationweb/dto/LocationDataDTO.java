/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Banita
 *
 */
public class LocationDataDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected String circleName;
	    protected String circleCode;
	    protected String status;
	    protected ArrayList circleList;
	    protected String createdBy;
	    protected String createdDate;
		protected String methodName;
		protected String userRole;
		protected String page1="";
		protected String modifiedBy;
		protected Timestamp modifiedDate;
		protected int rownum;
		protected String oldStatus;
		
	    /*Location mstr details*/
	    protected String locationName;
	    protected String locaionType;
	    protected ArrayList locationList;
	    protected int locMstrId;
	    protected String locationCode;
	    
	    
	    /*Location data details*/
	    protected String locationDataName;
	    protected ArrayList locationDataList;
	    protected int locDataId;
	    protected String parentId;           
	    protected String locDataCode;
	    
	    
	    protected String hubCode; 
	    protected String hubName;
	    protected String cityCode; 
	    protected String cityName; 
	    protected String zoneCode; 
	    protected String zoneName; 
	    protected String territoryCode;
	    protected String territoryName;
	    protected String rowNumber;
	    protected String userType;
	    
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
		 * It returns the String representation of the ServiceClassDTO. 
		 * @return Returns a <code>String</code> having all the content information of this object.
		 */
		public String toString() {
			return new StringBuffer (500)
				.append(" \nLocationDataDTO - circleCode: ").append(circleCode)
				.append(",  circleName: ").append(circleName)
				.append(",  status: ").append(status)
				.append(", \n createdBy: ").append(createdBy)
				.append(",  createdDate: ").append(createdDate)
				.append(",  modifiedDate: ").append(modifiedDate)
				.append(",  modifiedBy: ").append(modifiedBy)
				.append(",  locationName: ").append(locationName)
				.append(",  locaionType: ").append(locaionType)
				.append(",  locationDataName: ").append(locationDataName)
				.append(",  parentId: ").append(parentId)
				.append(",  locDataCode: ").append(locDataCode)
				.append(",  hubCode: ").append(hubCode)
				.append(",  hubName: ").append(hubName)
				.append(",  cityCode: ").append(cityCode)
				.append(",  cityName: ").append(cityName)
				.append(",  zoneCode: ").append(zoneCode)
				.append(",  zoneName: ").append(zoneName)
				.append(",  locDataId: ").append(locDataId)
				.append(",  territoryCode: ").append(territoryCode)
				.append(",  territoryName: ").append(territoryName)
				.append(",  locationDataName: ").append(locationDataName)
				.append(",  locationDataList: ").append(locationDataList)
				.append(",  territoryCode: ").append(territoryCode)
				.append(",  locMstrId: ").append(locMstrId)
				.toString();		
		}
		
		/** Creates a dto for the V_CIRCLE_MSTR table */
		public LocationDataDTO() {
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
		public String getCreatedDate() {
			return createdDate;
		}
		/**
		 * @param createdDate the createdDate to set
		 */
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
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
		 * @return the cityCode
		 */
		public String getCityCode() {
			return cityCode;
		}
		/**
		 * @param cityCode the cityCode to set
		 */
		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
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
		/**
		 * @return the territoryCode
		 */
		public String getTerritoryCode() {
			return territoryCode;
		}
		/**
		 * @param territoryCode the territoryCode to set
		 */
		public void setTerritoryCode(String territoryCode) {
			this.territoryCode = territoryCode;
		}
		/**
		 * @return the territoryName
		 */
		public String getTerritoryName() {
			return territoryName;
		}
		/**
		 * @param territoryName the territoryName to set
		 */
		public void setTerritoryName(String territoryName) {
			this.territoryName = territoryName;
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

		public String getOldStatus() {
			return oldStatus;
		}

		public void setOldStatus(String oldStatus) {
			this.oldStatus = oldStatus;
		}

		/**
		 * @return the rowNumber
		 */
		public String getRowNumber() {
			return rowNumber;
		}

		/**
		 * @param rowNumber the rowNumber to set
		 */
		public void setRowNumber(String rowNumber) {
			this.rowNumber = rowNumber;
		}
		
}
