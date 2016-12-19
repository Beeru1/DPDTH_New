/*
 * Created on Nov 26, 2007
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
public class HBOViewBatchDetailsFormBean extends ActionForm  {
 private String batchNo;
 private int simQty;
 private ArrayList simList;
 
 

/**
 * @return
 */
public String getBatchNo() {
	return batchNo;
}

/**
 * @return
 */
public ArrayList getSimList() {
	return simList;
}

/**
 * @return
 */
public int getSimQty() {
	return simQty;
}

/**
 * @param string
 */
public void setBatchNo(String string) {
	batchNo = string;
}

/**
 * @param list
 */
public void setSimList(ArrayList list) {
	simList = list;
}

/**
 * @param i
 */
public void setSimQty(int i) {
	simQty = i;
}

}
