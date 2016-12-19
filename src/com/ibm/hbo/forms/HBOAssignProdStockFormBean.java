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
public class HBOAssignProdStockFormBean extends ValidatorForm  {

ArrayList arrProducts=null;
String role="";
String Stocktype=null;
ArrayList arrCircle=null;
String avlProdStock=null;
String warehouseId=null;
String assignedProdQty=null;
ArrayList arrwarehousedetails=null;
String product=null;
String circle=null;
String bundle=null;
String pseudo_prodcode=null;
String availableProdQty=null;
ArrayList arrAssignedProdList = null;
ArrayList acceptRejectProdList = null;
String[] status = null;
String batch_no = null;
ArrayList arrBatchDetailsList = null;
String[] arrBatch = null;
String[] arrStatus = null;
String batchNo = "";
ArrayList arrBndlSatus=null;
String damageFlag = "";
String cond = "";
int warehouseIdHidden;



public String getDamageFlag() {
	return damageFlag;
}

public void setDamageFlag(String damageFlag) {
	this.damageFlag = damageFlag;
}

/*
 * @return
 */
public ArrayList getArrCircle() {
	return arrCircle;
}

/**
 * @return
 */
public ArrayList getArrProducts() {
	return arrProducts;
}

/**
 * @return
 */
public ArrayList getArrwarehousedetails() {
	return arrwarehousedetails;
}

/**
 * @return
 */
public String getAssignedProdQty() {
	return assignedProdQty;
}

/**
 * @return
 */
public String getAvlProdStock() {
	return avlProdStock;
}

/**
 * @return
 */
public String getStocktype() {
	return Stocktype;
}

/**
 * @return
 */
public String getWarehouseId() {
	return warehouseId;
}

/**
 * @param list
 */
public void setArrCircle(ArrayList list) {
	arrCircle = list;
}

/**
 * @param list
 */
public void setArrProducts(ArrayList list) {
	arrProducts = list;
}

/**
 * @param list
 */
public void setArrwarehousedetails(ArrayList list) {
	arrwarehousedetails = list;
}

/**
 * @param string
 */
public void setAssignedProdQty(String string) {
	assignedProdQty = string;
}

/**
 * @param string
 */
public void setAvlProdStock(String string) {
	avlProdStock = string;
}

/**
 * @param string
 */
public void setStocktype(String string) {
	Stocktype = string;
}

/**
 * @param string
 */
public void setWarehouseId(String string) {
	warehouseId = string;
}

/**
 * @return
 */
public String getCircle() {
	return circle;
}

/**
 * @return
 */
public String getProduct() {
	return product;
}

/**
 * @param string
 */
public void setCircle(String string) {
	circle = string;
}

/**
 * @param string
 */
public void setProduct(String string) {
	product = string;
}

/**
 * @return
 */
public String getBundle() {
	return bundle;
}

/**
 * @param string
 */
public void setBundle(String string) {
	bundle = string;
}

/**
 * @return
 */
public String getPseudo_prodcode() {
	return pseudo_prodcode;
}

/**
 * @param string
 */
public void setPseudo_prodcode(String string) {
	pseudo_prodcode = string;
}

/**
 * @return
 */
public String getAvailableProdQty() {
	return availableProdQty;
}

/**
 * @param string
 */
public void setAvailableProdQty(String string) {
	availableProdQty = string;
}

/**
 * @return
 */
public ArrayList getArrAssignedProdList() {
	return arrAssignedProdList;
}

/**
 * @param list
 */
public void setArrAssignedProdList(ArrayList list) {
	arrAssignedProdList = list;
}

/**
 * @return
 */
public ArrayList getAcceptRejectProdList() {
	return acceptRejectProdList;
}

/**
 * @param list
 */
public void setAcceptRejectProdList(ArrayList list) {
	acceptRejectProdList = list;
}



/**
 * @return
 */


/**
 * @return
 */
public String[] getStatus() {
	return status;
}

/**
 * @param strings
 */
public void setStatus(String[] strings) {
	status = strings;
}

/**
 * @return
 */
public String getBatch_no() {
	return batch_no;
}

/**
 * @param string
 */
public void setBatch_no(String string) {
	batch_no = string;
}

/**
 * @return
 */
public ArrayList getArrBatchDetailsList() {
	return arrBatchDetailsList;
}

/**
 * @param list
 */
public void setArrBatchDetailsList(ArrayList list) {
	arrBatchDetailsList = list;
}

/**
 * @return
 */


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
public ArrayList getArrBndlSatus() {
	return arrBndlSatus;
}

/**
 * @param list
 */
public void setArrBndlSatus(ArrayList list) {
	arrBndlSatus = list;
}

/**
 * @return
 */
public String getBatchNo() {
	return batchNo;
}

/**
 * @param string
 */
public void setBatchNo(String string) {
	batchNo = string;
}

public String getCond() {
	return cond;
}

public void setCond(String cond) {
	this.cond = cond;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public int getWarehouseIdHidden() {
	return warehouseIdHidden;
}

public void setWarehouseIdHidden(int warehouseIdHidden) {
	this.warehouseIdHidden = warehouseIdHidden;
}

}