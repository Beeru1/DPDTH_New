/**************************************************************************
 * File     : RequesterDTO.java
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

public class RequesterDTO implements Serializable{
	protected java.lang.String circleCode;
	protected java.lang.String circleName;
	protected java.lang.String hubName;
	protected java.lang.String errorCode;
	protected java.lang.String errorMessage;
	protected BusinessUserDTO[] businessUserArray;
    
    public RequesterDTO() {
    }


    public java.lang.String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }

    public java.lang.String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }

	/**
	 * @return the businessUserArray
	 */
	public BusinessUserDTO[] getBusinessUserArray() {
		return businessUserArray;
	}

	/**
	 * @param businessUserArray the businessUserArray to set
	 */
	public void setBusinessUserArray(BusinessUserDTO[] businessUserArray) {
		this.businessUserArray = businessUserArray;
	}


	/**
	 * @return the circleCode
	 */
	public java.lang.String getCircleCode() {
		return circleCode;
	}


	/**
	 * @param circleCode the circleCode to set
	 */
	public void setCircleCode(java.lang.String circleCode) {
		this.circleCode = circleCode;
	}


	/**
	 * @return the circleName
	 */
	public java.lang.String getCircleName() {
		return circleName;
	}


	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(java.lang.String circleName) {
		this.circleName = circleName;
	}


	/**
	 * @return the hubName
	 */
	public java.lang.String getHubName() {
		return hubName;
	}


	/**
	 * @param hubName the hubName to set
	 */
	public void setHubName(java.lang.String hubName) {
		this.hubName = hubName;
	}

}
