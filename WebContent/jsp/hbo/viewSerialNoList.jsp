<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ibm.hbo.dto.DistStockDTO"%>
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
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/FindAndDisplay.js"></SCRIPT>

	
<TITLE></TITLE>

<!-- Code is added by Amit Mishra 
	retrive stb no in list
	initialize javascript variable from java stb no list 
 -->
<% 
List stbNoList = new ArrayList();
Object obj = request.getAttribute("serialNoList");
	   if(obj != null)
		  stbNoList = (List) obj;
%>								
<script>
var checkedStbList = new Array();
var stbNoList =new Array();
<%
	DistStockDTO distDto = new DistStockDTO();
	if(stbNoList != null){
		int len  =	stbNoList.size();					
		for (int i=0; i<len;i++){
			distDto = (DistStockDTO) stbNoList.get(i);
%>		
			stbNoList[<%=i%>] ="<%=distDto.getSerialNo()%>";		
<%		} 
	}
%>
// End code 




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
	if(document.forms[0].checkbox.length==undefined){
		document.forms[0].checkbox.checked=false;
	}
	for (counter = 0; counter < document.forms[0].checkbox.length; counter++){
		document.forms[0].checkbox[counter].checked=false;
	}
	getSelectedSTB();	
}

function selectedSerialNos(){
	var splitSnos=opener.document.getElementById("startingSerial").value;
		if (!(splitSnos ==null || splitSnos == "")){
		var currentTagTokens = splitSnos.split( "," );
		var splitTemp = "";
		var i = 0;		
		for(var i=0 ;i<currentTagTokens.length;i++)
		{
			splitTemp = currentTagTokens[i];
			if(document.forms[0].checkbox.length != undefined && document.forms[0].checkbox.length >0){
				for (var counter = 0; counter < document.forms[0].checkbox.length; counter++){
					if (document.forms[0].checkbox[counter].value==splitTemp){
							document.forms[0].checkbox[counter].checked=true;
							break;
					}
	   			}
	   		}else{
		   		if (document.forms[0].checkbox.value == splitSnos){
						document.forms[0].checkbox.checked=true;
	   			}
	   		}	 
		}   	
	   	
	}
	return false;
}

function closeWindow(){
	var serialNos=new Array();
	var total_choices = 0;
	if(document.forms[0].checkbox.length != undefined){
		for (counter = 0; counter < document.forms[0].checkbox.length; counter++){
			if (document.forms[0].checkbox[counter].checked){	
				serialNos[total_choices]=document.forms[0].checkbox[counter].value;	
				++total_choices; 
			}
		}
		
	} else{
		total_choices=1;
		serialNos[0]=document.forms[0].checkbox.value;
		document.forms[0].checkbox.checked==true;			
	}
	if(document.forms[0].checkbox.checked==false){
		total_choices=0;
	}
	window.opener.document.forms[0].startingSerial.value=serialNos;//startingSerial contains total serial in array
	window.opener.document.forms[0].assignedProdQty.value=total_choices;//endingSno contains total serial number		
	window.close();	
}
	
function getSelectedSTB(){
	var serialNos=new Array();
	var total_choices = 0;

	var isCheckBoxExist =false;
	var formsLen = document.forms[0].length;
	var controlType = null;
	for(i = 0; i < formsLen; i++){
		controlType = document.forms[0].elements[i].type;
		if(controlType == "checkbox"){
			isCheckBoxExist=true;
			break;
		}
	}	
	if(isCheckBoxExist == true){

		if(document.forms[0].checkbox.length != undefined){
			for (counter = 0; counter < document.forms[0].checkbox.length; counter++){
				if (document.forms[0].checkbox[counter].checked){	
					serialNos[total_choices]=document.forms[0].checkbox[counter].value;	
					++total_choices; 
				}
			}
		} else{
			total_choices=1;
			serialNos[0]=document.forms[0].checkbox.value;
			document.forms[0].checkbox.checked==true;			
		}
		if(document.forms[0].checkbox.checked==false){
			total_choices=0;
		}
		checkedStbList=serialNos;
		window.opener.document.forms[0].startingSerial.value=serialNos;//startingSerial contains total serial in array
		window.opener.document.forms[0].assignedProdQty.value=total_choices;//endingSno contains total serial number		
	}
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
	
function checkAll(){
	var field=document.forms[0].checkbox
	if(field.length==undefined){
		field.checked = true ;
	}else{
		for (i = 0; i < field.length; i++)
			field[i].checked = true ;
	}
	getSelectedSTB();
}


function uncheckAll(){
	var field=document.forms[0].checkbox
	if(field.length==undefined){
		field.checked = true ;
	}else{
		for (i = 0; i < field.length; i++)
			field[i].checked = false ;
	}
	getSelectedSTB();
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
		width="450" height="22" alt=""><BR>
 <!--  <table>
	<tr><td>Starting Serial No:</td><td><html:text
				property="startingSerialNo" name="AssignDistStockForm"
				readonly="true" style="width:200px;" />&nbsp;&nbsp;</td>
	<td>Ending Serial No:</td><td><html:text property="endingSerialNo" name="AssignDistStockForm" readonly="true" style="width:200px;"/></td></tr>
	
				
</table> -->
	<p> Search Serial No :	<input type = "text" id = "textBoxId" name = "stbNo" title="Type some initial character in STB text box" onKeyUp = "changeVal('tableId','textBoxId',stbNoList)">
	
	<% int i=1; %>
  <logic:notEmpty property="availableSerialNosList" name="AssignDistStockForm">
  
	<div style="width:100%; height:400px; overflow:scroll;" >
	<table id="tableId" border="0" width="80%"  cellpadding="0" cellspacing="0" > 
	<tr>
		
	<logic:iterate id="serialNos" property="availableSerialNosList" name="AssignDistStockForm">
		<% if((i%4) == 1){out.println("</tr><tr>"); }i++;  %>
			<td align="center">
				<input type="checkbox" name="checkbox" height="30" width="25" value='<bean:write name="serialNos" property="serialNo"/>'/>
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
