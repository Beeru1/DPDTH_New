/*
 * Created on Nov 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dto;

/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBORequisition {
  String businessCategory="";
  String productCode="";
  int warehouse_to=0;
  int month = 0;
  String requisitionDate = "";
  int qtyRequisition =0;
/**
 * @return
 */

/**
 * @return
 */
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
public int getQtyRequisition() {
	return qtyRequisition;
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
 * @param i
 */
public void setQtyRequisition(int i) {
	qtyRequisition = i;
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
public int getMonth() {
	return month;
}

/**
 * @param i
 */
public void setMonth(int i) {
	month = i;
}

/**
 * @return
 */
public int getWarehouse_to() {
	return warehouse_to;
}

/**
 * @param i
 */
public void setWarehouse_to(int i) {
	warehouse_to = i;
}

}
