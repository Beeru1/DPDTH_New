package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.InterSSDReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class DPInterSSDStockTransferReportFormBean extends ActionForm{

	private List arrCIList=new ArrayList();
	private String circleid ;
	private String showCircle="";
	private String product="";
	private String circleName;
	private String strValue;
	private String strText;
	String accountName;
	String tsmId;
	private String dcNo;
	private String transferType;
	private String showTSM="";
	private String noShowTSM="";
	String distId;
	String distAccName;
	String fromDate;
	String toDate;
	String[] levelIdList=null;
	String trnsfrType;
	String dcno;
	String dateOption;
	private List<DpProductCategoryDto> dcProductCategListDTO;
	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();
	private List<InterSSDReportDTO> reportList =new ArrayList<InterSSDReportDTO>();
	
	private List tsmList = new ArrayList();
	private List distList = new ArrayList();
	private String showDist="";	
	private String productId;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenTrnsfrTypeSelec;
	private String hiddenDistSelecIds;
	private String hiddenSTBTypeSelec;
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}

	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}

	public String getHiddenTrnsfrTypeSelec() {
		return hiddenTrnsfrTypeSelec;
	}

	public void setHiddenTrnsfrTypeSelec(String hiddenTrnsfrTypeSelec) {
		this.hiddenTrnsfrTypeSelec = hiddenTrnsfrTypeSelec;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getShowDist() {
		return showDist;
	}

	public void setShowDist(String showDist) {
		this.showDist = showDist;
	}

	public List getTsmList() {
		return tsmList;
	}

	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
	}

	

	public String getCircleid() {
		return circleid;
	}

	public void setCircleid(String circleid) {
		this.circleid = circleid;
	}

	public String getShowCircle() {
		return showCircle;
	}

	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}

	

	

	public List getArrCIList() {
		return arrCIList;
	}

	public void setArrCIList(List arrCIList) {
		this.arrCIList = arrCIList;
	}

	

	public String getTsmId() {
		return tsmId;
	}

	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getNoShowTSM() {
		return noShowTSM;
	}

	public void setNoShowTSM(String noShowTSM) {
		this.noShowTSM = noShowTSM;
	}

	public String getShowTSM() {
		return showTSM;
	}

	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}

	public List getDistList() {
		return distList;
	}

	public void setDistList(List distList) {
		this.distList = distList;
	}

	

	public String getDistId() {
		return distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public String getDistAccName() {
		return distAccName;
	}

	public void setDistAccName(String distAccName) {
		this.distAccName = distAccName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String[] getLevelIdList() {
		return levelIdList;
	}

	public void setLevelIdList(String[] levelIdList) {
		this.levelIdList = levelIdList;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getDcno() {
		return dcno;
	}

	public void setDcno(String dcno) {
		this.dcno = dcno;
	}

	public String getTrnsfrType() {
		return trnsfrType;
	}

	public void setTrnsfrType(String trnsfrType) {
		this.trnsfrType = trnsfrType;
	}

	public String getStrText() {
		return strText;
	}

	public void setStrText(String strText) {
		this.strText = strText;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getDateOption() {
		return dateOption;
	}

	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}

	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}

	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}

	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}

	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<DpProductCategoryDto> getDcProductCategListDTO() {
		return dcProductCategListDTO;
	}

	public void setDcProductCategListDTO(
			List<DpProductCategoryDto> dcProductCategListDTO) {
		this.dcProductCategListDTO = dcProductCategListDTO;
	}

	public List<SelectionCollection> getCircleList() {
		return circleList;
	}

	public void setCircleList(List<SelectionCollection> circleList) {
		this.circleList = circleList;
	}

	public String getHiddenSTBTypeSelec() {
		return hiddenSTBTypeSelec;
	}

	public void setHiddenSTBTypeSelec(String hiddenSTBTypeSelec) {
		this.hiddenSTBTypeSelec = hiddenSTBTypeSelec;
	}

	public List<InterSSDReportDTO> getReportList() {
		return reportList;
	}

	public void setReportList(List<InterSSDReportDTO> reportList) {
		this.reportList = reportList;
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
