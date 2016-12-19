package com.ibm.dp.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
 
/**
 * DPPurchaseOrderFormBean class holds the Properties for Purchase Order Requisition
 *
 * @author Rohit Kunder
 */
public class DPPurchaseOrderFormBean extends ActionForm{
	
	
	private String quantity = null;//cpo 
	private String  productName = null;//cpo	
	private String   businessCategory = null ;//cpo
	private String productType = null; // cpo
	private ArrayList categoryList = null ;//cpo
	private ArrayList productTypeList = null ; //cpo
	private String methodName = "";//cpo
	public int raised_qty;//cpo
	private int productID;//cpo
	private String circleId;//cpo
	private String bg[];//cpo
	private String pn[];//cpo
	private String qty[];//cpo
	private String prdID[];//cpo
	private String prcancelStatus ="";
	
	private ArrayList pnameAList = null ;
	private String userName=null ;
	private ArrayList poList = null;
	private int catCode;
	private String porNumber=""; 
	private String productCode="";
	private String categoryCode="";
	private String categoryName="";
	public int por_no;
	public String product ="";
	public String  pr_dt ="";
	public int po_no;
	public String po_dt="";
	public int invoice_no;
	public String invoice_dt ="";
	public int dc_no;
	public String dc_dt ="";
	public int dd_cheque_no;
	public String dd_cheque_dt ="";
	public String status ="";
	private int cancelpor_no;
	private int distName;
	private String check[];
	private String roleCircleAdmin="";
	private ArrayList prCountList = null;
	private String comments="";
	private String unit;
	private String unitName;
	private ArrayList unitList;
	
	//Begin Added by Mohammad Aslam
	private String inHandQuantity = null;// cpo
	private String quantityAsPerDP = null;// cpo
	private String inHandQty[];// cpo
	private String qtyDP[];// cpo
	
	// Added by harbans 
	private String openStockAsPerDP = null;// cpo
	private String opStkDP[];// opening stock as per DP
	// END
	
	//Added By Harpreet
	private String statusSelect=null;
	private String intStatus=null;
	private ArrayList statusList;
	private String statusValue;
	private String statusId;
	private String balAmount;

	private double totalProductCost;
	private String flag ;
	private String maxPOQty[];
	private String balQty[];
	private String balAmount1[];
	private String flag1[];

	private String maxPoQty;
	private String eligibleQty;
	private ArrayList deliveryChallanList;
	private String balanceAmount;
	private String eligibleQuantity;
	private String hiddenTransaction;

	
	public String getEligibleQuantity() {
		return eligibleQuantity;
	}

	public void setEligibleQuantity(String eligibleQuantity) {
		this.eligibleQuantity = eligibleQuantity;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public void setTotalProductCost(double totalProductCost) {
		this.totalProductCost = totalProductCost;
	}

	public String getBalAmount() {
		return balAmount;
	}

	public void setBalAmount(String balAmount) {
		this.balAmount = balAmount;
	}


	public String getInHandQuantity() {
		return inHandQuantity;
	}
	
	public void setInHandQuantity(String inHandQuantity) {
		this.inHandQuantity = inHandQuantity;
	}

	public String[] getInHandQty() {
		return inHandQty;
	}

	public void setInHandQty(String[] inHandQty) {
		this.inHandQty = inHandQty;
	}

	public String getQuantityAsPerDP() {
		return quantityAsPerDP;
	}

	public void setQuantityAsPerDP(String quantityAsPerDP) {
		this.quantityAsPerDP = quantityAsPerDP;
	}

	public String[] getQtyDP() {
		return qtyDP;
	}

	public void setQtyDP(String[] qtyDP) {
		this.qtyDP = qtyDP;
	}
	//End Added by Mohammad Aslam	

	public ArrayList getUnitList() {
		return unitList;
	}

	public void setUnitList(ArrayList unitList) {
		this.unitList = unitList;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRoleCircleAdmin() {
		return roleCircleAdmin;
	}

	public void setRoleCircleAdmin(String roleCircleAdmin) {
		this.roleCircleAdmin = roleCircleAdmin;
	}

	public String[] getCheck() {
		return check;
	}

	public void setCheck(String[] check) {
		this.check = check;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void reset(){
		quantity = null;
		productName  = null;
		
		
	}
	
	public String getBusinessCategory() {
		return businessCategory;
	}
	
	public ArrayList getPnameAList() {
		return pnameAList;
	}

	public void setPnameAList(ArrayList pnameAList) {
		this.pnameAList = pnameAList;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	
	public ArrayList getPNameAList() {
		return pnameAList;
	}
	public void setPNameAList(ArrayList nameAList) {
		pnameAList = nameAList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuantity() { 
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList getPoList() {
		return poList;
	}

	public void setPoList(ArrayList poList) {
		this.poList = poList;
	}

	public int getCatCode() {
		return catCode;
	}

	public void setCatCode(int catCode) {
		this.catCode = catCode;
	}

	public String getPorNumber() {
		return porNumber;
	}

	public void setPorNumber(String porNumber) {
		this.porNumber = porNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ArrayList getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList categoryList) {
		this.categoryList = categoryList;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDc_dt() {
		return dc_dt;
	}

	public void setDc_date(String dc_date) {
		this.dc_dt = dc_date;
	}

	public int getDc_number() {
		return dc_no;
	}

	public void setDc_number(int dc_no) {
		this.dc_no = dc_no;
	}

	public String getDd_cheque_dt() {
		return dd_cheque_dt;
	}

	public void setDd_cheque_dt(String dd_cheque_dt) {
		this.dd_cheque_dt = dd_cheque_dt;
	}

	public int getDd_cheque_no() {
		return dd_cheque_no;
	}

	public void setDd_cheque_no(int dd_cheque_no) {
		this.dd_cheque_no = dd_cheque_no;
	}

	public String getInvoice_dt() {
		return invoice_dt;
	}

	public void setInvoice_dt(String invoice_dt) {
		this.invoice_dt = invoice_dt;
	}

	public int getInvoice_no() {
		return invoice_no;
	}

	public void setInvioce_no(int invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getPo_dt() {
		return po_dt;
	}

	public void setPo_dt(String po_dt) {
		this.po_dt = po_dt;
	}

	public int getPo_no() {
		return po_no;
	}

	public void setPo_no(int po_no) {
		this.po_no = po_no;
	}

	public int getPor_no() {
		return por_no;
	}

	public void setPor_no(int por_no) {
		this.por_no = por_no;
	}

	public String getPr_dt() {
		return pr_dt;
	}

	public void setPr_dt(String pr_dt) {
		this.pr_dt = pr_dt;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getRaised_qty() {
		return raised_qty;
	}

	public void setRaised_qty(int raised_qty) {
		this.raised_qty = raised_qty;
	}

	public int getCancelpor_no() {
		return cancelpor_no;
	}

	public void setCancelpor_no(int cancelpor_no) {
		this.cancelpor_no = cancelpor_no;
	}

	

	public String[] getBg() {
		return bg;
	}

	public void setBg(String[] bg) {
		this.bg = bg;
	}

	public String[] getPn() {
		return pn;
	}

	public void setPn(String[] pn) {
		this.pn = pn;
	}

	public String[] getQty() {
		return qty;
	}

	public void setQty(String[] qty) {
		this.qty = qty;
	}

	public String[] getPrdID() {
		return prdID;
	}

	public void setPrdID(String[] prdID) {
		this.prdID = prdID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public int getDc_no() {
		return dc_no;
	}

	public void setDc_no(int dc_no) {
		this.dc_no = dc_no;
	}

	public void setDc_dt(String dc_dt) {
		this.dc_dt = dc_dt;
	}

	public void setInvoice_no(int invoice_no) {
		this.invoice_no = invoice_no;
	}

	public int getDistName() {
		return distName;
	}

	public void setDistName(int distName) {
		this.distName = distName;
	}

	public String getPrcancelStatus() {
		return prcancelStatus;
	}

	public void setPrcancelStatus(String prcancelStatus) {
		this.prcancelStatus = prcancelStatus;
	}

	public ArrayList getPrCountList() {
		return prCountList;
	}

	public void setPrCountList(ArrayList prCountList) {
		this.prCountList = prCountList;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String[] getOpStkDP() {
		return opStkDP;
	}

	public void setOpStkDP(String[] opStkDP) {
		this.opStkDP = opStkDP;
	}

	public String getOpenStockAsPerDP() {
		return openStockAsPerDP;
	}

	public void setOpenStockAsPerDP(String openStockAsPerDP) {
		this.openStockAsPerDP = openStockAsPerDP;
	}

	public String getStatusSelect() {
		return statusSelect;
	}

	public void setStatusSelect(String statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getIntStatus() {
		return intStatus;
	}

	public void setIntStatus(String intStatus) {
		this.intStatus = intStatus;
	}

	public ArrayList getStatusList() {
		return statusList;
	}

	public void setStatusList(ArrayList statusList) {
		this.statusList = statusList;
	}

	

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		statusId = statusId;
	}

	public ArrayList getDeliveryChallanList() {
		return deliveryChallanList;
	}

	public void setDeliveryChallanList(ArrayList deliveryChallanList) {
		this.deliveryChallanList = deliveryChallanList;
	}


	public String[] getBalAmount1() {
		return balAmount1;
	}

	public void setBalAmount1(String[] balAmount1) {
		this.balAmount1 = balAmount1;
	}

	public String[] getBalQty() {
		return balQty;
	}

	public void setBalQty(String[] balQty) {
		this.balQty = balQty;
	}

	public String[] getMaxPOQty() {
		return maxPOQty;
	}

	public void setMaxPOQty(String[] maxPOQty) {
		this.maxPOQty = maxPOQty;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String[] getFlag1() {
		return flag1;
	}

	public void setFlag1(String[] flag1) {
		this.flag1 = flag1;
	}

	public String getEligibleQty() {
		return eligibleQty;
	}

	public void setEligibleQty(String eligibleQty) {
		this.eligibleQty = eligibleQty;
	}

	public String getMaxPoQty() {
		return maxPoQty;
	}

	public void setMaxPoQty(String maxPoQty) {
		this.maxPoQty = maxPoQty;
	}

	public double getTotalProductCost() {
		return totalProductCost;
	}

	public String getHiddenTransaction() {
		return hiddenTransaction;
	}

	public void setHiddenTransaction(String hiddenTransaction) {
		this.hiddenTransaction = hiddenTransaction;
	}

	public void setProductTypeList(ArrayList productTypeList) {
		this.productTypeList = productTypeList;
	}

	public ArrayList getProductTypeList() {
		return productTypeList;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}




}
