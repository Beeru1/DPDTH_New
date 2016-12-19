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
	  	
	  	
	  	if(document.getElementById("poStatus").value ==""){
				alert("Please Select atleast one PO Status");
				return false;
	   		}
	   		var selectedPOStatusValues = "";
	   		if (document.forms[0].poStatus[0].selected){
		
			for (var i=1; i < document.forms[0].poStatus.length; i++)
  	 		{
   		
     			if(selectedPOStatusValues !=""){
					selectedPOStatusValues += ",";
     			}
     		var selectedValPo = document.forms[0].poStatus[i].value.split(",");
     		selectedPOStatusValues += selectedValPo[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].poStatus.length; i++)
	  	 	{
	   		 if (document.forms[0].poStatus[i].selected)
	     	 {
	     		if(selectedPOStatusValues !=""){
					selectedPOStatusValues += ",";
     			}
	     	var selectedValPo = document.forms[0].poStatus[i].value.split(",");
     		selectedPOStatusValues += selectedValPo[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenPoStatusSelec").value =selectedPOStatusValues;
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
	    var searchCriteris = document.getElementById("searchCriteria").value;
	    var option =document.getElementById("searchOption").value;
	     if(option !="-1"){
			    if(searchCriteris == ""){
			    	alert("Please Enter Search Criteria");
			    	return false;
			    }
			 }
	    // document.forms[0].methodName.value = "exportExcel";
		document.forms[0].action = "poDetailReport.do?methodName=exportExcel";
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
		document.getElementById("searchId").style.display = 'none';
		document.getElementById("searchValue").style.display = 'none';		
	}else{
		document.getElementById("searchId").style.display = 'inline';
		document.getElementById("searchValue").style.display = 'inline';
	}
}
</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form name="PODetailReportBean" action="/poDetailReport" type="com.ibm.dp.beans.PODetailReportBean"  method="post"  enctype="multipart/form-data" >
	<html:hidden property="showCircle" name="PODetailReportBean" />
	<html:hidden property="showTSM" name="PODetailReportBean" />
	<html:hidden property="showDist" name="PODetailReportBean" />
	<html:hidden property="methodName" styleId="methodName" />
	
	<html:hidden property="hiddenCircleSelecIds" name="PODetailReportBean" />
	<html:hidden property="hiddenTsmSelecIds" name="PODetailReportBean" />
	<html:hidden property="hiddenDistSelecIds" name="PODetailReportBean" />
	<html:hidden property="hiddenPoStatusSelec" name="PODetailReportBean" />
	
	<TR valign="top">
		
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="${pageContext.request.contextPath}/images/PODetailReport.jpg"
						width="410" height="24" alt=""></TD>
		
				</TR>
			<TR>
				<logic:match value="A" property="showCircle" name="PODetailReportBean">	
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px;" size="6" onchange="getTsmName();">
							<logic:notEmpty name="PODetailReportBean" property="circleList">
								 <html:optionsCollection name="PODetailReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			</logic:match>
			<logic:match value="A" property="showTSM" name="PODetailReportBean">		
					<TD align="center"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" styleId="tsmId" style="width:150px;" size="6" onchange="getDistName();selectAllTSM()">
							<logic:notEmpty name="PODetailReportBean" property="tsmList">
							 <html:optionsCollection name="PODetailReportBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</logic:match>
				</TR>
				
				
					<TR>
				<logic:match value="A" property="showDist" name="PODetailReportBean">		
					<TD align="center"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="distId" styleId="distId" style="width:150px;" size="6" onchange="selectAllDist();" >
							<logic:notEmpty name="PODetailReportBean" property="distList">
								 <html:optionsCollection name="PODetailReportBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				<TD align="center"><b class="text pLeft15"> PO Status<font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="poStatus" styleId="poStatus" style="width:150px;" size="6" onchange="selectAllPoStatus();">
							
							<html:option value="-1">-All Status-</html:option>
							<logic:notEmpty name="PODetailReportBean" property="poStatusList">
								 <html:optionsCollection name="PODetailReportBean"	property="poStatusList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>	
				
				
				<TR>
					<TD align="center"><b class="text pLeft15"> Date Option<font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  property="dateOption" styleId="dateOption" style="width:150px;" onchange="showCalender();">
							<html:option value="-1">-Blank-</html:option>
							<html:option value="PO_CREATE_DATE">PO Create Date</html:option>
							<html:option value="DISPATCH_DATE">Dispatch Date</html:option>
							<html:option value="PO_ACCEPTANCE_DATE">PO Acceptance Date</html:option>
							
						</html:select>
					
						
						</TD>
				</TR>
				
				<TR id="calId" style="display: none;">
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="PODetailReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'PODetailReportBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
			
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="PODetailReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'PODetailReportBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
	
						 </FONT>
					</TD>
					
					
				</TR>
				<TR>
					<TD align="center"><b class="text pLeft15"> Search Option<font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  property="searchOption" styleId="searchOption" style="width:150px;" onchange="showSearch();">
							<html:option value="-1">-Blank-</html:option>
							<html:option value="PR_NO">PR No.</html:option>
							<html:option value="PO_NO">PO No.</html:option>
							<html:option value="DC_NO">DC No.</html:option>
							
						</html:select>
					
						
						</TD>
						<TD align="center" >
							<div id="searchId" style="display: none;">
								<b class="text pLeft15"> Search Criteria<font color="red">*</font></b>
							<div/>
						</TD>
						
						<TD>
							<div id="searchValue" style="display: none;">	
								<html:text property="searchCriteria" styleId="searchCriteria" name="PODetailReportBean"></html:text>
							</div>
						</TD>
				</TR>
				
				<tr>
					<td colspan="2" align="center">
						<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	 
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