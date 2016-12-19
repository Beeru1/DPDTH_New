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
<LINK href="<%=request.getContextPath()%>/theme/style.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE>Report Dump</TITLE>

<script>
function exportToExcel(){
	var url="reportActionExcel.do?methodName=getReportData&reportId=1";
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
}
</script>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>

<table>
<tr>
	<TD class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/dumpReport.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<tr>
<td align="center">	
	<logic:messagesPresent message="true">		
		<html:messages id="msg" property="NO_RECORD" bundle="hboView" message="true">
			<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
		</html:messages>
	</logic:messagesPresent>
</td>
</tr>

</table>

	<html:form action="/initReportAction.do">
	<logic:notEmpty name="DPReportForm" property="reportDataList">
	<br><br>
		<table width="98%">
				<tr bgcolor="#CD0504" style="color:#ffffff !important;">
					<td align="center"><bean:message bundle="view" key="report.hub" /></td>
					<td align="center"><bean:message bundle="view" key="report.circle" /></td>
					<td align="center"><bean:message bundle="view" key="report.zone" /></td>
					<td align="center"><bean:message bundle="view" key="report.distname" /></td>
					<td align="center"><bean:message bundle="view" key="report.retcode" /></td>
					<td align="center"><bean:message bundle="view" key="report.retname" /></td>
					<td align="center"><bean:message bundle="view" key="report.prodcat" /></td>
					<td align="center"><bean:message bundle="view" key="report.prodtype" /></td>
					<td align="center"><bean:message bundle="view" key="report.opening" /></td>
					<td align="center"><bean:message bundle="view" key="report.received" /></td>
					<td align="center"><bean:message bundle="view" key="report.returns" /></td>
					<td align="center"><bean:message bundle="view" key="report.sales" /></td>
					<td align="center"><bean:message bundle="view" key="report.closing" /></td>
				</tr>
			<logic:iterate id="reportData" name="DPReportForm" property="reportDataList" indexId="m">
				<tr BGCOLOR="#FCE8E6">
					<td align="center"><bean:write name="reportData" property="hub"/></td>
					<td align="center"><bean:write name="reportData" property="circle"/></td>
					<td align="center"><bean:write name="reportData" property="zone"/></td>
					<td align="center"><bean:write name="reportData" property="distributorName"/></td>
					<td align="center"><bean:write name="reportData" property="retmsisdn"/></td>
					<td align="center"><bean:write name="reportData" property="retName"/></td>
					<td align="center"><bean:write name="reportData" property="prodType"/></td>
					<td align="center"><bean:write name="reportData" property="prodCat"/></td>
					<td align="center"><bean:write name="reportData" property="openingBal"/></td>
					<td align="center"><bean:write name="reportData" property="received"/></td>
					<td align="center"><bean:write name="reportData" property="returns"/></td>
					<td align="center"><bean:write name="reportData" property="sales"/></td>
					<td align="center"><bean:write name="reportData" property="closingBal"/></td>
				</tr>
			</logic:iterate>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="13" align="center"><html:button property="btn" value="Export To Excel" onclick="return exportToExcel();"/></td>
				</tr>
		</table>	
	</logic:notEmpty>
</html:form>
</BODY>
</html>