/*
 * Created on Jul 18, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.dto;




import java.io.Serializable;


 /** 
	* @author Code Generator 
	* Created On Mon Jul 16 18:55:39 IST 2007 
	* Auto Generated Data Trasnfer Object class for table V_DISTRIBUTOR_DEALER_MAPPING 
 
 **/ 
public class VDistributorSubscriberMappingDTO implements Serializable {

	private String circleId;
	
	private String completeSIM;
	
	
    private String distributorId;

    private String subscriberId;
    
    private String search;
    
    private String dealerName;
    
    private String subscriberMSISDN;
    
    private String subscriberIMSI;
    
    private String subscriberSIM;
    
	
    private String listIndex;
    
    private String errorStatus;
    
    private String distributorName;
    
    private String rownum;
    private String circleCode;
    
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
	 * @return Returns the completeSIM.
	 */
	public String getCompleteSIM() {
		return completeSIM;
	}
	/**
	 * @param completeSIM The completeSIM to set.
	 */
	public void setCompleteSIM(String completeSIM) {
		this.completeSIM = completeSIM;
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
    /** Creates a dto for the V_DISTRIBUTOR_DEALER_MAPPING table */
    public VDistributorSubscriberMappingDTO() {
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
	 * @return Returns the errorStatus.
	 */
	public String getErrorStatus() {
		return errorStatus;
	}
	/**
	 * @param errorStatus The errorStatus to set.
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
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
	/**
	 * @return Returns the rownum.
	 */
	public String getRownum() {
		return rownum;
	}
	/**
	 * @param rownum The rownum to set.
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
}
