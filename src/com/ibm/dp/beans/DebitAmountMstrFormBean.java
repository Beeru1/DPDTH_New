package com.ibm.dp.beans;



import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.RecoSummaryDTO;

public class DebitAmountMstrFormBean extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private List productList = new ArrayList();
private String productId;
private String productType;
private String amount;
private String message;
private String updatedBy;


public String getUpdatedBy() {
	return updatedBy;
}

public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getProductId() {
	return productId;
}

public void setProductId(String productId) {
	this.productId = productId;
}

public List getProductList() {
	return productList;
}

public void setProductList(List productList) {
	this.productList = productList;
}

public String getProductType() {
	return productType;
}

public void setProductType(String productType) {
	this.productType = productType;
}

public String getAmount() {
	return amount;
}

public void setAmount(String amount) {
	this.amount = amount;
}

}
