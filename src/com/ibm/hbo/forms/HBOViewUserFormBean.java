package com.ibm.hbo.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author
 */
public class HBOViewUserFormBean extends ActionForm {
	private String userId;

	private String userLoginId;

	private String userFname;

	private String userMname;

	private String userLname;

	private String userMobileNumber;

	private String userEmailid;

	private String circleId;

	private String actorId;

	private String loginActorId;

	private String status;

	private String edit;
	
	private String UpdatedBy;
	
	private String message;
	private String userAddress;
	private String userCity;
	private String userState;
	private String warehouseName;

	private ArrayList userList;
	private ArrayList arrStateList;

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset field values here.

	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

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
	public String getLoginActorId() {
		return loginActorId;
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
	public ArrayList getUserList() {
		return userList;
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
	public void setLoginActorId(String string) {
		loginActorId = string;
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
	 * @param list
	 */
	public void setUserList(ArrayList list) {
		userList = list;
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
	 * @return
	 */
	public String getEdit() {
		return edit;
	}

	/**
	 * @param string
	 */
	public void setEdit(String string) {
		edit = string;
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
	public String getUserMobileNumber() {
		return userMobileNumber;
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
	public void setUserMobileNumber(String string) {
		userMobileNumber = string;
	}

	/**
	 * @return
	 */
	public String getUpdatedBy() {
		return UpdatedBy;
	}

	/**
	 * @param string
	 */
	public void setUpdatedBy(String string) {
		UpdatedBy = string;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
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
	public String getWarehouseName() {
		return warehouseName;
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
	public ArrayList getArrStateList() {
		return arrStateList;
	}

	/**
	 * @param list
	 */
	public void setArrStateList(ArrayList list) {
		arrStateList = list;
	}

}
