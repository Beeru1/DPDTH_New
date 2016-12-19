<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>


<%
System.out.println("--------------+++++++++++++++++++SerialNo.jsp for DAMAGE+++++++++++++++++++++++++--------------------");
 %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=./theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}

</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif">
<h2 align="center" ></h2>
<BR>
<html:form action="dpInitiateFnfAction.do">
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<IMG src="<%=request.getContextPath()%>/images/serialNos.gif" width="450" height="22" alt="">
	
<%try{%>
<logic:notEmpty property="stockList" name="DpInitiateFnfFormBean">
<div style="width:100%; height:500px; overflow:scroll;" >
<table border="2" width="100%" >
<tr><td>
<table border="0" width="100%" >
<tr class="text white">
<td align="center"><h3>Product</h3></td>
<td align="center"><h3>Serialized Stock</h3></td>					
<td align="center"><h3>Non-Serialized Stock</h3></td>
<td align="center"><h3>In-Transit PO Stock</h3></td>
<td align="center"><h3>Damaged Pending Stock</h3></td>
<td align="center"><h3>In-Transit DC Stock</h3></td>
<td align="center"><h3>Upgrade Pending</h3></td>
<td align="center"><h3>CPE Recovery Pending</h3></td>
<td align="center"><h3>Total Stock</h3></td>
</tr>

	<logic:iterate name="stockList" id="serialNos" indexId="i" type="com.ibm.dp.dto.DpInitiateFnfDto">
	<tr BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
	<td align="center"><b><bean:write name="serialNos" property="product"/></td>
	<td align="center"><b><bean:write name="serialNos" property="serializedStock"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="nonSerializedStock"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="inTransitPOStock"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="damagedPendingStock"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="inTransitDCStock"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="upgradePending"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="recoveryPending"/></b></td>
	<td align="center"><b><bean:write name="serialNos" property="sum"/></b></td>
	</tr>
	</logic:iterate>

</table>
</td></tr>
</table>
</div>

<table border="1" width="100%">
	<TR>
	<td align="center" width="100%">
		<html:button property="btn" value="Close" onclick="closeWindow();" /></td>
	</TR>	
</table>
</logic:notEmpty>	
<%}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>
</BODY>
</html:html>
