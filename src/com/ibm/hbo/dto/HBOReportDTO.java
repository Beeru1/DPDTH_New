package com.ibm.hbo.dto;

import java.io.Serializable;
import java.util.ArrayList;

/** 
	* @author Parul
	* Created On 13 Dec,2007


**/
public class HBOReportDTO implements Serializable {


// Sidharth

   String roleId;
   String mcode;
   String circleId;
   String warehouse_to;
   String month_from;
   String toDate;
   String date;
   String requisitionId;
   String requisition_qty;
   String for_month;
   String qty_received;
   String invoice_dt;
   String variance;
   String mname;
   String fromDate;
   String created_dt;
   String imei_no;
   String msidn_no;
   String sim_no;

   String primary_sales;
   String damage_stock;

	//Begin--Declared By Parul 
	String modelCode;
	String circleName;
	String warehouseName;
	String bundleSIH;
	String unbundleSIH;
	String totaleSIH;
	String report;
	ArrayList reportData;
	String stockInHand;
	String primarySale;
	String secondarySale;
	String apr_sale;
	String may_sale;
	String jun_sale;
	String jul_sale;
	String aug_sale;
	String sep_sale;
	String oct_sale;
	String nov_sale;
	String dec_sale;
	String jan_sale;
	String feb_sale;
	String mar_sale;
	String currMonth;
	String nextMonth;
	String nxtnxtMonth;
	int totalCurrMonth=0;
	int totalNextMonth=0;
	int totalNxtnxtMonth=0;
	String requisitionID;
	String invoiceDt;
	String imeiNO;
	String warehouseId;
	String simNo;
	String msidnNo;

	String minReorderQty;
	String warehouseCity;
	String stckInHand;

	String month;
	String year; 
	String projectionQty;

	
	String weightedVar;
	String weightedVarPer;
	String qty_used;
	String qty_available;

//End-Declared By Parul 


/**
 * @return
 */
public String getBundleSIH() {
	return bundleSIH;
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
public String getModelCode() {
	return modelCode;
}

/**
 * @return
 */
public String getTotaleSIH() {
	return totaleSIH;
}

/**
 * @return
 */
public String getUnbundleSIH() {
	return unbundleSIH;
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
public void setBundleSIH(String string) {
	bundleSIH = string;
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
public void setModelCode(String string) {
	modelCode = string;
}

/**
 * @param string
 */
public void setTotaleSIH(String string) {
	totaleSIH = string;
}

/**
 * @param string
 */
public void setUnbundleSIH(String string) {
	unbundleSIH = string;
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
public String getCircleId() {
	return circleId;
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
public String getDamage_stock() {
	return damage_stock;
}

/**
 * @return
 */
public String getDate() {
	return date;
}

/**
 * @return
 */
public String getFor_month() {
	return for_month;
}

/**
 * @return
 */
public String getFromDate() {
	return fromDate;
}

/**
 * @return
 */
public String getImei_no() {
	return imei_no;
}

/**
 * @return
 */
public String getInvoice_dt() {
	return invoice_dt;
}

/**
 * @return
 */
public String getMcode() {
	return mcode;
}

/**
 * @return
 */
public String getMname() {
	return mname;
}

/**
 * @return
 */
public String getMonth_from() {
	return month_from;
}

/**
 * @return
 */
public String getMsidn_no() {
	return msidn_no;
}

/**
 * @return
 */
public String getPrimary_sales() {
	return primary_sales;
}

/**
 * @return
 */
public String getQty_received() {
	return qty_received;
}

/**
 * @return
 */
public String getRequisition_qty() {
	return requisition_qty;
}

/**
 * @return
 */
public String getRequisitionId() {
	return requisitionId;
}

/**
 * @return
 */
public String getRoleId() {
	return roleId;
}

/**
 * @return
 */
public String getSim_no() {
	return sim_no;
}

/**
 * @return
 */
public String getToDate() {
	return toDate;
}

/**
 * @return
 */
public String getVariance() {
	return variance;
}

/**
 * @return
 */
public String getWarehouse_to() {
	return warehouse_to;
}

/**
 * @param string
 */
public void setCircleId(String string) {
	circleId = string;
}

/**
 * @param string
 */
public void setCreated_dt(String string) {
	created_dt = string;
}

/**
 * @param string
 */
public void setDamage_stock(String string) {
	damage_stock = string;
}

/**
 * @param string
 */
public void setDate(String string) {
	date = string;
}

/**
 * @param string
 */
public void setFor_month(String string) {
	for_month = string;
}

/**
 * @param string
 */
public void setFromDate(String string) {
	fromDate = string;
}

/**
 * @param string
 */
public void setImei_no(String string) {
	imei_no = string;
}

/**
 * @param string
 */
public void setInvoice_dt(String string) {
	invoice_dt = string;
}

/**
 * @param string
 */
public void setMcode(String string) {
	mcode = string;
}

/**
 * @param string
 */
public void setMname(String string) {
	mname = string;
}

/**
 * @param string
 */
public void setMonth_from(String string) {
	month_from = string;
}

/**
 * @param string
 */
public void setMsidn_no(String string) {
	msidn_no = string;
}

/**
 * @param string
 */
public void setPrimary_sales(String string) {
	primary_sales = string;
}

/**
 * @param string
 */
public void setQty_received(String string) {
	qty_received = string;
}

/**
 * @param string
 */
public void setRequisition_qty(String string) {
	requisition_qty = string;
}

/**
 * @param string
 */
public void setRequisitionId(String string) {
	requisitionId = string;
}

/**
 * @param string
 */
public void setRoleId(String string) {
	roleId = string;
}

/**
 * @param string
 */
public void setSim_no(String string) {
	sim_no = string;
}

/**
 * @param string
 */
public void setToDate(String string) {
	toDate = string;
}

/**
 * @param string
 */
public void setVariance(String string) {
	variance = string;
}

/**
 * @param string
 */
public void setWarehouse_to(String string) {
	warehouse_to = string;
}

/**
 * @return
 */
public String getApr_sale() {
	return apr_sale;
}

/**
 * @return
 */
public String getAug_sale() {
	return aug_sale;
}

/**
 * @return
 */
public String getDec_sale() {
	return dec_sale;
}

/**
 * @return
 */
public String getFeb_sale() {
	return feb_sale;
}

/**
 * @return
 */
public String getJan_sale() {
	return jan_sale;
}

/**
 * @return
 */
public String getJul_sale() {
	return jul_sale;
}

/**
 * @return
 */
public String getJun_sale() {
	return jun_sale;
}

/**
 * @return
 */
public String getMar_sale() {
	return mar_sale;
}

/**
 * @return
 */
public String getMay_sale() {
	return may_sale;
}

/**
 * @return
 */
public String getNov_sale() {
	return nov_sale;
}

/**
 * @return
 */
public String getOct_sale() {
	return oct_sale;
}

/**
 * @return
 */
public String getPrimarySale() {
	return primarySale;
}

	/**
	 * @return
	 */
	public String getReport() {
		return report;
	}

/**
 * @return
 */
public ArrayList getReportData() {
	return reportData;
}

/**
 * @return
 */
public String getSecondarySale() {
	return secondarySale;
}

/**
 * @return
 */
public String getSep_sale() {
	return sep_sale;
}

/**
 * @return
 */
public String getStockInHand() {
	return stockInHand;
}

/**
 * @param string
 */
public void setApr_sale(String string) {
	apr_sale = string;
}

/**
 * @param string
 */
public void setAug_sale(String string) {
	aug_sale = string;
}

/**
 * @param string
 */
public void setDec_sale(String string) {
	dec_sale = string;
}

/**
 * @param string
 */
public void setFeb_sale(String string) {
	feb_sale = string;
}

/**
 * @param string
 */
public void setJan_sale(String string) {
	jan_sale = string;
}

/**
 * @param string
 */
public void setJul_sale(String string) {
	jul_sale = string;
}

/**
 * @param string
 */
public void setJun_sale(String string) {
	jun_sale = string;
}

/**
 * @param string
 */
public void setMar_sale(String string) {
	mar_sale = string;
}

/**
 * @param string
 */
public void setMay_sale(String string) {
	may_sale = string;
}

/**
 * @param string
 */
public void setNov_sale(String string) {
	nov_sale = string;
}

/**
 * @param string
 */
public void setOct_sale(String string) {
	oct_sale = string;
}

/**
 * @param string
 */
public void setPrimarySale(String string) {
	primarySale = string;
}

	/**
	 * @param string
	 */
	public void setReport(String string) {
		report = string;
	}

/**
 * @param list
 */
public void setReportData(ArrayList list) {
	reportData = list;
}

/**
 * @param string
 */
public void setSecondarySale(String string) {
	secondarySale = string;
}

/**
 * @param string
 */
public void setSep_sale(String string) {
	sep_sale = string;
}

/**
 * @param string
 */
public void setStockInHand(String string) {
	stockInHand = string;
}

	/**
	 * @return
	 */
	public int getTotalCurrMonth() {
		return totalCurrMonth;
	}

	/**
	 * @return
	 */
	public int getTotalNextMonth() {
		return totalNextMonth;
	}

	/**
	 * @return
	 */
	public int getTotalNxtnxtMonth() {
		return totalNxtnxtMonth;
	}

	/**
	 * @param i
	 */
	public void setTotalCurrMonth(int i) {
		totalCurrMonth = i;
	}

	/**
	 * @param i
	 */
	public void setTotalNextMonth(int i) {
		totalNextMonth = i;
	}

	/**
	 * @param i
	 */
	public void setTotalNxtnxtMonth(int i) {
		totalNxtnxtMonth = i;
	}

	/**
	 * @return
	 */
	public String getCurrMonth() {
		return currMonth;
	}

	/**
	 * @return
	 */
	public String getNextMonth() {
		return nextMonth;
	}

	/**
	 * @return
	 */
	public String getNxtnxtMonth() {
		return nxtnxtMonth;
	}

	/**
	 * @param string
	 */
	public void setCurrMonth(String string) {
		currMonth = string;
	}

	/**
	 * @param string
	 */
	public void setNextMonth(String string) {
		nextMonth = string;
	}

	/**
	 * @param string
	 */
	public void setNxtnxtMonth(String string) {
		nxtnxtMonth = string;
	}

	/**
	 * @return
	 */
	public String getImeiNO() {
		return imeiNO;
	}

	/**
	 * @return
	 */
	public String getInvoiceDt() {
		return invoiceDt;
	}

	/**
	 * @return
	 */
	public String getMinReorderQty() {
		return minReorderQty;
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
	public String getMsidnNo() {
		return msidnNo;
	}

	/**
	 * @return
	 */
	public String getProjectionQty() {
		return projectionQty;
	}

	/**
	 * @return
	 */
	public String getRequisitionID() {
		return requisitionID;
	}

	/**
	 * @return
	 */
	public String getSimNo() {
		return simNo;
	}

	/**
	 * @return
	 */
	public String getStckInHand() {
		return stckInHand;
	}

	/**
	 * @return
	 */
	public String getWarehouseCity() {
		return warehouseCity;
	}

	/**
	 * @return
	 */
	public String getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @return
	 */
	public String getWeightedVar() {
		return weightedVar;
	}

	/**
	 * @return
	 */
	public String getWeightedVarPer() {
		return weightedVarPer;
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
	public void setImeiNO(String string) {
		imeiNO = string;
	}

	/**
	 * @param string
	 */
	public void setInvoiceDt(String string) {
		invoiceDt = string;
	}

	/**
	 * @param string
	 */
	public void setMinReorderQty(String string) {
		minReorderQty = string;
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
	public void setMsidnNo(String string) {
		msidnNo = string;
	}

	/**
	 * @param string
	 */
	public void setProjectionQty(String string) {
		projectionQty = string;
	}

	/**
	 * @param string
	 */
	public void setRequisitionID(String string) {
		requisitionID = string;
	}

	/**
	 * @param string
	 */
	public void setSimNo(String string) {
		simNo = string;
	}

	/**
	 * @param string
	 */
	public void setStckInHand(String string) {
		stckInHand = string;
	}

	/**
	 * @param string
	 */
	public void setWarehouseCity(String string) {
		warehouseCity = string;
	}

	/**
	 * @param string
	 */
	public void setWarehouseId(String string) {
		warehouseId = string;
	}

	/**
	 * @param string
	 */
	public void setWeightedVar(String string) {
		weightedVar = string;
	}

	/**
	 * @param string
	 */
	public void setWeightedVarPer(String string) {
		weightedVarPer = string;
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
	public String getQty_available() {
		return qty_available;
	}

	/**
	 * @return
	 */
	public String getQty_used() {
		return qty_used;
	}

	/**
	 * @param string
	 */
	public void setQty_available(String string) {
		qty_available = string;
	}

	/**
	 * @param string
	 */
	public void setQty_used(String string) {
		qty_used = string;
	}

}
