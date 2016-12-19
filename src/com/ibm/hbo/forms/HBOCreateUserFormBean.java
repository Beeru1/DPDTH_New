package com.ibm.hbo.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


 /** 
	* @author Avadesh 
	* Created On Tue Sep 25 17:26:13 IST 2007 
	* Form Bean class for table ARC_USER_MSTR 
 
 **/ 
public class HBOCreateUserFormBean extends ActionForm {


    private String userId;

    private String userLoginId;

    private String userFname;

    private String userMname;

    private String userLname;

    private String userMobileNumber;

    private String userEmailid;

    private String userPassword;

    private String userPsswrdExpryDt;
    
	private String userAddress;
	
	private String userCity;
	
	private String userState;
	
	private String userStartDate;
    

    private String createdDt;

    private String createdBy;

    private String updatedDt;

    private String updatedBy;

    private String connectId;

    private String circleId;

    private String actorId;
    
	private String loginActorId;

    private String loginAttempted;

    private String lastLoginTime;

    private String lastLoginIp;

    private String status;
    
    private ArrayList moduleList;
    
	private ArrayList userType;
	
	private ArrayList circleDetails;
	private ArrayList warehouseDetails=null;
	private ArrayList arrStateList=null;

	private String warehouseId="";
	private String warehouseName="";
	private String warehouse="";
	
	private ArrayList connectDetails;
		
	private String userConfirmPassword;

    /** Creates a dto for the ARC_USER_MSTR table */
    public HBOCreateUserFormBean() {
    }


    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
        return errors;
    }
 /** 
	* Get userId associated with this object.
	* @return userId
 **/ 

    public String getUserId() {
        return userId;
    }

 /** 
	* Set userId associated with this object.
	* @param userId The userId value to set
 **/ 

    public void setUserId(String userId) {
        this.userId = userId;
    }

 /** 
	* Get userLoginId associated with this object.
	* @return userLoginId
 **/ 

    public String getUserLoginId() {
        return userLoginId;
    }

 /** 
	* Set userLoginId associated with this object.
	* @param userLoginId The userLoginId value to set
 **/ 

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

 /** 
	* Get userFname associated with this object.
	* @return userFname
 **/ 

    public String getUserFname() {
        return userFname;
    }

 /** 
	* Set userFname associated with this object.
	* @param userFname The userFname value to set
 **/ 

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

 /** 
	* Get userMname associated with this object.
	* @return userMname
 **/ 

    public String getUserMname() {
        return userMname;
    }

 /** 
	* Set userMname associated with this object.
	* @param userMname The userMname value to set
 **/ 

    public void setUserMname(String userMname) {
        this.userMname = userMname;
    }

 /** 
	* Get userLname associated with this object.
	* @return userLname
 **/ 

    public String getUserLname() {
        return userLname;
    }

 /** 
	* Set userLname associated with this object.
	* @param userLname The userLname value to set
 **/ 

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

 /** 
	* Get userMobileNumber associated with this object.
	* @return userMobileNumber
 **/ 

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

 /** 
	* Set userMobileNumber associated with this object.
	* @param userMobileNumber The userMobileNumber value to set
 **/ 

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

 /** 
	* Get userEmailid associated with this object.
	* @return userEmailid
 **/ 

    public String getUserEmailid() {
        return userEmailid;
    }

 /** 
	* Set userEmailid associated with this object.
	* @param userEmailid The userEmailid value to set
 **/ 

    public void setUserEmailid(String userEmailid) {
        this.userEmailid = userEmailid;
    }

 /** 
	* Get userPassword associated with this object.
	* @return userPassword
 **/ 

    public String getUserPassword() {
        return userPassword;
    }

 /** 
	* Set userPassword associated with this object.
	* @param userPassword The userPassword value to set
 **/ 

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

 /** 
	* Get userPsswrdExpryDt associated with this object.
	* @return userPsswrdExpryDt
 **/ 

    public String getUserPsswrdExpryDt() {
        return userPsswrdExpryDt;
    }

 /** 
	* Set userPsswrdExpryDt associated with this object.
	* @param userPsswrdExpryDt The userPsswrdExpryDt value to set
 **/ 

    public void setUserPsswrdExpryDt(String userPsswrdExpryDt) {
        this.userPsswrdExpryDt = userPsswrdExpryDt;
    }

 /** 
	* Get createdDt associated with this object.
	* @return createdDt
 **/ 

    public String getCreatedDt() {
        return createdDt;
    }

 /** 
	* Set createdDt associated with this object.
	* @param createdDt The createdDt value to set
 **/ 

    public void setCreatedDt(String createdDt) {
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

    public String getUpdatedDt() {
        return updatedDt;
    }

 /** 
	* Set updatedDt associated with this object.
	* @param updatedDt The updatedDt value to set
 **/ 

    public void setUpdatedDt(String updatedDt) {
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
	* Get connectId associated with this object.
	* @return connectId
 **/ 

    public String getConnectId() {
        return connectId;
    }

 /** 
	* Set connectId associated with this object.
	* @param connectId The connectId value to set
 **/ 

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

 /** 
	* Get zonalId associated with this object.
	* @return zonalId
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
	* Get caolActorId associated with this object.
	* @return caolActorId
 **/ 

    public String getActorId() {
        return actorId;
    }

 /** 
	* Set caolActorId associated with this object.
	* @param caolActorId The caolActorId value to set
 **/ 

    public void setActorId(String caolActorId) {
        this.actorId = caolActorId;
    }

 /** 
	* Get loginAttempted associated with this object.
	* @return loginAttempted
 **/ 

    public String getLoginAttempted() {
        return loginAttempted;
    }

 /** 
	* Set loginAttempted associated with this object.
	* @param loginAttempted The loginAttempted value to set
 **/ 

    public void setLoginAttempted(String loginAttempted) {
        this.loginAttempted = loginAttempted;
    }

 /** 
	* Get lastLoginTime associated with this object.
	* @return lastLoginTime
 **/ 

    public String getLastLoginTime() {
        return lastLoginTime;
    }

 /** 
	* Set lastLoginTime associated with this object.
	* @param lastLoginTime The lastLoginTime value to set
 **/ 

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

 /** 
	* Get lastLoginIp associated with this object.
	* @return lastLoginIp
 **/ 

    public String getLastLoginIp() {
        return lastLoginIp;
    }

 /** 
	* Set lastLoginIp associated with this object.
	* @param lastLoginIp The lastLoginIp value to set
 **/ 

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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
	 * @return
	 */
	public ArrayList getModuleList() {
		return moduleList;
	}

	/**
	 * @param list
	 */
	public void setModuleList(ArrayList list) {
		moduleList = list;
	}

	/**
	 * @return
	 */
	public ArrayList getCircleDetails() {
		return circleDetails;
	}

	/**
	 * @return
	 */
	public ArrayList getConnectDetails() {
		return connectDetails;
	}

	/**
	 * @return
	 */
	public ArrayList getUserType() {
		return userType;
	}

	/**
	 * @return
	 */
	

	/**
	 * @param list
	 */
	public void setCircleDetails(ArrayList list) {
		circleDetails = list;
	}

	/**
	 * @param list
	 */
	public void setConnectDetails(ArrayList list) {
		connectDetails = list;
	}

	/**
	 * @param list
	 */
	public void setUserType(ArrayList list) {
		userType = list;
	}

	
	

	/**
	 * @return
	 */
	public String getUserConfirmPassword() {
		return userConfirmPassword;
	}

	/**
	 * @param string
	 */
	public void setUserConfirmPassword(String string) {
		userConfirmPassword = string;
	}

	/**
	 * @return
	 */
	public String getLoginActorId() {
		return loginActorId;
	}

	/**
	 * @param string
	 */
	public void setLoginActorId(String string) {
		loginActorId = string;
	}

	/**
	 * @return
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @return
	 */
	public String getUserCity() {
		return userCity;
	}

	/**
	 * @return
	 */
	public String getUserStartDate() {
		return userStartDate;
	}

	/**
	 * @return
	 */
	public String getUserState() {
		return userState;
	}

	/**
	 * @param string
	 */
	public void setUserAddress(String string) {
		userAddress = string;
	}

	/**
	 * @param string
	 */
	public void setUserCity(String string) {
		userCity = string;
	}

	/**
	 * @param string
	 */
	public void setUserStartDate(String string) {
		userStartDate = string;
	}

	/**
	 * @param string
	 */
	public void setUserState(String string) {
		userState = string;
	}

	/**
	 * @return
	 */
	public ArrayList getWarehouseDetails() {
		return warehouseDetails;
	}

	/**
	 * @param list
	 */
	public void setWarehouseDetails(ArrayList list) {
		warehouseDetails = list;
	}

	/**
	 * @return
	 */
	public String getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @return
	 */
	public String getWarehouseName() {
		return warehouseName;
	}

	/**
	 * @param string
	 */
	public void setWarehouseId(String string) {
		warehouseId = string;
	}

	/**
	 * @param string
	 */
	public void setWarehouseName(String string) {
		warehouseName = string;
	}

	/**
	 * @return
	 */
	public String getWarehouse() {
		return warehouse;
	}

	/**
	 * @param string
	 */
	public void setWarehouse(String string) {
		warehouse = string;
	}

	/**
	 * @return
	 */
	public ArrayList getArrStateList() {
		return arrStateList;
	}

	/**
	 * @param list
	 */
	public void setArrStateList(ArrayList list) {
		arrStateList = list;
	}

}
