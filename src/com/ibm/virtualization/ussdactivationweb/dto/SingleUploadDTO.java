/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.dto;

import java.util.List;

/**
 * @author abhipsa
 *
 */
public class SingleUploadDTO {

	protected List circleList;
	protected String circleId;
	protected int mappingType;
	protected int businessUserId;
	protected List businessUserList;
	protected String businessUserName;
	protected String businessUserCode;
	protected int mappedBusinessUserId;
	protected List mappedBusinessUserList;
	protected String mappedBusinessUserName;
	protected String mappedBusinessUserCode;
	protected String userRole;
	protected String searchBasis;
	protected String msisdn;
	protected String rownum;
	protected String[] selectedUser;
	protected String[] useList;
	protected String circleCode;
	protected String methodName;
	protected int locId;
	protected String hubCode;
	
	
	public int getLocId() {
		return locId;
	}


	public void setLocId(int locId) {
		this.locId = locId;
	}


	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - circleId: ").append(circleId)
			.append(", mappingType: ").append(mappingType)
			.append(", businessUserId: ").append(businessUserId)
			.append(", businessUserList: ").append(businessUserList)
			.append(", businessUserName: ").append(businessUserName)
			.append(", circleList: ").append(circleList)
			.append(", businessUserCode: ").append(businessUserCode)
			.append(", userRole: ").append(userRole)
			.append(", searchBasis: ").append(searchBasis)
			.append(", mappedBusinessUserId: ").append(mappedBusinessUserId)
			.append(", userRole: ").append(userRole)
			.append(", mappedBusinessUserName: ").append(mappedBusinessUserName)
			.append(", mappedBusinessUserCode: ").append(mappedBusinessUserCode)
			.append(", rownum: ").append(rownum)
			.append(", circleCode: ").append(circleCode)
			.append(", msisdn: ").append(msisdn).toString());
			
	}
	
	
	public String[] getUseList() {
		return useList;
	}

	public void setUseList(String[] useList) {
		this.useList = useList;
	}

	public String[] getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String[] selectedUser) {
		this.selectedUser = selectedUser;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getBusinessUserCode() {
		return businessUserCode;
	}

	public void setBusinessUserCode(String businessUserCode) {
		this.businessUserCode = businessUserCode;
	}

	public int getBusinessUserId() {
		return businessUserId;
	}

	public void setBusinessUserId(int businessUserId) {
		this.businessUserId = businessUserId;
	}

	public List getBusinessUserList() {
		return businessUserList;
	}

	public void setBusinessUserList(List businessUserList) {
		this.businessUserList = businessUserList;
	}

	public String getBusinessUserName() {
		return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public List getCircleList() {
		return circleList;
	}

	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}


	public int getMappingType() {
		return mappingType;
	}

	public void setMappingType(int mappingType) {
		this.mappingType = mappingType;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSearchBasis() {
		return searchBasis;
	}

	public void setSearchBasis(String searchBasis) {
		this.searchBasis = searchBasis;
	}

	public String getMappedBusinessUserCode() {
		return mappedBusinessUserCode;
	}

	public void setMappedBusinessUserCode(String mappedBusinessUserCode) {
		this.mappedBusinessUserCode = mappedBusinessUserCode;
	}

	public int getMappedBusinessUserId() {
		return mappedBusinessUserId;
	}

	public void setMappedBusinessUserId(int mappedBusinessUserId) {
		this.mappedBusinessUserId = mappedBusinessUserId;
	}

	public List getMappedBusinessUserList() {
		return mappedBusinessUserList;
	}

	public void setMappedBusinessUserList(List mappedBusinessUserList) {
		this.mappedBusinessUserList = mappedBusinessUserList;
	}

	public String getMappedBusinessUserName() {
		return mappedBusinessUserName;
	}

	public void setMappedBusinessUserName(String mappedBusinessUserName) {
		this.mappedBusinessUserName = mappedBusinessUserName;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
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


	public String getHubCode() {
		return hubCode;
	}


	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}
}
