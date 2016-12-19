package com.ibm.dp.dto;

import java.io.Serializable;
import java.sql.Date;

public class ActivationDetailReportDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public int distID = 0;
	 public String distName = "";
	 public String distLoginId = "";
	 public String circleName = "";
	 public String fseName = "";
	 public String retName = "";
	 public String tsmName="";
	 public int circle = 0;	
	 public String stbType;
	 public String serial;
	 public String status;
	 public String prNO;
	 public String poNO;
	 public Date stockAcceptanceDate;
	 public Date activationDate;
	 public String customerId;
	 public String tsmIds;
	 public String distIds;
	 public String circleIds;
	 public String productType;
	 public String fromDate;
	 public String todate;
	 
	public int getCircle() {
		return circle;
	}
	public void setCircle(int circle) {
		this.circle = circle;
	}
	public int getDistID() {
		return distID;
	}
	public void setDistID(int distID) {
		this.distID = distID;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public Date getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	public Date getStockAcceptanceDate() {
		return stockAcceptanceDate;
	}
	public void setStockAcceptanceDate(Date stockAcceptanceDate) {
		this.stockAcceptanceDate = stockAcceptanceDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFseName() {
		return fseName;
	}
	public void setFseName(String fseName) {
		this.fseName = fseName;
	}
	public String getPoNO() {
		return poNO;
	}
	public void setPoNO(String poNO) {
		this.poNO = poNO;
	}
	public String getPrNO() {
		return prNO;
	}
	public void setPrNO(String prNO) {
		this.prNO = prNO;
	}
	public String getRetName() {
		return retName;
	}
	public void setRetName(String retName) {
		this.retName = retName;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	  
}
