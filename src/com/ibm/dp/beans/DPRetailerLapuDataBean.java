/**
 * 
 */
package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.RetailerLapuDataDto;

/**
 * @author Administrator
 *
 */
public class DPRetailerLapuDataBean extends ActionForm
{
	private FormFile uploadedFile;
	private String strmsg = "";
	private String error_flag = "";
	private List<String> errors = new ArrayList<String>();
	private List<RetailerLapuDataDto> lapuDataList;

	public void setLapuDataList(List<RetailerLapuDataDto> lapuDataList) {
		this.lapuDataList = lapuDataList;
	}

	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public FormFile getUploadedFile() {
		return uploadedFile;
	}

	public List<RetailerLapuDataDto> getLapuDataList() {
		return lapuDataList;
	}

	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}

	public String getStrmsg() {
		return strmsg;
	}

	public String getError_flag() {
		return error_flag;
	}

	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
