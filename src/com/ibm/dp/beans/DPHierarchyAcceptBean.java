package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPHierarchyAcceptDTO;

public class DPHierarchyAcceptBean  extends ActionForm
{
	String strSuccessMsg = null;
	String methodName = null;
	String strCheckedTR = null;
	String strStockCount = null;
	
	List<DPHierarchyAcceptDTO> listTransferAccept = null;
	List<DPHierarchyAcceptDTO> listHierarchyView = null;
	List<DPHierarchyAcceptDTO> list_stock_details= null;
	
	
	public List<DPHierarchyAcceptDTO> getListTransferAccept() {
		return listTransferAccept;
	}
	public void setListTransferAccept(List<DPHierarchyAcceptDTO> listTransferAccept) {
		this.listTransferAccept = listTransferAccept;
	}
	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}
	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}
	public String getStrCheckedTR() {
		return strCheckedTR;
	}
	public void setStrCheckedTR(String strCheckedTR) {
		this.strCheckedTR = strCheckedTR;
	}
	public List<DPHierarchyAcceptDTO> getListHierarchyView() {
		return listHierarchyView;
	}
	public void setListHierarchyView(List<DPHierarchyAcceptDTO> listHierarchyView) {
		listHierarchyView = listHierarchyView;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<DPHierarchyAcceptDTO> getList_stock_details() {
		return list_stock_details;
	}
	public void setList_stock_details(List<DPHierarchyAcceptDTO> list_stock_details) {
		this.list_stock_details = list_stock_details;
	}
	public String getStrStockCount() {
		return strStockCount;
	}
	public void setStrStockCount(String strStockCount) {
		this.strStockCount = strStockCount;
	}
	
	

}
