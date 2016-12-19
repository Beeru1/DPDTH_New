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
public class HBOViewBundledStockFormBean extends ActionForm  {
	private ArrayList bundledBatch;
	private ArrayList bundledBatchDetails;
	private String requestId;
	private int bundledQTY;
	/**bundledBatch
	 * @return
	 */
	public ArrayList getBundledBatch() {
		return bundledBatch;
	}

	/**
	 * @param list
	 */
	public void setBundledBatch(ArrayList list) {
		bundledBatch = list;
	}

	/**
	 * @return
	 */
	public ArrayList getBundledBatchDetails() {
		return bundledBatchDetails;
	}

	/**
	 * @param list
	 */
	public void setBundledBatchDetails(ArrayList list) {
		bundledBatchDetails = list;
	}

	/**
	 * @return
	 */
	public int getBundledQTY() {
		return bundledQTY;
	}

	/**
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param i
	 */
	public void setBundledQTY(int i) {
		bundledQTY = i;
	}

	/**
	 * @param string
	 */
	public void setRequestId(String string) {
		requestId = string;
	}

}


