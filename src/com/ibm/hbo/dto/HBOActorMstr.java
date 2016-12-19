package com.ibm.hbo.dto;

import java.util.*;
import java.io.Serializable;


 /** 
	* @author Avadesh 
	* Created On Tue Oct 23 15:07:04 IST 2007 
	* Data Trasnfer Object class for table ARC_ACTOR_MSTR 
 
 **/ 
public class HBOActorMstr implements Serializable {


    private String actorId;

    private String actorName;

    private java.sql.Timestamp createdDt;

    private String createdBy;

    /** Creates a dto for the ARC_ACTOR_MSTR table */
    public HBOActorMstr() {
    }


 /** 
	* Get actorId associated with this object.
	* @return actorId
 **/ 

    public String getActorId() {
        return actorId;
    }

 /** 
	* Set actorId associated with this object.
	* @param actorId The actorId value to set
 **/ 

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

 /** 
	* Get actorName associated with this object.
	* @return actorName
 **/ 

    public String getActorName() {
        return actorName;
    }

 /** 
	* Set actorName associated with this object.
	* @param actorName The actorName value to set
 **/ 

    public void setActorName(String actorName) {
        this.actorName = actorName;
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

}
