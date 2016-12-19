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
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=reportSalesSummary.xls");
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

 <logic:notEmpty name="DPReportForm" property="reportDataList">
	<br><br>
		<table width="98%" border="1">
				<tr bgcolor="gray">
					<td class="colhead" align="center"><bean:message bundle="view" key="report.retname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.category" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.beatname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.fsename" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.zsm" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.tsm" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.grossactivation" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.easyrecharge" /></td>
				</tr>
			<logic:iterate id="reportData" name="DPReportForm" property="reportDataList" indexId="m">
				<tr>
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
		</table>	
	</logic:notEmpty>

</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>