package com.ibm.dp.dto;

import java.io.Serializable;

public class ProductMasterDto  implements Serializable{

	 public String productId = "";
	 public String productName = "";
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	 
	 
}
