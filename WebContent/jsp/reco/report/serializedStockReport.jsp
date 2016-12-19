<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.ibm.dp.common.Constants"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="${pageContext.request.contextPath}/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>
<script language="javascript" src="scripts/cal_conf.js">
</script>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/DropDownAjax.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/Ajax.js"
	type="text/javascript">
</SCRIPT>

<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"><!-- 

	
	function exportToExcel(form)
	{
			
		var circleid = document.getElementById("circleid").value;
		var tsmId = document.getElementById("tsmId").value;
		var distId = document.getElementById("distId").value;
		var fseId = document.getElementById("fseId").value;
		var retId = document.getElementById("retId").value;
		var productId = document.getElementById("productId").value;
		var date = document.getElementById("date").value;
		
		
		if(circleid == -1)
		{
		 alert('Please select any Circle.');
		 return false;		
		}		
		if(tsmId == -1)
		{
		 alert('Please select any Tsm.');
		 return false;		
		}
		
	    var url = "serializedStockAction.do?methodName=getSerializedStockDataExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	

function getDistName(){
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
		
	if(tsmId ==""){
		selectedTsmValues =0;
		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
		getFseName();
		getRetName();
	 
		return false;
	 }
	 if(document.forms[0].tsmId.length ==1){
			alert("No TSM exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].tsmId[0].selected){
		
			for (var i=1; i < document.forms[0].tsmId.length; i++)
  	 		{
   		
     			if(selectedTsmValues !=""){
					selectedTsmValues += ",";
     			}
     		var selectedValTSM = document.forms[0].tsmId[i].value.split(",");
     		selectedTsmValues += selectedValTSM[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].tsmId.length; i++)
	  	 {
	   		 if (document.forms[0].tsmId[i].selected)
	     	 {
	     		if(selectedTsmValues !=""){
					selectedTsmValues += ",";
     			}
	     	var selectedValTSM = document.forms[0].tsmId[i].value.split(",");
     		selectedTsmValues += selectedValTSM[0];
	     	 }
	   	}
   }
   		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
 }
}
function getTsmName() 
{
alert("getTsmName");
	    var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var circleid = document.getElementById("circleid").value;
		var selectedCircleValues ="";
		alert(circleid);
	if(circleid ==""){
	getDistName();
	selectedCircleValues =0;
	 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
		
		return false;
	 }
	
	 if(document.forms[0].circleid.length ==1){	 	
			alert("No Circle exists");
		return false;
		}
	   
 	
 	else {
 		
	 	if (document.forms[0].circleid[0].selected){
		   
			for (var i=1; i < document.forms[0].circleid.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}
     		var selectedValCircle = document.forms[0].circleid[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].circleid.length; i++)
	  	 {
	   		 if (document.forms[0].circleid[i].selected)
	     	 {
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}
	     	var selectedValCircle = document.forms[0].circleid[i].value.split(",");
	     	selectedCircleValues += selectedValCircle[0];
	     	 }
	   	}
	   	
   }alert(selectedCircleValues);
   		 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}
function getFseName(){

    	var distId = document.getElementById("distId").value;
    	alert(distId);
	var selectedDistValues ="";
		
	if(distId ==""){
		selectedDistValues =0;
		alert(selectedDistValues);
	  getAllAccountsUnderMultipleAccounts(selectedDistValues,'fseId');
		return false;
	 }
	 if(document.forms[0].distId.length ==1){
			alert("No Dist exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].distId[0].selected){
		
			for (var i=1; i < document.forms[0].distId.length; i++)
  	 		{
   		
     			if(selectedDistValues !=""){
					selectedDistValues += ",";
     			}
     		var selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].distId.length; i++)
	  	 {
	   		 if (document.forms[0].distId[i].selected)
	     	 {
	     		if(selectedDistValues !=""){
					selectedDistValues += ",";
     			}
	     	var selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
	     	 }
	   	}
   } alert(selectedDistValues);
   		 getAllAccountsUnderMultipleAccounts(selectedDistValues,'fseId');
 }
}

function getRetName(){

    	var fseId = document.getElementById("fseId").value;
	var selectedFseValues ="";
		
	if(fseId ==""){
		selectedFseValues =0;
	  getAllAccountsUnderMultipleAccounts(selectedFseValues,'retId');
		return false;
	 }
	 if(document.forms[0].fseId.length ==1){
			alert("No Dist exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].fseId[0].selected){
		
			for (var i=1; i < document.forms[	0].fseId.length; i++)
  	 		{
   		
     			if(selectedFseValues !=""){
					selectedFseValues += ",";
     			}
     		var selectedValFse = document.forms[0].fseId[i].value.split(",");
     		selectedFseValues += selectedValFse[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].fseId.length; i++)
	  	 {
	   		 if (document.forms[0].fseId[i].selected)
	     	 {
	     		if(selectedFseValues !=""){
					selectedFseValues += ",";
     			}
	     	var selectedValFse = document.forms[0].fseId[i].value.split(",");
     		selectedFseValues += selectedValFse[0];
	     	 }
	   	}
   }
   		 getAllAccountsUnderMultipleAccounts(selectedFseValues,'retId');
 }
}

function disableSelected(){
var groupId = document.getElementById("groupId").value;
alert(groupId);
if(groupId==7){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").size=1;
	document.getElementById("circleid").disabled=true;
	var circleid = document.getElementById("circleid").value;
	document.getElementById("tsmId").multiple=false;
	document.getElementById("tsmId").disabled=true;
	document.getElementById("distId").multiple=false;
	document.getElementById("distId").disabled=true;	
	}
if(groupId==6){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").disabled=true;
	document.getElementById("tsmId").multiple=false;
	document.getElementById("tsmId").disabled=true;	
	}
if(groupId==3 || groupId==4 || groupId==5){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").disabled=true;
	}
}
	--></SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="disableSelected()">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">


	<html:form action="/serializedStockAction">

<html:hidden property="isSelectAll" />	
<html:hidden property="isSelectAllDist" />	
		<html:hidden property="methodName"
			value="getStockDetailReportDataExcel" />		
		<html:hidden property="groupId" />

		<TR valign="top">

			<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<!-- Page title start -->
					<TABLE cellpadding="0" cellspacing="0" align="center" width="100%">
						<tr>
							<TD colspan="4" valign="bottom" class="dpPageTitle"><font
								size="4"><strong><bean:message bundle="dp"
								key="title.serialized.stock.report" /></strong></font></TD>
						</tr>

					</TABLE>
					<!-- Page title end --></TD>

				</TR>
				<TR>
					<TD>&nbsp;</TD>
				</TR>

				<TR>
					<TD class="txt" align="center" width="20%"><strong><font
						color="#000000"><bean:message bundle="dp"
						key="label.hierarchy.circle" /></font><font color="red">*</font>: </strong></TD>
					<td><html:select multiple="true" property="circleid"
						styleId="circleid" style="width:150px;" size="3"
						onchange="getTsmName();">
						<logic:notEmpty name="SerializedStockReportFormBean"
							property="arrCIList">
							<html:optionsCollection name="SerializedStockReportFormBean"
								property="arrCIList" label="strText" value="strValue" />
						</logic:notEmpty>
					</html:select></TD>
					</TR>
					<TR>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.tsm.list" /><font color="red">*</font></b>
					</td>
				<td>	
						<html:select   multiple="true"  property="tsmId" styleId="tsmId" style="width:150px;" size="3" onchange="getDistName();">							
						<logic:notEqual name="SerializedStockReportFormBean" property="isSelectAll" value="true">
						<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
						</logic:notEqual>					
							<logic:notEmpty name="SerializedStockReportFormBean" property="tsmList">
							 <html:optionsCollection name="SerializedStockReportFormBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				<TR>
					
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.dist.list" /><font color="red">*</font></b>
					</td>
				<td>	
						<html:select   multiple="true"  property="distId" styleId="distId" style="width:150px;" size="3" onchange="getFseName();">							
							<logic:notEqual name="SerializedStockReportFormBean" property="isSelectAllDist" value="true">
						<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
						</logic:notEqual>						
							<logic:notEmpty name="SerializedStockReportFormBean" property="distList">
							 <html:optionsCollection name="SerializedStockReportFormBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
						</tr><tr>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.fse.list" /><font color="red">*</font></b>
						<td>	
						<html:select   multiple="true"  property="fseId" styleId="fseId" style="width:150px;" size="3" onchange="getRetName();">
						<logic:notEqual name="SerializedStockReportFormBean" property="groupId" value="7">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
						</logic:notEqual>
						
							<logic:notEmpty name="SerializedStockReportFormBean" property="fseList">
							 <html:optionsCollection name="SerializedStockReportFormBean"	property="fseList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				<TR>
					
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.retailer.list" /><font color="red">*</font></b>
					</td>
				<td>	
						<html:select   multiple="true"  property="retId" styleId="retId" style="width:150px;" size="3" onchange="">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
							<logic:notEmpty name="SerializedStockReportFormBean" property="retList">
							 <html:optionsCollection name="SerializedStockReportFormBean"	property="retList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
						</TR><TR>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.product.type" /><font color="red">*</font></b>
						<td>	
						<html:select   multiple="true"  property="productId" styleId="productId" style="width:150px;" size="3" onchange="">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
							<logic:notEmpty name="SerializedStockReportFormBean" property="productList">
							 <html:optionsCollection name="SerializedStockReportFormBean"	property="productList" label="productCategory" value="productCategory"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				<tr>
							<td align="center" width="5%">
							<bean:message bundle="dp" key="label.date.text" />
							</td>
							<td width="20%" align="left">
							<html:text property="date" styleClass="box" styleId="date"
						 name="SerializedStockReportFormBean"/>
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'SerializedStockReportFormBean',
							// input name
							'controlname': 'date'
						});
					
						</script>
							</td>
				</TR>
				<TR><td></td>
					<TD align="left"><html:button property="excelBtn"
						value="Export To Excel" onclick="exportToExcel(this.form);" /></TD>
				</TR>
			</Table>
	</html:form>
	<TR align="center" bgcolor="4477bb" valign="top">

	</TR>

</TABLE>
</BODY>
</html:html>
