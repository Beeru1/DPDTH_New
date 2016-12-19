package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;

public class DistributorHomeForm  extends ValidatorForm {

	String distName;
	String prodname;
	String openingBalence;	
	String receiptFrBotree;
	String receiptFrFSE;
	String salesToFSE;
	String closingbalence;
	ArrayList distStockSumm;
	//rajiv jha added for retailer start
	String receiptFrRET;
	String salesToRET;
	//rajiv jha added for retailer end
	
	
	public String getClosingbalence() {
		return closingbalence;
	}
	public void setClosingbalence(String closingbalence) {
		this.closingbalence = closingbalence;
	}
	
	public String getOpeningBalence() {
		return openingBalence;
	}
	public void setOpeningBalence(String openingBalence) {
		this.openingBalence = openingBalence;
	}
	
	public String getReceiptFrBotree() {
		return receiptFrBotree;
	}
	public void setReceiptFrBotree(String receiptFrBotree) {
		this.receiptFrBotree = receiptFrBotree;
	}
	public String getReceiptFrFSE() {
		return receiptFrFSE;
	}
	public void setReceiptFrFSE(String receiptFrFSE) {
		this.receiptFrFSE = receiptFrFSE;
	}
	public String getSalesToFSE() {
		return salesToFSE;
	}
	public void setSalesToFSE(String salesToFSE) {
		this.salesToFSE = salesToFSE;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public ArrayList getDistStockSumm() {
		return distStockSumm;
	}
	public void setDistStockSumm(ArrayList distStockSumm) {
		this.distStockSumm = distStockSumm;
	}
	//rajiv jha added start
	public String getReceiptFrRET() {
		return receiptFrRET;
	}
	public void setReceiptFrRET(String receiptFrRET) {
		this.receiptFrRET = receiptFrRET;
	}
	public String getSalesToRET() {
		return salesToRET;
	}
	public void setSalesToRET(String salesToRET) {
		this.salesToRET = salesToRET;
	}
    // rajiv jha added end
}
