package com.ibm.virtualization.recharge.beans;



import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Utility;


 /** 
	* @author Code Generator 
	* Created On Wed Aug 08 10:59:49 IST 2007 
	* Auto Generated Form Bean class for table REC_TRANSACTION 
 
 **/ 
public class MoneyTransactionFormBean extends ActionForm {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1212467815268303626L;

	private String transactionId;

    private String accountId;
    
    private String accountCode;

    private String transactionAmt;

    private String creditedAmt;

    private String transactionDate;

    private String transactionType;

    private String chqccNumber;

    private String chequeDate;

    private String ccvalidDate;

    private String createdBy;

    private String updatedBy;

    private String createdTime;

    private String updatedTime;
    
    private String methodName;
    
    private String bankName;
    
    private String channelType;
    
    private String transactionListType;
    
    private ArrayList transactionList;
    
    private String reviewStatus;
    
    private String reviewComment;
    
    private String rate ;
    
    
    private String createdByName;
    
    

	/**
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}
	/**
	 * @param accountCode The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	/**
	 * @return Returns the rate.
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}
	/**
	 * @return Returns the reviewComment.
	 */
	public String getReviewComment() {
		return reviewComment;
	}
	/**
	 * @param reviewComment The reviewComment to set.
	 */
	public void setReviewComment(String ReviewComment) {
		reviewComment =ReviewComment;
	}
	/**
	 * @return Returns the reviewStatus.
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}
	/**
	 * @param reviewStatus The reviewStatus to set.
	 */
	public void setReviewStatus(String ReviewStatus) {
		reviewStatus = ReviewStatus;
	}
	/**
	 * @return Returns the transactionList.
	 */
	public ArrayList getTransactionList() {
		return transactionList;
	}
	/**
	 * @param transactionList The transactionList to set.
	 */
	public void setTransactionList(ArrayList transactionList) {
		this.transactionList = transactionList;
	}
	/**
	 * @return Returns the transactionListType.
	 */
	public String getTransactionListType() {
		return transactionListType;
	}
	/**
	 * @param transactionListType The transactionListType to set.
	 */
	public void setTransactionListType(String transactionListType) {
		this.transactionListType = transactionListType;
	}
	/**
	 * @return Returns the channelType.
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType The channelType to set.
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	/**
	 * @return Returns the bankName.
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName The bankName to set.
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
    /** Creates a dto for the REC_TRANSACTION table */
    public MoneyTransactionFormBean() {
    }


  
 /** 
	* Get transactionId associated with this object.
	* @return transactionId
 **/ 

    public String getTransactionId() {
        return transactionId;
    }

 /** 
	* Set transactionId associated with this object.
	* @param transactionId The transactionId value to set
 **/ 

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

 /** 
	* Get accountId associated with this object.
	* @return accountId
 **/ 

    public String getAccountId() {
        return accountId;
    }

 /** 
	* Set accountId associated with this object.
	* @param accountId The accountId value to set
 **/ 

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

 /** 
	* Get transactionAmt associated with this object.
	* @return transactionAmt
 **/ 

    public String getTransactionAmt() {
        return transactionAmt;
    }

 /** 
	* Set transactionAmt associated with this object.
	* @param transactionAmt The transactionAmt value to set
 **/ 

    public void setTransactionAmt(String transactionAmt) {
        this.transactionAmt = Utility.formatAmount(Double.parseDouble(transactionAmt));
    }

 /** 
	* Get creditedAmt associated with this object.
	* @return creditedAmt
 **/ 

    public String getCreditedAmt() {
        return creditedAmt;
    }

 /** 
	* Set creditedAmt associated with this object.
	* @param creditedAmt The creditedAmt value to set
 **/ 

    public void setCreditedAmt(String creditedAmt) {
        this.creditedAmt = Utility.formatAmount(Double.parseDouble(creditedAmt));
    }

 /** 
	* Get transactionDate associated with this object.
	* @return transactionDate
 **/ 

    public String getTransactionDate() {
        return transactionDate;
    }

 /** 
	* Set transactionDate associated with this object.
	* @param transactionDate The transactionDate value to set
 **/ 

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

 /** 
	* Get transactionType associated with this object.
	* @return transactionType
 **/ 

    public String getTransactionType() {
        return transactionType;
    }

 /** 
	* Set transactionType associated with this object.
	* @param transactionType The transactionType value to set
 **/ 

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

 /** 
	* Get chqccNumber associated with this object.
	* @return chqccNumber
 **/ 

    public String getChqccNumber() {
        return chqccNumber;
    }

 /** 
	* Set chqccNumber associated with this object.
	* @param chqccNumber The chqccNumber value to set
 **/ 

    public void setChqccNumber(String chqccNumber) {
        this.chqccNumber = chqccNumber;
    }

 /** 
	* Get chequeDate associated with this object.
	* @return chequeDate
 **/ 

    public String getChequeDate() {
        return chequeDate;
    }

 /** 
	* Set chequeDate associated with this object.
	* @param chequeDate The chequeDate value to set
 **/ 

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

 /** 
	* Get ccvalidDate associated with this object.
	* @return ccvalidDate
 **/ 

    public String getCcvalidDate() {
        return ccvalidDate;
    }

 /** 
	* Set ccvalidDate associated with this object.
	* @param ccvalidDate The ccvalidDate value to set
 **/ 

    public void setCcvalidDate(String ccvalidDate) {
        this.ccvalidDate = ccvalidDate;
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
	* Get createdTime associated with this object.
	* @return createdTime
 **/ 

    public String getCreatedTime() {
        return createdTime;
    }

 /** 
	* Set createdTime associated with this object.
	* @param createdTime The createdTime value to set
 **/ 

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

 /** 
	* Get updatedTime associated with this object.
	* @return updatedTime
 **/ 

    public String getUpdatedTime() {
        return updatedTime;
    }

 /** 
	* Set updatedTime associated with this object.
	* @param updatedTime The updatedTime value to set
 **/ 

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
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
	 * @return Returns the createdByName.
	 */
	public String getCreatedByName() {
		return createdByName;
	}
	/**
	 * @param createdByName The createdByName to set.
	 */
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	
	 public void reset(ActionMapping mapping, HttpServletRequest request) {

        // Reset values are provided as samples only. Change as appropriate.

        transactionId = null;
        chqccNumber = null;
        ccvalidDate = null;
        transactionAmt = null;
        accountId = null;
        transactionType = null;
        createdByName = null;
        creditedAmt = null;
        channelType = null;
        transactionListType = null;
        bankName = null;
        chequeDate = null;
        methodName = null;

    }

	
}
