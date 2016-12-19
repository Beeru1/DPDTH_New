package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.STBWiseSerializedStockReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class STBWiseSerializedStockReportFormBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;

	private String  groupId;
	private String circleId ="";
	private String tsmId;	
	private String distId;
	private String fseId;
	private String retId;
	private String date	;
	private String productId = "";
	private String stbStatus= "";
	private List<SelectionCollection> arrCIList=null;
	private List<SelectionCollection> distList=null;
	private List<SelectionCollection> fseList=null;
	private List<SelectionCollection> retList =null;
	private List<SelectionCollection> tsmList =null;
	private List<SelectionCollection> stbStatusList =null;
	private List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();
	List<STBWiseSerializedStockReportDTO> reportStockDataList=new ArrayList<STBWiseSerializedStockReportDTO>();


	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenProductSeletIds;
	private String hiddenFseSelecIds;
	private String hiddenRetSeletIds;
	private String hiddenStbStatus;
	
	
	public String getHiddenProductSeletIds() {
		return hiddenProductSeletIds;
	}
	public void setHiddenProductSeletIds(String hiddenProductSeletIds) {
		this.hiddenProductSeletIds = hiddenProductSeletIds;
	}
	
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getShowDist() {
		return showDist;
	}
	public void setShowDist(String showDist) {
		this.showDist = showDist;
	}
	public String getShowTSM() {
		return showTSM;
	}
	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}
	public List<SelectionCollection> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<SelectionCollection> circleList) {
		this.circleList = circleList;
	}

	
	private String hiddenProductTypeSelec;
	private String hiddenRetSelecIds;
	private String hiddenStbSelecIds;
	
			

	public String getHiddenStbSelecIds() {
		return hiddenStbSelecIds;
	}
	public void setHiddenStbSelecIds(String hiddenStbSelecIds) {
		this.hiddenStbSelecIds = hiddenStbSelecIds;
	}
	public List<SelectionCollection> getTsmList() {
		return tsmList;
	}
	public void setTsmList(List<SelectionCollection> tsmList) {
		this.tsmList = tsmList;
	}
	public List<SelectionCollection> getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(List<SelectionCollection> arrCIList) {
		this.arrCIList = arrCIList;
	}

	public List<DpProductCategoryDto> getProductList() {
		return productList;
	}
	public void setProductList(List<DpProductCategoryDto> productList) {
		this.productList = productList;
	}

	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public List<SelectionCollection> getDistList() {
		return distList;
	}
	public void setDistList(List<SelectionCollection> distList) {
		this.distList = distList;
	}
	public List<SelectionCollection> getFseList() {
		return fseList;
	}
	public void setFseList(List<SelectionCollection> fseList) {
		this.fseList = fseList;
	}
	public String getFseId() {
		return fseId;
	}
	public void setFseId(String fseId) {
		this.fseId = fseId;
	}
	public String getRetId() {
		return retId;
	}
	public void setRetId(String retId) {
		this.retId = retId;
	}
	public List<SelectionCollection> getRetList() {
		return retList;
	}
	public void setRetList(List<SelectionCollection> retList) {
		this.retList = retList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<STBWiseSerializedStockReportDTO> getReportStockDataList() {
		return reportStockDataList;
	}
	public void setReportStockDataList(
			List<STBWiseSerializedStockReportDTO> reportStockDataList) {
		this.reportStockDataList = reportStockDataList;
	}
	public String getStbStatus() {
		return stbStatus;
	}
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}
	public List<SelectionCollection> getStbStatusList() {
		return stbStatusList;
	}
	public void setStbStatusList(List<SelectionCollection> stbStatusList) {
		this.stbStatusList = stbStatusList;
	}

	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	
	
	public String getHiddenRetSeletIds() {
		return hiddenRetSeletIds;
	}
	public void setHiddenRetSeletIds(String hiddenRetSeletIds) {
		this.hiddenRetSeletIds = hiddenRetSeletIds;
	}
	public String getHiddenStbStatus() {
		return hiddenStbStatus;
	}
	public void setHiddenStbStatus(String hiddenStbStatus) {
		this.hiddenStbStatus = hiddenStbStatus;
	}

	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenFseSelecIds() {
		return hiddenFseSelecIds;
	}
	public void setHiddenFseSelecIds(String hiddenFseSelecIds) {
		this.hiddenFseSelecIds = hiddenFseSelecIds;
	}
	public String getHiddenProductTypeSelec() {
		return hiddenProductTypeSelec;
	}
	public void setHiddenProductTypeSelec(String hiddenProductTypeSelec) {
		this.hiddenProductTypeSelec = hiddenProductTypeSelec;
	}
	public String getHiddenRetSelecIds() {
		return hiddenRetSelecIds;
	}
	public void setHiddenRetSelecIds(String hiddenRetSelecIds) {
		this.hiddenRetSelecIds = hiddenRetSelecIds;
	}
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}



}
