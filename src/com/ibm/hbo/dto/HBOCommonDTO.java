package com.ibm.hbo.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class HBOCommonDTO implements Serializable{
	
	private String condName="";
	private ArrayList roleList=null;
	private int circleId=0;
	private int warehouseId=0;
	private String productType="";
	private String bundleFlag="";
	
		public HBOCommonDTO(String condName, ArrayList roleList, int circleId, int warehouseId, String productType, String bundleFlag) {
		super();
		this.condName = condName;
		this.roleList = roleList;
		this.circleId = circleId;
		this.warehouseId = warehouseId;
		this.productType = productType;
		this.bundleFlag = bundleFlag;
	}

	public HBOCommonDTO(String condName, ArrayList roleList, int warehouseId) {
		super();
		this.condName = condName;
		this.roleList = roleList;
		this.warehouseId = warehouseId;
	}

	public String getBundleFlag() {
		return bundleFlag;
	}

	public int getCircleId() {
		return circleId;
	}

	public String getCondName() {
		return condName;
	}

	public String getProductType() {
		return productType;
	}

	public ArrayList getRoleList() {
		return roleList;
	}

	public int getWarehouseId() {
		return warehouseId;
	}
	
		

	

}
