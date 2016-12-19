/**************************************************************************
 * File     : ServiceDTO.java
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
package com.ibm.dp.dto;

import java.io.Serializable;


/*******************************************************************************
 * This Data Transfer object class provides attributes to store business 
 * user details, and defines setter getter methods for the attributes.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class ServiceDTO implements Serializable{
	
	protected int serviceClassId;
	protected String serviceClassName;
	protected String circleCode;
	protected String createdBy;
	protected String createdDate;
	protected String modifiedBy;
	protected String modifiedDate;
	protected int status;
	protected boolean serviceClassCheck;
	protected String scCategoryName;
	protected int scCategoryId;
	protected String medCmdFta;
	protected String medRechargeCmdFta;
	protected String errorCode;
	protected String errorMessage;
	protected String tariffMessage;
			

			/**
			 * 
			 */
			public ServiceDTO() {
				// TODO Auto-generated constructor stub
				
			}
			

			/**
			 * It returns the String representation of the ServiceDTO. 
			 * @return Returns a <code>String</code> having all the content information of this object.
			 */
			public String toString() {
				return new StringBuffer (500)
					.append(" \nServiceDTO - serviceClassId: ").append(serviceClassId)
					.append(",  serviceClassName: ").append(serviceClassName)
					.append(",  circleCode: ").append(circleCode)
					.append(", \n createdBy: ").append(createdBy)
					.append(",  createdDate: ").append(createdDate)
					.append(",  modifiedBy: ").append(modifiedBy)
					.append(",  modifiedDate: ").append(modifiedDate)
					.append(",  scCategoryName: ").append(scCategoryName)
					.append(",  scCategoryId: ").append(scCategoryId)
					.append(",  medCmdFta: ").append(medCmdFta)
					.append(",  medRechargeCmdFta: ").append(medRechargeCmdFta)
					.append(",  errorMessage: ").append(errorMessage)
					.append(",  errorCode: ").append(errorCode)
					.toString();		
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
			 * @return the modifiedBy
			 */
			public String getModifiedBy() {
				return modifiedBy;
			}


			/**
			 * @param modifiedBy the modifiedBy to set
			 */
			public void setModifiedBy(String modifiedBy) {
				this.modifiedBy = modifiedBy;
			}


			/**
			 * @return the modifiedDate
			 */
			public String getModifiedDate() {
				return modifiedDate;
			}


			/**
			 * @param modifiedDate the modifiedDate to set
			 */
			public void setModifiedDate(String modifiedDate) {
				this.modifiedDate = modifiedDate;
			}

			/**
			 * @return the scCategoryId
			 */
			public int getScCategoryId() {
				return scCategoryId;
			}


			/**
			 * @param scCategoryId the scCategoryId to set
			 */
			public void setScCategoryId(int scCategoryId) {
				this.scCategoryId = scCategoryId;
			}


			/**
			 * @return the scCategoryName
			 */
			public String getScCategoryName() {
				return scCategoryName;
			}


			/**
			 * @param scCategoryName the scCategoryName to set
			 */
			public void setScCategoryName(String scCategoryName) {
				this.scCategoryName = scCategoryName;
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
			 * @return the createdDate
			 */
			public String getCreatedDate() {
				return createdDate;
			}


			/**
			 * @param createdDate the createdDate to set
			 */
			public void setCreatedDate(String createdDate) {
				this.createdDate = createdDate;
			}


			/**
			 * @return the serviceClassCheck
			 */
			public boolean isServiceClassCheck() {
				return serviceClassCheck;
			}


			/**
			 * @param serviceClassCheck the serviceClassCheck to set
			 */
			public void setServiceClassCheck(boolean serviceClassCheck) {
				this.serviceClassCheck = serviceClassCheck;
			}


			public String getTariffMessage() {
				return tariffMessage;
			}


			public void setTariffMessage(String tariffMessage) {
				this.tariffMessage = tariffMessage;
			}


			/**
			 * @return the medCmdFta
			 */
			public String getMedCmdFta() {
				return medCmdFta;
			}


			/**
			 * @param medCmdFta the medCmdFta to set
			 */
			public void setMedCmdFta(String medCmdFta) {
				this.medCmdFta = medCmdFta;
			}


			/**
			 * @return the medRechargeCmdFta
			 */
			public String getMedRechargeCmdFta() {
				return medRechargeCmdFta;
			}


			/**
			 * @param medRechargeCmdFta the medRechargeCmdFta to set
			 */
			public void setMedRechargeCmdFta(String medRechargeCmdFta) {
				this.medRechargeCmdFta = medRechargeCmdFta;
			}

}
