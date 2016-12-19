/*****************************************************************************\
 **
 **Distributor Portal.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class Geography implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private int geographyId;
	
	private String geographyCode;

	private String geographyName;

	private int regionId;

	private String status;

	private float rate;

	private Integer createdBy;

	private Date createdDt;

	private Integer updatedBy;

	private Date updatedDt;

	private String regionName;
	
	private String loginName;
	
	private String rowNum;
	
	private int totalRecords;
	
	private Integer level;
	private Integer parentId;


	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "Circle ( " + super.toString() + TAB + "geographyId = "
				+ this.geographyId + TAB + "geographyCode = " + this.geographyCode + TAB 
				+ "geographyName = " + this.geographyName + TAB
				+ "regionId = " + this.regionId + TAB + "status = "
				+ this.status + TAB + "rate = " + this.rate + TAB
				+ "createdBy = "
				+ this.createdBy + TAB + "createdDt = " + this.createdDt + TAB
				+ "updatedBy = " + this.updatedBy + TAB + "updatedDt = "
				+ this.updatedDt + TAB + "regionName = " + this.regionName
				+ TAB + " )";
		return retValue;
	}



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	



	public Integer getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}



	public Date getCreatedDt() {
		return createdDt;
	}



	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}



	public String getGeographyCode() {
		return geographyCode;
	}



	public void setGeographyCode(String geographyCode) {
		this.geographyCode = geographyCode;
	}



	public int getGeographyId() {
		return geographyId;
	}



	public void setGeographyId(int geographyId) {
		this.geographyId = geographyId;
	}



	public String getGeographyName() {
		return geographyName;
	}



	public void setGeographyName(String geographyName) {
		this.geographyName = geographyName;
	}



	public String getLoginName() {
		return loginName;
	}



	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public float getRate() {
		return rate;
	}



	public void setRate(float rate) {
		this.rate = rate;
	}



	public int getRegionId() {
		return regionId;
	}



	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}



	public String getRegionName() {
		return regionName;
	}



	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}



	public String getRowNum() {
		return rowNum;
	}



	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public int getTotalRecords() {
		return totalRecords;
	}



	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}





	public Date getUpdatedDt() {
		return updatedDt;
	}



	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}



	public Integer getLevel() {
		return level;
	}



	public void setLevel(Integer level) {
		this.level = level;
	}



	


	public Integer getParentId() {
		return parentId;
	}



	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}



	public Integer getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}



	
}