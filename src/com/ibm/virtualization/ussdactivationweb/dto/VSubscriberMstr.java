/**************************************************************************
 * File     : SubscriberAction.java
 * Author   : Ashad
 * Created  : Sep 10, 2008
 * Modified : Sep 10, 2008
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;

/**
 * 
 * @author Ashad
 *
 */
public class VSubscriberMstr implements Serializable {

	private static final long serialVersionUID = 44287435l;

	protected String circleName = "";

	protected String circleId;

	protected String imsi;

	protected String msisdn;

	protected String sim;

	protected String status;

	protected java.sql.Timestamp createdDt;

	protected String createdBy;

	protected java.sql.Timestamp updatedDt;

	protected String updatedBy;

	protected String strCompSim;

	protected String strOldCompSim;

	protected String strOldSim;

	protected String strOldImsi;

	protected String strOldMsisdn;

	protected String strOldStatus;

	protected String strOldCircleId;

	protected String strId;

	protected int serviceClassId;

	protected int oldServiceClassId;

	protected String serviceClassName;

	protected String circleCode;

	/**
	 * It returns the String representation of the VSubscriberMstr. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer(500).append(
				" \n VSubscriberMstr - circleName: ").append(circleName)
				.append(",  imsi: ").append(imsi).append(",  status: ").append(
						status).append(",  createdBy: ").append(createdBy)
				.append(",  updatedBy: ").append(updatedBy).append(
						",  updatedDt: ").append(updatedDt)
				.append(",  strId: ").append(strId).append(",  msisdn: ")
				.append(msisdn).append(",  circleCode: ").append(circleCode)
				.append(",  sim: ").append(sim).append(",  strCompSim: ")
				.append(strCompSim).append(",  serviceClassName: ").append(
						serviceClassName).append(",  circleName: ").append(
						circleName).append(",  strOldMsisdn: ").append(
						strOldMsisdn).toString();
	}

	/** Creates a dto for the V_USER_MSTR table */
	public VSubscriberMstr() {
	}

	/**
	 * @return Returns the circleName.
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName The circleName to set.
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return Returns the old_circleId.
	 */
	public String getOldCircleId() {
		return strOldCircleId;
	}

	/**
	 * @param old_circleId The old_circleId to set.
	 */
	public void setOldCircleId(String strOldCircleId) {
		this.strOldCircleId = strOldCircleId;
	}

	/**
	 * @return Returns the old_status.
	 */
	public String getOldStatus() {
		return strOldStatus;
	}

	/**
	 * @param old_status The old_status to set.
	 */
	public void setOldStatus(String strOldStatus) {
		this.strOldStatus = strOldStatus;
	}

	/**
	 * @return Returns the comp_sim.
	 */
	public String getCompSim() {
		return strCompSim;
	}

	/**
	 * @param comp_sim The comp_sim to set.
	 */
	public void setCompSim(String strCompSim) {
		this.strCompSim = strCompSim;
	}

	/** 
	 * Get circleId associated with this object.
	 * @return circleId
	 **/

	public String getCircleId() {
		return circleId;
	}

	/** 
	 * Set circleId associated with this object.
	 * @param circleId The circleId value to set
	 **/

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/** 
	 * Get imsi associated with this object.
	 * @return imsi
	 **/

	public String getImsi() {
		return imsi;
	}

	/** 
	 * Set imsi associated with this object.
	 * @param imsi The imsi value to set
	 **/

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	/** 
	 * Get msisdn associated with this object.
	 * @return msisdn
	 **/

	public String getMsisdn() {
		return msisdn;
	}

	/** 
	 * Set msisdn associated with this object.
	 * @param msisdn The msisdn value to set
	 **/

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/** 
	 * Get sim associated with this object.
	 * @return sim
	 **/

	public String getSim() {
		return sim;
	}

	/** 
	 * Set sim associated with this object.
	 * @param sim The sim value to set
	 **/

	public void setSim(String sim) {
		this.sim = sim;
	}

	/** 
	 * Get status associated with this object.
	 * @return status
	 **/

	public String getStatus() {
		return status;
	}

	/** 
	 * Set status associated with this object.
	 * @param status The status value to set
	 **/

	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	 * Get createdDt associated with this object.
	 * @return createdDt
	 **/

	public java.sql.Timestamp getCreatedDt() {
		return createdDt;
	}

	/** 
	 * Set createdDt associated with this object.
	 * @param createdDt The createdDt value to set
	 **/

	public void setCreatedDt(java.sql.Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/** 
	 * Get createdBy associated with this object.
	 * @return createdBy
	 **/

	public String getCreatedBy() {
		return createdBy;
	}

	/** 
	 * Set createdBy associated with this object.
	 * @param createdBy The createdBy value to set
	 **/

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/** 
	 * Get updatedDt associated with this object.
	 * @return updatedDt
	 **/

	public java.sql.Timestamp getUpdatedDt() {
		return updatedDt;
	}

	/** 
	 * Set updatedDt associated with this object.
	 * @param updatedDt The updatedDt value to set
	 **/

	public void setUpdatedDt(java.sql.Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	/** 
	 * Get updatedBy associated with this object.
	 * @return updatedBy
	 **/

	public String getUpdatedBy() {
		return updatedBy;
	}

	/** 
	 * Set updatedBy associated with this object.
	 * @param updatedBy The updatedBy value to set
	 **/

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Returns the old_comp_sim.
	 */
	public String getOldCompSim() {
		return strOldCompSim;
	}

	/**
	 * @param old_comp_sim The old_comp_sim to set.
	 */
	public void setOldCompSim(String strOldCompSim) {
		this.strOldCompSim = strOldCompSim;
	}

	/**
	 * @return Returns the old_imsi.
	 */
	public String getOldImsi() {
		return strOldImsi;
	}

	/**
	 * @param old_imsi The old_imsi to set.
	 */
	public void setOldImsi(String strOldImsi) {
		this.strOldImsi = strOldImsi;
	}

	/**
	 * @return Returns the old_msisdn.
	 */
	public String getOldMsisdn() {
		return strOldMsisdn;
	}

	/**
	 * @param old_msisdn The old_msisdn to set.
	 */
	public void setOldMsisdn(String strOldMsisdn) {
		this.strOldMsisdn = strOldMsisdn;
	}

	/**
	 * @return Returns the old_sim.
	 */
	public String getOldSim() {
		return strOldSim;
	}

	/**
	 * @param old_sim The old_sim to set.
	 */
	public void setOldSim(String strOldSim) {
		this.strOldSim = strOldSim;
	}

	public String getId() {
		return strId;
	}

	public void setId(String strId) {
		this.strId = strId;
	}

	/**
	 * @return the serviceClassId
	 */
	public int getServiceClassId() {
		return serviceClassId;
	}

	/**
	 * @param serviceClassId the serviceClassId to set
	 */
	public void setServiceClassId(int serviceClassId) {
		this.serviceClassId = serviceClassId;
	}

	/**
	 * @return the oldServiceClassId
	 */
	public int getOldServiceClassId() {
		return oldServiceClassId;
	}

	/**
	 * @param oldServiceClassId the oldServiceClassId to set
	 */
	public void setOldServiceClassId(int oldServiceClassId) {
		this.oldServiceClassId = oldServiceClassId;
	}

	/**
	 * @return the serviceClassName
	 */
	public String getServiceClassName() {
		return serviceClassName;
	}

	/**
	 * @param serviceClassName the serviceClassName to set
	 */
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
}
