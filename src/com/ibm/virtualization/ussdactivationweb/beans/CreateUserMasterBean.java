/*
 * Created on Jul 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;

//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.struts.action.ActionError;
//import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionMapping;

/**
 * @author Amit
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateUserMasterBean extends ActionForm{
	

// 	change as per User
	private String userId=null;
	private String loginId=null;
	private String status=null;
	private String emailId=null;
	private String circleId=null;
	private String groupId=null;
	private String loginAttempted=null;
	private String userCreateDate=null;
	private String userCreatedBy=null;
	private String userUpdatedDate=null;
	private String userUpdatedBy=null;
	private String userType=null;
	private String password=null;
	private String passwordChangedDate=null;
	
	private String functionType=null;
	private String checkPassword=null;
	
	private String circleCode;
	
	

	/**
	 * @return Returns the checkPassword.
	 */
	public String getCheckPassword() {
		return checkPassword;
	}
	/**
	 * @param checkPassword The checkPassword to set.
	 */
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
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
	 * @return Returns the emailId.
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId The emailId to set.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * @return Returns the loginAttempted.
	 */
	public String getLoginAttempted() {
		return loginAttempted;
	}
	/**
	 * @param loginAttempted The loginAttempted to set.
	 */
	public void setLoginAttempted(String loginAttempted) {
		this.loginAttempted = loginAttempted;
	}
	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId The loginId to set.
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the passwordChangedDate.
	 */
	public String getPasswordChangedDate() {
		return passwordChangedDate;
	}
	/**
	 * @param passwordChangedDate The passwordChangedDate to set.
	 */
	public void setPasswordChangedDate(String passwordChangedDate) {
		this.passwordChangedDate = passwordChangedDate;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the userCreateDate.
	 */
	public String getUserCreateDate() {
		return userCreateDate;
	}
	/**
	 * @param userCreateDate The userCreateDate to set.
	 */
	public void setUserCreateDate(String userCreateDate) {
		this.userCreateDate = userCreateDate;
	}
	/**
	 * @return Returns the userCreatedBy.
	 */
	public String getUserCreatedBy() {
		return userCreatedBy;
	}
	/**
	 * @param userCreatedBy The userCreatedBy to set.
	 */
	public void setUserCreatedBy(String userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}
/**
 * @return Returns the userId.
 */
public String getUserId() {
	return userId;
}
/**
 * @param userId The userId to set.
 */
public void setUserId(String userId) {
	this.userId = userId;
}
	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return Returns the userUpdatedBy.
	 */
	public String getUserUpdatedBy() {
		return userUpdatedBy;
	}
	/**
	 * @param userUpdatedBy The userUpdatedBy to set.
	 */
	public void setUserUpdatedBy(String userUpdatedBy) {
		this.userUpdatedBy = userUpdatedBy;
	}
	/**
	 * @return Returns the userUpdatedDate.
	 */
	public String getUserUpdatedDate() {
		return userUpdatedDate;
	}
	/**
	 * @param userUpdatedDate The userUpdatedDate to set.
	 */
	public void setUserUpdatedDate(String userUpdatedDate) {
		this.userUpdatedDate = userUpdatedDate;
	}
	

	/**
	 * @return Returns the functionType.
	 */
	public String getFunctionType() {
		return functionType;
	}
	/**
	 * @param functionType The functionType to set.
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	
	
//	public void reset(ActionMapping mapping, HttpServletRequest request){
//		/*this.circleId=0;
//		this.emailId="";
//		this.functionType="";
//		this.groupId=0;
//		this.loginAttempted=0;
//		this.loginId="";
//		this.password="";
//		this.status="";
//		this.userType="";*/
//	}
//	
	
//	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
//		ActionErrors errors=super.validate(mapping,request);
//		if(errors==null)errors=new ActionErrors();
//		
//		if(circleId.equals("0")){
//			errors.add("circleId",new ActionError("errors.circleId.required"));
//		}
//		if(groupId.equals("0")){
//			errors.add("groupId",new ActionError("errors.groupId.required"));
//		}
//		if((emailId==null)||(emailId.length()==0)){
//			errors.add("emailId",new ActionError("errors.emailId.required"));
//		}
//		if((loginId==null)||(loginId.length()==0)){
//			errors.add("loginId",new ActionError("errors.loginId.required"));
//		}
//		
//		if((status==null)||(status.length()==0)){
//			errors.add("status",new ActionError("errors.status.required"));
//		}
//		if((userType==null)||(userType.length()==0)){
//			errors.add("userType",new ActionError("errors.userType.required"));
//		}
//		
//		
//		
//		
//		return errors;
//	}
}