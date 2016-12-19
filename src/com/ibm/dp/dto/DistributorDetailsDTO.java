package com.ibm.dp.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mohammad Aslam
 */
public class DistributorDetailsDTO {

	private String distributorName;
	private String productId;
	private String productName;
	private String closingStock;
	private String comments;
	private String creationDate;	
	private Timestamp creationDateasTimestamp;	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private String requestedQuantity;
	private String transferQuantity;
	private String selectedSerialNumberList;
	private String requestedId;
	private Integer intDPStock;
	private Integer intOpenStock;
	
	public String getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(String requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}	

	public String getClosingStock() {
		return closingStock;
	}

	public void setClosingStock(String closingStock) {
		this.closingStock = closingStock;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreationDate() {
		if (getCreationDateasTimestamp() != null) {
			Date date = new Date(getCreationDateasTimestamp().getTime());
			return sdf.format(date);
		} else {
			return creationDate;
		}
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Timestamp getCreationDateasTimestamp() {
		return creationDateasTimestamp;
	}

	public void setCreationDateasTimestamp(Timestamp creationDateasTimestamp) {
		this.creationDateasTimestamp = creationDateasTimestamp;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSelectedSerialNumberList() {
		return selectedSerialNumberList;
	}

	public void setSelectedSerialNumberList(String selectedSerialNumberList) {
		this.selectedSerialNumberList = selectedSerialNumberList;
	}

	public String getTransferQuantity() {
		return transferQuantity;
	}

	public void setTransferQuantity(String transferQuantity) {
		this.transferQuantity = transferQuantity;
	}

	public String getRequestedId() {
		return requestedId;
	}

	public void setRequestedId(String requestedId) {
		this.requestedId = requestedId;
	}

	public Integer getIntDPStock() {
		return intDPStock;
	}

	public void setIntDPStock(Integer intDPStock) {
		this.intDPStock = intDPStock;
	}

	public Integer getIntOpenStock() {
		return intOpenStock;
	}

	public void setIntOpenStock(Integer intOpenStock) {
		this.intOpenStock = intOpenStock;
	}

}