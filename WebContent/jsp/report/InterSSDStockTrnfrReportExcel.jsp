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
	response.setHeader("content-disposition","attachment;filename=InterSSDTransferReport.xls");
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
					<td class="colhead" align="center"><b>Sl. No.</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>Date of Initiation</b></td>
					<td class="colhead" align="center"><b>ZBM/ZSM</b></td>
					<td class="colhead" align="center"><b>From Distributor</b></td>
					<td class="colhead" align="center"><b>From TSM</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>Quantity Requested</b></td>
					<td class="colhead" align="center"><b>Date of Transfer</b></td>
					
					<td class="colhead" align="center"><b>To Distributor</b></td>
					<td class="colhead" align="center"><b>To TSM</b></td>
					<td class="colhead" align="center"><b>Quantity Transfered</b></td>
					<td class="colhead" align="center"><b>Quantity Received</b></td>
					<td class="colhead" align="center"><b>Date of Receipt</b></td>
		</tr>
 		<logic:notEmpty name="InterSSDStockTrnfrReportBean" property="reportDataList">
 		<logic:iterate id="reportData" name="InterSSDStockTrnfrReportBean" property="reportDataList" indexId="m">
				<tr>
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="initiationDate"/></td>
					<td align="center"><bean:write name="reportData" property="zbmZsmName"/></td>
					<td align="center"><bean:write name="reportData" property="fromDistName"/></td>
					<td align="center"><bean:write name="reportData" property="fromTSM"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="requestedQty"/></td>
					<td align="center"><bean:write name="reportData" property="transferDate"/></td>
					<td align="center"><bean:write name="reportData" property="toDistName"/></td>
					<td align="center"><bean:write name="reportData" property="toTSM"/></td>
					<td align="center"><bean:write name="reportData" property="transferedQty"/></td>
					<td align="center"><bean:write name="reportData" property="transferedQty"/></td>
					<td align="center"><bean:write name="reportData" property="receiptDate"/></td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>