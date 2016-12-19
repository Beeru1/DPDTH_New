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
	response.setHeader("content-disposition","attachment;filename=StockDetailReport.xls");
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
					<td class="colhead" align="center"><b>Account Name</b></td>
					<td class="colhead" align="center"><b>Role</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>Opening Stock</b></td>
					<td class="colhead" align="center"><b>Total Stock Received</b></td>
					<td class="colhead" align="center"><b>Allocation</b></td>
					<td class="colhead" align="center"><b>Sold by retailers</b></td>
					<td class="colhead" align="center"><b>Closing stock</b></td>
		</tr>
 		<logic:iterate id="reportData" name="DPStockDetailsReportBean" property="reportStockDataList">
				<tr>
					<td align="center"><bean:write name="reportData" property="accountName"/></td>
					<td align="center"><bean:write name="reportData" property="role"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="openingStockQty"/></td>
					<td align="center"><bean:write name="reportData" property="stockReceivedQty"/></td>
					<td align="center"><bean:write name="reportData" property="allocationQty"/></td>
					<td align="center"><bean:write name="reportData" property="saleByRetailersQty"/></td>
					<td align="center"><bean:write name="reportData" property="closingStock"/></td>
				</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>