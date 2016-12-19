
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ibm.dp.common.Constants"%>
<%@page import="com.ibm.virtualization.recharge.dto.UserSessionContext"%>

<html:html>

<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/DropDownAjax.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/Ajax.js"
	type="text/javascript">
</SCRIPT>

<TITLE></TITLE>
<%
			UserSessionContext sessionContext = (UserSessionContext) request
			.getSession().getAttribute(Constants.AUTHENTICATED_USER);
	long loginUserId = sessionContext.getId();
	int accountLevel = Integer.parseInt(sessionContext
			.getAccountLevel());
	int circleId = sessionContext.getCircleId();

	//System.out.println("loginUserId**************" + loginUserId);
%>
<style type="text/css">

.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);  
}

.fieldvalueGrayReadOnly {
	FONT-WEIGHT: regular;
	FONT-SIZE: 10px;
	COLOR: #000000;
	background: #c0c0c0;
	FONT-FAMILY: verdana,arial,helvetica
}

.fieldvalue {
	FONT-WEIGHT: regular;
	FONT-SIZE: 10px;
	COLOR: #000000;
	FONT-FAMILY: verdana,arial,helvetica
}

</style>
<SCRIPT language="JavaScript" type="text/javascript"><!--

	function resetValuesProduct()
	{
		document.getElementById("productIdNew").value="-1";
		document.getElementById("intStockAvail").value="0";
		document.getElementById("find").style.display ='none';
	}
	
	function getProductList()
	{
		var circleId = document.getElementById("circleIdNew").value;
		if(circleId=='-1')
		{
			alert("Please select a Circle");
			document.getElementById("businessCat").value='-1';
			return false;
		}
		
		var businessCategory = document.getElementById("businessCat").value;
		
		if(businessCategory != '-1')
		{
			getAllProductsSingleCircle(businessCategory, circleId, 'productIdNew');
		}
	
	
		//document.getElementById("fromTsmId").value = "-1"; 
  	 	//document.getElementById("fromDistId").value = "-1"; 
	 	//document.getElementById("toTsmId").value = "-1"; 
 		//document.getElementById("toDistId").value = "-1"; 
 		document.getElementById("productIdNew").value = "-1";
 		document.getElementById("intStockAvail").value = "";
 		document.getElementById("intTransQty").value = "";
	}
	
	function getTsmName() 
	{
		var circleId = document.getElementById("circleIdNew").value;
		
		if(circleId=='-1')
		{
			document.getElementById("businessCat").value = "-1"; 
			document.getElementById("fromTsmId").value = "-1"; 
			document.getElementById("fromDistId").value = "-1"; 
			document.getElementById("toTsmId").value = "-1"; 
			document.getElementById("toDistId").value = "-1"; 
			document.getElementById("productIdNew").value = "-1";
			document.getElementById("intStockAvail").value = "";
			document.getElementById("intTransQty").value = "";
		}
		else
		{
			var loginId = <%= sessionContext.getId()%>;
	   		var url = "dpDistributorStockTransfer.do?methodName=getTsmList&levelId="+circleId;
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
			req.onreadystatechange = getTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}
	}
	function getTsmNameChange()
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
			var selectObj = document.getElementById("fromTsmId");
			var selectObj2 = document.getElementById("toTsmId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("--Select TSM--","-1");
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("--Select TSM--","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
				selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}	
		
	function getFromDistName()
	{
	    var levelId = document.getElementById("fromTsmId").value;
	    var circleId = document.getElementById("circleIdNew").value;
	    var busCategory = document.getElementById("businessCat").value;
	
	 	if(levelId!='-1'){
			var url = "dpDistributorStockTransfer.do?methodName=getFromDistList&parentId="+levelId+"&circleId="+circleId+"&busCategory="+busCategory;
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
			req.onreadystatechange = getFromDistNameChange;
			req.open("POST",url,false);
			req.send();
		}
	}

	function getFromDistNameChange()
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
			var selectObj = document.getElementById("fromDistId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Distributor","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
	
	function getToDistName()
	{
	    var levelId = document.getElementById("toTsmId").value;
	    var circleId = document.getElementById("circleIdNew").value;
		var busCategory = document.getElementById("businessCat").value;
		
	 	if(levelId!='-1'){
			var url = "dpDistributorStockTransfer.do?methodName=getFromDistList&parentId="+levelId+"&circleId="+circleId+"&busCategory="+busCategory;
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
			req.onreadystatechange = getToDistNameChange;
			req.open("POST",url,false);
			req.send();
		}
	}

	function getToDistNameChange()
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
			var selectObj = document.getElementById("toDistId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Distributor","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
	

function getAvailableStock()
{

	var prodID = document.getElementById('productIdNew').value;
	
	if(prodID==0 || prodID=='-1')
	{
		return false;
	}
		
	var fromDistID = document.getElementById('fromDistId').value;
	var toDistID = document.getElementById('toDistId').value;
	var alertMsg = "";
	
	if(fromDistID==-1)
	{
		alertMsg = "Select a From Distributor\n";
	}
	if(toDistID==-1)
	{
		alertMsg += "Select a To Distributor\n";
	}
	
	if(alertMsg!="")
	{
		alert(alertMsg);
		document.getElementById('productIdNew').value = 0;
		return false;
	}	
	else
	{
		document.getElementById('fromDistId').disabled = true;
		document.getElementById('toDistId').disabled = true;
		document.getElementById('circleIdNew').disabled = true;
		document.getElementById('fromTsmId').disabled = true;
		document.getElementById('toTsmId').disabled = true;
		document.getElementById("businessCat").disabled = true;
		document.getElementById("productIdNew").disabled = true;
		
		document.getElementById('intTransQty').value = "";
	}
	
	var req = GetXmlHttpObject();
	if(req == null)
	{
		alert ("Your Browser Does Not Support AJAX!");
		return;
	} 
	
	var url = "dpDistributorStockTransfer.do";
	url = url + "?methodName=getAvailableStock&intFromDist="+fromDistID+"&intProduct="+prodID;
	
	var elementId = "intStockAvail";
	req.onreadystatechange = function()
	{
		setAjaxTextValues(elementId, req);
	}
				
	req.open("POST",url,true);
	req.send();
}

function setAjaxTextValues(elementId, req)
{
	if(req.readyState == 4 || req.readyState == "complete")
	{
		if(req.status != 200)
		{
			document.getElementById(elementId).value = "Exception During Transaction" ;
		}
		else
		{
			var text = req.responseText;
			try
			{
				text = parseInt(text);
				
				if(text != null)
					document.getElementById(elementId).value = text; 		
				else
					document.getElementById(elementId).value = "0" ; 
			}
			catch(e)
			{
				document.getElementById(elementId).value = "Exception During Transaction" ;
			}
		}
	}
}
		
function GetXmlHttpObject()
{
	var xmlHttp=null;
	try
	{
  		// Firefox, Opera 8.0+, Safari
	  	xmlHttp=new XMLHttpRequest();
	}
	catch(e)
	{
		// Internet Explorer
	  	try
	  	{
	    	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	    }
	  	catch(e)
	  	{
	    	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	    }
	}
	return xmlHttp;
}

function transferDistStock()
{
	var alertMsg = "";
	
	var tbody = document.getElementById("transQtyBody");
	var rows=tbody.rows;
	var totrow=rows.length;

	if(totrow<1)
	{
		alert("No Stock Added to Transfer");
		return false;
	}
	else
	{
		document.getElementById('methodName').value = "transferDistStock";
		document.DSTForm.submit();
	}
}

function validateAdd()
{
	var alertMsg = "";
	
	if(document.getElementById('circleIdNew').value == "-1")
	{
		alertMsg = "Select a Circle\n";
		
	}
	if(document.getElementById('fromTsmId').value==0)
	{
		alertMsg += "Select a From TSM\n";
	}
	
	if(document.getElementById('toTsmId').value==0)
	{
		alertMsg += "Select a To TSM\n";
	}
	
	if(document.getElementById('fromDistId').value=="-1")
	{
		alertMsg += "Select a From Distributor\n";
	}
	
	if(document.getElementById('toDistId').value=="-1")
	{
		alertMsg += "Select a To Distributor\n";
	}
	if(document.getElementById('businessCat').value=="-1")
	{
		alertMsg += "Select a Business Category\n";
	}
	
	
	var selProduct = document.getElementById('productIdNew');
	if(selProduct.value==0)
	{
		alertMsg += "Select a Product\n";
	}
	
	var tbody = document.getElementById("transQtyBody");
	var rows=tbody.rows;
	var totrow=rows.length;
	var cols;
	
	if(totrow>0)
	{
		var addedRrodIDs = document.getElementsByName('arrProductID');
		//alert(addedRrodIDs);
		if(addedRrodIDs.length >0)
		{
			for(var count=0; count<addedRrodIDs.length; count++)
			{
					//alert(addedRrodIDs[count].value+"selProduct.value"+selProduct.value);
				if(addedRrodIDs[count].value == selProduct.value)
				{
					alertMsg += "Product is already selected, Please choose another Product";
					return alertMsg;
				}
			}
		}
	}
		
	if(alertMsg=="")
	{
		var stockAvail = document.getElementById('intStockAvail').value;
		if(!isNumeric(stockAvail))
		{
			alertMsg +="Wrong value of Stock Available..Reselect the values to Add to Grid\n";	
		}
		else if(parseInt(stockAvail)<1)
		{
			alertMsg +="No Stock Available\n";
			document.getElementById('intTransQty').value = "";
		}
		else
		{
			var transQty = document.getElementById('intTransQty').value;
			if(transQty=="")
			{
				alertMsg += "Enter a Transfered Quantity\n";
			}
			else if(!isNumeric(transQty))
			{
				alertMsg += "Transfer Quantity can only be Positive Numeric\n";
				document.getElementById('intTransQty').value = "";
				document.getElementById('intTransQty').focus();
			}
	
			else if(transQty.indexOf(".")>0)
			{
				alertMsg += "Transfer Quantity can be numeric only \n";
				document.getElementById('intTransQty').value = "";
				document.getElementById('intTransQty').focus();
			}
			else if(parseInt(transQty)>parseInt(stockAvail))
			{
				alertMsg +="Transfered Quantity should be less or equal to Stock Available\n";
				document.getElementById('intTransQty').value = "";
				document.getElementById('intTransQty').focus();
			}			
		}
	}
	
	return alertMsg;
}

function availableQty(){
		var fromDist = document.getElementById("fromDistId").value;
	  	var productId = document.getElementById("productIdNew").value;
	  	var circleId = document.getElementById("circleIdNew").value;
	  
		alert("fromDist"+fromDist);
	    if(productId!="-1" && productId!=""){
			var url = "dpDistributorStockTransfer.do?methodName=getAvailableStock&intFromDist="+fromDist+"&intProduct="+productId+"&circleId="+circleId;
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
			req.onreadystatechange = getavailableqty;
			req.open("POST",url,false);
			req.send();
		}else{
			document.getElementById("availableProdQty").value ="0";
		}		
		
		if(document.getElementById("availableProdQty").value>0){
			document.getElementById("find").style.display ='inline';
		}else{
			document.getElementById("find").style.display ='none';
		}
	}
	//Added by Neetika for inter ssd duplicate requests issue
	function checkCombination(){
		var fromDist = document.getElementById("fromDistId").value;
	  
	  	var toDistId = document.getElementById("toDistId").value;
	 	 var Index = document.getElementById("productIdNew").selectedIndex;
		var productId=document.getElementById("productIdNew").options[Index].value;
	//	alert("fromDist"+fromDist+"productId"+productId);
	    if(productId!="-1" && productId!=""){
			var url = "dpDistributorStockTransfer.do?methodName=checkCombination&intFromDist="+fromDist+"&intProduct="+productId+"&toDistId="+toDistId;
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
			req.onreadystatechange = showmessage;
			req.open("POST",url,false);
			req.send();
		}
	}
	function showmessage()
	{
		if (req.readyState==4 || req.readyState=="complete") 
		
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			optionValues = xmldoc.getElementsByTagName("option");
			//var checkCombFlag = document.getElementById("checkCombFlag");
			
			for(var i=0; i<optionValues.length; i++)
			{
		
				if(optionValues[i].getAttribute("text")=="true")
				{
				alert("Request of Same combination of From distributor, To distributor and Product is pending with distributor. New request cannot be initiated until old request is cancelled or transferred");
				return false;
				}
				else
				{
				addStockToGrid();
				}
				
			}
		}
	}
	
	function getavailableqty()
	{
		alert("getavailableqty");
		if (req.readyState==4 || req.readyState=="complete") 
		
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			// Get Conditions.
			
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("availableProdQty");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.value = optionValues[i].getAttribute("text");
				alert("selectObj.value"+selectObj.value);
			}
		}
	}
	
	function checkDuplicateProd(){
	alert("checkDuplicateProd");
		var productId = document.getElementById("productIdNew").value;
		
		if(productId != "" && productId != "-1"){
			if(document.getElementById("fromDistId").value =="" || document.getElementById("fromDistId").value == "-1"){
				alert("Please Select From Distributor!!");
				document.getElementById("productIdNew").value="-1";
				document.getElementById("fromDistId").focus();
				return false;
			}if(document.getElementById("toDistId").value =="" || document.getElementById("toDistId").value == "-1"){
				alert("Please Select To Distributor!!");
				document.getElementById("productIdNew").value="-1";
				document.getElementById("toDistId").focus();
				return false;
			}
		}
		alert("productId"+productId);
		var table = document.getElementById("dataTable");
		var rowCount = table.rows.length;
		var i=0;
		var prodId ;
		var duplicateFlag ="true";
		alert("rowCount"+rowCount);
		while (i+1<rowCount)
			{
				var x = document.getElementById("dataTable").rows[i+1].cells;
				prodId ="";
	     		// This is for Hidden parameter
	     		prodId= x[0].getElementsByTagName("input")[0].value;
	     		if(prodId == productId){
	     		 duplicateFlag ="false";
	     		}
	     		i++;
	     	}
	     	alert("Duplicate product !!::::::::");
	     if(duplicateFlag == "true"){
	     	availableQty();
	     }else  if(duplicateFlag == "false"){
	     	alert("Duplicate product !!");
	     	document.getElementById("productIdNew").value ="-1";
	     	document.getElementById("availableProdQty").value ="0";
	     	document.getElementById("find").style.display ='none';
	     	return false;	 
	     }
		
	
	}
	function resetting(){
		var selectedProdQty = document.getElementById("transfrProdQty").value;
		var selectedStbs = new Array();
		selectedStbs.value =document.getElementById("jsArrTransfrStbs").value;
		var Index = document.getElementById("productIdNew").selectedIndex;
		var productId=document.getElementById("productIdNew").options[Index].value;
		var prodName = document.getElementById("productIdNew").options[Index].text;
		var prodArray =new Array();
		var existProdArr = new Array();
		if(document.forms[0].jsArrprodId.value!=null && document.forms[0].jsArrprodId.value!=""){
			existProdArr = document.forms[0].jsArrprodId.value.split(",");
		}
		var newSelectedArray = selectedStbs.value.replace(/\,/g, 'A');
		if(selectedProdQty >0){
			try 
			{
				if(document.forms[0].alreadyProExist.value=="false"){
					 var table = document.getElementById("dataTable");
					 var row;
					 var rowCount;
					 var addedRow =0;
					 var i=0;
					 rowCount = table.rows.length;
				     
				     row = table.insertRow(rowCount);
					 
					 var cell1 = row.insertCell(0);
					 cell1.innerHTML = "<div value=\"" + prodName +  "\">" + prodName + "</div><input type=\"hidden\" value=\"" + productId + "\" />";
					 var cell2 = row.insertCell(1);
		   			 cell2.innerHTML = "<div value=\"" + selectedProdQty +  "\">" + selectedProdQty + "</div><input type=\"hidden\" value=\"" + newSelectedArray + "\" />";
					 
					  var cell3 = row.insertCell(2);
					 cell3.innerHTML = "<a href='#' onClick='OpenSerialWindow2("+productId+",\""+selectedStbs.value+"\")' id='viewSerialNos'>View Serial nos</a>";
					 var cell4 = row.insertCell(3);
					 var image = document.createElement("img");
					 image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
					 image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
					 cell4.appendChild(image);
					 rowCount = table.rows.length;
				     while (i+1<rowCount)
					{
						var x = document.getElementById("dataTable").rows[i+1].cells;
						prodId ="";
			     		// This is for Hidden parameter
			     		prodArray[i] = x[0].getElementsByTagName("input")[0].value;
						i++;
					}
					document.forms[0].jsArrprodId.value = prodArray ;
					document.getElementById("circleIdNew").disabled="true";
					document.getElementById("fromTsmId").disabled="true";
					document.getElementById("toTsmId").disabled="true";
					document.getElementById("fromDistId").disabled="true";
					document.getElementById("toDistId").disabled="true";
					document.getElementById("productIdNew").value="-1";
					document.getElementById("availableProdQty").value="0";
					document.getElementById("transfrProdQty").value ="0"
					document.getElementById("find").style.display ='none';
				}else if(document.forms[0].alreadyProExist.value=="true"){
					var table = document.getElementById("dataTable");
					var rowCount = table.rows.length;
					var i=0;
					var tableProductId =  document.forms[0].tableProdId.value ;
					var prodId ;
					while (i+1<rowCount)
						{
							var x = document.getElementById("dataTable").rows[i+1].cells;
							prodId ="";
				     		// This is for Hidden parameter
				     		prodId= x[0].getElementsByTagName("input")[0].value;
				     		
				     		if(document.all){
     							prodName = x[0].getElementsByTagName("div")[0].value;
							} else {
    							prodName = x[0].getElementsByTagName("div")[0].value.textContent;
							}			
				     		
				     		if(prodId == tableProductId){
				     			
				     			table.deleteRow(i+1);	
				     			
				     			table = document.getElementById("dataTable");
								 var row;
								 rowCount = table.rows.length;
							     row = table.insertRow(rowCount);
								 var cell1 = row.insertCell(0);
								 cell1.innerHTML = "<div value=\"" + prodName +  "\">" + prodName + "</div><input type=\"hidden\" value=\"" + prodId + "\" />";
								 var cell2 = row.insertCell(1);
					   			 cell2.innerHTML = "<div value=\"" + selectedProdQty +  "\">" + selectedProdQty + "</div><input type=\"hidden\" value=\"" + newSelectedArray + "\" />";
								 
								  var cell3 = row.insertCell(2);
								  cell3.innerHTML = "<a href='#' onClick='OpenSerialWindow2("+prodId+",\""+selectedStbs.value+"\")' id='viewSerialNos'>View Serial nos</a>";
					
								 var cell4 = row.insertCell(3);
								 var image = document.createElement("img");
								 image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
								 image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
								 cell4.appendChild(image);
							     			
							     			
				     		}
				     		i++;
			     	    }
						document.getElementById("transfrProdQty").value ="0";
				
				}
	     	} 
				catch(error) 
				{
					alert(error.message);
				}
				alternate();
		}
	}
	
function remove(rowCount2)
{
		var table = document.getElementById("dataTable");
		table.deleteRow(rowCount2);	
		alternate();
}

function resetPage()
{
	document.getElementById('fromTsmId').value = "-1";
	document.getElementById('fromDistId').value = "-1";
	document.getElementById('fromTsmId').disabled = false;
	document.getElementById('fromDistId').disabled = false;
	document.getElementById('toTsmId').value = "-1";
	document.getElementById('toDistId').value = "-1";
	document.getElementById('toTsmId').disabled = false;
	document.getElementById('toDistId').disabled = false;
	document.getElementById('intStockAvail').value = "";
	document.getElementById('intTransQty').value = "";
	document.getElementById('circleIdNew').disabled = false;
	document.getElementById('circleIdNew').value = "-1";
	document.getElementById('businessCat').value = "-1";
	document.getElementById('businessCat').disabled = false;
	document.getElementById('productIdNew').value = "-1";
	document.getElementById('productIdNew').disabled = false;
}

function fromDistChanged()
{

	document.getElementById('productIdNew').value = "-1";
	document.getElementById('intStockAvail').value = "";
<%--		document.getElementById('toDistId').value = 0; --%>
	document.getElementById('intTransQty').value = "";
	
}
function validateDist(DistId)
{
	var fromDist = document.getElementById('fromDistId').value;
	var toDist = document.getElementById('toDistId').value;

	if(fromDist==toDist)
	{
		alert("From Distributor and To Distributor can not be same");
		DistId.value = "-1"; 
		return false;
	}
}
function validateDistTO_(DistId)
{
	var fromDist = document.getElementById('fromDistId').value;
	var toDist = document.getElementById('toDistId').value;

	if(fromDist==toDist)
	{
		alert("From Distributor and To Distributor can not be same");
		DistId.value = "-1"; 
		return false;
	}
	else
	{
		var url = "dpDistributorStockTransfer.do?methodName=validateToDist&intFromDist="+fromDist+"&intToDist="+toDist;
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
		req.onreadystatechange = validateToDist;
		req.open("POST",url,false);
		req.send();
	}
}

function validateToDist()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null) 
		{
			return;
		}
		// Get Conditions.
		optionValues = xmldoc.getElementsByTagName("options");
		var isDistVal = document.getAttribute("isDistValid");
		if(isDistVal='false')
		{
			document.getElementById('toDistId').value = '-1';
			alert("To Distributor and From Distributor should belong to the same circle")
			return false;
		}
	}
}

function validateTSM(TsmId)
{
	var businessCategory = document.getElementById("businessCat").value;
		
	if(businessCategory == '-1')
	{
		alert("Please select a Business Category");
		TsmId.value="-1";
		return false;
	}
	
	var loginId = '<%= loginUserId %>';
	var fromTsm = document.getElementById('fromTsmId').value;
	var toTsm = document.getElementById('toTsmId').value;

	if(fromTsm==0 || toTsm==0)
	{
	 return true;
	}
	
	if(fromTsm != loginId && toTsm !=loginId  )
	{
		alert("Login TSM Should Be one From TSM or To TSM");
		TsmId.value = "-1";
		return false;
	}
}



function validateTransQty()
{

}

var prodRows = 0;
function addStockToGrid()
{
	
	var validateMsg = validateAdd();
	if(validateMsg!="")
	{
		alert(validateMsg);
		return false;
	}
	if(document.getElementById('intTransQty').value != "" && parseInt(document.getElementById('intTransQty').value)  <= 0)
	{
		alert("Transfered Quantity Can not be Zero");
		return false;
	}
	
	var tableBody = document.getElementById('transQtyBody');
	
	var tr 					= document.createElement('TR');
    
    var tdFromDistName 		= document.createElement('TD');
    //var tdBusinessCategory 	= document.createElement('TD');
    var tdProductName   	= document.createElement('TD');
    var tdStockAvail   		= document.createElement('TD');
    var tdTransQty			= document.createElement('TD');
    var tdToDistName 		= document.createElement('TD');
	var tdButRemove			= document.createElement('TD');
    
    var inpFromDistName 	 = document.createElement('INPUT');
    //var inpBusinessCategory  = document.createElement('INPUT');
    var inpProductName  	 = document.createElement('INPUT');
    var inpStockAvail   	 = document.createElement('INPUT');
    var inpTransQty			 = document.createElement('INPUT');
    var inpToDistName 		 = document.createElement('INPUT');
    
    var inpFromDistID 	= document.createElement('INPUT');
    var inpProductID   	= document.createElement('INPUT');
    var inpToDistID	 	= document.createElement('INPUT');
    
    var objFromDist = document.getElementById('fromDistId');
    inpFromDistName.value = objFromDist.options[objFromDist.selectedIndex].text;
    
    var objProduct = document.getElementById('productIdNew');
    inpProductName.value = objProduct.options[objProduct.selectedIndex].text;
    
    inpStockAvail.value = document.getElementById('intStockAvail').value;
    
    inpTransQty.value = document.getElementById('intTransQty').value;
    
    var objToDist = document.getElementById('toDistId');
    inpToDistName.value = objToDist.options[objToDist.selectedIndex].text;
    
	inpFromDistID.setAttribute("type", "hidden");
  	inpFromDistID.setAttribute("name", "arrFromDistID");
  	inpFromDistID.setAttribute("id", "arrFromDistID");
  	inpFromDistID.value = objFromDist.value;
  	
  	inpProductID.setAttribute("type", "hidden");
  	inpProductID.setAttribute("name", "arrProductID");
  	inpProductID.setAttribute("id", "arrProductID");
  	inpProductID.value = objProduct.value;
    
  	inpToDistID.setAttribute("type", "hidden");
  	inpToDistID.setAttribute("name", "arrToDistID");
  	inpToDistID.setAttribute("id", "arrToDistID");
  	inpToDistID.value = objToDist.value;
    
        
    //setting attributes for input texts
    
	inpFromDistName.name="fromDist";
	inpFromDistName.id="fromDistcol";
	inpFromDistName.width="180";
	inpFromDistName.style.fontSize ="11";
	inpFromDistName.readOnly = true ;
	
	inpProductName.name="product";
	inpProductName.id="productcol";
	inpProductName.width="130";
	inpProductName.style.fontSize ="11";
	inpProductName.readOnly = true ;
	
	inpStockAvail.name="stockAvail";
	inpStockAvail.id="stockAvailcol";
	inpStockAvail.width="100";
	inpStockAvail.style.fontSize ="11";
	inpStockAvail.readOnly = true ;
	
	inpTransQty.name="arrTransQty";
	inpTransQty.id="arrTransQty";
	inpTransQty.width="100";
	inpTransQty.style.fontSize ="11";
	inpTransQty.readOnly = true ;
	
	inpToDistName.name="toDist";
	inpToDistName.id="toDistcol";
	inpToDistName.width="180";
	inpToDistName.style.fontSize ="11";
	inpToDistName.readOnly = true ;
	
    tdFromDistName.appendChild(inpFromDistName);
    tdProductName.appendChild(inpProductName);
    tdStockAvail.appendChild(inpStockAvail);
    tdTransQty.appendChild(inpTransQty);
    tdToDistName.appendChild(inpToDistName);
   
    var arry = new Array();
    if(prodRows>-1)
    {
        var img     = document.createElement('BUTTON');
        img.value="Remove";
        img.onclick = function()
        {
            removeProduct(tr);
        }
        tdButRemove.appendChild(img);
    }
   
    tr.appendChild(tdFromDistName);
    tr.appendChild(tdToDistName);
	tr.appendChild(tdProductName);
	tr.appendChild(tdStockAvail);
	tr.appendChild(tdTransQty);
	tr.appendChild(tdButRemove);
	
	tr.appendChild(inpFromDistID);
	tr.appendChild(inpProductID);
	tr.appendChild(inpToDistID);
	
	tableBody.appendChild(tr);
	
	
}

function removeProduct(tr)
{
    tr.parentNode.removeChild(tr);
}

function onLoadTSM()
{
	 var accountLevel = '<%= accountLevel %>';
	 var circleId     = '<%= circleId %>';
	 if(accountLevel == 5)
	 {
		 document.getElementById("circleIdNew").value = circleId; 
		 getTsmName();
		 document.getElementById("circleLebal").style.visibility="hidden";
		 document.getElementById("circleIdNew").style.visibility="hidden";
	 }
	 
	 document.getElementById("fromTsmId").value = "-1"; 
	 document.getElementById("fromDistId").value = "-1"; 
	 document.getElementById("toTsmId").value = "-1"; 
	 document.getElementById("toDistId").value = "-1"; 
	 document.getElementById("productIdNew").value = "-1";
	 document.getElementById("intStockAvail").value = "";
	 document.getElementById("intTransQty").value = "";
}

</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">

<html:form action="/dpDistributorStockTransfer" name="DSTForm"
	type="com.ibm.dp.beans.DPDistributorStockTransferFormBean">

	<html:hidden property="showCircle"
		name="DPDistributorStockTransferFormBean" />
	<html:hidden property="showTSM"
		name="DPDistributorStockTransferFormBean" />
	<html:hidden property="showDist"
		name="DPDistributorStockTransferFormBean" />
	<html:hidden property="hiddenCircleSelecIds"
		name="DPDistributorStockTransferFormBean" />
	<html:hidden property="hiddenTsmSelecIds"
		name="DPDistributorStockTransferFormBean" />
	<html:hidden property="hiddenDistSelecIds"
		name="DPDistributorStockTransferFormBean" />

	<br>
	<IMG
		src="<%=request.getContextPath()%>/images/initiatestocktransfer.JPG"
		width="533" height="26" alt="" />
	<BR>
	<BR>
	<logic:notEmpty property="strSuccessMsg"
		name="DPDistributorStockTransferFormBean">
		<BR>
		<strong><font color="red"><bean:write
			property="strSuccessMsg" name="DPDistributorStockTransferFormBean" /></font></strong>
		<BR>
	</logic:notEmpty>

	<DIV style="height: 150px; overflow: auto;" width="100%">
	<TABLE width="90%" border="0" cellpadding="1" cellspacing="2"
		align="center">

		<TR id="circle">
			<TD class="text pLeft15">
			<div id="circleLebal"><STRONG> <FONT color="#000000">
			<bean:message bundle="dp" key="label.DST.Circle" /> </FONT> <FONT
				color="RED">*</FONT> </STRONG></div>
			</TD>
			<TD class="txt"><html:select property="circleIdNew"
				onchange="getTsmName();" style="width:150px">
				<logic:notEmpty property="circleList"
					name="DPDistributorStockTransferFormBean">
					<html:option value="-1">--Select Circle--</html:option>
					<bean:define id="circleList"
						name="DPDistributorStockTransferFormBean" property="circleList" />
					<html:options labelProperty="circleName" property="circleId"
						collection="circleList" />
				</logic:notEmpty>
			</html:select>
			</TD>
			<TD class="text pLeft15"><STRONG> <FONT
				color="#000000"> <bean:message bundle="dp"
				key="label.DST.BusinessCategoryLabel" /> </FONT> <FONT color="RED">*</FONT>
			</STRONG></TD>
			<TD class="txt"><html:select property="businessCat"
				style="width:150px" onchange="getProductList();">
				<html:option value="-1">--Select A category--</html:option>
				<html:optionsCollection name="DPDistributorStockTransferFormBean"
					property="busCatList" label="circleName" value="circleId" />
			</html:select></TD>
		</TR>
		<tr>
			<TD class="text pLeft15" width='20%'><STRONG> <FONT
				color="#000000"> <bean:message bundle="dp"
				key="label.DST.FromTSMLabel" /> </FONT> <FONT color="RED">*</FONT> </STRONG></TD>
			<TD class="txt" width='30%'><html:select property="fromTsmId"
				onchange="validateTSM(fromTsmId);getFromDistName();"
				style="width:150px">
				<html:option value="-1">--Select TSM--</html:option>
			</html:select></td>

			<TD class="text pLeft15" width='20%'><STRONG> <FONT
				color="#000000"> <bean:message bundle="dp"
				key="label.DST.FromDistLabel" /> </FONT> <FONT color="RED">*</FONT> </STRONG></TD>
			<TD class="txt" width='30%'><html:select property="fromDistId"
				style="width:150px"
				onchange="fromDistChanged();validateDist(fromDistId);">
				<html:option value="-1">--Select Distributor--</html:option>
			</html:select></td>

		</tr>
		<tr>
			<TD class="text pLeft15"><STRONG> <FONT
				color="#000000"> <bean:message bundle="dp"
				key="label.DST.ToYTSMLabel" /> </FONT> <FONT color="RED">*</FONT> </STRONG></TD>

			<TD class="txt"><html:select property="toTsmId"
				style="width:150px" onchange="validateTSM(toTsmId);getToDistName();">
				<html:option value="-1">--Select TSM--</html:option>
			</html:select></td>

			<TD class="text pLeft15"><STRONG> <FONT color="#000000">
			<bean:message bundle="dp" key="label.DST.ToDistLabel" /> </FONT> <FONT
				color="RED">*</FONT> </STRONG></TD>
			<TD class="txt"><html:select property="toDistId"
				style="width:150px" onchange="validateDistTO_(this);">
				<html:option value="-1">--Select Distributor--</html:option>
			</html:select></td>

		</tr>

		<TR>
			<TD class="text pLeft15"><STRONG> <FONT
				color="#000000"> <bean:message bundle="dp"
				key="label.DST.ProductLabel" /> </FONT> <FONT color="RED">*</FONT> </STRONG></TD>
			<TD class="txt"><html:select property="productIdNew"
				style="width:150px" onchange="getAvailableStock();">
				<html:option value="-1">--Select A Product--</html:option>
			</html:select></TD>

		</TR>

		<TR>
			<TD class="text pLeft15"><STRONG> <FONT color="#000000">
			<bean:message bundle="dp" key="label.DST.StockAvailLabel" /> </FONT> <FONT
				color="RED">*</FONT> </STRONG></TD>
			<TD><html:text property="intStockAvail"
				styleClass="fieldvalueGrayReadOnly" size="15" readonly="true" /></TD>
			<TD class="text pLeft15"><STRONG> <FONT color="#000000">
			<bean:message bundle="dp" key="label.DST.TransQtyLabel" /> </FONT> <FONT
				color="RED">*</FONT> </STRONG></TD>
			<TD><html:text property="intTransQty" styleClass="fieldvalue"
				onblur="validateTransQty()" size="15" maxlength="5" tabindex="4" />
			</TD>
		</TR>

		<TR>
			<TD align="right"></TD>
			<TD align="right"><input type="button" value="Add To Grid"
				class="buttonSimple" onclick="checkCombination();"></TD>
			<TD align="left"><input type="button" value="Reset"
				class="buttonSimple" onclick="resetPage();"></TD>

		</TR>
	</TABLE>
	</DIV>

	<DIV style="height: 300px; overflow: auto;" width="100%">
	<table width="90%" border="1" cellpadding="0" cellspacing="0"
		id="tableMSA" align="center" style="border-collapse: collapse;">
		<thead>
			<tr class="noScroll">
				<td width="25%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"><bean:message bundle="dp"
					key="label.DST.FromDistLabel" /></FONT></td>
				<td width="25%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"><bean:message bundle="dp"
					key="label.DST.ToDistLabel" /></FONT></td>
				<td width="20%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"><bean:message bundle="dp"
					key="label.DST.ProductLabel" /></FONT></td>
				<td width="10%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"><bean:message bundle="dp"
					key="label.DST.StockAvailLabel" /></FONT></td>
				<td width="10%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"><bean:message bundle="dp"
					key="label.DST.TransQtyLabel" /></FONT></td>
				<td width="10%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"><bean:message bundle="dp"
					key="label.DST.Remove" /></FONT></td>
			</tr>
		</thead>

		<tbody id="transQtyBody">

		</tbody>
	</table>
	</DIV>
	<br>
	<DIV>
	<table width="80%">
		<tr>
			<td width="100%" align="center"><input type="button"
				value="Submit" class="buttonSimple" onclick="transferDistStock();">


			</td>
		</tr>
	</table>
	</DIV>
	<html:hidden property="methodName" />
	
</html:form>
</body>
</html:html>
