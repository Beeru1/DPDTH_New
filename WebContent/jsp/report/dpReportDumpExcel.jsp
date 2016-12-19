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
			"attachment;filename=reportDump.xls");
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

 <logic:notEmpty property="reportDataList" name="DPReportForm">	
 	<table align="center" border="1">
 				<tr bgcolor="gray">
					<td class="colhead" align="center"><bean:message bundle="view" key="report.hub" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.circle" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.zone" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.distname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.retcode" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.retname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.prodcat" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.prodtype" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.opening" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.received" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.returns" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.sales" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="report.closing" /></td>
				</tr>
			<logic:iterate id="reportData" name="DPReportForm" property="reportDataList" indexId="m">
				<tr>
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
 	</table>
 </logic:notEmpty>
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>