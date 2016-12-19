package com.ibm.dp.dto;

import java.io.Serializable;


public class TransferHierarchyDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8743538231126947129L;
	private Integer intAccountID;
	private String strOLMID;
	private String strAccountName;
	private String strRole;
	private String strStatus;
	private String strCircleName;
	private String strCircleIDs;
	private String strParentName;
	private String strParentID;
	private String strParentCircleId;
	
	public String getStrParentCircleId() {
		return strParentCircleId;
	}
	public void setStrParentCircleId(String strParentCircleId) {
		this.strParentCircleId = strParentCircleId;
	}
	public String getStrParentName() {
		return strParentName;
	}
	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}
	public String getStrParentID() {
		return strParentID;
	}
	public void setStrParentID(String strParentID) {
		this.strParentID = strParentID;
	}
	public Integer getIntAccountID() {
		return intAccountID;
	}
	public void setIntAccountID(Integer intAccountID) {
		this.intAccountID = intAccountID;
	}
	public String getStrOLMID() {
		return strOLMID;
	}
	public void setStrOLMID(String strOLMID) {
		this.strOLMID = strOLMID;
	}
	public String getStrAccountName() {
		return strAccountName;
	}
	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}
	public String getStrRole() {
		return strRole;
	}
	public void setStrRole(String strRole) {
		this.strRole = strRole;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getStrCircleName() {
		return strCircleName;
	}
	public void setStrCircleName(String strCircleName) {
		this.strCircleName = strCircleName;
	}
	public String getStrCircleIDs() {
		return strCircleIDs;
	}
	public void setStrCircleIDs(String strCircleIDs) {
		this.strCircleIDs = strCircleIDs;
	}
	
}
