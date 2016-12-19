package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;


 /** 
	* @author 
	* Created On Tue Sep 25 17:22:59 IST 2007 
	* Form Bean class for table ARC_CONNECT_MSTR 
 
 **/ 
public class HBOCreateRequisitionFormBean extends ValidatorForm {

 
	private String businessCategory = ""; 
	private ArrayList arrBusinessCategory = null;
	private String productCode = "";
	private ArrayList arrProduct = null;
	private String qtyRequisition = "";
	private String requisitionDate = "";
	private String warehouse_to = "";
	private String month = "";
	private ArrayList arrWarehouse = null;
	private ArrayList arrMonth = null;
	private ArrayList arrRequisitionList = null;
	private String selectFile = "";
	private String requisition_id = "";
	private String condition = "";
	private String userType = "";
	private String newReqId = "";
	private String oldReqId = "";
	
//    public ActionErrors validate(
//        ActionMapping mapping,
//        HttpServletRequest request) {
//        ActionErrors errors = new ActionErrors();
//        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
//        return errors;
//    }
// 

	public String getNewReqId() {
		return newReqId;
	}

	public void setNewReqId(String newReqId) {
		this.newReqId = newReqId;
	}

	public String getOldReqId() {
		return oldReqId;
	}

	public void setOldReqId(String oldReqId) {
		this.oldReqId = oldReqId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void reset(){
		warehouse_to="";
		businessCategory="";
		month="";
		productCode="";
		qtyRequisition="";
	}
	
	/**
	 * @return
	 */
	public ArrayList getArrBusinessCategory() {
		return arrBusinessCategory;
	}

	/**
	 * @return
	 */
	public ArrayList getArrProduct() {
		return arrProduct;
	}

	/**
	 * @return
	 */
	public String getBusinessCategory() {
		return businessCategory;
	}

	/**
	 * @return
	 */
	

	/**
	 * @return
	 */
	public String getProductCode() {
		return productCode;
	}

	

	/**
	 * @return
	 */
	public String getRequisitionDate() {
		return requisitionDate;
	}

	/**
	 * @return
	 */
	

	/**
	 * @param list
	 */
	public void setArrBusinessCategory(ArrayList list) {
		arrBusinessCategory = list;
	}

	/**
	 * @param list
	 */
	public void setArrProduct(ArrayList list) {
		arrProduct = list;
	}

	/**
	 * @param string
	 */
	public void setBusinessCategory(String string) {
		businessCategory = string;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @param string
	 */
	public void setProductCode(String string) {
		productCode = string;
	}

	

	/**
	 * @param string
	 */
	public void setRequisitionDate(String string) {
		requisitionDate = string;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @return
	 */
	public ArrayList getArrWarehouse() {
		return arrWarehouse;
	}

	/**
	 * @param list
	 */
	public void setArrWarehouse(ArrayList list) {
		arrWarehouse = list;
	}

	/**
	 * @return
	 */
	

	/**
	 * @return
	 */
	public ArrayList getArrMonth() {
		return arrMonth;
	}

	/**
	 * @param list
	 */
	public void setArrMonth(ArrayList list) {
		arrMonth = list;
	}




	/**
	 * @return
	 */
	public ArrayList getArrRequisitionList() {
		return arrRequisitionList;
	}

	/**
	 * @param list
	 */
	public void setArrRequisitionList(ArrayList list) {
		arrRequisitionList = list;
	}

	/**
	 * @return
	 */
	public String getSelectFile() {
		return selectFile;
	}

	/**
	 * @param string
	 */
	public void setSelectFile(String string) {
		selectFile = string;
	}

	/**
	 * @return
	 */
	public String getRequisition_id() {
		return requisition_id;
	}

	/**
	 * @param string
	 */
	public void setRequisition_id(String string) {
		requisition_id = string;
	}

	/**
	 * @return
	 */
	public String getQtyRequisition() {
		return qtyRequisition;
	}

	/**
	 * @param string
	 */
	public void setQtyRequisition(String string) {
		qtyRequisition = string;
	}

	/**
	 * @return
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param string
	 */
	public void setCondition(String string) {
		condition = string;
	}

	/**
	 * @return
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public String getWarehouse_to() {
		return warehouse_to;
	}

	/**
	 * @param string
	 */
	public void setMonth(String string) {
		month = string;
	}

	/**
	 * @param string
	 */
	public void setWarehouse_to(String string) {
		warehouse_to = string;
	}

}
