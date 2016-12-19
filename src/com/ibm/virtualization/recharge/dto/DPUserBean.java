/*
 * Created on Aug 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.recharge.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DPUserBean implements Serializable{
	private String userLoginId;

		private String userFname;

		private String userMname;

		private String userLname;

		private String userMobileNumber;

		private String userEmailid;

		private String userPassword;

		private String userAddress;
	
		private String userCity;
	
		private String userState;
	
		private String userActiveFlag;
	
		private String userStartDate;
	
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

		private String status;

		private String actorName;

		private String circleName;
		private String warehouseID;

		private ArrayList moduleList;
		private HashMap categoryList;
		private HashMap ModuleData;

		private String userID = "";
		private String arrCIList=null;
		
		private String circleid = "";

		public String getCircleid() {
			return circleid;
		}

		public void setCircleid(String circleid) {
			this.circleid = circleid;
		}

		public String getArrCIList() {
			return arrCIList;
		}

		public void setArrCIList(String arrCIList) {
			this.arrCIList = arrCIList;
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
		public String getActorName() {
			return actorName;
		}

		/**
		 * @return
		 */
		public HashMap getCategoryList() {
			return categoryList;
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
		public String getCircleName() {
			return circleName;
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
		public HashMap getModuleData() {
			return ModuleData;
		}

		/**
		 * @return
		 */
		public ArrayList getModuleList() {
			return moduleList;
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
		public String getUserActiveFlag() {
			return userActiveFlag;
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
		public String getUserID() {
			return userID;
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
		public String getUserStartDate() {
			return userStartDate;
		}

		/**
		 * @return
		 */
		public String getUserState() {
			return userState;
		}

		/**
		 * @return
		 */
		public String getWarehouseID() {
			return warehouseID;
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
		public void setActorName(String string) {
			actorName = string;
		}

		/**
		 * @param map
		 */
		public void setCategoryList(HashMap map) {
			categoryList = map;
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
		public void setCircleName(String string) {
			circleName = string;
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
		 * @param map
		 */
		public void setModuleData(HashMap map) {
			ModuleData = map;
		}

		/**
		 * @param list
		 */
		public void setModuleList(ArrayList list) {
			moduleList = list;
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
		public void setUserActiveFlag(String string) {
			userActiveFlag = string;
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
		public void setUserID(String string) {
			userID = string;
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
		public void setUserStartDate(String string) {
			userStartDate = string;
		}

		/**
		 * @param string
		 */
		public void setUserState(String string) {
			userState = string;
		}

		/**
		 * @param string
		 */
		public void setWarehouseID(String string) {
			warehouseID = string;
		}

		/**
		 * @param string
		 */
		public void setZonalId(String string) {
			zonalId = string;
		}

}
