package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.WrongShipmentDTO;

public class DPStbInBulkBean extends ActionForm 
{
	private String wrongSerialsNos ="";
	private String allSerialsNos="";
	private String serialID="";
	private String invoice_no="";
	private String totalShortSerialNo="";
	private String extraSerialNo="";
	private String totalExtraSerialNo="";
	private String shortShipmentSerialsBox="";
	private String extraSerialsNoBox="";
	private String productName="";
	private String productId="";
	private String deliveryChallanNo="";
	private String dcId="";
	private String transMessage = "";
	private FormFile POfile;
	private String methodName="";
	private String strmsg="";
	private String error_flag="";
	private List error_file = new ArrayList();
	private String error_flag_wrong_short="";
	private String[] arrShort_sr_no;
	private String[] arrProduct_id;
	private String[] arr_available_sr_no;
	private String serial_no = "";  
	
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	private List<ProductMasterDto> productList = new ArrayList<ProductMasterDto>();
	private List<WrongShipmentDTO> shortShipmentSerialsList = new ArrayList<WrongShipmentDTO>();
	private List<WrongShipmentDTO> assignedSerialsSerialsList = new ArrayList<WrongShipmentDTO>();
	private	List<WrongShipmentDTO> invoiceList = new ArrayList<WrongShipmentDTO>();
	private	List<WrongShipmentDTO> dcNoList = new ArrayList<WrongShipmentDTO>();
	private List<DPPoAcceptanceBulkDTO> availableSerialList = new ArrayList<DPPoAcceptanceBulkDTO>();
	List<DPPoAcceptanceBulkDTO> short_sr_no_list= new ArrayList<DPPoAcceptanceBulkDTO>();
	List<DPPoAcceptanceBulkDTO> extra_sr_no_list= new ArrayList<DPPoAcceptanceBulkDTO>();
	
	private List<String> headersOne;
	private List<String> headersTwo;
	private List<String[]> output;
	
	private FormFile stbFile;
	ArrayList availableSerialNosList = null;
	
	public ArrayList getAvailableSerialNosList() {
		return availableSerialNosList;
	}
	public void setAvailableSerialNosList(ArrayList availableSerialNosList) {
		this.availableSerialNosList = availableSerialNosList;
	}
	
	
	public List<DPPoAcceptanceBulkDTO> getExtra_sr_no_list() {
		return extra_sr_no_list;
	}
	public void setExtra_sr_no_list(List<DPPoAcceptanceBulkDTO> extra_sr_no_list) {
		this.extra_sr_no_list = extra_sr_no_list;
	}
	public List<DPPoAcceptanceBulkDTO> getShort_sr_no_list() {
		return short_sr_no_list;
	}
	public void setShort_sr_no_list(List<DPPoAcceptanceBulkDTO> short_sr_no_list) {
		this.short_sr_no_list = short_sr_no_list;
	}
	
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public String getAllSerialsNos() {
		return allSerialsNos;
	}
	public void setAllSerialsNos(String allSerialsNos) {
		this.allSerialsNos = allSerialsNos;
	}
	public String getSerialID() {
		return serialID;
	}
	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}
	
	
	public String getWrongSerialsNos() {
		return wrongSerialsNos;
	}
	public void setWrongSerialsNos(String wrongSerialsNos) {
		this.wrongSerialsNos = wrongSerialsNos;
	}
	
	public List<WrongShipmentDTO> getAssignedSerialsSerialsList() {
		return assignedSerialsSerialsList;
	}
	public void setAssignedSerialsSerialsList(
			List<WrongShipmentDTO> assignedSerialsSerialsList) {
		this.assignedSerialsSerialsList = assignedSerialsSerialsList;
	}
	public List<WrongShipmentDTO> getShortShipmentSerialsList() {
		return shortShipmentSerialsList;
	}
	public void setShortShipmentSerialsList(
			List<WrongShipmentDTO> shortShipmentSerialsList) {
		this.shortShipmentSerialsList = shortShipmentSerialsList;
	}
	public String getExtraSerialNo() {
		return extraSerialNo;
	}
	public void setExtraSerialNo(String extraSerialNo) {
		this.extraSerialNo = extraSerialNo;
	}
	public String getTotalExtraSerialNo() {
		return totalExtraSerialNo;
	}
	public void setTotalExtraSerialNo(String totalExtraSerialNo) {
		this.totalExtraSerialNo = totalExtraSerialNo;
	}
	public String getTotalShortSerialNo() {
		return totalShortSerialNo;
	}
	public void setTotalShortSerialNo(String totalShortSerialNo) {
		this.totalShortSerialNo = totalShortSerialNo;
	}
	public List<WrongShipmentDTO> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<WrongShipmentDTO> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public String getShortShipmentSerialsBox() {
		return shortShipmentSerialsBox;
	}
	public void setShortShipmentSerialsBox(String shortShipmentSerialsBox) {
		this.shortShipmentSerialsBox = shortShipmentSerialsBox;
	}
	public String getExtraSerialsNoBox() {
		return extraSerialsNoBox;
	}
	public void setExtraSerialsNoBox(String extraSerialsNoBox) {
		this.extraSerialsNoBox = extraSerialsNoBox;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<ProductMasterDto> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductMasterDto> productList) {
		this.productList = productList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<WrongShipmentDTO> getDcNoList() {
		return dcNoList;
	}
	public void setDcNoList(List<WrongShipmentDTO> dcNoList) {
		this.dcNoList = dcNoList;
	}
	public String getDcId() {
		return dcId;
	}
	public void setDcId(String dcId) {
		this.dcId = dcId;
	}
	public String getDeliveryChallanNo() {
		return deliveryChallanNo;
	}
	public void setDeliveryChallanNo(String deliveryChallanNo) {
		this.deliveryChallanNo = deliveryChallanNo;
	}
	public List<DPPoAcceptanceBulkDTO> getAvailableSerialList() {
		return availableSerialList;
	}
	public void setAvailableSerialList(List<DPPoAcceptanceBulkDTO> availableSerialList) {
		this.availableSerialList = availableSerialList;
	}
	public String getTransMessage() {
		return transMessage;
	}
	public void setTransMessage(String transMessage) {
		this.transMessage = transMessage;
	}
	public FormFile getPOfile() {
		return POfile;
	}
	public void setPOfile(FormFile ofile) {
		POfile = ofile;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public List getError_file() {
		return error_file;
	}
	public void setError_file(List error_file) {
		this.error_file = error_file;
	}
	public String getError_flag_wrong_short() {
		return error_flag_wrong_short;
	}
	public void setError_flag_wrong_short(String error_flag_wrong_short) {
		this.error_flag_wrong_short = error_flag_wrong_short;
	}
	public String[] getArrProduct_id() {
		return arrProduct_id;
	}
	public void setArrProduct_id(String[] arrProduct_id) {
		this.arrProduct_id = arrProduct_id;
	}
	public String[] getArrShort_sr_no() {
		return arrShort_sr_no;
	}
	public void setArrShort_sr_no(String[] arrShort_sr_no) {
		this.arrShort_sr_no = arrShort_sr_no;
	}
	public String[] getArr_available_sr_no() {
		return arr_available_sr_no;
	}
	public void setArr_available_sr_no(String[] arr_available_sr_no) {
		this.arr_available_sr_no = arr_available_sr_no;
	}
	public FormFile getStbFile() {
		return stbFile;
	}
	public void setStbFile(FormFile stbFile) {
		this.stbFile = stbFile;
	}
	
	public List<String[]> getOutput() {
		return output;
	}
	public void setOutput(List<String[]> output) {
		this.output = output;
	}
	public List<String> getHeadersOne() {
		return headersOne;
	}
	public void setHeadersOne(List<String> headersOne) {
		this.headersOne = headersOne;
	}
	public List<String> getHeadersTwo() {
		return headersTwo;
	}
	public void setHeadersTwo(List<String> headersTwo) {
		this.headersTwo = headersTwo;
	}

	
}
