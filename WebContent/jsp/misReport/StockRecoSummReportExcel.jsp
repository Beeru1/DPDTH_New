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
	response.setHeader("content-disposition","attachment;filename=StockRecoSummaryReport.xls");
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
					<td class="colhead" align="center"><b>Distributor Login ID</b></td>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>STB Type</b></td>
					<td class="colhead" align="center"><b>Opening Stock</b></td>
					<td class="colhead" align="center"><b>Received</b></td>
					<td class="colhead" align="center"><b>Returned</b></td>
					<td class="colhead" align="center"><b>Activation</b></td>
					<td class="colhead" align="center"><b>Closing Stock</b></td>
					
		</tr>
		<logic:notEmpty name="StockRecoSummReportBean" property="reportList">
 		<logic:iterate id="reportData" name="StockRecoSummReportBean" property="reportList" indexId="m">
				<tr>
				
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="openingStock"/></td>
					<td align="center"><bean:write name="reportData" property="receivedStock"/></td>
					<td align="center"><bean:write name="reportData" property="returnedStock"/></td>
					<td align="center"><bean:write name="reportData" property="activation"/></td>
					<td align="center"><bean:write name="reportData" property="closingStock"/></td>
					
					
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