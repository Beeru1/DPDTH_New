<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%try{ %>   
<%@page import="com.ibm.dp.common.Constants"%>
<html>
<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>

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
	
function exportToExcel(str) {
	var skipValidation = false
	//alert("document.getElementById(accountTypeMultiSelectIds).value  :"+document.getElementById("accountTypeMultiSelectIds").value );
	/*	if(typeof(document.forms[0].searchOption)!="undefined") {
			var selectedIndex = document.getElementById("searchOption").selectedIndex;
			if(selectedIndex != '0') {
				skipValidation = true; 
			} 
		}*/
		
		var searchOption =document.getElementById("searchOption").value;
			if(searchOption != "-1"){
				var searchCriteria = document.getElementById("searchCriteria").value;
					if ((searchCriteria==null)||(searchCriteria=="0") || searchCriteria==""){
					alert('Search Criteria is Required');
					return false;
				}	
					if(searchOption == "PIN_CODE"){
					searchCriteria = document.getElementById("searchCriteria").value;
					if (!isNumericNumber(searchCriteria)){
					alert('Only Numeric Value Allowed in Pin Code');
					return false;
				}	
				}
			}	
		
		if(document.forms[0].showCircle.value=="A" && !skipValidation){
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	  	}
	  	
	  		var accountType =document.getElementById("accountType").value;
	  	//alert("accountType :"+accountType);
	  	if(accountType=="")
	  	{
	  	alert("Please Select atleast one account type");
				return false;
	  	}
		/*if(document.forms[0].showTSM.value=="A"){
			if(document.getElementById("tsmId").value ==""){
				alert("Please Select atleast one TSM");
				return false;
	   		}
	  		if(document.forms[0].tsmId.length ==1){
				alert("No TSM exists under the Cirlce");
				return false;
	   		}
		}
		if(document.forms[0].showDist.value=="A"){
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
	    	
	    	if(daysBetween(startDate,endDate) >= 30)
			{
			  alert('Please select dates having only max 30 days difference');
			  return false;
			}*/
	    
	   // var searchCriteris = document.getElementById("searchCriteria").value;
	   // var option =document.getElementById("searchOption").value;
	  //   if(option !="-1"){
		//	    if(searchCriteris == ""){
		//	    	alert("Please Enter Search Criteria");
		//	    	return false;
		//	    }
		//	 }
	    // document.forms[0].methodName.value = "exportExcel";
	    
	    // added by Rampratap on 03-07-13 for updateReport
	    if(str!='undefined' && str=='updateReport')
	    {
	    document.forms[0].action = "accountDetailReport.do?methodName=exportExcel&&reportName=updateReport";
	    }
	    else
	    {
		document.forms[0].action = "accountDetailReport.do?methodName=exportExcel";
		}
		setInterval(getReportDownloadStatus,1000);
		//niki
		
		var accountIdVal = document.getElementById("accountType").value;
		var selectedaccountValues ="";
		//alert("accountIdVal.."+accountIdVal);
		//alert("Export to excel  ::"+document.getElementById("accountTypeMultiSelect").value)//accountTypeMultiSelect
		if(document.getElementById("accountTypeMultiSelect").value!="YES")
		{
			if(accountIdVal !="-1")
			{
					selectedaccountValues=accountIdVal;
					document.getElementById("accountTypeMultiSelectIds").value =selectedaccountValues;
					//alert(" a selectedaccountValues"+selectedaccountValues);	
			}
		 	else
		 	{
			 			//alert("niki"+document.forms[0].accountType.length);
				
					for (var i=1; i < document.forms[0].accountType.length; i++)
		  	 		{
		     			if(selectedaccountValues !=""){
							selectedaccountValues += ",";
		     			}
		     		var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
		     		//alert("niki"+selectedValAccountType);
		     		selectedaccountValues += selectedValAccountType[0];
	   			}
	   			//alert(" b selectedaccountValues"+selectedaccountValues);
	   			document.getElementById("accountTypeMultiSelectIds").value =selectedaccountValues;
			}	
	   		//alert("selectedaccountValues"+selectedaccountValues);
	   	 	//niki
		}
		
		
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
   		 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}

function setSelectedCircles() 
{

	    //var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
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
 	//alert(document.forms[0].circleId[0].value);
	 	if (document.forms[0].circleId[0].selected){
		
			for (var i=0; i < document.forms[0].circleId.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}
     		var selectedValCircle = document.forms[0].circleId[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
   			}
		}	
		else{
			for (var i=0; i < document.forms[0].circleId.length; i++)
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
   		 //getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
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

function selectAllTSM(){
		var ctrl = document.forms[0].tsmId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
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
function selectAllAccountType(){
		var ctrl = document.forms[0].accountType;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].accountType[0].selected){
			for (var i=1; i < document.forms[0].accountType.length; i++){
  	 			document.forms[0].accountType[i].selected =true;
     		}
   		}
   	
}
function setSelectedAccountType() 
{

	    //var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var accountIdVal = document.getElementById("accountType").value;
		var selectedaccountValues ="";
		
	if(accountIdVal ==""){
		alert("Please Select atleast one account type");
		return false;
	 }
	 if(document.forms[0].accountType.length ==1){
			alert("No account type exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].accountType[0].selected){
		
			for (var i=1; i < document.forms[0].accountType.length; i++)
  	 		{
   		
     			if(selectedaccountValues !=""){
					selectedaccountValues += ",";
     			}
     		var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
     		selectedaccountValues += selectedValAccountType[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].accountType.length; i++)
	  	 {
	   		 if (document.forms[0].accountType[i].selected)
	     	 {
	     		if(selectedaccountValues !=""){
					selectedaccountValues += ",";
	     		}
	     	var selectedValaccount = document.forms[0].accountType[i].value.split(",");
	     	selectedaccountValues += selectedValaccount[0];
	     	 }
	   	}
   }
   	 	document.getElementById("accountTypeMultiSelectIds").value =selectedaccountValues;
   		 //getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}

function selectAllAccountType(){
		var ctrl = document.forms[0].accountType;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].accountType[0].selected){
			for (var i=1; i < document.forms[0].accountType.length; i++){
  	 			document.forms[0].accountType[i].selected =true;
     		}
   		}
   	
}
function setSelectedAccountType() 
{
	    //var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var accountIdVal = document.getElementById("accountType").value;
		var selectedaccountValues ="";
		var selectedValaccount="";
		
	if(accountIdVal ==""){
		alert("Please Select atleast one account type");
		return false;
	 }
	 if(document.forms[0].accountType.length ==1){
			alert("No account type exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].accountType[0].selected){
		
			for (var i=1; i < document.forms[0].accountType.length; i++)
  	 		{
   		
     			if(selectedaccountValues !=""){
					selectedaccountValues += ",";
     			}
     		var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
     		selectedaccountValues += selectedValAccountType[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].accountType.length; i++)
	  	 {
	   		 if (document.forms[0].accountType[i].selected)
	     	 {
	     	 	if(selectedaccountValues !=""){
					selectedaccountValues += ",";
	     		}
	     	selectedValaccount = document.forms[0].accountType[i].value.split(",");
	     	selectedaccountValues += selectedValaccount[0];
	     	 }
	   	}
   }
   	 	document.getElementById("accountTypeMultiSelectIds").value =selectedaccountValues;
   	 	//alert("selectedaccountValues :"+selectedaccountValues);
   		 //getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}

function selectAllCircle(){
		var ctrl = document.forms[0].circleId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].circleId[0].selected){
			for (var i=1; i < document.forms[0].circleId.length; i++){
  	 			document.forms[0].circleId[i].selected =true;
     		}
   		}
   	
}

function selectAllPoStatus(){
		var ctrl = document.forms[0].poStatus;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].poStatus[0].selected){
			for (var i=1; i < document.forms[0].poStatus.length; i++){
  	 			document.forms[0].poStatus[i].selected =true;
     		}
   		}
   	
}
function showCalender(){
	var dateOption =document.getElementById("dateOption").value;
	
	if(dateOption == "-1"){
		document.getElementById("fromDate").value ="";
		document.getElementById("toDate").value ="";
		document.getElementById("calId").style.display = 'none';
		
	}else{
		document.getElementById("calId").style.display = 'inline';
	}
}

function showSearch(){
	var searchOption =document.getElementById("searchOption").value;
	
	if(searchOption == "-1"){
		document.getElementById("searchCriteria").value ="";
		document.getElementById("searchRow").style.display ='none';
	} else {
		document.getElementById("searchRow").style.display = 'inline';	
	}
	/*
	if(searchOption == "-1"){
		document.getElementById("searchCriteria").value ="";
		document.getElementById("searchRow").style.display ='none';
		document.getElementById("searchId").style.display = 'none';
		document.getElementById("searchValue").style.display = 'none';
				
		document.getElementById("loginId1").value ="";
		document.getElementById("loginId1").style.display = 'none';
		
		document.getElementById("accountName1").value ="";
		document.getElementById("accountName1").style.display = 'none';
	}
	else if(searchOption == "LOGIN_ID"){
		document.getElementById("searchCriteria").value = "";
			document.getElementById("searchRow").style.display ='none';			
		document.getElementById("accountName1").value ="";
		document.getElementById("accountName1").style.display = 'none';		
		
		document.getElementById("loginId1").style.display = 'inline';	
	}
	else if(searchOption == "ACCOUNT_NAME"){
		document.getElementById("searchCriteria").value = "";
			document.getElementById("searchRow").style.display ='none';
		document.getElementById("loginId1").value ="";
		document.getElementById("loginId1").style.display = 'none';		
		
		document.getElementById("accountName1").style.display = 'inline';
	}
	else {
		document.getElementById("loginId1").value ="";
		document.getElementById("loginId1").style.display = 'none';		
		document.getElementById("accountName1").value ="";
		document.getElementById("accountName1").style.display = 'none';	
		document.getElementById("searchRow").style.display = 'inline';	
	}*/
}
--></SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form name="AccountDetailReportBean" action="/accountDetailReport" type="com.ibm.dp.beans.AccountDetailReportBean"  method="post"  enctype="multipart/form-data" >
	<html:hidden property="showCircle" name="AccountDetailReportBean" />
	<html:hidden property="showTSM" name="AccountDetailReportBean" />
	<html:hidden property="showDist" name="AccountDetailReportBean" />
	<html:hidden property="methodName" styleId="methodName" />
	
	<html:hidden property="hiddenCircleSelecIds" name="AccountDetailReportBean" />
	<html:hidden property="hiddenTsmSelecIds" name="AccountDetailReportBean" />
	<html:hidden property="hiddenDistSelecIds" name="AccountDetailReportBean" />
	<html:hidden property="hiddenPoStatusSelec" name="AccountDetailReportBean" />
	<html:hidden property="accountTypeMultiSelect" name="AccountDetailReportBean" />
	<html:hidden property="accountTypeMultiSelectIds" name="AccountDetailReportBean" />
	
	<TR valign="top">
		
		<td>
			<TABLE width="800" border="0" cellpadding="5" cellspacing="0" class="text"  align="top">
				<TR>
					<TD colspan="4" valign="bottom"  class="dpReportTitle">
								Account Detail Report
					</TD>
		
				</TR>
			<TR>
				<logic:match value="A" property="showCircle" name="AccountDetailReportBean">	
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td height="46">	
						
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px;" size="6" onchange="setSelectedCircles();selectAllCircle();">
							<logic:notEmpty name="AccountDetailReportBean" property="circleList">
								 <html:optionsCollection name="AccountDetailReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
						<TD colspan="2"></TD>
			</logic:match>
				</TR>
			
				
				<TR align="center">
					<!--  adding by pratap for account type multi select for dthadmin and circle admin  -->
			<logic:equal value="YES" property="accountTypeMultiSelect" name="AccountDetailReportBean">
			<TD align="center"><b class="text pLeft15"> Account Type <font color="red">*</font></b>
					</TD>
					<TD class="txt" align="left"  width="275" ><html:select
						property="accountType" style="width:150px" styleId="accountType"
						name="AccountDetailReportBean" multiple="true"style="width:150px;" size="6" onchange="selectAllAccountType();setSelectedAccountType();">
					<logic:notEmpty name="AccountDetailReportBean" property="accountTypeList">								
								 <html:optionsCollection name="AccountDetailReportBean"	property="accountTypeList" label="value" value="id"/> 
					</logic:notEmpty>

					</html:select></TD>
			</logic:equal>
				<logic:notEqual value="YES" property="accountTypeMultiSelect" name="AccountDetailReportBean">
				<TD align="center"><b class="text pLeft15"> Account Type <font color="red">*</font></b>
					</TD>
					<TD class="txt" align="left"  width="275" ><html:select
						property="accountType" style="width:150px" styleId="accountType"
						name="AccountDetailReportBean">
					<logic:notEmpty name="AccountDetailReportBean" property="accountTypeList">								
								 <html:optionsCollection name="AccountDetailReportBean"	property="accountTypeList" label="value" value="id"/> 
					</logic:notEmpty>

					</html:select></TD>
				</logic:notEqual>
			<!--  end  by pratap for account type multi select for dthadmin and circle admin  -->


					<TD align="center"><b class="text pLeft15"> Status <font color="red">*</font></b>
					</TD>
					<TD class="txt" align="left"  width="275" ><html:select
						property="status" style="width:150px" styleId="status"
						name="AccountDetailReportBean">
						<html:option value="0">
							<bean:message bundle="view" key="report.option.all" />
						</html:option>
						<html:option value="1">
							<bean:message bundle="view" key="report.active" />
						</html:option>
						<html:option value="2">
							<bean:message bundle="view" key="report.inactive" />
						</html:option>
					
						<html:option value="3">
							<bean:message bundle="view" key="report.close" />
						</html:option>
					
					</html:select></TD>
				</TR>
				<%--				
				<TR id="calId">
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="AccountDetailReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'AccountDetailReportBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
			
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="AccountDetailReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'AccountDetailReportBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
	
						 </FONT>
					</TD>
					
					
				</TR>
				--%>
				<TR>
					<TD align="center"><b class="text pLeft15"> Search Option  <span style="display:none"><font color="red">*</font></span></b>
					</TD>
					<TD>	
						<html:select  property="searchOption" styleId="searchOption" style="width:150px;" onchange="showSearch();">
							<html:option value="-1">-Blank-</html:option>
							<html:option value="LOGIN_ID">Login Id</html:option>
							<html:option value="ACCOUNT_NAME">Account Name</html:option>
							<html:option value="MOBILE_NO">Mobile No.</html:option>
							<html:option value="EMAIL_ID">Email Id</html:option>
							<html:option value="PIN_CODE">Pin Code</html:option>
							<html:option value="CITY">City</html:option>
						</html:select>
					</TD>
				</TR>

				

				<TR id="searchRow" style="display: none">	
						<TD align="center" >
							<div id="searchId">
								<b class="text pLeft15"> Search Criteria<font color="red">*</font></b>
							<div/>
						</TD>
						
						<TD>
							<div id="searchValue">	
								<html:text property="searchCriteria" styleId="searchCriteria" name="AccountDetailReportBean"></html:text>
							</div>
						</TD>
				</TR>
				
				<tr>
					<td colspan="2" align="center">
					<logic:notEqual value="13" property="accountLevel" name="AccountDetailReportBean">
						<html:button property="excelBtnAcc" value="Account Details Report" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					</td>
					<td colspan="2" align="center">
						<html:button property="excelBtnUpd" value="Account update Report" onclick="return exportToExcel('updateReport');"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	 <!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
			</table>
		</td>
		</TR>
		</html:form>
		
	
	
</TABLE>
<input type="hidden" name="reportDownloadStatus" value=""> <!-- Neetika BFR 16 14Aug -->
<script type="text/javascript">
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	var url= "accountDetailReport.do?methodName=getReportDownloadStatus";
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	//alert("get report .."+document.getElementById("reportDownloadStatus").value);
	if(document.getElementById("reportDownloadStatus").value != null && document.getElementById("reportDownloadStatus").value == 'inprogress') 
	{
	document.getElementById("excelBtnAcc").disabled=true;
	document.getElementById("excelBtnUpd").disabled=true;
    loadSubmit();
    }
    else
    {
    document.getElementById("excelBtnAcc").disabled=false;
    document.getElementById("excelBtnUpd").disabled=false;
    finishSubmit();
	}
	//alert("value:" + document.getElementById("reportDownloadStatus").value);
	}
	</SCRIPT>
</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>