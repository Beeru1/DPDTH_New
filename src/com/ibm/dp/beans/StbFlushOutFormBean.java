package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.UploadFileDto;

public class StbFlushOutFormBean extends ActionForm
{
	private FormFile stbSearchFile;
	private ArrayList<DuplicateSTBDTO> freshSTBList;
	private FormFile freshStockFlushFile;
	private FormFile damageStockFlushFile;
	private String methodName;
	private boolean fileEmptyFlag = true;
	
	
	private String strmsg="";
	private String error_flag="";
	private UploadFileDto uploadFileDto=null;
	private List error_file = new ArrayList();
	
	private static final long serialVersionUID = 1098305345870553453L;
	
	public void reset()
	{
		
	}


	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public FormFile getFreshStockFlushFile() {
		return freshStockFlushFile;
	}


	public void setFreshStockFlushFile(FormFile freshStockFlushFile) {
		this.freshStockFlushFile = freshStockFlushFile;
	}

	public FormFile getStbSearchFile() {
		return stbSearchFile;
	}


	public void setStbSearchFile(FormFile stbSearchFile) {
		this.stbSearchFile = stbSearchFile;
	}

	public FormFile getDamageStockFlushFile() {
		return damageStockFlushFile;
	}


	public void setDamageStockFlushFile(FormFile damageStockFlushFile) {
		this.damageStockFlushFile = damageStockFlushFile;
	}


	public ArrayList<DuplicateSTBDTO> getFreshSTBList() {
		return freshSTBList;
	}


	public void setFreshSTBList(ArrayList<DuplicateSTBDTO> freshSTBList) {
		this.freshSTBList = freshSTBList;
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

	
}
