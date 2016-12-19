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
	response.setHeader("content-disposition","attachment;filename=POStatusReport.xls");
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
	int i = 0;
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>

	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>Sl.</b></td>
					<td class="colhead" align="center"><b>Name</b></td>
					<td class="colhead" align="center"><b>Login Name</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>PR Date</b></td>
					<td class="colhead" align="center"><b>PR No.</b></td>
					<td class="colhead" align="center"><b>PO No.</b></td>
					<td class="colhead" align="center"><b>PO Date</b></td>
					<td class="colhead" align="center"><b>PO Status</b></td>
					<td class="colhead" align="center"><b>PO Acceptance Date</b></td>
					<td class="colhead" align="center"><b>Invoice No.</b></td>
					<td class="colhead" align="center"><b>Invoice Date</b></td>
					<td class="colhead" align="center"><b>DC No.</b></td>
					<td class="colhead" align="center"><b>DC Date</b></td>
					<td class="colhead" align="center"><b>DD/Cheque No.</b></td>
					<td class="colhead" align="center"><b>DD/Cheque Date</b></td>
					<td class="colhead" align="center"><b>Raised Qty.</b></td>
					<td class="colhead" align="center"><b>PO Qty.</b></td>
					<td class="colhead" align="center"><b>Invoice Qty.</b></td>
					<td class="colhead" align="center"><b>Unit</b></td>
					<td class="colhead" align="center"><b>PR Satus</b></td>
					<td class="colhead" align="center"><b>Remarks</b></td>
		</tr>
 		<logic:iterate id="poReportData" name="DPReverseLogisticReportFormBean" property="poReportList">
				<tr>
					<td align="center"><%=++i %></td>
					<td align="center"><bean:write name="poReportData" property="name"/></td>
					<td align="center"><bean:write name="poReportData" property="loginName"/></td>
					<td align="center"><bean:write name="poReportData" property="circleName"/></td>
					<td align="center"><bean:write name="poReportData" property="productName"/></td>
					<td align="center"><bean:write name="poReportData" property="prDate"/></td>
					<td align="center"><bean:write name="poReportData" property="prNo"/></td>
					<td align="center"><bean:write name="poReportData" property="poNo"/></td>
					<td align="center"><bean:write name="poReportData" property="poDate"/></td>
					<td align="center"><bean:write name="poReportData" property="poStatus"/></td>
					<td align="center"><bean:write name="poReportData" property="poAccDate"/></td>
					<td align="center"><bean:write name="poReportData" property="invoiceNo"/></td>
					<td align="center"><bean:write name="poReportData" property="invoiceDate"/></td>
					<td align="center"><bean:write name="poReportData" property="dcNo"/></td>
					<td align="center"><bean:write name="poReportData" property="dcDate"/></td>
					<td align="center"><bean:write name="poReportData" property="ddNo"/></td>
					<td align="center"><bean:write name="poReportData" property="ddDate"/></td>
					<td align="center"><bean:write name="poReportData" property="raisedQty"/></td>
					<td align="center"><bean:write name="poReportData" property="poQty"/></td>
					<td align="center"><bean:write name="poReportData" property="invoiceQty"/></td>	
					<td align="center"><bean:write name="poReportData" property="unit"/></td>			
					<td align="center"><bean:write name="poReportData" property="prStatus"/></td>
					<td align="center"><bean:write name="poReportData" property="remarks"/></td>
					
				</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>