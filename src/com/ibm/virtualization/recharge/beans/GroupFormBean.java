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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form Bean class for table VR_GROUP_DETAILS
 * 
 * @author Paras
 *  
 */
public class GroupFormBean extends ActionForm {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1922734195533893340L;

	private String groupId;
    
    private String groupName;
    
    private String description;
    
    private String createdBy;

    private String updatedBy;

    private String updatedDt;

    private String createdDt;
    
    private ArrayList displayDetails;
    
    private String methodName;
    
    private String type;
    
    private String editStatus; 
    
    
    /**
	 * @return the editStatus
	 */
	public String getEditStatus() {
		return editStatus;
	}
	/**
	 * @param editStatus the editStatus to set
	 */
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
     * @return Returns the createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * @return Returns the createdDt.
     */
    public String getCreatedDt() {
        return createdDt;
    }
    /**
     * @param createdDt The createdDt to set.
     */
    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the groupId.
     */
    public String getGroupId() {
        return groupId;
    }
    /**
     * @param groupId The groupId to set.
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    /**
     * @return Returns the groupName.
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName The groupName to set.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return Returns the updatedBy.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }
    /**
     * @param updatedBy The updatedBy to set.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    /**
     * @return Returns the updatedDt.
     */
    public String getUpdatedDt() {
        return updatedDt;
    }
    /**
     * @param updatedDt The updatedDt to set.
     */
    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }
    
    
    
    /**
     * @return Returns the displayDetails.
     */
    public ArrayList getDisplayDetails() {
        return displayDetails;
    }
    /**
     * @param displayDetails The displayDetails to set.
     */
    public void setDisplayDetails(ArrayList displayDetails) {
        this.displayDetails = displayDetails;
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
    	
        groupName=null;
        description=null;
        type=null;    	
    }
    
}
