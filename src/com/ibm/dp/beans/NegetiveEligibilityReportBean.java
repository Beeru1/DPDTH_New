package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.NegetiveEligibilityDTO;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class NegetiveEligibilityReportBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private int circleid = -1;
	private List<SelectionCollection> arrCIList=null;
	List<NegetiveEligibilityDTO> reportStockDataList=new ArrayList<NegetiveEligibilityDTO>();
	
	private String hiddenCircleSelecIds;
	
	public List<SelectionCollection> getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(List<SelectionCollection> arrCIList) {
		this.arrCIList = arrCIList;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}

	public List<NegetiveEligibilityDTO> getReportStockDataList() {
		return reportStockDataList;
	}
	public void setReportStockDataList(List<NegetiveEligibilityDTO> reportStockDataList) {
		this.reportStockDataList = reportStockDataList;
	}

	
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}


}
