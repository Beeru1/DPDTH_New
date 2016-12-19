package com.ibm.dp.dto;

	
public class SCMConsumptionReportDTO {

	private String Batch_Id;
	private String Source;
	private String Process;
	private String login_Name;
	private String company_Code;
	private String area;
	private String sub_Area;
	private String source_Type;
	private String product_Code;
	private String quantity;
	private String status;
	private String error_Desc;
	private String request_Id;
	private String request_Date;
	private String update_Date;

	
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBatch_Id() {
		return Batch_Id;
	}
	public void setBatch_Id(String batch_Id) {
		Batch_Id = batch_Id;
	}
	public String getCompany_Code() {
		return company_Code;
	}
	public void setCompany_Code(String company_Code) {
		this.company_Code = company_Code;
	}
	public String getError_Desc() {
		return error_Desc;
	}
	public void setError_Desc(String error_Desc) {
		this.error_Desc = error_Desc;
	}
	public String getLogin_Name() {
		return login_Name;
	}
	public void setLogin_Name(String login_Name) {
		this.login_Name = login_Name;
	}
	public String getProcess() {
		return Process;
	}
	public void setProcess(String process) {
		Process = process;
	}
	public String getProduct_Code() {
		return product_Code;
	}
	public void setProduct_Code(String product_Code) {
		this.product_Code = product_Code;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRequest_Date() {
		return request_Date;
	}
	public void setRequest_Date(String request_Date) {
		this.request_Date = request_Date;
	}
	public String getRequest_Id() {
		return request_Id;
	}
	public void setRequest_Id(String request_Id) {
		this.request_Id = request_Id;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getSource_Type() {
		return source_Type;
	}
	public void setSource_Type(String source_Type) {
		this.source_Type = source_Type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSub_Area() {
		return sub_Area;
	}
	public void setSub_Area(String sub_Area) {
		this.sub_Area = sub_Area;
	}
	public String getUpdate_Date() {
		return update_Date;
	}
	public void setUpdate_Date(String update_Date) {
		this.update_Date = update_Date;
	}
	
}
