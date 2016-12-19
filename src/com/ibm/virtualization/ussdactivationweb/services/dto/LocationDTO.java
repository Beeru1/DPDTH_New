/**************************************************************************
 * File     : LocationDTO.java
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
 * This Data Transfer object class provides attributes to location, 
 * and defines setter getter methods for the attributes.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class LocationDTO  implements Serializable{
	
	    protected int status;
	  
	   
	    /*Location mstr details*/
	    protected String locationName;
	    protected String locaionType;
	    protected int locMstrId;
	    
	    
	    /*Location data details*/
	    protected String locationDataName;
	    protected String locDataId;
	    protected String parentId;
	    protected String locDataCode;
	    
	    protected String createdDt;
	    protected String updatedDt;
	    
	    
	    protected String errorMessage;
	    protected String errorCode;
	    
	    protected String releaseTime;
	    

		public String getReleaseTime() {
			return releaseTime;
		}


		public void setReleaseTime(String releaseTime) {
			this.releaseTime = releaseTime;
		}


		/**
		 * It returns the String representation of the ServiceClassDTO. 
		 * @return Returns a <code>String</code> having all the content information of this object.
		 */
		public String toString() {
			return new StringBuffer (500)
				.append(" \nLocationDTO - circleCode: ")
				.append(",  locationName: ").append(locationName)
				.append(",  locaionType: ").append(locaionType)
				.append(",  locationDataName: ").append(locationDataName)
				.append(",  parentId: ").append(parentId)
				.append(",  locDataCode: ").append(locDataCode)
				.append(",  errorMessage: ").append(errorMessage)
				.append(",  errorCode: ").append(errorCode)
				.toString();		
		}


		/** Creates a dto for the V_CIRCLE_MSTR table */
		public LocationDTO() {
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
		 * @return the locaionType
		 */
		public String getLocaionType() {
			return locaionType;
		}


		/**
		 * @param locaionType the locaionType to set
		 */
		public void setLocaionType(String locaionType) {
			this.locaionType = locaionType;
		}


		/**
		 * @return the locationDataName
		 */
		public String getLocationDataName() {
			return locationDataName;
		}


		/**
		 * @param locationDataName the locationDataName to set
		 */
		public void setLocationDataName(String locationDataName) {
			this.locationDataName = locationDataName;
		}


		/**
		 * @return the locationName
		 */
		public String getLocationName() {
			return locationName;
		}


		/**
		 * @param locationName the locationName to set
		 */
		public void setLocationName(String locationName) {
			this.locationName = locationName;
		}


		/**
		 * @return the locDataCode
		 */
		public String getLocDataCode() {
			return locDataCode;
		}


		/**
		 * @param locDataCode the locDataCode to set
		 */
		public void setLocDataCode(String locDataCode) {
			this.locDataCode = locDataCode;
		}


		/**
		 * @return the locDataId
		 */
		public String getLocDataId() {
			return locDataId;
		}


		/**
		 * @param locDataId the locDataId to set
		 */
		public void setLocDataId(String locDataId) {
			this.locDataId = locDataId;
		}


		/**
		 * @return the locMstrId
		 */
		public int getLocMstrId() {
			return locMstrId;
		}


		/**
		 * @param locMstrId the locMstrId to set
		 */
		public void setLocMstrId(int locMstrId) {
			this.locMstrId = locMstrId;
		}


	

		/**
		 * @return the parentId
		 */
		public String getParentId() {
			return parentId;
		}


		/**
		 * @param parentId the parentId to set
		 */
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}


		/**
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}


		/**
		 * @param status the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
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


}
