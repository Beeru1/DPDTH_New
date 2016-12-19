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
public class HBORequisitionDTO {
  String businessCategory="";
  String productCode="";
  int warehouse_to=0;
  String month = "";
  String requisitionDate = "";
  String qtyRequisition ="";
  int requisitionId = 0;
  String assigneddate = "";
  int qtyuploaded = 0;
  String invoice_no = "";
  String invoice_dt = "";
  int sumqtyuploaded = 0;
  String warehouse_to_name = "";
  String modelCode = "";
  

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

/**
 * @return
 */
public String getAssigneddate() {
	return assigneddate;
}

/**
 * @return
 */
public int getQtyuploaded() {
	return qtyuploaded;
}

/**
 * @return
 */
public int getRequisitionId() {
	return requisitionId;
}

/**
 * @param string
 */
public void setAssigneddate(String string) {
	assigneddate = string;
}

/**
 * @param i
 */
public void setQtyuploaded(int i) {
	qtyuploaded = i;
}

/**
 * @param i
 */
public void setRequisitionId(int i) {
	requisitionId = i;
}

/**
 * @return
 */
public String getInvoice_dt() {
	return invoice_dt;
}

/**
 * @return
 */
public String getInvoice_no() {
	return invoice_no;
}

/**
 * @param string
 */
public void setInvoice_dt(String string) {
	invoice_dt = string;
}

/**
 * @param string
 */
public void setInvoice_no(String string) {
	invoice_no = string;
}

/**
 * @return
 */
public int getSumqtyuploaded() {
	return sumqtyuploaded;
}

/**
 * @param i
 */
public void setSumqtyuploaded(int i) {
	sumqtyuploaded = i;
}

/**
 * @return
 */
public String getWarehouse_to_name() {
	return warehouse_to_name;
}

/**
 * @param string
 */
public void setWarehouse_to_name(String string) {
	warehouse_to_name = string;
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
public String getMonth() {
	return month;
}

/**
 * @param string
 */
public void setMonth(String string) {
	month = string;
}

/**
 * @return
 */
public String getModelCode() {
	return modelCode;
}

/**
 * @param string
 */
public void setModelCode(String string) {
	modelCode = string;
}

}
