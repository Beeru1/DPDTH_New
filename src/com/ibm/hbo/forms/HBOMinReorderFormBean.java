/*
 * Created on Nov 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;

import java.util.ArrayList;


import org.apache.struts.validator.ValidatorForm;

/**
 * @author Aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOMinReorderFormBean extends ValidatorForm
{
ArrayList arrdistributorList=null;
ArrayList arrproductsList=null;
ArrayList arrBusinessCat=null;
String product="";
String distributor="";
ArrayList arrgetChangedValues=new ArrayList(); 
int minreorder=0;
String message = null;
String business_catg=""; 
String bcode = "";
/**
 * @return
 */

/**
 * @return
 */
public ArrayList getArrdistributorList() {
	return arrdistributorList;
}

/**
 * @return
 */
public ArrayList getArrproductsList() {
	return arrproductsList;
}

/**
 * @return
 */
public String getDistributor() {
	return distributor;
}

/**
 * @return
 */
public int getMinreorder() {
	return minreorder;
}

/**
 * @return
 */
public String getProduct() {
	return product;
}

/**
 * @param list
 */
public void setArrdistributorList(ArrayList list) {
	arrdistributorList = list;
}

/**
 * @param list
 */
public void setArrproductsList(ArrayList list) {
	arrproductsList = list;
}

/**
 * @param string
 */
public void setDistributor(String string) {
	distributor = string;
}

/**
 * @param i
 */
public void setMinreorder(int i) {
	minreorder = i;
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
public ArrayList getArrgetChangedValues() {
	return arrgetChangedValues;
}

/**
 * @param list
 */
public void setArrgetChangedValues(ArrayList list) {
	arrgetChangedValues = list;
}

/**
 * @return
 */
public String getMessage() {
	return message;
}

/**
 * @param string
 */
public void setMessage(String string) {
	message = string;
}

public void reset(){
	this.arrdistributorList=null;
	this.arrgetChangedValues=null;
	this.arrproductsList=null;
	this.distributor=null;
	this.message=null;
	this.minreorder=0;
    this.product=null;
	this.arrBusinessCat=null;
	this.business_catg=null;
}
/**
 * @return
 */
public ArrayList getArrBusinessCat() {
	return arrBusinessCat;
}

/**
 * @param list
 */
public void setArrBusinessCat(ArrayList list) {
	arrBusinessCat = list;
}

/**
 * @return
 */
public String getBusiness_catg() {
	return business_catg;
}

/**
 * @param string
 */
public void setBusiness_catg(String string) {
	business_catg = string;
}

/**
 * @return
 */
public String getBcode() {
	return bcode;
}

/**
 * @param string
 */
public void setBcode(String string) {
	bcode = string;
}

}
