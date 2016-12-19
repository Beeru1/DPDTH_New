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
public class HBOBundleStockFormBean extends ActionForm  {

int avlSimStock=0;
int avlPairedStock=0;
ArrayList productdetails=null;
ArrayList circledetails=null;
String circle="";
String circlename="";
String product="";
String productcode="";
int bundleQty=0;
int requestNo=0;


/**
 * @return
 */
public int getAvlPairedStock() {
	return avlPairedStock;
}

/**
 * @return
 */
public int getAvlSimStock() {
	return avlSimStock;
}

/**
 * @return
 */
public int getBundleQty() {
	return bundleQty;
}

/**
 * @return
 */
public ArrayList getCircledetails() {
	return circledetails;
}

/**
 * @return
 */
public ArrayList getProductdetails() {
	return productdetails;
}

/**
 * @return
 */

/**
 * @param i
 */
public void setAvlPairedStock(int i) {
	avlPairedStock = i;
}

/**
 * @param i
 */
public void setAvlSimStock(int i) {
	avlSimStock = i;
}

/**
 * @param i
 */
public void setBundleQty(int i) {
	bundleQty = i;
}

/**
 * @param list
 */
public void setCircledetails(ArrayList list) {
	circledetails = list;
}

/**
 * @param list
 */
public void setProductdetails(ArrayList list) {
	productdetails = list;
}

/**
 * @param i
 */


/**
 * @return
 */
public String getCircle() {
	return circle;
}

/**
 * @return
 */
public String getCirclename() {
	return circlename;
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
public void setCirclename(String string) {
	circlename = string;
}

/**
 * @return
 */
public String getProduct() {
	return product;
}

/**
 * @return
 */
public String getProductcode() {
	return productcode;
}

/**
 * @param string
 */
public void setProduct(String string) {
	product = string;
}

/**
 * @param string
 */
public void setProductcode(String string) {
	productcode = string;
}

/**
 * @return
 */
public int getRequestNo() {
	return requestNo;
}

/**
 * @param i
 */
public void setRequestNo(int i) {
	requestNo = i;
}

}