package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

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
public class DPReverseChurnCollectionBean extends ActionForm {
	public List<DPReverseCollectionDto>  collectSerialsList  = new ArrayList<DPReverseCollectionDto>();
    	public String collectionId = "";
	public String collectionName = "";
	public String productId = "";
	public String vc="";
	public String customerId="";
	public int ageing;
	public String product_Name = "";
	public String collectSerialsNoBox ="";
	public String remarks ="";
	public String message="";
	private String strmsg = "";
	private FormFile  uploadedFile;
	private String methodName="";
	private String error_flag="";
	private FormFile churnFile;
	ArrayList availableSerialNosList = null;
	
	private List error_file = new ArrayList();
	public String serial_Number=""; public String product_Id=""; public String vc_Id="";  public String customer_Id="";
   public String si_Id="";  public ArrayList poList;
   public String statusId="";
   public String strCheckedChurn;
   public int typedist;
	
	public int getTypedist() {
		return typedist;
	}
	public void setTypedist(int typedist) {
		this.typedist = typedist;
	}
	public String getCollectSerialsNoBox() {		return collectSerialsNoBox;	}
	public void setCollectSerialsNoBox(String collectSerialsNoBox) {		this.collectSerialsNoBox = collectSerialsNoBox;	}
	
	
	public String getCollectionId() {		return collectionId;	}
	public void setCollectionId(String collectionId) {		this.collectionId = collectionId;	}
	
	public String getCollectionName() {		return collectionName;	}
	public void setCollectionName(String collectionName) {		this.collectionName = collectionName;	}
	
	public String getProductId() {		return productId;	}
	public void setProductId(String productId) {		this.productId = productId;	}
	
	
	public String getRemarks() {		return remarks;	}
	public void setRemarks(String remarks) {		this.remarks = remarks;	}
	
	public String getMessage() {		return message;	}
	public void setMessage(String message) {		this.message = message;	}
	
	public String getCustomerId() {		return customerId;	}
	public void setCustomerId(String customerId) {		this.customerId = customerId;	}
	
	public String getVc() {		return vc;	}
	public void setVc(String vc) {		this.vc = vc;	}
	

	public List<DPReverseCollectionDto> getCollectSerialsList() {		return collectSerialsList;	}
	public void setCollectSerialsList(List<DPReverseCollectionDto> collectSerialsList) {this.collectSerialsList = collectSerialsList;}
	
	
	public FormFile getChurnFile() {		return churnFile;	}
	public void setChurnFile(FormFile churnFile) {		this.churnFile = churnFile;	}
	
	public String getError_flag() {		return error_flag;	}
	public void setError_flag(String error_flag) {		this.error_flag = error_flag;	}
	
	public ArrayList getPoList() {		return poList;	}
	public void setPoList(ArrayList poList) {		this.poList = poList;	}
	
	public String getStatusId() {		return statusId;	}
	public void setStatusId(String statusId) {		this.statusId = statusId;	}
	
	public String getCustomer_Id() {		return customer_Id;	}
	public void setCustomer_Id(String customer_Id) {		this.customer_Id = customer_Id;	}
	
	public String getProduct_Id() {		return product_Id;	}
	public void setProduct_Id(String product_Id) {		this.product_Id = product_Id;	}
	
	public String getSerial_Number() {		return serial_Number;	}
	public void setSerial_Number(String serial_Number) {		this.serial_Number = serial_Number;	}
	
	public String getSi_Id() {		return si_Id;	}
	public void setSi_Id(String si_Id) {		this.si_Id = si_Id;	}
	
	public String getVc_Id() {		return vc_Id;	}
	public void setVc_Id(String vc_Id) {		this.vc_Id = vc_Id;	}
	
	public int getAgeing() {		return ageing;	}
	public void setAgeing(int ageing) {		ageing = ageing;	}
	
	public ArrayList getAvailableSerialNosList() {		return availableSerialNosList;	}
	public void setAvailableSerialNosList(ArrayList availableSerialNosList) {		this.availableSerialNosList = availableSerialNosList;	}
	
	public FormFile getUploadedFile() {		return uploadedFile;	}
	public void setUploadedFile(FormFile uploadedFile) {		this.uploadedFile = uploadedFile;	}
	
	public String getStrmsg() {		return strmsg;	}
	public void setStrmsg(String strmsg) {		this.strmsg = strmsg;	}
	
	public List getError_file() {		return error_file;	}
	public void setError_file(List error_file) {		this.error_file = error_file;	}
	
	public String getStrCheckedChurn() {		return strCheckedChurn;	}
	public void setStrCheckedChurn(String strCheckedChurn) {		this.strCheckedChurn = strCheckedChurn;	}
	
	public String getProduct_Name() {		return product_Name;	}
	public void setProduct_Name(String product_Name) {		this.product_Name = product_Name;	}

	public String getMethodName() {		return methodName;	}
	public void setMethodName(String methodName) {		this.methodName = methodName;	}


}
