package com.ibm.dp.dto;

import java.util.ArrayList;
import java.util.List;

public class NonSerializedToSerializedDTO {
	
	
	//added by aman
	private String olmId="";
	private String distName="";
	private String tsmOlmId="";
	private String tsmName="";
	private String circle="";
	private List list_srno = new ArrayList();
	private List list_productId = new ArrayList();
	
	public List getList_productId() {
		return list_productId;
	}
	public void setList_productId(List list_productId) {
		this.list_productId = list_productId;
	}
	public List getList_srno() {
		return list_srno;
	}
	public void setList_srno(List list_srno) {
		this.list_srno = list_srno;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	private String circleId="";
	private String strStatus;
	private String strMessage;
	private String err_msg;
	private String serial_no;
	private String productName;
	private String productId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
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
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getOlmId() {
		return olmId;
	}
	public void setOlmId(String olmId) {
		this.olmId = olmId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getTsmOlmId() {
		return tsmOlmId;
	}
	public void setTsmOlmId(String tsmOlmId) {
		this.tsmOlmId = tsmOlmId;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	
	//end of changes by aman

}
