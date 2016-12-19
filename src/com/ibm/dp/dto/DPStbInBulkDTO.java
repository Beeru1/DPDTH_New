package com.ibm.dp.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DPStbInBulkDTO {

	private String serial_no = "";

	private String product_name = "";

	private String rev_product_name = "";
	private String status = "";

	private String distId;

	private String distributor = "";

	private String distIdRev;

	private String distributorRev = "";
	
	private String circle;
	
	private String circleRev;

	private String pono = "";

	private String poDate;

	private String dcno = "";

	private String dcDate = "";

	private String accept_date = "";
	
	private String invoice_status="";

	private String fsc = "";

	private String retailer = "";

	private String activationDate = "";

	private String assign_inv_status;

	private String customer = "";

	private String inv_change_date;

	private String collection_date;
	private String inv_change_type;

	private String defect_type;

	private String rev_dcno;

	private String rev_dc_date;

	private String rev_dc_status;

	private String rev_stock_in_status;

	private String err_msg = "";

	private int product_id = 0;

	private String reverseDCType="";
	
	private Timestamp validationDate=null;
	
	private List list_srno = new ArrayList();
	
	
	//added for STB History 25/06/13
	
	private String loginName="";
	private String accountName="";
	private String invNo="";
	private String fseName="";
	private String retName="";
	private String assignDate="";
	private String dcStatus="";
	private String defectiveSrNo="";
	
	// end of changes

	public String getDefectiveSrNo() {
		return defectiveSrNo;
	}

	public void setDefectiveSrNo(String defectiveSrNo) {
		this.defectiveSrNo = defectiveSrNo;
	}

	public String getDcStatus() {
		return dcStatus;
	}

	public void setDcStatus(String dcStatus) {
		this.dcStatus = dcStatus;
	}

	List<DPPoAcceptanceBulkDTO> short_sr_no_list = new ArrayList<DPPoAcceptanceBulkDTO>();

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getFseName() {
		return fseName;
	}

	public void setFseName(String fseName) {
		this.fseName = fseName;
	}

	public String getRetName() {
		return retName;
	}

	public void setRetName(String retName) {
		this.retName = retName;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	List<DPPoAcceptanceBulkDTO> extra_sr_no_list = new ArrayList<DPPoAcceptanceBulkDTO>();

	List<DPPoAcceptanceBulkDTO> available_sr_no_list = new ArrayList<DPPoAcceptanceBulkDTO>();

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public List getList_srno() {
		return list_srno;
	}

	public void setList_srno(List list_srno) {
		this.list_srno = list_srno;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public List<DPPoAcceptanceBulkDTO> getExtra_sr_no_list() {
		return extra_sr_no_list;
	}

	public void setExtra_sr_no_list(List<DPPoAcceptanceBulkDTO> extra_sr_no_list) {
		this.extra_sr_no_list = extra_sr_no_list;
	}

	public List<DPPoAcceptanceBulkDTO> getShort_sr_no_list() {
		return short_sr_no_list;
	}

	public void setShort_sr_no_list(List<DPPoAcceptanceBulkDTO> short_sr_no_list) {
		this.short_sr_no_list = short_sr_no_list;
	}

	public List<DPPoAcceptanceBulkDTO> getAvailable_sr_no_list() {
		return available_sr_no_list;
	}

	public void setAvailable_sr_no_list(
			List<DPPoAcceptanceBulkDTO> available_sr_no_list) {
		this.available_sr_no_list = available_sr_no_list;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public String getDcno() {
		return dcno;
	}

	public void setDcno(String dcno) {
		this.dcno = dcno;
	}

	public String getFsc() {
		return fsc;
	}

	public void setFsc(String fsc) {
		this.fsc = fsc;
	}

	public String getAccept_date() {
		return accept_date;
	}

	public void setAccept_date(String accept_date) {
		this.accept_date = accept_date;
	}

	public String getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getDcDate() {
		return dcDate;
	}

	public void setDcDate(String dcDate) {
		this.dcDate = dcDate;
	}

	public String getDistId() {
		return distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getAssign_inv_status() {
		return assign_inv_status;
	}

	public void setAssign_inv_status(String assign_inv_status) {
		this.assign_inv_status = assign_inv_status;
	}

	public String getDefect_type() {
		return defect_type;
	}

	public void setDefect_type(String defect_type) {
		this.defect_type = defect_type;
	}

	public String getInv_change_date() {
		return inv_change_date;
	}

	public void setInv_change_date(String inv_change_date) {
		this.inv_change_date = inv_change_date;
	}

	public String getInv_change_type() {
		return inv_change_type;
	}

	public void setInv_change_type(String inv_change_type) {
		this.inv_change_type = inv_change_type;
	}

	public String getRev_dc_date() {
		return rev_dc_date;
	}

	public void setRev_dc_date(String rev_dc_date) {
		this.rev_dc_date = rev_dc_date;
	}

	public String getRev_dc_status() {
		return rev_dc_status;
	}

	public void setRev_dc_status(String rev_dc_status) {
		this.rev_dc_status = rev_dc_status;
	}

	public String getRev_dcno() {
		return rev_dcno;
	}

	public void setRev_dcno(String rev_dcno) {
		this.rev_dcno = rev_dcno;
	}

	public String getRev_stock_in_status() {
		return rev_stock_in_status;
	}

	public void setRev_stock_in_status(String rev_stock_in_status) {
		this.rev_stock_in_status = rev_stock_in_status;
	}

	public String getDistIdRev() {
		return distIdRev;
	}

	public void setDistIdRev(String distIdRev) {
		this.distIdRev = distIdRev;
	}

	public String getDistributorRev() {
		return distributorRev;
	}

	public void setDistributorRev(String distributorRev) {
		this.distributorRev = distributorRev;
	}

	public String getCircleRev() {
		return circleRev;
	}

	public void setCircleRev(String circleRev) {
		this.circleRev = circleRev;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getCollection_date() {
		return collection_date;
	}

	public void setCollection_date(String collection_date) {
		this.collection_date = collection_date;
	}

	public String getRev_product_name() {
		return rev_product_name;
	}

	public void setRev_product_name(String rev_product_name) {
		this.rev_product_name = rev_product_name;
	}

	public String getReverseDCType() {
		return reverseDCType;
	}

	public void setReverseDCType(String reverseDCType) {
		this.reverseDCType = reverseDCType;
	}

	/**
	 * @return the validationDate
	 */
	public Timestamp getValidationDate() {
		return validationDate;
	}

	/**
	 * @param validationDate the validationDate to set
	 */
	public void setValidationDate(Timestamp validationDate) {
		this.validationDate = validationDate;
	}

}
