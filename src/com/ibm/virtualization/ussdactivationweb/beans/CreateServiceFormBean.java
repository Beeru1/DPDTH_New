package com.ibm.virtualization.ussdactivationweb.beans;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;


/**
 * Form bean for a Struts application.
 * Users may access 2 fields on this form:
 * <ul> 
 * <li>ServiceClass - [your comment here]
 * <li>CircleCode - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class CreateServiceFormBean extends ActionForm

{
	public static final long serialVersionUID = 1L;
	private int serviceId1;
	private String circleCode1;
	private int serviceId;
    private String serviceClassName;
    private String serviceClassId;
    private String circleName;
    private String circleCode;
    private int status;
    private ArrayList circleList;
    private ArrayList serviceList;
    protected String createdBy;
	protected Timestamp createdDate;
	private String methodName;
	private boolean registrationReq;
	private String userRole;
	protected String scCategoryName;
	protected String scCategoryId;
	private ArrayList scCategoryList;
	private String oldServiceClassName;
	private String page1="";
	private String tariffMessage;
	public String getTariffMessage() {
		return tariffMessage;
	}

	public void setTariffMessage(String tariffMessage) {
		this.tariffMessage = tariffMessage;
	}

	/**
	 * @return the scCategoryList
	 */
	public ArrayList getScCategoryList() {
		return scCategoryList;
	}

	/**
	 * @param scCategoryList the scCategoryList to set
	 */
	public void setScCategoryList(ArrayList scCategoryList) {
		this.scCategoryList = scCategoryList;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public CreateServiceFormBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return (new StringBuffer(200)
			.append("CreateServiceFormBean - serviceId: ").append(serviceId)
			.append(", serviceClassName: ").append(serviceClassName)
			.append(", circleCode: ").append(circleCode)
			.append(", circleName: ").append(circleName)
			.append(", serviceClassId: ").append(serviceClassId)
			.append(", serviceId1: ").append(serviceId1)
			.append(", circleList: ").append(circleList)
			.append(", serviceList: ").append(serviceList)
			.append(", createdBy: ").append(createdBy)
			.append(", createdDate: ").append(createdDate)
			.append(", createdDate: ").append(registrationReq)
			.append(", userRole: ").append(userRole).toString());
			
	}

	/**
	 *  This method gets the circleCode of circle to be created.
	 * @return the circleCode
	 */
	
	public String getCircleCode() {
		return circleCode;
	}
	/**
	 * This method sets the circleCode of circle to be created.
	 * @param circleCode the circleCode to set
	 */
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	/**
	 * This method gets the circleName.
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * This method sets the circleName.
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 *  This method gets the serviceClass Status.
	 * @param return the Status.
	 */

	public int getStatus() {
		return status;
	}
	/**
	 *  This method sets the serviceClass Status.
	 * @param Status the Status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 *  This method gets the information about the creator of ServiceClass.
	 * @param return the createdBy.
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 *  This method sets the creator of ServiceClass.
	 * @param CreatedBy the CreatedBy to set.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 *  This method gets the time and date  of creation of ServiceClass.
	 * @param return the CreatedDate.
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	/**
	 *  This method sets the time and date  of creation of ServiceClass.
	 * @param CreatedDate the CreatedDate to set.
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * This method gets the list of circles.
	 * @return the circleList
	 */
	public ArrayList getCircleList() {
		return circleList;
	}
	/**
	 * This method sets the list of circles.
	 * @param circleList the circleList to set
	 */
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}
	/**
	 * This method gets the name of serviceClass to be created.
	 * @return the ServiceClassName
	 */
	public String getServiceClassName() {
		return serviceClassName;
	}
	/**
	 * This method sets the name of serviceClass to be created.
	 * @param ServiceClassName the ServiceClassName to set
	 */
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}
	/**
	 * This method gets the list of serviceClass.
	 * @return the ServiceList
	 */
	public ArrayList getServiceList() {
		return serviceList;
	}
	/**
	 * This method sets the the list of serviceClass.
	 * @param ServiceList the ServiceList to set
	 */
	public void setServiceList(ArrayList serviceList) {
		this.serviceList = serviceList;
	}
	/**
	 * This method gets the  hidden field serviceId which stores ServiceClassId.
	 * @return the ServiceId
	 */
	public int getServiceId() {
		return serviceId;
	}
	/**
	 * This method sets the  hidden field serviceId which stores ServiceClassId.
	 * @param ServiceId the ServiceId to set
	 */
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * This method gets the ServiceClassId.
	 * @return the ServiceId
	 */
	public String getServiceClassId() {
		return serviceClassId;
	}
	/**
	 * This method sets the  ServiceClassId.
	 * @param ServiceClassId the ServiceClassId to set.
	 */
	public void setServiceClassId(String serviceClassId) {
		this.serviceClassId = serviceClassId;
	}
	/**
	 * This method is used to reset the values.
	 * 
	 */
	public void reset(){
		circleCode="";
		serviceClassId="";
		serviceClassName="";
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
	 * This method gets the  hidden field circlecode1 which stores CircleCode.
	 * @return the circlecode1
	 */
	public String getCircleCode1() {
		return circleCode1;
	}
	/**
	 * This method sets the  hidden field circlecode1 which stores CircleCode.
	 * @param ServiceId1 the circlecode1 to set.
	 */
	public void setCircleCode1(String circleCode1) {
		this.circleCode1 = circleCode1;
	}
	/**
	 * This method gets the  hidden field serviceId1 which stores ServiceClassId.
	 * @return the ServiceId1
	 */
	public int getServiceId1() {
		return serviceId1;
	}
	/**
	 * This method sets the  hidden field serviceId1 which stores ServiceClassId.
	 * @param ServiceId1 the ServiceId1 to set.
	 */
	public void setServiceId1(int serviceId1) {
		this.serviceId1 = serviceId1;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the registrationReq
	 */
	public boolean isRegistrationReq() {
		return registrationReq;
	}

	/**
	 * @param registrationReq the registrationReq to set
	 */
	public void setRegistrationReq(boolean registrationReq) {
		this.registrationReq = registrationReq;
	}

	/**
	 * @return the scCategoryId
	 */
	public String getScCategoryId() {
		return scCategoryId;
	}

	/**
	 * @param scCategoryId the scCategoryId to set
	 */
	public void setScCategoryId(String scCategoryId) {
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
	 * @return the oldServiceClassName
	 */
	public String getOldServiceClassName() {
		return oldServiceClassName;
	}

	/**
	 * @param oldServiceClassName the oldServiceClassName to set
	 */
	public void setOldServiceClassName(String oldServiceClassName) {
		this.oldServiceClassName = oldServiceClassName;
	}

	/**
	 * @return the page1
	 */
	public String getPage1() {
		return page1;
	}

	/**
	 * @param page1 the page1 to set
	 */
	public void setPage1(String page1) {
		this.page1 = page1;
	}

    }
