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
			"attachment;filename=reportAgeing.xls");
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
				<tr bgcolor="red">
					<td class="colhead" align="center"><bean:message bundle="view" key="report.hub" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.circle" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.zone" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.distname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.retcode" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.retname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.fsename" /></td>					
					<td class="colhead" align="center"><bean:message bundle="view" key="report.prodcat" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.prodtype" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.lessthanequalto90" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.greaterthan90" /></td>
				</tr>
			<logic:iterate id="reportData" name="DPReportForm" property="reportDataList" indexId="m">
				<logic:equal value="zzzz" name="reportData" property="prodCat">
					<tr bgcolor="gray">
						<td colspan="9" align="center"><b>Total</b></td>
						<td align="center"><bean:write name="reportData" property="noLtEq90"/></td>
						<td align="center"><bean:write name="reportData" property="noGt90"/></td>
					</tr>
				</logic:equal>
				<logic:notEqual value="zzzz" name="reportData" property="prodCat">
				<tr bgcolor="#FFE4E1">
					<td align="center"><bean:write name="reportData" property="hub"/></td>
					<td align="center"><bean:write name="reportData" property="circle"/></td>
					<td align="center"><bean:write name="reportData" property="zone"/></td>
					<td align="center"><bean:write name="reportData" property="distributorName"/></td>
					<td align="center"><bean:write name="reportData" property="retmsisdn"/></td>
					<td align="center"><bean:write name="reportData" property="retName"/></td>
					<td align="center"><bean:write name="reportData" property="fseName"/></td>					
					<td align="center"><bean:write name="reportData" property="prodType"/></td>
					<td align="center"><bean:write name="reportData" property="prodCat"/></td>
					<td align="center"><bean:write name="reportData" property="noLtEq90"/></td>
					<td align="center"><bean:write name="reportData" property="noGt90"/></td>
				</tr>				
				</logic:notEqual>
			</logic:iterate>
		</table>	
	</logic:notEmpty>
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>