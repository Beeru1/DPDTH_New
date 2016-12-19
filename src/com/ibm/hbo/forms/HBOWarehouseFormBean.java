package com.ibm.hbo.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a ArcLoginForm.
 * @version 	1.0
 * @author Avadesh
 */
public class HBOWarehouseFormBean extends ActionForm {
	private int warehouseId=0;
	private int parentWarehouseId=0;
	
	private String name=""; 
	private String address="";
	private String city="";
	private String state="";
	private String circle="";
	private String contact="";
	private String status="";
	private int role=0;
	private String createddate="";
	private String createdby="";
	private String updateddate="";
	private String updatedby="";
	private ArrayList arrStateList=null;
	private ArrayList arrCircleList=null;
	private ArrayList arrRoleList=null;
	private ArrayList arrWarehouseList=null;
	
	
	
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
	
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public ArrayList getArrCircleList() {
		return arrCircleList;
	}

	/**
	 * @return
	 */
	public ArrayList getArrRoleList() {
		return arrRoleList;
	}

	/**
	 * @return
	 */
	public ArrayList getArrStateList() {
		return arrStateList;
	}

	/**
	 * @return
	 */
	public ArrayList getArrWarehouseList() {
		return arrWarehouseList;
	}

	/**
	 * @return
	 */
	public String getCircle() {
		return circle;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @return
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @return
	 */
	public String getCreateddate() {
		return createddate;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
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
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @return
	 */
	public String getUpdateddate() {
		return updateddate;
	}

	/**
	 * @return
	 */
	public int getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @param list
	 */
	public void setArrCircleList(ArrayList list) {
		arrCircleList = list;
	}

	/**
	 * @param list
	 */
	public void setArrRoleList(ArrayList list) {
		arrRoleList = list;
	}

	/**
	 * @param list
	 */
	public void setArrStateList(ArrayList list) {
		arrStateList = list;
	}

	/**
	 * @param list
	 */
	public void setArrWarehouseList(ArrayList list) {
		arrWarehouseList = list;
	}

	/**
	 * @param string
	 */
	public void setCircle(String string) {
		circle = string;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}

	/**
	 * @param string
	 */
	public void setContact(String string) {
		contact = string;
	}

	/**
	 * @param string
	 */
	public void setCreatedby(String string) {
		createdby = string;
	}

	/**
	 * @param string
	 */
	public void setCreateddate(String string) {
		createddate = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param i
	 */
	public void setRole(int i) {
		role = i;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
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
	public void setUpdatedby(String string) {
		updatedby = string;
	}

	/**
	 * @param string
	 */
	public void setUpdateddate(String string) {
		updateddate = string;
	}

	/**
	 * @param i
	 */
	public void setWarehouseId(int i) {
		warehouseId = i;
	}

	/**
	 * @return
	 */
	
	/**
	 * @return
	 */
	public int getParentWarehouseId() {
		return parentWarehouseId;
	}

	/**
	 * @param i
	 */
	public void setParentWarehouseId(int i) {
		parentWarehouseId = i;
	}

}