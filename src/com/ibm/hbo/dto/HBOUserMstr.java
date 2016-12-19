package com.ibm.hbo.dto;

import java.io.Serializable;

/** 
	* @author Avadesh 
	* Created On Tue Sep 25 17:26:13 IST 2007 
	* Data Trasnfer Object class for table ARC_USER_MSTR 

**/
public class HBOUserMstr implements Serializable {

	private String userId;

   private String userLoginId;

   private String userFname;

   private String userMname;

   private String userLname;

   private String userMobileNumber;

   private String userEmailid;

   private String userPassword;

   private String userPsswrdExpryDt;

   private String createdDt;

   private String createdBy;

   private String updatedDt;

   private String updatedBy;

   private String connectId;

   private String zonalId;

   private String circleId;

   private String actorId;

   private String loginAttempted;

   private String lastLoginTime;

   private String lastLoginIp;
  
   private String userAddress;
   private String userCity;
   private String userState;
   private String finalStatus;
   private String userStartDate="";// chnages on 18th march 2008 for Enter User Start Date

   private String status;
   
   private String loginActorId;
   
   private String UserActorName;
   
   private String newPassword;
   
   private String oldPassword;
	
	private String warehouseId="";
	private String warehouseName ="";


	/** Creates a dto for the USER_MSTR table */
	public HBOUserMstr() {
	}

	

/**
 * @return
 */
public String getActorId() {
	return actorId;
}

/**
 * @return
 */
public String getCircleId() {
	return circleId;
}

/**
 * @return
 */
public String getConnectId() {
	return connectId;
}

/**
 * @return
 */
public String getCreatedBy() {
	return createdBy;
}

/**
 * @return
 */
public String getCreatedDt() {
	return createdDt;
}

/**
 * @return
 */
public String getLastLoginIp() {
	return lastLoginIp;
}

/**
 * @return
 */
public String getLastLoginTime() {
	return lastLoginTime;
}

/**
 * @return
 */
public String getLoginAttempted() {
	return loginAttempted;
}

/**
 * @return
 */
public String getStatus() {
	return status;
}

/**
 * @return
 */
public String getUpdatedBy() {
	return updatedBy;
}

/**
 * @return
 */
public String getUpdatedDt() {
	return updatedDt;
}

/**
 * @return
 */
public String getUserEmailid() {
	return userEmailid;
}

/**
 * @return
 */
public String getUserFname() {
	return userFname;
}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

/**
 * @return
 */
public String getUserLname() {
	return userLname;
}

/**
 * @return
 */
public String getUserLoginId() {
	return userLoginId;
}

/**
 * @return
 */
public String getUserMname() {
	return userMname;
}

/**
 * @return
 */
public String getUserMobileNumber() {
	return userMobileNumber;
}

/**
 * @return
 */
public String getUserPassword() {
	return userPassword;
}

/**
 * @return
 */
public String getUserPsswrdExpryDt() {
	return userPsswrdExpryDt;
}

/**
 * @return
 */
public String getZonalId() {
	return zonalId;
}

/**
 * @param string
 */
public void setActorId(String string) {
	actorId = string;
}

/**
 * @param string
 */
public void setCircleId(String string) {
	circleId = string;
}

/**
 * @param string
 */
public void setConnectId(String string) {
	connectId = string;
}

/**
 * @param string
 */
public void setCreatedBy(String string) {
	createdBy = string;
}

/**
 * @param string
 */
public void setCreatedDt(String string) {
	createdDt = string;
}

/**
 * @param string
 */
public void setLastLoginIp(String string) {
	lastLoginIp = string;
}

/**
 * @param string
 */
public void setLastLoginTime(String string) {
	lastLoginTime = string;
}

/**
 * @param string
 */
public void setLoginAttempted(String string) {
	loginAttempted = string;
}

/**
 * @param string
 */
public void setStatus(String string) {
	status = string;
}

/**
 * @param string
 */
public void setUpdatedBy(String string) {
	updatedBy = string;
}

/**
 * @param string
 */
public void setUpdatedDt(String string) {
	updatedDt = string;
}

/**
 * @param string
 */
public void setUserEmailid(String string) {
	userEmailid = string;
}

/**
 * @param string
 */
public void setUserFname(String string) {
	userFname = string;
}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
	}

/**
 * @param string
 */
public void setUserLname(String string) {
	userLname = string;
}

/**
 * @param string
 */
public void setUserLoginId(String string) {
	userLoginId = string;
}

/**
 * @param string
 */
public void setUserMname(String string) {
	userMname = string;
}

/**
 * @param string
 */
public void setUserMobileNumber(String string) {
	userMobileNumber = string;
}

/**
 * @param string
 */
public void setUserPassword(String string) {
	userPassword = string;
}

/**
 * @param string
 */
public void setUserPsswrdExpryDt(String string) {
	userPsswrdExpryDt = string;
}

/**
 * @param string
 */
public void setZonalId(String string) {
	zonalId = string;
}

/**
 * @return
 */
public String getLoginActorId() {
	return loginActorId;
}

/**
 * @param string
 */
public void setLoginActorId(String string) {
	loginActorId = string;
}

/**
 * @return
 */
public String getUserActorName() {
	return UserActorName;
}

/**
 * @param string
 */
public void setUserActorName(String string) {
	UserActorName = string;
}

/**
 * @return
 */
public String getNewPassword() {
	return newPassword;
}

/**
 * @return
 */
public String getOldPassword() {
	return oldPassword;
}

/**
 * @param string
 */
public void setNewPassword(String string) {
	newPassword = string;
}

/**
 * @param string
 */
public void setOldPassword(String string) {
	oldPassword = string;
}

	/**
	 * @return
	 */
	public String getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @return
	 */
	public String getWarehouseName() {
		return warehouseName;
	}

	/**
	 * @param string
	 */
	public void setWarehouseId(String string) {
		warehouseId = string;
	}

	/**
	 * @param string
	 */
	public void setWarehouseName(String string) {
		warehouseName = string;
	}

/**
 * @return
 */
public String getUserAddress() {
	return userAddress;
}

/**
 * @return
 */
public String getUserCity() {
	return userCity;
}

/**
 * @return
 */
public String getUserState() {
	return userState;
}

/**
 * @param string
 */
public void setUserAddress(String string) {
	userAddress = string;
}

/**
 * @param string
 */
public void setUserCity(String string) {
	userCity = string;
}

/**
 * @param string
 */
public void setUserState(String string) {
	userState = string;
}

/**
 * @return
 */
public String getFinalStatus() {
	return finalStatus;
}

/**
 * @param string
 */
public void setFinalStatus(String string) {
	finalStatus = string;
}

/**
 * @return
 */
public String getUserStartDate() {
	return userStartDate;
}

/**
 * @param string
 */
public void setUserStartDate(String string) {
	userStartDate = string;
}

}
