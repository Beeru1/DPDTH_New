package com.ibm.hbo.dto;

import java.io.Serializable;
import java.util.ArrayList;

/** 
	* @author Avadesh 
	* Created On Tue Sep 25 17:26:13 IST 2007 
	* Data Trasnfer Object class for table ARC_USER_MSTR 

**/
public class HBOStockDTO implements Serializable {

private String batch_no = ""; 
 private int warehouse_from = 0;     //Looged In User ID
 private int warehouseId = 0;  //warehouseId;
 private int assignedSimQty = 0;   //ASSIGNED_QTY;
 private String created_by  = "";  //Looged In User ID
 private String status  = ""; 		//H or T
 private String product_type  = "";  // S or H
 private int avlSimStock =0; 
 private String avlStock="";
 private String updated_by  = "";  //Looged In User ID
	private String simnoList ="";
	private int loginuser_warehouse_id= 0;
	private String created_dt ="";
	private String updated_dt ="";		
	private String[] arrStatus;
	private String[] arrBatch;
	private String simNo = "";
	private String msidnNo = "";
	private String warehouseTo_Name;	
	private String stockStatus;
	// For View Bundled Stock
	
	private int requestId;
	private int bundledQty;
	private String modelCode;
	private String modelName;
	private String companyName;
	private String circleName;
	private String bundledDt;
	private String imeiNo;
	private String damageFlag = "";
	
	private int assignedProdQty = 0;
	private String product_code= "";
	private String bundled_flag = "";
	private String assign_date = "";	
	private String invoice_no = "";
	private String invoice_date = "";
	private String IMEINo = ""; 
	private String issuance_date = "";
	private String receive_date = "";
	private String company_name = "";
	private String model_code = ""; 
	private String model_name = "";


	private String avlPairedStock="";	
	private String avlProducts="0";

	private String circle="";
	private String pseudo_prodcode="";
	private	String product="";
	private String bundle="";
	private String avlProdStock="";
	private String projQty="";
	private String availableProdQty="";

	private	String month="";
	private	String year="";
	private	int quantity=0;
	private Long id;
	private	String ImeiNo="";
	private String serialNo="";
	private String remarks="";
	private float cost=0;
	private ArrayList roleList=new ArrayList();
	 


public ArrayList getRoleList() {
		return roleList;
	}

	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}

/**
 * @return
 */
public int getAssignedSimQty() {
	return assignedSimQty;
}

/**
 * @return
 */
public int getAvlSimStock() {
	return avlSimStock;
}

/**
 * @return
 */
public String getBatch_no() {
	return batch_no;
}

/**
 * @return
 */
public String getCreated_by() {
	return created_by;
}

/**
 * @return
 */
public String getCreated_dt() {
	return created_dt;
}

/**
 * @return
 */
public int getLoginuser_warehouse_id() {
	return loginuser_warehouse_id;
}

/**
 * @return
 */
public String getProduct_type() {
	return product_type;
}

/**
 * @return
 */
public String getSimnoList() {
	return simnoList;
}

/**
 * @return
 */
public String getStatus() {
	return status;
}

/**
 * @return
 */
public String getUpdated_by() {
	return updated_by;
}

/**
 * @return
 */
public String getUpdated_dt() {
	return updated_dt;
}

/**
 * @return
 */
public int getWarehouse_from() {
	return warehouse_from;
}

/**
 * @return
 */
public int getWarehouseId() {
	return warehouseId;
}

/**
 * @param i
 */
public void setAssignedSimQty(int i) {
	assignedSimQty = i;
}

/**
 * @param i
 */
public void setAvlSimStock(int i) {
	avlSimStock = i;
}

/**
 * @param string
 */
public void setBatch_no(String string) {
	batch_no = string;
}

/**
 * @param string
 */
public void setCreated_by(String string) {
	created_by = string;
}

/**
 * @param string
 */
public void setCreated_dt(String string) {
	created_dt = string;
}

/**
 * @param i
 */
public void setLoginuser_warehouse_id(int i) {
	loginuser_warehouse_id = i;
}

/**
 * @param string
 */
public void setProduct_type(String string) {
	product_type = string;
}

/**
 * @param string
 */
public void setSimnoList(String string) {
	simnoList = string;
}

/**
 * @param string
 */
public void setStatus(String string) {
	status = string;
}

/**
 * @param string
 */
public void setUpdated_by(String string) {
	updated_by = string;
}

/**
 * @param string
 */
public void setUpdated_dt(String string) {
	updated_dt = string;
}

/**
 * @param i
 */
public void setWarehouse_from(int i) {
	warehouse_from = i;
}

/**
 * @param i
 */
public void setWarehouseId(int i) {
	warehouseId = i;
}

/**
 * @return
 */
public String[] getArrBatch() {
	return arrBatch;
}

/**
 * @return
 */
public String[] getArrStatus() {
	return arrStatus;
}

/**
 * @param strings
 */
public void setArrBatch(String[] strings) {
	arrBatch = strings;
}

/**
 * @param strings
 */
public void setArrStatus(String[] strings) {
	arrStatus = strings;
}

/**
 * @return
 */

/**
 * @param string
 */


/**
 * @return
 */
public String getAvlStock() {
	return avlStock;
}

/**
 * @param string
 */
public void setAvlStock(String string) {
	avlStock = string;
}

/**
 * @return
 */
public String getMsidnNo() {
	return msidnNo;
}

/**
 * @return
 */
public String getSimNo() {
	return simNo;
}

/**
 * @param string
 */
public void setMsidnNo(String string) {
	msidnNo = string;
}

/**
 * @param string
 */
public void setSimNo(String string) {
	simNo = string;
}

/**
 * @return
 */
public String getWarehouseTo_Name() {
	return warehouseTo_Name;
}

/**
 * @param string
 */
public void setWarehouseTo_Name(String string) {
	warehouseTo_Name = string;
}

	/**
	 * @return
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @return
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @return
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @return
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @return
	 */
	public int getRequestId() {
		return requestId;
	}

	/**
	 * @param string
	 */
	public void setCircleName(String string) {
		circleName = string;
	}

	/**
	 * @param string
	 */
	public void setCompanyName(String string) {
		companyName = string;
	}

	/**
	 * @param string
	 */
	public void setModelCode(String string) {
		modelCode = string;
	}

	/**
	 * @param string
	 */
	public void setModelName(String string) {
		modelName = string;
	}

	/**
	 * @param i
	 */
	public void setRequestId(int i) {
		requestId = i;
	}

	/**
	 * @return
	 */
	public String getBundledDt() {
		return bundledDt;
	}

	/**
	 * @param string
	 */
	public void setBundledDt(String string) {
		bundledDt = string;
	}

	/**
	 * @return
	 */
	public int getBundledQty() {
		return bundledQty;
	}

	/**
	 * @param i
	 */
	public void setBundledQty(int i) {
		bundledQty = i;
	}

	/**
	 * @return
	 */
	public String getImeiNo() {
		return imeiNo;
	}

	/**
	 * @param string
	 */
	public void setImeiNo(String string) {
		imeiNo = string;
	}

	/**
	 * @return
	 */
	public String getAssign_date() {
		return assign_date;
	}

	/**
	 * @return
	 */
	public int getAssignedProdQty() {
		return assignedProdQty;
	}

	/**
	 * @return
	 */
	public String getBundled_flag() {
		return bundled_flag;
	}

	/**
	 * @return
	 */
	public String getCompany_name() {
		return company_name;
	}

	/**
	 * @return
	 */
	public String getIMEINo() {
		return IMEINo;
	}

	/**
	 * @return
	 */
	public String getInvoice_date() {
		return invoice_date;
	}

	/**
	 * @return
	 */
	public String getInvoice_no() {
		return invoice_no;
	}

	/**
	 * @return
	 */
	public String getIssuance_date() {
		return issuance_date;
	}

	/**
	 * @return
	 */
	public String getModel_code() {
		return model_code;
	}

	/**
	 * @return
	 */
	public String getModel_name() {
		return model_name;
	}

	/**
	 * @return
	 */
	public String getProduct_code() {
		return product_code;
	}

	/**
	 * @return
	 */
	public String getReceive_date() {
		return receive_date;
	}

	/**
	 * @param string
	 */
	public void setAssign_date(String string) {
		assign_date = string;
	}

	/**
	 * @param i
	 */
	public void setAssignedProdQty(int i) {
		assignedProdQty = i;
	}

	/**
	 * @param string
	 */
	public void setBundled_flag(String string) {
		bundled_flag = string;
	}

	/**
	 * @param string
	 */
	public void setCompany_name(String string) {
		company_name = string;
	}

	/**
	 * @param string
	 */
	public void setIMEINo(String string) {
		IMEINo = string;
	}

	/**
	 * @param string
	 */
	public void setInvoice_date(String string) {
		invoice_date = string;
	}

	/**
	 * @param string
	 */
	public void setInvoice_no(String string) {
		invoice_no = string;
	}

	/**
	 * @param string
	 */
	public void setIssuance_date(String string) {
		issuance_date = string;
	}

	/**
	 * @param string
	 */
	public void setModel_code(String string) {
		model_code = string;
	}

	/**
	 * @param string
	 */
	public void setModel_name(String string) {
		model_name = string;
	}

	/**
	 * @param string
	 */
	public void setProduct_code(String string) {
		product_code = string;
	}

	/**
	 * @param string
	 */
	public void setReceive_date(String string) {
		receive_date = string;
	}

	/**
	 * @return
	 */
	public String getAvailableProdQty() {
		return availableProdQty;
	}

	/**
	 * @return
	 */
	public String getAvlPairedStock() {
		return avlPairedStock;
	}

	/**
	 * @return
	 */
	public String getAvlProdStock() {
		return avlProdStock;
	}

	/**
	 * @return
	 */
	public String getAvlProducts() {
		return avlProducts;
	}

	/**
	 * @return
	 */
	public String getBundle() {
		return bundle;
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
	public String getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @return
	 */
	public String getProjQty() {
		return projQty;
	}

	/**
	 * @return
	 */
	public String getPseudo_prodcode() {
		return pseudo_prodcode;
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
	 * @param string
	 */
	public void setAvailableProdQty(String string) {
		availableProdQty = string;
	}

	/**
	 * @param string
	 */
	public void setAvlPairedStock(String string) {
		avlPairedStock = string;
	}

	/**
	 * @param string
	 */
	public void setAvlProdStock(String string) {
		avlProdStock = string;
	}

	/**
	 * @param string
	 */
	public void setAvlProducts(String string) {
		avlProducts = string;
	}

	/**
	 * @param string
	 */
	public void setBundle(String string) {
		bundle = string;
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
	public void setMonth(String string) {
		month = string;
	}

	/**
	 * @param string
	 */
	public void setProduct(String string) {
		product = string;
	}

	/**
	 * @param string
	 */
	public void setProjQty(String string) {
		projQty = string;
	}

	/**
	 * @param string
	 */
	public void setPseudo_prodcode(String string) {
		pseudo_prodcode = string;
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
	public String getDamageFlag() {
		return damageFlag;
	}

	/**
	 * @param string
	 */
	public void setDamageFlag(String string) {
		damageFlag = string;
	}

	/**
	 * @return
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * @param string
	 */
	public void setStockStatus(String string) {
		stockStatus = string;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
