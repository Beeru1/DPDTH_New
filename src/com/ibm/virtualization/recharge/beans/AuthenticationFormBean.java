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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.dto.Link;

/**
 * Form Bean class for login Form
 * 
 * @author Paras    
 *  
 */
public class AuthenticationFormBean extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6610171211438811528L;
	private String loginName ;
	private String password ;
	private ArrayList arrSysConfigLinks =null ;
	private ArrayList arrAcctMgmtLinks =null;
	private ArrayList arrMoneyTranLinks =null;
	private ArrayList arrSysAdminLinks =null;
	private ArrayList arrReverseFlowLinks =null;
	private ArrayList arrUssdActvLinks =null;
	private ArrayList arrBalEnqLinks =null;
	private ArrayList arrReportLinks =null;
	private ArrayList arrForwardReportLinks = null;
	private ArrayList arrReverseReportLinks = null;
	private ArrayList arrStockReportLinks = null;
	private ArrayList arrRecoReportLinks = null;
	private ArrayList arrConsumptionReportLinks = null;
	
	private String message ;
	private String disabledLink;

	private String result;
	public String getDisabledLink() {
		return disabledLink;
	}
	public void setDisabledLink(String disabledLink) {
		this.disabledLink = disabledLink;
	}
	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	// this method is used to check blank values from login form  
	public ActionErrors validate(
	        ActionMapping mapping,
	        HttpServletRequest request) {
	 	
	        ActionErrors errors = new ActionErrors();
	        if ((getLoginName()== null)
	                || (getLoginName().trim().equalsIgnoreCase(""))) {
	        
	           errors.add("errors.login.username_required",new ActionError("errors.login.username_required"));
	        }
	        
	        if ((getPassword()== null)
	                || (getPassword().trim().equalsIgnoreCase(""))) {
	        	errors.add("errors.login.password_required",new ActionError("errors.login.password_required"));
	            
	        }
	        return errors;
	    }
	public ArrayList getArrAcctMgmtLinks() {
		return arrAcctMgmtLinks;
	}
	public void setArrAcctMgmtLinks(ArrayList arrAcctMgmtLinks) {
		this.arrAcctMgmtLinks = arrAcctMgmtLinks;
	}
	public ArrayList getArrMoneyTranLinks() {
		return arrMoneyTranLinks;
	}
	public void setArrMoneyTranLinks(ArrayList arrMoneyTranLinks) {
		this.arrMoneyTranLinks = arrMoneyTranLinks;
	}
	public ArrayList getArrSysAdminLinks() {
		return arrSysAdminLinks;
	}
	public void setArrSysAdminLinks(ArrayList arrSysAdminLinks) {
		this.arrSysAdminLinks = arrSysAdminLinks;
	}
	public ArrayList getArrSysConfigLinks() {
		return arrSysConfigLinks;
	}
	public void setArrSysConfigLinks(ArrayList arrSysConfigLinks) {
		this.arrSysConfigLinks = arrSysConfigLinks;
	}
	public ArrayList getArrUssdActvLinks() {
		return arrUssdActvLinks;
	}
	public void setArrUssdActvLinks(ArrayList arrUssdActvLinks) {
		this.arrUssdActvLinks = arrUssdActvLinks;
	}
	public ArrayList getArrBalEnqLinks() {
		return arrBalEnqLinks;
	}
	public void setArrBalEnqLinks(ArrayList arrBalEnqLinks) {
		this.arrBalEnqLinks = arrBalEnqLinks;
	}
	public ArrayList getArrReportLinks() {
		return arrReportLinks;
	}
	public void setArrReportLinks(ArrayList arrReportLinks) {
		this.arrReportLinks = arrReportLinks;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList getArrReverseFlowLinks() {
		return arrReverseFlowLinks;
	}
	public void setArrReverseFlowLinks(ArrayList arrReverseFlowLinks) {
		this.arrReverseFlowLinks = arrReverseFlowLinks;
	}
	/**
	 * @return the arrConsumptionReportLinks
	 */
	public ArrayList getArrConsumptionReportLinks() {
		return arrConsumptionReportLinks;
	}
	/**
	 * @param arrConsumptionReportLinks the arrConsumptionReportLinks to set
	 */
	public void setArrConsumptionReportLinks(ArrayList arrConsumptionReportLinks) {
		this.arrConsumptionReportLinks = arrConsumptionReportLinks;
	}
	/**
	 * @return the arrForwardReportLinks
	 */
	public ArrayList getArrForwardReportLinks() {
		return arrForwardReportLinks;
	}
	/**
	 * @param arrForwardReportLinks the arrForwardReportLinks to set
	 */
	public void setArrForwardReportLinks(ArrayList arrForwardReportLinks) {
		this.arrForwardReportLinks = arrForwardReportLinks;
	}
	/**
	 * @return the arrRecoReportLinks
	 */
	public ArrayList getArrRecoReportLinks() {
		return arrRecoReportLinks;
	}
	/**
	 * @param arrRecoReportLinks the arrRecoReportLinks to set
	 */
	public void setArrRecoReportLinks(ArrayList arrRecoReportLinks) {
		this.arrRecoReportLinks = arrRecoReportLinks;
	}
	/**
	 * @return the arrReverseReportLinks
	 */
	public ArrayList getArrReverseReportLinks() {
		return arrReverseReportLinks;
	}
	/**
	 * @param arrReverseReportLinks the arrReverseReportLinks to set
	 */
	public void setArrReverseReportLinks(ArrayList arrReverseReportLinks) {
		this.arrReverseReportLinks = arrReverseReportLinks;
	}
	/**
	 * @return the arrStockReportLinks
	 */
	public ArrayList getArrStockReportLinks() {
		return arrStockReportLinks;
	}
	/**
	 * @param arrStockReportLinks the arrStockReportLinks to set
	 */
	public void setArrStockReportLinks(ArrayList arrStockReportLinks) {
		this.arrStockReportLinks = arrStockReportLinks;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
