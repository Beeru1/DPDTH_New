<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META name="GENERATOR" content="IBM Software Development Platform">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
	<TITLE><bean:message bundle="dp" key="application.title"/></TITLE>
	<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
	<script language="JavaScript" src="<%=request.getContextPath()%>/scripts/POCreate.js"></script>
	   
<script>
function getDistName(circleId)
	{
	    var levelId = document.getElementById("tsm_id").value;
	 	if(levelId!=''){
			var url = "reverseLogisticReport.do?methodName=initDistReport&parentId="+levelId+"&circleId="+circleId;
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
			req.onreadystatechange = getDistNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(2);
		}
	}
	
	function getDistNameChange()
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
			var selectObj = document.getElementById("to_dist");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("----Select----","");
			//selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
function checkRequest()
{
	var reqType=  "<bean:write property='str_msg' name='InterSSDFormBean' />";
	
	if(reqType == "SUCCESS")
	{
		window.close();
		opener.document.forms[0].submit();
	}
	var trans_type="<bean:write property='trans_type' name='InterSSDFormBean' />";
	if(trans_type == "RETAILER TRANSFER")
	{
		document.getElementById("sp_from_fse").style.display="block";
	    document.getElementById("sp_from_fse_val").style.display="block";
	    //document.getElementById("sp_to_fse").style.display="block";
	    //document.getElementById("sp_to_fse_val").style.display="block";	
	}
	
	
	
	
}
var rowSelected = 0;

	function submitDataTableRows() {
	var x;
	var i=0;
	var table = document.getElementById("dataTable2");
	var rowCount = table.rows.length;
	var jsArrTransId = new Array();
	var jsArrtoDistId = new Array();
	var jsArrtoFseId = new Array();
	
	var Transaction_type = document.getElementById("Transaction_type").value;
	var index;
	var todistIndex;
	var tofseIndex;
	
	if(Transaction_type == "RETAILER TRANSFER")
	{
		index = 10;
		todistIndex = 11;
		tofseIndex = 12;
	}
	else
	{
		index = 9;
		todistIndex=10;
	}
	
	if(rowCount  > 1)
  	    {
  	    while (i+1<rowCount){
  	    
  	    
  	    var x = document.getElementById("dataTable2").rows[i+1].cells;
  	    
  	    jsArrtoDistId = x[todistIndex].getElementsByTagName("input")[0].value;
  	    if(Transaction_type == "RETAILER TRANSFER")
  	    {
  	    	jsArrtoFseId = x[tofseIndex].getElementsByTagName("input")[0].value;
  	    	jsArrTransId[i] = x[index].getElementsByTagName("input")[0].value+"#"+jsArrtoDistId+"#"+jsArrtoFseId;
  	    }
  	    else
  	    {
  	    	jsArrTransId[i] = x[index].getElementsByTagName("input")[0].value+"#"+jsArrtoDistId;
  	    }
  	    
  	    
  	    
  	   // jsArrTransId[i] = x[index].getElementsByTagName("input")[0].value;
  	  	
		
  			//alert("hi");
  			i++;
  		}
  		
  		document.getElementById("transaction_Id").value=jsArrTransId;
  		//alert(document.getElementById("transaction_Id").value);
  		document.forms[0].action ="interssdtransfer.do?methodName=submitHirerchyTransfer";
		document.forms[0].submit();
		
		}
  		else
  		{ 
  		   alert("Please Add atleast One Record!!")
  		}
	
	
	}
	function getFSE(trans_type)
	{
			var selectedDist = document.getElementById("to_dist").value
			xmlHttp=GetXmlHttpObject();
			if (xmlHttp==null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
			var url="dpHierarchyTransfer.do";
			url=url+"?methodName=getFSEList&selected="+selectedDist;
			xmlHttp.onreadystatechange=stateChanged;
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
	}
	function stateChanged(){ 
			if (xmlHttp.readyState == 4){ 
				var xmldoc = xmlHttp.responseXML.documentElement;
				if(xmldoc == null){		
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");

				var selectObj = document.getElementById("to_fse");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("--Select FSE--","");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value").split(",")[0]);
			    }
			    document.getElementById("sp_from_fse").style.display="block";
			    document.getElementById("sp_from_fse_val").style.display="block";
			    document.getElementById("sp_to_fse").style.display="block";
			    document.getElementById("sp_to_fse_val").style.display="block";	    
			    			    
			    
			    //document.getElementById("fse_list").style.display="block";
			    var selectObj = document.getElementById("to_fse");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("------Select FSE------","");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value").split(",")[0]);
			    }
			}
		}
		
function addRow(trans) 
{

	document.getElementById("Transaction_type").value=trans;
	

  if(validateData())
  {
	rowSelected = rowSelected + 1;;
	addRowToDataTable(trans);
	//resetValues();
  }
}


function remove(rowCount2 ,trans_type){
		var table = document.getElementById("dataTable2");
		rowSelected = rowSelected -1;
			//get the deleted row data
		var x = table.rows[rowCount2].cells;
				if(document.all){
     			var jsAccountName = x[0].getElementsByTagName("div")[0].value;
				} else {
    				var jsAccountName = x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			 var jsContactName = x[1].getElementsByTagName("div")[0].value;
				} else {
    				var jsContactName = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			var jsRole= x[2].getElementsByTagName("div")[0].value;
				} else {
    				var jsRole = x[2].getElementsByTagName("div")[0].value.textContent;
				}
				
					
				if(document.all){
     			var jsContactNumber = x[3].getElementsByTagName("div")[0].value;
				} else {
    				var jsContactNumber = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			var jsZone = x[4].getElementsByTagName("div")[0].value;
				} else {
    			var	jsZone = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			var jsCity = x[5].getElementsByTagName("div")[0].value;
				} else {
    			var	jsCity = x[5].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     				//var jsToDist = x[6].getElementsByTagName("div")[0].value;
				} else {
    				//var	jsToDist = x[6].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     					//var jsToFSE = x[7].getElementsByTagName("div")[0].value;
				} else {
    					//var	jsToFSE = x[7].getElementsByTagName("div")[0].value.textContent;
				}
				var	jsCheckBoxval;
				if(trans_type == "RETAILER TRANSFER")
				{
					jsCheckBoxval = x[10].getElementsByTagName("input")[0].value;
					var jsStatus = x[8].getElementsByTagName("div")[0].value;
				}
				else
				{
					jsCheckBoxval = x[9].getElementsByTagName("input")[0].value;
					var jsStatus = x[7].getElementsByTagName("div")[0].value;
				}
				//alert(jsStatus);
				
//				alert(x[8].getElementsByTagName("input")[0].value);
				//alert(x[7].getElementsByTagName("input")[0].value);
				
			
			 //To insert in first table 
		    var table2 = document.getElementById("dataTable1");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			
			var cell7 = row2.insertCell(0);
			
			cell7.innerHTML = "<input type=\"checkbox\" id=\"trans_id\" name=\"trans_id\" value=\"" + jsCheckBoxval + "\" />";
		
			
			var cell1 = row2.insertCell(1);
			cell1.innerHTML = "<div value=\"" + jsAccountName +  "\">" + jsAccountName + "</div>";
			
			var cell2 = row2.insertCell(2);
			cell2.innerHTML = "<div value=\"" + jsContactName +  "\">" + jsContactName + "</div>";
			
			var cell3 = row2.insertCell(3);
			cell3.innerHTML = "<div value=\"" + jsRole +  "\">" + jsRole + "</div>";
			
			var cell4 = row2.insertCell(4);
			cell4.innerHTML = "<div value=\"" + jsContactNumber +  "\">" + jsContactNumber + "</div>";
			
			var cell5 = row2.insertCell(5);
			cell5.innerHTML = "<div value=\"" + jsZone +  "\">" + jsZone + "</div>";
			
			var cell6 = row2.insertCell(6);
			cell6.innerHTML = "<div value=\"" + jsCity +  "\">" + jsCity + "</div>";
			
			var cell7 = row2.insertCell(7);
			cell7.innerHTML = "<div value=\"\">&nbsp;</div>";
			
			
			if(trans_type == "RETAILER TRANSFER")
			{
					var cell8 = row2.insertCell(8);
					cell8.innerHTML = "<div value=\"\">&nbsp;</div>";
					
					var cell9 = row2.insertCell(9);
					cell9.innerHTML = "<div value=\"" + jsStatus +  "\">" + jsStatus + "</div>";
					
					
			}
			else
			{
					var cell9 = row2.insertCell(8);
					cell9.innerHTML = "<div value=\"" + jsStatus +  "\">" + jsStatus + "</div>";
			}
			
			
		//to delete row from 2nd table
 		table.deleteRow(rowCount2);	
		alternate();
		
		
		
		
		
	}
	
	 function alternate() {
   if(document.getElementsByTagName){  
   var table = document.getElementById("dataTable1");  
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

function addRowToDataTable(trans) {
	try {
			var table = document.getElementById("dataTable1");
			var rowCount = table.rows.length;
			var j=0;
			if(rowCount > 1){
			var i=0;
			var jsAccountName= new Array();
			var jsContactName= new Array();
			var jsRole= new Array();
			var jsContactNumber= new Array();
			var jsZone= new Array();
			var jsCity= new Array();
			var jsToDist= new Array();
			var jsToFSE= new Array();
			
			var jsToDistDisp= new Array();
			var jsToFSEDisp= new Array();
			
			var jsCheckBoxValue= new Array();
			var jsStatus= new Array();
			var strArraySelected = new Array();
			var str=new Array();
			
	if(document.forms[0].trans_id.length != undefined)
	{
		for (count = 0; count < document.forms[0].trans_id.length; count++)
		{
				if(document.forms[0].trans_id[count].checked==true)
			{
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
				
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				if(document.all){
				jsAccountName[i] = x[1].getElementsByTagName("div")[0].value;
				} else {
    				jsAccountName[i] = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsContactName[i] = x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsContactName[i] = x[2].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsRole[i] = x[3].getElementsByTagName("div")[0].value;
				} else {
    				jsRole[i] = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			jsContactNumber[i] = x[4].getElementsByTagName("div")[0].value;
				} else {
    				jsContactNumber[i] = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsZone[i] = x[5].getElementsByTagName("div")[0].value;
				} else {
    				jsZone[i] = x[5].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsCity[i] = x[6].getElementsByTagName("div")[0].value;
				} else {
    				jsCity[i] = x[6].getElementsByTagName("div")[0].value.textContent;
				}
				
				jsCheckBoxValue[i]="";
				if(document.all){
				if(trans == "RETAILER TRANSFER")  {
					
     				//jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value+"#"+document.getElementById("to_dist").value+"#"+document.getElementById("to_fse").value;
     				jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value;
     				
     				jsStatus[i] = x[9].getElementsByTagName("div")[0].value;
     				
     				
     				
     				
				}
				else
				{
					//jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value+"#"+document.getElementById("to_dist").value;
					jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value;
					jsStatus[i] = x[8].getElementsByTagName("div")[0].value;
				}
				} else {
    				jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value.textContent;
				}
				
				var w = document.getElementById("to_dist").selectedIndex;
				var selected_text = document.getElementById("to_dist").options[w].text
				jsToDistDisp[i] = selected_text;
				jsToDist[i] = document.getElementById("to_dist").value;
				
				//jsCheckBoxValue[i] = document.getElementById("trans_id").value;
				
				if(trans == "RETAILER TRANSFER")
				{
						var y = document.getElementById("to_fse").selectedIndex;
						var selected_texty = document.getElementById("to_fse").options[y].text;
						jsToFSEDisp[i] = selected_texty;
						jsToFSE[i] = document.getElementById("to_fse").value;
				}
				
				
			var table2 = document.getElementById("dataTable2");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsAccountName[i] +  "\">" + jsAccountName[i] + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsContactName[i] +  "\">" + jsContactName[i] + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsRole[i] +  "\">" + jsRole[i] + "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsContactNumber[i] +  "\">" + jsContactNumber[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsZone[i] +  "\">" + jsZone[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsCity[i] +  "\">" + jsCity[i] + "</div>";
			var cel17 = row2.insertCell(6);
			cel17.innerHTML = "<div value=\"" + jsToDist[i] +  "\">" + jsToDistDisp[i] + "</div>";
			if(trans == "RETAILER TRANSFER")
				{
					
					var cell8 = row2.insertCell(7);
					cell8.innerHTML = "<div value=\"" + jsToFSE[i] +  "\">" + jsToFSEDisp[i] + "</div>";
					
					var cel23 = row2.insertCell(8);
					cel23.innerHTML = "<div value=\"" + jsStatus[i] +  "\">" + jsStatus[i] + "</div>";
					
					var image = document.createElement("img");
					image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
					str[j] = j+1;
					image.onclick= function() { remove(this.parentNode.parentNode.rowIndex , trans);};
					var cell9 = row2.insertCell(9);
					cell9.appendChild(image);
					
					
					
					
					var cel20 = row2.insertCell(10);
					cel20.innerHTML = "<input type=hidden name=checkBoxVal value=\"" + jsCheckBoxValue[i] +  "\">";
					
					var cel21 = row2.insertCell(11);
					cel21.innerHTML = "<input type=hidden name=todistval value=\"" + jsToDist[i] +  "\">";
					
					var cel22 = row2.insertCell(12);
					cel22.innerHTML = "<input type=hidden name=tofseval value=\"" + jsToFSE[i] +  "\">";
					
					
					
				}
			else
			{
					var cel23 = row2.insertCell(7);
					cel23.innerHTML = "<div value=\"" + jsStatus[i] +  "\">" + jsStatus[i] + "</div>";
					
					
					var cell9 = row2.insertCell(8);
					var image = document.createElement("img");
					image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
					str[j] = j+1;
					image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
					cell9.appendChild(image);
					
					
					
					var cel20 = row2.insertCell(9);
					cel20.innerHTML = "<input type=hidden name=checkBoxVal value=\"" + jsCheckBoxValue[i] +  "\">";
					
					
					
					var cel21 = row2.insertCell(10);
					cel21.innerHTML = "<input type=hidden name=todistval value=\"" + jsToDist[i] +  "\">";
					
					var cel22 = row2.insertCell(11);
					cel22.innerHTML = "<input type=hidden name=tofseval value=\"" + jsToFSE[i] +  "\">";
					
					
					
					
			}
			
			
			
			
			
			}
		}
	} else 	if(document.forms[0].trans_id.checked==true)
			{
			
				var count=0;
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
				
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				if(document.all){
				jsAccountName[i] = x[1].getElementsByTagName("div")[0].value;
				} else {
    				jsAccountName[i] = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsContactName[i] = x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsContactName[i] = x[2].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsRole[i] = x[3].getElementsByTagName("div")[0].value;
				} else {
    				jsRole[i] = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			jsContactNumber[i] = x[4].getElementsByTagName("div")[0].value;
				} else {
    				jsContactNumber[i] = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsZone[i] = x[5].getElementsByTagName("div")[0].value;
				} else {
    				jsZone[i] = x[5].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsCity[i] = x[6].getElementsByTagName("div")[0].value;
				} else {
    				jsCity[i] = x[6].getElementsByTagName("div")[0].value.textContent;
				}
				
				jsCheckBoxValue[i]="";
				if(document.all){
				if(trans == "RETAILER TRANSFER")  {
					
     				//jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value+"#"+document.getElementById("to_dist").value+"#"+document.getElementById("to_fse").value;
     				jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value;
     				
     				jsStatus[i] = x[9].getElementsByTagName("div")[0].value;
     				
     				
     				
     				
				}
				else
				{
					//jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value+"#"+document.getElementById("to_dist").value;
					jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value;
					jsStatus[i] = x[8].getElementsByTagName("div")[0].value;
				}
				} else {
    				jsCheckBoxValue[i] = x[0].getElementsByTagName("input")[0].value.textContent;
				}
				
				var w = document.getElementById("to_dist").selectedIndex;
				var selected_text = document.getElementById("to_dist").options[w].text
				jsToDistDisp[i] = selected_text;
				jsToDist[i] = document.getElementById("to_dist").value;
				
				//jsCheckBoxValue[i] = document.getElementById("trans_id").value;
				
				if(trans == "RETAILER TRANSFER")
				{
						var y = document.getElementById("to_fse").selectedIndex;
						var selected_texty = document.getElementById("to_fse").options[y].text;
						jsToFSEDisp[i] = selected_texty;
						jsToFSE[i] = document.getElementById("to_fse").value;
				}
				
				
			var table2 = document.getElementById("dataTable2");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsAccountName[i] +  "\">" + jsAccountName[i] + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsContactName[i] +  "\">" + jsContactName[i] + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsRole[i] +  "\">" + jsRole[i] + "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsContactNumber[i] +  "\">" + jsContactNumber[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsZone[i] +  "\">" + jsZone[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsCity[i] +  "\">" + jsCity[i] + "</div>";
			var cel17 = row2.insertCell(6);
			cel17.innerHTML = "<div value=\"" + jsToDist[i] +  "\">" + jsToDistDisp[i] + "</div>";
			if(trans == "RETAILER TRANSFER")
				{
					
					var cell8 = row2.insertCell(7);
					cell8.innerHTML = "<div value=\"" + jsToFSE[i] +  "\">" + jsToFSEDisp[i] + "</div>";
					
					var cel23 = row2.insertCell(8);
					cel23.innerHTML = "<div value=\"" + jsStatus[i] +  "\">" + jsStatus[i] + "</div>";
					
					var image = document.createElement("img");
					image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
					str[j] = j+1;
					image.onclick= function() { remove(this.parentNode.parentNode.rowIndex , trans);};
					var cell9 = row2.insertCell(9);
					cell9.appendChild(image);
					
					
					
					
					var cel20 = row2.insertCell(10);
					cel20.innerHTML = "<input type=hidden name=checkBoxVal value=\"" + jsCheckBoxValue[i] +  "\">";
					
					var cel21 = row2.insertCell(11);
					cel21.innerHTML = "<input type=hidden name=todistval value=\"" + jsToDist[i] +  "\">";
					
					var cel22 = row2.insertCell(12);
					cel22.innerHTML = "<input type=hidden name=tofseval value=\"" + jsToFSE[i] +  "\">";
					
					
					
				}
			else
			{
					var cel23 = row2.insertCell(7);
					cel23.innerHTML = "<div value=\"" + jsStatus[i] +  "\">" + jsStatus[i] + "</div>";
					
					
					var cell9 = row2.insertCell(8);
					var image = document.createElement("img");
					image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
					str[j] = j+1;
					image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
					cell9.appendChild(image);
					
					
					
					var cel20 = row2.insertCell(9);
					cel20.innerHTML = "<input type=hidden name=checkBoxVal value=\"" + jsCheckBoxValue[i] +  "\">";
					
					
					
					var cel21 = row2.insertCell(10);
					cel21.innerHTML = "<input type=hidden name=todistval value=\"" + jsToDist[i] +  "\">";
					
					var cel22 = row2.insertCell(11);
					cel22.innerHTML = "<input type=hidden name=tofseval value=\"" + jsToFSE[i] +  "\">";
					
					
					
					
			}
			
			
			
			}
			
			
			
			
			
			
			
		if(strArraySelected.length>0){
		for(i=0;i<strArraySelected.length;i++){
			var rownum = strArraySelected[i];
			document.getElementById("dataTable1").deleteRow(rownum-i);
		}
		// alternate();
		
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
function validateData()
{
	 var alertMsg = "";
	var checkedRowLen = document.forms[0].trans_id.length;
	var noOfCheckedRows = 0;
	
	var count = 0;
	var els = document.getElementsByTagName('input');
	for (var i=0; i < els.length; ++i)
	{
		var e = els[i];
		if (e.type == 'checkbox')
			++count;
	}
	//var tableRows = document.getElementById("tableMSA").rows;
	for(rowCount=1; rowCount<count; rowCount++)
		{
			if(document.forms[0].trans_id[rowCount-1].checked==true)
			{
				noOfCheckedRows = 1;
			}
		}
		
		if(noOfCheckedRows==0)
		{
			//alert("No row/rows selected");
			//return false;
		}
	 
	 if(document.getElementById("tsm_id").value=="")
	 {
	 	alert("Please Select TSM");
	 	return false;
	 }
	 if(document.getElementById("to_dist").value=="")
	 {
	 	alert("Please Select To Distributor");
	 	return false;
	 }
	  var trans_type="<bean:write property='trans_type' name='InterSSDFormBean' />";
	  
	
	 
	 
	
	 if(trans_type == "RETAILER TRANSFER")
	 {
		 if(document.getElementById("to_fse").value=="")
		 {
		 	alert("Please Select To FSE");
		 	return false;
		 }
		
		if(document.getElementById("to_fse").value == document.getElementById("current_fse_id").value)
		 {
		 	alert("From Fse and To Fse can not be same");
		 	return false;
		 }
	}
	else
	{
		 if(trans_type != "RETAILER TRANSFER")
		 {
			 if(document.getElementById("to_dist").value == document.getElementById("current_dist_id").value)
			 {
			 	alert("From Distributor and To Distributor can not be same");
			 	return false;
			 }
		 }
	} 
	return true;
}

</script>	
	
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" 
	onload="checkRequest();" >
	

<html:form action="/interssdtransfer" name="InterSSDFormBean" type="com.ibm.dp.beans.InterSSDFormBean">	
		 
		<html:hidden property="methodName" value="insertPurchaseOrder" />
		
		<html:hidden property="transaction_type"  styleId="Transaction_type"/>
		<html:hidden property="transaction_Id" styleId="Transaction_Id"/>
		
<div style="display: none;">
<table id="inputTable" width="650px" border="0" align="center">
<tr>
			<td class="txt" width="150px"><strong><bean:message bundle="view" key="DcCreation.CollectionType" /></strong><font color="red">*</font></td>
			
			
					
					
			<td class="txt">
			&nbsp;
			</td>
			<td class="txt" align="right" colspan="4">
			<input type="button" value="GO" onclick="validateData()" /></td>
</tr>
</table>
</div>
		
<DIV style="height: 100px; overflow: auto;" width="80%" height="30%">				
<table id="dataTable1"  width="100%" align="left" border="0" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;"> 
			
				
<logic:equal value="FSE TRANSFER" property="trans_type" name="InterSSDFormBean">			
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="5%"><FONT color="white">&nbsp;</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Account Name</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Contact Name</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Role</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Contact Number</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Zone</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">City</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="13%"><FONT color="white">To Dist</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="13%"><FONT color="white">Status</TD>
					

					
					</TR>
					<logic:notEmpty name="InterSSDFormBean" property="arrList">
					<logic:iterate name="InterSSDFormBean" id="arrList" indexId="i" property="arrList">
					
				<TR>
					<td width="5%">
					<logic:equal value="INIT" property="status" name="arrList">
						<html:checkbox property="trans_id" name="InterSSDFormBean" value="${arrList.tr_no}#${arrList.account_id}"></html:checkbox>
					</logic:equal>
					<logic:notEqual value="INIT" property="status" name="arrList">
						<html:checkbox property="trans_id" name="InterSSDFormBean" value="${arrList.tr_no}#${arrList.account_id}" disabled="true"></html:checkbox>
					</logic:notEqual>
					</td>
					<TD align="center"  width="15%"><FONT color="black"><div value='<bean:write name="arrList" property="trans_name"/>'><bean:write name="arrList" property="trans_name"/></div> </TD>
					<TD align="center"  width="15%"><FONT color="black"><div value='<bean:write name="arrList" property="contact_name"/>'><bean:write name="arrList" property="contact_name"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="level_name"/>'><bean:write name="arrList" property="level_name"/></div> </TD>
					<TD align="center"  width="15%"><FONT color="black"><div value='<bean:write name="arrList" property="contact_number"/>'><bean:write name="arrList" property="contact_number"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="zone_name"/>'><bean:write name="arrList" property="zone_name"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="city_name"/>'><bean:write name="arrList" property="city_name"/></div> </TD>
					<TD align="center" width="13%"><FONT color="black"><div value='<bean:write name="arrList" property="to_dist"/>'><bean:write name="arrList" property="to_dist"/></div> </TD>
					<TD align="center" width="13%"><FONT color="black"><div value='<bean:write name="arrList" property="strAction"/>'><bean:write name="arrList" property="strAction"/></div> </TD>
				</TR>
				</logic:iterate>
				</logic:notEmpty>
			</logic:equal>
			
<logic:equal value="RETAILER TRANSFER" property="trans_type" name="InterSSDFormBean">			
				
				<TR>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="5%"><FONT color="white">&nbsp; </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Account Name </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Contact Name </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Role </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Contact Number </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Zone </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">City </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="13%"><FONT color="white">To Dist </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">To FSE </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="4%"><FONT color="white">Status</TD>
					

					
					</TR>
					<logic:notEmpty name="InterSSDFormBean" property="arrList">
					<logic:iterate name="InterSSDFormBean" id="arrList" indexId="i" property="arrList">
					
				<TR>
					<td width="5%">
					<logic:equal value="INIT" property="status" name="arrList">
						<html:checkbox property="trans_id" name="InterSSDFormBean" value="${arrList.tr_no}#${arrList.account_id}" ></html:checkbox>
					</logic:equal>
					<logic:notEqual value="INIT" property="status" name="arrList">
						<html:checkbox property="trans_id" name="InterSSDFormBean" value="${arrList.tr_no}#${arrList.account_id}" disabled="true" ></html:checkbox>
					</logic:notEqual>
					</td>
					<TD align="center"  width="15%"><FONT color="black"><div value='<bean:write name="arrList" property="trans_name"/>'><bean:write name="arrList" property="trans_name"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="contact_name"/>'><bean:write name="arrList" property="contact_name"/></div> </TD>
					<TD align="center"  width="15%"><FONT color="black"><div value='<bean:write name="arrList" property="level_name"/>'><bean:write name="arrList" property="level_name"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="contact_number"/>'><bean:write name="arrList" property="contact_number"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="zone_name"/>'><bean:write name="arrList" property="zone_name"/></div> </TD>
					<TD align="center"  width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="city_name"/>'><bean:write name="arrList" property="city_name"/></div> </TD>
					<TD align="center" width="13%"><FONT color="black"><div value='<bean:write name="arrList" property="to_dist"/>'><bean:write name="arrList" property="to_dist"/></div> </TD>
					<TD align="center" width="10%"><FONT color="black"><div value='<bean:write name="arrList" property="to_fse"/>'><bean:write name="arrList" property="to_fse"/></div> </TD>
					<TD align="center" width="4%"><FONT color="black"><div value='<bean:write name="arrList" property="strAction"/>'><bean:write name="arrList" property="strAction"/></div> </TD>
				</TR>
				</logic:iterate>
				</logic:notEmpty>
			</logic:equal>


			</TABLE>
</DIV>
<table align="center" width="100%" border=0 >
		<tr> <td align="right" colspan="6">&nbsp;</td><td></td></tr>
 		
 		
 		<tr> 
 		
 		<td align="right" width="15%" colspan="1"><STRONG>From Distributor:</STRONG></td>
		<td align="left" width="20%" colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="InterSSDFormBean" property="current_dist_name" />
		<html:hidden name="InterSSDFormBean" property="current_dist_id" styleId="current_dist_id"/>
 </td>
 		
 		<td align="right" width="7%" colspan="1"><STRONG>TSM:</STRONG></td>
		<td align="left" width="13%" colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;
 			<html:select property="tsm_id" name="InterSSDFormBean" onchange="javascript:getDistName( ${InterSSDFormBean.circle_id} )">
			<html:option value="">----Select----</html:option>
	 				<html:optionsCollection name="InterSSDFormBean" property="distList" label="account_name" value="account_id"/>
	 
	 </html:select>
 		</td>
		
 
 		
 		
 		
 		 <td align="right" width="20%" colspan="1"><STRONG>To Distributor :</STRONG></td>
		<td align="left" width="25%" colspan="1">
		<logic:equal value="RETAILER TRANSFER" property="trans_type" name="InterSSDFormBean" >&nbsp;&nbsp;&nbsp;&nbsp;
			<html:select property="to_dist" name="InterSSDFormBean" onchange="javascript:getFSE()">
			<html:option value="">----Select----</html:option>
	 </html:select>
			
			
		</logic:equal>
		<logic:notEqual value="RETAILER TRANSFER" property="trans_type" name="InterSSDFormBean" >&nbsp;&nbsp;&nbsp;&nbsp;
			<html:select property="to_dist" name="InterSSDFormBean" >
			<html:option value="">----Select----</html:option>
	 			
	 
	 </html:select>
		</logic:notEqual>
 					
 </td>
 </tr>
 <tr> <td align="right" colspan="6">&nbsp;</td><td></td></tr>
 <tr> <td align="right" colspan="6">&nbsp;</td><td></td></tr>
 <tr>

 
		<td valign="top" align="right" width="15%" style="display:none;" id="sp_from_fse"><STRONG>
					From Fse:</STRONG></td>
					<td align="left" colspan="1" width="20%" style="display:none;" id="sp_from_fse_val">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
						name="InterSSDFormBean" property="current_fse_name" /> <html:hidden
						name="InterSSDFormBean" property="current_fse_id"
						styleId="current_fse_id" /></td>
					<td width="7%">&nbsp;</td>
					<td width="13%">&nbsp;</td>

					<td align="right" width="20%" style="display:none;" id="sp_to_fse" ><STRONG>To
					FSE:</STRONG></td>
					<td align="left" colspan="1" width="25%" style="display:none;" id="sp_to_fse_val">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:select
						property="to_fse" name="InterSSDFormBean" styleId="to_fse">
					</html:select></td>
				</tr>
  	<tr> <td align="right" colspan="6">&nbsp;</td><td></td></tr>
		<tr>
					<td class="txt" align="center" colspan="6"><input
						class="submit" type="button" value="Add to Grid"
						onclick="addRow('<bean:write name="InterSSDFormBean" property="trans_type"/>')"></td>
				</tr>
 
</table>				

	<DIV style="height: 100px; overflow: auto;" width="80%" height="30%">
		<table id="dataTable2"  width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
				<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Account Name </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Contact Name </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Role </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Contact Number </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">Zone </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="10%"><FONT color="white">City </TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">To Dist </TD>
					
	<logic:equal value="RETAILER TRANSFER" property="trans_type" name="InterSSDFormBean" >				
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">To Fse</TD>
	</logic:equal>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Status</TD>
					<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="15%"><FONT color="white">Remove</TD>
				</tr>
	</table></DIV>
	<table width="100%" border="0" cellPadding="0" cellSpacing="0" >			
				<tr>  
				<td  class="txt" align="center" colspan="10">
				<input type="button" id="submitAllocation" value="Transfer" onclick="submitDataTableRows()" />
				<input type="button" id="submitAllocationClose" value="Close" onclick="javascript:window.close();" />
				</td>
			</tr>
				
				
		</table>		

</html:form>
</BODY>
</html:html>