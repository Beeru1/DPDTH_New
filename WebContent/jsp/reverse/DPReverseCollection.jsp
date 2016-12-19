<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.virtualization.recharge.dto.UserSessionContext;" %>
<%
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute("USER_INFO");
			
		String username= sessionContext.getLoginName();
		
 %>

<html>
<head>    
<title>Reverse Collection Screen</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<link href="${pageContext.request.contextPath}/jsp/hbo/theme/text_new.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/theme/naaztt.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<style type="text/css">
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
<script language="JavaScript" src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script> 
<script type="text/javascript">
 
 function checkDuplicateno(collSRNo){
 // Check same serial not alowed in Grid
	var table = document.getElementById("dataTable");
	var rowCount = table.rows.length;
	var i=0;
	var message ="";
	while(i+1<rowCount)
	{
		var x = document.getElementById("dataTable").rows[i+1].cells;
		var tempSerialNo = x[1].getElementsByTagName("div")[0].value;
		if(tempSerialNo == collSRNo)
        {
        	message = collSRNo + ", Defective Serial no. has arleady been added \n";
        }
        i++;
	}
	return message;
 
 }
 
 
 
 function showTable(){
 document.getElementById("stbCount").style.display="none";
		document.getElementById("stCnt").style.display="none";
		
	var collectionId = document.getElementById("collectionId").value;
	if(collectionId==2)
	{
		document.getElementById("defectTypeId1").style.display="inline";
		document.getElementById("defectTypeId2").style.display="inline";
		getCollectionDefectType();
	}
	else
	{
	document.getElementById("defectTypeId1").style.display="none";
	document.getElementById("defectTypeId2").style.display="none";
	
	}
	if(collectionId ==1 || collectionId ==6 || collectionId ==3 || collectionId ==5 ) 
		{
		
		getInventoryChangeList();
		document.getElementById("InputDiv").style.display="none";
		document.getElementById("TableDiv").style.display="inline";
		//document.getElementById("defectId").disabled=true;
		document.getElementById("ResetButton").style.display="none";
		if(collectionId ==5){
		document.getElementById("defcType").style.display="none";
		}else{
		document.getElementById("defcType").style.display="inline";
		}
		document.getElementById("stbCount").style.display="inline";
		document.getElementById("stCnt").style.display="inline";
		
		var table = document.getElementById("dataTableNew");
		var rowCount = table.rows.length;
		var countStb=rowCount-1;	
		document.getElementById("stbCount").innerHTML = "<div value=\"" + countStb +  "\"> <strong><font color='#000000'>" + countStb + "</font></strong></div>";
}
	else if(collectionId ==4 || collectionId ==2)
	{
	document.getElementById("ResetButton").style.display="inline";
	getCollectionProduct();
	document.getElementById("InputDiv").style.display="inline";
	document.getElementById("TableDiv").style.display="none";
	if(collectionId ==2){
	//document.getElementById("defectId").disabled=false;
	}else if(collectionId ==4){
	//document.getElementById("defectId").disabled=true;
	document.getElementById("collectSerialNo").focus();
	}
	
	}
	
}
 
 function deleteRowDataTable3(collectionType,productType){
 	// alert("deleteRowDataTable3");
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
	
	function loopDataTable3(collectionType,productType){
	 //alert("loopDataTable3");
	var table = document.getElementById("dataTable3");
	var count=0;
	var newInsert=0;
	if(table.rows.length==1){
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
	
 //Added by Shilpa khanna

function callTableAdded(){
 //alert("callTableAdded");
var collectionId = document.getElementById("collectionId").value;
 if(collectionId == '0'){
  	    alert ("Please select Collection Type.");
	   document.getElementById("collectionId").focus();
	   return false;
 }

var remarks = document.getElementById("remarks").value;
	 if(remarks == '' || trimAll(remarks) == "")
	 {
	   alert ("Please enter remark.");
	   document.getElementById("remarks").focus();
	   return false;
	 }
	 	 	 
	if(collectionId ==1 || collectionId ==6 || collectionId ==3 || collectionId ==5 ) 
	{
	addTableGridToTableGrid();
	}

	else if(collectionId ==4 || collectionId ==2)
	{
	 addRow();
	}
}
 
 function addTableGridToTableGrid(){
	try {
		//alert("addTableGridToTableGrid");
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
			var jsReqId = new Array();
			var ageing =new Array();
			var customerId=new Array();
			var jsRemark = new Array();
			var strArraySelected = new Array();
			var str=new Array();
			var defectType =new Array();
			var defectId =new Array();
			var defValue;
			var message ="";
	
	 var jsRejectedDC=new Array(); //Neetika
	
	if(document.forms[0].strCheckedSTA.length != undefined)
	{
			
		for (count = 0; count < document.forms[0].strCheckedSTA.length; count++)
		{
				if(document.forms[0].strCheckedSTA[count].checked==true)
			{
				var selectedRow1 = count+1;
				strArraySelected[j]= selectedRow1;
				j++;
		
			var rownum1 = selectedRow1+1;
			var y = document.getElementById("dataTableNew").rows[rownum1-1].cells;
			jsCollectIonId[i] = y[0].getElementsByTagName("input")[0].value;
				//ends
				
				
				if(jsCollectIonId[i] != 5 && jsCollectIonId[i]!=2){
					defValue = 	y[8].getElementsByTagName("div")[0].innerText;
				
			
				}
				if(jsCollectIonId[i] == 2){
					defValue = 	y[8].getElementsByTagName("select")[0].value;
					
    		
				if(defValue == '0'){
				alert("Please select Defect Type");
				return false;
				}
			
				}
				
			}
		}
	}else 	if(document.forms[0].strCheckedSTA.checked==true)
			{
			    var count=0;
				var selectedRow1 = count+1;
				strArraySelected[j]= selectedRow1;
				j++;
				
			var rownum1 = selectedRow1+1;
			var y = document.getElementById("dataTableNew").rows[rownum1-1].cells;
			jsCollectIonId[i] = y[0].getElementsByTagName("input")[0].value;
				//ends
				
				
				if(jsCollectIonId[i] != 5 && jsCollectIonId[i]!=2){
					defValue = 	y[8].getElementsByTagName("div")[0].innerText;
					
    		
				
			
			}
			if(jsCollectIonId[i] == 2){
					defValue = 	y[8].getElementsByTagName("select")[0].value;
					
    		
				if(defValue == '0'){
				alert("Please select Defect Type");
				return false;
				}
			
			}
		}
	
	
	
	
		i=0;
		j=0;var collectionType;
			var productType;
			
		strArraySelected = new Array();
		defValue="";
	if(document.forms[0].strCheckedSTA.length != undefined)
	{
			
		for (count = 0; count < document.forms[0].strCheckedSTA.length; count++)
		{
				if(document.forms[0].strCheckedSTA[count].checked==true)
			{
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
		
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTableNew").rows[rownum-1].cells;
			
			
			if(document.all){
     		 if(typeof( x[0].getElementsByTagName("div")[0])!="undefined")
			{    
     		jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value;
     			collectionType= x[0].getElementsByTagName("div")[0].value;}
				} else {
				 if(typeof( x[0].getElementsByTagName("div")[0])!="undefined")
			{  
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType= x[0].getElementsByTagName("div")[0].value.textContent;
				}
			}	
				//for hidden collection  id 
				jsCollectIonId[i] = x[0].getElementsByTagName("input")[0].value;
				//ends
				
				
				if(jsCollectIonId[i] != 5 && jsCollectIonId[i]!=2){
				 if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
			{    
     		
					defValue = 	x[8].getElementsByTagName("div")[0].innerText;
					}else{
					defValue="0";
					}
    		
				if(defValue == '0'){
				alert("Please select Defect Type");
				return false;
				}
			}else if(jsCollectIonId[i] == 5){
			defValue =  "";
			}
			if(jsCollectIonId[i] == 2){
				 if(typeof( x[8].getElementsByTagName("select")[0])!="undefined")
			{    
     		
					defValue = 	x[8].getElementsByTagName("select")[0].value;
					}else{
					defValue="0";
					}
    		
				if(defValue == '0'){
				alert("Please select Defect Type");
				return false;
				}
			}
			
				
				if(document.all){
				 if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
			{  
     			jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value;
     			}
				} else {
				 if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
			{  
    				jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    				}
				}
				
				if(document.all){
				 if(typeof( x[3].getElementsByTagName("div")[0])!="undefined")
			{  
     			jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value;
     			productType= x[3].getElementsByTagName("div")[0].value;
     			}
				} else {
    				 if(typeof( x[3].getElementsByTagName("div")[0])!="undefined")
			{  jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value.textContent;
    				productType= x[3].getElementsByTagName("div")[0].value.textContent;
    				}
				}
				
				//for hidden  defect product id 
				jsDefectProductId[i] = x[3].getElementsByTagName("input")[0].value;
				//ends
				
				if(document.all){
				 if(typeof( x[4].getElementsByTagName("div")[0])!="undefined")
			{  
     			jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value;
     			}
				} else {
				 if(typeof( x[4].getElementsByTagName("div")[0])!="undefined")
			{  
    				jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value.textContent;
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
				//for hidden  new product id 
				jsNewProductId[i] = x[5].getElementsByTagName("input")[0].value;
				//ends
				loopDataTable3(collectionType,productType);
				
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
			}	} else {
			 if(typeof( x[7].getElementsByTagName("div")[0])!="undefined")
			{  
    				ageing[i] = x[7].getElementsByTagName("div")[0].value.textContent;
			}	}
				if(document.all){
	 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		
     			customerId[i] = x[1].getElementsByTagName("div")[0].value;}
				} else {
    					 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		customerId[i] = x[1].getElementsByTagName("div")[0].value.textContent;}
				}
				
				
				if(jsCollectIonId[i] == 5)
				{	if(document.all){
	 					if(typeof( x[10].getElementsByTagName("div")[0])!="undefined")// changed from 9 to 10 by Neetika
						{    
     		
     					jsReqId[i] = x[10].getElementsByTagName("div")[0].value;// changed from 9 to 10
     					}
					}else {
    					if(typeof( x[10].getElementsByTagName("div")[0])!="undefined") //changed from 9 to 10
						{    
     						jsReqId[i] = x[10].getElementsByTagName("div")[0].value.textContent; //changed from 9 to 10
     					}
					}
				}
				else
				{
				if(document.all){
	 					if(typeof( x[11].getElementsByTagName("div")[0])!="undefined")//changed 10 to 11
						{    
     		
     					jsReqId[i] = x[11].getElementsByTagName("div")[0].value; //changed form 10 to 11
     					}
					}else {
    					if(typeof( x[11].getElementsByTagName("div")[0])!="undefined") //changed form 10 to 11
						{    
     						jsReqId[i] = x[11].getElementsByTagName("div")[0].value.textContent; //chnaged from 10 to 11
     					}
					}
				
				
				}
				//Added Neetika
				if(document.all){
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{  
     			jsRejectedDC[i] = x[9].getElementsByTagName("div")[0].value;
     			}
				} else {
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{  
    				jsRejectedDC[i] = x[9].getElementsByTagName("div")[0].value.textContent;
    				}
				}
				
				//end neetika
				if(jsCollectIonId[i] != 5 && jsCollectIonId[i] !=2){
					defValue = 	x[8].getElementsByTagName("div")[0].innerText;
					defectType[i] =x[8].getElementsByTagName("div")[0].innerText;
    				defectId[i] =x[8].getElementsByTagName("div")[0].value;
				}else if(jsCollectIonId[i] == 5){
					defectType[i] =  "";
					defectId[i] = "0";
			}
			if(jsCollectIonId[i] == 2){
					defValue = 	x[8].getElementsByTagName("select")[0].value;
					defectType[i] =defValue.split("#")[1];
    				defectId[i] =defValue.split("#")[0];
				}	
			message += checkDuplicateno(jsDefectSrNo[i]);
			if(message !=""){
				alert(message);
				return false;
			}
			
		
			var table2 = document.getElementById("dataTable");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div><input type=\"hidden\" value=\"" + jsCollectIonId[i] + "\" />";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + customerId[i] +  "\">" + customerId[i] + "</div>";
			
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsDefectSrNo[i] +  "\">" + jsDefectSrNo[i] + "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsDefectProductType[i] +  "\">" + jsDefectProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsDefectProductId[i] + "\" />";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsNewSrNo[i] +  "\">" + jsNewSrNo[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsNewProductType[i] +  "\">" + jsNewProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsNewProductId[i] + "\" />";
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div value=\"" + jsInventoryChangeDate[i] +  "\">" + jsInventoryChangeDate[i] + "</div>";
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div value=\"" + ageing[i] +  "\">" + ageing[i] + "</div>";
			
			
			
			
			var cell9 = row2.insertCell(8);
			if(jsCollectIonId[i] != 5){
			cell9.innerHTML = "<div value=\"" + defectType[i] +  "\">" + defectType[i] + "</div> <input type=\"hidden\" value=\"" + defectId[i] + "\" />"; 
			}
			//Added by Neetika
			var cell13 = row2.insertCell(9);
			
			cell13.innerHTML = "<div value=\"" + jsRejectedDC[i] +  "\">" + jsRejectedDC[i] + "</div>"; 
					//end
			var cell10 = row2.insertCell(10);// changed from 9 to 10
			cell10.innerHTML = "<div class='remarks' value=\"" + document.getElementById("remarks").value +  "\">" + document.getElementById("remarks").value + "</div>";
			
			
			var cell11 = row2.insertCell(11);// changed from 10 to 11
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			str[j] = j+1;
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
			cell11.appendChild(image);
			
			 var cell12 = document.createElement("td");    
			   
			cell12.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + jsReqId[i] +  "\">" + jsReqId[i] + "</div>";
			cell12.setAttribute('class','widthzero'); 
			cell12.setAttribute('display','none'); 
			cell12.setAttribute('width','0'); 
			cell12.setAttribute('style','display:none'); 
			cell12.style.display = 'none';
			row2.appendChild(cell12);
				
				
				
			}
		} //for loop end
		resetValues2();
	}else 	if(document.forms[0].strCheckedSTA.checked==true)
			{
			    var count=0;
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
				
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTableNew").rows[rownum-1].cells;
		
			if(typeof(x[0].getElementsByTagName("div")[0]) !="undefined"){
				if(document.all){
     			jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value;
     			collectionType = x[0].getElementsByTagName("div")[0].value;
				} else {
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType = x[0].getElementsByTagName("div")[0].value.textContent;
				}
			}	
				//for hidden collection  id 
				if(typeof(x[0].getElementsByTagName("input")[0]) !="undefined"){
			
				jsCollectIonId[i] = x[0].getElementsByTagName("input")[0].value;
				}//ends
				
				if(typeof(x[8].getElementsByTagName("div")[0]) !="undefined"){
			if(jsCollectIonId[i] != 5 && jsCollectIonId[i]!=2){
					defValue = 	x[8].getElementsByTagName("div")[0].innerText;
				
    		
				
			}else if(jsCollectIonId[i] == 5){
					defValue =  "";
			}
			
			if(jsCollectIonId[i] == 2){
					defValue = 	x[8].getElementsByTagName("select")[0].value;
				
    		
				if(defValue == '0'){
				alert("Please select Defect Type");
				return false;
				}
			}
			}
			if(typeof(x[2].getElementsByTagName("div")[0]) !="undefined"){
				
				if(document.all){
     			jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsDefectSrNo[i] = x[2].getElementsByTagName("div")[0].value.textContent;
				}
				
			}	
			
			if(typeof(x[3].getElementsByTagName("div")[0]) !="undefined"){
			if(document.all){
     			jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value;
     			productType =  x[3].getElementsByTagName("div")[0].value;
				} else {
    				jsDefectProductType[i] = x[3].getElementsByTagName("div")[0].value.textContent;
    				productType =  x[3].getElementsByTagName("div")[0].value.textContent;
				}
			}	
				//for hidden  defect product id 
				if(typeof(x[3].getElementsByTagName("input")[0]) !="undefined"){
			
				jsDefectProductId[i] = x[3].getElementsByTagName("input")[0].value;
				}//ends
				
				if(typeof(x[4].getElementsByTagName("div")[0]) !="undefined"){
				if(document.all){
     			jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value;
				} else {
    				jsNewSrNo[i] = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				}
				if(typeof(x[5].getElementsByTagName("div")[0]) !="undefined"){
			
				if(document.all){
     			jsNewProductType[i] = x[5].getElementsByTagName("div")[0].value;
				} else {
    				jsNewProductType[i] = x[5].getElementsByTagName("div")[0].value.textContent;
				}
				}
				//for hidden  new product id 
				if(typeof(x[5].getElementsByTagName("input")[0]) !="undefined"){
			
				jsNewProductId[i] = x[5].getElementsByTagName("input")[0].value;
				}//ends
				
				loopDataTable3(collectionType,productType);
				if(typeof(x[6].getElementsByTagName("div")[0]) !="undefined"){
			
				if(document.all){
     			jsInventoryChangeDate[i] = x[6].getElementsByTagName("div")[0].value;
				} else {
    				jsInventoryChangeDate[i] = x[6].getElementsByTagName("div")[0].value.textContent;
				}
				}
				
				if(typeof(x[7].getElementsByTagName("div")[0]) !="undefined"){
			
				if(document.all){
     			ageing[i] = x[7].getElementsByTagName("div")[0].value;
				} else {
    				ageing[i] = x[7].getElementsByTagName("div")[0].value.textContent;
				}
				}
				
				if(jsCollectIonId[i]==5){
					if(typeof(x[10].getElementsByTagName("div")[0]) !="undefined"){ //changed form 9 to 10
				
					if(document.all){
	     			jsReqId[i] = x[10].getElementsByTagName("div")[0].value;//changed from 9 to 10
					} else {
	    				jsReqId[i] = x[10].getElementsByTagName("div")[0].value.textContent;//chnaged from 9 to 10
					}
					}
				}
				else
				{
				
					if(typeof(x[11].getElementsByTagName("div")[0]) !="undefined"){//changed from 10 to 11
				
					if(document.all){
	     			jsReqId[i] = x[11].getElementsByTagName("div")[0].value; //changed form 10 to 11
					} else {
	    				jsReqId[i] = x[11].getElementsByTagName("div")[0].value.textContent;//changed from 10 to 11
					}
					}
				
				
				
				}
				//Added Neetika
				
				if(document.all){
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{  
     			jsRejectedDC[i] = x[9].getElementsByTagName("div")[0].value;
     			}
				} else {
				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{  
    				jsRejectedDC[i] = x[9].getElementsByTagName("div")[0].value.textContent;
    				}
				}
				
				//end neetika
				
				if(typeof(x[1].getElementsByTagName("div")[0]) !="undefined"){
			
				if(document.all){
					 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		
     			customerId[i] = x[1].getElementsByTagName("div")[0].value;
     			}
				} else {
    					 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		customerId[i] = x[1].getElementsByTagName("div")[0].value.textContent;}
				}
			}	
				if(jsCollectIonId[i] != 5 && jsCollectIonId[i]!=2){
    			defectType[i] =x[8].getElementsByTagName("div")[0].innerText;
    			defectId[i] =x[8].getElementsByTagName("div")[0].value;
    			}else if(jsCollectIonId[i] == 5){
					defectType[i] =  "";
					defectId[i] = "0";
			}
			if(jsCollectIonId[i] == 2){
    			defectType[i] =defValue.split("#")[1];
    				defectId[i] =defValue.split("#")[0];
    			}
			message += checkDuplicateno(jsDefectSrNo[i]);
			
			if(message !=""){
				alert(message);
				return false;
			}
				
			var table2 = document.getElementById("dataTable");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div><input type=\"hidden\" value=\"" + jsCollectIonId[i] + "\" />";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + customerId[i] +  "\">" + customerId[i] + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsDefectSrNo[i] +  "\">" + jsDefectSrNo[i] + "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsDefectProductType[i] +  "\">" + jsDefectProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsDefectProductId[i] + "\" />";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsNewSrNo[i] +  "\">" + jsNewSrNo[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsNewProductType[i] +  "\">" + jsNewProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsNewProductId[i] + "\" />";
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div value=\"" + jsInventoryChangeDate[i] +  "\">" + jsInventoryChangeDate[i] + "</div>";
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div value=\"" + ageing[i] +  "\">" + ageing[i] + "</div>";
			
		
			
			var cell9 = row2.insertCell(8);
			if(jsCollectIonId[i] != 5){
			cell9.innerHTML = "<div value=\"" + defectType[i] +  "\">" + defectType[i] + "</div> <input type=\"hidden\" value=\"" + defectId[i] + "\" />"; 
			}
			
			//Added by Neetika
			if(jsCollectIonId[i] != 5 && jsCollectIonId[i] != 2){
			var cell13 = row2.insertCell(9);
			cell13.innerHTML = "<div value=\"" + jsRejectedDC[i] +  "\">" + jsRejectedDC[i] + "</div>"; 
					//end
			
			}
			else{
			var cell13 = row2.insertCell(9);
			
			cell13.innerHTML = "<div value=\"" + "" +  "\">" + "" + "</div>"; 
					//end
			}

			var cell10 = row2.insertCell(10);//changed by neetika
			cell10.innerHTML = "<div class='remarks' value=\"" + document.getElementById("remarks").value +  "\">" + document.getElementById("remarks").value + "</div>";
					
			var cell11 = row2.insertCell(11);//changed by neetika
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
			cell11.appendChild(image);
			
			 var cell12 = document.createElement("td");    
			    
			cell12.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + jsReqId[i] +  "\">" + jsReqId[i] + "</div>";
			cell12.setAttribute('class','widthzero'); 
			cell12.setAttribute('display','none'); 
			cell12.setAttribute('width','0'); 
			cell12.setAttribute('style','display:none'); 
			cell12.style.display = 'none';
			row2.appendChild(cell12);
			
			
			resetValues2();
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
				alert("Error "+e);
		 }
	
	
}
 
 function resetValues2()
{
        document.getElementById("remarks").value="";   
}
 
 
 function remove(rowCount2)
{
		var collID = document.getElementById("collectionId").value;
		var table = document.getElementById("dataTable");
	//get the deleted row data
		var x = table.rows[rowCount2].cells;
		var collectionType,productType;
	//for hidden Collection id 
		var	jsCollectionId = x[0].getElementsByTagName("input")[0].value;
	//ends
	
		if(jsCollectionId ==1 || jsCollectionId ==3 ||jsCollectionId ==5 || jsCollectionId ==6){
			if(jsCollectionId == collID){
		
			
			if(jsCollectionId !=5){
				if(document.all){
     			var jsCollectionType = x[0].getElementsByTagName("div")[0].value;
     			collectionType= x[0].getElementsByTagName("div")[0].value;
				} else {
    				var jsCollectionType = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType= x[0].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			var jsDefectSrNo = x[2].getElementsByTagName("div")[0].value;
				} 
				else {
    				var jsDefectSrNo = x[2].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			var jsDefectProductType = x[3].getElementsByTagName("div")[0].value;
     			productType= x[3].getElementsByTagName("div")[0].value;
				} else {
    				var jsDefectProductType = x[3].getElementsByTagName("div")[0].value.textContent;
    				productType= x[3].getElementsByTagName("div")[0].value.textContent;
				}
				
				//for hidden  defect product id 
				var jsDefectProductId = x[3].getElementsByTagName("input")[0].value;
				//ends
				
				if(document.all){
     			var jsNewSrNo = x[4].getElementsByTagName("div")[0].value;
				} else {
    			var 	jsNewSrNo = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			var jsNewProductType = x[5].getElementsByTagName("div")[0].value;
     			
				} else {
    				var jsNewProductType = x[5].getElementsByTagName("div")[0].value.textContent;
    				
				}
				//for hidden  new product id 
				var jsNewProductId = x[5].getElementsByTagName("input")[0].value;
				//ends
				
				//deleteRowDataTable3(collectionType,productType);
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
     			var jsReqId= x[12].getElementsByTagName("div")[0].value;//changed from 11 to 12
				} else {
    			var jsReqId = x[12].getElementsByTagName("div")[0].value.textContent;//changed from 11 to 12
				}
				
			if(document.all){
     				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		var customerId= x[1].getElementsByTagName("div")[0].value;}
				} else {
    				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		var customerId = x[1].getElementsByTagName("div")[0].value.textContent;}
				}
			
		if(jsCollectionId!=2){	
			if(document.all){
     				 if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
			{    
     		var defectId= x[8].getElementsByTagName("input")[0].value;
     		var defectType=x[8].getElementsByTagName("div")[0].innerText;
     		}
				} else {
    				 if(typeof( x[8].getElementsByTagName("div")[0])!="undefined")
			{    
     		var defectId = x[8].getElementsByTagName("input")[0].value;
     		var defectType=x[8].getElementsByTagName("div")[0].innerText;
     		}
				}
					
				//Added by Neetika
				if(document.all){
     				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined") //23 feb
			{    
			
     		var	jsRejectedDC = x[9].getElementsByTagName("div")[0].value;
     		
     		}
				} else {
    				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{    
     		var	jsRejectedDC = x[9].getElementsByTagName("div")[0].value;
     			//alert(" in remove jsRejectedDC x10"+jsRejectedDC);
     		}
	}//end neetika 
			}		//end not equal to 2
				
			var table2 = document.getElementById("dataTableNew");
			var rowValue =table2.rows.length;
		   	var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType +  "\">" + jsCollectionType + "</div><input type=\"hidden\" value=\"" + jsCollectionId + "\" />";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + customerId +  "\">" + customerId + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsDefectSrNo +  "\">" + jsDefectSrNo+ "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsDefectProductType +  "\">" + jsDefectProductType + "</div> <input type=\"hidden\" value=\"" + jsDefectProductId + "\" />";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsNewSrNo +  "\">" + jsNewSrNo + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsNewProductType +  "\">" + jsNewProductType + "</div> <input type=\"hidden\" value=\"" + jsNewProductId + "\" />";
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div value=\"" + jsInventoryChangeDate +  "\">" + jsInventoryChangeDate + "</div>";
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div value=\"" + ageing +  "\">" + ageing + "</div>";
			
			
			if(jsCollectionId!=2){
			var cell9 = row2.insertCell(8);
			//call Ajax for defect Dropdown
			cell9.innerHTML = "<div value=\"" + defectId +  "\">" + defectType + "</div>";
			//Neetika BFR 9
			var cell12 = row2.insertCell(9);
			//call Ajax for defect Dropdown
			cell12.innerHTML = "<div value=\"" + jsRejectedDC +  "\">" + jsRejectedDC + "</div>";
			}
			
			if(jsCollectionId==2){
			var cell9 = row2.insertCell(8);
			//call Ajax for defect Dropdown
			callDefectList(jsCollectionId,cell9);
			//callDefectList(jsCollectionId,cell9);
			  }
			
			
			var cell10 = row2.insertCell(10);// changed by neetika 9 to 10
			
			cell10.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + jsCollectionId + "\" />";
			
			 var cell11 = document.createElement("td");    
			  
			cell11.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + jsReqId +  "\">" + jsReqId + "</div>";
			cell11.setAttribute('class','widthzero'); 
			cell11.setAttribute('style','display:none'); 
			cell11.setAttribute('width','0'); 
			cell11.style.display = 'none';
			row2.appendChild(cell11);
	}
	
		else if(jsCollectionId ==5){
				
		// ******
				var defectType ="";
				//document.getElementById("defcType").style.display="none";
				if(document.all){
     			var jsCollectionType = x[0].getElementsByTagName("div")[0].value;
     			collectionType = x[0].getElementsByTagName("div")[0].value;
				} else {
    				var jsCollectionType = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType = x[0].getElementsByTagName("div")[0].value.textContent;
				
				}
				
				if(document.all){
     			var jsDefectSrNo = x[2].getElementsByTagName("div")[0].value;
				} 
				else {
    				var jsDefectSrNo = x[2].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			var jsDefectProductType = x[3].getElementsByTagName("div")[0].value;
     			productType = x[3].getElementsByTagName("div")[0].value;
				
				} else {
    				var jsDefectProductType = x[3].getElementsByTagName("div")[0].value.textContent;
    				productType = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				
				//for hidden  defect product id 
				var jsDefectProductId = x[3].getElementsByTagName("input")[0].value;
				//ends
				
				if(document.all){
     			var jsNewSrNo = x[4].getElementsByTagName("div")[0].value;
				} else {
    			var 	jsNewSrNo = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			var jsNewProductType = x[5].getElementsByTagName("div")[0].value;
     			} else {
    				var jsNewProductType = x[5].getElementsByTagName("div")[0].value.textContent;
    				
				}
				//for hidden  new product id 
				var jsNewProductId = x[5].getElementsByTagName("input")[0].value;
				//ends
				
				
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
				
				if(document.all){	 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		
     			var customerId= x[1].getElementsByTagName("div")[0].value;}
				} else {
    				 if(typeof( x[1].getElementsByTagName("div")[0])!="undefined")
			{    
     		var customerId = x[1].getElementsByTagName("div")[0].value.textContent;}
				}
				
				//check below code for upgrade
				
				if(document.all){
     			var jsReqId= x[10].getElementsByTagName("div")[0].value;//changed from 11 to 10
				} else {
    			var jsReqId = x[10].getElementsByTagName("div")[0].value.textContent;//changed from 11 to 10
				}
				
				//Added by Neetika
				if(document.all){
     				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{    
     		var	jsRejectedDC = x[9].getElementsByTagName("div")[0].value;
     		}
				} else {
    				 if(typeof( x[9].getElementsByTagName("div")[0])!="undefined")
			{    
     		var	jsRejectedDC = x[9].getElementsByTagName("div")[0].value;
     		
     		}
	
			}		//end neetika
				
				//deleteRowDataTable3(collectionType,productType);
				
			var table2 = document.getElementById("dataTableNew");
			var rowValue =table2.rows.length;
		   	var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType +  "\">" + jsCollectionType + "</div><input type=\"hidden\" value=\"" + jsCollectionId + "\" />";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + customerId +  "\">" + customerId + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsDefectSrNo +  "\">" + jsDefectSrNo+ "</div>";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsDefectProductType +  "\">" + jsDefectProductType + "</div> <input type=\"hidden\" value=\"" + jsDefectProductId + "\" />";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsNewSrNo +  "\">" + jsNewSrNo + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsNewProductType +  "\">" + jsNewProductType + "</div> <input type=\"hidden\" value=\"" + jsNewProductId + "\" />";
			
			var cell7 = row2.insertCell(6);
			cell7.innerHTML = "<div value=\"" + jsInventoryChangeDate +  "\">" + jsInventoryChangeDate + "</div>";
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div value=\"" + ageing +  "\">" + ageing + "</div>";
			
			//Neetika BFR 9
			var cell12 = row2.insertCell(8);
			//call Ajax for defect Dropdown
			cell12.innerHTML = "<div value=\"" + jsRejectedDC +  "\">" + jsRejectedDC + "</div>";
			
			//var cell8 = row2.insertCell(7);
			//cell8.innerHTML = "<div value=\"" + defectType +  "\">" + defectType + "</div>";
			
			var cell9 = row2.insertCell(9);//chnaged by neetika from 8 to 9
			
			cell9.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + jsCollectionId + "\" />";
			 var cell10 = document.createElement("td");    
			
			cell10.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + jsReqId +  "\">" + jsReqId + "</div>";
			cell10.setAttribute('class','widthzero'); 
			cell10.setAttribute('display','none'); 
			cell10.setAttribute('style','display:none'); 
			
			cell10.setAttribute('width','0'); 
			cell10.style.display = 'none';
			row2.appendChild(cell10);
	}
			
	}
}
		
	//to delete row from 2nd table
	collectionType=x[0].getElementsByTagName("div")[0].value;
	productType = x[3].getElementsByTagName("div")[0].value;
	deleteRowDataTable3(collectionType,productType);
 		table.deleteRow(rowCount2);	
 		 var rowValue =table.rows.length;
		alternate();
	}
 
 function alternate(){
   if(document.getElementsByTagName){  
   var table = document.getElementById("dataTableNew");  
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
 
 function addRow() 
{
  if(validateData()==1)
  {
	addRowToDataTable();
	resetValues();
  }
}
 
 function addRowToDataTable() 
	{
		try 
		{
			 var table = document.getElementById("dataTable");
			 var row;
			 var rowCount;
			 var addedRow =0;
			 // Add by chunk of products
			 //alert("addRowToDataTable");
			 var collectSerialsNoBox = document.getElementById("collectSerialsNoBox");
			 for (var intLoop=0; intLoop < collectSerialsNoBox.length;intLoop++) 
			 {
		         if (collectSerialsNoBox.options[intLoop]!=null) 
		         {
		            // Split product from serials
		            var serialProductStr = collectSerialsNoBox.options[intLoop].value;
					var serialProductText = serialProductStr.split("#")
					var collectIonId = serialProductText[0];
					var collectionName = serialProductText[1];
					var defectId = serialProductText[2];
					var defectName = serialProductText[3];
					var productId = serialProductText[4];
		            var productName = serialProductText[5];
		            var serialNo = serialProductText[6];
		            var reqId=0;
		          var blank='';
		          var abc=null;
		          
		            rowCount = table.rows.length;
		            
		          row = table.insertRow(rowCount);
						
					var cell1 = row.insertCell(0);
					
					cell1.innerHTML = "<div value=\"" + collectionName +  "\">" + collectionName + "</div><input type=\"hidden\" value=\"" + collectIonId + "\" />";
					
					var cell2 = row.insertCell(1);
				cell2.innerHTML="<div value=\"\">&nbsp;</div>";
				
					// Collect serial numbers
					var cell3 = row.insertCell(2);
					cell3.innerHTML = "<div value=\"" + serialNo +  "\">" + serialNo + "</div>";
					
					var cell4 = row.insertCell(3);
					cell4.innerHTML = "<div value=\"" + productName +  "\">" + productName + "</div><input type=\"hidden\" value=\"" + productId + "\" />";
				 //for new Sr no and product
					var cell5 = row.insertCell(4);
				cell5.innerHTML = "<div value=\"\">&nbsp;</div>";
			

					var cell6 = row.insertCell(5);
					cell6.innerHTML= "<div value=\"\">&nbsp;</div>";
									// Ends
				var cell7 = row.insertCell(6);
				cell7.innerHTML= "<div value=\"\">&nbsp;</div>";
				
				//for ageing
				var cell8 = row.insertCell(7);
				cell8.innerHTML= "<div value=\"\">&nbsp;</div>";
				
					var cell9 = row.insertCell(8);
					if(collectIonId!=4)
					cell9.innerHTML = "<div value=\"" + defectName +  "\">" + defectName + "</div><input type=\"hidden\" value=\"" + defectId + "\" />";
					else
						cell9.innerHTML =  "<div value=\"\">&nbsp;</div>";
					//Neetika	Rejected DC			
					var cell13 = row.insertCell(9);
					cell13.innerHTML= "<div value=\"\">&nbsp;</div>";					
																	
					var cell10 = row.insertCell(10);//changed from 9 to 10
					cell10.innerHTML = "<div class='remarks' value=\"" + document.getElementById("remarks").value +  "\">" + document.getElementById("remarks").value + "</div>";
					
					var cell11 = row.insertCell(11);//changed from 10 to 11
					var image = document.createElement("img");
					image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
					image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
										
					cell11.appendChild(image);
					
				 var cell12 = document.createElement("td");    
			
			cell12.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + reqId +  "\">" + reqId + "</div>";
			cell12.setAttribute('class','widthzero'); 
			cell12.setAttribute('display','none'); 
			cell12.setAttribute('width','0'); 
			cell12.setAttribute('style','display:none'); 
			cell12.style.display = 'none';
			
			row.appendChild(cell12);
					loopDataTable3(collectionName,productName);
					
		         }
		     }
		     var table2 = document.getElementById("dataTable");
			 var rowValue =table2.rows.length;
			 
     		} 
			catch(error) 
			{
				//alert(error.message);
			}
	}
 
 
 function validateData()
{
	 if(document.getElementById("collectSerialsNoBox").length == 0) 
	 {
	 	alert('Please enter atleast one collect serial ');	 
	 	document.getElementById("collectSerialNo").focus();	
	 	return false;	
	 }
	 
	 var remarks = document.getElementById("remarks").value;
	 if(remarks == '' || trimAll(remarks) == "")
	 {
	   alert ("Please enter remark.");
	   document.getElementById("remarks").focus();
	   return false;
	 }
	 	 	 
	 return 1;
}
 
 function resetValues()
{
     
     var collectSerialsNoBox = document.getElementById("collectSerialsNoBox");
	 for (var intLoop=0; intLoop < collectSerialsNoBox.length;intLoop++) 
	 {
         if (collectSerialsNoBox.options[intLoop]!=null) 
         {
             collectSerialsNoBox[intLoop]=null;
             intLoop=-1;
         }
     }
     
     document.getElementById("collectionId").value=0;
    // document.getElementById("defectId").value=0;     
	 document.getElementById("productId").value=0;     
	 document.getElementById("collectSerialNo").value="";     
	// document.getElementById("collectionCount").value=0;     
	 document.getElementById("remarks").value="";   
	
    
	   
}
 
 function submitDataTableRows() 
{
		var table = document.getElementById("dataTable");
		var rowCount = table.rows.length;
		var i=0;
		var jsArrCollectionId = new Array();
		var jsArrDefectId = new Array();
		var jsArrProductId = new Array();
		var jsArrSerails = new Array();
		var jsArrRemarks = new Array();
		//Added for New Format
		var jsArrNewSrNo = new Array();
		var jsArrNewProduct = new Array();
		var jsArrAgeing = new Array();
		var jsArrInventoryCahngDate = new Array();
		var jsArrCustomerId= new Array();
		var jsArrReqId= new Array();
		//alert("Before setting rowCount"+rowCount);
		while (i+1<rowCount)
		{
			//alert("1111111");
			var x = document.getElementById("dataTable").rows[i+1].cells;
			jsArrDefectId[i] ="0";
     		// This is for Hidden parameter
     		//alert("Before setting coll id "+x[0].getElementsByTagName("input")[0].value);
     		jsArrCollectionId[i] = x[0].getElementsByTagName("input")[0].value;
     	     		
     	if(jsArrCollectionId[i] ==2 || jsArrCollectionId[i]==4){
     	//alert("Before setting coll id ");
     	    		//alert("Before setting coll id.. "+x[8].getElementsByTagName("input")[0].value);
     			 if(typeof( x[8].getElementsByTagName("input")[0])!="undefined")
			{    
     		jsArrDefectId[i] = x[8].getElementsByTagName("input")[0].value;
     		}else
     		{
     		jsArrDefectId[i]="0";
     		}
     		jsArrProductId[i] = x[3].getElementsByTagName("input")[0].value;
     	 //alert("Before setting coll id "+x[0].getElementsByTagName("input")[0].value);		
			// work for all browser firefox
			if(document.all){
			if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
			    {
     			jsArrSerails[i] = x[2].getElementsByTagName("div")[0].value;
     			} 
			} else {
			if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
			    {
     		
    			jsArrSerails[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    			}
			}
    		//alert('Serails ' + jsArrSerails[i]);
			 //alert("Before setting remakrs "+x[9].getElementsByTagName("div")[0].value);	
			 //changed from 9 to 10	
			if(document.all){
			    if(typeof( x[10].getElementsByTagName("div")[0])!="undefined")
			    	jsArrRemarks[i] = x[10].getElementsByTagName("div")[0].value;
			   else
			    	jsArrRemarks[i]='  ';
			    if(jsArrRemarks[i] == null || jsArrRemarks[i] == '' )
     			{
     			 jsArrRemarks[i]='  ';
     			}
     			//alert("jsArrRemarks[i] line 1486"+jsArrRemarks[i]);
			} else {
			 if(typeof( x[10].getElementsByTagName("div")[0])!="undefined")
			    	jsArrRemarks[i] = x[10].getElementsByTagName("div")[0].value.textContent;
			   else
			    	jsArrRemarks[i]='  ';
    			if(jsArrRemarks[i] == null || jsArrRemarks[i] == '')
     			{
     			 jsArrRemarks[i]='  ';
     			}
    			//alert("jsArrRemarks[i] line 1496"+jsArrRemarks[i]);
			}
			
			
			
			jsArrNewSrNo[i] ="-1";
			jsArrAgeing[i] ="-1";
			jsArrInventoryCahngDate[i]="-1";
			jsArrNewProduct[i]="-1";
			jsArrReqId[i]="0";
     		//alert('Remarks ' + jsArrRemarks[i]);
		
			}else if(jsArrCollectionId[i] ==1 || jsArrCollectionId[i]==3|| jsArrCollectionId[i]==5 || jsArrCollectionId[i]==6){
     		if(document.all){
     			jsArrNewSrNo[i] = x[4].getElementsByTagName("div")[0].value;
			} else {
    			jsArrNewSrNo[i] = x[4].getElementsByTagName("div")[0].value.textContent;
			}
     		 jsArrNewProduct[i] = x[5].getElementsByTagName("input")[0].value;
			if(document.all){
     			jsArrAgeing[i] = x[7].getElementsByTagName("div")[0].value;
			} else {
    			jsArrAgeing[i] = x[7].getElementsByTagName("div")[0].value.textContent;
			}
			if(document.all){
     			jsArrInventoryCahngDate[i] = x[6].getElementsByTagName("div")[0].value;
			} else {
    			jsArrInventoryCahngDate[i] = x[6].getElementsByTagName("div")[0].value.textContent;
			}
		// alert("Before setting remakrs in other colls");
		if(document.all){
		    	jsArrReqId[i] = x[12].getElementsByTagName("div")[0].value;//changed from 11 to 12
			} else {
    			jsArrReqId[i] = x[12].getElementsByTagName("div")[0].value.textContent;//changed from 11 to 12
			}
		
     		
     		if(jsArrCollectionId[i]!=5){
     		 if(typeof( x[8].getElementsByTagName("input")[0])!="undefined")
			{    
     		jsArrDefectId[i] = x[8].getElementsByTagName("input")[0].value;
     		}
     		else{
     		jsArrDefectId[i]="0";
     		}
     		}else if(jsArrCollectionId[i]==5){
     		
     		 if(typeof( x[8].getElementsByTagName("input")[0])!="undefined")
			{    
     		jsArrDefectId[i] = "0";
     		}
     		
     		}
     		
     		jsArrProductId[i] = x[3].getElementsByTagName("input")[0].value;
     	
     		   		
			// work for all browser firefox
			if(document.all){
			if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
			    {
     			jsArrSerails[i] = x[2].getElementsByTagName("div")[0].value;
     			}
			} else {
			if(typeof( x[2].getElementsByTagName("div")[0])!="undefined")
			    {
     		
    			jsArrSerails[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    			}
			}
    		//alert('Serails ' + jsArrSerails[i]);
			//alert("Before setting remakrs..... "+x[9].getElementsByTagName("div")[0].value);	
			//changed from 9 to 10	
			if(document.all){
			    if(typeof( x[10].getElementsByTagName("div")[0])!="undefined")
			    	jsArrRemarks[i] = x[10].getElementsByTagName("div")[0].value;
			   else
			    	jsArrRemarks[i]='  ';
			    if(jsArrRemarks[i] == null || jsArrRemarks[i] == '' )
     			{
     			 jsArrRemarks[i]='  ';
     			}
     			//alert("jsArrRemarks[i] line 1576"+jsArrRemarks[i]);
			} else {
			 if(typeof( x[10].getElementsByTagName("div")[0])!="undefined")
			    	jsArrRemarks[i] = x[10].getElementsByTagName("div")[0].value.textContent;
			   else
			    	jsArrRemarks[i]='  ';
    			if(jsArrRemarks[i] == null || jsArrRemarks[i] == '')
     			{
     			 jsArrRemarks[i]='  ';
     			}
    				//alert("jsArrRemarks[i] line 1586"+jsArrRemarks[i]);
			}
     		if(document.all){
			    jsArrCustomerId[i] = x[1].getElementsByTagName("div")[0].value;
			    if(jsArrCustomerId[i] == null || jsArrCustomerId[i] == '')
     			{
     			 jsArrCustomerId[i]='  ';
     			}
			} else {
    			jsArrCustomerId[i] = x[1].getElementsByTagName("div")[0].value.textContent;
    			if(jsArrCustomerId[i] == null || jsArrCustomerId[i] == '')
     			{
     			 jsArrCustomerId[i]='  ';
     			}
    			
			}//alert('Remarks ' + jsArrCustomerId[i]);
		
		
     	}
     		
			i++;
  		}
  		//alert("Before setting "+jsArrCollectionId[0]);
  		document.forms[0].jsArrCollectionId.value=jsArrCollectionId;
  		document.forms[0].jsArrDefectId.value=jsArrDefectId;
  		document.forms[0].jsArrProductId.value=jsArrProductId;
  		document.forms[0].jsArrSerails.value=jsArrSerails;
  		document.forms[0].jsArrRemarks.value=jsArrRemarks;
  		
  		document.forms[0].jsArrNewSrNo.value=jsArrNewSrNo;
  		document.forms[0].jsArrNewProduct.value=jsArrNewProduct;
  		document.forms[0].jsArrAgeing.value=jsArrAgeing;
  		document.forms[0].jsArrInventoryCahngDate.value=jsArrInventoryCahngDate;
  		document.forms[0].jsArrCustomerId.value=jsArrCustomerId;
  		document.forms[0].jsArrReqId.value=jsArrReqId;
  		
     	
  		//alert("Before setting "+document.forms[0].jsArrCollectionId.value);
  	    if((document.forms[0].jsArrCollectionId.value).length >0)
  	    {
  			return true;
  		}
  		else
  		{
  		   alert("Please Add atleast One Record!!")
  		   return false;
  		}
  		
  		
  		
	}
 
 // **************************
   function goOther()
   {
   		
   		//document.forms[0].testParam.value="Hi";
   		//document.forms[0].returnurl.value="http://10.24.60.82:9090/DPDTH/jsp/common/home.jsp";
   		
   		document.write("<form name='testForm' action='http://10.24.60.94:9080/DPDTH/LoginAction.do' method='post'>");
   		document.write("<input type='text' name='testtext' /> ");
   		document.write("<input type='hidden' name='testParam' value='goDirect' /> ");
   		document.write("<input type='hidden' name='username' value='<%=username%>' /> ");
   		document.write("<input type='hidden' name='returnurl' value='http://10.24.60.82:9090/DPDTH/jsp/common/home.jsp' /> ");
		

   		//document.forms[0].action="http://10.24.60.94:9080/DPDTH/";
   		//document.forms[0].method="POST";
   		   		
		document.testForm.submit();   		
   }
   
   
    function validateCollectSerialNoAllType(serialNo, productId)
	{
	    var collectionId = document.getElementById("collectionId").value;
	    var url = "ReverseCollection.do?methodName=validateCollectSerialNoAllType&serialNo="+serialNo+"&productId="+productId;
		
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
		req.onreadystatechange = getvalidateCollectSerialNoAllType;
		req.open("POST",url,false);
		req.send();
	}

	function getvalidateCollectSerialNoAllType()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
				
			var optionValuesTxt1 = xmldoc.getElementsByTagName("optionCollectUniqueFlag");
			var selectObj1 = document.getElementById("collectUniqueFlag");
			selectObj1.value = optionValuesTxt1[0].getAttribute("value");
	}
}

	function validateCollectSerialNoDAO(serialNo, productId)
	{
	    var collectionId = document.getElementById("collectionId").value;
		var url = "ReverseCollection.do?methodName=validateCollectSerialNoDAOType&serialNo="+serialNo+"&productId="+productId;
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
		req.onreadystatechange = getValidateCollectSerialNoDAOAJAX;
		req.open("POST",url,false);
		req.send();
	}

	function getValidateCollectSerialNoDAOAJAX()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
											
			var optionValuesTxt = xmldoc.getElementsByTagName("optionCollectFlag");
			var selectObj = document.getElementById("collectDAOFlag");
			
			selectObj.value = optionValuesTxt[0].getAttribute("value");
			
	}
}


	function validateCollectSerialNoC2SType(serialNo)
	{
	    var collectionId = document.getElementById("collectionId").value;
		var url = "ReverseCollection.do?methodName=validateCollectSerialNoC2SType&serialNo="+serialNo;
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
		req.onreadystatechange = validateCollectSerialNoC2STypeAJAX;
		req.open("POST",url,false);
		req.send();
	}

	function validateCollectSerialNoC2STypeAJAX()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
											
			var optionValuesTxt = xmldoc.getElementsByTagName("optionCollectC2SFlag");
			var selectObj = document.getElementById("collectC2SFlag");
			selectObj.value = optionValuesTxt[0].getAttribute("value");
	}
}

	function getCollectionDefectType()
	{
	   
	    var collectionId = document.getElementById("collectionId").value;
	    if(collectionId==2)
	   { document.getElementById("defectId").value='0';
	    }
	   // alert("Collection Id: " + collectionId);
	    // In case of 'DEAD ON ARRIVAL' and 'DEFFECTIVE' .
	    if(collectionId == 2)
	    {
	    	//document.getElementById("defectId").disabled = false;
			//document.getElementById("defectId").focus();	    	
	    	var url = "ReverseCollection.do?methodName=getCollectionDefectType&collectionId="+collectionId;
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
			req.onreadystatechange = getDefectTypeAJAX;
			req.open("POST",url,false);
			req.send();

		}

		else
		{
		 // document.getElementById("defectId").disabled = true;
		}
		
	}
function printPage()
{
 	if(document.forms[0].takePrint.value !=""){
			
			var url="DPPrintReverseCollectionAction.do?methodName=printReverseCollection";
			window.open(url,"ReverseUpgradeCollection","width=700,height=600,scrollbars=yes,toolbar=no");	
			
				}
  			}
	function getDefectTypeAJAX()
	{
		// Get defect type 
		//alert("getDefectTypeAJAX");
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get all serials Nos.
			
			var optionValues = xmldoc.getElementsByTagName("option");
			var distType = document.getElementById("typedist").value;
			
			distType=optionValues[0].getAttribute("CPEFlag");
		
			//alert("distType in doabi ::"+distType);
			if(distType==0)
			{
			alert("You are not of CPE Type. Please get your ID created by ID Helpdesk");
			return false;
			}
			else
			{
			
			var selectObj = document.getElementById("defectId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Defect ","0");
			for(var i=0; i<optionValues.length; i++)
			{
			    selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			
			// Get all product Nos.
			var optionValues = xmldoc.getElementsByTagName("optionProduct");
			var selectObj = document.getElementById("productId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Product ","0");
			for(var i=0; i<optionValues.length; i++)
			{
			    selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			}
		}
		
		 
	}

	function getCollectionProduct()
	{
	    var collectionId = document.getElementById("collectionId").value;
			
    	var url = "ReverseCollection.do?methodName=getCollectionProduct&collectionId="+collectionId;
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
		req.onreadystatechange = getCollectionProductAJAX;
		req.open("POST",url,false);
		req.send();

	}

	function getCollectionProductAJAX()
	{
		// Get defect type 
		if (req.readyState==4 || req.readyState=="complete") 
	  	{

			var xmldoc = req.responseXML.documentElement;
			
			if(xmldoc == null) 
			{
				return;
			}
						
			// Get all product Nos.
			var optionValues = xmldoc.getElementsByTagName("optionProduct");
			var selectObj = document.getElementById("productId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Product ","0");
			//alert('Length : ' + optionValues.length);
			for(var i=0; i<optionValues.length; i++)
			{
				//alert('Value  ' + optionValues[i].getAttribute("value"));
				//alert('Text  ' + optionValues[i].getAttribute("text"));
			
			    selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
	}
}
//Added by Shilpa khanna  for Inventory Chane

function getInventoryChangeList()
	{
	    var collectionId = document.getElementById("collectionId").value;
			
   	var url = "ReverseCollection.do?methodName=getInventoryList&collectionId="+collectionId;

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
		
		req.onreadystatechange = getInventoryChangeListAJAX;
		req.open("POST",url,false);
		req.send();
			
	}

//  call my Ajax
	function getInventoryChangeListAJAX()
	{
		// Get defect type 
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			
			if(xmldoc == null) 
			{
				return;
			}
						
			// Get all product Nos.optionflag
			
		var optionValues = xmldoc.getElementsByTagName("optionProduct");
		var optionValuesDefect = xmldoc.getElementsByTagName("optionDefect");
		
		var selectObj = document.getElementById("dataTableNew");
		var rowCount2 = selectObj.rows.length;
		var optionflag = xmldoc.getElementsByTagName("optionflag");
		
		var distType = document.getElementById("typedist").value;
		
		distType=optionflag[0].getAttribute("CPEFlag");
		
		//alert("distType::"+distType);
		if(distType==0)
		{
		alert("You are not of CPE Type. Please get your ID created by ID Helpdesk");
		return false;
		}
		else
		{
		if(rowCount2 >1){
			var rowdelete =rowCount2-1;
			 for(var i=0;i<rowCount2-1;i++){
				document.getElementById("dataTableNew").deleteRow(rowdelete);
				rowdelete --;
			}
			
			}
			 var row;
			 var rowCount;
			 for (var intLoop=0; intLoop < optionValues.length;intLoop++) 
			 {
		        	var collectionType = optionValues[intLoop].getAttribute("collectionType");
					var collectionId = optionValues[intLoop].getAttribute("collectionId");
					var defectiveSerialNo = optionValues[intLoop].getAttribute("defectiveSerialNo");
					var defectiveProductId = optionValues[intLoop].getAttribute("defectiveProductId");
					var defectiveProductName = optionValues[intLoop].getAttribute("defectiveProductName");
					var changedSerialNo = optionValues[intLoop].getAttribute("changedSerialNo");
		            var changedProductId = optionValues[intLoop].getAttribute("changedProductId");
		            var changedProductName = optionValues[intLoop].getAttribute("changedProductName");
		            var inventoryChangedDate = optionValues[intLoop].getAttribute("inventoryChangedDate");
		            var ageing = optionValues[intLoop].getAttribute("ageing");
		            var customerId = optionValues[intLoop].getAttribute("customerId");
		            var rejectedDC = optionValues[intLoop].getAttribute("rejectedDC");//Neetika
		            if(collectionId!=2){
		             	var defectId = optionValues[intLoop].getAttribute("defectId");
		            	var defectName = optionValues[intLoop].getAttribute("defectName");
		           }
		          var reqId = optionValues[intLoop].getAttribute("reqId");
		              
		            if(collectionId ==5 ){
		            
		             rowCount = selectObj.rows.length;
					 row = selectObj.insertRow(rowCount);
						
					var cell1 = row.insertCell(0);
					cell1.innerHTML = "<div value=\"" + collectionType +  "\">" + collectionType + "</div><input type=\"hidden\" value=\"" + collectionId + "\" />";
					
					var cell2 = row.insertCell(1);
					cell2.innerHTML = "<div value=\"" + customerId +  "\">" + customerId + "</div>";
					
					var cell3 = row.insertCell(2);
					cell3.innerHTML = "<div value=\"" + defectiveSerialNo +  "\">" + defectiveSerialNo + "</div>";
											
					var cell4 = row.insertCell(3);
					cell4.innerHTML = "<div value=\"" + defectiveProductName +  "\">" + defectiveProductName + "</div><input type=\"hidden\" value=\"" + defectiveProductId + "\" />";
										
					// Collect serial numbers
					var cell5 = row.insertCell(4);
					cell5.innerHTML = "<div value=\"" + changedSerialNo +  "\">" + changedSerialNo + "</div>";
					
					var cell6 = row.insertCell(5);
					cell6.innerHTML = "<div value=\"" + changedProductName +  "\">" + changedProductName + "</div><input type=\"hidden\" value=\"" + changedProductId + "\" />";
					
					var cell7 = row.insertCell(6);
					cell7.innerHTML = "<div value=\"" + inventoryChangedDate +  "\">" + inventoryChangedDate + "</div>";
					
					var cell8 = row.insertCell(7);
					cell8.innerHTML = "<div value=\"" + ageing +  "\">" + ageing + "</div>";
					
					
					//var defectType = "";
					//var cell8 = row.insertCell(7);
					//cell8.innerHTML = "<div value=\"" + defectType +  "\">" + defectType + "</div>";
					
				 	//var cell9 = row.insertCell(8);
					//cell9.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + intLoop + "\" />";
			       //Added by neetika
					var cell112 = row.insertCell(8);
					cell112.innerHTML = "<div value=\"" + rejectedDC +  "\">" + rejectedDC + "</div>";
					//end by neetika
			       
			       
			       var cell9 = row.insertCell(9);//changed by Neetika
					cell9.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + intLoop + "\" />";
			       
		            var cell10 = document.createElement("td");    
			  
			cell10.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + reqId +  "\">" + reqId + "</div>";
			cell10.setAttribute('class','widthzero'); 
			cell10.setAttribute('display','none'); 
			cell10.setAttribute('width','0'); 
			cell10.setAttribute('style','display:none'); 
			cell10.style.display = 'none';
			row.appendChild(cell10);
		            }
		           
		           else{
		           
		            rowCount = selectObj.rows.length;
					row = selectObj.insertRow(rowCount);
						
					var cell1 = row.insertCell(0);
					
					cell1.innerHTML = "<div value=\"" + collectionType +  "\">" + collectionType + "</div><input type=\"hidden\" value=\"" + collectionId + "\" />";
					
					var cell2 = row.insertCell(1);
					cell2.innerHTML = "<div value=\"" + customerId +  "\">" + customerId + "</div>";
					
					var cell3 = row.insertCell(2);
					cell3.innerHTML = "<div value=\"" + defectiveSerialNo +  "\">" + defectiveSerialNo + "</div>";
											
					var cell4 = row.insertCell(3);
					cell4.innerHTML = "<div value=\"" + defectiveProductName +  "\">" + defectiveProductName + "</div><input type=\"hidden\" value=\"" + defectiveProductId + "\" />";
										
					// Collect serial numbers
					var cell5 = row.insertCell(4);
					cell5.innerHTML = "<div value=\"" + changedSerialNo +  "\">" + changedSerialNo + "</div>";
					
					var cell6 = row.insertCell(5);
					cell6.innerHTML = "<div value=\"" + changedProductName +  "\">" + changedProductName + "</div><input type=\"hidden\" value=\"" + changedProductId + "\" />";
					
					var cell7 = row.insertCell(6);
					cell7.innerHTML = "<div value=\"" + inventoryChangedDate +  "\">" + inventoryChangedDate + "</div>";
					
					var cell8 = row.insertCell(7);
					cell8.innerHTML = "<div value=\"" + ageing +  "\">" + ageing + "</div>";
					
					
					
					var cell9 = row.insertCell(8);
					
					if(collectionId==2){
					var data = "<select id=\"defect\"  name=\"defect\" > <option value=\"0\" >  --Select-- </option> ";
					 for (var intLoop2=0; intLoop2 < optionValuesDefect.length;intLoop2++) 
			 		{
			 			 var text = optionValuesDefect[intLoop2].getAttribute("text");
						var value = optionValuesDefect[intLoop2].getAttribute("value");
					    var drpValue =  value +"#"+ text;
			 			data = data + " <option value=\""+  drpValue  +"\" >"+ text +"</option>"; 
			 		}	
					data = data + " </select> ";
					cell9.innerHTML = data;
					}
					else
					{
					cell9.innerHTML = "<div value=\"" + defectId +  "\">" + defectName + "</div>";
					
					}
					/*
				 	var cell10 = row.insertCell(9);
					cell10.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + intLoop + "\" />";
			       */
					//Added by neetika
					var cell112 = row.insertCell(9);
					cell112.innerHTML = "<div value=\"" + rejectedDC +  "\">" + rejectedDC + "</div>";
					//end by neetika
				 	var cell10 = row.insertCell(10);//changed by neetika from 9 to 10
					cell10.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + intLoop + "\" />";
					 var cell11 = document.createElement("td");    
			  
			cell11.innerHTML =  "<div class='widthzero' style='display:none' value=\"" + reqId +  "\">" + reqId + "</div>";
			cell11.setAttribute('class','widthzero'); 
			cell11.setAttribute('display','none'); 
			cell11.setAttribute('width','0'); 
			cell11.setAttribute('style','display:none'); 
			cell11.style.display = 'none';
			row.appendChild(cell11);
		           }
		          
		     }
	}//Neetika	
	}
}





	function validateSerialNumber(collSRNo, collID, prodID)
	{
    	var url = "ReverseCollection.do?methodName=validateSerialNumber&collSRNo="+collSRNo+"&collID="+collID+"&prodID="+prodID;
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
		req.onreadystatechange = validateSerialNumberAJAX;
		req.open("POST",url,false);
		req.send();
	}
	
	function validateSerialNumberAJAX()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
											
			var optionValuesTxt = xmldoc.getElementsByTagName("message");
			document.getElementById("collectC2SFlag").value = optionValuesTxt[0].getAttribute("value");;
		}
	}
function assignCollectSerials(collectBox)
{
	var collID = document.getElementById("collectionId").value;
	if(collID==2)
		var defectID = document.getElementById("defectId").value;
	var collSRNo = trim(document.getElementById("collectSerialNo").value);
	var prodID = document.getElementById("productId").value;
	var collName, defectName, prodName;
	
	
	//alert("collID  ::  "+collID+"\ndefectID  ::  "+defectID+"\ncollSRNo  ::  "+collSRNo+"\nprodID  ::  "+prodID);
	
	if(collID==0)
	{
		alert("Select a Collection Type");
		return false;	
	}
	else if(collID==2)
	{
		if(defectID==0)
		{
			alert("Select a Defect Type");
			return false;
		}
	}
	if(collSRNo=='')
	{
		alert("Enter a Serial Number");
		return false;
	}
	else 
	{
		if(!isNumeric(collSRNo))
		{
		  alert('Received serial number should be numeric.');
		  document.getElementById("collectSerialNo").focus();
		  return false;
		}
		
		if(collSRNo.length != 11)
		{
		  alert('Received serial number should not be less than 11 digits.');
		  document.getElementById("collectSerialNo").focus();
		  return false;
		}	
	}
	
	if(prodID==0)
	{
		alert("Select a Product");
		return false;
	}
	
	// Check same serial not allowed in list-box
	for(var intLoop=0; intLoop<collectBox.length; intLoop++) 
	{
		if (collectBox.options[intLoop]!=null) 
		{
		   	var serialProductStr = collectBox.options[intLoop].value;
		   	var serialProductText = serialProductStr.split("#")
		   	var tempSerialNo = serialProductText[6];
			if(tempSerialNo == collSRNo)
	      	{
        		alert('Given serial no already Received.');
        		return false;     
      		}
   		}
	}
	
	// Check same serial not alowed in Grid
	var table = document.getElementById("dataTable");
	var rowCount = table.rows.length;
	var i=0;

	while(i+1<rowCount)
	{
		var x = document.getElementById("dataTable").rows[i+1].cells;
		var tempSerialNo = x[1].getElementsByTagName("div")[0].value;
		if(tempSerialNo == collSRNo)
        {
        	alert('Given serial no allready received in grid.');
          	return false;     
        }
        i++;
	}
	

	// Check server side validation on collection for allready collected serial for same product.
	// For each collection type
	/* Commente by Nazim Hussain as all validation have been implemented in one go using validateSerialNumber method below
		validateCollectSerialNoAllType(collSRNo, prodID);	
		var collectUniqueFlag = document.getElementById("collectUniqueFlag").value;
		if(collectUniqueFlag == 'TRUE')
		{
			alert('Collect serial No allready collected for same product in inventory.');
			document.getElementById("productId").value = 0;
			document.getElementById("collectSerialNo").value = '';
			return false;
		}
	*/
	//Check for already received numbers
	validateSerialNumber(collSRNo, collID, prodID);
	var valid = document.getElementById("collectC2SFlag").value;
	
	if(valid=='SUCCESS')
	{
		collName = document.getElementById("collectionId").options[document.getElementById("collectionId").selectedIndex].text;
		if(defectID==0)
		{
			defectName = '&nbsp;';
		}
		else
		{
		    defectName = document.getElementById("defectId").options[document.getElementById("defectId").selectedIndex].text;
		}
		
		prodName = document.getElementById("productId").options[document.getElementById("productId").selectedIndex].text;
				
		var boxLength = collectBox.length;
 		collectBox[boxLength] = new Option(collSRNo);
	 	collectBox[boxLength].value = collID+'#'+collName+'#'+defectID+'#'+defectName+'#'+prodID+'#'+prodName+'#'+collSRNo;
 	
	 	// Increase product count
	 	//document.getElementById("collectionCount").value=boxLength+1;
	 	
	 	// Reset Values
	 	document.getElementById("collectSerialNo").value="";
	 	document.getElementById("productId").value=0;
	 }
	 else
	 {
	 	alert(valid);
	 	return false;
	 }
	 
}


	function clearCollectSerials(collectClearBox)
	{
	   for (var intLoop=0; intLoop < collectClearBox.length; intLoop++) 
	   {
	        if (collectClearBox.options[intLoop]!=null && collectClearBox.options[intLoop].selected==true) 
	        {
	            collectClearBox[intLoop]=null;
	            intLoop=-1;
	        }
	    }
	    
	    // Decrease product count
	    var boxLength = collectClearBox.length;
	    
 		//document.getElementById("collectionCount").value=boxLength;
	}

    
	//Added by Shilpa khanna  for Inventory Chane

function callDefectList(collectionId,cell8){
	{
	   var url = "ReverseCollection.do?methodName=getDefectList&collectionId="+collectionId;

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
		
		req.onreadystatechange = callDefectListAJAX;
		req.open("POST",url,false);
		req.send();
	}

//  call my Ajax
	function callDefectListAJAX()
	{
		
		// Get defect type 
		

		if (req.readyState==4 || req.readyState=="complete") 
	  	{
	  	
	  	
	  		var xmldoc = req.responseXML.documentElement;
			
			if(xmldoc == null) 
			{
				return;
			}
		    var optionValuesDefect = xmldoc.getElementsByTagName("optionDefect");
		    var data = "<select id=\"defect\"  name=\"defect\" > <option value=\"0\" >  --Select-- </option> ";
					 for (var intLoop2=0; intLoop2 < optionValuesDefect.length;intLoop2++) 
			 		{
			 			var text = optionValuesDefect[intLoop2].getAttribute("text");
						var value = optionValuesDefect[intLoop2].getAttribute("value");
					    var drpValue =  value +"#"+ text;
			 			data = data + " <option value=\""+  drpValue  +"\" >"+ text +"</option>"; 
			 		}	
			 		data = data + " </select> ";
					cell8.innerHTML = data;
					
			}
		      
		          
		
	}
		
		

function maxlength() 
{
	
    var size=200;
    var remarks = document.getElementById("remarks").value;
  	if (remarks.length >= size) 
  	{
   	  remarks = remarks.substring(0, size-1);
   	  document.getElementById("remarks").value = remarks;
      alert ("Remarks  Can Contain 200 Characters Only.");
   	  document.getElementById("remarks").focus();
	  return false;
  	}
  	var iChars = "~`!@#$%^&*()-_=[{]}\|;:,<\/?\\";

	for (var i = 0; i < document.getElementById("remarks").value.length; i++) {
  	if (iChars.indexOf(document.getElementById("remarks").value.charAt(i)) != -1) 
  	{
  		alert("Special characters (` ~ ' ,) are not allowed.");
  		document.getElementById("remarks").value = remarks.substring(0, document.getElementById("remarks").value.length -1);
  		return false;
  	}
}	
} 



}


</script>
</head>
<BODY  background="<%=request.getContextPath()%>/images/bg_main.gif"  background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="printPage();">
	<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    
    
   
    
	%>
<html:form name="DPReverseCollectionBean" action="/ReverseCollection"
	type="com.ibm.dp.beans.DPReverseCollectionBean">
	<html:hidden property="methodName" styleId="methodName"
		value="submitReverseCollection" />
	<html:hidden property="jsArrCollectionId"
		name="DPReverseCollectionBean" />
	<html:hidden property="jsArrDefectId" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrProductId" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrSerails" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrRemarks" name="DPReverseCollectionBean" />
	<html:hidden property="collectDAOFlag" name="DPReverseCollectionBean" />
	<html:hidden property="collectUniqueFlag" name="DPReverseCollectionBean" />
	<html:hidden property="collectC2SFlag" name="DPReverseCollectionBean" />	
	
	<html:hidden property="jsArrNewSrNo" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrNewProduct" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrAgeing" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrInventoryCahngDate" name="DPReverseCollectionBean" />
	<html:hidden property="takePrint" styleId="takePrint" name="DPReverseCollectionBean"  />
	<html:hidden property="jsArrCustomerId" name="DPReverseCollectionBean" />
	<html:hidden property="jsArrReqId" name="DPReverseCollectionBean" />
		
<html:hidden property="typedist" 
		name="DPReverseCollectionBean"/>
 <img src="<%=request.getContextPath()%>/images/Reverse_Collection.jpg"
				width="544" height="25" alt="">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor='red'>
		<tr>
			<td colspan="6">
			<strong><font color="red" class="text" size="15px">
					<bean:write name="DPReverseCollectionBean" property="message"/>
					</font>
			</strong></td>
		</tr>
		<TR>
		<td colspan='6'>&nbsp;
		</td>
		</TR>
		<tr>
			<td colspan="6" class="text" align="center">
				<table width="100%" border="0" bordercolor='blue' cellspacing="0" cellpadding="0">
					<tr>
						<td width='16%'><strong><font color="#000000">
							<bean:message bundle="view" key="reverse.collection.CollectionType" /><font color="RED">*</font> :</font></strong></td>
	
	
						<td width='20%'><html:select property="collectionId" tabindex="1"
							onchange=" showTable();" style="width: 180px">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPReverseCollectionBean"
								property="reverseCollectionList">
								<html:options collection="reverseCollectionList"
									labelProperty="collectionName" property="collectionId" />
							</logic:notEmpty>
						</html:select></td>
	
					<td  width='10%' id="stCnt" style="display:none"><strong><font color="#000000">STB Count : </font></strong></td>
					<td  width='10%' style="display:none" id="stbCount"></td>	
						
				
			<td colspan="2" width='15%'>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	
						<td id="defectTypeId1"  style="display:none" width='15%' align='right'><strong><font color="#000000"><bean:message
							bundle="view" key="reverse.collection.DefectType" /> :</font></strong></td>
	
						<td  id="defectTypeId2"  style="display:none" width='36%'>&nbsp;&nbsp;<html:select property="defectId"
							style="width: 300px" >
							<html:option value="0">
								--Select--
							</html:option>
	
						</html:select></td>
							</tr>
				</table>
			</td>
		</tr>

		<TR>
		<td colspan='6'>&nbsp;
		</td>
		</TR>
				<!-- Extra Shipped -->
		<tr>
			<td colspan="6" align="center" width='100%'>
				<!-- Start of First Grid -->
				<div id="InputDiv">	
					<table border="3" width="100%">
						<tr>
							<td>
							<table border="0" bordercolor='GREEN' width="100%">

								<tr>
									<td colspan="2" width="35%"><font color="#000000"><strong><bean:message
										bundle="view" key="reverse.collection.CollectserialNo" /></strong></font></td>
									<td colspan="2" width="32%">&nbsp;&nbsp;</td>
									<td colspan="2" width="33%"><font color="#000000"><strong><bean:message
										bundle="view" key="reverse.collection.CollectedSerialNo" /></strong></font></td>
								</tr>

								<tr>
									<td colspan="2" >
									<table width="100%" border="0" bordercolor='BLUE'>
										<tr>
											<td ><strong><font
												color="#000000"><bean:message bundle="view"
												key="reverse.collection.SerialNo" /><font color="RED">*</font> :</font></strong></td>

											<td ><font color="#003399"> <html:text
												property="collectSerialNo" styleClass="box"
												style="width: 150px" maxlength="11"
												name="DPReverseCollectionBean"/> </font></td>

										</tr>
										<tr>
											<td ><strong><font
												color="#000000"><bean:message bundle="view"
												key="reverse.collection.Product" /><font color="RED">*</font> :</font></strong></td>

											<td ><html:select property="productId"
												style="width: 150px" >
												<html:option value="0">
													<bean:message key="application.option.select" bundle="view" />
												</html:option>
											</html:select></td>

										</tr>
									</table>
									</td>

									<td valign="middle" colspan="2" align="center"><input
										type="button"
										onclick="assignCollectSerials(collectSerialsNoBox)"
										class="btnActive mLeft5 mTop5 mBot5" value="  &gt;  "><br>
									<input type="button"
										onclick="clearCollectSerials(collectSerialsNoBox)"
										class="btnActive mLeft5 mTop5 mBot5" value="  Clear  ">
									</td>
									<td colspan="2" width="338"><font color="#3C3C3C"> 
									<html:select
										property="collectSerialsNoBox" size="4" multiple="true"
										style="width: 150px">
										<html:optionsCollection value="label" label="value"
											property="collectSerialsList" />
									</html:select> </font></td>
								</tr>
							</table>
							</td>
						</tr>
					</table> 
					
					</div> 
				<!--  End of First Grid -->
					
				<!-- Start of Second Grid -->	
				<div id="TableDiv"  style= "display: none; overflow: auto; width: 100%; height: 160px" >	
					<table border="3" width="100%">
						<tr>
							<td>
								<table id="dataTableNew" width="100%" height='100%' border="1" cellpadding="2" cellspacing="0" align="center" >
									<tr align="center" bgcolor="#CD0504"
										style="color:#ffffff !important;" class="noScroll">
										<td align="center" bgcolor="#CD0504" width="8%"><span
											class="white10heading mLeft5 mTop5"><strong><bean:message
											bundle="view" key="reverse.collection.CollectionType" /></strong></span></td>
										<td align="center" bgcolor="#CD0504" width="5%"><span
											class="white10heading mLeft5 mTop5"><strong>Customer Id</strong></span></td>
										<td align="center" bgcolor="#CD0504" width="8%"><span
											class="white10heading mLeft5 mTop5"><strong>Defective Serial Number</strong></span></td>
										<td align="center" bgcolor="#CD0504" width="10%"><span
											class="white10heading mLeft5 mTop5"><strong><bean:message
											bundle="view" key="reverse.collection.Product" /></strong></span></td>
										<td align="center" bgcolor="#CD0504" width="8%"><span
											class="white10heading mLeft5 mTop5"><strong>Changed Serial Number</strong></span></td>
										<td align="center" bgcolor="#CD0504" width="10%"><span
											class="white10heading mLeft5 mTop5"><strong><bean:message
											bundle="view" key="reverse.collection.Product" /></strong></span></td>
										<td align="center" bgcolor="#CD0504" width="8%"><span
											class="white10heading mLeft5 mTop5"><strong>Inventory Changed Date</strong></span></td>
										<td align="center" bgcolor="#CD0504" width="5%"><span
											class="white10heading mLeft5 mTop5"><strong>Ageing</strong></span></td>
										<td id ="defcType" align="center" bgcolor="#CD0504" width="25%"><span
											class="white10heading mLeft5 mTop5"><strong><bean:message
											bundle="view" key="reverse.collection.DefectType" /></strong></span></td>
											<!-- Added by Neetika 19-Feb -->
											
											<td id ="rejectedDC" align="center" bgcolor="#CD0504" width="10%"><span
											class="white10heading mLeft5 mTop5"><strong><bean:message
											bundle="view" key="reverse.collection.RejectedDC" /></strong></span></td>
										
												<!-- End by Neetika 19-Feb -->
										<td align="center" bgcolor="#CD0504" width="8%"><span
											class="white10heading mLeft5 mTop5"><strong>Select</strong></span></td>
										<td class='widthzero' style="display:none;" align="center" bgcolor="#CD0504" ><span
											class="white10heading mLeft5 mTop5"><strong></strong></span></td>
										
									</tr>
									
								</table>
							</td>
						</tr>
					</table>
				</div>  
				<!--  End of Second Grid -->
			</td>
		</tr>
		
		<TR>
		<td colspan='6'>&nbsp;
		</td>
		</TR>
		
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><font
				color="#000000"><bean:message bundle="view"
				key="reverse.collection.Remarks" /><font color="RED">*</font> : </font></strong></td>
			<td colspan='3' width='30%'><html:textarea
				name="DPReverseCollectionBean" property="remarks"
				style="width:290px" onkeyup="return maxlength();" /></td>
				
			<!--<td align='right'><strong><font
				color="#000000"><bean:message bundle="view"
				key="reverse.collection.Count" /> : </font></strong></td>
			<td >&nbsp;&nbsp;<input type="text" name="collectionCount"
				id="collectionCount" readonly="true"></td>
				
				
		-->
			<td class="txt" align="right"><input
				class="submit" type="button" value="Add to Grid"
				onclick="callTableAdded();"> &nbsp;&nbsp;&nbsp;</td>
				
			<td align="left"><input class="submit" id="ResetButton"
			type="button" onclick="resetValues();" name="Submit2" value="Reset"
			tabindex="5">
		
		</tr>
				<TR>
		<td colspan='6'>&nbsp;
		</td>
		</TR>
		<tr>
			<td colspan="6">
				<div class="scrollacc"
					style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 180px" align="center">
					<table width="100%">
					<tr>
						<td width="100%">
						
					<table id="dataTable" width="100%" border="1" cellpadding="2"
						cellspacing="0" align="center">
					<tr align="center" bgcolor="#CD0504"
							style="color:#ffffff !important;" class="noScroll">
							<td align="center" bgcolor="#CD0504" width="8%"><span
								class="white10heading mLeft5 mTop5"><strong><bean:message
								bundle="view" key="reverse.collection.CollectionType" /></strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Customer Id</strong></span></td>
							
							<td align="center" bgcolor="#CD0504" width="8%"><span
								class="white10heading mLeft5 mTop5"><strong>Defective Serial Number</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="10%"><span
								class="white10heading mLeft5 mTop5"><strong><bean:message
								bundle="view" key="reverse.collection.Product" /></strong></span></td>
							<td align="center" bgcolor="#CD0504" width="8%"><span
								class="white10heading mLeft5 mTop5"><strong>Changed Serial Number</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="10%"><span
								class="white10heading mLeft5 mTop5"><strong><bean:message
								bundle="view" key="reverse.collection.Product" /></strong></span></td>
							<td align="center" bgcolor="#CD0504" width="8%"><span
								class="white10heading mLeft5 mTop5"><strong>Inventory Changed Date</strong></span></td>
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Ageing</strong></span></td>
								
							<td id ="defcType" align="center" bgcolor="#CD0504" width="11%"><span
								class="white10heading mLeft5 mTop5"><strong><bean:message
								bundle="view" key="reverse.collection.DefectType" /></strong></span></td>
								<!-- Added by Neetika 19-Feb -->
								
											<td id ="rejectedDC" align="center" bgcolor="#CD0504" width="9%"><span
											class="white10heading mLeft5 mTop5"><strong><bean:message
											bundle="view" key="reverse.collection.RejectedDC" /></strong></span></td>
												<!-- End by Neetika 19-Feb -->
							<td id ="defcType" align="center" bgcolor="#CD0504" width="19%"><span
								class="white10heading mLeft5 mTop5"><strong>Remarks</strong></span></td>
								
							<td align="center" bgcolor="#CD0504" width="5%"><span
								class="white10heading mLeft5 mTop5"><strong>Remove</strong></span></td>
						</tr>
						
					</table>
					</td>
					</tr>
					
					</table>
				</div>
			</td>
		</tr>
		<TR>
		<td colspan='6'>&nbsp;
		</td>
		</TR></table>
		
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
		
		
		<table align="center">
		<tr>
			
			
			<td align="center"><input class="submit"
			type="submit" tabindex="4" name="Submit" value="Submit"
			onclick="return submitDataTableRows();">&nbsp;&nbsp;&nbsp;
			</td>
			<td colspan='3'>&nbsp;
			</td>
		</tr>
	</table>
	
</html:form>

</BODY>
</html>
