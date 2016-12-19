package com.ibm.hbo.dto;

import java.io.Serializable;


 /** 
	* @author Avadesh 
	* Created On Tue Sep 25 17:26:38 IST 2007 
	* Data Trasnfer Object class for table ARC_ZONE_MSTR 
 
 **/ 
public class HBOZoneMstr implements Serializable {


    private String zoneId;

    private String zoneName;

    private java.sql.Timestamp createdDt;

    private String createdBy;

    private java.sql.Timestamp updatedDt;

    private String updatedBy;

    private String circleId;

    private String status;

    /** Creates a dto for the ARC_ZONE_MSTR table */
    public HBOZoneMstr() {
    }


 /** 
	* Get zoneId associated with this object.
	* @return zoneId
 **/ 

    public String getZoneId() {
        return zoneId;
    }

 /** 
	* Set zoneId associated with this object.
	* @param zoneId The zoneId value to set
 **/ 

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

 /** 
	* Get zoneName associated with this object.
	* @return zoneName
 **/ 

    public String getZoneName() {
        return zoneName;
    }

 /** 
	* Set zoneName associated with this object.
	* @param zoneName The zoneName value to set
 **/ 

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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

    public void setCircleId(String circleId) {
        this.circleId = circleId;
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

}
