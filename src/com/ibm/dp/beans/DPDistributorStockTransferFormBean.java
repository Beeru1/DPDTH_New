package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPDistributorStockTransferDTO;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.dpmisreports.common.SelectionCollection;

public class DPDistributorStockTransferFormBean extends ActionForm 
{
	private String methodName;
	private String strSuccessMsg;
	private List<DPDistributorStockTransferDTO> listStockTransfer;
	private Integer intFromDist;
	private List<DPDistributorStockTransferDTO> listDistributor;
	private Integer intProduct;
	private List<DPDistributorStockTransferDTO> listProduct;
	private List<DPDistributorStockTransferDTO> dcProductCategListDTO;
	private Integer intStockAvail;
	private Integer intToDist;
	private Integer intTransQty;
	private String circleIdNew;
	private String[] arrFromDistID;
	private String[] arrToDistID;
	private String[] arrProductID;
	private String[] arrTransQty;
	private String fromTsmId;
	private String toTsmId;
	private String fromDistId;
	private String toDistId;
	private String productIdNew;
	private int availableProdQty;
	private List arrCIList=new ArrayList();
	private String circleid ;
	private String showCircle="";
	private String product="";
	private String circleName;
	String tsmId;
	private String showTSM="";
	private String noShowTSM="";
	String distId;
	String distAccName;
	private List tsmList = new ArrayList();
	private List distList = new ArrayList();
	private String showDist="";	
	private String productId;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenSTBTypeSelec;
	private String loginId;
	
	private List<CircleDto>  circleList  = new ArrayList<CircleDto>();
	private List<CircleDto> busCatList= new ArrayList<CircleDto>();
	private List<ProductMasterDto>  productList  = new ArrayList<ProductMasterDto>();
	private List<TSMDto>  fromTSMList  = new ArrayList<TSMDto>();
	private List<TSMDto>  toTSMList  = new ArrayList<TSMDto>();
	private List<DistributorDTO>  fromDistList  = new ArrayList<DistributorDTO>();
	private int businessCat;
	
	
	public int getBusinessCat() {
		return businessCat;
	}

	public void setBusinessCat(int businessCat) {
		this.businessCat = businessCat;
	}

	public Integer getIntStockAvail() {
		return intStockAvail;
	}

	public List<CircleDto> getCircleList() {
		return circleList;
	}

	public void setCircleList(List<CircleDto> circleList) {
		this.circleList = circleList;
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

	public List<TSMDto> getToTSMList() {
		return toTSMList;
	}

	public void setToTSMList(List<TSMDto> toTSMList) {
		this.toTSMList = toTSMList;
	}

	public void setIntStockAvail(Integer intStockAvail) {
		this.intStockAvail = intStockAvail;
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

	public List<DPDistributorStockTransferDTO> getListStockTransfer() {
		return listStockTransfer;
	}

	public void setListStockTransfer(
			List<DPDistributorStockTransferDTO> listStockTransfer) {
		this.listStockTransfer = listStockTransfer;
	}

	public Integer getIntFromDist() {
		return intFromDist;
	}

	public void setIntFromDist(Integer intFromDist) {
		this.intFromDist = intFromDist;
	}

	public List<DPDistributorStockTransferDTO> getListDistributor() {
		return listDistributor;
	}

	public void setListDistributor(
			List<DPDistributorStockTransferDTO> listDistributor) {
		this.listDistributor = listDistributor;
	}

	public List<DPDistributorStockTransferDTO> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<DPDistributorStockTransferDTO> listProduct) {
		this.listProduct = listProduct;
	}

	public Integer getIntProduct() {
		return intProduct;
	}

	public void setIntProduct(Integer intProduct) {
		this.intProduct = intProduct;
	}

	public Integer getIntToDist() {
		return intToDist;
	}

	public void setIntToDist(Integer intToDist) {
		this.intToDist = intToDist;
	}

	public Integer getIntTransQty() {
		return intTransQty;
	}

	public void setIntTransQty(Integer intTransQty) {
		this.intTransQty = intTransQty;
	}

	public String[] getArrFromDistID() {
		return arrFromDistID;
	}

	public void setArrFromDistID(String[] arrFromDistID) {
		this.arrFromDistID = arrFromDistID;
	}

	public String[] getArrProductID() {
		return arrProductID;
	}

	public void setArrProductID(String[] arrProductID) {
		this.arrProductID = arrProductID;
	}

	public String[] getArrToDistID() {
		return arrToDistID;
	}

	public void setArrToDistID(String[] arrToDistID) {
		this.arrToDistID = arrToDistID;
	}

	public String[] getArrTransQty() {
		return arrTransQty;
	}

	public void setArrTransQty(String[] arrTransQty) {
		this.arrTransQty = arrTransQty;
	}

	public List getArrCIList() {
		return arrCIList;
	}

	public void setArrCIList(List arrCIList) {
		this.arrCIList = arrCIList;
	}

	public String getCircleid() {
		return circleid;
	}

	public void setCircleid(String circleid) {
		this.circleid = circleid;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getDistAccName() {
		return distAccName;
	}

	public void setDistAccName(String distAccName) {
		this.distAccName = distAccName;
	}

	public String getDistId() {
		return distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public List getDistList() {
		return distList;
	}

	public void setDistList(List distList) {
		this.distList = distList;
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

	public String getNoShowTSM() {
		return noShowTSM;
	}

	public void setNoShowTSM(String noShowTSM) {
		this.noShowTSM = noShowTSM;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getTsmId() {
		return tsmId;
	}

	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}

	public List getTsmList() {
		return tsmList;
	}

	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<DPDistributorStockTransferDTO> getDcProductCategListDTO() {
		return dcProductCategListDTO;
	}

	public void setDcProductCategListDTO(
			List<DPDistributorStockTransferDTO> dcProductCategListDTO) {
		this.dcProductCategListDTO = dcProductCategListDTO;
	}

	public List<DistributorDTO> getFromDistList() {
		return fromDistList;
	}

	public void setFromDistList(List<DistributorDTO> fromDistList) {
		this.fromDistList = fromDistList;
	}

	public String getCircleIdNew() {
		return circleIdNew;
	}

	public void setCircleIdNew(String circleIdNew) {
		this.circleIdNew = circleIdNew;
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

	public String getProductIdNew() {
		return productIdNew;
	}

	public void setProductIdNew(String productIdNew) {
		this.productIdNew = productIdNew;
	}

	public int getAvailableProdQty() {
		return availableProdQty;
	}

	public void setAvailableProdQty(int availableProdQty) {
		this.availableProdQty = availableProdQty;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List<CircleDto> getBusCatList() {
		return busCatList;
	}

	public void setBusCatList(List<CircleDto> busCatList) {
		this.busCatList = busCatList;
	}

}
