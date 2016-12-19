package com.ibm.dp.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form Bean object for State Warehouse Locator Master
 * 
 * @author Saatae Issa
 *  
 */
public class SWLocatorFormBean extends ActionForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7384006324066921447L;

	/**
	 * swId
	 */
	private int swId;

	/**
	 * swCompanyCode
	 */
	private String swCompanyCode;

	/**
	 * swAreaCode
	 */
	private String swAreaCode;

	/**
	 * swSubareaCode
	 */
	private String swSubareaCode;

	/**
	 * swSourceType
	 */
	private String swSourceType;

	/**
	 * swCircle
	 */
	private String swCircle;

	/**
	 * userId
	 */
	private String userId;

	/**
	 * locatorList
	 */
	ArrayList locatorList;
	
	/**
	 * circleList
	 */
	ArrayList circleList;

	/**
	 * Reset field values
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		this.swId = 0;
		this.swCompanyCode = null;
		this.swAreaCode = null;
		this.swSubareaCode = null;
		this.swSourceType = null;
		this.swCircle = null;
		this.userId = null;
		this.locatorList = null;
		this.circleList = null;

	}

	/**
	 * Validate the form bean
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		// errors.add("field", new
		// org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

	}

	/**
	 * Getter for property "locatorList"
	 */
	public ArrayList getLocatorList() {
		return locatorList;
	}

	/**
	 * Setter for property <code>locatorList</code>.
	 * @param locatorList
	 */
	public void setLocatorList(ArrayList locatorList) {
		this.locatorList = locatorList;
	}

	/**
	 * Getter for property "swAreaCode"
	 */
	public String getSwAreaCode() {
		return swAreaCode;
	}

	/**
	 * Setter for property <code>swAreaCode</code>.
	 * @param swAreaCode
	 */
	public void setSwAreaCode(String swAreaCode) {
		this.swAreaCode = swAreaCode;
	}

	/**
	 * Getter for property "swCircle"
	 */
	public String getSwCircle() {
		return swCircle;
	}

	/**
	 * Setter for property <code>swCircle</code>.
	 * @param swCircle
	 */
	public void setSwCircle(String swCircle) {
		this.swCircle = swCircle;
	}

	/**
	 * Getter for property "swCompanyCode"
	 */
	public String getSwCompanyCode() {
		return swCompanyCode;
	}

	/**
	 * Setter for property <code>swCompanyCode</code>.
	 * @param swCompanyCode
	 */
	public void setSwCompanyCode(String swCompanyCode) {
		this.swCompanyCode = swCompanyCode;
	}

	/**
	 * Getter for property "swId"
	 */
	public int getSwId() {
		return swId;
	}

	/**
	 * Setter for property <code>swId</code>.
	 * @param swId
	 */
	public void setSwId(int swId) {
		this.swId = swId;
	}

	/**
	 * Getter for property "swSourceType"
	 */
	public String getSwSourceType() {
		return swSourceType;
	}

	/**
	 * Setter for property <code>swSourceType</code>.
	 * @param swSourceType
	 */
	public void setSwSourceType(String swSourceType) {
		this.swSourceType = swSourceType;
	}

	/**
	 * Getter for property "swSubareaCode"
	 */
	public String getSwSubareaCode() {
		return swSubareaCode;
	}

	/**
	 * Setter for property <code>swSubareaCode</code>.
	 * @param swSubareaCode
	 */
	public void setSwSubareaCode(String swSubareaCode) {
		this.swSubareaCode = swSubareaCode;
	}

	/**
	 * Getter for property "userId"
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setter for property <code>userId</code>.
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Getter for property "circleList"
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * Setter for property <code>circleList</code>.
	 * @param circleList
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}

}