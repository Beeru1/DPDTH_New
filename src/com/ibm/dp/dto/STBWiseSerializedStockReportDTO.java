package com.ibm.dp.dto;

import java.io.Serializable;

public class STBWiseSerializedStockReportDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String SNO = "";
	 public String distID ;
	 public String distName = "";
	 public String tsmName="";
	 public String circle = "";
	 public String stbType;
	 public String stbSerialNo;
	 public String stbStatus;
	 public String PRNO;
	 public String PONO;
	 public String stockAcceptanceDate;
	 public String fseID;
	 public String tsmID;
	 public String fseName;
	 public String fseAllocationDate;
	 public String retailerID;
	 public String retailerAllocationDate;
	 public String productId;
	 public String account_level="";
	 public String dist_login="";
	 public String product_name="";
	 public String retailer_name="";
	 public String login_id="";
	 
	 public String tsmIds;
	 public String distIds;
	 public String circleIds;
	 public String fseIds;
	 public String retIds;
	 public String productType;
	 public String stbStatuses;
	 
	public String getCircleIds() {
		return circleIds;
	}
	public void setCircleIds(String circleIds) {
		this.circleIds = circleIds;
	}
	public String getDistIds() {
		return distIds;
	}
	public void setDistIds(String distIds) {
		this.distIds = distIds;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getTsmIds() {
		return tsmIds;
	}
	public void setTsmIds(String tsmIds) {
		this.tsmIds = tsmIds;
	}
	
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getFseAllocationDate() {
		return fseAllocationDate;
	}
	public void setFseAllocationDate(String fseAllocationDate) {
		this.fseAllocationDate = fseAllocationDate;
	}

	public String getFseName() {
		return fseName;
	}
	public void setFseName(String fseName) {
		this.fseName = fseName;
	}
	public String getPONO() {
		return PONO;
	}
	public void setPONO(String pono) {
		PONO = pono;
	}
	public String getPRNO() {
		return PRNO;
	}
	public void setPRNO(String prno) {
		PRNO = prno;
	}
	public String getRetailerAllocationDate() {
		return retailerAllocationDate;
	}
	public void setRetailerAllocationDate(String retailerAllocationDate) {
		this.retailerAllocationDate = retailerAllocationDate;
	}

	public String getSNO() {
		return SNO;
	}
	public void setSNO(String sno) {
		SNO = sno;
	}
	public String getStbSerialNo() {
		return stbSerialNo;
	}
	public void setStbSerialNo(String stbSerialNo) {
		this.stbSerialNo = stbSerialNo;
	}

	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public String getStockAcceptanceDate() {
		return stockAcceptanceDate;
	}
	public void setStockAcceptanceDate(String stockAcceptanceDate) {
		this.stockAcceptanceDate = stockAcceptanceDate;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}

	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getDistID() {
		return distID;
	}
	public void setDistID(String distID) {
		this.distID = distID;
	}
	public String getFseID() {
		return fseID;
	}
	public void setFseID(String fseID) {
		this.fseID = fseID;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRetailerID() {
		return retailerID;
	}
	public void setRetailerID(String retailerID) {
		this.retailerID = retailerID;
	}
	public String getStbStatus() {
		return stbStatus;
	}
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}
	public String getTsmID() {
		return tsmID;
	}
	public void setTsmID(String tsmID) {
		this.tsmID = tsmID;
	}

	public String getFseIds() {
		return fseIds;
	}
	public void setFseIds(String fseIds) {
		this.fseIds = fseIds;
	}
	public String getRetIds() {
		return retIds;
	}
	public void setRetIds(String retIds) {
		this.retIds = retIds;
	}
	public String getStbStatuses() {
		return stbStatuses;
	}
	public void setStbStatuses(String stbStatuses) {
		this.stbStatuses = stbStatuses;
	}
	public String getAccount_level() {
		return account_level;
	}
	public void setAccount_level(String account_level) {
		this.account_level = account_level;
	}
	public String getDist_login() {
		return dist_login;
	}
	public void setDist_login(String dist_login) {
		this.dist_login = dist_login;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getRetailer_name() {
		return retailer_name;
	}
	public void setRetailer_name(String retailer_name) {
		this.retailer_name = retailer_name;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	 

}
