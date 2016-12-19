package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;

/**
 * Form Bean object for State Warehouse Locator Master List for populating Create Account drop-down
 * 
 * @author Saatae Issa
 *  
 */
public class SWLocatorListFormBean extends ActionForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3008824669718490698L;

	/**
	 * swId
	 */
	private int swId;

	/**
	 * swCoAreaSubareaSrcType
	 */
	private String swCoAreaSubareaSrcType;

	/**
	 * Getter for property "swId"
	 */
	public int getSwId() {
		return swId;
	}

	/**
	 * Setter for property <code>swId</code>.
	 * @param swId
	 */
	public void setSwId(int swId) {
		this.swId = swId;
	}

	/**
	 * Getter for property "swCoAreaSubareaSrcType"
	 */
	public String getSwCoAreaSubareaSrcType() {
		return swCoAreaSubareaSrcType;
	}

	/**
	 * Setter for property <code>setSwCoAreaSubareaSrcType</code>.
	 * @param setSwCoAreaSubareaSrcType
	 */
	public void setSwCoAreaSubareaSrcType(String swCoAreaSubareaSrcType) {
		this.swCoAreaSubareaSrcType = swCoAreaSubareaSrcType;
	}
}