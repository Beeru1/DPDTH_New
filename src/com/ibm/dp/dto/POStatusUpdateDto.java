package com.ibm.dp.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class POStatusUpdateDto {

	private String productName="";
	private String quantity="";
	private String product="";
	private String invoiceNo=""; 
	private String invoiceDT ="";
	private int    invoiceQTY;
	private String dc_no="" ;
	private String dc_dt ="";
	private String dd_cheque_no;
	private String dd_cheque_dt ="";
	private String extDistID;
	private String prNO;
	private String poNO;
	private String poDate="";
	private String serialNo="";
	private ArrayList serialNos= null;
	private List   poPrdID= null;
	private int    poQty;
	private HashMap productIDmap = null;
	private List   productList= new ArrayList();
	
	private int poStatus;
	//private int poQty;
	private String  poStatusDate = "";
// Error code
	private String errorCode;

// Error Message
	private String errorMessage;

// Response code
	private String resposeCode;
	//Added By Shilpa Khanna--  Added New Variable poRemarks
	private String poRemarks="";
	public String getDc_dt() {
		return dc_dt;
	}
	public void setDc_dt(String dc_dt) {
		this.dc_dt = dc_dt;
	}
	public String getDc_no() {
		return dc_no;
	}
	public void setDc_no(String dc_no) {
		this.dc_no = dc_no;
	}
	public String getDd_cheque_dt() {
		return dd_cheque_dt;
	}
	public void setDd_cheque_dt(String dd_cheque_dt) {
		this.dd_cheque_dt = dd_cheque_dt;
	}
	public String getDd_cheque_no() {
		return dd_cheque_no;
	}
	public void setDd_cheque_no(String dd_cheque_no) {
		this.dd_cheque_no = dd_cheque_no;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getExtDistID() {
		return extDistID;
	}
	public void setExtDistID(String extDistID) {
		this.extDistID = extDistID;
	}
	public String getInvoiceDT() {
		return invoiceDT;
	}
	public void setInvoiceDT(String invoiceDT) {
		this.invoiceDT = invoiceDT;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public int getInvoiceQTY() {
		return invoiceQTY;
	}
	public void setInvoiceQTY(int invoiceQTY) {
		this.invoiceQTY = invoiceQTY;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public String getPoNO() {
		return poNO;
	}
	public void setPoNO(String poNO) {
		this.poNO = poNO;
	}
	public List getPoPrdID() {
		return poPrdID;
	}
	public void setPoPrdID(List poPrdID) {
		this.poPrdID = poPrdID;
	}
	public int getPoQty() {
		return poQty;
	}
	public void setPoQty(int poQty) {
		this.poQty = poQty;
	}
	public String getPoRemarks() {
		return poRemarks;
	}
	public void setPoRemarks(String poRemarks) {
		this.poRemarks = poRemarks;
	}
	
	
	public String getPrNO() {
		return prNO;
	}
	public void setPrNO(String prNO) {
		this.prNO = prNO;
	}
	public int getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(int poStatus) {
		this.poStatus = poStatus;
	}
	public String getPoStatusDate() {
		return poStatusDate;
	}
	public void setPoStatusDate(String poStatusDate) {
		this.poStatusDate = poStatusDate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public HashMap getProductIDmap() {
		return productIDmap;
	}
	public void setProductIDmap(HashMap productIDmap) {
		this.productIDmap = productIDmap;
	}
	public List getProductList() {
		return productList;
	}
	public void setProductList(List productList) {
		this.productList = productList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getResposeCode() {
		return resposeCode;
	}
	public void setResposeCode(String resposeCode) {
		this.resposeCode = resposeCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public ArrayList getSerialNos() {
		return serialNos;
	}
	public void setSerialNos(ArrayList serialNos) {
		this.serialNos = serialNos;
	}


}
