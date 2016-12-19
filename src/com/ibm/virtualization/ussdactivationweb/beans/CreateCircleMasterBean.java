/*
 * Created on Jul 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Mohit
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateCircleMasterBean extends ValidatorActionForm{
	private String circleId;
	private String circleName;
	private String functionType;
	private String circleCode;
	
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
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
   
}