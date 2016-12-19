package com.ibm.dp.dto;

import java.io.Serializable;

public class DpProductCategoryDto  implements Serializable  {
	public String productCategory= "";
	public String productCategoryId= "";
	
	public String strValue ="";
	public String strText ="";
	

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getStrText() {
		return strText;
	}

	public void setStrText(String strText) {
		this.strText = strText;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
}
