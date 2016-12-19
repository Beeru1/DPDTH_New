package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.dp.dto.TransferPendingSTBDto;

public class TransferPendingSTBBean  extends ValidatorForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String successMessage="";
	private String circleName;
	private String fromCircleId;
	private String toCircleId;
	private String fromTsmId;
	private String toTsmId;
	private String fromDistId;
	private String toDistId;
	private String productIdNew;
	private String productName;
	
	private String methodName;	

	String[] serialNo = null;
	private List<TransferPendingSTBDto>  collectionList  = new ArrayList<TransferPendingSTBDto>();
	private List<CircleDto>  circleList  = new ArrayList<CircleDto>();
	private List<ProductMasterDto>  productList  = new ArrayList<ProductMasterDto>();
	private List<TSMDto>  fromTSMList  = new ArrayList<TSMDto>();
	private List<TSMDto>  toTSMList  = new ArrayList<TSMDto>();
	private List<DistributorDTO>  fromDistList  = new ArrayList<DistributorDTO>();
	private List<DistributorDTO>  toDistList  = new ArrayList<DistributorDTO>();
	public List<DPReverseCollectionDto>  reverseCollectionList  = new ArrayList<DPReverseCollectionDto>();
	private String selectedSerialNo;
	public String collectionId = "";
	
	public String getSelectedSerialNo() {
		return selectedSerialNo;
	}
	public void setSelectedSerialNo(String selectedSerialNo) {
		this.selectedSerialNo = selectedSerialNo;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	
	public String getProductIdNew() {
		return productIdNew;
	}
	public void setProductIdNew(String productIdNew) {
		this.productIdNew = productIdNew;
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

	public List<TransferPendingSTBDto> getCollectionList() {
		return collectionList;
	}
	public void setCollectionList(List<TransferPendingSTBDto> collectionList) {
		this.collectionList = collectionList;
	}
	public String getFromCircleId() {
		return fromCircleId;
	}
	public void setFromCircleId(String fromCircleId) {
		this.fromCircleId = fromCircleId;
	}
	public String getToCircleId() {
		return toCircleId;
	}
	public void setToCircleId(String toCircleId) {
		this.toCircleId = toCircleId;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public List<DPReverseCollectionDto> getReverseCollectionList() {
		return reverseCollectionList;
	}
	public void setReverseCollectionList(
			List<DPReverseCollectionDto> reverseCollectionList) {
		this.reverseCollectionList = reverseCollectionList;
	}

	
	

}
