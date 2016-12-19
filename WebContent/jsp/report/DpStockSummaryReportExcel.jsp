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
	response.setHeader("content-disposition","attachment;filename=StockSummaryReport.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>Parent Account</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>Non Serialized (As on date)</b></td>
					<td class="colhead" align="center"><b>Serialized</b></td>
					<td class="colhead" align="center"><b>Total Received</b></td>
					<td class="colhead" align="center"><b>Total Serialized Activation</b></td>
					<td class="colhead" align="center"><b>Damaged (Available with Dist)</b></td>
					<td class="colhead" align="center"><b>Damaged (Sent To warehouse)</b></td>
					<td class="colhead" align="center"><b>Total Inhand Stock</b></td>
					
		</tr>
		<logic:notEmpty name="StockSummaryReportBean" property="reportList">
 		<logic:iterate id="reportData" name="StockSummaryReportBean" property="reportList">
				<tr>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="parentAccount"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="nonSerailizedAsonDate"/></td>
					<td align="center"><bean:write name="reportData" property="serialized"/></td>
					<td align="center"><bean:write name="reportData" property="totalReceived"/></td>
					<td align="center"><bean:write name="reportData" property="totalSerializedActivation"/></td>
					<td align="center"><bean:write name="reportData" property="damagedAvailableDist"/></td>
					<td align="center"><bean:write name="reportData" property="damagedS2W"/></td>
					<td align="center"><bean:write name="reportData" property="totalInhandStock"/></td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>