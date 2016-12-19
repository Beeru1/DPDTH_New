	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.ibm.hbo.dto.DistStockDTO" %>
<%try{ %>   
<%@page import="com.ibm.dp.common.Constants"%>
<html>
<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript"></SCRIPT>

	
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>


<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<style>
 .odd{background-color: #FCE8E6;}
 .even{background-color: #FFFFFF;}
</style>
<script language="JavaScript">

function getProductList()
	{
		var circleId = document.getElementById("circleIdNew").value;
		var businessCategory = document.getElementById("businessCat").value;
		if(circleId=='-1')
		{
			alert("Select a Circle");
			document.getElementById("circleIdNew").focus();
			document.getElementById("businessCat").value = "-1";
			return false;
		}
		else if(businessCategory != '-1')
		{
			getAllProductsSingleCircle(businessCategory, circleId, 'productIdNew');
			
			document.getElementById("fromTsmId").value = "";
			//document.getElementById("toTsmId").value = "";
			document.getElementById("fromDistId").value = "-1";
			//document.getElementById("toDistId").value = "-1";
			document.getElementById("find").style.display ='none';
		}
	}
	
    function validateDist()
    {
     var fromDist = document.getElementById("fromDistId").value;
    // var toDist = document.getElementById("toDistId").value;
	    	
    }
    
	function OpenSerialWindow()
	{
	  var fromDist = document.getElementById("fromDistId").value;
	  var productId = document.getElementById("productIdNew").value;
	  var circleId = document.getElementById("circleIdNew").value;
	  var stockType=document.getElementById("stockType").value;
	  document.forms[0].alreadyProExist.value = false;
	  document.forms[0].tableProdId.value ="";
	  var url="viewStbList.do?methodName=viewSerialNoList&fromDist="+fromDist+"&productId="+productId+"&circleId="+circleId+"&stockType="+stockType;
	  window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
	
   }
   
   function OpenSerialWindow2(productId,newSelected)
	{
	  var fromDist = document.getElementById("fromDistId").value;
	  document.getElementById("jsArrTransfrStbs").value = newSelected;
	  var circleId = document.getElementById("circleIdNew").value;
	  var stockType=document.getElementById("stockType").value;
	  document.forms[0].alreadyProExist.value = true;
	  document.forms[0].tableProdId.value =productId;
	  
	  var url="viewStbList.do?methodName=viewSerialNoList&fromDist="+fromDist+"&productId="+productId+"&circleId="+circleId+"&stockType="+stockType;
	  window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
   }


	function getFromTsmName() 
	{

	    var levelId = document.getElementById("circleIdNew").value;
	    var busCat = document.getElementById("businessCat").value;
		if(levelId!=''){
			var url = "interSsdTranfrAdmin.do?methodName=getFromTsmList&levelId="+levelId+"&busCat="+busCat;
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
			req.onreadystatechange = getFromTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
	}
	
	
	function getFromTsmNameChange()
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
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select a TSM","");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			
			//Updated by Shilpa Khanna against defect Id : MASDB00179829 
			
			// Get Conditions.
			var selectObj2 = document.getElementById("fromDistId");
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("Select Distributor","-1");
			
			
			document.getElementById("availableProdQty").value ="0";
		}
	}	
	
	function getTsmName() 
	{

	    var levelId = document.getElementById("circleIdNew").value;
	    var busCat = document.getElementById("businessCat").value;
		if(levelId!=''){
			var url = "interSsdTranfrAdmin.do?methodName=getTsmList&levelId="+levelId+"&busCat="+busCat;
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
		}else{
			resetValues(1);

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
			var selectObj2 = document.getElementById("toTsmId");
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("Select a TSM","");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			
			//Updated by Shilpa Khanna against defect Id : MASDB00179829 
			
			
			// Get Conditions.
			var selectObj3 = document.getElementById("toDistId");
			selectObj3.options.length = 0;
			selectObj3.options[selectObj3.options.length] = new Option("Select Distributor","-1");
			
			document.getElementById("availableProdQty").value ="0";
		}
	}	
		
	function getFromDistName()
	{
	    var levelId = document.getElementById("fromTsmId").value;
	    var circleId = document.getElementById("circleIdNew").value;
	    var busCategory = document.getElementById("businessCat").value;
	
	 	if(levelId!=''){
			var url = "interSsdTranfrAdmin.do?methodName=getFromInactiveDistList&parentId="+levelId+"&circleId="+circleId+"&busCategory="+busCategory;
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
	
	 	if(levelId!=''){
			var url = "interSsdTranfrAdmin.do?methodName=getToDistList&parentId="+levelId+"&circleId="+circleId+"&busCategory="+busCategory;
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
	
	
	function availableQty(){
		var fromDist = document.getElementById("fromDistId").value;
	  	var productId = document.getElementById("productIdNew").value;
	  	var circleId = document.getElementById("circleIdNew").value;
	  	var stockType=document.getElementById("stockType").value;
	  

	    if(productId!="-1" && productId!=""){
			var url = "interSsdTranfrAdmin.do?methodName=getAvailableQty&fromDist="+fromDist+"&productId="+productId+"&circleId="+circleId+"&stockType="+stockType;
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
	
	function getavailableqty()
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
			var selectObj = document.getElementById("availableProdQty");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.value = optionValues[i].getAttribute("text");
			}
		}
	}
	
	function checkDuplicateProd(){
		var productId = document.getElementById("productIdNew").value;
		if(productId != "" && productId != "-1"){
			if(document.getElementById("fromDistId").value =="" || document.getElementById("fromDistId").value == "-1"){
				alert("Please Select From Distributor!!");
				document.getElementById("productIdNew").value="-1";
				document.getElementById("fromDistId").focus();
				return false;
			}
		}
		var table = document.getElementById("dataTable");
		var rowCount = table.rows.length;
		var i=0;
		var prodId ;
		var duplicateFlag ="true";
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
					//document.getElementById("toTsmId").disabled="true";
					document.getElementById("fromDistId").disabled="true";
				//	document.getElementById("toDistId").disabled="true";
					document.getElementById("availableProdQty").value="0";
					document.getElementById("transfrProdQty").value ="0"
					document.getElementById("businessCat").disabled="true";
					//document.getElementById("businessCat").value ="-1";
					document.getElementById("productIdNew").disabled="true";
					document.getElementById("productIdNew").value ="-1";
					document.getElementById("stockType").disabled="true";
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
		
		document.getElementById("circleIdNew").disabled=false;
		document.getElementById("fromTsmId").disabled=false;
		document.getElementById("fromDistId").disabled=false;
		//document.getElementById("toDistId").disabled=false;
		document.getElementById("businessCat").disabled=false;
		document.getElementById("stockType").disabled=false;
		document.getElementById("productIdNew").disabled=false;
		//document.getElementById("find").style.display ='block';
		alternate();
		
		
}
function insertData(){
	var table = document.getElementById("dataTable");
	var rowCount = table.rows.length;
	var i=0;
	var rowLength =0;
	var jsArrProductId = new Array();
	var jsArrSerialNo = new Array();
	var jsArrProductQty= new Array();
	var jsArrProductName= new Array();
	var fromDist = document.getElementById("fromDistId").value;
	var circleId = document.getElementById("circleIdNew").value;
	var courierAg=document.getElementById("courierAgency").value;
	var docketNum=document.getElementById("docketNum").value;
	var dcRemarks=document.getElementById("dcRemarks").value;
	var stockType=document.getElementById("stockType").value;
	//alert(stockType);
//	var toDist = document.getElementById("toDistId").value;
	//Updated Against Defect Id   :MASDB00179822 
	if(circleId == "-1"){
		alert("Please select One circle!");
		return false;
	}
	else if(document.getElementById("businessCat").value == "-1"){
		alert("Please select Business Category!");
		return false;
	}	
	else if(document.getElementById("fromTsmId").value == ""){
		alert("Please select From TSM!");
		return false;
	}
	else if(fromDist == "-1"){
		alert("Please select From Distributor!");
		return false;
	}
	else if(courierAg == null || courierAg == "")
	{
	 alert("Courier Agency Cannot be left blank");
		return false;
	}
	else if(docketNum == null || docketNum == "")
	{
	 alert("Docket Number Cannot be left blank");
		return false;
	}
	else if(dcRemarks == null || dcRemarks =="")
	{
	  alert("Dc Remarks Cannot be left blank");
		return false;
	}
	else if(dcRemarks.length>200)
	{
	 alert("Remarks cannont be greater than 200 chars");
	 return false;
	}
	
	document.getElementById("docketNumber").value=docketNum;
	document.getElementById("courierAge").value=courierAg;
	document.getElementById("dcRem").value=dcRemarks;
	
	while (i+1<rowCount)
		{
			var x = document.getElementById("dataTable").rows[i+1].cells;
				// this is for Hidden parameter
				
     			jsArrProductId[i] = x[0].getElementsByTagName("input")[0].value;
				jsArrSerialNo[i] = x[1].getElementsByTagName("input")[0].value.replace(/[A]/g,',');
				
			// work for all browser firefox
			
			if(document.all){
     			jsArrProductName[i] = x[0].getElementsByTagName("div")[0].value;
			} else {
    			jsArrProductName[i] = x[0].getElementsByTagName("div")[0].value.textContent;
			}
			
			if(document.all){
     			jsArrProductQty[i] = x[1].getElementsByTagName("div")[0].value;
			} else {
    			jsArrProductQty[i] = x[1].getElementsByTagName("div")[0].value.textContent;
			}
			
			
			
			i++;
			rowLength ++;
		}
	if(rowLength == 0){
	    alert("Please Add atleast one product!!");
	    return false;
	}else if(rowLength > 0){
	
	//alert(jsArrSerialNo);
	
	document.forms[0].jsArrprodId.value=jsArrProductId;
  	document.forms[0].jsArrTransfrStbs.value=jsArrSerialNo;
  	document.forms[0].jsArrTransfrProdQty.value=jsArrProductQty;
  	document.forms[0].jsArrprodName.value=jsArrProductName;
  	if(confirm("Are you sure to return the stock?")){
		document.forms[0].action="interSsdTranfrAdmin.do?methodName=insertDataInactive&fromDist="+fromDist+"&circleId="+circleId+"&stockType="+stockType;
		document.forms[0].method="POST";
		
		  document.getElementById("interssdButton").disabled = true;
		document.forms[0].submit();
		}else{
			return false;
		}
	}
	
	return true;
}


function insertDataReturnSecondary(){
	var table = document.getElementById("dataTable");
	var rowCount = table.rows.length;
	var i=0;
	var rowLength =0;
	var jsArrProductId = new Array();
	var jsArrSerialNo = new Array();
	var jsArrProductQty= new Array();
	var jsArrProductName= new Array();
	var fromDist = document.getElementById("fromDistId").value;
	var circleId = document.getElementById("circleIdNew").value;
	//var courierAg=document.getElementById("courierAgency").value;
	//var docketNum=document.getElementById("docketNum").value;
	//var dcRemarks=document.getElementById("dcRemarks").value;
	var stockType=document.getElementById("stockType").value;
	alert(stockType);
//	var toDist = document.getElementById("toDistId").value;
	//Updated Against Defect Id   :MASDB00179822 
	if(circleId == "-1"){
		alert("Please select One circle!");
		return false;
	}
	else if(document.getElementById("businessCat").value == "-1"){
		alert("Please select Business Category!");
		return false;
	}	
	else if(document.getElementById("fromTsmId").value == ""){
		alert("Please select From TSM!");
		return false;
	}
	else if(fromDist == "-1"){
		alert("Please select From Distributor!");
		return false;
	}
	
	
	
	while (i+1<rowCount)
		{
			var x = document.getElementById("dataTable").rows[i+1].cells;
				// this is for Hidden parameter
				
     			jsArrProductId[i] = x[0].getElementsByTagName("input")[0].value;
				jsArrSerialNo[i] = x[1].getElementsByTagName("input")[0].value.replace(/[A]/g,',');
				
			// work for all browser firefox
			
			if(document.all){
     			jsArrProductName[i] = x[0].getElementsByTagName("div")[0].value;
			} else {
    			jsArrProductName[i] = x[0].getElementsByTagName("div")[0].value.textContent;
			}
			
			if(document.all){
     			jsArrProductQty[i] = x[1].getElementsByTagName("div")[0].value;
			} else {
    			jsArrProductQty[i] = x[1].getElementsByTagName("div")[0].value.textContent;
			}
			
			
			
			i++;
			rowLength ++;
		}
	if(rowLength == 0){
	    alert("Please Add atleast one product!!");
	    return false;
	}else if(rowLength > 0){
	
	alert(jsArrSerialNo);
	
	document.forms[0].jsArrprodId.value=jsArrProductId;
  	document.forms[0].jsArrTransfrStbs.value=jsArrSerialNo;
  	document.forms[0].jsArrTransfrProdQty.value=jsArrProductQty;
  	document.forms[0].jsArrprodName.value=jsArrProductName;
  	if(confirm("Are you sure to return the stock to Distributor?")){
		document.forms[0].action="interSsdTranfrAdmin.do?methodName=insertDataInactive&fromDist="+fromDist+"&circleId="+circleId+"&stockType="+stockType;
		document.forms[0].method="POST";
		
		  document.getElementById("interssdButton").disabled = true;
		document.forms[0].submit();
		}else{
			return false;
		}
	}
	
	return true;
}

function resetValuesProduct()
{
	document.getElementById("productIdNew").value="-1";
	document.getElementById("availableProdQty").value="0";
	document.getElementById("find").style.display ='none';
	

}

function resetValuesProductValue(obj)
{
			
	document.getElementById("productIdNew").value="-1";
	document.getElementById("availableProdQty").value="0";
	document.getElementById("find").style.display ='none';
	
	if(obj=='3')
	{
	document.getElementById("dataRequired").style.display ='none';
	document.getElementById("returnStockToWare").style.display ='none';
	document.getElementById("returnStockToDist").style.display ='block';
	
	}
	else
	{
	document.getElementById("dataRequired").style.display ='block';
	document.getElementById("returnStockToWare").style.display ='block';
	document.getElementById("returnStockToDist").style.display ='none';
	}
}

function specialChar(obj)
{
  var fieldName;

  if (obj.name == "courierAgency")
  {
   fieldName = "Courier Agency";
  }
  if (obj.name == "docketNumber")
  {
   fieldName = "Docket Number";
  }
 if (obj.name == "dcRemarks")
  {
   fieldName = "DC Remarks";
  }
  
  var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~`_";

  for (var i = 0; i < obj.value.length; i++) 
    {
  	if (iChars.indexOf(obj.value.charAt(i)) != -1) 
    {
  	alert ("Special characters are not allowed for "+fieldName);
  	obj.value = obj.value.substring(0,i);
  	return false;
  	}
  }	
}
function alternate()
{
   if(document.getElementsByTagName){  
   var table = document.getElementById("dataTable");  
   var rows = table.getElementsByTagName("TR");  
   var cellsStyle = table.getElementsByTagName("TD");  
   for(i = 1; i < rows.length; i++){          
 //manipulate rows
     if(i % 2 == 0){
       rows[i].className = "even";
     }else{
       rows[i].className = "odd";
     }      
   }
   
    for(i = 1; i < cellsStyle.length; i++){          
 //manipulate cells
     cellsStyle[i].align = "center";
     if(i % 2 == 0){
       cellsStyle[i].className = "text";
      }else{
       cellsStyle[i].className = "text";
     }      
   }
   
   
 }
}

</script>



</head>
<body> 
<html:form action="/interSsdTranfrAdmin.do"  method="post" name="InterSsdTransferAdminBean" type="com.ibm.dp.beans.InterSsdTransferAdminBean" enctype="multipart/form-data" >
<html:hidden property="methodName" styleId="methodName"/>
<html:hidden property="docketNumber" styleId="docketNumber"/>
<html:hidden property="courierAge" styleId="courierAge"/>
<html:hidden property="dcRem" styleId="dcRem"/>

<html:hidden property="jsArrprodId" />
<html:hidden property="alreadyProExist" />
<html:hidden property="tableProdId" />
<html:hidden property="jsArrprodName" />
<html:hidden property="transfrProdQty" name="InterSsdTransferAdminBean"/>
<html:hidden property="jsArrTransfrProdQty" name="InterSsdTransferAdminBean"/>
<html:hidden property="jsArrTransfrStbs" name="InterSsdTransferAdminBean"/>
<html:hidden property="productName" name="InterSsdTransferAdminBean"/>

	<TABLE>
		<TBODY>
		<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/inactivePartnerStockTrans.jpg"
				width="505" height="22" alt="">
			</TD>
		</tr>
		<tr>
			<td colspan="6">
				<strong><font color="red" class="text" size="15px">
					<bean:write name="InterSsdTransferAdminBean" property="strMessage"/></font></strong>
			</td>
		</tr>
		</TBODY>
	</TABLE>
	<TABLE width="80%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
			<tr>
			<td colspan="6">
			<strong><font color="red" class="text" size="15px">
					<bean:write name="InterSsdTransferAdminBean" property="message"/>
					</font>
			</strong></td>
		</tr>
		<TR>
		<td colspan='2'>&nbsp;
		</td>
		</TR>
			<TR>
				<TD width="25%"></TD>
				<TD width="55%"></TD>
			</TR>
			<TR  id="circle">
				<TD class="txt"><bean:message bundle="dp" key="interSSD.Circle"/><font color="red">*</font></TD>
				<TD class="txt">
				<html:select property="circleIdNew" style="width:150px">
					<logic:notEmpty property="circleList" name="InterSsdTransferAdminBean" >
						<html:option value="-1">--Select Circle--</html:option>
						<bean:define id="circleList" name="InterSsdTransferAdminBean" property="circleList" />
						<html:options labelProperty="circleName" property="circleId" collection="circleList" />
					
					</logic:notEmpty>
				</html:select>
				</TD>
			</TR>
			
			<tr>
				<TD class="txt" width="222"><bean:message bundle="dp" key="interSSD.busCat"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="businessCat" style="width:150px" onchange="getProductList();getFromTsmName();">
					<html:option value="-1">--Select A Category--</html:option>
					<!-- 
					<bean:define id="busCatList" name="InterSsdTransferAdminBean" property="busCatList" />
					<html:options labelProperty="circleName" property="circleId" collection="busCatList" /> -->
					<html:optionsCollection name="InterSsdTransferAdminBean" property="busCatList" label="circleName" value="circleId"/>
				</html:select></td>
			</tr>
			
			<tr>
				<TD class="txt" width="222"><bean:message bundle="dp" key="interSSD.FromTsm"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="fromTsmId" onchange="getFromDistName();" style="width:150px">
						<html:option value="">--Select TSM--</html:option>
					</html:select>
				
				</td>
			 </tr>
			 <tr>
				<TD class="txt" width="222"><bean:message bundle="dp" key="interSSD.FromDist"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="fromDistId"  style="width:150px" onchange="resetValuesProduct();">
						<html:option value="-1">--Select Distributor--</html:option>
				</html:select>
				
				</td>
			 </tr>
			 
			 
			 <tr>
				<TD class="txt" width="222"><bean:message bundle="dp" key="interSSD.stockType"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<select name="stockType" id="stockType" style="width:150px" onchange="resetValuesProductValue(this.value);">
						<option value="1">Fresh Stock</option>
						<option value="2">Defective Stock</option>		
						<option value="3">Secondary Stock</option>
						<option value="4">Churn Stock</option>
						
				</select>
				
				</td>
			 </tr>
		<!--  	<tr> 
				<TD class="txt" width="222"><bean:message bundle="dp" key="interSSD.ToTsm"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="toTsmId" style="width:150px" onchange="getToDistName();">
					<html:option value="">--Select TSM--</html:option>
				</html:select></td>
			</tr>
			
			
			<tr>
				<TD class="txt" width="222"><bean:message bundle="dp" key="interSSD.ToDist"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="toDistId" style="width:150px" onchange="validateDist()">
					<html:option value="-1">--Select Distributor--</html:option>
				</html:select></td>
			</tr>
			-->
			
			
			<TR>
			<TD class="txt"><bean:message bundle="dp" key="interSSD.Product"/><font color="red">*</font></TD>
				<TD class="txt">
				<html:select property="productIdNew" style="width:150px"	onchange="checkDuplicateProd();"  >
						<html:option value="-1">--Select A Product--</html:option>
				</html:select> 
				
				<html:text name="InterSsdTransferAdminBean"  readonly="true"
					property="availableProdQty" readonly="true" style="width:80px;"  onfocus="resetting();"/>&nbsp;
				
				<div id="find" style="display:none;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()" id="viewSerialNos">Select STBs</a></div>
				</TD>
				
			</TR>
		
				<tr>
				<td colspan="2">
					<table id="dataTable" width="100%" border="1" cellpadding="2"
						cellspacing="0" align="center">
					<tr align="center" bgcolor="#CD0504"
							style="color:#ffffff !important;" class="noScroll">
							<td align="center" bgcolor="#CD0504" width="8%"><span
								class="white10heading mLeft5 mTop5"><strong>Product</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="8%"><span
								class="white10heading mLeft5 mTop5"><strong>Quantity</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="10%"><span
								class="white10heading mLeft5 mTop5"><strong>View Serial No.</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Remove</strong></span></td>
						</tr>
						
						</table>
					</td>
	</tr>
	
	
	
					
</TBODY>
</TABLE>
<br/><br/><br/><br/><br/><br/>


<TABLE id="dataRequired" style ="width:70%;" border="0" cellpadding="1" cellspacing="1"
					align="center" class ="border">
			  <tr>
			  <td class="txt">
			<bean:message bundle="view" key="DcCreation.courierAgency" /><font color="red">*</font>
			</td>
			
			<td>
			<html:text property="courierAgency" styleId="courierAgency" maxlength="100" value="" onkeyup="return specialChar(this);" />
			</td>
			<td class="txt">
			<bean:message bundle="view" key="DcCreation.DocketNumber" /><font color="red">*</font>
			</td>
			
			<td>
			<html:text property="docketNum" styleId="docketNum" maxlength="50" value="" onkeyup="return specialChar(this);" />
			</td>
			  </tr>
			  <TR>
				<TD class="txt"><bean:message bundle="hboView" key="returnFreshStock.DCRemarks"/><font color="red">*</font></TD>
				<TD class="txt"><html:textarea 
					property="dcRemarks" styleId="dcRemarks" style="width:230px" onkeypress="return maxlength();" onkeyup="return specialChar(this);" value=""/></TD>
			 
			  </TR>
			</table>

<br/><br/><br/><br/><br/><br/>

<TABLE id="returnStockToWare" width="60%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
	
	<tr>
				<td align="center">
				<input type="button" name="interssdButton" value="Return to Warehouse" onclick="insertData();">
				</td>
				
				
	</tr>
</TABLE>

<TABLE id="returnStockToDist" style="display:none;" width="60%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
	
	<tr>
				<td align="center">
				<input type="button" name="interssdButton" value="Return to Distributor" onclick="insertDataReturnSecondary();">
				</td>
				
				
	</tr>
</TABLE>


	<%
	String dc_no = (String) request.getAttribute("SDCNo");
	String transType=(String) request.getAttribute("transType");
	 %>	
<script>
	var dc_no= '<%=dc_no%>';
	var transType ='<%=transType%>';
 	if(dc_no!=null && dc_no != "null" ){
		
		var url="printDCDetails.do?methodName=printDC&Dc_No="+dc_no+"&TransType="+transType;
	    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");				
  	}
</script>

</html:form>

</body>

</html>
<%}catch(Exception e){
e.printStackTrace();
}%>