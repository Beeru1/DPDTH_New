/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.beans;



import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * Form Bean class for Group Role mapping
 * 
 * @author Navroze
 *  
 */

public class GroupRoleMappingFormBean extends ActionForm {


	private String groupId;
	
	private String groupName;
	
	private String roleId;
	
	private String roleName;
	
	private String methodName;
	
	private String[] rolesId;
	
	private String createdBy;

	private String updatedBy;

	private String updatedDt;

    private String createdDt;
    
    private String channelType;
    
    /* boolean variable to Display the Roles table on its value=true */
    private boolean showTable;
    
    /* To display Groups */
	private ArrayList groupList = null ;
	
	private ArrayList roleAssignedList = null ;
	
	private ArrayList roleNotAssignedList = null ;
	
 	
	/**
	 * @return the showTable
	 */
	public boolean isShowTable() {
		return showTable;
	}
	/**
	 * @param showTable the showTable to set
	 */
	public void setShowTable(boolean showTable) {
		this.showTable = showTable;
	}
	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String[] getRolesId() {
		return rolesId;
	}
	public void setRolesId(String[] rolesId) {
		this.rolesId = rolesId;
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
	/**
	 * @return the groupList
	 */
	public ArrayList getGroupList() {
		return groupList;
	}
	/**
	 * @param groupList the groupList to set
	 */
	public void setGroupList(ArrayList groupList) {
		this.groupList = groupList;
	}
	/**
	 * @return the roleAssignedList
	 */
	public ArrayList getRoleAssignedList() {
		return roleAssignedList;
	}
	/**
	 * @param roleAssignedList the roleAssignedList to set
	 */
	public void setRoleAssignedList(ArrayList roleAssignedList) {
		this.roleAssignedList = roleAssignedList;
	}
	/**
	 * @return the roleNotAssignedList
	 */
	public ArrayList getRoleNotAssignedList() {
		return roleNotAssignedList;
	}
	/**
	 * @param roleNotAssignedList the roleNotAssignedList to set
	 */
	public void setRoleNotAssignedList(ArrayList roleNotAssignedList) {
		this.roleNotAssignedList = roleNotAssignedList;
	}
	
	
	
	
		
}