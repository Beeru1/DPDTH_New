/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;

/**
 * Form Bean class for table VR_CIRCLE_MASTER
 * 
 * @author Paras
 *  
 */
public class CircleFormBean extends ActionForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9169076242533742539L;

	private String circleId;

    private String circleName;

    private String regionId;

    private String regionName;

    private String status;

    private String rate;

    private String createdBy;

    private String updatedBy;

    private String updatedDt;

    private String createdDt;

    private String description;

    private String openingBalance;

    private String operatingBalance;

    private String availableBalance;

    private String threshold;

    private ArrayList displayDetails;

    private String methodName;
    
    private String isCircleAdmin;
    
    private String disabledRegion;
    
    private String startDate;
    
    private String endDate;
    
    private String circleCode;
    
    private String editStatus;
    
    private String circleStatus;
    
    private String flag;
    
    private String page;
    
    
    private String phone;
    private String terms1;
    private String terms2;
    private String terms3;
    private String terms4;
    private String remarks;
    private String saletax;
    private String saletaxdate;
    private String service;
    private String compName;
    private String tin;
    private String tinDate;
    private String address1;
    private String address2;
    private String fax;
    private String cst;
    private String cstdate; 
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.circleId = null;
		this.circleName = null;
		this.regionId = null;
		this.regionName = null;
		this.status = null;
		this.rate = null;
		this.createdBy = null;
		this.updatedBy = null;
		this.updatedDt = null;
		this.createdDt = null;
		this.description = null;
		this.openingBalance = null;
		this.operatingBalance = null;
		this.availableBalance = null;
		this.threshold = null;
		this.displayDetails = null;
		this.methodName = null;
		this.isCircleAdmin = null;
		this.disabledRegion = null;
		this.startDate = null;
		this.endDate = null;
		this.circleCode = null;
		this.editStatus=null;
		
		this.phone=null;
		this.remarks=null;
		this.terms1=null;
		this.terms2=null;
		this.terms3=null;
		this.terms4=null;
		
		this.saletax=null;
		this.saletaxdate="";
		this.service=null;
		this.compName=null;
		this.tin=null;
		this.tinDate="";
		this.address1=null;
		this.address2=null;
		this.fax=null;
		this.cst=null;
		this.cstdate=""; 
		
		

	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}


	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

    /**
     * @return Returns the methodName.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName
     *            The methodName to set.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return Returns the displayDetails.
     */
    public ArrayList getDisplayDetails() {
        return displayDetails;
    }

    /**
     * @param displayDetails
     *            The displayDetails to set.
     */
    public void setDisplayDetails(ArrayList displayDetails) {
        this.displayDetails = displayDetails;
    }

    /**
     * @return Returns the regionName.
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * @param regionName
     *            The regionName to set.
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /** Creates a dto for the VR_CIRCLE_MASTER table */
    public CircleFormBean() {
    }

    /**
     * Get circleId associated with this object.
     * 
     * @return circleId
     */

    public String getCircleId() {
        return circleId;
    }

    /**
     * Set circleId associated with this object.
     * 
     * @param circleId
     *            The circleId value to set
     */

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    /**
     * Get circleName associated with this object.
     * 
     * @return circleName
     */

    public String getCircleName() {
        return circleName;
    }

    /**
     * Set circleName associated with this object.
     * 
     * @param circleName
     *            The circleName value to set
     */

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    /**
     * Get status associated with this object.
     * 
     * @return status
     */

    public String getStatus() {
        return status;
    }

    /**
     * Set status associated with this object.
     * 
     * @param status
     *            The status value to set
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get rate associated with this object.
     * 
     * @return rate
     */

    public String getRate() {
        return rate;
    }

    /**
     * Set rate associated with this object.
     * 
     * @param rate
     *            The rate value to set
     */

    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * Get createdBy associated with this object.
     * 
     * @return createdBy
     */

    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdBy associated with this object.
     * 
     * @param createdBy
     *            The createdBy value to set
     */

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get updatedBy associated with this object.
     * 
     * @return updatedBy
     */

    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedBy associated with this object.
     * 
     * @param updatedBy
     *            The updatedBy value to set
     */

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get updatedDt associated with this object.
     * 
     * @return updatedDt
     */

    public String getUpdatedDt() {
        return updatedDt;
    }

    /**
     * Set updatedDt associated with this object.
     * 
     * @param updatedDt
     *            The updatedDt value to set
     */

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * Get createdDt associated with this object.
     * 
     * @return createdDt
     */

    public String getCreatedDt() {
        return createdDt;
    }

    /**
     * Set createdDt associated with this object.
     * 
     * @param createdDt
     *            The createdDt value to set
     */

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * Get description associated with this object.
     * 
     * @return description
     */

    public String getDescription() {
        return description;
    }

    /**
     * Set description associated with this object.
     * 
     * @param description
     *            The description value to set
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get regionId associated with this object.
     * 
     * @return regionId
     */

    public String getRegionId() {
        return regionId;
    }

    /**
     * Set regionId associated with this object.
     * 
     * @param regionId
     *            The regionId value to set
     */

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     * @return Returns the threshold.
     */
    public String getThreshold() {
        return threshold;
    }

    /**
     * @param threshold
     *            The threshold to set.
     */
    public void setThreshold(String threshold) {
    	if(threshold.trim().equals(""))
    		this.threshold = threshold;
    	else
        this.threshold = Utility.formatAmount(Double.parseDouble(threshold));
    }

    /**
     * @return Returns the availableBalance.
     */
    public String getAvailableBalance() {
        return availableBalance;
    }

    /**
     * @param availableBalance
     *            The availableBalance to set.
     */
    public void setAvailableBalance(String availableBalance) {
    	if(availableBalance.trim().equals(""))
    		this.availableBalance = availableBalance;
    	else
    		this.availableBalance = Utility.formatAmount(Double.parseDouble(availableBalance));
    }

    /**
     * @return Returns the openingBalance.
     */
    public String getOpeningBalance() {
        return openingBalance;
    }

    /**
     * @param openingBalance
     *            The openingBalance to set.
     */
    public void setOpeningBalance(String openingBalance) {
    	if(openingBalance.trim().equals(""))
    		this.openingBalance = openingBalance;
    	else
        this.openingBalance = Utility.formatAmount(Double.parseDouble(openingBalance));
    }

    /**
     * @return Returns the operatingBalance.
     */
    public String getOperatingBalance() {
        return operatingBalance;
    }

    /**
     * @param operatingBalance
     *            The operatingBalance to set.
     */
    public void setOperatingBalance(String operatingBalance) {
    	if(operatingBalance.trim().equals(""))
    		this.operatingBalance = operatingBalance;
    	else
        this.operatingBalance =Utility.formatAmount(Double.parseDouble(operatingBalance));
    }

	/**
	 * @return the disabledRegion
	 */
	public String getDisabledRegion() {
		return disabledRegion;
	}

	/**
	 * @param disabledRegion the disabledRegion to set
	 */
	public void setDisabledRegion(String disabledRegion) {
		this.disabledRegion = disabledRegion;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the isCircleAdmin
	 */
	public String getIsCircleAdmin() {
		return isCircleAdmin;
	}

	/**
	 * @param isCircleAdmin the isCircleAdmin to set
	 */
	public void setIsCircleAdmin(String isCircleAdmin) {
		this.isCircleAdmin = isCircleAdmin;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	 * @return the editStatus
	 */
	public String getEditStatus() {
		return editStatus;
	}


	/**
	 * @param editStatus the editStatus to set
	 */
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}


	public String getCircleStatus() {
		return circleStatus;
	}


	public void setCircleStatus(String circleStatus) {
		this.circleStatus = circleStatus;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTerms1() {
		return terms1;
	}

	public void setTerms1(String terms1) {
		this.terms1 = terms1;
	}

	public String getTerms2() {
		return terms2;
	}

	public void setTerms2(String terms2) {
		this.terms2 = terms2;
	}

	public String getTerms3() {
		return terms3;
	}

	public void setTerms3(String terms3) {
		this.terms3 = terms3;
	}

	public String getTerms4() {
		return terms4;
	}

	public void setTerms4(String terms4) {
		this.terms4 = terms4;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getCstdate() {
		return cstdate;
	}

	public void setCstdate(String cstdate) {
		this.cstdate = cstdate;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSaletax() {
		return saletax;
	}

	public void setSaletax(String saletax) {
		this.saletax = saletax;
	}

	public String getSaletaxdate() {
		return saletaxdate;
	}

	public void setSaletaxdate(String saletaxdate) {
		this.saletaxdate = saletaxdate;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getTinDate() {
		return tinDate;
	}

	public void setTinDate(String tinDate) {
		this.tinDate = tinDate;
	}
	
}