package com.ibm.dp.dto;
import java.io.Serializable;
public class DiststockAccpTransferDTO  implements Serializable{
	
	private String strTransFrom;
	private String strDCNo;
	private String strDCDate;
	private int fromDist_Id=0;
	private String serial_No="";
	private String prodName="";
	private int prodId=0;
	/**
	 * @return the prodId
	 */
	public int getProdId() {
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
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
	 * @return the serial_No
	 */
	public String getSerial_No() {
		return serial_No;
	}
	/**
	 * @param serial_No the serial_No to set
	 */
	public void setSerial_No(String serial_No) {
		this.serial_No = serial_No;
	}
	/**
	 * @return the fromDist_Id
	 */
	public int getFromDist_Id() {
		return fromDist_Id;
	}
	/**
	 * @param fromDist_Id the fromDist_Id to set
	 */
	public void setFromDist_Id(int fromDist_Id) {
		this.fromDist_Id = fromDist_Id;
	}
	/**
	 * @return the strDCDate
	 */
	public String getStrDCDate() {
		return strDCDate;
	}
	/**
	 * @param strDCDate the strDCDate to set
	 */
	public void setStrDCDate(String strDCDate) {
		this.strDCDate = strDCDate;
	}
	/**
	 * @return the strDCNo
	 */
	public String getStrDCNo() {
		return strDCNo;
	}
	/**
	 * @param strDCNo the strDCNo to set
	 */
	public void setStrDCNo(String strDCNo) {
		this.strDCNo = strDCNo;
	}
	/**
	 * @return the strTransFrom
	 */
	public String getStrTransFrom() {
		return strTransFrom;
	}
	/**
	 * @param strTransFrom the strTransFrom to set
	 */
	public void setStrTransFrom(String strTransFrom) {
		this.strTransFrom = strTransFrom;
	}

}
