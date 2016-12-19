package com.ibm.dp.dto;

import java.io.Serializable;

public class DistStockAccpDisplayDTO implements Serializable{

	
	private String transDCNoText="";
	private String transFromText="";
	private String transTo="";
	private String quantity="";
	private String DCDate="";
	private String prodName="";
	private String SerialNo="";
	
	
	/**
	 * @return the dCDate
	 */
	public String getDCDate() {
		return DCDate;
	}
	/**
	 * @param date the dCDate to set
	 */
	public void setDCDate(String date) {
		DCDate = date;
	}
	/**
	 * @return the prodName
	 */
	public String getProdName() {
		return prodName;
	}
	/**
	 * @param prodName the prodName to set
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return SerialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}
	/**
	 * @return the transDCNoText
	 */
	public String getTransDCNoText() {
		return transDCNoText;
	}
	/**
	 * @param transDCNoText the transDCNoText to set
	 */
	public void setTransDCNoText(String transDCNoText) {
		this.transDCNoText = transDCNoText;
	}
	/**
	 * @return the transFromText
	 */
	public String getTransFromText() {
		return transFromText;
	}
	/**
	 * @param transFromText the transFromText to set
	 */
	public void setTransFromText(String transFromText) {
		this.transFromText = transFromText;
	}
	/**
	 * @return the transTo
	 */
	public String getTransTo() {
		return transTo;
	}
	/**
	 * @param transTo the transTo to set
	 */
	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}
}
