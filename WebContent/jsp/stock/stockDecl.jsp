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
<title>Allocation of Stock</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<script language="JavaScript">
function validateData() {
		
		if(document.getElementById('product').value == 0) {
			alert("Please select Product Name");
			document.getElementById('product').focus();
			return 0;
		}
		if(document.getElementById('closingStock').value == "") {
			alert("Please Enter Closing Stock ");
			document.getElementById('closingStock').focus();
			return 0;
		}
		if (isNumeric(document.getElementById('closingStock').value) == false) {
	    		alert("Non numeric value cannot be given for 'Assign Quantity'");
				document.getElementById('closingStock').focus();
	      		return 0;
	    	}
	    	if(!(document.getElementById('comments').value == ""))
	    	{
	   // 	alert((document.getElementById('comments').value).length);
	    	
		if(check(document.getElementById('comments').value)) {
			alert("Please Remove special Charecters from Comments");
			document.getElementById('comments').focus();
			return 0;
	 	}
	 	if((document.getElementById('comments').value).length >200)
	 	{
	 	alert("Your comments should not be greter than 200 charecters");
			document.getElementById('comments').focus();
			return 0;
	 	}
	 
	 	/*
	 	if(/[a-zA-z ! @ # <> - %]+/.test(document.getElementById('comments').value)) {
			alert("Please Remove special Charecters from Comments");
			document.getElementById('comments').focus();
			return 0;
	 	}
	 		*/
	 	}
		return 1;
	}
function isNumeric(strString) { //  check for valid numeric strings	
   		var strValidChars = "0123456789";
   		var strChar;
   		var blnResult = true;
   		if (strString.length == 0) return false;
   		//  test strString consists of valid characters listed above
   		for (i = 0; i < strString.length && blnResult == true; i++) {
      		strChar = strString.charAt(i);
      		if (strValidChars.indexOf(strChar) == -1) {
      			blnResult = false;
      		}
   		}
   		return blnResult;
   	}
   	
   	function check(inputString)
{
	var reCharSpecial=/^[a-zA-Z0-9-,_#*@&%\s][a-zA-Z0-9-,_#*@&%\s]*$/
    if(!reCharSpecial.test(inputString))
	{
		 return true;
	}
	else
	{
		return false;
	}
}
   	/*
function isSpecialChar(strString) { //  check for valid numeric strings	
var str=' ';
   		var strValidChars = "abcdefghijklmnopqrstuvwxyz0123456789<>!-%@#"+str;
   		var strChar;
   		var blnResult = true;
   		if (strString.length == 0) return false;
   		//  test strString consists of valid characters listed above
   		for (i = 0; i < strString.length && blnResult == true; i++) {
      		strChar = strString.charAt(i);
      		alert(strChar);
      		if (strValidChars.indexOf(strChar) == -1) {
      		alert("inside if");
      			blnResult = false;
      		}
      		alert("out of if");
   		}
   		return blnResult;
   	}   	
  */ 	
function resetValues() {
		document.getElementById('product').value = 0;
		document.getElementById('closingStock').value = "";
		document.getElementById('comments').value = "";
		
	}
	function addRowToDataTable() {
		try {

			var table = document.getElementById("dataTable");

			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			var cell1 = row.insertCell(0);
			var image = document.createElement("img");
			image.src='${pageContext.request.contextPath}/images/delete_icon.gif';
			var index_drop = document.getElementById("product").selectedIndex ;
			var value_drop  = document.getElementById("product").options[index_drop].value;
			var text_drop  = document.getElementById("product").options[index_drop].text;
			
			image.onclick= function() { removeRow(row , value_drop , text_drop);};
			cell1.appendChild(image);

			var cell2 = row.insertCell(1);
			var catIndex = document.getElementById("product").selectedIndex;
			var catElement = document.getElementById("product").options[catIndex].value;
			
			cell2.innerHTML = "<div value=\"" + document.getElementById("product").options[catIndex].text +  "\">" + document.getElementById("product").options[catIndex].text + "</div><input type=\"hidden\" value=\"" + catElement + "\" />";

             var cell3 = row.insertCell(2);
			cell3.innerHTML = "<div value=\"" + document.getElementById("closingStock").value +  "\">" + document.getElementById("closingStock").value + "</div>";
			
			var cell4 = row.insertCell(3);
			cell4.innerHTML = "<div value=\"" + document.getElementById("comments").value +  "\">" + document.getElementById("comments").value + "</div>";
			document.getElementById("product").remove(catIndex);
			} catch(e) {
				alert(e);
		}
	}
		
		function removeRow(row , value , text) {
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		document.getElementById("product").options.add(optn);
		row.parentNode.removeChild(row);
	}
	
function addRow() {
           if(validateData()==1)
           {
			addRowToDataTable();
			resetValues();
		    }
		    
	}
function submitDataTableRows() {
		var table = document.getElementById("dataTable");
		var rowCount = table.rows.length;
		var i=0;
		var jsArrProductId = new Array();
		var jsArrProductName = new Array();
		var jsArrClosingStock = new Array();
		var jsArrComments = new Array();
		while (i+1<rowCount){
			var x = document.getElementById("dataTable").rows[i+1].cells;
     			// this is for Hidden parameter
     			jsArrProductId[i] = x[1].getElementsByTagName("input")[0].value;
			// work for all browser firefox
			if(document.all){
     			jsArrProductName[i] = x[1].getElementsByTagName("div")[0].value;
			} else {
    			jsArrProductName[i] = x[1].getElementsByTagName("div")[0].value.textContent;
			}
			if(document.all){
     			jsArrClosingStock[i] = x[2].getElementsByTagName("div")[0].value;
			} else {
    			jsArrClosingStock[i] = x[2].getElementsByTagName("div")[0].value.textContent;
			}
			if(document.all){
     			jsArrComments[i] = x[3].getElementsByTagName("div")[0].value;
			} else {
    			jsArrComments[i] = x[3].getElementsByTagName("div")[0].value.textContent;
			}	
		
			i++;
  		}
  		document.forms[0].arrProductId.value=jsArrProductId;
  		document.forms[0].arrProductName.value=jsArrProductName;
  		document.forms[0].arrClosingStock.value=jsArrClosingStock;
  		document.forms[0].arrComments.value=jsArrComments;
  	
  	//	alert(document.forms[0].arrProductId.value);
  	//	alert(document.forms[0].arrProductName.value);
  	//	alert(document.forms[0].arrClosingStock.value);
  	//	alert(document.forms[0].arrComments.value);
  	   
  	 //   alert((document.forms[0].arrProductId.value).length >0);
  	    if((document.forms[0].arrProductId.value).length >0)
  	    {
  		document.forms[0].submit();
  		}
  		else
  		{
  		   alert("Please Add atleast One Record!!")
  		}
	}	
	
	
</script>
</head>
<body>
<html:form action="/performStockDeclaration">
<html:hidden property="methodName" value="performStockDeclaration" />
<br>
<IMG src="<%=request.getContextPath()%>/images/stockdeclaration.JPG"
		width="544" height="26" alt=""/>
		
	<html:hidden property="arrProductId" name="DistStockDeclarationBean" />
	<html:hidden property="arrProductName" name="DistStockDeclarationBean" />
	<html:hidden property="arrClosingStock" name="DistStockDeclarationBean" />
	<html:hidden property="arrComments" name="DistStockDeclarationBean" />
<table>
		<tbody>
			<tr>
				<%--<td colspan="4" class="text"><br>
				<font size=2><b>Stock Declaration :</b></font></td>
				<td colspan="4" class="text"><br>
				<font size=2><b></b></font></td>--%>
				<td><logic:notEmpty name="DistStockDeclarationBean" property="message">
			<font color="red"><B><bean:write name="DistStockDeclarationBean" property="message"/></B></font>
			</logic:notEmpty>
			</td>
			</tr>
		</tbody>
	</table>
	<table>
		<tbody>
			<tr>
				<td colspan="6" class="text"><br>
				<font size=2> I hereby declare that I have following closing stock as on - <b>
<% Date date = new Date();String DATE_FORMAT = "dd/MMM/yyyy"; SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				
				out.println(sdf.format(date));%></b></font></td>
			</tr>
		</tbody>
	</table>
	<br>
<table id="inputTable" width="650px" border="0" align="center">
<tr>
			<td class="txt" width="150px"><strong><bean:message bundle="view" key="stockAdd.product" /></strong><font color="red">*</font></td>
			<td class="txt">
			<html:select property="product" styleId="product" style="width:150px" >
				<html:option value="0"><bean:message bundle="view" key="stockAdd.selectProduct" /></html:option>
				<logic:notEmpty property="productList" name="DistStockDeclarationBean">
					<bean:define id="product" name="DistStockDeclarationBean"
						property="productList" />
					<html:options labelProperty="optionText" property="optionValue"
						collection="product" />
				</logic:notEmpty>
			</html:select>
			</td>
			<td class="txt" width="50px"></td>
</tr>
<tr>
			<td class="txt" width="150px"><strong><bean:message bundle="view" key="stockAdd.closingStock" /></strong><font color="red">*</font></td>
			<td class="txt"><html:text name="DistStockDeclarationBean"
				property="closingStock" styleId="closingStock" style="width:150px"></html:text></td>
			<td class="txt" width="50px"></td>
			</tr>
			<tr>
			<td class="txt" width="150px"><strong><bean:message bundle="view" key="stockAdd.comments" /></strong></td>
			<td class="txt"><html:text name="DistStockDeclarationBean"
				property="comments" styleId="comments" style="width:150px"></html:text></td>
			<td class="txt" width="50px"></td>
			</tr>
			<tr>
			<td class="txt" width="150px"></td>
			<td class="txt"></td>
			<td class="txt" width="50px"></td>
			</tr>
			<tr>
			<td class="txt" width="150px"></td>
			<td class="txt"></td>
			<td class="txt" width="50px"></td>
			</tr>
			<tr>
			<td class="txt" align="center" colspan="4"><input type="button"
				value="Add to Grid" onclick="addRow()" /></td>
		</tr>			
</table>
<br>
<div id="grid">

		<table id="dataTable" width="650px" border="1" cellPadding="2"
			cellSpacing="0" align="center">
			<TR align="center" bgcolor="#CD0504" style="color:#ffffff !important;">
				<TD align="center" bgcolor="#CD0504" width="50px"><SPAN
					class="white10heading mLeft5 mTop5"><strong><bean:message bundle="view" key="stockAdd.tblDelete" /></strong></SPAN></td>
				<TD align="center" bgcolor="#CD0504" width="150px"><SPAN
					class="white10heading mLeft5 mTop5"><strong><bean:message bundle="view" key="stockAdd.tblProdName" /></strong></SPAN></td>
				<TD align="center" bgcolor="#CD0504" width="175px"><SPAN
					class="white10heading mLeft5 mTop5"><strong><bean:message bundle="view" key="stockAdd.tblClosStock" /></strong></SPAN></td>
				<TD align="center" bgcolor="#CD0504" width="175px"><SPAN
					class="white10heading mLeft5 mTop5"><strong><bean:message bundle="view" key="stockAdd.tblcomments" /></strong></SPAN></td>
				
			</tr>
		</table>
		<br>

		<table width="600px" border="0" cellPadding="0" cellSpacing="0" align="center">
			<tr>
				<td width="100%" align="center">
				<input type="button" id="submitAllocation" value="Submit" onclick="submitDataTableRows()" />
				</td>
			</tr>
		</table>
</div>	
</html:form>
</body>
</html>