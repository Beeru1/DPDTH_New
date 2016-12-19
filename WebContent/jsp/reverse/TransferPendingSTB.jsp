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
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
div.remarks {
width:175px;
float:left;
display:inline;
} 

.widthzero{
width:0 ;
display:none;
}
</style>
<script language="JavaScript">

  
		function getFromTsmName() 
	{

	    var levelId = document.getElementById("fromCircleId").value;
		if(levelId!=''){
			var url = "transferPendingAction.do?methodName=getFromTsmList&levelId="+levelId;
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
		}
	}	
	
	
		function getToTsmName() 
	{

	    var levelId = document.getElementById("toCircleId").value;
		if(levelId!=''){
			var url = "transferPendingAction.do?methodName=getTsmList&levelId="+levelId;
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
			req.onreadystatechange = getToTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
	}
	
	
	function getToTsmNameChange()
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
		}
	}	
		
	function getFromDistName()
	{
		document.getElementById("successMessage").value="";
	    var levelId = document.getElementById("fromTsmId").value;
	    var circleId = document.getElementById("fromCircleId").value;
	
	 	if(levelId!=''){
			var url = "transferPendingAction.do?methodName=getFromDistList&parentId="+levelId+"&circleId="+circleId;
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
	    var circleId = document.getElementById("toCircleId").value;
	
	 	if(levelId!=''){
			var url = "transferPendingAction.do?methodName=getToDistList&parentId="+levelId+"&circleId="+circleId;
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


function getCollectionDetails(){	
	document.forms[0].successMessage.value = ''; 
	document.forms[0].action="transferPendingAction.do?methodName=getCollectionData";
		

}

function showTable()
	{
	var distId = document.getElementById("fromDistId").value;
	var collectionId = document.getElementById("collectionId").value ; 
	if(collectionId != "" && collectionId != "-1"){
		if(distId == "" || distId == "-1" ){
			alert("Please Select From Distributor!");
			document.getElementById("collectionId").value = "-1";
			return false;
		}
		
	   	var url = document.forms[0].action="transferPendingAction.do?methodName=getCollectionData&distId="+distId+"&collectionId="+collectionId;
	
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
			req.onreadystatechange = getTableDataAJAX;
			req.open("POST",url,false);
			req.send();
		}else{
			var rowCount2 = document.getElementById("dataTableNew").rows.length;
			document.getElementById("stbCount").style.display="none";
			document.getElementById("stCnt").style.display="none";
			if(rowCount2 >0){
				var rowdelete =rowCount2-1;			
				 for(var i=0;i< rowCount2-1;i++){
					document.getElementById("dataTableNew").deleteRow(rowdelete);
					rowdelete --;
					}
				
				}	
		}
		
	}
	
	function getTableDataAJAX()
	{
		if (req.readyState==4 || req.readyState=="complete") 
		
	  	{	
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			var	optionValues ="";
			// Get Conditions.
		optionValues = xmldoc.getElementsByTagName("option");
		//	var selectObj = document.getElementById("toDistId");
		var selectObj = document.getElementById("dataTableNew");
		var rowCount2 = document.getElementById("dataTableNew").rows.length;
		
		if(rowCount2 >0){
			var rowdelete =rowCount2-1;			
			 for(var i=0;i< rowCount2-1;i++){
				document.getElementById("dataTableNew").deleteRow(rowdelete);
				rowdelete --;
				}
			
			}			
		
		document.getElementById("stbCount").style.display="inline";
		document.getElementById("stCnt").style.display="inline";
		var countStb=optionValues.length ;	
		document.getElementById("stbCount").innerHTML = "<div value=\"" + countStb +  "\"> <strong><font color='#000000'>" + countStb + "</font></strong></div>";
		//	selectObj.options.length = 0;
		//	selectObj.options[selectObj.options.length] = new Option("Select Distributor","-1");
			for (var intLoop=0; intLoop < optionValues.length;intLoop++) 
			 {
		        	var collectionType = optionValues[intLoop].getAttribute("CollectionType");
		        	var customerId = optionValues[intLoop].getAttribute("customerId");
					var serialNo = optionValues[intLoop].getAttribute("SerialNo");					
					var product = optionValues[intLoop].getAttribute("Product");
					var serialNoNew = optionValues[intLoop].getAttribute("SerialNoNew");					
					var productNew = optionValues[intLoop].getAttribute("ProductNew");
					var invenChangeDate = optionValues[intLoop].getAttribute("InvenChangeDate");
					var ageing = optionValues[intLoop].getAttribute("Ageing");
					var defectType = optionValues[intLoop].getAttribute("DefectType");
					var collectionDate = optionValues[intLoop].getAttribute("CollectionDate");
					
					var productId = optionValues[intLoop].getAttribute("ProductId");
					var collectionId = optionValues[intLoop].getAttribute("CollectionId");
					var defectId = optionValues[intLoop].getAttribute("DefectId");
					var createdBy = optionValues[intLoop].getAttribute("CreatedBy");
					
					var reqId = optionValues[intLoop].getAttribute("ReqId");
					var flag = optionValues[intLoop].getAttribute("Flag");
											
		             rowCount = selectObj.rows.length;
					 row = selectObj.insertRow(rowCount);
						
					var cell1 = row.insertCell(0);
					cell1.innerHTML = "<div	 value=\"" + collectionType +  "\">&nbsp;" + collectionType + "</div>";
				
					var cell2 = row.insertCell(1);
					cell2.innerHTML = "<div	 value=\"" + customerId +  "\">&nbsp;" + customerId + "</div>";
					
					var cell3 = row.insertCell(2);
					cell3.innerHTML = "<div  value=\"" + serialNo +  "\">&nbsp;" + serialNo + "</div>";
											
					var cell4 = row.insertCell(3);
					cell4.innerHTML = "<div   value=\"" + product +  "\">&nbsp;" + product + "</div>";		
					
					var cell5 = row.insertCell(4);
					cell5.innerHTML = "<div  value=\"" + serialNoNew +  "\">&nbsp;" + serialNoNew + "</div>";
											
					var cell6 = row.insertCell(5);
					cell6.innerHTML = "<div   value=\"" + productNew +  "\">&nbsp;" + productNew + "</div>";		
					
					
					var cell7 = row.insertCell(6);
					cell7.innerHTML = "<div   value=\"" + invenChangeDate +  "\">&nbsp;" + invenChangeDate + "</div>";	
					
					var cell8 = row.insertCell(7);
					cell8.innerHTML = "<div  value=\"" + ageing +  "\">&nbsp;" + ageing + "</div>";	
					
					var cell9 = row.insertCell(8);
					cell9.innerHTML = "<div  value=\"" + defectType +  "\">&nbsp;" + defectType + "</div>";
						
					var cell10 = row.insertCell(9);
				
					cell10.innerHTML = "<div  value=\"" + collectionDate +  "\">&nbsp;" +  collectionDate + "</div>";	
													
																					    
					var cell11 = row.insertCell(10);
					var inputCell5 = document.createElement('input');	
					inputCell5.type = "checkbox";
					inputCell5.name = "checkSelect";
					inputCell5.value = serialNo+"#"+productId+"#"+reqId+"#"+flag+"#"+invenChangeDate+"#"+collectionId+"#"+defectId+"#"+collectionDate+"#"+collectionType+"#"+createdBy+"#"+defectType+"#"+ageing+"#"+product;
					inputCell5.id = "checkSelect";		
					cell11.setAttribute("align", "center");				
					//inputCell2.setAttribute("check", check);
					cell11.appendChild(inputCell5);
																			            
		            }
		}
		
	}	
	   
   function transferStock() 
	{
	var fromDist = document.getElementById("fromDistId").value;
	var toDist = document.getElementById("toDistId").value;
	var fromDistName = document.getElementById('fromDistId').options[document.getElementById("fromDistId").selectedIndex].text;
	var toDistName = document.getElementById('toDistId').options[document.getElementById("toDistId").selectedIndex].text;
	var collectionId = document.getElementById("collectionId").value;
 	
	if(fromDist=="" || fromDist==-1){
	alert("Please Select From Distributor");
	return false;		
	}
	if(toDist=="" || toDist==-1){	
	alert("Please Select To Distributor");
	return false;		
	}

	if(fromDist == toDist) {
		alert('From and To Distributor should not be same');
		return false;
	}

	if(collectionId == "" || collectionId == '-1'){
  	    alert ("Please select Collection Type.");
	   	document.getElementById("collectionId").focus();
	  	return false;
 	}
	
	//*****************************************
	
	var table = document.getElementById("dataTable");
	var rowCount = table.rows.length;
	if(rowCount <2 ){
		 alert("Please Add atleast One Record!!")
		return false;
	}
	var j=0;
	var selectedSerialNo="";
	for (count = 1; count < rowCount; count++)
	{
		var x = table.rows[count].cells;
		
		if(document.all){
     		var jsCheckBoxValue= x[11].getElementsByTagName("div")[0].value;
		} else {
    		var jsCheckBoxValue = x[11].getElementsByTagName("div")[0].value.textContent;
		}
		if(count==rowCount-1){
			selectedSerialNo+= jsCheckBoxValue;
		}else{				
			selectedSerialNo+= jsCheckBoxValue + ";";
		}				
		j++;
	}
	//****************************************
  document.getElementById("selectedSerialNo").value =selectedSerialNo;
  document.forms[0].action="transferPendingAction.do?methodName=transferStock&fromDistName="+fromDistName+"&toDistName="+toDistName;
  document.forms[0].method="POST";
  document.getElementById("transferBut").disabled = true;
  document.forms[0].submit();
}
function setDefauult(){
	document.getElementById("collectionId").value = "-1";
}


function remove(rowCount2,jsCollectionId)
{
		var collID = document.getElementById("collectionId").value;
		var table = document.getElementById("dataTable");
	//get the deleted row data
		var x = table.rows[rowCount2].cells;
		var collectionType,productType;
		var todo=true; //Niki
	if(jsCollectionId == collID){
		if(document.all){
   			var jsCollectionType = x[0].getElementsByTagName("div")[0].value;
   			collectionType = x[0].getElementsByTagName("div")[0].value;
   		} else {
			var jsCollectionType = x[0].getElementsByTagName("div")[0].value.textContent;
			collectionType  = x[0].getElementsByTagName("div")[0].value.textContent;
		}
		
		if(document.all){
    		if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
		{    
    		var customerId= x[1].getElementsByTagName("div")[0].value;}
		} else {
   			if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
    		var customerId = x[1].getElementsByTagName("div")[0].value.textContent;
    		}
		}
		if(document.all){
   			var jsDefectSrNo = x[2].getElementsByTagName("div")[0].value;
   			//Neetika on 16 Sept
   					var table22 = document.getElementById("dataTableNew");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
							//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[2].getElementsByTagName("div")[0].value;
	   				if(jsDefectSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[2].getElementsByTagName("div")[0].value.textContent;
	  				if(jsDefectSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					}
					
					}
		} 
		else {
  			var jsDefectSrNo = x[2].getElementsByTagName("div")[0].value.textContent;
  					var table22 = document.getElementById("dataTableNew");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
							//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[2].getElementsByTagName("div")[0].value;
	   				if(jsDefectSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[2].getElementsByTagName("div")[0].value.textContent;
	  				if(jsDefectSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					}
					
					}
		}
		
		if(document.all){
   			var jsDefectProductType = x[3].getElementsByTagName("div")[0].value;
   			productType = x[3].getElementsByTagName("div")[0].value;
   		} else {
  			var jsDefectProductType = x[3].getElementsByTagName("div")[0].value.textContent;
  			productType = x[3].getElementsByTagName("div")[0].value.textContent;
  		}
		if(document.all){
   			var jsNewSrNo = x[4].getElementsByTagName("div")[0].value;
   		
   			/*	//alert("jsNewSrNo"+jsNewSrNo);
     				var table22 = document.getElementById("dataTableNew");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
							//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[4].getElementsByTagName("div")[0].value;
	   				if(jsNewSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[4].getElementsByTagName("div")[0].value.textContent;
	  				if(jsNewSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					}
					
					}*/
		} else {
  			var jsNewSrNo = x[4].getElementsByTagName("div")[0].value.textContent;
  			/*	//alert("jsNewSrNo"+jsNewSrNo);
     				var table22 = document.getElementById("dataTableNew");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
							//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[4].getElementsByTagName("div")[0].value;
	   				if(jsNewSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[4].getElementsByTagName("div")[0].value.textContent;
	  				if(jsNewSrNo==jsNewSrNotable2)
	   				{
	   				todo=false;
	   				
	   				}
					}
					
					}*/
		}
		
		if(document.all){
   			var jsNewProductType = x[5].getElementsByTagName("div")[0].value;
   			
		} else {
  			var jsNewProductType = x[5].getElementsByTagName("div")[0].value.textContent;
  		}
		if(document.all){
   			var jsInventoryChangeDate = x[6].getElementsByTagName("div")[0].value;
		} else {
  			var jsInventoryChangeDate = x[6].getElementsByTagName("div")[0].value.textContent;
		}

		if(document.all){
   			var ageing= x[7].getElementsByTagName("div")[0].value;
		} else {
  			var ageing = x[7].getElementsByTagName("div")[0].value.textContent;
		}
		if(document.all){
     		if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
			{    
     			var defectType=x[8].getElementsByTagName("div")[0].innerText;
     		}
		} else {
    		if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
			{    
     			var defectType=x[8].getElementsByTagName("div")[0].innerText;
     		}
		}
				
		if(document.all){
   			var jsCollectionDate = x[9].getElementsByTagName("div")[0].value;
		} else {
  			var jsCollectionDate = x[9].getElementsByTagName("div")[0].value.textContent;
		}
		
		if(document.all){
     		var jsCheckBoxValue= x[11].getElementsByTagName("div")[0].value;
		} else {
    		var jsCheckBoxValue = x[11].getElementsByTagName("div")[0].value.textContent;
		}
				
			//var valuesArray = jsCheckBoxValue.split("#");
			var table2 = document.getElementById("dataTableNew");
			var rowValue =table2.rows.length;
		   	var row2 = table2.insertRow(rowValue);
			if(todo==true)
			{
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div	 value=\"" + jsCollectionType +  "\">&nbsp;" + jsCollectionType + "</div>";
		
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div	 value=\"" + customerId +  "\">&nbsp;" + customerId + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div  value=\"" + jsDefectSrNo +  "\">&nbsp;" + jsDefectSrNo + "</div>";
									
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div   value=\"" + jsDefectProductType +  "\">&nbsp;" + jsDefectProductType + "</div>";		
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div  value=\"" + jsNewSrNo +  "\">&nbsp;" + jsNewSrNo + "</div>";
									
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div   value=\"" + jsNewProductType +  "\">&nbsp;" + jsNewProductType + "</div>";		
			
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div   value=\"" + jsInventoryChangeDate +  "\">&nbsp;" + jsInventoryChangeDate + "</div>";	
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div  value=\"" + ageing +  "\">&nbsp;" + ageing + "</div>";	
			
			var cell9 = row2.insertCell(8);
			cell9.innerHTML = "<div  value=\"" + defectType +  "\">&nbsp;" + defectType + "</div>";
				
			var cell10 = row2.insertCell(9);
		
			cell10.innerHTML = "<div  value=\"" + jsCollectionDate +  "\">&nbsp;" +  jsCollectionDate + "</div>";	
											
																			    
			var cell11 = row2.insertCell(10);
			var inputCell5 = document.createElement('input');	
			inputCell5.type = "checkbox";
			inputCell5.name = "checkSelect";
			inputCell5.value = jsCheckBoxValue;
			// jsDefectSrNo+"#"+valuesArray[1]+"#"+valuesArray[2]+"#"+valuesArray[3]+"#"+valuesArray[4]+"#"+valuesArray[5]+"#"+valuesArray[6]+"#"+valuesArray[7]+"#"+collectionType+"#"+createdBy+"#"+defectType+"#"+ageing+"#"+product;
			inputCell5.id = "checkSelect";		
			cell11.setAttribute("align", "center");				
			//inputCell2.setAttribute("check", check);
			cell11.appendChild(inputCell5);
			}
		
	}
	//to delete row from 2nd table
	collectionType=x[0].getElementsByTagName("div")[0].value;
	productType = x[3].getElementsByTagName("div")[0].value;
	deleteRowDataTable3(collectionType,productType);
 	table.deleteRow(rowCount2);	
 	var rowValue =table.rows.length;
}
function callTableAdded(){
	var collectionId = document.getElementById("collectionId").value;
 	if(collectionId == '-1'){
  	    alert ("Please select Collection Type.");
	   	document.getElementById("collectionId").focus();
	  	return false;
 }
	try {
			var table = document.getElementById("dataTableNew");
			var rowCount = table.rows.length;
			var j=0;
			if(rowCount > 1){
				var i=0;
				var jsCollectionType = new Array();
				var jsCollectIonId = new Array();
				var jsDefectSrNo = new Array();
				var jsDefectProductType = new Array();
				var jsDefectProductId = new Array();
				var jsNewSrNo = new Array();
				var jsNewProductType = new Array();
				var jsNewProductId = new Array();
				var jsInventoryChangeDate = new Array();
				var jsCollectionDate = new Array();
				var jsReqId = new Array();
				var ageing =new Array();
				var jsCustomerId=new Array();
				var strArraySelected = new Array();
				var str=new Array();
				var defectType =new Array();
				var defectId =new Array();
				var defValue;
				var message ="";
				var selectedCheckBoxVal = "";
				
				strArraySelected = new Array();
				defValue="";
				var count = 0;
	if(document.forms[0].checkSelect.length != undefined)
	{
		
		for (count = 0; count < document.forms[0].checkSelect.length; count++)
		{
			if(document.forms[0].checkSelect[count].checked==true)
			{
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
		
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTableNew").rows[rownum-1].cells;
			selectedCheckBoxVal = "";
			selectedCheckBoxVal = document.forms[0].checkSelect[count].value
			var dataDetailsArr = selectedCheckBoxVal.split("#");	
			
			if(document.all){
	     		 if(typeof( x[0].getElementsByTagName("div")[0])!="undefined")
				{    
	     			jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value;
	     		}
			} else {
				 if(typeof( x[0].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
    			}
			}	
			
			if(document.all){
				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsCustomerId[i] = x[1].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsCustomerId[i] = x[1].getElementsByTagName("div")[0].value.textContent;
    			}
			}
			
			if(document.all){
				 if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value;
     				//Neetika on 16 Sept
     				var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
						//	alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[2].getElementsByTagName("div")[0].value;
	   				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[2].getElementsByTagName("div")[0].value.textContent;
	  				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}
     			}
			} else {
				 if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    				//Added by neetika on 16 sept
    				var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
					//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[2].getElementsByTagName("div")[0].value;
	   				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[2].getElementsByTagName("div")[0].value.textContent;
	  				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}
    				
    			}
			}
				
			if(document.all){
				 if(typeof( x[3].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value;
     			}
			} else {
    			if(typeof( x[3].getElementsByTagName("div")[0])!="undefined"){  
   				 	jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value.textContent;
   				}
			}
				
			if(document.all){
				 if(typeof( x[4].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value; // Niki]
     				/*//alert("jsNewSrNo[i]"+jsNewSrNo[i]);
     				var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
						//	alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[4].getElementsByTagName("div")[0].value;
	   				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[4].getElementsByTagName("div")[0].value.textContent;
	  				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}*/
     			}
			} else {
				 if(typeof( x[4].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value.textContent; // Niki
    				//alert("jsNewSrNo[i]"+jsNewSrNo[i]);
     				/*var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
					//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[4].getElementsByTagName("div")[0].value;
	   				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[4].getElementsByTagName("div")[0].value.textContent;
	  				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}*/
    			}
			}
				
			if(document.all){
				 if(typeof( x[5].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsNewProductType[i] = x[5].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[5].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsNewProductType[i] = x[5].getElementsByTagName("div")[0].value.textContent;
    			}
			}
			//************************************************
			loopDataTable3(jsCollectionType[i],jsDefectProductType[i]);
			//*************************************************
			if(document.all){
				 if(typeof( x[6].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsInventoryChangeDate[i] = x[6].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[6].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsInventoryChangeDate[i] = x[6].getElementsByTagName("div")[0].value.textContent;
				}
			}
			if(document.all){
				 if(typeof( x[7].getElementsByTagName("div")[0])!="undefined")
				{  
     				ageing[i] = x[7].getElementsByTagName("div")[0].value;
				}	
			} else {
			 	if(typeof( x[7].getElementsByTagName("div")[0])!="undefined")
				{  
    				ageing[i] = x[7].getElementsByTagName("div")[0].value.textContent;
				}	
			}
			
			if(document.all){
				 if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
				{  
     				defectType[i] = x[8].getElementsByTagName("div")[0].value;
				}	
			} else {
			 	if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
				{  
    				defectType[i] = x[8].getElementsByTagName("div")[0].value.textContent;
				}	
			}
			
		
			if(document.all){
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsCollectionDate[i] = x[9].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsCollectionDate[i] = x[9].getElementsByTagName("div")[0].value.textContent;
				}
			}
			var table2 = document.getElementById("dataTable");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsCustomerId[i] +  "\">" + jsCustomerId[i] + "</div>";
			
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsDefectSrNo[i] +  "\">" + jsDefectSrNo[i] + "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsDefectProductType[i] +  "\">" + jsDefectProductType[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsNewSrNo[i] +  "\">" + jsNewSrNo[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsNewProductType[i] +  "\">" + jsNewProductType[i] + "</div>";
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div value=\"" + jsInventoryChangeDate[i] +  "\">" + jsInventoryChangeDate[i] + "</div>";
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div value=\"" + ageing[i] +  "\">" + ageing[i] + "</div>";
			
			var cell9 = row2.insertCell(8);
			cell9.innerHTML = "<div value=\"" + defectType[i] +  "\">" + defectType[i] + "</div>"; 
						
			var cell10 = row2.insertCell(9);
			cell10.innerHTML = "<div value=\"" + jsCollectionDate[i] +  "\">" + jsCollectionDate[i] + "</div>"; 
			
			
			var cell11 = row2.insertCell(10);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			str[j] = j+1;
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex,dataDetailsArr[5]);};
			cell11.appendChild(image);
			
			//var cell12 = document.createElement("td");    
			var cell12 =  row2.insertCell(11);
			cell12.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + selectedCheckBoxVal +  "\">" + selectedCheckBoxVal + "</div>";
			cell12.setAttribute('class','widthzero'); 
			cell12.setAttribute('display','none'); 
			cell12.setAttribute('width','0'); 
			cell12.setAttribute('style','display:none'); 
			cell12.style.display = 'none';
			//row2.appendChild(cell12);
			}
		}
	}else 	if(document.forms[0].checkSelect.checked==true)
			{ //alert("in else if");	
			var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
		
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTableNew").rows[rownum-1].cells;
			selectedCheckBoxVal = "";
			selectedCheckBoxVal = document.forms[0].checkSelect.value;
			var dataDetailsArr = selectedCheckBoxVal.split("#");	
			if(document.all){
	     		 if(typeof( x[0].getElementsByTagName("div")[0])!="undefined")
				{    
	     			jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value;
	     		}
			} else {
				 if(typeof( x[0].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
    			}
			}	
			if(document.all){
				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsCustomerId[i] = x[1].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsCustomerId[i] = x[1].getElementsByTagName("div")[0].value.textContent;
    			}
			}
			if(document.all){
				 if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value;
     				
     				var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
					//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[2].getElementsByTagName("div")[0].value;
	   				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[2].getElementsByTagName("div")[0].value.textContent;
	  				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}
     				
     			}
			} else {
				 if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    				
    				var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
					//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[2].getElementsByTagName("div")[0].value;
	   				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[2].getElementsByTagName("div")[0].value.textContent;
	  				if(jsDefectSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsDefectSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}
    			}
			}
				
			if(document.all){
				 if(typeof( x[3].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value;
     			}
			} else {
    			if(typeof( x[3].getElementsByTagName("div")[0])!="undefined"){  
   				 	jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value.textContent;
   				}
			}
			
			if(document.all){
				 if(typeof( x[4].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value;
     				//alert("jsNewSrNo[i]"+jsNewSrNo[i]);
     				/*var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
					//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[4].getElementsByTagName("div")[0].value;
	   				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[4].getElementsByTagName("div")[0].value.textContent;
	  				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}*/
     			}
			} else {
				 if(typeof( x[4].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value.textContent;
    				//alert("jsNewSrNo[i]"+jsNewSrNo[i]);
     				/*var table22 = document.getElementById("dataTable");
					var rowValue2 =table22.rows.length;
					//alert("rowValue2"+rowValue2);
					for(var j=1;j<rowValue2;j++){
					//alert("j"+j);
					var jsNewSrNotable2;
					var xtable2 = table22.rows[j].cells;
					if(document.all){
	   				jsNewSrNotable2= xtable2[4].getElementsByTagName("div")[0].value;
	   				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+": This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					} else {
	  				jsNewSrNotable2 = xtable2[4].getElementsByTagName("div")[0].value.textContent;
	  				if(jsNewSrNo[i]==jsNewSrNotable2)
	   				{
	   				alert(jsNewSrNo[i]+" : This Serial Number has already been selected. Please uncheck it");
	   				return false;
	   				}
					}
					//alert("jsNewSrNotable2"+jsNewSrNotable2);
					}*/
    			}
			}
				
			if(document.all){
				 if(typeof( x[5].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsNewProductType[i] = x[5].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[5].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsNewProductType[i] = x[5].getElementsByTagName("div")[0].value.textContent;
    			}
			}
			
			//************************************************
			loopDataTable3(jsCollectionType[i],jsDefectProductType[i]);
			//*************************************************
			if(document.all){
				 if(typeof( x[6].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsInventoryChangeDate[i] = x[6].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[6].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsInventoryChangeDate[i] = x[6].getElementsByTagName("div")[0].value.textContent;
				}
			}
			if(document.all){
				 if(typeof( x[7].getElementsByTagName("div")[0])!="undefined")
				{  
     				ageing[i] = x[7].getElementsByTagName("div")[0].value;
				}	
			} else {
			 	if(typeof( x[7].getElementsByTagName("div")[0])!="undefined")
				{  
    				ageing[i] = x[7].getElementsByTagName("div")[0].value.textContent;
				}	
			}
			
			if(document.all){
				 if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
				{  
     				defectType[i] = x[8].getElementsByTagName("div")[0].value;
				}	
			} else {
			 	if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
				{  
    				defectType[i] = x[8].getElementsByTagName("div")[0].value.textContent;
				}	
			}
			
		
			if(document.all){
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
				{  
     				jsCollectionDate[i] = x[9].getElementsByTagName("div")[0].value;
     			}
			} else {
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
				{  
    				jsCollectionDate[i] = x[9].getElementsByTagName("div")[0].value.textContent;
				}
			}
			
			var table2 = document.getElementById("dataTable");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsCustomerId[i] +  "\">" + jsCustomerId[i] + "</div>";
			
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsDefectSrNo[i] +  "\">" + jsDefectSrNo[i] + "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsDefectProductType[i] +  "\">" + jsDefectProductType[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsNewSrNo[i] +  "\">" + jsNewSrNo[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsNewProductType[i] +  "\">" + jsNewProductType[i] + "</div>";
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div value=\"" + jsInventoryChangeDate[i] +  "\">" + jsInventoryChangeDate[i] + "</div>";
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div value=\"" + ageing[i] +  "\">" + ageing[i] + "</div>";
			
			var cell9 = row2.insertCell(8);
			cell9.innerHTML = "<div value=\"" + defectType[i] +  "\">" + defectType[i] + "</div>"; 
						
			var cell10 = row2.insertCell(9);
			cell10.innerHTML = "<div value=\"" + jsCollectionDate[i] +  "\">" + jsCollectionDate[i] + "</div>"; 
			
			
			var cell11 = row2.insertCell(10);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			str[j] = j+1;
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex,dataDetailsArr[5]);};
			cell11.appendChild(image);
			
			//var cell12 = document.createElement("td");    
			var cell12 =  row2.insertCell(11);
			cell12.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + selectedCheckBoxVal +  "\">" + selectedCheckBoxVal + "</div>";
			cell12.setAttribute('class','widthzero'); 
			cell12.setAttribute('display','none'); 
			cell12.setAttribute('width','0'); 
			cell12.setAttribute('style','display:none'); 
			cell12.style.display = 'none';
			//row2.appendChild(cell12);
		}
		
			
		if(strArraySelected.length>0){
			for(i=0;i<strArraySelected.length;i++){
			var rownum = strArraySelected[i];
			document.getElementById("dataTableNew").deleteRow(rownum-i);
			}
		}
			
	}
			
	if(j==0){
       	alert("Please Select atleast one row.");
       	return false;
 	}
			
} 
catch(e) {
				alert(e);
		 }
}

function loopDataTable3(collectionType,productType){
	var table = document.getElementById("dataTable3");
	var count=0;
	var newInsert=0;
	if(table.rows.length==1){
	//alert("table.rows.length."+table.rows.length); // going into this when adding first tym
				var rowValue =table.rows.length;
				var row2 = table.insertRow(rowValue);
				var cell1 = row2.insertCell(0);
				cell1.innerHTML = "<div value=\"" + collectionType +  "\">" + collectionType + "</div>";
				
				var cell2 = row2.insertCell(1);
				cell2.innerHTML = "<div value=\"" + productType +  "\">" + productType + "</div>";
				
				var cell3 = row2.insertCell(2);
				cell3.innerHTML = "<div value=\"" + 1 +  "\">" + 1 + "</div>";
				newInsert=1;
		}else{
		for (var i = 1, row; row = table.rows[i]; i++) {
		
	   		if((row.cells[0].getElementsByTagName("div")[0].value==collectionType) && (row.cells[1].getElementsByTagName("div")[0].value==productType) ){
	   			count=parseInt(row.cells[2].getElementsByTagName("div")[0].value)+1;
	   			row.cells[2].innerHTML = "<div value=\"" + count +  "\">" + count + "</div>";
	   			newInsert=1;
	   			break;
	   		}
		}
		}
		
		if(newInsert==0){
	
				var rowValue =table.rows.length;
				var row2 = table.insertRow(rowValue);
				var cell1 = row2.insertCell(0);
				cell1.innerHTML = "<div value=\"" + collectionType +  "\">" + collectionType + "</div>";
				
				var cell2 = row2.insertCell(1);
				cell2.innerHTML = "<div value=\"" + productType +  "\">" + productType + "</div>";
				
				var cell3 = row2.insertCell(2);
				cell3.innerHTML = "<div value=\"" + 1 +  "\">" + 1 + "</div>";
		}
	
	}
function deleteRowDataTable3(collectionType,productType){
	var table = document.getElementById("dataTable3");
	var count=0;
	var newInsert=0;
	for (var i = 1, row; row = table.rows[i]; i++) {
		if((row.cells[0].getElementsByTagName("div")[0].value==collectionType) && (row.cells[1].getElementsByTagName("div")[0].value==productType) ){
   			count=parseInt(row.cells[2].getElementsByTagName("div")[0].value);
   			if(count==1)
   			{
   				document.getElementById("dataTable3").deleteRow(i);
   			}
   			else
   			{
   				count=count-1;
   				row.cells[2].innerHTML = "<div value=\"" + count +  "\">" + count + "</div>";
   			}
   			break;
   			}
		}
	}
</script>



</head>
<BODY>
<html:form action="/transferPendingAction.do"  method="post" enctype="multipart/form-data" >
<html:hidden property="methodName" styleId="methodName"/>
<html:hidden property="selectedSerialNo"/>
<html:hidden property="successMessage" styleId="successMessage"/>
	<TABLE>
		<TBODY>
		<tr>			
			<TD>			
			<!-- Page title start -->
								<IMG src="${pageContext.request.contextPath}/images/Pending_List_Transfer.jpg"
									width="544" height="25" alt="">
					<!-- Page title end -->
			</TD>					
			<!-- Page title end -->	
		</tr>
		</TBODY>
	</TABLE>
	<TABLE width="100%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
		<TR>
				<TD colspan="4">
				<logic:notEmpty property="successMessage" name="TransferPendingSTBBean">
					<BR>
						<strong><font color="red"><bean:write property="successMessage"  name="TransferPendingSTBBean"/></font></strong>
					<BR>
				</logic:notEmpty>
				</TD> 
			</TR>
		<TR>
		<td colspan='2'>&nbsp;
		</td>
		</TR>
			<TR>
				<TD width="25%"></TD>
				<TD width="55%"></TD>
			</TR>
			<TR  id="circle">
				<TD class="txt" width="225"><bean:message bundle="dp" key="label.from.Circle"/><font color="red">*</font></TD>
				<TD class="txt" width="250">
				<html:select property="fromCircleId" onchange="getFromTsmName()" style="width:150px">
					<logic:notEmpty property="circleList" name="TransferPendingSTBBean" >
						<html:option value="-1">--Select Circle--</html:option>
						<bean:define id="circleList" name="TransferPendingSTBBean" property="circleList" />
						<html:options labelProperty="circleName" property="circleId" collection="circleList" />
					
					</logic:notEmpty>
				</html:select>
				</TD>
				<TD class="txt" width="225"><bean:message bundle="dp" key="label.to.Circle"/><font color="red">*</font></TD>
				<TD class="txt" width="250">
				<html:select property="toCircleId" onchange="getToTsmName()" style="width:150px">
					<logic:notEmpty property="circleList" name="TransferPendingSTBBean" >
						<html:option value="-1">--Select Circle--</html:option>
						<bean:define id="circleList" name="TransferPendingSTBBean" property="circleList" />
						<html:options labelProperty="circleName" property="circleId" collection="circleList" />
					
					</logic:notEmpty>
				</html:select>
				</TD>				
			</TR>
		
			<tr> 
				<TD class="txt"><bean:message bundle="dp" key="interSSD.FromTsm"/><font color="red">*</font></td>
				<TD class="txt">
				<html:select property="fromTsmId" onchange="getFromDistName();" style="width:150px">
						<html:option value="">--Select TSM--</html:option>
					</html:select>
				
				</td>
				<TD class="txt"><bean:message bundle="dp" key="interSSD.ToTsm"/><font color="red">*</font></td>
				<TD class="txt">
				<html:select property="toTsmId" style="width:150px" onchange="getToDistName();">
					<html:option value="">--Select TSM--</html:option>
				</html:select></td>
				
			</tr>
		
			<tr>
				<TD class="txt"><bean:message bundle="dp" key="interSSD.FromDist"/><font color="red">*</font></td>
				<TD class="txt">
				<html:select property="fromDistId"  style="width:150px" onchange="setDefauult();" >
						<html:option value="-1">--Select Distributor--</html:option>
				</html:select>
				
				</td>
				<TD class="txt" nowrap><bean:message bundle="dp" key="interSSD.ToDist"/><font color="red">*</font></td>
				<TD class="txt">
				<html:select property="toDistId" style="width:150px">
					<html:option value="-1">--Select Distributor--</html:option>
				</html:select></td>
			</tr>
						
			<tr>
			<TD class="txt">Collection Type<font color="RED">*</font></td>

			<td class="txt"><html:select property="collectionId" tabindex="1"
				onchange="showTable();" style="width: 150px">
				<html:option value="-1">
					<bean:message key="application.option.select" bundle="view" />
				</html:option>
				<!-- added by aman for new option "all" -->
				<html:option value="A">
							<bean:message bundle="view" key="application.option.select_all" />
				</html:option>
				<logic:notEmpty name="TransferPendingSTBBean"
					property="reverseCollectionList">
					<html:options collection="reverseCollectionList"
						labelProperty="collectionName" property="collectionId" />
				</logic:notEmpty>
			</html:select></td>

		<td  class="txt" id="stCnt" style="display:none">
		STB Count  </td>
		<td  class="txt"  style="display:none" id="stbCount"></td>	
		</tr>
						
						<tr><td>&nbsp;</td></tr>						
		
				<tr>
				</TBODY>
</TABLE>
<TABLE width="100%" border="0" 
		align="center" class ="border">
		<TBODY><tr>
				<td colspan="8">
				
	<div id="TableDiv"  style= "overflow: auto; width: 100%; height: 190px" >	
				<table border="0" width="100%">
						<tr>
							<td width="100%">	
							<table id="dataTableNew" width="100%" border="1" cellpadding="2"
						cellspacing="0" align="center">
					<tr align="center" bgcolor="#CD0504"
							style="color:#ffffff !important;" class="noScroll">
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Collection Type</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="7%"><span
								class="white10heading mLeft5 mTop5"><strong>Customer ID</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span	
								class="white10heading mLeft5 mTop5"><strong>Defective Serial Number</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="10%"><span
								class="white10heading mLeft5 mTop5"><strong>Product</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span	
								class="white10heading mLeft5 mTop5"><strong>Changed Serial Number</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="10%"><span
								class="white10heading mLeft5 mTop5"><strong>Product</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Inventory Change Date</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="1%"><span
								class="white10heading mLeft5 mTop5"><strong>Ageing</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="45%"><span
								class="white10heading mLeft5 mTop5"><strong>Defect Type</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Collection Date</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="1%"><span
								class="white10heading mLeft5 mTop5" ><strong>Select</strong></span></td>
						</tr></table></td></tr></table></DIV>
					</td></tr>
		<TR><td colspan='8'>&nbsp;</td></TR>
		<tr><td colspan='8' class="txt" align="center"><input
				class="submit" type="button" value="Add to Grid"
				onclick="callTableAdded();"> &nbsp;&nbsp;&nbsp;</td>
		</tr>
		<TR><td colspan='8'>&nbsp;</td></TR>
		
		<tr>
			<td colspan="8">
				<div class="scrollacc" style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 180px" align="center">
					<table width="100%">
					<tr>
						<td width="100%">
						
					<table id="dataTable" width="100%" border="1" cellpadding="2"
						cellspacing="0" align="center">
					<tr align="center" bgcolor="#CD0504"
							style="color:#ffffff !important;" class="noScroll">
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Collection Type</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Customer ID</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span	
								class="white10heading mLeft5 mTop5"><strong>Defective Serial Number</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="7%"><span
								class="white10heading mLeft5 mTop5"><strong>Product</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span	
								class="white10heading mLeft5 mTop5"><strong>Changed Serial Number</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="7%"><span
								class="white10heading mLeft5 mTop5"><strong>Product</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Inventory Change Date</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="3%"><span
								class="white10heading mLeft5 mTop5"><strong>Ageing</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="45%"><span
								class="white10heading mLeft5 mTop5"><strong>Defect Type</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Collection Date</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="3%"><span
								class="white10heading mLeft5 mTop5"><strong>Remove</strong></span></td>
						</tr>
						
					</table>
					</td>
					</tr>
					
					</table>
				</div>
			</td>
		</tr>
</TBODY>
</TABLE>
<br/><br/>
<DIV style="height: 120px; overflow: auto;" width="80%">

		<table id="dataTable3"  width="80%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
				<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Collection Type</FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Product Type</FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Count</FONT></td>
					
				</tr>
				</thead>
		</table></DIV>
<TABLE width="60%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
	
	<tr>
				<td align="center">
				<input type="button" name="transferBut" value="Transfer Stock" onclick="transferStock();">
				</td>
				
				
	</tr>
</TABLE>


</html:form>

</body>

</html>
<%}catch(Exception e){
e.printStackTrace();
}%>