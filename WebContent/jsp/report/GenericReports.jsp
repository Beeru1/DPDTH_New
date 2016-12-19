<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>


<%try{ %>

<%@page import="java.util.Map"%>
<%@ page import="java.util.*"%>
<%@page import="com.ibm.dp.common.Constants"%>
<%@page import="com.ibm.dp.reports.common.*"%>
<%@page import="com.ibm.virtualization.recharge.dto.UserSessionContext"%>

<html>
<head>

<link rel="stylesheet" href="../../theme/Master.css" type="text/css">


<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript"></SCRIPT>

<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>

<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/DropDownAjax.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/Ajax.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/validation.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/GenericReports.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/progressBar.js"
	type="text/javascript">
</SCRIPT>

<script>

 
function exportToExcel(accountLevel) {
// alert(" genericreports.jsp  exportToExcel :"+accountLevel);

	var groupId = document.getElementById("groupId").value;
	var reportId = document.getElementById("reportId").value;
	
	var skipValidation = false;
	

		if(typeof(document.forms[0].dcNo)!="undefined") {
			var dcNo = document.getElementById("dcNo").value;
			if(dcNo != "") {
				skipValidation = true; 
			} 
		}

		if(typeof(document.forms[0].searchOption)!="undefined") {
			var selectedIndex = document.getElementById("searchOption").selectedIndex;
			if(selectedIndex != '0') {
				skipValidation = true; 
			} 
		}
		if(typeof(document.forms[0].transactionType)!="undefined" && !skipValidation )
		{ 
			if(document.getElementById("transactionType").disabled==false){
				if(document.getElementById("transactionType").value ==""){
					alert("Please Select atleast one Business Category");
					return false;
		   		}
		   		
		   		var selectedTransTypeValues="";
   		 
		   		 for (var i=0; i < document.forms[0].transactionType.length; i++)
			  	 {
			   		 if (document.forms[0].transactionType[i].selected)
			     	 {
			     		if(selectedTransTypeValues !=""){
							selectedTransTypeValues += ",";
			     		}
			     	var selectedValTrans = document.forms[0].transactionType[i].value.split(",");
			     	selectedTransTypeValues += selectedValTrans[0];
			     	 }
			   	}
		   		 	document.getElementById("hiddenBusinesscatSelectIds").value =selectedTransTypeValues;
		   	}
	  	}
		if(typeof(document.forms[0].circleId)!="undefined" && !skipValidation)
		{ 
			if(document.getElementById("circleId").disabled==false){
				if(document.getElementById("circleId").value ==""){
					alert("Please Select atleast one Circle");
					return false;
		   		}
	  		}
	  	}
	  	
		if(typeof(document.forms[0].tsmId)!="undefined" && !skipValidation)
		{
			if(document.getElementById("tsmId").disabled==false){
				if(document.getElementById("tsmId").value ==""){
					alert("Please Select atleast one TSM");
					return false;
		   		}
		   		if(accountLevel != 5) {
			  		if(document.forms[0].tsmId.length ==1){
						alert("No TSM exists under the Cirlce");
						return false;
			   		}  
			   	}
			}
		}
		
		
		if(typeof(document.forms[0].distId)!="undefined" && !skipValidation) 
		{	
			if(document.getElementById("distId").disabled==false){
				if(document.getElementById("distId").value ==""){
					alert("Please Select atleast one Distributor");
					return false;
	   			}
	   			}
	   	}
		
		if(typeof(document.forms[0].distId)!="undefined" && document.forms[0].distId.length!=0) 
		{	
			if(document.getElementById("distId").disabled==false){			
		   		var selectedDistValues = "";
		   		if (document.forms[0].distId[0].selected){
			
				selectedDistValues=document.forms[0].distId[0].value;
				}	
			else{
				for (var i=0; i < document.forms[0].distId.length; i++)
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
   		 	document.getElementById("hiddenDistSelectIds").value =selectedDistValues;
	  	}
	  }
	 
	 if(typeof(document.forms[0].collectionName)!="undefined" && !skipValidation)
	 {
	 	 if(document.getElementById("collectionName").disabled==false){
			if(document.getElementById("collectionName").value ==""){
					alert("Please Select atleast one collection type");
					return false;
		   	}
		 }
	 }	   
	 if(typeof(document.forms[0].collectionName)!="undefined")
	 {
	 	 if(document.getElementById("collectionName").disabled==false){
		
		   		var selectedcollectionTypeValues = "";
		   		if (document.forms[0].collectionName[0].selected){
			
				for (var i=1; i < document.forms[0].collectionName.length; i++)
  	 		{
   		
     			if(selectedcollectionTypeValues !=""){
					selectedcollectionTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].collectionName[i].value.split(",");
     		selectedcollectionTypeValues += selectedVal[0];
   			}
				}	
			else{
				for (var i=0; i < document.forms[0].collectionName.length; i++)
		  	 	{
		   		 if (document.forms[0].collectionName[i].selected)
		     	 {
		     		if(selectedcollectionTypeValues !=""){
						selectedcollectionTypeValues += ",";
	     			}
		     	var selectedVal = document.forms[0].collectionName[i].value.split(",");
	     		selectedcollectionTypeValues += selectedVal[0];
		     	 }
		   		}
   			}
   		 	document.getElementById("hiddenCollectionTypeSelectIds").value =selectedcollectionTypeValues;
   		
   		}
   		}
   		
   		if(typeof(document.forms[0].transferType)!="undefined" && !skipValidation)
   		{
   			if(document.getElementById("transferType").value ==""){
				alert("Please Select atleast one Transfer Type");
				return false;
	   		}
	   	}
	   		
   		
   		if(typeof(document.forms[0].transferType)!="undefined")
   		{
	   		var selectedtrnsfrTypeValues = "";
	   		if (document.forms[0].transferType[0].selected){
		
			for (var i=1; i < document.forms[0].transferType.length; i++)
  	 		{
   		
     			if(selectedtrnsfrTypeValues !=""){
					selectedtrnsfrTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].transferType[i].value.split(",");
     		selectedtrnsfrTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].transferType.length; i++)
	  	 	{
	   		 if (document.forms[0].transferType[i].selected)
	     	 {
	     		if(selectedtrnsfrTypeValues !=""){
					selectedtrnsfrTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].transferType[i].value.split(",");
     		selectedtrnsfrTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenTrnsfrTypeSelec").value =selectedtrnsfrTypeValues;
   		 
   		 }
   		 
   		if(typeof(document.forms[0].status)!="undefined" && !skipValidation )
   		{
   		   if(document.getElementById("status").disabled==false){

   		   if(reportId != 7 ) 
   		   {
		 		if(document.getElementById("status").value ==""){
					alert("Please Select atleast one status");
					return false;
		   		}
		   	}
		   }
		}
   		 
   		if(typeof(document.forms[0].status)!="undefined")
   		{
   		   if(document.getElementById("status").disabled==false){

	   			var selectedStatusValues = "";
		   		if (document.forms[0].status[0].selected){
			
				for (var i=1; i < document.forms[0].status.length; i++)
	  	 		{
	   		
	     			if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
	     		var selectedVal = document.forms[0].status[i].value.split(",");
	     		selectedStatusValues += selectedVal[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].status.length; i++)
		  	 	{
		   		 if (document.forms[0].status[i].selected)
		     	 {
		     		if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
		     	var selectedVal = document.forms[0].status[i].value.split(",");
	     		selectedStatusValues += selectedVal[0];
		     	 }
		   		}
   			}
   		 document.getElementById("hiddenStatusSelectIds").value =selectedStatusValues;
	  	}}
	  	
		  if(typeof(document.forms[0].productId)!="undefined" && !skipValidation)
			{ 
			   	if(document.getElementById("productId").disabled==false){
			 		 if(document.getElementById("productId").value ==""){
			 		 		alert("Please Select atleast one Product/STB Type");
							return false;
					}
				}
			}		
	 	
	  if(typeof(document.forms[0].productId)!="undefined")
	   { if(document.getElementById("productId").disabled==false){
			
	   		var selectedSTBTypeValues = "";
	   		if (document.forms[0].productId[0].selected){
		
			for (var i=1; i < document.forms[0].productId.length; i++)
  	 		{
   		
     			if(selectedSTBTypeValues !=""){
					selectedSTBTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedSTBTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].productId.length; i++)
	  	 	{
	   		 if (document.forms[0].productId[i].selected)
	     	 {
	     		if(selectedSTBTypeValues !=""){
					selectedSTBTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedSTBTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenProductTypeSelectIds").value =selectedSTBTypeValues;
	  }}	
	  	
	  	
	  	
		
		if(typeof(document.forms[0].recoPeriod)!="undefined" )
   		{
   		   if(document.getElementById("recoPeriod").disabled==false){
				if(document.getElementById("recoPeriod").value ==""){
					alert("Please Select reco Period");
					return false;
		   		}
		   }
		}
	  	
	  if(typeof(document.forms[0].accountType)!="undefined" && !skipValidation)
		{ if(document.getElementById("accountType").disabled==false){
				if(document.getElementById("accountType").value ==""){
				alert("Please Select atleast one Account Type");
				return false;
	   		}
	   		}
	   	}
	  	if(typeof(document.forms[0].accountType)!="undefined" )
		{ if(document.getElementById("accountType").disabled==false){

	   		var selectedAccountTypeValues = "";
	   		if (document.forms[0].accountType[0].selected){
		
			for (var i=1; i < document.forms[0].accountType.length; i++)
  	 		{
   		
     			if(selectedAccountTypeValues !=""){
					selectedAccountTypeValues += ",";
     			}
     		var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
     		selectedAccountTypeValues += selectedValAccountType[0];
   			}
			}	
		else{
			for (var i=0; i < document.forms[0].accountType.length; i++)
	  	 {
	   		 if (document.forms[0].accountType[i].selected)
	     	 {
	     		if(selectedAccountTypeValues !=""){
					selectedAccountTypeValues += ",";
     			}
	     	var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
     		selectedAccountTypeValues += selectedValAccountType[0];
	     	 }
	   	}
   		}
   		 document.getElementById("hiddenAccountTypeSelectIds").value =selectedAccountTypeValues;
	  	}
	  	}
	  	
	  		if(typeof(document.forms[0].accountName)!="undefined" && !skipValidation) 
	  		{ 
	  			if(document.getElementById("accountName").disabled==false){
				if(document.getElementById("accountName").value ==""){
				alert("Please Select atleast one Account Name");
				return false;
	   			}
	   			}
	   		}	
	  	
	  		if(typeof(document.forms[0].accountName)!="undefined") 
	  		{ 
	  			if(document.getElementById("accountName").disabled==false){

		   		var selectedAccountNameValues = "";
		   		if (document.forms[0].accountName[0].selected){
			
				for (var i=1; i < document.forms[0].accountName.length; i++)
	  	 		{
	   		
	     			if(selectedAccountNameValues !=""){
						selectedAccountNameValues += ",";
	     			}
	     		var selectedValAccountName = document.forms[0].accountName[i].value.split(",");
	     		selectedAccountNameValues += selectedValAccountName[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].accountName.length; i++)
		  	 {
		   		 if (document.forms[0].accountName[i].selected)
		     	 {
		     		if(selectedAccountNameValues !=""){
						selectedAccountNameValues += ",";
	     			}
		     	var selectedValAccountName = document.forms[0].accountName[i].value.split(",");
	     		selectedAccountNameValues += selectedValAccountName[0];
		     	 }
		   	}
	   		}
   		 document.getElementById("hiddenAccountNameSelectIds").value =selectedAccountNameValues;
	  	}
	  	}
	  	
	  		if(typeof(document.forms[0].poStatus)!="undefined" && !skipValidation)
	  		{	
	  			if(document.getElementById("poStatus").disabled==false){
					if(document.getElementById("poStatus").value ==""){
					alert("Please Select atleast one PO Status");
					return false;
		   		}
		   		}
		   	}	
	  		if(typeof(document.forms[0].poStatus)!="undefined" )
	  		{	
	  			if(document.getElementById("poStatus").disabled==false){

		   		var selectedPOStatusValues = "";
		   	if (document.forms[0].poStatus[0].selected){
			
				for (var i=1; i < document.forms[0].poStatus.length; i++)
	  	 		{
	   		
	     			if(selectedPOStatusValues !=""){
						selectedPOStatusValues += ",";
	     			}
	     		var selectedValPOStatus = document.forms[0].poStatus[i].value.split(",");
	     		selectedPOStatusValues += selectedValPOStatus[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].poStatus.length; i++)
		  	 {
		   		 if (document.forms[0].poStatus[i].selected)
		     	 {
		     		if(selectedPOStatusValues !=""){
						selectedPOStatusValues += ",";
	     			}
		     	var selectedValPOStatus = document.forms[0].poStatus[i].value.split(",");
	     		selectedPOStatusValues += selectedValPOStatus[0];
		     	 }
		   	}
	   		}
	   		 document.getElementById("hiddenPOStatusSelectIds").value =selectedPOStatusValues;
	  	}
	  	}
	  	 
	  		if(typeof(document.forms[0].pendingAt)!="undefined" && !skipValidation) 
	  		 { 
	  		 if(document.getElementById("pendingAt").disabled==false){
				if(document.getElementById("pendingAt").value ==""){
				alert("Please Select atleast one Pending At");
				return false;
	   			}
	   			}
	   		}	
	  	
	  		if(typeof(document.forms[0].pendingAt)!="undefined") 
	  		 { 
	  		 if(document.getElementById("pendingAt").disabled==false){

		   		var selectedPendingAtValues = "";
		   		if (document.forms[0].pendingAt[0].selected){
			
				for (var i=1; i < document.forms[0].pendingAt.length; i++)
	  	 		{
	   		
	     			if(selectedPendingAtValues !=""){
						selectedPendingAtValues += ",";
	     			}
	     		var selectedValPendingAt = document.forms[0].pendingAt[i].value.split(",");
	     		selectedPendingAtValues += selectedValPendingAt[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].pendingAt.length; i++)
		  	 {
		   		 if (document.forms[0].pendingAt[i].selected)
		     	 {
		     		if(selectedPendingAtValues !=""){
						selectedPendingAtValues += ",";
	     			}
		     	var selectedValPendingAt = document.forms[0].pendingAt[i].value.split(",");
	     		selectedPendingAtValues += selectedValPendingAt[0];
		     	 }
		   	}
	   		}
   		 document.getElementById("hiddenPendingAtSelectIds").value =selectedPendingAtValues;
	  	}
	  	}
	  	
	  		if(typeof(document.forms[0].dcStatus)!="undefined" && !skipValidation) 
	  		{	if(document.getElementById("dcStatus").disabled==false)
	  			{
					if(document.getElementById("dcStatus").value ==""){
					alert("Please Select atleast one DC Status");
					return false;
		   			}
		   		}
		   	}		
	  		if(typeof(document.forms[0].dcStatus)!="undefined" ) 
	  		{	if(document.getElementById("dcStatus").disabled==false)
	  			{
		   		var selectedDCStatusValues = "";
		   		if (document.forms[0].dcStatus[0].selected){
			
				for (var i=1; i < document.forms[0].dcStatus.length; i++)
	  	 		{
	   		
	     			if(selectedDCStatusValues !=""){
						selectedDCStatusValues += ",";
	     			}
	     		var selectedValDCStatus = document.forms[0].dcStatus[i].value.split(",");
	     		selectedDCStatusValues += selectedValDCStatus[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].dcStatus.length; i++)
		  	 {
		   		 if (document.forms[0].dcStatus[i].selected)
		     	 {
		     		if(selectedDCStatusValues !=""){
						selectedDCStatusValues += ",";
	     			}
		     	var selectedValDCStatus = document.forms[0].dcStatus[i].value.split(",");
	     		selectedDCStatusValues += selectedValDCStatus[0];
		     	 }
		   	}
   			}
   		 	document.getElementById("hiddenDCStatusSelectIds").value =selectedDCStatusValues;
	  	}}
	  	
	  	if(typeof(document.forms[0].searchOption)!="undefined"){
			var searchOption =document.getElementById("searchOption").value;
			if(searchOption != "-1"){
				var searchCriteria = document.getElementById("searchCriteria").value;
					if ((searchCriteria==null)||(searchCriteria=="0") || searchCriteria==""){
					alert('Search Criteria is Required');
					return false;
				}	
			}	
		}
			
	  if(typeof(document.forms[0].stbStatus)!="undefined" && !skipValidation)
	   { 
   		 	if(document.getElementById("stbStatus").value =="") {
				alert("Please Select atleast one STB Status");
				return false;
	   		}
	   	}			
	  if(typeof(document.forms[0].stbStatus)!="undefined")
	   { 

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
   		 
   	
   	}
   	
   	
   	if(typeof(document.forms[0].stockType)!="undefined")
	   { 
   		 	if(document.getElementById("stockType").value =="") {
				alert("Please Select atleast one Stock Type");
				return false;
	   		}
	   	}			
	  if(typeof(document.forms[0].stockType)!="undefined")
	   { 

	   		var selectedStatusValues = "";
	   		if (document.forms[0].stockType[0].selected){
		
				for (var i=1; i < document.forms[0].stockType.length; i++)
	  	 		{
	   		
	     			if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
		     		var selectedStatusValuesArr = document.forms[0].stockType[i].value.split(",");
		     		selectedStatusValues += selectedStatusValuesArr[0];
	   			}
			}	
		else{
			for (var i=1; i < document.forms[0].stockType.length; i++)
	  	 	{
	   		 if (document.forms[0].stockType[i].selected)
	     	 {
	     		if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
	     	var selectedStatusValuesArr = document.forms[0].stockType[i].value.split(",");
     		selectedStatusValues += selectedStatusValuesArr[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenStockTypeSelectIds").value =selectedStatusValues;
   		 
   	
   	}
   	
   	//aman
		  if(typeof(document.forms[0].activity)!="undefined" && !skipValidation) 
	  		{
	  		if(document.getElementById("activity").disabled==false)
	  			{
					if(document.getElementById("activity").value ==-1){
					alert("Please Select atleast one activity ");
					return false;
		   			}
		   		}
	  		
	  		var fromDate = document.getElementById("fromDate").value;
	  		var toDate = document.getElementById("toDate").value;
	  		//alert("toDate 1111111:"+toDate);
	  		
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
	  		if(daysBetween(startDate,endDate) >= 31)
			{
	  			alert('Please select dates having only max 31 days difference');
			  return false;
			}
			}	
	  //
 	 if(typeof(document.forms[0].dateOption)!="undefined") 
  		{	
			var dateOption =document.getElementById("dateOption").value;
			if(dateOption =="-1" && (document.getElementById("reportId").value == 45 ||document.getElementById("reportId").value == 46 ) )
			{
				alert("Please select any date option");
				return false;
			}
			
			
			
			if(dateOption !="-1"){
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
			
	var dateValidation= document.getElementById("dateValidation").value;
			var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	    	var currentDate=null;
	    	if(dateValidation=="-1")
	    	{
	    	var dt=new Date();
	    		 currentDate = new Date((dt.getMonth()+1)+"/"+(dt.getDate()-1)+"/"+dt.getYear())
	    	
	    	}else
	    	 currentDate = new Date();
	    	
	    	
	    	if(startDate > endDate)
	    	{
	    		 alert('From date can not be greater than To Date');
	  		  	 return false;
	    	}
	    	if(endDate > currentDate)
	    	{
	    	if(dateValidation=="-1")
	    	{
	    	alert('To date should be smaller than current Date');
	  		  	 return false;
	    	}else{
	    		 alert('To date can not be greater than todays Date');
	  		  	 return false;
	    	}
	    	}
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	    	if(daysBetween(startDate,endDate) >= 45)
			{
			  alert('Please select dates having only max 45 days difference');
			  return false;
			}
	   }
	   }
	   else{
	   if(typeof(document.forms[0].fromDate)!="undefined" && typeof(document.forms[0].toDate)!="undefined" && !skipValidation ) 
	  		{
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
			
			var dateValidation= document.getElementById("dateValidation").value;
			var currentDate=null;
			var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	    	if(dateValidation=="-1")
	    	{
	    		var dt=new Date();
	    		currentDate = new Date((dt.getMonth()+1)+"/"+(dt.getDate()-1)+"/"+dt.getYear())
	    	
	    	} else {
	    	 	currentDate = new Date();
	    	}
	    	
	    	if(startDate > endDate)
	    	{
	    		 alert('From date can not be greater than To Date');
	  		  	 return false;
	    	}
	    	if(endDate > currentDate) {
	    		if(dateValidation=="-1") {
	    			alert('To date can not be greater than Yesterday Date');
	  		  	 	return false;
	    		}else{
	    		 	alert('To date can not be greater than todays Date');
	  		  	 	return false;
	    		}
	    	}
	    	
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	    	if(document.getElementById("reportId").value!=49)
			    	{
			    	if(daysBetween(startDate,endDate) >= 45)
						{
						  alert('Please select dates having only max 45 days difference');
						  return false;
						}
					}
			else
					{	
						if(daysBetween(startDate,endDate) >= 365)
							{
								alert('Please select dates having only max 365 days difference');
						  		return false;
								
							}
					}	
	   	}
	   }
	   	if(typeof(document.forms[0].date)!="undefined" && !skipValidation)
		{ 
			if(document.getElementById("date").disabled==false){
				if(document.getElementById("date").value ==""){
					alert("Please Select Date");
					return false;
		   		}
	  		}
	  	}
		setInterval(getReportDownloadStatus,1000);
		var url = "GenericReportsAction.do?methodName=exportToExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
	
		document.forms[0].submit();

	}

function printtPage(form)
{				
		var recoID = document.getElementById("recoPeriod").value;
		var circleid = document.getElementById("circleId").value;
		var selectedProductId = document.getElementById("productId").value;	
		populateRetailerIdArray(form);
		var flag=false;
		
		if(circleid == -1 || circleid == "")
		{
			 alert('Please select any circle.');
			 return false;		
		}
		if(circleid == "0")
		{
			alert("Please select circle other than Pan India ");
			return false;		
		}
		
		  var selObj = document.getElementById("circleid");
		  var count = 0;
		  for (i=0; i<selObj.options.length; i++) {
		    if (selObj.options[i].selected) {
		      count++;
		    }
  		}
		if(count > 1 )
		{
			alert("Please select only one circle");
			return false;
		}
		if(selectedProductId=="")
		{
			// alert('Please select any product.');
			// return false;		
			
			  var selObj1 = document.getElementById("productId");
			  for (i=0; i<selObj1.options.length; i++) {
			    selObj1.options[i].selected = true;
			}
		}
		
	  var selObj1 = document.getElementById("productId");
	  for (i=0; i<selObj1.options.length; i++) {
	    if (selObj1.options[i].selected) {
	      if(selObj1.options[i].value == "-1")
	      {
	      	flag=true;
	      }
	    }
 	}
  	if(!flag)	
  	{
  		alert("Please select All product");
  		return false;
  	}
  	
		if(recoID == "")
		{
			 alert('Please select any Reco Period.');
			 return false;		
		}		
		
	var url="recoDetailReport.do?methodName=printRecoDetail&recoID="+recoID+"&circleid="+circleid;
	window.open(url,"SerialNo","width=750,height=650,scrollbars=yes,toolbar=no");
  
}
function populateRetailerIdArray(form) { 
	 var result = ""; 
    var jsProductIdArr = new Array();
    for (var i = 0; i < form.productId.length; i++) { 
        if (form.productId.options[i].selected) { 
        	if(form.productId.options[i].value==0) {
        		for (var j = 1; j < form.productId.length; j++) {
        			jsProductIdArr.push(form.productId.options[j].value);
        		}
        	} 
        	else {
        		jsProductIdArr.push(form.productId.options[i].value);
        	}
        } 
    } 
    document.forms[0].productIdArray.value=jsProductIdArr;
   
}
</script>

</HEAD>
<bean:define name="GenericReportsFormBean" id="form"
	type="com.ibm.reports.beans.GenericReportsFormBean" />

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="enableDate()">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<html:form action="/GenericReportsAction" method="post">

		<html:hidden property="reportId" name="GenericReportsFormBean" />
		<html:hidden property="hiddendistIds" name="GenericReportsFormBean" />
		<html:hidden property="distData" name="GenericReportsFormBean" />
		<html:hidden property="accountLevel" name="GenericReportsFormBean" />
		<html:hidden property="otherUserData" name="GenericReportsFormBean" />
		<html:hidden property="dateValidation" name="GenericReportsFormBean" />
		<html:hidden property="msgValidation" name="GenericReportsFormBean" />
		<html:hidden property="groupId" />
		<html:hidden property="hiddenStbStatus" name="GenericReportsFormBean" />
		<html:hidden property="hiddenStockTypeSelectIds"
			name="GenericReportsFormBean" />

		<html:hidden property="methodName"
			value="getStockDetailReportDataExcel" />
		<html:hidden property="productIdArray" />
		<html:hidden property="groupId" />
		
		<html:hidden property="hiddenCircleSelecIds" name="GenericReportsFormBean" />
		<html:hidden property="hiddenZoneSelectIds" name="GenericReportsFormBean" />
		<html:hidden property="strMessage" />


	
		<td>
		<TABLE width="850" border="0" cellpadding="5" cellspacing="0"
			class="text" height="90%" align="top">


			<TR height="20%">
				<TD colspan="3" valign="bottom" class="dpReportTitle"><%=form.getReportLabel() %>
				</TD>
				

				<TD colspan="4">
				<p style="visibility: hidden;" id="progress"><img
					id="progress_image"
					style="visibility: hidden; padding-left: 55px; padding-top: 500px; width: 30; height: 34"
					src="${pageContext.request.contextPath}/images/ajax-loader.gif" />
				</p>
				</TD>



			</TR>
			<tr>
				<TD colspan="4" id="displaySchTitle" style="display: none"
					align="left"><FONT size="-1"> <b> This report only
				reflects transactions which have happened till <%= form.getLastSchedulerDate()%></b></FONT>
				</TD>
			</tr>
			<TR>
				<TD colspan="4" align="center"><FONT color="#ff0000" size="-2"><html:errors
					bundle="error" property="errors" /></FONT></TD>
			</TR>
			<TR>
				<%
			int i=1;
			List idList= (List) request.getAttribute("idList");
			Iterator idData= idList.iterator();
			
	 		while(idData.hasNext()){
	 		i++;
			String field=(String) idData.next();
			
			 %>
				<%
			if(i%2==0){
				
			%>
			
			<TR>
				<% 
			}   
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_BUSINESS_CATEGORY)){
			 %>
				<TD align="left" width="100"><b class="text pLeft15">Business
				Category<span id="diststar"><font color="red">*</font></span></b></TD>
				<TD width="250"><html:select multiple="true"
					disabled="<%=form.isEnablebusinessCategory()%>"
					property="transactionType" styleId="transactionType"
					onchange="resetCircle();getProductList();"
					style="width:250px; height:80px;" size="6">
					<logic:notEmpty name="GenericReportsFormBean"
						property="businessCategoryList">
						<html:optionsCollection name="GenericReportsFormBean"
							property="businessCategoryList" label="strText" value="strValue" />
					</logic:notEmpty>
				</html:select></TD>


				<html:hidden property="hiddenBusinesscatSelectIds"
					name="GenericReportsFormBean" />


				<%}
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_ZONE)){
			 %>
				<logic:equal value="true" property="zoneRequired"
					name="GenericReportsFormBean">

					<TD align="left" width="100"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.zonesList" /><span id="cirstar"><font
						color="red">*</font></span></b></TD>
					<TD width="250"><logic:notEqual value="5"
						property="accountLevel" name="GenericReportsFormBean">

						<logic:notEqual value="41" property="reportId"
							name="GenericReportsFormBean">

							<html:select multiple="true" property="zoneId"
								disabled="<%=form.isEnableZone()%>" styleId="zoneId"
								style="width:250px; height:80px;"
								onchange="selectAllzones();getAllcirclesZone();" size="6">
								<logic:notEmpty name="GenericReportsFormBean"
									property="zonesList">
									<html:optionsCollection name="GenericReportsFormBean"
										property="zonesList" label="strText" value="strValue" />
								</logic:notEmpty>
							</html:select>

						</logic:notEqual>

					</logic:notEqual></TD>


				</logic:equal>
				<%} 
				if(field.equalsIgnoreCase(ReportConstants.CRITERIA_PT)){
			 %>
				<TD align="left" width="100"><b class="text pLeft15">Product
				Type<span id="recostar"><font color="red">*</font></span></b></td>

				<td width="250"><html:select disabled="<%=form.isEnablePT()%>"
					property="ptID" styleId="recoStatus"
					style="width:250px; height:80px;">
					<logic:notEmpty name="GenericReportsFormBean" property="ptList">
						<html:optionsCollection name="GenericReportsFormBean"
							property="ptList" label="value" value="id" />
					</logic:notEmpty>
				</html:select></TD>



				<%} 
				
				if(field.equalsIgnoreCase(ReportConstants.CRITERIA_DT)){
			 %>
				<TD align="left" width="100"><b class="text pLeft15">SSD or
				SF Type<span id="recostar"><font color="red">*</font></span></b></td>

				<td width="250"><html:select disabled="<%=form.isEnableDT()%>"
					property="dtID" styleId="recoStatus"
					style="width:250px; height:80px;">
					<logic:notEmpty name="GenericReportsFormBean" property="dtList">
						<html:optionsCollection name="GenericReportsFormBean"
							property="dtList" label="value" value="id" />
					</logic:notEmpty>
				</html:select></TD>



				<%} %>



				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_CIRCLE)){
			 %>
				<logic:equal value="true" property="circleRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenCircleSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><span id="cirstar"><font
						color="red">*</font></span></b></TD>
					<TD width="250"><logic:equal value="5" property="accountLevel"
						name="GenericReportsFormBean">
						<html:select multiple="true" property="circleId"
							disabled="<%=form.isEnableCircle()%>" styleId="circleId"
							style="width:250px; height:80px;"
							onchange="resetTSM();getTsmName();" size="6">
							<logic:notEmpty name="GenericReportsFormBean"
								property="circleList">
								<html:optionsCollection name="GenericReportsFormBean"
									property="circleList" label="strText" value="strValue" />
							</logic:notEmpty>
						</html:select>
					</logic:equal> <logic:notEqual value="5" property="accountLevel"
						name="GenericReportsFormBean">
						<!--  condition for report id 41 (Revers Error STB Report) is added by pratap -->
						<logic:notEqual value="41" property="reportId"
							name="GenericReportsFormBean">
							<html:select multiple="true" property="circleId"
								disabled="<%=form.isEnableCircle()%>" styleId="circleId"
								style="width:250px; height:80px;"
								onchange="selectAllCircles();getTsmName();" size="6">
								<logic:notEmpty name="GenericReportsFormBean"
									property="circleList">
									<html:optionsCollection name="GenericReportsFormBean"
										property="circleList" label="strText" value="strValue" />
								</logic:notEmpty>
							</html:select>
						</logic:notEqual>
						<logic:equal value="41" property="reportId"
							name="GenericReportsFormBean">
							<html:select multiple="true" property="circleId"
								disabled="<%=form.isEnableCircle()%>" styleId="circleId"
								style="width:250px; height:80px;" onchange="selectAllCircles();"
								size="6">
								<!-- removed getTsmName() from here by pratap -->
								<logic:notEmpty name="GenericReportsFormBean"
									property="circleList">
									<html:optionsCollection name="GenericReportsFormBean"
										property="circleList" label="strText" value="strValue" />
								</logic:notEmpty>
							</html:select>
						</logic:equal>
						<!-- end changing of pratap -->
					</logic:notEqual></TD>


				</logic:equal>
				<%} %>
				<%    
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_TSM)){
			 %>

				<logic:equal value="true" property="tsmRequired"
					name="GenericReportsFormBean">
					<TD align="left" width="100"><b class="text pLeft15"> TSM<span
						id="tsmstar"><font color="red">*</font></span></b></TD>
					<TD width="250"><logic:equal value="5" property="accountLevel"
						name="GenericReportsFormBean">

						<html:select multiple="true" disabled="<%=form.isEnableTSM()%>"
							property="tsmId" styleId="tsmId"
							style="width:250px; height:80px;" size="6"
							onchange="getDistName(5);">
							<logic:notEmpty name="GenericReportsFormBean" property="tsmList">
								<html:optionsCollection name="GenericReportsFormBean"
									property="tsmList" label="strText" value="strValue" />
							</logic:notEmpty>
						</html:select>

					</logic:equal> <logic:notEqual value="5" property="accountLevel"
						name="GenericReportsFormBean">
						<html:select multiple="true" disabled="<%=form.isEnableTSM()%>"
							property="tsmId" styleId="tsmId"
							style="width:250px; height:80px;" size="6"
							onchange="getDistName(0);selectAllTSM();">
							<logic:notEmpty name="GenericReportsFormBean" property="tsmList">
								<html:optionsCollection name="GenericReportsFormBean"
									property="tsmList" label="strText" value="strValue" />
							</logic:notEmpty>
						</html:select>
					</logic:notEqual></TD>

					<html:hidden property="hiddenTsmSelectIds"
						name="GenericReportsFormBean" />

				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_DISTRIBUTOR)){
			 %>

				<logic:equal value="true" property="distributorRequired"
					name="GenericReportsFormBean">
					<TD align="left" width="100"><b class="text pLeft15">
					Distributor<span id="diststar"><font color="red">*</font></span></b></TD>
					<TD width="250"><html:select multiple="true"
						disabled="<%=form.isEnableDistributor()%>" property="distId"
						styleId="distId" style="width:250px; height:80px;" size="6"
						onchange="selectAllDist();">
						<logic:notEmpty name="GenericReportsFormBean" property="distList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="distList" label="strText" value="strValue" />
						</logic:notEmpty>
					</html:select></TD>


					<html:hidden property="hiddenDistSelectIds"
						name="GenericReportsFormBean" />

				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_STB_TYPE)){
			 %>

				<logic:equal value="true" property="stbTypeRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenSTBTypeSelectIds"
						name="GenericReportsFormBean" />
					<html:hidden property="hiddenProductTypeSelectIds"
						name="GenericReportsFormBean" />

					<TD align="left" width="100"><b class="text pLeft15">STB
					Type<span id="stbstar"><font color="red">*</font></span></b></TD>
					<TD width="250"><html:select property="productId"
						disabled="<%=form.isEnableSTBType()%>"
						style="width:250px; height:80px;" styleId="productId"
						name="GenericReportsFormBean" onchange="selectAllProducts()"
						multiple="true">
						<html:option value="-1">--All--</html:option>
						<logic:notEmpty property="productList"
							name="GenericReportsFormBean">
							<html:optionsCollection name="GenericReportsFormBean"
								property="productList" label="productCategory"
								value="productCategoryId" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_PRODUCT_TYPE)){
			 %>

				<logic:equal value="true" property="productTypeRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenSTBTypeSelectIds"
						name="GenericReportsFormBean" />
					<html:hidden property="hiddenProductTypeSelectIds"
						name="GenericReportsFormBean" />

					<TD align="left" height="46" width="100"><b
						class="text pLeft15">Product Type<span id="prodstar"><font
						color="red">*</font></span></b></TD>
					<TD width="250"><html:select property="productId"
						disabled="<%=form.isEnableProductType()%>"
						style="width:250px; height:80px;" styleId="productId"
						name="GenericReportsFormBean" onchange="selectAllProducts()"
						multiple="true">
						<html:option value="-1">--All--</html:option>
						<logic:notEmpty property="productList"
							name="GenericReportsFormBean">
							<html:optionsCollection name="GenericReportsFormBean"
								property="productList" label="productCategory"
								value="productCategoryId" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_COLLECTION_TYPE)){
			 %>
				<logic:equal value="true" property="collectionTypeRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenCollectionTypeSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15">Collection
					Type<span id="colstar"><font color="red">*</font></span></b></TD>
					<TD width="250"><html:select property="collectionName"
						disabled="<%=form.isEnableCollectionType()%>"
						style="width:250px; height:80px;" styleId="collectionName"
						name="GenericReportsFormBean"
						onchange="selectAllCollectionTypes()" multiple="true">
						<logic:notEmpty property="collectionType"
							name="GenericReportsFormBean">
							<html:option value="-1">--All--</html:option>
							<html:optionsCollection name="GenericReportsFormBean"
								property="collectionType" label="collectionName"
								value="collectionId" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_DCSTATUS)){
			 %>


				<logic:equal value="true" property="dcStatusRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenDCStatusSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15">DC
					Status<span id="dcstar"><font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnableDCStatus()%>" property="dcStatus"
						styleId="dcStatus" style="width:250px; height:80px;" size="6"
						onchange="selectAllDCStatus()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="dcStatusList">
							<html:option value="-1">--All--</html:option>
							<!--  html:optionsCollection name="GenericReportsFormBean"	property="dcStatusList" label="value" value="id"/ -->
							<logic:iterate name="GenericReportsFormBean" id="criteriaList"
								property="dcStatusList">
								<option value='<bean:write name="criteriaList" property="id"/>'
									title='<bean:write name="criteriaList" property="value"/>'>
								<bean:write name="criteriaList" property="value" /></option>
							</logic:iterate>

						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_DATEOPTION)){
			 %>



				<logic:equal value="true" property="dateOptionRequired"
					name="GenericReportsFormBean">

					<TD align="left" width="100"><b class="text pLeft15">Date
					Option</b><STRONG><span id="req"><FONT color="RED">*</FONT><span></STRONG>
					</td>
					<td width="250"><html:select
						disabled="<%=form.isEnableDateOption()%>"
						onchange="javascript:enableDate()" property="dateOption"
						styleId="dateOption" style="width:250px; height:80px;">
						<logic:notEmpty name="GenericReportsFormBean"
							property="dateOptionList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="dateOptionList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>

				<!-- added by aman for distributor activity -->

				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_ACTIVITY)){
			 %>

				<logic:equal value="true" property="activityRequired"
					name="GenericReportsFormBean">

					<TD align="left" width="100"><b class="text pLeft15">Activity</b>
					</td>
					<td width="250"><html:select
						disabled="<%=form.isEnableActivity()%>" property="activity"
						styleId="enableActivity" style="width:250px; height:80px;">
						<logic:notEmpty name="GenericReportsFormBean"
							property="activityList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="activityList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<!-- end of changes by aman -->


				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_FROM_DATE)){
			 %>

				<logic:equal value="true" property="fromDateRequired"
					name="GenericReportsFormBean">

					<TD id="fromDateId" align="left" width="100"><b
						class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><span id="req"><FONT
						color="RED">*</FONT><span></STRONG></b></TD>

					<TD width="277x" align="left"><FONT color="#003399"> <html:text
						disabled="<%=form.isEnableFromDate()%>" property="fromDate"
						styleClass="box" styleId="fromDate" readonly="true" size="38"
						maxlength="15" name="GenericReportsFormBean" /> <script
						language="JavaScript">
						new tcal ({
							// form name
							'formname': 'GenericReportsFormBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script> </FONT></TD>

				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_TO_DATE)){
			 %>
				<logic:equal value="true" property="toDateRequired"
					name="GenericReportsFormBean">

					<TD align="left"><b class="text pLeft15"><bean:message
						bundle="view" key="report.toDate" /><STRONG><span
						id="req1"><FONT color="RED">*</FONT></span></STRONG></b></TD>
					<TD width="277px" align="left"><FONT color="#003399"> <html:text
						disabled="<%=form.isEnableToDate()%>" property="toDate"
						styleClass="box" styleId="toDate" readonly="true" size="38"
						maxlength="15" name="GenericReportsFormBean" /> <script
						language="JavaScript">
						new tcal ({
							// form name
							'formname': 'GenericReportsFormBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script> </FONT></TD>

				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_STATUS)){
			 %>
				<logic:equal value="true" property="statusRequired"
					name="GenericReportsFormBean">

					<html:hidden property="hiddenStatusSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15"><span
						id="statstar"> Status<font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnableStatus()%>" property="status"
						styleId="status" style="width:250px; height:80px;" size="6"
						onchange="selectAllStatus()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="statusOptionList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="statusOptionList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>

				<%} %>
				<%  
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_ACCOUNT_TYPE)){
			 %>
				<logic:equal value="true" property="accountTypeRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenAccountTypeSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15">
					Account Type<span id="acctypestar"><font color="red">*</font></span></b>
					</td>
					<td width="250"><html:select
						disabled="<%=form.isEnableAccountType()%>" property="accountType"
						styleId="accountType" style="width:250px; height:80px;"
						onchange="getAccountNames()">

						<logic:notEmpty name="GenericReportsFormBean"
							property="accountTypeList">
							<html:option value="-1">--All--</html:option>
							<html:optionsCollection name="GenericReportsFormBean"
								property="accountTypeList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_ACCOUNT_NAME)){
			 %>
				<logic:equal value="true" property="accountNameRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenAccountNameSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15">
					Account Name<span id="accnamestar"><font color="red">*</font></span></b>
					</td>
					<td width="250"><html:select
						disabled="<%=form.isEnableAccountName()%>" property="accountName"
						styleId="accountName" style="width:250px; height:80px;"
						onchange="selectAllAccountName()">
						<html:option value="-1">--All--</html:option>
						<logic:notEmpty name="GenericReportsFormBean"
							property="accountNameList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="accountNameList" label="strText" value="strValue" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_SEARCH_OPTION)){
			 %>
				<logic:equal value="true" property="searchOptionRequired"
					name="GenericReportsFormBean">
					<TD align="left" width="100"><b class="text pLeft15">Search
					Option</b></td>
					<td width="250"><html:select
						disabled="<%=form.isEnableSearchOption()%>"
						onchange="javascript:enableDate();toggleMandatory();"
						property="searchOption" styleId="searchOption"
						style="width:250px; height:80px;">
						<logic:notEmpty name="GenericReportsFormBean"
							property="searchOptionList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="searchOptionList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_SEARCH_CRITERIA)){
			 %>
				<logic:equal value="true" property="searchCreteriaRequired"
					name="GenericReportsFormBean">
					<td align="left" width="100"><b class="text pLeft15">Search
					Criteria<STRONG><span id="reqsearch"><FONT
						color="RED">*</FONT><span></STRONG></b></td>
					<td width="277" align="left"><html:text size="38"
						property="searchCriteria" styleId="searchCriteria"
						styleClass="box" name="GenericReportsFormBean" /></td>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_PO_STATUS)){
			 %>
				<logic:equal value="true" property="poStatusRequired"
					name="GenericReportsFormBean">
					<TD allign="left" width="100"><b class="text pLeft15">PO
					Status<span id="postar"><font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnablePOStatus()%>" property="poStatus"
						styleId="poStatus" style="width:250px; height:80px;"
						onchange="selectAllPOStatus()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="poStatusList">
							<html:option value="-1">--All--</html:option>
							<!--  html:optionsCollection name="GenericReportsFormBean"	property="poStatusList" label="value" value="id"/ -->
							<logic:iterate name="GenericReportsFormBean" id="criteriaList"
								property="poStatusList">
								<option value='<bean:write name="criteriaList" property="id"/>'
									title='<bean:write name="criteriaList" property="value"/>'>
								<bean:write name="criteriaList" property="value" /></option>
							</logic:iterate>

						</logic:notEmpty>
					</html:select></TD>
					<html:hidden property="hiddenPOStatusSelectIds"
						name="GenericReportsFormBean" />

				</logic:equal>
				<%} %>
				<%
				if(field.equalsIgnoreCase(ReportConstants.CRITERIA_RECO_STATUS)){
			 %>
				<logic:equal value="true" property="recoStatusRequired"
					name="GenericReportsFormBean">
					<TD allign="left" width="100"><b class="text pLeft15">RECO
					Status<span id="recostar"><font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnablerecoStatus()%>" property="recoStatus"
						styleId="recoStatus" style="width:250px; height:80px;"
						onchange="selectAllRecoStatus()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="recoStatusList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="recoStatusList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
					<html:hidden property="hiddenRecoStatusSelectId"
						name="GenericReportsFormBean" />

				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_PENDING_AT)){
			 %>
				<logic:equal value="true" property="pendingAtRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenPendingAtSelectIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15">Pending
					At<span id="pendstar"><font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnablePendingAt()%>" property="pendingAt"
						styleId="pendingAt" style="width:250px; height:80px;" size="6"
						onchange="selectAllPendingAt()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="pendingAtList">
							<html:option value="-1">--All--</html:option>
							<html:optionsCollection name="GenericReportsFormBean"
								property="pendingAtList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_TRANSFER_TYPE)){
			 %>
				<logic:equal value="true" property="transferTypeRequired"
					name="GenericReportsFormBean">
					<html:hidden property="hiddenTrnsfrTypeSelec"
						name="GenericReportsFormBean" />

					<TD align="left" width="100"><b class="text pLeft15">Transfer
					Type<span id="transtar"><font color="red">*</font></span></b></td>
					<td width="250"><html:select
						disabled="<%=form.isEnableTransferType()%>"
						property="transferType" styleId="transferType"
						style="width:250px; height:80px;">
						<logic:notEmpty name="GenericReportsFormBean"
							property="transferTypeList">
							<html:option value="">- Select Transfer Type -</html:option>

							<html:optionsCollection name="GenericReportsFormBean"
								property="transferTypeList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_STB_STATUS)){
			 %>
				<logic:equal value="true" property="stbStatusRequired"
					name="GenericReportsFormBean">
					<TD align="left" width="100"><b class="text pLeft15">STB
					Status<span id="stbstatstar"><font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnableStbStatus()%>" property="stbStatus"
						styleId="stbStatus" style="width:250px; height:80px;"
						onchange="selectAllSTBStatus()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="stbStatusList">
							<html:option value="-1">--All--</html:option>
							<html:optionsCollection name="GenericReportsFormBean"
								property="stbStatusList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
					if(field.equalsIgnoreCase(ReportConstants.CRITERIA_STOCK_TYPE)){
			 %>
				<logic:equal value="true" property="stockTypeRequired"
					name="GenericReportsFormBean">
					<TD align="left" width="100"><b class="text pLeft15">Stock
					Type<span><font color="red">*</font></span></b></td>
					<td width="250"><html:select multiple="true"
						disabled="<%=form.isEnableStockType()%>" property="stockType"
						styleId="stockType" style="width:250px; height:80px;"
						onchange="selectAllStockType()">
						<logic:notEmpty name="GenericReportsFormBean"
							property="stockTypeList">
							<html:option value="0">--All--</html:option>
							<html:optionsCollection name="GenericReportsFormBean"
								property="stockTypeList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
				if(field.equalsIgnoreCase(ReportConstants.CRITERIA_FSE)){
			 %>
				<%
			 
					
			  %>
				<logic:equal value="true" property="fseRequired"
					name="GenericReportsFormBean">
					<TD align="left"><b class="text pLeft15">FSE<font
						color="red">*</font>:</b></td>
					<td><html:select multiple="true"
						disabled="<%=form.isEnableFSE()%>" property="fseId"
						styleId="fseId" style="width:250px; height:80px;"
						onchange="getRetName()">
						<logic:notEmpty name="GenericReportsFormBean" property="fseList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="fseList" label="strText" value="strValue" />
						</logic:notEmpty>
					</html:select></TD>
					<html:hidden property="hiddenFseSelectIds"
						name="GenericReportsFormBean" />
				</logic:equal>

				<%} %>
				<%
				if(field.equalsIgnoreCase(ReportConstants.CRITERIA_RETAILER)){
			 %>

				<logic:equal value="true" property="retailerRequired"
					name="GenericReportsFormBean">
					<TD align="left"><b class="text pLeft15"> Retailer<font
						color="red">*</font>:</b></td>
					<td><html:select multiple="true"
						disabled="<%=form.isEnableRetailer()%>" property="retId"
						styleId="retId" style="width:250px; height:80px;">
						<logic:notEmpty name="GenericReportsFormBean"
							property="retailerList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="retailerList" label="strText" value="strValue" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_DC_NO)){
			 %>
				<logic:equal value="true" property="dcNoRequired"
					name="GenericReportsFormBean">
					<td align="left" width="100"><b class="text pLeft15">DC No</b>
					</td>
					<td align="left" width="277"><html:text size="38"
						property="dcNo" styleId="dcNo" styleClass="box"
						name="GenericReportsFormBean"
						onchange="javascript:showHideMandatory();" /></td>
				</logic:equal>
				<%} %>
				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_DATE)){
			 %>
				<logic:equal value="true" property="dateRequired"
					name="GenericReportsFormBean">

					<TD align="left" width="100"><b class="text pLeft15">Date<STRONG><span
						id="req1"><FONT color="RED">*</FONT></span></STRONG></b></TD>
					<TD width="277px" align="left"><FONT color="#003399"> <html:text
						disabled="<%=form.isEnableDate()%>" property="date"
						styleClass="box" styleId="date" readonly="true" size="38"
						maxlength="15" name="GenericReportsFormBean" /> <script
						language="JavaScript">
						new tcal ({
							// form name
							'formname': 'GenericReportsFormBean',
							// input name
							'controlname': 'date'
						});
					
						</script> </FONT></TD>

				</logic:equal>
				<%} %>


				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_RECO)){
			 %>
				<logic:equal value="true" property="recoRequired"
					name="GenericReportsFormBean">

					<html:hidden property="hiddenRecoRequiredIds"
						name="GenericReportsFormBean" />
					<TD align="left" width="100"><b class="text pLeft15"><span
						id="statstar"> Reco period<font color="red">*</font></span></b></td>
					<td width="250"><html:select
						disabled="<%=form.isEnableStatus()%>" property="recoPeriod"
						styleId="recoPeriod" style="width:250px; height:80px;">
						<html:option value="">--Select Reco--</html:option>
						<logic:notEmpty name="GenericReportsFormBean"
							property="recoPeriodList">
							<html:optionsCollection name="GenericReportsFormBean"
								property="recoPeriodList" label="value" value="id" />
						</logic:notEmpty>
					</html:select></TD>
				</logic:equal>

				<%} %>



				<%
			if(field.equalsIgnoreCase(ReportConstants.CRITERIA_BLANK)){
			 %>
				<TD align="left">&nbsp;</TD>


				<%} %>
				<%
				if(i%2==0){
				continue;
				}else{
				%>
			</TR>
			<% }
				} %>
			<TR>
				<td></td>
				<td colspan="3" align="center">
					<logic:equal value="5" property="accountLevel" name="GenericReportsFormBean">
						<html:button property="excelBtn" value="Export To Excel"
							onclick="return exportToExcel(5) ;">
						</html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:equal> 
					
					<logic:notEqual value="5" property="accountLevel" name="GenericReportsFormBean">
						<html:button property="excelBtn" value="Export To Excel"
							onclick="return exportToExcel(0) ;">
						</html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual> 
					
					<logic:equal value="43" name="GenericReportsFormBean" property="reportId">
					
						<logic:equal value="1" name="GenericReportsFormBean" property="accountLevel">
							<input type="button" id="printPage" value="Print Certificate" 
								onclick="printtPage(this.form)" />
						</logic:equal>
					</logic:equal>
				</td>

				<html:hidden property="methodName" styleId="methodName"
					value="exporttoExcel" />
			</TR>

		</table>
		</td>
		<logic:equal value="5" property="accountLevel"
			name="GenericReportsFormBean">
			<script>
			//init();					
		  </script>
		</logic:equal>
		<logic:equal value="3" property="accountLevel"
			name="GenericReportsFormBean">
			<script>
			//initZBM();					
		  </script>
		</logic:equal>
		<logic:equal value="4" property="accountLevel"
			name="GenericReportsFormBean">
			<script>
			//initZBM();					
		  </script>
		</logic:equal>

	</html:form>

</TABLE>

<input type="hidden" name="reportDownloadStatus" value="">
<script>
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	//if(document.getElementById("reportId") == null)
	//return;
	var reportId = document.getElementById("reportId").value;
	if(reportId == null || reportId == '')
	return;
	
	var url= "GenericReportsAction.do?methodName=getReportDownloadStatus&reportId="+reportId;
	
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	
	if(document.getElementById("reportDownloadStatus").value != null && document.getElementById("reportDownloadStatus").value == 'inprogress') 
    {
    document.getElementById("excelBtn").disabled=true;
    loadSubmit();
    }
    else
    {
    document.getElementById("excelBtn").disabled=false;
    finishSubmit();
    }

	//alert("value:" + document.getElementById("reportDownloadStatus").value);
	}
//Neetika
function selectAllzones()
{	
	var ctrl = document.forms[0].zoneId;
	//var selCount = selectedCount(ctrl);
	//var ctrLength = ctrl.length;
	
	var selectedZoneValues = "-1";
	
	//alert("selectAllzones "+document.forms[0].zoneId[0].value);
	
	if (document.forms[0].zoneId[0].selected && document.forms[0].zoneId[0].value =="-1") 
	{
		for (var i=1; i < document.forms[0].zoneId.length; i++)
		{
			document.forms[0].zoneId[i].selected =true;
 		}
	}
	else
	{
		selectedZoneValues = "";
	 	for (i=1; i < document.forms[0].zoneId.length; i++)
	 	{
	 		if(document.forms[0].zoneId[i].selected)
	 		{
	 			selectedZoneValues += document.forms[0].zoneId[i].value + ",";
	 		}
	 	}
	 	selectedZoneValues = selectedZoneValues.slice(0, selectedZoneValues.length - 1);
	}
	
	document.getElementById("hiddenZoneSelectIds").value =selectedZoneValues;
}

//Neetika
function getAllcirclesZone() 
{   	
		var zone = document.forms[0].zoneId[0].value;
		var selectedCircleValues ="";
		selectedCircleValues =0;
		
	   //alert("getAllcirclesZone== "+zone);
		if(zone ==""){
			alert("Please Select atleast one Zone");
			return false;
	 	}
	// if(document.forms[0].circleId.length ==1){
	//		alert("No Circle exists");
	//		return false;
	//   }
	//else{

	 	if (document.forms[0].zoneId[0].selected && document.forms[0].zoneId[0].value=="-1"){
		
			for (var i=1; i < document.forms[0].zoneId.length; i++)
	 		{
 		
   			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
   			}
   		var selectedValCircle = document.forms[0].zoneId[i].value.split(",");
   		selectedCircleValues += selectedValCircle[0];
 			}
 			
		}	
		else{
		
		  for (var i=0; i < document.forms[0].zoneId.length; i++)
	  	 {
	   		 if(document.forms[0].zoneId[i].value != "-1") {	 
		   		 if (document.forms[0].zoneId[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
			     	var selectedValCircle = document.forms[0].zoneId[i].value.split(",");
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
	     	 }
	   	}
	   	//alert("getAllcirclesZone22 else"+selectedCircleValues);
 }
 	
 	
 		 
	 	getAllcirclesZone1(selectedCircleValues,'circleId');
//}
		
}
//Added by Neetika
function getAllcirclesZone1(selectedzoneValues,target)
{
	
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllCirclesAsperZone&cval="+selectedzoneValues;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);

}	


</script>
</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>