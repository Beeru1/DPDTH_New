/*
 * Created on Nov 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dto;

import java.io.Serializable;

/**
 * @author Aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOMinReorderDTO implements Serializable{
	
	String distributor="";
	String product="";
	String product_code="";
	int minreorder=0;
	String Created_by="";
	String business_catg="";

	/**
	 * @return
	 */
	public String getCreated_by() {
		return Created_by;
	}

	/**
	 * @return
	 */
	public String getDistributor() {
		return distributor;
	}

	/**
	 * @return
	 */
	public int getMinreorder() {
		return minreorder;
	}

	/**
	 * @return
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param string
	 */
	public void setCreated_by(String string) {
		Created_by = string;
	}

	/**
	 * @param string
	 */
	public void setDistributor(String string) {
		distributor = string;
	}

	/**
	 * @param i
	 */
	public void setMinreorder(int i) {
		minreorder = i;
	}

	/**
	 * @param string
	 */
	public void setProduct(String string) {
		product = string;
	}

	/**
	 * @return
	 */
	public String getProduct_code() {
		return product_code;
	}

	/**
	 * @param string
	 */
	public void setProduct_code(String string) {
		product_code = string;
	}
	
	/**
	 * @return
	 */
	public String getBusiness_catg() {
		return business_catg;
	}

	/**
	 * @param string
	 */
	public void setBusiness_catg(String string) {
		business_catg = string;
	}

}
