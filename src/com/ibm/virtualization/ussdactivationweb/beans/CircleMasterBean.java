/*
 * Created on Jul 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Amit
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CircleMasterBean extends ValidatorActionForm {
	public static final long serialVersionUID = 1L;

	private String circleCreateDate;

	private String circleCreatedBy;

	private String circleUpdatedDate;

	private String circleUpdatedBy;

	private String circleStatus;

	private String circleId;

	private String circleName;

	private String functionType;

	private ArrayList displayDetails;

	private String oldcircleStatus;

	private String oldcircleName;

	private String oldStatus;

	private boolean minsatCheck;

	private boolean apefCheck ;

	private String circleCode;

	private String hubCode;

	private ArrayList hubList;

	private String hubName;

	private String page1 = "";
	private ArrayList displayList;
	
	private String moveToCircle;
	private ArrayList moveCircleList;
	
	private String welcomeMsg;
	private String simReq;
	private boolean simReqCheck;
	
	private String releaseTime;

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

	public ArrayList getMoveCircleList() {
		return moveCircleList;
	}

	public void setMoveCircleList(ArrayList moveCircleList) {
		this.moveCircleList = moveCircleList;
	}

	public String getMoveToCircle() {
		return moveToCircle;
	}

	public void setMoveToCircle(String moveToCircle) {
		this.moveToCircle = moveToCircle;
	}

	/**
	 * @return the displayList
	 */
	public ArrayList getDisplayList() {
		return displayList;
	}

	/**
	 * @param displayList the displayList to set
	 */
	public void setDisplayList(ArrayList displayList) {
		this.displayList = displayList;
	}

	/**
	 * It returns the String representation of the CircleMasterDTO.
	 * 
	 * @return Returns a <code>String</code> having all the content
	 *         information of this object.
	 */
	public String toString() {
		return new StringBuffer(500).append(
				" \nCircleMasterBean - circleCode: ").append(circleCode)
				.append(",  circleName: ").append(circleName).append(
						",  status: ").append(circleStatus).append(
						", \n createdBy: ").append(circleCreatedBy).append(
						",  createdDt: ").append(circleCreateDate).append(
						",  updatedBy: ").append(circleUpdatedBy).append(
						",  updatedDt: ").append(circleUpdatedDate).append(
						",  minsatCheck: ").append(minsatCheck).append(
						",  apefCheck: ").append(apefCheck).append(
						",  hubCode: ").append(hubCode).append(",  hubName: ")
				.append(hubName).toString();
	}

	/**
	 * @return Returns the circleCreateDate.
	 */
	public String getCircleCreateDate() {
		return circleCreateDate;
	}

	/**
	 * @param circleCreateDate
	 *            The circleCreateDate to set.
	 */
	public void setCircleCreateDate(String circleCreateDate) {
		this.circleCreateDate = circleCreateDate;
	}

	/**
	 * @return Returns the circleCreatedBy.
	 */
	public String getCircleCreatedBy() {
		return circleCreatedBy;
	}

	/**
	 * @param circleCreatedBy
	 *            The circleCreatedBy to set.
	 */
	public void setCircleCreatedBy(String circleCreatedBy) {
		this.circleCreatedBy = circleCreatedBy;
	}

	/**
	 * @return Returns the circleStatus.
	 */
	public String getCircleStatus() {
		return circleStatus;
	}

	/**
	 * @param circleStatus
	 *            The circleStatus to set.
	 */
	public void setCircleStatus(String circleStatus) {
		this.circleStatus = circleStatus;
	}

	/**
	 * @return Returns the circleUpdatedBy.
	 */
	public String getCircleUpdatedBy() {
		return circleUpdatedBy;
	}

	/**
	 * @param circleUpdatedBy
	 *            The circleUpdatedBy to set.
	 */
	public void setCircleUpdatedBy(String circleUpdatedBy) {
		this.circleUpdatedBy = circleUpdatedBy;
	}

	/**
	 * @return Returns the circleUpdatedDate.
	 */
	public String getCircleUpdatedDate() {
		return circleUpdatedDate;
	}

	/**
	 * @param circleUpdatedDate
	 *            The circleUpdatedDate to set.
	 */
	public void setCircleUpdatedDate(String circleUpdatedDate) {
		this.circleUpdatedDate = circleUpdatedDate;
	}

	/**
	 * @return Returns the circleName.
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName
	 *            The circleName to set.
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            The circleId to set.
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return Returns the functionType.
	 */
	public String getFunctionType() {
		return functionType;
	}

	/**
	 * @param functionType
	 *            The functionType to set.
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	/**
	 * @return Returns the displayDetails.
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}

	/**
	 * @param displayDetails
	 *            The displayDetails to set.
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}

	
	/**
	 * @return Returns the oldcircleName.
	 */
	public String getOldcircleName() {
		return oldcircleName;
	}

	/**
	 * @param oldcircleName
	 *            The oldcircleName to set.
	 */
	public void setOldcircleName(String oldcircleName) {
		this.oldcircleName = oldcircleName;
	}

	/**
	 * @return Returns the oldcircleStatus.
	 */
	public String getOldcircleStatus() {
		return oldcircleStatus;
	}

	/**
	 * @param oldcircleStatus
	 *            The oldcircleStatus to set.
	 */
	public void setOldcircleStatus(String oldcircleStatus) {
		this.oldcircleStatus = oldcircleStatus;
	}

	/**
	 * @return Returns the oldStatus.
	 */
	public String getOldStatus() {
		return oldStatus;
	}

	/**
	 * @param oldStatus
	 *            The oldStatus to set.
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public boolean isApefCheck() {
		return apefCheck;
	}

	public void setApefCheck(boolean apefCheck) {
		this.apefCheck = apefCheck;
	}

	public boolean isMinsatCheck() {
		return minsatCheck;
	}

	public void setMinsatCheck(boolean minsatCheck) {
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

	/**
	 * @return the page
	 */
	public String getPage1() {
		return page1;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage1(String page1) {
		this.page1 = page1;
	}

}
