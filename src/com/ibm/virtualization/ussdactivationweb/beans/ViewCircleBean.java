/*
 * Created on Jul 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Amit
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ViewCircleBean extends ValidatorActionForm
{
	/**
	 * @return Returns the cIRCLE_CREATEDBY.
	 */
	public String getCircleCreatedBy() {
		return strCrlCrtdBy;
	}
	/**
	 * @param circle_createdby The cIRCLE_CREATEDBY to set.
	 */
	public void setCircleCreatedBy(String strCrlCrtdBy) {
		this.strCrlCrtdBy = strCrlCrtdBy;
	}
	/**
	 * @return Returns the cIRCLE_ID.
	 */
	public String getCircleId() {
		return strCircleId;
	}
	/**
	 * @param circle_id The cIRCLE_ID to set.
	 */
	public void setCircleId(String strCircleId) {
		this.strCircleId = strCircleId;
	}
	/**
	 * @return Returns the cIRCLE_NAME.
	 */
	public String getCircleName() {
		return strCircleName;
	}
	/**
	 * @param circle_name The cIRCLE_NAME to set.
	 */
	public void setCircleName(String strCircleName) {
		this.strCircleName = strCircleName;
	}
	/**
	 * @return Returns the cIRCLE_RATE.
	 */
	public float getCircleRate() {
		return flCircleRate;
	}
	/**
	 * @param circle_rate The cIRCLE_RATE to set.
	 */
	public void setCircleRate(float flCircleRate) {
		this.flCircleRate = flCircleRate;
	}
	/**
	 * @return Returns the cIRCLE_REGION.
	 */
	public String getCircleRegion() {
		return strCircleRegion;
	}
	/**
	 * @param circle_region The cIRCLE_REGION to set.
	 */
	public void setCircleRegion(String strCircleRegion) {
		this.strCircleRegion = strCircleRegion;
	}
	/**
	 * @return Returns the cIRCLE_THRESHOLD.
	 */
	public String getCircleThreshold() {
		return strCirThshold;
	}
	/**
	 * @param circle_threshold The cIRCLE_THRESHOLD to set.
	 */
	public void setCircleThreshold(String strCirThshold) {
		this.strCirThshold = strCirThshold;
	}
	/**
	 * @return Returns the cIRCLE_UPDATEDBY.
	 */
	public String getCircleUpdatedBy() {
		return strCrlUpdtdBy;
	}
	/**
	 * @param circle_updatedby The cIRCLE_UPDATEDBY to set.
	 */
	public void setCircleUpdateBy(String strCrlUpdtdBy) {
		this.strCrlUpdtdBy = strCrlUpdtdBy;
	}
	/**
	 * @return Returns the circleList.
	 */
	public String getCircleList() {
		return strCircleList;
	}
	/**
	 * @param circleList The circleList to set.
	 */
	public void setCircleList(String strCircleList) {
		this.strCircleList = strCircleList;
	}
	private String strCircleList;
	private String strCircleId;
	private String strCircleName;
	private String strCircleRegion;
	private float flCircleRate;
	private String strCirThshold;
	private String strCrlCrtdBy;
	private String strCrlUpdtdBy;
}
