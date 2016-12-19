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
<title>Repair STB</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<style>
 .odd{background-color: #FCE8E6;}
 .even{background-color: #FFFFFF;}
</style>
<script language="JavaScript" src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script> 
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
		
			//get the deleted row data
		var x = table.rows[rowCount2].cells;
				if(document.all){
     			var jsCollectionType = x[0].getElementsByTagName("div")[0].value;
				} else {
    				var jsCollectionType = x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			 var jsDefectType = x[1].getElementsByTagName("div")[0].value;
				} else {
    				var jsDefectType = x[1].getElementsByTagName("div")[0].value.textContent;
				}
			//	if(document.all){
     			//var jsProductType= x[2].getElementsByTagName("div")[0].value;
			//	} else {
    			//	var jsProductType = x[2].getElementsByTagName("div")[0].value.textContent;
				//}
				
				//for hidden product id 
			// var	jsProductId = x[2].getElementsByTagName("input")[0].value;
				//ends
				
					//for hidden product id 
			 var	jsProductId = x[0].getElementsByTagName("input")[0].value;
				//ends
				
					//for hidden product Type
			 var	jsProductType = x[1].getElementsByTagName("input")[0].value;
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
				//for hidden Remarks
			 var	jsRemark = x[5].getElementsByTagName("input")[0].value;
				//ends
				//if(document.all){
     			//var jsRemark = x[5].getElementsByTagName("div")[0].value;
				//} else {
    		//	var	jsRemark = x[5].getElementsByTagName("div")[0].value.textContent;
			//	}
		 
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
			
		
		//to delete row from 2nd table
 		table.deleteRow(rowCount2);	
		alternate();
		
		
		
		
		
	}
	
	function addRowToDataTable() {
	try {
			
			var table = document.getElementById("dataTable1");
			var rowCount = table.rows.length;
			var j=0;
			var remarks = document.getElementById('strRemarks').value;
			//var productNew = document.getElementById('selectedProductId').value;
		  // if(productNew == 0){
		//	alert("Please Select Product");
		 //	return false;
		//  }
		  if(remarks == "" || trimAll(remarks) == ""){
			alert("Please Enter Remarks");
		 	return false;
		  }else if(remarks.length>200){
			alert("Please Enter Remarks less than 200 characters.");
		 	return false;
		  }
		//  var SelectedIndex=document.getElementById('selectedProductId').selectedIndex;
		 //  var productName = document.getElementById('selectedProductId').options[SelectedIndex].text;
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
				} else {
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsDefectType[i] = x[1].getElementsByTagName("div")[0].value;
				} else {
    				jsDefectType[i] = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsProductType[i] = x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsProductType[i] = x[2].getElementsByTagName("div")[0].value.textContent;
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
				
			
				
			var table2 = document.getElementById("dataTable2");
			var rowValue =table2.rows.length;
			var row2 = table2.insertRow(rowValue);
			
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div> <input type=\"hidden\" value=\"" + jsProductId[i] + "\" />";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsDefectType[i] +  "\">" + jsDefectType[i] + "</div><input type=\"hidden\" value=\"" + jsProductType[i] + "\" />";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsProductType[i] +  "\">" + jsProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsProductId[i] + "\" />";
			// cell3.innerHTML = "<div value=\"" + productName +  "\">" + productName + "</div> <input type=\"hidden\" value=\"" + productNew + "\" />";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsSrNo[i] +  "\">" + jsSrNo[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsCollectionDate[i] +  "\">" + jsCollectionDate[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + remarks +  "\">" + remarks + "</div> <input type=\"hidden\" value=\"" + jsRemark[i] + "\" />";
			
			var cell7 = row2.insertCell(6);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			str[j] = j+1;
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
			cell7.appendChild(image);
     		
     			
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
				} else {
    				jsCollectionType[i] = x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsDefectType[i] = x[1].getElementsByTagName("div")[0].value;
				} else {
    				jsDefectType[i] = x[1].getElementsByTagName("div")[0].value.textContent;
				}
				if(document.all){
     			jsProductType[i] = x[2].getElementsByTagName("div")[0].value;
				} else {
    				jsProductType[i] = x[2].getElementsByTagName("div")[0].value.textContent;
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
				
			var table2 = document.getElementById("dataTable2");
			var rowValue =table2.rows.length;
		   	var row2 = table2.insertRow(rowValue);
			var cell1 = row2.insertCell(0);
			cell1.innerHTML = "<div value=\"" + jsCollectionType[i] +  "\">" + jsCollectionType[i] + "</div> <input type=\"hidden\" value=\"" + jsProductId[i] + "\" />";
			
			var cell2 = row2.insertCell(1);
			cell2.innerHTML = "<div value=\"" + jsDefectType[i] +  "\">" + jsDefectType[i] + "</div><input type=\"hidden\" value=\"" + jsProductType[i] + "\" />";
			
			var cell3 = row2.insertCell(2);
			cell3.innerHTML = "<div value=\"" + jsProductType[i] +  "\">" + jsProductType[i] + "</div> <input type=\"hidden\" value=\"" + jsProductId[i] + "\" />";
			// cell3.innerHTML = "<div value=\"" + productName +  "\">" + productName + "</div> <input type=\"hidden\" value=\"" + productNew + "\" />";
			
			var cell4 = row2.insertCell(3);
			cell4.innerHTML = "<div value=\"" + jsSrNo[i] +  "\">" + jsSrNo[i] + "</div>";
			
			var cell5 = row2.insertCell(4);
			cell5.innerHTML = "<div value=\"" + jsCollectionDate[i] +  "\">" + jsCollectionDate[i] + "</div>";
			
			var cell6 = row2.insertCell(5);
			cell6.innerHTML = "<div value=\"" + remarks +  "\">" + remarks + "</div> <input type=\"hidden\" value=\"" + jsRemark[i] + "\" />";
			
			var cell7 = row2.insertCell(6);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			image.onclick= function() { remove(this.parentNode.parentNode.rowIndex);};
			cell7.appendChild(image);
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
 	}else{
		document.getElementById('strRemarks').value="";
	    // document.getElementById('selectedProductId').value="0";
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
	
function submitDataTableRows() {
		var table = document.getElementById("dataTable2");
		var rowCount = table.rows.length;
		if(rowCount >71){
			alert("Repair STB can not be done for more than 70 STBs");
		 	return false;
		}
		
		var i=0;
		var jsArrProductId = new Array();
		var jsArrOldProductId = new Array();
		var jsArrSerialNo = new Array();
		var jsArrNewRemarks = new Array();
		var jsArrOldRemarks = new Array();
		var oldRemarks="";
		while (i+1<rowCount){
			var x = document.getElementById("dataTable2").rows[i+1].cells;
     			// this is for Hidden parameter
     			jsArrProductId[i] = x[2].getElementsByTagName("input")[0].value;
     			jsArrOldProductId[i] = x[0].getElementsByTagName("input")[0].value;
     			if(x[5].getElementsByTagName("input")[0].value == "" || x[5].getElementsByTagName("input")[0].value == null ){
					jsArrOldRemarks[i] = "null";
     			}else{
     				jsArrOldRemarks[i] = x[5].getElementsByTagName("input")[0].value;
     			}
     			
			// work for all browser firefox
			if(document.all){
     			jsArrSerialNo[i] = x[3].getElementsByTagName("div")[0].value;
			} else {
    			jsArrSerialNo[i] = x[3].getElementsByTagName("div")[0].value.textContent;
			}
			if(document.all){
     			jsArrNewRemarks[i] = x[5].getElementsByTagName("div")[0].value;
			} else {
    			jsArrNewRemarks[i] = x[5].getElementsByTagName("div")[0].value.textContent;
			}
			
			i++;
  		}
  		document.forms[0].hidStrProductId.value=jsArrOldProductId;
  		document.forms[0].hidStrSerialNo.value=jsArrSerialNo;
  		document.forms[0].hidStrOldProductId.value=jsArrOldProductId;
  		document.forms[0].hidStrNewRemarks.value=jsArrNewRemarks;
  		document.forms[0].hidStrOldRemarks.value=jsArrOldRemarks;
  		if((document.forms[0].hidStrProductId.value).length >0)
  	    {
  	  	document.forms[0].action ="repairSTB.do?methodName=performStbRepair";
		document.forms[0].submit();
		
  			//alert("hi");
  		}
  		else
  		{
  		   alert("Please Add atleast One Record!!")
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
	
</script>
</head>
<body>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<html:form action="/repairSTB" type="com.ibm.dp.beans.RepairSTBBean">
<html:hidden property="hidStrProductId" name="RepairSTBBean" value=""/>
<html:hidden property="hidStrSerialNo" name="RepairSTBBean" value=""/>
<html:hidden property="hidStrOldProductId" name="RepairSTBBean" value=""/>
<html:hidden property="hidStrNewRemarks" name="RepairSTBBean" value=""/>
<html:hidden property="hidStrOldRemarks" name="RepairSTBBean" value=""/>
<html:hidden property="isValidUser"/>

<IMG src="<%=request.getContextPath()%>/images/Rep_STB.jpg" 
		width="544" height="25" alt=""/> 
		
	<table>
		<tbody>
			<tr>
				<td><logic:notEmpty name="RepairSTBBean" property="message">
			<font color="red"><B><bean:write name="RepairSTBBean" property="message"/></B></font>
			</logic:notEmpty>
			</td>
			</tr>
		</tbody>
	</table>
	
	<br>


<DIV style="height: 100px; overflow: auto;" width="80%" height="30%">
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
					<logic:notEmpty name="RepairSTBBean" property="reverseStockInventoryList">
					<logic:iterate name="RepairSTBBean" id="stockList" indexId="i" property="reverseStockInventoryList">
						
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
							
								<input type="checkbox" id="strCheckedSTA" name="strCheckedSTA" value='<bean:write name='stockList' property='rowSrNo'/>' />
							</TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			
			</tbody>
		</table>
		
		</DIV>
	<br>
	<br><DIV  style="height: 80px; overflow: auto;" width="80%" height="20%">
		<table width="100%" border="0" cellPadding="0" cellSpacing="0" align="left">
			<tr>
			<!--<td class="txt" width="110px"><strong>Product</strong><font color="red">*</font></td>
			<td><html:select property="selectedProductId" styleId="selectedProductId" style="width:150px" >
				<html:option value="0">--Select Product---</html:option>
				<logic:notEmpty property="productList" name="RepairSTBBean"><html:options labelProperty="productName" property="productId"
						collection="productList" />
				</logic:notEmpty>
			</html:select></td>
			--><td class="txt" width="110px"><strong><bean:message bundle="view" key="DcCreation.Remarks" /></strong><font color="red">*</font></td>
			<td><html:textarea name="RepairSTBBean"
				property="strRemarks" styleId="strRemarks"  rows="2" cols="39" onkeyup="return maxlength();" ></html:textarea>
			</td></tr> <tr>
<td></td>			</tr>
			<tr>
			<td class="txt" align="center" colspan="4">
			<logic:equal property="isValidUser" name="RepairSTBBean" value="Y">
			<input type="button"
				value="Add to Grid" onclick="addRowToDataTable()" />
			</logic:equal></td>
		</tr>	
			</table></DIV>

	
	<DIV style="height: 100px; overflow: auto;" width="80%" height="30%">
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
		<br>
		<br>
		<table width="100%" border="0" cellPadding="0" cellSpacing="0" align="left">
			
			<tr>
				<td  class="txt" align="center" colspan="4">
								<logic:equal property="isValidUser" name="RepairSTBBean" value="Y">
								<input type="button" id="submitAllocation" value="Repaired" onclick="submitDataTableRows()"/> 
								</logic:equal>
				
				
				</td>
			</tr>
		</table>
		
		
	
</html:form>
</body>
</html>