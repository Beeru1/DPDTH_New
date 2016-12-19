package com.ibm.hbo.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a ArcLoginForm.
 * @version 	1.0
 * @author Sachin
 */
public class HBOWarrantyFormBean extends ActionForm {

	
	
	
	private String imeino="";
	private int warehouseId=0;
	private String damageStatus="";
	private String stWarranty="";
	private int extWarranty=0;
	private String prCode="";
	private ArrayList arrWarrantyList=null;
	private String warehouseName="";
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
	
	
	
	public String getImeino() {
		return imeino;
	}

	/**
	 * @param string
	 */
	public void setImeino(String string) {
		imeino = string;
	}

	/**
	 * @return
	 */
	public ArrayList getArrWarrantyList() {
		return arrWarrantyList;
	}

	/**
	 * @param list
	 */
	public void setArrWarrantyList(ArrayList list) {
		arrWarrantyList = list;
	}

	/**
	 * @return
	 */
	public String getDamageStatus() {
		return damageStatus;
	}

	/**
	 * @return
	 */
	public int getExtWarranty() {
		return extWarranty;
	}

	/**
	 * @return
	 */
	public String getPrCode() {
		return prCode;
	}

	/**
	 * @return
	 */
	

	/**
	 * @return
	 */
	public int getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @param string
	 */
	public void setDamageStatus(String string) {
		damageStatus = string;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @param string
	 */
	public void setPrCode(String string) {
		prCode = string;
	}

	/**
	 * @param string
	 */
	
	/**
	 * @param i
	 */
	public void setWarehouseId(int i) {
		warehouseId = i;
	}

	/**
	 * @return
	 */
	public String getStWarranty() {
		return stWarranty;
	}

	/**
	 * @param string
	 */
	public void setStWarranty(String string) {
		stWarranty = string;
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
	public void setWarehouseName(String string) {
		warehouseName = string;
	}

	/**
	 * @param i
	 */
	public void setExtWarranty(int i) {
		extWarranty = i;
	}

}