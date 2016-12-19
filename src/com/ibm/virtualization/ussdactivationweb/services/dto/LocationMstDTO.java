/**************************************************************************
 * File     : LocationMstDTO.java
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
 * This Data Transfer object class provides attributes to store location details,
 *  and defines setter getter methods for the attributes.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class LocationMstDTO implements Serializable{
	protected java.lang.String errorCode=null;
	protected java.lang.String errorMessage=null;
	protected LocationDTO[] locationArray;
   
        public LocationMstDTO() {
        }

		

		/**
		 * @return the errorCode
		 */
		public java.lang.String getErrorCode() {
			return errorCode;
		}

		/**
		 * @param errorCode the errorCode to set
		 */
		public void setErrorCode(java.lang.String errorCode) {
			this.errorCode = errorCode;
		}

		/**
		 * @return the errorMessage
		 */
		public java.lang.String getErrorMessage() {
			return errorMessage;
		}

		/**
		 * @param errorMessage the errorMessage to set
		 */
		public void setErrorMessage(java.lang.String errorMessage) {
			this.errorMessage = errorMessage;
		}



		/**
		 * @return the locationArray
		 */
		public LocationDTO[] getLocationArray() {
			return locationArray;
		}



		/**
		 * @param locationArray the locationArray to set
		 */
		public void setLocationArray(LocationDTO[] locationArray) {
			this.locationArray = locationArray;
		}

}
