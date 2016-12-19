/*****************************************************************************\
 **
 ** Distributor Portal.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;

public class GeographyFormBean extends ActionForm {

  
	private static final long serialVersionUID = 9169076242533742539L;

	private String geographyId;
    private String geographyName;   
    private String status;      
    private String geographyCode;    
    private String flag;    
    private String page;
    private String methodName;
    private ArrayList regionList;
    private String parentId;
    private Integer level;
    private ArrayList geographyList;
    private String editStatus;
    private String geographyStatus;
    private String isGeographyAdmin;   
    private String disabledRegion;
    private String startDate;    
    private String endDate;
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.geographyId = null;
		this.geographyName = null;
		this.status = null;
		this.geographyCode=null;
		this.parentId=null;
	}

	public String getGeographyCode() {
		return geographyCode;
	}

	public void setGeographyCode(String geographyCode) {
		this.geographyCode = geographyCode;
	}

	public String getGeographyId() {
		return geographyId;
	}

	public void setGeographyId(String geographyId) {
		this.geographyId = geographyId;
	}

	public String getGeographyName() {
		return geographyName;
	}

	public void setGeographyName(String geographyName) {
		this.geographyName = geographyName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ArrayList getRegionList() {
		return regionList;
	}

	public void setRegionList(ArrayList regionList) {
		this.regionList = regionList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public ArrayList getGeographyList() {
		return geographyList;
	}

	public void setGeographyList(ArrayList geographyList) {
		this.geographyList = geographyList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getGeographyStatus() {
		return geographyStatus;
	}

	public void setGeographyStatus(String geographyStatus) {
		this.geographyStatus = geographyStatus;
	}

	public String getDisabledRegion() {
		return disabledRegion;
	}

	public void setDisabledRegion(String disabledRegion) {
		this.disabledRegion = disabledRegion;
	}

	public String getIsGeographyAdmin() {
		return isGeographyAdmin;
	}

	public void setIsGeographyAdmin(String isGeographyAdmin) {
		this.isGeographyAdmin = isGeographyAdmin;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
		
}