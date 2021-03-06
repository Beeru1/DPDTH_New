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
	response.setHeader("content-disposition","attachment;filename=InventoryStatusReport.xls");
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
					<td class="colhead" align="center"><b>S.NO.</b></td>
					<td class="colhead" align="center"><b>Account Name</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>Serial Number</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					<td class="colhead" align="center"><b>Stock Date</b></td>
		</tr>
 		<logic:iterate id="reportData" name="DPInventoryStatusFormBean" property="reportDataList" indexId="m">
				<tr>
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="accountName"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center">&nbsp;&nbsp;<bean:write name="reportData" property="serialNo"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
					<td align="center"><bean:write name="reportData" property="distributorPurchaseDate"/></td>
				</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>