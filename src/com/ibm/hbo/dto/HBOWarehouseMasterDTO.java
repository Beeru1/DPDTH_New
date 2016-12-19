package com.ibm.hbo.dto;

import java.io.Serializable;

/** 
	* @author Sachin 
	* Created On Tue Sep 25 17:26:13 IST 2007 
	* Data Trasnfer Object class for table ARC_USER_MSTR 

**/
public class HBOWarehouseMasterDTO implements Serializable {

	 int warehouseId=0;
	 String name=""; 
	 String address="";
	 String city="";
	 String state="";
	 String circle="";
	 String contact="";
	 String status="";
	 int role=0;
	String createddate="";
    String createdby="";
    String updateddate="";
    String updatedby="";
	String statename="";
	String circlename="";
	String rolename="";
	String optionText="";
	String optionValue="";
	
		
	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
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
	public String getCirclename() {
		return circlename;
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
	public String getRolename() {
		return rolename;
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
	
	/**
	 * @return
	 */
	public String getStatename() {
		return statename;
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
	 * @param string
	 */
	public void setCircle(String string) {
		circle = string;
	}

	/**
	 * @param string
	 */
	public void setCirclename(String string) {
		circlename = string;
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
	public void setRolename(String string) {
		rolename = string;
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
	

	/**
	 * @param string
	 */
	public void setStatename(String string) {
		statename = string;
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

}
