package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.dp.dto.UploadFileDto;

public class AvStockUploadFormBean extends ActionForm
{
	private FormFile stockList;
	private String methodName;
	private boolean fileEmptyFlag = true;
	private String strmsg="";
	private String error_flag="";
	private UploadFileDto uploadFileDto=null;
	private List error_file = new ArrayList();
	private ArrayList<DuplicateSTBDTO> freshSTBList;
	private static final long serialVersionUID = 1098305345870553453L;
	private int intProductID = 0;
	private int intBusCatHid ;
	private int intBusCatID ;
	private List<CircleDto> listBusCategory = new ArrayList<CircleDto>();
	private List<CircleDto> listProduct = new ArrayList<CircleDto>();
	
	//added by aman
	private String olmId="";
	private String distName="";
	private String tsmOlmId="";
	private String tsmName="";
	private String circleId="";
	private List<NonSerializedToSerializedDTO> nonSerializedToSerializedList=new ArrayList<NonSerializedToSerializedDTO>();
	private String uploadbtn="";
	private FormFile uploadedFile;
	private List<NonSerializedToSerializedDTO> prodList = new ArrayList<NonSerializedToSerializedDTO>();
	private String success_message="";
	private String success="";
	private String file_message="";
	//end of changes by aman
	
	
	public String getFile_message() {
		return file_message;
	}
	public void setFile_message(String file_message) {
		this.file_message = file_message;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getSuccess_message() {
		return success_message;
	}
	public void setSuccess_message(String success_message) {
		this.success_message = success_message;
	}
	public List<NonSerializedToSerializedDTO> getProdList() {
		return prodList;
	}
	public void setProdList(List<NonSerializedToSerializedDTO> prodList) {
		this.prodList = prodList;
	}
	public String getUploadbtn() {
		return uploadbtn;
	}
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public void setUploadbtn(String uploadbtn) {
		this.uploadbtn = uploadbtn;
	}
	public List<NonSerializedToSerializedDTO> getNonSerializedToSerializedList() {
		return nonSerializedToSerializedList;
	}
	public void setNonSerializedToSerializedList(
			List<NonSerializedToSerializedDTO> nonSerializedToSerializedList) {
		this.nonSerializedToSerializedList = nonSerializedToSerializedList;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getTsmOlmId() {
		return tsmOlmId;
	}
	public void setTsmOlmId(String tsmOlmId) {
		this.tsmOlmId = tsmOlmId;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getOlmId() {
		return olmId;
	}
	public void setOlmId(String olmId) {
		this.olmId = olmId;
	}

	
	public int getIntProductID() {
		return intProductID;
	}
	public void setIntProductID(int intProductID) {
		this.intProductID = intProductID;
	}
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public UploadFileDto getUploadFileDto() {
		return uploadFileDto;
	}
	public void setUploadFileDto(UploadFileDto uploadFileDto) {
		this.uploadFileDto = uploadFileDto;
	}
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public String getStrmsg() {
		return strmsg;
	}


	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}


	public List getError_file() {
		return error_file;
	}


	public void setError_file(List error_file) {
		this.error_file = error_file;
	}


	public boolean isFileEmptyFlag() {
		return fileEmptyFlag;
	}


	public void setFileEmptyFlag(boolean fileEmptyFlag) {
		this.fileEmptyFlag = fileEmptyFlag;
	}
	public ArrayList<DuplicateSTBDTO> getFreshSTBList() {
		return freshSTBList;
	}


	public void setFreshSTBList(ArrayList<DuplicateSTBDTO> freshSTBList) {
		this.freshSTBList = freshSTBList;
	}


	public FormFile getStockList() {
		return stockList;
	}


	public void setStockList(FormFile stockList) {
		this.stockList = stockList;
	}
	public List<CircleDto> getListBusCategory() {
		return listBusCategory;
	}
	public void setListBusCategory(List<CircleDto> listBusCategory) {
		this.listBusCategory = listBusCategory;
	}
	public List<CircleDto> getListProduct() {
		return listProduct;
	}
	public void setListProduct(List<CircleDto> listProduct) {
		this.listProduct = listProduct;
	}
	public int getIntBusCatHid() {
		return intBusCatHid;
	}
	public void setIntBusCatHid(int intBusCatHid) {
		this.intBusCatHid = intBusCatHid;
	}
	public int getIntBusCatID() {
		return intBusCatID;
	}
	public void setIntBusCatID(int intBusCatID) {
		this.intBusCatID = intBusCatID;
	}
	
	

}
