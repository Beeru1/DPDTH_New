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
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"><!-- 

function printtPage(form)
{				
		var recoID = document.getElementById("recoID").value;
		var circleid = document.getElementById("circleid").value;
		var selectedProductId = document.getElementById("productId").value;	
		var selectedTsmId = document.getElementById("accountId").value;
		var selectedDistId = document.getElementById("distAccId").value;	
		
		
		
		populateRetailerIdArray(form);
		var flag=false;
		
		if(recoID == -1)
		{
			 alert('Please select any Reco Period.');
			 return false;		
		}		
		if(circleid == -1 || circleid == -2 || circleid == "")
		{
			 alert('Please select any circle.');
			 return false;		
		}
		if(circleid == "0")
		{
			alert("Please select circle other than Pan India ");
			return false;		
		}
		
		if(selectedTsmId != "")
		{
			if(selectedDistId == "")
			{
				alert("Please select Distributor ");
				return false;
			}
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
			 alert('Please select any product.');
			 return false;		
		}
		
	  var selObj1 = document.getElementById("productId");
	  for (i=0; i<selObj1.options.length; i++) {
	    if (selObj1.options[i].selected) {
	      if(selObj1.options[i].value == "0")
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
  	var hiddenDistId = document.forms[0].hiddenDistId.value;
  	//alert(hiddenDistId);
  	//document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
  	//if(selectedTsmId != "")
	//var url="recoDetailReport.do?methodName=printRecoDetail&recoID="+recoID+"&circleid="+circleid;
	//var url="recoDetailReport.do?methodName=printRecoDetail&recoID="+recoID+"&circleid="+circleid+"&selectedTsmId="+selectedTsmId+"&selectedDistId="+selectedDistId;
	var url="recoDetailReport.do?methodName=printRecoDetail&recoID="+recoID+"&circleid="+circleid+"&selectedTsmId="+selectedTsmId+"&hiddenDistId="+hiddenDistId;
	window.open(url,"SerialNo","width=750,height=650,scrollbars=yes,toolbar=no");
  
}

function disableSelected(){
/*
var groupId = document.getElementById("groupId").value;
if(groupId==7){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").size=1;
	document.getElementById("circleid").disabled=true;
	var circleid = document.getElementById("circleid").value;
		
	}
if(groupId==6){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").disabled=true;
	
	}
if(groupId==3 || groupId==4 || groupId==5){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").disabled=true;
	}
	*/
}
	function exportToExcel(form)
	{
		var recoID = document.getElementById("recoID").value;
		var circleid = document.getElementById("circleid").value;
		var selectedProductId = document.getElementById("productId").value;	
		populateRetailerIdArray(form);
		
		if(recoID == -1)
		{
		 alert('Please select any Reco Period.');
		 return false;		
		}		
		// added by rampratap circleid == ""
		if(circleid == -1 || circleid == -2 || circleid == "")
		{
		 alert('Please select any circle.');
		 return false;		
		}
		if(selectedProductId=="")
		{
		 alert('Please select any product.');
		 return false;		
		}
		
	    var url = "recoDetailReport.do?methodName=getRecoDetailReportDataExcel&recoID="+recoID+"&circleid="+circleid+"&productId="+selectedProductId;
		document.forms[0].action=url;
		document.forms[0].method="post";
		setInterval(getReportDownloadStatus,1000);
		document.forms[0].submit();
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
	function getProductList(){
		var businessCategory = '<% out.print(Constants.PRODUCT_BUSINESS_CATEGORY_CPE); %>';		
		var circleid = document.getElementById("circleid").value;
		getAllProductsSingleCircle(businessCategory, circleid, 'productId');		
	}


function ajax(url,elementId){

	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function(){
		getAjaxValues(elementId);
	}

	req.open("POST",url,false);
	req.send();
}

function getAjaxValues(elementId){
	if (req.readyState==4 || req.readyState=="complete") {
		
		var xmldoc = req.responseXML.documentElement;
		
		if(xmldoc == null){		
			var selectObj = document.getElementById(elementId);
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("--Select--", "0");
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById(elementId);
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option("--Select--", "0");
		for(var i=0; i<optionValues.length; i++){
		   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
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
function selectedCount(ctrl){
	var count = 0;
	for(i=1; i < ctrl.length; i++){
		if(ctrl[i].selected){
			count++;
		}
	}
	return count;
}

function getSelectedValue()
{
	var selectedCircleValues="";
	var groupId = document.getElementById("groupId").value;
	
	if((groupId==1 || groupId==2 || groupId==3) && document.forms[0].circleid[0].selected && document.forms[0].circleid[0].value=="0")
	{
		for (var i=1; i < document.forms[0].circleid.length; i++)
 		{
 			if(selectedCircleValues=="")
 				selectedCircleValues = document.forms[0].circleid[i].value;
 			else
 				selectedCircleValues = selectedCircleValues +","+document.forms[0].circleid[i].value;
 			/*	
    		if(selectedCircleValues !="")
    		{
				selectedCircleValues += ",";
    		}
     		var selectedValCircle = document.forms[0].circleid[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
     		*/
     		
     		document.forms[0].circleid[i].selected = true;
   		}
	}	
	else
	{
		for (var i=0; i < document.forms[0].circleid.length; i++)
	  	{
	   		 if (document.forms[0].circleid[i].selected)
	     	 {
				if(selectedCircleValues=="")
 					selectedCircleValues = document.forms[0].circleid[i].value;
 				else
 					selectedCircleValues = selectedCircleValues +","+document.forms[0].circleid[i].value;
				/*
				selectedCircleValues += ",";
	     		var selectedValCircle = document.forms[0].circleid[i].value.split(",");
	     		selectedCircleValues += selectedValCircle[0];
	     		*/
	     	 }
	     	
	   	}
	}
	document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
}

//added by aman







function resetValues(flag){
		var accountId = document.getElementById("accountId");
		var distAccId = document.getElementById("distAccId");
		if(flag==1){
			accountId.options.length = 0;
			accountId.options[accountId.options.length] = new Option("Select a TSM","");
			distAccId.options.length = 0;
			distAccId.options[distAccId.options.length] = new Option("Select a Distributor","");
		}
		if(flag==2){
			distAccId.options.length = 0;
			distAccId.options[distAccId.options.length] = new Option("Select a Distributor","");
		}
	}





function getTsmName() 
	{
	
	 var circleId = document.getElementById("circleID").value;
	
	
		var selectedCircleValues="";
		
		
		
		
		
		
		
		
		if(circleId!=''){
			var url = "recoDetailReport.do?methodName=InitTsmList&circleId="+circleId;
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
	function getTsmNameChange()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("accountId");
			//var selectObj1 = document.getElementById("tsmList");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select a TSM","");
			//selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
			//alert("response" + optionValues[i].getAttribute("text") + "   VALUE " + optionValues[i].getAttribute("value") );
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
				//selectObj1.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}	
		
		
		
		
		
		
		
		
		
	/*	var url = "recoDetailReport.do?methodName=InitTsmList&circleId="+circleId;
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
			
		}
		*/
	/*	for (var i=1; i < document.forms[0].circleid.length; i++)
 		{
 		
 		alert("here1");
 			if(selectedCircleValues=="")
 				selectedCircleValues = document.forms[0].circleid[i].value;
 			else
 			selectedCircleValues = selectedCircleValues +","+document.forms[0].circleid[i].value;
 				
    		if(selectedCircleValues !="")
    		{
				selectedCircleValues += ",";
    		}
     		var selectedValCircle = document.forms[0].circleid[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
     		
     		alert("here2"+selectedCircleValues)
     		document.forms[0].circleid[i].selected = true;
   		}
   		*/	  
	  //  var circleId = document.getElementById("circleID").value;
	    
	   // alert("leavel:"+circleId);
	//	 alert("2222222222222222");
		 
		 //var url = "recoDetailReport.do?methodName=InitTsmList&circleId="+circleId;
		 
				 
			//  document.forms[0].methodName.value = "InitTsmList";
			 // document.forms[0].submit();
		
		//var url = "recoDetailReport.do?methodName=InitTsmList&circleid="+selectedCircleValues;
		
	//}



	function getDistName()
	{
	    var levelId = document.getElementById("accountId").value;
	    var circleId = document.getElementById("circleid").value;
	
	 	if(levelId!=''){
			//var url = "recoDetailReport.do?methodName=InitDistList&parentId="+levelId+"&circleId="+circleId;
			var url = "recoDetailReport.do?methodName=InitDistList&levelId="+levelId;
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
			req.onreadystatechange = getDistNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(2);
		}
	}
	
	
	
	
		function getDistNameChange()
	{
		if (req.readyState==4 || req.readyState=="complete") 
		
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("distAccId");
			selectObj.options.length = 0;
			if(optionValues.length == 0 )
			selectObj.options[selectObj.options.length] = new Option("Select a Distributor","");
			else
			selectObj.options[selectObj.options.length] = new Option("All","0");
			
			//selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
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
	
	
	function selectAllDist(){
		var ctrl = document.forms[0].distAccId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].distAccId[0].selected){
			for (var i=1; i < document.forms[0].distAccId.length; i++){
  	 			document.forms[0].distAccId[i].selected =true;
     		}
   		}
   	
}



function setSelectedDistType() 
{

	   var distAccId = document.getElementById("distAccId").value;
		var selectedAccountType ="";
		var selectedAccountTypeIds="";
		
	
	if(distAccId ==""){
		alert("Please Select atleast one Distributor Type");
		return false;
	 }
	 if(document.forms[0].distAccId.length ==1){
			alert("No Account Type exists");
		return false;
	   } 
 	else{
	 	if (document.forms[0].distAccId[0].selected){
		
			for (var i=1; i < document.forms[0].distAccId.length; i++)
  	 		{
   			
     		selectedAccountTypeIds = document.forms[0].distAccId[i].value;
     		
     		if(selectedAccountTypeIds!=""){
     	
	     	 selectedAccountType =selectedAccountType +","+ selectedAccountTypeIds;
	     		//alert("selectedAccountType="+selectedAccountType);
	     	 }
	     	 selectedAccountTypeIds="";
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].distAccId.length; i++)
	  	 {
	   		 if (document.forms[0].distAccId[i].selected)
	     	 {
	     	
	     		selectedAccountTypeIds = document.forms[0].distAccId[i].value;
	     		//alert("selectedAccountType==="+selectedAccountType);
     		}
	     	 if(selectedAccountTypeIds!=""){
	     	 
	     	 selectedAccountType =selectedAccountType +","+ selectedAccountTypeIds;
	     	 //alert("selectedAccountType=="+selectedAccountType);
	     	 }
	     	 selectedAccountTypeIds="";
	   	}
   }
   	 	document.getElementById("hiddenDistId").value =selectedAccountType;
   		
 }
	//alert("selectedAccountTypevalues :"+selectedAccountType);
	
	//alert("document.getElementById('hiddenAccountType').value :"+document.getElementById("hiddenAccountType").value);	
}
//end of changes by aman




	--></SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="disableSelected()">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">


	<html:form action="/recoDetailReport">

	<html:hidden property="methodName" value="getStockDetailReportDataExcel"/>
	<html:hidden property="productIdArray"/>
	<html:hidden property="groupId"/>
	<html:hidden property="hiddenCircleSelecIds" name="RecoPeriodConfFormBean"/>
	<html:hidden property="hiddenDistId" name="RecoPeriodConfFormBean" />
	<html:hidden property="strMessage"/>
		<TR valign="top">

			<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<!-- Page title start -->
					
								<IMG src="${pageContext.request.contextPath}/images/recoDetailReport.jpg"
									width="410" height="24" alt="">
					<!-- Page title end --></TD>

				</TR>
				<TR><TD>&nbsp;</TD></TR>
				<TR>
				    <TD class="txt" align="left" width="20%"><strong><font
						color="#000000">Reco Period</font><font color="red">*</font>: </strong></TD>
					<td><html:select
						styleId="recoID" property="recoID"
						name="RecoPeriodConfFormBean" style="width:150px"
						style="height:20px">
						<html:option value="-1" >
							<bean:message bundle="dp" key="option.select" />
						</html:option>
						<logic:notEmpty name="RecoPeriodConfFormBean" property="recoListTotal">
							<bean:define id="recoListTotal" name="RecoPeriodConfFormBean" property="recoListTotal" />
						<html:options labelProperty="strText" property="strValue" collection="recoListTotal" />
						
						</logic:notEmpty>
					</html:select></TD>
			     </TR>
				<TR>
				    <TD class="txt" align="left" width="20%"><strong><font
						color="#000000"><bean:message bundle="dp"
						key="label.hierarchy.circle" /></font><font color="red">*</font>: </strong></TD>
					<td><html:select multiple="true" styleId="circleid" property="circleid"
						name="RecoPeriodConfFormBean" style="width:180px" size="3" onchange="javascript:getTsmName();">
						
						<logic:notEmpty name="RecoPeriodConfFormBean" property="arrCIList">
							<bean:define id="arrCIList" name="RecoPeriodConfFormBean" property="arrCIList" />
						<html:options labelProperty="strText" property="strValue" collection="arrCIList" />
						</logic:notEmpty>
					</html:select></TD>
			     </TR>
			     
			     <!-- added by aman -->
				
					<TR>
					<!-- <TD align="center" height="46" >  -->
					
					 <TD class="txt" align="left" width="20%"><strong><font
						color="#000000">TSM</font><font color="red">*</font>: </strong></TD>
					
					
					<!--  	<b class="text pLeft15">TSM<font color="red">*</font></b>
					</td>
					-->
					
					
					<TD >				   
						<html:select  property="accountId"  name="RecoPeriodConfFormBean" onchange="javascript:getDistName();">
							<html:option value="">- Select a TSM -</html:option>
							
							  <logic:notEmpty property="tsmList" name="RecoPeriodConfFormBean" >  
						
						<html:optionsCollection name="RecoPeriodConfFormBean"	property="tsmList" label="accountName" value="accountId"/>
					</logic:notEmpty>
						</html:select>
					</TD>
				</TR>
				
				
				
					<TR>
					<!--  
					<TD align="center" height="46" >
						<b class="text pLeft15">Disributor<font color="red">*</font></b>
					</td>
					-->
					
					 <TD class="txt" align="left" width="20%"><strong><font
						color="#000000">Disributors</font><font color="red">*</font>: </strong></TD>
					
					
					<TD >				   
						<html:select multiple="true" size="6" onchange="setSelectedDistType();selectAllDist();" 
						property="distAccId" name="RecoPeriodConfFormBean">
							<html:option value="">- Select a Distributor -</html:option>
							<logic:notEmpty property="distList" name="RecoPeriodConfFormBean">
							<!-- <option value="0">Select all Distributors</option>  -->
						</logic:notEmpty>
						</html:select>
					</TD>
				</TR>
				
				
				
				
				<!-- end of changes by aman -->
			     <TR>
			     <TD class="txt" align="left" width="20%"><strong><font
						color="#000000"><bean:message bundle="dp"
						key="label.product.type" /></font><font color="red">*</font>: </strong></TD>
					
						<td>	
						<html:select   multiple="true"  property="productId" styleId="productId" style="width:180px;" size="3" onchange="selectAllProducts()">
							<html:option value="0"><bean:message bundle="dp" key="select.all" /></html:option>
							<logic:notEmpty name="RecoPeriodConfFormBean" property="productList">
							 <html:optionsCollection name="RecoPeriodConfFormBean"	property="productList" label="productCategory" value="productCategoryId"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
			
			<logic:notEqual name="RecoPeriodConfFormBean" property="groupId" value="3">
				<script>
						document.getElementById("recoID").value="-1";
						
					</script>
				<logic:equal name="RecoPeriodConfFormBean" property="productId" value="">
					<script>
						document.getElementById("circleid").value="-1";
						
					</script>
				</logic:equal>	
			</logic:notEqual>	
			<logic:equal name="RecoPeriodConfFormBean" property="groupId" value="3">
			<script>
			document.getElementById("recoID").value="-1";
			</script>
			</logic:equal>
				
				
			
				</TR>
				
				
				<TR></TR>
				<TR></TR>
				<TR>
				<TD></TD>
					<TD align="left">
						<html:button property="excelBtn" value="Generate Report" onclick="exportToExcel(this.form);"/>
						<logic:equal value="2" name="RecoPeriodConfFormBean" property="groupId">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="printPage" value="Print Certificate" onclick="printtPage(this.form)" />
						</logic:equal>
					</TD>
				</TR>
</Table>
				</html:form>
				<!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
				<TR align="center" bgcolor="4477bb" valign="top">

				</TR>


			</TABLE>
<input type="hidden" name="reportDownloadStatus" value=""> <!-- Neetika BFR 16 14Aug -->
<script type="text/javascript">
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	var url= "recoDetailReport.do?methodName=getReportDownloadStatus";
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	//alert("get report .."+document.getElementById("reportDownloadStatus").value);
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
	</SCRIPT>
</BODY>
</html:html>
