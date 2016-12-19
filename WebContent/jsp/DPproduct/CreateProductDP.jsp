<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html> 
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script language="javascript" src="scripts/calendar1.js"></script>
<script language="javascript" src="scripts/Utilities.js"></script>
<script language="javascript" src="scripts/cal2.js"></script>
<script language="javascript" src="scripts/cal_conf2.js"></script>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="javascript" type="text/javascript"> 
var focusvar="0";
function f2(value)
{
	//alert(value);
	focusvar =value;
	//alert(focusvar);
	
}

function f1(e)
{
	//alert(e.keyCode);
	//alert(focusvar);
	//if (e.keyCode == 8 && (focusvar=='0'||focusvar=='1'||focusvar=='2'||focusvar=='6'||focusvar=='17'||focusvar=='26'||focusvar=='28'||focusvar=='30'||focusvar=='31'||focusvar=='32'))
	//{	
		//alert(focusvar);
		//window.history.forward(1);
		//return false;
	
	//}
//return true;
}
	
	
function onCircleChange()
{
	var circleid = document.getElementById("circleid").value;
	//alert(circleid);
	var url= "initDPCreateProductAction.do?methodName=onCircleChange&circleId="+circleid;
	var elementId = "cardgroup" ;
	ajax(url,elementId);
}

	
function ajax(url,elementId)
{
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function(){
		getAjaxValues(elementId);
	}
	req.open("POST",url,false);
	req.send();
}

function getAjaxValues(elementId)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		
		var xmldoc = req.responseXML.documentElement;
		
		if(xmldoc == null)
		{		
			var selectObj = document.getElementById(elementId);
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById(elementId);
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
		for(var i=0; i<optionValues.length; i++)
		{
		   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}
		
		// Add by harbans on Reservation Obserbation 30th June
		optionValues1 = xmldoc.getElementsByTagName("optionrevesre");
		var selectObj1 = document.getElementById("parentProductId");
		selectObj1.options.length = 0;
		selectObj1.options[selectObj1.options.length] = new Option(" -Select Product Parent ", "999");
		for(var i=0; i<optionValues1.length; i++)
		{
		   selectObj1.options[selectObj1.options.length] = new Option(optionValues1[i].getAttribute("text"),optionValues1[i].getAttribute("value"));
		}
	}

}
	
	
	function rmvSpace(e)
	{ 
		if(window.event.keyCode==32){ 
		
		var cardgrp = trimAll(document.getElementById("cardgroup").value);
	   	document.getElementById("cardgroup").value = cardgrp;
	  	}
	}



	function trimAll(sString)
	{
	while (sString.substring(0,1) == ' ')
	{
	sString = sString.substring(1, sString.length);
	}
	while (sString.substring(sString.length-1, sString.length) == ' ')
	{
	sString = sString.substring(0,sString.length-1);
	}
	return sString;
	} 

function getParentProduct() {


	    var businessCategory = document.getElementById("businessCategory").value;
		if(businessCategory!=''){
			var url = "initDPCreateProductAction.do?methodName=getParentProduct&businessCategory="+businessCategory;
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
			req.onreadystatechange = getParentProductChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
}
	
	
function getParentProductChange(){
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("productCategory");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("--Select Parent Product--","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
}	

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
		optionValues = xmldoc.getElementsByTagName("option");
		var bolReturn = optionValues[0].getAttribute("message");
		//alert("niki"+bolReturn);
		if(bolReturn == "false")
		{
			alert("Product already exists against the selected Parent Product");
			document.getElementById("productCategory").value='-1';
		}
	}
}

function Show()
{
focusvar=1;
	document.getElementById("discount").value="0";
	var tbl = document.getElementById('businessCategory').value;

	 document.getElementById("goldennumbercharge").readOnly=true;
	 document.getElementById("goldennumbercharge").value="0";
	 
	if(tbl=='3') // easy recharge
	{
		document.getElementById("simcardcost").readOnly=true;
		document.getElementById("simcardcost").value="0";
		//document.getElementById("activation").readOnly=true;
		document.getElementById("activation").value="0";
		//document.getElementById("processingfee").readOnly=true;
		document.getElementById("processingfee").value="0";
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
		document.getElementById("talktime").value="0";
		document.getElementById("servicetax").readOnly=false;
		document.getElementById("servicetax").value="0";
		document.getElementById("cesstax").readOnly=false;
		document.getElementById("cesstax").value="0";
		document.getElementById("sh_educess").readOnly=false;
		document.getElementById("sh_educess").value="0";
		
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="none";
		document.getElementById("erheader").style.display="inline";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("av").style.display="none";
		document.getElementById("oraclItemCode").style.display="none";
		document.getElementById("submission").style.display="block";
		document.getElementById("mrp").value="0.0";
		document.getElementById("productTypeId").value="999";
		document.getElementById("productTypeId").style.display="none";
		document.getElementById("productTypeLabel").style.display="none";
		document.getElementById("sukservice").style.display="block";
		document.getElementById("ParentProduct").style.display="none";
		document.getElementById("ParentProduct1").style.display="none";
		document.getElementById("itemCodeAvLable").style.display="none";
		document.getElementById("itemCodeAvText").style.display="none";
		
		
		
	} 
	else if(tbl=='1') //CPE
	{
	//rajiv jha start
	document.getElementById("oracleitmecode").value="0";
	document.getElementById("additionalvat").value="0";
	document.getElementById("oraclItemCode").style.display="inline";
	//rajiv jha end
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
		document.getElementById("simcardcost").value="0";
		document.getElementById("salestax").readOnly=false;
		document.getElementById("salestax").value="0";
		document.getElementById("vat").readOnly=false;
		document.getElementById("vat").value="0";
		document.getElementById("turnovertax").readOnly=false;
		document.getElementById("turnovertax").value="0";
		document.getElementById("octoriprice").readOnly=false;
		document.getElementById("octoriprice").value="0";
		document.getElementById("surcharge").readOnly=false;
		document.getElementById("surcharge").value="0";
		document.getElementById("freight").readOnly=false;
		document.getElementById("freight").value="0";
		document.getElementById("insurance").readOnly=false;
		document.getElementById("insurance").value="0";
		document.getElementById("tradediscount").readOnly=false;
		document.getElementById("tradediscount").value="0";
		document.getElementById("cashdiscount").readOnly=false;
		document.getElementById("cashdiscount").value="0";
		
		
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="block";
		document.getElementById("erheader").style.display="none";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("av").style.display="none";
		document.getElementById("submission").style.display="block";
		document.getElementById("mrp").value="0.0";	
		
		document.getElementById("productTypeId").value="999";
		document.getElementById("productTypeId").style.display="block";
		document.getElementById("productTypeLabel").style.display="block";
		
		
		document.getElementById("ParentProduct").style.display="none";
		document.getElementById("ParentProduct1").style.display="none";
		document.getElementById("itemCodeAvLable").style.display="none";
		document.getElementById("itemCodeAvText").style.display="none";
		
	 }
	 else if(tbl== '2'||tbl== '4') // paper recharge
	 {
	  	document.getElementById("simcardcost").readOnly=true;
		document.getElementById("simcardcost").value="0";
	//	document.getElementById("processingfee").readOnly=true;
	
	
	
		document.getElementById("processingfee").value="0";
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
		document.getElementById("talktime").value="0";
		document.getElementById("activation").readOnly=false;
		document.getElementById("activation").value="0";
		document.getElementById("servicetax").readOnly=false;
		document.getElementById("servicetax").value="0";
		document.getElementById("cesstax").readOnly=false;
		document.getElementById("cesstax").value="0";
		document.getElementById("sh_educess").readOnly=false;
		document.getElementById("sh_educess").value="0";
		
	 	
		document.getElementById("sukservice").style.display="block";
		document.getElementById("sukheader").style.display="none";
		document.getElementById("erheader").style.display="none";
		document.getElementById("oraclItemCode").style.display="none";
		document.getElementById("submission").style.display="block";
		if(tbl== '2') // paper recharge
			document.getElementById("rcheader").style.display="block";
		else if(tbl== '4')
			document.getElementById("av").style.display="block";
		
		
		document.getElementById("mrp").value="0.0";
		
		document.getElementById("productTypeId").value="999";
		document.getElementById("productTypeId").style.display="none";
		document.getElementById("productTypeLabel").style.display="none";
		document.getElementById("ParentProduct").style.display="none";
		document.getElementById("ParentProduct1").style.display="none";
		document.getElementById("itemCodeAvLable").style.display="inline";
		document.getElementById("itemCodeAvText").style.display="inline";
		
    }
     else if(tbl== '4') // Set top box VC pair
	 {
	 }
	 else if(tbl== '5') // Set topbox
	 {
	 }
	 else if(tbl== '6') // VC
	 {
	 }
    else
    { 
		document.getElementById("sukservice").style.display="none";
		document.getElementById("rcheader").style.display="none";
		document.getElementById("submission").style.display="none";
		document.getElementById("av").style.display="none";
		document.getElementById("oraclItemCode").style.display="none";
	}
}

function doSubmit(){

	//alert("dosubmit")
	if (document.DPCreateProductFormBean.circleid.value == 0)
	{
	//alert("dosubmit 1")
		var msg=confirm("This is going to create product for all existing circles. Please confirm it");
		if(msg){
			document.DPCreateProductFormBean.action="DPCreateProductAction.do?methodName=Insertdata";
			document.DPCreateProductFormBean.submit();
			document.getElementById("productname").value=="";
       }
	 }
	 else if ((document.DPCreateProductFormBean.businessCategory.value == '1'|| document.DPCreateProductFormBean.businessCategory.value == '2' ||
	document.DPCreateProductFormBean.businessCategory.value == '3')&& !(document.DPCreateProductFormBean.circleid.value == 0))
	{
//	alert("dosubmit 23")
		document.DPCreateProductFormBean.action="DPCreateProductAction.do?methodName=Insertdata";
		document.DPCreateProductFormBean.submit();
		document.getElementById("productname").value=="";
	 }
	 else if (document.DPCreateProductFormBean.businessCategory.value == '4')
	{
//	alert("dosubmit 345325")
		document.DPCreateProductFormBean.action="DPCreateProductAction.do?methodName=Insertdata";
		document.DPCreateProductFormBean.submit();
		//document.getElementById("productname").value=="";
	} 
}

function validate()
{

	var productStatus=document.getElementById("productStatus").value

//    alert("1");
    if(isNull('document.forms[0]','businessCategory')|| document.getElementById("businessCategory").value=="0"){
		alert('<bean:message bundle="error" key="error.product.selectedSub" />');
		document.getElementById("businessCategory").focus();
		return false; 			
	}
	
	// add by harbans 
	if(document.getElementById("businessCategory").value=="1" && document.getElementById("productTypeId").value=="999" )
	{
			alert('<bean:message bundle="error" key="error.product.productType" />');
			document.getElementById("productTypeId").focus();
			return false; 			
	}
	
	// added by shilpa 
	if(document.getElementById("productCategory").value=="-1")
	{
			
			alert('<bean:message bundle="error" key="error.product.parentProduct" />');
			document.getElementById("productCategory").focus();
			return false; 			
	}
	
	// Add by harbans on Reservation Obserbation 30th June
	if(document.getElementById("businessCategory").value=="1" && document.getElementById("productTypeId").value=="0")
	{
		
		if(document.getElementById("circleid").value!=0 && document.getElementById("parentProductId").value=="999")
		{
				//alert('<bean:message bundle="error" key="error.product.parentProduct" />');
				alert('Please Select Product Category');
				document.getElementById("parentProductId").focus();
				return false; 			
		}
	}

	
	if(document.getElementById("cardgroup").value==""){
			alert('<bean:message bundle="error" key="error.product.cardgroup" />');
			document.getElementById("cardgroup").focus();
			return false; 			
	}
	
	    //alert('Bussiness category : ' + document.getElementById("businessCategory").value);
		if(document.getElementById("businessCategory").value == '41')
		{	
			if(isNull('document.forms[0]','productname')|| document.getElementById("productname").value==""){
				alert('<bean:message bundle="error" key="error.product.productname" />');
				document.getElementById("productname").focus();
				return false; 
			}
			if(isNull('document.forms[0]','companyname')|| document.getElementById("companyname").value==""){
				alert('<bean:message bundle="error" key="error.product.companyname" />');
				document.getElementById("companyname").focus();
				return false; 
			}
			if(isNull('document.forms[0]','warranty')|| document.getElementById("warranty").value=="0"){
				alert('<bean:message bundle="error" key="error.product.warranty" />');
				document.getElementById("warranty").focus();
				return false; 
			}
			if(isNull('document.forms[0]','stocktype')|| document.getElementById("stocktype").selectedIndex=="0"){
				alert('<bean:message bundle="error" key="error.product.stocktype" />');
				document.getElementById("stocktype").focus();
				return false; 
			}
			if(isNull('document.forms[0]','productdesc')|| document.getElementById("productdesc").value==""){
				alert('<bean:message bundle="error" key="error.product.productdesc" />');
				document.getElementById("productdesc").focus();
				return false; 
			}
			if(!(isNumeric(document.forms[0].warranty.value))){
			alert('<bean:message bundle="error" key="error.product.warranty2"/>');
			document.getElementById("warranty").focus();
			return false;
			}
			if(document.getElementById("unit").value == "0" || document.getElementById("unit").value == ""){
				alert('Please Select The Unit');
				document.getElementById("unit").focus();
				return false;
			}
	}
	else if (document.getElementById("businessCategory").value == '1'|| document.getElementById("businessCategory").value == '2' || document.getElementById("businessCategory").value == '3' || document.getElementById("businessCategory").value == '4')
	{
	//	alert("dd");
		  if(isNull('document.forms[0]','productname1')|| document.getElementById("productname1").value=="")
		  {
				alert('<bean:message bundle="error" key="error.product.productname" />');
				document.getElementById("productname1").focus();
				return false; 
		  }
		  if(document.getElementById("cardgroup").value=="")
		  {
				alert('<bean:message bundle="error" key="error.product.cardgroup" />');
				document.getElementById("cardgroup").focus();
				return false; 
		  }
		 /* if(document.getElementById("packtype").value=="0")
		 if(isNull('document.forms[0]','packtype')|| document.getElementById("packtype").value=="")
		  {
				alert('<bean:message bundle="error" key="error.product.packtype" />');
				document.getElementById("packtype").focus();
				return false; 
		  }
		  if(isNull('document.forms[0]','mrp')|| document.getElementById("mrp").value=="" || document.getElementById("mrp").value=="0.0" || document.getElementById("mrp").value=="0")
		  {
				alert('<bean:message bundle="error" key="error.product.mrp" />');
				document.getElementById("mrp").focus();
				return false; 
		  }*/
		  if (document.forms[0].mrp.value != "")
			{
			  if(isLastCharAlphabet(document.forms[0].mrp.value)==true)
			  {
						alert('<bean:message bundle="error" key="error.product.mrp1" />');
						document.getElementById("mrp").focus();
						return false;
			  }
			  if(!(isNumeric(document.forms[0].mrp.value)))
			  {
				alert('<bean:message bundle="error" key="error.product.mrp2" />');
				document.getElementById("warranty").focus();
				return false;
		  }
		 }
		  if(isNull('document.forms[0]','effectivedate')|| document.getElementById("effectivedate").value=="")
		  {
				alert('<bean:message bundle="error" key="error.product.effectivedate" />');
				document.getElementById("effectivedate").focus();
				return false; 
		  }

    	  if(document.getElementById("unit1").value=="0" || document.getElementById("unit1").value=="")
    	  {
			alert("Select Unit for the Product.");
			document.getElementById("unit1").focus();
			return false;	
		  }

	  }// end else
		

	//rajiv jha added start
	if (document.getElementById("businessCategory").value == '1'){
	 if(document.getElementById("oracleitmecode").value=="" || document.getElementById("oracleitmecode").value=="0")
	 {
	        alert("Oracle item code Cannot Be Empty/Zero. ");
			document.getElementById("oracleitmecode").value=0.0;
			document.getElementById("oracleitmecode").focus();
			return false;
	 }	
	
	
	 if(!(document.getElementById("oracleitmecode").value=="" || document.getElementById("oracleitmecode").value=="0"))
	 { 
  	  	   var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?"; 
           for (var i = 0; i < document.getElementById("oracleitmecode").value.length; i++) 
           { 
             if (iChars.indexOf(document.getElementById("oracleitmecode").value.charAt(i)) != -1) 
             { 
               alert ("The input has special characters. \nThese are not allowed.\n"); 
               document.getElementById("oracleitmecode").value=0.0;
			   document.getElementById("oracleitmecode").focus();
			   return false;
             } 
   		  }
   	  } 
   	  
	  if(document.getElementById("additionalvat").value=="" ||document.getElementById("additionalvat").value=="0")
      {
            alert("Additional Vat Cannot Be Empty/Zero.");
			document.getElementById("additionalvat").value=0.0;
			document.getElementById("additionalvat").focus(); 
			return false;
      }
	  	  
	  if(!isNumeric(document.getElementById("additionalvat").value))
		{
			alert("Additional Vat must be numeric");
			document.getElementById("additionalvat").value=0.0;
			document.getElementById("additionalvat").focus();
			return false;
		}
	}
	
	if (document.getElementById("businessCategory").value == '4'){
	 if(document.getElementById("itmeCodeAv").value=="" || document.getElementById("itmeCodeAv").value==" ")
	 {
	        alert("Item code Cannot Be Empty. ");
			document.getElementById("itmeCodeAv").focus();
			return false;
	 }	
	 
	  if(!(document.getElementById("itmeCodeAv").value=="" || document.getElementById("itmeCodeAv").value==" "))
	 { 
  	  	   var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?"; 
           for (var i = 0; i < document.getElementById("itmeCodeAv").value.length; i++) 
           { 
             if (iChars.indexOf(document.getElementById("itmeCodeAv").value.charAt(i)) != -1) 
             { 
               alert ("The item code has special characters. \nThese are not allowed.\n"); 
               document.getElementById("itmeCodeAv").focus();
			   return false;
             } 
   		  }
   	  } 
	}
	
//rajiv jha end
		doSubmit();
	}
	
	
	
	function onpageload(){
	//alert(document.forms[0].businessCategory.value);
	}

function calculateMRP()
{
	var mrp=0;
	var distP=0;
	var basicP=0;
	var buscategory = document.getElementById('businessCategory').value;
	if(buscategory=='1') //CPE
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
		
		distP = mrp-parseFloat(document.getElementById("discount").value);*/
		//distP = distP-parseFloat(document.getElementById("discount").value); 
	}
	else if(buscategory=='2'||buscategory=='4')
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
		
	/*	mrp = parseFloat(document.getElementById("talktime").value)+parseFloat(document.getElementById("activation").value)+parseFloat(document.getElementById("processingfee").value);
		mrp = mrp+parseFloat(document.getElementById("servicetax").value)+parseFloat(document.getElementById("cesstax").value);
		mrp = mrp+parseFloat(document.getElementById("sh_educess").value);
		distP = mrp-parseFloat(document.getElementById("discount").value);
	*/
	}
	else if(buscategory=='3')
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
		
		/*mrp = parseFloat(document.getElementById("talktime").value)+parseFloat(document.getElementById("activation").value)+parseFloat(document.getElementById("processingfee").value);
		mrp = mrp+parseFloat(document.getElementById("servicetax").value)+parseFloat(document.getElementById("cesstax").value);
		mrp = mrp+parseFloat(document.getElementById("sh_educess").value);
		distP = mrp-parseFloat(document.getElementById("discount").value);*/
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

function retailerPrice()
{
	var mrp = parseFloat(document.getElementById("mrp").value);
	var retDis = parseFloat(document.getElementById("retDiscount").value);
	var retP;
	retP = mrp - retDis;
//Added  by neetika
	retP=retP.toFixed(2);
	document.getElementById("retPrice").value = retP
}    
function f3()
{
focusvar='0';
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
	marginheight="0" onkeydown="f1(event);" onload="focusTab();">

	<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
		height="100%" valign="top">
		<TR>
			<TD colspan="2" valign="top" width="100%"><jsp:include
				page="../common/dpheader.jsp" flush="" /></TD>
		</TR>
		<html:form action="DPCreateProductAction">
		<TR valign="top">
			<TD align="left" bgcolor="#FFFFFF"
				background="<%=request.getContextPath()%>/images/bg_main.gif"
				valign="top" height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
			<td>
			<table>
				<tr>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/createProduct.gif"
						width="505" height="22" alt="">
					
					</TD>
				</tr>
			</table>
			<table>
				<TR>
					<TD align="center" colspan="6"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.businesscategory" /><font
						color="red">*</font></b> <html:select styleId="businessCategory"
						property="businessCategory" onchange="Show();getParentProduct();"
						name="DPCreateProductFormBean" style="width:150px"
						style="height:20px"> 
						<html:option value="" >
							<bean:message bundle="view" key="product.selectcategory" />
						</html:option>
						<logic:notEmpty name="DPCreateProductFormBean" property="arrBCList">
							<html:optionsCollection name="DPCreateProductFormBean" property="arrBCList" />
						</logic:notEmpty>
					</html:select><br>
					<br>
					</TD>
				</TR>
				<TR>
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
				<logic:match value="A" property="showCircle" name="DPCreateProductFormBean">		
					<html:select styleId="circleid" property="circleid"
						name="DPCreateProductFormBean" style="width:150px"
						style="height:20px" onchange="onCircleChange();checkProductExist()"> <!-- Neetika added check product exists on 7 oct because if user selects product parent first and then change circle for AV then this validation should occur again -->
						<html:option value="">
							<bean:message bundle="view" key="product.selectcircle" />
						</html:option>
						<logic:notEmpty name="DPCreateProductFormBean"
							property="arrCIList">
							<html:optionsCollection name="DPCreateProductFormBean"
								property="arrCIList" />
						</logic:notEmpty>
					</html:select>
					</logic:match>
					<logic:match value="N" property="showCircle" name="DPCreateProductFormBean">		
					<html:select styleId="circleid" property="circleid"
						name="DPCreateProductFormBean" style="width:150px"
						style="height:20px" disabled="true">
						<html:option value="">
							<bean:message bundle="view" key="product.selectcircle" />
						</html:option>
						<logic:notEmpty name="DPCreateProductFormBean"
							property="arrCIList">
							<html:optionsCollection name="DPCreateProductFormBean"
								property="arrCIList" />
						</logic:notEmpty>
					</html:select>
					</logic:match>
					</TD>
				</TR>
				<TR>
					<TD id="msg" width="754"><FONT color="RED"><STRONG><html:errors
						bundle="view" property="message.product" /><html:errors
						bundle="error" property="errors.product" /><html:errors />
						<html:errors bundle="error" property="errors.product1" /></STRONG></FONT></TD>
				</TR>
				<tr>
					<td width="754">
					
					<TABLE id="sukservice" style="display:none">
						<TR id="sukheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b><font color='red'>CPE SERVICES</font></b></TD>
						</TR>
						<TR id="erheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b><font color='red'>EASY RECHARGE</font>
							</b> </TD>
						</TR>
						<TR id="rcheader" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b><font color='red'>PAPER RECHARGE SERVICES</font></b></TD>
						</TR>
						<TR id="av" style="display:none">
							<TD valign="top" height="100%" align="left" colspan='6'><b><font color='red'>ACTIVATION VOUCHER</font></b></TD>
						</TR>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.productname" /><font color="red">*</font></TD>
							<TD><html:text name="DPCreateProductFormBean" styleId="productname1"
								property="productname1" maxlength="100" onkeypress="alphaNumWithSpace()" onfocus="f2('3')"/></TD>
							
					<!-- Added by Shilpa -->
						<TD class="text pLeft15" id="">&nbsp;<span id="productCategLabel" >&nbsp;<bean:message bundle="view"
								key="productcreate.ParentProduct" /><font color="red">*</font></span>
						</TD>
						<TD>
							<html:select property="productCategory"  styleId="productCategory" onchange="checkProductExist()" style="width:150px" style="height:20px"> 
							<html:option value="-1">
							<bean:message bundle="view" key="productcreate.ParentProduct" />
							</html:option>
							<logic:notEmpty  property="dcProductCategoryList" name="DPCreateProductFormBean" >
							<html:options labelProperty="productCategory" property="productCategory" collection="dcProductCategoryList" />
							</logic:notEmpty>
						</html:select>
						</TD>
							</TR>			<TR>
						<TD class="text pLeft15" id="">&nbsp;<span id="productTypeLabel" >&nbsp;<bean:message bundle="view"
								key="productcreate.ProductType" /><font color="red">*</font></span>
						</TD>
						<TD>
							<html:select property="productTypeId"  styleId="productTypeId" onchange="changeParentProductList();" style="width:150px" style="height:20px"> 
							<html:option value="999">
								<bean:message bundle="view" key="product.selectProductType" />
							</html:option>
							<logic:notEmpty  property="dcProductTypeList" name="DPCreateProductFormBean" >
							<html:options labelProperty="productType" property="productId" collection="dcProductTypeList" />
							</logic:notEmpty>
						</html:select>
						</TD>
						
						
						<TD class="text pLeft15" id="ParentProduct" style="display:none">&nbsp; Product Category<font color="red">*</font>
						</TD>
						<TD id="ParentProduct1" style="display:none">
							<html:select property="parentProductId"  styleId="ParentProduct" style="width:150px" style="height:20px"> 
							<html:option value="999">
								
									Select Product Category
							</html:option>
							<logic:notEmpty  property="reverseProductList" name="DPCreateProductFormBean" >
							<html:options labelProperty="productName" property="productId" collection="reverseProductList" />
							</logic:notEmpty>
						</html:select>
						</TD>
						</TR>
							
						<TR>
						<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.cardgroup" /><font color="red">*</font></TD>
						<TD><!-- >html:text name="DPCreateProductFormBean" styleId="cardgroup"
								property="cardgroup" maxlength="7" onkeypress="alphaNumWithoutSpace();"  onfocus="f2('4')"/ -->
						<html:select property="cardgroup" name="DPCreateProductFormBean" style="width:150px"
						style="height:20px"> 
						<html:option value="">
							<bean:message bundle="view" key="product.selectcg" />
						</html:option>
				
						<logic:notEmpty name="DPCreateProductFormBean" 
							property="arrCGList">
							<html:optionsCollection name="DPCreateProductFormBean"
								property="arrCGList" />
						</logic:notEmpty>
						</html:select>
						</TD>
				
						<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.packtype" /><font color="red"></font></TD>
							<TD>
						
							<html:text name="DPCreateProductFormBean" styleId="packtype"
								property="packtype" maxlength="2" onkeypress="alphaNumWithoutSpace()"  onfocus="f2('5')"/>
								 
								</TD>
						
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.mrp" /></TD>
							<TD><html:text name="DPCreateProductFormBean" property="mrp" 
								maxlength="10" readonly="true" onfocus="f2('6')"/></TD>
							</TR>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.simcardcost" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="simcardcost" maxlength="10" onblur="calculateMRP();" onfocus="f2('7')"/></TD>
						
							
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.activation" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="activation" maxlength="50" onblur="calculateMRP();" onfocus="f2('9')"/></TD>
							
							
							
							
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.talktime" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="talktime" maxlength="10" onblur="calculateMRP();" onfocus="f2('8')" /></TD>
						</TR>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.salestax" /></TD>
							
							
                            <TD><html:text name="DPCreateProductFormBean"
								property="salestax" maxlength="10" onblur="calculateMRP();" onfocus="f2('11')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.cesstax" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="cesstax" maxlength="10" onblur="calculateMRP();" onfocus="f2('12')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.vat" /></TD>
							<TD><html:text name="DPCreateProductFormBean" property="vat"
								maxlength="10" onblur="calculateMRP();"  onfocus="f2('13')"/></TD>
								</TR>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.servicetax" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="servicetax" maxlength="10" onblur="calculateMRP();"  onfocus="f2('14')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.turnovertax" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="turnovertax" maxlength="10" onblur="calculateMRP();"  onfocus="f2('15')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.sheducess" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="sh_educess" maxlength="10" onblur="calculateMRP();"  onfocus="f2('16')"/></TD>

						</tr>
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.goldennumbercharge" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="goldennumbercharge" maxlength="10" onblur="calculateMRP();"  onfocus="f2('17')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.surcharge" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="surcharge" maxlength="10" onblur="calculateMRP();"  onfocus="f2('18')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.freight" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="freight" maxlength="10" onblur="calculateMRP();"  onfocus="f2('19')"/></TD>

							</tr>
						<tr>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.insurance" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="insurance" maxlength="10" onblur="calculateMRP();"  onfocus="f2('20')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.tradediscount" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="tradediscount" maxlength="10" onblur="calculateMRP();"  onfocus="f2('21')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.cashdiscount" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="cashdiscount" maxlength="10" onblur="calculateMRP();"  onfocus="f2('22')"/></TD>
							</TR>
						<TR>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.costprice" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="costprice" maxlength="10"  onfocus="f2('23')" /></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.octoriprice" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="octoriprice" maxlength="10" onblur="calculateMRP();"  onfocus="f2('24')" /></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.discount" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="discount" maxlength="10" onblur="calculateMRP();"  onfocus="f2('25')"/></TD>
							
						</TR>
						<TR>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.distprice" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="distprice" maxlength="10" readonly="true" onfocus="f2('26')"   /></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.retDiscount" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="retDiscount" maxlength="10" onblur="retailerPrice();"  onfocus="f2('27')"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.retPrice" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="retPrice" maxlength="10" readonly="true"  onfocus="f2('28')"/></TD>
							</TR>
						<TR>
						<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.freeservice" /></TD>
							<TD><html:checkbox property="freeservice" value="Y" onclick="f2('29')" /><bean:write
								name='DPCreateProductFormBean' property='freeservice' /></TD>
								<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.processingfee" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="processingfee" maxlength="10" onblur="calculateMRP();" onfocus="f2('10')"/></TD>
							<TD id="itemCodeAvLable" class="text pLeft15" style="display:none">Item Code<font color="red">*</font></TD>
							<TD id="itemCodeAvText" style="display:none"><html:text name="DPCreateProductFormBean"
								property="itmeCodeAv" maxlength="40"/></TD>	
						</TR>
						<!-- rajiv jha added start -->
						<TR id="oraclItemCode" style="display:none">
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.additionalvat" /><font color="red">*</font></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="additionalvat" maxlength="10" onblur="calculateMRP();"/></TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.oracleitemcode" /><font color="red">*</font></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="oracleitmecode" maxlength="40"/></TD>														
							</TR>
						<!-- rajiv jha added start -->
						
						<TR>
							<TD class="text pLeft15">
							<bean:message bundle="view" key="product.unit"/><font color="red">*</font>
							</TD>
							<td>
							<html:select
								property="unit1" name="DPCreateProductFormBean" styleId="unit" onchange="f2('30')">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty name="DPCreateProductFormBean"
									property="unitList">
							<bean:define name="DPCreateProductFormBean" property="unitList" id="unitId"/>	
									<html:options collection="unitId" name="DPCreateProductFormBean"
										property="unit" labelProperty="unitName" />	
								</logic:notEmpty>
							</html:select>
							</td>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.effectivedate" /><font color="red">*</font></TD>
							<TD><html:text name="DPCreateProductFormBean"
								readonly="true" property="effectivedate" size="12"  onchange="f2('31')"/>
				 				
							</TD>
							<TD class="text pLeft15"><bean:message bundle="view"
								key="productcreate.version" /></TD>
							<TD><html:text name="DPCreateProductFormBean"
								property="version" maxlength="50" readonly="true"  onfocus="f2('32')"  /></TD>
						</TR>
						
						
						<!-- aman -->
						
						<TR>
							<TD class="text pLeft15"><bean:message bundle="view" key="product.status" /><font color="red">*</font>
							</TD>
							<td>
								<html:select
								property="productStatus" name="DPCreateProductFormBean" styleId="productStatus" onchange="f2('30')">
									<html:option value="A"><bean:message bundle="view" key="product.active" /></html:option>
									<html:option value="I"><bean:message bundle="view" key="product.inactive" /></html:option>
								</html:select>
							</td>
						</TR>
						
						<!-- end by aman -->
						
						<TR>
								<TD></TD>
								<TD></TD>
								<TD></TD>
								<TD></TD>
								<TD></TD>
								<TD></TD>
						 
						</TR>
					</TABLE>
				
				<TR id="submission" style="display: none;">
					<td colspan="1" align="center" width="754"><html:button
						property="btn" value="Submit" onclick="return validate();" /></td>
				</TR>
				
				<html:hidden property="circleid"/>
				<html:hidden property="showCircle"/>				
			</table>
			</td>
			</TR>
			</html:form>
			<tr>
				<td colspan="4">
					<jsp:include page="../common/footer.jsp" flush="" />
				</td>	
			</tr>
	</TABLE>

<script>
Show();
</script>
</BODY>
</html:html>