package com.ibm.dp.beans;

import java.io.Serializable;
import org.apache.struts.validator.ValidatorForm;
import java.util.ArrayList;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
 
/**
 * StockDeclarationBean class holds the Properties for Stock Declaration
 *
 * @author Arvind Gupta
 */
public class StockDeclarationBean extends ValidatorForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String closingStock="";
	
	private String product="";
	
	ArrayList<DistStockDecOptionsDTO> productList;
	
	private String comments="";
	
	private String[] arrProductId;
	
	private String[] arrProductName;
	
	private String[] arrClosingStock;
	
	private String[] arrComments;
	
	private String message="";

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the arrClosingStock
	 */
	public String[] getArrClosingStock() {
		return arrClosingStock;
	}

	/**
	 * @param arrClosingStock the arrClosingStock to set
	 */
	public void setArrClosingStock(String[] arrClosingStock) {
		this.arrClosingStock = arrClosingStock;
	}

	/**
	 * @return the arrComments
	 */
	public String[] getArrComments() {
		return arrComments;
	}

	/**
	 * @param arrComments the arrComments to set
	 */
	public void setArrComments(String[] arrComments) {
		this.arrComments = arrComments;
	}

	/**
	 * @return the arrProductId
	 */
	public String[] getArrProductId() {
		return arrProductId;
	}

	/**
	 * @param arrProductId the arrProductId to set
	 */
	public void setArrProductId(String[] arrProductId) {
		this.arrProductId = arrProductId;
	}

	/**
	 * @return the arrProductName
	 */
	public String[] getArrProductName() {
		return arrProductName;
	}

	/**
	 * @param arrProductName the arrProductName to set
	 */
	public void setArrProductName(String[] arrProductName) {
		this.arrProductName = arrProductName;
	}

	/**
	 * @return the closingStock
	 */
	public String getClosingStock() {
		return closingStock;
	}

	/**
	 * @param closingStock the closingStock to set
	 */
	public void setClosingStock(String closingStock) {
		this.closingStock = closingStock;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the productList
	 */
	public ArrayList<DistStockDecOptionsDTO> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(ArrayList<DistStockDecOptionsDTO> productList) {
		this.productList = productList;
	}

}
