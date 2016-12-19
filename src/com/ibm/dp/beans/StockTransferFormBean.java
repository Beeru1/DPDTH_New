package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
/**
 * @author Mohammad Aslam
 */
public class StockTransferFormBean extends ActionForm {
		
	private static final long serialVersionUID = -1052442724039089269L;
	private String transferRequestedBy;
	private String transferTo;
	private String action [];
	private String serialNos [];
	private String totalCount [];
	private String dcNumber;
	private String from;
	private String to;
	private String transQuantity;	
	private String selectedSerialNumberList;
	//---------Added by Nazim Hussain-------------------------------
	private List<DistributorDTO> toDistList = new ArrayList<DistributorDTO>();
	private List<DistributorDTO> distributorFromList = new ArrayList<DistributorDTO>();
	private List<DistributorDetailsDTO> distributorDetailsList = new ArrayList<DistributorDetailsDTO>();
	private String strAction;
	private String strTransferTo;
	private String strSuccessMsg;

	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}
	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}
	//	---------Added by Nazim Hussain ends here-------------------------------
	public String[] getAction() {
		return action;
	}
	public void setAction(String[] action) {
		this.action = action;
	}
	public String getTransferRequestedBy() {
		return transferRequestedBy;
	}
	public void setTransferRequestedBy(String transferRequestedBy) {
		this.transferRequestedBy = transferRequestedBy;
	}
	public String getTransferTo() {
		return transferTo;
	}
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}
	public String getDcNumber() {
		return dcNumber;
	}
	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTransQuantity() {
		return transQuantity;
	}
	public void setTransQuantity(String transQuantity) {
		this.transQuantity = transQuantity;
	}
	public String[] getSerialNos() {
		return serialNos;
	}
	public void setSerialNos(String[] serialNos) {
		this.serialNos = serialNos;
	}
	public String[] getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String[] totalCount) {
		this.totalCount = totalCount;
	}
	public String getSelectedSerialNumberList() {
		return selectedSerialNumberList;
	}
	public void setSelectedSerialNumberList(String selectedSerialNumberList) {
		this.selectedSerialNumberList = selectedSerialNumberList;
	}
	public List<DistributorDTO> getToDistList() {
		return toDistList;
	}
	public void setToDistList(List<DistributorDTO> toDistList) {
		this.toDistList = toDistList;
	}
	public String getStrAction() {
		return strAction;
	}
	public void setStrAction(String strAction) {
		this.strAction = strAction;
	}
	public String getStrTransferTo() {
		return strTransferTo;
	}
	public void setStrTransferTo(String strTransferTo) {
		this.strTransferTo = strTransferTo;
	}
	public List<DistributorDetailsDTO> getDistributorDetailsList() {
		return distributorDetailsList;
	}
	public void setDistributorDetailsList(
			List<DistributorDetailsDTO> distributorDetailsList) {
		this.distributorDetailsList = distributorDetailsList;
	}
	public List<DistributorDTO> getDistributorFromList() {
		return distributorFromList;
	}
	public void setDistributorFromList(List<DistributorDTO> distributorFromList) {
		this.distributorFromList = distributorFromList;
	}
}
