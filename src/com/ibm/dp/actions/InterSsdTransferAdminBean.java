package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.TSMDto;

public class InterSsdTransferAdminBean  extends ValidatorForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String circleName;
	private String circleIdNew;
	private String fromTsmId;
	private String toTsmId;
	private String fromDistId;
	private String toDistId;
	private String productIdNew;
	private String productName;
	private int availableProdQty;
	private String transfrProdQty;
	private String selectedStbs;
	private String jsArrprodId[];
	private String jsArrprodName[];
	private String jsArrTransfrProdQty[];
	private String jsArrTransfrStbs[];
	private String methodName;
	private String message;
	private boolean alreadyProExist;
	private String tableProdId ;
	String[] serialNo = null;
	private List<CircleDto>  circleList  = new ArrayList<CircleDto>();
	private List<CircleDto> busCatList= new ArrayList<CircleDto>();
	private List<ProductMasterDto>  productList  = new ArrayList<ProductMasterDto>();
	private List<TSMDto>  fromTSMList  = new ArrayList<TSMDto>();
	private List<TSMDto>  toTSMList  = new ArrayList<TSMDto>();
	private List<DistributorDTO>  fromDistList  = new ArrayList<DistributorDTO>();
	private List<DistributorDTO>  toDistList  = new ArrayList<DistributorDTO>();
	private List arrAvailableStb = new ArrayList();
	private int businessCat;
	private String strMessage;
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String[] getJsArrTransfrStbs() {
		return jsArrTransfrStbs;
	}
	public void setJsArrTransfrStbs(String[] jsArrTransfrStbs) {
		this.jsArrTransfrStbs = jsArrTransfrStbs;
	}
	public int getAvailableProdQty() {
		return availableProdQty;
	}
	public void setAvailableProdQty(int availableProdQty) {
		this.availableProdQty = availableProdQty;
	}
	
	public String getCircleIdNew() {
		return circleIdNew;
	}
	public void setCircleIdNew(String circleIdNew) {
		this.circleIdNew = circleIdNew;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	public String getFromTsmId() {
		return fromTsmId;
	}
	public void setFromTsmId(String fromTsmId) {
		this.fromTsmId = fromTsmId;
	}
	public String[] getJsArrprodId() {
		return jsArrprodId;
	}
	public void setJsArrprodId(String[] jsArrprodId) {
		this.jsArrprodId = jsArrprodId;
	}
	public String[] getJsArrTransfrProdQty() {
		return jsArrTransfrProdQty;
	}
	public void setJsArrTransfrProdQty(String[] jsArrTransfrProdQty) {
		this.jsArrTransfrProdQty = jsArrTransfrProdQty;
	}
	
	public String getProductIdNew() {
		return productIdNew;
	}
	public void setProductIdNew(String productIdNew) {
		this.productIdNew = productIdNew;
	}
	public String getSelectedStbs() {
		return selectedStbs;
	}
	public void setSelectedStbs(String selectedStbs) {
		this.selectedStbs = selectedStbs;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	public String getToTsmId() {
		return toTsmId;
	}
	public void setToTsmId(String toTsmId) {
		this.toTsmId = toTsmId;
	}
	public String getTransfrProdQty() {
		return transfrProdQty;
	}
	public void setTransfrProdQty(String transfrProdQty) {
		this.transfrProdQty = transfrProdQty;
	}
	public List<CircleDto> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<CircleDto> circleList) {
		this.circleList = circleList;
	}
	public List<DistributorDTO> getFromDistList() {
		return fromDistList;
	}
	public void setFromDistList(List<DistributorDTO> fromDistList) {
		this.fromDistList = fromDistList;
	}
	public List<TSMDto> getFromTSMList() {
		return fromTSMList;
	}
	public void setFromTSMList(List<TSMDto> fromTSMList) {
		this.fromTSMList = fromTSMList;
	}
	public List<ProductMasterDto> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductMasterDto> productList) {
		this.productList = productList;
	}
	public List<DistributorDTO> getToDistList() {
		return toDistList;
	}
	public void setToDistList(List<DistributorDTO> toDistList) {
		this.toDistList = toDistList;
	}
	public List<TSMDto> getToTSMList() {
		return toTSMList;
	}
	public void setToTSMList(List<TSMDto> toTSMList) {
		this.toTSMList = toTSMList;
	}
	public List getArrAvailableStb() {
		return arrAvailableStb;
	}
	public void setArrAvailableStb(List arrAvailableStb) {
		this.arrAvailableStb = arrAvailableStb;
	}
	public String[] getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String[] serialNo) {
		this.serialNo = serialNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public boolean isAlreadyProExist() {
		return alreadyProExist;
	}
	public void setAlreadyProExist(boolean alreadyProExist) {
		this.alreadyProExist = alreadyProExist;
	}
	public String getTableProdId() {
		return tableProdId;
	}
	public void setTableProdId(String tableProdId) {
		this.tableProdId = tableProdId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getJsArrprodName() {
		return jsArrprodName;
	}
	public void setJsArrprodName(String[] jsArrprodName) {
		this.jsArrprodName = jsArrprodName;
	}
	public int getBusinessCat() {
		return businessCat;
	}
	public void setBusinessCat(int businessCat) {
		this.businessCat = businessCat;
	}
	public List<CircleDto> getBusCatList() {
		return busCatList;
	}
	public void setBusCatList(List<CircleDto> busCatList) {
		this.busCatList = busCatList;
	}
	
	
	

}
