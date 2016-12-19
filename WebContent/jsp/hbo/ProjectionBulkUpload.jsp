
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<HEAD>
<%@ page  
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>Projection Bulk Upload </TITLE>
<script language="JavaScript">
function validate()
{
	var mth = document.getElementById("month").value;
	var yr = document.getElementById("year").value;
	var myDate=new Date();
	var nxtyr=myDate.getFullYear()+1;
	var thisyr=myDate.getFullYear();
	var date = myDate.getDate();
	var month=myDate.getMonth()+1;
	if(document.forms[0].circle.value==""){
		alert("Please Select A Circle");
		document.forms[0].thefile.focus();
		return false;
	}
	if(document.forms[0].thefile.value	=="")
	{
		alert("Please Select A File");
		document.forms[0].thefile.focus();
		return false;
	}	
    if(document.forms[0].thefile.value.lastIndexOf(".csv")==-1)
	{
		alert("Please Upload Only .csv Extention File");
		document.forms[0].thefile.focus();
		return false;
	}
	if (document.forms[0].role.value==2 && date>10)
	{
		alert("Projection Can Be Done Before 11th Of Each Month");
		return false;
	}
	if ((mth==11 && nxtyr==yr)||(mth==12 && nxtyr==yr))
	{
	alert("Please Check Year");
	return false;
	}
	if (((month==11||month==12) && mth==1 && thisyr==yr)||((month==11||month==12) && mth==2 && thisyr==yr))
	{
	alert("Please Check Year");
	return false;
	}
	if(document.forms[0].month.value==""){
		alert("Please Select A Month");
		document.forms[0].thefile.focus();
		return false;
	}
	if(document.forms[0].year.value==""){
		alert("Please Select Year");
		document.forms[0].thefile.focus();
		return false;
	}
	return true;
}
</script>
</HEAD>
<BODY>
<html:form action="/projectionBulkUpload.do?methodName=insertBulkProjection" enctype="multipart/form-data"  onsubmit="return validate();" >
<html:hidden property="role" name="HBOProjectionForm"/>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/bulkProjection.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE border="0" align="center" class ="border">
		<TBODY>
			<tr>
				<logic:messagesPresent message="true">
					<p align="center"><html:messages id="msg" property="UPLOAD_SUCCESS" bundle="hboView" message="true">
					   <b><font color="red" size="2"><strong><bean:write name="msg"/></strong></font></b>
				   	</html:messages>
				   	<html:messages id="msg1" property="ERROR_OCCURED" bundle="hboView" message="true">
					   <b><font color="red" size="2"><strong><bean:write name="msg"/></strong></font></b>
				   	</html:messages>
				   	<html:messages id="msg2" property="INSERT_FAILED" bundle="hboView" message="true">
					   <b><font color="red" size="2"><strong><bean:write name="msg"/></strong></font></b>
				   	</html:messages></p>
				</logic:messagesPresent>
			</tr>
			<TR>
				<TD width="222"></TD>
				<TD width="161"></TD>
			</TR>
	
			<TR>
			<TD><bean:message key="projectionBulkUpload.circle" bundle="hboView"/><font color="red">*</font> </TD>
			<TD>
				<html:select property="circle" style="width:200px">
				<html:option value="">--Select A Circle--</html:option>
				<logic:notEmpty property="arrCircle" name="HBOProjectionForm">
					<bean:define id="circle" name="HBOProjectionForm" property="arrCircle" />
					<html:options labelProperty="circlename" property="circle" collection="circle" />
				</logic:notEmpty>			
				</html:select>
				
				</TD>
			</TR>
			<TR>
				<TD width="235"><bean:message key="projectionBulkUpload.selectFile" bundle="hboView"/><font color="red">*</font> </TD>
				<TD width="184" class="txt"><html:file property="thefile" name="HBOProjectionForm" /></TD>
				
			</TR>
			<TR>
			<TD><bean:message key="projectionBulkUpload.month" bundle="hboView"/><font color="red">*</font> </TD>
			<TD>
				<html:select property="month" style="width:200px">
				<html:option value="">--Select Month--</html:option>
				<logic:notEmpty property="arrMonth" name="HBOProjectionForm">
					<bean:define id="month" name="HBOProjectionForm" property="arrMonth" />
					<html:options labelProperty="monthName" property="monthId" collection="month" />
				</logic:notEmpty>
				</html:select>
				
				</TD>
			</TR>
			<TR>
			<TD><bean:message key="projectionBulkUpload.year" bundle="hboView"/><font color="red">*</font> </TD>
				<TD>
					<html:select property="year" style="width:200px">
						<html:option value="">--Select Year--</html:option>
						<logic:notEmpty property="arrYear" name="HBOProjectionForm">
							<bean:define id="year" name="HBOProjectionForm" property="arrYear" />
							<html:options labelProperty="nextYear" property="nextYear" collection="year" />
						</logic:notEmpty>
					</html:select>
				</TD>
			</TR> 
			<TR>
				<TD width="235" align="right"></TD>
				<TD width="321"><html:reset styleClass="medium"></html:reset><html:submit styleClass="medium" value="Submit" ></html:submit></TD>
			</TR>
		</TBODY>
	</TABLE>
</html:form>
</BODY>
</html:html>
