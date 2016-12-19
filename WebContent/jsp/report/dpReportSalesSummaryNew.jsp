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
<LINK href="${pageContext.request.contextPath}/theme/style.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>
<TITLE>Sales Summary Report</TITLE>

<script>
function exportToExcel(){
	var url="reportActionExcel.do?methodName=getReportData&reportId=7";
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
}
</script>
</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>

<html:form action="/initReportAction.do">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/salesSummary.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<logic:messagesPresent message="true">
	<tr align="center">
		<td colspan="4" align="center">			
			<html:messages id="msg" property="NO_RECORD" bundle="hboView" message="true">
				<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
			</html:messages>
		</td>
	</tr>
</logic:messagesPresent>
</table>
	<logic:notEmpty name="DPReportForm" property="reportDataList">
	<br><br>
		<table width="98%">
				<tr bgcolor="#CD0504" style="color:#ffffff !important;">
					<td align="center"><bean:message bundle="view" key="report.retname" /></td>
					<td align="center"><bean:message bundle="view" key="report.category" /></td>
					<td align="center"><bean:message bundle="view" key="report.beatname" /></td>
					<td align="center"><bean:message bundle="view" key="report.fsename" /></td>
					<td align="center"><bean:message bundle="view" key="report.zsm" /></td>
					<td align="center"><bean:message bundle="view" key="report.tsm" /></td>
					<td align="center"><bean:message bundle="view" key="report.grossactivation" /></td>
					<td align="center"><bean:message bundle="view" key="report.easyrecharge" /></td>
				</tr>
			<logic:iterate id="reportData" name="DPReportForm" property="reportDataList" indexId="m">
				<tr BGCOLOR="#FCE8E6">
					<td align="center"><bean:write name="reportData" property="retName"/></td>
					<td align="center"><bean:write name="reportData" property="category"/></td>
					<td align="center"><bean:write name="reportData" property="beatName"/></td>
					<td align="center"><bean:write name="reportData" property="fseName"/></td>
					<td align="center"><bean:write name="reportData" property="zsmName"/></td>
					<td align="center"><bean:write name="reportData" property="tmName"/></td>
					<td align="center"><bean:write name="reportData" property="grossActivations"/></td>
					<td align="center"><bean:write name="reportData" property="easyRecharge"/></td>
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