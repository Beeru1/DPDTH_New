/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class Circle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private int circleId;
	
	private String circleCode;

	private String circleName;

	private int regionId;

	private String status;

	private float rate;

	private String description;

	private double openingBalance;

	private double operatingBalance;

	private double availableBalance;

	private double threshold;

	private long createdBy;

	private Date createdDt;

	private long updatedBy;

	private Date updatedDt;

	private String regionName;
	
	private String loginName;
	
	private String rowNum;
	
	private int totalRecords;
	
	private String wireLessCode;
	
	private String wireLineCode;
	
	private String dthCode;
	

	/*
	 * Added by ajay
	 */
	private String phone;
    private String terms1;
    private String terms2;
    private String terms3;
    private String terms4;
    private String remarks;
    private String saletax;
    private String saletaxdate=null;
    private String service;
    private String compName;
    private String tin;
    private String tinDate=null;
    private String address1;
    private String address2;
    private String fax;
    private String cst;
    private String cstdate=null; 
    
    

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getCstdate() {
		return cstdate;
	}

	public void setCstdate(String cstdate) {
		this.cstdate = cstdate;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSaletax() {
		return saletax;
	}

	public void setSaletax(String saletax) {
		this.saletax = saletax;
	}

	public String getSaletaxdate() {
		return saletaxdate;
	}

	public void setSaletaxdate(String saletaxdate) {
		this.saletaxdate = saletaxdate;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getTinDate() {
		return tinDate;
	}

	public void setTinDate(String tinDate) {
		this.tinDate = tinDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTerms1() {
		return terms1;
	}

	public void setTerms1(String terms1) {
		this.terms1 = terms1;
	}

	public String getTerms2() {
		return terms2;
	}

	public void setTerms2(String terms2) {
		this.terms2 = terms2;
	}

	public String getTerms3() {
		return terms3;
	}

	public void setTerms3(String terms3) {
		this.terms3 = terms3;
	}

	public String getTerms4() {
		return terms4;
	}

	public void setTerms4(String terms4) {
		this.terms4 = terms4;
	}
/*
 * Ended by Ajay
 */
	
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** Creates a dto for the VR_CIRCLE_MASTER table */
	public Circle() {
	}

	/**
	 * Get circleId associated with this object.
	 * 
	 * @return circleId
	 */

	public int getCircleId() {
		return circleId;
	}

	/**
	 * Set circleId associated with this object.
	 * 
	 * @param circleId
	 *            The circleId value to set
	 */

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	
	/**
	 * @return the circleCode
	 */
	public String getCircleCode() {
		return circleCode;
	}

	/**
	 * @param circleCode the circleCode to set
	 */
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	/**
	 * Get circleName associated with this object.
	 * 
	 * @return circleName
	 */

	public String getCircleName() {
		return circleName;
	}

	/**
	 * Set circleName associated with this object.
	 * 
	 * @param circleName
	 *            The circleName value to set
	 */

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * Get status associated with this object.
	 * 
	 * @return status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * Set status associated with this object.
	 * 
	 * @param status
	 *            The status value to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get rate associated with this object.
	 * 
	 * @return rate
	 */

	public float getRate() {
		return rate;
	}

	/**
	 * Set rate associated with this object.
	 * 
	 * @param rate
	 *            The rate value to set
	 */

	public void setRate(float rate) {
		this.rate = rate;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public long getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get updatedBy associated with this object.
	 * 
	 * @return updatedBy
	 */

	public long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy associated with this object.
	 * 
	 * @param updatedBy
	 *            The updatedBy value to set
	 */

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the createdDt.
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            The createdDt to set.
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return Returns the updatedDt.
	 */
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            The updatedDt to set.
	 */
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * Get description associated with this object.
	 * 
	 * @return description
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * Set description associated with this object.
	 * 
	 * @param description
	 *            The description value to set
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get regionId associated with this object.
	 * 
	 * @return regionId
	 */

	public int getRegionId() {
		return regionId;
	}

	/**
	 * Set regionId associated with this object.
	 * 
	 * @param regionId
	 *            The regionId value to set
	 */

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return Returns the availableBalance.
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            The availableBalance to set.
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return Returns the openingBalance.
	 */
	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance
	 *            The openingBalance to set.
	 */
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return Returns the operatingBalance.
	 */
	public double getOperatingBalance() {
		return operatingBalance;
	}

	/**
	 * @param operatingBalance
	 *            The operatingBalance to set.
	 */
	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}

	/**
	 * @return Returns the threshold.
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold
	 *            The threshold to set.
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return Returns the regionName.
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            The regionName to set.
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getDthCode() {
		return dthCode;
	}

	public void setDthCode(String dthCode) {
		this.dthCode = dthCode;
	}

	public String getWireLessCode() {
		return wireLessCode;
	}

	public void setWireLessCode(String wireLessCode) {
		this.wireLessCode = wireLessCode;
	}

	public String getWireLineCode() {
		return wireLineCode;
	}

	public void setWireLineCode(String wireLineCode) {
		this.wireLineCode = wireLineCode;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "Circle ( " + super.toString() + TAB + "circleId = "
				+ this.circleId + TAB + "circleCode = " + this.circleCode + TAB 
				+ "circleName = " + this.circleName + TAB
				+ "regionId = " + this.regionId + TAB + "status = "
				+ this.status + TAB + "rate = " + this.rate + TAB
				+ "description = " + this.description + TAB
				+ "openingBalance = " + this.openingBalance + TAB
				+ "operatingBalance = " + this.operatingBalance + TAB
				+ "availableBalance = " + this.availableBalance + TAB
				+ "threshold = " + this.threshold + TAB + "createdBy = "
				+ this.createdBy + TAB + "createdDt = " + this.createdDt + TAB
				+ "updatedBy = " + this.updatedBy + TAB + "updatedDt = "
				+ this.updatedDt + TAB + "regionName = " + this.regionName
				+ TAB + " )";

		return retValue;
	}

	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
}