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

	function daysBetween(date1, date2) 
	{
	    var DSTAdjust = 0;
	    // constants used for our calculations below
	    oneMinute = 1000 * 60;
	    var oneDay = oneMinute * 60 * 24;
	    // equalize times in case date objects have them
	    date1.setHours(0);
	    date1.setMinutes(0);
	    date1.setSeconds(0);
	    date2.setHours(0);
	    date2.setMinutes(0);
	    date2.setSeconds(0);
	    // take care of spans across Daylight Saving Time changes
	    if (date2 > date1) {
	        DSTAdjust = (date2.getTimezoneOffset() - date1.getTimezoneOffset()) * oneMinute;
	    } else {
	        DSTAdjust = (date1.getTimezoneOffset() - date2.getTimezoneOffset()) * oneMinute;    
	    }
	    var diff = Math.abs(date2.getTime() - date1.getTime()) - DSTAdjust;
	    return Math.ceil(diff/oneDay);
	}
	function exportToExcel(form)
	{
			
		var circleid = document.getElementById("circleid").value;
		var tsmId = document.getElementById("tsmId").value;
		var distId = document.getElementById("distId").value;	
		var productId = document.getElementById("productId").value;
		if(circleid =="" )
		{
		 alert('Please select any Circle.');
		 return false;		
		}		
		if(tsmId=="")
		{
		 alert('Please select any Tsm.');
		 return false;		
		}
		if(distId =="")
		{
		 alert('Please select any Distributor.');
		 return false;		
		}
		if(getProductName()==false){
		return false;
		}
		
		var fromDate = document.getElementById("fromDate").value;
			if ((fromDate==null)||(fromDate=="0") || fromDate==""){
				alert('From Date is Required');
				return false;
			}
			
			var toDate = document.getElementById("toDate").value;
		    if ((toDate==null)||(toDate=="0") || toDate==""){
				alert('To Date is Required');
				return false;
			}
			
	
			var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	    	var currentDate = new Date();
	    	
	    	if(startDate > endDate)
	    	{
	    		 alert('From date can not be greater than To Date');
	  		  	 return false;
	    	}
	    	if(endDate > currentDate)
	    	{
	    		 alert('To date can not be greater than todays Date');
	  		  	 return false;
	    	}
	    	
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	    	if(daysBetween(startDate,endDate) > 31)
			{
			  alert('Please select dates having only max 31 days difference');
			  return false;
			}
	    var url = "activationDetailAction.do?methodName=getActionDetailReportDataExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
function getProductName(){	
if(document.getElementById("productId").value ==""){
				alert("Please Select atleast Product Type");
				return false;
	   		}
	   		var selectedProductTypeValues = "";
	   		if (document.forms[0].productId[0].selected){
		
			for (var i=1; i < document.forms[0].productId.length; i++)
  	 		{
   		
     			if(selectedProductTypeValues !=""){
					selectedProductTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedProductTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].productId.length; i++)
	  	 	{
	   		 if (document.forms[0].productId[i].selected)
	     	 {
	     		if(selectedProductTypeValues !=""){
					selectedProductTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedProductTypeValues += selectedVal[0];
	     	 }
	   		}
   		}   	
   		 document.getElementById("hiddenProductTypeSelec").value =selectedProductTypeValues;
 }  		 
   		 
function getDistName(){
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
		
	if(tsmId ==""){
		selectedTsmValues =0;
		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
 
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
   		 document.getElementById("hiddenTsmSelecIds").value =selectedTsmValues;
   		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
 }
}
function getTsmName() 
{		

	    var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var circleid = document.getElementById("circleid").value;				
		selectedCircleValues =0;
		getAllAccountsUnderMultipleAccounts(selectedCircleValues,'distId');

	
	 if(document.forms[0].circleid.length ==1){	 	
			alert("No Circle exists");
		return false;
		}
	   
 	
 	else {
 		
	 	if (document.forms[0].circleid[0].selected || document.forms[0].circleid[1].selected){
		   
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
	   	
   }
  		 document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
   		 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}
function getFseName(){

    	var distId = document.getElementById("distId").value;
		var selectedDistValues ="";
		
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
  		   }document.getElementById("hiddenDistSelecIds").value =selectedDistValues;
   		 
 		}
	}


function disableSelected(){
var groupId = document.getElementById("groupId").value;
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

function selectAllTSM(){
		var ctrl = document.forms[0].tsmId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		/*if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}*/
		
		
		if (document.forms[0].tsmId[0].selected){
			for (var i=1; i < document.forms[0].tsmId.length; i++){
  	 			document.forms[0].tsmId[i].selected =true;
     		}
   		}
   	
}

function selectAllDist(){
		var ctrl = document.forms[0].distId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].distId[0].selected){
			for (var i=1; i < document.forms[0].distId.length; i++){
  	 			document.forms[0].distId[i].selected =true;
     		}
   		}
   	
}



function selectAllProducts(){
		var ctrl = document.forms[0].productId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].productId[0].selected){
			for (var i=1; i < document.forms[0].productId.length; i++){
  	 			document.forms[0].productId[i].selected =true;
     		}
   		}
   	
}
	
function selectAllCircles(){
		var ctrl = document.forms[0].circleid;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].circleid[0].selected){
			for (var i=1; i < document.forms[0].circleid.length; i++){
  	 			document.forms[0].circleid[i].selected =true;
     		}
   		}
   	
}
function selectedCount(ctrl){
	var count = 0;
	for(i=1; i < ctrl.length; i++){
		if(ctrl[i].selected){
			count++;
		}
	}
	return count;
}
	--></SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="disableSelected()">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">


	<html:form action="/activationDetailAction">

	<html:hidden property="hiddenCircleSelecIds" name="ActivationDetailReportFormBean"/>
	<html:hidden property="hiddenTsmSelecIds" />
	<html:hidden property="hiddenDistSelecIds"/>
	<html:hidden property="hiddenProductTypeSelec"/>
	
		<html:hidden property="methodName"
			value="getActionDetailReportDataExcel" />		
		<html:hidden property="groupId" />

		<TR valign="top">

			<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<!-- Page title start -->
					
								<IMG src="${pageContext.request.contextPath}/images/Activation_Detail_Report.jpg"
									width="410" height="24" alt="">
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
						onchange="getTsmName();selectAllCircles()">
						<html:option value="-1">-All-</html:option>
						<logic:notEmpty name="ActivationDetailReportFormBean"
							property="arrCIList">
							<html:optionsCollection name="ActivationDetailReportFormBean"
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
						<html:select   multiple="true"  property="tsmId" styleId="tsmId" style="width:150px;" size="3" onchange="getDistName();selectAllTSM()">							
									
							<logic:notEmpty name="ActivationDetailReportFormBean" property="tsmList">
							 <html:optionsCollection name="ActivationDetailReportFormBean"	property="tsmList" label="strText" value="strValue"/> 
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
						<html:select   multiple="true"  property="distId" styleId="distId" style="width:150px;" size="3" onchange="getFseName();selectAllDist();">							
										
							<logic:notEmpty name="ActivationDetailReportFormBean" property="distList">
							 <html:optionsCollection name="ActivationDetailReportFormBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
						</tr>
				<TR>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.product.type" /><font color="red">*</font></b>
						<td>	
						<html:select   multiple="true"  property="productId" styleId="productId" style="width:150px;" size="3" onchange="selectAllProducts()">
						<html:option value="-1">-All-</html:option>
							<logic:notEmpty name="ActivationDetailReportFormBean" property="productList">
							 <html:optionsCollection name="ActivationDetailReportFormBean"	property="productList" label="productCategory" value="productCategoryId"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				<tr>
							<TD align="center"><b class="text pLeft15">
							<bean:message bundle="dp" key="label.reco.from.date" /><font color="red">*</font></b>
							</td>
							<td width="20%" align="left">
							<html:text property="fromDate" styleClass="box" styleId="date"
						 name="ActivationDetailReportFormBean"/>
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'ActivationDetailReportFormBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
							</td>
				</TR>
				<tr>
							<TD align="center"><b class="text pLeft15">
							<bean:message bundle="dp" key="label.reco.to.date" /><font color="red">*</font></b>
							</td>
							<td width="20%" align="left">
							<html:text property="toDate" styleClass="box" styleId="date"
						 name="ActivationDetailReportFormBean"/>
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'ActivationDetailReportFormBean',
							// input name
							'controlname': 'toDate'
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
