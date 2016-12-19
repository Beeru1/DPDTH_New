package com.ibm.hbo.dto;

import java.io.Serializable;

/** 
	* @author Sachin 
	* Created On Tue Sep 25 17:26:13 IST 2007 
	* Data Trasnfer Object class for table ARC_USER_MSTR 

**/
public class HBOWarrantyDTO implements Serializable {

	 
	
	 String imeino="";
	 int warehouseId=0;
	 String warehouseName="";
	 String damageStatus="";
	 String stWarranty="";
	 int extWarranty=0;
	 String prCode="";
	 
	
	

	/**
	 * @return
	 */
	public String getImeino() {
		return imeino;
	}

	/**
	 * @param string
	 */
	public void setImeino(String string) {
		imeino = string;
	}

	/**
	 * @return
	 */
	public String getDamageStatus() {
		return damageStatus;
	}

	/**
	 * @return
	 */
	

	/**
	 * @return
	 */
	public String getPrCode() {
		return prCode;
	}

	/**
	 * @return
	 */
	

	/**
	 * @return
	 */
	public int getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @param string
	 */
	public void setDamageStatus(String string) {
		damageStatus = string;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @param string
	 */
	public void setPrCode(String string) {
		prCode = string;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @param i
	 */
	public void setWarehouseId(int i) {
		warehouseId = i;
	}

	/**
	 * @return
	 */
	public String getWarehouseName() {
		return warehouseName;
	}

	/**
	 * @param string
	 */
	public void setWarehouseName(String string) {
		warehouseName = string;
	}

	/**
	 * @return
	 */
	public String getStWarranty() {
		return stWarranty;
	}

	/**
	 * @param string
	 */
	public void setStWarranty(String string) {
		stWarranty = string;
	}

	/**
	 * @return
	 */
	public int getExtWarranty() {
		return extWarranty;
	}

	/**
	 * @param i
	 */
	public void setExtWarranty(int i) {
		extWarranty = i;
	}

}
