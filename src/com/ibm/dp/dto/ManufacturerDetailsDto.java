package com.ibm.dp.dto;

public class ManufacturerDetailsDto {

	
	
	String manufacturerId;
	String Status ;
	String modelNumber;
	String manufacturer;
	String selectionFlag;
	

	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSelectionFlag() {
		return selectionFlag;
	}
	public void setSelectionFlag(String selectionFlag) {
		this.selectionFlag = selectionFlag;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	
}
