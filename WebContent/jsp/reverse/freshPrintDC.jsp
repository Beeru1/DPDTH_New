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
<LINK href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet"
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
<BODY background="${pageContext.request.contextPath}/images/bg_main.gif">
<h2 align="center" ></h2>
<BR>
<html:form action="/stockAcceptTransfer.do">

	
<%try{

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String date = sdf.format(new java.util.Date()) ;


%>

<table border="0" width="100%" >
<tr><td colspan=4 align=center><strong><font size="6">DELIVERY CHALLAN</font></strong></td></tr>
<tr><td colspan=4 align=center>&nbsp;</td></tr>
<tr>
<td width="15%"><strong>DC NO.: </strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="dc_no" /></td>
<td width="15%"><strong>DC DATE:</strong></td>
<td width="25%">
	<logic:notEmpty name="DPReverseCollectionBean" property="dc_date">
		<bean:write name="DPReverseCollectionBean" property="dc_date" />
	</logic:notEmpty>
	<logic:empty name="DPReverseCollectionBean" property="dc_date">
		<%=date%>
	</logic:empty>
</td>
</tr>
<tr>
<td width="15%"><strong>From: </strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="from_name" /></td>
<td width="15%"> <strong>To:</strong></td>
<td width="25%"><bean:write name="DPReverseCollectionBean" property="wh_name" /></td>
</tr>
<tr>
<td width="15%"><strong>City:</strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="city_name" /></td>
<td width="15%"><strong>Address:</strong></td>
<td width="25%"><bean:write name="DPReverseCollectionBean" property="wh_address" /></td>
</tr>
<tr>
<td width="15%"><strong>State: </strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="state_name" /></td>

</tr>
<tr>
<td width="15%"><strong>Name: </strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="contact_name" /></td>
<td width="15%"><strong>Phone:</strong></td>
<td width="25%"><bean:write name="DPReverseCollectionBean" property="wh_phone" /></td>
</tr>
<tr>
<td width="15%"><strong>Mob No: </strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="mobile_no" /></td>

</tr>
<tr>
<td width="15%"><strong>Courier Agency</strong></td>
<td width="45%"><bean:write name="DPReverseCollectionBean" property="courier_agency" /></td>
<td width="15%"><strong>Docket No: </strong></td>
<td width="25%"><bean:write name="DPReverseCollectionBean" property="docket_no" /></td>

</tr>
<tr>
<td colspan=4>&nbsp;</td>
</tr>
</table>

<table width="100%" bordercolor="black" cellspacing="0" cellpadding="0" border='1'>
<tr>
	<td width="10%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Serial No</strong></td>
	<td width="15%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Product</strong></td>
	<td width="10%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Return Date</strong></td>
	<td width="30%" align="center" style="border-bottom:1px solid;"><strong>Remark</strong></td>
</tr>
<logic:notEmpty property="dcDetailsCollectionList"
			name="DPReverseCollectionBean">
<logic:iterate id="dcdetails" property="dcDetailsCollectionList"
				name="DPReverseCollectionBean">
<tr>
	<td width="10%" align="center" style="border-right:1px solid;"><font size="2"> <bean:write name="dcdetails" property="serial_no" /></font></td>
	<td width="15%" align="center" style="border-right:1px solid;"><font size="2"><bean:write name="dcdetails" property="product_name" /></font></td>
	<td width="10%" align="center" style="border-right:1px solid;"><font size="2"><bean:write name="dcdetails" property="collection_date" /></font></td>
	<td width="30%" align="center" ><font size="2"><bean:write name="dcdetails" property="remarks" /></font></td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
<BR>
<table>
<tr>
	<td colspan="6" align="left">
		<strong>DC Remark:</strong><bean:write name="DPReverseCollectionBean" property="remarks" />
</td>
</tr>
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
