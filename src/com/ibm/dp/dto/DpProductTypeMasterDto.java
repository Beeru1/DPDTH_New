package com.ibm.dp.dto;

import java.io.Serializable;

public class DpProductTypeMasterDto  implements Serializable  {
	 public String productId = "";
	 public String productType = "";
//	 add by harbans on Reservation Obserbation 30th June
	 public String productName = "";
	
	 public String getProductId() {
		return productId;
	}
	 public void setProductId(String productId) {
		this.productId = productId;
	}
	 public String getProductType() {
		return productType;
	}
	 public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	 
	 
}
