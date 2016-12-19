<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
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

<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>
<script>
function getReportData(){
	var url="reportAction.do?methodName=getReportData&reportId="+document.getElementById("reportId").value;
//	document.forms[0].action=url;
//	document.forms[0].method="post";
	var reportId = document.getElementById("reportId").value;
	if(reportId=="" || reportId==null || reportId.length==0)
		return false;
	//var url = "viewStockBatchDetails.do?methodName=viewBatchDetails&Id="+Id+"&cond="+cond;
	var url="reportAction.do?methodName=getReportData&reportId="+reportId;
	window.open(url,"report","width=950,scrollbars=yes,toolbar=no");
	//document.forms[0].action=url;
	//document.forms[0].method="post";
	//document.forms[0].submit();
}

function exportToExcel(){
	var url="reportAction.do?methodName=getReportDataExcel";
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
}
</script>

<html:form action="/initReportAction.do?methodName=initReportAction" >
<br>
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/dpReports.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
<table align="center" border="0" class ="border">
	<tr>
		<td colspan="2" width="232">
			<font color="red"><b><html:errors/></b></font>
		</td>
	</tr>
<logic:messagesPresent message="true">
	<tr>
		<html:messages id="msg" property="INSERT_SUCCESS" bundle="hboView" message="true">
			<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
		</html:messages>
	</tr>
	<tr>
		<html:messages id="msg1" property="ERROR_OCCURED" bundle="hboView" message="true">
			<font color="red" size="2"><strong><bean:write name="msg1"/></strong></font>
		</html:messages>
	</tr>
</logic:messagesPresent>
  	<tr>
  				<TD class="txt" width="155"><STRONG><FONT 
  				color="#000000">Report</FONT></STRONG><font color="red">*</font>:</td>
				<TD>
				<html:select property="reportId" styleClass="box" onchange="getReportData()">
					<option value="">--Select a report--</option>
				<logic:notEmpty property="reportNamesList" name="DPReportForm">
				<bean:define id="report" name="DPReportForm" property="reportNamesList" />
					<html:options labelProperty="reportName" property="reportId"
						collection="report" />
				</logic:notEmpty>	
				</html:select>
				</TD>
  	</tr>
  	 <tr>
	  <%--	<td align="right" width="155">
	  	
	  	<html:submit value="Submit" styleClass="medium" /></td>
		<td align="left" colspan="2" height="15" width="232"><input type="reset"
				value="Reset">
		</td> --%>
	</tr>
 </table>
	<BR>
	<BR>
	<%int i=1; %>
 <logic:notEmpty property="reportDataList" name="DPReportForm">	
 	<table align="center">
 	<tr><td><html:button property="excelBtn" value="Export To Excel" onclick="exportToExcel();"></html:button></td> 
 	</tr>
 	<logic:iterate id="reportData" name="DPReportForm" property="reportDataList" indexId="m">
			<%if(i==1){%>
			<TR bgcolor="red">
			<%}else{ %>
			<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
			<%} %>
				<c:forEach begin="1" end="${DPReportForm.noOfColumns}" step="1" var="counter">
					<td><bean:write name="reportData" property="field${counter+5}"/></td>
				</c:forEach>
			</TR>
			<%i++; %>
	</logic:iterate>
 	</table>
 </logic:notEmpty>
</html:form>
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>