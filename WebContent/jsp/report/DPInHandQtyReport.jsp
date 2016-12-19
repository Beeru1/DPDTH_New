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
	response.setHeader("content-disposition","attachment;filename=InHandQtyReport.xls");
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
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>Distributor login id</b></td>
					<td class="colhead" align="center"><b>Distributor Account Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
					<td class="colhead" align="center"><b>Oracle locator</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>PO date</b></td>
					<td class="colhead" align="center"><b>PR No.</b></td>
					<td class="colhead" align="center"><b>Quantity</b></td>
					<td class="colhead" align="center"><b>Non srialized stock</b></td>
					<td class="colhead" align="center"><b>Serialized stock</b></td>
					<td class="colhead" align="center"><b>Total DP Stock</b></td>
					<td class="colhead" align="center"><b>Inhand Qty</b></td>
					<td class="colhead" align="center"><b>Difference</b></td>
					
					
					
					

		</tr>
 		<logic:iterate id="reportData" name="DPReverseLogisticReportFormBean" property="revLogReportList">
				<tr>
					<td align="center"><bean:write name="reportData" property="circle"/></td>
					<td align="center"><bean:write name="reportData" property="loginName"/></td>
					<td align="center"><bean:write name="reportData" property="account_name"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="distributor_locator_code"/></td>
					<td align="center"><bean:write name="reportData" property="product_name"/></td>
					<td align="center"><bean:write name="reportData" property="last_pr_date"/></td>
					<td align="center"><bean:write name="reportData" property="last_pr_number"/></td>
					<td align="center"><bean:write name="reportData" property="physical_stock"/></td>
					<td align="center"><bean:write name="reportData" property="non_ser_qty"/></td>
					<td align="center"><bean:write name="reportData" property="ser_qty"/></td>
					<td align="center"><bean:write name="reportData" property="total_dp_qty"/></td>
					<td align="center"><bean:write name="reportData" property="in_hand_qty"/></td>
					<td align="center"><bean:write name="reportData" property="difference"/></td>
					
					
					
				</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>