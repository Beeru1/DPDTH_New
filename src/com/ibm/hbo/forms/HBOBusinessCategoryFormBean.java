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
public class HBOBusinessCategoryFormBean extends ActionForm {
	private int bcode=0;
	private String bname="";
	private ArrayList arrBCList=null;
	
	
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
	public String getBname() {
		return bname;
	}

	/**
	 * @param list
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
	public void setBname(String string) {
		bname = string;
	}

}