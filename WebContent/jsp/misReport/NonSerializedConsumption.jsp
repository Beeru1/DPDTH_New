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
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
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

<SCRIPT language="javascript" type="text/javascript">



	function daysBetween(date1, date2) 
	{
	    var DSTAdjust = 0;
	    // constants used for our calculations below
	    oneMinute = 1000 * 50;
	    var oneDay = oneMinute * 50 * 24;
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
		/*if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		*/
		if (document.forms[0].tsmId[0].selected){
			for (var i=1; i < document.forms[0].tsmId.length; i++){
  	 			document.forms[0].tsmId[i].selected =true;
     		}
   		}
   	
}
function selectAllCircles(){
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



function exportToExcel()
	{
		if(document.forms[0].showCircle.value=="A"){
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	  	}
		if(document.forms[0].showTSM.value=="A"){
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
	  	
	  	
   	
	  	
	 	if(document.getElementById("productId").value ==""){
				alert("Please Select atleast one STB Type");
				return false;
	   		}
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
   		 document.getElementById("hiddenSTBTypeSelec").value =selectedSTBTypeValues;
	  	
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
			}
	   
	   
	var url = "NonSerializedConsumptionReport.do?methodName=exporttoExcel";
	
	document.forms[0].action=url;
	document.forms[0].method="post";
	setInterval(getReportDownloadStatus,1000);  
	document.forms[0].submit();
	}
</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form  action="/NonSerializedConsumptionReport"   method="post" >
	<html:hidden property="showCircle" name="NonSerializedConsumptionReportBean" />
	<html:hidden property="showTSM" name="NonSerializedConsumptionReportBean" />
	<html:hidden property="showDist" name="NonSerializedConsumptionReportBean" />
	
	<html:hidden property="hiddenCircleSelecIds" name="NonSerializedConsumptionReportBean" />
	<html:hidden property="hiddenTsmSelecIds" name="NonSerializedConsumptionReportBean" />
	<html:hidden property="hiddenDistSelecIds" name="NonSerializedConsumptionReportBean" />
	<html:hidden property="hiddenSTBTypeSelec" name="NonSerializedConsumptionReportBean" />
	
	
	
	
	<TR valign="top">
		
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Non_Serialized_Consumption.jpg"
						width="410" height="24" alt=""></TD>
						
		
				</TR>
			<TR>
				<logic:match value="A" property="showCircle" name="NonSerializedConsumptionReportBean">	
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px; height:50px;"  onchange="selectAllCircles();" size="6" onblur="getTsmName();">
							<html:option value="-1">-All-</html:option>
							<logic:notEmpty name="NonSerializedConsumptionReportBean" property="circleList">
								 <html:optionsCollection name="NonSerializedConsumptionReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			</logic:match>
			<logic:match value="N" property="showCircle" name="NonSerializedConsumptionReportBean">	
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleId"  disabled="true" styleId="circleId" style="width:150px; height:50px;" onchange="selectAllCircles();" size="6" onblur="getTsmName();">
							<logic:notEmpty name="NonSerializedConsumptionReportBean" property="circleList">
								 <html:optionsCollection name="NonSerializedConsumptionReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			</logic:match>
			</tr><tr>
			<logic:match value="A" property="showTSM" name="NonSerializedConsumptionReportBean">		
					<TD align="center"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" styleId="tsmId" style="width:150px; height:50px;" size="6" onblur="getDistName();" onchange="selectAllTSM();">
							<logic:notEmpty name="NonSerializedConsumptionReportBean" property="tsmList">
							 <html:optionsCollection name="NonSerializedConsumptionReportBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</logic:match>
				<logic:match value="N" property="showTSM" name="NonSerializedConsumptionReportBean">		
					<TD align="center"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" disabled="true" styleId="tsmId" style="width:150px; height:50px;" size="6" onblur="getDistName();" onchange="selectAllTSM();">
						<logic:notEmpty name="NonSerializedConsumptionReportBean" property="tsmList">
							 <html:optionsCollection name="NonSerializedConsumptionReportBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</logic:match>
				</TR>
				
				
					<TR>
				<logic:match value="A" property="showDist" name="NonSerializedConsumptionReportBean">		
					<TD align="center"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="distId" styleId="distId" style="width:150px; height:50px;" size="6" onchange="selectAllDist();" >
							<logic:notEmpty name="NonSerializedConsumptionReportBean" property="distList">
								 <html:optionsCollection name="NonSerializedConsumptionReportBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				<logic:match value="N" property="showDist" name="NonSerializedConsumptionReportBean">		
					<TD align="center"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  disabled="true"  property="distId" styleId="distId" style="width:150px; height:50px;" size="6" onchange="selectAllDist();" >
							<logic:notEmpty name="NonSerializedConsumptionReportBean" property="distList">
								 <html:optionsCollection name="NonSerializedConsumptionReportBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				</tr><tr>
				<TD align="center" height="46" width="180">
						<b class="text pLeft15">Product Type<font color="red">*</font></b>
						
					</td>
					 <TD >				   
						<html:select property="productId"  style="width:180px; height:50px;" styleId="dcProductCategListDTO" name="NonSerializedConsumptionReportBean" onchange="selectAllProducts()" multiple="true">
							<html:option value="-1">-All-</html:option>
							<logic:notEmpty property="dcProductCategListDTO" name="NonSerializedConsumptionReportBean">
						
						<html:optionsCollection name="NonSerializedConsumptionReportBean"	property="dcProductCategListDTO" label="productCategory" value="productCategory"/>
						 </logic:notEmpty>
						</html:select>
						
					</TD>  
				</TR>	
				
				<TR>
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="NonSerializedConsumptionReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'NonSerializedConsumptionReportBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
			</tr><tr>
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="NonSerializedConsumptionReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'NonSerializedConsumptionReportBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
	
						 </FONT>
					</TD>
					
					
				</TR>
				
				<tr>
					<td colspan="2" align="center">
					<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
						
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
				<TR align="center" bgcolor="4477bb" valign="top">

				</TR>
	 <html:hidden property="methodName" styleId="methodName" value="exporttoExcel"  />
	
			</table>
		</td>
		</TR>
		</html:form>
		
	
	
</TABLE>
<input type="hidden" name="reportDownloadStatus" value=""> <!-- Neetika BFR 16 14Aug -->
<script type="text/javascript">
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	var url= "NonSerializedConsumptionReport.do?methodName=getReportDownloadStatus";
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	//alert("get report .."+document.getElementById("reportDownloadStatus").value);
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
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>