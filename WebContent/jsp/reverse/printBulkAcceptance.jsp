<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@page import="java.text.SimpleDateFormat"%>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=<%=request.getContextPath()%>/jsp/hbo/theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}
</script>
</HEAD>
<BODY style="height:310px" background="<%=request.getContextPath()%>/images/bg_main.gif">
<h2 align="center" ></h2>
<BR>
<html:form action="/stockAcceptTransfer.do">

	
<%try{

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String date = sdf.format(new java.util.Date()) ;


%>

<table border="0" width="100%" >
<tr><td colspan=11 align=center><strong><font size="5">SHIPMENT DETAILS</font></strong></td></tr>
<tr><td colspan=4 align=center>&nbsp;</td></tr>
<tr>
<td width="10%"><strong>PO No.: </strong></td><td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="poNo" /></td><td width="5%"></td>
<td width="10%"><strong>PO Qty: </strong></td><td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="poQty" /></td><td width="5%"></td>
<td width="10%"><strong>DC No.:</strong></td><td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="dcNo" /></td><td width="5%"></td>
<td width="20%"><strong>Invoice Qty:</strong></td><td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="invoiceQty" /></td><td width="5%"></td>

</tr>
<tr>
<td width="10%"><strong>Received:</strong></td>
<td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="totalReceived" /></td><td width="5%"></td>
<td width="20%"> <strong>Wrong Shipped:</strong></td>
<td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="totalWrongShipped" /></td><td width="5%"></td>

<td width="10%"> <strong>Missing:</strong></td>
<td width="10%"><bean:write name="DPPrintBulkAcceptanceBean" property="totalMissing" /></td><td width="5%"></td>
<td width="10%"> </td><td width="10%"> </td><td width="5%"> </td>
</tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr>
<td colspan=4>&nbsp;</td>
</tr>
</table>
<b>Serial Number Wise Details</b>
<br/><br/>
Correct Serial Numbers
<table width="100%" bordercolor="black" cellspacing="0" cellpadding="0" border='1'>
<tr>
	<td width="10%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Serial No</strong></td>
	<td width="15%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Product Name</strong></td>
	
</tr>
<logic:notEmpty property="stbcorrectList"
			name="DPPrintBulkAcceptanceBean">
<logic:iterate id="correctStbdetails" property="stbcorrectList"
				name="DPPrintBulkAcceptanceBean">
<tr>
	<td width="10%" align="center" style="border-right:1px solid;"><font size="2"> <bean:write name="correctStbdetails" property="serial_no" /></font></td>
	<td width="15%" align="center" style="border-right:1px solid;"><font size="2"><bean:write name="correctStbdetails" property="product_name" /></font></td>
	
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
<BR>Wrong Shipped Serial Numbers
<table width="100%" bordercolor="black" cellspacing="0" cellpadding="0" border='1'>
<tr>
	<td width="10%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Serial No</strong></td>
	<td width="15%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Product Name</strong></td>
	
</tr>
<logic:notEmpty property="stbwrongList"
			name="DPPrintBulkAcceptanceBean">
<logic:iterate id="wrongStbDetails" property="stbwrongList"
				name="DPPrintBulkAcceptanceBean">
<tr>
	<td width="10%" align="center" style="border-right:1px solid;"><font size="2"> <bean:write name="wrongStbDetails" property="serial_no" /></td>
	<td width="15%" align="center" style="border-right:1px solid;"><font size="2"><bean:write name="wrongStbDetails" property="product_name" /></td>
	
</tr>
</logic:iterate>
</logic:notEmpty>
</table>


Missing Serial Numbers
<table width="100%" bordercolor="black" cellspacing="0" cellpadding="0" border='1'>
<tr>
	<td width="10%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Serial No</strong></td>
	<td width="15%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Product Name</strong></td>
	
</tr>
<logic:notEmpty property="stbmissingList"
			name="DPPrintBulkAcceptanceBean">
<logic:iterate id="missingStbDetails" property="stbmissingList"
				name="DPPrintBulkAcceptanceBean">
<tr>
	<td width="10%" align="center" style="border-right:1px solid;"><font size="2"> <bean:write name="missingStbDetails" property="serial_no" /></td>
	<td width="15%" align="center" style="border-right:1px solid;"><font size="2"><bean:write name="missingStbDetails" property="product_name" /></td>
	
</tr>
</logic:iterate>
</logic:notEmpty>
</table>


<table border="0" width="100%">
	<TR>
	<td align="center" width="100%">
		<html:button property="btn" value="Close" onclick="closeWindow();" />
		<html:button property="btn" value="Print" onclick="javascript:window.print();" />
		</td>
	</TR>	
</table>

<%}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>
<script>
	window.print();
</script>
</BODY>
</html:html>
