package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import com.ibm.dp.dto.WHdistmappbulkDto;

public class WHdistmappbulkBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553454L;
	private FormFile  uploadedFile;
	private String methodName ;
	private String strmsg ;
	private String error_flag;
	private List error_file = new ArrayList();
	List<WHdistmappbulkDto> whDistMapList=new ArrayList<WHdistmappbulkDto>();
	public String distOlmId;
	public String wareHouseId;
	public String wareName ;
	public String warehouseAddress;
	public String circleName ;
	
	
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getWarehouseAddress() {
		return warehouseAddress;
	}
	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public List getError_file() {
		return error_file;
	}
	public void setError_file(List error_file) {
		this.error_file = error_file;
	}
	public List<WHdistmappbulkDto> getWhDistMapList() {
		return whDistMapList;
	}
	public void setWhDistMapList(List<WHdistmappbulkDto> whDistMapList) {
		this.whDistMapList = whDistMapList;
	}
	public String getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	
}
