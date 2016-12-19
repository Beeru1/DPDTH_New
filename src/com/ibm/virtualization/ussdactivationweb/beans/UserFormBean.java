package com.ibm.virtualization.ussdactivationweb.beans;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.*;


 /** 
	* @author Code Generator 
	* Created On Mon Jul 16 11:36:22 IST 2007 
	* Auto Generated Form Bean class for table V_USER_MSTR 
 
 **/ 
public class UserFormBean extends ActionForm {


    private String userId;

    private String loginId;

    private String status;

    private String createdBy;

    private java.sql.Timestamp createdDt;

    private java.sql.Timestamp updatedDt;

    private String emailId;

    private String updatedBy;

    private String circleId;

    private String password;

    private java.sql.Timestamp passwordChangedDt;

    private String userType;

    private String groupId;
    private String circleCode;

    /** Creates a dto for the V_USER_MSTR table */
    public UserFormBean() {
    }


    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
        return errors;
    }
 /** 
	* Get userId associated with this object.
	* @return userId
 **/ 

    public String getUserId() {
        return userId;
    }

 /** 
	* Set userId associated with this object.
	* @param userId The userId value to set
 **/ 

    public void setUserId(String userId) {
        this.userId = userId;
    }

 /** 
	* Get loginId associated with this object.
	* @return loginId
 **/ 

    public String getLoginId() {
        return loginId;
    }

 /** 
	* Set loginId associated with this object.
	* @param loginId The loginId value to set
 **/ 

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

 /** 
	* Get status associated with this object.
	* @return status
 **/ 

    public String getStatus() {
        return status;
    }

 /** 
	* Set status associated with this object.
	* @param status The status value to set
 **/ 

    public void setStatus(String status) {
        this.status = status;
    }

 /** 
	* Get createdBy associated with this object.
	* @return createdBy
 **/ 

    public String getCreatedBy() {
        return createdBy;
    }

 /** 
	* Set createdBy associated with this object.
	* @param createdBy The createdBy value to set
 **/ 

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

 /** 
	* Get createdDt associated with this object.
	* @return createdDt
 **/ 

    public java.sql.Timestamp getCreatedDt() {
        return createdDt;
    }

 /** 
	* Set createdDt associated with this object.
	* @param createdDt The createdDt value to set
 **/ 

    public void setCreatedDt(java.sql.Timestamp createdDt) {
        this.createdDt = createdDt;
    }

 /** 
	* Get updatedDt associated with this object.
	* @return updatedDt
 **/ 

    public java.sql.Timestamp getUpdatedDt() {
        return updatedDt;
    }

 /** 
	* Set updatedDt associated with this object.
	* @param updatedDt The updatedDt value to set
 **/ 

    public void setUpdatedDt(java.sql.Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

 /** 
	* Get emailId associated with this object.
	* @return emailId
 **/ 

    public String getEmailId() {
        return emailId;
    }

 /** 
	* Set emailId associated with this object.
	* @param emailId The emailId value to set
 **/ 

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

 /** 
	* Get updatedBy associated with this object.
	* @return updatedBy
 **/ 

    public String getUpdatedBy() {
        return updatedBy;
    }

 /** 
	* Set updatedBy associated with this object.
	* @param updatedBy The updatedBy value to set
 **/ 

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

 /** 
	* Get circleId associated with this object.
	* @return circleId
 **/ 

    public String getCircleId() {
        return circleId;
    }

 /** 
	* Set circleId associated with this object.
	* @param circleId The circleId value to set
 **/ 

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

 /** 
	* Get password associated with this object.
	* @return password
 **/ 

    public String getPassword() {
        return password;
    }

 /** 
	* Set password associated with this object.
	* @param password The password value to set
 **/ 

    public void setPassword(String password) {
        this.password = password;
    }

 /** 
	* Get passwordChangedDt associated with this object.
	* @return passwordChangedDt
 **/ 

    public java.sql.Timestamp getPasswordChangedDt() {
        return passwordChangedDt;
    }

 /** 
	* Set passwordChangedDt associated with this object.
	* @param passwordChangedDt The passwordChangedDt value to set
 **/ 

    public void setPasswordChangedDt(java.sql.Timestamp passwordChangedDt) {
        this.passwordChangedDt = passwordChangedDt;
    }

 /** 
	* Get userType associated with this object.
	* @return userType
 **/ 

    public String getUserType() {
        return userType;
    }

 /** 
	* Set userType associated with this object.
	* @param userType The userType value to set
 **/ 

    public void setUserType(String userType) {
        this.userType = userType;
    }

 /** 
	* Get groupId associated with this object.
	* @return groupId
 **/ 

    public String getGroupId() {
        return groupId;
    }

 /** 
	* Set groupId associated with this object.
	* @param groupId The groupId value to set
 **/ 

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


public String getCircleCode() {
	return circleCode;
}


public void setCircleCode(String circleCode) {
	this.circleCode = circleCode;
}

}
