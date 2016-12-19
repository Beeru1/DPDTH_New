<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<title>DC Creation</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<style>
 .odd{background-color: #FCE8E6;}
 .even{background-color: #FFFFFF;}
</style>
<script language="JavaScript" src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script> 
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>
<script language="JavaScript">


function submitCtrl(thisFrm){
document.forms[0].action ="dcCreation.do?methodName=viewStockCollection";
document.forms[0].submit();
}
function validateData() {
		submitCtrl(document.forms[0]);
		return true;
	}
	
  function alternate(){
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


	
	function remove(rowCount2){
		var table = document.getElementById("dataTable2");
		var collectionType;
		var productType;
		
			//get the deleted row data
		var x = table.rows[rowCount2].cells;
				if(document.all){
     			var jsCollectionType = x[0].getElementsByTagName("div")[0].value;
     			collectionType=x[0].getElementsByTagName("div")[0].value;
				} else {
    				var jsCollectionType = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType=x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			 var jsDefectType = x[1].getElementsByTagName("div")[0].value;
				} else {
    				var jsDefectType = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
	     			var jsProductType= x[2].getElementsByTagName("div")[0].value;
	     			productType=x[2].getElementsByTagName("div")[0].value;
				} else {
    				var jsProductType = x[2].getElementsByTagName("div")[0].value.textContent;
    				productType=x[2].getElementsByTagName("div")[0].value.textContent;
				}
				
				//for hidden product id 
			 var	jsProductId = x[2].getElementsByTagName("input")[0].value;
				//ends
				
				if(document.all){
     			var jsSrNo = x[3].getElementsByTagName("div")[0].value;
				} else {
    				var jsSrNo = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			var jsCollectionDate = x[4].getElementsByTagName("div")[0].value;
				} else {
    			var	jsCollectionDate = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			var jsRemark = x[5].getElementsByTagName("div")[0].value;
				} else {
    			var	jsRemark = x[5].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(document.all){
     			 var jsCustomerId = x[7].getElementsByTagName("div")[0].value;
				} else {
    				var jsCustomerId = x[7].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			 var jsReqId = x[8].getElementsByTagName("div")[0].value;
				} else {
    				var jsReqId = x[8].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			 var jsInvChangeDate = x[9].getElementsByTagName("div")[0].value;
				} else {
    				var jsInvChangeDate = x[9].getElementsByTagName("div")[0].value.textContent;
				}
		 deleteRowDataTable3(collectionType,productType);
		 //To insert in first table 
		    var table2 = document.getElementById("dataTable1");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType +  "\">" + jsCollectionType + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsDefectType +  "\">" + jsDefectType + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsProductType +  "\">" + jsProductType + "</div> <input type=\"hidden\" value=\"" + jsProductId + "\" />";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsSrNo +  "\">" + jsSrNo + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsCollectionDate +  "\">" + jsCollectionDate + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsRemark +  "\">" + jsRemark + "</div>";
			
			var cell7 = row2.insertCell(6);
			
			cell7.innerHTML = "<input type=\"checkbox\" id=\"strCheckedSTA\" name=\"strCheckedSTA\" value=\"" + jsProductId + "\" />";
			
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div style='display:none' value=\"" + jsCustomerId +  "\">" + jsCustomerId + "</div>";
			
     		var cell9 = row2.insertCell(8);
			cell9.innerHTML = "<div style='display:none' value=\"" + jsReqId +  "\">" + jsReqId + "</div>";
			
     		var cell10 = row2.insertCell(9);
			cell10.innerHTML = "<div style='display:none' value=\"" + jsInvChangeDate +  "\">" + jsInvChangeDate + "</div>";	
     			
     			
		
		//to delete row from 2nd table
 		table.deleteRow(rowCount2);	
		alternate();
		
		
		
		
		
	}
	
	
	function deleteRowDataTable3(collectionType,productType){
	var table = document.getElementById("dataTable3");
	var count=0;
	var newInsert=0;

		for (var i = 1, row; row = table.rows[i]; i++) {
		
	   		if((row.cells[0].getElementsByTagName("div")[0].value==collectionType) && (row.cells[1].getElementsByTagName("div")[0].value==productType) ){
	   			count=parseInt(row.cells[2].getElementsByTagName("div")[0].value);
	   			if(count!=1)
	   			{
	   			count=count-1;
	   			row.cells[2].innerHTML = "<div value=\"" + count +  "\">" + count + "</div>";
	   			}
	   			else
	   			{
	   			document.getElementById("dataTable3").deleteRow(i);
	   			}
	   			break;
	   		}
		}
		
		
		
	
	}
	
	
	function loopDataTable3(collectionType,productType){
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
	
	function addRowToDataTable() {
	try {
			var table = document.getElementById("dataTable1");
			var rowCount = table.rows.length;
			var j=0;
			if(rowCount > 1){
			var i=0;
			var jsCollectionType = new Array();
			var jsDefectType = new Array();
			var jsProductType = new Array();
			var jsProductId = new Array();
			var jsSrNo = new Array();
			var jsCollectionDate = new Array();
			var jsRemark = new Array();
			var strArraySelected = new Array();
			var str=new Array();
			var collectionType;
			var productType;
			var jsCustomerId = new Array();
			var jsReqId = new Array();
			var jsInvChangeDate = new Array();
			
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
			var x = document.getElementById("dataTable1").rows[rownum-1].cells;
			
				if(document.all){
     			jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value;
     			collectionType=x[0].getElementsByTagName("div")[0].value;
				} else {
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType=x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsDefectType[i] = x[1].getElementsByTagName("div")[0].value;
				} else {
    				jsDefectType[i] = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsProductType[i] = x[2].getElementsByTagName("div")[0].value;
     			productType=x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsProductType[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    				productType=x[2].getElementsByTagName("div")[0].value.textContent;
				}
				//for hidden product id 
				jsProductId[i] = x[2].getElementsByTagName("input")[0].value;
				//ends
				
				if(document.all){
     			jsSrNo[i] = x[3].getElementsByTagName("div")[0].value;
				} else {
    				jsSrNo[i] = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsCollectionDate[i] = x[4].getElementsByTagName("div")[0].value;
				} else {
    				jsCollectionDate[i] = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsRemark[i] = x[5].getElementsByTagName("div")[0].value;
				} else {
    				jsRemark[i] = x[5].getElementsByTagName("div")[0].value.textContent;
				}
			if(document.all){
     			jsCustomerId[i] = x[7].getElementsByTagName("div")[0].value;
     			} else {
    				jsCustomerId[i] = x[7].getElementsByTagName("div")[0].value.textContent;
    			}
    		if(document.all){
     			jsReqId[i] = x[8].getElementsByTagName("div")[0].value;
     			} else {
    				jsReqId[i] = x[8].getElementsByTagName("div")[0].value.textContent;
    			}
			if(document.all){
     				jsInvChangeDate[i] = x[9].getElementsByTagName("div")[0].value;
     			} else {
    				jsInvChangeDate[i] = x[9].getElementsByTagName("div")[0].value.textContent;
    			}
    				
    			
    			
    			
    		
    			
				loopDataTable3(collectionType,productType);
			var table2 = document.getElementById("dataTable2");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsDefectType[i] +  "\">" + jsDefectType[i] + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsProductType[i] +  "\">" + jsProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsProductId[i] + "\" />";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsSrNo[i] +  "\">" + jsSrNo[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsCollectionDate[i] +  "\">" + jsCollectionDate[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsRemark[i] +  "\">" + jsRemark[i] + "</div>";
			var cell7 = row2.insertCell(6);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			str[j] = j+1;
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
			cell7.appendChild(image);
     		var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div style='display:none' value=\"" + jsCustomerId[i] +  "\">" + jsCustomerId[i] + "</div>";
			
     		var cell9 = row2.insertCell(8);
			cell9.innerHTML = "<div style='display:none' value=\"" + jsReqId[i] +  "\">" + jsReqId[i] + "</div>";
			var cell10 = row2.insertCell(9);
			cell10.innerHTML = "<div style='display:none' value=\"" + jsInvChangeDate[i] +  "\">" + jsInvChangeDate[i] + "</div>";	
     			
     			
     			
     			
			}
		}
	}else 	if(document.forms[0].strCheckedSTA.checked==true)
			{
			var count=0;
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				if(document.all){
     			jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value;
     			collectionType= x[0].getElementsByTagName("div")[0].value;
				} else {
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
    				collectionType= x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsDefectType[i] = x[1].getElementsByTagName("div")[0].value;
				} else {
    				jsDefectType[i] = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsProductType[i] = x[2].getElementsByTagName("div")[0].value;
     			productType= x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsProductType[i] = x[2].getElementsByTagName("div")[0].value.textContent;
    				productType= x[2].getElementsByTagName("div")[0].value.textContent;
				}
				
				//for hidden product id 
				jsProductId[i] = x[2].getElementsByTagName("input")[0].value;
				//ends
				if(document.all){
     			jsSrNo[i] = x[3].getElementsByTagName("div")[0].value;
				} else {
    				jsSrNo[i] = x[3].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsCollectionDate[i] = x[4].getElementsByTagName("div")[0].value;
				} else {
    				jsCollectionDate[i] = x[4].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsRemark[i] = x[5].getElementsByTagName("div")[0].value;
				} else {
    				jsRemark[i] = x[5].getElementsByTagName("div")[0].value.textContent;
				}
			if(document.all){
     			jsCustomerId[i] = x[7].getElementsByTagName("div")[0].value;
				} else {
    				jsCustomerId[i] = x[7].getElementsByTagName("div")[0].value.textContent;
				}
			if(document.all){
     			jsReqId[i] = x[8].getElementsByTagName("div")[0].value;
				} else {
    				jsReqId[i] = x[8].getElementsByTagName("div")[0].value.textContent;
				}
				
			if(document.all){
     			jsInvChangeDate[i] = x[9].getElementsByTagName("div")[0].value;
				} else {
    				jsInvChangeDate[i] = x[9].getElementsByTagName("div")[0].value.textContent;
				}	
				
				
				
				
			loopDataTable3(collectionType,productType);	
			var table2 = document.getElementById("dataTable2");
			var rowValue =table2.rows.length;
		   	var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div>";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsDefectType[i] +  "\">" + jsDefectType[i] + "</div>";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsProductType[i] +  "\">" + jsProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsProductId[i] + "\" />";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsSrNo[i] +  "\">" + jsSrNo[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsCollectionDate[i] +  "\">" + jsCollectionDate[i] + "</div>";
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + jsRemark[i] +  "\">" + jsRemark[i] + "</div>";
			
			var cell7 = row2.insertCell(6);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
			cell7.appendChild(image);
			
			var cell8 = row2.insertCell(7);
			cell8.innerHTML = "<div style='display:none' value=\"" + jsCustomerId[i] +  "\">" + jsCustomerId[i] + "</div>";
			var cell9 = row2.insertCell(8);
			cell9.innerHTML = "<div style='display:none' value=\"" + jsReqId[i] +  "\">" + jsReqId[i] + "</div>";
			
			
			var cell10 = row2.insertCell(9);
			cell10.innerHTML = "<div style='display:none' value=\"" + jsInvChangeDate[i] +  "\">" + jsInvChangeDate[i] + "</div>";			
			
			
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



function printPage()
{
 	if(document.forms[0].hidstrDcNo.value !=""){
				var strDCNo=document.forms[0].hidstrDcNo.value;
				var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo;
 			    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
				
  			}
 // printDCDetails.do?methodName=printDC&Dc_No=
  
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
	
function submitDataTableRows(obj) {

var agencyName = document.getElementById('courierAgency').value;
var docketNum = document.getElementById('docketNumber').value;
var remarks = document.getElementById('strRemarks').value;
var isSubmit=true;
	if(trim(agencyName) =="" || agencyName ==null || trim(docketNum)=="" || docketNum ==null){
			alert("Please enter courier agency/docket number");
			isSubmit =false;
	}
	if(remarks == "" || remarks == null || trimAll(remarks) == ""){
			alert("Please Enter Remarks");
		 	return false;
		}else if(remarks.length>200){
			alert("Please Enter Remarks less than 200 characters.");
		 	return false;
		}
		else{
			if(isSubmit ==true){
				var table = document.getElementById("dataTable2");
				var rowCount = table.rows.length;
				if(rowCount >71){
					alert("Delivery Challan can not be generated for more than 70 STBs");
				 	return false;
				}
				
				var i=0;
				var jsArrProductId = new Array();
				var jsArrSerialNo = new Array();
				var jsArrCustomerId = new Array();
				var jsArrReqId = new Array();
				var jsArrjsInvChangeDate= new Array();
				
				
				while (i+1<rowCount){
					var x = document.getElementById("dataTable2").rows[i+1].cells;
		     			// this is for Hidden parameter
		     			jsArrProductId[i] = x[2].getElementsByTagName("input")[0].value;
					// work for all browser firefox
					if(document.all){
		     			jsArrSerialNo[i] = x[3].getElementsByTagName("div")[0].value;
					} else {
		    			jsArrSerialNo[i] = x[3].getElementsByTagName("div")[0].value.textContent;
					}
					
				if(document.all){
		     			jsArrCustomerId[i] = x[7].getElementsByTagName("div")[0].value;
					} else {
		    			jsArrCustomerId[i] = x[7].getElementsByTagName("div")[0].value.textContent;
					}
					if(jsArrCustomerId[i]  == null || jsArrCustomerId[i]  == "" || jsArrCustomerId[i] ==" "){
						jsArrCustomerId[i] ="-1";
					}
					
					if(document.all){
		     			jsArrReqId[i] = x[8].getElementsByTagName("div")[0].value;
					} else {
		    			jsArrReqId[i] = x[8].getElementsByTagName("div")[0].value.textContent;
					}
					if(document.all){
		     			jsArrjsInvChangeDate[i] = x[9].getElementsByTagName("div")[0].value;
					} else {
		    			jsArrjsInvChangeDate[i] = x[9].getElementsByTagName("div")[0].value.textContent;
					}
					
					
					
					
					
					i++;
		  		}
		  		document.forms[0].hidStrProductId.value=jsArrProductId;
		  		document.forms[0].hidStrSerialNo.value=jsArrSerialNo;
		  		document.forms[0].hidStrCustId.value=jsArrCustomerId;
		  		document.forms[0].hidStrReqId.value=jsArrReqId;
		  		document.forms[0].hidStrInvChangeDate.value=jsArrjsInvChangeDate;
		  		
		  		
		  		if((document.forms[0].hidStrProductId.value).length >0)
		  	    {
	  	    	  	document.forms[0].action ="dcCreation.do?methodName=performDcCreation";
	  	    	  	obj.disabled = true;
					document.forms[0].submit();
				
		  		}
		  		else
		  		{
		  		   alert("Please Add atleast One Record!!")
		  		}
  		}
  	}
 		
}	
	function maxlength() 
{
   var size=200;
   var remarks = document.getElementById("strRemarks").value;
  	if (remarks.length >= size) 
  	{
   	  remarks = remarks.substring(0, size-1);
   	  document.getElementById("strRemarks").value = remarks;
      alert ("Remarks Can Contain, 200 Characters Only.");
   	  document.getElementById("strRemarks").focus();
	  return false;
  	}
  	
  		var iChars = "~`!@#$%^&*()-_=[{]}\|;:,<\/?\\";

	for (var i = 0; i < document.getElementById("strRemarks").value.length; i++) {
  	if (iChars.indexOf(document.getElementById("strRemarks").value.charAt(i)) != -1) 
  	{
  		alert("Special characters (` ~ ' ,) are not allowed.");
  		document.getElementById("strRemarks").value = remarks.substring(0, document.getElementById("strRemarks").value.length -1);
  		return false;
  	}
}
  	
  	
 } 
function checkERR(strSerialNo,e) {
if(e.checked){
 isStatusERR(strSerialNo);
if(document.getElementById("isERR").value=='Y'){
	e.checked=false;	
}
}
}	
function isStatusERR(strSerialNo) {
var url ="dcCreation.do?methodName=checkERR&strSerialNo="+strSerialNo;								
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
			req.onreadystatechange = getcheckERR;
			req.open("POST",url,false);
			req.send();		
			
		}
		function getcheckERR()
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
			var strERR = optionValues[0].getAttribute("strERR");	
			document.getElementById("isERR").value=strERR;
			if(strERR=='Y'){
				alert("This STB cannot be added in DC.Please contact TSM for details.");							
			}							
		}
	}	

</script>
</head>
<body onload="javascript:printPage();">
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<html:form action="/dcCreation" type="com.ibm.dp.beans.DpDcCreationBean">
<html:hidden property="hidStrProductId" name="DpDcCreationBean" value=""/>
<html:hidden property="hidStrSerialNo" name="DpDcCreationBean" value=""/>
<html:hidden property="hidStrCustId" name="DpDcCreationBean" value=""/>
<html:hidden property="hidStrReqId" name="DpDcCreationBean" value=""/>
<html:hidden property="hidStrInvChangeDate" name="DpDcCreationBean" value=""/>
<html:hidden property="isERR" name="DpDcCreationBean" value=""/>

<html:hidden property="hidstrDcNo"/>

<IMG src="<%=request.getContextPath()%>/images/DC_Create.jpg" 
		width="544" height="25" alt=""/> 
		
	<table>
		<tbody>
			<tr>
				<td><logic:notEmpty name="DpDcCreationBean" property="message">
			<font color="red"><B><bean:write name="DpDcCreationBean" property="message"/></B></font>
			</logic:notEmpty>
			</td>
			</tr>
		</tbody>
	</table>

<div style="display: none;">
<table id="inputTable" width="650px" border="0" align="center">
<tr>
			<td class="txt" width="150px"><strong><bean:message bundle="view" key="DcCreation.CollectionType" /></strong><font color="red">*</font></td>
			
			
					
					
			<td class="txt">
			<html:select property="collectionId" styleId="collectionId" style="width:150px" >
				<html:option value="0"><bean:message bundle="view" key="DcCreation.CollectionAll" /></html:option>
				<logic:notEmpty property="dcCollectionList" name="DpDcCreationBean">
				<html:options labelProperty="collectionName" property="collectionId"
						collection="dcCollectionList" />
				</logic:notEmpty>
			</html:select>
			</td>
			<td class="txt" align="right" colspan="4">
			<input type="button" value="GO" onclick="validateData()" /></td>
</tr>
</table>
</div>


<DIV style="height: 110px; overflow: auto;" width="80%">

		<table id="dataTable1"  width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.CollectionList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.DefectList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.ProductList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.SerialNo" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.CollectionDate" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.Remarks" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"></td>
				</tr>
			</thead>
			<tbody>
					<logic:notEmpty name="DpDcCreationBean" property="reverseStockInventoryList">
					<logic:iterate name="DpDcCreationBean" id="stockList" indexId="i" property="reverseStockInventoryList">
						
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text" id='"collectionType"+<%=i%>'>
							<div value='<bean:write name="stockList" property="strCollectionType"/>'>	<bean:write name="stockList" property="strCollectionType"/>	</div>				
							</TD>
							<TD align="center" class="text">
								<div value='<bean:write name="stockList" property="strDefectType"/>'><bean:write name="stockList" property="strDefectType"/>					</div>
							</TD>
							<TD align="center" class="text"><input type="hidden" value='<bean:write name="stockList" property="prodId"/>'/>
							<div  value='<bean:write name="stockList" property="strProduct"/>'>	<bean:write name="stockList" property="strProduct"/>					</div>
							</TD>
							<TD align="center" class="text">
							<div value='<bean:write name="stockList" property="strSerialNo"/>'>	<bean:write name="stockList" property="strSerialNo"/>					</div>
							</TD>
							<TD align="center" class="text">
						<div value='<bean:write name="stockList" property="strCollectionDate"/>'>	<bean:write name="stockList" property="strCollectionDate"/>					</div>
							</TD>
							<TD align="center" class="text">
							<div value='<bean:write name="stockList" property="strRemarks"/>'>	<bean:write name="stockList" property="strRemarks"/>					</div>
							</TD>
							<TD align="center" class="text">
							
								<input type="checkbox" id="strCheckedSTA" name="strCheckedSTA" value='<bean:write name='stockList' property='rowSrNo'/>' onclick="checkERR('<bean:write name='stockList' property='strSerialNo'/>',this);"/>
							</TD>
							
							<TD align="center" style="display:none" class="text">
							<div value='<bean:write name="stockList" property="strCustomerId"/>' style="display: none">	<bean:write name="stockList" property="strCustomerId"/>
							</div>
							</TD>
							<TD align="center" style="display:none" class="text">
								<div value='<bean:write name="stockList" property="strReqId"/>' style="display: none">	<bean:write name="stockList" property="strReqId"/>
							</div>
							</TD>
							
							<TD align="center" style="display:none" class="text">
								<div value='<bean:write name="stockList" property="strInvChangeDate"/>' style="display: none">	<bean:write name="stockList" property="strInvChangeDate"/>
							</div>
							</TD>
							
							
							
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			
			</tbody>
		</table>
		
		</DIV>
	
	<table width="100%" align="center">
		<tr>
			<td class="txt" align="center" colspan="4"><input type="button"
				value="Add to Grid" onclick="addRowToDataTable()" /></td>
		</tr>	
		</table>
	

	<DIV style="height: 120px; overflow: auto;" width="80%">

		<table id="dataTable2"  width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
				<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.CollectionList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.DefectList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.ProductList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.SerialNo" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.CollectionDate" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.Remarks" /></FONT></td>
					
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Remove</FONT></td>
					
				</tr>
				</thead>
		</table></DIV>
		
		
		<table width="100%" border="0" cellPadding="0" cellSpacing="0" align="center">
			<tr><td class="txt" width="110px"><strong>
			<bean:message bundle="view" key="DcCreation.courierAgency" /><font color="red">*</font></strong>
			</td>
			<td>
			<html:text name="DpDcCreationBean"	property="courierAgency" styleId="courierAgency"  maxlength="100" onkeyup="return specialChar(this);" />
			</td>
			<td class="txt" width="110px"><strong>
			<bean:message bundle="view" key="DcCreation.DocketNumber" /><font color="red">*</font></strong>
			</td>
			<td>
			<html:text name="DpDcCreationBean"	property="docketNumber" styleId="docketNumber"   maxlength="50" onkeyup="return specialChar(this);" />
			</td>
			<td class="txt" width="110px"><strong><bean:message bundle="view" key="DcCreation.Remarks" /></strong><font color="red">*</font></td>
			<td><html:textarea name="DpDcCreationBean"
				property="strRemarks" styleId="strRemarks"  rows="4" cols="28" onkeyup="return maxlength();" ></html:textarea>
			</td>
			</tr>
			<tr></tr><tr></tr>
			</table>
			<br><br>

			<DIV style="height: 120px; overflow: auto;" width="80%">

		<table id="dataTable3"  width="80%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
				<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="DcCreation.CollectionList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Product Type</FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Count</FONT></td>
					
				</tr>
				</thead>
		</table></DIV>
			
			<table width="100%" border="0" cellPadding="0" cellSpacing="0" align="left">
			<tr>
<td></td><td></td>
				<td colspan="2" align="center" class="txt">
				<input type="button" id="submitAllocation" value="Submit" onclick="submitDataTableRows(this)" />
				
				</td>
			</tr>
		</table>
		
		
	
</html:form>
</body>
</html>