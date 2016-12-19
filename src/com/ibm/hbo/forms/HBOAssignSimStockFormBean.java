/*
 * Created on Nov 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOAssignSimStockFormBean extends ValidatorForm  {

int avlSimStock=0;
String warehouseId;
String assignedSimQty;
ArrayList accountdetails;



/**
 * @return
 */
public String getAssignedSimQty() {
	return assignedSimQty;
}

/**
 * @return
 */


/**
 * @return
 */


/**
 * @return
 */
public String getWarehouseId() {
	return warehouseId;
}

/**
 * @param string
 */
public void setAssignedSimQty(String string) {
	assignedSimQty = string;
}



/**
 * @param string
 */
public void setWarehouseId(String string) {
	warehouseId = string;
}

/**
 * @param i
 */
public void setAvlSimStock(int i) {
	avlSimStock = i;
}

/**
 * @return
 */
public int getAvlSimStock() {
	return avlSimStock;
}

public ArrayList getAccountdetails() {
	return accountdetails;
}

public void setAccountdetails(ArrayList accountdetails) {
	this.accountdetails = accountdetails;
}

}