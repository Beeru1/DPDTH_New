/**
 * 
 */
package com.ibm.dp.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;


import com.ibm.utilities.Utility;

/**
 * @author Nitesh
 *
 */
public class ServiceClassDTO implements Serializable{
	private int serviceId;
	protected int serviceClassId;
	protected String serviceClassName;
	protected String circleCode;
	protected String createdBy;
	protected Timestamp createdDate;
	protected String modifiedBy;
	protected Timestamp modifiedDate;
	protected String loginId;
	private ArrayList serviceList;
	protected int status;
	protected String executionDateString;
	protected String registrationReq;
	protected int statusString;
	protected String scCategoryName;
	protected int scCategoryId;

	protected String errorCode;
	protected String errorMessage;
	protected String rowNum;
	protected String tariffMessage;


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
	 * 
	 */
	public ServiceClassDTO() {
		// TODO Auto-generated constructor stub
		//status = ServiceClassEnum.ACTIVE.getValue();
	}

	/**
	 * Overloaded constructor taking serviceClassId and serviceClassName as parameter and initialize it.
	 * @param id
	 * @param name
	 */
	public ServiceClassDTO(int serviceClassId,
			 String serviceClassName) {
		this.serviceClassId=serviceClassId;
		this.serviceClassName=serviceClassName;
	}


	/**
	 * Overloaded constructor takes all properties as a patameter and initialize it.
	 * @param serviceClassId
	 * @param serviceClassName
	 * @param circleCode
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public ServiceClassDTO(int serviceClassId,
	 String serviceClassName,
	 String circleCode,
	 String createdBy,
	 Timestamp createdDate,
	 String modifiedBy,
	 Timestamp modifiedDate) {
		 this.serviceClassName=serviceClassName;
		 this.circleCode=circleCode;
		 this.createdBy=createdBy;
		 this.createdDate=createdDate;
		 this.modifiedBy=modifiedBy;

	}

	/**
	 * This method takes all propertes as a patameter and set to the ServiceClassDTO.
	 * @param serviceClassId
	 * @param serviceClassName
	 * @param circleCode
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */

	 public void setValues(int serviceClassId,
			 String serviceClassName,
			 String circleCode,
			 String createdBy,
			 Timestamp createdDate,
			 String modifiedBy,
			 Timestamp modifiedDate){

		 this.serviceClassName=serviceClassName;
		 this.circleCode=circleCode;
		 this.createdBy=createdBy;
		 this.createdDate=createdDate;
		 this.modifiedBy=modifiedBy;

	 }


	/**
	 * It returns the String representation of the ServiceClassDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer (500)
			.append(" \nServiceClassDTO - serviceClassId: ").append(serviceClassId)
			.append(",  serviceClassName: ").append(serviceClassName)
			.append(",  circleCode: ").append(circleCode)
			.append(", \n createdBy: ").append(createdBy)
			.append(",  createdDate: ").append(createdDate)
			.append(",  modifiedBy: ").append(modifiedBy)
			.append(",  modifiedDate: ").append(modifiedDate)
			.append(",  scCategoryName: ").append(scCategoryName)
			.append(",  scCategoryId: ").append(scCategoryId)
			.append(",  registrationReq: ").append(registrationReq)
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
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	 * Returns true if the ServiceClassDTO object has the same serviceClassId as of the current parameterDTO. 
	 * @param object The ServiceClassDTO object (serviceClassId)to be compared with serviceClassDTO.
	 * @return Returns true if the given Object's serviceClassId is same as current bject's serviceClassId. Else it returns false.
	 */
	public boolean equals(Object object) {
		if (object != null && object instanceof ServiceClassDTO) {
			ServiceClassDTO serviceClassDTO = (ServiceClassDTO) object;
			if(this.serviceClassId == serviceClassDTO.getServiceClassId()) {
				return true;
			}
		}

		return false;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public ArrayList getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList serviceList) {
		this.serviceList = serviceList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the status
	 */
	/*public String getStatusString() {
		return (GroupStatusEnum.getEnumState(status)).getName();
	}*/

	public String getCreatedDateAsString() {
		if(createdDate != null ){
		return (Utility.getTimestampAsString(createdDate, "dd/MM/yyyy"));
		} else {
			return null;
			}
	}

	/**
	 * @return the registrationReq
	 */
	public String getRegistrationReq() {
		return registrationReq;
	}

	/**
	 * @param registrationReq the registrationReq to set
	 */
	public void setRegistrationReq(String registrationReq) {
		this.registrationReq = registrationReq;
	}
	/**
	 * This method gets StatusString
	 * 
	 * @return status
	 */
	public String getStatusString() {
		if(status==1){
			return "Active";	
		} else {
			return "Inactive";	
		}
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
	 * @return the executionDateString
	 */
	public String getExecutionDateString() {
		return executionDateString;
	}

	/**
	 * @param executionDateString the executionDateString to set
	 */
	public void setExecutionDateString(String executionDateString) {
		this.executionDateString = executionDateString;
	}



	/**
	 * @param statusString the statusString to set
	 */
	public void setStatusString(int statusString) {
		this.statusString = statusString;
	}

	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getTariffMessage() {
		return tariffMessage;
	}

	public void setTariffMessage(String tariffMessage) {
		this.tariffMessage = tariffMessage;
	}



}