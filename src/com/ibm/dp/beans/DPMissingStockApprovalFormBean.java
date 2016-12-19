package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DCNoListDto;
import com.ibm.dp.dto.DPMissingStockApprovalDTO;

public class DPMissingStockApprovalFormBean extends ActionForm
{
	private String methodName;
	private List<DPMissingStockApprovalDTO> missingStockList;
	private List<DPMissingStockApprovalDTO> listAction;
	private String strStatus;
	private String strCheckedMSA;
	private String strSuccessMsg;
	private String assignValues;
	private String dcNo;
	private List<DCNoListDto> lstDc =new ArrayList<DCNoListDto>();
	private ArrayList poList = null;
	private String po_no;
	private String poNo;
	private String poNo1;
	private String childStatus;
	private String hiddenChildStatus;
	//Added by shilpa on 08-09-2012
	private String hidCheckedMSA;
	public String getChildStatus() {
		return childStatus;
	}

	public void setChildStatus(String childStatus) {
		this.childStatus = childStatus;
	}

	public ArrayList getPoList() {
		return poList;
	}

	public void setPoList(ArrayList poList) {
		this.poList = poList;
	}

	public List<DPMissingStockApprovalDTO> getMissingStockList() {
		return missingStockList;
	}

	public void setMissingStockList(
			List<DPMissingStockApprovalDTO> missingStockList) {
		this.missingStockList = missingStockList;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public List<DPMissingStockApprovalDTO> getListAction() {
		return listAction;
	}

	public void setListAction(List<DPMissingStockApprovalDTO> listAction) {
		this.listAction = listAction;
	}

	public String getStrCheckedMSA() {
		return strCheckedMSA;
	}

	public void setStrCheckedMSA(String strCheckedMSA) {
		this.strCheckedMSA = strCheckedMSA;
	}

	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}

	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}

	public List<DCNoListDto> getLstDc() {
		return lstDc;
	}

	public void setLstDc(List<DCNoListDto> lstDc) {
		this.lstDc = lstDc;
	}

	public String getDcNo() {
		return dcNo;
	}

	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}

	public String getAssignValues() {
		return assignValues;
	}

	public void setAssignValues(String assignValues) {
		this.assignValues = assignValues;
	}

	public String getPo_no() {
		return po_no;
	}

	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}

	public String getHiddenChildStatus() {
		return hiddenChildStatus;
	}

	public void setHiddenChildStatus(String hiddenChildStatus) {
		this.hiddenChildStatus = hiddenChildStatus;
	}

	public String getPoNo1() {
		return poNo1;
	}

	public void setPoNo1(String poNo1) {
		this.poNo1 = poNo1;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getHidCheckedMSA() {
		return hidCheckedMSA;
	}

	public void setHidCheckedMSA(String hidCheckedMSA) {
		this.hidCheckedMSA = hidCheckedMSA;
	}

	
	

	
	
}
