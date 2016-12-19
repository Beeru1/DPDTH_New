package com.ibm.dp.dto;

import java.io.Serializable;

public class DPPrintBulkAcceptanceDTO implements Serializable {
	private String serial_no="";
	private String product_name="";
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
}
