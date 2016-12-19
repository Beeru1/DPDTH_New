<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=UnbarredStock.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</HEAD>


<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeypress="return checkKeyPressed();" onload="setSearchControlDisabled()">
	<table cellspacing="0" cellpadding="0" border="1" width="100%" height="100%" valign="top">
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>		
    <html:form action="reportAction">
    <logic:empty name="ReportForUnbarringForm" property="serialNos">
    <tr>
	    <td align="center" bgcolor="#cd0504">
			<bean:message bundle="view"
			key="report.noRecordFound" />
	    </td>
    </tr>
    </logic:empty>
					<logic:notEmpty name="ReportForUnbarringForm" property="serialNos">
					
						<tr align="center" bgcolor="#104e8b">
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.serialNos" /></td>
					</tr>
						<logic:iterate id="report" name="ReportForUnbarringForm" property="serialNos" indexId="i">
						<TR>
								<td align="center"><bean:write name="report" property="serialNo" /></td>
							</tr>
						</logic:iterate>
						</logic:notEmpty>
						</html:form>
					</table>
</BODY>
</html:html>