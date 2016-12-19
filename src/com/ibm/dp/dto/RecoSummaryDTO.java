package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RecoSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String recoStatus;
	
	private List   recoStatusList;
	
	private Integer circleId;
	
	private String[] circleIdArr = null;
	
	private String[] circleList;
	
	private String distCode;
	
	private String distName;
	
	private Date recoStartDate;
	
	private Date recoEndDate;
	
	private String productName;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getRecoEndDate() {
		return recoEndDate;
	}

	public void setRecoEndDate(Date recoEndDate) {
		this.recoEndDate = recoEndDate;
	}

	public Date getRecoStartDate() {
		return recoStartDate;
	}

	public void setRecoStartDate(Date recoStartDate) {
		this.recoStartDate = recoStartDate;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public String[] getCircleIdArr() {
		return circleIdArr;
	}

	public void setCircleIdArr(String[] circleIdArr) {
		this.circleIdArr = circleIdArr;
	}

	public String[] getCircleList() {
		return circleList;
	}

	public void setCircleList(String[] circleList) {
		this.circleList = circleList;
	}

	public String getRecoStatus() {
		return recoStatus;
	}

	public void setRecoStatus(String recoStatus) {
		this.recoStatus = recoStatus;
	}

	public List getRecoStatusList() {
		return recoStatusList;
	}

	public void setRecoStatusList(List recoStatusList) {
		this.recoStatusList = recoStatusList;
	}

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}
}
