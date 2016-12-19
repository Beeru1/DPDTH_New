package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPMissingStockApprovalDTO;
import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpReverseInvetryChangeDTO;
/**
 * 
 * @author harbans
 *	Bean class for reverse collection
 *
 */
public class DPReverseCollectionBean extends ActionForm
{
	public List<DPReverseCollectionDto>  reverseCollectionList  = new ArrayList<DPReverseCollectionDto>();
	public List<DPReverseCollectionDto>  collectionDefectList  = new ArrayList<DPReverseCollectionDto>();
	public List<DPReverseCollectionDto>  collectSerialsList  = new ArrayList<DPReverseCollectionDto>();
	public List<DPReverseCollectionDto>  upgradeCollectionList  = new ArrayList<DPReverseCollectionDto>();
	
	public String[] jsArrCollectionId;
	public String[] jsArrDefectId;
	public String[] jsArrProductId;
	public String[] jsArrSerails;
	public String[] jsArrRemarks;
	//Adde by Shilpa for new format
	public String[] jsArrNewSrNo;
	public String[] jsArrNewProduct;
	public String[] jsArrAgeing;
	public String[] jsArrInventoryCahngDate;
	public String[] jsArrCustomerId;
	public String[] jsArrReqId;
	public List<DPPrintDCDTO>  dcDetailsCollectionList  = new ArrayList<DPPrintDCDTO>();
	//Added by Shilpa Khanna on 9-8-2012
	public List<DPPrintDCDTO>  dcDetailsCollectionListChurn  = new ArrayList<DPPrintDCDTO>();
	//Ends here 
	private String dc_no;
	private String dc_date;
	private String from_name;
	private String city_name;
	private String state_name;
	private String contact_name;
	private String mobile_no;
	private String dc_remarks;
	private String takePrint="";;
	private String wh_name="";
	private String wh_address="";
	private String wh_phone="";
	private String courier_agency="";
	private String docket_no="";
	public String collectionId = "";
	public String collectionName = "";
	public String defectId = "";
	public String defectName = "";
	public String productId = "";
	public String productName = "";
	public String collectSerialNo = "";
	public String collectSerialsNoBox ="";
	public String remarks ="";
	public String collectDAOFlag ="";
	public String collectUniqueFlag = "";
	public String collectCircleFlag = "";
	public String collectC2SFlag = "";
	public String testParam = "";
	public String returnurl = "";
	
	public String churnDcGenerationLess = "";
	public String churnDcGenerationMore = "";
	
	public String message ="";
	
	public List<DpReverseInvetryChangeDTO>  inventoryChangeList  = new ArrayList<DpReverseInvetryChangeDTO>();
	private List<DpReverseInvetryChangeDTO> listDefect;
	public int typedist;
	
	public int getTypedist() {
		return typedist;
	}
	public void setTypedist(int typedist) {
		this.typedist = typedist;
	}
	public String getTakePrint() {
		return takePrint;
	}
	public void setTakePrint(String takePrint) {
		this.takePrint = takePrint;
	}
	public String getCollectSerialsNoBox() {
		return collectSerialsNoBox;
	}
	public void setCollectSerialsNoBox(String collectSerialsNoBox) {
		this.collectSerialsNoBox = collectSerialsNoBox;
	}
	public String getCollectSerialNo() {
		return collectSerialNo;
	}
	public void setCollectSerialNo(String collectSerialNo) {
		this.collectSerialNo = collectSerialNo;
	}
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public String getDefectName() {
		return defectName;
	}
	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public List<DPReverseCollectionDto> getReverseCollectionList() {
		return reverseCollectionList;
	}
	public void setReverseCollectionList(
			List<DPReverseCollectionDto> reverseCollectionList) {
		this.reverseCollectionList = reverseCollectionList;
	}
	public List<DPReverseCollectionDto> getCollectionDefectList() {
		return collectionDefectList;
	}
	public void setCollectionDefectList(
			List<DPReverseCollectionDto> collectionDefectList) {
		this.collectionDefectList = collectionDefectList;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<DPReverseCollectionDto> getCollectSerialsList() {
		return collectSerialsList;
	}
	public void setCollectSerialsList(
			List<DPReverseCollectionDto> collectSerialsList) {
		this.collectSerialsList = collectSerialsList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[] getJsArrCollectionId() {
		return jsArrCollectionId;
	}
	public void setJsArrCollectionId(String[] jsArrCollectionId) {
		this.jsArrCollectionId = jsArrCollectionId;
	}
	public String[] getJsArrDefectId() {
		return jsArrDefectId;
	}
	public void setJsArrDefectId(String[] jsArrDefectId) {
		this.jsArrDefectId = jsArrDefectId;
	}
	public String[] getJsArrProductId() {
		return jsArrProductId;
	}
	public void setJsArrProductId(String[] jsArrProductId) {
		this.jsArrProductId = jsArrProductId;
	}
	public String[] getJsArrRemarks() {
		return jsArrRemarks;
	}
	public void setJsArrRemarks(String[] jsArrRemarks) {
		this.jsArrRemarks = jsArrRemarks;
	}
	public String[] getJsArrSerails() {
		return jsArrSerails;
	}
	public void setJsArrSerails(String[] jsArrSerails) {
		this.jsArrSerails = jsArrSerails;
	}

	public List<DPPrintDCDTO> getDcDetailsCollectionList() {
		return dcDetailsCollectionList;
	}
	public void setDcDetailsCollectionList(
			List<DPPrintDCDTO> dcDetailsCollectionList) {
		this.dcDetailsCollectionList = dcDetailsCollectionList;
	}
		public String getCollectDAOFlag() {
		return collectDAOFlag;
	}
	public void setCollectDAOFlag(String collectDAOFlag) {
		this.collectDAOFlag = collectDAOFlag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCollectUniqueFlag() {
		return collectUniqueFlag;
	}
	public void setCollectUniqueFlag(String collectUniqueFlag) {
		this.collectUniqueFlag = collectUniqueFlag;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getDc_no() {
		return dc_no;
	}
	public void setDc_no(String dc_no) {
		this.dc_no = dc_no;
	}
	public String getFrom_name() {
		return from_name;
	}
	public void setFrom_name(String from_name) {
		this.from_name = from_name;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getDc_remarks() {
		return dc_remarks;
	}
	public void setDc_remarks(String dc_remarks) {
		this.dc_remarks = dc_remarks;
	}
	public String getCollectCircleFlag() {
		return collectCircleFlag;
	}
	public void setCollectCircleFlag(String collectCircleFlag) {
		this.collectCircleFlag = collectCircleFlag;
	}
	public String getReturnurl() {
		return returnurl;
	}
	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}
	public String getTestParam() {
		return testParam;
	}
	public void setTestParam(String testParam) {
		this.testParam = testParam;
	}
	public String getCollectC2SFlag() {
		return collectC2SFlag;
	}
	public void setCollectC2SFlag(String collectC2SFlag) {
		this.collectC2SFlag = collectC2SFlag;
	}
	public List<DpReverseInvetryChangeDTO> getInventoryChangeList() {
		return inventoryChangeList;
	}
	public void setInventoryChangeList(
			List<DpReverseInvetryChangeDTO> inventoryChangeList) {
		this.inventoryChangeList = inventoryChangeList;
	}
	public List<DpReverseInvetryChangeDTO> getListDefect() {
		return listDefect;
	}
	public void setListDefect(List<DpReverseInvetryChangeDTO> listDefect) {
		this.listDefect = listDefect;
	}
	public String[] getJsArrAgeing() {
		return jsArrAgeing;
	}
	public void setJsArrAgeing(String[] jsArrAgeing) {
		this.jsArrAgeing = jsArrAgeing;
	}
	public String[] getJsArrInventoryCahngDate() {
		return jsArrInventoryCahngDate;
	}
	public void setJsArrInventoryCahngDate(String[] jsArrInventoryCahngDate) {
		this.jsArrInventoryCahngDate = jsArrInventoryCahngDate;
	}
	public String[] getJsArrNewProduct() {
		return jsArrNewProduct;
	}
	public void setJsArrNewProduct(String[] jsArrNewProduct) {
		this.jsArrNewProduct = jsArrNewProduct;
	}
	public String[] getJsArrNewSrNo() {
		return jsArrNewSrNo;
	}
	public void setJsArrNewSrNo(String[] jsArrNewSrNo) {
		this.jsArrNewSrNo = jsArrNewSrNo;
	}
	public String getDc_date() {
		return dc_date;
	}
	public void setDc_date(String dc_date) {
		this.dc_date = dc_date;
	}
	public List<DPReverseCollectionDto> getUpgradeCollectionList() {
		return upgradeCollectionList;
	}
	public void setUpgradeCollectionList(
			List<DPReverseCollectionDto> upgradeCollectionList) {
		this.upgradeCollectionList = upgradeCollectionList;
	}
	public String[] getJsArrCustomerId() {
		return jsArrCustomerId;
	}
	public void setJsArrCustomerId(String[] jsArrCustomerId) {
		this.jsArrCustomerId = jsArrCustomerId;
	}
	public String getCourier_agency() {
		return courier_agency;
	}
	public void setCourier_agency(String courier_agency) {
		this.courier_agency = courier_agency;
	}
	public String getDocket_no() {
		return docket_no;
	}
	public void setDocket_no(String docket_no) {
		this.docket_no = docket_no;
	}
	public String getWh_address() {
		return wh_address;
	}
	public void setWh_address(String wh_address) {
		this.wh_address = wh_address;
	}
	public String getWh_name() {
		return wh_name;
	}
	public void setWh_name(String wh_name) {
		this.wh_name = wh_name;
	}
	public String getWh_phone() {
		return wh_phone;
	}
	public void setWh_phone(String wh_phone) {
		this.wh_phone = wh_phone;
	}
	public String[] getJsArrReqId() {
		return jsArrReqId;
	}
	public void setJsArrReqId(String[] jsArrReqId) {
		this.jsArrReqId = jsArrReqId;
	}
	public List<DPPrintDCDTO> getDcDetailsCollectionListChurn() {
		return dcDetailsCollectionListChurn;
	}
	public void setDcDetailsCollectionListChurn(
			List<DPPrintDCDTO> dcDetailsCollectionListChurn) {
		this.dcDetailsCollectionListChurn = dcDetailsCollectionListChurn;
	}
	public String getChurnDcGenerationLess() {
		return churnDcGenerationLess;
	}
	public void setChurnDcGenerationLess(String churnDcGenerationLess) {
		this.churnDcGenerationLess = churnDcGenerationLess;
	}
	public String getChurnDcGenerationMore() {
		return churnDcGenerationMore;
	}
	public void setChurnDcGenerationMore(String churnDcGenerationMore) {
		this.churnDcGenerationMore = churnDcGenerationMore;
	}

	
}
