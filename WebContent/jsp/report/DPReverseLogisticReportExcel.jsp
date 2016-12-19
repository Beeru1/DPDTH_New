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
	response.setHeader("content-disposition","attachment;filename=ReverseLogisticReport.xls");
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
					<td class="colhead" align="center"><b>Distributor</b></td>
					<td class="colhead" align="center"><b>Product</b></td>
					<td class="colhead" align="center"><b>Churn Received</b></td>
					<td class="colhead" align="center"><b>Upgrade Received</b></td>
					<td class="colhead" align="center"><b>DOA Received</b></td>
					<td class="colhead" align="center"><b>Defective Received</b></td>
					<td class="colhead" align="center"><b>C2S Received</b></td>
					<td class="colhead" align="center"><b>Sent To Warehouse</b></td>
					<td class="colhead" align="center"><b>Repaired</b></td>
		</tr>
 		<logic:iterate id="reportData" name="DPReverseLogisticReportFormBean" property="revLogReportList">
				<tr>
					<td align="center"><bean:write name="reportData" property="accountName"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="churnInventory"/></td>
					<td align="center"><bean:write name="reportData" property="upgradeInventory"/></td>
					<td align="center"><bean:write name="reportData" property="doaInventory"/></td>
					<td align="center"><bean:write name="reportData" property="defectiveInventory"/></td>
					<td align="center"><bean:write name="reportData" property="c2sInventory"/></td>
					<td align="center"><bean:write name="reportData" property="s2wInventory"/></td>
					<td align="center"><bean:write name="reportData" property="repInventory"/></td>
				</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>