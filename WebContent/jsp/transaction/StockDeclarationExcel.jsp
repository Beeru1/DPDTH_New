<!-- Added by Mohammad Aslam -->
<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
	<HEAD>
		<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
		<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<META name="GENERATOR" content="IBM WebSphere Studio">
		<META http-equiv="Content-Style-Type" content="text/css">
		<TITLE>Stock Details Report</TITLE>
		<%
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-disposition","attachment;filename=StockDetailsReport.xls");
			response.setHeader("Cache-Control", "must-revalidate");
			response.setHeader( "Pragma", "public" );
		%>
	</HEAD>																		
	<BODY>
		<% 
    		String rowDark ="#FCE8E6";
    		String rowLight = "#FFFFFF";
		%>
		<table border="1">
			<tr>
				<td class="colhead" align="center" colspan='2'><b>&nbsp;</b></td>
				<td class="colhead" align="center" colspan='2'><b>As Per DP</b></td>
				<td class="colhead" align="center"><b>As Per Partner</b></td>
				<td class="colhead" align="center" colspan='2'><b>&nbsp;</b></td>
			</tr>
			<tr>
				<td class="colhead" align="center"><b>Distributor Name</b></td>
				<td class="colhead" align="center"><b>Product Name</b></td>
				<td class="colhead" align="center"><b>DP Stock</b></td>
				<td class="colhead" align="center"><b>Opening Stock</b></td>
				<td class="colhead" align="center"><b>Closing Stock(Overall)</b></td>
				<td class="colhead" align="center"><b>Comments</b></td>
				<td class="colhead" align="center"><b>Declaration Date </b></td>
			</tr>
	 		<logic:iterate id="distributorDetails" name="distributorDetailsList" indexId="i">
	 		<tr BGCOLOR='<%=((i.intValue()+1) % 2 == 0 ? rowDark : rowLight) %>'>
				<td align="center"><bean:write name="distributorDetails" property="distributorName"/></td>
				<td align="center"><bean:write name="distributorDetails" property="productName"/></td>
				<td align="center"><bean:write name="distributorDetails" property="intDPStock"/></td>
				<td align="center"><bean:write name="distributorDetails" property="intOpenStock"/></td>
				<td align="center"><bean:write name="distributorDetails" property="closingStock"/></td>
				<td align="center"><bean:write name="distributorDetails" property="comments"/></td>
				<td align="center"><bean:write name="distributorDetails" property="creationDate"/></td>				
			</tr>
			</logic:iterate>
	 	</table> 
		<P><BR></P>
	</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>