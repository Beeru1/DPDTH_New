
/**************************************************************************
 * File     : SubscriberAction.java
 * Author   : Ashad
 * Created  : Sep 10, 2008
 * Modified : Sep 10, 2008
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


 /** 
   * @author Ashad
   */ 
public class VSubscriberMstrFormBean extends ActionForm {
	
	public static final long serialVersionUID = 1L;

	private static long serialId = 898754L;
	private String id;
	private String circleId;
	private String imsi;
	private String msisdn;
	private String sim;
	private String status;
	private java.sql.Timestamp createdDt;
	private String createdBy;
	private java.sql.Timestamp updatedDt;
	private String updatedBy;
	private String compSim;
	private String initCrclVal = null;
	private String userRole;
	private String oldCompSim;
	private String oldSim;
	private String oldImsi;
	private String oldMsisdn;    
	private String oldStatus;
	private String oldCircleId;
	private ArrayList serviceClassList;
	private String serviceClassName;
	private String serviceClassId;
	private String methodName;
	private String changedCircleId;
	private String oldServiceClassId;
	private int jspPageLoadControl = -1;
    /**
	 * It returns the String representation of the VSubscriberMstrFormBean. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer(500).append(" \n VSubscriberMstrFormBean - userRole: ")
				.append(userRole).append(",  imsi: ").append(imsi).append(
						",  status: ").append(status).append(",  createdBy: ")
				.append(createdBy).append(",  updatedBy: ").append(updatedBy)
				.append(",  updatedDt: ").append(updatedDt).append(
						",  oldImsi: ").append(oldImsi).append(",  msisdn: ")
				.append(msisdn).append(",  circleCode: ").append(circleCode)
				.append(",  sim: ").append(sim).append(
						",  oldCompSim: ").append(oldCompSim).append(
						",  serviceClassName: ").append(serviceClassName).append(
						",  oldSim: ").append(oldSim).append(
						",  oldMsisdn: ").append(oldMsisdn).toString();
	}

    
    private String circleCode;
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the serviceClassId
	 */
	public String getServiceClassId() {
		return serviceClassId;
	}
	/**
	 * @param serviceClassId the serviceClassId to set
	 */
	public void setServiceClassId(String serviceClassId) {
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
	 * @return Returns the old_circleId.
	 */
	public String getOldCircleId() {
		return oldCircleId;
	}
	/**
	 * @param old_circleId The old_circleId to set.
	 */
	public void setOldCircleId(String strOldCircleId) {
		this.oldCircleId = strOldCircleId;
	}
	/**
	 * @return Returns the old_status.
	 */
	public String getOldStatus() {
		return oldStatus;
	}
	/**
	 * @param old_status The old_status to set.
	 */
	public void setOldStatus(String strOldStatus) {
		this.oldStatus = strOldStatus;
	}
	/**
	 * @return Returns the old_comp_sim.
	 */
	public String getOldCompSim() {
		return oldCompSim;
	}
	/**
	 * @param old_comp_sim The old_comp_sim to set.
	 */
	public void setOldCompSim(String strOldCompSim) {
		this.oldCompSim = strOldCompSim;
	}
	/**
	 * @return Returns the old_imsi.
	 */
	public String getOldImsi() {
		return oldImsi;
	}
	/**
	 * @param old_imsi The old_imsi to set.
	 */
	public void setOldImsi(String strOldImsi) {
		this.oldImsi = strOldImsi;
	}
	/**
	 * @return Returns the old_msisdn.
	 */
	public String getOldMsisdn() {
		return oldMsisdn;
	}
	/**
	 * @param old_msisdn The old_msisdn to set.
	 */
	public void setOldMsisdn(String strOldMsisdn) {
		this.oldMsisdn = strOldMsisdn;
	}
	/**
	 * @return Returns the old_sim.
	 */
	public String getOldSim() {
		return oldSim;
	}
	/**
	 * @param old_sim The old_sim to set.
	 */
	public void setOldSim(String strOldSim) {
		this.oldSim = strOldSim;
	}
	/**
	 * @return Returns the initialCircleValue.
	 */
	public String getInitialCircleValue() {
		return initCrclVal;
	}
	/**
	 * @param initialCircleValue The initialCircleValue to set.
	 */
	public void setInitialCircleValue(String initCrclVal) {
		this.initCrclVal = initCrclVal;
	}
	/**
	 * @return Returns the comp_sim.
	 */
	public String getCompSim() {
		return compSim;
	}
	/**
	 * @param comp_sim The comp_sim to set.
	 */
	public void setCompSim(String comp_sim) {
		this.compSim = comp_sim;
	}
    /** Creates a dto for the V_SUBSCRIBER_MSTR table */
    
    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
        return errors;
    }
 /** 
	* Get circleId associated with this object.
	* @return circleId
 **/ 

    public String getCircleId() {
        return circleId;
    }

 /** 
	* Set circleId associated with this object.
	* @param circleId The circleId value to set
 **/ 

    public void setCircleId(String strCircleId) {
        this.circleId = strCircleId;
    }

 /** 
	* Get imsi associated with this object.
	* @return imsi
 **/ 

    public String getImsi() {
        return imsi;
    }

 /** 
	* Set imsi associated with this object.
	* @param imsi The imsi value to set
 **/ 

    public void setImsi(String strImsi) {
        this.imsi = strImsi;
    }

 /** 
	* Get msisdn associated with this object.
	* @return msisdn
 **/ 

    public String getMsisdn() {
        return msisdn;
    }

 /** 
	* Set msisdn associated with this object.
	* @param msisdn The msisdn value to set
 **/ 

    public void setMsisdn(String strMsisdn) {
        this.msisdn = strMsisdn;
    }

 /** 
	* Get sim associated with this object.
	* @return sim
 **/ 

    public String getSim() {
        return sim;
    }

 /** 
	* Set sim associated with this object.
	* @param sim The sim value to set
 **/ 

    public void setSim(String sim) {
        this.sim = sim;
    }

 /** 
	* Get status associated with this object.
	* @return status
 **/ 

    public String getStatus() {
        return status;
    }

 /** 
	* Set status associated with this object.
	* @param status The status value to set
 **/ 

    public void setStatus(String status) {
        this.status = status;
    }

 /** 
	* Get createdDt associated with this object.
	* @return createdDt
 **/ 

    public java.sql.Timestamp getCreatedDt() {
        return createdDt;
    }

 /** 
	* Set createdDt associated with this object.
	* @param createdDt The createdDt value to set
 **/ 

    public void setCreatedDt(java.sql.Timestamp createdDt) {
        this.createdDt = createdDt;
    }

 /** 
	* Get createdBy associated with this object.
	* @return createdBy
 **/ 

    public String getCreatedBy() {
        return createdBy;
    }

 /** 
	* Set createdBy associated with this object.
	* @param createdBy The createdBy value to set
 **/ 

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

 /** 
	* Get updatedDt associated with this object.
	* @return updatedDt
 **/ 

    public java.sql.Timestamp getUpdatedDt() {
        return updatedDt;
    }

 /** 
	* Set updatedDt associated with this object.
	* @param updatedDt The updatedDt value to set
 **/ 

    public void setUpdatedDt(java.sql.Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

 /** 
	* Get updatedBy associated with this object.
	* @return updatedBy
 **/ 

    public String getUpdatedBy() {
        return updatedBy;
    }

 /** 
	* Set updatedBy associated with this object.
	* @param updatedBy The updatedBy value to set
 **/ 

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


	/**
	 * @return Returns the userRole.
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole The userRole to set.
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String strId) {
		this.id = strId;
	}
	public static long getSerialId() {
		return serialId;
	}
	public static void setSerialId(long serialId) {
		VSubscriberMstrFormBean.serialId = serialId;
	}
	/**
	 * @return the serviceClassList
	 */
	public ArrayList getServiceClassList() {
		return serviceClassList;
	}
	/**
	 * @param serviceClassList the serviceClassList to set
	 */
	public void setServiceClassList(ArrayList serviceClassList) {
		this.serviceClassList = serviceClassList;
	}
	/**
	 * @return the changedCircleId
	 */
	public String getChangedCircleId() {
		return changedCircleId;
	}
	/**
	 * @param changedCircleId the changedCircleId to set
	 */
	public void setChangedCircleId(String changedCircleId) {
		this.changedCircleId = changedCircleId;
	}
	/**
	 * @return the oldServiceClassId
	 */
	public String getOldServiceClassId() {
		return oldServiceClassId;
	}
	/**
	 * @param oldServiceClassId the oldServiceClassId to set
	 */
	public void setOldServiceClassId(String oldServiceClassId) {
		this.oldServiceClassId = oldServiceClassId;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	public int getJspPageLoadControl() {
		return jspPageLoadControl;
	}
	public void setJspPageLoadControl(int jspPageLoadControl) {
		this.jspPageLoadControl = jspPageLoadControl;
	}
	
	
}
