package com.ibm.virtualization.recharge.beans;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.dp.dto.DPProductDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;

import java.util.ArrayList;
import java.util.List;

public class DPViewProductFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Handset services
	private int bcode=0;
	private String bname=""; 	
	
	private String companyname="";
	private String productdesc="";
	private String stocktype="";	

	private String unit;
	private String unitName;
	private ArrayList unitList;
	private String unit1;
	
	// SUK Services
	private String productcode="";
	private String productname="";
	private String productname1="";
	private String cardgroup="";
	private String packtype="";
	private double mrp=0;
	private double simcardcost=0;
	private double talktime=0;
	private double activation=0;
	private double processingfee=0;
	private double salestax=0;
	private double servicetax=0;
	private double cesstax=0;
	private double vat=0;
	private double turnovertax=0;
	private double sh_educess=0;
	private double goldennumbercharge=0;
	private double discount=0;
	private double distprice=0;
	private double costprice=0;
	private double octoriprice=0;
	private String effectivedate="";
	private String version="";
	
	private String freeservice="";
	private double retDiscount;
	private double retPrice;
	
	//	 Added for DTH
	private double surcharge=0;
	private double freight=0;
	private double insurance=0;
	private double tradediscount=0;
	private double cashdiscount=0;

//	Added by rajiv jha start
	private double additionalvat=0;
	private String oracleitmecode="";
    // rajiv jha end
	
	// commmon services
	private int warranty=0;
	private int prodId=0;
	private int circleId=0;
	private String createddate="";
	private String circleName="";
	private String circlename="";
	private String categoryname="";
	private String createdby="";
	private String updatedby="";
	private String selectedSub;
	private String status="A";
	private String datecreeated = null;
	private String dateupdated = null;
	private int categorycode=0;
	private ArrayList arrBCList=null;
	private String subList="";
	//private String productid="";
	
	private int productid=0;
	private String LoginName="";
	private String businessCategory ="";
	private ArrayList list;
	private ArrayList productList;
	private ArrayList arrCIList;
	//Added by Shilpa
	private List<DpProductTypeMasterDto>  dcProductTypeList  = new ArrayList<DpProductTypeMasterDto>();
	
//	 Add by harbans on Reservation Obserbation 30th June
	private List<DpProductTypeMasterDto>  reverseProductList  = new ArrayList<DpProductTypeMasterDto>();
	private String productTypeId = "";
	private String parentProductId = "";
	private int extProductId = 0;
//	Added by Shilpa Khanna to add Product Category
	private List<DpProductCategoryDto>  dcProductCategoryList  = new ArrayList<DpProductCategoryDto>();
	private String productCategory ="";
	private String itmeCodeAv;
	private String prodValue ="";
	
	
	
	//aman
	private String dpCircleName="";
	private String prodCode="";
	private String prodName="";	
	private String cardGrpName;	
	private String prodType;	
	private String dpMrp;	
	private String basicValue;
	private String rechargeValue;
	private String dpActivation;
	private String serviceTax;
	private String cess;
	private String cessTax;
	private String distMargin;	
	private String distPrice;
	private String effectiveDate;
	private String creationDate;
	private String dpStatus;
	
	private List<DPProductDto> prodList =new ArrayList<DPProductDto>();
	
	//end by aman
	
	//aman
	private String productStatus =""; 
	
	
	
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public int getExtProductId() {
		return extProductId;
	}
	public String getDpCircleName() {
		return dpCircleName;
	}
	public void setDpCircleName(String dpCircleName) {
		this.dpCircleName = dpCircleName;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getCardGrpName() {
		return cardGrpName;
	}
	public void setCardGrpName(String cardGrpName) {
		this.cardGrpName = cardGrpName;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getDpMrp() {
		return dpMrp;
	}
	public void setDpMrp(String dpMrp) {
		this.dpMrp = dpMrp;
	}
	public String getBasicValue() {
		return basicValue;
	}
	public void setBasicValue(String basicValue) {
		this.basicValue = basicValue;
	}
	public String getRechargeValue() {
		return rechargeValue;
	}
	public void setRechargeValue(String rechargeValue) {
		this.rechargeValue = rechargeValue;
	}
	public String getDpActivation() {
		return dpActivation;
	}
	public void setDpActivation(String dpActivation) {
		this.dpActivation = dpActivation;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getCess() {
		return cess;
	}
	public void setCess(String cess) {
		this.cess = cess;
	}
	public String getCessTax() {
		return cessTax;
	}
	public void setCessTax(String cessTax) {
		this.cessTax = cessTax;
	}
	public String getDistMargin() {
		return distMargin;
	}
	public void setDistMargin(String distMargin) {
		this.distMargin = distMargin;
	}
	public String getDistPrice() {
		return distPrice;
	}
	public void setDistPrice(String distPrice) {
		this.distPrice = distPrice;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getDpStatus() {
		return dpStatus;
	}
	public void setDpStatus(String dpStatus) {
		this.dpStatus = dpStatus;
	}
	public List<DPProductDto> getProdList() {
		return prodList;
	}
	public void setProdList(List<DPProductDto> prodList) {
		this.prodList = prodList;
	}
	public void setExtProductId(int extProductId) {
		this.extProductId = extProductId;
	}
	public ArrayList getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
	}
	public ArrayList getList() {
		return list;
	}
	public void setList(ArrayList list) {
		this.list = list;
	}


	public void reset(){

	        companyname=null;
	        productdesc=null;
			productname=null;
			cardgroup=null;
		    packtype=null;
		    //productTypeId=null;
		    mrp=0;
		    simcardcost=0;
	        talktime=0;
	        activation=0;
           processingfee=0;
           salestax=0;
           servicetax=0;
           cesstax=0;
           vat=0;
           turnovertax=0;
           sh_educess=0;
           goldennumbercharge=0;
           discount=0;
           distprice=0;
           costprice=0;
           octoriprice=0;
           effectivedate=null;
         version=null;	
         warranty=0;
         selectedSub="0";
         surcharge=0;
     	freight=0;
     	insurance=0;
     	tradediscount=0;
     	cashdiscount=0;
	}
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

			ActionErrors errors = new ActionErrors();
			// Validate the fields in your form, adding
			// adding each error to this.errors as found, e.g.

			// if ((field == null) || (field.length() == 0)) {
			//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
			// }
			return errors;

		}
	public double getActivation() {
		return activation;
	}
	public void setActivation(double activation) {
		this.activation = activation;
	}
	public ArrayList getArrBCList() {
		return arrBCList;
	}
	public void setArrBCList(ArrayList arrBCList) {
		this.arrBCList = arrBCList;
	}
	public int getBcode() {
		return bcode;
	}
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getBusinessCategory() {
		return businessCategory;
	}
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
	public String getCardgroup() {
		return cardgroup;
	}
	public void setCardgroup(String cardgroup) {
		this.cardgroup = cardgroup;
	}
	
	public double getCesstax() {
		return cesstax;
	}
	public void setCesstax(double cesstax) {
		this.cesstax = cesstax;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public double getCostprice() {
		return costprice;
	}
	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getDatecreeated() {
		return datecreeated;
	}
	public void setDatecreeated(String datecreeated) {
		this.datecreeated = datecreeated;
	}
	public String getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(String dateupdated) {
		this.dateupdated = dateupdated;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getDistprice() {
		return distprice;
	}
	public void setDistprice(double distprice) {
		this.distprice = distprice;
	}
	public String getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}
	public String getFreeservice() {
		return freeservice;
	}
	public void setFreeservice(String freeservice) {
		this.freeservice = freeservice;
	}
	public double getGoldennumbercharge() {
		return goldennumbercharge;
	}
	public void setGoldennumbercharge(double goldennumbercharge) {
		this.goldennumbercharge = goldennumbercharge;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	public double getOctoriprice() {
		return octoriprice;
	}
	public void setOctoriprice(double octoriprice) {
		this.octoriprice = octoriprice;
	}
	public String getPacktype() {
		return packtype;
	}
	public void setPacktype(String packtype) {
		this.packtype = packtype;
	}
	public double getProcessingfee() {
		return processingfee;
	}
	public void setProcessingfee(double processingfee) {
		this.processingfee = processingfee;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	
	public ArrayList getProductList() {
		return productList;
	}
	public void setProductList(ArrayList productList) {
		this.productList = productList;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public double getSalestax() {
		return salestax;
	}
	public void setSalestax(double salestax) {
		this.salestax = salestax;
	}
	public String getSelectedSub() {
		return selectedSub;
	}
	public void setSelectedSub(String selectedSub) {
		this.selectedSub = selectedSub;
	}
	public double getServicetax() {
		return servicetax;
	}
	public void setServicetax(double servicetax) {
		this.servicetax = servicetax;
	}
	public double getSh_educess() {
		return sh_educess;
	}
	public void setSh_educess(double sh_educess) {
		this.sh_educess = sh_educess;
	}
	public double getSimcardcost() {
		return simcardcost;
	}
	public void setSimcardcost(double simcardcost) {
		this.simcardcost = simcardcost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStocktype() {
		return stocktype;
	}
	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}
	public String getSubList() {
		return subList;
	}
	public void setSubList(String subList) {
		this.subList = subList;
	}
	public double getTalktime() {
		return talktime;
	}
	public void setTalktime(double talktime) {
		this.talktime = talktime;
	}
	public double getTurnovertax() {
		return turnovertax;
	}
	public void setTurnovertax(double turnovertax) {
		this.turnovertax = turnovertax;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getWarranty() {
		return warranty;
	}
	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public int getCategorycode() {
		return categorycode;
	}
	public void setCategorycode(int categorycode) {
		this.categorycode = categorycode;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getProductname1() {
		return productname1;
	}
	public void setProductname1(String productname1) {
		this.productname1 = productname1;
	}
	public String getCirclename() {
		return circlename;
	}
	public void setCirclename(String circlename) {
		this.circlename = circlename;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public double getRetDiscount() {
		return retDiscount;
	}
	public void setRetDiscount(double retDiscount) {
		this.retDiscount = retDiscount;
	}
	public double getRetPrice() {
		return retPrice;
	}
	public void setRetPrice(double retPrice) {
		this.retPrice = retPrice;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
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
	public String getUnit1() {
		return unit1;
	}
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}
	public double getCashdiscount() {
		return cashdiscount;
	}
	public void setCashdiscount(double cashdiscount) {
		this.cashdiscount = cashdiscount;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public double getInsurance() {
		return insurance;
	}
	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}
	public double getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}
	public double getTradediscount() {
		return tradediscount;
	}
	public void setTradediscount(double tradediscount) {
		this.tradediscount = tradediscount;
	}
	
	private ArrayList arrCGList;





	public ArrayList getArrCGList() {
		return arrCGList;
	}
	public void setArrCGList(ArrayList arrCGList) {
		this.arrCGList = arrCGList;
	}
	public double getAdditionalvat() {
		return additionalvat;
	}
	public void setAdditionalvat(double additionalvat) {
		this.additionalvat = additionalvat;
	}
	public String getOracleitmecode() {
		return oracleitmecode;
	}
	public void setOracleitmecode(String oracleitmecode) {
		this.oracleitmecode = oracleitmecode;
	}
	public List<DpProductTypeMasterDto> getDcProductTypeList() {
		return dcProductTypeList;
	}
	public void setDcProductTypeList(List<DpProductTypeMasterDto> dcProductTypeList) {
		this.dcProductTypeList = dcProductTypeList;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public List<DpProductTypeMasterDto> getReverseProductList() {
		return reverseProductList;
	}
	public void setReverseProductList(
			List<DpProductTypeMasterDto> reverseProductList) {
		this.reverseProductList = reverseProductList;
	}
	public String getParentProductId() {
		return parentProductId;
	}
	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}
	public List<DpProductCategoryDto> getDcProductCategoryList() {
		return dcProductCategoryList;
	}
	public void setDcProductCategoryList(
			List<DpProductCategoryDto> dcProductCategoryList) {
		this.dcProductCategoryList = dcProductCategoryList;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getItmeCodeAv() {
		return itmeCodeAv;
	}
	public void setItmeCodeAv(String itmeCodeAv) {
		this.itmeCodeAv = itmeCodeAv;
	}
	public String getProdValue() {
		return prodValue;
	}
	public void setProdValue(String prodValue) {
		this.prodValue = prodValue;
	}
	

}
