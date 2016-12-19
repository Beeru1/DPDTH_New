package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPOpenStockDepletionDTO;

public class DPOpenStockDepletionBean extends ActionForm
{
	String methodName;
	
	Integer intTSMID;
	Integer intDistributorID;
	Integer intProductID;
	Integer intCurrentStock;
	Integer intStockDeplete;
	String strSuccessMsg;
		
	List<DPOpenStockDepletionDTO> listTSM;
	List<DPOpenStockDepletionDTO> listDist;
	List<DPOpenStockDepletionDTO> listProduct;

	public Integer getIntDistributorID() {
		return intDistributorID;
	}

	public void setIntDistributorID(Integer intDistributorID) {
		this.intDistributorID = intDistributorID;
	}

	public Integer getIntProductID() {
		return intProductID;
	}

	public void setIntProductID(Integer intProductID) {
		this.intProductID = intProductID;
	}

	public Integer getIntTSMID() {
		return intTSMID;
	}

	public void setIntTSMID(Integer intTSMID) {
		this.intTSMID = intTSMID;
	}

	public List<DPOpenStockDepletionDTO> getListTSM() {
		return listTSM;
	}

	public void setListTSM(List<DPOpenStockDepletionDTO> listTSM) {
		this.listTSM = listTSM;
	}

	public List<DPOpenStockDepletionDTO> getListDist() {
		return listDist;
	}

	public void setListDist(List<DPOpenStockDepletionDTO> listDist) {
		this.listDist = listDist;
	}

	public List<DPOpenStockDepletionDTO> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<DPOpenStockDepletionDTO> listProduct) {
		this.listProduct = listProduct;
	}

	public Integer getIntCurrentStock() {
		return intCurrentStock;
	}

	public void setIntCurrentStock(Integer intCurrentStock) {
		this.intCurrentStock = intCurrentStock;
	}

	public Integer getIntStockDeplete() {
		return intStockDeplete;
	}

	public void setIntStockDeplete(Integer intStockDeplete) {
		this.intStockDeplete = intStockDeplete;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}

	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}
}
