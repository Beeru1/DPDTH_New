package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.dto.WHdistmappbulkDto;

public class EditRecoDetailBean extends ValidatorForm
{
	private static final long serialVersionUID = 1L;
	
	List<RecoPeriodDTO> recoPeriodList = null;
	int recoPeriodId=0;
	int  gracePeriod;
	private FormFile  uploadedFile;
	public String distOlmId;
	private String updatedBy;
	private String strmsg ;
	private String error_flag;
	private List error_file = new ArrayList();
	
	public int getRecoPeriodId() {
	 return recoPeriodId;
	}

	public void setRecoPeriodId(int recoPeriodId) {
		this.recoPeriodId = recoPeriodId;
	}

	public List<RecoPeriodDTO> getRecoPeriodList() {
		return recoPeriodList;
	}

	public void setRecoPeriodList(List<RecoPeriodDTO> recoPeriodList) {
		this.recoPeriodList = recoPeriodList;
	}

	


	public int getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(int gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public FormFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List getError_file() {
		return error_file;
	}

	public void setError_file(List error_file) {
		this.error_file = error_file;
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

	public String getDistOlmId() {
		return distOlmId;
	}

	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	
	
}
