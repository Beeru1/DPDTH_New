package com.ibm.dp.dto;

import java.util.ArrayList;
import java.util.List;

public class DPPoAcceptanceBulkDTO {
	
	private String serial_no="";
	private String err_msg="";
	private int product_id=0;
	private String product_name="";
	private String dc_no="";
	private String inv_no="";
	private String totalSerialNumbers=""; 
	private String[] shortSrNo;
	
	List<DPPoAcceptanceBulkDTO> short_sr_no_list= new ArrayList<DPPoAcceptanceBulkDTO>();
	List<DPPoAcceptanceBulkDTO> extra_sr_no_list= new ArrayList<DPPoAcceptanceBulkDTO>();
	List<DPPoAcceptanceBulkDTO> available_sr_no_list= new ArrayList<DPPoAcceptanceBulkDTO>();
	List<DPPoAcceptanceBulkDTO> totalSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();
	
	private List list_srno = new ArrayList();
	
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public List getList_srno() {
		return list_srno;
	}
	public void setList_srno(List list_srno) {
		this.list_srno = list_srno;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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
	public List<DPPoAcceptanceBulkDTO> getAvailable_sr_no_list() {
		return available_sr_no_list;
	}
	public void setAvailable_sr_no_list(
			List<DPPoAcceptanceBulkDTO> available_sr_no_list) {
		this.available_sr_no_list = available_sr_no_list;
	}
	public String getTotalSerialNumbers() {
		return totalSerialNumbers;
	}
	public void setTotalSerialNumbers(String totalSerialNumbers) {
		this.totalSerialNumbers = totalSerialNumbers;
	}
	public List<DPPoAcceptanceBulkDTO> getTotalSrNoList() {
		return totalSrNoList;
	}
	public void setTotalSrNoList(List<DPPoAcceptanceBulkDTO> totalSrNoList) {
		this.totalSrNoList = totalSrNoList;
	}
	public String[] getShortSrNo() {
		return shortSrNo;
	}
	public void setShortSrNo(String[] shortSrNo) {
		this.shortSrNo = shortSrNo;
	}
	public String getDc_no() {
		return dc_no;
	}
	public void setDc_no(String dc_no) {
		this.dc_no = dc_no;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	

}
