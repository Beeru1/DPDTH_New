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

<SCRIPT language="javascript" type="text/javascript">
function selectedCount(ctrl){
	var count = 0;
	for(i=1; i < ctrl.length; i++){
		if(ctrl[i].selected){
			count++;
		}
	}
	return count;
}

function selectAllStatus(){

		var ctrl = document.forms[0].stbStatus;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].stbStatus[0].selected){
			for (var i=1; i < document.forms[0].stbStatus.length; i++){
  	 			document.forms[0].stbStatus[i].selected =true;
     		}
   		}
   	
}
function selectAllProduct(){

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
function selectAllFSE(){
		var ctrl = document.forms[0].fseId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].fseId[0].selected){
			for (var i=1; i < document.forms[0].fseId.length; i++){
  	 			document.forms[0].fseId[i].selected =true;
     		}
   		}
   	
}
function selectAllRet(){
		var ctrl = document.forms[0].retId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].retId[0].selected){
			for (var i=1; i < document.forms[0].retId.length; i++){
  	 			document.forms[0].retId[i].selected =true;
     		}
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
	function exportToExcel(form)
	{	
		var groupId = document.getElementById("groupId").value;
		var showCircle='<bean:write name="STBWiseSerializedStockReportFormBean" property="showCircle" />';
		var showTSM='<bean:write name="STBWiseSerializedStockReportFormBean" property="showTSM" />';
		var showDist='<bean:write name="STBWiseSerializedStockReportFormBean" property="showDist" />';
		
			
		if(showCircle=="A"){
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	  	}
		if(showTSM=="A"){
			if(document.getElementById("tsmId").value ==""){
				alert("Please Select atleast one TSM");
				return false;
	   		}
	  		if(document.forms[0].tsmId.length ==1){
				alert("No TSM exists under the Cirlce");
				return false;
	   		}
		}
		if(showDist=="A"){
			if(document.getElementById("distId").value ==""){
				alert("Please Select atleast one Distributor");
				return false;
	   		}
	   		var selectedDistValues = "";
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
   		}
   		 document.getElementById("hiddenDistSelecIds").value =selectedDistValues;
	  	}
	  	
	  	
	  	if(document.getElementById("productId").value =="") {
				alert("Please Select atleast one Product Category");
				return false;
	   		}
	   		var selectedProductValues = "";
	   		if (document.forms[0].productId[0].selected){
		
			for (var i=1; i < document.forms[0].productId.length; i++)
  	 		{
   		
     			if(selectedProductValues !=""){
					selectedProductValues += ",";
     			}
     		var selectedValProduct = document.forms[0].productId[i].value.split(",");
     		selectedProductValues += selectedValProduct[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].productId.length; i++)
	  	 	{
	   		 if (document.forms[0].productId[i].selected)
	     	 {
	     		if(selectedProductValues !=""){
					selectedProductValues += ",";
     			}
	     	var selectedValProduct = document.forms[0].productId[i].value.split(",");
     		selectedProductValues += selectedValProduct[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenProductSeletIds").value =selectedProductValues;
   		 
   		 
   		 
   		 
   		 
   		 	if(document.getElementById("stbStatus").value =="") {
				alert("Please Select atleast one STB Status");
				return false;
	   		}
	   		var selectedStatusValues = "";
	   		if (document.forms[0].stbStatus[0].selected){
		
			for (var i=1; i < document.forms[0].stbStatus.length; i++)
  	 		{
   		
     			if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
     		var selectedStatusValuesArr = document.forms[0].stbStatus[i].value.split(",");
     		selectedStatusValues += selectedStatusValuesArr[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].stbStatus.length; i++)
	  	 	{
	   		 if (document.forms[0].stbStatus[i].selected)
	     	 {
	     		if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
	     	var selectedStatusValuesArr = document.forms[0].stbStatus[i].value.split(",");
     		selectedStatusValues += selectedStatusValuesArr[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenStbStatus").value =selectedStatusValues;
   		 
   		 
   		if(groupId == 7)
   		{
   			if(document.getElementById("fseId").value ==""){
				alert("Please Select atleast one FSE");
				return false;
	   		}
	   		if(document.getElementById("retId").value ==""){
				alert("Please Select atleast one Retailer Id");
				return false;
	   		}
	   		
	   		
	   		var SelectedFse = "";
	   		if (document.forms[0].fseId[0].selected){
			
			for (var i=1; i < document.forms[0].fseId.length; i++)
  	 		{
   		
     			if(SelectedFse !=""){
					SelectedFse += ",";
     			}
     		var SelectedFseVal = document.forms[0].fseId[i].value.split(",");
     		SelectedFse += SelectedFseVal[0];
   			}
			}	
		else{
	   		
	   		
	   		for (var i=0; i < document.forms[0].fseId.length; i++)
		  	 {
		   		 if (document.forms[0].fseId[i].selected)
		     	 {
		     		if(SelectedFse !=""){
						SelectedFse += ",";
	     			}
			     	var SelectedFseVal = document.forms[0].fseId[i].value.split(",");
		     		SelectedFse += SelectedFseVal[0];
		     	 }
		   	}
		   	}
		   	 document.getElementById("hiddenFseSelecIds").value =SelectedFse;
		   	 
		   	 var SelectedRET = "";
		   	 
		   	 if (document.forms[0].retId[0].selected){
			
			for (var i=1; i < document.forms[0].retId.length; i++)
  	 		{
   		
     			if(SelectedRET !=""){
					SelectedRET += ",";
     			}
     		var SelectedRETVal = document.forms[0].retId[i].value.split(",");
     		SelectedRET += SelectedRETVal[0];
   			}
			}	
		else{
	   		for (var i=0; i < document.forms[0].retId.length; i++)
		  	 {
		   		 if (document.forms[0].retId[i].selected)
		     	 {
		     		if(SelectedRET !=""){
						SelectedRET += ",";
	     			}
			     	var SelectedRETVal = document.forms[0].retId[i].value.split(",");
		     		SelectedRET += SelectedRETVal[0];
		     	 }
//		     	 alert("SelectedRET:"+SelectedRET);
		   	}
		   	}
		   	 document.getElementById("hiddenRetSeletIds").value =SelectedRET;
		   	 
		   	 //alert(document.getElementById("hiddenFseSelecIds").value);
//		   	 alert("Final::"+document.getElementById("hiddenRetSeletIds").value);
	   	}
   		
	  	var url = "stbWiseSerializedStockAction.do?methodName=getSerializedStockDataExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	

function getDistName(){
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
		
	if(tsmId ==""){
		alert("Please Select atleast one TSM");
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


 var tsmId = document.getElementById("tsmId").value;
 if(tsmId ==""){
		selectedTsmValues =0;
		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
		getFseName();
		getRetName();
	 
		return false;
	 }

}
function getTsmName() 
{

	    var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var circleId = document.getElementById("circleId").value;
		var selectedCircleValues ="";
		
	if(circleId ==""){
		alert("Please Select atleast one Circle");
		return false;
	 }
	 if(document.forms[0].circleId.length ==1){
			alert("No Circle exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].circleId[0].selected){
		
			for (var i=1; i < document.forms[0].circleId.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}
     		var selectedValCircle = document.forms[0].circleId[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].circleId.length; i++)
	  	 {
	   		 if (document.forms[0].circleId[i].selected)
	     	 {
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}
	     	var selectedValCircle = document.forms[0].circleId[i].value.split(",");
	     	selectedCircleValues += selectedValCircle[0];
	     	 }
	   	}
   }
   	 	document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
    document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
   		 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}
function getFseName(){
var groupId = document.getElementById("groupId").value;

    	var distId = document.getElementById("distId").value;
	var selectedDistValues ="";
		
	if(distId ==""){
		selectedDistValues =0;
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
   } 
   //alert(selectedDistValues);
   document.getElementById("hiddenDistSelecIds").value =selectedDistValues;
   if(groupId==7){
   		 getAllAccountsUnderMultipleAccounts(selectedDistValues,'fseId');
 }
 else{
 return;}
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
   		document.getElementById("hiddenFseSelecIds").value =selectedFseValues;
   		 getAllAccountsUnderMultipleAccounts(selectedFseValues,'retId');
 }
 selectAllFSE();
}


</SCRIPT>


</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">


	<html:form action="/stbWiseSerializedStockAction">


	
		
		<html:hidden property="methodName" value="getStockDetailReportDataExcel" />		
		<html:hidden property="groupId" />
		<html:hidden property="hiddenCircleSelecIds" name="STBWiseSerializedStockReportFormBean" />
		<html:hidden property="hiddenTsmSelecIds" name="STBWiseSerializedStockReportFormBean" />
		<html:hidden property="hiddenDistSelecIds" name="STBWiseSerializedStockReportFormBean" />
		<html:hidden property="hiddenProductSeletIds" name="STBWiseSerializedStockReportFormBean" />
		<html:hidden property="hiddenFseSelecIds" name="STBWiseSerializedStockReportFormBean" />
		<html:hidden property="hiddenRetSeletIds" name="STBWiseSerializedStockReportFormBean" />
		<html:hidden property="hiddenStbStatus" name="STBWiseSerializedStockReportFormBean" />
		<TR valign="top">

			<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<!-- Page title start -->
					<IMG src="${pageContext.request.contextPath}/images/STB_Wise_Serialized_Stock_Report.jpg"
									width="410" height="24" alt="">
					<!-- Page title end --></TD>

				</TR>
				<TR>
					<TD>&nbsp;</TD>
				</TR>
				
				<logic:match value="A" property="showCircle" name="STBWiseSerializedStockReportFormBean">
				<TR>					
					<TD class="txt" align="center" width="20%"><strong><font
						color="#000000"><bean:message bundle="dp"
						key="label.hierarchy.circle" /></font><font color="red">*</font>: </strong></TD>
					<td>
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px;" size="6" onchange="getTsmName();">
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="circleList">
								 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			
					
					</TR>
					</logic:match>
			<logic:match value="A" property="showTSM" name="STBWiseSerializedStockReportFormBean">		
					<TR>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.tsm.list" /><font color="red">*</font>:</b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" styleId="tsmId" style="width:150px;" size="6" onchange="getDistName();selectAllTSM();">
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="tsmList">
							 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				</logic:match>
				
				<logic:match value="A" property="showDist" name="STBWiseSerializedStockReportFormBean">
				<TR>
					
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.dist.list" /><font color="red">*</font>:</b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="distId" styleId="distId" style="width:150px;" size="6" onchange="getFseName();selectAllDist();" >
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="distList">
								 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
						</tr>
					</logic:match>	
						<logic:equal name="STBWiseSerializedStockReportFormBean" property="groupId" value="7" >
						<tr>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.fse.list" /><font color="red">*</font>:</b>
						<td>	
						<html:select   multiple="true"  property="fseId" styleId="fseId" style="width:150px;" size="3" onchange="getRetName();">
						<logic:empty name="STBWiseSerializedStockReportFormBean" property="fseList">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
					</logic:empty>
						
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="fseList">
							 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="fseList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
					
				<TR>
					
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.retailer.list" /><font color="red">*</font>:</b>
					</td>
				<td>	

						<html:select   multiple="true"  property="retId" styleId="retId" style="width:150px;" size="3" onchange="selectAllRet();">

						<logic:empty name="STBWiseSerializedStockReportFormBean" property="retList">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
						</logic:empty>
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="retList">
							 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="retList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
						</TR>
						</logic:equal>
						<TR>
						<TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp"
						key="label.product.type" /><font color="red">*</font>:</b>
						<td>	
						<html:select   multiple="true"  property="productId" styleId="productId" style="width:150px;" size="3" onchange="selectAllProduct();">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="productList">
							 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="productList" label="productCategory" value="productCategoryId"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				<tr><TD align="center"><b class="text pLeft15">
						<bean:message bundle="dp" key="label.stb.status" /><font color="red">*</font>:</b>
						<td>		

						<html:select   multiple="true"  property="stbStatus" styleId="stbStatus" style="width:150px;" size="3" onchange="selectAllStatus();">

							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
							<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="stbStatusList">
							 <html:optionsCollection name="STBWiseSerializedStockReportFormBean"	property="stbStatusList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>					
							
				</TR>
				<TR><td></td>
					<TD align="left"><html:button property="excelBtn"
						value="Export To Excel" onclick="exportToExcel(this.form);" /></TD>
				</TR>
			</Table>
	
	<logic:match value="A" property="showCircle" name="STBWiseSerializedStockReportFormBean">
	<script>
			document.getElementById("circleId").value = "-1";
	</script>
	</logic:match>
	</html:form>
	<TR align="center" bgcolor="4477bb" valign="top">

	</TR>
	
</TABLE>
</BODY>
</html:html>
