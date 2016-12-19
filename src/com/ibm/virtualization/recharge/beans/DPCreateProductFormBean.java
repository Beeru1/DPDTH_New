package com.ibm.virtualization.recharge.beans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;



import java.util.ArrayList;
import java.util.List;

/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DPCreateProductFormBean extends ValidatorForm {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5562368733705800196L;
	
	
	
	private String unit;
	private String unitName;
	private ArrayList unitList;
	private String unit1;
	
	// Handset services
	private int bcode=0;
	private String bname=""; 	
	private String modelcode="";
	private String modelname="";
	private String companyname="";
	private String productdesc="";
	private String stocktype="";
	private String circleCode="";
	private String showCircle="";

		
	
	// SUK Services
	private String productType="";
	private String productcode="";
	private String productname="";
	private String productname1="";
	private String Productname2="";
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
	private String version="1.0";
	private double distMargin; 
	private String freeservice="";
	private double retDiscount;
	private double retPrice;
	
	// Added for DTH
	private double surcharge=0;
	private double freight=0;
	private double insurance=0;
	private double tradediscount=0;
	private double cashdiscount=0;
	

	
	
	
	// commmon services
	private int prodStatus=1;
	private int warranty=0;
	private String createddate="";
	private String createdby="";
	private String updatedby="";
	private String selectedSub;
	private String status="A";
	private String datecreeated = null;
	private String dateupdated = null;
	private String categorycode="";
	private ArrayList arrBCList=null;
	private String subList="";
	private String productid="";
	private String LoginName="";
	private String businessCategory =""; 
	private ArrayList arrCIList=null;
	
	private int circleid = 0;
	private int packTypeInt=0;
	private double activationDouble;
	private String parent_product_ext_id ="";
	
//	Added by Shilpa
	private List<DpProductTypeMasterDto>  dcProductTypeList  = new ArrayList<DpProductTypeMasterDto>();
	private String productTypeId="";
	private String parentProductId ="";
	private String itmeCodeAv;
//	 Add by harbans on Reservation Obserbation 30th June
	private List<DpProductTypeMasterDto>  reverseProductList  = new ArrayList<DpProductTypeMasterDto>();
	
//Added by Shilpa Khanna to add Product Category
	private List<DpProductCategoryDto>  dcProductCategoryList  = new ArrayList<DpProductCategoryDto>();
	private String productCategory ="";
	
	
//aman
	private String productStatus =""; 
	
	
	
	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public double getActivationDouble() {
		return activationDouble;
	}

	public void setActivationDouble(double activationDouble) {
		this.activationDouble = activationDouble;
	}

	public int getPackTypeInt() {
		return packTypeInt;
	}

	public void setPackTypeInt(int packTypeInt) {
		this.packTypeInt = packTypeInt;
	}

	//Added by rajiv jha start
	private double additionalvat=0;
	private String oracleitmecode="";
    // rajiv jha end


	public void reset(){
//		businessCategory = "";
		modelcode=null;
        modelname=null;
        companyname=null;
        productdesc=null;
		productname=null;
		productname1 = "";
		cardgroup=null;
	    packtype=null;
	    mrp=0.0;
	    simcardcost=0.0;
        talktime=0.0;
        activation=0.0;
        processingfee=0.0;
        salestax=0.0;
        servicetax=0.0;
        cesstax=0.0;
        vat=0.0;
        turnovertax=0.0;
        sh_educess=0.0;
        goldennumbercharge=0.0;
        discount=0.0;
        distprice=0.0;
        costprice=0.0;
        octoriprice=0.0;
//        effectivedate=null;
	    version="1.0";	
	    warranty=0;
	    selectedSub="0";
	    unit="0";
	    retPrice=0.0;
	    retDiscount = 0.0;
        stocktype="0";
        freeservice = "";
        unit1="0";
        surcharge=0;
    	freight=0;
    	insurance=0;
    	tradediscount=0;
    	cashdiscount=0;
    	productType="";
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




	public ArrayList getArrCIList() {
		return arrCIList;
	}




	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
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




	public String getCategorycode() {
		return categorycode;
	}




	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
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




	public String getModelcode() {
		return modelcode;
	}




	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}




	public String getModelname() {
		return modelname;
	}




	public void setModelname(String modelname) {
		this.modelname = modelname;
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




	public String getProductid() {
		return productid;
	}




	public void setProductid(String productid) {
		this.productid = productid;
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




	public int getCircleid() {
		return circleid;
	}




	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}




	public String getProductname1() {
		return productname1;
	}




	public void setProductname1(String productname1) {
		this.productname1 = productname1;
	}




	public String getProductname2() {
		return Productname2;
	}




	public void setProductname2(String productname2) {
		Productname2 = productname2;
	}




	public String getCircleCode() {
		return circleCode;
	}




	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}




	public int getProdStatus() {
		return prodStatus;
	}




	public void setProdStatus(int prodStatus) {
		this.prodStatus = prodStatus;
	}




	public double getDistMargin() {
		return distMargin;
	}




	public void setDistMargin(double distMargin) {
		this.distMargin = distMargin;
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




	public String getShowCircle() {
		return showCircle;
	}




	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
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




	private ArrayList arrCGList=null;

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




	public String getProductType() {
		return productType;
	}




	public void setProductType(String productType) {
		this.productType = productType;
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





	public String getParent_product_ext_id() {
		return parent_product_ext_id;
	}




	public void setParent_product_ext_id(String parent_product_ext_id) {
		this.parent_product_ext_id = parent_product_ext_id;
	}

	public String getItmeCodeAv() {
		return itmeCodeAv;
	}

	public void setItmeCodeAv(String itmeCodeAv) {
		this.itmeCodeAv = itmeCodeAv;
	}




	

}
