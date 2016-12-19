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
public class HBOReportFormBean extends ActionForm {
ArrayList prodList;
ArrayList circleLIst;
String mcode;
String circleId;
String report;
ArrayList reportData;
ArrayList reportData1;
ArrayList total=null;

ArrayList warehouseList;
String warehouse_to = "";
String fromDate ="";
String toDate ="";

String reportType;

/**
 * @return
 */
public ArrayList getCircleLIst() {
	return circleLIst;
}

/**
 * @return
 */
public ArrayList getProdList() {
	return prodList;
}

/**
 * @param list
 */
public void setCircleLIst(ArrayList list) {
	circleLIst = list;
}

/**
 * @param list
 */
public void setProdList(ArrayList list) {
	prodList = list;
}

/**
 * @return
 */
public String getMcode() {
	return mcode;
}

/**
 * @param string
 */
public void setMcode(String string) {
	mcode = string;
}

/**
 * @return
 */
public String getReport() {
	return report;
}

/**
 * @param string
 */
public void setReport(String string) {
	report = string;
}

/**
 * @return
 */
public String getCircleId() {
	return circleId;
}

/**
 * @param string
 */
public void setCircleId(String string) {
	circleId = string;
}

/**
 * @return
 */
public ArrayList getReportData() {
	return reportData;
}

/**
 * @param list
 */
public void setReportData(ArrayList list) {
	reportData = list;
}

/**
 * @return
 */
public String getFromDate() {
	return fromDate;
}

/**
 * @return
 */
public String getToDate() {
	return toDate;
}

/**
 * @return
 */
public String getWarehouse_to() {
	return warehouse_to;
}

/**
 * @return
 */
public ArrayList getWarehouseList() {
	return warehouseList;
}

/**
 * @param string
 */
public void setFromDate(String string) {
	fromDate = string;
}

/**
 * @param string
 */
public void setToDate(String string) {
	toDate = string;
}

/**
 * @param string
 */
public void setWarehouse_to(String string) {
	warehouse_to = string;
}

/**
 * @param list
 */
public void setWarehouseList(ArrayList list) {
	warehouseList = list;
}

/**
 * @return
 */
public ArrayList getTotal() {
	return total;
}

/**
 * @param list
 */
public void setTotal(ArrayList list) {
	total = list;
}

/**
 * @return
 */
public String getReportType() {
	return reportType;
}

/**
 * @param string
 */
public void setReportType(String string) {
	reportType = string;
}

/**
 * @return
 */
public ArrayList getReportData1() {
	return reportData1;
}

/**
 * @param list
 */
public void setReportData1(ArrayList list) {
	reportData1 = list;
}

}