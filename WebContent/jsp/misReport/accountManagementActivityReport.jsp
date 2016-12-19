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
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
<script language="javascript" src="scripts/calendar1.js">
</script>
<script language="javascript" src="scripts/Utilities.js">
</script>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" type="text/javascript">

 function isDate2Greater(date1, date2){

		var start = new Date(date1);
		var end = new Date(date2);
		var today=new Date();
		var flag=true;
		var diff =end - start;
		//alert("today - start :"+(today -start));
		//alert("today date :"+new Date());
	//alert("diff :"+diff);
		if (diff <0){
			alert("From Date Should Be Smaller Than To Date");
			flag= false;
		}
		var sysDate = new Date();
		if(start > sysDate){
			alert("From Date Cannot Be More Than Today");
			flag= false;
		}
		if(end > sysDate){
			alert("To Date Cannot Be More Than Today");
			flag= false;
		}
		
		if((today -start) > 31605598390 )
		{
		alert("From date connot be older than 1 yrs from today");
		flag= false;
		}
		if(diff > 2678400000 )
		{
		alert("Report can be generated only for 31 days");
		flag= false;
		}
		//alert("Returning from isDate2Greater flag ="+flag);
		return flag;
		
	} 
function validate(){
		date1=document.getElementById("startDt").value;
		date2=document.getElementById("endDt").value;
		
		 if(date1 != "" && date2 == "")
		{
			alert("Please enter To Date");
			return false;
		}
		else if(date2 != "" && date1 == "")
		{
			alert("Please enter From Date");
			return false;
		}
		else if(date1 == "" && date2 == "")
		{
		alert("Please enter From Date and to Date");
			return false;
		}
		 else if (!(isDate2Greater(date1, date2))){	  
			return false;
		}
		else
		{
	    return true;
	    }
	} 
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
	
function exportToExcel() {

if(document.getElementById("hiddenAction").value=="")
{
document.getElementById("hiddenAction").value=",";
}
//alert(document.getElementById("startDt").value);
//alert(document.getElementById("endDt").value);
	var skipValidation = false
	var flag=true;
		var searchOption =document.getElementById("searchOption").value;
		//alert("searchOption :"+searchOption);
		if(searchOption != "-1"){
		var searchCriteria = document.getElementById("searchCriteria").value;
					if ((searchCriteria==null)||(searchCriteria=="0") || searchCriteria==""){
					alert('Search Criteria is Required');
					return false;
				}	
			
		if(document.forms[0].showCircle.value=="A" && !skipValidation){
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	   		
	   		if(document.forms[0].accountType.value==""){
				alert("Please Select atleast one Account Type");
				return false;
	   		}
	   		
	   }
	}
	else
	{
		if(document.forms[0].showCircle.value=="A" && !skipValidation){
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	   		
	   		if(document.forms[0].accountType.value==""){
				alert("Please Select atleast one Account Type");
				return false;
	   		}
	   		
	   }
	   
	   /*if(document.forms[0].action.value==""){
			alert("Please Select atleast one Action");
			return false;
	   		}*/
	   		
	   /*flag=validate();
	  	//alert("Flag :"+flag);
	  	if(flag)
	  	{
	  	flag = true;
	  	}
	  	else
	  	{
	  	flag = false;
	  	}*/
	  		
	}
	
  	if(document.forms[0].action.value!="")
  	{
  		//alert("in if");
  		if(searchOption == 'APPROVER' || searchOption == '-1') //USERNAME SRNO
  		{
  		//alert("searchOption 11111 :"+searchOption);
	  	flag=validate();
	  	}
	  	//alert("Flag :"+flag);
	  	if(flag)
	  	{
	  	flag = true;
	  	}
	  	else
	  	{
	  	flag = false;
	  	}
  	}
  	else
  	{
  	alert("Please select an action");
  	flag=false;
  	}
  	if(flag==true)
  	{
  //	alert("=== Submiting form  now ===");
    document.getElementById("methodName").value="exportExcel"; 
    setInterval(getReportDownloadStatus,1000);
	document.forms[0].submit();
  	}
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
   		 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}

function setSelectedCircles() 
{

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
   		
 }
	//alert("selectedCircleValues :"+selectedCircleValues);	
}


function setSelectedAccountType() 
{

	   var accountType = document.getElementById("accountType").value;
		var selectedAccountType ="";
		var selectedAccountTypeIds="";
	
	if(accountType ==""){
		alert("Please Select atleast one Account Type");
		return false;
	 }
	 if(document.forms[0].accountType.length ==1){
			alert("No Account Type exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].accountType[0].selected){
		
			for (var i=1; i < document.forms[0].accountType.length; i++)
  	 		{
   			
     		selectedAccountTypeIds = document.forms[0].accountType[i].value;
     		
     		if(selectedAccountTypeIds!=""){
	     	 selectedAccountType =selectedAccountType +","+ selectedAccountTypeIds;
	     	 }
	     	 selectedAccountTypeIds="";
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].accountType.length; i++)
	  	 {
	   		 if (document.forms[0].accountType[i].selected)
	     	 {
	     	
	     		selectedAccountTypeIds = document.forms[0].accountType[i].value;
     		}
	     	 if(selectedAccountTypeIds!=""){
	     	 selectedAccountType =selectedAccountType +","+ selectedAccountTypeIds;
	     	 }
	     	 selectedAccountTypeIds="";
	   	}
   }
   	 	document.getElementById("hiddenAccountType").value =selectedAccountType;
   		
 }
	//alert("selectedAccountTypevalues :"+selectedAccountType);
	
	//alert("document.getElementById('hiddenAccountType').value :"+document.getElementById("hiddenAccountType").value);	
}


function setSelectedAction() 
{
		//alert("in setSelectedAction");
	   var action = document.getElementById("action").value;
		var selectedActionValues ="";
		var selectedActionIds="";
	//(action ==""  )
	if(action=="" || action==null )
	{
		alert("Please Select atleast one Action");
		return false;
	 }
	 if(document.forms[0].action.length ==1){
			alert("No Action exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].action[0].selected){
		
			for (var i=1; i < document.forms[0].action.length; i++)
  	 		{
   			
     		selectedActionIds = document.forms[0].action[i].value;
     		
     		if(selectedActionIds!=""){
	     	 selectedActionValues =selectedActionValues +","+ selectedActionIds;
	     	 }
	     	 selectedActionIds="";
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].action.length; i++)
	  	 {
	   		 if (document.forms[0].action[i].selected)
	     	 {
	     	
	     		selectedActionIds = document.forms[0].action[i].value;
     		}
	     	 if(selectedActionIds!=""){
	     	 selectedActionValues =selectedActionValues +","+ selectedActionIds;
	     	 }
	     	 selectedActionIds="";
	   	}
   }
   	 	document.getElementById("hiddenAction").value =selectedActionValues;
   		
 }
	//alert("selectedActionValues :"+selectedActionValues);
	
	//alert("document.getElementById('hiddenAction').value :"+document.getElementById("hiddenAction").value);	
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
			for (var i=0; i < document.forms[0].circleId.length; i++){
  	 			document.forms[0].circleId[i].selected =true;
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

function selectAllAction(){
		var ctrl = document.forms[0].action;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].action[0].selected){
			for (var i=1; i < document.forms[0].action.length; i++){
  	 			document.forms[0].action[i].selected =true;
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
}
</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form name="AccountManagementActivityReportBean" action="/accountManagementActivityReport" type="com.ibm.dp.beans.AccountManagementActivityReportBean"  method="post"  enctype="multipart/form-data" >
	<html:hidden property="showCircle" name="AccountManagementActivityReportBean" />
	<html:hidden property="showTSM" name="AccountManagementActivityReportBean" />
	<html:hidden property="showDist" name="AccountManagementActivityReportBean" />
	<html:hidden property="methodName" styleId="methodName" />
	
	<html:hidden property="hiddenCircleSelecIds" name="AccountManagementActivityReportBean" />
	<html:hidden property="hiddenTsmSelecIds" name="AccountManagementActivityReportBean" />
	<html:hidden property="hiddenDistSelecIds" name="AccountManagementActivityReportBean" />
	<html:hidden property="hiddenPoStatusSelec" name="AccountManagementActivityReportBean" />
	<html:hidden property="hiddenAccountType" name="AccountManagementActivityReportBean" />
	<html:hidden property="hiddenAction" name="AccountManagementActivityReportBean" />
	
	
	<TR valign="top">
		
		<td>
			<TABLE width="800" border="0" cellpadding="5" cellspacing="0" class="text"  align="top">
				<TR>
					<TD colspan="4" valign="bottom"  class="dpReportTitle">
								Account Management Activity Report
					</TD>
		
				</TR>
			<TR>
				<logic:match value="A" property="showCircle" name="AccountManagementActivityReportBean">	
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td height="46">	
						
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px;" size="6" onchange="setSelectedCircles();selectAllCircle();">
							<logic:notEmpty name="AccountManagementActivityReportBean" property="circleList">
								 <html:optionsCollection name="AccountManagementActivityReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
						<TD colspan="2"></TD>
			</logic:match>
				</TR>
			
				
				<TR align="center">
					<TD align="center"><b class="text pLeft15"> Account Type <font color="red">*</font></b>
					</TD>
					<TD class="txt" align="left"  width="275" >
					<html:select multiple="true" size="6" onchange="setSelectedAccountType();selectAllAccountType();"
						property="accountType" style="width:150px" styleId="accountType"
						name="AccountManagementActivityReportBean">
					<logic:notEmpty name="AccountManagementActivityReportBean" property="accountTypeList">								
								 <html:optionsCollection name="AccountManagementActivityReportBean"	property="accountTypeList" label="value" value="id"/> 
					</logic:notEmpty>

					</html:select></TD>

					
					
					<TD class="txt" align="left"  width="275" >
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
					<!--<TD align="center"><b class="text pLeft15">Status<font color="red">*</font></b>
					</TD>
					
					 <TD class="txt" align="left"  width="275" ><html:select
						property="status" style="width:150px" styleId="status"
						name="AccountManagementActivityReportBean">
						<html:option value="0">
							<bean:message bundle="view" key="report.option.all" />
						</html:option>
						<html:option value="1">
							<bean:message bundle="view" key="report.active" />
						</html:option>
						<html:option value="2">
							<bean:message bundle="view" key="report.inactive" />
						</html:option>
					</html:select></TD>-->
				</TR>
				
				
				<TR>
					<TD align="center"><b class="text pLeft15"> Action  <span style="display:none"><font color="red">*</font></span></b>
					</TD>
					<TD>	
						<html:select multiple="true" size="6" property="action" styleId="action" style="width:150px;" onchange="setSelectedAction();selectAllAction();">
							<html:option value="-1">-All-</html:option>
							<html:option value="CREATE">Create</html:option>
							<html:option value="EDIT">Edit</html:option>
							<html:option value="INACTIVE">Inactive</html:option>
						</html:select>
					</TD>
				</TR>
				
				
				<TR>
					<TD align="center"><b class="text pLeft15"> Search Option  <span style="display:none"><font color="red">*</font></span></b>
					</TD>
					<TD>	
						<html:select  property="searchOption" styleId="searchOption" style="width:150px;" onchange="showSearch();">
							<html:option value="-1">-Blank-</html:option>
							<html:option value="SRNO">Sr No</html:option>
							<html:option value="USERNAME">User Name</html:option>
							<html:option value="APPROVER">Approver</html:option>
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
								<html:text property="searchCriteria" styleId="searchCriteria" name="AccountManagementActivityReportBean"></html:text>
							</div>
						</TD>
				</TR>
				<TR>
					
						<TD align="center"><b class="text pLeft15"><bean:message
						bundle="view" key="application.start_date" /></b>
						<html:text property="startDt" styleId="startDt" styleClass="box"
						readonly="true" size="15" maxlength="10" value="" /> 
						<a onclick="javascript:showCal('CalendarUp13')">
						<img src="images/calendar.gif" width='16' height='16' 
						alt="Click here to choose the date" title="Click here to choose the date" border='0'></a>
						</TD>
						
						<TD align="center"><b class="text pLeft15"><bean:message
						bundle="view" key="application.end_date" /></b>
						<html:text property="endDt" styleId="endDt" styleClass="box" readonly="true"
						size="15" maxlength="10" value=" "/>
						<a onclick="javascript:showCal('CalendarUp14')">
						<img src="images/calendar.gif" width='16' height='16' 
						alt="Click here to choose the date" title="Click here to choose the date" border='0'></a>
						</TD>
						
				</TR>
				
				
				<tr>
					<td colspan="2" align="center">
						<html:button property="excelBtn" value="Account Management Activity Report" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				
				</tr>
	 
			</table>
		</td>
		</TR>
		</html:form>
		
	
	
</TABLE>
<input type="hidden" name="reportDownloadStatus" value="">
<script>
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	
	var url= "accountManagementActivityReport.do?methodName=getReportDownloadStatus";
	
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

</script>
</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>