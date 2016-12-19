<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=./theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
/*
function totalSerialNos()
{
alert("hiiiiiiiiiii");
alert(document.forms[0].checkbox.length);
var serialNos=new Array();
var total_choices = 0;
alert(document.forms[0].checkbox[0].checked);
	for (counter = 0; counter < document.forms[0].checkbox.length; counter++)
	{
	if (document.forms[0].checkbox[counter].checked)
	{	
	serialNos[counter]=document.forms[0].checkbox[counter].value;	
	//alert(serialNos);
	++total_choices;
	//alert("total_choices :"+total_choices);
	}
}
alert(total_choices);
alert(serialNos);
//return false;
}*/

/*
	function populateSerialNos(obj){
		if(document.getElementById("startingSerialNo").value=="" || document.getElementById("startingSerialNo").value==null){
			document.getElementById("startingSerialNo").value=obj.value;	
		}
		else if(document.getElementById("endingSerialNo").value=="" || document.getElementById("endingSerialNo").value==null){
			if(parseInt(document.getElementById("startingSerialNo").value)>parseInt(obj.value)){
				alert("Starting Serial No Cannot Be Greater Than Ending Serial No");
				return false;
			}
			document.getElementById("btn").disabled=false;
			document.getElementById("endingSerialNo").value=obj.value;
		}	
	}*/
	
	function resetFields(){
		//alert(document.forms[0].checkbox.length);
		if(document.forms[0].checkbox.length==undefined)
		{
		document.forms[0].checkbox.checked=false;
		}
		
		for (counter = 0; counter < document.forms[0].checkbox.length; counter++)
			{
		document.forms[0].checkbox[counter].checked=false;
		}
	}
	
	
	function selectedSerialNos()
	{
	var splitSnos=opener.document.getElementById("startingSerial").value;
	var currentTagTokens = splitSnos.split( "," );
	var splitTemp = "";
	var i = 0;		
		for (var counter = 0; counter < document.forms[0].checkbox.length; counter++)
		{
			splitTemp = currentTagTokens[i];
			if (document.forms[0].checkbox[counter].value==splitTemp)
			{
				document.forms[0].checkbox[counter].checked=true;
				i++;
				continue;
			}
	   	} 

  return false;
}
	
	function closeWindow(){
	
		var serialNos=new Array();
		var total_choices = 0;
		  
		  if(document.forms[0].checkbox.length != undefined){
		   
			
			for (counter = 0; counter < document.forms[0].checkbox.length; counter++)
			{
			if (document.forms[0].checkbox[counter].checked)
			{	
				//alert(document.forms[0].checkbox[counter].value);
				serialNos[total_choices]=document.forms[0].checkbox[counter].value;	
				++total_choices; 
				//alert("total_choices :"+total_choices);
			}
			if(total_choices > 70)
			{
				alert("You Can select maximum 70 serial Numbers at a time");
				return false;
			}			
		}
		
		} else{
		//alert("else");
		total_choices=1;
		serialNos[0]=document.forms[0].checkbox.value;
		document.forms[0].checkbox.checked==true;			
		}
		if(document.forms[0].checkbox.checked==false)
		{
		total_choices=0;
		}
	    window.opener.document.forms[0].startingSerial.value=serialNos;//startingSerial contains total serial in array
		window.opener.document.forms[0].assignedProdQty.value=total_choices;//endingSno contains total serial number		
		//window.opener.document.forms[0].assignedProdQty.value=total_choices;		
		window.close();	
	}
	
	function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}	
	//rajiv jha added for select all and unselect
	
function checkAll()
{
	var field=document.forms[0].checkbox
	//alert(field.length);
	
	if(field.length==undefined){
		field.checked = true ;
	}
	else{
			for (i = 0; i < field.length; i++)
				field[i].checked = true ;
	}
}


function uncheckAll()
{
	var field=document.forms[0].checkbox
	
	if(field.length==undefined)
	{
		field.checked = true ;
	}
	else
	{
		for (i = 0; i < field.length; i++)
			field[i].checked = false ;
	}
}
	
</script>
</HEAD>
<BODY onload="return selectedSerialNos();">
<h2 align="center" ></h2>
<BR>
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>
<html:form action="/viewSerialNoList.do">

		<IMG src="<%=request.getContextPath()%>/images/serialNos.gif"
		width="450" height="22" alt="">
 <!--  <table>
	<tr><td>Starting Serial No:</td><td><html:text
				property="startingSerialNo" name="AssignDistStockForm"
				readonly="true" style="width:200px;" />&nbsp;&nbsp;</td>
	<td>Ending Serial No:</td><td><html:text property="endingSerialNo" name="AssignDistStockForm" readonly="true" style="width:200px;"/></td></tr>
	
				
</table> -->
	
	
	<% int i=1; %>
  <logic:notEmpty property="availableSerialNosList" name="AssignDistStockForm">
  
	<div style="width:100%; height:400px; overflow:scroll;" >
	<table border="1" width="100%">
	<tr>
		
	<logic:iterate id="serialNos" property="availableSerialNosList" name="AssignDistStockForm">
		<% if((i%4) == 1){out.println("</tr><tr>"); }i++;  %>
			<td align="center">
				<input type="checkbox" name="checkbox" value='<bean:write name="serialNos" property="serialNo"/>'/>
				</td><td><bean:write name="serialNos" property="serialNo"/>&nbsp;&nbsp;</td>
			
	</logic:iterate>
	

	</tr>
	</table>
		</div>
	
<table border="1" width="100%">
	<TR>
	<td align="center" width="100%"><html:button property="btn" value="Close" onclick="closeWindow();" />&nbsp;&nbsp;&nbsp;<html:button
				property="btn1" value="Reset" onclick="return resetFields();" />
				<input type="button" name="CheckAll" value="Check All" onClick="checkAll()">
               <input type="button" name="UnCheckAll" value="Uncheck All" onClick="uncheckAll()">
	         </td>
				</TR>	
	</table>
	
  </logic:notEmpty>	

<br>

</html:form>
</BODY>
</html:html>
