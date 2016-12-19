/*
 * Created on Nov 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOVerifySimStockFormBean extends ActionForm  {
	private String[] arrStatus;
	private String[] arrBatch;
	
	private ArrayList unverifiedBatch = null;
	public String status;
	private ArrayList verifiedBatch;
	private ArrayList assignedBatch;
	private int unverifiedBatch_size;
	private int verifiedBatch_size;
	
	private String created_dt ="";
	private String updated_dt ="";	
	private String batch_no = "";
	private int assignedSimQty = 0;  




	/**
	 * @return
	 */
	public int getAssignedSimQty() {
		return assignedSimQty;
	}

	/**
	 * @return
	 */
	public String getBatch_no() {
		return batch_no;
	}

	/**
	 * @return
	 */
	public String getCreated_dt() {
		return created_dt;
	}

	/**
	 * @return
	 */
	public ArrayList getUnverifiedBatch() {
		return unverifiedBatch;
	}

	/**
	 * @return
	 */
	public String getUpdated_dt() {
		return updated_dt;
	}

	/**
	 * @return
	 */
	public ArrayList getVerifiedBatch() {
		return verifiedBatch;
	}

	/**
	 * @param i
	 */
	public void setAssignedSimQty(int i) {
		assignedSimQty = i;
	}

	/**
	 * @param string
	 */
	public void setBatch_no(String string) {
		batch_no = string;
	}

	/**
	 * @param string
	 */
	public void setCreated_dt(String string) {
		created_dt = string;
	}

	/**
	 * @param list
	 */
	public void setUnverifiedBatch(ArrayList list) {
		unverifiedBatch = list;
	}

	/**
	 * @param string
	 */
	public void setUpdated_dt(String string) {
		updated_dt = string;
	}

	/**
	 * @param list
	 */
	public void setVerifiedBatch(ArrayList list) {
		verifiedBatch = list;
	}

	/**
	 * @param list
	 */
	public void setStatus(String list) {
		status = list;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

 	/**
	 * @return
	 */
	public String[] getArrBatch() {
		return arrBatch;
	}

	/**
	 * @return
	 */
	public String[] getArrStatus() {
		return arrStatus;
	}

	/**
	 * @param strings
	 */
	public void setArrBatch(String[] strings) {
		arrBatch = strings;
	}

	/**
	 * @param strings
	 */
	public void setArrStatus(String[] strings) {
		arrStatus = strings;
	}

	/**
	 * @return
	 */
	public ArrayList getAssignedBatch() {
		return assignedBatch;
	}

	/**
	 * @param list
	 */
	public void setAssignedBatch(ArrayList list) {
		assignedBatch = list;
	}

	/**
	 * @return
	 */


}


