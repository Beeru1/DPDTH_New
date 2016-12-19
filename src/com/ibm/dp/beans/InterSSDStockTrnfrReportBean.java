package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.InterSSDStockTrnfrReportDto;

public class InterSSDStockTrnfrReportBean extends ActionForm{
	private String circleId;
	private String fromDate = "";
	private String toDate = "";
	private int circleid = -1;
	private String showCircle="";
	private ArrayList arrCIList=null;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenSTBTypeSelec;
	private String hiddenCollectionTypeSelec;
	private String dateOption;
	
	private String dcNo;
	private String transferType;
	List<DpProductCategoryDto> dcProductCategListDTO;
	private List<InterSSDStockTrnfrReportDto> reportDataList;
	public ArrayList getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public List<InterSSDStockTrnfrReportDto> getReportDataList() {
		return reportDataList;
	}
	public void setReportDataList(List<InterSSDStockTrnfrReportDto> reportDataList) {
		this.reportDataList = reportDataList;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getHiddenCollectionTypeSelec() {
		return hiddenCollectionTypeSelec;
	}
	public void setHiddenCollectionTypeSelec(String hiddenCollectionTypeSelec) {
		this.hiddenCollectionTypeSelec = hiddenCollectionTypeSelec;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenSTBTypeSelec() {
		return hiddenSTBTypeSelec;
	}
	public void setHiddenSTBTypeSelec(String hiddenSTBTypeSelec) {
		this.hiddenSTBTypeSelec = hiddenSTBTypeSelec;
	}
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	public List<DpProductCategoryDto> getDcProductCategListDTO() {
		return dcProductCategListDTO;
	}
	public void setDcProductCategListDTO(
			List<DpProductCategoryDto> dcProductCategListDTO) {
		this.dcProductCategListDTO = dcProductCategListDTO;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

}
