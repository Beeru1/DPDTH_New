/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author abhipsa
 *
 */
public class SingleUploadBean extends ActionForm{
	
	public static final long serialVersionUID = 1L;
	
	private List circleList;
	private String circleId;
	private int mappingType;
	private int businessUserId;
	private int businessUserId1;
	private List businessUserList;
	private String businessUserName;
	private String businessUserCode;
	private String userRole;
	private String searchBasis;
	private int mappedBusinessUserId;
	private List mappedBusinessUserList;
	private String mappedBusinessUserName;
	private String mappedBusinessUserCode;
	private String msisdn;
	private String rownum;
	private String[] selectedUser;
	private String[] useList;
	private String circleCode;
	private String page1="";
	private String methodName;
	private String bizUserHierarchy;
	
	
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

	public String getSearchBasis() {
		return searchBasis;
	}

	public void setSearchBasis(String searchBasis) {
		this.searchBasis = searchBasis;
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getMappingType() {
		return mappingType;
	}

	public void setMappingType(int mappingType) {
		this.mappingType = mappingType;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
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
	public int getBusinessUserId1() {
		return businessUserId1;
	}
	public void setBusinessUserId1(int businessUserId1) {
		this.businessUserId1 = businessUserId1;
	}
	/**
	 * @return the bizUserHierarchy
	 */
	public String getBizUserHierarchy() {
		return bizUserHierarchy;
	}
	/**
	 * @param bizUserHierarchy the bizUserHierarchy to set
	 */
	public void setBizUserHierarchy(String bizUserHierarchy) {
		this.bizUserHierarchy = bizUserHierarchy;
	}
	
	
	
	

}
