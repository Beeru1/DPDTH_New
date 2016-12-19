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
<SCRIPT language="javascript" type="text/javascript">



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
		var circleid = document.getElementById("circleid").value;
		var selectedCircleValues ="";			
		getAllAccountsUnderMultipleAccounts(0,'distId');
		
	if(circleid ==""){
		alert("Please Select atleast one Circle");
		return false;
	 }
	 if(document.forms[0].circleid.length ==1){
			alert("No Circle exists");
		return false;
	   }
 	else{
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
function selectAllCollectionTypes(){
		var ctrl = document.forms[0].collectionName;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].collectionName[0].selected){
			for (var i=1; i < document.forms[0].collectionName.length; i++){
  	 			document.forms[0].collectionName[i].selected =true;
     		}
   		}
   	
}



function exportToExcel()
	{
		if(document.forms[0].showCircle.value=="A"){
			if(document.getElementById("circleid").value ==""){
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
				alert("Please Select atleast one Product Type");
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
   		 
   		
	  	
	 	if(document.getElementById("trnsfrType").value ==""){
				alert("Please Select atleast one Transfer Type");
				return false;
	   		}
	   		var selectedtrnsfrTypeValues = "";
	   		if (document.forms[0].trnsfrType[0].selected){
		
			for (var i=1; i < document.forms[0].trnsfrType.length; i++)
  	 		{
   		
     			if(selectedtrnsfrTypeValues !=""){
					selectedtrnsfrTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].trnsfrType[i].value.split(",");
     		selectedtrnsfrTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].trnsfrType.length; i++)
	  	 	{
	   		 if (document.forms[0].trnsfrType[i].selected)
	     	 {
	     		if(selectedtrnsfrTypeValues !=""){
					selectedtrnsfrTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].trnsfrType[i].value.split(",");
     		selectedtrnsfrTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenTrnsfrTypeSelec").value =selectedSTBTypeValues;
	  	var dateOption =document.getElementById("dateOption").value;
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
	   }
	   
	var url = "initInterSSDStockTransferReport.do?methodName=exporttoExcel";
	
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
	}
</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form  action="/initInterSSDStockTransferReport"   method="post" >
	<html:hidden property="showCircle" name="DPInterSSDStockTransferReportFormBean" />
	<html:hidden property="showTSM" name="DPInterSSDStockTransferReportFormBean" />
	<html:hidden property="showDist" name="DPInterSSDStockTransferReportFormBean" />
	
	<html:hidden property="hiddenCircleSelecIds" name="DPInterSSDStockTransferReportFormBean" />
	<html:hidden property="hiddenTsmSelecIds" name="DPInterSSDStockTransferReportFormBean" />
	<html:hidden property="hiddenDistSelecIds" name="DPInterSSDStockTransferReportFormBean" />
	<html:hidden property="hiddenTrnsfrTypeSelec" name="DPInterSSDStockTransferReportFormBean" />
	<html:hidden property="hiddenSTBTypeSelec" name="DPInterSSDStockTransferReportFormBean" />
	
	
	
	
	<TR valign="top">
		
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Inter_SSD_Stock_Transfer_Report.jpg"
						width="410" height="24" alt=""></TD>
		
				</TR>
			<TR>
				<logic:match value="A" property="showCircle" name="DPInterSSDStockTransferReportFormBean">	
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleid" styleId="circleid" style="width:150px;" size="3" onchange="getTsmName();selectAllCircles()" onblur="">
							<html:option value="-1">-All-</html:option>
							<logic:notEmpty name="DPInterSSDStockTransferReportFormBean" property="circleList">
								 <html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			</logic:match>
			<logic:match value="N" property="showCircle" name="DPInterSSDStockTransferReportFormBean">		
						<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
					<td>
						<html:select styleId="circleid" property="circleid" name="DPInterSSDStockTransferReportFormBean" style="width:150px;" size="3"
							 disabled="true"  onchange="javascript:getTsmName();selectAllCircles()" onblur=""  multiple="true">
							
							<logic:notEmpty name="DPInterSSDStockTransferReportFormBean" 
								property="circleList">
							<html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="circleList" label="strText" value="strValue"/>
						</logic:notEmpty>
						</html:select>
						</td>
						</logic:match>
			</tr><tr>
			<logic:match value="A" property="showTSM" name="DPInterSSDStockTransferReportFormBean">		
					<TD align="center"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" styleId="tsmId" style="width:150px;" size="3" onblur="" onchange="getDistName();selectAllTSM();">
							<logic:notEmpty name="DPInterSSDStockTransferReportFormBean" property="tsmList">
							 <html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</logic:match>
					<logic:match value="N" property="showTSM" name="DPInterSSDStockTransferReportFormBean">		
					<TD align="center"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td><td>
					<html:select property="tsmId"  style="width:150px;" size="3" styleId="tsmId" disabled="true" onblur="" onchange="javascript:getDistName();selectAllTSM();" name="DPInterSSDStockTransferReportFormBean"  multiple="true">
							<logic:notEmpty property="tsmList" name="DPInterSSDStockTransferReportFormBean">
						<html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="tsmList" label="strText" value="strValue"/>
						</logic:notEmpty>
						</html:select>
						</td>
						</logic:match>
				</TR>
				
				
					<TR>
				<logic:match value="A" property="showDist" name="DPInterSSDStockTransferReportFormBean">		
					<TD align="center"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="distId" styleId="distId" style="width:150px;" size="3" onchange="selectAllDist();" >
							<logic:notEmpty name="DPInterSSDStockTransferReportFormBean" property="distList">
								 <html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				<logic:match value="N" property="showDist" name="DPInterSSDStockTransferReportFormBean">		
					<TD align="center"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="distId"  disabled="true" styleId="distId" style="width:150px;" size="3" onchange="selectAllDist();" >
							<logic:notEmpty name="DPInterSSDStockTransferReportFormBean" property="distList">
								 <html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				</tr><tr>
				<TD align="center" height="46" width="180">
						<b class="text pLeft15">Transfer Type<font color="red">*</font></b>
					</td>
					<TD >				   
						<html:select property="trnsfrType" name="DPInterSSDStockTransferReportFormBean" styleId="trnsfrType" style="width:150px;">
							<html:option value="">- Select Transfer Type -</html:option>
							<option value="0">Received</option>
							<option value="1">Sent</option>
							<option value="2">Both</option>
						</html:select>
					</TD>
				</TR>	
				
				
				<TR>
					<td align="center" height="46" width="180">
				<b class="text pLeft15"><bean:message bundle="view" key="report.dcno" /></b></td>
							<td width="155" align="left">
							<html:text property="dcno" styleId="dcno" styleClass="box" 
						 name="DPInterSSDStockTransferReportFormBean"/>
						
							<td>
				</TR>
				<TR>
					
					<TD align="center" height="46" width="180">
						<b class="text pLeft15"><bean:message bundle="view"
						key="report.dateOption" /></b>
					</td>
					<TD >				   
						<html:select property="dateOption"  style="width:150px" onchange="javascript:showCalender()" name="DPInterSSDStockTransferReportFormBean" >
							<html:option value="-1">-Blank-</html:option>
							<option value="INITIATION_DATE">Initiation Date</option>
							<option value="TRANSFER_DATE">Transfer Date</option>
							<option value="ACCEPTANCE_DATE">Acceptance Date</option>
						</html:select>
					</TD>
					</TR>
					<tr><TD align="center" height="46" width="180">
						<b class="text pLeft15">Product Type<font color="red">*</font></b>
						
					</td>
					 <TD >				   
						<html:select property="productId"  style="width:150px;" size="3" styleId="dcProductCategListDTO" name="DPInterSSDStockTransferReportFormBean" onchange="selectAllProducts()" multiple="true">
							<html:option value="-1">-All-</html:option>
							<logic:notEmpty property="dcProductCategListDTO" name="DPInterSSDStockTransferReportFormBean">
						
						<html:optionsCollection name="DPInterSSDStockTransferReportFormBean"	property="dcProductCategListDTO" label="productCategory" value="productCategoryId"/>
						 </logic:notEmpty>
						</html:select>
						
					</TD>  
				</tr>
				<TR id="calId"  style="display: none;">
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155" height="50"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="DPInterSSDStockTransferReportFormBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'DPInterSSDStockTransferReportFormBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
			
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155" height="50"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="DPInterSSDStockTransferReportFormBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'DPInterSSDStockTransferReportFormBean',
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
	 <html:hidden property="methodName" styleId="methodName" value="exporttoExcel"  />
	
			</table>
		</td>
		</TR>
		</html:form>
		
	
	
</TABLE>

</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>