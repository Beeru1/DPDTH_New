package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.SackDistributorDetailDto;
import com.ibm.dp.dto.TSMDto;

public class SackDistributorBean extends ActionForm{
	
	public List<TSMDto>  tsmList  = new ArrayList<TSMDto>();
	public List<SackDistributorDetailDto> distributorList;
	public String tsmSelectedId = "";
	private String[] hidSeletedDistIds;
	private String message="";
	private String strRemarks;
	
	public List<SackDistributorDetailDto> getDistributorList() {
		return distributorList;
	}
	public void setDistributorList(List<SackDistributorDetailDto> distributorList) {
		this.distributorList = distributorList;
	}
	public String[] getHidSeletedDistIds() {
		return hidSeletedDistIds;
	}
	public void setHidSeletedDistIds(String[] hidSeletedDistIds) {
		this.hidSeletedDistIds = hidSeletedDistIds;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TSMDto> getTsmList() {
		return tsmList;
	}
	public void setTsmList(List<TSMDto> tsmList) {
		this.tsmList = tsmList;
	}
	public String getTsmSelectedId() {
		return tsmSelectedId;
	}
	public void setTsmSelectedId(String tsmSelectedId) {
		this.tsmSelectedId = tsmSelectedId;
	}
	public String getStrRemarks() {
		return strRemarks;
	}
	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	
	
}
