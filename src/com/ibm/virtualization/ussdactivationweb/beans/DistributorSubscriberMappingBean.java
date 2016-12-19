/*
 * Created on Jul 18, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


 /** 
	* @author Code Generator 
	* Created On Mon Jul 16 18:55:39 IST 2007 
	* Auto Generated Form Bean class for table V_DISTRIBUTOR_DEALER_MAPPING 
 
 **/ 
public class DistributorSubscriberMappingBean extends ActionForm {

    private String circleId="";
	
	
	private List distList;
	
	
    private String distributorId;

    private String subscriberId;
    
    private String search;
    
    private String dealerName;
    
    private String smsisdn;
    
    private String simsi;
    
    private String ssim;
    
    private String flag;
    
    private String subscriberMSISDN;
    
    private String subscriberIMSI;
    
    private String subscriberSIM;
    
    private ArrayList subscriberItrator;
    
    private String listIndex;
    
    private String methodName;
   
    private String distributorName;
    
    private String userRole;
    
    private String circleCode;
   
    
    
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
    /**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId The circleId to set.
	 */
	
	
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
    
    /**
	 * @return Returns the distList.
	 */
	public List getDistList() {
		return distList;
	}
	/**
	 * @param distList The distList to set.
	 */
	public void setDistList(List distList) {
		this.distList = distList;
	}
	/**
	 * @return Returns the distributorName.
	 */
	public String getDistributorName() {
		return distributorName;
	}
	/**
	 * @param distributorName The distributorName to set.
	 */
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	/**
	 * @return Returns the simsi.
	 */
	public String getSimsi() {
		return simsi;
	}
	/**
	 * @param simsi The simsi to set.
	 */
	public void setSimsi(String simsi) {
		this.simsi = simsi;
	}
	/**
	 * @return Returns the smsisdn.
	 */
	public String getSmsisdn() {
		return smsisdn;
	}
	/**
	 * @param smsisdn The smsisdn to set.
	 */
	public void setSmsisdn(String smsisdn) {
		this.smsisdn = smsisdn;
	}
	/**
	 * @return Returns the ssim.
	 */
	public String getSsim() {
		return ssim;
	}
	/**
	 * @param ssim The ssim to set.
	 */
	public void setSsim(String ssim) {
		this.ssim = ssim;
	}
    

    /** Creates a dto for the V_DISTRIBUTOR_DEALER_MAPPING table */
    public DistributorSubscriberMappingBean() {
    }


    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
        return errors;
    }
 /** 
	* Get distributorId associated with this object.
	* @return distributorId
 **/ 

    public String getDistributorId() {
        return distributorId;
    }

 /** 
	* Set distributorId associated with this object.
	* @param distributorId The distributorId value to set
 **/ 

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

 
	/**
	 * @return Returns the subscriberId.
	 */
	public String getSubscriberId() {
		return subscriberId;
	}
	/**
	 * @param subscriberId The subscriberId to set.
	 */
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	/**
	 * @return Returns the search.
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @param search The search to set.
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	/**
	 * @return Returns the dealerName.
	 */
	public String getDealerName() {
		return dealerName;
	}
	/**
	 * @param dealerName The dealerName to set.
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	/**
	 * @return Returns the flag.
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag The flag to set.
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
    /**
     * @return Returns the listIndex.
     */
    public String getListIndex() {
        return listIndex;
    }
    /**
     * @param listIndex The listIndex to set.
     */
    public void setListIndex(String listIndex) {
        this.listIndex = listIndex;
    }
	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return Returns the subscriberIMSI.
	 */
	public String getSubscriberIMSI() {
		return subscriberIMSI;
	}
	/**
	 * @param subscriberIMSI The subscriberIMSI to set.
	 */
	public void setSubscriberIMSI(String subscriberIMSI) {
		this.subscriberIMSI = subscriberIMSI;
	}
	/**
	 * @return Returns the subscriberItrator.
	 */
	public ArrayList getSubscriberItrator() {
		return subscriberItrator;
	}
	/**
	 * @param subscriberItrator The subscriberItrator to set.
	 */
	public void setSubscriberItrator(ArrayList subscriberItrator) {
		this.subscriberItrator = subscriberItrator;
	}
	/**
	 * @return Returns the subscriberMSISDN.
	 */
	public String getSubscriberMSISDN() {
		return subscriberMSISDN;
	}
	/**
	 * @param subscriberMSISDN The subscriberMSISDN to set.
	 */
	public void setSubscriberMSISDN(String subscriberMSISDN) {
		this.subscriberMSISDN = subscriberMSISDN;
	}
	/**
	 * @return Returns the subscriberSIM.
	 */
	public String getSubscriberSIM() {
		return subscriberSIM;
	}
	/**
	 * @param subscriberSIM The subscriberSIM to set.
	 */
	public void setSubscriberSIM(String subscriberSIM) {
		this.subscriberSIM = subscriberSIM;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
}
