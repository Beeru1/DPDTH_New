package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.WrongShipmentDTO;
/**
 * 
 * @author Rohit kunder
 *	Bean class for report poc with S&D
 *
 */
public class DPWrongShipmentBean extends ActionForm
{
	private String wrongSerialsNos ="";
	private String allSerialsNos="";
	private String serialID="";
	private String invoiceNo="";
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
	private String hidtkprint="";
	
	private List<WrongShipmentDTO> productList = new ArrayList<WrongShipmentDTO>();
	private List<WrongShipmentDTO> shortShipmentSerialsList = new ArrayList<WrongShipmentDTO>();
	private List<WrongShipmentDTO> assignedSerialsSerialsList = new ArrayList<WrongShipmentDTO>();
	private	List<WrongShipmentDTO> invoiceList = new ArrayList<WrongShipmentDTO>();
	private	List<WrongShipmentDTO> dcNoList = new ArrayList<WrongShipmentDTO>();
	private List<WrongShipmentDTO> availableSerialList = new ArrayList<WrongShipmentDTO>();
		
	
	
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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
	public List<WrongShipmentDTO> getProductList() {
		return productList;
	}
	public void setProductList(List<WrongShipmentDTO> productList) {
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
	public List<WrongShipmentDTO> getAvailableSerialList() {
		return availableSerialList;
	}
	public void setAvailableSerialList(List<WrongShipmentDTO> availableSerialList) {
		this.availableSerialList = availableSerialList;
	}
	public String getTransMessage() {
		return transMessage;
	}
	public void setTransMessage(String transMessage) {
		this.transMessage = transMessage;
	}
	public String getHidtkprint() {
		return hidtkprint;
	}
	public void setHidtkprint(String hidtkprint) {
		this.hidtkprint = hidtkprint;
	}

	
}
