/**************************************************************************
 * File     : CircleDTO.java
 * Author   : Banita
 * Created  : Oct 6, 2008
 * Modified : Oct 6, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 6, 2008 	Banita	First Cut.
 * V0.2		Oct 6, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.services.dto;

import java.io.Serializable;


/*******************************************************************************
 * This Data Transfer object class provides attributes to store business 
 * user details, and defines setter getter methods for the attributes.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class CircleDTO  implements Serializable {


	protected String circleName;
	
	protected Integer circleId;

	protected String status;

	protected String createdDt;

	protected String createdBy;

	protected String updatedDt;

	protected String updatedBy;

	protected boolean minsatCheck;

	protected boolean apefCheck;

	protected String circleCode;

	protected String hubCode;

	protected String hubName;

	protected String errorCode; 
	
	protected String errorMessage;
	
	protected String welcomeMsg;
	
	protected boolean simReqCheck;
	
	
	public boolean isSimReqCheck() {
		return simReqCheck;
	}

	public void setSimReqCheck(boolean simReqCheck) {
		this.simReqCheck = simReqCheck;
	}

	/** Creates a dto for the V_CIRCLE_MSTR table */
	public CircleDTO() {
	}
	
	/**
	 * It returns the String representation of the CircleDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer (500)
			.append(" \nCircleDTO- circleCode: ").append(circleCode)
			.append(",  circleName: ").append(circleName)
			.append(",  status: ").append(status)
			.append(", \n createdBy: ").append(createdBy)
			.append(",  createdDt: ").append(createdDt)
			.append(",  updatedBy: ").append(updatedBy)
			.append(",  updatedDt: ").append(updatedDt)
			.append(",  minsatCheck: ").append(minsatCheck)
			.append(",  apefCheck: ").append(apefCheck)
			.append(",  hubCode: ").append(hubCode)
			.append(",  hubName: ").append(hubName)
			.append(",  errorMessage: ").append(errorMessage)
			.append(",  errorCode: ").append(errorCode)
			.toString();		
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
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public String getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the hubCode
	 */
	public String getHubCode() {
		return hubCode;
	}

	/**
	 * @param hubCode the hubCode to set
	 */
	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}

	/**
	 * @return the hubName
	 */
	public String getHubName() {
		return hubName;
	}

	/**
	 * @param hubName the hubName to set
	 */
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public String getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * @param apefCheck the apefCheck to set
	 */
	public void setApefCheck(boolean apefCheck) {
		this.apefCheck = apefCheck;
	}

	/**
	 * @param minsatCheck the minsatCheck to set
	 */
	public void setMinsatCheck(boolean minsatCheck) {
		this.minsatCheck = minsatCheck;
	}

	/**
	 * @return the apefCheck
	 */
	public boolean isApefCheck() {
		return apefCheck;
	}

	/**
	 * @return the minsatCheck
	 */
	public boolean isMinsatCheck() {
		return minsatCheck;
	}

	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

}
