<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>HIerarchy Transfer Screen</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<link href="${pageContext.request.contextPath}/jsp/hbo/theme/text_new.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>


<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>
<script type="text/javascript">

function trnferCheck(){
	var chkedRdbVal;
  	for (var i=0; i < document.forms[0].rdbTransfer.length; i++){
		if (document.forms[0].rdbTransfer[i].checked){
			chkedRdbVal = document.forms[0].rdbTransfer[i].value;
      	}
   	}
 	 return chkedRdbVal;
}    
function validateData(){
	
	var trnfrRdbVal = trnferCheck();		// either 1 for FSE and 2 for Retailer
	var distVal =  document.getElementById("fromDistributorId").value;
	var distToVal = document.getElementById("toDistId").value;
	var  selectedFseValues=""; 
	var selectedRetailors ="";
	if(distVal == "-1"){
		alert("Please select from distributor");
		document.getElementById("fromDistributorId").focus();
		return false
	}
	if(distToVal == "-1"){
		alert("Please select to distributor");
		document.getElementById("toDistId").focus();
		return false
	}
	
	
	
	if(trnfrRdbVal == 1) {
	if(distVal == distToVal)
	{
		alert("From and To distributor cannot be same ");
		document.getElementById("fromDistributorId").focus();
		return false
	
	}
		if(document.getElementById("fromFSEIdsForFseTrans").value ==""){
			alert("Please Select atleast one FSE");
			return false;
	    }
	    if(document.forms[0].fromFSEIdsForFseTrans.length ==1){
			alert("No FSE exists under the distributor");
			return false;
	   }else{
		 	if (document.forms[0].fromFSEIdsForFseTrans[0].selected){
				for (var i=1; i < document.forms[0].fromFSEIdsForFseTrans.length; i++){
   				  	if(selectedFseValues !=""){
						selectedFseValues += ",";
     				}
     				var selectedValFse = document.forms[0].fromFSEIdsForFseTrans[i].value.split(",");
     				selectedFseValues += selectedValFse[0];
   				}
		 	}else{
				for (var i=1; i < document.forms[0].fromFSEIdsForFseTrans.length; i++){
					if (document.forms[0].fromFSEIdsForFseTrans[i].selected){
			     		if(selectedFseValues!=""){
							selectedFseValues += ",";
     					}
			     		var selectedValFse = document.forms[0].fromFSEIdsForFseTrans[i].value.split(",");
     					selectedFseValues += selectedValFse[0];
     				}
   				}
   			}
   		 		document.getElementById("hiddenFSESelecIds").value =selectedFseValues;
	   	}
	var chkedRdbVal;
	var checkFlag="";
	for (var i=0; i < document.forms[0].rdbWithAndWithoutRtlr.length; i++){
		if (document.forms[0].rdbWithAndWithoutRtlr[i].checked){
      		chkedRdbVal = document.forms[0].rdbWithAndWithoutRtlr[i].value;
      	}
   	}
	if(chkedRdbVal =="1"){
		var alertMsg= "";
		for (var i=1; i < document.forms[0].fromFSEIdsForFseTrans.length; i++){
			if (document.forms[0].fromFSEIdsForFseTrans[i].selected){
				var selectedValFse = document.forms[0].fromFSEIdsForFseTrans[i].value.split(",");
			    var w = document.forms[0].fromFSEIdsForFseTrans[i].selectedIndex;
				var selected_text = document.forms[0].fromFSEIdsForFseTrans[i].text;
				if(selectedValFse[2]!=0){
			    	if(alertMsg !=""){
						alertMsg = alertMsg+"\n";
			     	}
			     	alertMsg=alertMsg + selected_text +" have "+ selectedValFse[2]+" retailer which are pending to transfer ,Please Transfer these retailers first before transfering FSE.";
			     	//alert("The FSE has Retailer, Please first Transfer the reltailer.");
			     	document.forms[0].fromFSEIdsForFseTrans[i].selected =false;
			     	document.forms[0].fromFSEIdsForFseTrans[0].selected =false;
			     	checkFlag="false";
		     	}
			      
			 }
		}
		if(alertMsg !=""){
			alert(alertMsg);
			return false;
		}
	}   
	   
 }else if(trnfrRdbVal ==2){
	if( document.getElementById("fromFSEIdForRtlrTrans").value ==0){
		alert("Please Select From FSE");
		document.getElementById("fromFSEIdForRtlrTrans").focus();
		return false
	}
	if( document.getElementById("toFSEIdForRtlrTrans").value ==0){
		alert("Please Select to FSE");
		document.getElementById("toFSEIdForRtlrTrans").focus();
		return false
	}
	if(document.getElementById("fromFSEIdForRtlrTrans").value ==document.getElementById("toFSEIdForRtlrTrans").value)
	{
		alert("From and To FSE  cannot be same ");
		document.getElementById("toFSEIdForRtlrTrans").focus();
		return false
	}
	
	
	if(document.getElementById("strRetailerId").value ==""){
		alert("Please Select atleast one Retailor");
		return false;
	}
	if(document.forms[0].strRetailerId.length ==1){
		alert("No Retailer exists under the FSE");
		return false;
	}else{
		if (document.forms[0].strRetailerId[0].selected){
			for (var i=1; i < document.forms[0].strRetailerId.length; i++){
   			  	if(selectedRetailors !=""){
					selectedRetailors += ",";
     			}
     			var selectedValRtlr= document.forms[0].strRetailerId[i].value.split(",");
     			selectedRetailors += selectedValRtlr[0];
   			}
		}else{
			for (var i=1; i < document.forms[0].strRetailerId.length; i++){
				if (document.forms[0].strRetailerId[i].selected){
			     	if(selectedRetailors!=""){
						selectedRetailors += ",";
     				}
			     	var selectedValRtlr = document.forms[0].strRetailerId[i].value.split(",");
			     	selectedRetailors += selectedValRtlr[0];
      			}
   			}
   		}
   		 document.getElementById("hiddenRetlrSelecIds").value =selectedRetailors;
   	}
 }
	if(!FSECheck()){
		return false;
	}
	 return true; //////////////////////////////////////////////////////
}

function TransferData(){
	if(validateData()==1){
		document.forms[0].methodName.value = "performTransfer";
		  document.getElementById("transferButton").disabled = true;
		document.forms[0].submit();
	}else{
		 return false;
  	}
}
function resetValues()
{
     
     var collectSerialsNoBox = document.getElementById("collectSerialsNoBox");
	 for (var intLoop=0; intLoop < collectSerialsNoBox.length;intLoop++) 
	 {
         if (collectSerialsNoBox.options[intLoop]!=null) 
         {
             collectSerialsNoBox[intLoop]=null;
             intLoop=-1;
         }
     }
     
     document.getElementById("collectionId").value=0;
     document.getElementById("defectId").value=0;     
	 document.getElementById("productId").value=0;     
	 document.getElementById("collectSerialNo").value="";     
	 document.getElementById("collectionCount").value=0;     
	 document.getElementById("remarks").value="";     
}


function onRdbFSEClick(){
	document.getElementById("toDistId").value="-1";
	document.getElementById("fromDistributorId").value="-1";
	document.getElementById("FSEListLable").style.display="inline";
	document.getElementById("FSEListValue").style.display="inline";
	document.getElementById("withRtlrKeyVal").style.display="none";
	document.getElementById("withoutRtlrKeyVal").style.display="none";
	
	document.getElementById("RtlrListLable").style.display="none";
	document.getElementById("RtlrListValue").style.display="none";
	document.getElementById("FSEDrpLable").style.display="none";
	document.getElementById("FSEDrpValue").style.display="none";
	document.getElementById("FSEDrpLable2").style.display="none";
	document.getElementById("FSEDrpValue2").style.display="none";
}
function onRdbRtlrClick(){
	document.getElementById("toDistId").value="-1";
	document.getElementById("fromFSEIdForRtlrTrans").value="0";
	document.getElementById("toFSEIdForRtlrTrans").value="0";	
	document.getElementById("fromDistributorId").value="-1";
	document.getElementById("FSEListLable").style.display="none";
	document.getElementById("FSEListValue").style.display="none";
	document.getElementById("RtlrListLable").style.display="inline";
	document.getElementById("RtlrListValue").style.display="inline";
	document.getElementById("FSEDrpLable").style.display="inline";
	document.getElementById("FSEDrpValue").style.display="inline";
	document.getElementById("FSEDrpLable2").style.display="inline";
	document.getElementById("FSEDrpValue2").style.display="inline";
	document.getElementById("withRtlrKeyVal").style.display="none";
	document.getElementById("withoutRtlrKeyVal").style.display="none";
}
function FSECheck(){
	var hsRetailer =0;
	var alertMsg="";
	var chkedRdbVal;
	var checkFlag="";
	for (var i=0; i < document.forms[0].rdbWithAndWithoutRtlr.length; i++){
		if (document.forms[0].rdbWithAndWithoutRtlr[i].checked){
			chkedRdbVal = document.forms[0].rdbWithAndWithoutRtlr[i].value;
        }
   	}
 	if(chkedRdbVal =="2"){
	//if (document.forms[0].fromFSEIdsForFseTrans[0].selected){
		//	for (var i=1; i < document.forms[0].fromFSEIdsForFseTrans.length; i++)
  	 //	{
   			//var selectedValFse = document.forms[0].fromFSEIdsForFseTrans[i].value.split(",");
     		///if(selectedValFse[1]!=0){
     		//hsRetailer =1;
     //	}
     	
   //	}
   //	if(hsRetailer ==1){
   	//	alert("One of the FSE has Retailer, Please first Transfer the reltailer.***");
   	//	for (var i=0; i < document.forms[0].fromFSEIdsForFseTrans.length; i++)
  	 //	{
  	 //	document.forms[0].fromFSEIdsForFseTrans[i].selected =false;
   	//	}
   	//	return false;
   //	}
	//}
	//else{
		for (var i=1; i < document.forms[0].fromFSEIdsForFseTrans.length; i++)
  	 {
   		 if (document.forms[0].fromFSEIdsForFseTrans[i].selected)
      {
     	var selectedValFse = document.forms[0].fromFSEIdsForFseTrans[i].value.split(",");
     	var w = document.forms[0].fromFSEIdsForFseTrans[i].selectedIndex;
		var selected_text = document.forms[0].fromFSEIdsForFseTrans[i].text;
				
     	if(selectedValFse[1]!=0){
     	if(alertMsg !=""){
			alertMsg = alertMsg+"\n";
     	}
     	alertMsg=alertMsg + selected_text +" have "+ selectedValFse[1]+" retailers,Please Transfer these retailers first before transfering FSE without retailer. ";
     	
     	//alert("The FSE has Retailer, Please first Transfer the reltailer.");
     	document.forms[0].fromFSEIdsForFseTrans[i].selected =false;
     	document.forms[0].fromFSEIdsForFseTrans[0].selected =false;
     	checkFlag="false";
     	}
      
      }
   	}
//}
if(checkFlag == "false")
{
	alert(alertMsg);
	return false;
}
  }
  return true;
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
function selectAllFSE(){
		var ctrl = document.forms[0].fromFSEIdsForFseTrans;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].fromFSEIdsForFseTrans[0].selected){
			for (var i=1; i < document.forms[0].fromFSEIdsForFseTrans.length; i++){
  	 			document.forms[0].fromFSEIdsForFseTrans[i].selected =true;
     		}
   		}
   	
}

function selectAllRetailor(){
		var ctrl = document.forms[0].strRetailerId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].strRetailerId[0].selected){
			for (var i=1; i < document.forms[0].strRetailerId.length; i++){
  	 			document.forms[0].strRetailerId[i].selected =true;
     		}
   		}
   	
}


		var xmlHttp;
		function showFSE(){ 
			var selectedDist = document.getElementById("fromDistributorId").value;
			//alert("hi" +selectedDist);
			xmlHttp=GetXmlHttpObject();
			if (xmlHttp==null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
			var url="dpHierarchyTransfer.do";
			url=url+"?methodName=getFSEList&selected="+selectedDist;
			xmlHttp.onreadystatechange=stateChanged;
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
		}
		
		function showToFSE(){ 
			var selectedDist = document.getElementById("toDistributorId").value;
			//alert("hi" +selectedDist);
			xmlHttp=GetXmlHttpObject();
			if (xmlHttp==null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
			var url="dpHierarchyTransfer.do";
			url=url+"?methodName=getFSEList&selected="+selectedDist;
			xmlHttp.onreadystatechange=stateChangedToFse;
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
		}
		function showRetailor(){ 
			var selectedDist = document.getElementById("fromFSEIdForRtlrTrans").value
			xmlHttp=GetXmlHttpObject();
			if (xmlHttp==null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
			var url="dpHierarchyTransfer.do";
			url=url+"?methodName=getRetlrList&selected="+selectedDist;
			xmlHttp.onreadystatechange=stateChangedRtlr;
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
		}
		
function GetXmlHttpObject(){
			var xmlHttp=null;
			try{
  				// Firefox, Opera 8.0+, Safari
	  			xmlHttp=new XMLHttpRequest();
	  		}
			catch (e){
	  			// Internet Explorer
	  			try{
	    			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	    		}
	  			catch (e){
	    			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	    		}
	  		}
			return xmlHttp;
		}

	function stateChanged(){ 
			if (xmlHttp.readyState == 4){ 
				var xmldoc = xmlHttp.responseXML.documentElement;
				if(xmldoc == null){		
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");

				var selectObj = document.getElementById("fromFSEIdsForFseTrans");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("-All FSE-","0");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			    }
			    
			    var selectObj = document.getElementById("fromFSEIdForRtlrTrans");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("--Select FSE--","0");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			    }
			}
		}
		
		function stateChangedToFse(){ 
			if (xmlHttp.readyState == 4){ 
				var xmldoc = xmlHttp.responseXML.documentElement;
				if(xmldoc == null){		
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");

				var selectObj = document.getElementById("toFSEIdForRtlrTrans");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("--Select FSE--","0");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			    }
			    
			   
			}
		}
		function stateChangedRtlr(){ 
			if (xmlHttp.readyState == 4){ 
				var xmldoc = xmlHttp.responseXML.documentElement;
				if(xmldoc == null){		
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");

				var selectObj = document.getElementById("strRetailerId");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("-All Retailers-","0");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			    }
			    
			  
			}
		}


function getFromTsmName() {

	    var levelId = document.getElementById("circleIdNew").value;
		if(levelId!=''){
			var url = "dpHierarchyTransfer.do?methodName=getFromTsmList&levelId="+levelId;
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
			req.onreadystatechange = getFromTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
}
	
	
function getFromTsmNameChange(){
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("fromTsmId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("--Select a TSM--","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			
			//Updated by Shilpa Khanna against defect Id : MASDB00179829 
			
			// Get Conditions.
			var selectObj2 = document.getElementById("fromDistributorId");
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("--Select Distributor--","-1");
			
		}
}	
	
	function getTsmName() {

	    var levelId = document.getElementById("circleIdNew").value;
		if(levelId!=''){
			var url = "dpHierarchyTransfer.do?methodName=getTsmList&levelId="+levelId;
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
			req.onreadystatechange = getTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
}
	
	
function getTsmNameChange(){
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj2 = document.getElementById("toTsmId");
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("--Select a TSM--","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			
			//Updated by Shilpa Khanna against defect Id : MASDB00179829 
			
			
			// Get Conditions.
			var selectObj3 = document.getElementById("toDistId");
			selectObj3.options.length = 0;
			selectObj3.options[selectObj3.options.length] = new Option("--Select Distributor--","-1");
			
			//document.getElementById("availableProdQty").value ="0";
		}
}	
	
	
function getFromDistName(){
	    var levelId = document.getElementById("fromTsmId").value;
	    var circleId = document.getElementById("circleIdNew").value;
	
	 	if(levelId!=''){
			var url = "dpHierarchyTransfer.do?methodName=getFromDistList&parentId="+levelId+"&circleId="+circleId;
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
			req.onreadystatechange = getFromDistNameChange;
			req.open("POST",url,false);
			req.send();
		}
}
function getFromDistNameChange(){
		if (req.readyState==4 || req.readyState=="complete") 
		
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("fromDistributorId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("--Select Distributor--","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
		
}
function getToDistName(){
	    var levelId = document.getElementById("toTsmId").value;
	    var circleId = document.getElementById("circleIdNew").value;
	
	 	if(levelId!=''){
			var url = "dpHierarchyTransfer.do?methodName=getToDistList&parentId="+levelId+"&circleId="+circleId;
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
			req.onreadystatechange = getToDistNameChange;
			req.open("POST",url,false);
			req.send();
		}
}
function getToDistNameChange(){
		if (req.readyState==4 || req.readyState=="complete"){
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("toDistId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("--Select Distributor--","-1");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
}
</script>
</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >
<html:form name="HierarchyTransferBean" action="/dpHierarchyTransfer"
	type="com.ibm.dp.beans.HierarchyTransferBean">
	<html:hidden property="methodName" styleId="methodName" />
	<html:hidden property="strLoginId" name="HierarchyTransferBean" />
	<html:hidden property="hiddenFSESelecIds" name="HierarchyTransferBean" />
	<html:hidden property="hiddenRetlrSelecIds" name="HierarchyTransferBean" />
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Init_Hier_Trans.jpg""
						width="505" height="22" alt=""></TD>
				</tr>
			</table>
				
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="6">
				<strong><font color="red" class="text" size="15px">
					<bean:write name="HierarchyTransferBean" property="strSuccessMsg"/></font></strong>
			</td>
		</tr>
		<tr>
			<td colspan="6" class="text">&nbsp;</td>
		</tr>
		
		<tr>
			<td colspan="6" class="text" align="center" >
			 <table width="90%" border="0" cellspacing="0" cellpadding="0" >
				
				<TR id="circle" >
				<td class="txt" width="18%">
						<strong><bean:message bundle="dp" key="interSSD.Circle"/></strong>
				</TD>
				<TD class="txt" width="32%">
					<html:select property="circleIdNew" onchange="getFromTsmName();getTsmName();" style="width:150px">
						<logic:notEmpty property="circleList" name="HierarchyTransferBean" >
							<html:option value="-1">--Select Circle--</html:option>
								<bean:define id="circleList" name="HierarchyTransferBean" property="circleList" />
								<html:options labelProperty="circleName" property="circleId" collection="circleList" />
						</logic:notEmpty>
				</html:select>
			</TD>
			<td width="18%">&nbsp;</td>
			<td width="32%">&nbsp;</td>
			
		</TR>
		<tr>
					<td width="100%" colspan='4'><BR><BR></td>
				</tr> 
		<tr>
				<TD class="txt" width="18%">
					<strong><bean:message bundle="dp" key="interSSD.FromTsm"/></strong>
				</td>
				<TD class="txt" width="32" >
				<html:select property="fromTsmId" onchange="getFromDistName();" style="width:150px">
						<html:option value="-1">--Select TSM--</html:option>
					</html:select>
			</td>
			<td width="18%"><strong><bean:message bundle="dp" key="interSSD.ToTsm"/></strong></td>
			<td width="32%"><html:select property="toTsmId" onchange="getToDistName();" style="width:150px">
						<html:option value="-1">--Select TSM--</html:option>
					</html:select></td>
			
		 </tr>
				
				<tr>
					<td width="100%" colspan='4'><BR><BR></td>
				</tr> 
				
				<tr>
					<td width="18%">
						<html:radio property="rdbTransfer" value="1" styleId="rdbTransferFSE"   onclick="onRdbFSEClick();"></html:radio><strong>
						<bean:message bundle="view" key="hierarchyTrans.TransFSE" /></strong>
					</td>
					<td width="32%">&nbsp;</td>
					<td width="18%">
						<html:radio property="rdbTransfer" value="2" styleId="rdbTransferRtlr" onclick="onRdbRtlrClick();"></html:radio><strong><bean:message
										bundle="view" key="hierarchyTrans.TransRetailer" /></strong>
					</td>
					<td width="32%">&nbsp;</td>
				</tr>
				<tr>
					<td width="100%" colspan='4'><BR><BR></td>
				</tr> 

				
				
				
				<!--<tr> 
					<td align="left" width="18%">
						<strong><bean:message	bundle="view" key="hierarchyTrans.FromDist" /></strong>
					</td>
					<td width="32%">
					<html:select property="fromDistributorId" styleId="fromDistributorId" style="width:150px" onchange="showFSE();">
							<html:option value="0"><bean:message bundle="view" key="dcStatus.Select" /></html:option>
							<logic:notEmpty property="fromDistributorList" name="HierarchyTransferBean">
								<html:options labelProperty="accountName" property="accountId"	collection="fromDistributorList" />
							</logic:notEmpty>
						</html:select>
					</td>
								<td width="18%">&nbsp;</td>
								<td width="32%">&nbsp;</td>
										
				</tr>
				
				-->
				<tr>
					<TD class="txt" width="18%"><strong><bean:message bundle="dp" key="interSSD.FromDist"/></strong></td>
					<TD class="txt" width="32%">
							<!-- property="fromDistributorId" -->
						<html:select property="fromDistributorId"  style="width:150px" styleId="fromDistributorId" onchange="showFSE();">
							<html:option value="-1">--Select Distributor--</html:option>
							</html:select>
					</td>
					<TD class="txt" width="18%"><STRONG><bean:message bundle="dp" key="interSSD.ToDist"/></STRONG></td>
					<TD class="txt" width="32%">
					<html:select property="toDistId" style="width:150px" styleId="toDistributorId" onchange="showToFSE()";>
						<html:option value="-1">--Select Distributor--</html:option>
					</html:select></td>
				</tr>
				<tr>
					<td width="100%" colspan='4'><BR><BR></td>
				</tr> 
				<tr>
					<td width="18%" align="left">
					 <span id="FSEDrpLable" style="display: none">
						<strong><bean:message bundle="view" key="hierarchyTrans.FromFSE" /></strong>
					</span>
					</td>
					<td width="32%">
					<span id="FSEDrpValue"  style="display: none" >
						<html:select  property="fromFSEIdForRtlrTrans" styleId="fromFSEIdForRtlrTrans" style="width:150px" onchange="showRetailor();">
							<html:option value="0">--Select FSE--</html:option>
						</html:select>
						</span>
					</td>
							<td width="18%" align="left">
					 <span id="FSEDrpLable2" style="display: none">
						<strong><bean:message bundle="view" key="hierarchyTrans.ToFSE" /></strong>
					</span>
					</td>
					<td width="32%">
					<span id="FSEDrpValue2"  style="display: none" >
						<html:select  property="toFSEIdForRtlrTrans" styleId="toFSEIdForRtlrTrans" style="width:150px" >
							<html:option value="0">--Select FSE--</html:option>
						</html:select>
						</span>
					</td>
					
				</tr>
				
				
				<tr>
					<td width="100%" colspan='4'><BR><BR></td>
				</tr>
			
				
				
				
				
				
				
				
				
				
				<tr height="40">
				 	<td width="18%">
				 	<span id="withRtlrKeyVal" style="display: none">
				 		<html:radio property="rdbWithAndWithoutRtlr" value="1" styleId="rdbWithRtlr" >
				 		</html:radio>
				 		<strong><bean:message bundle="view" key="hierarchyTrans.WithRtlr" /></strong>
				 	</span>
					</td>
					
					<td width="32%">&nbsp;</td>
					
					<td width="18%">
				 	<span id="withoutRtlrKeyVal" style="display: none">
						<html:radio property="rdbWithAndWithoutRtlr" value="2" styleId="rdbWithOutRtlr" >
						</html:radio><strong><bean:message	bundle="view" key="hierarchyTrans.WithoutRtlr" /></strong>
						</span>
					</td>
					
					<td width="32%">&nbsp;</td>
				 </tr>
				<tr>
					<td width="100%" colspan='4'><BR><BR></td>
				</tr>
				<tr>
				
				 <td width="18%" valign='top'>
					 <span id="FSEListLable">
					 <strong><bean:message bundle="view" key="hierarchyTrans.FSE" /></strong>
					 </span>
					 
					 <span id="RtlrListLable"  style="display: none">
					 <strong><bean:message bundle="view" key="hierarchyTrans.RetailerList" /></strong>
					 </span>
				 </td>
				 
				 <td width="35%">
				 	 <span id="FSEListValue">
						<html:select  multiple="true"  property="fromFSEIdsForFseTrans" styleId="fromFSEIdsForFseTrans" style="width:150px;" size="6" onchange="selectAllFSE();">
							<html:option value="0">-All FSE-</html:option>
						</html:select>
					</span>
					
					<span id="RtlrListValue"  style="display: none">
						<html:select  multiple="true"  property="strRetailerId" styleId="strRetailerId" style="width:150px" size="6" onchange="selectAllRetailor();">
							<html:option value="0">-All Retailers-</html:option>
						</html:select>
					</span>
				</td>
				<td width="50%" colspan='2'>&nbsp;</td>
				</tr>			
			</table>
			</td>
		</tr>
	</table>
	
	<br>
	<DIV>
		<table width="100%">
			<tr>
				<td width="100%" align="center">
					<input type="button" name="transferButton" value="Transfer" onclick="TransferData();" >
				</td>
			</tr>
		</table>
	</DIV>

</html:form>
</BODY>
</html>
