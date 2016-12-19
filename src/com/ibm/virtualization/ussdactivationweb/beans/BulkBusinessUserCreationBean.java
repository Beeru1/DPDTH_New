/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.beans;


import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import java.util.List;


/**
 * @author Nitesh
 *
 */
public class BulkBusinessUserCreationBean extends ActionForm{
	
	public static final long serialVersionUID = 1L;
	
	private FormFile uploadFile1; 
	private FormFile uploadFile2; 
	private FormFile uploadFile3; 
	private String newUsersRoleType;
	private ArrayList usersRoleTypeList;
	private String circle1;
	private String circle2;
	private String circle3;
	private String zone1;
	private String zone2;
	private String zone3;
	private String city1;
	private String city2;
	private String city3;
	private String mappingType;
	private String fileId;
	private String label;
	private String userRole;
	private String circleId;
	private String circleCode;
	private ArrayList circleList;
	private ArrayList zoneList1;
	private ArrayList cityList1;
	private ArrayList zoneList2;
	private ArrayList cityList2;
	private ArrayList zoneList3;
	private ArrayList cityList3;
	private ArrayList parentUserList1;
	private ArrayList parentUserList2;
	private ArrayList parentUserList3;
	private ArrayList grandParentUserList1;
	private ArrayList grandParentUserList2;
	private ArrayList grandParentUserList3;
	private String parent1;
	private String parent2;
	private String parent3;
	private String grandParent1;
	private String grandParent2;
	private String grandParent3;
	private String fileFormat;
	
	/**
	 * @return the grandParentUserList1
	 */
	public ArrayList getGrandParentUserList1() {
		return grandParentUserList1;
	}


	/**
	 * @param grandParentUserList1 the grandParentUserList1 to set
	 */
	public void setGrandParentUserList1(ArrayList grandParentUserList1) {
		this.grandParentUserList1 = grandParentUserList1;
	}


	/**
	 * @return the grandParentUserList2
	 */
	public ArrayList getGrandParentUserList2() {
		return grandParentUserList2;
	}


	/**
	 * @param grandParentUserList2 the grandParentUserList2 to set
	 */
	public void setGrandParentUserList2(ArrayList grandParentUserList2) {
		this.grandParentUserList2 = grandParentUserList2;
	}


	/**
	 * @return the grandParentUserList3
	 */
	public ArrayList getGrandParentUserList3() {
		return grandParentUserList3;
	}


	/**
	 * @param grandParentUserList3 the grandParentUserList3 to set
	 */
	public void setGrandParentUserList3(ArrayList grandParentUserList3) {
		this.grandParentUserList3 = grandParentUserList3;
	}


	public String toString()
	{
		return new StringBuffer(200).append("BulkBusinessUserCreationBean - ")
					.append(" File Format: "+this.fileFormat)
					.append(" Upload File 1: "+this.uploadFile1)
					.append(" Upload File 2: "+this.uploadFile2)
					.append(" Upload File 3: "+this.uploadFile3)
					.append(" Upload Circle 1: "+this.circle1)
					.append(" Upload Circle 2: "+this.circle2)
					.append(" Upload Circle 3: "+this.circle3)					
					.append(" Upload Zone 1: "+this.zone1)					
					.append(" Upload Zone 2: "+this.zone2)
					.append(" Upload Zone 3: "+this.zone3)
					.append(" Upload City 1: "+this.city1)
					.append(" Upload City 2: "+this.city2)
					.append(" Upload City 3: "+this.city3)
					.append(" Upload Parent 1: "+this.parent1)
					.append(" Upload Grand Parent 1: "+this.grandParent1)
					.append(" Upload Parent 2: "+this.parent2)
					.append(" Upload Grand Parent 2: "+this.grandParent2)
					.append(" Upload Parent 3: "+this.parent3)
					.append(" Upload Grand Parent 3: "+this.grandParent3)
					.append(" Upload File Id: "+this.fileId)
					.append( "Upload Label: "+this.label)
					.append(" Upload User Role: "+this.userRole)
					.append("\nNew Users Role List: "+this.usersRoleTypeList)
					.append("\nCircle List: "+this.circleList)
					.append("\nZone List 1: "+this.zoneList1+" City List 1: "+this.cityList1+" User List 1: "+this.parentUserList1)
					.append("\nGrand Parent List1: "+this.grandParentUserList1)
					.append("\nZone List 2: "+this.zoneList2+" City List 2: "+this.cityList2+" User List 2: "+this.parentUserList2)
					.append("\nGrand Parent List2: "+this.grandParentUserList2)
					.append("\nZone List 3: "+this.zoneList3+" City List 3: "+this.cityList3+" User List 3: "+this.parentUserList3)
					.append("\nGrand Parent List3: "+this.grandParentUserList3)
					.toString();
	}
	
	
	/**
	 * @return the circle1
	 */
	public String getCircle1() {
		return circle1;
	}
	/**
	 * @param circle1 the circle1 to set
	 */
	public void setCircle1(String circle1) {
		this.circle1 = circle1;
	}
	/**
	 * @return the circle2
	 */
	public String getCircle2() {
		return circle2;
	}
	/**
	 * @param circle2 the circle2 to set
	 */
	public void setCircle2(String circle2) {
		this.circle2 = circle2;
	}
	/**
	 * @return the circle3
	 */
	public String getCircle3() {
		return circle3;
	}
	/**
	 * @param circle3 the circle3 to set
	 */
	public void setCircle3(String circle3) {
		this.circle3 = circle3;
	}
	/**
	 * @return the city1
	 */
	public String getCity1() {
		return city1;
	}
	/**
	 * @param city1 the city1 to set
	 */
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	/**
	 * @return the city2
	 */
	public String getCity2() {
		return city2;
	}
	/**
	 * @param city2 the city2 to set
	 */
	public void setCity2(String city2) {
		this.city2 = city2;
	}
	/**
	 * @return the city3
	 */
	public String getCity3() {
		return city3;
	}
	/**
	 * @param city3 the city3 to set
	 */
	public void setCity3(String city3) {
		this.city3 = city3;
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
	 * @return the newUsersRoleType
	 */
	public String getNewUsersRoleType() {
		return newUsersRoleType;
	}


	/**
	 * @param newUsersRoleType the newUsersRoleType to set
	 */
	public void setNewUsersRoleType(String newUsersRoleType) {
		this.newUsersRoleType = newUsersRoleType;
	}


	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}


	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * @return the usersRoleTypeList
	 */
	public ArrayList getUsersRoleTypeList() {
		return usersRoleTypeList;
	}


	/**
	 * @param usersRoleTypeList the usersRoleTypeList to set
	 */
	public void setUsersRoleTypeList(ArrayList usersRoleTypeList) {
		this.usersRoleTypeList = usersRoleTypeList;
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
	 * @return the uploadFile1
	 */
	public FormFile getUploadFile1() {
		return uploadFile1;
	}
	/**
	 * @param uploadFile1 the uploadFile1 to set
	 */
	public void setUploadFile1(FormFile uploadFile1) {
		this.uploadFile1 = uploadFile1;
	}
	/**
	 * @return the uploadFile2
	 */
	public FormFile getUploadFile2() {
		return uploadFile2;
	}
	/**
	 * @param uploadFile2 the uploadFile2 to set
	 */
	public void setUploadFile2(FormFile uploadFile2) {
		this.uploadFile2 = uploadFile2;
	}
	/**
	 * @return the uploadFile3
	 */
	public FormFile getUploadFile3() {
		return uploadFile3;
	}
	/**
	 * @param uploadFile3 the uploadFile3 to set
	 */
	public void setUploadFile3(FormFile uploadFile3) {
		this.uploadFile3 = uploadFile3;
	}
	
	/**
	 * @return the zone1
	 */
	public String getZone1() {
		return zone1;
	}
	/**
	 * @param zone1 the zone1 to set
	 */
	public void setZone1(String zone1) {
		this.zone1 = zone1;
	}
	/**
	 * @return the zone2
	 */
	public String getZone2() {
		return zone2;
	}
	/**
	 * @param zone2 the zone2 to set
	 */
	public void setZone2(String zone2) {
		this.zone2 = zone2;
	}
	/**
	 * @return the zone3
	 */
	public String getZone3() {
		return zone3;
	}
	/**
	 * @param zone3 the zone3 to set
	 */
	public void setZone3(String zone3) {
		this.zone3 = zone3;
	}


	/**
	 * @return the cityList1
	 */
	public ArrayList getCityList1() {
		return cityList1;
	}


	/**
	 * @param cityList1 the cityList1 to set
	 */
	public void setCityList1(ArrayList cityList1) {
		this.cityList1 = cityList1;
	}


	/**
	 * @return the cityList2
	 */
	public ArrayList getCityList2() {
		return cityList2;
	}


	/**
	 * @param cityList2 the cityList2 to set
	 */
	public void setCityList2(ArrayList cityList2) {
		this.cityList2 = cityList2;
	}


	/**
	 * @return the cityList3
	 */
	public ArrayList getCityList3() {
		return cityList3;
	}


	/**
	 * @param cityList3 the cityList3 to set
	 */
	public void setCityList3(ArrayList cityList3) {
		this.cityList3 = cityList3;
	}


	/**
	 * @return the zoneList1
	 */
	public ArrayList getZoneList1() {
		return zoneList1;
	}


	/**
	 * @param zoneList1 the zoneList1 to set
	 */
	public void setZoneList1(ArrayList zoneList1) {
		this.zoneList1 = zoneList1;
	}


	/**
	 * @return the zoneList2
	 */
	public ArrayList getZoneList2() {
		return zoneList2;
	}


	/**
	 * @param zoneList2 the zoneList2 to set
	 */
	public void setZoneList2(ArrayList zoneList2) {
		this.zoneList2 = zoneList2;
	}


	/**
	 * @return the zoneList3
	 */
	public ArrayList getZoneList3() {
		return zoneList3;
	}


	/**
	 * @param zoneList3 the zoneList3 to set
	 */
	public void setZoneList3(ArrayList zoneList3) {
		this.zoneList3 = zoneList3;
	}


	/**
	 * @return the parentUserList1
	 */
	public ArrayList getParentUserList1() {
		return parentUserList1;
	}


	/**
	 * @param parentUserList1 the parentUserList1 to set
	 */
	public void setParentUserList1(ArrayList parentUserList1) {
		this.parentUserList1 = parentUserList1;
	}


	/**
	 * @return the parentUserList2
	 */
	public ArrayList getParentUserList2() {
		return parentUserList2;
	}


	/**
	 * @param parentUserList2 the parentUserList2 to set
	 */
	public void setParentUserList2(ArrayList parentUserList2) {
		this.parentUserList2 = parentUserList2;
	}


	/**
	 * @return the parentUserList3
	 */
	public ArrayList getParentUserList3() {
		return parentUserList3;
	}


	/**
	 * @param parentUserList3 the parentUserList3 to set
	 */
	public void setParentUserList3(ArrayList parentUserList3) {
		this.parentUserList3 = parentUserList3;
	}


	/**
	 * @return the grandParent1
	 */
	public String getGrandParent1() {
		return grandParent1;
	}


	/**
	 * @param grandParent1 the grandParent1 to set
	 */
	public void setGrandParent1(String grandParent1) {
		this.grandParent1 = grandParent1;
	}


	/**
	 * @return the grandParent2
	 */
	public String getGrandParent2() {
		return grandParent2;
	}


	/**
	 * @param grandParent2 the grandParent2 to set
	 */
	public void setGrandParent2(String grandParent2) {
		this.grandParent2 = grandParent2;
	}


	/**
	 * @return the grandParent3
	 */
	public String getGrandParent3() {
		return grandParent3;
	}


	/**
	 * @param grandParent3 the grandParent3 to set
	 */
	public void setGrandParent3(String grandParent3) {
		this.grandParent3 = grandParent3;
	}


	/**
	 * @return the parent1
	 */
	public String getParent1() {
		return parent1;
	}


	/**
	 * @param parent1 the parent1 to set
	 */
	public void setParent1(String parent1) {
		this.parent1 = parent1;
	}


	/**
	 * @return the parent2
	 */
	public String getParent2() {
		return parent2;
	}


	/**
	 * @param parent2 the parent2 to set
	 */
	public void setParent2(String parent2) {
		this.parent2 = parent2;
	}


	/**
	 * @return the parent3
	 */
	public String getParent3() {
		return parent3;
	}


	/**
	 * @param parent3 the parent3 to set
	 */
	public void setParent3(String parent3) {
		this.parent3 = parent3;
	}


	/**
	 * @return the fileFormat
	 */
	public String getFileFormat() {
		return fileFormat;
	}


	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	
	
	

}
