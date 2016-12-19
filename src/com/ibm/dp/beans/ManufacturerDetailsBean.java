package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ManufacturerDetailsDto;

public class ManufacturerDetailsBean extends ActionForm{
	
	private String methodName;
	private List<ManufacturerDetailsDto> manufacturerList = new ArrayList<ManufacturerDetailsDto>();
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<ManufacturerDetailsDto> getManufacturerList() {
		return manufacturerList;
	}
	public void setManufacturerList(List<ManufacturerDetailsDto> manufacturerList) {
		this.manufacturerList = manufacturerList;
	}
	
	
}
