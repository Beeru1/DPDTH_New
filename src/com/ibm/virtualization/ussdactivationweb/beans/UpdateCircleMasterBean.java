/*
 * Created on Jul 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;

//import java.sql.Date;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;


/**
 * @author Amit
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdateCircleMasterBean extends ValidatorActionForm{
	private String circleId;
	private String circleName;
	private String circleCreateDate;
	private String circleCreatedBy;
	private Date circleUpdatedDate;
	private String circleUpdatedBy;
	private String circleStatus;
	private String functionType;
	private String circleCode;
	
	private ArrayList displayDetails;
	private String loginId;
	/**
	 * @return Returns the circleCreateDate.
	 */
	public String getCircleCreateDate() {
		return circleCreateDate;
	}
	/**
	 * @param circleCreateDate The circleCreateDate to set.
	 */
	public void setCircleCreateDate(String circleCreateDate) {
		this.circleCreateDate = circleCreateDate;
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
	 * @return Returns the circleUpdatedDate.
	 */
	public Date getCircleUpdatedDate() {
		return circleUpdatedDate;
	}
	/**
	 * @param circleUpdatedDate The circleUpdatedDate to set.
	 */
	public void setCircleUpdatedDate(Date circleUpdatedDate) {
		this.circleUpdatedDate = circleUpdatedDate;
	}
	/**
	 * @return Returns the functionType.
	 */
	public String getFunctionType() {
		return functionType;
	}
	/**
	 * @param functionType The functionType to set.
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	
    /**
     * @return Returns the circleStatus.
     */
    public String getCircleStatus() {
        return circleStatus;
    }
    /**
     * @param circleStatus The circleStatus to set.
     */
    public void setCircleStatus(String circleStatus) {
        this.circleStatus = circleStatus;
    }
    /**
     * @return Returns the circleCreatedBy.
     */
    public String getCircleCreatedBy() {
        return circleCreatedBy;
    }
    /**
     * @param circleCreatedBy The circleCreatedBy to set.
     */
    public void setCircleCreatedBy(String circleCreatedBy) {
        this.circleCreatedBy = circleCreatedBy;
    }
    /**
     * @return Returns the circleId.
     */
    public String getCircleId() {
        return circleId;
    }
    /**
     * @param circleId The circleId to set.
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }
    /**
     * @return Returns the circleUpdatedBy.
     */
    public String getCircleUpdatedBy() {
        return circleUpdatedBy;
    }
    /**
     * @param circleUpdatedBy The circleUpdatedBy to set.
     */
    public void setCircleUpdatedBy(String circleUpdatedBy) {
        this.circleUpdatedBy = circleUpdatedBy;
    }
	/**
	 * @return Returns the displayDetails.
	 */
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}
	/**
	 * @param displayDetails The displayDetails to set.
	 */
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}
	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId The loginId to set.
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
}