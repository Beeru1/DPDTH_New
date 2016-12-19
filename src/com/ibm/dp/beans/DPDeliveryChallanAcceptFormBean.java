package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class DPDeliveryChallanAcceptFormBean extends ActionForm
{
	private String methodName;
	private ArrayList deliveryChallanList;
	private String strCheckedDC;
	private String errMsg;

	public String getStrCheckedDC() {
		return strCheckedDC;
	}

	public void setStrCheckedDC(String strCheckedDC) {
		this.strCheckedDC = strCheckedDC;
	}

	public ArrayList getDeliveryChallanList() {
		return deliveryChallanList;
	}

	public void setDeliveryChallanList(ArrayList deliveryChallanList) {
		this.deliveryChallanList = deliveryChallanList;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public void reset()
	{
		
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
