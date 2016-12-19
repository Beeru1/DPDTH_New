<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ibm.virtualization.recharge.dto.DPEditProductDTO"%>

<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script language="javascript" src="scripts/calendar1.js">
</script>
<script language="javascript" src="scripts/Utilities.js">
</script>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="javascript" type="text/javascript"> 


// trim the spaces
		function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}
function hasSpecial(objInput,iChars)
 {
		
		for (var i = 0; i < objInput.value.length; i++)
		{
	    	if (iChars.indexOf( objInput.value.charAt(i)) != -1)
			{
				//alert(objInput.value.charAt(i));
				//alert ("Your username has special characters. \nThese are not allowed.\n Please remove them and try again.");
				return (true);
			}
		}
	return (false);
 }
function checkProductExistDP()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null) 
		{
			return;
		}
		
		// Get Conditions.
		optionValues = xmldoc.getElementsByTagName("option");
		var bolReturn = document.getElementById("message");
		return bolReturn;
	}
}
function Show()
{
		var checkProductTypeId = "<bean:write	name='DPViewProductFormBean' property='productTypeId' />";
		var bussinessCategory = "<bean:write	name='DPViewProductFormBean' property='businessCategory' />";
		
		if(bussinessCategory == "CPE") // Only for CPE
		{
			if(checkProductTypeId == '1' )
		    {
		    	document.getElementById("ParentProduct").style.display="none";
		    	document.getElementById("ParentProduct1").style.display="none";
			}
			else
			{
			   document.getElementById("ParentProduct").style.display="inline";
		    	document.getElementById("ParentProduct1").style.display="inline";
			}
		}
	var tbl = document.getElementById("categorycode").value;
 	
	if(tbl=='1') 
	{
	//rajiv jha start
	//document.getElementById("oracleitmecode").value="0";
	//document.getElementById("additionalvat").value="0";
	document.getElementById("oraclItemCode").style.display="inline";
	//rajiv jha end
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="block";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("erheader").style.display="none";
		document.getElementById("updation").style.display="block";
		document.getElementById("avheader").style.display="none";
   }
   else if(tbl== '2') 
   {
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="none";
		document.getElementById("rcheader").style.display="block";
		document.getElementById("erheader").style.display="none";
		document.getElementById("updation").style.display="block";
		document.getElementById("avheader").style.display="none";
		document.getElementById("oraclItemCode").style.display="none";
   }
   else if(tbl=='3') 
   {
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="block";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("erheader").style.display="inline";
		document.getElementById("updation").style.display="block";
		document.getElementById("avheader").style.display="none";
		document.getElementById("oraclItemCode").style.display="none";
   }
   else if(tbl=='4') 
   {
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="none";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("erheader").style.display="none";
		document.getElementById("avheader").style.display="block";
		document.getElementById("updation").style.display="block";
		document.getElementById("oraclItemCode").style.display="none";
   }
   else
   { 
		document.getElementById("sukservice").style.display="none";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("updation").style.display="none";
		document.getElementById("avheader").style.display="none";
		document.getElementById("erheader").style.display="none";
		document.getElementById("oraclItemCode").style.display="none";
 	}
 	
 	
 }
function doSubmit()
{
document.DPViewProductFormBean.action="DPEditProductAction.do?methodName=Updatedata";
document.DPViewProductFormBean.submit();
}

function validate(){


var productStatus=document.getElementById("productStatus").value

	//Added by Shilpa Khanna
	
	if(document.getElementById("productCategory").value=="-1")
	{
			alert('Please Select Parent Product');
			document.getElementById("productCategory").focus();
			return false; 			
	}
	
	//rajiv jha added start
if(document.getElementById("oraclItemCode").style.display=='inline') //CPE
	{
       if(hasSpecial(document.getElementById("oracleitmecode"),'!@#$%^*()+=[]\\\';,./{}|\":<>?~&_')) {  
			alert('Speical Character not allowed !!');
			document.getElementById("oracleitmecode").focus();
			return false;
	 	 }
	 
	 if(document.getElementById("oracleitmecode").value=="" || document.getElementById("additionalvat").value=="")
	 {
	 alert ("Please Enter All Mandatory Fields");
	 return false;
	 }
	 if(!isNumeric(document.getElementById("additionalvat").value))
		{
			alert("Additional Vat must be numeric");
			document.getElementById("additionalvat").focus();
			return false;
		}
	 }
	//rajiv jha end
	 
		if (document.getElementById("businessCategory").value == "CPE" || document.getElementById("businessCategory").value == "Paper Recharge" || document.getElementById("businessCategory").value == "Easy Recharge"|| document.getElementById("businessCategory").value == "Activation Voucher(AV)") 
		{
			if(isNull('document.forms[0]','productname1')|| document.getElementById("productname1").value==""){
				alert('<bean:message bundle="error" key="error.product.productname" />');
				document.getElementById("productname1").focus();
				return false; 
			}
			
			// add by harbans 
			if(document.getElementById("productTypeId").value=="999" && document.getElementById("businessCategory").value == "CPE" ){
					alert('<bean:message bundle="error" key="error.product.productType" />');
					document.getElementById("productTypeId").focus();
					return false; 			
			}
			else if(document.getElementById("productTypeId").value=="0")
			{
				if(document.getElementById("parentProductId").value=="999")
				{
					alert("Select a Product category");
					document.getElementById("parentProductId").focus();
					return false;
				}
			}
			
			if(document.getElementById("cardgroup").value=="" && document.getElementById("businessCategory").value == "CPE" ){
				alert('<bean:message bundle="error" key="error.product.cardgroup" />');
				document.getElementById("cardgroup").focus();
				return false; 
			}
			/*	
			if(document.getElementById("packtype").value=="0"){
				alert('<bean:message bundle="error" key="error.product.packtype" />');
				document.getElementById("packtype").focus();
				return false; 
			}
			if(isNull('document.forms[0]','mrp')|| document.getElementById("mrp").value=="" || document.getElementById("mrp").value=="0.0"){
				alert('<bean:message bundle="error" key="error.product.mrp" />');
				document.getElementById("simcardcost").focus();
				return false; 
			}*/
			if (document.forms[0].mrp.value != "")
			{
				if(isLastCharAlphabet(document.forms[0].mrp.value)==true){
						alert('<bean:message bundle="error" key="error.product.mrp1" />');
						document.getElementById("mrp").focus();
						return false;
					}
			
					if(!(isNumeric(document.forms[0].mrp.value))){
			alert('<bean:message bundle="error" key="error.product.mrp2" />');
			document.getElementById("warranty").focus();
			return false;
		}
		}
		if(isNull('document.forms[0]','effectivedate')|| document.getElementById("effectivedate").value==""){
			alert('<bean:message bundle="error" key="error.product.effectivedate" />');
			document.getElementById("effectivedate").focus();
			return false; 
		}
		if(document.getElementById("unit1").value=="0" || document.getElementById("unit1").value==""){
			alert("Select Unit for the Product.");
			document.getElementById("unit1").focus();
			return false;	
		}
	}
	
	
	if(document.getElementById("itemCodeAvLable").style.display=='inline') //AV
	{
       
        if(document.getElementById("itmeCodeAv").value=="" || document.getElementById("itmeCodeAv").value==" ")
		 {
		 alert ("Please Enter Item Code");
		 document.getElementById("itmeCodeAv").focus();
		 return false;
		 }
       if(hasSpecial(document.getElementById("itmeCodeAv"),'!@#$%^*()+=[]\\\';,./{}|\":<>?~&_')) {  
			alert('Speical Character not allowed in item code !!');
			document.getElementById("itmeCodeAv").focus();
			return false;
	 	 }
	
	 }
	doSubmit();
}
// check  parent product for one circle should be on
function checkProductExist() 
{
	var bolReturn = "false";
    var businessCategory = document.getElementById("businessCategory").value;
    var circleid = document.getElementById("circleid").value;
    var productCategory = document.getElementById("productCategory").value;
    
	if(productCategory!='-1' && businessCategory !='1' && circleid !='')
	{
	
		var url = "initDPCreateProductAction.do?methodName=getCheckedParentProduct&productCategory="+productCategory+"&circleid="+circleid;
		
		if(window.XmlHttpRequest) 
		{
			req = new XmlHttpRequest();
		}else if(window.ActiveXObject) 
		{
			req=new ActiveXObject("Microsoft.XMLHTTP");
		}
		if(req==null) 
		{
			alert("Browser Doesnt Support AJAX");
			return;
		}
		
		req.onreadystatechange = checkProductExistDP;
		req.open("POST",url,false);
		req.send();
	}
	
}

function checkProductExistDP()
{
	
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null) 
		{
			return;
		}
		
		// Get Conditions.
		
		optionValues = xmldoc.getElementsByTagName("option");
		//alert(optionValues);
		//alert(optionValues[0].getAttribute("message"));
		var bolReturn = optionValues[0].getAttribute("message");
		if(bolReturn == "false")
		{
			alert("Product already exists against the selected Parent Product");
			document.getElementById("productCategory").value=document.getElementById("prodValue").value;
		}
	}
}

// check to see if input is alphanumeric
function isAlphaNumeric(val)
{
	if (val.match(/^[a-zA-Z0-9]+$/))
	{
		return true;
	}
	else
	{
		return false;
	} 
}


function calculateMRP()
{
	
	var mrp=0;
	var distP=0;
	var basicP=0;
	var buscategory = document.getElementById('businessCategory').value;
	
	if(buscategory=='CPE') //CPE
	{
		
		if(!isNumeric(document.getElementById("simcardcost").value))
		{
			alert("Basic price must be numeric");
			document.getElementById("simcardcost").value=0.0;
			document.getElementById("simcardcost").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("salestax").value))
		{
			alert("salestax must be numeric");
			document.getElementById("salestax").value=0.0;
			document.getElementById("salestax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("vat").value))
		{
			alert("vat must be numeric");
			document.getElementById("vat").value=0.0;
			document.getElementById("vat").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("turnovertax").value))
		{
			alert("turnovertax must be numeric");
			document.getElementById("turnovertax").value=0.0;
			document.getElementById("turnovertax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("surcharge").value))
		{
			alert("surcharge must be numeric");
			document.getElementById("surcharge").value=0.0;
			document.getElementById("surcharge").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("freight").value))
		{
			alert("freight must be numeric");
			document.getElementById("freight").value=0.0;
			document.getElementById("freight").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("insurance").value))
		{
			alert("insurance must be numeric");
			document.getElementById("insurance").value=0.0;
			document.getElementById("insurance").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("tradediscount").value))
		{
			alert("trade discount must be numeric");
			document.getElementById("tradediscount").value=0.0;
			document.getElementById("tradediscount").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("cashdiscount").value))
		{
			alert("cash discount must be numeric");
			document.getElementById("cashdiscount").value=0.0;
			document.getElementById("cashdiscount").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("octoriprice").value))
		{
			alert("octori price must be numeric");
			document.getElementById("octoriprice").value=0.0;
			document.getElementById("octoriprice").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("discount").value))
		{
			alert("Distributer discount must be numeric");
			document.getElementById("discount").value=0.0;
			document.getElementById("discount").focus();
			return false;
		}else if(!isNumeric(document.getElementById("retDiscount").value))
		{
			alert("Retailer Discount must be numeric");
			document.getElementById("retDiscount").value=0.0;
			document.getElementById("retDiscount").focus();
			return false;
		}
	
				//rajiv jha added for MRP start
	
		BasicValue=parseFloat(document.getElementById("simcardcost").value);
		SalesTax=parseFloat(document.getElementById("salestax").value);
		VATtax=parseFloat(document.getElementById("vat").value);
		AddTax=parseFloat(document.getElementById("additionalvat").value);	
		OctroiPrice=parseFloat(document.getElementById("octoriprice").value);
		Surcharge=parseFloat(document.getElementById("surcharge").value);
		Freight=parseFloat(document.getElementById("freight").value);
		Insurance=parseFloat(document.getElementById("insurance").value);
		TradeDiscount=parseFloat(document.getElementById("tradediscount").value);
		CashDiscount=parseFloat(document.getElementById("cashdiscount").value);	
		DistMargin=parseFloat(document.getElementById("discount").value);
				
		SalesTax=((BasicValue-TradeDiscount-CashDiscount-DistMargin)*SalesTax)/100;
		//alert('Sale TAx :' + SalesTax);
	    VATtax= ((BasicValue-TradeDiscount-CashDiscount-DistMargin)*VATtax)/100;
	   // alert('VATtax :' + VATtax);    
	    AddTax=((BasicValue-TradeDiscount-CashDiscount-DistMargin)*AddTax)/100;
	   // alert('AddTax :' + AddTax);
	    mrp= (BasicValue+VATtax+AddTax+SalesTax+OctroiPrice+Surcharge+Freight+Insurance);
	   // alert('MRP : ' + mrp);
	    distP=(BasicValue+VATtax+AddTax+SalesTax+OctroiPrice+Surcharge+Freight+Insurance) -(TradeDiscount+CashDiscount+DistMargin);
	    //alert('distP :' + distP);
	    
    
    
	
	
		// rajiv jha added for MRP end
		/*	
		basicP=parseFloat(document.getElementById("simcardcost").value);
		mrp = basicP+ ((basicP/100)*parseFloat(document.getElementById("vat").value))+((basicP/100)*parseFloat(document.getElementById("salestax").value));
		mrp = mrp+parseFloat(document.getElementById("turnovertax").value)+parseFloat(document.getElementById("octoriprice").value)+parseFloat(document.getElementById("surcharge").value);
		mrp = mrp+parseFloat(document.getElementById("insurance").value)+parseFloat(document.getElementById("freight").value);
		mrp = mrp-parseFloat(document.getElementById("tradediscount").value)-parseFloat(document.getElementById("cashdiscount").value);
		
		distP = mrp-parseFloat(document.getElementById("discount").value);
		//distP = distP-parseFloat(document.getElementById("discount").value); 
		*/
	}
	else if(buscategory=='Paper Recharge') 
	{
		if(!isNumeric(document.getElementById("talktime").value))
		{
			alert("Recharge Value must be numeric");
			document.getElementById("talktime").value=0.0;
			document.getElementById("talktime").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("activation").value))
		{
			alert("activation must be numeric");
			document.getElementById("activation").value=0.0;
			document.getElementById("activation").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("processingfee").value))
		{
			alert("processingfee must be numeric");
			document.getElementById("processingfee").value=0.0;
			document.getElementById("processingfee").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("servicetax").value))
		{
			alert("servicetax must be numeric");
			document.getElementById("servicetax").value=0.0;
			document.getElementById("servicetax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("cesstax").value))
		{
			alert("cesstax must be numeric");
			document.getElementById("cesstax").value=0.0;
			document.getElementById("cesstax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("sh_educess").value))
		{
			alert("sh_educess must be numeric");
			document.getElementById("sh_educess").value=0.0;
			document.getElementById("sh_educess").focus();
			return false;
		}
		
		BasicValue=parseFloat(document.getElementById("simcardcost").value);
	SalesTax=parseFloat(document.getElementById("salestax").value);
	VATtax=parseFloat(document.getElementById("vat").value);
	AddTax=parseFloat(document.getElementById("additionalvat").value);	
	OctroiPrice=parseFloat(document.getElementById("octoriprice").value);
	Surcharge=parseFloat(document.getElementById("surcharge").value);
	Freight=parseFloat(document.getElementById("freight").value);
	Insurance=parseFloat(document.getElementById("insurance").value);
	TradeDiscount=parseFloat(document.getElementById("tradediscount").value);
	CashDiscount=parseFloat(document.getElementById("cashdiscount").value);	
	DistMargin=parseFloat(document.getElementById("discount").value);
				
		
	BasicValue=parseFloat(document.getElementById("simcardcost").value);
	SalesTax=parseFloat(document.getElementById("salestax").value);
	VATtax=parseFloat(document.getElementById("vat").value);
	AddTax=parseFloat(document.getElementById("additionalvat").value);
	TradeDiscount=parseFloat(document.getElementById("tradediscount").value);
	Recharge=parseFloat(document.getElementById("talktime").value);
	ServiceTax=parseFloat(document.getElementById("servicetax").value);
	Activation=parseFloat(document.getElementById("activation").value);
	ProcessingFee=parseFloat(document.getElementById("processingfee").value);
	Cess=parseFloat(document.getElementById("cesstax").value);
	TurnOverTax=parseFloat(document.getElementById("turnovertax").value);
	SH_Educess=parseFloat(document.getElementById("sh_educess").value);	
	Golden_Number_Charge=parseFloat(document.getElementById("goldennumbercharge").value);
	DistMargin=parseFloat(document.getElementById("discount").value);
		
	 SalesTax=((BasicValue-TradeDiscount-CashDiscount-DistMargin)*SalesTax)/100  
     mrp=(BasicValue + Recharge + Activation + ProcessingFee + SalesTax + ServiceTax + Cess + TurnOverTax + SH_Educess + Golden_Number_Charge) 
     distP=(mrp- DistMargin) 
	
		/*
		mrp = parseFloat(document.getElementById("talktime").value)+parseFloat(document.getElementById("activation").value)+parseFloat(document.getElementById("processingfee").value);
		mrp = mrp+parseFloat(document.getElementById("servicetax").value)+parseFloat(document.getElementById("cesstax").value);
		mrp = mrp+parseFloat(document.getElementById("sh_educess").value);
		distP = mrp-parseFloat(document.getElementById("discount").value);
		*/
	}
	else if(buscategory=='Easy Recharge')
	{
		
		if(!isNumeric(document.getElementById("talktime").value))
		{
			alert("Recharge Value must be numeric");
			document.getElementById("talktime").value=0.0;
			document.getElementById("talktime").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("activation").value))
		{
			alert("activation must be numeric");
			document.getElementById("activation").value=0.0;
			document.getElementById("activation").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("processingfee").value))
		{
			alert("processingfee must be numeric");
			document.getElementById("processingfee").value=0.0;
			document.getElementById("processingfee").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("servicetax").value))
		{
			alert("servicetax must be numeric");
			document.getElementById("servicetax").value=0.0;
			document.getElementById("servicetax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("cesstax").value))
		{
			alert("cesstax must be numeric");
			document.getElementById("cesstax").value=0.0;
			document.getElementById("cesstax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("sh_educess").value))
		{
			alert("sh_educess must be numeric");
			document.getElementById("sh_educess").value=0.0;
			document.getElementById("sh_educess").focus();
			return false;
		}
		
		BasicValue=parseFloat(document.getElementById("simcardcost").value);
		SalesTax=parseFloat(document.getElementById("salestax").value);
		VATtax=parseFloat(document.getElementById("vat").value);
		AddTax=parseFloat(document.getElementById("additionalvat").value);
		
		Recharge=parseFloat(document.getElementById("talktime").value);
		servicetax=parseFloat(document.getElementById("servicetax").value);
		Activation=parseFloat(document.getElementById("activation").value);
		ProcessingFee=parseFloat(document.getElementById("processingfee").value);
		Cess=parseFloat(document.getElementById("cesstax").value);
		TurnOverTax=parseFloat(document.getElementById("turnovertax").value);
		SH_Educess=parseFloat(document.getElementById("sh_educess").value);	
		Golden_Number_Charge=parseFloat(document.getElementById("goldennumbercharge").value);
		DistMargin=parseFloat(document.getElementById("discount").value);
			
		 SalesTax=((BasicValue-TradeDiscount-CashDiscount-DistMargin)*SalesTax)/100  
	     mrp=(BasicValue + Recharge + Activation + ProcessingFee + SalesTax + ServiceTax + Cess + TurnOverTax + SH_Educess + Golden_Number_Charge) 
	     distP=(mrp- DistMargin) 
	
		/*
		mrp = parseFloat(document.getElementById("talktime").value)+parseFloat(document.getElementById("activation").value)+parseFloat(document.getElementById("processingfee").value);
		mrp = mrp+parseFloat(document.getElementById("servicetax").value)+parseFloat(document.getElementById("cesstax").value);
		mrp = mrp+parseFloat(document.getElementById("sh_educess").value);
		distP = mrp-parseFloat(document.getElementById("discount").value);
		*/
	}
	else if(buscategory=='Activation Voucher(AV)') 
	{
		if(!isNumeric(document.getElementById("talktime").value))
		{
			alert("Recharge Value must be numeric");
			document.getElementById("talktime").value=0.0;
			document.getElementById("talktime").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("activation").value))
		{
			alert("activation must be numeric");
			document.getElementById("activation").value=0.0;
			document.getElementById("activation").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("processingfee").value))
		{
			alert("processingfee must be numeric");
			document.getElementById("processingfee").value=0.0;
			document.getElementById("processingfee").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("servicetax").value))
		{
			alert("servicetax must be numeric");
			document.getElementById("servicetax").value=0.0;
			document.getElementById("servicetax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("cesstax").value))
		{
			alert("cesstax must be numeric");
			document.getElementById("cesstax").value=0.0;
			document.getElementById("cesstax").focus();
			return false;
		}
		else if(!isNumeric(document.getElementById("sh_educess").value))
		{
			alert("sh_educess must be numeric");
			document.getElementById("sh_educess").value=0.0;
			document.getElementById("sh_educess").focus();
			return false;
		}
		
		mrp = parseFloat(document.getElementById("talktime").value)+parseFloat(document.getElementById("activation").value)+parseFloat(document.getElementById("processingfee").value);
		mrp = mrp+parseFloat(document.getElementById("servicetax").value)+parseFloat(document.getElementById("cesstax").value);
		mrp = mrp+parseFloat(document.getElementById("sh_educess").value);
		distP = mrp-parseFloat(document.getElementById("discount").value);
	}
	//document.getElementById("mrp").value = mrp;
	//document.getElementById("distprice").value = distP;
	var retDis = parseFloat(document.getElementById("retDiscount").value);
	var retP;
	retP = mrp - retDis;
//Added by neetika
	mrp=  mrp.toFixed(2);
	distP=distP.toFixed(2);
	retP=retP.toFixed(2);
	document.getElementById("mrp").value = mrp;
	document.getElementById("distprice").value = distP;
	document.getElementById("retPrice").value = retP
}

function retailerPrice(){
	var mrp = parseFloat(document.getElementById("mrp").value);
	var retDis = parseFloat(document.getElementById("retDiscount").value);
	var retP;
	retP = mrp - retDis;
//Added by neetika
	retP=retP.toFixed(2);
	document.getElementById("retPrice").value = retP
}
function f1(){
	if (document.getElementById("businessCategory").value == "CPE")
	{
		document.getElementById("talktime").readOnly=true;
		document.getElementById("talktime").value="0";
		document.getElementById("activation").readOnly=true;
		document.getElementById("activation").value="0";
		document.getElementById("processingfee").readOnly=true;
		document.getElementById("processingfee").value="0";
		document.getElementById("servicetax").readOnly=true;
		document.getElementById("servicetax").value="0";
		document.getElementById("cesstax").readOnly=true;
		document.getElementById("cesstax").value="0";
		document.getElementById("sh_educess").readOnly=true;
		document.getElementById("sh_educess").value="0";
		document.getElementById("costprice").readOnly=true;
		document.getElementById("costprice").value="0";
		
		document.getElementById("simcardcost").readOnly=false;
		document.getElementById("salestax").readOnly=false;
		document.getElementById("vat").readOnly=false;
		document.getElementById("turnovertax").readOnly=false;
		document.getElementById("octoriprice").readOnly=false;
		document.getElementById("surcharge").readOnly=false;
		document.getElementById("freight").readOnly=false;
		document.getElementById("insurance").readOnly=false;
		document.getElementById("tradediscount").readOnly=false;
		document.getElementById("cashdiscount").readOnly=false;
		
		//document.getElementById("productTypeId").value="0";
		document.getElementById("productTypeId").style.display="block";
		document.getElementById("productTypeLabel").style.display="block";
		
		document.getElementById("itemCodeAvLable").style.display="none";
		document.getElementById("itemCodeAvText").style.display="none";
		
	}
	else if ((document.getElementById("businessCategory").value == "Paper Recharge")||(document.getElementById("businessCategory").value == "Activation Voucher(AV)"))
	{
		document.getElementById("simcardcost").readOnly=true;
		document.getElementById("simcardcost").value="0";
		//document.getElementById("processingfee").readOnly=true;
		//document.getElementById("processingfee").value="0";
		document.getElementById("salestax").readOnly=true;
		document.getElementById("salestax").value="0";
		document.getElementById("vat").readOnly=true;
		document.getElementById("vat").value="0";
		document.getElementById("turnovertax").readOnly=true;
		document.getElementById("turnovertax").value="0";
		document.getElementById("costprice").readOnly=true;
		document.getElementById("costprice").value="0";
		document.getElementById("octoriprice").readOnly=true;
		document.getElementById("octoriprice").value="0";
		document.getElementById("surcharge").readOnly=true;
		document.getElementById("surcharge").value="0";
		document.getElementById("freight").readOnly=true;
		document.getElementById("freight").value="0";
		document.getElementById("insurance").readOnly=true;
		document.getElementById("insurance").value="0";
		document.getElementById("tradediscount").readOnly=true;
		document.getElementById("tradediscount").value="0";
		document.getElementById("cashdiscount").readOnly=true;
		document.getElementById("cashdiscount").value="0";
	 	
	 	document.getElementById("talktime").readOnly=false;
		document.getElementById("activation").readOnly=false;
		document.getElementById("servicetax").readOnly=false;
		document.getElementById("cesstax").readOnly=false;
		document.getElementById("sh_educess").readOnly=false;
		
		document.getElementById("processingfee").readOnly=false;
		
		document.getElementById("productTypeId").value="2";
		document.getElementById("productTypeId").style.display="none";
		document.getElementById("productTypeLabel").style.display="none";
		
		document.getElementById("itemCodeAvLable").style.display="inline";
		document.getElementById("itemCodeAvText").style.display="inline";
		
	}
	else if (document.getElementById("businessCategory").value == "Easy Recharge")
	{
		document.getElementById("simcardcost").readOnly=true;
		document.getElementById("simcardcost").value="0";
		//document.getElementById("activation").readOnly=true;
		//document.getElementById("activation").value="0";
		//document.getElementById("processingfee").readOnly=true;
		//document.getElementById("processingfee").value="0";
		document.getElementById("salestax").readOnly=true;
		document.getElementById("salestax").value="0";
		document.getElementById("vat").readOnly=true;
		document.getElementById("vat").value="0";
		document.getElementById("turnovertax").readOnly=true;
		document.getElementById("turnovertax").value="0";
		document.getElementById("costprice").readOnly=true;
		document.getElementById("costprice").value="0";
		document.getElementById("octoriprice").readOnly=true;
		document.getElementById("octoriprice").value="0";
		document.getElementById("surcharge").readOnly=true;
		document.getElementById("surcharge").value="0";
		document.getElementById("freight").readOnly=true;
		document.getElementById("freight").value="0";
		document.getElementById("insurance").readOnly=true;
		document.getElementById("insurance").value="0";
		document.getElementById("tradediscount").readOnly=true;
		document.getElementById("tradediscount").value="0";
		document.getElementById("cashdiscount").readOnly=true;
		document.getElementById("cashdiscount").value="0";
		
		document.getElementById("talktime").readOnly=false;
		document.getElementById("servicetax").readOnly=false;
		document.getElementById("cesstax").readOnly=false;
		document.getElementById("sh_educess").readOnly=false;
		
		document.getElementById("processingfee").readOnly=false;
		document.getElementById("activation").readOnly=false;
		document.getElementById("productTypeId").value="999";
		document.getElementById("productTypeId").style.display="none";
		document.getElementById("productTypeLabel").style.display="none";
		document.getElementById("itemCodeAvLable").style.display="none";
		document.getElementById("itemCodeAvText").style.display="none";
		
}
}

//Add by harbans on Reservation Obserbation 30th June
	function changeParentProductList()
	{
		
		var productTypeId = document.getElementById("productTypeId").value;
        
        // On SWAP type product show list of commercial product
	    if(productTypeId == 1)
	    {
	    	document.getElementById("ParentProduct").style.display="none";
	    	document.getElementById("ParentProduct1").style.display="none";
		}
		else
		{
		   document.getElementById("ParentProduct").style.display="inline";
	    	document.getElementById("ParentProduct1").style.display="inline";
		}
	}

function focusTab(){
document.getElementById("businessCategory").focus();
}
</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="f1()">
	<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
		height="100%" valign="top">
<html:form action="DPEditProductAction.do?methodName=Updatedata">
	<html:hidden property="prodId"/>
	<html:hidden property="prodValue" styleId="prodValue"/>
		<TR>
			<TD colspan="2" valign="top" width="100%"><jsp:include
				page="../common/dpheader.jsp" flush="" /></TD>
		</TR>
		<TR valign="top">
			<TD align="left" bgcolor="#FFFFFF"
				background="<%=request.getContextPath()%>/images/bg_main.gif"
				valign="top" height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
			<td>
			<table>
				<tr>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/editProduct.gif"
						width="505" height="22" alt=""></TD>
				</tr>
			</table>
			<table>

				<TR>
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.businesscategory" /></b> <html:hidden
						property="categorycode" name="DPViewProductFormBean">
						<bean:write name="DPViewProductFormBean" property="categorycode" />
					</html:hidden> <html:text property="businessCategory"
						name="DPViewProductFormBean" readonly="true">
						<bean:write name="DPViewProductFormBean"
							property="businessCategory" />
					</html:text></TD>
				</TR>
				<TR>
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /></b> <html:text
						property="circlename" name="DPViewProductFormBean" readonly="true">
						<bean:write name="DPViewProductFormBean" property="circlename" />
					</html:text></TD>
				</TR>
				<TR>
					<TD width="754"><FONT color="RED"><STRONG><html:errors
						bundle="view" property="message.product" /><html:errors
						bundle="error" property="errors.product" /><html:errors /></STRONG></FONT></TD>
					<html:hidden property="circleId"/>
				</TR>
				<tr>
					<td width="754">
					

					<TABLE id="sukservice" style="display:none">
						<TR id="sukheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b>CPE SERVICES </b><br></TD>
						</TR>
						<TR id="erheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b>EASY RECHARGE</b><br></TD>
						</TR>
						<TR id="rcheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b>PAPER RECHARGE SERVICES</b><br></TD>
						</TR>
						<TR id="avheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b>Activation Voucher</b><br></TD>
						</TR>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.productname" /><font color="red">*</font></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="productname1" maxlength="100" readonly="true" onkeypress="alphaNumWithSpace();">
								<bean:write name="DPViewProductFormBean" property="productname1"/>
							</html:text></TD>
						
						
						
						<!-- Added by Shilpa -->
						<TD class="text pLeft15" id="">&nbsp;<span id="productCategLabel" >&nbsp;Select Parent Product<font color="red">*</font></span>
						</TD>
						<TD>
							<html:select property="productCategory"  styleId="productCategory" onchange="checkProductExist()"  style="width:150px" style="height:20px"> 
							<html:option value="-1">
								Select Parent Product
							</html:option>
							<logic:notEmpty  property="dcProductCategoryList" name="DPViewProductFormBean" >
							<html:options labelProperty="productCategory" property="productCategory" collection="dcProductCategoryList" />
							</logic:notEmpty>
						</html:select>
						</TD>
							</TR>			<TR>
						
						
							<TD class="text pLeft15">&nbsp;<span id="productTypeLabel" >&nbsp;<bean:message bundle="view"
								key="productcreate.ProductType" /><font color="red">*</font></span>
						</TD>
						<TD>
							<html:select property="productTypeId"   name="DPViewProductFormBean" onchange="changeParentProductList();" style="width:150px" style="height:20px"> 
							<html:option value="999">
								<bean:message bundle="view" key="product.selectProductType" />
							</html:option>
							<logic:notEmpty  property="dcProductTypeList" name="DPViewProductFormBean" >
							<html:options labelProperty="productType" property="productId" collection="dcProductTypeList" />
							</logic:notEmpty>
						</html:select>
						</TD>
						
						<TD class="text pLeft15" id="ParentProduct" style="display:none">Product Category<font color="red">*</font>
						</TD>
						<TD class="text pLeft15" id="ParentProduct1" style="display:none">
							<html:select property="parentProductId"  styleId="parentProductId" style="width:150px" style="height:20px"> 
							<html:option value="999">
								<bean:message bundle="view" key="product.selectProductType" />
							</html:option>
							<logic:notEmpty  property="reverseProductList" name="DPViewProductFormBean" >
							<html:options labelProperty="productName" property="productId" collection="reverseProductList" />
							</logic:notEmpty>
						</html:select>
						</TD>
						</TR>
						
						<TR>
						   <TD class="text pLeft15"><bean:message bundle="view" key="productcreate.cardgroup" /><font color="red">*</font></TD>
							<TD>
							<html:select property="cardgroup" name="DPViewProductFormBean" style="width:150px" style="height:20px"> 
							<html:option value="">
								<bean:message bundle="view" key="product.selectcg" />
							</html:option>
							<logic:notEmpty name="DPViewProductFormBean" property="arrCGList">
								<html:optionsCollection name="DPViewProductFormBean" 	property="arrCGList" />
							</logic:notEmpty>
							</html:select>
						    </TD>
							
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.packtype" /><font color="red"></font></TD>
							
							<TD>
							<html:text name="DPViewProductFormBean"
								property="packtype" maxlength="2" onkeypress="alphaNumWithoutSpace()">
								<bean:write name="DPViewProductFormBean" property="packtype" />
							</html:text>
							
						
							</TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.mrp" /></TD>
							<TD><html:text name="DPViewProductFormBean" property="mrp"
								maxlength="10" readonly="true"/></TD>
							
							</TR>

						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.simcardcost" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="simcardcost" maxlength="10" onblur="calculateMRP();"/></TD>
							
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.activation" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="activation" maxlength="50" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.talktime" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="talktime" maxlength="10" onblur="calculateMRP();" onfocus="f2('8')" /></TD>
						</TR>

						<TR>
						   <TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.salestax" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="salestax" maxlength="10" onblur="calculateMRP();"/></TD>
						
							
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.cesstax" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="cesstax" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.vat" /></TD>
							<TD><html:text name="DPViewProductFormBean" property="vat"
								maxlength="10" onblur="calculateMRP();"/></TD>
						</TR>
						<TR>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.servicetax" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="servicetax" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.turnovertax" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="turnovertax" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.sheducess" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="sh_educess" maxlength="10" onblur="calculateMRP();"/></TD>
								</tr>
						
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.goldennumbercharge" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="goldennumbercharge" readonly="true" value="0.0" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.surcharge" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="surcharge" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.freight" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="freight" maxlength="10" onblur="calculateMRP();"/></TD>

							</tr>
						
						<tr>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.insurance" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="insurance" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.tradediscount" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="tradediscount" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.cashdiscount" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="cashdiscount" maxlength="10" onblur="calculateMRP();"/></TD>
							</TR>
						<TR>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.costprice" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="costprice" maxlength="10" /></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.octoriprice" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="octoriprice" maxlength="10" /></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.discount" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="discount" maxlength="10" onblur="calculateMRP();"/></TD>
						
							
						</TR>
						<TR>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.distprice" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="distprice" maxlength="10" readonly="true"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.retDiscount" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="retDiscount" maxlength="10" onblur="retailerPrice();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.retPrice" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="retPrice" maxlength="10" readonly="true"/></TD>
								</TR>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.freeservice" /></TD>
							<TD>
							<html:checkbox property="freeservice"  value="Y"/>
							</TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.processingfee" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="processingfee" maxlength="10" onblur="calculateMRP();"/></TD>
								
							<TD id="itemCodeAvLable" class="text pLeft15" style="display:none">Item Code
							<font color="red">*</font></TD>
							<TD id="itemCodeAvText" style="display:none"><html:text name="DPViewProductFormBean"
								property="itmeCodeAv" maxlength="40"/></TD>	
						</TR>
						
						<!-- rajiv jha added start -->
						<TR id="oraclItemCode" style="display:none">
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.additionalvat" /><font color="red">*</font></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="additionalvat" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.oracleitemcode" /><font color="red">*</font></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="oracleitmecode" maxlength="40"/></TD>														
							</TR>
						<!-- rajiv jha added end -->
						<tr>
						<TD class="text pLeft15"><bean:message bundle="view" key="product.unit"/>
						<font color="red">*</font>
							</TD>
							<td>
							<html:select
								property="unit1" name="DPViewProductFormBean">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty name="DPViewProductFormBean"
									property="unitList">
									<bean:define name="DPViewProductFormBean" property="unitList" id="unit"/>	
									<html:options collection="unit" name="DPCreateProductFormBean"
										property="unit" labelProperty="unitName" />
								</logic:notEmpty>
							</html:select>
							</td>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.effectivedate" /><font color="red">*</font></TD>
							<TD><html:text name="DPViewProductFormBean" readonly="true"
								property="effectivedate" size="12"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.version" /></TD>
							<TD><html:text name="DPViewProductFormBean"
								property="version" maxlength="50" readonly="true"/></TD>
						</tr>
						
							<!-- aman -->
						
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view" key="product.status" /><font color="red">*</font>
							</TD>
							<td>
								<html:select
								property="productStatus" name="DPViewProductFormBean" styleId="productStatus">
									<html:option value="A"><bean:message bundle="view" key="product.active" /></html:option>
									<html:option value="I"><bean:message bundle="view" key="product.inactive" /></html:option>
								</html:select>
							</td>
						</TR>
						
						<!-- end by aman -->
						
						
					
					</TABLE>
				<TR>
					<td id="updation" colspan="1" align="center" width="754"><html:button
						property="btn" value="UPDATE" onclick="return validate();" /></td>
				</TR>
				
				
				<script>
					document.getElementById("prodValue").value = document.getElementById("productCategory").value;
				</script>
				
			</table>
		</html:form>
	<tr>
		<jsp:include page="../common/footer.jsp" flush="" />
	</tr>
	</TABLE>

<script>
Show();
</script>
</BODY>
</html:html>