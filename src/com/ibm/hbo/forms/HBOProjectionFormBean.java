/*
 * Created on Nov 27, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;

import java.util.ArrayList;

//import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;



/**
 * @author Aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProjectionFormBean extends ValidatorForm{
	
	private ArrayList arrCircle=null;
	private ArrayList arrProducts=null;
	private ArrayList arrBusinessCat=null;
	private ArrayList arrYear=null;
	private String month="";
	private String year="";
	private int quantity=0;
	private String circle="";
	private String product="";
	private String business_catg=""; 
	private String message ="";
	private ArrayList arrMonth=null;
	private FormFile thefile;
	private String fileName="";
	private String filePath="";
	private String role="";

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public void reset(){
		circle = "";
		business_catg = "";
		month = "";
		year = "";
		product = "";
		quantity = 0;
	}
	
	/**
	 * @return
	 */
	public ArrayList getArrCircle() {
		return arrCircle;
	}

	/**
	 * @return
	 */
	public ArrayList getArrProducts() {
		return arrProducts;
	}

	/**
	 * @return
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param list
	 */
	public void setArrCircle(ArrayList list) {
		arrCircle = list;
	}

	/**
	 * @param list
	 */
	public void setArrProducts(ArrayList list) {
		arrProducts = list;
	}

	/**
	 * @param string
	 */
	public void setMonth(String string) {
		month = string;
	}

	/**
	 * @param i
	 */
	public void setQuantity(int i) {
		quantity = i;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

	/**
	 * @return
	 */
	public ArrayList getArrBusinessCat() {
		return arrBusinessCat;
	}

	/**
	 * @param list
	 */
	public void setArrBusinessCat(ArrayList list) {
		arrBusinessCat = list;
	}

	/**
	 * @return
	 */
	public String getBusiness_catg() {
		return business_catg;
	}

	/**
	 * @return
	 */
	public String getCircle() {
		return circle;
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
	public void setBusiness_catg(String string) {
		business_catg = string;
	}

	/**
	 * @param string
	 */
	public void setCircle(String string) {
		circle = string;
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
	public String getMessage() {
		return message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}



	/**
	 * @return
	 */
	public ArrayList getArrMonth() {
		return arrMonth;
	}

	/**
	 * @param list
	 */
	public void setArrMonth(ArrayList list) {
		arrMonth = list;
	}

	/**
	 * @return
	 */
	public ArrayList getArrYear() {
		return arrYear;
	}

	/**
	 * @param list
	 */
	public void setArrYear(ArrayList list) {
		arrYear = list;
	}

}
