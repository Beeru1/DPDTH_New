package com.ibm.hbo.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a ArcLoginForm.
 * @version 	1.0
 * @author Avadesh
 */
public class HBOProductFormBean extends ActionForm {
	private int bcode=0;
	private String bname=""; 
	private String productcode="";
	private String modelcode="";
	private String modelname="";
	private String companyname="";
	private String productdesc="";
	private String stocktype="";
	private int warranty=0;
	private String createddate="";
	private String createdby="";
	private String updateddate="";
	private String updatedby="";
	private ArrayList arrBCList=null;
	private ArrayList arrProductList=null;
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset field values here.

	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

	}
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public ArrayList getArrBCList() {
		return arrBCList;
	}

	/**
	 * @return
	 */
	public int getBcode() {
		return bcode;
	}

	/**
	 * @return
	 */
	
	 
	public void setArrBCList(ArrayList list) {
		arrBCList = list;
	}

	/**
	 * @param i
	 */
	public void setBcode(int i) {
		bcode = i;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @return
	 */
	public String getCompanyname() {
		return companyname;
	}

	/**
	 * @return
	 */
	public String getModelcode() {
		return modelcode;
	}

	/**
	 * @return
	 */
	public String getModelname() {
		return modelname;
	}

	/**
	 * @return
	 */
	public String getProductcode() {
		return productcode;
	}

	/**
	 * @return
	 */
	public String getProductdesc() {
		return productdesc;
	}

	/**
	 * @return
	 */
	public String getStocktype() {
		return stocktype;
	}

	/**
	 * @return
	 */
	public int getWarranty() {
		return warranty;
	}

	/**
	 * @param string
	 */
	public void setCompanyname(String string) {
		companyname = string;
	}

	/**
	 * @param string
	 */
	public void setModelcode(String string) {
		modelcode = string;
	}

	/**
	 * @param string
	 */
	public void setModelname(String string) {
		modelname = string;
	}

	/**
	 * @param string
	 */
	public void setProductcode(String string) {
		productcode = string;
	}

	/**
	 * @param string
	 */
	public void setProductdesc(String string) {
		productdesc = string;
	}

	/**
	 * @param string
	 */
	public void setStocktype(String string) {
		stocktype = string;
	}

	/**
	 * @param i
	 */
	public void setWarranty(int i) {
		warranty = i;
	}

	/**
	 * @return
	 */
	public ArrayList getArrProductList() {
		return arrProductList;
	}

	/**
	 * @param list
	 */
	public void setArrProductList(ArrayList list) {
		arrProductList = list;
	}

	/**
	 * @return
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @return
	 */
	public String getCreateddate() {
		return createddate;
	}

	/**
	 * @return
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @return
	 */
	public String getUpdateddate() {
		return updateddate;
	}

	/**
	 * @param string
	 */
	public void setCreatedby(String string) {
		createdby = string;
	}

	/**
	 * @param string
	 */
	public void setCreateddate(String string) {
		createddate = string;
	}

	/**
	 * @param string
	 */
	public void setUpdatedby(String string) {
		updatedby = string;
	}

	/**
	 * @param string
	 */
	public void setUpdateddate(String string) {
		updateddate = string;
	}

	/**
	 * @return
	 */
	public String getBname() {
		return bname;
	}

	/**
	 * @param string
	 */
	public void setBname(String string) {
		bname = string;
	}

}