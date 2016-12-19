package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Code Generator Created On Mon Jul 16 15:03:41 IST 2007 Auto Generated
 *         Data Trasnfer Object class for table V_CIRCLE_MSTR
 * 
 */
public class CircleMasterDTO implements Serializable {

	protected String circleId;

	protected String circleName;

	protected String status;

	protected java.sql.Timestamp createdDt;

	protected String createdBy;

	protected java.sql.Timestamp updatedDt;

	protected String updatedBy;

	protected String rownum;

	protected String minsatCheck;

	protected String apefCheck;

	protected String circleCode;

	protected String hubCode;

	protected ArrayList hubList;

	protected String hubName;
	
	private String welcomeMsg;
	private String simReq;
	private boolean simReqCheck;
	protected String releaseTime;

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getSimReq() {
		return simReq;
	}

	public void setSimReq(String simReq) {
		this.simReq = simReq;
	}

	public boolean isSimReqCheck() {
		return simReqCheck;
	}

	public void setSimReqCheck(boolean simReqCheck) {
		this.simReqCheck = simReqCheck;
	}

	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	/**
	 * It returns the String representation of the CircleMasterDTO.
	 * 
	 * @return Returns a <code>String</code> having all the content
	 *         information of this object.
	 */
	public String toString() {
		return new StringBuffer(500)
				.append(" \nCircleMasterDTO - circleCode: ").append(circleCode)
				.append(",  circleName: ").append(circleName).append(
						",  status: ").append(status)
				.append(", \n createdBy: ").append(createdBy).append(
						",  createdDt: ").append(createdDt).append(
						",  updatedBy: ").append(updatedBy).append(
						",  updatedDt: ").append(updatedDt).append(
						",  minsatCheck: ").append(minsatCheck).append(
						",  apefCheck: ").append(apefCheck).append(
						",  hubCode: ").append(hubCode).append(",  hubName: ")
				.append(hubName).append(",  errorMessage: ").toString();
	}

	/** Creates a dto for the V_CIRCLE_MSTR table */
	public CircleMasterDTO() {
	}

	/**
	 * @return Returns the rownum.
	 */
	public String getRownum() {
		return rownum;
	}

	/**
	 * @param rownum
	 *            The rownum to set.
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	/**
	 * Get circleId associated with this object.
	 * 
	 * @return circleId
	 */

	public String getCircleId() {
		return circleId;
	}

	/**
	 * Set circleId associated with this object.
	 * 
	 * @param circleId
	 *            The circleId value to set
	 */

	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
	 * Get createdDt associated with this object.
	 * 
	 * @return createdDt
	 */

	public java.sql.Timestamp getCreatedDt() {
		return createdDt;
	}

	/**
	 * Set createdDt associated with this object.
	 * 
	 * @param createdDt
	 *            The createdDt value to set
	 */

	public void setCreatedDt(java.sql.Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get updatedDt associated with this object.
	 * 
	 * @return updatedDt
	 */

	public java.sql.Timestamp getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * Set updatedDt associated with this object.
	 * 
	 * @param updatedDt
	 *            The updatedDt value to set
	 */

	public void setUpdatedDt(java.sql.Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * Get updatedBy associated with this object.
	 * 
	 * @return updatedBy
	 */

	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy associated with this object.
	 * 
	 * @param updatedBy
	 *            The updatedBy value to set
	 */

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getApefCheck() {
		return apefCheck;
	}

	public void setApefCheck(String apefCheck) {
		this.apefCheck = apefCheck;
	}

	public String getMinsatCheck() {
		return minsatCheck;
	}

	public void setMinsatCheck(String minsatCheck) {
		this.minsatCheck = minsatCheck;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public String getHubCode() {
		return hubCode;
	}

	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}

	public ArrayList getHubList() {
		return hubList;
	}

	public void setHubList(ArrayList hubList) {
		this.hubList = hubList;
	}

	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

}
