<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
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
<script language="JavaScript">

function validate(){
	if(document.getElementById("reportType").value=="0"){
		alert("Select A Report Type");
		return false;
	}
	else if(document.getElementById("startDt").value==null || document.getElementById("startDt").value==""){
		alert("Select The Start Date");
		return false;
	}
	else if(document.getElementById("endDt").value==null || document.getElementById("endDt").value==""){
		alert("Select The End Date");
		return false;
	}
	else if(!(isDate2Greater(document.getElementById("startDt").value, document.getElementById("endDt").value))){
		return false;
	}
	else if(!(isDateDiff3(document.getElementById("startDt").value, document.getElementById("endDt").value))){
		return false;
	}
	else if(document.getElementById("category").value==""){
		alert("Select A Business Category");
		return false;
	}
	else if((!document.getElementById("category").value=="A") || document.getElementById("product").value==""){
		alert("Select A Product");
		return false;
	}
	exportDataToExcel();
	
}

// Function to comapare to and from date
function isDate2Greater(date1, date2){
		var start = new Date(date1);
		var end = new Date(date2);
		var diff =end - start;
		if (diff <0){
			alert("From Date Should Be Smaller Than To Date");
			return false;
		}
		var sysDate = new Date();
		if(start > sysDate){
			alert("From Date Cannot Be More Than Today");
			return false;
		}
		if(end > sysDate){
			alert("To Date Cannot Be More Than Today");
			return false;
		}
		return true;
	} 
	
	
	function isDateDiff3(date1, date2){
		var start = new Date(date1);
		var end = new Date(date2);
		var diff =(end - start)/(24*3600*1000);
		if (diff >2){
			alert("System Cannot Generate Report For A Period Of More Than 3 Days");
			return false;
		}
		var sysDate = new Date();
		if(start > sysDate){
			alert("From Date Cannot Be More Than Today");
			return false;
		}
		if(end > sysDate){
			alert("To Date Cannot Be More Than Today");
			return false;
		}
		return true;
	}
	
function prodList(){
	var Index = document.getElementById("category").selectedIndex;
	var element=document.getElementById("category").options[Index].value;
	var prodCategory=document.getElementById("category").value;
	var url= "initAssignADStock.do?methodName=getProdList&cond1="+prodCategory;
	var elementId = "product" ;
	ajax(url,elementId);
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

function getAjaxValues(elementId)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null)
		{		
			var selectObj = document.getElementById(elementId);
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option(" -Select A "+ elementId + "-", "");
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById(elementId);
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option("--Select A "+ elementId + "--", "");
		selectObj.options[selectObj.options.length] = new Option("-- All --", "A");		
		for(var i=0; i<optionValues.length; i++){
		   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}
	}
}
function exportDataToExcel1(){
	var elementId = "product" ;
	var fromDt = document.getElementById("startDt").value;
	var toDt = document.getElementById("endDt").value;
	var busCat = document.getElementById("category").value;
	var product = document.getElementById("product").value;
	var reportType = document.getElementById("reportType").value;
	var url = "initAssignADStock.do?methodName=downloadAssignedStock&fromDt="+fromDt+"&toDt="+toDt+"&busCat="+busCat+"&product="+product+"&reportType="+reportType;
	//document.forms[0].export.disabled=true;
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
		getAjaxTextValues(elementId);
	}
	req.open("POST",url,false);
	req.send();
}
function getAjaxTextValues(elementId)
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var text=req.responseText;
		 if(text!=null){

		}
		else
		document.getElementById(elementId).value = "0" ; 
	}
}

	/* This function lets the user to download the displayed rows. */
	function exportDataToExcel(){
		var url = "initAssignADStock.do?methodName=downloadAssignedStock";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();

	}
	
</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();">

<TABLE 
	valign="top" align="center">
	<TR valign="top">
		<TD valign="top">
		<html:form action="/initAssignADStock.do">

			<TABLE border="0" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewStockReport.gif"
						width="505" height="22" alt=""></TD>
				</TR>

				<TR>
				</TR>

				<TR>
					<TD colspan="4" align="center"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" property="error.account" /></FONT> <FONT color="#ff0000"
						size="-2"><html:errors bundle="view"
						property="message.account" /></FONT></TD>
					<input type="hidden" name="methodName" styleId="methodName" />
				</TR>
				<tr align="center">
					<TD align="right"><STRONG> <FONT
						color="#000000"> <bean:message bundle="view"
						key="report.type" /></FONT> <FONT color="RED">*</FONT>: </STRONG></TD>
					<TD align="left" width="275"><FONT color="#003399"> <html:select
						property="reportType" tabindex="27" styleClass="w130">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="1">
							<bean:message key="report.1" bundle="view" />
						</html:option>
						<html:option value="2">
							<bean:message key="report.2" bundle="view" />
						</html:option>
					</html:select> </FONT></TD>
				</tr>

				<TR align="center">
					<TD align="right"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="application.start_date" /></FONT><font color="red">*</font>:</STRONG></TD>
					<TD align="left" width="275"><html:text
						property="startDt" styleId="startDt" styleClass="box"
						readonly="true" size="15" maxlength="10" /> 
						<a onclick="javascript:showCal('CalendarUp7')">
						<img src="images/calendar.gif" width='16' height='16' 
						alt="Click here to choose the date" title="Click here to choose the date" border='0'></a></TD>
				</TR>
				<tr align="center">
					<td align="right"><strong><font color="#000000"><bean:message bundle="view"
						key="application.end_date" /></font><font color="red">*</font>:</strong></td>
					<td align="left"> <html:text property="endDt"
						styleId="endDt" styleClass="box" readonly="true" size="15"
						maxlength="10" /> <a onclick="javascript:showCal('CalendarUp8')">
					<img src="images/calendar.gif" width='16' height='16'
						alt="Click here to choose the date"
						title="Click here to choose the date" border='0'></a></td>
				</tr>
				<tr align="center">
					<TD class="txt" align="right"><strong><font color="#000000"><bean:message bundle="hboView" key="assignADStock.Category"/></font><font color="red">*</font>:</strong></TD>
					<TD class="txt" align="left" width="275"><html:select
						property="category" onchange="prodList();" style="width:230px">
						<logic:notEmpty name="AssignDistStockForm" property="arrCategory">
							<bean:define id="category" name="AssignDistStockForm"
								property="arrCategory" />
							<html:option value="">--Select A Category--</html:option>
							<html:option value="A">
								<bean:message bundle="view" key="application.option.select_all" />
							</html:option>
							<html:options labelProperty="bname" property="bcode"
								collection="category" />
						</logic:notEmpty>
					</html:select></TD>
				</tr> 
				<tr align="center">
					<TD class="txt" align="right"><strong><font color="#000000"><bean:message bundle="hboView" key="assignADStock.Product"/></font><font color="red">*</font>:</strong></TD>
					<TD class="txt" align="left" width="275">
					<html:select property="product" style="width:230px">
						<html:option value="">--Select A Product--</html:option>
						<html:option value="A">
							<bean:message bundle="view" key="application.option.select_all" />
						</html:option>
					</html:select>
					</TD>
				</tr>
			</TABLE>
			<br>
			<TABLE align="center" class="mLeft5">
					<TR>
						<TD ><input type="button" name="export"
							value="Export to CSV" tabindex="8" onclick="validate();"/></TD>
							
					</TR>
				<input name="distId" type="hidden"/>	
			</TABLE>
		</html:form></TD>
	</TR>
</TABLE>
</BODY>
</html>